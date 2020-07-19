package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class TileEntityEnchantmentTableRenderer extends TileEntitySpecialRenderer<TileEntityEnchantmentTable> {
   private static final ResourceLocation field_147540_b = new ResourceLocation("textures/entity/enchanting_table_book.png");
   private final ModelBook field_147541_c = new ModelBook();

   public void func_192841_a(TileEntityEnchantmentTable p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)p_192841_2_ + 0.5F, (float)p_192841_4_ + 0.75F, (float)p_192841_6_ + 0.5F);
      float f = (float)p_192841_1_.field_145926_a + p_192841_8_;
      GlStateManager.func_179109_b(0.0F, 0.1F + MathHelper.func_76126_a(f * 0.1F) * 0.01F, 0.0F);

      float f1;
      for(f1 = p_192841_1_.field_145928_o - p_192841_1_.field_145925_p; f1 >= 3.1415927F; f1 -= 6.2831855F) {
         ;
      }

      while(f1 < -3.1415927F) {
         f1 += 6.2831855F;
      }

      float f2 = p_192841_1_.field_145925_p + f1 * p_192841_8_;
      GlStateManager.func_179114_b(-f2 * 57.295776F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(80.0F, 0.0F, 0.0F, 1.0F);
      this.func_147499_a(field_147540_b);
      float f3 = p_192841_1_.field_145931_j + (p_192841_1_.field_145933_i - p_192841_1_.field_145931_j) * p_192841_8_ + 0.25F;
      float f4 = p_192841_1_.field_145931_j + (p_192841_1_.field_145933_i - p_192841_1_.field_145931_j) * p_192841_8_ + 0.75F;
      f3 = (f3 - (float)MathHelper.func_76140_b((double)f3)) * 1.6F - 0.3F;
      f4 = (f4 - (float)MathHelper.func_76140_b((double)f4)) * 1.6F - 0.3F;
      if (f3 < 0.0F) {
         f3 = 0.0F;
      }

      if (f4 < 0.0F) {
         f4 = 0.0F;
      }

      if (f3 > 1.0F) {
         f3 = 1.0F;
      }

      if (f4 > 1.0F) {
         f4 = 1.0F;
      }

      float f5 = p_192841_1_.field_145927_n + (p_192841_1_.field_145930_m - p_192841_1_.field_145927_n) * p_192841_8_;
      GlStateManager.func_179089_o();
      this.field_147541_c.func_78088_a((Entity)null, f, f3, f4, f5, 0.0F, 0.0625F);
      GlStateManager.func_179121_F();
   }
}
