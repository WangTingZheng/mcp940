package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDaylightDetector;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDaylightDetector extends BlockContainer {
   public static final PropertyInteger field_176436_a = PropertyInteger.func_177719_a("power", 0, 15);
   protected static final AxisAlignedBB field_185566_b = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D);
   private final boolean field_176435_b;

   public BlockDaylightDetector(boolean p_i45729_1_) {
      super(Material.field_151575_d);
      this.field_176435_b = p_i45729_1_;
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176436_a, Integer.valueOf(0)));
      this.func_149647_a(CreativeTabs.field_78028_d);
      this.func_149711_c(0.2F);
      this.func_149672_a(SoundType.field_185848_a);
      this.func_149663_c("daylightDetector");
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      return field_185566_b;
   }

   public int func_180656_a(IBlockState p_180656_1_, IBlockAccess p_180656_2_, BlockPos p_180656_3_, EnumFacing p_180656_4_) {
      return ((Integer)p_180656_1_.func_177229_b(field_176436_a)).intValue();
   }

   public void func_180677_d(World p_180677_1_, BlockPos p_180677_2_) {
      if (p_180677_1_.field_73011_w.func_191066_m()) {
         IBlockState iblockstate = p_180677_1_.func_180495_p(p_180677_2_);
         int i = p_180677_1_.func_175642_b(EnumSkyBlock.SKY, p_180677_2_) - p_180677_1_.func_175657_ab();
         float f = p_180677_1_.func_72929_e(1.0F);
         if (this.field_176435_b) {
            i = 15 - i;
         }

         if (i > 0 && !this.field_176435_b) {
            float f1 = f < 3.1415927F ? 0.0F : 6.2831855F;
            f = f + (f1 - f) * 0.2F;
            i = Math.round((float)i * MathHelper.func_76134_b(f));
         }

         i = MathHelper.func_76125_a(i, 0, 15);
         if (((Integer)iblockstate.func_177229_b(field_176436_a)).intValue() != i) {
            p_180677_1_.func_180501_a(p_180677_2_, iblockstate.func_177226_a(field_176436_a, Integer.valueOf(i)), 3);
         }

      }
   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      if (p_180639_4_.func_175142_cm()) {
         if (p_180639_1_.field_72995_K) {
            return true;
         } else {
            if (this.field_176435_b) {
               p_180639_1_.func_180501_a(p_180639_2_, Blocks.field_150453_bW.func_176223_P().func_177226_a(field_176436_a, p_180639_3_.func_177229_b(field_176436_a)), 4);
               Blocks.field_150453_bW.func_180677_d(p_180639_1_, p_180639_2_);
            } else {
               p_180639_1_.func_180501_a(p_180639_2_, Blocks.field_180402_cm.func_176223_P().func_177226_a(field_176436_a, p_180639_3_.func_177229_b(field_176436_a)), 4);
               Blocks.field_180402_cm.func_180677_d(p_180639_1_, p_180639_2_);
            }

            return true;
         }
      } else {
         return super.func_180639_a(p_180639_1_, p_180639_2_, p_180639_3_, p_180639_4_, p_180639_5_, p_180639_6_, p_180639_7_, p_180639_8_, p_180639_9_);
      }
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Item.func_150898_a(Blocks.field_150453_bW);
   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return new ItemStack(Blocks.field_150453_bW);
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public EnumBlockRenderType func_149645_b(IBlockState p_149645_1_) {
      return EnumBlockRenderType.MODEL;
   }

   public boolean func_149744_f(IBlockState p_149744_1_) {
      return true;
   }

   public TileEntity func_149915_a(World p_149915_1_, int p_149915_2_) {
      return new TileEntityDaylightDetector();
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176436_a, Integer.valueOf(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((Integer)p_176201_1_.func_177229_b(field_176436_a)).intValue();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176436_a});
   }

   public void func_149666_a(CreativeTabs p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
      if (!this.field_176435_b) {
         super.func_149666_a(p_149666_1_, p_149666_2_);
      }

   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return p_193383_4_ == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
   }
}
