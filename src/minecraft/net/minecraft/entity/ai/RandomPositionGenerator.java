package net.minecraft.entity.ai;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RandomPositionGenerator
{
    /**
     * used to store a driection when the user passes a point to move towards or away from. WARNING: NEVER THREAD SAFE.
     * MULTIPLE findTowards and findAway calls, will share this var
     */
    private static Vec3d staticVector = Vec3d.ZERO;

    @Nullable

    /**
     * finds a random target within par1(x,z) and par2 (y) blocks
     */
    public static Vec3d findRandomTarget(EntityCreature entitycreatureIn, int xz, int y)
    {
        return findRandomTargetBlock(entitycreatureIn, xz, y, (Vec3d)null);
    }

    @Nullable
    public static Vec3d getLandPos(EntityCreature p_191377_0_, int p_191377_1_, int p_191377_2_)
    {
        return generateRandomPos(p_191377_0_, p_191377_1_, p_191377_2_, (Vec3d)null, false);
    }

    @Nullable

    /**
     * finds a random target within par1(x,z) and par2 (y) blocks in the direction of the point par3
     */
    public static Vec3d findRandomTargetBlockTowards(EntityCreature entitycreatureIn, int xz, int y, Vec3d targetVec3)
    {
        staticVector = targetVec3.subtract(entitycreatureIn.posX, entitycreatureIn.posY, entitycreatureIn.posZ);
        return findRandomTargetBlock(entitycreatureIn, xz, y, staticVector);
    }

    @Nullable

    /**
     * finds a random target within par1(x,z) and par2 (y) blocks in the reverse direction of the point par3
     */
    public static Vec3d findRandomTargetBlockAwayFrom(EntityCreature entitycreatureIn, int xz, int y, Vec3d targetVec3)
    {
        staticVector = (new Vec3d(entitycreatureIn.posX, entitycreatureIn.posY, entitycreatureIn.posZ)).subtract(targetVec3);
        return findRandomTargetBlock(entitycreatureIn, xz, y, staticVector);
    }

    @Nullable

    /**
     * searches 10 blocks at random in a within par1(x,z) and par2 (y) distance, ignores those not in the direction of
     * par3Vec3, then points to the tile for which creature.getBlockPathWeight returns the highest number
     */
    private static Vec3d findRandomTargetBlock(EntityCreature entitycreatureIn, int xz, int y, @Nullable Vec3d targetVec3)
    {
        return generateRandomPos(entitycreatureIn, xz, y, targetVec3, true);
    }

    @Nullable
    private static Vec3d generateRandomPos(EntityCreature p_191379_0_, int p_191379_1_, int p_191379_2_, @Nullable Vec3d p_191379_3_, boolean p_191379_4_)
    {
        PathNavigate pathnavigate = p_191379_0_.getNavigator();
        Random random = p_191379_0_.getRNG();
        boolean flag;

        if (p_191379_0_.hasHome())
        {
            double d0 = p_191379_0_.getHomePosition().distanceSq((double)MathHelper.floor(p_191379_0_.posX), (double)MathHelper.floor(p_191379_0_.posY), (double)MathHelper.floor(p_191379_0_.posZ)) + 4.0D;
            double d1 = (double)(p_191379_0_.getMaximumHomeDistance() + (float)p_191379_1_);
            flag = d0 < d1 * d1;
        }
        else
        {
            flag = false;
        }

        boolean flag1 = false;
        float f = -99999.0F;
        int k1 = 0;
        int i = 0;
        int j = 0;

        for (int k = 0; k < 10; ++k)
        {
            int l = random.nextInt(2 * p_191379_1_ + 1) - p_191379_1_;
            int i1 = random.nextInt(2 * p_191379_2_ + 1) - p_191379_2_;
            int j1 = random.nextInt(2 * p_191379_1_ + 1) - p_191379_1_;

            if (p_191379_3_ == null || (double)l * p_191379_3_.x + (double)j1 * p_191379_3_.z >= 0.0D)
            {
                if (p_191379_0_.hasHome() && p_191379_1_ > 1)
                {
                    BlockPos blockpos = p_191379_0_.getHomePosition();

                    if (p_191379_0_.posX > (double)blockpos.getX())
                    {
                        l -= random.nextInt(p_191379_1_ / 2);
                    }
                    else
                    {
                        l += random.nextInt(p_191379_1_ / 2);
                    }

                    if (p_191379_0_.posZ > (double)blockpos.getZ())
                    {
                        j1 -= random.nextInt(p_191379_1_ / 2);
                    }
                    else
                    {
                        j1 += random.nextInt(p_191379_1_ / 2);
                    }
                }

                BlockPos blockpos1 = new BlockPos((double)l + p_191379_0_.posX, (double)i1 + p_191379_0_.posY, (double)j1 + p_191379_0_.posZ);

                if ((!flag || p_191379_0_.isWithinHomeDistanceFromPosition(blockpos1)) && pathnavigate.canEntityStandOnPos(blockpos1))
                {
                    if (!p_191379_4_)
                    {
                        blockpos1 = moveAboveSolid(blockpos1, p_191379_0_);

                        if (isWaterDestination(blockpos1, p_191379_0_))
                        {
                            continue;
                        }
                    }

                    float f1 = p_191379_0_.getBlockPathWeight(blockpos1);

                    if (f1 > f)
                    {
                        f = f1;
                        k1 = l;
                        i = i1;
                        j = j1;
                        flag1 = true;
                    }
                }
            }
        }

        if (flag1)
        {
            return new Vec3d((double)k1 + p_191379_0_.posX, (double)i + p_191379_0_.posY, (double)j + p_191379_0_.posZ);
        }
        else
        {
            return null;
        }
    }

    private static BlockPos moveAboveSolid(BlockPos p_191378_0_, EntityCreature p_191378_1_)
    {
        if (!p_191378_1_.world.getBlockState(p_191378_0_).getMaterial().isSolid())
        {
            return p_191378_0_;
        }
        else
        {
            BlockPos blockpos;

            for (blockpos = p_191378_0_.up(); blockpos.getY() < p_191378_1_.world.getHeight() && p_191378_1_.world.getBlockState(blockpos).getMaterial().isSolid(); blockpos = blockpos.up())
            {
                ;
            }

            return blockpos;
        }
    }

    private static boolean isWaterDestination(BlockPos p_191380_0_, EntityCreature p_191380_1_)
    {
        return p_191380_1_.world.getBlockState(p_191380_0_).getMaterial() == Material.WATER;
    }
}
