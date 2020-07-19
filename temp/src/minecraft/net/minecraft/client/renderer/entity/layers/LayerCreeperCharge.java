package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;

public class LayerCreeperCharge implements LayerRenderer<EntityCreeper> {
   private static final ResourceLocation field_177172_a = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
   private final RenderCreeper field_177170_b;
   private final ModelCreeper field_177171_c = new ModelCreeper(2.0F);

   public LayerCreeperCharge(RenderCreeper p_i46121_1_) {
      this.field_177170_b = p_i46121_1_;
   }

   public void func_177141_a(EntityCreeper p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
      if (p_177141_1_.func_70830_n()) {
         boolean flag = p_177141_1_.func_82150_aj();
         GlStateManager.func_179132_a(!flag);
         this.field_177170_b.func_110776_a(field_177172_a);
         GlStateManager.func_179128_n(5890);
         GlStateManager.func_179096_D();
         float f = (float)p_177141_1_.field_70173_aa + p_177141_4_;
         GlStateManager.func_179109_b(f * 0.01F, f * 0.01F, 0.0F);
         GlStateManager.func_179128_n(5888);
         GlStateManager.func_179147_l();
         float f1 = 0.5F;
         GlStateManager.func_179131_c(0.5F, 0.5F, 0.5F, 1.0F);
         GlStateManager.func_179140_f();
         GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
         this.field_177171_c.func_178686_a(this.field_177170_b.func_177087_b());
         Minecraft.func_71410_x().field_71460_t.func_191514_d(true);
         this.field_177171_c.func_78088_a(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
         Minecraft.func_71410_x().field_71460_t.func_191514_d(false);
         GlStateManager.func_179128_n(5890);
         GlStateManager.func_179096_D();
         GlStateManager.func_179128_n(5888);
         GlStateManager.func_179145_e();
         GlStateManager.func_179084_k();
         GlStateManager.func_179132_a(flag);
      }
   }

   public boolean func_177142_b() {
      return false;
   }
}
