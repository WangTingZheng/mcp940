package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderSnowball<T extends Entity> extends Render<T> {
   protected final Item field_177084_a;
   private final RenderItem field_177083_e;

   public RenderSnowball(RenderManager p_i46137_1_, Item p_i46137_2_, RenderItem p_i46137_3_) {
      super(p_i46137_1_);
      this.field_177084_a = p_i46137_2_;
      this.field_177083_e = p_i46137_3_;
   }

   public void func_76986_a(T p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
      GlStateManager.func_179091_B();
      GlStateManager.func_179114_b(-this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b((float)(this.field_76990_c.field_78733_k.field_74320_O == 2 ? -1 : 1) * this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
      this.func_110776_a(TextureMap.field_110575_b);
      if (this.field_188301_f) {
         GlStateManager.func_179142_g();
         GlStateManager.func_187431_e(this.func_188298_c(p_76986_1_));
      }

      this.field_177083_e.func_181564_a(this.func_177082_d(p_76986_1_), ItemCameraTransforms.TransformType.GROUND);
      if (this.field_188301_f) {
         GlStateManager.func_187417_n();
         GlStateManager.func_179119_h();
      }

      GlStateManager.func_179101_C();
      GlStateManager.func_179121_F();
      super.func_76986_a(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   public ItemStack func_177082_d(T p_177082_1_) {
      return new ItemStack(this.field_177084_a);
   }

   protected ResourceLocation func_110775_a(Entity p_110775_1_) {
      return TextureMap.field_110575_b;
   }
}
