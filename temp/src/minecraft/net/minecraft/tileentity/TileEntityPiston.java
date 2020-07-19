package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TileEntityPiston extends TileEntity implements ITickable {
   private IBlockState field_174932_a;
   private EnumFacing field_174931_f;
   private boolean field_145875_k;
   private boolean field_145872_l;
   private static final ThreadLocal<EnumFacing> field_190613_i = new ThreadLocal<EnumFacing>() {
      protected EnumFacing initialValue() {
         return null;
      }
   };
   private float field_145873_m;
   private float field_145870_n;

   public TileEntityPiston() {
   }

   public TileEntityPiston(IBlockState p_i45665_1_, EnumFacing p_i45665_2_, boolean p_i45665_3_, boolean p_i45665_4_) {
      this.field_174932_a = p_i45665_1_;
      this.field_174931_f = p_i45665_2_;
      this.field_145875_k = p_i45665_3_;
      this.field_145872_l = p_i45665_4_;
   }

   public IBlockState func_174927_b() {
      return this.field_174932_a;
   }

   public NBTTagCompound func_189517_E_() {
      return this.func_189515_b(new NBTTagCompound());
   }

   public int func_145832_p() {
      return 0;
   }

   public boolean func_145868_b() {
      return this.field_145875_k;
   }

   public EnumFacing func_174930_e() {
      return this.field_174931_f;
   }

   public boolean func_145867_d() {
      return this.field_145872_l;
   }

   public float func_145860_a(float p_145860_1_) {
      if (p_145860_1_ > 1.0F) {
         p_145860_1_ = 1.0F;
      }

      return this.field_145870_n + (this.field_145873_m - this.field_145870_n) * p_145860_1_;
   }

   public float func_174929_b(float p_174929_1_) {
      return (float)this.field_174931_f.func_82601_c() * this.func_184320_e(this.func_145860_a(p_174929_1_));
   }

   public float func_174928_c(float p_174928_1_) {
      return (float)this.field_174931_f.func_96559_d() * this.func_184320_e(this.func_145860_a(p_174928_1_));
   }

   public float func_174926_d(float p_174926_1_) {
      return (float)this.field_174931_f.func_82599_e() * this.func_184320_e(this.func_145860_a(p_174926_1_));
   }

   private float func_184320_e(float p_184320_1_) {
      return this.field_145875_k ? p_184320_1_ - 1.0F : 1.0F - p_184320_1_;
   }

   public AxisAlignedBB func_184321_a(IBlockAccess p_184321_1_, BlockPos p_184321_2_) {
      return this.func_184319_a(p_184321_1_, p_184321_2_, this.field_145873_m).func_111270_a(this.func_184319_a(p_184321_1_, p_184321_2_, this.field_145870_n));
   }

   public AxisAlignedBB func_184319_a(IBlockAccess p_184319_1_, BlockPos p_184319_2_, float p_184319_3_) {
      p_184319_3_ = this.func_184320_e(p_184319_3_);
      IBlockState iblockstate = this.func_190606_j();
      return iblockstate.func_185900_c(p_184319_1_, p_184319_2_).func_72317_d((double)(p_184319_3_ * (float)this.field_174931_f.func_82601_c()), (double)(p_184319_3_ * (float)this.field_174931_f.func_96559_d()), (double)(p_184319_3_ * (float)this.field_174931_f.func_82599_e()));
   }

   private IBlockState func_190606_j() {
      return !this.func_145868_b() && this.func_145867_d() ? Blocks.field_150332_K.func_176223_P().func_177226_a(BlockPistonExtension.field_176325_b, this.field_174932_a.func_177230_c() == Blocks.field_150320_F ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT).func_177226_a(BlockPistonExtension.field_176387_N, this.field_174932_a.func_177229_b(BlockPistonBase.field_176387_N)) : this.field_174932_a;
   }

   private void func_184322_i(float p_184322_1_) {
      EnumFacing enumfacing = this.field_145875_k ? this.field_174931_f : this.field_174931_f.func_176734_d();
      double d0 = (double)(p_184322_1_ - this.field_145873_m);
      List<AxisAlignedBB> list = Lists.<AxisAlignedBB>newArrayList();
      this.func_190606_j().func_185908_a(this.field_145850_b, BlockPos.field_177992_a, new AxisAlignedBB(BlockPos.field_177992_a), list, (Entity)null, true);
      if (!list.isEmpty()) {
         AxisAlignedBB axisalignedbb = this.func_190607_a(this.func_191515_a(list));
         List<Entity> list1 = this.field_145850_b.func_72839_b((Entity)null, this.func_190610_a(axisalignedbb, enumfacing, d0).func_111270_a(axisalignedbb));
         if (!list1.isEmpty()) {
            boolean flag = this.field_174932_a.func_177230_c() == Blocks.field_180399_cE;

            for(int i = 0; i < list1.size(); ++i) {
               Entity entity = list1.get(i);
               if (entity.func_184192_z() != EnumPushReaction.IGNORE) {
                  if (flag) {
                     switch(enumfacing.func_176740_k()) {
                     case X:
                        entity.field_70159_w = (double)enumfacing.func_82601_c();
                        break;
                     case Y:
                        entity.field_70181_x = (double)enumfacing.func_96559_d();
                        break;
                     case Z:
                        entity.field_70179_y = (double)enumfacing.func_82599_e();
                     }
                  }

                  double d1 = 0.0D;

                  for(int j = 0; j < list.size(); ++j) {
                     AxisAlignedBB axisalignedbb1 = this.func_190610_a(this.func_190607_a(list.get(j)), enumfacing, d0);
                     AxisAlignedBB axisalignedbb2 = entity.func_174813_aQ();
                     if (axisalignedbb1.func_72326_a(axisalignedbb2)) {
                        d1 = Math.max(d1, this.func_190612_a(axisalignedbb1, enumfacing, axisalignedbb2));
                        if (d1 >= d0) {
                           break;
                        }
                     }
                  }

                  if (d1 > 0.0D) {
                     d1 = Math.min(d1, d0) + 0.01D;
                     field_190613_i.set(enumfacing);
                     entity.func_70091_d(MoverType.PISTON, d1 * (double)enumfacing.func_82601_c(), d1 * (double)enumfacing.func_96559_d(), d1 * (double)enumfacing.func_82599_e());
                     field_190613_i.set((Object)null);
                     if (!this.field_145875_k && this.field_145872_l) {
                        this.func_190605_a(entity, enumfacing, d0);
                     }
                  }
               }
            }

         }
      }
   }

   private AxisAlignedBB func_191515_a(List<AxisAlignedBB> p_191515_1_) {
      double d0 = 0.0D;
      double d1 = 0.0D;
      double d2 = 0.0D;
      double d3 = 1.0D;
      double d4 = 1.0D;
      double d5 = 1.0D;

      for(AxisAlignedBB axisalignedbb : p_191515_1_) {
         d0 = Math.min(axisalignedbb.field_72340_a, d0);
         d1 = Math.min(axisalignedbb.field_72338_b, d1);
         d2 = Math.min(axisalignedbb.field_72339_c, d2);
         d3 = Math.max(axisalignedbb.field_72336_d, d3);
         d4 = Math.max(axisalignedbb.field_72337_e, d4);
         d5 = Math.max(axisalignedbb.field_72334_f, d5);
      }

      return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
   }

   private double func_190612_a(AxisAlignedBB p_190612_1_, EnumFacing p_190612_2_, AxisAlignedBB p_190612_3_) {
      switch(p_190612_2_.func_176740_k()) {
      case X:
         return func_190611_b(p_190612_1_, p_190612_2_, p_190612_3_);
      case Y:
      default:
         return func_190608_c(p_190612_1_, p_190612_2_, p_190612_3_);
      case Z:
         return func_190604_d(p_190612_1_, p_190612_2_, p_190612_3_);
      }
   }

   private AxisAlignedBB func_190607_a(AxisAlignedBB p_190607_1_) {
      double d0 = (double)this.func_184320_e(this.field_145873_m);
      return p_190607_1_.func_72317_d((double)this.field_174879_c.func_177958_n() + d0 * (double)this.field_174931_f.func_82601_c(), (double)this.field_174879_c.func_177956_o() + d0 * (double)this.field_174931_f.func_96559_d(), (double)this.field_174879_c.func_177952_p() + d0 * (double)this.field_174931_f.func_82599_e());
   }

   private AxisAlignedBB func_190610_a(AxisAlignedBB p_190610_1_, EnumFacing p_190610_2_, double p_190610_3_) {
      double d0 = p_190610_3_ * (double)p_190610_2_.func_176743_c().func_179524_a();
      double d1 = Math.min(d0, 0.0D);
      double d2 = Math.max(d0, 0.0D);
      switch(p_190610_2_) {
      case WEST:
         return new AxisAlignedBB(p_190610_1_.field_72340_a + d1, p_190610_1_.field_72338_b, p_190610_1_.field_72339_c, p_190610_1_.field_72340_a + d2, p_190610_1_.field_72337_e, p_190610_1_.field_72334_f);
      case EAST:
         return new AxisAlignedBB(p_190610_1_.field_72336_d + d1, p_190610_1_.field_72338_b, p_190610_1_.field_72339_c, p_190610_1_.field_72336_d + d2, p_190610_1_.field_72337_e, p_190610_1_.field_72334_f);
      case DOWN:
         return new AxisAlignedBB(p_190610_1_.field_72340_a, p_190610_1_.field_72338_b + d1, p_190610_1_.field_72339_c, p_190610_1_.field_72336_d, p_190610_1_.field_72338_b + d2, p_190610_1_.field_72334_f);
      case UP:
      default:
         return new AxisAlignedBB(p_190610_1_.field_72340_a, p_190610_1_.field_72337_e + d1, p_190610_1_.field_72339_c, p_190610_1_.field_72336_d, p_190610_1_.field_72337_e + d2, p_190610_1_.field_72334_f);
      case NORTH:
         return new AxisAlignedBB(p_190610_1_.field_72340_a, p_190610_1_.field_72338_b, p_190610_1_.field_72339_c + d1, p_190610_1_.field_72336_d, p_190610_1_.field_72337_e, p_190610_1_.field_72339_c + d2);
      case SOUTH:
         return new AxisAlignedBB(p_190610_1_.field_72340_a, p_190610_1_.field_72338_b, p_190610_1_.field_72334_f + d1, p_190610_1_.field_72336_d, p_190610_1_.field_72337_e, p_190610_1_.field_72334_f + d2);
      }
   }

   private void func_190605_a(Entity p_190605_1_, EnumFacing p_190605_2_, double p_190605_3_) {
      AxisAlignedBB axisalignedbb = p_190605_1_.func_174813_aQ();
      AxisAlignedBB axisalignedbb1 = Block.field_185505_j.func_186670_a(this.field_174879_c);
      if (axisalignedbb.func_72326_a(axisalignedbb1)) {
         EnumFacing enumfacing = p_190605_2_.func_176734_d();
         double d0 = this.func_190612_a(axisalignedbb1, enumfacing, axisalignedbb) + 0.01D;
         double d1 = this.func_190612_a(axisalignedbb1, enumfacing, axisalignedbb.func_191500_a(axisalignedbb1)) + 0.01D;
         if (Math.abs(d0 - d1) < 0.01D) {
            d0 = Math.min(d0, p_190605_3_) + 0.01D;
            field_190613_i.set(p_190605_2_);
            p_190605_1_.func_70091_d(MoverType.PISTON, d0 * (double)enumfacing.func_82601_c(), d0 * (double)enumfacing.func_96559_d(), d0 * (double)enumfacing.func_82599_e());
            field_190613_i.set((Object)null);
         }
      }

   }

   private static double func_190611_b(AxisAlignedBB p_190611_0_, EnumFacing p_190611_1_, AxisAlignedBB p_190611_2_) {
      return p_190611_1_.func_176743_c() == EnumFacing.AxisDirection.POSITIVE ? p_190611_0_.field_72336_d - p_190611_2_.field_72340_a : p_190611_2_.field_72336_d - p_190611_0_.field_72340_a;
   }

   private static double func_190608_c(AxisAlignedBB p_190608_0_, EnumFacing p_190608_1_, AxisAlignedBB p_190608_2_) {
      return p_190608_1_.func_176743_c() == EnumFacing.AxisDirection.POSITIVE ? p_190608_0_.field_72337_e - p_190608_2_.field_72338_b : p_190608_2_.field_72337_e - p_190608_0_.field_72338_b;
   }

   private static double func_190604_d(AxisAlignedBB p_190604_0_, EnumFacing p_190604_1_, AxisAlignedBB p_190604_2_) {
      return p_190604_1_.func_176743_c() == EnumFacing.AxisDirection.POSITIVE ? p_190604_0_.field_72334_f - p_190604_2_.field_72339_c : p_190604_2_.field_72334_f - p_190604_0_.field_72339_c;
   }

   public void func_145866_f() {
      if (this.field_145870_n < 1.0F && this.field_145850_b != null) {
         this.field_145873_m = 1.0F;
         this.field_145870_n = this.field_145873_m;
         this.field_145850_b.func_175713_t(this.field_174879_c);
         this.func_145843_s();
         if (this.field_145850_b.func_180495_p(this.field_174879_c).func_177230_c() == Blocks.field_180384_M) {
            this.field_145850_b.func_180501_a(this.field_174879_c, this.field_174932_a, 3);
            this.field_145850_b.func_190524_a(this.field_174879_c, this.field_174932_a.func_177230_c(), this.field_174879_c);
         }
      }

   }

   public void func_73660_a() {
      this.field_145870_n = this.field_145873_m;
      if (this.field_145870_n >= 1.0F) {
         this.field_145850_b.func_175713_t(this.field_174879_c);
         this.func_145843_s();
         if (this.field_145850_b.func_180495_p(this.field_174879_c).func_177230_c() == Blocks.field_180384_M) {
            this.field_145850_b.func_180501_a(this.field_174879_c, this.field_174932_a, 3);
            this.field_145850_b.func_190524_a(this.field_174879_c, this.field_174932_a.func_177230_c(), this.field_174879_c);
         }

      } else {
         float f = this.field_145873_m + 0.5F;
         this.func_184322_i(f);
         this.field_145873_m = f;
         if (this.field_145873_m >= 1.0F) {
            this.field_145873_m = 1.0F;
         }

      }
   }

   public static void func_189685_a(DataFixer p_189685_0_) {
   }

   public void func_145839_a(NBTTagCompound p_145839_1_) {
      super.func_145839_a(p_145839_1_);
      this.field_174932_a = Block.func_149729_e(p_145839_1_.func_74762_e("blockId")).func_176203_a(p_145839_1_.func_74762_e("blockData"));
      this.field_174931_f = EnumFacing.func_82600_a(p_145839_1_.func_74762_e("facing"));
      this.field_145873_m = p_145839_1_.func_74760_g("progress");
      this.field_145870_n = this.field_145873_m;
      this.field_145875_k = p_145839_1_.func_74767_n("extending");
      this.field_145872_l = p_145839_1_.func_74767_n("source");
   }

   public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_) {
      super.func_189515_b(p_189515_1_);
      p_189515_1_.func_74768_a("blockId", Block.func_149682_b(this.field_174932_a.func_177230_c()));
      p_189515_1_.func_74768_a("blockData", this.field_174932_a.func_177230_c().func_176201_c(this.field_174932_a));
      p_189515_1_.func_74768_a("facing", this.field_174931_f.func_176745_a());
      p_189515_1_.func_74776_a("progress", this.field_145870_n);
      p_189515_1_.func_74757_a("extending", this.field_145875_k);
      p_189515_1_.func_74757_a("source", this.field_145872_l);
      return p_189515_1_;
   }

   public void func_190609_a(World p_190609_1_, BlockPos p_190609_2_, AxisAlignedBB p_190609_3_, List<AxisAlignedBB> p_190609_4_, @Nullable Entity p_190609_5_) {
      if (!this.field_145875_k && this.field_145872_l) {
         this.field_174932_a.func_177226_a(BlockPistonBase.field_176320_b, Boolean.valueOf(true)).func_185908_a(p_190609_1_, p_190609_2_, p_190609_3_, p_190609_4_, p_190609_5_, false);
      }

      EnumFacing enumfacing = field_190613_i.get();
      if ((double)this.field_145873_m >= 1.0D || enumfacing != (this.field_145875_k ? this.field_174931_f : this.field_174931_f.func_176734_d())) {
         int i = p_190609_4_.size();
         IBlockState iblockstate;
         if (this.func_145867_d()) {
            iblockstate = Blocks.field_150332_K.func_176223_P().func_177226_a(BlockPistonExtension.field_176387_N, this.field_174931_f).func_177226_a(BlockPistonExtension.field_176327_M, Boolean.valueOf(this.field_145875_k != 1.0F - this.field_145873_m < 0.25F));
         } else {
            iblockstate = this.field_174932_a;
         }

         float f = this.func_184320_e(this.field_145873_m);
         double d0 = (double)((float)this.field_174931_f.func_82601_c() * f);
         double d1 = (double)((float)this.field_174931_f.func_96559_d() * f);
         double d2 = (double)((float)this.field_174931_f.func_82599_e() * f);
         iblockstate.func_185908_a(p_190609_1_, p_190609_2_, p_190609_3_.func_72317_d(-d0, -d1, -d2), p_190609_4_, p_190609_5_, true);

         for(int j = i; j < p_190609_4_.size(); ++j) {
            p_190609_4_.set(j, ((AxisAlignedBB)p_190609_4_.get(j)).func_72317_d(d0, d1, d2));
         }

      }
   }
}
