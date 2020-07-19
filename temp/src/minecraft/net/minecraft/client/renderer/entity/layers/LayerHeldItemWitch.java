package net.minecraft.client.renderer.entity.layers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderWitch;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;

public class LayerHeldItemWitch implements LayerRenderer<EntityWitch> {
   private final RenderWitch field_177144_a;

   public LayerHeldItemWitch(RenderWitch p_i46106_1_) {
      this.field_177144_a = p_i46106_1_;
   }

   public void func_177141_a(EntityWitch p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
      ItemStack itemstack = p_177141_1_.func_184614_ca();
      if (!itemstack.func_190926_b()) {
         GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
         GlStateManager.func_179094_E();
         if (this.field_177144_a.func_177087_b().field_78091_s) {
            GlStateManager.func_179109_b(0.0F, 0.625F, 0.0F);
            GlStateManager.func_179114_b(-20.0F, -1.0F, 0.0F, 0.0F);
            float f = 0.5F;
            GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
         }

         this.field_177144_a.func_177087_b().field_82898_f.func_78794_c(0.0625F);
         GlStateManager.func_179109_b(-0.0625F, 0.53125F, 0.21875F);
         Item item = itemstack.func_77973_b();
         Minecraft minecraft = Minecraft.func_71410_x();
         if (Block.func_149634_a(item).func_176223_P().func_185901_i() == EnumBlockRenderType.ENTITYBLOCK_ANIMATED) {
            GlStateManager.func_179109_b(0.0F, 0.0625F, -0.25F);
            GlStateManager.func_179114_b(30.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(-5.0F, 0.0F, 1.0F, 0.0F);
            float f1 = 0.375F;
            GlStateManager.func_179152_a(0.375F, -0.375F, 0.375F);
         } else if (item == Items.field_151031_f) {
            GlStateManager.func_179109_b(0.0F, 0.125F, -0.125F);
            GlStateManager.func_179114_b(-45.0F, 0.0F, 1.0F, 0.0F);
            float f2 = 0.625F;
            GlStateManager.func_179152_a(0.625F, -0.625F, 0.625F);
            GlStateManager.func_179114_b(-100.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(-20.0F, 0.0F, 1.0F, 0.0F);
         } else if (item.func_77662_d()) {
            if (item.func_77629_n_()) {
               GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
               GlStateManager.func_179109_b(0.0F, -0.0625F, 0.0F);
            }

            this.field_177144_a.func_82422_c();
            GlStateManager.func_179109_b(0.0625F, -0.125F, 0.0F);
            float f3 = 0.625F;
            GlStateManager.func_179152_a(0.625F, -0.625F, 0.625F);
            GlStateManager.func_179114_b(0.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(0.0F, 0.0F, 1.0F, 0.0F);
         } else {
            GlStateManager.func_179109_b(0.1875F, 0.1875F, 0.0F);
            float f4 = 0.875F;
            GlStateManager.func_179152_a(0.875F, 0.875F, 0.875F);
            GlStateManager.func_179114_b(-20.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.func_179114_b(-60.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(-30.0F, 0.0F, 0.0F, 1.0F);
         }

         GlStateManager.func_179114_b(-15.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179114_b(40.0F, 0.0F, 0.0F, 1.0F);
         minecraft.func_175597_ag().func_178099_a(p_177141_1_, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
         GlStateManager.func_179121_F();
      }
   }

   public boolean func_177142_b() {
      return false;
   }
}
