package net.minecraft.client.particle;

import net.minecraft.world.World;

public class ParticleTotem extends ParticleSimpleAnimated
{
    public ParticleTotem(World p_i47220_1_, double p_i47220_2_, double p_i47220_4_, double p_i47220_6_, double p_i47220_8_, double p_i47220_10_, double p_i47220_12_)
    {
        super(p_i47220_1_, p_i47220_2_, p_i47220_4_, p_i47220_6_, 176, 8, -0.05F);
        this.motionX = p_i47220_8_;
        this.motionY = p_i47220_10_;
        this.motionZ = p_i47220_12_;
        this.particleScale *= 0.75F;
        this.particleMaxAge = 60 + this.rand.nextInt(12);

        if (this.rand.nextInt(4) == 0)
        {
            this.setRBGColorF(0.6F + this.rand.nextFloat() * 0.2F, 0.6F + this.rand.nextFloat() * 0.3F, this.rand.nextFloat() * 0.2F);
        }
        else
        {
            this.setRBGColorF(0.1F + this.rand.nextFloat() * 0.2F, 0.4F + this.rand.nextFloat() * 0.3F, this.rand.nextFloat() * 0.2F);
        }

        this.setBaseAirFriction(0.6F);
    }

    public static class Factory implements IParticleFactory
    {
        public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_)
        {
            return new ParticleTotem(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        }
    }
}
