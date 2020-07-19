package net.minecraft.world.end;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.dragon.phase.PhaseList;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeEndDecorator;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.WorldGenEndGateway;
import net.minecraft.world.gen.feature.WorldGenEndPodium;
import net.minecraft.world.gen.feature.WorldGenSpikes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DragonFightManager
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Predicate<EntityPlayerMP> VALID_PLAYER = Predicates.<EntityPlayerMP> and (EntitySelectors.IS_ALIVE, EntitySelectors.withinRange(0.0D, 128.0D, 0.0D, 192.0D));
    private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(new TextComponentTranslation("entity.EnderDragon.name", new Object[0]), BossInfo.Color.PINK, BossInfo.Overlay.PROGRESS)).setPlayEndBossMusic(true).setCreateFog(true);
    private final WorldServer world;
    private final List<Integer> gateways = Lists.<Integer>newArrayList();
    private final BlockPattern portalPattern;
    private int ticksSinceDragonSeen;
    private int aliveCrystals;
    private int ticksSinceCrystalsScanned;
    private int ticksSinceLastPlayerScan;
    private boolean dragonKilled;
    private boolean previouslyKilled;
    private UUID dragonUniqueId;
    private boolean scanForLegacyFight = true;
    private BlockPos exitPortalLocation;
    private DragonSpawnManager respawnState;
    private int respawnStateTicks;
    private List<EntityEnderCrystal> crystals;

    public DragonFightManager(WorldServer worldIn, NBTTagCompound compound)
    {
        this.world = worldIn;

        if (compound.hasKey("DragonKilled", 99))
        {
            if (compound.hasUniqueId("DragonUUID"))
            {
                this.dragonUniqueId = compound.getUniqueId("DragonUUID");
            }

            this.dragonKilled = compound.getBoolean("DragonKilled");
            this.previouslyKilled = compound.getBoolean("PreviouslyKilled");

            if (compound.getBoolean("IsRespawning"))
            {
                this.respawnState = DragonSpawnManager.START;
            }

            if (compound.hasKey("ExitPortalLocation", 10))
            {
                this.exitPortalLocation = NBTUtil.getPosFromTag(compound.getCompoundTag("ExitPortalLocation"));
            }
        }
        else
        {
            this.dragonKilled = true;
            this.previouslyKilled = true;
        }

        if (compound.hasKey("Gateways", 9))
        {
            NBTTagList nbttaglist = compound.getTagList("Gateways", 3);

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                this.gateways.add(Integer.valueOf(nbttaglist.getIntAt(i)));
            }
        }
        else
        {
            this.gateways.addAll(ContiguousSet.create(Range.closedOpen(Integer.valueOf(0), Integer.valueOf(20)), DiscreteDomain.integers()));
            Collections.shuffle(this.gateways, new Random(worldIn.getSeed()));
        }

        this.portalPattern = FactoryBlockPattern.start().aisle("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").aisle("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").aisle("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").aisle("  ###  ", " #   # ", "#     #", "#  #  #", "#     #", " #   # ", "  ###  ").aisle("       ", "  ###  ", " ##### ", " ##### ", " ##### ", "  ###  ", "       ").where('#', BlockWorldState.hasState(BlockMatcher.forBlock(Blocks.BEDROCK))).build();
    }

    public NBTTagCompound getCompound()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        if (this.dragonUniqueId != null)
        {
            nbttagcompound.setUniqueId("DragonUUID", this.dragonUniqueId);
        }

        nbttagcompound.setBoolean("DragonKilled", this.dragonKilled);
        nbttagcompound.setBoolean("PreviouslyKilled", this.previouslyKilled);

        if (this.exitPortalLocation != null)
        {
            nbttagcompound.setTag("ExitPortalLocation", NBTUtil.createPosTag(this.exitPortalLocation));
        }

        NBTTagList nbttaglist = new NBTTagList();
        Iterator iterator = this.gateways.iterator();

        while (iterator.hasNext())
        {
            int i = ((Integer)iterator.next()).intValue();
            nbttaglist.appendTag(new NBTTagInt(i));
        }

        nbttagcompound.setTag("Gateways", nbttaglist);
        return nbttagcompound;
    }

    public void tick()
    {
        this.bossInfo.setVisible(!this.dragonKilled);

        if (++this.ticksSinceLastPlayerScan >= 20)
        {
            this.updateplayers();
            this.ticksSinceLastPlayerScan = 0;
        }

        if (!this.bossInfo.getPlayers().isEmpty())
        {
            if (this.scanForLegacyFight)
            {
                LOGGER.info("Scanning for legacy world dragon fight...");
                this.loadChunks();
                this.scanForLegacyFight = false;
                boolean flag = this.hasDragonBeenKilled();

                if (flag)
                {
                    LOGGER.info("Found that the dragon has been killed in this world already.");
                    this.previouslyKilled = true;
                }
                else
                {
                    LOGGER.info("Found that the dragon has not yet been killed in this world.");
                    this.previouslyKilled = false;
                    this.generatePortal(false);
                }

                List<EntityDragon> list = this.world.getEntities(EntityDragon.class, EntitySelectors.IS_ALIVE);

                if (list.isEmpty())
                {
                    this.dragonKilled = true;
                }
                else
                {
                    EntityDragon entitydragon = list.get(0);
                    this.dragonUniqueId = entitydragon.getUniqueID();
                    LOGGER.info("Found that there's a dragon still alive ({})", (Object)entitydragon);
                    this.dragonKilled = false;

                    if (!flag)
                    {
                        LOGGER.info("But we didn't have a portal, let's remove it.");
                        entitydragon.setDead();
                        this.dragonUniqueId = null;
                    }
                }

                if (!this.previouslyKilled && this.dragonKilled)
                {
                    this.dragonKilled = false;
                }
            }

            if (this.respawnState != null)
            {
                if (this.crystals == null)
                {
                    this.respawnState = null;
                    this.respawnDragon();
                }

                this.respawnState.process(this.world, this, this.crystals, this.respawnStateTicks++, this.exitPortalLocation);
            }

            if (!this.dragonKilled)
            {
                if (this.dragonUniqueId == null || ++this.ticksSinceDragonSeen >= 1200)
                {
                    this.loadChunks();
                    List<EntityDragon> list1 = this.world.getEntities(EntityDragon.class, EntitySelectors.IS_ALIVE);

                    if (list1.isEmpty())
                    {
                        LOGGER.debug("Haven't seen the dragon, respawning it");
                        this.createNewDragon();
                    }
                    else
                    {
                        LOGGER.debug("Haven't seen our dragon, but found another one to use.");
                        this.dragonUniqueId = ((EntityDragon)list1.get(0)).getUniqueID();
                    }

                    this.ticksSinceDragonSeen = 0;
                }

                if (++this.ticksSinceCrystalsScanned >= 100)
                {
                    this.findAliveCrystals();
                    this.ticksSinceCrystalsScanned = 0;
                }
            }
        }
    }

    protected void setRespawnState(DragonSpawnManager state)
    {
        if (this.respawnState == null)
        {
            throw new IllegalStateException("Dragon respawn isn't in progress, can't skip ahead in the animation.");
        }
        else
        {
            this.respawnStateTicks = 0;

            if (state == DragonSpawnManager.END)
            {
                this.respawnState = null;
                this.dragonKilled = false;
                EntityDragon entitydragon = this.createNewDragon();

                for (EntityPlayerMP entityplayermp : this.bossInfo.getPlayers())
                {
                    CriteriaTriggers.SUMMONED_ENTITY.trigger(entityplayermp, entitydragon);
                }
            }
            else
            {
                this.respawnState = state;
            }
        }
    }

    private boolean hasDragonBeenKilled()
    {
        for (int i = -8; i <= 8; ++i)
        {
            for (int j = -8; j <= 8; ++j)
            {
                Chunk chunk = this.world.getChunkFromChunkCoords(i, j);

                for (TileEntity tileentity : chunk.getTileEntityMap().values())
                {
                    if (tileentity instanceof TileEntityEndPortal)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Nullable
    private BlockPattern.PatternHelper findExitPortal()
    {
        for (int i = -8; i <= 8; ++i)
        {
            for (int j = -8; j <= 8; ++j)
            {
                Chunk chunk = this.world.getChunkFromChunkCoords(i, j);

                for (TileEntity tileentity : chunk.getTileEntityMap().values())
                {
                    if (tileentity instanceof TileEntityEndPortal)
                    {
                        BlockPattern.PatternHelper blockpattern$patternhelper = this.portalPattern.match(this.world, tileentity.getPos());

                        if (blockpattern$patternhelper != null)
                        {
                            BlockPos blockpos = blockpattern$patternhelper.translateOffset(3, 3, 3).getPos();

                            if (this.exitPortalLocation == null && blockpos.getX() == 0 && blockpos.getZ() == 0)
                            {
                                this.exitPortalLocation = blockpos;
                            }

                            return blockpattern$patternhelper;
                        }
                    }
                }
            }
        }

        int k = this.world.getHeight(WorldGenEndPodium.END_PODIUM_LOCATION).getY();

        for (int l = k; l >= 0; --l)
        {
            BlockPattern.PatternHelper blockpattern$patternhelper1 = this.portalPattern.match(this.world, new BlockPos(WorldGenEndPodium.END_PODIUM_LOCATION.getX(), l, WorldGenEndPodium.END_PODIUM_LOCATION.getZ()));

            if (blockpattern$patternhelper1 != null)
            {
                if (this.exitPortalLocation == null)
                {
                    this.exitPortalLocation = blockpattern$patternhelper1.translateOffset(3, 3, 3).getPos();
                }

                return blockpattern$patternhelper1;
            }
        }

        return null;
    }

    private void loadChunks()
    {
        for (int i = -8; i <= 8; ++i)
        {
            for (int j = -8; j <= 8; ++j)
            {
                this.world.getChunkFromChunkCoords(i, j);
            }
        }
    }

    private void updateplayers()
    {
        Set<EntityPlayerMP> set = Sets.<EntityPlayerMP>newHashSet();

        for (EntityPlayerMP entityplayermp : this.world.getPlayers(EntityPlayerMP.class, VALID_PLAYER))
        {
            this.bossInfo.addPlayer(entityplayermp);
            set.add(entityplayermp);
        }

        Set<EntityPlayerMP> set1 = Sets.newHashSet(this.bossInfo.getPlayers());
        set1.removeAll(set);

        for (EntityPlayerMP entityplayermp1 : set1)
        {
            this.bossInfo.removePlayer(entityplayermp1);
        }
    }

    private void findAliveCrystals()
    {
        this.ticksSinceCrystalsScanned = 0;
        this.aliveCrystals = 0;

        for (WorldGenSpikes.EndSpike worldgenspikes$endspike : BiomeEndDecorator.getSpikesForWorld(this.world))
        {
            this.aliveCrystals += this.world.getEntitiesWithinAABB(EntityEnderCrystal.class, worldgenspikes$endspike.getTopBoundingBox()).size();
        }

        LOGGER.debug("Found {} end crystals still alive", (int)this.aliveCrystals);
    }

    public void processDragonDeath(EntityDragon dragon)
    {
        if (dragon.getUniqueID().equals(this.dragonUniqueId))
        {
            this.bossInfo.setPercent(0.0F);
            this.bossInfo.setVisible(false);
            this.generatePortal(true);
            this.spawnNewGateway();

            if (!this.previouslyKilled)
            {
                this.world.setBlockState(this.world.getHeight(WorldGenEndPodium.END_PODIUM_LOCATION), Blocks.DRAGON_EGG.getDefaultState());
            }

            this.previouslyKilled = true;
            this.dragonKilled = true;
        }
    }

    private void spawnNewGateway()
    {
        if (!this.gateways.isEmpty())
        {
            int i = ((Integer)this.gateways.remove(this.gateways.size() - 1)).intValue();
            int j = (int)(96.0D * Math.cos(2.0D * (-Math.PI + 0.15707963267948966D * (double)i)));
            int k = (int)(96.0D * Math.sin(2.0D * (-Math.PI + 0.15707963267948966D * (double)i)));
            this.generateGateway(new BlockPos(j, 75, k));
        }
    }

    private void generateGateway(BlockPos pos)
    {
        this.world.playEvent(3000, pos, 0);
        (new WorldGenEndGateway()).generate(this.world, new Random(), pos);
    }

    private void generatePortal(boolean active)
    {
        WorldGenEndPodium worldgenendpodium = new WorldGenEndPodium(active);

        if (this.exitPortalLocation == null)
        {
            for (this.exitPortalLocation = this.world.getTopSolidOrLiquidBlock(WorldGenEndPodium.END_PODIUM_LOCATION).down(); this.world.getBlockState(this.exitPortalLocation).getBlock() == Blocks.BEDROCK && this.exitPortalLocation.getY() > this.world.getSeaLevel(); this.exitPortalLocation = this.exitPortalLocation.down())
            {
                ;
            }
        }

        worldgenendpodium.generate(this.world, new Random(), this.exitPortalLocation);
    }

    private EntityDragon createNewDragon()
    {
        this.world.getChunkFromBlockCoords(new BlockPos(0, 128, 0));
        EntityDragon entitydragon = new EntityDragon(this.world);
        entitydragon.getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
        entitydragon.setLocationAndAngles(0.0D, 128.0D, 0.0D, this.world.rand.nextFloat() * 360.0F, 0.0F);
        this.world.spawnEntity(entitydragon);
        this.dragonUniqueId = entitydragon.getUniqueID();
        return entitydragon;
    }

    public void dragonUpdate(EntityDragon dragonIn)
    {
        if (dragonIn.getUniqueID().equals(this.dragonUniqueId))
        {
            this.bossInfo.setPercent(dragonIn.getHealth() / dragonIn.getMaxHealth());
            this.ticksSinceDragonSeen = 0;

            if (dragonIn.hasCustomName())
            {
                this.bossInfo.setName(dragonIn.getDisplayName());
            }
        }
    }

    public int getNumAliveCrystals()
    {
        return this.aliveCrystals;
    }

    public void onCrystalDestroyed(EntityEnderCrystal crystal, DamageSource dmgSrc)
    {
        if (this.respawnState != null && this.crystals.contains(crystal))
        {
            LOGGER.debug("Aborting respawn sequence");
            this.respawnState = null;
            this.respawnStateTicks = 0;
            this.resetSpikeCrystals();
            this.generatePortal(true);
        }
        else
        {
            this.findAliveCrystals();
            Entity entity = this.world.getEntityFromUuid(this.dragonUniqueId);

            if (entity instanceof EntityDragon)
            {
                ((EntityDragon)entity).onCrystalDestroyed(crystal, new BlockPos(crystal), dmgSrc);
            }
        }
    }

    public boolean hasPreviouslyKilledDragon()
    {
        return this.previouslyKilled;
    }

    public void respawnDragon()
    {
        if (this.dragonKilled && this.respawnState == null)
        {
            BlockPos blockpos = this.exitPortalLocation;

            if (blockpos == null)
            {
                LOGGER.debug("Tried to respawn, but need to find the portal first.");
                BlockPattern.PatternHelper blockpattern$patternhelper = this.findExitPortal();

                if (blockpattern$patternhelper == null)
                {
                    LOGGER.debug("Couldn't find a portal, so we made one.");
                    this.generatePortal(true);
                }
                else
                {
                    LOGGER.debug("Found the exit portal & temporarily using it.");
                }

                blockpos = this.exitPortalLocation;
            }

            List<EntityEnderCrystal> list1 = Lists.<EntityEnderCrystal>newArrayList();
            BlockPos blockpos1 = blockpos.up(1);

            for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
            {
                List<EntityEnderCrystal> list = this.world.getEntitiesWithinAABB(EntityEnderCrystal.class, new AxisAlignedBB(blockpos1.offset(enumfacing, 2)));

                if (list.isEmpty())
                {
                    return;
                }

                list1.addAll(list);
            }

            LOGGER.debug("Found all crystals, respawning dragon.");
            this.respawnDragon(list1);
        }
    }

    private void respawnDragon(List<EntityEnderCrystal> crystalsIn)
    {
        if (this.dragonKilled && this.respawnState == null)
        {
            for (BlockPattern.PatternHelper blockpattern$patternhelper = this.findExitPortal(); blockpattern$patternhelper != null; blockpattern$patternhelper = this.findExitPortal())
            {
                for (int i = 0; i < this.portalPattern.getPalmLength(); ++i)
                {
                    for (int j = 0; j < this.portalPattern.getThumbLength(); ++j)
                    {
                        for (int k = 0; k < this.portalPattern.getFingerLength(); ++k)
                        {
                            BlockWorldState blockworldstate = blockpattern$patternhelper.translateOffset(i, j, k);

                            if (blockworldstate.getBlockState().getBlock() == Blocks.BEDROCK || blockworldstate.getBlockState().getBlock() == Blocks.END_PORTAL)
                            {
                                this.world.setBlockState(blockworldstate.getPos(), Blocks.END_STONE.getDefaultState());
                            }
                        }
                    }
                }
            }

            this.respawnState = DragonSpawnManager.START;
            this.respawnStateTicks = 0;
            this.generatePortal(false);
            this.crystals = crystalsIn;
        }
    }

    public void resetSpikeCrystals()
    {
        for (WorldGenSpikes.EndSpike worldgenspikes$endspike : BiomeEndDecorator.getSpikesForWorld(this.world))
        {
            for (EntityEnderCrystal entityendercrystal : this.world.getEntitiesWithinAABB(EntityEnderCrystal.class, worldgenspikes$endspike.getTopBoundingBox()))
            {
                entityendercrystal.setEntityInvulnerable(false);
                entityendercrystal.setBeamTarget((BlockPos)null);
            }
        }
    }
}
