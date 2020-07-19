package net.minecraft.world.gen.structure;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.TemplateManager;

public abstract class StructureComponent {
   protected StructureBoundingBox field_74887_e;
   @Nullable
   private EnumFacing field_74885_f;
   private Mirror field_186168_b;
   private Rotation field_186169_c;
   protected int field_74886_g;

   public StructureComponent() {
   }

   protected StructureComponent(int p_i2091_1_) {
      this.field_74886_g = p_i2091_1_;
   }

   public final NBTTagCompound func_143010_b() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.func_74778_a("id", MapGenStructureIO.func_143036_a(this));
      nbttagcompound.func_74782_a("BB", this.field_74887_e.func_151535_h());
      EnumFacing enumfacing = this.func_186165_e();
      nbttagcompound.func_74768_a("O", enumfacing == null ? -1 : enumfacing.func_176736_b());
      nbttagcompound.func_74768_a("GD", this.field_74886_g);
      this.func_143012_a(nbttagcompound);
      return nbttagcompound;
   }

   protected abstract void func_143012_a(NBTTagCompound var1);

   public void func_143009_a(World p_143009_1_, NBTTagCompound p_143009_2_) {
      if (p_143009_2_.func_74764_b("BB")) {
         this.field_74887_e = new StructureBoundingBox(p_143009_2_.func_74759_k("BB"));
      }

      int i = p_143009_2_.func_74762_e("O");
      this.func_186164_a(i == -1 ? null : EnumFacing.func_176731_b(i));
      this.field_74886_g = p_143009_2_.func_74762_e("GD");
      this.func_143011_b(p_143009_2_, p_143009_1_.func_72860_G().func_186340_h());
   }

   protected abstract void func_143011_b(NBTTagCompound var1, TemplateManager var2);

   public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
   }

   public abstract boolean func_74875_a(World var1, Random var2, StructureBoundingBox var3);

   public StructureBoundingBox func_74874_b() {
      return this.field_74887_e;
   }

   public int func_74877_c() {
      return this.field_74886_g;
   }

   public static StructureComponent func_74883_a(List<StructureComponent> p_74883_0_, StructureBoundingBox p_74883_1_) {
      for(StructureComponent structurecomponent : p_74883_0_) {
         if (structurecomponent.func_74874_b() != null && structurecomponent.func_74874_b().func_78884_a(p_74883_1_)) {
            return structurecomponent;
         }
      }

      return null;
   }

   protected boolean func_74860_a(World p_74860_1_, StructureBoundingBox p_74860_2_) {
      int i = Math.max(this.field_74887_e.field_78897_a - 1, p_74860_2_.field_78897_a);
      int j = Math.max(this.field_74887_e.field_78895_b - 1, p_74860_2_.field_78895_b);
      int k = Math.max(this.field_74887_e.field_78896_c - 1, p_74860_2_.field_78896_c);
      int l = Math.min(this.field_74887_e.field_78893_d + 1, p_74860_2_.field_78893_d);
      int i1 = Math.min(this.field_74887_e.field_78894_e + 1, p_74860_2_.field_78894_e);
      int j1 = Math.min(this.field_74887_e.field_78892_f + 1, p_74860_2_.field_78892_f);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int k1 = i; k1 <= l; ++k1) {
         for(int l1 = k; l1 <= j1; ++l1) {
            if (p_74860_1_.func_180495_p(blockpos$mutableblockpos.func_181079_c(k1, j, l1)).func_185904_a().func_76224_d()) {
               return true;
            }

            if (p_74860_1_.func_180495_p(blockpos$mutableblockpos.func_181079_c(k1, i1, l1)).func_185904_a().func_76224_d()) {
               return true;
            }
         }
      }

      for(int i2 = i; i2 <= l; ++i2) {
         for(int k2 = j; k2 <= i1; ++k2) {
            if (p_74860_1_.func_180495_p(blockpos$mutableblockpos.func_181079_c(i2, k2, k)).func_185904_a().func_76224_d()) {
               return true;
            }

            if (p_74860_1_.func_180495_p(blockpos$mutableblockpos.func_181079_c(i2, k2, j1)).func_185904_a().func_76224_d()) {
               return true;
            }
         }
      }

      for(int j2 = k; j2 <= j1; ++j2) {
         for(int l2 = j; l2 <= i1; ++l2) {
            if (p_74860_1_.func_180495_p(blockpos$mutableblockpos.func_181079_c(i, l2, j2)).func_185904_a().func_76224_d()) {
               return true;
            }

            if (p_74860_1_.func_180495_p(blockpos$mutableblockpos.func_181079_c(l, l2, j2)).func_185904_a().func_76224_d()) {
               return true;
            }
         }
      }

      return false;
   }

   protected int func_74865_a(int p_74865_1_, int p_74865_2_) {
      EnumFacing enumfacing = this.func_186165_e();
      if (enumfacing == null) {
         return p_74865_1_;
      } else {
         switch(enumfacing) {
         case NORTH:
         case SOUTH:
            return this.field_74887_e.field_78897_a + p_74865_1_;
         case WEST:
            return this.field_74887_e.field_78893_d - p_74865_2_;
         case EAST:
            return this.field_74887_e.field_78897_a + p_74865_2_;
         default:
            return p_74865_1_;
         }
      }
   }

   protected int func_74862_a(int p_74862_1_) {
      return this.func_186165_e() == null ? p_74862_1_ : p_74862_1_ + this.field_74887_e.field_78895_b;
   }

   protected int func_74873_b(int p_74873_1_, int p_74873_2_) {
      EnumFacing enumfacing = this.func_186165_e();
      if (enumfacing == null) {
         return p_74873_2_;
      } else {
         switch(enumfacing) {
         case NORTH:
            return this.field_74887_e.field_78892_f - p_74873_2_;
         case SOUTH:
            return this.field_74887_e.field_78896_c + p_74873_2_;
         case WEST:
         case EAST:
            return this.field_74887_e.field_78896_c + p_74873_1_;
         default:
            return p_74873_2_;
         }
      }
   }

   protected void func_175811_a(World p_175811_1_, IBlockState p_175811_2_, int p_175811_3_, int p_175811_4_, int p_175811_5_, StructureBoundingBox p_175811_6_) {
      BlockPos blockpos = new BlockPos(this.func_74865_a(p_175811_3_, p_175811_5_), this.func_74862_a(p_175811_4_), this.func_74873_b(p_175811_3_, p_175811_5_));
      if (p_175811_6_.func_175898_b(blockpos)) {
         if (this.field_186168_b != Mirror.NONE) {
            p_175811_2_ = p_175811_2_.func_185902_a(this.field_186168_b);
         }

         if (this.field_186169_c != Rotation.NONE) {
            p_175811_2_ = p_175811_2_.func_185907_a(this.field_186169_c);
         }

         p_175811_1_.func_180501_a(blockpos, p_175811_2_, 2);
      }
   }

   protected IBlockState func_175807_a(World p_175807_1_, int p_175807_2_, int p_175807_3_, int p_175807_4_, StructureBoundingBox p_175807_5_) {
      int i = this.func_74865_a(p_175807_2_, p_175807_4_);
      int j = this.func_74862_a(p_175807_3_);
      int k = this.func_74873_b(p_175807_2_, p_175807_4_);
      BlockPos blockpos = new BlockPos(i, j, k);
      return !p_175807_5_.func_175898_b(blockpos) ? Blocks.field_150350_a.func_176223_P() : p_175807_1_.func_180495_p(blockpos);
   }

   protected int func_189916_b(World p_189916_1_, int p_189916_2_, int p_189916_3_, int p_189916_4_, StructureBoundingBox p_189916_5_) {
      int i = this.func_74865_a(p_189916_2_, p_189916_4_);
      int j = this.func_74862_a(p_189916_3_ + 1);
      int k = this.func_74873_b(p_189916_2_, p_189916_4_);
      BlockPos blockpos = new BlockPos(i, j, k);
      return !p_189916_5_.func_175898_b(blockpos) ? EnumSkyBlock.SKY.field_77198_c : p_189916_1_.func_175642_b(EnumSkyBlock.SKY, blockpos);
   }

   protected void func_74878_a(World p_74878_1_, StructureBoundingBox p_74878_2_, int p_74878_3_, int p_74878_4_, int p_74878_5_, int p_74878_6_, int p_74878_7_, int p_74878_8_) {
      for(int i = p_74878_4_; i <= p_74878_7_; ++i) {
         for(int j = p_74878_3_; j <= p_74878_6_; ++j) {
            for(int k = p_74878_5_; k <= p_74878_8_; ++k) {
               this.func_175811_a(p_74878_1_, Blocks.field_150350_a.func_176223_P(), j, i, k, p_74878_2_);
            }
         }
      }

   }

   protected void func_175804_a(World p_175804_1_, StructureBoundingBox p_175804_2_, int p_175804_3_, int p_175804_4_, int p_175804_5_, int p_175804_6_, int p_175804_7_, int p_175804_8_, IBlockState p_175804_9_, IBlockState p_175804_10_, boolean p_175804_11_) {
      for(int i = p_175804_4_; i <= p_175804_7_; ++i) {
         for(int j = p_175804_3_; j <= p_175804_6_; ++j) {
            for(int k = p_175804_5_; k <= p_175804_8_; ++k) {
               if (!p_175804_11_ || this.func_175807_a(p_175804_1_, j, i, k, p_175804_2_).func_185904_a() != Material.field_151579_a) {
                  if (i != p_175804_4_ && i != p_175804_7_ && j != p_175804_3_ && j != p_175804_6_ && k != p_175804_5_ && k != p_175804_8_) {
                     this.func_175811_a(p_175804_1_, p_175804_10_, j, i, k, p_175804_2_);
                  } else {
                     this.func_175811_a(p_175804_1_, p_175804_9_, j, i, k, p_175804_2_);
                  }
               }
            }
         }
      }

   }

   protected void func_74882_a(World p_74882_1_, StructureBoundingBox p_74882_2_, int p_74882_3_, int p_74882_4_, int p_74882_5_, int p_74882_6_, int p_74882_7_, int p_74882_8_, boolean p_74882_9_, Random p_74882_10_, StructureComponent.BlockSelector p_74882_11_) {
      for(int i = p_74882_4_; i <= p_74882_7_; ++i) {
         for(int j = p_74882_3_; j <= p_74882_6_; ++j) {
            for(int k = p_74882_5_; k <= p_74882_8_; ++k) {
               if (!p_74882_9_ || this.func_175807_a(p_74882_1_, j, i, k, p_74882_2_).func_185904_a() != Material.field_151579_a) {
                  p_74882_11_.func_75062_a(p_74882_10_, j, i, k, i == p_74882_4_ || i == p_74882_7_ || j == p_74882_3_ || j == p_74882_6_ || k == p_74882_5_ || k == p_74882_8_);
                  this.func_175811_a(p_74882_1_, p_74882_11_.func_180780_a(), j, i, k, p_74882_2_);
               }
            }
         }
      }

   }

   protected void func_189914_a(World p_189914_1_, StructureBoundingBox p_189914_2_, Random p_189914_3_, float p_189914_4_, int p_189914_5_, int p_189914_6_, int p_189914_7_, int p_189914_8_, int p_189914_9_, int p_189914_10_, IBlockState p_189914_11_, IBlockState p_189914_12_, boolean p_189914_13_, int p_189914_14_) {
      for(int i = p_189914_6_; i <= p_189914_9_; ++i) {
         for(int j = p_189914_5_; j <= p_189914_8_; ++j) {
            for(int k = p_189914_7_; k <= p_189914_10_; ++k) {
               if (p_189914_3_.nextFloat() <= p_189914_4_ && (!p_189914_13_ || this.func_175807_a(p_189914_1_, j, i, k, p_189914_2_).func_185904_a() != Material.field_151579_a) && (p_189914_14_ <= 0 || this.func_189916_b(p_189914_1_, j, i, k, p_189914_2_) < p_189914_14_)) {
                  if (i != p_189914_6_ && i != p_189914_9_ && j != p_189914_5_ && j != p_189914_8_ && k != p_189914_7_ && k != p_189914_10_) {
                     this.func_175811_a(p_189914_1_, p_189914_12_, j, i, k, p_189914_2_);
                  } else {
                     this.func_175811_a(p_189914_1_, p_189914_11_, j, i, k, p_189914_2_);
                  }
               }
            }
         }
      }

   }

   protected void func_175809_a(World p_175809_1_, StructureBoundingBox p_175809_2_, Random p_175809_3_, float p_175809_4_, int p_175809_5_, int p_175809_6_, int p_175809_7_, IBlockState p_175809_8_) {
      if (p_175809_3_.nextFloat() < p_175809_4_) {
         this.func_175811_a(p_175809_1_, p_175809_8_, p_175809_5_, p_175809_6_, p_175809_7_, p_175809_2_);
      }

   }

   protected void func_180777_a(World p_180777_1_, StructureBoundingBox p_180777_2_, int p_180777_3_, int p_180777_4_, int p_180777_5_, int p_180777_6_, int p_180777_7_, int p_180777_8_, IBlockState p_180777_9_, boolean p_180777_10_) {
      float f = (float)(p_180777_6_ - p_180777_3_ + 1);
      float f1 = (float)(p_180777_7_ - p_180777_4_ + 1);
      float f2 = (float)(p_180777_8_ - p_180777_5_ + 1);
      float f3 = (float)p_180777_3_ + f / 2.0F;
      float f4 = (float)p_180777_5_ + f2 / 2.0F;

      for(int i = p_180777_4_; i <= p_180777_7_; ++i) {
         float f5 = (float)(i - p_180777_4_) / f1;

         for(int j = p_180777_3_; j <= p_180777_6_; ++j) {
            float f6 = ((float)j - f3) / (f * 0.5F);

            for(int k = p_180777_5_; k <= p_180777_8_; ++k) {
               float f7 = ((float)k - f4) / (f2 * 0.5F);
               if (!p_180777_10_ || this.func_175807_a(p_180777_1_, j, i, k, p_180777_2_).func_185904_a() != Material.field_151579_a) {
                  float f8 = f6 * f6 + f5 * f5 + f7 * f7;
                  if (f8 <= 1.05F) {
                     this.func_175811_a(p_180777_1_, p_180777_9_, j, i, k, p_180777_2_);
                  }
               }
            }
         }
      }

   }

   protected void func_74871_b(World p_74871_1_, int p_74871_2_, int p_74871_3_, int p_74871_4_, StructureBoundingBox p_74871_5_) {
      BlockPos blockpos = new BlockPos(this.func_74865_a(p_74871_2_, p_74871_4_), this.func_74862_a(p_74871_3_), this.func_74873_b(p_74871_2_, p_74871_4_));
      if (p_74871_5_.func_175898_b(blockpos)) {
         while(!p_74871_1_.func_175623_d(blockpos) && blockpos.func_177956_o() < 255) {
            p_74871_1_.func_180501_a(blockpos, Blocks.field_150350_a.func_176223_P(), 2);
            blockpos = blockpos.func_177984_a();
         }

      }
   }

   protected void func_175808_b(World p_175808_1_, IBlockState p_175808_2_, int p_175808_3_, int p_175808_4_, int p_175808_5_, StructureBoundingBox p_175808_6_) {
      int i = this.func_74865_a(p_175808_3_, p_175808_5_);
      int j = this.func_74862_a(p_175808_4_);
      int k = this.func_74873_b(p_175808_3_, p_175808_5_);
      if (p_175808_6_.func_175898_b(new BlockPos(i, j, k))) {
         while((p_175808_1_.func_175623_d(new BlockPos(i, j, k)) || p_175808_1_.func_180495_p(new BlockPos(i, j, k)).func_185904_a().func_76224_d()) && j > 1) {
            p_175808_1_.func_180501_a(new BlockPos(i, j, k), p_175808_2_, 2);
            --j;
         }

      }
   }

   protected boolean func_186167_a(World p_186167_1_, StructureBoundingBox p_186167_2_, Random p_186167_3_, int p_186167_4_, int p_186167_5_, int p_186167_6_, ResourceLocation p_186167_7_) {
      BlockPos blockpos = new BlockPos(this.func_74865_a(p_186167_4_, p_186167_6_), this.func_74862_a(p_186167_5_), this.func_74873_b(p_186167_4_, p_186167_6_));
      return this.func_191080_a(p_186167_1_, p_186167_2_, p_186167_3_, blockpos, p_186167_7_, (IBlockState)null);
   }

   protected boolean func_191080_a(World p_191080_1_, StructureBoundingBox p_191080_2_, Random p_191080_3_, BlockPos p_191080_4_, ResourceLocation p_191080_5_, @Nullable IBlockState p_191080_6_) {
      if (p_191080_2_.func_175898_b(p_191080_4_) && p_191080_1_.func_180495_p(p_191080_4_).func_177230_c() != Blocks.field_150486_ae) {
         if (p_191080_6_ == null) {
            p_191080_6_ = Blocks.field_150486_ae.func_176458_f(p_191080_1_, p_191080_4_, Blocks.field_150486_ae.func_176223_P());
         }

         p_191080_1_.func_180501_a(p_191080_4_, p_191080_6_, 2);
         TileEntity tileentity = p_191080_1_.func_175625_s(p_191080_4_);
         if (tileentity instanceof TileEntityChest) {
            ((TileEntityChest)tileentity).func_189404_a(p_191080_5_, p_191080_3_.nextLong());
         }

         return true;
      } else {
         return false;
      }
   }

   protected boolean func_189419_a(World p_189419_1_, StructureBoundingBox p_189419_2_, Random p_189419_3_, int p_189419_4_, int p_189419_5_, int p_189419_6_, EnumFacing p_189419_7_, ResourceLocation p_189419_8_) {
      BlockPos blockpos = new BlockPos(this.func_74865_a(p_189419_4_, p_189419_6_), this.func_74862_a(p_189419_5_), this.func_74873_b(p_189419_4_, p_189419_6_));
      if (p_189419_2_.func_175898_b(blockpos) && p_189419_1_.func_180495_p(blockpos).func_177230_c() != Blocks.field_150367_z) {
         this.func_175811_a(p_189419_1_, Blocks.field_150367_z.func_176223_P().func_177226_a(BlockDispenser.field_176441_a, p_189419_7_), p_189419_4_, p_189419_5_, p_189419_6_, p_189419_2_);
         TileEntity tileentity = p_189419_1_.func_175625_s(blockpos);
         if (tileentity instanceof TileEntityDispenser) {
            ((TileEntityDispenser)tileentity).func_189404_a(p_189419_8_, p_189419_3_.nextLong());
         }

         return true;
      } else {
         return false;
      }
   }

   protected void func_189915_a(World p_189915_1_, StructureBoundingBox p_189915_2_, Random p_189915_3_, int p_189915_4_, int p_189915_5_, int p_189915_6_, EnumFacing p_189915_7_, BlockDoor p_189915_8_) {
      this.func_175811_a(p_189915_1_, p_189915_8_.func_176223_P().func_177226_a(BlockDoor.field_176520_a, p_189915_7_), p_189915_4_, p_189915_5_, p_189915_6_, p_189915_2_);
      this.func_175811_a(p_189915_1_, p_189915_8_.func_176223_P().func_177226_a(BlockDoor.field_176520_a, p_189915_7_).func_177226_a(BlockDoor.field_176523_O, BlockDoor.EnumDoorHalf.UPPER), p_189915_4_, p_189915_5_ + 1, p_189915_6_, p_189915_2_);
   }

   public void func_181138_a(int p_181138_1_, int p_181138_2_, int p_181138_3_) {
      this.field_74887_e.func_78886_a(p_181138_1_, p_181138_2_, p_181138_3_);
   }

   @Nullable
   public EnumFacing func_186165_e() {
      return this.field_74885_f;
   }

   public void func_186164_a(@Nullable EnumFacing p_186164_1_) {
      this.field_74885_f = p_186164_1_;
      if (p_186164_1_ == null) {
         this.field_186169_c = Rotation.NONE;
         this.field_186168_b = Mirror.NONE;
      } else {
         switch(p_186164_1_) {
         case SOUTH:
            this.field_186168_b = Mirror.LEFT_RIGHT;
            this.field_186169_c = Rotation.NONE;
            break;
         case WEST:
            this.field_186168_b = Mirror.LEFT_RIGHT;
            this.field_186169_c = Rotation.CLOCKWISE_90;
            break;
         case EAST:
            this.field_186168_b = Mirror.NONE;
            this.field_186169_c = Rotation.CLOCKWISE_90;
            break;
         default:
            this.field_186168_b = Mirror.NONE;
            this.field_186169_c = Rotation.NONE;
         }
      }

   }

   public abstract static class BlockSelector {
      protected IBlockState field_151562_a = Blocks.field_150350_a.func_176223_P();

      public abstract void func_75062_a(Random var1, int var2, int var3, int var4, boolean var5);

      public IBlockState func_180780_a() {
         return this.field_151562_a;
      }
   }
}
