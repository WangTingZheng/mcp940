package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemCompass extends Item
{
    public ItemCompass()
    {
        this.addPropertyOverride(new ResourceLocation("angle"), new IItemPropertyGetter()
        {
            double rotation;
            double rota;
            long lastUpdateTick;
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if (entityIn == null && !stack.isOnItemFrame())
                {
                    return 0.0F;
                }
                else
                {
                    boolean flag = entityIn != null;
                    Entity entity = (Entity)(flag ? entityIn : stack.getItemFrame());

                    if (worldIn == null)
                    {
                        worldIn = entity.world;
                    }

                    double d0;

                    if (worldIn.provider.isSurfaceWorld())
                    {
                        double d1 = flag ? (double)entity.rotationYaw : this.getFrameRotation((EntityItemFrame)entity);
                        d1 = MathHelper.positiveModulo(d1 / 360.0D, 1.0D);
                        double d2 = this.getSpawnToAngle(worldIn, entity) / (Math.PI * 2D);
                        d0 = 0.5D - (d1 - 0.25D - d2);
                    }
                    else
                    {
                        d0 = Math.random();
                    }

                    if (flag)
                    {
                        d0 = this.wobble(worldIn, d0);
                    }

                    return MathHelper.positiveModulo((float)d0, 1.0F);
                }
            }
            private double wobble(World worldIn, double p_185093_2_)
            {
                if (worldIn.getTotalWorldTime() != this.lastUpdateTick)
                {
                    this.lastUpdateTick = worldIn.getTotalWorldTime();
                    double d0 = p_185093_2_ - this.rotation;
                    d0 = MathHelper.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
                    this.rota += d0 * 0.1D;
                    this.rota *= 0.8D;
                    this.rotation = MathHelper.positiveModulo(this.rotation + this.rota, 1.0D);
                }

                return this.rotation;
            }
            private double getFrameRotation(EntityItemFrame p_185094_1_)
            {
                return (double)MathHelper.clampAngle(180 + p_185094_1_.facingDirection.getHorizontalIndex() * 90);
            }
            private double getSpawnToAngle(World p_185092_1_, Entity p_185092_2_)
            {
                BlockPos blockpos = p_185092_1_.getSpawnPoint();
                return Math.atan2((double)blockpos.getZ() - p_185092_2_.posZ, (double)blockpos.getX() - p_185092_2_.posX);
            }
        });
    }
}
