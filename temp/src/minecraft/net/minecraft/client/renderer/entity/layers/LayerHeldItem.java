package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;

public class LayerHeldItem implements LayerRenderer<EntityLivingBase> {
   protected final RenderLivingBase<?> field_177206_a;

   public LayerHeldItem(RenderLivingBase<?> p_i46115_1_) {
      this.field_177206_a = p_i46115_1_;
   }

   public void func_177141_a(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
      boolean flag = p_177141_1_.func_184591_cq() == EnumHandSide.RIGHT;
      ItemStack itemstack = flag ? p_177141_1_.func_184592_cb() : p_177141_1_.func_184614_ca();
      ItemStack itemstack1 = flag ? p_177141_1_.func_184614_ca() : p_177141_1_.func_184592_cb();
      if (!itemstack.func_190926_b() || !itemstack1.func_190926_b()) {
         GlStateManager.func_179094_E();
         if (this.field_177206_a.func_177087_b().field_78091_s) {
            float f = 0.5F;
            GlStateManager.func_179109_b(0.0F, 0.75F, 0.0F);
            GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
         }

         this.func_188358_a(p_177141_1_, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
         this.func_188358_a(p_177141_1_, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
         GlStateManager.func_179121_F();
      }
   }

   private void func_188358_a(EntityLivingBase p_188358_1_, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, EnumHandSide p_188358_4_) {
      if (!p_188358_2_.func_190926_b()) {
         GlStateManager.func_179094_E();
         this.func_191361_a(p_188358_4_);
         if (p_188358_1_.func_70093_af()) {
            GlStateManager.func_179109_b(0.0F, 0.2F, 0.0F);
         }

         GlStateManager.func_179114_b(-90.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
         boolean flag = p_188358_4_ == EnumHandSide.LEFT;
         GlStateManager.func_179109_b((float)(flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);
         Minecraft.func_71410_x().func_175597_ag().func_187462_a(p_188358_1_, p_188358_2_, p_188358_3_, flag);
         GlStateManager.func_179121_F();
      }
   }

   protected void func_191361_a(EnumHandSide p_191361_1_) {
      ((ModelBiped)this.field_177206_a.func_177087_b()).func_187073_a(0.0625F, p_191361_1_);
   }

   public boolean func_177142_b() {
      return false;
   }
}
