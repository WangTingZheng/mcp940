package net.minecraft.client.renderer.debug;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class DebugRendererSolidFace implements DebugRenderer.IDebugRenderer {
   private final Minecraft field_193851_a;

   public DebugRendererSolidFace(Minecraft p_i47478_1_) {
      this.field_193851_a = p_i47478_1_;
   }

   public void func_190060_a(float p_190060_1_, long p_190060_2_) {
      EntityPlayer entityplayer = this.field_193851_a.field_71439_g;
      double d0 = entityplayer.field_70142_S + (entityplayer.field_70165_t - entityplayer.field_70142_S) * (double)p_190060_1_;
      double d1 = entityplayer.field_70137_T + (entityplayer.field_70163_u - entityplayer.field_70137_T) * (double)p_190060_1_;
      double d2 = entityplayer.field_70136_U + (entityplayer.field_70161_v - entityplayer.field_70136_U) * (double)p_190060_1_;
      World world = this.field_193851_a.field_71439_g.field_70170_p;
      Iterable<BlockPos> iterable = BlockPos.func_191532_a(MathHelper.func_76128_c(entityplayer.field_70165_t - 6.0D), MathHelper.func_76128_c(entityplayer.field_70163_u - 6.0D), MathHelper.func_76128_c(entityplayer.field_70161_v - 6.0D), MathHelper.func_76128_c(entityplayer.field_70165_t + 6.0D), MathHelper.func_76128_c(entityplayer.field_70163_u + 6.0D), MathHelper.func_76128_c(entityplayer.field_70161_v + 6.0D));
      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      GlStateManager.func_187441_d(2.0F);
      GlStateManager.func_179090_x();
      GlStateManager.func_179132_a(false);

      for(BlockPos blockpos : iterable) {
         IBlockState iblockstate = world.func_180495_p(blockpos);
         if (iblockstate.func_177230_c() != Blocks.field_150350_a) {
            AxisAlignedBB axisalignedbb = iblockstate.func_185918_c(world, blockpos).func_186662_g(0.002D).func_72317_d(-d0, -d1, -d2);
            double d3 = axisalignedbb.field_72340_a;
            double d4 = axisalignedbb.field_72338_b;
            double d5 = axisalignedbb.field_72339_c;
            double d6 = axisalignedbb.field_72336_d;
            double d7 = axisalignedbb.field_72337_e;
            double d8 = axisalignedbb.field_72334_f;
            float f = 1.0F;
            float f1 = 0.0F;
            float f2 = 0.0F;
            float f3 = 0.5F;
            if (iblockstate.func_193401_d(world, blockpos, EnumFacing.WEST) == BlockFaceShape.SOLID) {
               Tessellator tessellator = Tessellator.func_178181_a();
               BufferBuilder bufferbuilder = tessellator.func_178180_c();
               bufferbuilder.func_181668_a(5, DefaultVertexFormats.field_181706_f);
               bufferbuilder.func_181662_b(d3, d4, d5).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               bufferbuilder.func_181662_b(d3, d4, d8).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               bufferbuilder.func_181662_b(d3, d7, d5).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               bufferbuilder.func_181662_b(d3, d7, d8).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               tessellator.func_78381_a();
            }

            if (iblockstate.func_193401_d(world, blockpos, EnumFacing.SOUTH) == BlockFaceShape.SOLID) {
               Tessellator tessellator1 = Tessellator.func_178181_a();
               BufferBuilder bufferbuilder1 = tessellator1.func_178180_c();
               bufferbuilder1.func_181668_a(5, DefaultVertexFormats.field_181706_f);
               bufferbuilder1.func_181662_b(d3, d7, d8).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               bufferbuilder1.func_181662_b(d3, d4, d8).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               bufferbuilder1.func_181662_b(d6, d7, d8).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               bufferbuilder1.func_181662_b(d6, d4, d8).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               tessellator1.func_78381_a();
            }

            if (iblockstate.func_193401_d(world, blockpos, EnumFacing.EAST) == BlockFaceShape.SOLID) {
               Tessellator tessellator2 = Tessellator.func_178181_a();
               BufferBuilder bufferbuilder2 = tessellator2.func_178180_c();
               bufferbuilder2.func_181668_a(5, DefaultVertexFormats.field_181706_f);
               bufferbuilder2.func_181662_b(d6, d4, d8).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               bufferbuilder2.func_181662_b(d6, d4, d5).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               bufferbuilder2.func_181662_b(d6, d7, d8).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               bufferbuilder2.func_181662_b(d6, d7, d5).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               tessellator2.func_78381_a();
            }

            if (iblockstate.func_193401_d(world, blockpos, EnumFacing.NORTH) == BlockFaceShape.SOLID) {
               Tessellator tessellator3 = Tessellator.func_178181_a();
               BufferBuilder bufferbuilder3 = tessellator3.func_178180_c();
               bufferbuilder3.func_181668_a(5, DefaultVertexFormats.field_181706_f);
               bufferbuilder3.func_181662_b(d6, d7, d5).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               bufferbuilder3.func_181662_b(d6, d4, d5).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               bufferbuilder3.func_181662_b(d3, d7, d5).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               bufferbuilder3.func_181662_b(d3, d4, d5).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               tessellator3.func_78381_a();
            }

            if (iblockstate.func_193401_d(world, blockpos, EnumFacing.DOWN) == BlockFaceShape.SOLID) {
               Tessellator tessellator4 = Tessellator.func_178181_a();
               BufferBuilder bufferbuilder4 = tessellator4.func_178180_c();
               bufferbuilder4.func_181668_a(5, DefaultVertexFormats.field_181706_f);
               bufferbuilder4.func_181662_b(d3, d4, d5).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               bufferbuilder4.func_181662_b(d6, d4, d5).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               bufferbuilder4.func_181662_b(d3, d4, d8).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               bufferbuilder4.func_181662_b(d6, d4, d8).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               tessellator4.func_78381_a();
            }

            if (iblockstate.func_193401_d(world, blockpos, EnumFacing.UP) == BlockFaceShape.SOLID) {
               Tessellator tessellator5 = Tessellator.func_178181_a();
               BufferBuilder bufferbuilder5 = tessellator5.func_178180_c();
               bufferbuilder5.func_181668_a(5, DefaultVertexFormats.field_181706_f);
               bufferbuilder5.func_181662_b(d3, d7, d5).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               bufferbuilder5.func_181662_b(d3, d7, d8).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               bufferbuilder5.func_181662_b(d6, d7, d5).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               bufferbuilder5.func_181662_b(d6, d7, d8).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
               tessellator5.func_78381_a();
            }
         }
      }

      GlStateManager.func_179132_a(true);
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }
}
