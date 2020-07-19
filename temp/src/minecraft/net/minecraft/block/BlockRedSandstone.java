package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;

public class BlockRedSandstone extends Block {
   public static final PropertyEnum<BlockRedSandstone.EnumType> field_176336_a = PropertyEnum.<BlockRedSandstone.EnumType>func_177709_a("type", BlockRedSandstone.EnumType.class);

   public BlockRedSandstone() {
      super(Material.field_151576_e, BlockSand.EnumType.RED_SAND.func_176687_c());
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176336_a, BlockRedSandstone.EnumType.DEFAULT));
      this.func_149647_a(CreativeTabs.field_78030_b);
   }

   public int func_180651_a(IBlockState p_180651_1_) {
      return ((BlockRedSandstone.EnumType)p_180651_1_.func_177229_b(field_176336_a)).func_176827_a();
   }

   public void func_149666_a(CreativeTabs p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
      for(BlockRedSandstone.EnumType blockredsandstone$enumtype : BlockRedSandstone.EnumType.values()) {
         p_149666_2_.add(new ItemStack(this, 1, blockredsandstone$enumtype.func_176827_a()));
      }

   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176336_a, BlockRedSandstone.EnumType.func_176825_a(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((BlockRedSandstone.EnumType)p_176201_1_.func_177229_b(field_176336_a)).func_176827_a();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176336_a});
   }

   public static enum EnumType implements IStringSerializable {
      DEFAULT(0, "red_sandstone", "default"),
      CHISELED(1, "chiseled_red_sandstone", "chiseled"),
      SMOOTH(2, "smooth_red_sandstone", "smooth");

      private static final BlockRedSandstone.EnumType[] field_176831_d = new BlockRedSandstone.EnumType[values().length];
      private final int field_176832_e;
      private final String field_176829_f;
      private final String field_176830_g;

      private EnumType(int p_i45690_3_, String p_i45690_4_, String p_i45690_5_) {
         this.field_176832_e = p_i45690_3_;
         this.field_176829_f = p_i45690_4_;
         this.field_176830_g = p_i45690_5_;
      }

      public int func_176827_a() {
         return this.field_176832_e;
      }

      public String toString() {
         return this.field_176829_f;
      }

      public static BlockRedSandstone.EnumType func_176825_a(int p_176825_0_) {
         if (p_176825_0_ < 0 || p_176825_0_ >= field_176831_d.length) {
            p_176825_0_ = 0;
         }

         return field_176831_d[p_176825_0_];
      }

      public String func_176610_l() {
         return this.field_176829_f;
      }

      public String func_176828_c() {
         return this.field_176830_g;
      }

      static {
         for(BlockRedSandstone.EnumType blockredsandstone$enumtype : values()) {
            field_176831_d[blockredsandstone$enumtype.func_176827_a()] = blockredsandstone$enumtype;
         }

      }
   }
}
