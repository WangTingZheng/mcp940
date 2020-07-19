package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockPistonStructureHelper;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPistonBase extends BlockDirectional {
   public static final PropertyBool field_176320_b = PropertyBool.func_177716_a("extended");
   protected static final AxisAlignedBB field_185648_b = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D);
   protected static final AxisAlignedBB field_185649_c = new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
   protected static final AxisAlignedBB field_185650_d = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D);
   protected static final AxisAlignedBB field_185651_e = new AxisAlignedBB(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D);
   protected static final AxisAlignedBB field_185652_f = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);
   protected static final AxisAlignedBB field_185653_g = new AxisAlignedBB(0.0D, 0.25D, 0.0D, 1.0D, 1.0D, 1.0D);
   private final boolean field_150082_a;

   public BlockPistonBase(boolean p_i45443_1_) {
      super(Material.field_76233_E);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176387_N, EnumFacing.NORTH).func_177226_a(field_176320_b, Boolean.valueOf(false)));
      this.field_150082_a = p_i45443_1_;
      this.func_149672_a(SoundType.field_185851_d);
      this.func_149711_c(0.5F);
      this.func_149647_a(CreativeTabs.field_78028_d);
   }

   public boolean func_176214_u(IBlockState p_176214_1_) {
      return !((Boolean)p_176214_1_.func_177229_b(field_176320_b)).booleanValue();
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      if (((Boolean)p_185496_1_.func_177229_b(field_176320_b)).booleanValue()) {
         switch((EnumFacing)p_185496_1_.func_177229_b(field_176387_N)) {
         case DOWN:
            return field_185653_g;
         case UP:
         default:
            return field_185652_f;
         case NORTH:
            return field_185651_e;
         case SOUTH:
            return field_185650_d;
         case WEST:
            return field_185649_c;
         case EAST:
            return field_185648_b;
         }
      } else {
         return field_185505_j;
      }
   }

   public boolean func_185481_k(IBlockState p_185481_1_) {
      return !((Boolean)p_185481_1_.func_177229_b(field_176320_b)).booleanValue() || p_185481_1_.func_177229_b(field_176387_N) == EnumFacing.DOWN;
   }

   public void func_185477_a(IBlockState p_185477_1_, World p_185477_2_, BlockPos p_185477_3_, AxisAlignedBB p_185477_4_, List<AxisAlignedBB> p_185477_5_, @Nullable Entity p_185477_6_, boolean p_185477_7_) {
      func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, p_185477_1_.func_185900_c(p_185477_2_, p_185477_3_));
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public void func_180633_a(World p_180633_1_, BlockPos p_180633_2_, IBlockState p_180633_3_, EntityLivingBase p_180633_4_, ItemStack p_180633_5_) {
      p_180633_1_.func_180501_a(p_180633_2_, p_180633_3_.func_177226_a(field_176387_N, EnumFacing.func_190914_a(p_180633_2_, p_180633_4_)), 2);
      if (!p_180633_1_.field_72995_K) {
         this.func_176316_e(p_180633_1_, p_180633_2_, p_180633_3_);
      }

   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      if (!p_189540_2_.field_72995_K) {
         this.func_176316_e(p_189540_2_, p_189540_3_, p_189540_1_);
      }

   }

   public void func_176213_c(World p_176213_1_, BlockPos p_176213_2_, IBlockState p_176213_3_) {
      if (!p_176213_1_.field_72995_K && p_176213_1_.func_175625_s(p_176213_2_) == null) {
         this.func_176316_e(p_176213_1_, p_176213_2_, p_176213_3_);
      }

   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      return this.func_176223_P().func_177226_a(field_176387_N, EnumFacing.func_190914_a(p_180642_2_, p_180642_8_)).func_177226_a(field_176320_b, Boolean.valueOf(false));
   }

   private void func_176316_e(World p_176316_1_, BlockPos p_176316_2_, IBlockState p_176316_3_) {
      EnumFacing enumfacing = (EnumFacing)p_176316_3_.func_177229_b(field_176387_N);
      boolean flag = this.func_176318_b(p_176316_1_, p_176316_2_, enumfacing);
      if (flag && !((Boolean)p_176316_3_.func_177229_b(field_176320_b)).booleanValue()) {
         if ((new BlockPistonStructureHelper(p_176316_1_, p_176316_2_, enumfacing, true)).func_177253_a()) {
            p_176316_1_.func_175641_c(p_176316_2_, this, 0, enumfacing.func_176745_a());
         }
      } else if (!flag && ((Boolean)p_176316_3_.func_177229_b(field_176320_b)).booleanValue()) {
         p_176316_1_.func_175641_c(p_176316_2_, this, 1, enumfacing.func_176745_a());
      }

   }

   private boolean func_176318_b(World p_176318_1_, BlockPos p_176318_2_, EnumFacing p_176318_3_) {
      for(EnumFacing enumfacing : EnumFacing.values()) {
         if (enumfacing != p_176318_3_ && p_176318_1_.func_175709_b(p_176318_2_.func_177972_a(enumfacing), enumfacing)) {
            return true;
         }
      }

      if (p_176318_1_.func_175709_b(p_176318_2_, EnumFacing.DOWN)) {
         return true;
      } else {
         BlockPos blockpos = p_176318_2_.func_177984_a();

         for(EnumFacing enumfacing1 : EnumFacing.values()) {
            if (enumfacing1 != EnumFacing.DOWN && p_176318_1_.func_175709_b(blockpos.func_177972_a(enumfacing1), enumfacing1)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean func_189539_a(IBlockState p_189539_1_, World p_189539_2_, BlockPos p_189539_3_, int p_189539_4_, int p_189539_5_) {
      EnumFacing enumfacing = (EnumFacing)p_189539_1_.func_177229_b(field_176387_N);
      if (!p_189539_2_.field_72995_K) {
         boolean flag = this.func_176318_b(p_189539_2_, p_189539_3_, enumfacing);
         if (flag && p_189539_4_ == 1) {
            p_189539_2_.func_180501_a(p_189539_3_, p_189539_1_.func_177226_a(field_176320_b, Boolean.valueOf(true)), 2);
            return false;
         }

         if (!flag && p_189539_4_ == 0) {
            return false;
         }
      }

      if (p_189539_4_ == 0) {
         if (!this.func_176319_a(p_189539_2_, p_189539_3_, enumfacing, true)) {
            return false;
         }

         p_189539_2_.func_180501_a(p_189539_3_, p_189539_1_.func_177226_a(field_176320_b, Boolean.valueOf(true)), 3);
         p_189539_2_.func_184133_a((EntityPlayer)null, p_189539_3_, SoundEvents.field_187715_dR, SoundCategory.BLOCKS, 0.5F, p_189539_2_.field_73012_v.nextFloat() * 0.25F + 0.6F);
      } else if (p_189539_4_ == 1) {
         TileEntity tileentity1 = p_189539_2_.func_175625_s(p_189539_3_.func_177972_a(enumfacing));
         if (tileentity1 instanceof TileEntityPiston) {
            ((TileEntityPiston)tileentity1).func_145866_f();
         }

         p_189539_2_.func_180501_a(p_189539_3_, Blocks.field_180384_M.func_176223_P().func_177226_a(BlockPistonMoving.field_176426_a, enumfacing).func_177226_a(BlockPistonMoving.field_176425_b, this.field_150082_a ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT), 3);
         p_189539_2_.func_175690_a(p_189539_3_, BlockPistonMoving.func_185588_a(this.func_176203_a(p_189539_5_), enumfacing, false, true));
         if (this.field_150082_a) {
            BlockPos blockpos = p_189539_3_.func_177982_a(enumfacing.func_82601_c() * 2, enumfacing.func_96559_d() * 2, enumfacing.func_82599_e() * 2);
            IBlockState iblockstate = p_189539_2_.func_180495_p(blockpos);
            Block block = iblockstate.func_177230_c();
            boolean flag1 = false;
            if (block == Blocks.field_180384_M) {
               TileEntity tileentity = p_189539_2_.func_175625_s(blockpos);
               if (tileentity instanceof TileEntityPiston) {
                  TileEntityPiston tileentitypiston = (TileEntityPiston)tileentity;
                  if (tileentitypiston.func_174930_e() == enumfacing && tileentitypiston.func_145868_b()) {
                     tileentitypiston.func_145866_f();
                     flag1 = true;
                  }
               }
            }

            if (!flag1 && iblockstate.func_185904_a() != Material.field_151579_a && func_185646_a(iblockstate, p_189539_2_, blockpos, enumfacing.func_176734_d(), false, enumfacing) && (iblockstate.func_185905_o() == EnumPushReaction.NORMAL || block == Blocks.field_150331_J || block == Blocks.field_150320_F)) {
               this.func_176319_a(p_189539_2_, p_189539_3_, enumfacing, false);
            }
         } else {
            p_189539_2_.func_175698_g(p_189539_3_.func_177972_a(enumfacing));
         }

         p_189539_2_.func_184133_a((EntityPlayer)null, p_189539_3_, SoundEvents.field_187712_dQ, SoundCategory.BLOCKS, 0.5F, p_189539_2_.field_73012_v.nextFloat() * 0.15F + 0.6F);
      }

      return true;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   @Nullable
   public static EnumFacing func_176317_b(int p_176317_0_) {
      int i = p_176317_0_ & 7;
      return i > 5 ? null : EnumFacing.func_82600_a(i);
   }

   public static boolean func_185646_a(IBlockState p_185646_0_, World p_185646_1_, BlockPos p_185646_2_, EnumFacing p_185646_3_, boolean p_185646_4_, EnumFacing p_185646_5_) {
      Block block = p_185646_0_.func_177230_c();
      if (block == Blocks.field_150343_Z) {
         return false;
      } else if (!p_185646_1_.func_175723_af().func_177746_a(p_185646_2_)) {
         return false;
      } else if (p_185646_2_.func_177956_o() >= 0 && (p_185646_3_ != EnumFacing.DOWN || p_185646_2_.func_177956_o() != 0)) {
         if (p_185646_2_.func_177956_o() <= p_185646_1_.func_72800_K() - 1 && (p_185646_3_ != EnumFacing.UP || p_185646_2_.func_177956_o() != p_185646_1_.func_72800_K() - 1)) {
            if (block != Blocks.field_150331_J && block != Blocks.field_150320_F) {
               if (p_185646_0_.func_185887_b(p_185646_1_, p_185646_2_) == -1.0F) {
                  return false;
               }

               switch(p_185646_0_.func_185905_o()) {
               case BLOCK:
                  return false;
               case DESTROY:
                  return p_185646_4_;
               case PUSH_ONLY:
                  return p_185646_3_ == p_185646_5_;
               }
            } else if (((Boolean)p_185646_0_.func_177229_b(field_176320_b)).booleanValue()) {
               return false;
            }

            return !block.func_149716_u();
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean func_176319_a(World p_176319_1_, BlockPos p_176319_2_, EnumFacing p_176319_3_, boolean p_176319_4_) {
      if (!p_176319_4_) {
         p_176319_1_.func_175698_g(p_176319_2_.func_177972_a(p_176319_3_));
      }

      BlockPistonStructureHelper blockpistonstructurehelper = new BlockPistonStructureHelper(p_176319_1_, p_176319_2_, p_176319_3_, p_176319_4_);
      if (!blockpistonstructurehelper.func_177253_a()) {
         return false;
      } else {
         List<BlockPos> list = blockpistonstructurehelper.func_177254_c();
         List<IBlockState> list1 = Lists.<IBlockState>newArrayList();

         for(int i = 0; i < list.size(); ++i) {
            BlockPos blockpos = list.get(i);
            list1.add(p_176319_1_.func_180495_p(blockpos).func_185899_b(p_176319_1_, blockpos));
         }

         List<BlockPos> list2 = blockpistonstructurehelper.func_177252_d();
         int k = list.size() + list2.size();
         IBlockState[] aiblockstate = new IBlockState[k];
         EnumFacing enumfacing = p_176319_4_ ? p_176319_3_ : p_176319_3_.func_176734_d();

         for(int j = list2.size() - 1; j >= 0; --j) {
            BlockPos blockpos1 = list2.get(j);
            IBlockState iblockstate = p_176319_1_.func_180495_p(blockpos1);
            iblockstate.func_177230_c().func_176226_b(p_176319_1_, blockpos1, iblockstate, 0);
            p_176319_1_.func_180501_a(blockpos1, Blocks.field_150350_a.func_176223_P(), 4);
            --k;
            aiblockstate[k] = iblockstate;
         }

         for(int l = list.size() - 1; l >= 0; --l) {
            BlockPos blockpos3 = list.get(l);
            IBlockState iblockstate2 = p_176319_1_.func_180495_p(blockpos3);
            p_176319_1_.func_180501_a(blockpos3, Blocks.field_150350_a.func_176223_P(), 2);
            blockpos3 = blockpos3.func_177972_a(enumfacing);
            p_176319_1_.func_180501_a(blockpos3, Blocks.field_180384_M.func_176223_P().func_177226_a(field_176387_N, p_176319_3_), 4);
            p_176319_1_.func_175690_a(blockpos3, BlockPistonMoving.func_185588_a(list1.get(l), p_176319_3_, p_176319_4_, false));
            --k;
            aiblockstate[k] = iblockstate2;
         }

         BlockPos blockpos2 = p_176319_2_.func_177972_a(p_176319_3_);
         if (p_176319_4_) {
            BlockPistonExtension.EnumPistonType blockpistonextension$enumpistontype = this.field_150082_a ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT;
            IBlockState iblockstate3 = Blocks.field_150332_K.func_176223_P().func_177226_a(BlockPistonExtension.field_176387_N, p_176319_3_).func_177226_a(BlockPistonExtension.field_176325_b, blockpistonextension$enumpistontype);
            IBlockState iblockstate1 = Blocks.field_180384_M.func_176223_P().func_177226_a(BlockPistonMoving.field_176426_a, p_176319_3_).func_177226_a(BlockPistonMoving.field_176425_b, this.field_150082_a ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
            p_176319_1_.func_180501_a(blockpos2, iblockstate1, 4);
            p_176319_1_.func_175690_a(blockpos2, BlockPistonMoving.func_185588_a(iblockstate3, p_176319_3_, true, true));
         }

         for(int i1 = list2.size() - 1; i1 >= 0; --i1) {
            p_176319_1_.func_175685_c(list2.get(i1), aiblockstate[k++].func_177230_c(), false);
         }

         for(int j1 = list.size() - 1; j1 >= 0; --j1) {
            p_176319_1_.func_175685_c(list.get(j1), aiblockstate[k++].func_177230_c(), false);
         }

         if (p_176319_4_) {
            p_176319_1_.func_175685_c(blockpos2, Blocks.field_150332_K, false);
         }

         return true;
      }
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176387_N, func_176317_b(p_176203_1_)).func_177226_a(field_176320_b, Boolean.valueOf((p_176203_1_ & 8) > 0));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      int i = 0;
      i = i | ((EnumFacing)p_176201_1_.func_177229_b(field_176387_N)).func_176745_a();
      if (((Boolean)p_176201_1_.func_177229_b(field_176320_b)).booleanValue()) {
         i |= 8;
      }

      return i;
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      return p_185499_1_.func_177226_a(field_176387_N, p_185499_2_.func_185831_a((EnumFacing)p_185499_1_.func_177229_b(field_176387_N)));
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      return p_185471_1_.func_185907_a(p_185471_2_.func_185800_a((EnumFacing)p_185471_1_.func_177229_b(field_176387_N)));
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176387_N, field_176320_b});
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      p_193383_2_ = this.func_176221_a(p_193383_2_, p_193383_1_, p_193383_3_);
      return p_193383_2_.func_177229_b(field_176387_N) != p_193383_4_.func_176734_d() && ((Boolean)p_193383_2_.func_177229_b(field_176320_b)).booleanValue() ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
   }
}
