package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;

public class LayerWolfCollar implements LayerRenderer<EntityWolf> {
   private static final ResourceLocation field_177147_a = new ResourceLocation("textures/entity/wolf/wolf_collar.png");
   private final RenderWolf field_177146_b;

   public LayerWolfCollar(RenderWolf p_i46104_1_) {
      this.field_177146_b = p_i46104_1_;
   }

   public void func_177141_a(EntityWolf p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
      if (p_177141_1_.func_70909_n() && !p_177141_1_.func_82150_aj()) {
         this.field_177146_b.func_110776_a(field_177147_a);
         float[] afloat = p_177141_1_.func_175546_cu().func_193349_f();
         GlStateManager.func_179124_c(afloat[0], afloat[1], afloat[2]);
         this.field_177146_b.func_177087_b().func_78088_a(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
      }
   }

   public boolean func_177142_b() {
      return true;
   }
}
