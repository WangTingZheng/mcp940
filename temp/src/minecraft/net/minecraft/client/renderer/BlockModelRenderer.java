package net.minecraft.client.renderer;

import java.util.BitSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;

public class BlockModelRenderer {
   private final BlockColors field_187499_a;

   public BlockModelRenderer(BlockColors p_i46575_1_) {
      this.field_187499_a = p_i46575_1_;
   }

   public boolean func_178267_a(IBlockAccess p_178267_1_, IBakedModel p_178267_2_, IBlockState p_178267_3_, BlockPos p_178267_4_, BufferBuilder p_178267_5_, boolean p_178267_6_) {
      return this.func_187493_a(p_178267_1_, p_178267_2_, p_178267_3_, p_178267_4_, p_178267_5_, p_178267_6_, MathHelper.func_180186_a(p_178267_4_));
   }

   public boolean func_187493_a(IBlockAccess p_187493_1_, IBakedModel p_187493_2_, IBlockState p_187493_3_, BlockPos p_187493_4_, BufferBuilder p_187493_5_, boolean p_187493_6_, long p_187493_7_) {
      boolean flag = Minecraft.func_71379_u() && p_187493_3_.func_185906_d() == 0 && p_187493_2_.func_177555_b();

      try {
         return flag ? this.func_187498_b(p_187493_1_, p_187493_2_, p_187493_3_, p_187493_4_, p_187493_5_, p_187493_6_, p_187493_7_) : this.func_187497_c(p_187493_1_, p_187493_2_, p_187493_3_, p_187493_4_, p_187493_5_, p_187493_6_, p_187493_7_);
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.func_85055_a(throwable, "Tesselating block model");
         CrashReportCategory crashreportcategory = crashreport.func_85058_a("Block model being tesselated");
         CrashReportCategory.func_175750_a(crashreportcategory, p_187493_4_, p_187493_3_);
         crashreportcategory.func_71507_a("Using AO", Boolean.valueOf(flag));
         throw new ReportedException(crashreport);
      }
   }

   public boolean func_187498_b(IBlockAccess p_187498_1_, IBakedModel p_187498_2_, IBlockState p_187498_3_, BlockPos p_187498_4_, BufferBuilder p_187498_5_, boolean p_187498_6_, long p_187498_7_) {
      boolean flag = false;
      float[] afloat = new float[EnumFacing.values().length * 2];
      BitSet bitset = new BitSet(3);
      BlockModelRenderer.AmbientOcclusionFace blockmodelrenderer$ambientocclusionface = new BlockModelRenderer.AmbientOcclusionFace();

      for(EnumFacing enumfacing : EnumFacing.values()) {
         List<BakedQuad> list = p_187498_2_.func_188616_a(p_187498_3_, enumfacing, p_187498_7_);
         if (!list.isEmpty() && (!p_187498_6_ || p_187498_3_.func_185894_c(p_187498_1_, p_187498_4_, enumfacing))) {
            this.func_187492_a(p_187498_1_, p_187498_3_, p_187498_4_, p_187498_5_, list, afloat, bitset, blockmodelrenderer$ambientocclusionface);
            flag = true;
         }
      }

      List<BakedQuad> list1 = p_187498_2_.func_188616_a(p_187498_3_, (EnumFacing)null, p_187498_7_);
      if (!list1.isEmpty()) {
         this.func_187492_a(p_187498_1_, p_187498_3_, p_187498_4_, p_187498_5_, list1, afloat, bitset, blockmodelrenderer$ambientocclusionface);
         flag = true;
      }

      return flag;
   }

   public boolean func_187497_c(IBlockAccess p_187497_1_, IBakedModel p_187497_2_, IBlockState p_187497_3_, BlockPos p_187497_4_, BufferBuilder p_187497_5_, boolean p_187497_6_, long p_187497_7_) {
      boolean flag = false;
      BitSet bitset = new BitSet(3);

      for(EnumFacing enumfacing : EnumFacing.values()) {
         List<BakedQuad> list = p_187497_2_.func_188616_a(p_187497_3_, enumfacing, p_187497_7_);
         if (!list.isEmpty() && (!p_187497_6_ || p_187497_3_.func_185894_c(p_187497_1_, p_187497_4_, enumfacing))) {
            int i = p_187497_3_.func_185889_a(p_187497_1_, p_187497_4_.func_177972_a(enumfacing));
            this.func_187496_a(p_187497_1_, p_187497_3_, p_187497_4_, i, false, p_187497_5_, list, bitset);
            flag = true;
         }
      }

      List<BakedQuad> list1 = p_187497_2_.func_188616_a(p_187497_3_, (EnumFacing)null, p_187497_7_);
      if (!list1.isEmpty()) {
         this.func_187496_a(p_187497_1_, p_187497_3_, p_187497_4_, -1, true, p_187497_5_, list1, bitset);
         flag = true;
      }

      return flag;
   }

