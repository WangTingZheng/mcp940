package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemClock extends Item
{
    public ItemClock()
    {
        this.addPropertyOverride(new ResourceLocation("time"), new IItemPropertyGetter()
        {
            double rotation;
            double rota;
            long lastUpdateTick;
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                boolean flag = entityIn != null;
                Entity entity = (Entity)(flag ? entityIn : stack.getItemFrame());

                if (worldIn == null && entity != null)
                {
                    worldIn = entity.world;
                }

                if (worldIn == null)
                {
                    return 0.0F;
                }
                else
                {
                    double d0;

                    if (worldIn.provider.isSurfaceWorld())
                    {
                        d0 = (double)worldIn.getCelestialAngle(1.0F);
                    }
                    else
                    {
                        d0 = Math.random();
                    }

                    d0 = this.wobble(worldIn, d0);
                    return (float)d0;
                }
            }
            private double wobble(World p_185087_1_, double p_185087_2_)
            {
                if (p_185087_1_.getTotalWorldTime() != this.lastUpdateTick)
                {
                    this.lastUpdateTick = p_185087_1_.getTotalWorldTime();
                    double d0 = p_185087_2_ - this.rotation;
                    d0 = MathHelper.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
                    this.rota += d0 * 0.1D;
                    this.rota *= 0.9D;
                    this.rotation = MathHelper.positiveModulo(this.rotation + this.rota, 1.0D);
                }

                return this.rotation;
            }
        });
    }
}
