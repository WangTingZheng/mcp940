package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelVex;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.util.ResourceLocation;

public class RenderVex extends RenderBiped<EntityVex> {
   private static final ResourceLocation field_191343_a = new ResourceLocation("textures/entity/illager/vex.png");
   private static final ResourceLocation field_191344_j = new ResourceLocation("textures/entity/illager/vex_charging.png");
   private int field_191345_k;

   public RenderVex(RenderManager p_i47190_1_) {
      super(p_i47190_1_, new ModelVex(), 0.3F);
      this.field_191345_k = ((ModelVex)this.field_77045_g).func_191228_a();
   }

   protected ResourceLocation func_110775_a(EntityVex p_110775_1_) {
      return p_110775_1_.func_190647_dj() ? field_191344_j : field_191343_a;
   }

   public void func_76986_a(EntityVex p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      int i = ((ModelVex)this.field_77045_g).func_191228_a();
      if (i != this.field_191345_k) {
         this.field_77045_g = new ModelVex();
         this.field_191345_k = i;
      }

      super.func_76986_a(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   protected void func_77041_b(EntityVex p_77041_1_, float p_77041_2_) {
      GlStateManager.func_179152_a(0.4F, 0.4F, 0.4F);
   }
}
