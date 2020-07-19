package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderSnowMan;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class LayerSnowmanHead implements LayerRenderer<EntitySnowman> {
   private final RenderSnowMan field_177152_a;

   public LayerSnowmanHead(RenderSnowMan p_i46110_1_) {
      this.field_177152_a = p_i46110_1_;
   }

   public void func_177141_a(EntitySnowman p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
      if (!p_177141_1_.func_82150_aj() && p_177141_1_.func_184748_o()) {
         GlStateManager.func_179094_E();
         this.field_177152_a.func_177087_b().field_78195_c.func_78794_c(0.0625F);
         float f = 0.625F;
         GlStateManager.func_179109_b(0.0F, -0.34375F, 0.0F);
         GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.func_179152_a(0.625F, -0.625F, -0.625F);
         Minecraft.func_71410_x().func_175597_ag().func_178099_a(p_177141_1_, new ItemStack(Blocks.field_150423_aK, 1), ItemCameraTransforms.TransformType.HEAD);
         GlStateManager.func_179121_F();
      }
   }

   public boolean func_177142_b() {
      return true;
   }
}
