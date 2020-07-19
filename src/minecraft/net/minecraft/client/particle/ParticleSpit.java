package net.minecraft.client.particle;

import net.minecraft.world.World;

public class ParticleSpit extends ParticleExplosion
{
    protected ParticleSpit(World p_i47221_1_, double p_i47221_2_, double p_i47221_4_, double p_i47221_6_, double p_i47221_8_, double p_i47221_10_, double p_i47221_12_)
    {
        super(p_i47221_1_, p_i47221_2_, p_i47221_4_, p_i47221_6_, p_i47221_8_, p_i47221_10_, p_i47221_12_);
        this.particleGravity = 0.5F;
    }

    public void onUpdate()
    {
        super.onUpdate();
        this.motionY -= 0.004D + 0.04D * (double)this.particleGravity;
    }

    public static class Factory implements IParticleFactory
    {
        public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_)
        {
            return new ParticleSpit(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        }
    }
}
