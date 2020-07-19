package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;

public class BlockChest extends BlockContainer {
   public static final PropertyDirection field_176459_a = BlockHorizontal.field_185512_D;
   protected static final AxisAlignedBB field_185557_b = new AxisAlignedBB(0.0625D, 0.0D, 0.0D, 0.9375D, 0.875D, 0.9375D);
   protected static final AxisAlignedBB field_185558_c = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.875D, 1.0D);
   protected static final AxisAlignedBB field_185559_d = new AxisAlignedBB(0.0D, 0.0D, 0.0625D, 0.9375D, 0.875D, 0.9375D);
   protected static final AxisAlignedBB field_185560_e = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 1.0D, 0.875D, 0.9375D);
   protected static final AxisAlignedBB field_185561_f = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.875D, 0.9375D);
   public final BlockChest.Type field_149956_a;

   protected BlockChest(BlockChest.Type p_i46689_1_) {
      super(Material.field_151575_d);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176459_a, EnumFacing.NORTH));
      this.field_149956_a = p_i46689_1_;
      this.func_149647_a(p_i46689_1_ == BlockChest.Type.TRAP ? CreativeTabs.field_78028_d : CreativeTabs.field_78031_c);
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_190946_v(IBlockState p_190946_1_) {
      return true;
   }

   public EnumBlockRenderType func_149645_b(IBlockState p_149645_1_) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      if (p_185496_2_.func_180495_p(p_185496_3_.func_177978_c()).func_177230_c() == this) {
         return field_185557_b;
      } else if (p_185496_2_.func_180495_p(p_185496_3_.func_177968_d()).func_177230_c() == this) {
         return field_185558_c;
      } else if (p_185496_2_.func_180495_p(p_185496_3_.func_177976_e()).func_177230_c() == this) {
         return field_185559_d;
      } else {
         return p_185496_2_.func_180495_p(p_185496_3_.func_177974_f()).func_177230_c() == this ? field_185560_e : field_185561_f;
      }
   }

   public void func_176213_c(World p_176213_1_, BlockPos p_176213_2_, IBlockState p_176213_3_) {
      this.func_176455_e(p_176213_1_, p_176213_2_, p_176213_3_);

      for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
         BlockPos blockpos = p_176213_2_.func_177972_a(enumfacing);
         IBlockState iblockstate = p_176213_1_.func_180495_p(blockpos);
         if (iblockstate.func_177230_c() == this) {
            this.func_176455_e(p_176213_1_, blockpos, iblockstate);
         }
      }

   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      return this.func_176223_P().func_177226_a(field_176459_a, p_180642_8_.func_174811_aO());
   }

   public void func_180633_a(World p_180633_1_, BlockPos p_180633_2_, IBlockState p_180633_3_, EntityLivingBase p_180633_4_, ItemStack p_180633_5_) {
      EnumFacing enumfacing = EnumFacing.func_176731_b(MathHelper.func_76128_c((double)(p_180633_4_.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3).func_176734_d();
      p_180633_3_ = p_180633_3_.func_177226_a(field_176459_a, enumfacing);
      BlockPos blockpos = p_180633_2_.func_177978_c();
      BlockPos blockpos1 = p_180633_2_.func_177968_d();
      BlockPos blockpos2 = p_180633_2_.func_177976_e();
      BlockPos blockpos3 = p_180633_2_.func_177974_f();
      boolean flag = this == p_180633_1_.func_180495_p(blockpos).func_177230_c();
      boolean flag1 = this == p_180633_1_.func_180495_p(blockpos1).func_177230_c();
      boolean flag2 = this == p_180633_1_.func_180495_p(blockpos2).func_177230_c();
      boolean flag3 = this == p_180633_1_.func_180495_p(blockpos3).func_177230_c();
      if (!flag && !flag1 && !flag2 && !flag3) {
         p_180633_1_.func_180501_a(p_180633_2_, p_180633_3_, 3);
      } else if (enumfacing.func_176740_k() != EnumFacing.Axis.X || !flag && !flag1) {
         if (enumfacing.func_176740_k() == EnumFacing.Axis.Z && (flag2 || flag3)) {
            if (flag2) {
               p_180633_1_.func_180501_a(blockpos2, p_180633_3_, 3);
            } else {
               p_180633_1_.func_180501_a(blockpos3, p_180633_3_, 3);
            }

            p_180633_1_.func_180501_a(p_180633_2_, p_180633_3_, 3);
         }
      } else {
         if (flag) {
            p_180633_1_.func_180501_a(blockpos, p_180633_3_, 3);
         } else {
            p_180633_1_.func_180501_a(blockpos1, p_180633_3_, 3);
         }

         p_180633_1_.func_180501_a(p_180633_2_, p_180633_3_, 3);
      }

      if (p_180633_5_.func_82837_s()) {
         TileEntity tileentity = p_180633_1_.func_175625_s(p_180633_2_);
         if (tileentity instanceof TileEntityChest) {
            ((TileEntityChest)tileentity).func_190575_a(p_180633_5_.func_82833_r());
         }
      }

   }

   public IBlockState func_176455_e(World p_176455_1_, BlockPos p_176455_2_, IBlockState p_176455_3_) {
      if (p_176455_1_.field_72995_K) {
         return p_176455_3_;
      } else {
         IBlockState iblockstate = p_176455_1_.func_180495_p(p_176455_2_.func_177978_c());
         IBlockState iblockstate1 = p_176455_1_.func_180495_p(p_176455_2_.func_177968_d());
         IBlockState iblockstate2 = p_176455_1_.func_180495_p(p_176455_2_.func_177976_e());
         IBlockState iblockstate3 = p_176455_1_.func_180495_p(p_176455_2_.func_177974_f());
         EnumFacing enumfacing = (EnumFacing)p_176455_3_.func_177229_b(field_176459_a);
         if (iblockstate.func_177230_c() != this && iblockstate1.func_177230_c() != this) {
            boolean flag = iblockstate.func_185913_b();
            boolean flag1 = iblockstate1.func_185913_b();
            if (iblockstate2.func_177230_c() == this || iblockstate3.func_177230_c() == this) {
               BlockPos blockpos1 = iblockstate2.func_177230_c() == this ? p_176455_2_.func_177976_e() : p_176455_2_.func_177974_f();
               IBlockState iblockstate7 = p_176455_1_.func_180495_p(blockpos1.func_177978_c());
               IBlockState iblockstate6 = p_176455_1_.func_180495_p(blockpos1.func_177968_d());
               enumfacing = EnumFacing.SOUTH;
               EnumFacing enumfacing2;
               if (iblockstate2.func_177230_c() == this) {
                  enumfacing2 = (EnumFacing)iblockstate2.func_177229_b(field_176459_a);
               } else {
                  enumfacing2 = (EnumFacing)iblockstate3.func_177229_b(field_176459_a);
               }

               if (enumfacing2 == EnumFacing.NORTH) {
                  enumfacing = EnumFacing.NORTH;
               }

               if ((flag || iblockstate7.func_185913_b()) && !flag1 && !iblockstate6.func_185913_b()) {
                  enumfacing = EnumFacing.SOUTH;
               }

               if ((flag1 || iblockstate6.func_185913_b()) && !flag && !iblockstate7.func_185913_b()) {
                  enumfacing = EnumFacing.NORTH;
               }
            }
         } else {
            BlockPos blockpos = iblockstate.func_177230_c() == this ? p_176455_2_.func_177978_c() : p_176455_2_.func_177968_d();
            IBlockState iblockstate4 = p_176455_1_.func_180495_p(blockpos.func_177976_e());
            IBlockState iblockstate5 = p_176455_1_.func_180495_p(blockpos.func_177974_f());
            enumfacing = EnumFacing.EAST;
            EnumFacing enumfacing1;
            if (iblockstate.func_177230_c() == this) {
               enumfacing1 = (EnumFacing)iblockstate.func_177229_b(field_176459_a);
            } else {
               enumfacing1 = (EnumFacing)iblockstate1.func_177229_b(field_176459_a);
            }

            if (enumfacing1 == EnumFacing.WEST) {
               enumfacing = EnumFacing.WEST;
            }

            if ((iblockstate2.func_185913_b() || iblockstate4.func_185913_b()) && !iblockstate3.func_185913_b() && !iblockstate5.func_185913_b()) {
               enumfacing = EnumFacing.EAST;
            }

            if ((iblockstate3.func_185913_b() || iblockstate5.func_185913_b()) && !iblockstate2.func_185913_b() && !iblockstate4.func_185913_b()) {
               enumfacing = EnumFacing.WEST;
            }
         }

         p_176455_3_ = p_176455_3_.func_177226_a(field_176459_a, enumfacing);
         p_176455_1_.func_180501_a(p_176455_2_, p_176455_3_, 3);
         return p_176455_3_;
      }
   }

   public IBlockState func_176458_f(World p_176458_1_, BlockPos p_176458_2_, IBlockState p_176458_3_) {
      EnumFacing enumfacing = null;

      for(EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL) {
         IBlockState iblockstate = p_176458_1_.func_180495_p(p_176458_2_.func_177972_a(enumfacing1));
         if (iblockstate.func_177230_c() == this) {
            return p_176458_3_;
         }

         if (iblockstate.func_185913_b()) {
            if (enumfacing != null) {
               enumfacing = null;
               break;
            }

            enumfacing = enumfacing1;
         }
      }

      if (enumfacing != null) {
         return p_176458_3_.func_177226_a(field_176459_a, enumfacing.func_176734_d());
      } else {
         EnumFacing enumfacing2 = (EnumFacing)p_176458_3_.func_177229_b(field_176459_a);
         if (p_176458_1_.func_180495_p(p_176458_2_.func_177972_a(enumfacing2)).func_185913_b()) {
            enumfacing2 = enumfacing2.func_176734_d();
         }

         if (p_176458_1_.func_180495_p(p_176458_2_.func_177972_a(enumfacing2)).func_185913_b()) {
            enumfacing2 = enumfacing2.func_176746_e();
         }

         if (p_176458_1_.func_180495_p(p_176458_2_.func_177972_a(enumfacing2)).func_185913_b()) {
            enumfacing2 = enumfacing2.func_176734_d();
         }

         return p_176458_3_.func_177226_a(field_176459_a, enumfacing2);
      }
   }

   public boolean func_176196_c(World p_176196_1_, BlockPos p_176196_2_) {
      int i = 0;
      BlockPos blockpos = p_176196_2_.func_177976_e();
      BlockPos blockpos1 = p_176196_2_.func_177974_f();
      BlockPos blockpos2 = p_176196_2_.func_177978_c();
      BlockPos blockpos3 = p_176196_2_.func_177968_d();
      if (p_176196_1_.func_180495_p(blockpos).func_177230_c() == this) {
         if (this.func_176454_e(p_176196_1_, blockpos)) {
            return false;
         }

         ++i;
      }

      if (p_176196_1_.func_180495_p(blockpos1).func_177230_c() == this) {
         if (this.func_176454_e(p_176196_1_, blockpos1)) {
            return false;
         }

         ++i;
      }

      if (p_176196_1_.func_180495_p(blockpos2).func_177230_c() == this) {
         if (this.func_176454_e(p_176196_1_, blockpos2)) {
            return false;
         }

         ++i;
      }

      if (p_176196_1_.func_180495_p(blockpos3).func_177230_c() == this) {
         if (this.func_176454_e(p_176196_1_, blockpos3)) {
            return false;
         }

         ++i;
      }

      return i <= 1;
   }

   private boolean func_176454_e(World p_176454_1_, BlockPos p_176454_2_) {
      if (p_176454_1_.func_180495_p(p_176454_2_).func_177230_c() != this) {
         return false;
      } else {
         for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
            if (p_176454_1_.func_180495_p(p_176454_2_.func_177972_a(enumfacing)).func_177230_c() == this) {
               return true;
            }
         }

         return false;
      }
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      super.func_189540_a(p_189540_1_, p_189540_2_, p_189540_3_, p_189540_4_, p_189540_5_);
      TileEntity tileentity = p_189540_2_.func_175625_s(p_189540_3_);
      if (tileentity instanceof TileEntityChest) {
         tileentity.func_145836_u();
      }

   }

   public void func_180663_b(World p_180663_1_, BlockPos p_180663_2_, IBlockState p_180663_3_) {
      TileEntity tileentity = p_180663_1_.func_175625_s(p_180663_2_);
      if (tileentity instanceof IInventory) {
         InventoryHelper.func_180175_a(p_180663_1_, p_180663_2_, (IInventory)tileentity);
         p_180663_1_.func_175666_e(p_180663_2_, this);
      }

      super.func_180663_b(p_180663_1_, p_180663_2_, p_180663_3_);
   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      if (p_180639_1_.field_72995_K) {
         return true;
      } else {
         ILockableContainer ilockablecontainer = this.func_180676_d(p_180639_1_, p_180639_2_);
         if (ilockablecontainer != null) {
            p_180639_4_.func_71007_a(ilockablecontainer);
            if (this.field_149956_a == BlockChest.Type.BASIC) {
               p_180639_4_.func_71029_a(StatList.field_188063_ac);
            } else if (this.field_149956_a == BlockChest.Type.TRAP) {
               p_180639_4_.func_71029_a(StatList.field_188089_W);
            }
         }

         return true;
      }
   }

   @Nullable
   public ILockableContainer func_180676_d(World p_180676_1_, BlockPos p_180676_2_) {
      return this.func_189418_a(p_180676_1_, p_180676_2_, false);
   }

   @Nullable
   public ILockableContainer func_189418_a(World p_189418_1_, BlockPos p_189418_2_, boolean p_189418_3_) {
      TileEntity tileentity = p_189418_1_.func_175625_s(p_189418_2_);
      if (!(tileentity instanceof TileEntityChest)) {
         return null;
      } else {
         ILockableContainer ilockablecontainer = (TileEntityChest)tileentity;
         if (!p_189418_3_ && this.func_176457_m(p_189418_1_, p_189418_2_)) {
            return null;
         } else {
            for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
               BlockPos blockpos = p_189418_2_.func_177972_a(enumfacing);
               Block block = p_189418_1_.func_180495_p(blockpos).func_177230_c();
               if (block == this) {
                  if (this.func_176457_m(p_189418_1_, blockpos)) {
                     return null;
                  }

                  TileEntity tileentity1 = p_189418_1_.func_175625_s(blockpos);
                  if (tileentity1 instanceof TileEntityChest) {
                     if (enumfacing != EnumFacing.WEST && enumfacing != EnumFacing.NORTH) {
                        ilockablecontainer = new InventoryLargeChest("container.chestDouble", ilockablecontainer, (TileEntityChest)tileentity1);
                     } else {
                        ilockablecontainer = new InventoryLargeChest("container.chestDouble", (TileEntityChest)tileentity1, ilockablecontainer);
                     }
                  }
               }
            }

            return ilockablecontainer;
         }
      }
   }

   public TileEntity func_149915_a(World p_149915_1_, int p_149915_2_) {
      return new TileEntityChest();
   }

   public boolean func_149744_f(IBlockState p_149744_1_) {
      return this.field_149956_a == BlockChest.Type.TRAP;
   }

   public int func_180656_a(IBlockState p_180656_1_, IBlockAccess p_180656_2_, BlockPos p_180656_3_, EnumFacing p_180656_4_) {
      if (!p_180656_1_.func_185897_m()) {
         return 0;
      } else {
         int i = 0;
         TileEntity tileentity = p_180656_2_.func_175625_s(p_180656_3_);
         if (tileentity instanceof TileEntityChest) {
            i = ((TileEntityChest)tileentity).field_145987_o;
         }

         return MathHelper.func_76125_a(i, 0, 15);
      }
   }

   public int func_176211_b(IBlockState p_176211_1_, IBlockAccess p_176211_2_, BlockPos p_176211_3_, EnumFacing p_176211_4_) {
      return p_176211_4_ == EnumFacing.UP ? p_176211_1_.func_185911_a(p_176211_2_, p_176211_3_, p_176211_4_) : 0;
   }

   private boolean func_176457_m(World p_176457_1_, BlockPos p_176457_2_) {
      return this.func_176456_n(p_176457_1_, p_176457_2_) || this.func_176453_o(p_176457_1_, p_176457_2_);
   }

   private boolean func_176456_n(World p_176456_1_, BlockPos p_176456_2_) {
      return p_176456_1_.func_180495_p(p_176456_2_.func_177984_a()).func_185915_l();
   }

   private boolean func_176453_o(World p_176453_1_, BlockPos p_176453_2_) {
      for(Entity entity : p_176453_1_.func_72872_a(EntityOcelot.class, new AxisAlignedBB((double)p_176453_2_.func_177958_n(), (double)(p_176453_2_.func_177956_o() + 1), (double)p_176453_2_.func_177952_p(), (double)(p_176453_2_.func_177958_n() + 1), (double)(p_176453_2_.func_177956_o() + 2), (double)(p_176453_2_.func_177952_p() + 1)))) {
         EntityOcelot entityocelot = (EntityOcelot)entity;
         if (entityocelot.func_70906_o()) {
            return true;
         }
      }

      return false;
   }

   public boolean func_149740_M(IBlockState p_149740_1_) {
      return true;
   }

   public int func_180641_l(IBlockState p_180641_1_, World p_180641_2_, BlockPos p_180641_3_) {
      return Container.func_94526_b(this.func_180676_d(p_180641_2_, p_180641_3_));
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      EnumFacing enumfacing = EnumFacing.func_82600_a(p_176203_1_);
      if (enumfacing.func_176740_k() == EnumFacing.Axis.Y) {
         enumfacing = EnumFacing.NORTH;
      }

      return this.func_176223_P().func_177226_a(field_176459_a, enumfacing);
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((EnumFacing)p_176201_1_.func_177229_b(field_176459_a)).func_176745_a();
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      return p_185499_1_.func_177226_a(field_176459_a, p_185499_2_.func_185831_a((EnumFacing)p_185499_1_.func_177229_b(field_176459_a)));
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      return p_185471_1_.func_185907_a(p_185471_2_.func_185800_a((EnumFacing)p_185471_1_.func_177229_b(field_176459_a)));
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176459_a});
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }

   public static enum Type {
      BASIC,
      TRAP;
   }
}
