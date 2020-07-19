package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelMinecart extends ModelBase
{
    public ModelRenderer[] sideModels = new ModelRenderer[7];

    public ModelMinecart()
    {
        this.sideModels[0] = new ModelRenderer(this, 0, 10);
        this.sideModels[1] = new ModelRenderer(this, 0, 0);
        this.sideModels[2] = new ModelRenderer(this, 0, 0);
        this.sideModels[3] = new ModelRenderer(this, 0, 0);
        this.sideModels[4] = new ModelRenderer(this, 0, 0);
        this.sideModels[5] = new ModelRenderer(this, 44, 10);
        int i = 20;
        int j = 8;
        int k = 16;
        int l = 4;
        this.sideModels[0].addBox(-10.0F, -8.0F, -1.0F, 20, 16, 2, 0.0F);
        this.sideModels[0].setRotationPoint(0.0F, 4.0F, 0.0F);
        this.sideModels[5].addBox(-9.0F, -7.0F, -1.0F, 18, 14, 1, 0.0F);
        this.sideModels[5].setRotationPoint(0.0F, 4.0F, 0.0F);
        this.sideModels[1].addBox(-8.0F, -9.0F, -1.0F, 16, 8, 2, 0.0F);
        this.sideModels[1].setRotationPoint(-9.0F, 4.0F, 0.0F);
        this.sideModels[2].addBox(-8.0F, -9.0F, -1.0F, 16, 8, 2, 0.0F);
        this.sideModels[2].setRotationPoint(9.0F, 4.0F, 0.0F);
        this.sideModels[3].addBox(-8.0F, -9.0F, -1.0F, 16, 8, 2, 0.0F);
        this.sideModels[3].setRotationPoint(0.0F, 4.0F, -7.0F);
        this.sideModels[4].addBox(-8.0F, -9.0F, -1.0F, 16, 8, 2, 0.0F);
        this.sideModels[4].setRotationPoint(0.0F, 4.0F, 7.0F);
        this.sideModels[0].rotateAngleX = ((float)Math.PI / 2F);
        this.sideModels[1].rotateAngleY = ((float)Math.PI * 3F / 2F);
        this.sideModels[2].rotateAngleY = ((float)Math.PI / 2F);
        this.sideModels[3].rotateAngleY = (float)Math.PI;
        this.sideModels[5].rotateAngleX = -((float)Math.PI / 2F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.sideModels[5].rotationPointY = 4.0F - ageInTicks;

        for (int i = 0; i < 6; ++i)
        {
            this.sideModels[i].render(scale);
        }
    }
}
