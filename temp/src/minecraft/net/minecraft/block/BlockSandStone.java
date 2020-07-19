package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockSandStone extends Block {
   public static final PropertyEnum<BlockSandStone.EnumType> field_176297_a = PropertyEnum.<BlockSandStone.EnumType>func_177709_a("type", BlockSandStone.EnumType.class);

   public BlockSandStone() {
      super(Material.field_151576_e);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176297_a, BlockSandStone.EnumType.DEFAULT));
      this.func_149647_a(CreativeTabs.field_78030_b);
   }

   public int func_180651_a(IBlockState p_180651_1_) {
      return ((BlockSandStone.EnumType)p_180651_1_.func_177229_b(field_176297_a)).func_176675_a();
   }

   public void func_149666_a(CreativeTabs p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
      for(BlockSandStone.EnumType blocksandstone$enumtype : BlockSandStone.EnumType.values()) {
         p_149666_2_.add(new ItemStack(this, 1, blocksandstone$enumtype.func_176675_a()));
      }

   }

   public MapColor func_180659_g(IBlockState p_180659_1_, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
      return MapColor.field_151658_d;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176297_a, BlockSandStone.EnumType.func_176673_a(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((BlockSandStone.EnumType)p_176201_1_.func_177229_b(field_176297_a)).func_176675_a();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176297_a});
   }

   public static enum EnumType implements IStringSerializable {
      DEFAULT(0, "sandstone", "default"),
      CHISELED(1, "chiseled_sandstone", "chiseled"),
      SMOOTH(2, "smooth_sandstone", "smooth");

      private static final BlockSandStone.EnumType[] field_176679_d = new BlockSandStone.EnumType[values().length];
      private final int field_176680_e;
      private final String field_176677_f;
      private final String field_176678_g;

      private EnumType(int p_i45686_3_, String p_i45686_4_, String p_i45686_5_) {
         this.field_176680_e = p_i45686_3_;
         this.field_176677_f = p_i45686_4_;
         this.field_176678_g = p_i45686_5_;
      }

      public int func_176675_a() {
         return this.field_176680_e;
      }

      public String toString() {
         return this.field_176677_f;
      }

      public static BlockSandStone.EnumType func_176673_a(int p_176673_0_) {
         if (p_176673_0_ < 0 || p_176673_0_ >= field_176679_d.length) {
            p_176673_0_ = 0;
         }

         return field_176679_d[p_176673_0_];
      }

      public String func_176610_l() {
         return this.field_176677_f;
      }

      public String func_176676_c() {
         return this.field_176678_g;
      }

      static {
         for(BlockSandStone.EnumType blocksandstone$enumtype : values()) {
            field_176679_d[blocksandstone$enumtype.func_176675_a()] = blocksandstone$enumtype;
         }

      }
   }
}
