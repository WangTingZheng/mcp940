package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDoor extends Block {
   public static final PropertyDirection field_176520_a = BlockHorizontal.field_185512_D;
   public static final PropertyBool field_176519_b = PropertyBool.func_177716_a("open");
   public static final PropertyEnum<BlockDoor.EnumHingePosition> field_176521_M = PropertyEnum.<BlockDoor.EnumHingePosition>func_177709_a("hinge", BlockDoor.EnumHingePosition.class);
   public static final PropertyBool field_176522_N = PropertyBool.func_177716_a("powered");
   public static final PropertyEnum<BlockDoor.EnumDoorHalf> field_176523_O = PropertyEnum.<BlockDoor.EnumDoorHalf>func_177709_a("half", BlockDoor.EnumDoorHalf.class);
   protected static final AxisAlignedBB field_185658_f = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1875D);
   protected static final AxisAlignedBB field_185659_g = new AxisAlignedBB(0.0D, 0.0D, 0.8125D, 1.0D, 1.0D, 1.0D);
   protected static final AxisAlignedBB field_185656_B = new AxisAlignedBB(0.8125D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
   protected static final AxisAlignedBB field_185657_C = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.1875D, 1.0D, 1.0D);

   protected BlockDoor(Material p_i45402_1_) {
      super(p_i45402_1_);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176520_a, EnumFacing.NORTH).func_177226_a(field_176519_b, Boolean.valueOf(false)).func_177226_a(field_176521_M, BlockDoor.EnumHingePosition.LEFT).func_177226_a(field_176522_N, Boolean.valueOf(false)).func_177226_a(field_176523_O, BlockDoor.EnumDoorHalf.LOWER));
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      p_185496_1_ = p_185496_1_.func_185899_b(p_185496_2_, p_185496_3_);
      EnumFacing enumfacing = (EnumFacing)p_185496_1_.func_177229_b(field_176520_a);
      boolean flag = !((Boolean)p_185496_1_.func_177229_b(field_176519_b)).booleanValue();
      boolean flag1 = p_185496_1_.func_177229_b(field_176521_M) == BlockDoor.EnumHingePosition.RIGHT;
      switch(enumfacing) {
      case EAST:
      default:
         return flag ? field_185657_C : (flag1 ? field_185659_g : field_185658_f);
      case SOUTH:
         return flag ? field_185658_f : (flag1 ? field_185657_C : field_185656_B);
      case WEST:
         return flag ? field_185656_B : (flag1 ? field_185658_f : field_185659_g);
      case NORTH:
         return flag ? field_185659_g : (flag1 ? field_185656_B : field_185657_C);
      }
   }

   public String func_149732_F() {
      return I18n.func_74838_a((this.func_149739_a() + ".name").replaceAll("tile", "item"));
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_176205_b(IBlockAccess p_176205_1_, BlockPos p_176205_2_) {
      return func_176516_g(func_176515_e(p_176205_1_, p_176205_2_));
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   private int func_185654_e() {
      return this.field_149764_J == Material.field_151573_f ? 1011 : 1012;
   }

   private int func_185655_g() {
      return this.field_149764_J == Material.field_151573_f ? 1005 : 1006;
   }

   public MapColor func_180659_g(IBlockState p_180659_1_, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
      if (p_180659_1_.func_177230_c() == Blocks.field_150454_av) {
         return MapColor.field_151668_h;
      } else if (p_180659_1_.func_177230_c() == Blocks.field_180413_ao) {
         return BlockPlanks.EnumType.OAK.func_181070_c();
      } else if (p_180659_1_.func_177230_c() == Blocks.field_180414_ap) {
         return BlockPlanks.EnumType.SPRUCE.func_181070_c();
      } else if (p_180659_1_.func_177230_c() == Blocks.field_180412_aq) {
         return BlockPlanks.EnumType.BIRCH.func_181070_c();
      } else if (p_180659_1_.func_177230_c() == Blocks.field_180411_ar) {
         return BlockPlanks.EnumType.JUNGLE.func_181070_c();
      } else if (p_180659_1_.func_177230_c() == Blocks.field_180410_as) {
         return BlockPlanks.EnumType.ACACIA.func_181070_c();
      } else {
         return p_180659_1_.func_177230_c() == Blocks.field_180409_at ? BlockPlanks.EnumType.DARK_OAK.func_181070_c() : super.func_180659_g(p_180659_1_, p_180659_2_, p_180659_3_);
      }
   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      if (this.field_149764_J == Material.field_151573_f) {
         return false;
      } else {
         BlockPos blockpos = p_180639_3_.func_177229_b(field_176523_O) == BlockDoor.EnumDoorHalf.LOWER ? p_180639_2_ : p_180639_2_.func_177977_b();
         IBlockState iblockstate = p_180639_2_.equals(blockpos) ? p_180639_3_ : p_180639_1_.func_180495_p(blockpos);
         if (iblockstate.func_177230_c() != this) {
            return false;
         } else {
            p_180639_3_ = iblockstate.func_177231_a(field_176519_b);
            p_180639_1_.func_180501_a(blockpos, p_180639_3_, 10);
            p_180639_1_.func_175704_b(blockpos, p_180639_2_);
            p_180639_1_.func_180498_a(p_180639_4_, ((Boolean)p_180639_3_.func_177229_b(field_176519_b)).booleanValue() ? this.func_185655_g() : this.func_185654_e(), p_180639_2_, 0);
            return true;
         }
      }
   }

   public void func_176512_a(World p_176512_1_, BlockPos p_176512_2_, boolean p_176512_3_) {
      IBlockState iblockstate = p_176512_1_.func_180495_p(p_176512_2_);
      if (iblockstate.func_177230_c() == this) {
         BlockPos blockpos = iblockstate.func_177229_b(field_176523_O) == BlockDoor.EnumDoorHalf.LOWER ? p_176512_2_ : p_176512_2_.func_177977_b();
         IBlockState iblockstate1 = p_176512_2_ == blockpos ? iblockstate : p_176512_1_.func_180495_p(blockpos);
         if (iblockstate1.func_177230_c() == this && ((Boolean)iblockstate1.func_177229_b(field_176519_b)).booleanValue() != p_176512_3_) {
            p_176512_1_.func_180501_a(blockpos, iblockstate1.func_177226_a(field_176519_b, Boolean.valueOf(p_176512_3_)), 10);
            p_176512_1_.func_175704_b(blockpos, p_176512_2_);
            p_176512_1_.func_180498_a((EntityPlayer)null, p_176512_3_ ? this.func_185655_g() : this.func_185654_e(), p_176512_2_, 0);
         }

      }
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      if (p_189540_1_.func_177229_b(field_176523_O) == BlockDoor.EnumDoorHalf.UPPER) {
         BlockPos blockpos = p_189540_3_.func_177977_b();
         IBlockState iblockstate = p_189540_2_.func_180495_p(blockpos);
         if (iblockstate.func_177230_c() != this) {
            p_189540_2_.func_175698_g(p_189540_3_);
         } else if (p_189540_4_ != this) {
            iblockstate.func_189546_a(p_189540_2_, blockpos, p_189540_4_, p_189540_5_);
         }
      } else {
         boolean flag1 = false;
         BlockPos blockpos1 = p_189540_3_.func_177984_a();
         IBlockState iblockstate1 = p_189540_2_.func_180495_p(blockpos1);
         if (iblockstate1.func_177230_c() != this) {
            p_189540_2_.func_175698_g(p_189540_3_);
            flag1 = true;
         }

         if (!p_189540_2_.func_180495_p(p_189540_3_.func_177977_b()).func_185896_q()) {
            p_189540_2_.func_175698_g(p_189540_3_);
            flag1 = true;
            if (iblockstate1.func_177230_c() == this) {
               p_189540_2_.func_175698_g(blockpos1);
            }
         }

         if (flag1) {
            if (!p_189540_2_.field_72995_K) {
               this.func_176226_b(p_189540_2_, p_189540_3_, p_189540_1_, 0);
            }
         } else {
            boolean flag = p_189540_2_.func_175640_z(p_189540_3_) || p_189540_2_.func_175640_z(blockpos1);
            if (p_189540_4_ != this && (flag || p_189540_4_.func_176223_P().func_185897_m()) && flag != ((Boolean)iblockstate1.func_177229_b(field_176522_N)).booleanValue()) {
               p_189540_2_.func_180501_a(blockpos1, iblockstate1.func_177226_a(field_176522_N, Boolean.valueOf(flag)), 2);
               if (flag != ((Boolean)p_189540_1_.func_177229_b(field_176519_b)).booleanValue()) {
                  p_189540_2_.func_180501_a(p_189540_3_, p_189540_1_.func_177226_a(field_176519_b, Boolean.valueOf(flag)), 2);
                  p_189540_2_.func_175704_b(p_189540_3_, p_189540_3_);
                  p_189540_2_.func_180498_a((EntityPlayer)null, flag ? this.func_185655_g() : this.func_185654_e(), p_189540_3_, 0);
               }
            }
         }
      }

   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return p_180660_1_.func_177229_b(field_176523_O) == BlockDoor.EnumDoorHalf.UPPER ? Items.field_190931_a : this.func_176509_j();
   }

   public boolean func_176196_c(World p_176196_1_, BlockPos p_176196_2_) {
      if (p_176196_2_.func_177956_o() >= 255) {
         return false;
      } else {
         return p_176196_1_.func_180495_p(p_176196_2_.func_177977_b()).func_185896_q() && super.func_176196_c(p_176196_1_, p_176196_2_) && super.func_176196_c(p_176196_1_, p_176196_2_.func_177984_a());
      }
   }

   public EnumPushReaction func_149656_h(IBlockState p_149656_1_) {
      return EnumPushReaction.DESTROY;
   }

   public static int func_176515_e(IBlockAccess p_176515_0_, BlockPos p_176515_1_) {
      IBlockState iblockstate = p_176515_0_.func_180495_p(p_176515_1_);
      int i = iblockstate.func_177230_c().func_176201_c(iblockstate);
      boolean flag = func_176518_i(i);
      IBlockState iblockstate1 = p_176515_0_.func_180495_p(p_176515_1_.func_177977_b());
      int j = iblockstate1.func_177230_c().func_176201_c(iblockstate1);
      int k = flag ? j : i;
      IBlockState iblockstate2 = p_176515_0_.func_180495_p(p_176515_1_.func_177984_a());
      int l = iblockstate2.func_177230_c().func_176201_c(iblockstate2);
      int i1 = flag ? i : l;
      boolean flag1 = (i1 & 1) != 0;
      boolean flag2 = (i1 & 2) != 0;
      return func_176510_b(k) | (flag ? 8 : 0) | (flag1 ? 16 : 0) | (flag2 ? 32 : 0);
   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return new ItemStack(this.func_176509_j());
   }

   private Item func_176509_j() {
      if (this == Blocks.field_150454_av) {
         return Items.field_151139_aw;
      } else if (this == Blocks.field_180414_ap) {
         return Items.field_179569_ar;
      } else if (this == Blocks.field_180412_aq) {
         return Items.field_179568_as;
      } else if (this == Blocks.field_180411_ar) {
         return Items.field_179567_at;
      } else if (this == Blocks.field_180410_as) {
         return Items.field_179572_au;
      } else {
         return this == Blocks.field_180409_at ? Items.field_179571_av : Items.field_179570_aq;
      }
   }

   public void func_176208_a(World p_176208_1_, BlockPos p_176208_2_, IBlockState p_176208_3_, EntityPlayer p_176208_4_) {
      BlockPos blockpos = p_176208_2_.func_177977_b();
      BlockPos blockpos1 = p_176208_2_.func_177984_a();
      if (p_176208_4_.field_71075_bZ.field_75098_d && p_176208_3_.func_177229_b(field_176523_O) == BlockDoor.EnumDoorHalf.UPPER && p_176208_1_.func_180495_p(blockpos).func_177230_c() == this) {
         p_176208_1_.func_175698_g(blockpos);
      }

      if (p_176208_3_.func_177229_b(field_176523_O) == BlockDoor.EnumDoorHalf.LOWER && p_176208_1_.func_180495_p(blockpos1).func_177230_c() == this) {
         if (p_176208_4_.field_71075_bZ.field_75098_d) {
            p_176208_1_.func_175698_g(p_176208_2_);
         }

         p_176208_1_.func_175698_g(blockpos1);
      }

   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   public IBlockState func_176221_a(IBlockState p_176221_1_, IBlockAccess p_176221_2_, BlockPos p_176221_3_) {
      if (p_176221_1_.func_177229_b(field_176523_O) == BlockDoor.EnumDoorHalf.LOWER) {
         IBlockState iblockstate = p_176221_2_.func_180495_p(p_176221_3_.func_177984_a());
         if (iblockstate.func_177230_c() == this) {
            p_176221_1_ = p_176221_1_.func_177226_a(field_176521_M, iblockstate.func_177229_b(field_176521_M)).func_177226_a(field_176522_N, iblockstate.func_177229_b(field_176522_N));
         }
      } else {
         IBlockState iblockstate1 = p_176221_2_.func_180495_p(p_176221_3_.func_177977_b());
         if (iblockstate1.func_177230_c() == this) {
            p_176221_1_ = p_176221_1_.func_177226_a(field_176520_a, iblockstate1.func_177229_b(field_176520_a)).func_177226_a(field_176519_b, iblockstate1.func_177229_b(field_176519_b));
         }
      }

      return p_176221_1_;
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      return p_185499_1_.func_177229_b(field_176523_O) != BlockDoor.EnumDoorHalf.LOWER ? p_185499_1_ : p_185499_1_.func_177226_a(field_176520_a, p_185499_2_.func_185831_a((EnumFacing)p_185499_1_.func_177229_b(field_176520_a)));
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      return p_185471_2_ == Mirror.NONE ? p_185471_1_ : p_185471_1_.func_185907_a(p_185471_2_.func_185800_a((EnumFacing)p_185471_1_.func_177229_b(field_176520_a))).func_177231_a(field_176521_M);
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return (p_176203_1_ & 8) > 0 ? this.func_176223_P().func_177226_a(field_176523_O, BlockDoor.EnumDoorHalf.UPPER).func_177226_a(field_176521_M, (p_176203_1_ & 1) > 0 ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT).func_177226_a(field_176522_N, Boolean.valueOf((p_176203_1_ & 2) > 0)) : this.func_176223_P().func_177226_a(field_176523_O, BlockDoor.EnumDoorHalf.LOWER).func_177226_a(field_176520_a, EnumFacing.func_176731_b(p_176203_1_ & 3).func_176735_f()).func_177226_a(field_176519_b, Boolean.valueOf((p_176203_1_ & 4) > 0));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      int i = 0;
      if (p_176201_1_.func_177229_b(field_176523_O) == BlockDoor.EnumDoorHalf.UPPER) {
         i = i | 8;
         if (p_176201_1_.func_177229_b(field_176521_M) == BlockDoor.EnumHingePosition.RIGHT) {
            i |= 1;
         }

         if (((Boolean)p_176201_1_.func_177229_b(field_176522_N)).booleanValue()) {
            i |= 2;
         }
      } else {
         i = i | ((EnumFacing)p_176201_1_.func_177229_b(field_176520_a)).func_176746_e().func_176736_b();
         if (((Boolean)p_176201_1_.func_177229_b(field_176519_b)).booleanValue()) {
            i |= 4;
         }
      }

      return i;
   }

   protected static int func_176510_b(int p_176510_0_) {
      return p_176510_0_ & 7;
   }

   public static boolean func_176514_f(IBlockAccess p_176514_0_, BlockPos p_176514_1_) {
      return func_176516_g(func_176515_e(p_176514_0_, p_176514_1_));
   }

   public static EnumFacing func_176517_h(IBlockAccess p_176517_0_, BlockPos p_176517_1_) {
      return func_176511_f(func_176515_e(p_176517_0_, p_176517_1_));
   }

   public static EnumFacing func_176511_f(int p_176511_0_) {
      return EnumFacing.func_176731_b(p_176511_0_ & 3).func_176735_f();
   }

   protected static boolean func_176516_g(int p_176516_0_) {
      return (p_176516_0_ & 4) != 0;
   }

   protected static boolean func_176518_i(int p_176518_0_) {
      return (p_176518_0_ & 8) != 0;
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176523_O, field_176520_a, field_176519_b, field_176521_M, field_176522_N});
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }

   public static enum EnumDoorHalf implements IStringSerializable {
      UPPER,
      LOWER;

      public String toString() {
         return this.func_176610_l();
      }

      public String func_176610_l() {
         return this == UPPER ? "upper" : "lower";
      }
   }

   public static enum EnumHingePosition implements IStringSerializable {
      LEFT,
      RIGHT;

      public String toString() {
         return this.func_176610_l();
      }

      public String func_176610_l() {
         return this == LEFT ? "left" : "right";
      }
   }
}
