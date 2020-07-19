package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockLiquid extends Block {
   public static final PropertyInteger field_176367_b = PropertyInteger.func_177719_a("level", 0, 15);

   protected BlockLiquid(Material p_i45413_1_) {
      super(p_i45413_1_);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176367_b, Integer.valueOf(0)));
      this.func_149675_a(true);
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      return field_185505_j;
   }

   @Nullable
   public AxisAlignedBB func_180646_a(IBlockState p_180646_1_, IBlockAccess p_180646_2_, BlockPos p_180646_3_) {
      return field_185506_k;
   }

   public boolean func_176205_b(IBlockAccess p_176205_1_, BlockPos p_176205_2_) {
      return this.field_149764_J != Material.field_151587_i;
   }

   public static float func_149801_b(int p_149801_0_) {
      if (p_149801_0_ >= 8) {
         p_149801_0_ = 0;
      }

      return (float)(p_149801_0_ + 1) / 9.0F;
   }

   protected int func_189542_i(IBlockState p_189542_1_) {
      return p_189542_1_.func_185904_a() == this.field_149764_J ? ((Integer)p_189542_1_.func_177229_b(field_176367_b)).intValue() : -1;
   }

   protected int func_189545_x(IBlockState p_189545_1_) {
      int i = this.func_189542_i(p_189545_1_);
      return i >= 8 ? 0 : i;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_176209_a(IBlockState p_176209_1_, boolean p_176209_2_) {
      return p_176209_2_ && ((Integer)p_176209_1_.func_177229_b(field_176367_b)).intValue() == 0;
   }

   private boolean func_176212_b(IBlockAccess p_176212_1_, BlockPos p_176212_2_, EnumFacing p_176212_3_) {
      IBlockState iblockstate = p_176212_1_.func_180495_p(p_176212_2_);
      Block block = iblockstate.func_177230_c();
      Material material = iblockstate.func_185904_a();
      if (material == this.field_149764_J) {
         return false;
      } else if (p_176212_3_ == EnumFacing.UP) {
         return true;
      } else if (material == Material.field_151588_w) {
         return false;
      } else {
         boolean flag = func_193382_c(block) || block instanceof BlockStairs;
         return !flag && iblockstate.func_193401_d(p_176212_1_, p_176212_2_, p_176212_3_) == BlockFaceShape.SOLID;
      }
   }

   public boolean func_176225_a(IBlockState p_176225_1_, IBlockAccess p_176225_2_, BlockPos p_176225_3_, EnumFacing p_176225_4_) {
      if (p_176225_2_.func_180495_p(p_176225_3_.func_177972_a(p_176225_4_)).func_185904_a() == this.field_149764_J) {
         return false;
      } else {
         return p_176225_4_ == EnumFacing.UP ? true : super.func_176225_a(p_176225_1_, p_176225_2_, p_176225_3_, p_176225_4_);
      }
   }

   public boolean func_176364_g(IBlockAccess p_176364_1_, BlockPos p_176364_2_) {
      for(int i = -1; i <= 1; ++i) {
         for(int j = -1; j <= 1; ++j) {
            IBlockState iblockstate = p_176364_1_.func_180495_p(p_176364_2_.func_177982_a(i, 0, j));
            if (iblockstate.func_185904_a() != this.field_149764_J && !iblockstate.func_185913_b()) {
               return true;
            }
         }
      }

      return false;
   }

   public EnumBlockRenderType func_149645_b(IBlockState p_149645_1_) {
      return EnumBlockRenderType.LIQUID;
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Items.field_190931_a;
   }

   public int func_149745_a(Random p_149745_1_) {
      return 0;
   }

   protected Vec3d func_189543_a(IBlockAccess p_189543_1_, BlockPos p_189543_2_, IBlockState p_189543_3_) {
      double d0 = 0.0D;
      double d1 = 0.0D;
      double d2 = 0.0D;
      int i = this.func_189545_x(p_189543_3_);
      BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.func_185346_s();

      for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
         blockpos$pooledmutableblockpos.func_189533_g(p_189543_2_).func_189536_c(enumfacing);
         int j = this.func_189545_x(p_189543_1_.func_180495_p(blockpos$pooledmutableblockpos));
         if (j < 0) {
            if (!p_189543_1_.func_180495_p(blockpos$pooledmutableblockpos).func_185904_a().func_76230_c()) {
               j = this.func_189545_x(p_189543_1_.func_180495_p(blockpos$pooledmutableblockpos.func_177977_b()));
               if (j >= 0) {
                  int k = j - (i - 8);
                  d0 += (double)(enumfacing.func_82601_c() * k);
                  d1 += (double)(enumfacing.func_96559_d() * k);
                  d2 += (double)(enumfacing.func_82599_e() * k);
               }
            }
         } else if (j >= 0) {
            int l = j - i;
            d0 += (double)(enumfacing.func_82601_c() * l);
            d1 += (double)(enumfacing.func_96559_d() * l);
            d2 += (double)(enumfacing.func_82599_e() * l);
         }
      }

      Vec3d vec3d = new Vec3d(d0, d1, d2);
      if (((Integer)p_189543_3_.func_177229_b(field_176367_b)).intValue() >= 8) {
         for(EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL) {
            blockpos$pooledmutableblockpos.func_189533_g(p_189543_2_).func_189536_c(enumfacing1);
            if (this.func_176212_b(p_189543_1_, blockpos$pooledmutableblockpos, enumfacing1) || this.func_176212_b(p_189543_1_, blockpos$pooledmutableblockpos.func_177984_a(), enumfacing1)) {
               vec3d = vec3d.func_72432_b().func_72441_c(0.0D, -6.0D, 0.0D);
               break;
            }
         }
      }

      blockpos$pooledmutableblockpos.func_185344_t();
      return vec3d.func_72432_b();
   }

   public Vec3d func_176197_a(World p_176197_1_, BlockPos p_176197_2_, Entity p_176197_3_, Vec3d p_176197_4_) {
      return p_176197_4_.func_178787_e(this.func_189543_a(p_176197_1_, p_176197_2_, p_176197_1_.func_180495_p(p_176197_2_)));
   }

   public int func_149738_a(World p_149738_1_) {
      if (this.field_149764_J == Material.field_151586_h) {
         return 5;
      } else if (this.field_149764_J == Material.field_151587_i) {
         return p_149738_1_.field_73011_w.func_177495_o() ? 10 : 30;
      } else {
         return 0;
      }
   }

   public int func_185484_c(IBlockState p_185484_1_, IBlockAccess p_185484_2_, BlockPos p_185484_3_) {
      int i = p_185484_2_.func_175626_b(p_185484_3_, 0);
      int j = p_185484_2_.func_175626_b(p_185484_3_.func_177984_a(), 0);
      int k = i & 255;
      int l = j & 255;
      int i1 = i >> 16 & 255;
      int j1 = j >> 16 & 255;
      return (k > l ? k : l) | (i1 > j1 ? i1 : j1) << 16;
   }

   public BlockRenderLayer func_180664_k() {
      return this.field_149764_J == Material.field_151586_h ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.SOLID;
   }

   public void func_180655_c(IBlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
      double d0 = (double)p_180655_3_.func_177958_n();
      double d1 = (double)p_180655_3_.func_177956_o();
      double d2 = (double)p_180655_3_.func_177952_p();
      if (this.field_149764_J == Material.field_151586_h) {
         int i = ((Integer)p_180655_1_.func_177229_b(field_176367_b)).intValue();
         if (i > 0 && i < 8) {
            if (p_180655_4_.nextInt(64) == 0) {
               p_180655_2_.func_184134_a(d0 + 0.5D, d1 + 0.5D, d2 + 0.5D, SoundEvents.field_187917_gq, SoundCategory.BLOCKS, p_180655_4_.nextFloat() * 0.25F + 0.75F, p_180655_4_.nextFloat() + 0.5F, false);
            }
         } else if (p_180655_4_.nextInt(10) == 0) {
            p_180655_2_.func_175688_a(EnumParticleTypes.SUSPENDED, d0 + (double)p_180655_4_.nextFloat(), d1 + (double)p_180655_4_.nextFloat(), d2 + (double)p_180655_4_.nextFloat(), 0.0D, 0.0D, 0.0D);
         }
      }

      if (this.field_149764_J == Material.field_151587_i && p_180655_2_.func_180495_p(p_180655_3_.func_177984_a()).func_185904_a() == Material.field_151579_a && !p_180655_2_.func_180495_p(p_180655_3_.func_177984_a()).func_185914_p()) {
         if (p_180655_4_.nextInt(100) == 0) {
            double d8 = d0 + (double)p_180655_4_.nextFloat();
            double d4 = d1 + p_180655_1_.func_185900_c(p_180655_2_, p_180655_3_).field_72337_e;
            double d6 = d2 + (double)p_180655_4_.nextFloat();
            p_180655_2_.func_175688_a(EnumParticleTypes.LAVA, d8, d4, d6, 0.0D, 0.0D, 0.0D);
            p_180655_2_.func_184134_a(d8, d4, d6, SoundEvents.field_187662_cZ, SoundCategory.BLOCKS, 0.2F + p_180655_4_.nextFloat() * 0.2F, 0.9F + p_180655_4_.nextFloat() * 0.15F, false);
         }

         if (p_180655_4_.nextInt(200) == 0) {
            p_180655_2_.func_184134_a(d0, d1, d2, SoundEvents.field_187656_cX, SoundCategory.BLOCKS, 0.2F + p_180655_4_.nextFloat() * 0.2F, 0.9F + p_180655_4_.nextFloat() * 0.15F, false);
         }
      }

      if (p_180655_4_.nextInt(10) == 0 && p_180655_2_.func_180495_p(p_180655_3_.func_177977_b()).func_185896_q()) {
         Material material = p_180655_2_.func_180495_p(p_180655_3_.func_177979_c(2)).func_185904_a();
         if (!material.func_76230_c() && !material.func_76224_d()) {
            double d3 = d0 + (double)p_180655_4_.nextFloat();
            double d5 = d1 - 1.05D;
            double d7 = d2 + (double)p_180655_4_.nextFloat();
            if (this.field_149764_J == Material.field_151586_h) {
               p_180655_2_.func_175688_a(EnumParticleTypes.DRIP_WATER, d3, d5, d7, 0.0D, 0.0D, 0.0D);
            } else {
               p_180655_2_.func_175688_a(EnumParticleTypes.DRIP_LAVA, d3, d5, d7, 0.0D, 0.0D, 0.0D);
            }
         }
      }

   }

   public static float func_189544_a(IBlockAccess p_189544_0_, BlockPos p_189544_1_, Material p_189544_2_, IBlockState p_189544_3_) {
      Vec3d vec3d = func_176361_a(p_189544_2_).func_189543_a(p_189544_0_, p_189544_1_, p_189544_3_);
      return vec3d.field_72450_a == 0.0D && vec3d.field_72449_c == 0.0D ? -1000.0F : (float)MathHelper.func_181159_b(vec3d.field_72449_c, vec3d.field_72450_a) - 1.5707964F;
   }

   public void func_176213_c(World p_176213_1_, BlockPos p_176213_2_, IBlockState p_176213_3_) {
      this.func_176365_e(p_176213_1_, p_176213_2_, p_176213_3_);
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      this.func_176365_e(p_189540_2_, p_189540_3_, p_189540_1_);
   }

   public boolean func_176365_e(World p_176365_1_, BlockPos p_176365_2_, IBlockState p_176365_3_) {
      if (this.field_149764_J == Material.field_151587_i) {
         boolean flag = false;

         for(EnumFacing enumfacing : EnumFacing.values()) {
            if (enumfacing != EnumFacing.DOWN && p_176365_1_.func_180495_p(p_176365_2_.func_177972_a(enumfacing)).func_185904_a() == Material.field_151586_h) {
               flag = true;
               break;
            }
         }

         if (flag) {
            Integer integer = (Integer)p_176365_3_.func_177229_b(field_176367_b);
            if (integer.intValue() == 0) {
               p_176365_1_.func_175656_a(p_176365_2_, Blocks.field_150343_Z.func_176223_P());
               this.func_180688_d(p_176365_1_, p_176365_2_);
               return true;
            }

            if (integer.intValue() <= 4) {
               p_176365_1_.func_175656_a(p_176365_2_, Blocks.field_150347_e.func_176223_P());
               this.func_180688_d(p_176365_1_, p_176365_2_);
               return true;
            }
         }
      }

      return false;
   }

   protected void func_180688_d(World p_180688_1_, BlockPos p_180688_2_) {
      double d0 = (double)p_180688_2_.func_177958_n();
      double d1 = (double)p_180688_2_.func_177956_o();
      double d2 = (double)p_180688_2_.func_177952_p();
      p_180688_1_.func_184133_a((EntityPlayer)null, p_180688_2_, SoundEvents.field_187659_cY, SoundCategory.BLOCKS, 0.5F, 2.6F + (p_180688_1_.field_73012_v.nextFloat() - p_180688_1_.field_73012_v.nextFloat()) * 0.8F);

      for(int i = 0; i < 8; ++i) {
         p_180688_1_.func_175688_a(EnumParticleTypes.SMOKE_LARGE, d0 + Math.random(), d1 + 1.2D, d2 + Math.random(), 0.0D, 0.0D, 0.0D);
      }

   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176367_b, Integer.valueOf(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((Integer)p_176201_1_.func_177229_b(field_176367_b)).intValue();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176367_b});
   }

   public static BlockDynamicLiquid func_176361_a(Material p_176361_0_) {
      if (p_176361_0_ == Material.field_151586_h) {
         return Blocks.field_150358_i;
      } else if (p_176361_0_ == Material.field_151587_i) {
         return Blocks.field_150356_k;
      } else {
         throw new IllegalArgumentException("Invalid material");
      }
   }

   public static BlockStaticLiquid func_176363_b(Material p_176363_0_) {
      if (p_176363_0_ == Material.field_151586_h) {
         return Blocks.field_150355_j;
      } else if (p_176363_0_ == Material.field_151587_i) {
         return Blocks.field_150353_l;
      } else {
         throw new IllegalArgumentException("Invalid material");
      }
   }

   public static float func_190973_f(IBlockState p_190973_0_, IBlockAccess p_190973_1_, BlockPos p_190973_2_) {
      int i = ((Integer)p_190973_0_.func_177229_b(field_176367_b)).intValue();
      return (i & 7) == 0 && p_190973_1_.func_180495_p(p_190973_2_.func_177984_a()).func_185904_a() == Material.field_151586_h ? 1.0F : 1.0F - func_149801_b(i);
   }

   public static float func_190972_g(IBlockState p_190972_0_, IBlockAccess p_190972_1_, BlockPos p_190972_2_) {
      return (float)p_190972_2_.func_177956_o() + func_190973_f(p_190972_0_, p_190972_1_, p_190972_2_);
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }
}
