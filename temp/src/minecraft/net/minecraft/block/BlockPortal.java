package net.minecraft.block;

import com.google.common.cache.LoadingCache;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPortal extends BlockBreakable {
   public static final PropertyEnum<EnumFacing.Axis> field_176550_a = PropertyEnum.<EnumFacing.Axis>func_177706_a("axis", EnumFacing.Axis.class, EnumFacing.Axis.X, EnumFacing.Axis.Z);
   protected static final AxisAlignedBB field_185683_b = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D);
   protected static final AxisAlignedBB field_185684_c = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D);
   protected static final AxisAlignedBB field_185685_d = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);

   public BlockPortal() {
      super(Material.field_151567_E, false);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176550_a, EnumFacing.Axis.X));
      this.func_149675_a(true);
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      switch((EnumFacing.Axis)p_185496_1_.func_177229_b(field_176550_a)) {
      case X:
         return field_185683_b;
      case Y:
      default:
         return field_185685_d;
      case Z:
         return field_185684_c;
      }
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      super.func_180650_b(p_180650_1_, p_180650_2_, p_180650_3_, p_180650_4_);
      if (p_180650_1_.field_73011_w.func_76569_d() && p_180650_1_.func_82736_K().func_82766_b("doMobSpawning") && p_180650_4_.nextInt(2000) < p_180650_1_.func_175659_aa().func_151525_a()) {
         int i = p_180650_2_.func_177956_o();

         BlockPos blockpos;
         for(blockpos = p_180650_2_; !p_180650_1_.func_180495_p(blockpos).func_185896_q() && blockpos.func_177956_o() > 0; blockpos = blockpos.func_177977_b()) {
            ;
         }

         if (i > 0 && !p_180650_1_.func_180495_p(blockpos.func_177984_a()).func_185915_l()) {
            Entity entity = ItemMonsterPlacer.func_77840_a(p_180650_1_, EntityList.func_191306_a(EntityPigZombie.class), (double)blockpos.func_177958_n() + 0.5D, (double)blockpos.func_177956_o() + 1.1D, (double)blockpos.func_177952_p() + 0.5D);
            if (entity != null) {
               entity.field_71088_bW = entity.func_82147_ab();
            }
         }
      }

   }

   @Nullable
   public AxisAlignedBB func_180646_a(IBlockState p_180646_1_, IBlockAccess p_180646_2_, BlockPos p_180646_3_) {
      return field_185506_k;
   }

   public static int func_176549_a(EnumFacing.Axis p_176549_0_) {
      if (p_176549_0_ == EnumFacing.Axis.X) {
         return 1;
      } else {
         return p_176549_0_ == EnumFacing.Axis.Z ? 2 : 0;
      }
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_176548_d(World p_176548_1_, BlockPos p_176548_2_) {
      BlockPortal.Size blockportal$size = new BlockPortal.Size(p_176548_1_, p_176548_2_, EnumFacing.Axis.X);
      if (blockportal$size.func_150860_b() && blockportal$size.field_150864_e == 0) {
         blockportal$size.func_150859_c();
         return true;
      } else {
         BlockPortal.Size blockportal$size1 = new BlockPortal.Size(p_176548_1_, p_176548_2_, EnumFacing.Axis.Z);
         if (blockportal$size1.func_150860_b() && blockportal$size1.field_150864_e == 0) {
            blockportal$size1.func_150859_c();
            return true;
         } else {
            return false;
         }
      }
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      EnumFacing.Axis enumfacing$axis = (EnumFacing.Axis)p_189540_1_.func_177229_b(field_176550_a);
      if (enumfacing$axis == EnumFacing.Axis.X) {
         BlockPortal.Size blockportal$size = new BlockPortal.Size(p_189540_2_, p_189540_3_, EnumFacing.Axis.X);
         if (!blockportal$size.func_150860_b() || blockportal$size.field_150864_e < blockportal$size.field_150868_h * blockportal$size.field_150862_g) {
            p_189540_2_.func_175656_a(p_189540_3_, Blocks.field_150350_a.func_176223_P());
         }
      } else if (enumfacing$axis == EnumFacing.Axis.Z) {
         BlockPortal.Size blockportal$size1 = new BlockPortal.Size(p_189540_2_, p_189540_3_, EnumFacing.Axis.Z);
         if (!blockportal$size1.func_150860_b() || blockportal$size1.field_150864_e < blockportal$size1.field_150868_h * blockportal$size1.field_150862_g) {
            p_189540_2_.func_175656_a(p_189540_3_, Blocks.field_150350_a.func_176223_P());
         }
      }

   }

   public boolean func_176225_a(IBlockState p_176225_1_, IBlockAccess p_176225_2_, BlockPos p_176225_3_, EnumFacing p_176225_4_) {
      p_176225_3_ = p_176225_3_.func_177972_a(p_176225_4_);
      EnumFacing.Axis enumfacing$axis = null;
      if (p_176225_1_.func_177230_c() == this) {
         enumfacing$axis = (EnumFacing.Axis)p_176225_1_.func_177229_b(field_176550_a);
         if (enumfacing$axis == null) {
            return false;
         }

         if (enumfacing$axis == EnumFacing.Axis.Z && p_176225_4_ != EnumFacing.EAST && p_176225_4_ != EnumFacing.WEST) {
            return false;
         }

         if (enumfacing$axis == EnumFacing.Axis.X && p_176225_4_ != EnumFacing.SOUTH && p_176225_4_ != EnumFacing.NORTH) {
            return false;
         }
      }

      boolean flag = p_176225_2_.func_180495_p(p_176225_3_.func_177976_e()).func_177230_c() == this && p_176225_2_.func_180495_p(p_176225_3_.func_177985_f(2)).func_177230_c() != this;
      boolean flag1 = p_176225_2_.func_180495_p(p_176225_3_.func_177974_f()).func_177230_c() == this && p_176225_2_.func_180495_p(p_176225_3_.func_177965_g(2)).func_177230_c() != this;
      boolean flag2 = p_176225_2_.func_180495_p(p_176225_3_.func_177978_c()).func_177230_c() == this && p_176225_2_.func_180495_p(p_176225_3_.func_177964_d(2)).func_177230_c() != this;
      boolean flag3 = p_176225_2_.func_180495_p(p_176225_3_.func_177968_d()).func_177230_c() == this && p_176225_2_.func_180495_p(p_176225_3_.func_177970_e(2)).func_177230_c() != this;
      boolean flag4 = flag || flag1 || enumfacing$axis == EnumFacing.Axis.X;
      boolean flag5 = flag2 || flag3 || enumfacing$axis == EnumFacing.Axis.Z;
      if (flag4 && p_176225_4_ == EnumFacing.WEST) {
         return true;
      } else if (flag4 && p_176225_4_ == EnumFacing.EAST) {
         return true;
      } else if (flag5 && p_176225_4_ == EnumFacing.NORTH) {
         return true;
      } else {
         return flag5 && p_176225_4_ == EnumFacing.SOUTH;
      }
   }

   public int func_149745_a(Random p_149745_1_) {
      return 0;
   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   public void func_180634_a(World p_180634_1_, BlockPos p_180634_2_, IBlockState p_180634_3_, Entity p_180634_4_) {
      if (!p_180634_4_.func_184218_aH() && !p_180634_4_.func_184207_aI() && p_180634_4_.func_184222_aU()) {
         p_180634_4_.func_181015_d(p_180634_2_);
      }

   }

   public void func_180655_c(IBlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
      if (p_180655_4_.nextInt(100) == 0) {
         p_180655_2_.func_184134_a((double)p_180655_3_.func_177958_n() + 0.5D, (double)p_180655_3_.func_177956_o() + 0.5D, (double)p_180655_3_.func_177952_p() + 0.5D, SoundEvents.field_187810_eg, SoundCategory.BLOCKS, 0.5F, p_180655_4_.nextFloat() * 0.4F + 0.8F, false);
      }

      for(int i = 0; i < 4; ++i) {
         double d0 = (double)((float)p_180655_3_.func_177958_n() + p_180655_4_.nextFloat());
         double d1 = (double)((float)p_180655_3_.func_177956_o() + p_180655_4_.nextFloat());
         double d2 = (double)((float)p_180655_3_.func_177952_p() + p_180655_4_.nextFloat());
         double d3 = ((double)p_180655_4_.nextFloat() - 0.5D) * 0.5D;
         double d4 = ((double)p_180655_4_.nextFloat() - 0.5D) * 0.5D;
         double d5 = ((double)p_180655_4_.nextFloat() - 0.5D) * 0.5D;
         int j = p_180655_4_.nextInt(2) * 2 - 1;
         if (p_180655_2_.func_180495_p(p_180655_3_.func_177976_e()).func_177230_c() != this && p_180655_2_.func_180495_p(p_180655_3_.func_177974_f()).func_177230_c() != this) {
            d0 = (double)p_180655_3_.func_177958_n() + 0.5D + 0.25D * (double)j;
            d3 = (double)(p_180655_4_.nextFloat() * 2.0F * (float)j);
         } else {
            d2 = (double)p_180655_3_.func_177952_p() + 0.5D + 0.25D * (double)j;
            d5 = (double)(p_180655_4_.nextFloat() * 2.0F * (float)j);
         }

         p_180655_2_.func_175688_a(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
      }

   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return ItemStack.field_190927_a;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176550_a, (p_176203_1_ & 3) == 2 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return func_176549_a((EnumFacing.Axis)p_176201_1_.func_177229_b(field_176550_a));
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      switch(p_185499_2_) {
      case COUNTERCLOCKWISE_90:
      case CLOCKWISE_90:
         switch((EnumFacing.Axis)p_185499_1_.func_177229_b(field_176550_a)) {
         case X:
            return p_185499_1_.func_177226_a(field_176550_a, EnumFacing.Axis.Z);
         case Z:
            return p_185499_1_.func_177226_a(field_176550_a, EnumFacing.Axis.X);
         default:
            return p_185499_1_;
         }
      default:
         return p_185499_1_;
      }
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176550_a});
   }

   public BlockPattern.PatternHelper func_181089_f(World p_181089_1_, BlockPos p_181089_2_) {
      EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.Z;
      BlockPortal.Size blockportal$size = new BlockPortal.Size(p_181089_1_, p_181089_2_, EnumFacing.Axis.X);
      LoadingCache<BlockPos, BlockWorldState> loadingcache = BlockPattern.func_181627_a(p_181089_1_, true);
      if (!blockportal$size.func_150860_b()) {
         enumfacing$axis = EnumFacing.Axis.X;
         blockportal$size = new BlockPortal.Size(p_181089_1_, p_181089_2_, EnumFacing.Axis.Z);
      }

      if (!blockportal$size.func_150860_b()) {
         return new BlockPattern.PatternHelper(p_181089_2_, EnumFacing.NORTH, EnumFacing.UP, loadingcache, 1, 1, 1);
      } else {
         int[] aint = new int[EnumFacing.AxisDirection.values().length];
         EnumFacing enumfacing = blockportal$size.field_150866_c.func_176735_f();
         BlockPos blockpos = blockportal$size.field_150861_f.func_177981_b(blockportal$size.func_181100_a() - 1);

         for(EnumFacing.AxisDirection enumfacing$axisdirection : EnumFacing.AxisDirection.values()) {
            BlockPattern.PatternHelper blockpattern$patternhelper = new BlockPattern.PatternHelper(enumfacing.func_176743_c() == enumfacing$axisdirection ? blockpos : blockpos.func_177967_a(blockportal$size.field_150866_c, blockportal$size.func_181101_b() - 1), EnumFacing.func_181076_a(enumfacing$axisdirection, enumfacing$axis), EnumFacing.UP, loadingcache, blockportal$size.func_181101_b(), blockportal$size.func_181100_a(), 1);

            for(int i = 0; i < blockportal$size.func_181101_b(); ++i) {
               for(int j = 0; j < blockportal$size.func_181100_a(); ++j) {
                  BlockWorldState blockworldstate = blockpattern$patternhelper.func_177670_a(i, j, 1);
                  if (blockworldstate.func_177509_a() != null && blockworldstate.func_177509_a().func_185904_a() != Material.field_151579_a) {
                     ++aint[enumfacing$axisdirection.ordinal()];
                  }
               }
            }
         }

         EnumFacing.AxisDirection enumfacing$axisdirection1 = EnumFacing.AxisDirection.POSITIVE;

         for(EnumFacing.AxisDirection enumfacing$axisdirection2 : EnumFacing.AxisDirection.values()) {
            if (aint[enumfacing$axisdirection2.ordinal()] < aint[enumfacing$axisdirection1.ordinal()]) {
               enumfacing$axisdirection1 = enumfacing$axisdirection2;
            }
         }

         return new BlockPattern.PatternHelper(enumfacing.func_176743_c() == enumfacing$axisdirection1 ? blockpos : blockpos.func_177967_a(blockportal$size.field_150866_c, blockportal$size.func_181101_b() - 1), EnumFacing.func_181076_a(enumfacing$axisdirection1, enumfacing$axis), EnumFacing.UP, loadingcache, blockportal$size.func_181101_b(), blockportal$size.func_181100_a(), 1);
      }
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }

   public static class Size {
      private final World field_150867_a;
      private final EnumFacing.Axis field_150865_b;
      private final EnumFacing field_150866_c;
      private final EnumFacing field_150863_d;
      private int field_150864_e;
      private BlockPos field_150861_f;
      private int field_150862_g;
      private int field_150868_h;

      public Size(World p_i45694_1_, BlockPos p_i45694_2_, EnumFacing.Axis p_i45694_3_) {
         this.field_150867_a = p_i45694_1_;
         this.field_150865_b = p_i45694_3_;
         if (p_i45694_3_ == EnumFacing.Axis.X) {
            this.field_150863_d = EnumFacing.EAST;
            this.field_150866_c = EnumFacing.WEST;
         } else {
            this.field_150863_d = EnumFacing.NORTH;
            this.field_150866_c = EnumFacing.SOUTH;
         }

         for(BlockPos blockpos = p_i45694_2_; p_i45694_2_.func_177956_o() > blockpos.func_177956_o() - 21 && p_i45694_2_.func_177956_o() > 0 && this.func_150857_a(p_i45694_1_.func_180495_p(p_i45694_2_.func_177977_b()).func_177230_c()); p_i45694_2_ = p_i45694_2_.func_177977_b()) {
            ;
         }

         int i = this.func_180120_a(p_i45694_2_, this.field_150863_d) - 1;
         if (i >= 0) {
            this.field_150861_f = p_i45694_2_.func_177967_a(this.field_150863_d, i);
            this.field_150868_h = this.func_180120_a(this.field_150861_f, this.field_150866_c);
            if (this.field_150868_h < 2 || this.field_150868_h > 21) {
               this.field_150861_f = null;
               this.field_150868_h = 0;
            }
         }

         if (this.field_150861_f != null) {
            this.field_150862_g = this.func_150858_a();
         }

      }

      protected int func_180120_a(BlockPos p_180120_1_, EnumFacing p_180120_2_) {
         int i;
         for(i = 0; i < 22; ++i) {
            BlockPos blockpos = p_180120_1_.func_177967_a(p_180120_2_, i);
            if (!this.func_150857_a(this.field_150867_a.func_180495_p(blockpos).func_177230_c()) || this.field_150867_a.func_180495_p(blockpos.func_177977_b()).func_177230_c() != Blocks.field_150343_Z) {
               break;
            }
         }

         Block block = this.field_150867_a.func_180495_p(p_180120_1_.func_177967_a(p_180120_2_, i)).func_177230_c();
         return block == Blocks.field_150343_Z ? i : 0;
      }

      public int func_181100_a() {
         return this.field_150862_g;
      }

      public int func_181101_b() {
         return this.field_150868_h;
      }

      protected int func_150858_a() {
         label56:
         for(this.field_150862_g = 0; this.field_150862_g < 21; ++this.field_150862_g) {
            for(int i = 0; i < this.field_150868_h; ++i) {
               BlockPos blockpos = this.field_150861_f.func_177967_a(this.field_150866_c, i).func_177981_b(this.field_150862_g);
               Block block = this.field_150867_a.func_180495_p(blockpos).func_177230_c();
               if (!this.func_150857_a(block)) {
                  break label56;
               }

               if (block == Blocks.field_150427_aO) {
                  ++this.field_150864_e;
               }

               if (i == 0) {
                  block = this.field_150867_a.func_180495_p(blockpos.func_177972_a(this.field_150863_d)).func_177230_c();
                  if (block != Blocks.field_150343_Z) {
                     break label56;
                  }
               } else if (i == this.field_150868_h - 1) {
                  block = this.field_150867_a.func_180495_p(blockpos.func_177972_a(this.field_150866_c)).func_177230_c();
                  if (block != Blocks.field_150343_Z) {
                     break label56;
                  }
               }
            }
         }

         for(int j = 0; j < this.field_150868_h; ++j) {
            if (this.field_150867_a.func_180495_p(this.field_150861_f.func_177967_a(this.field_150866_c, j).func_177981_b(this.field_150862_g)).func_177230_c() != Blocks.field_150343_Z) {
               this.field_150862_g = 0;
               break;
            }
         }

         if (this.field_150862_g <= 21 && this.field_150862_g >= 3) {
            return this.field_150862_g;
         } else {
            this.field_150861_f = null;
            this.field_150868_h = 0;
            this.field_150862_g = 0;
            return 0;
         }
      }

      protected boolean func_150857_a(Block p_150857_1_) {
         return p_150857_1_.field_149764_J == Material.field_151579_a || p_150857_1_ == Blocks.field_150480_ab || p_150857_1_ == Blocks.field_150427_aO;
      }

      public boolean func_150860_b() {
         return this.field_150861_f != null && this.field_150868_h >= 2 && this.field_150868_h <= 21 && this.field_150862_g >= 3 && this.field_150862_g <= 21;
      }

      public void func_150859_c() {
         for(int i = 0; i < this.field_150868_h; ++i) {
            BlockPos blockpos = this.field_150861_f.func_177967_a(this.field_150866_c, i);

            for(int j = 0; j < this.field_150862_g; ++j) {
               this.field_150867_a.func_180501_a(blockpos.func_177981_b(j), Blocks.field_150427_aO.func_176223_P().func_177226_a(BlockPortal.field_176550_a, this.field_150865_b), 2);
            }
         }

      }
   }
}
