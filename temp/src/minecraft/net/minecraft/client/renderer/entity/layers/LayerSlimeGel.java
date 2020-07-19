package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.entity.monster.EntitySlime;

public class LayerSlimeGel implements LayerRenderer<EntitySlime> {
   private final RenderSlime field_177161_a;
   private final ModelBase field_177160_b = new ModelSlime(0);

   public LayerSlimeGel(RenderSlime p_i46111_1_) {
      this.field_177161_a = p_i46111_1_;
   }

   public void func_177141_a(EntitySlime p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
      if (!p_177141_1_.func_82150_aj()) {
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179108_z();
         GlStateManager.func_179147_l();
         GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
         this.field_177160_b.func_178686_a(this.field_177161_a.func_177087_b());
         this.field_177160_b.func_78088_a(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
         GlStateManager.func_179084_k();
         GlStateManager.func_179133_A();
      }
   }

   public boolean func_177142_b() {
      return true;
   }
}
