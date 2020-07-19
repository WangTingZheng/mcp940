package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockRedstoneDiode extends BlockHorizontal {
   protected static final AxisAlignedBB field_185548_c = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);
   protected final boolean field_149914_a;

   protected BlockRedstoneDiode(boolean p_i45400_1_) {
      super(Material.field_151594_q);
      this.field_149914_a = p_i45400_1_;
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      return field_185548_c;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_176196_c(World p_176196_1_, BlockPos p_176196_2_) {
      return p_176196_1_.func_180495_p(p_176196_2_.func_177977_b()).func_185896_q() ? super.func_176196_c(p_176196_1_, p_176196_2_) : false;
   }

   public boolean func_176409_d(World p_176409_1_, BlockPos p_176409_2_) {
      return p_176409_1_.func_180495_p(p_176409_2_.func_177977_b()).func_185896_q();
   }

   public void func_180645_a(World p_180645_1_, BlockPos p_180645_2_, IBlockState p_180645_3_, Random p_180645_4_) {
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      if (!this.func_176405_b(p_180650_1_, p_180650_2_, p_180650_3_)) {
         boolean flag = this.func_176404_e(p_180650_1_, p_180650_2_, p_180650_3_);
         if (this.field_149914_a && !flag) {
            p_180650_1_.func_180501_a(p_180650_2_, this.func_180675_k(p_180650_3_), 2);
         } else if (!this.field_149914_a) {
            p_180650_1_.func_180501_a(p_180650_2_, this.func_180674_e(p_180650_3_), 2);
            if (!flag) {
               p_180650_1_.func_175654_a(p_180650_2_, this.func_180674_e(p_180650_3_).func_177230_c(), this.func_176399_m(p_180650_3_), -1);
            }
         }

      }
   }

   public boolean func_176225_a(IBlockState p_176225_1_, IBlockAccess p_176225_2_, BlockPos p_176225_3_, EnumFacing p_176225_4_) {
      return p_176225_4_.func_176740_k() != EnumFacing.Axis.Y;
   }

   protected boolean func_176406_l(IBlockState p_176406_1_) {
      return this.field_149914_a;
   }

   public int func_176211_b(IBlockState p_176211_1_, IBlockAccess p_176211_2_, BlockPos p_176211_3_, EnumFacing p_176211_4_) {
      return p_176211_1_.func_185911_a(p_176211_2_, p_176211_3_, p_176211_4_);
   }

   public int func_180656_a(IBlockState p_180656_1_, IBlockAccess p_180656_2_, BlockPos p_180656_3_, EnumFacing p_180656_4_) {
      if (!this.func_176406_l(p_180656_1_)) {
         return 0;
      } else {
         return p_180656_1_.func_177229_b(field_185512_D) == p_180656_4_ ? this.func_176408_a(p_180656_2_, p_180656_3_, p_180656_1_) : 0;
      }
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      if (this.func_176409_d(p_189540_2_, p_189540_3_)) {
         this.func_176398_g(p_189540_2_, p_189540_3_, p_189540_1_);
      } else {
         this.func_176226_b(p_189540_2_, p_189540_3_, p_189540_1_, 0);
         p_189540_2_.func_175698_g(p_189540_3_);

         for(EnumFacing enumfacing : EnumFacing.values()) {
            p_189540_2_.func_175685_c(p_189540_3_.func_177972_a(enumfacing), this, false);
         }

      }
   }

   protected void func_176398_g(World p_176398_1_, BlockPos p_176398_2_, IBlockState p_176398_3_) {
      if (!this.func_176405_b(p_176398_1_, p_176398_2_, p_176398_3_)) {
         boolean flag = this.func_176404_e(p_176398_1_, p_176398_2_, p_176398_3_);
         if (this.field_149914_a != flag && !p_176398_1_.func_175691_a(p_176398_2_, this)) {
            int i = -1;
            if (this.func_176402_i(p_176398_1_, p_176398_2_, p_176398_3_)) {
               i = -3;
            } else if (this.field_149914_a) {
               i = -2;
            }

            p_176398_1_.func_175654_a(p_176398_2_, this, this.func_176403_d(p_176398_3_), i);
         }

      }
   }

   public boolean func_176405_b(IBlockAccess p_176405_1_, BlockPos p_176405_2_, IBlockState p_176405_3_) {
      return false;
   }

   protected boolean func_176404_e(World p_176404_1_, BlockPos p_176404_2_, IBlockState p_176404_3_) {
      return this.func_176397_f(p_176404_1_, p_176404_2_, p_176404_3_) > 0;
   }

   protected int func_176397_f(World p_176397_1_, BlockPos p_176397_2_, IBlockState p_176397_3_) {
      EnumFacing enumfacing = (EnumFacing)p_176397_3_.func_177229_b(field_185512_D);
      BlockPos blockpos = p_176397_2_.func_177972_a(enumfacing);
      int i = p_176397_1_.func_175651_c(blockpos, enumfacing);
      if (i >= 15) {
         return i;
      } else {
         IBlockState iblockstate = p_176397_1_.func_180495_p(blockpos);
         return Math.max(i, iblockstate.func_177230_c() == Blocks.field_150488_af ? ((Integer)iblockstate.func_177229_b(BlockRedstoneWire.field_176351_O)).intValue() : 0);
      }
   }

   protected int func_176407_c(IBlockAccess p_176407_1_, BlockPos p_176407_2_, IBlockState p_176407_3_) {
      EnumFacing enumfacing = (EnumFacing)p_176407_3_.func_177229_b(field_185512_D);
      EnumFacing enumfacing1 = enumfacing.func_176746_e();
      EnumFacing enumfacing2 = enumfacing.func_176735_f();
      return Math.max(this.func_176401_c(p_176407_1_, p_176407_2_.func_177972_a(enumfacing1), enumfacing1), this.func_176401_c(p_176407_1_, p_176407_2_.func_177972_a(enumfacing2), enumfacing2));
   }

   protected int func_176401_c(IBlockAccess p_176401_1_, BlockPos p_176401_2_, EnumFacing p_176401_3_) {
      IBlockState iblockstate = p_176401_1_.func_180495_p(p_176401_2_);
      Block block = iblockstate.func_177230_c();
      if (this.func_185545_A(iblockstate)) {
         if (block == Blocks.field_150451_bX) {
            return 15;
         } else {
            return block == Blocks.field_150488_af ? ((Integer)iblockstate.func_177229_b(BlockRedstoneWire.field_176351_O)).intValue() : p_176401_1_.func_175627_a(p_176401_2_, p_176401_3_);
         }
      } else {
         return 0;
      }
   }

   public boolean func_149744_f(IBlockState p_149744_1_) {
      return true;
   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      return this.func_176223_P().func_177226_a(field_185512_D, p_180642_8_.func_174811_aO().func_176734_d());
   }

   public void func_180633_a(World p_180633_1_, BlockPos p_180633_2_, IBlockState p_180633_3_, EntityLivingBase p_180633_4_, ItemStack p_180633_5_) {
      if (this.func_176404_e(p_180633_1_, p_180633_2_, p_180633_3_)) {
         p_180633_1_.func_175684_a(p_180633_2_, this, 1);
      }

   }

   public void func_176213_c(World p_176213_1_, BlockPos p_176213_2_, IBlockState p_176213_3_) {
      this.func_176400_h(p_176213_1_, p_176213_2_, p_176213_3_);
   }

   protected void func_176400_h(World p_176400_1_, BlockPos p_176400_2_, IBlockState p_176400_3_) {
      EnumFacing enumfacing = (EnumFacing)p_176400_3_.func_177229_b(field_185512_D);
      BlockPos blockpos = p_176400_2_.func_177972_a(enumfacing.func_176734_d());
      p_176400_1_.func_190524_a(blockpos, this, p_176400_2_);
      p_176400_1_.func_175695_a(blockpos, this, enumfacing);
   }

   public void func_176206_d(World p_176206_1_, BlockPos p_176206_2_, IBlockState p_176206_3_) {
      if (this.field_149914_a) {
         for(EnumFacing enumfacing : EnumFacing.values()) {
            p_176206_1_.func_175685_c(p_176206_2_.func_177972_a(enumfacing), this, false);
         }
      }

      super.func_176206_d(p_176206_1_, p_176206_2_, p_176206_3_);
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   protected boolean func_185545_A(IBlockState p_185545_1_) {
      return p_185545_1_.func_185897_m();
   }

   protected int func_176408_a(IBlockAccess p_176408_1_, BlockPos p_176408_2_, IBlockState p_176408_3_) {
      return 15;
   }

   public static boolean func_185546_B(IBlockState p_185546_0_) {
      return Blocks.field_150413_aR.func_185547_C(p_185546_0_) || Blocks.field_150441_bU.func_185547_C(p_185546_0_);
   }

   public boolean func_185547_C(IBlockState p_185547_1_) {
      Block block = p_185547_1_.func_177230_c();
      return block == this.func_180674_e(this.func_176223_P()).func_177230_c() || block == this.func_180675_k(this.func_176223_P()).func_177230_c();
   }

   public boolean func_176402_i(World p_176402_1_, BlockPos p_176402_2_, IBlockState p_176402_3_) {
      EnumFacing enumfacing = ((EnumFacing)p_176402_3_.func_177229_b(field_185512_D)).func_176734_d();
      BlockPos blockpos = p_176402_2_.func_177972_a(enumfacing);
      if (func_185546_B(p_176402_1_.func_180495_p(blockpos))) {
         return p_176402_1_.func_180495_p(blockpos).func_177229_b(field_185512_D) != enumfacing;
      } else {
         return false;
      }
   }

   protected int func_176399_m(IBlockState p_176399_1_) {
      return this.func_176403_d(p_176399_1_);
   }

   protected abstract int func_176403_d(IBlockState var1);

   protected abstract IBlockState func_180674_e(IBlockState var1);

   protected abstract IBlockState func_180675_k(IBlockState var1);

   public boolean func_149667_c(Block p_149667_1_) {
      return this.func_185547_C(p_149667_1_.func_176223_P());
   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return p_193383_4_ == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
   }
}
