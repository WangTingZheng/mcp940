package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelRabbit;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class RenderRabbit extends RenderLiving<EntityRabbit> {
   private static final ResourceLocation field_177127_a = new ResourceLocation("textures/entity/rabbit/brown.png");
   private static final ResourceLocation field_177126_e = new ResourceLocation("textures/entity/rabbit/white.png");
   private static final ResourceLocation field_177132_j = new ResourceLocation("textures/entity/rabbit/black.png");
   private static final ResourceLocation field_177133_k = new ResourceLocation("textures/entity/rabbit/gold.png");
   private static final ResourceLocation field_177130_l = new ResourceLocation("textures/entity/rabbit/salt.png");
   private static final ResourceLocation field_177131_m = new ResourceLocation("textures/entity/rabbit/white_splotched.png");
   private static final ResourceLocation field_177128_n = new ResourceLocation("textures/entity/rabbit/toast.png");
   private static final ResourceLocation field_177129_o = new ResourceLocation("textures/entity/rabbit/caerbannog.png");

   public RenderRabbit(RenderManager p_i47196_1_) {
      super(p_i47196_1_, new ModelRabbit(), 0.3F);
   }

   protected ResourceLocation func_110775_a(EntityRabbit p_110775_1_) {
      String s = TextFormatting.func_110646_a(p_110775_1_.func_70005_c_());
      if (s != null && "Toast".equals(s)) {
         return field_177128_n;
      } else {
         switch(p_110775_1_.func_175531_cl()) {
         case 0:
         default:
            return field_177127_a;
         case 1:
            return field_177126_e;
         case 2:
            return field_177132_j;
         case 3:
            return field_177131_m;
         case 4:
            return field_177133_k;
         case 5:
            return field_177130_l;
         case 99:
            return field_177129_o;
         }
      }
   }
}
