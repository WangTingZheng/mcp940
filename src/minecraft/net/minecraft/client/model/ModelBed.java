package net.minecraft.client.model;

public class ModelBed extends ModelBase
{
    public ModelRenderer headPiece;
    public ModelRenderer footPiece;
    public ModelRenderer[] legs = new ModelRenderer[4];

    public ModelBed()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.headPiece = new ModelRenderer(this, 0, 0);
        this.headPiece.addBox(0.0F, 0.0F, 0.0F, 16, 16, 6, 0.0F);
        this.footPiece = new ModelRenderer(this, 0, 22);
        this.footPiece.addBox(0.0F, 0.0F, 0.0F, 16, 16, 6, 0.0F);
        this.legs[0] = new ModelRenderer(this, 50, 0);
        this.legs[1] = new ModelRenderer(this, 50, 6);
        this.legs[2] = new ModelRenderer(this, 50, 12);
        this.legs[3] = new ModelRenderer(this, 50, 18);
        this.legs[0].addBox(0.0F, 6.0F, -16.0F, 3, 3, 3);
        this.legs[1].addBox(0.0F, 6.0F, 0.0F, 3, 3, 3);
        this.legs[2].addBox(-16.0F, 6.0F, -16.0F, 3, 3, 3);
        this.legs[3].addBox(-16.0F, 6.0F, 0.0F, 3, 3, 3);
        this.legs[0].rotateAngleX = ((float)Math.PI / 2F);
        this.legs[1].rotateAngleX = ((float)Math.PI / 2F);
        this.legs[2].rotateAngleX = ((float)Math.PI / 2F);
        this.legs[3].rotateAngleX = ((float)Math.PI / 2F);
        this.legs[0].rotateAngleZ = 0.0F;
        this.legs[1].rotateAngleZ = ((float)Math.PI / 2F);
        this.legs[2].rotateAngleZ = ((float)Math.PI * 3F / 2F);
        this.legs[3].rotateAngleZ = (float)Math.PI;
    }

    public int getModelVersion()
    {
        return 51;
    }

    public void render()
    {
        this.headPiece.render(0.0625F);
        this.footPiece.render(0.0625F);
        this.legs[0].render(0.0625F);
        this.legs[1].render(0.0625F);
        this.legs[2].render(0.0625F);
        this.legs[3].render(0.0625F);
    }

    public void preparePiece(boolean p_193769_1_)
    {
        this.headPiece.showModel = p_193769_1_;
        this.footPiece.showModel = !p_193769_1_;
        this.legs[0].showModel = !p_193769_1_;
        this.legs[1].showModel = p_193769_1_;
        this.legs[2].showModel = !p_193769_1_;
        this.legs[3].showModel = p_193769_1_;
    }
}
