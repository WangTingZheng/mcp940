package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;

public class ChestRenderer {
   public void func_178175_a(Block p_178175_1_, float p_178175_2_) {
      GlStateManager.func_179131_c(p_178175_2_, p_178175_2_, p_178175_2_, 1.0F);
      GlStateManager.func_179114_b(90.0F, 0.0F, 1.0F, 0.0F);
      TileEntityItemStackRenderer.field_147719_a.func_179022_a(new ItemStack(p_178175_1_));
   }
}
