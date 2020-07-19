package net.minecraft.village;

import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;

public class VillageSiege
{
    private final World world;
    private boolean hasSetupSiege;
    private int siegeState = -1;
    private int siegeCount;
    private int nextSpawnTime;

    /** Instance of Village. */
    private Village village;
    private int spawnX;
    private int spawnY;
    private int spawnZ;

    public VillageSiege(World worldIn)
    {
        this.world = worldIn;
    }

    /**
     * Runs a single tick for the village siege
     */
    public void tick()
    {
        if (this.world.isDaytime())
        {
            this.siegeState = 0;
        }
        else if (this.siegeState != 2)
        {
            if (this.siegeState == 0)
            {
                float f = this.world.getCelestialAngle(0.0F);

                if ((double)f < 0.5D || (double)f > 0.501D)
                {
                    return;
                }

                this.siegeState = this.world.rand.nextInt(10) == 0 ? 1 : 2;
                this.hasSetupSiege = false;

                if (this.siegeState == 2)
                {
                    return;
                }
            }

            if (this.siegeState != -1)
            {
                if (!this.hasSetupSiege)
                {
                    if (!this.trySetupSiege())
                    {
                        return;
                    }

                    this.hasSetupSiege = true;
                }

                if (this.nextSpawnTime > 0)
                {
                    --this.nextSpawnTime;
                }
                else
                {
                    this.nextSpawnTime = 2;

                    if (this.siegeCount > 0)
                    {
                        this.spawnZombie();
                        --this.siegeCount;
                    }
                    else
                    {
                        this.siegeState = 2;
                    }
                }
            }
        }
    }

    private boolean trySetupSiege()
    {
        List<EntityPlayer> list = this.world.playerEntities;
        Iterator iterator = list.iterator();

        while (true)
        {
            if (!iterator.hasNext())
            {
                return false;
            }

            EntityPlayer entityplayer = (EntityPlayer)iterator.next();

            if (!entityplayer.isSpectator())
            {
                this.village = this.world.getVillageCollection().getNearestVillage(new BlockPos(entityplayer), 1);

                if (this.village != null && this.village.getNumVillageDoors() >= 10 && this.village.getTicksSinceLastDoorAdding() >= 20 && this.village.getNumVillagers() >= 20)
                {
                    BlockPos blockpos = this.village.getCenter();
                    float f = (float)this.village.getVillageRadius();
                    boolean flag = false;

                    for (int i = 0; i < 10; ++i)
                    {
                        float f1 = this.world.rand.nextFloat() * ((float)Math.PI * 2F);
                        this.spawnX = blockpos.getX() + (int)((double)(MathHelper.cos(f1) * f) * 0.9D);
                        this.spawnY = blockpos.getY();
                        this.spawnZ = blockpos.getZ() + (int)((double)(MathHelper.sin(f1) * f) * 0.9D);
                        flag = false;

                        for (Village village : this.world.getVillageCollection().getVillageList())
                        {
                            if (village != this.village && village.isBlockPosWithinSqVillageRadius(new BlockPos(this.spawnX, this.spawnY, this.spawnZ)))
                            {
                                flag = true;
                                break;
                            }
                        }

                        if (!flag)
                        {
                            break;
                        }
                    }

                    if (flag)
                    {
                        return false;
                    }

                    Vec3d vec3d = this.findRandomSpawnPos(new BlockPos(this.spawnX, this.spawnY, this.spawnZ));

                    if (vec3d != null)
                    {
                        break;
                    }
                }
            }
        }

        this.nextSpawnTime = 0;
        this.siegeCount = 20;
        return true;
    }

    private boolean spawnZombie()
    {
        Vec3d vec3d = this.findRandomSpawnPos(new BlockPos(this.spawnX, this.spawnY, this.spawnZ));

        if (vec3d == null)
        {
            return false;
        }
        else
        {
            EntityZombie entityzombie;

            try
            {
                entityzombie = new EntityZombie(this.world);
                entityzombie.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityzombie)), (IEntityLivingData)null);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                return false;
            }

            entityzombie.setLocationAndAngles(vec3d.x, vec3d.y, vec3d.z, this.world.rand.nextFloat() * 360.0F, 0.0F);
            this.world.spawnEntity(entityzombie);
            BlockPos blockpos = this.village.getCenter();
            entityzombie.setHomePosAndDistance(blockpos, this.village.getVillageRadius());
            return true;
        }
    }

    @Nullable
    private Vec3d findRandomSpawnPos(BlockPos pos)
    {
        for (int i = 0; i < 10; ++i)
        {
            BlockPos blockpos = pos.add(this.world.rand.nextInt(16) - 8, this.world.rand.nextInt(6) - 3, this.world.rand.nextInt(16) - 8);

            if (this.village.isBlockPosWithinSqVillageRadius(blockpos) && WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, this.world, blockpos))
            {
                return new Vec3d((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ());
            }
        }

        return null;
    }
}
