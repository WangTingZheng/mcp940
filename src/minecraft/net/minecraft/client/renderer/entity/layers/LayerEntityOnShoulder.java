package net.minecraft.client.renderer.entity.layers;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelParrot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderParrot;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class LayerEntityOnShoulder implements LayerRenderer<EntityPlayer>
{
    private final RenderManager renderManager;
    protected RenderLivingBase <? extends EntityLivingBase > leftRenderer;
    private ModelBase leftModel;
    private ResourceLocation leftResource;
    private UUID leftUniqueId;
    private Class<?> leftEntityClass;
    protected RenderLivingBase <? extends EntityLivingBase > rightRenderer;
    private ModelBase rightModel;
    private ResourceLocation rightResource;
    private UUID rightUniqueId;
    private Class<?> rightEntityClass;

    public LayerEntityOnShoulder(RenderManager p_i47370_1_)
    {
        this.renderManager = p_i47370_1_;
    }

    public void doRenderLayer(EntityPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (entitylivingbaseIn.getLeftShoulderEntity() != null || entitylivingbaseIn.getRightShoulderEntity() != null)
        {
            GlStateManager.enableRescaleNormal();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            NBTTagCompound nbttagcompound = entitylivingbaseIn.getLeftShoulderEntity();

            if (!nbttagcompound.hasNoTags())
            {
                LayerEntityOnShoulder.DataHolder layerentityonshoulder$dataholder = this.renderEntityOnShoulder(entitylivingbaseIn, this.leftUniqueId, nbttagcompound, this.leftRenderer, this.leftModel, this.leftResource, this.leftEntityClass, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, true);
                this.leftUniqueId = layerentityonshoulder$dataholder.entityId;
                this.leftRenderer = layerentityonshoulder$dataholder.renderer;
                this.leftResource = layerentityonshoulder$dataholder.textureLocation;
                this.leftModel = layerentityonshoulder$dataholder.model;
                this.leftEntityClass = layerentityonshoulder$dataholder.clazz;
            }

            NBTTagCompound nbttagcompound1 = entitylivingbaseIn.getRightShoulderEntity();

            if (!nbttagcompound1.hasNoTags())
            {
                LayerEntityOnShoulder.DataHolder layerentityonshoulder$dataholder1 = this.renderEntityOnShoulder(entitylivingbaseIn, this.rightUniqueId, nbttagcompound1, this.rightRenderer, this.rightModel, this.rightResource, this.rightEntityClass, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, false);
                this.rightUniqueId = layerentityonshoulder$dataholder1.entityId;
                this.rightRenderer = layerentityonshoulder$dataholder1.renderer;
                this.rightResource = layerentityonshoulder$dataholder1.textureLocation;
                this.rightModel = layerentityonshoulder$dataholder1.model;
                this.rightEntityClass = layerentityonshoulder$dataholder1.clazz;
            }

            GlStateManager.disableRescaleNormal();
        }
    }

    private LayerEntityOnShoulder.DataHolder renderEntityOnShoulder(EntityPlayer p_192864_1_, @Nullable UUID p_192864_2_, NBTTagCompound p_192864_3_, RenderLivingBase <? extends EntityLivingBase > p_192864_4_, ModelBase p_192864_5_, ResourceLocation p_192864_6_, Class<?> p_192864_7_, float p_192864_8_, float p_192864_9_, float p_192864_10_, float p_192864_11_, float p_192864_12_, float p_192864_13_, float p_192864_14_, boolean p_192864_15_)
    {
        if (p_192864_2_ == null || !p_192864_2_.equals(p_192864_3_.getUniqueId("UUID")))
        {
            p_192864_2_ = p_192864_3_.getUniqueId("UUID");
            p_192864_7_ = EntityList.getClassFromName(p_192864_3_.getString("id"));

            if (p_192864_7_ == EntityParrot.class)
            {
                p_192864_4_ = new RenderParrot(this.renderManager);
                p_192864_5_ = new ModelParrot();
                p_192864_6_ = RenderParrot.PARROT_TEXTURES[p_192864_3_.getInteger("Variant")];
            }
        }

        p_192864_4_.bindTexture(p_192864_6_);
        GlStateManager.pushMatrix();
        float f = p_192864_1_.isSneaking() ? -1.3F : -1.5F;
        float f1 = p_192864_15_ ? 0.4F : -0.4F;
        GlStateManager.translate(f1, f, 0.0F);

        if (p_192864_7_ == EntityParrot.class)
        {
            p_192864_11_ = 0.0F;
        }

        p_192864_5_.setLivingAnimations(p_192864_1_, p_192864_8_, p_192864_9_, p_192864_10_);
        p_192864_5_.setRotationAngles(p_192864_8_, p_192864_9_, p_192864_11_, p_192864_12_, p_192864_13_, p_192864_14_, p_192864_1_);
        p_192864_5_.render(p_192864_1_, p_192864_8_, p_192864_9_, p_192864_11_, p_192864_12_, p_192864_13_, p_192864_14_);
        GlStateManager.popMatrix();
        return new LayerEntityOnShoulder.DataHolder(p_192864_2_, p_192864_4_, p_192864_5_, p_192864_6_, p_192864_7_);
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }

    class DataHolder
    {
        public UUID entityId;
        public RenderLivingBase <? extends EntityLivingBase > renderer;
        public ModelBase model;
        public ResourceLocation textureLocation;
        public Class<?> clazz;

        public DataHolder(UUID p_i47463_2_, RenderLivingBase <? extends EntityLivingBase > p_i47463_3_, ModelBase p_i47463_4_, ResourceLocation p_i47463_5_, Class<?> p_i47463_6_)
        {
            this.entityId = p_i47463_2_;
            this.renderer = p_i47463_3_;
            this.model = p_i47463_4_;
            this.textureLocation = p_i47463_5_;
            this.clazz = p_i47463_6_;
        }
    }
}