   private void func_187492_a(IBlockAccess p_187492_1_, IBlockState p_187492_2_, BlockPos p_187492_3_, BufferBuilder p_187492_4_, List<BakedQuad> p_187492_5_, float[] p_187492_6_, BitSet p_187492_7_, BlockModelRenderer.AmbientOcclusionFace p_187492_8_) {
      Vec3d vec3d = p_187492_2_.func_191059_e(p_187492_1_, p_187492_3_);
      double d0 = (double)p_187492_3_.func_177958_n() + vec3d.field_72450_a;
      double d1 = (double)p_187492_3_.func_177956_o() + vec3d.field_72448_b;
      double d2 = (double)p_187492_3_.func_177952_p() + vec3d.field_72449_c;
      int i = 0;

      for(int j = p_187492_5_.size(); i < j; ++i) {
         BakedQuad bakedquad = p_187492_5_.get(i);
         this.func_187494_a(p_187492_2_, bakedquad.func_178209_a(), bakedquad.func_178210_d(), p_187492_6_, p_187492_7_);
         p_187492_8_.func_187491_a(p_187492_1_, p_187492_2_, p_187492_3_, bakedquad.func_178210_d(), p_187492_6_, p_187492_7_);
         p_187492_4_.func_178981_a(bakedquad.func_178209_a());
         p_187492_4_.func_178962_a(p_187492_8_.field_178207_c[0], p_187492_8_.field_178207_c[1], p_187492_8_.field_178207_c[2], p_187492_8_.field_178207_c[3]);
         if (bakedquad.func_178212_b()) {
            int k = this.field_187499_a.func_186724_a(p_187492_2_, p_187492_1_, p_187492_3_, bakedquad.func_178211_c());
            if (EntityRenderer.field_78517_a) {
               k = TextureUtil.func_177054_c(k);
            }

            float f = (float)(k >> 16 & 255) / 255.0F;
            float f1 = (float)(k >> 8 & 255) / 255.0F;
            float f2 = (float)(k & 255) / 255.0F;
            p_187492_4_.func_178978_a(p_187492_8_.field_178206_b[0] * f, p_187492_8_.field_178206_b[0] * f1, p_187492_8_.field_178206_b[0] * f2, 4);
            p_187492_4_.func_178978_a(p_187492_8_.field_178206_b[1] * f, p_187492_8_.field_178206_b[1] * f1, p_187492_8_.field_178206_b[1] * f2, 3);
            p_187492_4_.func_178978_a(p_187492_8_.field_178206_b[2] * f, p_187492_8_.field_178206_b[2] * f1, p_187492_8_.field_178206_b[2] * f2, 2);
            p_187492_4_.func_178978_a(p_187492_8_.field_178206_b[3] * f, p_187492_8_.field_178206_b[3] * f1, p_187492_8_.field_178206_b[3] * f2, 1);
         } else {
            p_187492_4_.func_178978_a(p_187492_8_.field_178206_b[0], p_187492_8_.field_178206_b[0], p_187492_8_.field_178206_b[0], 4);
            p_187492_4_.func_178978_a(p_187492_8_.field_178206_b[1], p_187492_8_.field_178206_b[1], p_187492_8_.field_178206_b[1], 3);
            p_187492_4_.func_178978_a(p_187492_8_.field_178206_b[2], p_187492_8_.field_178206_b[2], p_187492_8_.field_178206_b[2], 2);
            p_187492_4_.func_178978_a(p_187492_8_.field_178206_b[3], p_187492_8_.field_178206_b[3], p_187492_8_.field_178206_b[3], 1);
         }

         p_187492_4_.func_178987_a(d0, d1, d2);
      }

   }

