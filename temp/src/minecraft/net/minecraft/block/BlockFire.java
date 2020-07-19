package net.minecraft.block;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;

public class BlockFire extends Block {
   public static final PropertyInteger field_176543_a = PropertyInteger.func_177719_a("age", 0, 15);
   public static final PropertyBool field_176545_N = PropertyBool.func_177716_a("north");
   public static final PropertyBool field_176546_O = PropertyBool.func_177716_a("east");
   public static final PropertyBool field_176541_P = PropertyBool.func_177716_a("south");
   public static final PropertyBool field_176539_Q = PropertyBool.func_177716_a("west");
   public static final PropertyBool field_176542_R = PropertyBool.func_177716_a("up");
   private final Map<Block, Integer> field_149849_a = Maps.<Block, Integer>newIdentityHashMap();
   private final Map<Block, Integer> field_149848_b = Maps.<Block, Integer>newIdentityHashMap();

   public IBlockState func_176221_a(IBlockState p_176221_1_, IBlockAccess p_176221_2_, BlockPos p_176221_3_) {
      return !p_176221_2_.func_180495_p(p_176221_3_.func_177977_b()).func_185896_q() && !Blocks.field_150480_ab.func_176535_e(p_176221_2_, p_176221_3_.func_177977_b()) ? p_176221_1_.func_177226_a(field_176545_N, Boolean.valueOf(this.func_176535_e(p_176221_2_, p_176221_3_.func_177978_c()))).func_177226_a(field_176546_O, Boolean.valueOf(this.func_176535_e(p_176221_2_, p_176221_3_.func_177974_f()))).func_177226_a(field_176541_P, Boolean.valueOf(this.func_176535_e(p_176221_2_, p_176221_3_.func_177968_d()))).func_177226_a(field_176539_Q, Boolean.valueOf(this.func_176535_e(p_176221_2_, p_176221_3_.func_177976_e()))).func_177226_a(field_176542_R, Boolean.valueOf(this.func_176535_e(p_176221_2_, p_176221_3_.func_177984_a()))) : this.func_176223_P();
   }

