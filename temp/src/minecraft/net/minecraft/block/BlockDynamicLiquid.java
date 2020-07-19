package net.minecraft.block;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDynamicLiquid extends BlockLiquid {
   int field_149815_a;

   protected BlockDynamicLiquid(Material p_i45403_1_) {
      super(p_i45403_1_);
   }

   private void func_180690_f(World p_180690_1_, BlockPos p_180690_2_, IBlockState p_180690_3_) {
      p_180690_1_.func_180501_a(p_180690_2_, func_176363_b(this.field_149764_J).func_176223_P().func_177226_a(field_176367_b, p_180690_3_.func_177229_b(field_176367_b)), 2);
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      int i = ((Integer)p_180650_3_.func_177229_b(field_176367_b)).intValue();
      int j = 1;
      if (this.field_149764_J == Material.field_151587_i && !p_180650_1_.field_73011_w.func_177500_n()) {
         j = 2;
      }

      int k = this.func_149738_a(p_180650_1_);
      if (i > 0) {
         int l = -100;
         this.field_149815_a = 0;

         for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
            l = this.func_176371_a(p_180650_1_, p_180650_2_.func_177972_a(enumfacing), l);
         }

         int i1 = l + j;
         if (i1 >= 8 || l < 0) {
            i1 = -1;
         }

         int j1 = this.func_189542_i(p_180650_1_.func_180495_p(p_180650_2_.func_177984_a()));
         if (j1 >= 0) {
            if (j1 >= 8) {
               i1 = j1;
            } else {
               i1 = j1 + 8;
            }
         }

         if (this.field_149815_a >= 2 && this.field_149764_J == Material.field_151586_h) {
            IBlockState iblockstate = p_180650_1_.func_180495_p(p_180650_2_.func_177977_b());
            if (iblockstate.func_185904_a().func_76220_a()) {
               i1 = 0;
            } else if (iblockstate.func_185904_a() == this.field_149764_J && ((Integer)iblockstate.func_177229_b(field_176367_b)).intValue() == 0) {
               i1 = 0;
            }
         }

         if (this.field_149764_J == Material.field_151587_i && i < 8 && i1 < 8 && i1 > i && p_180650_4_.nextInt(4) != 0) {
            k *= 4;
         }

         if (i1 == i) {
            this.func_180690_f(p_180650_1_, p_180650_2_, p_180650_3_);
         } else {
            i = i1;
            if (i1 < 0) {
               p_180650_1_.func_175698_g(p_180650_2_);
            } else {
               p_180650_3_ = p_180650_3_.func_177226_a(field_176367_b, Integer.valueOf(i1));
               p_180650_1_.func_180501_a(p_180650_2_, p_180650_3_, 2);
               p_180650_1_.func_175684_a(p_180650_2_, this, k);
               p_180650_1_.func_175685_c(p_180650_2_, this, false);
            }
         }
      } else {
         this.func_180690_f(p_180650_1_, p_180650_2_, p_180650_3_);
      }

      IBlockState iblockstate1 = p_180650_1_.func_180495_p(p_180650_2_.func_177977_b());
      if (this.func_176373_h(p_180650_1_, p_180650_2_.func_177977_b(), iblockstate1)) {
         if (this.field_149764_J == Material.field_151587_i && p_180650_1_.func_180495_p(p_180650_2_.func_177977_b()).func_185904_a() == Material.field_151586_h) {
            p_180650_1_.func_175656_a(p_180650_2_.func_177977_b(), Blocks.field_150348_b.func_176223_P());
            this.func_180688_d(p_180650_1_, p_180650_2_.func_177977_b());
            return;
         }

         if (i >= 8) {
            this.func_176375_a(p_180650_1_, p_180650_2_.func_177977_b(), iblockstate1, i);
         } else {
            this.func_176375_a(p_180650_1_, p_180650_2_.func_177977_b(), iblockstate1, i + 8);
         }
      } else if (i >= 0 && (i == 0 || this.func_176372_g(p_180650_1_, p_180650_2_.func_177977_b(), iblockstate1))) {
         Set<EnumFacing> set = this.func_176376_e(p_180650_1_, p_180650_2_);
         int k1 = i + j;
         if (i >= 8) {
            k1 = 1;
         }

         if (k1 >= 8) {
            return;
         }

         for(EnumFacing enumfacing1 : set) {
            this.func_176375_a(p_180650_1_, p_180650_2_.func_177972_a(enumfacing1), p_180650_1_.func_180495_p(p_180650_2_.func_177972_a(enumfacing1)), k1);
         }
      }

   }

   private void func_176375_a(World p_176375_1_, BlockPos p_176375_2_, IBlockState p_176375_3_, int p_176375_4_) {
      if (this.func_176373_h(p_176375_1_, p_176375_2_, p_176375_3_)) {
         if (p_176375_3_.func_185904_a() != Material.field_151579_a) {
            if (this.field_149764_J == Material.field_151587_i) {
               this.func_180688_d(p_176375_1_, p_176375_2_);
            } else {
               p_176375_3_.func_177230_c().func_176226_b(p_176375_1_, p_176375_2_, p_176375_3_, 0);
            }
         }

         p_176375_1_.func_180501_a(p_176375_2_, this.func_176223_P().func_177226_a(field_176367_b, Integer.valueOf(p_176375_4_)), 3);
      }

   }

   private int func_176374_a(World p_176374_1_, BlockPos p_176374_2_, int p_176374_3_, EnumFacing p_176374_4_) {
      int i = 1000;

      for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
         if (enumfacing != p_176374_4_) {
            BlockPos blockpos = p_176374_2_.func_177972_a(enumfacing);
            IBlockState iblockstate = p_176374_1_.func_180495_p(blockpos);
            if (!this.func_176372_g(p_176374_1_, blockpos, iblockstate) && (iblockstate.func_185904_a() != this.field_149764_J || ((Integer)iblockstate.func_177229_b(field_176367_b)).intValue() > 0)) {
               if (!this.func_176372_g(p_176374_1_, blockpos.func_177977_b(), iblockstate)) {
                  return p_176374_3_;
               }

               if (p_176374_3_ < this.func_185698_b(p_176374_1_)) {
                  int j = this.func_176374_a(p_176374_1_, blockpos, p_176374_3_ + 1, enumfacing.func_176734_d());
                  if (j < i) {
                     i = j;
                  }
               }
            }
         }
      }

      return i;
   }

   private int func_185698_b(World p_185698_1_) {
      return this.field_149764_J == Material.field_151587_i && !p_185698_1_.field_73011_w.func_177500_n() ? 2 : 4;
   }

   private Set<EnumFacing> func_176376_e(World p_176376_1_, BlockPos p_176376_2_) {
      int i = 1000;
      Set<EnumFacing> set = EnumSet.<EnumFacing>noneOf(EnumFacing.class);

      for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
         BlockPos blockpos = p_176376_2_.func_177972_a(enumfacing);
         IBlockState iblockstate = p_176376_1_.func_180495_p(blockpos);
         if (!this.func_176372_g(p_176376_1_, blockpos, iblockstate) && (iblockstate.func_185904_a() != this.field_149764_J || ((Integer)iblockstate.func_177229_b(field_176367_b)).intValue() > 0)) {
            int j;
            if (this.func_176372_g(p_176376_1_, blockpos.func_177977_b(), p_176376_1_.func_180495_p(blockpos.func_177977_b()))) {
               j = this.func_176374_a(p_176376_1_, blockpos, 1, enumfacing.func_176734_d());
            } else {
               j = 0;
            }

            if (j < i) {
               set.clear();
            }

            if (j <= i) {
               set.add(enumfacing);
               i = j;
            }
         }
      }

      return set;
   }

   private boolean func_176372_g(World p_176372_1_, BlockPos p_176372_2_, IBlockState p_176372_3_) {
      Block block = p_176372_1_.func_180495_p(p_176372_2_).func_177230_c();
      if (!(block instanceof BlockDoor) && block != Blocks.field_150472_an && block != Blocks.field_150468_ap && block != Blocks.field_150436_aH) {
         return block.field_149764_J != Material.field_151567_E && block.field_149764_J != Material.field_189963_J ? block.field_149764_J.func_76230_c() : true;
      } else {
         return true;
      }
   }

   protected int func_176371_a(World p_176371_1_, BlockPos p_176371_2_, int p_176371_3_) {
      int i = this.func_189542_i(p_176371_1_.func_180495_p(p_176371_2_));
      if (i < 0) {
         return p_176371_3_;
      } else {
         if (i == 0) {
            ++this.field_149815_a;
         }

         if (i >= 8) {
            i = 0;
         }

         return p_176371_3_ >= 0 && i >= p_176371_3_ ? p_176371_3_ : i;
      }
   }

   private boolean func_176373_h(World p_176373_1_, BlockPos p_176373_2_, IBlockState p_176373_3_) {
      Material material = p_176373_3_.func_185904_a();
      return material != this.field_149764_J && material != Material.field_151587_i && !this.func_176372_g(p_176373_1_, p_176373_2_, p_176373_3_);
   }

   public void func_176213_c(World p_176213_1_, BlockPos p_176213_2_, IBlockState p_176213_3_) {
      if (!this.func_176365_e(p_176213_1_, p_176213_2_, p_176213_3_)) {
         p_176213_1_.func_175684_a(p_176213_2_, this, this.func_149738_a(p_176213_1_));
      }

   }
}
