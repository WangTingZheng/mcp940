package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSilverfish extends Block {
   public static final PropertyEnum<BlockSilverfish.EnumType> field_176378_a = PropertyEnum.<BlockSilverfish.EnumType>func_177709_a("variant", BlockSilverfish.EnumType.class);

   public BlockSilverfish() {
      super(Material.field_151571_B);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176378_a, BlockSilverfish.EnumType.STONE));
      this.func_149711_c(0.0F);
      this.func_149647_a(CreativeTabs.field_78031_c);
   }

   public int func_149745_a(Random p_149745_1_) {
      return 0;
   }

   public static boolean func_176377_d(IBlockState p_176377_0_) {
      Block block = p_176377_0_.func_177230_c();
      return p_176377_0_ == Blocks.field_150348_b.func_176223_P().func_177226_a(BlockStone.field_176247_a, BlockStone.EnumType.STONE) || block == Blocks.field_150347_e || block == Blocks.field_150417_aV;
   }

   protected ItemStack func_180643_i(IBlockState p_180643_1_) {
      switch((BlockSilverfish.EnumType)p_180643_1_.func_177229_b(field_176378_a)) {
      case COBBLESTONE:
         return new ItemStack(Blocks.field_150347_e);
      case STONEBRICK:
         return new ItemStack(Blocks.field_150417_aV);
      case MOSSY_STONEBRICK:
         return new ItemStack(Blocks.field_150417_aV, 1, BlockStoneBrick.EnumType.MOSSY.func_176612_a());
      case CRACKED_STONEBRICK:
         return new ItemStack(Blocks.field_150417_aV, 1, BlockStoneBrick.EnumType.CRACKED.func_176612_a());
      case CHISELED_STONEBRICK:
         return new ItemStack(Blocks.field_150417_aV, 1, BlockStoneBrick.EnumType.CHISELED.func_176612_a());
      default:
         return new ItemStack(Blocks.field_150348_b);
      }
   }

   public void func_180653_a(World p_180653_1_, BlockPos p_180653_2_, IBlockState p_180653_3_, float p_180653_4_, int p_180653_5_) {
      if (!p_180653_1_.field_72995_K && p_180653_1_.func_82736_K().func_82766_b("doTileDrops")) {
         EntitySilverfish entitysilverfish = new EntitySilverfish(p_180653_1_);
         entitysilverfish.func_70012_b((double)p_180653_2_.func_177958_n() + 0.5D, (double)p_180653_2_.func_177956_o(), (double)p_180653_2_.func_177952_p() + 0.5D, 0.0F, 0.0F);
         p_180653_1_.func_72838_d(entitysilverfish);
         entitysilverfish.func_70656_aK();
      }

   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return new ItemStack(this, 1, p_185473_3_.func_177230_c().func_176201_c(p_185473_3_));
   }

   public void func_149666_a(CreativeTabs p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
      for(BlockSilverfish.EnumType blocksilverfish$enumtype : BlockSilverfish.EnumType.values()) {
         p_149666_2_.add(new ItemStack(this, 1, blocksilverfish$enumtype.func_176881_a()));
      }

   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176378_a, BlockSilverfish.EnumType.func_176879_a(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((BlockSilverfish.EnumType)p_176201_1_.func_177229_b(field_176378_a)).func_176881_a();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176378_a});
   }

   public static enum EnumType implements IStringSerializable {
      STONE(0, "stone") {
         public IBlockState func_176883_d() {
            return Blocks.field_150348_b.func_176223_P().func_177226_a(BlockStone.field_176247_a, BlockStone.EnumType.STONE);
         }
      },
      COBBLESTONE(1, "cobblestone", "cobble") {
         public IBlockState func_176883_d() {
            return Blocks.field_150347_e.func_176223_P();
         }
      },
      STONEBRICK(2, "stone_brick", "brick") {
         public IBlockState func_176883_d() {
            return Blocks.field_150417_aV.func_176223_P().func_177226_a(BlockStoneBrick.field_176249_a, BlockStoneBrick.EnumType.DEFAULT);
         }
      },
      MOSSY_STONEBRICK(3, "mossy_brick", "mossybrick") {
         public IBlockState func_176883_d() {
            return Blocks.field_150417_aV.func_176223_P().func_177226_a(BlockStoneBrick.field_176249_a, BlockStoneBrick.EnumType.MOSSY);
         }
      },
      CRACKED_STONEBRICK(4, "cracked_brick", "crackedbrick") {
         public IBlockState func_176883_d() {
            return Blocks.field_150417_aV.func_176223_P().func_177226_a(BlockStoneBrick.field_176249_a, BlockStoneBrick.EnumType.CRACKED);
         }
      },
      CHISELED_STONEBRICK(5, "chiseled_brick", "chiseledbrick") {
         public IBlockState func_176883_d() {
            return Blocks.field_150417_aV.func_176223_P().func_177226_a(BlockStoneBrick.field_176249_a, BlockStoneBrick.EnumType.CHISELED);
         }
      };

      private static final BlockSilverfish.EnumType[] field_176885_g = new BlockSilverfish.EnumType[values().length];
      private final int field_176893_h;
      private final String field_176894_i;
      private final String field_176891_j;

      private EnumType(int p_i45704_3_, String p_i45704_4_) {
         this(p_i45704_3_, p_i45704_4_, p_i45704_4_);
      }

      private EnumType(int p_i45705_3_, String p_i45705_4_, String p_i45705_5_) {
         this.field_176893_h = p_i45705_3_;
         this.field_176894_i = p_i45705_4_;
         this.field_176891_j = p_i45705_5_;
      }

      public int func_176881_a() {
         return this.field_176893_h;
      }

      public String toString() {
         return this.field_176894_i;
      }

      public static BlockSilverfish.EnumType func_176879_a(int p_176879_0_) {
         if (p_176879_0_ < 0 || p_176879_0_ >= field_176885_g.length) {
            p_176879_0_ = 0;
         }

         return field_176885_g[p_176879_0_];
      }

      public String func_176610_l() {
         return this.field_176894_i;
      }

      public String func_176882_c() {
         return this.field_176891_j;
      }

      public abstract IBlockState func_176883_d();

      public static BlockSilverfish.EnumType func_176878_a(IBlockState p_176878_0_) {
         for(BlockSilverfish.EnumType blocksilverfish$enumtype : values()) {
            if (p_176878_0_ == blocksilverfish$enumtype.func_176883_d()) {
               return blocksilverfish$enumtype;
            }
         }

         return STONE;
      }

      static {
         for(BlockSilverfish.EnumType blocksilverfish$enumtype : values()) {
            field_176885_g[blocksilverfish$enumtype.func_176881_a()] = blocksilverfish$enumtype;
         }

      }
   }
}
