package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelParrot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderParrot extends RenderLiving<EntityParrot> {
   public static final ResourceLocation[] field_192862_a = new ResourceLocation[]{new ResourceLocation("textures/entity/parrot/parrot_red_blue.png"), new ResourceLocation("textures/entity/parrot/parrot_blue.png"), new ResourceLocation("textures/entity/parrot/parrot_green.png"), new ResourceLocation("textures/entity/parrot/parrot_yellow_blue.png"), new ResourceLocation("textures/entity/parrot/parrot_grey.png")};

   public RenderParrot(RenderManager p_i47375_1_) {
      super(p_i47375_1_, new ModelParrot(), 0.3F);
   }

   protected ResourceLocation func_110775_a(EntityParrot p_110775_1_) {
      return field_192862_a[p_110775_1_.func_191998_ds()];
   }

   public float func_77044_a(EntityParrot p_77044_1_, float p_77044_2_) {
      return this.func_192861_b(p_77044_1_, p_77044_2_);
   }

   private float func_192861_b(EntityParrot p_192861_1_, float p_192861_2_) {
      float f = p_192861_1_.field_192011_bE + (p_192861_1_.field_192008_bB - p_192861_1_.field_192011_bE) * p_192861_2_;
      float f1 = p_192861_1_.field_192010_bD + (p_192861_1_.field_192009_bC - p_192861_1_.field_192010_bD) * p_192861_2_;
      return (MathHelper.func_76126_a(f) + 1.0F) * f1;
   }
}