   private void func_187494_a(IBlockState p_187494_1_, int[] p_187494_2_, EnumFacing p_187494_3_, @Nullable float[] p_187494_4_, BitSet p_187494_5_) {
      float f = 32.0F;
      float f1 = 32.0F;
      float f2 = 32.0F;
      float f3 = -32.0F;
      float f4 = -32.0F;
      float f5 = -32.0F;

      for(int i = 0; i < 4; ++i) {
         float f6 = Float.intBitsToFloat(p_187494_2_[i * 7]);
         float f7 = Float.intBitsToFloat(p_187494_2_[i * 7 + 1]);
         float f8 = Float.intBitsToFloat(p_187494_2_[i * 7 + 2]);
         f = Math.min(f, f6);
         f1 = Math.min(f1, f7);
         f2 = Math.min(f2, f8);
         f3 = Math.max(f3, f6);
         f4 = Math.max(f4, f7);
         f5 = Math.max(f5, f8);
      }

      if (p_187494_4_ != null) {
         p_187494_4_[EnumFacing.WEST.func_176745_a()] = f;
         p_187494_4_[EnumFacing.EAST.func_176745_a()] = f3;
         p_187494_4_[EnumFacing.DOWN.func_176745_a()] = f1;
         p_187494_4_[EnumFacing.UP.func_176745_a()] = f4;
         p_187494_4_[EnumFacing.NORTH.func_176745_a()] = f2;
         p_187494_4_[EnumFacing.SOUTH.func_176745_a()] = f5;
         int j = EnumFacing.values().length;
         p_187494_4_[EnumFacing.WEST.func_176745_a() + j] = 1.0F - f;
         p_187494_4_[EnumFacing.EAST.func_176745_a() + j] = 1.0F - f3;
         p_187494_4_[EnumFacing.DOWN.func_176745_a() + j] = 1.0F - f1;
         p_187494_4_[EnumFacing.UP.func_176745_a() + j] = 1.0F - f4;
         p_187494_4_[EnumFacing.NORTH.func_176745_a() + j] = 1.0F - f2;
         p_187494_4_[EnumFacing.SOUTH.func_176745_a() + j] = 1.0F - f5;
      }

      float f9 = 1.0E-4F;
      float f10 = 0.9999F;
      switch(p_187494_3_) {
      case DOWN:
         p_187494_5_.set(1, f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F);
         p_187494_5_.set(0, (f1 < 1.0E-4F || p_187494_1_.func_185917_h()) && f1 == f4);
         break;
      case UP:
         p_187494_5_.set(1, f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F);
         p_187494_5_.set(0, (f4 > 0.9999F || p_187494_1_.func_185917_h()) && f1 == f4);
         break;
      case NORTH:
         p_187494_5_.set(1, f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F);
         p_187494_5_.set(0, (f2 < 1.0E-4F || p_187494_1_.func_185917_h()) && f2 == f5);
         break;
      case SOUTH:
         p_187494_5_.set(1, f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F);
         p_187494_5_.set(0, (f5 > 0.9999F || p_187494_1_.func_185917_h()) && f2 == f5);
         break;
      case WEST:
         p_187494_5_.set(1, f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F);
         p_187494_5_.set(0, (f < 1.0E-4F || p_187494_1_.func_185917_h()) && f == f3);
         break;
      case EAST:
         p_187494_5_.set(1, f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F);
         p_187494_5_.set(0, (f3 > 0.9999F || p_187494_1_.func_185917_h()) && f == f3);
      }

   }

   private void func_187496_a(IBlockAccess p_187496_1_, IBlockState p_187496_2_, BlockPos p_187496_3_, int p_187496_4_, boolean p_187496_5_, BufferBuilder p_187496_6_, List<BakedQuad> p_187496_7_, BitSet p_187496_8_) {
      Vec3d vec3d = p_187496_2_.func_191059_e(p_187496_1_, p_187496_3_);
      double d0 = (double)p_187496_3_.func_177958_n() + vec3d.field_72450_a;
      double d1 = (double)p_187496_3_.func_177956_o() + vec3d.field_72448_b;
      double d2 = (double)p_187496_3_.func_177952_p() + vec3d.field_72449_c;
      int i = 0;

      for(int j = p_187496_7_.size(); i < j; ++i) {
         BakedQuad bakedquad = p_187496_7_.get(i);
         if (p_187496_5_) {
            this.func_187494_a(p_187496_2_, bakedquad.func_178209_a(), bakedquad.func_178210_d(), (float[])null, p_187496_8_);
            BlockPos blockpos = p_187496_8_.get(0) ? p_187496_3_.func_177972_a(bakedquad.func_178210_d()) : p_187496_3_;
            p_187496_4_ = p_187496_2_.func_185889_a(p_187496_1_, blockpos);
         }

         p_187496_6_.func_178981_a(bakedquad.func_178209_a());
         p_187496_6_.func_178962_a(p_187496_4_, p_187496_4_, p_187496_4_, p_187496_4_);
         if (bakedquad.func_178212_b()) {
            int k = this.field_187499_a.func_186724_a(p_187496_2_, p_187496_1_, p_187496_3_, bakedquad.func_178211_c());
            if (EntityRenderer.field_78517_a) {
               k = TextureUtil.func_177054_c(k);
            }

            float f = (float)(k >> 16 & 255) / 255.0F;
            float f1 = (float)(k >> 8 & 255) / 255.0F;
            float f2 = (float)(k & 255) / 255.0F;
            p_187496_6_.func_178978_a(f, f1, f2, 4);
            p_187496_6_.func_178978_a(f, f1, f2, 3);
            p_187496_6_.func_178978_a(f, f1, f2, 2);
            p_187496_6_.func_178978_a(f, f1, f2, 1);
         }

         p_187496_6_.func_178987_a(d0, d1, d2);
      }

   }

