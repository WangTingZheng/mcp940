package net.minecraft.entity.player;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketClientSettings;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.network.play.server.SPacketCamera;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketCloseWindow;
import net.minecraft.network.play.server.SPacketCombatEvent;
import net.minecraft.network.play.server.SPacketCustomPayload;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketEffect;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketOpenWindow;
import net.minecraft.network.play.server.SPacketPlayerAbilities;
import net.minecraft.network.play.server.SPacketRemoveEntityEffect;
import net.minecraft.network.play.server.SPacketResourcePackSend;
import net.minecraft.network.play.server.SPacketSetExperience;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.network.play.server.SPacketSignEditorOpen;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketUpdateHealth;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.network.play.server.SPacketUseBed;
import net.minecraft.network.play.server.SPacketWindowItems;
import net.minecraft.network.play.server.SPacketWindowProperty;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.IScoreCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraft.stats.RecipeBookServer;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsManagerServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.CooldownTrackerServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.GameType;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.ILootContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityPlayerMP extends EntityPlayer implements IContainerListener
{
    private static final Logger LOGGER = LogManager.getLogger();
    private String language = "en_US";

    /**
     * The NetServerHandler assigned to this player by the ServerConfigurationManager.
     */
    public NetHandlerPlayServer connection;

    /** Reference to the MinecraftServer object. */
    public final MinecraftServer mcServer;

    /** The player interaction manager for this player */
    public final PlayerInteractionManager interactionManager;

    /** player X position as seen by PlayerManager */
    public double managedPosX;

    /** player Z position as seen by PlayerManager */
    public double managedPosZ;
    private final List<Integer> entityRemoveQueue = Lists.<Integer>newLinkedList();
    private final PlayerAdvancements advancements;
    private final StatisticsManagerServer statsFile;

    /**
     * the total health of the player, includes actual health and absorption health. Updated every tick.
     */
    private float lastHealthScore = Float.MIN_VALUE;
    private int lastFoodScore = Integer.MIN_VALUE;
    private int lastAirScore = Integer.MIN_VALUE;
    private int lastArmorScore = Integer.MIN_VALUE;
    private int lastLevelScore = Integer.MIN_VALUE;
    private int lastExperienceScore = Integer.MIN_VALUE;

    /** amount of health the client was last set to */
    private float lastHealth = -1.0E8F;

    /** set to foodStats.GetFoodLevel */
    private int lastFoodLevel = -99999999;

    /** set to foodStats.getSaturationLevel() == 0.0F each tick */
    private boolean wasHungry = true;

    /** Amount of experience the client was last set to */
    private int lastExperience = -99999999;
    private int respawnInvulnerabilityTicks = 60;
    private EntityPlayer.EnumChatVisibility chatVisibility;
    private boolean chatColours = true;
    private long playerLastActiveTime = System.currentTimeMillis();

    /** The entity the player is currently spectating through. */
    private Entity spectatingEntity;
    private boolean invulnerableDimensionChange;
    private boolean seenCredits;
    private final RecipeBookServer recipeBook = new RecipeBookServer();

    /** The position this player started levitating at. */
    private Vec3d levitationStartPos;

    /** The value of ticksExisted when this player started levitating. */
    private int levitatingSince;
    private boolean disconnected;
    private Vec3d enteredNetherPosition;

    /**
     * The currently in use window ID. Incremented every time a window is opened.
     */
    private int currentWindowId;

    /**
     * set to true when player is moving quantity of items from one inventory to another(crafting) but item in either
     * slot is not changed
     */
    public boolean isChangingQuantityOnly;
    public int ping;

    /**
     * True when the player has left the End using an the exit portal, but has not yet been respawned in the overworld
     */
    public boolean queuedEndExit;

    public EntityPlayerMP(MinecraftServer server, WorldServer worldIn, GameProfile profile, PlayerInteractionManager interactionManagerIn)
    {
        super(worldIn, profile);
        interactionManagerIn.player = this;
        this.interactionManager = interactionManagerIn;
        BlockPos blockpos = worldIn.getSpawnPoint();

        if (worldIn.provider.hasSkyLight() && worldIn.getWorldInfo().getGameType() != GameType.ADVENTURE)
        {
            int i = Math.max(0, server.getSpawnRadius(worldIn));
            int j = MathHelper.floor(worldIn.getWorldBorder().getClosestDistance((double)blockpos.getX(), (double)blockpos.getZ()));

            if (j < i)
            {
                i = j;
            }

            if (j <= 1)
            {
                i = 1;
            }

            blockpos = worldIn.getTopSolidOrLiquidBlock(blockpos.add(this.rand.nextInt(i * 2 + 1) - i, 0, this.rand.nextInt(i * 2 + 1) - i));
        }

        this.mcServer = server;
        this.statsFile = server.getPlayerList().getPlayerStatsFile(this);
        this.advancements = server.getPlayerList().getPlayerAdvancements(this);
        this.stepHeight = 1.0F;
        this.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);

        while (!worldIn.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty() && this.posY < 255.0D)
        {
            this.setPosition(this.posX, this.posY + 1.0D, this.posZ);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        if (compound.hasKey("playerGameType", 99))
        {
            if (this.getServer().getForceGamemode())
            {
                this.interactionManager.setGameType(this.getServer().getGameType());
            }
            else
            {
                this.interactionManager.setGameType(GameType.getByID(compound.getInteger("playerGameType")));
            }
        }

        if (compound.hasKey("enteredNetherPosition", 10))
        {
            NBTTagCompound nbttagcompound = compound.getCompoundTag("enteredNetherPosition");
            this.enteredNetherPosition = new Vec3d(nbttagcompound.getDouble("x"), nbttagcompound.getDouble("y"), nbttagcompound.getDouble("z"));
        }

        this.seenCredits = compound.getBoolean("seenCredits");

        if (compound.hasKey("recipeBook", 10))
        {
            this.recipeBook.read(compound.getCompoundTag("recipeBook"));
        }
    }

    public static void registerFixesPlayerMP(DataFixer p_191522_0_)
    {
        p_191522_0_.registerWalker(FixTypes.PLAYER, new IDataWalker()
        {
            public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn)
            {
                if (compound.hasKey("RootVehicle", 10))
                {
                    NBTTagCompound nbttagcompound = compound.getCompoundTag("RootVehicle");

                    if (nbttagcompound.hasKey("Entity", 10))
                    {
                        nbttagcompound.setTag("Entity", fixer.process(FixTypes.ENTITY, nbttagcompound.getCompoundTag("Entity"), versionIn));
                    }
                }

                return compound;
            }
        });
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("playerGameType", this.interactionManager.getGameType().getID());
        compound.setBoolean("seenCredits", this.seenCredits);

        if (this.enteredNetherPosition != null)
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setDouble("x", this.enteredNetherPosition.x);
            nbttagcompound.setDouble("y", this.enteredNetherPosition.y);
            nbttagcompound.setDouble("z", this.enteredNetherPosition.z);
            compound.setTag("enteredNetherPosition", nbttagcompound);
        }

        Entity entity1 = this.getLowestRidingEntity();
        Entity entity = this.getRidingEntity();

        if (entity != null && entity1 != this && entity1.getRecursivePassengersByType(EntityPlayerMP.class).size() == 1)
        {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            NBTTagCompound nbttagcompound2 = new NBTTagCompound();
            entity1.writeToNBTOptional(nbttagcompound2);
            nbttagcompound1.setUniqueId("Attach", entity.getUniqueID());
            nbttagcompound1.setTag("Entity", nbttagcompound2);
            compound.setTag("RootVehicle", nbttagcompound1);
        }

        compound.setTag("recipeBook", this.recipeBook.write());
    }

    /**
     * Add experience levels to this player.
     */
    public void addExperienceLevel(int levels)
    {
        super.addExperienceLevel(levels);
        this.lastExperience = -1;
    }

    public void onEnchant(ItemStack enchantedItem, int cost)
    {
        super.onEnchant(enchantedItem, cost);
        this.lastExperience = -1;
    }

    public void addSelfToInternalCraftingInventory()
    {
        this.openContainer.addListener(this);
    }

    /**
     * Sends an ENTER_COMBAT packet to the client
     */
    public void sendEnterCombat()
    {
        super.sendEnterCombat();
        this.connection.sendPacket(new SPacketCombatEvent(this.getCombatTracker(), SPacketCombatEvent.Event.ENTER_COMBAT));
    }

    /**
     * Sends an END_COMBAT packet to the client
     */
    public void sendEndCombat()
    {
        super.sendEndCombat();
        this.connection.sendPacket(new SPacketCombatEvent(this.getCombatTracker(), SPacketCombatEvent.Event.END_COMBAT));
    }

    protected void onInsideBlock(IBlockState p_191955_1_)
    {
        CriteriaTriggers.ENTER_BLOCK.trigger(this, p_191955_1_);
    }

    protected CooldownTracker createCooldownTracker()
    {
        return new CooldownTrackerServer(this);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.interactionManager.updateBlockRemoving();
        --this.respawnInvulnerabilityTicks;

        if (this.hurtResistantTime > 0)
        {
            --this.hurtResistantTime;
        }

        this.openContainer.detectAndSendChanges();

        if (!this.world.isRemote && !this.openContainer.canInteractWith(this))
        {
            this.closeScreen();
            this.openContainer = this.inventoryContainer;
        }

        while (!this.entityRemoveQueue.isEmpty())
        {
            int i = Math.min(this.entityRemoveQueue.size(), Integer.MAX_VALUE);
            int[] aint = new int[i];
            Iterator<Integer> iterator = this.entityRemoveQueue.iterator();
            int j = 0;

            while (iterator.hasNext() && j < i)
            {
                aint[j++] = ((Integer)iterator.next()).intValue();
                iterator.remove();
            }

            this.connection.sendPacket(new SPacketDestroyEntities(aint));
        }

        Entity entity = this.getSpectatingEntity();

        if (entity != this)
        {
            if (entity.isEntityAlive())
            {
                this.setPositionAndRotation(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
                this.mcServer.getPlayerList().serverUpdateMovingPlayer(this);

                if (this.isSneaking())
                {
                    this.setSpectatingEntity(this);
                }
            }
            else
            {
                this.setSpectatingEntity(this);
            }
        }

        CriteriaTriggers.TICK.trigger(this);

        if (this.levitationStartPos != null)
        {
            CriteriaTriggers.LEVITATION.trigger(this, this.levitationStartPos, this.ticksExisted - this.levitatingSince);
        }

        this.advancements.flushDirty(this);
    }

    public void onUpdateEntity()
    {
        try
        {
            super.onUpdate();

            for (int i = 0; i < this.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemstack = this.inventory.getStackInSlot(i);

                if (!itemstack.isEmpty() && itemstack.getItem().isMap())
                {
                    Packet<?> packet = ((ItemMapBase)itemstack.getItem()).createMapDataPacket(itemstack, this.world, this);

                    if (packet != null)
                    {
                        this.connection.sendPacket(packet);
                    }
                }
            }

            if (this.getHealth() != this.lastHealth || this.lastFoodLevel != this.foodStats.getFoodLevel() || this.foodStats.getSaturationLevel() == 0.0F != this.wasHungry)
            {
                this.connection.sendPacket(new SPacketUpdateHealth(this.getHealth(), this.foodStats.getFoodLevel(), this.foodStats.getSaturationLevel()));
                this.lastHealth = this.getHealth();
                this.lastFoodLevel = this.foodStats.getFoodLevel();
                this.wasHungry = this.foodStats.getSaturationLevel() == 0.0F;
            }

            if (this.getHealth() + this.getAbsorptionAmount() != this.lastHealthScore)
            {
                this.lastHealthScore = this.getHealth() + this.getAbsorptionAmount();
                this.updateScorePoints(IScoreCriteria.HEALTH, MathHelper.ceil(this.lastHealthScore));
            }

            if (this.foodStats.getFoodLevel() != this.lastFoodScore)
            {
                this.lastFoodScore = this.foodStats.getFoodLevel();
                this.updateScorePoints(IScoreCriteria.FOOD, MathHelper.ceil((float)this.lastFoodScore));
            }

            if (this.getAir() != this.lastAirScore)
            {
                this.lastAirScore = this.getAir();
                this.updateScorePoints(IScoreCriteria.AIR, MathHelper.ceil((float)this.lastAirScore));
            }

            if (this.getTotalArmorValue() != this.lastArmorScore)
            {
                this.lastArmorScore = this.getTotalArmorValue();
                this.updateScorePoints(IScoreCriteria.ARMOR, MathHelper.ceil((float)this.lastArmorScore));
            }

            if (this.experienceTotal != this.lastExperienceScore)
            {
                this.lastExperienceScore = this.experienceTotal;
                this.updateScorePoints(IScoreCriteria.XP, MathHelper.ceil((float)this.lastExperienceScore));
            }

            if (this.experienceLevel != this.lastLevelScore)
            {
                this.lastLevelScore = this.experienceLevel;
                this.updateScorePoints(IScoreCriteria.LEVEL, MathHelper.ceil((float)this.lastLevelScore));
            }

            if (this.experienceTotal != this.lastExperience)
            {
                this.lastExperience = this.experienceTotal;
                this.connection.sendPacket(new SPacketSetExperience(this.experience, this.experienceTotal, this.experienceLevel));
            }

            if (this.ticksExisted % 20 == 0)
            {
                CriteriaTriggers.LOCATION.trigger(this);
            }
        }
        catch (Throwable throwable)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Ticking player");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Player being ticked");
            this.addEntityCrashInfo(crashreportcategory);
            throw new ReportedException(crashreport);
        }
    }

    private void updateScorePoints(IScoreCriteria criteria, int points)
    {
        for (ScoreObjective scoreobjective : this.getWorldScoreboard().getObjectivesFromCriteria(criteria))
        {
            Score score = this.getWorldScoreboard().getOrCreateScore(this.getName(), scoreobjective);
            score.setScorePoints(points);
        }
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause)
    {
        boolean flag = this.world.getGameRules().getBoolean("showDeathMessages");
        this.connection.sendPacket(new SPacketCombatEvent(this.getCombatTracker(), SPacketCombatEvent.Event.ENTITY_DIED, flag));

        if (flag)
        {
            Team team = this.getTeam();

            if (team != null && team.getDeathMessageVisibility() != Team.EnumVisible.ALWAYS)
            {
                if (team.getDeathMessageVisibility() == Team.EnumVisible.HIDE_FOR_OTHER_TEAMS)
                {
                    this.mcServer.getPlayerList().sendMessageToAllTeamMembers(this, this.getCombatTracker().getDeathMessage());
                }
                else if (team.getDeathMessageVisibility() == Team.EnumVisible.HIDE_FOR_OWN_TEAM)
                {
                    this.mcServer.getPlayerList().sendMessageToTeamOrAllPlayers(this, this.getCombatTracker().getDeathMessage());
                }
            }
            else
            {
                this.mcServer.getPlayerList().sendMessage(this.getCombatTracker().getDeathMessage());
            }
        }

        this.spawnShoulderEntities();

        if (!this.world.getGameRules().getBoolean("keepInventory") && !this.isSpectator())
        {
            this.destroyVanishingCursedItems();
            this.inventory.dropAllItems();
        }

        for (ScoreObjective scoreobjective : this.world.getScoreboard().getObjectivesFromCriteria(IScoreCriteria.DEATH_COUNT))
        {
            Score score = this.getWorldScoreboard().getOrCreateScore(this.getName(), scoreobjective);
            score.incrementScore();
        }

        EntityLivingBase entitylivingbase = this.getAttackingEntity();

        if (entitylivingbase != null)
        {
            EntityList.EntityEggInfo entitylist$entityegginfo = EntityList.ENTITY_EGGS.get(EntityList.getKey(entitylivingbase));

            if (entitylist$entityegginfo != null)
            {
                this.addStat(entitylist$entityegginfo.entityKilledByStat);
            }

            entitylivingbase.awardKillScore(this, this.scoreValue, cause);
        }

        this.addStat(StatList.DEATHS);
        this.takeStat(StatList.TIME_SINCE_DEATH);
        this.extinguish();
        this.setFlag(0, false);
        this.getCombatTracker().reset();
    }

    public void awardKillScore(Entity p_191956_1_, int p_191956_2_, DamageSource p_191956_3_)
    {
        if (p_191956_1_ != this)
        {
            super.awardKillScore(p_191956_1_, p_191956_2_, p_191956_3_);
            this.addScore(p_191956_2_);
            Collection<ScoreObjective> collection = this.getWorldScoreboard().getObjectivesFromCriteria(IScoreCriteria.TOTAL_KILL_COUNT);

            if (p_191956_1_ instanceof EntityPlayer)
            {
                this.addStat(StatList.PLAYER_KILLS);
                collection.addAll(this.getWorldScoreboard().getObjectivesFromCriteria(IScoreCriteria.PLAYER_KILL_COUNT));
            }
            else
            {
                this.addStat(StatList.MOB_KILLS);
            }

            collection.addAll(this.awardTeamKillScores(p_191956_1_));

            for (ScoreObjective scoreobjective : collection)
            {
                this.getWorldScoreboard().getOrCreateScore(this.getName(), scoreobjective).incrementScore();
            }

            CriteriaTriggers.PLAYER_KILLED_ENTITY.trigger(this, p_191956_1_, p_191956_3_);
        }
    }

    private Collection<ScoreObjective> awardTeamKillScores(Entity p_192038_1_)
    {
        String s = p_192038_1_ instanceof EntityPlayer ? p_192038_1_.getName() : p_192038_1_.getCachedUniqueIdString();
        ScorePlayerTeam scoreplayerteam = this.getWorldScoreboard().getPlayersTeam(this.getName());

        if (scoreplayerteam != null)
        {
            int i = scoreplayerteam.getColor().getColorIndex();

            if (i >= 0 && i < IScoreCriteria.KILLED_BY_TEAM.length)
            {
                for (ScoreObjective scoreobjective : this.getWorldScoreboard().getObjectivesFromCriteria(IScoreCriteria.KILLED_BY_TEAM[i]))
                {
                    Score score = this.getWorldScoreboard().getOrCreateScore(s, scoreobjective);
                    score.incrementScore();
                }
            }
        }

        ScorePlayerTeam scoreplayerteam1 = this.getWorldScoreboard().getPlayersTeam(s);

        if (scoreplayerteam1 != null)
        {
            int j = scoreplayerteam1.getColor().getColorIndex();

            if (j >= 0 && j < IScoreCriteria.TEAM_KILL.length)
            {
                return this.getWorldScoreboard().getObjectivesFromCriteria(IScoreCriteria.TEAM_KILL[j]);
            }
        }

        return Lists.<ScoreObjective>newArrayList();
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else
        {
            boolean flag = this.mcServer.isDedicatedServer() && this.canPlayersAttack() && "fall".equals(source.damageType);

            if (!flag && this.respawnInvulnerabilityTicks > 0 && source != DamageSource.OUT_OF_WORLD)
            {
                return false;
            }
            else
            {
                if (source instanceof EntityDamageSource)
                {
                    Entity entity = source.getTrueSource();

                    if (entity instanceof EntityPlayer && !this.canAttackPlayer((EntityPlayer)entity))
                    {
                        return false;
                    }

                    if (entity instanceof EntityArrow)
                    {
                        EntityArrow entityarrow = (EntityArrow)entity;

                        if (entityarrow.shootingEntity instanceof EntityPlayer && !this.canAttackPlayer((EntityPlayer)entityarrow.shootingEntity))
                        {
                            return false;
                        }
                    }
                }

                return super.attackEntityFrom(source, amount);
            }
        }
    }

    public boolean canAttackPlayer(EntityPlayer other)
    {
        return !this.canPlayersAttack() ? false : super.canAttackPlayer(other);
    }

    /**
     * Returns if other players can attack this player
     */
    private boolean canPlayersAttack()
    {
        return this.mcServer.isPVPEnabled();
    }

    @Nullable
    public Entity changeDimension(int dimensionIn)
    {
        this.invulnerableDimensionChange = true;

        if (this.dimension == 0 && dimensionIn == -1)
        {
            this.enteredNetherPosition = new Vec3d(this.posX, this.posY, this.posZ);
        }
        else if (this.dimension != -1 && dimensionIn != 0)
        {
            this.enteredNetherPosition = null;
        }

        if (this.dimension == 1 && dimensionIn == 1)
        {
            this.world.removeEntity(this);

            if (!this.queuedEndExit)
            {
                this.queuedEndExit = true;
                this.connection.sendPacket(new SPacketChangeGameState(4, this.seenCredits ? 0.0F : 1.0F));
                this.seenCredits = true;
            }

            return this;
        }
        else
        {
            if (this.dimension == 0 && dimensionIn == 1)
            {
                dimensionIn = 1;
            }

            this.mcServer.getPlayerList().changePlayerDimension(this, dimensionIn);
            this.connection.sendPacket(new SPacketEffect(1032, BlockPos.ORIGIN, 0, false));
            this.lastExperience = -1;
            this.lastHealth = -1.0F;
            this.lastFoodLevel = -1;
            return this;
        }
    }

    public boolean isSpectatedByPlayer(EntityPlayerMP player)
    {
        if (player.isSpectator())
        {
            return this.getSpectatingEntity() == this;
        }
        else
        {
            return this.isSpectator() ? false : super.isSpectatedByPlayer(player);
        }
    }

    private void sendTileEntityUpdate(TileEntity p_147097_1_)
    {
        if (p_147097_1_ != null)
        {
            SPacketUpdateTileEntity spacketupdatetileentity = p_147097_1_.getUpdatePacket();

            if (spacketupdatetileentity != null)
            {
                this.connection.sendPacket(spacketupdatetileentity);
            }
        }
    }

    /**
     * Called when the entity picks up an item.
     */
    public void onItemPickup(Entity entityIn, int quantity)
    {
        super.onItemPickup(entityIn, quantity);
        this.openContainer.detectAndSendChanges();
    }

    public EntityPlayer.SleepResult trySleep(BlockPos bedLocation)
    {
        EntityPlayer.SleepResult entityplayer$sleepresult = super.trySleep(bedLocation);

        if (entityplayer$sleepresult == EntityPlayer.SleepResult.OK)
        {
            this.addStat(StatList.SLEEP_IN_BED);
            Packet<?> packet = new SPacketUseBed(this, bedLocation);
            this.getServerWorld().getEntityTracker().sendToTracking(this, packet);
            this.connection.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.connection.sendPacket(packet);
            CriteriaTriggers.SLEPT_IN_BED.trigger(this);
        }

        return entityplayer$sleepresult;
    }

    /**
     * Wake up the player if they're sleeping.
     */
    public void wakeUpPlayer(boolean immediately, boolean updateWorldFlag, boolean setSpawn)
    {
        if (this.isPlayerSleeping())
        {
            this.getServerWorld().getEntityTracker().sendToTrackingAndSelf(this, new SPacketAnimation(this, 2));
        }

        super.wakeUpPlayer(immediately, updateWorldFlag, setSpawn);

        if (this.connection != null)
        {
            this.connection.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        }
    }

    public boolean startRiding(Entity entityIn, boolean force)
    {
        Entity entity = this.getRidingEntity();

        if (!super.startRiding(entityIn, force))
        {
            return false;
        }
        else
        {
            Entity entity1 = this.getRidingEntity();

            if (entity1 != entity && this.connection != null)
            {
                this.connection.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            }

            return true;
        }
    }

    /**
     * Dismounts this entity from the entity it is riding.
     */
    public void dismountRidingEntity()
    {
        Entity entity = this.getRidingEntity();
        super.dismountRidingEntity();
        Entity entity1 = this.getRidingEntity();

        if (entity1 != entity && this.connection != null)
        {
            this.connection.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        }
    }

    /**
     * Returns whether this Entity is invulnerable to the given DamageSource.
     */
    public boolean isEntityInvulnerable(DamageSource source)
    {
        return super.isEntityInvulnerable(source) || this.isInvulnerableDimensionChange();
    }

    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos)
    {
    }

    protected void frostWalk(BlockPos pos)
    {
        if (!this.isSpectator())
        {
            super.frostWalk(pos);
        }
    }

    /**
     * process player falling based on movement packet
     */
    public void handleFalling(double y, boolean onGroundIn)
    {
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.posY - 0.20000000298023224D);
        int k = MathHelper.floor(this.posZ);
        BlockPos blockpos = new BlockPos(i, j, k);
        IBlockState iblockstate = this.world.getBlockState(blockpos);

        if (iblockstate.getMaterial() == Material.AIR)
        {
            BlockPos blockpos1 = blockpos.down();
            IBlockState iblockstate1 = this.world.getBlockState(blockpos1);
            Block block = iblockstate1.getBlock();

            if (block instanceof BlockFence || block instanceof BlockWall || block instanceof BlockFenceGate)
            {
                blockpos = blockpos1;
                iblockstate = iblockstate1;
            }
        }

        super.updateFallState(y, onGroundIn, iblockstate, blockpos);
    }

    public void openEditSign(TileEntitySign signTile)
    {
        signTile.setPlayer(this);
        this.connection.sendPacket(new SPacketSignEditorOpen(signTile.getPos()));
    }

    /**
     * get the next window id to use
     */
    private void getNextWindowId()
    {
        this.currentWindowId = this.currentWindowId % 100 + 1;
    }

    public void displayGui(IInteractionObject guiOwner)
    {
        if (guiOwner instanceof ILootContainer && ((ILootContainer)guiOwner).getLootTable() != null && this.isSpectator())
        {
            this.sendStatusMessage((new TextComponentTranslation("container.spectatorCantOpen", new Object[0])).setStyle((new Style()).setColor(TextFormatting.RED)), true);
        }
        else
        {
            this.getNextWindowId();
            this.connection.sendPacket(new SPacketOpenWindow(this.currentWindowId, guiOwner.getGuiID(), guiOwner.getDisplayName()));
            this.openContainer = guiOwner.createContainer(this.inventory, this);
            this.openContainer.windowId = this.currentWindowId;
            this.openContainer.addListener(this);
        }
    }

    /**
     * Displays the GUI for interacting with a chest inventory.
     */
    public void displayGUIChest(IInventory chestInventory)
    {
        if (chestInventory instanceof ILootContainer && ((ILootContainer)chestInventory).getLootTable() != null && this.isSpectator())
        {
            this.sendStatusMessage((new TextComponentTranslation("container.spectatorCantOpen", new Object[0])).setStyle((new Style()).setColor(TextFormatting.RED)), true);
        }
        else
        {
            if (this.openContainer != this.inventoryContainer)
            {
                this.closeScreen();
            }

            if (chestInventory instanceof ILockableContainer)
            {
                ILockableContainer ilockablecontainer = (ILockableContainer)chestInventory;

                if (ilockablecontainer.isLocked() && !this.canOpen(ilockablecontainer.getLockCode()) && !this.isSpectator())
                {
                    this.connection.sendPacket(new SPacketChat(new TextComponentTranslation("container.isLocked", new Object[] {chestInventory.getDisplayName()}), ChatType.GAME_INFO));
                    this.connection.sendPacket(new SPacketSoundEffect(SoundEvents.BLOCK_CHEST_LOCKED, SoundCategory.BLOCKS, this.posX, this.posY, this.posZ, 1.0F, 1.0F));
                    return;
                }
            }

            this.getNextWindowId();

            if (chestInventory instanceof IInteractionObject)
            {
                this.connection.sendPacket(new SPacketOpenWindow(this.currentWindowId, ((IInteractionObject)chestInventory).getGuiID(), chestInventory.getDisplayName(), chestInventory.getSizeInventory()));
                this.openContainer = ((IInteractionObject)chestInventory).createContainer(this.inventory, this);
            }
            else
            {
                this.connection.sendPacket(new SPacketOpenWindow(this.currentWindowId, "minecraft:container", chestInventory.getDisplayName(), chestInventory.getSizeInventory()));
                this.openContainer = new ContainerChest(this.inventory, chestInventory, this);
            }

            this.openContainer.windowId = this.currentWindowId;
            this.openContainer.addListener(this);
        }
    }

    public void displayVillagerTradeGui(IMerchant villager)
    {
        this.getNextWindowId();
        this.openContainer = new ContainerMerchant(this.inventory, villager, this.world);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addListener(this);
        IInventory iinventory = ((ContainerMerchant)this.openContainer).getMerchantInventory();
        ITextComponent itextcomponent = villager.getDisplayName();
        this.connection.sendPacket(new SPacketOpenWindow(this.currentWindowId, "minecraft:villager", itextcomponent, iinventory.getSizeInventory()));
        MerchantRecipeList merchantrecipelist = villager.getRecipes(this);

        if (merchantrecipelist != null)
        {
            PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
            packetbuffer.writeInt(this.currentWindowId);
            merchantrecipelist.writeToBuf(packetbuffer);
            this.connection.sendPacket(new SPacketCustomPayload("MC|TrList", packetbuffer));
        }
    }

    public void openGuiHorseInventory(AbstractHorse horse, IInventory inventoryIn)
    {
        if (this.openContainer != this.inventoryContainer)
        {
            this.closeScreen();
        }

        this.getNextWindowId();
        this.connection.sendPacket(new SPacketOpenWindow(this.currentWindowId, "EntityHorse", inventoryIn.getDisplayName(), inventoryIn.getSizeInventory(), horse.getEntityId()));
        this.openContainer = new ContainerHorseInventory(this.inventory, inventoryIn, horse, this);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addListener(this);
    }

    public void openBook(ItemStack stack, EnumHand hand)
    {
        Item item = stack.getItem();

        if (item == Items.WRITTEN_BOOK)
        {
            PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
            packetbuffer.writeEnumValue(hand);
            this.connection.sendPacket(new SPacketCustomPayload("MC|BOpen", packetbuffer));
        }
    }

    public void displayGuiCommandBlock(TileEntityCommandBlock commandBlock)
    {
        commandBlock.setSendToClient(true);
        this.sendTileEntityUpdate(commandBlock);
    }

    /**
     * Sends the contents of an inventory slot to the client-side Container. This doesn't have to match the actual
     * contents of that slot.
     */
    public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack)
    {
        if (!(containerToSend.getSlot(slotInd) instanceof SlotCrafting))
        {
            if (containerToSend == this.inventoryContainer)
            {
                CriteriaTriggers.INVENTORY_CHANGED.trigger(this, this.inventory);
            }

            if (!this.isChangingQuantityOnly)
            {
                this.connection.sendPacket(new SPacketSetSlot(containerToSend.windowId, slotInd, stack));
            }
        }
    }

    public void sendContainerToPlayer(Container containerIn)
    {
        this.sendAllContents(containerIn, containerIn.getInventory());
    }

    /**
     * update the crafting window inventory with the items in the list
     */
    public void sendAllContents(Container containerToSend, NonNullList<ItemStack> itemsList)
    {
        this.connection.sendPacket(new SPacketWindowItems(containerToSend.windowId, itemsList));
        this.connection.sendPacket(new SPacketSetSlot(-1, -1, this.inventory.getItemStack()));
    }

    /**
     * Sends two ints to the client-side Container. Used for furnace burning time, smelting progress, brewing progress,
     * and enchanting level. Normally the first int identifies which variable to update, and the second contains the new
     * value. Both are truncated to shorts in non-local SMP.
     */
    public void sendWindowProperty(Container containerIn, int varToUpdate, int newValue)
    {
        this.connection.sendPacket(new SPacketWindowProperty(containerIn.windowId, varToUpdate, newValue));
    }

    public void sendAllWindowProperties(Container containerIn, IInventory inventory)
    {
        for (int i = 0; i < inventory.getFieldCount(); ++i)
        {
            this.connection.sendPacket(new SPacketWindowProperty(containerIn.windowId, i, inventory.getField(i)));
        }
    }

    /**
     * set current crafting inventory back to the 2x2 square
     */
    public void closeScreen()
    {
        this.connection.sendPacket(new SPacketCloseWindow(this.openContainer.windowId));
        this.closeContainer();
    }

    /**
     * updates item held by mouse
     */
    public void updateHeldItem()
    {
        if (!this.isChangingQuantityOnly)
        {
            this.connection.sendPacket(new SPacketSetSlot(-1, -1, this.inventory.getItemStack()));
        }
    }

    /**
     * Closes the container the player currently has open.
     */
    public void closeContainer()
    {
        this.openContainer.onContainerClosed(this);
        this.openContainer = this.inventoryContainer;
    }

    public void setEntityActionState(float strafe, float forward, boolean jumping, boolean sneaking)
    {
        if (this.isRiding())
        {
            if (strafe >= -1.0F && strafe <= 1.0F)
            {
                this.moveStrafing = strafe;
            }

            if (forward >= -1.0F && forward <= 1.0F)
            {
                this.moveForward = forward;
            }

            this.isJumping = jumping;
            this.setSneaking(sneaking);
        }
    }

    /**
     * Adds a value to a statistic field.
     */
    public void addStat(StatBase stat, int amount)
    {
        if (stat != null)
        {
            this.statsFile.increaseStat(this, stat, amount);

            for (ScoreObjective scoreobjective : this.getWorldScoreboard().getObjectivesFromCriteria(stat.getCriteria()))
            {
                this.getWorldScoreboard().getOrCreateScore(this.getName(), scoreobjective).increaseScore(amount);
            }
        }
    }

    public void takeStat(StatBase stat)
    {
        if (stat != null)
        {
            this.statsFile.unlockAchievement(this, stat, 0);

            for (ScoreObjective scoreobjective : this.getWorldScoreboard().getObjectivesFromCriteria(stat.getCriteria()))
            {
                this.getWorldScoreboard().getOrCreateScore(this.getName(), scoreobjective).setScorePoints(0);
            }
        }
    }

    public void unlockRecipes(List<IRecipe> p_192021_1_)
    {
        this.recipeBook.add(p_192021_1_, this);
    }

    public void unlockRecipes(ResourceLocation[] p_193102_1_)
    {
        List<IRecipe> list = Lists.<IRecipe>newArrayList();

        for (ResourceLocation resourcelocation : p_193102_1_)
        {
            list.add(CraftingManager.getRecipe(resourcelocation));
        }

        this.unlockRecipes(list);
    }

    public void resetRecipes(List<IRecipe> p_192022_1_)
    {
        this.recipeBook.remove(p_192022_1_, this);
    }

    public void mountEntityAndWakeUp()
    {
        this.disconnected = true;
        this.removePassengers();

        if (this.sleeping)
        {
            this.wakeUpPlayer(true, false, false);
        }
    }

    public boolean hasDisconnected()
    {
        return this.disconnected;
    }

    /**
     * this function is called when a players inventory is sent to him, lastHealth is updated on any dimension
     * transitions, then reset.
     */
    public void setPlayerHealthUpdated()
    {
        this.lastHealth = -1.0E8F;
    }

    public void sendStatusMessage(ITextComponent chatComponent, boolean actionBar)
    {
        this.connection.sendPacket(new SPacketChat(chatComponent, actionBar ? ChatType.GAME_INFO : ChatType.CHAT));
    }

    /**
     * Used for when item use count runs out, ie: eating completed
     */
    protected void onItemUseFinish()
    {
        if (!this.activeItemStack.isEmpty() && this.isHandActive())
        {
            this.connection.sendPacket(new SPacketEntityStatus(this, (byte)9));
            super.onItemUseFinish();
        }
    }

    public void copyFrom(EntityPlayerMP that, boolean keepEverything)
    {
        if (keepEverything)
        {
            this.inventory.copyInventory(that.inventory);
            this.setHealth(that.getHealth());
            this.foodStats = that.foodStats;
            this.experienceLevel = that.experienceLevel;
            this.experienceTotal = that.experienceTotal;
            this.experience = that.experience;
            this.setScore(that.getScore());
            this.lastPortalPos = that.lastPortalPos;
            this.lastPortalVec = that.lastPortalVec;
            this.teleportDirection = that.teleportDirection;
        }
        else if (this.world.getGameRules().getBoolean("keepInventory") || that.isSpectator())
        {
            this.inventory.copyInventory(that.inventory);
            this.experienceLevel = that.experienceLevel;
            this.experienceTotal = that.experienceTotal;
            this.experience = that.experience;
            this.setScore(that.getScore());
        }

        this.xpSeed = that.xpSeed;
        this.enderChest = that.enderChest;
        this.getDataManager().set(PLAYER_MODEL_FLAG, that.getDataManager().get(PLAYER_MODEL_FLAG));
        this.lastExperience = -1;
        this.lastHealth = -1.0F;
        this.lastFoodLevel = -1;
        this.recipeBook.apply(that.recipeBook);
        this.entityRemoveQueue.addAll(that.entityRemoveQueue);
        this.seenCredits = that.seenCredits;
        this.enteredNetherPosition = that.enteredNetherPosition;
        this.setLeftShoulderEntity(that.getLeftShoulderEntity());
        this.setRightShoulderEntity(that.getRightShoulderEntity());
    }

    protected void onNewPotionEffect(PotionEffect id)
    {
        super.onNewPotionEffect(id);
        this.connection.sendPacket(new SPacketEntityEffect(this.getEntityId(), id));

        if (id.getPotion() == MobEffects.LEVITATION)
        {
            this.levitatingSince = this.ticksExisted;
            this.levitationStartPos = new Vec3d(this.posX, this.posY, this.posZ);
        }

        CriteriaTriggers.EFFECTS_CHANGED.trigger(this);
    }

    protected void onChangedPotionEffect(PotionEffect id, boolean p_70695_2_)
    {
        super.onChangedPotionEffect(id, p_70695_2_);
        this.connection.sendPacket(new SPacketEntityEffect(this.getEntityId(), id));
        CriteriaTriggers.EFFECTS_CHANGED.trigger(this);
    }

    protected void onFinishedPotionEffect(PotionEffect effect)
    {
        super.onFinishedPotionEffect(effect);
        this.connection.sendPacket(new SPacketRemoveEntityEffect(this.getEntityId(), effect.getPotion()));

        if (effect.getPotion() == MobEffects.LEVITATION)
        {
            this.levitationStartPos = null;
        }

        CriteriaTriggers.EFFECTS_CHANGED.trigger(this);
    }

    /**
     * Sets the position of the entity and updates the 'last' variables
     */
    public void setPositionAndUpdate(double x, double y, double z)
    {
        this.connection.setPlayerLocation(x, y, z, this.rotationYaw, this.rotationPitch);
    }

    /**
     * Called when the entity is dealt a critical hit.
     */
    public void onCriticalHit(Entity entityHit)
    {
        this.getServerWorld().getEntityTracker().sendToTrackingAndSelf(this, new SPacketAnimation(entityHit, 4));
    }

    public void onEnchantmentCritical(Entity entityHit)
    {
        this.getServerWorld().getEntityTracker().sendToTrackingAndSelf(this, new SPacketAnimation(entityHit, 5));
    }

    /**
     * Sends the player's abilities to the server (if there is one).
     */
    public void sendPlayerAbilities()
    {
        if (this.connection != null)
        {
            this.connection.sendPacket(new SPacketPlayerAbilities(this.capabilities));
            this.updatePotionMetadata();
        }
    }

    public WorldServer getServerWorld()
    {
        return (WorldServer)this.world;
    }

    /**
     * Sets the player's game mode and sends it to them.
     */
    public void setGameType(GameType gameType)
    {
        this.interactionManager.setGameType(gameType);
        this.connection.sendPacket(new SPacketChangeGameState(3, (float)gameType.getID()));

        if (gameType == GameType.SPECTATOR)
        {
            this.spawnShoulderEntities();
            this.dismountRidingEntity();
        }
        else
        {
            this.setSpectatingEntity(this);
        }

        this.sendPlayerAbilities();
        this.markPotionsDirty();
    }

    /**
     * Returns true if the player is in spectator mode.
     */
    public boolean isSpectator()
    {
        return this.interactionManager.getGameType() == GameType.SPECTATOR;
    }

    public boolean isCreative()
    {
        return this.interactionManager.getGameType() == GameType.CREATIVE;
    }

    /**
     * Send a chat message to the CommandSender
     */
    public void sendMessage(ITextComponent component)
    {
        this.connection.sendPacket(new SPacketChat(component));
    }

    /**
     * Returns {@code true} if the CommandSender is allowed to execute the command, {@code false} if not
     */
    public boolean canUseCommand(int permLevel, String commandName)
    {
        if ("seed".equals(commandName) && !this.mcServer.isDedicatedServer())
        {
            return true;
        }
        else if (!"tell".equals(commandName) && !"help".equals(commandName) && !"me".equals(commandName) && !"trigger".equals(commandName))
        {
            if (this.mcServer.getPlayerList().canSendCommands(this.getGameProfile()))
            {
                UserListOpsEntry userlistopsentry = (UserListOpsEntry)this.mcServer.getPlayerList().getOppedPlayers().getEntry(this.getGameProfile());

                if (userlistopsentry != null)
                {
                    return userlistopsentry.getPermissionLevel() >= permLevel;
                }
                else
                {
                    return this.mcServer.getOpPermissionLevel() >= permLevel;
                }
            }
            else
            {
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    /**
     * Gets the player's IP address. Used in /banip.
     */
    public String getPlayerIP()
    {
        String s = this.connection.netManager.getRemoteAddress().toString();
        s = s.substring(s.indexOf("/") + 1);
        s = s.substring(0, s.indexOf(":"));
        return s;
    }

    public void handleClientSettings(CPacketClientSettings packetIn)
    {
        this.language = packetIn.getLang();
        this.chatVisibility = packetIn.getChatVisibility();
        this.chatColours = packetIn.isColorsEnabled();
        this.getDataManager().set(PLAYER_MODEL_FLAG, Byte.valueOf((byte)packetIn.getModelPartFlags()));
        this.getDataManager().set(MAIN_HAND, Byte.valueOf((byte)(packetIn.getMainHand() == EnumHandSide.LEFT ? 0 : 1)));
    }

    public EntityPlayer.EnumChatVisibility getChatVisibility()
    {
        return this.chatVisibility;
    }

    public void loadResourcePack(String url, String hash)
    {
        this.connection.sendPacket(new SPacketResourcePackSend(url, hash));
    }

    /**
     * Get the position in the world. <b>{@code null} is not allowed!</b> If you are not an entity in the world, return
     * the coordinates 0, 0, 0
     */
    public BlockPos getPosition()
    {
        return new BlockPos(this.posX, this.posY + 0.5D, this.posZ);
    }

    public void markPlayerActive()
    {
        this.playerLastActiveTime = MinecraftServer.getCurrentTimeMillis();
    }

    /**
     * Gets the stats file for reading achievements
     */
    public StatisticsManagerServer getStatFile()
    {
        return this.statsFile;
    }

    public RecipeBookServer getRecipeBook()
    {
        return this.recipeBook;
    }

    /**
     * Sends a packet to the player to remove an entity.
     */
    public void removeEntity(Entity entityIn)
    {
        if (entityIn instanceof EntityPlayer)
        {
            this.connection.sendPacket(new SPacketDestroyEntities(new int[] {entityIn.getEntityId()}));
        }
        else
        {
            this.entityRemoveQueue.add(Integer.valueOf(entityIn.getEntityId()));
        }
    }

    public void addEntity(Entity entityIn)
    {
        this.entityRemoveQueue.remove(Integer.valueOf(entityIn.getEntityId()));
    }

    /**
     * Clears potion metadata values if the entity has no potion effects. Otherwise, updates potion effect color,
     * ambience, and invisibility metadata values
     */
    protected void updatePotionMetadata()
    {
        if (this.isSpectator())
        {
            this.resetPotionEffectMetadata();
            this.setInvisible(true);
        }
        else
        {
            super.updatePotionMetadata();
        }

        this.getServerWorld().getEntityTracker().updateVisibility(this);
    }

    public Entity getSpectatingEntity()
    {
        return (Entity)(this.spectatingEntity == null ? this : this.spectatingEntity);
    }

    public void setSpectatingEntity(Entity entityToSpectate)
    {
        Entity entity = this.getSpectatingEntity();
        this.spectatingEntity = (Entity)(entityToSpectate == null ? this : entityToSpectate);

        if (entity != this.spectatingEntity)
        {
            this.connection.sendPacket(new SPacketCamera(this.spectatingEntity));
            this.setPositionAndUpdate(this.spectatingEntity.posX, this.spectatingEntity.posY, this.spectatingEntity.posZ);
        }
    }

    /**
     * Decrements the counter for the remaining time until the entity may use a portal again.
     */
    protected void decrementTimeUntilPortal()
    {
        if (this.timeUntilPortal > 0 && !this.invulnerableDimensionChange)
        {
            --this.timeUntilPortal;
        }
    }

    /**
     * Attacks for the player the targeted entity with the currently equipped item.  The equipped item has hitEntity
     * called on it. Args: targetEntity
     */
    public void attackTargetEntityWithCurrentItem(Entity targetEntity)
    {
        if (this.interactionManager.getGameType() == GameType.SPECTATOR)
        {
            this.setSpectatingEntity(targetEntity);
        }
        else
        {
            super.attackTargetEntityWithCurrentItem(targetEntity);
        }
    }

    public long getLastActiveTime()
    {
        return this.playerLastActiveTime;
    }

    @Nullable

    /**
     * Returns null which indicates the tab list should just display the player's name, return a different value to
     * display the specified text instead of the player's name
     */
    public ITextComponent getTabListDisplayName()
    {
        return null;
    }

    public void swingArm(EnumHand hand)
    {
        super.swingArm(hand);
        this.resetCooldown();
    }

    public boolean isInvulnerableDimensionChange()
    {
        return this.invulnerableDimensionChange;
    }

    public void clearInvulnerableDimensionChange()
    {
        this.invulnerableDimensionChange = false;
    }

    public void setElytraFlying()
    {
        this.setFlag(7, true);
    }

    public void clearElytraFlying()
    {
        this.setFlag(7, true);
        this.setFlag(7, false);
    }

    public PlayerAdvancements getAdvancements()
    {
        return this.advancements;
    }

    @Nullable
    public Vec3d getEnteredNetherPosition()
    {
        return this.enteredNetherPosition;
    }
}
