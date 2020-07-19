package net.minecraft.client.renderer.tileentity;

import java.util.List;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class TileEntityBeaconRenderer extends TileEntitySpecialRenderer<TileEntityBeacon> {
   public static final ResourceLocation field_147523_b = new ResourceLocation("textures/entity/beacon_beam.png");

   public void func_192841_a(TileEntityBeacon p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
      this.func_188206_a(p_192841_2_, p_192841_4_, p_192841_6_, (double)p_192841_8_, (double)p_192841_1_.func_146002_i(), p_192841_1_.func_174907_n(), (double)p_192841_1_.func_145831_w().func_82737_E());
   }

   public void func_188206_a(double p_188206_1_, double p_188206_3_, double p_188206_5_, double p_188206_7_, double p_188206_9_, List<TileEntityBeacon.BeamSegment> p_188206_11_, double p_188206_12_) {
      GlStateManager.func_179092_a(516, 0.1F);
      this.func_147499_a(field_147523_b);
      if (p_188206_9_ > 0.0D) {
         GlStateManager.func_179106_n();
         int i = 0;

         for(int j = 0; j < p_188206_11_.size(); ++j) {
            TileEntityBeacon.BeamSegment tileentitybeacon$beamsegment = p_188206_11_.get(j);
            func_188204_a(p_188206_1_, p_188206_3_, p_188206_5_, p_188206_7_, p_188206_9_, p_188206_12_, i, tileentitybeacon$beamsegment.func_177264_c(), tileentitybeacon$beamsegment.func_177263_b());
            i += tileentitybeacon$beamsegment.func_177264_c();
         }

         GlStateManager.func_179127_m();
      }

   }

   public static void func_188204_a(double p_188204_0_, double p_188204_2_, double p_188204_4_, double p_188204_6_, double p_188204_8_, double p_188204_10_, int p_188204_12_, int p_188204_13_, float[] p_188204_14_) {
      func_188205_a(p_188204_0_, p_188204_2_, p_188204_4_, p_188204_6_, p_188204_8_, p_188204_10_, p_188204_12_, p_188204_13_, p_188204_14_, 0.2D, 0.25D);
   }

   public static void func_188205_a(double p_188205_0_, double p_188205_2_, double p_188205_4_, double p_188205_6_, double p_188205_8_, double p_188205_10_, int p_188205_12_, int p_188205_13_, float[] p_188205_14_, double p_188205_15_, double p_188205_17_) {
      int i = p_188205_12_ + p_188205_13_;
      GlStateManager.func_187421_b(3553, 10242, 10497);
      GlStateManager.func_187421_b(3553, 10243, 10497);
      GlStateManager.func_179140_f();
      GlStateManager.func_179129_p();
      GlStateManager.func_179084_k();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      double d0 = p_188205_10_ + p_188205_6_;
      double d1 = p_188205_13_ < 0 ? d0 : -d0;
      double d2 = MathHelper.func_181162_h(d1 * 0.2D - (double)MathHelper.func_76128_c(d1 * 0.1D));
      float f = p_188205_14_[0];
      float f1 = p_188205_14_[1];
      float f2 = p_188205_14_[2];
      double d3 = d0 * 0.025D * -1.5D;
      double d4 = 0.5D + Math.cos(d3 + 2.356194490192345D) * p_188205_15_;
      double d5 = 0.5D + Math.sin(d3 + 2.356194490192345D) * p_188205_15_;
      double d6 = 0.5D + Math.cos(d3 + 0.7853981633974483D) * p_188205_15_;
      double d7 = 0.5D + Math.sin(d3 + 0.7853981633974483D) * p_188205_15_;
      double d8 = 0.5D + Math.cos(d3 + 3.9269908169872414D) * p_188205_15_;
      double d9 = 0.5D + Math.sin(d3 + 3.9269908169872414D) * p_188205_15_;
      double d10 = 0.5D + Math.cos(d3 + 5.497787143782138D) * p_188205_15_;
      double d11 = 0.5D + Math.sin(d3 + 5.497787143782138D) * p_188205_15_;
      double d12 = 0.0D;
      double d13 = 1.0D;
      double d14 = -1.0D + d2;
      double d15 = (double)p_188205_13_ * p_188205_8_ * (0.5D / p_188205_15_) + d14;
      bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
      bufferbuilder.func_181662_b(p_188205_0_ + d4, p_188205_2_ + (double)i, p_188205_4_ + d5).func_187315_a(1.0D, d15).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d4, p_188205_2_ + (double)p_188205_12_, p_188205_4_ + d5).func_187315_a(1.0D, d14).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d6, p_188205_2_ + (double)p_188205_12_, p_188205_4_ + d7).func_187315_a(0.0D, d14).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d6, p_188205_2_ + (double)i, p_188205_4_ + d7).func_187315_a(0.0D, d15).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d10, p_188205_2_ + (double)i, p_188205_4_ + d11).func_187315_a(1.0D, d15).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d10, p_188205_2_ + (double)p_188205_12_, p_188205_4_ + d11).func_187315_a(1.0D, d14).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d8, p_188205_2_ + (double)p_188205_12_, p_188205_4_ + d9).func_187315_a(0.0D, d14).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d8, p_188205_2_ + (double)i, p_188205_4_ + d9).func_187315_a(0.0D, d15).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d6, p_188205_2_ + (double)i, p_188205_4_ + d7).func_187315_a(1.0D, d15).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d6, p_188205_2_ + (double)p_188205_12_, p_188205_4_ + d7).func_187315_a(1.0D, d14).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d10, p_188205_2_ + (double)p_188205_12_, p_188205_4_ + d11).func_187315_a(0.0D, d14).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d10, p_188205_2_ + (double)i, p_188205_4_ + d11).func_187315_a(0.0D, d15).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d8, p_188205_2_ + (double)i, p_188205_4_ + d9).func_187315_a(1.0D, d15).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d8, p_188205_2_ + (double)p_188205_12_, p_188205_4_ + d9).func_187315_a(1.0D, d14).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d4, p_188205_2_ + (double)p_188205_12_, p_188205_4_ + d5).func_187315_a(0.0D, d14).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d4, p_188205_2_ + (double)i, p_188205_4_ + d5).func_187315_a(0.0D, d15).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      GlStateManager.func_179132_a(false);
      d3 = 0.5D - p_188205_17_;
      d4 = 0.5D - p_188205_17_;
      d5 = 0.5D + p_188205_17_;
      d6 = 0.5D - p_188205_17_;
      d7 = 0.5D - p_188205_17_;
      d8 = 0.5D + p_188205_17_;
      d9 = 0.5D + p_188205_17_;
      d10 = 0.5D + p_188205_17_;
      d11 = 0.0D;
      d12 = 1.0D;
      d13 = -1.0D + d2;
      d14 = (double)p_188205_13_ * p_188205_8_ + d13;
      bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
      bufferbuilder.func_181662_b(p_188205_0_ + d3, p_188205_2_ + (double)i, p_188205_4_ + d4).func_187315_a(1.0D, d14).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d3, p_188205_2_ + (double)p_188205_12_, p_188205_4_ + d4).func_187315_a(1.0D, d13).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d5, p_188205_2_ + (double)p_188205_12_, p_188205_4_ + d6).func_187315_a(0.0D, d13).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d5, p_188205_2_ + (double)i, p_188205_4_ + d6).func_187315_a(0.0D, d14).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d9, p_188205_2_ + (double)i, p_188205_4_ + d10).func_187315_a(1.0D, d14).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d9, p_188205_2_ + (double)p_188205_12_, p_188205_4_ + d10).func_187315_a(1.0D, d13).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d7, p_188205_2_ + (double)p_188205_12_, p_188205_4_ + d8).func_187315_a(0.0D, d13).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d7, p_188205_2_ + (double)i, p_188205_4_ + d8).func_187315_a(0.0D, d14).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d5, p_188205_2_ + (double)i, p_188205_4_ + d6).func_187315_a(1.0D, d14).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d5, p_188205_2_ + (double)p_188205_12_, p_188205_4_ + d6).func_187315_a(1.0D, d13).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d9, p_188205_2_ + (double)p_188205_12_, p_188205_4_ + d10).func_187315_a(0.0D, d13).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d9, p_188205_2_ + (double)i, p_188205_4_ + d10).func_187315_a(0.0D, d14).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d7, p_188205_2_ + (double)i, p_188205_4_ + d8).func_187315_a(1.0D, d14).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d7, p_188205_2_ + (double)p_188205_12_, p_188205_4_ + d8).func_187315_a(1.0D, d13).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d3, p_188205_2_ + (double)p_188205_12_, p_188205_4_ + d4).func_187315_a(0.0D, d13).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
      bufferbuilder.func_181662_b(p_188205_0_ + d3, p_188205_2_ + (double)i, p_188205_4_ + d4).func_187315_a(0.0D, d14).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179145_e();
      GlStateManager.func_179098_w();
      GlStateManager.func_179132_a(true);
   }

   public boolean func_188185_a(TileEntityBeacon p_188185_1_) {
      return true;
   }
}