   public void func_178262_a(IBakedModel p_178262_1_, float p_178262_2_, float p_178262_3_, float p_178262_4_, float p_178262_5_) {
      this.func_187495_a((IBlockState)null, p_178262_1_, p_178262_2_, p_178262_3_, p_178262_4_, p_178262_5_);
   }

   public void func_187495_a(IBlockState p_187495_1_, IBakedModel p_187495_2_, float p_187495_3_, float p_187495_4_, float p_187495_5_, float p_187495_6_) {
      for(EnumFacing enumfacing : EnumFacing.values()) {
         this.func_178264_a(p_187495_3_, p_187495_4_, p_187495_5_, p_187495_6_, p_187495_2_.func_188616_a(p_187495_1_, enumfacing, 0L));
      }

      this.func_178264_a(p_187495_3_, p_187495_4_, p_187495_5_, p_187495_6_, p_187495_2_.func_188616_a(p_187495_1_, (EnumFacing)null, 0L));
   }

   public void func_178266_a(IBakedModel p_178266_1_, IBlockState p_178266_2_, float p_178266_3_, boolean p_178266_4_) {
      Block block = p_178266_2_.func_177230_c();
      GlStateManager.func_179114_b(90.0F, 0.0F, 1.0F, 0.0F);
      int i = this.field_187499_a.func_186724_a(p_178266_2_, (IBlockAccess)null, (BlockPos)null, 0);
      if (EntityRenderer.field_78517_a) {
         i = TextureUtil.func_177054_c(i);
      }

      float f = (float)(i >> 16 & 255) / 255.0F;
      float f1 = (float)(i >> 8 & 255) / 255.0F;
      float f2 = (float)(i & 255) / 255.0F;
      if (!p_178266_4_) {
         GlStateManager.func_179131_c(p_178266_3_, p_178266_3_, p_178266_3_, 1.0F);
      }

      this.func_187495_a(p_178266_2_, p_178266_1_, p_178266_3_, f, f1, f2);
   }

