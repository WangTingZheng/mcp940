package net.minecraft.src;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class PlayerItemModel
{
    private Dimension textureSize = null;
    private boolean usePlayerTexture = false;
    private PlayerItemRenderer[] modelRenderers = new PlayerItemRenderer[0];
    private ResourceLocation textureLocation = null;
    private BufferedImage textureImage = null;
    private DynamicTexture texture = null;
    private ResourceLocation locationMissing = new ResourceLocation("textures/blocks/wool_colored_red.png");
    public static final int ATTACH_BODY = 0;
    public static final int ATTACH_HEAD = 1;
    public static final int ATTACH_LEFT_ARM = 2;
    public static final int ATTACH_RIGHT_ARM = 3;
    public static final int ATTACH_LEFT_LEG = 4;
    public static final int ATTACH_RIGHT_LEG = 5;
    public static final int ATTACH_CAPE = 6;

    public PlayerItemModel(Dimension p_i70_1_, boolean p_i70_2_, PlayerItemRenderer[] p_i70_3_)
    {
        this.textureSize = p_i70_1_;
        this.usePlayerTexture = p_i70_2_;
        this.modelRenderers = p_i70_3_;
    }

    public void render(ModelBiped p_render_1_, AbstractClientPlayer p_render_2_, float p_render_3_, float p_render_4_)
    {
        TextureManager texturemanager = Config.getTextureManager();

        if (this.usePlayerTexture)
        {
            texturemanager.bindTexture(p_render_2_.getLocationSkin());
        }
        else if (this.textureLocation != null)
        {
            if (this.texture == null && this.textureImage != null)
            {
                this.texture = new DynamicTexture(this.textureImage);
                Minecraft.getMinecraft().getTextureManager().loadTexture(this.textureLocation, this.texture);
            }

            texturemanager.bindTexture(this.textureLocation);
        }
        else
        {
            texturemanager.bindTexture(this.locationMissing);
        }

        for (int i = 0; i < this.modelRenderers.length; ++i)
        {
            PlayerItemRenderer playeritemrenderer = this.modelRenderers[i];
            GlStateManager.pushMatrix();

            if (p_render_2_.isSneaking())
            {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }

            playeritemrenderer.render(p_render_1_, p_render_3_);
            GlStateManager.popMatrix();
        }
    }

    public static ModelRenderer getAttachModel(ModelBiped p_getAttachModel_0_, int p_getAttachModel_1_)
    {
        switch (p_getAttachModel_1_)
        {
            case 0:
                return p_getAttachModel_0_.bipedBody;

            case 1:
                return p_getAttachModel_0_.bipedHead;

            case 2:
                return p_getAttachModel_0_.bipedLeftArm;

            case 3:
                return p_getAttachModel_0_.bipedRightArm;

            case 4:
                return p_getAttachModel_0_.bipedLeftLeg;

            case 5:
                return p_getAttachModel_0_.bipedRightLeg;

            default:
                return null;
        }
    }

    public BufferedImage getTextureImage()
    {
        return this.textureImage;
    }

    public void setTextureImage(BufferedImage p_setTextureImage_1_)
    {
        this.textureImage = p_setTextureImage_1_;
    }

    public DynamicTexture getTexture()
    {
        return this.texture;
    }

    public ResourceLocation getTextureLocation()
    {
        return this.textureLocation;
    }

    public void setTextureLocation(ResourceLocation p_setTextureLocation_1_)
    {
        this.textureLocation = p_setTextureLocation_1_;
    }

    public boolean isUsePlayerTexture()
    {
        return this.usePlayerTexture;
    }
}
