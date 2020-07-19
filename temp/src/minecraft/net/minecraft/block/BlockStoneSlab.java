package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockStoneSlab extends BlockSlab {
   public static final PropertyBool field_176555_b = PropertyBool.func_177716_a("seamless");
   public static final PropertyEnum<BlockStoneSlab.EnumType> field_176556_M = PropertyEnum.<BlockStoneSlab.EnumType>func_177709_a("variant", BlockStoneSlab.EnumType.class);

   public BlockStoneSlab() {
      super(Material.field_151576_e);
      IBlockState iblockstate = this.field_176227_L.func_177621_b();
      if (this.func_176552_j()) {
         iblockstate = iblockstate.func_177226_a(field_176555_b, Boolean.valueOf(false));
      } else {
         iblockstate = iblockstate.func_177226_a(field_176554_a, BlockSlab.EnumBlockHalf.BOTTOM);
      }

      this.func_180632_j(iblockstate.func_177226_a(field_176556_M, BlockStoneSlab.EnumType.STONE));
      this.func_149647_a(CreativeTabs.field_78030_b);
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Item.func_150898_a(Blocks.field_150333_U);
   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return new ItemStack(Blocks.field_150333_U, 1, ((BlockStoneSlab.EnumType)p_185473_3_.func_177229_b(field_176556_M)).func_176624_a());
   }

   public String func_150002_b(int p_150002_1_) {
      return super.func_149739_a() + "." + BlockStoneSlab.EnumType.func_176625_a(p_150002_1_).func_176627_c();
   }

   public IProperty<?> func_176551_l() {
      return field_176556_M;
   }

   public Comparable<?> func_185674_a(ItemStack p_185674_1_) {
      return BlockStoneSlab.EnumType.func_176625_a(p_185674_1_.func_77960_j() & 7);
   }

   public void func_149666_a(CreativeTabs p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
      for(BlockStoneSlab.EnumType blockstoneslab$enumtype : BlockStoneSlab.EnumType.values()) {
         if (blockstoneslab$enumtype != BlockStoneSlab.EnumType.WOOD) {
            p_149666_2_.add(new ItemStack(this, 1, blockstoneslab$enumtype.func_176624_a()));
         }
      }

   }

   public IBlockState func_176203_a(int p_176203_1_) {
      IBlockState iblockstate = this.func_176223_P().func_177226_a(field_176556_M, BlockStoneSlab.EnumType.func_176625_a(p_176203_1_ & 7));
      if (this.func_176552_j()) {
         iblockstate = iblockstate.func_177226_a(field_176555_b, Boolean.valueOf((p_176203_1_ & 8) != 0));
      } else {
         iblockstate = iblockstate.func_177226_a(field_176554_a, (p_176203_1_ & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
      }

      return iblockstate;
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      int i = 0;
      i = i | ((BlockStoneSlab.EnumType)p_176201_1_.func_177229_b(field_176556_M)).func_176624_a();
      if (this.func_176552_j()) {
         if (((Boolean)p_176201_1_.func_177229_b(field_176555_b)).booleanValue()) {
            i |= 8;
         }
      } else if (p_176201_1_.func_177229_b(field_176554_a) == BlockSlab.EnumBlockHalf.TOP) {
         i |= 8;
      }

      return i;
   }

   protected BlockStateContainer func_180661_e() {
      return this.func_176552_j() ? new BlockStateContainer(this, new IProperty[]{field_176555_b, field_176556_M}) : new BlockStateContainer(this, new IProperty[]{field_176554_a, field_176556_M});
   }

   public int func_180651_a(IBlockState p_180651_1_) {
      return ((BlockStoneSlab.EnumType)p_180651_1_.func_177229_b(field_176556_M)).func_176624_a();
   }

   public MapColor func_180659_g(IBlockState p_180659_1_, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
      return ((BlockStoneSlab.EnumType)p_180659_1_.func_177229_b(field_176556_M)).func_181074_c();
   }

   public static enum EnumType implements IStringSerializable {
      STONE(0, MapColor.field_151665_m, "stone"),
      SAND(1, MapColor.field_151658_d, "sandstone", "sand"),
      WOOD(2, MapColor.field_151663_o, "wood_old", "wood"),
      COBBLESTONE(3, MapColor.field_151665_m, "cobblestone", "cobble"),
      BRICK(4, MapColor.field_151645_D, "brick"),
      SMOOTHBRICK(5, MapColor.field_151665_m, "stone_brick", "smoothStoneBrick"),
      NETHERBRICK(6, MapColor.field_151655_K, "nether_brick", "netherBrick"),
      QUARTZ(7, MapColor.field_151677_p, "quartz");

      private static final BlockStoneSlab.EnumType[] field_176640_i = new BlockStoneSlab.EnumType[values().length];
      private final int field_176637_j;
      private final MapColor field_181075_k;
      private final String field_176638_k;
      private final String field_176635_l;

      private EnumType(int p_i46381_3_, MapColor p_i46381_4_, String p_i46381_5_) {
         this(p_i46381_3_, p_i46381_4_, p_i46381_5_, p_i46381_5_);
      }

      private EnumType(int p_i46382_3_, MapColor p_i46382_4_, String p_i46382_5_, String p_i46382_6_) {
         this.field_176637_j = p_i46382_3_;
         this.field_181075_k = p_i46382_4_;
         this.field_176638_k = p_i46382_5_;
         this.field_176635_l = p_i46382_6_;
      }

      public int func_176624_a() {
         return this.field_176637_j;
      }

      public MapColor func_181074_c() {
         return this.field_181075_k;
      }

      public String toString() {
         return this.field_176638_k;
      }

      public static BlockStoneSlab.EnumType func_176625_a(int p_176625_0_) {
         if (p_176625_0_ < 0 || p_176625_0_ >= field_176640_i.length) {
            p_176625_0_ = 0;
         }

         return field_176640_i[p_176625_0_];
      }

      public String func_176610_l() {
         return this.field_176638_k;
      }

      public String func_176627_c() {
         return this.field_176635_l;
      }

      static {
         for(BlockStoneSlab.EnumType blockstoneslab$enumtype : values()) {
            field_176640_i[blockstoneslab$enumtype.func_176624_a()] = blockstoneslab$enumtype;
         }

      }
   }
}