   private void func_178264_a(float p_178264_1_, float p_178264_2_, float p_178264_3_, float p_178264_4_, List<BakedQuad> p_178264_5_) {
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      int i = 0;

      for(int j = p_178264_5_.size(); i < j; ++i) {
         BakedQuad bakedquad = p_178264_5_.get(i);
         bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_176599_b);
         bufferbuilder.func_178981_a(bakedquad.func_178209_a());
         if (bakedquad.func_178212_b()) {
            bufferbuilder.func_178990_f(p_178264_2_ * p_178264_1_, p_178264_3_ * p_178264_1_, p_178264_4_ * p_178264_1_);
         } else {
            bufferbuilder.func_178990_f(p_178264_1_, p_178264_1_, p_178264_1_);
         }

         Vec3i vec3i = bakedquad.func_178210_d().func_176730_m();
         bufferbuilder.func_178975_e((float)vec3i.func_177958_n(), (float)vec3i.func_177956_o(), (float)vec3i.func_177952_p());
         tessellator.func_78381_a();
      }

   }

   class AmbientOcclusionFace {
      private final float[] field_178206_b = new float[4];
      private final int[] field_178207_c = new int[4];

      public void func_187491_a(IBlockAccess p_187491_1_, IBlockState p_187491_2_, BlockPos p_187491_3_, EnumFacing p_187491_4_, float[] p_187491_5_, BitSet p_187491_6_) {
         BlockPos blockpos = p_187491_6_.get(0) ? p_187491_3_.func_177972_a(p_187491_4_) : p_187491_3_;
         BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.func_185346_s();
         BlockModelRenderer.EnumNeighborInfo blockmodelrenderer$enumneighborinfo = BlockModelRenderer.EnumNeighborInfo.func_178273_a(p_187491_4_);
         BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos1 = BlockPos.PooledMutableBlockPos.func_185342_g(blockpos).func_189536_c(blockmodelrenderer$enumneighborinfo.field_178276_g[0]);
         BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos2 = BlockPos.PooledMutableBlockPos.func_185342_g(blockpos).func_189536_c(blockmodelrenderer$enumneighborinfo.field_178276_g[1]);
         BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos3 = BlockPos.PooledMutableBlockPos.func_185342_g(blockpos).func_189536_c(blockmodelrenderer$enumneighborinfo.field_178276_g[2]);
         BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos4 = BlockPos.PooledMutableBlockPos.func_185342_g(blockpos).func_189536_c(blockmodelrenderer$enumneighborinfo.field_178276_g[3]);
         int i = p_187491_2_.func_185889_a(p_187491_1_, blockpos$pooledmutableblockpos1);
         int j = p_187491_2_.func_185889_a(p_187491_1_, blockpos$pooledmutableblockpos2);
         int k = p_187491_2_.func_185889_a(p_187491_1_, blockpos$pooledmutableblockpos3);
         int l = p_187491_2_.func_185889_a(p_187491_1_, blockpos$pooledmutableblockpos4);
         float f = p_187491_1_.func_180495_p(blockpos$pooledmutableblockpos1).func_185892_j();
         float f1 = p_187491_1_.func_180495_p(blockpos$pooledmutableblockpos2).func_185892_j();
         float f2 = p_187491_1_.func_180495_p(blockpos$pooledmutableblockpos3).func_185892_j();
         float f3 = p_187491_1_.func_180495_p(blockpos$pooledmutableblockpos4).func_185892_j();
         boolean flag = p_187491_1_.func_180495_p(blockpos$pooledmutableblockpos.func_189533_g(blockpos$pooledmutableblockpos1).func_189536_c(p_187491_4_)).func_185895_e();
         boolean flag1 = p_187491_1_.func_180495_p(blockpos$pooledmutableblockpos.func_189533_g(blockpos$pooledmutableblockpos2).func_189536_c(p_187491_4_)).func_185895_e();
         boolean flag2 = p_187491_1_.func_180495_p(blockpos$pooledmutableblockpos.func_189533_g(blockpos$pooledmutableblockpos3).func_189536_c(p_187491_4_)).func_185895_e();
         boolean flag3 = p_187491_1_.func_180495_p(blockpos$pooledmutableblockpos.func_189533_g(blockpos$pooledmutableblockpos4).func_189536_c(p_187491_4_)).func_185895_e();
         float f4;
         int i1;
         if (!flag2 && !flag) {
            f4 = f;
            i1 = i;
         } else {
            BlockPos blockpos1 = blockpos$pooledmutableblockpos.func_189533_g(blockpos$pooledmutableblockpos1).func_189536_c(blockmodelrenderer$enumneighborinfo.field_178276_g[2]);
            f4 = p_187491_1_.func_180495_p(blockpos1).func_185892_j();
            i1 = p_187491_2_.func_185889_a(p_187491_1_, blockpos1);
         }

         float f5;
         int j1;
         if (!flag3 && !flag) {
            f5 = f;
            j1 = i;
         } else {
            BlockPos blockpos2 = blockpos$pooledmutableblockpos.func_189533_g(blockpos$pooledmutableblockpos1).func_189536_c(blockmodelrenderer$enumneighborinfo.field_178276_g[3]);
            f5 = p_187491_1_.func_180495_p(blockpos2).func_185892_j();
            j1 = p_187491_2_.func_185889_a(p_187491_1_, blockpos2);
         }

         float f6;
         int k1;
         if (!flag2 && !flag1) {
            f6 = f1;
            k1 = j;
         } else {
            BlockPos blockpos3 = blockpos$pooledmutableblockpos.func_189533_g(blockpos$pooledmutableblockpos2).func_189536_c(blockmodelrenderer$enumneighborinfo.field_178276_g[2]);
            f6 = p_187491_1_.func_180495_p(blockpos3).func_185892_j();
            k1 = p_187491_2_.func_185889_a(p_187491_1_, blockpos3);
         }

         float f7;
         int l1;
         if (!flag3 && !flag1) {
            f7 = f1;
            l1 = j;
         } else {
            BlockPos blockpos4 = blockpos$pooledmutableblockpos.func_189533_g(blockpos$pooledmutableblockpos2).func_189536_c(blockmodelrenderer$enumneighborinfo.field_178276_g[3]);
            f7 = p_187491_1_.func_180495_p(blockpos4).func_185892_j();
            l1 = p_187491_2_.func_185889_a(p_187491_1_, blockpos4);
         }

         int i3 = p_187491_2_.func_185889_a(p_187491_1_, p_187491_3_);
         if (p_187491_6_.get(0) || !p_187491_1_.func_180495_p(p_187491_3_.func_177972_a(p_187491_4_)).func_185914_p()) {
            i3 = p_187491_2_.func_185889_a(p_187491_1_, p_187491_3_.func_177972_a(p_187491_4_));
         }

         float f8 = p_187491_6_.get(0) ? p_187491_1_.func_180495_p(blockpos).func_185892_j() : p_187491_1_.func_180495_p(p_187491_3_).func_185892_j();
         BlockModelRenderer.VertexTranslations blockmodelrenderer$vertextranslations = BlockModelRenderer.VertexTranslations.func_178184_a(p_187491_4_);
         blockpos$pooledmutableblockpos.func_185344_t();
         blockpos$pooledmutableblockpos1.func_185344_t();
         blockpos$pooledmutableblockpos2.func_185344_t();
         blockpos$pooledmutableblockpos3.func_185344_t();
         blockpos$pooledmutableblockpos4.func_185344_t();
         if (p_187491_6_.get(1) && blockmodelrenderer$enumneighborinfo.field_178289_i) {
            float f29 = (f3 + f + f5 + f8) * 0.25F;
            float f30 = (f2 + f + f4 + f8) * 0.25F;
            float f31 = (f2 + f1 + f6 + f8) * 0.25F;
            float f32 = (f3 + f1 + f7 + f8) * 0.25F;
            float f13 = p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178286_j[0].field_178229_m] * p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178286_j[1].field_178229_m];
            float f14 = p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178286_j[2].field_178229_m] * p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178286_j[3].field_178229_m];
            float f15 = p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178286_j[4].field_178229_m] * p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178286_j[5].field_178229_m];
            float f16 = p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178286_j[6].field_178229_m] * p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178286_j[7].field_178229_m];
            float f17 = p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178287_k[0].field_178229_m] * p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178287_k[1].field_178229_m];
            float f18 = p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178287_k[2].field_178229_m] * p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178287_k[3].field_178229_m];
            float f19 = p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178287_k[4].field_178229_m] * p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178287_k[5].field_178229_m];
            float f20 = p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178287_k[6].field_178229_m] * p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178287_k[7].field_178229_m];
            float f21 = p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178284_l[0].field_178229_m] * p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178284_l[1].field_178229_m];
            float f22 = p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178284_l[2].field_178229_m] * p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178284_l[3].field_178229_m];
            float f23 = p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178284_l[4].field_178229_m] * p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178284_l[5].field_178229_m];
            float f24 = p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178284_l[6].field_178229_m] * p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178284_l[7].field_178229_m];
            float f25 = p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178285_m[0].field_178229_m] * p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178285_m[1].field_178229_m];
            float f26 = p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178285_m[2].field_178229_m] * p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178285_m[3].field_178229_m];
            float f27 = p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178285_m[4].field_178229_m] * p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178285_m[5].field_178229_m];
            float f28 = p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178285_m[6].field_178229_m] * p_187491_5_[blockmodelrenderer$enumneighborinfo.field_178285_m[7].field_178229_m];
            this.field_178206_b[blockmodelrenderer$vertextranslations.field_178191_g] = f29 * f13 + f30 * f14 + f31 * f15 + f32 * f16;
            this.field_178206_b[blockmodelrenderer$vertextranslations.field_178200_h] = f29 * f17 + f30 * f18 + f31 * f19 + f32 * f20;
            this.field_178206_b[blockmodelrenderer$vertextranslations.field_178201_i] = f29 * f21 + f30 * f22 + f31 * f23 + f32 * f24;
            this.field_178206_b[blockmodelrenderer$vertextranslations.field_178198_j] = f29 * f25 + f30 * f26 + f31 * f27 + f32 * f28;
            int i2 = this.func_147778_a(l, i, j1, i3);
            int j2 = this.func_147778_a(k, i, i1, i3);
            int k2 = this.func_147778_a(k, j, k1, i3);
            int l2 = this.func_147778_a(l, j, l1, i3);
            this.field_178207_c[blockmodelrenderer$vertextranslations.field_178191_g] = this.func_178203_a(i2, j2, k2, l2, f13, f14, f15, f16);
            this.field_178207_c[blockmodelrenderer$vertextranslations.field_178200_h] = this.func_178203_a(i2, j2, k2, l2, f17, f18, f19, f20);
            this.field_178207_c[blockmodelrenderer$vertextranslations.field_178201_i] = this.func_178203_a(i2, j2, k2, l2, f21, f22, f23, f24);
            this.field_178207_c[blockmodelrenderer$vertextranslations.field_178198_j] = this.func_178203_a(i2, j2, k2, l2, f25, f26, f27, f28);
         } else {
            float f9 = (f3 + f + f5 + f8) * 0.25F;
            float f10 = (f2 + f + f4 + f8) * 0.25F;
            float f11 = (f2 + f1 + f6 + f8) * 0.25F;
            float f12 = (f3 + f1 + f7 + f8) * 0.25F;
            this.field_178207_c[blockmodelrenderer$vertextranslations.field_178191_g] = this.func_147778_a(l, i, j1, i3);
            this.field_178207_c[blockmodelrenderer$vertextranslations.field_178200_h] = this.func_147778_a(k, i, i1, i3);
            this.field_178207_c[blockmodelrenderer$vertextranslations.field_178201_i] = this.func_147778_a(k, j, k1, i3);
            this.field_178207_c[blockmodelrenderer$vertextranslations.field_178198_j] = this.func_147778_a(l, j, l1, i3);
            this.field_178206_b[blockmodelrenderer$vertextranslations.field_178191_g] = f9;
            this.field_178206_b[blockmodelrenderer$vertextranslations.field_178200_h] = f10;
            this.field_178206_b[blockmodelrenderer$vertextranslations.field_178201_i] = f11;
            this.field_178206_b[blockmodelrenderer$vertextranslations.field_178198_j] = f12;
         }

      }

      private int func_147778_a(int p_147778_1_, int p_147778_2_, int p_147778_3_, int p_147778_4_) {
         if (p_147778_1_ == 0) {
            p_147778_1_ = p_147778_4_;
         }

         if (p_147778_2_ == 0) {
            p_147778_2_ = p_147778_4_;
         }

         if (p_147778_3_ == 0) {
            p_147778_3_ = p_147778_4_;
         }

         return p_147778_1_ + p_147778_2_ + p_147778_3_ + p_147778_4_ >> 2 & 16711935;
      }

      private int func_178203_a(int p_178203_1_, int p_178203_2_, int p_178203_3_, int p_178203_4_, float p_178203_5_, float p_178203_6_, float p_178203_7_, float p_178203_8_) {
         int i = (int)((float)(p_178203_1_ >> 16 & 255) * p_178203_5_ + (float)(p_178203_2_ >> 16 & 255) * p_178203_6_ + (float)(p_178203_3_ >> 16 & 255) * p_178203_7_ + (float)(p_178203_4_ >> 16 & 255) * p_178203_8_) & 255;
         int j = (int)((float)(p_178203_1_ & 255) * p_178203_5_ + (float)(p_178203_2_ & 255) * p_178203_6_ + (float)(p_178203_3_ & 255) * p_178203_7_ + (float)(p_178203_4_ & 255) * p_178203_8_) & 255;
         return i << 16 | j;
      }
   }

   public static enum EnumNeighborInfo {
      DOWN(new EnumFacing[]{EnumFacing.WEST, EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.SOUTH}, 0.5F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.SOUTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.SOUTH}),
      UP(new EnumFacing[]{EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH}, 1.0F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.SOUTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.SOUTH}),
      NORTH(new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.EAST, EnumFacing.WEST}, 0.8F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_WEST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_EAST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_EAST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_WEST}),
      SOUTH(new EnumFacing[]{EnumFacing.WEST, EnumFacing.EAST, EnumFacing.DOWN, EnumFacing.UP}, 0.8F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.WEST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.WEST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.EAST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.EAST}),
      WEST(new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH}, 0.6F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.SOUTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.SOUTH}),
      EAST(new EnumFacing[]{EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH}, 0.6F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.SOUTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.SOUTH});

      private final EnumFacing[] field_178276_g;
      private final float field_178288_h;
      private final boolean field_178289_i;
      private final BlockModelRenderer.Orientation[] field_178286_j;
      private final BlockModelRenderer.Orientation[] field_178287_k;
      private final BlockModelRenderer.Orientation[] field_178284_l;
      private final BlockModelRenderer.Orientation[] field_178285_m;
      private static final BlockModelRenderer.EnumNeighborInfo[] field_178282_n = new BlockModelRenderer.EnumNeighborInfo[6];

      private EnumNeighborInfo(EnumFacing[] p_i46236_3_, float p_i46236_4_, boolean p_i46236_5_, BlockModelRenderer.Orientation[] p_i46236_6_, BlockModelRenderer.Orientation[] p_i46236_7_, BlockModelRenderer.Orientation[] p_i46236_8_, BlockModelRenderer.Orientation[] p_i46236_9_) {
         this.field_178276_g = p_i46236_3_;
         this.field_178288_h = p_i46236_4_;
         this.field_178289_i = p_i46236_5_;
         this.field_178286_j = p_i46236_6_;
         this.field_178287_k = p_i46236_7_;
         this.field_178284_l = p_i46236_8_;
         this.field_178285_m = p_i46236_9_;
      }

      public static BlockModelRenderer.EnumNeighborInfo func_178273_a(EnumFacing p_178273_0_) {
         return field_178282_n[p_178273_0_.func_176745_a()];
      }

      static {
         field_178282_n[EnumFacing.DOWN.func_176745_a()] = DOWN;
         field_178282_n[EnumFacing.UP.func_176745_a()] = UP;
         field_178282_n[EnumFacing.NORTH.func_176745_a()] = NORTH;
         field_178282_n[EnumFacing.SOUTH.func_176745_a()] = SOUTH;
         field_178282_n[EnumFacing.WEST.func_176745_a()] = WEST;
         field_178282_n[EnumFacing.EAST.func_176745_a()] = EAST;
      }
   }

   public static enum Orientation {
      DOWN(EnumFacing.DOWN, false),
      UP(EnumFacing.UP, false),
      NORTH(EnumFacing.NORTH, false),
      SOUTH(EnumFacing.SOUTH, false),
      WEST(EnumFacing.WEST, false),
      EAST(EnumFacing.EAST, false),
      FLIP_DOWN(EnumFacing.DOWN, true),
      FLIP_UP(EnumFacing.UP, true),
      FLIP_NORTH(EnumFacing.NORTH, true),
      FLIP_SOUTH(EnumFacing.SOUTH, true),
      FLIP_WEST(EnumFacing.WEST, true),
      FLIP_EAST(EnumFacing.EAST, true);

      private final int field_178229_m;

      private Orientation(EnumFacing p_i46233_3_, boolean p_i46233_4_) {
         this.field_178229_m = p_i46233_3_.func_176745_a() + (p_i46233_4_ ? EnumFacing.values().length : 0);
      }
   }

   static enum VertexTranslations {
      DOWN(0, 1, 2, 3),
      UP(2, 3, 0, 1),
      NORTH(3, 0, 1, 2),
      SOUTH(0, 1, 2, 3),
      WEST(3, 0, 1, 2),
      EAST(1, 2, 3, 0);

      private final int field_178191_g;
      private final int field_178200_h;
      private final int field_178201_i;
      private final int field_178198_j;
      private static final BlockModelRenderer.VertexTranslations[] field_178199_k = new BlockModelRenderer.VertexTranslations[6];

      private VertexTranslations(int p_i46234_3_, int p_i46234_4_, int p_i46234_5_, int p_i46234_6_) {
         this.field_178191_g = p_i46234_3_;
         this.field_178200_h = p_i46234_4_;
         this.field_178201_i = p_i46234_5_;
         this.field_178198_j = p_i46234_6_;
      }

      public static BlockModelRenderer.VertexTranslations func_178184_a(EnumFacing p_178184_0_) {
         return field_178199_k[p_178184_0_.func_176745_a()];
      }

      static {
         field_178199_k[EnumFacing.DOWN.func_176745_a()] = DOWN;
         field_178199_k[EnumFacing.UP.func_176745_a()] = UP;
         field_178199_k[EnumFacing.NORTH.func_176745_a()] = NORTH;
         field_178199_k[EnumFacing.SOUTH.func_176745_a()] = SOUTH;
         field_178199_k[EnumFacing.WEST.func_176745_a()] = WEST;
         field_178199_k[EnumFacing.EAST.func_176745_a()] = EAST;
      }
   }
}
