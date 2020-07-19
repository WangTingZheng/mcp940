package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemDye;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleFirework
{
    public static class Factory implements IParticleFactory
    {
        public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_)
        {
            ParticleFirework.Spark particlefirework$spark = new ParticleFirework.Spark(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, Minecraft.getMinecraft().effectRenderer);
            particlefirework$spark.setAlphaF(0.99F);
            return particlefirework$spark;
        }
    }

    public static class Overlay extends Particle
    {
        protected Overlay(World p_i46466_1_, double p_i46466_2_, double p_i46466_4_, double p_i46466_6_)
        {
            super(p_i46466_1_, p_i46466_2_, p_i46466_4_, p_i46466_6_);
            this.particleMaxAge = 4;
        }

        public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
        {
            float f = 0.25F;
            float f1 = 0.5F;
            float f2 = 0.125F;
            float f3 = 0.375F;
            float f4 = 7.1F * MathHelper.sin(((float)this.particleAge + partialTicks - 1.0F) * 0.25F * (float)Math.PI);
            this.setAlphaF(0.6F - ((float)this.particleAge + partialTicks - 1.0F) * 0.25F * 0.5F);
            float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
            float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
            float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);
            int i = this.getBrightnessForRender(partialTicks);
            int j = i >> 16 & 65535;
            int k = i & 65535;
            buffer.pos((double)(f5 - rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4), (double)(f7 - rotationYZ * f4 - rotationXZ * f4)).tex(0.5D, 0.375D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
            buffer.pos((double)(f5 - rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4), (double)(f7 - rotationYZ * f4 + rotationXZ * f4)).tex(0.5D, 0.125D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
            buffer.pos((double)(f5 + rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4), (double)(f7 + rotationYZ * f4 + rotationXZ * f4)).tex(0.25D, 0.125D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
            buffer.pos((double)(f5 + rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4), (double)(f7 + rotationYZ * f4 - rotationXZ * f4)).tex(0.25D, 0.375D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        }
    }

    public static class Spark extends ParticleSimpleAnimated
    {
        private boolean trail;
        private boolean twinkle;
        private final ParticleManager effectRenderer;
        private float fadeColourRed;
        private float fadeColourGreen;
        private float fadeColourBlue;
        private boolean hasFadeColour;

        public Spark(World p_i46465_1_, double p_i46465_2_, double p_i46465_4_, double p_i46465_6_, double p_i46465_8_, double p_i46465_10_, double p_i46465_12_, ParticleManager p_i46465_14_)
        {
            super(p_i46465_1_, p_i46465_2_, p_i46465_4_, p_i46465_6_, 160, 8, -0.004F);
            this.motionX = p_i46465_8_;
            this.motionY = p_i46465_10_;
            this.motionZ = p_i46465_12_;
            this.effectRenderer = p_i46465_14_;
            this.particleScale *= 0.75F;
            this.particleMaxAge = 48 + this.rand.nextInt(12);
        }

        public void setTrail(boolean trailIn)
        {
            this.trail = trailIn;
        }

        public void setTwinkle(boolean twinkleIn)
        {
            this.twinkle = twinkleIn;
        }

        public boolean shouldDisableDepth()
        {
            return true;
        }

        public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
        {
            if (!this.twinkle || this.particleAge < this.particleMaxAge / 3 || (this.particleAge + this.particleMaxAge) / 3 % 2 == 0)
            {
                super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
            }
        }

        public void onUpdate()
        {
            super.onUpdate();

            if (this.trail && this.particleAge < this.particleMaxAge / 2 && (this.particleAge + this.particleMaxAge) % 2 == 0)
            {
                ParticleFirework.Spark particlefirework$spark = new ParticleFirework.Spark(this.world, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, this.effectRenderer);
                particlefirework$spark.setAlphaF(0.99F);
                particlefirework$spark.setRBGColorF(this.particleRed, this.particleGreen, this.particleBlue);
                particlefirework$spark.particleAge = particlefirework$spark.particleMaxAge / 2;

                if (this.hasFadeColour)
                {
                    particlefirework$spark.hasFadeColour = true;
                    particlefirework$spark.fadeColourRed = this.fadeColourRed;
                    particlefirework$spark.fadeColourGreen = this.fadeColourGreen;
                    particlefirework$spark.fadeColourBlue = this.fadeColourBlue;
                }

                particlefirework$spark.twinkle = this.twinkle;
                this.effectRenderer.addEffect(particlefirework$spark);
            }
        }
    }

    public static class Starter extends Particle
    {
        private int fireworkAge;
        private final ParticleManager manager;
        private NBTTagList fireworkExplosions;
        boolean twinkle;

        public Starter(World p_i46464_1_, double p_i46464_2_, double p_i46464_4_, double p_i46464_6_, double p_i46464_8_, double p_i46464_10_, double p_i46464_12_, ParticleManager p_i46464_14_, @Nullable NBTTagCompound p_i46464_15_)
        {
            super(p_i46464_1_, p_i46464_2_, p_i46464_4_, p_i46464_6_, 0.0D, 0.0D, 0.0D);
            this.motionX = p_i46464_8_;
            this.motionY = p_i46464_10_;
            this.motionZ = p_i46464_12_;
            this.manager = p_i46464_14_;
            this.particleMaxAge = 8;

            if (p_i46464_15_ != null)
            {
                this.fireworkExplosions = p_i46464_15_.getTagList("Explosions", 10);

                if (this.fireworkExplosions.hasNoTags())
                {
                    this.fireworkExplosions = null;
                }
                else
                {
                    this.particleMaxAge = this.fireworkExplosions.tagCount() * 2 - 1;

                    for (int i = 0; i < this.fireworkExplosions.tagCount(); ++i)
                    {
                        NBTTagCompound nbttagcompound = this.fireworkExplosions.getCompoundTagAt(i);

                        if (nbttagcompound.getBoolean("Flicker"))
                        {
                            this.twinkle = true;
                            this.particleMaxAge += 15;
                            break;
                        }
                    }
                }
            }
        }

        public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
        {
        }

        public void onUpdate()
        {
            if (this.fireworkAge == 0 && this.fireworkExplosions != null)
            {
                boolean flag = this.isFarFromCamera();
                boolean flag1 = false;

                if (this.fireworkExplosions.tagCount() >= 3)
                {
                    flag1 = true;
                }
                else
                {
                    for (int i = 0; i < this.fireworkExplosions.tagCount(); ++i)
                    {
                        NBTTagCompound nbttagcompound = this.fireworkExplosions.getCompoundTagAt(i);

                        if (nbttagcompound.getByte("Type") == 1)
                        {
                            flag1 = true;
                            break;
                        }
                    }
                }

                SoundEvent soundevent1;

                if (flag1)
                {
                    soundevent1 = flag ? SoundEvents.ENTITY_FIREWORK_LARGE_BLAST_FAR : SoundEvents.ENTITY_FIREWORK_LARGE_BLAST;
                }
                else
                {
                    soundevent1 = flag ? SoundEvents.ENTITY_FIREWORK_BLAST_FAR : SoundEvents.ENTITY_FIREWORK_BLAST;
                }

                this.world.playSound(this.posX, this.posY, this.posZ, soundevent1, SoundCategory.AMBIENT, 20.0F, 0.95F + this.rand.nextFloat() * 0.1F, true);
            }

            if (this.fireworkAge % 2 == 0 && this.fireworkExplosions != null && this.fireworkAge / 2 < this.fireworkExplosions.tagCount())
            {
                int k = this.fireworkAge / 2;
                NBTTagCompound nbttagcompound1 = this.fireworkExplosions.getCompoundTagAt(k);
                int l = nbttagcompound1.getByte("Type");
                boolean flag4 = nbttagcompound1.getBoolean("Trail");
                boolean flag2 = nbttagcompound1.getBoolean("Flicker");
                int[] aint = nbttagcompound1.getIntArray("Colors");
                int[] aint1 = nbttagcompound1.getIntArray("FadeColors");

                if (aint.length == 0)
                {
                    aint = new int[] {ItemDye.DYE_COLORS[0]};
                }

                if (l == 1)
                {
                    this.createBall(0.5D, 4, aint, aint1, flag4, flag2);
                }
                else if (l == 2)
                {
                    this.createShaped(0.5D, new double[][] {{0.0D, 1.0D}, {0.3455D, 0.309D}, {0.9511D, 0.309D}, {0.3795918367346939D, -0.12653061224489795D}, {0.6122448979591837D, -0.8040816326530612D}, {0.0D, -0.35918367346938773D}}, aint, aint1, flag4, flag2, false);
                }
                else if (l == 3)
                {
                    this.createShaped(0.5D, new double[][] {{0.0D, 0.2D}, {0.2D, 0.2D}, {0.2D, 0.6D}, {0.6D, 0.6D}, {0.6D, 0.2D}, {0.2D, 0.2D}, {0.2D, 0.0D}, {0.4D, 0.0D}, {0.4D, -0.6D}, {0.2D, -0.6D}, {0.2D, -0.4D}, {0.0D, -0.4D}}, aint, aint1, flag4, flag2, true);
                }
                else if (l == 4)
                {
                    this.createBurst(aint, aint1, flag4, flag2);
                }
                else
                {
                    this.createBall(0.25D, 2, aint, aint1, flag4, flag2);
                }

                int j = aint[0];
                float f = (float)((j & 16711680) >> 16) / 255.0F;
                float f1 = (float)((j & 65280) >> 8) / 255.0F;
                float f2 = (float)((j & 255) >> 0) / 255.0F;
                ParticleFirework.Overlay particlefirework$overlay = new ParticleFirework.Overlay(this.world, this.posX, this.posY, this.posZ);
                particlefirework$overlay.setRBGColorF(f, f1, f2);
                this.manager.addEffect(particlefirework$overlay);
            }

            ++this.fireworkAge;

            if (this.fireworkAge > this.particleMaxAge)
            {
                if (this.twinkle)
                {
                    boolean flag3 = this.isFarFromCamera();
                    SoundEvent soundevent = flag3 ? SoundEvents.ENTITY_FIREWORK_TWINKLE_FAR : SoundEvents.ENTITY_FIREWORK_TWINKLE;
                    this.world.playSound(this.posX, this.posY, this.posZ, soundevent, SoundCategory.AMBIENT, 20.0F, 0.9F + this.rand.nextFloat() * 0.15F, true);
                }

                this.setExpired();
            }
        }

        private boolean isFarFromCamera()
        {
            Minecraft minecraft = Minecraft.getMinecraft();
            return minecraft == null || minecraft.getRenderViewEntity() == null || minecraft.getRenderViewEntity().getDistanceSq(this.posX, this.posY, this.posZ) >= 256.0D;
        }

        private void createParticle(double p_92034_1_, double p_92034_3_, double p_92034_5_, double p_92034_7_, double p_92034_9_, double p_92034_11_, int[] p_92034_13_, int[] p_92034_14_, boolean p_92034_15_, boolean p_92034_16_)
        {
            ParticleFirework.Spark particlefirework$spark = new ParticleFirework.Spark(this.world, p_92034_1_, p_92034_3_, p_92034_5_, p_92034_7_, p_92034_9_, p_92034_11_, this.manager);
            particlefirework$spark.setAlphaF(0.99F);
            particlefirework$spark.setTrail(p_92034_15_);
            particlefirework$spark.setTwinkle(p_92034_16_);
            int i = this.rand.nextInt(p_92034_13_.length);
            particlefirework$spark.setColor(p_92034_13_[i]);

            if (p_92034_14_ != null && p_92034_14_.length > 0)
            {
                particlefirework$spark.setColorFade(p_92034_14_[this.rand.nextInt(p_92034_14_.length)]);
            }

            this.manager.addEffect(particlefirework$spark);
        }

        private void createBall(double speed, int size, int[] colours, int[] fadeColours, boolean trail, boolean twinkleIn)
        {
            double d0 = this.posX;
            double d1 = this.posY;
            double d2 = this.posZ;

            for (int i = -size; i <= size; ++i)
            {
                for (int j = -size; j <= size; ++j)
                {
                    for (int k = -size; k <= size; ++k)
                    {
                        double d3 = (double)j + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
                        double d4 = (double)i + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
                        double d5 = (double)k + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
                        double d6 = (double)MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5) / speed + this.rand.nextGaussian() * 0.05D;
                        this.createParticle(d0, d1, d2, d3 / d6, d4 / d6, d5 / d6, colours, fadeColours, trail, twinkleIn);

                        if (i != -size && i != size && j != -size && j != size)
                        {
                            k += size * 2 - 1;
                        }
                    }
                }
            }
        }

        private void createShaped(double speed, double[][] shape, int[] colours, int[] fadeColours, boolean trail, boolean twinkleIn, boolean p_92038_8_)
        {
            double d0 = shape[0][0];
            double d1 = shape[0][1];
            this.createParticle(this.posX, this.posY, this.posZ, d0 * speed, d1 * speed, 0.0D, colours, fadeColours, trail, twinkleIn);
            float f = this.rand.nextFloat() * (float)Math.PI;
            double d2 = p_92038_8_ ? 0.034D : 0.34D;

            for (int i = 0; i < 3; ++i)
            {
                double d3 = (double)f + (double)((float)i * (float)Math.PI) * d2;
                double d4 = d0;
                double d5 = d1;

                for (int j = 1; j < shape.length; ++j)
                {
                    double d6 = shape[j][0];
                    double d7 = shape[j][1];

                    for (double d8 = 0.25D; d8 <= 1.0D; d8 += 0.25D)
                    {
                        double d9 = (d4 + (d6 - d4) * d8) * speed;
                        double d10 = (d5 + (d7 - d5) * d8) * speed;
                        double d11 = d9 * Math.sin(d3);
                        d9 = d9 * Math.cos(d3);

                        for (double d12 = -1.0D; d12 <= 1.0D; d12 += 2.0D)
                        {
                            this.createParticle(this.posX, this.posY, this.posZ, d9 * d12, d10, d11 * d12, colours, fadeColours, trail, twinkleIn);
                        }
                    }

                    d4 = d6;
                    d5 = d7;
                }
            }
        }

        private void createBurst(int[] colours, int[] fadeColours, boolean trail, boolean twinkleIn)
        {
            double d0 = this.rand.nextGaussian() * 0.05D;
            double d1 = this.rand.nextGaussian() * 0.05D;

            for (int i = 0; i < 70; ++i)
            {
                double d2 = this.motionX * 0.5D + this.rand.nextGaussian() * 0.15D + d0;
                double d3 = this.motionZ * 0.5D + this.rand.nextGaussian() * 0.15D + d1;
                double d4 = this.motionY * 0.5D + this.rand.nextDouble() * 0.5D;
                this.createParticle(this.posX, this.posY, this.posZ, d2, d4, d3, colours, fadeColours, trail, twinkleIn);
            }
        }

        public int getFXLayer()
        {
            return 0;
        }
    }
}
