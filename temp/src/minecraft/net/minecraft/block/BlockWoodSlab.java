package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockWoodSlab extends BlockSlab {
   public static final PropertyEnum<BlockPlanks.EnumType> field_176557_b = PropertyEnum.<BlockPlanks.EnumType>func_177709_a("variant", BlockPlanks.EnumType.class);

   public BlockWoodSlab() {
      super(Material.field_151575_d);
      IBlockState iblockstate = this.field_176227_L.func_177621_b();
      if (!this.func_176552_j()) {
         iblockstate = iblockstate.func_177226_a(field_176554_a, BlockSlab.EnumBlockHalf.BOTTOM);
      }

      this.func_180632_j(iblockstate.func_177226_a(field_176557_b, BlockPlanks.EnumType.OAK));
      this.func_149647_a(CreativeTabs.field_78030_b);
   }

   public MapColor func_180659_g(IBlockState p_180659_1_, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
      return ((BlockPlanks.EnumType)p_180659_1_.func_177229_b(field_176557_b)).func_181070_c();
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Item.func_150898_a(Blocks.field_150376_bx);
   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return new ItemStack(Blocks.field_150376_bx, 1, ((BlockPlanks.EnumType)p_185473_3_.func_177229_b(field_176557_b)).func_176839_a());
   }

   public String func_150002_b(int p_150002_1_) {
      return super.func_149739_a() + "." + BlockPlanks.EnumType.func_176837_a(p_150002_1_).func_176840_c();
   }

   public IProperty<?> func_176551_l() {
      return field_176557_b;
   }

   public Comparable<?> func_185674_a(ItemStack p_185674_1_) {
      return BlockPlanks.EnumType.func_176837_a(p_185674_1_.func_77960_j() & 7);
   }

   public void func_149666_a(CreativeTabs p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
      for(BlockPlanks.EnumType blockplanks$enumtype : BlockPlanks.EnumType.values()) {
         p_149666_2_.add(new ItemStack(this, 1, blockplanks$enumtype.func_176839_a()));
      }

   }

   public IBlockState func_176203_a(int p_176203_1_) {
      IBlockState iblockstate = this.func_176223_P().func_177226_a(field_176557_b, BlockPlanks.EnumType.func_176837_a(p_176203_1_ & 7));
      if (!this.func_176552_j()) {
         iblockstate = iblockstate.func_177226_a(field_176554_a, (p_176203_1_ & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
      }

      return iblockstate;
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      int i = 0;
      i = i | ((BlockPlanks.EnumType)p_176201_1_.func_177229_b(field_176557_b)).func_176839_a();
      if (!this.func_176552_j() && p_176201_1_.func_177229_b(field_176554_a) == BlockSlab.EnumBlockHalf.TOP) {
         i |= 8;
      }

      return i;
   }

   protected BlockStateContainer func_180661_e() {
      return this.func_176552_j() ? new BlockStateContainer(this, new IProperty[]{field_176557_b}) : new BlockStateContainer(this, new IProperty[]{field_176554_a, field_176557_b});
   }

   public int func_180651_a(IBlockState p_180651_1_) {
      return ((BlockPlanks.EnumType)p_180651_1_.func_177229_b(field_176557_b)).func_176839_a();
   }
}
