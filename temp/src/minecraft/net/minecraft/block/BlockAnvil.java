package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockAnvil extends BlockFalling {
   public static final PropertyDirection field_176506_a = BlockHorizontal.field_185512_D;
   public static final PropertyInteger field_176505_b = PropertyInteger.func_177719_a("damage", 0, 2);
   protected static final AxisAlignedBB field_185760_c = new AxisAlignedBB(0.0D, 0.0D, 0.125D, 1.0D, 1.0D, 0.875D);
   protected static final AxisAlignedBB field_185761_d = new AxisAlignedBB(0.125D, 0.0D, 0.0D, 0.875D, 1.0D, 1.0D);
   protected static final Logger field_185762_e = LogManager.getLogger();

   protected BlockAnvil() {
      super(Material.field_151574_g);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176506_a, EnumFacing.NORTH).func_177226_a(field_176505_b, Integer.valueOf(0)));
      this.func_149713_g(0);
      this.func_149647_a(CreativeTabs.field_78031_c);
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      EnumFacing enumfacing = p_180642_8_.func_174811_aO().func_176746_e();

      try {
         return super.func_180642_a(p_180642_1_, p_180642_2_, p_180642_3_, p_180642_4_, p_180642_5_, p_180642_6_, p_180642_7_, p_180642_8_).func_177226_a(field_176506_a, enumfacing).func_177226_a(field_176505_b, Integer.valueOf(p_180642_7_ >> 2));
      } catch (IllegalArgumentException var11) {
         if (!p_180642_1_.field_72995_K) {
            field_185762_e.warn(String.format("Invalid damage property for anvil at %s. Found %d, must be in [0, 1, 2]", p_180642_2_, p_180642_7_ >> 2));
            if (p_180642_8_ instanceof EntityPlayer) {
               p_180642_8_.func_145747_a(new TextComponentTranslation("Invalid damage property. Please pick in [0, 1, 2]", new Object[0]));
            }
         }

         return super.func_180642_a(p_180642_1_, p_180642_2_, p_180642_3_, p_180642_4_, p_180642_5_, p_180642_6_, 0, p_180642_8_).func_177226_a(field_176506_a, enumfacing).func_177226_a(field_176505_b, Integer.valueOf(0));
      }
   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      if (!p_180639_1_.field_72995_K) {
         p_180639_4_.func_180468_a(new BlockAnvil.Anvil(p_180639_1_, p_180639_2_));
      }

      return true;
   }

   public int func_180651_a(IBlockState p_180651_1_) {
      return ((Integer)p_180651_1_.func_177229_b(field_176505_b)).intValue();
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      EnumFacing enumfacing = (EnumFacing)p_185496_1_.func_177229_b(field_176506_a);
      return enumfacing.func_176740_k() == EnumFacing.Axis.X ? field_185760_c : field_185761_d;
   }

   public void func_149666_a(CreativeTabs p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
      p_149666_2_.add(new ItemStack(this));
      p_149666_2_.add(new ItemStack(this, 1, 1));
      p_149666_2_.add(new ItemStack(this, 1, 2));
   }

   protected void func_149829_a(EntityFallingBlock p_149829_1_) {
      p_149829_1_.func_145806_a(true);
   }

   public void func_176502_a_(World p_176502_1_, BlockPos p_176502_2_, IBlockState p_176502_3_, IBlockState p_176502_4_) {
      p_176502_1_.func_175718_b(1031, p_176502_2_, 0);
   }

   public void func_190974_b(World p_190974_1_, BlockPos p_190974_2_) {
      p_190974_1_.func_175718_b(1029, p_190974_2_, 0);
   }

   public boolean func_176225_a(IBlockState p_176225_1_, IBlockAccess p_176225_2_, BlockPos p_176225_3_, EnumFacing p_176225_4_) {
      return true;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176506_a, EnumFacing.func_176731_b(p_176203_1_ & 3)).func_177226_a(field_176505_b, Integer.valueOf((p_176203_1_ & 15) >> 2));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      int i = 0;
      i = i | ((EnumFacing)p_176201_1_.func_177229_b(field_176506_a)).func_176736_b();
      i = i | ((Integer)p_176201_1_.func_177229_b(field_176505_b)).intValue() << 2;
      return i;
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      return p_185499_1_.func_177230_c() != this ? p_185499_1_ : p_185499_1_.func_177226_a(field_176506_a, p_185499_2_.func_185831_a((EnumFacing)p_185499_1_.func_177229_b(field_176506_a)));
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176506_a, field_176505_b});
   }

   public static class Anvil implements IInteractionObject {
      private final World field_175130_a;
      private final BlockPos field_175129_b;

      public Anvil(World p_i45741_1_, BlockPos p_i45741_2_) {
         this.field_175130_a = p_i45741_1_;
         this.field_175129_b = p_i45741_2_;
      }

      public String func_70005_c_() {
         return "anvil";
      }

      public boolean func_145818_k_() {
         return false;
      }

      public ITextComponent func_145748_c_() {
         return new TextComponentTranslation(Blocks.field_150467_bQ.func_149739_a() + ".name", new Object[0]);
      }

      public Container func_174876_a(InventoryPlayer p_174876_1_, EntityPlayer p_174876_2_) {
         return new ContainerRepair(p_174876_1_, this.field_175130_a, this.field_175129_b, p_174876_2_);
      }

      public String func_174875_k() {
         return "minecraft:anvil";
      }
   }
}
