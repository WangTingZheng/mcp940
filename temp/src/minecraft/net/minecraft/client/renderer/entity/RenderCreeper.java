package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerCreeperCharge;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderCreeper extends RenderLiving<EntityCreeper> {
   private static final ResourceLocation field_110830_f = new ResourceLocation("textures/entity/creeper/creeper.png");

   public RenderCreeper(RenderManager p_i46186_1_) {
      super(p_i46186_1_, new ModelCreeper(), 0.5F);
      this.func_177094_a(new LayerCreeperCharge(this));
   }

   protected void func_77041_b(EntityCreeper p_77041_1_, float p_77041_2_) {
      float f = p_77041_1_.func_70831_j(p_77041_2_);
      float f1 = 1.0F + MathHelper.func_76126_a(f * 100.0F) * f * 0.01F;
      f = MathHelper.func_76131_a(f, 0.0F, 1.0F);
      f = f * f;
      f = f * f;
      float f2 = (1.0F + f * 0.4F) * f1;
      float f3 = (1.0F + f * 0.1F) / f1;
      GlStateManager.func_179152_a(f2, f3, f2);
   }

   protected int func_77030_a(EntityCreeper p_77030_1_, float p_77030_2_, float p_77030_3_) {
      float f = p_77030_1_.func_70831_j(p_77030_3_);
      if ((int)(f * 10.0F) % 2 == 0) {
         return 0;
      } else {
         int i = (int)(f * 0.2F * 255.0F);
         i = MathHelper.func_76125_a(i, 0, 255);
         return i << 24 | 822083583;
      }
   }

   protected ResourceLocation func_110775_a(EntityCreeper p_110775_1_) {
      return field_110830_f;
   }
}
