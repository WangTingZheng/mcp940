package net.minecraft.entity;

import net.minecraft.util.math.MathHelper;

public class EntityBodyHelper
{
    /** Instance of EntityLiving. */
    private final EntityLivingBase living;

    /**
     * Used to progressively ajust the rotation of the body to the rotation of the head
     */
    private int rotationTickCounter;
    private float prevRenderYawHead;

    public EntityBodyHelper(EntityLivingBase livingIn)
    {
        this.living = livingIn;
    }

    /**
     * Update the Head and Body rendenring angles
     */
    public void updateRenderAngles()
    {
        double d0 = this.living.posX - this.living.prevPosX;
        double d1 = this.living.posZ - this.living.prevPosZ;

        if (d0 * d0 + d1 * d1 > 2.500000277905201E-7D)
        {
            this.living.renderYawOffset = this.living.rotationYaw;
            this.living.rotationYawHead = this.computeAngleWithBound(this.living.renderYawOffset, this.living.rotationYawHead, 75.0F);
            this.prevRenderYawHead = this.living.rotationYawHead;
            this.rotationTickCounter = 0;
        }
        else
        {
            if (this.living.getPassengers().isEmpty() || !(this.living.getPassengers().get(0) instanceof EntityLiving))
            {
                float f = 75.0F;

                if (Math.abs(this.living.rotationYawHead - this.prevRenderYawHead) > 15.0F)
                {
                    this.rotationTickCounter = 0;
                    this.prevRenderYawHead = this.living.rotationYawHead;
                }
                else
                {
                    ++this.rotationTickCounter;
                    int i = 10;

                    if (this.rotationTickCounter > 10)
                    {
                        f = Math.max(1.0F - (float)(this.rotationTickCounter - 10) / 10.0F, 0.0F) * 75.0F;
                    }
                }

                this.living.renderYawOffset = this.computeAngleWithBound(this.living.rotationYawHead, this.living.renderYawOffset, f);
            }
        }
    }

    /**
     * Return the new angle2 such that the difference between angle1 and angle2 is lower than angleMax. Args : angle1,
     * angle2, angleMax
     */
    private float computeAngleWithBound(float p_75665_1_, float p_75665_2_, float p_75665_3_)
    {
        float f = MathHelper.wrapDegrees(p_75665_1_ - p_75665_2_);

        if (f < -p_75665_3_)
        {
            f = -p_75665_3_;
        }

        if (f >= p_75665_3_)
        {
            f = p_75665_3_;
        }

        return p_75665_1_ - f;
    }
}
