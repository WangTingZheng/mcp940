package net.minecraft.src;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

public class PlayerItemRenderer
{
    private int attachTo = 0;
    private ModelRenderer modelRenderer = null;

    public PlayerItemRenderer(int p_i71_1_, ModelRenderer p_i71_2_)
    {
        this.attachTo = p_i71_1_;
        this.modelRenderer = p_i71_2_;
    }

    public ModelRenderer getModelRenderer()
    {
        return this.modelRenderer;
    }

    public void render(ModelBiped p_render_1_, float p_render_2_)
    {
        ModelRenderer modelrenderer = PlayerItemModel.getAttachModel(p_render_1_, this.attachTo);

        if (modelrenderer != null)
        {
            modelrenderer.postRender(p_render_2_);
        }

        this.modelRenderer.render(p_render_2_);
    }
}
