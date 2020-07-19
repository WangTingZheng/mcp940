package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.math.MathHelper;

public class ModelZombieVillager extends ModelBiped
{
    public ModelZombieVillager()
    {
        this(0.0F, 0.0F, false);
    }

    public ModelZombieVillager(float p_i1165_1_, float p_i1165_2_, boolean p_i1165_3_)
    {
        super(p_i1165_1_, 0.0F, 64, p_i1165_3_ ? 32 : 64);

        if (p_i1165_3_)
        {
            this.bipedHead = new ModelRenderer(this, 0, 0);
            this.bipedHead.addBox(-4.0F, -10.0F, -4.0F, 8, 8, 8, p_i1165_1_);
            this.bipedHead.setRotationPoint(0.0F, 0.0F + p_i1165_2_, 0.0F);
            this.bipedBody = new ModelRenderer(this, 16, 16);
            this.bipedBody.setRotationPoint(0.0F, 0.0F + p_i1165_2_, 0.0F);
            this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, p_i1165_1_ + 0.1F);
            this.bipedRightLeg = new ModelRenderer(this, 0, 16);
            this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F + p_i1165_2_, 0.0F);
            this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1165_1_ + 0.1F);
            this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
            this.bipedLeftLeg.mirror = true;
            this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F + p_i1165_2_, 0.0F);
            this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1165_1_ + 0.1F);
        }
        else
        {
            this.bipedHead = new ModelRenderer(this, 0, 0);
            this.bipedHead.setRotationPoint(0.0F, p_i1165_2_, 0.0F);
            this.bipedHead.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, p_i1165_1_);
            this.bipedHead.setTextureOffset(24, 0).addBox(-1.0F, -3.0F, -6.0F, 2, 4, 2, p_i1165_1_);
            this.bipedBody = new ModelRenderer(this, 16, 20);
            this.bipedBody.setRotationPoint(0.0F, 0.0F + p_i1165_2_, 0.0F);
            this.bipedBody.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, p_i1165_1_);
            this.bipedBody.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, p_i1165_1_ + 0.05F);
            this.bipedRightArm = new ModelRenderer(this, 44, 38);
            this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, p_i1165_1_);
            this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + p_i1165_2_, 0.0F);
            this.bipedLeftArm = new ModelRenderer(this, 44, 38);
            this.bipedLeftArm.mirror = true;
            this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i1165_1_);
            this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + p_i1165_2_, 0.0F);
            this.bipedRightLeg = new ModelRenderer(this, 0, 22);
            this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F + p_i1165_2_, 0.0F);
            this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1165_1_);
            this.bipedLeftLeg = new ModelRenderer(this, 0, 22);
            this.bipedLeftLeg.mirror = true;
            this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F + p_i1165_2_, 0.0F);
            this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1165_1_);
        }
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        EntityZombie entityzombie = (EntityZombie)entityIn;
        float f = MathHelper.sin(this.swingProgress * (float)Math.PI);
        float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float)Math.PI);
        this.bipedRightArm.rotateAngleZ = 0.0F;
        this.bipedLeftArm.rotateAngleZ = 0.0F;
        this.bipedRightArm.rotateAngleY = -(0.1F - f * 0.6F);
        this.bipedLeftArm.rotateAngleY = 0.1F - f * 0.6F;
        float f2 = -(float)Math.PI / (entityzombie.isArmsRaised() ? 1.5F : 2.25F);
        this.bipedRightArm.rotateAngleX = f2;
        this.bipedLeftArm.rotateAngleX = f2;
        this.bipedRightArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
        this.bipedLeftArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
        this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    }
}
