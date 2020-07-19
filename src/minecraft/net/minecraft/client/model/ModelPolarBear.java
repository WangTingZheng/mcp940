package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityPolarBear;

public class ModelPolarBear extends ModelQuadruped
{
    public ModelPolarBear()
    {
        super(12, 0.0F);
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-3.5F, -3.0F, -3.0F, 7, 7, 7, 0.0F);
        this.head.setRotationPoint(0.0F, 10.0F, -16.0F);
        this.head.setTextureOffset(0, 44).addBox(-2.5F, 1.0F, -6.0F, 5, 3, 3, 0.0F);
        this.head.setTextureOffset(26, 0).addBox(-4.5F, -4.0F, -1.0F, 2, 2, 1, 0.0F);
        ModelRenderer modelrenderer = this.head.setTextureOffset(26, 0);
        modelrenderer.mirror = true;
        modelrenderer.addBox(2.5F, -4.0F, -1.0F, 2, 2, 1, 0.0F);
        this.body = new ModelRenderer(this);
        this.body.setTextureOffset(0, 19).addBox(-5.0F, -13.0F, -7.0F, 14, 14, 11, 0.0F);
        this.body.setTextureOffset(39, 0).addBox(-4.0F, -25.0F, -7.0F, 12, 12, 10, 0.0F);
        this.body.setRotationPoint(-2.0F, 9.0F, 12.0F);
        int i = 10;
        this.leg1 = new ModelRenderer(this, 50, 22);
        this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 8, 0.0F);
        this.leg1.setRotationPoint(-3.5F, 14.0F, 6.0F);
        this.leg2 = new ModelRenderer(this, 50, 22);
        this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 8, 0.0F);
        this.leg2.setRotationPoint(3.5F, 14.0F, 6.0F);
        this.leg3 = new ModelRenderer(this, 50, 40);
        this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 6, 0.0F);
        this.leg3.setRotationPoint(-2.5F, 14.0F, -7.0F);
        this.leg4 = new ModelRenderer(this, 50, 40);
        this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 6, 0.0F);
        this.leg4.setRotationPoint(2.5F, 14.0F, -7.0F);
        --this.leg1.rotationPointX;
        ++this.leg2.rotationPointX;
        this.leg1.rotationPointZ += 0.0F;
        this.leg2.rotationPointZ += 0.0F;
        --this.leg3.rotationPointX;
        ++this.leg4.rotationPointX;
        --this.leg3.rotationPointZ;
        --this.leg4.rotationPointZ;
        this.childZOffset += 2.0F;
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

        if (this.isChild)
        {
            float f = 2.0F;
            this.childYOffset = 16.0F;
            this.childZOffset = 4.0F;
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.6666667F, 0.6666667F, 0.6666667F);
            GlStateManager.translate(0.0F, this.childYOffset * scale, this.childZOffset * scale);
            this.head.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
            this.body.render(scale);
            this.leg1.render(scale);
            this.leg2.render(scale);
            this.leg3.render(scale);
            this.leg4.render(scale);
            GlStateManager.popMatrix();
        }
        else
        {
            this.head.render(scale);
            this.body.render(scale);
            this.leg1.render(scale);
            this.leg2.render(scale);
            this.leg3.render(scale);
            this.leg4.render(scale);
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
        float f = ageInTicks - (float)entityIn.ticksExisted;
        float f1 = ((EntityPolarBear)entityIn).getStandingAnimationScale(f);
        f1 = f1 * f1;
        float f2 = 1.0F - f1;
        this.body.rotateAngleX = ((float)Math.PI / 2F) - f1 * (float)Math.PI * 0.35F;
        this.body.rotationPointY = 9.0F * f2 + 11.0F * f1;
        this.leg3.rotationPointY = 14.0F * f2 + -6.0F * f1;
        this.leg3.rotationPointZ = -8.0F * f2 + -4.0F * f1;
        this.leg3.rotateAngleX -= f1 * (float)Math.PI * 0.45F;
        this.leg4.rotationPointY = this.leg3.rotationPointY;
        this.leg4.rotationPointZ = this.leg3.rotationPointZ;
        this.leg4.rotateAngleX -= f1 * (float)Math.PI * 0.45F;
        this.head.rotationPointY = 10.0F * f2 + -12.0F * f1;
        this.head.rotationPointZ = -16.0F * f2 + -3.0F * f1;
        this.head.rotateAngleX += f1 * (float)Math.PI * 0.15F;
    }
}
