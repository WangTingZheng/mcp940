package net.minecraft.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

public class RenderFireball extends Render<EntityFireball> {
   private final float field_77002_a;

   public RenderFireball(RenderManager p_i46176_1_, float p_i46176_2_) {
      super(p_i46176_1_);
      this.field_77002_a = p_i46176_2_;
   }

   public void func_76986_a(EntityFireball p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      GlStateManager.func_179094_E();
      this.func_180548_c(p_76986_1_);
      GlStateManager.func_179109_b((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
      GlStateManager.func_179091_B();
      GlStateManager.func_179152_a(this.field_77002_a, this.field_77002_a, this.field_77002_a);
      TextureAtlasSprite textureatlassprite = Minecraft.func_71410_x().func_175599_af().func_175037_a().func_178082_a(Items.field_151059_bz);
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      float f = textureatlassprite.func_94209_e();
      float f1 = textureatlassprite.func_94212_f();
      float f2 = textureatlassprite.func_94206_g();
      float f3 = textureatlassprite.func_94210_h();
      float f4 = 1.0F;
      float f5 = 0.5F;
      float f6 = 0.25F;
      GlStateManager.func_179114_b(180.0F - this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b((float)(this.field_76990_c.field_78733_k.field_74320_O == 2 ? -1 : 1) * -this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F);
      if (this.field_188301_f) {
         GlStateManager.func_179142_g();
         GlStateManager.func_187431_e(this.func_188298_c(p_76986_1_));
      }

      bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181710_j);
      bufferbuilder.func_181662_b(-0.5D, -0.25D, 0.0D).func_187315_a((double)f, (double)f3).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      bufferbuilder.func_181662_b(0.5D, -0.25D, 0.0D).func_187315_a((double)f1, (double)f3).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      bufferbuilder.func_181662_b(0.5D, 0.75D, 0.0D).func_187315_a((double)f1, (double)f2).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      bufferbuilder.func_181662_b(-0.5D, 0.75D, 0.0D).func_187315_a((double)f, (double)f2).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      tessellator.func_78381_a();
      if (this.field_188301_f) {
         GlStateManager.func_187417_n();
         GlStateManager.func_179119_h();
      }

      GlStateManager.func_179101_C();
      GlStateManager.func_179121_F();
      super.func_76986_a(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   protected ResourceLocation func_110775_a(EntityFireball p_110775_1_) {
      return TextureMap.field_110575_b;
   }
}
