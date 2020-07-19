package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDirt extends Block {
   public static final PropertyEnum<BlockDirt.DirtType> field_176386_a = PropertyEnum.<BlockDirt.DirtType>func_177709_a("variant", BlockDirt.DirtType.class);
   public static final PropertyBool field_176385_b = PropertyBool.func_177716_a("snowy");

   protected BlockDirt() {
      super(Material.field_151578_c);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176386_a, BlockDirt.DirtType.DIRT).func_177226_a(field_176385_b, Boolean.valueOf(false)));
      this.func_149647_a(CreativeTabs.field_78030_b);
   }

   public MapColor func_180659_g(IBlockState p_180659_1_, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
      return ((BlockDirt.DirtType)p_180659_1_.func_177229_b(field_176386_a)).func_181066_d();
   }

   public IBlockState func_176221_a(IBlockState p_176221_1_, IBlockAccess p_176221_2_, BlockPos p_176221_3_) {
      if (p_176221_1_.func_177229_b(field_176386_a) == BlockDirt.DirtType.PODZOL) {
         Block block = p_176221_2_.func_180495_p(p_176221_3_.func_177984_a()).func_177230_c();
         p_176221_1_ = p_176221_1_.func_177226_a(field_176385_b, Boolean.valueOf(block == Blocks.field_150433_aE || block == Blocks.field_150431_aC));
      }

      return p_176221_1_;
   }

   public void func_149666_a(CreativeTabs p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
      p_149666_2_.add(new ItemStack(this, 1, BlockDirt.DirtType.DIRT.func_176925_a()));
      p_149666_2_.add(new ItemStack(this, 1, BlockDirt.DirtType.COARSE_DIRT.func_176925_a()));
      p_149666_2_.add(new ItemStack(this, 1, BlockDirt.DirtType.PODZOL.func_176925_a()));
   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return new ItemStack(this, 1, ((BlockDirt.DirtType)p_185473_3_.func_177229_b(field_176386_a)).func_176925_a());
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176386_a, BlockDirt.DirtType.func_176924_a(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((BlockDirt.DirtType)p_176201_1_.func_177229_b(field_176386_a)).func_176925_a();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176386_a, field_176385_b});
   }

   public int func_180651_a(IBlockState p_180651_1_) {
      BlockDirt.DirtType blockdirt$dirttype = (BlockDirt.DirtType)p_180651_1_.func_177229_b(field_176386_a);
      if (blockdirt$dirttype == BlockDirt.DirtType.PODZOL) {
         blockdirt$dirttype = BlockDirt.DirtType.DIRT;
      }

      return blockdirt$dirttype.func_176925_a();
   }

   public static enum DirtType implements IStringSerializable {
      DIRT(0, "dirt", "default", MapColor.field_151664_l),
      COARSE_DIRT(1, "coarse_dirt", "coarse", MapColor.field_151664_l),
      PODZOL(2, "podzol", MapColor.field_151654_J);

      private static final BlockDirt.DirtType[] field_176930_d = new BlockDirt.DirtType[values().length];
      private final int field_176931_e;
      private final String field_176928_f;
      private final String field_176929_g;
      private final MapColor field_181067_h;

      private DirtType(int p_i46396_3_, String p_i46396_4_, MapColor p_i46396_5_) {
         this(p_i46396_3_, p_i46396_4_, p_i46396_4_, p_i46396_5_);
      }

      private DirtType(int p_i46397_3_, String p_i46397_4_, String p_i46397_5_, MapColor p_i46397_6_) {
         this.field_176931_e = p_i46397_3_;
         this.field_176928_f = p_i46397_4_;
         this.field_176929_g = p_i46397_5_;
         this.field_181067_h = p_i46397_6_;
      }

      public int func_176925_a() {
         return this.field_176931_e;
      }

      public String func_176927_c() {
         return this.field_176929_g;
      }

      public MapColor func_181066_d() {
         return this.field_181067_h;
      }

      public String toString() {
         return this.field_176928_f;
      }

      public static BlockDirt.DirtType func_176924_a(int p_176924_0_) {
         if (p_176924_0_ < 0 || p_176924_0_ >= field_176930_d.length) {
            p_176924_0_ = 0;
         }

         return field_176930_d[p_176924_0_];
      }

      public String func_176610_l() {
         return this.field_176928_f;
      }

      static {
         for(BlockDirt.DirtType blockdirt$dirttype : values()) {
            field_176930_d[blockdirt$dirttype.func_176925_a()] = blockdirt$dirttype;
         }

      }
   }
}
