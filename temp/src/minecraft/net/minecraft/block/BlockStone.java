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
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;

public class BlockStone extends Block {
   public static final PropertyEnum<BlockStone.EnumType> field_176247_a = PropertyEnum.<BlockStone.EnumType>func_177709_a("variant", BlockStone.EnumType.class);

   public BlockStone() {
      super(Material.field_151576_e);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176247_a, BlockStone.EnumType.STONE));
      this.func_149647_a(CreativeTabs.field_78030_b);
   }

   public String func_149732_F() {
      return I18n.func_74838_a(this.func_149739_a() + "." + BlockStone.EnumType.STONE.func_176644_c() + ".name");
   }

   public MapColor func_180659_g(IBlockState p_180659_1_, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
      return ((BlockStone.EnumType)p_180659_1_.func_177229_b(field_176247_a)).func_181072_c();
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return p_180660_1_.func_177229_b(field_176247_a) == BlockStone.EnumType.STONE ? Item.func_150898_a(Blocks.field_150347_e) : Item.func_150898_a(Blocks.field_150348_b);
   }

   public int func_180651_a(IBlockState p_180651_1_) {
      return ((BlockStone.EnumType)p_180651_1_.func_177229_b(field_176247_a)).func_176642_a();
   }

   public void func_149666_a(CreativeTabs p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
      for(BlockStone.EnumType blockstone$enumtype : BlockStone.EnumType.values()) {
         p_149666_2_.add(new ItemStack(this, 1, blockstone$enumtype.func_176642_a()));
      }

   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176247_a, BlockStone.EnumType.func_176643_a(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((BlockStone.EnumType)p_176201_1_.func_177229_b(field_176247_a)).func_176642_a();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176247_a});
   }

   public static enum EnumType implements IStringSerializable {
      STONE(0, MapColor.field_151665_m, "stone", true),
      GRANITE(1, MapColor.field_151664_l, "granite", true),
      GRANITE_SMOOTH(2, MapColor.field_151664_l, "smooth_granite", "graniteSmooth", false),
      DIORITE(3, MapColor.field_151677_p, "diorite", true),
      DIORITE_SMOOTH(4, MapColor.field_151677_p, "smooth_diorite", "dioriteSmooth", false),
      ANDESITE(5, MapColor.field_151665_m, "andesite", true),
      ANDESITE_SMOOTH(6, MapColor.field_151665_m, "smooth_andesite", "andesiteSmooth", false);

      private static final BlockStone.EnumType[] field_176655_h = new BlockStone.EnumType[values().length];
      private final int field_176656_i;
      private final String field_176653_j;
      private final String field_176654_k;
      private final MapColor field_181073_l;
      private final boolean field_190913_m;

      private EnumType(int p_i46383_3_, MapColor p_i46383_4_, String p_i46383_5_, boolean p_i46383_6_) {
         this(p_i46383_3_, p_i46383_4_, p_i46383_5_, p_i46383_5_, p_i46383_6_);
      }

      private EnumType(int p_i46384_3_, MapColor p_i46384_4_, String p_i46384_5_, String p_i46384_6_, boolean p_i46384_7_) {
         this.field_176656_i = p_i46384_3_;
         this.field_176653_j = p_i46384_5_;
         this.field_176654_k = p_i46384_6_;
         this.field_181073_l = p_i46384_4_;
         this.field_190913_m = p_i46384_7_;
      }

      public int func_176642_a() {
         return this.field_176656_i;
      }

      public MapColor func_181072_c() {
         return this.field_181073_l;
      }

      public String toString() {
         return this.field_176653_j;
      }

      public static BlockStone.EnumType func_176643_a(int p_176643_0_) {
         if (p_176643_0_ < 0 || p_176643_0_ >= field_176655_h.length) {
            p_176643_0_ = 0;
         }

         return field_176655_h[p_176643_0_];
      }

      public String func_176610_l() {
         return this.field_176653_j;
      }

      public String func_176644_c() {
         return this.field_176654_k;
      }

      public boolean func_190912_e() {
         return this.field_190913_m;
      }

      static {
         for(BlockStone.EnumType blockstone$enumtype : values()) {
            field_176655_h[blockstone$enumtype.func_176642_a()] = blockstone$enumtype;
         }

      }
   }
}
