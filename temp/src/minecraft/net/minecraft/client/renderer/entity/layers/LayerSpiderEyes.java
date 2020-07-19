package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;

public class LayerSpiderEyes<T extends EntitySpider> implements LayerRenderer<T> {
   private static final ResourceLocation field_177150_a = new ResourceLocation("textures/entity/spider_eyes.png");
   private final RenderSpider<T> field_177149_b;

   public LayerSpiderEyes(RenderSpider<T> p_i46109_1_) {
      this.field_177149_b = p_i46109_1_;
   }

   public void func_177141_a(T p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
      this.field_177149_b.func_110776_a(field_177150_a);
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
      if (p_177141_1_.func_82150_aj()) {
         GlStateManager.func_179132_a(false);
      } else {
         GlStateManager.func_179132_a(true);
      }

      int i = 61680;
      int j = i % 65536;
      int k = i / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)j, (float)k);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      Minecraft.func_71410_x().field_71460_t.func_191514_d(true);
      this.field_177149_b.func_177087_b().func_78088_a(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
      Minecraft.func_71410_x().field_71460_t.func_191514_d(false);
      i = p_177141_1_.func_70070_b();
      j = i % 65536;
      k = i / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)j, (float)k);
      this.field_177149_b.func_177105_a(p_177141_1_);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
   }

   public boolean func_177142_b() {
      return false;
   }
}
