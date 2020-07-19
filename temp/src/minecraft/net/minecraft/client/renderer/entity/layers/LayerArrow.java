package net.minecraft.client.renderer.entity.layers;

import java.util.Random;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.util.math.MathHelper;

public class LayerArrow implements LayerRenderer<EntityLivingBase> {
   private final RenderLivingBase<?> field_177168_a;

   public LayerArrow(RenderLivingBase<?> p_i46124_1_) {
      this.field_177168_a = p_i46124_1_;
   }

   public void func_177141_a(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
      int i = p_177141_1_.func_85035_bI();
      if (i > 0) {
         Entity entity = new EntityTippedArrow(p_177141_1_.field_70170_p, p_177141_1_.field_70165_t, p_177141_1_.field_70163_u, p_177141_1_.field_70161_v);
         Random random = new Random((long)p_177141_1_.func_145782_y());
         RenderHelper.func_74518_a();

         for(int j = 0; j < i; ++j) {
            GlStateManager.func_179094_E();
            ModelRenderer modelrenderer = this.field_177168_a.func_177087_b().func_85181_a(random);
            ModelBox modelbox = modelrenderer.field_78804_l.get(random.nextInt(modelrenderer.field_78804_l.size()));
            modelrenderer.func_78794_c(0.0625F);
            float f = random.nextFloat();
            float f1 = random.nextFloat();
            float f2 = random.nextFloat();
            float f3 = (modelbox.field_78252_a + (modelbox.field_78248_d - modelbox.field_78252_a) * f) / 16.0F;
            float f4 = (modelbox.field_78250_b + (modelbox.field_78249_e - modelbox.field_78250_b) * f1) / 16.0F;
            float f5 = (modelbox.field_78251_c + (modelbox.field_78246_f - modelbox.field_78251_c) * f2) / 16.0F;
            GlStateManager.func_179109_b(f3, f4, f5);
            f = f * 2.0F - 1.0F;
            f1 = f1 * 2.0F - 1.0F;
            f2 = f2 * 2.0F - 1.0F;
            f = f * -1.0F;
            f1 = f1 * -1.0F;
            f2 = f2 * -1.0F;
            float f6 = MathHelper.func_76129_c(f * f + f2 * f2);
            entity.field_70177_z = (float)(Math.atan2((double)f, (double)f2) * 57.2957763671875D);
            entity.field_70125_A = (float)(Math.atan2((double)f1, (double)f6) * 57.2957763671875D);
            entity.field_70126_B = entity.field_70177_z;
            entity.field_70127_C = entity.field_70125_A;
            double d0 = 0.0D;
            double d1 = 0.0D;
            double d2 = 0.0D;
            this.field_177168_a.func_177068_d().func_188391_a(entity, 0.0D, 0.0D, 0.0D, 0.0F, p_177141_4_, false);
            GlStateManager.func_179121_F();
         }

         RenderHelper.func_74519_b();
      }
   }

   public boolean func_177142_b() {
      return false;
   }
}