   protected BlockFire() {
      super(Material.field_151581_o);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176543_a, Integer.valueOf(0)).func_177226_a(field_176545_N, Boolean.valueOf(false)).func_177226_a(field_176546_O, Boolean.valueOf(false)).func_177226_a(field_176541_P, Boolean.valueOf(false)).func_177226_a(field_176539_Q, Boolean.valueOf(false)).func_177226_a(field_176542_R, Boolean.valueOf(false)));
      this.func_149675_a(true);
   }

   public static void func_149843_e() {
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150344_f, 5, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150373_bw, 5, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150376_bx, 5, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_180390_bo, 5, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_180391_bp, 5, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_180392_bq, 5, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_180386_br, 5, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_180385_bs, 5, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_180387_bt, 5, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_180407_aO, 5, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_180408_aP, 5, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_180404_aQ, 5, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_180403_aR, 5, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_180406_aS, 5, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_180405_aT, 5, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150476_ad, 5, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150487_bG, 5, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150485_bF, 5, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150481_bH, 5, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150400_ck, 5, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150401_cl, 5, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150364_r, 5, 5);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150363_s, 5, 5);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150362_t, 30, 60);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150361_u, 30, 60);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150342_X, 30, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150335_W, 15, 100);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150329_H, 60, 100);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150398_cm, 60, 100);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150327_N, 60, 100);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150328_O, 60, 100);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150330_I, 60, 100);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150325_L, 30, 60);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150395_bd, 15, 100);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150402_ci, 5, 5);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150407_cf, 60, 20);
      Blocks.field_150480_ab.func_180686_a(Blocks.field_150404_cg, 60, 20);
   }

   public void func_180686_a(Block p_180686_1_, int p_180686_2_, int p_180686_3_) {
      this.field_149849_a.put(p_180686_1_, Integer.valueOf(p_180686_2_));
      this.field_149848_b.put(p_180686_1_, Integer.valueOf(p_180686_3_));
   }

   @Nullable
   public AxisAlignedBB func_180646_a(IBlockState p_180646_1_, IBlockAccess p_180646_2_, BlockPos p_180646_3_) {
      return field_185506_k;
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public int func_149745_a(Random p_149745_1_) {
      return 0;
   }

   public int func_149738_a(World p_149738_1_) {
      return 30;
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      if (p_180650_1_.func_82736_K().func_82766_b("doFireTick")) {
         if (!this.func_176196_c(p_180650_1_, p_180650_2_)) {
            p_180650_1_.func_175698_g(p_180650_2_);
         }

         Block block = p_180650_1_.func_180495_p(p_180650_2_.func_177977_b()).func_177230_c();
         boolean flag = block == Blocks.field_150424_aL || block == Blocks.field_189877_df;
         if (p_180650_1_.field_73011_w instanceof WorldProviderEnd && block == Blocks.field_150357_h) {
            flag = true;
         }

         int i = ((Integer)p_180650_3_.func_177229_b(field_176543_a)).intValue();
         if (!flag && p_180650_1_.func_72896_J() && this.func_176537_d(p_180650_1_, p_180650_2_) && p_180650_4_.nextFloat() < 0.2F + (float)i * 0.03F) {
            p_180650_1_.func_175698_g(p_180650_2_);
         } else {
            if (i < 15) {
               p_180650_3_ = p_180650_3_.func_177226_a(field_176543_a, Integer.valueOf(i + p_180650_4_.nextInt(3) / 2));
               p_180650_1_.func_180501_a(p_180650_2_, p_180650_3_, 4);
            }

            p_180650_1_.func_175684_a(p_180650_2_, this, this.func_149738_a(p_180650_1_) + p_180650_4_.nextInt(10));
            if (!flag) {
               if (!this.func_176533_e(p_180650_1_, p_180650_2_)) {
                  if (!p_180650_1_.func_180495_p(p_180650_2_.func_177977_b()).func_185896_q() || i > 3) {
                     p_180650_1_.func_175698_g(p_180650_2_);
                  }

                  return;
               }

               if (!this.func_176535_e(p_180650_1_, p_180650_2_.func_177977_b()) && i == 15 && p_180650_4_.nextInt(4) == 0) {
                  p_180650_1_.func_175698_g(p_180650_2_);
                  return;
               }
            }

            boolean flag1 = p_180650_1_.func_180502_D(p_180650_2_);
            int j = 0;
            if (flag1) {
               j = -50;
            }

            this.func_176536_a(p_180650_1_, p_180650_2_.func_177974_f(), 300 + j, p_180650_4_, i);
            this.func_176536_a(p_180650_1_, p_180650_2_.func_177976_e(), 300 + j, p_180650_4_, i);
            this.func_176536_a(p_180650_1_, p_180650_2_.func_177977_b(), 250 + j, p_180650_4_, i);
            this.func_176536_a(p_180650_1_, p_180650_2_.func_177984_a(), 250 + j, p_180650_4_, i);
            this.func_176536_a(p_180650_1_, p_180650_2_.func_177978_c(), 300 + j, p_180650_4_, i);
            this.func_176536_a(p_180650_1_, p_180650_2_.func_177968_d(), 300 + j, p_180650_4_, i);

            for(int k = -1; k <= 1; ++k) {
               for(int l = -1; l <= 1; ++l) {
                  for(int i1 = -1; i1 <= 4; ++i1) {
                     if (k != 0 || i1 != 0 || l != 0) {
                        int j1 = 100;
                        if (i1 > 1) {
                           j1 += (i1 - 1) * 100;
                        }

                        BlockPos blockpos = p_180650_2_.func_177982_a(k, i1, l);
                        int k1 = this.func_176538_m(p_180650_1_, blockpos);
                        if (k1 > 0) {
                           int l1 = (k1 + 40 + p_180650_1_.func_175659_aa().func_151525_a() * 7) / (i + 30);
                           if (flag1) {
                              l1 /= 2;
                           }

                           if (l1 > 0 && p_180650_4_.nextInt(j1) <= l1 && (!p_180650_1_.func_72896_J() || !this.func_176537_d(p_180650_1_, blockpos))) {
                              int i2 = i + p_180650_4_.nextInt(5) / 4;
                              if (i2 > 15) {
                                 i2 = 15;
                              }

                              p_180650_1_.func_180501_a(blockpos, p_180650_3_.func_177226_a(field_176543_a, Integer.valueOf(i2)), 3);
                           }
                        }
                     }
                  }
               }
            }

         }
      }
   }

   protected boolean func_176537_d(World p_176537_1_, BlockPos p_176537_2_) {
      return p_176537_1_.func_175727_C(p_176537_2_) || p_176537_1_.func_175727_C(p_176537_2_.func_177976_e()) || p_176537_1_.func_175727_C(p_176537_2_.func_177974_f()) || p_176537_1_.func_175727_C(p_176537_2_.func_177978_c()) || p_176537_1_.func_175727_C(p_176537_2_.func_177968_d());
   }

   public boolean func_149698_L() {
      return false;
   }

   private int func_176532_c(Block p_176532_1_) {
      Integer integer = this.field_149848_b.get(p_176532_1_);
      return integer == null ? 0 : integer.intValue();
   }

   private int func_176534_d(Block p_176534_1_) {
      Integer integer = this.field_149849_a.get(p_176534_1_);
      return integer == null ? 0 : integer.intValue();
   }

   private void func_176536_a(World p_176536_1_, BlockPos p_176536_2_, int p_176536_3_, Random p_176536_4_, int p_176536_5_) {
      int i = this.func_176532_c(p_176536_1_.func_180495_p(p_176536_2_).func_177230_c());
      if (p_176536_4_.nextInt(p_176536_3_) < i) {
         IBlockState iblockstate = p_176536_1_.func_180495_p(p_176536_2_);
         if (p_176536_4_.nextInt(p_176536_5_ + 10) < 5 && !p_176536_1_.func_175727_C(p_176536_2_)) {
            int j = p_176536_5_ + p_176536_4_.nextInt(5) / 4;
            if (j > 15) {
               j = 15;
            }

            p_176536_1_.func_180501_a(p_176536_2_, this.func_176223_P().func_177226_a(field_176543_a, Integer.valueOf(j)), 3);
         } else {
            p_176536_1_.func_175698_g(p_176536_2_);
         }

         if (iblockstate.func_177230_c() == Blocks.field_150335_W) {
            Blocks.field_150335_W.func_176206_d(p_176536_1_, p_176536_2_, iblockstate.func_177226_a(BlockTNT.field_176246_a, Boolean.valueOf(true)));
         }
      }

   }

   private boolean func_176533_e(World p_176533_1_, BlockPos p_176533_2_) {
      for(EnumFacing enumfacing : EnumFacing.values()) {
         if (this.func_176535_e(p_176533_1_, p_176533_2_.func_177972_a(enumfacing))) {
            return true;
         }
      }

      return false;
   }

   private int func_176538_m(World p_176538_1_, BlockPos p_176538_2_) {
      if (!p_176538_1_.func_175623_d(p_176538_2_)) {
         return 0;
      } else {
         int i = 0;

         for(EnumFacing enumfacing : EnumFacing.values()) {
            i = Math.max(this.func_176534_d(p_176538_1_.func_180495_p(p_176538_2_.func_177972_a(enumfacing)).func_177230_c()), i);
         }

         return i;
      }
   }

   public boolean func_149703_v() {
      return false;
   }

   public boolean func_176535_e(IBlockAccess p_176535_1_, BlockPos p_176535_2_) {
      return this.func_176534_d(p_176535_1_.func_180495_p(p_176535_2_).func_177230_c()) > 0;
   }

   public boolean func_176196_c(World p_176196_1_, BlockPos p_176196_2_) {
      return p_176196_1_.func_180495_p(p_176196_2_.func_177977_b()).func_185896_q() || this.func_176533_e(p_176196_1_, p_176196_2_);
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      if (!p_189540_2_.func_180495_p(p_189540_3_.func_177977_b()).func_185896_q() && !this.func_176533_e(p_189540_2_, p_189540_3_)) {
         p_189540_2_.func_175698_g(p_189540_3_);
      }

   }

   public void func_176213_c(World p_176213_1_, BlockPos p_176213_2_, IBlockState p_176213_3_) {
      if (p_176213_1_.field_73011_w.func_186058_p().func_186068_a() > 0 || !Blocks.field_150427_aO.func_176548_d(p_176213_1_, p_176213_2_)) {
         if (!p_176213_1_.func_180495_p(p_176213_2_.func_177977_b()).func_185896_q() && !this.func_176533_e(p_176213_1_, p_176213_2_)) {
            p_176213_1_.func_175698_g(p_176213_2_);
         } else {
            p_176213_1_.func_175684_a(p_176213_2_, this, this.func_149738_a(p_176213_1_) + p_176213_1_.field_73012_v.nextInt(10));
         }
      }
   }

   public void func_180655_c(IBlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
      if (p_180655_4_.nextInt(24) == 0) {
         p_180655_2_.func_184134_a((double)((float)p_180655_3_.func_177958_n() + 0.5F), (double)((float)p_180655_3_.func_177956_o() + 0.5F), (double)((float)p_180655_3_.func_177952_p() + 0.5F), SoundEvents.field_187643_bs, SoundCategory.BLOCKS, 1.0F + p_180655_4_.nextFloat(), p_180655_4_.nextFloat() * 0.7F + 0.3F, false);
      }

      if (!p_180655_2_.func_180495_p(p_180655_3_.func_177977_b()).func_185896_q() && !Blocks.field_150480_ab.func_176535_e(p_180655_2_, p_180655_3_.func_177977_b())) {
         if (Blocks.field_150480_ab.func_176535_e(p_180655_2_, p_180655_3_.func_177976_e())) {
            for(int j = 0; j < 2; ++j) {
               double d3 = (double)p_180655_3_.func_177958_n() + p_180655_4_.nextDouble() * 0.10000000149011612D;
               double d8 = (double)p_180655_3_.func_177956_o() + p_180655_4_.nextDouble();
               double d13 = (double)p_180655_3_.func_177952_p() + p_180655_4_.nextDouble();
               p_180655_2_.func_175688_a(EnumParticleTypes.SMOKE_LARGE, d3, d8, d13, 0.0D, 0.0D, 0.0D);
            }
         }

         if (Blocks.field_150480_ab.func_176535_e(p_180655_2_, p_180655_3_.func_177974_f())) {
            for(int k = 0; k < 2; ++k) {
               double d4 = (double)(p_180655_3_.func_177958_n() + 1) - p_180655_4_.nextDouble() * 0.10000000149011612D;
               double d9 = (double)p_180655_3_.func_177956_o() + p_180655_4_.nextDouble();
               double d14 = (double)p_180655_3_.func_177952_p() + p_180655_4_.nextDouble();
               p_180655_2_.func_175688_a(EnumParticleTypes.SMOKE_LARGE, d4, d9, d14, 0.0D, 0.0D, 0.0D);
            }
         }

         if (Blocks.field_150480_ab.func_176535_e(p_180655_2_, p_180655_3_.func_177978_c())) {
            for(int l = 0; l < 2; ++l) {
               double d5 = (double)p_180655_3_.func_177958_n() + p_180655_4_.nextDouble();
               double d10 = (double)p_180655_3_.func_177956_o() + p_180655_4_.nextDouble();
               double d15 = (double)p_180655_3_.func_177952_p() + p_180655_4_.nextDouble() * 0.10000000149011612D;
               p_180655_2_.func_175688_a(EnumParticleTypes.SMOKE_LARGE, d5, d10, d15, 0.0D, 0.0D, 0.0D);
            }
         }

         if (Blocks.field_150480_ab.func_176535_e(p_180655_2_, p_180655_3_.func_177968_d())) {
            for(int i1 = 0; i1 < 2; ++i1) {
               double d6 = (double)p_180655_3_.func_177958_n() + p_180655_4_.nextDouble();
               double d11 = (double)p_180655_3_.func_177956_o() + p_180655_4_.nextDouble();
               double d16 = (double)(p_180655_3_.func_177952_p() + 1) - p_180655_4_.nextDouble() * 0.10000000149011612D;
               p_180655_2_.func_175688_a(EnumParticleTypes.SMOKE_LARGE, d6, d11, d16, 0.0D, 0.0D, 0.0D);
            }
         }

         if (Blocks.field_150480_ab.func_176535_e(p_180655_2_, p_180655_3_.func_177984_a())) {
            for(int j1 = 0; j1 < 2; ++j1) {
               double d7 = (double)p_180655_3_.func_177958_n() + p_180655_4_.nextDouble();
               double d12 = (double)(p_180655_3_.func_177956_o() + 1) - p_180655_4_.nextDouble() * 0.10000000149011612D;
               double d17 = (double)p_180655_3_.func_177952_p() + p_180655_4_.nextDouble();
               p_180655_2_.func_175688_a(EnumParticleTypes.SMOKE_LARGE, d7, d12, d17, 0.0D, 0.0D, 0.0D);
            }
         }
      } else {
         for(int i = 0; i < 3; ++i) {
            double d0 = (double)p_180655_3_.func_177958_n() + p_180655_4_.nextDouble();
            double d1 = (double)p_180655_3_.func_177956_o() + p_180655_4_.nextDouble() * 0.5D + 0.5D;
            double d2 = (double)p_180655_3_.func_177952_p() + p_180655_4_.nextDouble();
            p_180655_2_.func_175688_a(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   public MapColor func_180659_g(IBlockState p_180659_1_, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
      return MapColor.field_151656_f;
   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176543_a, Integer.valueOf(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((Integer)p_176201_1_.func_177229_b(field_176543_a)).intValue();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176543_a, field_176545_N, field_176546_O, field_176541_P, field_176539_Q, field_176542_R});
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }
}
