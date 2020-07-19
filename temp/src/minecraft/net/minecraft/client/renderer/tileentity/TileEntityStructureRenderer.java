package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityStructureRenderer extends TileEntitySpecialRenderer<TileEntityStructure> {
   public void func_192841_a(TileEntityStructure p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
      if (Minecraft.func_71410_x().field_71439_g.func_189808_dh() || Minecraft.func_71410_x().field_71439_g.func_175149_v()) {
         super.func_192841_a(p_192841_1_, p_192841_2_, p_192841_4_, p_192841_6_, p_192841_8_, p_192841_9_, p_192841_10_);
         BlockPos blockpos = p_192841_1_.func_189711_e();
         BlockPos blockpos1 = p_192841_1_.func_189717_g();
         if (blockpos1.func_177958_n() >= 1 && blockpos1.func_177956_o() >= 1 && blockpos1.func_177952_p() >= 1) {
            if (p_192841_1_.func_189700_k() == TileEntityStructure.Mode.SAVE || p_192841_1_.func_189700_k() == TileEntityStructure.Mode.LOAD) {
               double d0 = 0.01D;
               double d1 = (double)blockpos.func_177958_n();
               double d2 = (double)blockpos.func_177952_p();
               double d6 = p_192841_4_ + (double)blockpos.func_177956_o() - 0.01D;
               double d9 = d6 + (double)blockpos1.func_177956_o() + 0.02D;
               double d3;
               double d4;
               switch(p_192841_1_.func_189716_h()) {
               case LEFT_RIGHT:
                  d3 = (double)blockpos1.func_177958_n() + 0.02D;
                  d4 = -((double)blockpos1.func_177952_p() + 0.02D);
                  break;
               case FRONT_BACK:
                  d3 = -((double)blockpos1.func_177958_n() + 0.02D);
                  d4 = (double)blockpos1.func_177952_p() + 0.02D;
                  break;
               default:
                  d3 = (double)blockpos1.func_177958_n() + 0.02D;
                  d4 = (double)blockpos1.func_177952_p() + 0.02D;
               }

               double d5;
               double d7;
               double d8;
               double d10;
               switch(p_192841_1_.func_189726_i()) {
               case CLOCKWISE_90:
                  d5 = p_192841_2_ + (d4 < 0.0D ? d1 - 0.01D : d1 + 1.0D + 0.01D);
                  d7 = p_192841_6_ + (d3 < 0.0D ? d2 + 1.0D + 0.01D : d2 - 0.01D);
                  d8 = d5 - d4;
                  d10 = d7 + d3;
                  break;
               case CLOCKWISE_180:
                  d5 = p_192841_2_ + (d3 < 0.0D ? d1 - 0.01D : d1 + 1.0D + 0.01D);
                  d7 = p_192841_6_ + (d4 < 0.0D ? d2 - 0.01D : d2 + 1.0D + 0.01D);
                  d8 = d5 - d3;
                  d10 = d7 - d4;
                  break;
               case COUNTERCLOCKWISE_90:
                  d5 = p_192841_2_ + (d4 < 0.0D ? d1 + 1.0D + 0.01D : d1 - 0.01D);
                  d7 = p_192841_6_ + (d3 < 0.0D ? d2 - 0.01D : d2 + 1.0D + 0.01D);
                  d8 = d5 + d4;
                  d10 = d7 - d3;
                  break;
               default:
                  d5 = p_192841_2_ + (d3 < 0.0D ? d1 + 1.0D + 0.01D : d1 - 0.01D);
                  d7 = p_192841_6_ + (d4 < 0.0D ? d2 + 1.0D + 0.01D : d2 - 0.01D);
                  d8 = d5 + d3;
                  d10 = d7 + d4;
               }

               int i = 255;
               int j = 223;
               int k = 127;
               Tessellator tessellator = Tessellator.func_178181_a();
               BufferBuilder bufferbuilder = tessellator.func_178180_c();
               GlStateManager.func_179106_n();
               GlStateManager.func_179140_f();
               GlStateManager.func_179090_x();
               GlStateManager.func_179147_l();
               GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
               this.func_190053_a(true);
               if (p_192841_1_.func_189700_k() == TileEntityStructure.Mode.SAVE || p_192841_1_.func_189721_I()) {
                  this.func_190055_a(tessellator, bufferbuilder, d5, d6, d7, d8, d9, d10, 255, 223, 127);
               }

               if (p_192841_1_.func_189700_k() == TileEntityStructure.Mode.SAVE && p_192841_1_.func_189707_H()) {
                  this.func_190054_a(p_192841_1_, p_192841_2_, p_192841_4_, p_192841_6_, blockpos, tessellator, bufferbuilder, true);
                  this.func_190054_a(p_192841_1_, p_192841_2_, p_192841_4_, p_192841_6_, blockpos, tessellator, bufferbuilder, false);
               }

               this.func_190053_a(false);
               GlStateManager.func_187441_d(1.0F);
               GlStateManager.func_179145_e();
               GlStateManager.func_179098_w();
               GlStateManager.func_179126_j();
               GlStateManager.func_179132_a(true);
               GlStateManager.func_179127_m();
            }
         }
      }
   }

   private void func_190054_a(TileEntityStructure p_190054_1_, double p_190054_2_, double p_190054_4_, double p_190054_6_, BlockPos p_190054_8_, Tessellator p_190054_9_, BufferBuilder p_190054_10_, boolean p_190054_11_) {
      GlStateManager.func_187441_d(p_190054_11_ ? 3.0F : 1.0F);
      p_190054_10_.func_181668_a(3, DefaultVertexFormats.field_181706_f);
      World world = p_190054_1_.func_145831_w();
      BlockPos blockpos = p_190054_1_.func_174877_v();
      BlockPos blockpos1 = blockpos.func_177971_a(p_190054_8_);

      for(BlockPos blockpos2 : BlockPos.func_177980_a(blockpos1, blockpos1.func_177971_a(p_190054_1_.func_189717_g()).func_177982_a(-1, -1, -1))) {
         IBlockState iblockstate = world.func_180495_p(blockpos2);
         boolean flag = iblockstate == Blocks.field_150350_a.func_176223_P();
         boolean flag1 = iblockstate == Blocks.field_189881_dj.func_176223_P();
         if (flag || flag1) {
            float f = flag ? 0.05F : 0.0F;
            double d0 = (double)((float)(blockpos2.func_177958_n() - blockpos.func_177958_n()) + 0.45F) + p_190054_2_ - (double)f;
            double d1 = (double)((float)(blockpos2.func_177956_o() - blockpos.func_177956_o()) + 0.45F) + p_190054_4_ - (double)f;
            double d2 = (double)((float)(blockpos2.func_177952_p() - blockpos.func_177952_p()) + 0.45F) + p_190054_6_ - (double)f;
            double d3 = (double)((float)(blockpos2.func_177958_n() - blockpos.func_177958_n()) + 0.55F) + p_190054_2_ + (double)f;
            double d4 = (double)((float)(blockpos2.func_177956_o() - blockpos.func_177956_o()) + 0.55F) + p_190054_4_ + (double)f;
            double d5 = (double)((float)(blockpos2.func_177952_p() - blockpos.func_177952_p()) + 0.55F) + p_190054_6_ + (double)f;
            if (p_190054_11_) {
               RenderGlobal.func_189698_a(p_190054_10_, d0, d1, d2, d3, d4, d5, 0.0F, 0.0F, 0.0F, 1.0F);
            } else if (flag) {
               RenderGlobal.func_189698_a(p_190054_10_, d0, d1, d2, d3, d4, d5, 0.5F, 0.5F, 1.0F, 1.0F);
            } else {
               RenderGlobal.func_189698_a(p_190054_10_, d0, d1, d2, d3, d4, d5, 1.0F, 0.25F, 0.25F, 1.0F);
            }
         }
      }

      p_190054_9_.func_78381_a();
   }

   private void func_190055_a(Tessellator p_190055_1_, BufferBuilder p_190055_2_, double p_190055_3_, double p_190055_5_, double p_190055_7_, double p_190055_9_, double p_190055_11_, double p_190055_13_, int p_190055_15_, int p_190055_16_, int p_190055_17_) {
      GlStateManager.func_187441_d(2.0F);
      p_190055_2_.func_181668_a(3, DefaultVertexFormats.field_181706_f);
      p_190055_2_.func_181662_b(p_190055_3_, p_190055_5_, p_190055_7_).func_181666_a((float)p_190055_16_, (float)p_190055_16_, (float)p_190055_16_, 0.0F).func_181675_d();
      p_190055_2_.func_181662_b(p_190055_3_, p_190055_5_, p_190055_7_).func_181669_b(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).func_181675_d();
      p_190055_2_.func_181662_b(p_190055_9_, p_190055_5_, p_190055_7_).func_181669_b(p_190055_16_, p_190055_17_, p_190055_17_, p_190055_15_).func_181675_d();
      p_190055_2_.func_181662_b(p_190055_9_, p_190055_5_, p_190055_13_).func_181669_b(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).func_181675_d();
      p_190055_2_.func_181662_b(p_190055_3_, p_190055_5_, p_190055_13_).func_181669_b(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).func_181675_d();
      p_190055_2_.func_181662_b(p_190055_3_, p_190055_5_, p_190055_7_).func_181669_b(p_190055_17_, p_190055_17_, p_190055_16_, p_190055_15_).func_181675_d();
      p_190055_2_.func_181662_b(p_190055_3_, p_190055_11_, p_190055_7_).func_181669_b(p_190055_17_, p_190055_16_, p_190055_17_, p_190055_15_).func_181675_d();
      p_190055_2_.func_181662_b(p_190055_9_, p_190055_11_, p_190055_7_).func_181669_b(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).func_181675_d();
      p_190055_2_.func_181662_b(p_190055_9_, p_190055_11_, p_190055_13_).func_181669_b(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).func_181675_d();
      p_190055_2_.func_181662_b(p_190055_3_, p_190055_11_, p_190055_13_).func_181669_b(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).func_181675_d();
      p_190055_2_.func_181662_b(p_190055_3_, p_190055_11_, p_190055_7_).func_181669_b(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).func_181675_d();
      p_190055_2_.func_181662_b(p_190055_3_, p_190055_11_, p_190055_13_).func_181669_b(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).func_181675_d();
      p_190055_2_.func_181662_b(p_190055_3_, p_190055_5_, p_190055_13_).func_181669_b(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).func_181675_d();
      p_190055_2_.func_181662_b(p_190055_9_, p_190055_5_, p_190055_13_).func_181669_b(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).func_181675_d();
      p_190055_2_.func_181662_b(p_190055_9_, p_190055_11_, p_190055_13_).func_181669_b(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).func_181675_d();
      p_190055_2_.func_181662_b(p_190055_9_, p_190055_11_, p_190055_7_).func_181669_b(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).func_181675_d();
      p_190055_2_.func_181662_b(p_190055_9_, p_190055_5_, p_190055_7_).func_181669_b(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).func_181675_d();
      p_190055_2_.func_181662_b(p_190055_9_, p_190055_5_, p_190055_7_).func_181666_a((float)p_190055_16_, (float)p_190055_16_, (float)p_190055_16_, 0.0F).func_181675_d();
      p_190055_1_.func_78381_a();
      GlStateManager.func_187441_d(1.0F);
   }

   public boolean func_188185_a(TileEntityStructure p_188185_1_) {
      return true;
   }
}
