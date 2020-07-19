package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public abstract class TileEntitySpecialRenderer<T extends TileEntity> {
   protected static final ResourceLocation[] field_178460_a = new ResourceLocation[]{new ResourceLocation("textures/blocks/destroy_stage_0.png"), new ResourceLocation("textures/blocks/destroy_stage_1.png"), new ResourceLocation("textures/blocks/destroy_stage_2.png"), new ResourceLocation("textures/blocks/destroy_stage_3.png"), new ResourceLocation("textures/blocks/destroy_stage_4.png"), new ResourceLocation("textures/blocks/destroy_stage_5.png"), new ResourceLocation("textures/blocks/destroy_stage_6.png"), new ResourceLocation("textures/blocks/destroy_stage_7.png"), new ResourceLocation("textures/blocks/destroy_stage_8.png"), new ResourceLocation("textures/blocks/destroy_stage_9.png")};
   protected TileEntityRendererDispatcher field_147501_a;

   public void func_192841_a(T p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
      ITextComponent itextcomponent = p_192841_1_.func_145748_c_();
      if (itextcomponent != null && this.field_147501_a.field_190057_j != null && p_192841_1_.func_174877_v().equals(this.field_147501_a.field_190057_j.func_178782_a())) {
         this.func_190053_a(true);
         this.func_190052_a(p_192841_1_, itextcomponent.func_150254_d(), p_192841_2_, p_192841_4_, p_192841_6_, 12);
         this.func_190053_a(false);
      }

   }

   protected void func_190053_a(boolean p_190053_1_) {
      GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
      if (p_190053_1_) {
         GlStateManager.func_179090_x();
      } else {
         GlStateManager.func_179098_w();
      }

      GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
   }

   protected void func_147499_a(ResourceLocation p_147499_1_) {
      TextureManager texturemanager = this.field_147501_a.field_147553_e;
      if (texturemanager != null) {
         texturemanager.func_110577_a(p_147499_1_);
      }

   }

   protected World func_178459_a() {
      return this.field_147501_a.field_147550_f;
   }

   public void func_147497_a(TileEntityRendererDispatcher p_147497_1_) {
      this.field_147501_a = p_147497_1_;
   }

   public FontRenderer func_147498_b() {
      return this.field_147501_a.func_147548_a();
   }

   public boolean func_188185_a(T p_188185_1_) {
      return false;
   }

   protected void func_190052_a(T p_190052_1_, String p_190052_2_, double p_190052_3_, double p_190052_5_, double p_190052_7_, int p_190052_9_) {
      Entity entity = this.field_147501_a.field_147551_g;
      double d0 = p_190052_1_.func_145835_a(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
      if (d0 <= (double)(p_190052_9_ * p_190052_9_)) {
         float f = this.field_147501_a.field_147562_h;
         float f1 = this.field_147501_a.field_147563_i;
         boolean flag = false;
         EntityRenderer.func_189692_a(this.func_147498_b(), p_190052_2_, (float)p_190052_3_ + 0.5F, (float)p_190052_5_ + 1.5F, (float)p_190052_7_ + 0.5F, 0, f, f1, false, false);
      }
   }
}
