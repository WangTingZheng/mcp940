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

public class BlockPlanks extends Block {
   public static final PropertyEnum<BlockPlanks.EnumType> field_176383_a = PropertyEnum.<BlockPlanks.EnumType>func_177709_a("variant", BlockPlanks.EnumType.class);

   public BlockPlanks() {
      super(Material.field_151575_d);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176383_a, BlockPlanks.EnumType.OAK));
      this.func_149647_a(CreativeTabs.field_78030_b);
   }

   public int func_180651_a(IBlockState p_180651_1_) {
      return ((BlockPlanks.EnumType)p_180651_1_.func_177229_b(field_176383_a)).func_176839_a();
   }

   public void func_149666_a(CreativeTabs p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
      for(BlockPlanks.EnumType blockplanks$enumtype : BlockPlanks.EnumType.values()) {
         p_149666_2_.add(new ItemStack(this, 1, blockplanks$enumtype.func_176839_a()));
      }

   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176383_a, BlockPlanks.EnumType.func_176837_a(p_176203_1_));
   }

   public MapColor func_180659_g(IBlockState p_180659_1_, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
      return ((BlockPlanks.EnumType)p_180659_1_.func_177229_b(field_176383_a)).func_181070_c();
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((BlockPlanks.EnumType)p_176201_1_.func_177229_b(field_176383_a)).func_176839_a();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176383_a});
   }

   public static enum EnumType implements IStringSerializable {
      OAK(0, "oak", MapColor.field_151663_o),
      SPRUCE(1, "spruce", MapColor.field_151654_J),
      BIRCH(2, "birch", MapColor.field_151658_d),
      JUNGLE(3, "jungle", MapColor.field_151664_l),
      ACACIA(4, "acacia", MapColor.field_151676_q),
      DARK_OAK(5, "dark_oak", "big_oak", MapColor.field_151650_B);

      private static final BlockPlanks.EnumType[] field_176842_g = new BlockPlanks.EnumType[values().length];
      private final int field_176850_h;
      private final String field_176851_i;
      private final String field_176848_j;
      private final MapColor field_181071_k;

      private EnumType(int p_i46388_3_, String p_i46388_4_, MapColor p_i46388_5_) {
         this(p_i46388_3_, p_i46388_4_, p_i46388_4_, p_i46388_5_);
      }

      private EnumType(int p_i46389_3_, String p_i46389_4_, String p_i46389_5_, MapColor p_i46389_6_) {
         this.field_176850_h = p_i46389_3_;
         this.field_176851_i = p_i46389_4_;
         this.field_176848_j = p_i46389_5_;
         this.field_181071_k = p_i46389_6_;
      }

      public int func_176839_a() {
         return this.field_176850_h;
      }

      public MapColor func_181070_c() {
         return this.field_181071_k;
      }

      public String toString() {
         return this.field_176851_i;
      }

      public static BlockPlanks.EnumType func_176837_a(int p_176837_0_) {
         if (p_176837_0_ < 0 || p_176837_0_ >= field_176842_g.length) {
            p_176837_0_ = 0;
         }

         return field_176842_g[p_176837_0_];
      }

      public String func_176610_l() {
         return this.field_176851_i;
      }

      public String func_176840_c() {
         return this.field_176848_j;
      }

      static {
         for(BlockPlanks.EnumType blockplanks$enumtype : values()) {
            field_176842_g[blockplanks$enumtype.func_176839_a()] = blockplanks$enumtype;
         }

      }
   }
}
