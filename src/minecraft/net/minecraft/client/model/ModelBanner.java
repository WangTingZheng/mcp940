package net.minecraft.client.model;

public class ModelBanner extends ModelBase
{
    public ModelRenderer bannerSlate;
    public ModelRenderer bannerStand;
    public ModelRenderer bannerTop;

    public ModelBanner()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.bannerSlate = new ModelRenderer(this, 0, 0);
        this.bannerSlate.addBox(-10.0F, 0.0F, -2.0F, 20, 40, 1, 0.0F);
        this.bannerStand = new ModelRenderer(this, 44, 0);
        this.bannerStand.addBox(-1.0F, -30.0F, -1.0F, 2, 42, 2, 0.0F);
        this.bannerTop = new ModelRenderer(this, 0, 42);
        this.bannerTop.addBox(-10.0F, -32.0F, -1.0F, 20, 2, 2, 0.0F);
    }

    /**
     * Renders the banner model in.
     */
    public void renderBanner()
    {
        this.bannerSlate.rotationPointY = -32.0F;
        this.bannerSlate.render(0.0625F);
        this.bannerStand.render(0.0625F);
        this.bannerTop.render(0.0625F);
    }
}
