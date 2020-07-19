package net.minecraft.client.particle;

import net.minecraft.world.World;

public class ParticleSimpleAnimated extends Particle
{
    /**
     * The base texture index. The texture index starts at this + (numAgingFrames - 1), and works its way down to this
     * number as the particle decays.
     */
    private final int textureIdx;

    /**
     * How many different textures there are to progress through as the particle decays
     */
    private final int numAgingFrames;

    /**
     * Added to the ySpeed every tick. Usually a small (thousandths), negative value.
     */
    private final float yAccel;
    private float baseAirFriction = 0.91F;

    /** The red value to drift toward */
    private float fadeTargetRed;

    /** The green value to drift toward */
    private float fadeTargetGreen;

    /** The blue value to drift toward */
    private float fadeTargetBlue;

    /** True if setColorFade has been called */
    private boolean fadingColor;

    public ParticleSimpleAnimated(World worldIn, double x, double y, double z, int textureIdxIn, int numFrames, float yAccelIn)
    {
        super(worldIn, x, y, z);
        this.textureIdx = textureIdxIn;
        this.numAgingFrames = numFrames;
        this.yAccel = yAccelIn;
    }

    public void setColor(int p_187146_1_)
    {
        float f = (float)((p_187146_1_ & 16711680) >> 16) / 255.0F;
        float f1 = (float)((p_187146_1_ & 65280) >> 8) / 255.0F;
        float f2 = (float)((p_187146_1_ & 255) >> 0) / 255.0F;
        float f3 = 1.0F;
        this.setRBGColorF(f * 1.0F, f1 * 1.0F, f2 * 1.0F);
    }

    /**
     * sets a color for the particle to drift toward (20% closer each tick, never actually getting very close)
     */
    public void setColorFade(int rgb)
    {
        this.fadeTargetRed = (float)((rgb & 16711680) >> 16) / 255.0F;
        this.fadeTargetGreen = (float)((rgb & 65280) >> 8) / 255.0F;
        this.fadeTargetBlue = (float)((rgb & 255) >> 0) / 255.0F;
        this.fadingColor = true;
    }

    public boolean shouldDisableDepth()
    {
        return true;
    }

    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setExpired();
        }

        if (this.particleAge > this.particleMaxAge / 2)
        {
            this.setAlphaF(1.0F - ((float)this.particleAge - (float)(this.particleMaxAge / 2)) / (float)this.particleMaxAge);

            if (this.fadingColor)
            {
                this.particleRed += (this.fadeTargetRed - this.particleRed) * 0.2F;
                this.particleGreen += (this.fadeTargetGreen - this.particleGreen) * 0.2F;
                this.particleBlue += (this.fadeTargetBlue - this.particleBlue) * 0.2F;
            }
        }

        this.setParticleTextureIndex(this.textureIdx + (this.numAgingFrames - 1 - this.particleAge * this.numAgingFrames / this.particleMaxAge));
        this.motionY += (double)this.yAccel;
        this.move(this.motionX, this.motionY, this.motionZ);
        this.motionX *= (double)this.baseAirFriction;
        this.motionY *= (double)this.baseAirFriction;
        this.motionZ *= (double)this.baseAirFriction;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
    }

    public int getBrightnessForRender(float p_189214_1_)
    {
        return 15728880;
    }

    protected void setBaseAirFriction(float p_191238_1_)
    {
        this.baseAirFriction = p_191238_1_;
    }
}
