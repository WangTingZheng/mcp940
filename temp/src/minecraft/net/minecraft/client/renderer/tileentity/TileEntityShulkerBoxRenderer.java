package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelShulker;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderShulker;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.EnumFacing;

public class TileEntityShulkerBoxRenderer extends TileEntitySpecialRenderer<TileEntityShulkerBox> {
   private final ModelShulker field_191285_a;

   public TileEntityShulkerBoxRenderer(ModelShulker p_i47216_1_) {
      this.field_191285_a = p_i47216_1_;
   }

   public void func_192841_a(TileEntityShulkerBox p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
      EnumFacing enumfacing = EnumFacing.UP;
      if (p_192841_1_.func_145830_o()) {
         IBlockState iblockstate = this.func_178459_a().func_180495_p(p_192841_1_.func_174877_v());
         if (iblockstate.func_177230_c() instanceof BlockShulkerBox) {
            enumfacing = (EnumFacing)iblockstate.func_177229_b(BlockShulkerBox.field_190957_a);
         }
      }

      GlStateManager.func_179126_j();
      GlStateManager.func_179143_c(515);
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179129_p();
      if (p_192841_9_ >= 0) {
         this.func_147499_a(field_178460_a[p_192841_9_]);
         GlStateManager.func_179128_n(5890);
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(4.0F, 4.0F, 1.0F);
         GlStateManager.func_179109_b(0.0625F, 0.0625F, 0.0625F);
         GlStateManager.func_179128_n(5888);
      } else {
         this.func_147499_a(RenderShulker.field_188342_a[p_192841_1_.func_190592_s().func_176765_a()]);
      }

      GlStateManager.func_179094_E();
      GlStateManager.func_179091_B();
      if (p_192841_9_ < 0) {
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, p_192841_10_);
      }

      GlStateManager.func_179109_b((float)p_192841_2_ + 0.5F, (float)p_192841_4_ + 1.5F, (float)p_192841_6_ + 0.5F);
      GlStateManager.func_179152_a(1.0F, -1.0F, -1.0F);
      GlStateManager.func_179109_b(0.0F, 1.0F, 0.0F);
      float f = 0.9995F;
      GlStateManager.func_179152_a(0.9995F, 0.9995F, 0.9995F);
      GlStateManager.func_179109_b(0.0F, -1.0F, 0.0F);
      switch(enumfacing) {
      case DOWN:
         GlStateManager.func_179109_b(0.0F, 2.0F, 0.0F);
         GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
      case UP:
      default:
         break;
      case NORTH:
         GlStateManager.func_179109_b(0.0F, 1.0F, 1.0F);
         GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
         break;
      case SOUTH:
         GlStateManager.func_179109_b(0.0F, 1.0F, -1.0F);
         GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
         break;
      case WEST:
         GlStateManager.func_179109_b(-1.0F, 1.0F, 0.0F);
         GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179114_b(-90.0F, 0.0F, 0.0F, 1.0F);
         break;
      case EAST:
         GlStateManager.func_179109_b(1.0F, 1.0F, 0.0F);
         GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179114_b(90.0F, 0.0F, 0.0F, 1.0F);
      }

      this.field_191285_a.field_187067_b.func_78785_a(0.0625F);
      GlStateManager.func_179109_b(0.0F, -p_192841_1_.func_190585_a(p_192841_8_) * 0.5F, 0.0F);
      GlStateManager.func_179114_b(270.0F * p_192841_1_.func_190585_a(p_192841_8_), 0.0F, 1.0F, 0.0F);
      this.field_191285_a.field_187068_c.func_78785_a(0.0625F);
      GlStateManager.func_179089_o();
      GlStateManager.func_179101_C();
      GlStateManager.func_179121_F();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      if (p_192841_9_ >= 0) {
         GlStateManager.func_179128_n(5890);
         GlStateManager.func_179121_F();
         GlStateManager.func_179128_n(5888);
      }

   }
}
