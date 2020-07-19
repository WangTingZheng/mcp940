package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelEvokerFangs extends ModelBase
{
    private final ModelRenderer base = new ModelRenderer(this, 0, 0);
    private final ModelRenderer upperJaw;
    private final ModelRenderer lowerJaw;

    public ModelEvokerFangs()
    {
        this.base.setRotationPoint(-5.0F, 22.0F, -5.0F);
        this.base.addBox(0.0F, 0.0F, 0.0F, 10, 12, 10);
        this.upperJaw = new ModelRenderer(this, 40, 0);
        this.upperJaw.setRotationPoint(1.5F, 22.0F, -4.0F);
        this.upperJaw.addBox(0.0F, 0.0F, 0.0F, 4, 14, 8);
        this.lowerJaw = new ModelRenderer(this, 40, 0);
        this.lowerJaw.setRotationPoint(-1.5F, 22.0F, 4.0F);
        this.lowerJaw.addBox(0.0F, 0.0F, 0.0F, 4, 14, 8);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        float f = limbSwing * 2.0F;

        if (f > 1.0F)
        {
            f = 1.0F;
        }

        f = 1.0F - f * f * f;
        this.upperJaw.rotateAngleZ = (float)Math.PI - f * 0.35F * (float)Math.PI;
        this.lowerJaw.rotateAngleZ = (float)Math.PI + f * 0.35F * (float)Math.PI;
        this.lowerJaw.rotateAngleY = (float)Math.PI;
        float f1 = (limbSwing + MathHelper.sin(limbSwing * 2.7F)) * 0.6F * 12.0F;
        this.upperJaw.rotationPointY = 24.0F - f1;
        this.lowerJaw.rotationPointY = this.upperJaw.rotationPointY;
        this.base.rotationPointY = this.upperJaw.rotationPointY;
        this.base.render(scale);
        this.upperJaw.render(scale);
        this.lowerJaw.render(scale);
    }
}
