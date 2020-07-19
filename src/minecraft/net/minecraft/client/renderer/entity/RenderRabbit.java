package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelRabbit;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class RenderRabbit extends RenderLiving<EntityRabbit>
{
    private static final ResourceLocation BROWN = new ResourceLocation("textures/entity/rabbit/brown.png");
    private static final ResourceLocation WHITE = new ResourceLocation("textures/entity/rabbit/white.png");
    private static final ResourceLocation BLACK = new ResourceLocation("textures/entity/rabbit/black.png");
    private static final ResourceLocation GOLD = new ResourceLocation("textures/entity/rabbit/gold.png");
    private static final ResourceLocation SALT = new ResourceLocation("textures/entity/rabbit/salt.png");
    private static final ResourceLocation WHITE_SPLOTCHED = new ResourceLocation("textures/entity/rabbit/white_splotched.png");
    private static final ResourceLocation TOAST = new ResourceLocation("textures/entity/rabbit/toast.png");
    private static final ResourceLocation CAERBANNOG = new ResourceLocation("textures/entity/rabbit/caerbannog.png");

    public RenderRabbit(RenderManager p_i47196_1_)
    {
        super(p_i47196_1_, new ModelRabbit(), 0.3F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityRabbit entity)
    {
        String s = TextFormatting.getTextWithoutFormattingCodes(entity.getName());

        if (s != null && "Toast".equals(s))
        {
            return TOAST;
        }
        else
        {
            switch (entity.getRabbitType())
            {
                case 0:
                default:
                    return BROWN;

                case 1:
                    return WHITE;

                case 2:
                    return BLACK;

                case 3:
                    return WHITE_SPLOTCHED;

                case 4:
                    return GOLD;

                case 5:
                    return SALT;

                case 99:
                    return CAERBANNOG;
            }
        }
    }
}
