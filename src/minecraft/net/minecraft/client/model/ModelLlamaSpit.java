package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelLlamaSpit extends ModelBase
{
    private final ModelRenderer main;

    public ModelLlamaSpit()
    {
        this(0.0F);
    }

    public ModelLlamaSpit(float p_i47225_1_)
    {
        this.main = new ModelRenderer(this);
        int i = 2;
        this.main.setTextureOffset(0, 0).addBox(-4.0F, 0.0F, 0.0F, 2, 2, 2, p_i47225_1_);
        this.main.setTextureOffset(0, 0).addBox(0.0F, -4.0F, 0.0F, 2, 2, 2, p_i47225_1_);
        this.main.setTextureOffset(0, 0).addBox(0.0F, 0.0F, -4.0F, 2, 2, 2, p_i47225_1_);
        this.main.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, p_i47225_1_);
        this.main.setTextureOffset(0, 0).addBox(2.0F, 0.0F, 0.0F, 2, 2, 2, p_i47225_1_);
        this.main.setTextureOffset(0, 0).addBox(0.0F, 2.0F, 0.0F, 2, 2, 2, p_i47225_1_);
        this.main.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 2.0F, 2, 2, 2, p_i47225_1_);
        this.main.setRotationPoint(0.0F, 0.0F, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        this.main.render(scale);
    }
}
