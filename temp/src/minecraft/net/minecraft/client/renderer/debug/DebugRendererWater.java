package net.minecraft.client.renderer.debug;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DebugRendererWater implements DebugRenderer.IDebugRenderer {
   private final Minecraft field_188288_a;
   private EntityPlayer field_190062_b;
   private double field_190063_c;
   private double field_190064_d;
   private double field_190065_e;

   public DebugRendererWater(Minecraft p_i46555_1_) {
      this.field_188288_a = p_i46555_1_;
   }

   public void func_190060_a(float p_190060_1_, long p_190060_2_) {
      this.field_190062_b = this.field_188288_a.field_71439_g;
      this.field_190063_c = this.field_190062_b.field_70142_S + (this.field_190062_b.field_70165_t - this.field_190062_b.field_70142_S) * (double)p_190060_1_;
      this.field_190064_d = this.field_190062_b.field_70137_T + (this.field_190062_b.field_70163_u - this.field_190062_b.field_70137_T) * (double)p_190060_1_;
      this.field_190065_e = this.field_190062_b.field_70136_U + (this.field_190062_b.field_70161_v - this.field_190062_b.field_70136_U) * (double)p_190060_1_;
      BlockPos blockpos = this.field_188288_a.field_71439_g.func_180425_c();
      World world = this.field_188288_a.field_71439_g.field_70170_p;
      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      GlStateManager.func_179131_c(0.0F, 1.0F, 0.0F, 0.75F);
      GlStateManager.func_179090_x();
      GlStateManager.func_187441_d(6.0F);

      for(BlockPos blockpos1 : BlockPos.func_177980_a(blockpos.func_177982_a(-10, -10, -10), blockpos.func_177982_a(10, 10, 10))) {
         IBlockState iblockstate = world.func_180495_p(blockpos1);
         if (iblockstate.func_177230_c() == Blocks.field_150355_j || iblockstate.func_177230_c() == Blocks.field_150358_i) {
            double d0 = (double)BlockLiquid.func_190972_g(iblockstate, world, blockpos1);
            RenderGlobal.func_189696_b((new AxisAlignedBB((double)((float)blockpos1.func_177958_n() + 0.01F), (double)((float)blockpos1.func_177956_o() + 0.01F), (double)((float)blockpos1.func_177952_p() + 0.01F), (double)((float)blockpos1.func_177958_n() + 0.99F), d0, (double)((float)blockpos1.func_177952_p() + 0.99F))).func_72317_d(-this.field_190063_c, -this.field_190064_d, -this.field_190065_e), 1.0F, 1.0F, 1.0F, 0.2F);
         }
      }

      for(BlockPos blockpos2 : BlockPos.func_177980_a(blockpos.func_177982_a(-10, -10, -10), blockpos.func_177982_a(10, 10, 10))) {
         IBlockState iblockstate1 = world.func_180495_p(blockpos2);
         if (iblockstate1.func_177230_c() == Blocks.field_150355_j || iblockstate1.func_177230_c() == Blocks.field_150358_i) {
            Integer integer = (Integer)iblockstate1.func_177229_b(BlockLiquid.field_176367_b);
            double d1 = integer.intValue() > 7 ? 0.9D : 1.0D - 0.11D * (double)integer.intValue();
            String s = iblockstate1.func_177230_c() == Blocks.field_150358_i ? "f" : "s";
            DebugRenderer.func_190076_a(s + " " + integer, (double)blockpos2.func_177958_n() + 0.5D, (double)blockpos2.func_177956_o() + d1, (double)blockpos2.func_177952_p() + 0.5D, p_190060_1_, -16777216);
         }
      }

      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }
}
