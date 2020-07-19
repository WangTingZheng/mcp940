package net.minecraft.client.renderer.color;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockStem;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;

public class BlockColors {
   private final ObjectIntIdentityMap<IBlockColor> field_186725_a = new ObjectIntIdentityMap<IBlockColor>(32);

   public static BlockColors func_186723_a() {
      final BlockColors blockcolors = new BlockColors();
      blockcolors.func_186722_a(new IBlockColor() {
         public int func_186720_a(IBlockState p_186720_1_, @Nullable IBlockAccess p_186720_2_, @Nullable BlockPos p_186720_3_, int p_186720_4_) {
            BlockDoublePlant.EnumPlantType blockdoubleplant$enumplanttype = (BlockDoublePlant.EnumPlantType)p_186720_1_.func_177229_b(BlockDoublePlant.field_176493_a);
            return p_186720_2_ != null && p_186720_3_ != null && (blockdoubleplant$enumplanttype == BlockDoublePlant.EnumPlantType.GRASS || blockdoubleplant$enumplanttype == BlockDoublePlant.EnumPlantType.FERN) ? BiomeColorHelper.func_180286_a(p_186720_2_, p_186720_1_.func_177229_b(BlockDoublePlant.field_176492_b) == BlockDoublePlant.EnumBlockHalf.UPPER ? p_186720_3_.func_177977_b() : p_186720_3_) : -1;
         }
      }, Blocks.field_150398_cm);
      blockcolors.func_186722_a(new IBlockColor() {
         public int func_186720_a(IBlockState p_186720_1_, @Nullable IBlockAccess p_186720_2_, @Nullable BlockPos p_186720_3_, int p_186720_4_) {
            if (p_186720_2_ != null && p_186720_3_ != null) {
               TileEntity tileentity = p_186720_2_.func_175625_s(p_186720_3_);
               if (tileentity instanceof TileEntityFlowerPot) {
                  Item item = ((TileEntityFlowerPot)tileentity).func_145965_a();
                  IBlockState iblockstate = Block.func_149634_a(item).func_176223_P();
                  return blockcolors.func_186724_a(iblockstate, p_186720_2_, p_186720_3_, p_186720_4_);
               } else {
                  return -1;
               }
            } else {
               return -1;
            }
         }
      }, Blocks.field_150457_bL);
      blockcolors.func_186722_a(new IBlockColor() {
         public int func_186720_a(IBlockState p_186720_1_, @Nullable IBlockAccess p_186720_2_, @Nullable BlockPos p_186720_3_, int p_186720_4_) {
            return p_186720_2_ != null && p_186720_3_ != null ? BiomeColorHelper.func_180286_a(p_186720_2_, p_186720_3_) : ColorizerGrass.func_77480_a(0.5D, 1.0D);
         }
      }, Blocks.field_150349_c);
      blockcolors.func_186722_a(new IBlockColor() {
         public int func_186720_a(IBlockState p_186720_1_, @Nullable IBlockAccess p_186720_2_, @Nullable BlockPos p_186720_3_, int p_186720_4_) {
            BlockPlanks.EnumType blockplanks$enumtype = (BlockPlanks.EnumType)p_186720_1_.func_177229_b(BlockOldLeaf.field_176239_P);
            if (blockplanks$enumtype == BlockPlanks.EnumType.SPRUCE) {
               return ColorizerFoliage.func_77466_a();
            } else if (blockplanks$enumtype == BlockPlanks.EnumType.BIRCH) {
               return ColorizerFoliage.func_77469_b();
            } else {
               return p_186720_2_ != null && p_186720_3_ != null ? BiomeColorHelper.func_180287_b(p_186720_2_, p_186720_3_) : ColorizerFoliage.func_77468_c();
            }
         }
      }, Blocks.field_150362_t);
      blockcolors.func_186722_a(new IBlockColor() {
         public int func_186720_a(IBlockState p_186720_1_, @Nullable IBlockAccess p_186720_2_, @Nullable BlockPos p_186720_3_, int p_186720_4_) {
            return p_186720_2_ != null && p_186720_3_ != null ? BiomeColorHelper.func_180287_b(p_186720_2_, p_186720_3_) : ColorizerFoliage.func_77468_c();
         }
      }, Blocks.field_150361_u);
      blockcolors.func_186722_a(new IBlockColor() {
         public int func_186720_a(IBlockState p_186720_1_, @Nullable IBlockAccess p_186720_2_, @Nullable BlockPos p_186720_3_, int p_186720_4_) {
            return p_186720_2_ != null && p_186720_3_ != null ? BiomeColorHelper.func_180288_c(p_186720_2_, p_186720_3_) : -1;
         }
      }, Blocks.field_150355_j, Blocks.field_150358_i);
      blockcolors.func_186722_a(new IBlockColor() {
         public int func_186720_a(IBlockState p_186720_1_, @Nullable IBlockAccess p_186720_2_, @Nullable BlockPos p_186720_3_, int p_186720_4_) {
            return BlockRedstoneWire.func_176337_b(((Integer)p_186720_1_.func_177229_b(BlockRedstoneWire.field_176351_O)).intValue());
         }
      }, Blocks.field_150488_af);
      blockcolors.func_186722_a(new IBlockColor() {
         public int func_186720_a(IBlockState p_186720_1_, @Nullable IBlockAccess p_186720_2_, @Nullable BlockPos p_186720_3_, int p_186720_4_) {
            return p_186720_2_ != null && p_186720_3_ != null ? BiomeColorHelper.func_180286_a(p_186720_2_, p_186720_3_) : -1;
         }
      }, Blocks.field_150436_aH);
      blockcolors.func_186722_a(new IBlockColor() {
         public int func_186720_a(IBlockState p_186720_1_, @Nullable IBlockAccess p_186720_2_, @Nullable BlockPos p_186720_3_, int p_186720_4_) {
            int i = ((Integer)p_186720_1_.func_177229_b(BlockStem.field_176484_a)).intValue();
            int j = i * 32;
            int k = 255 - i * 8;
            int l = i * 4;
            return j << 16 | k << 8 | l;
         }
      }, Blocks.field_150394_bc, Blocks.field_150393_bb);
      blockcolors.func_186722_a(new IBlockColor() {
         public int func_186720_a(IBlockState p_186720_1_, @Nullable IBlockAccess p_186720_2_, @Nullable BlockPos p_186720_3_, int p_186720_4_) {
            if (p_186720_2_ != null && p_186720_3_ != null) {
               return BiomeColorHelper.func_180286_a(p_186720_2_, p_186720_3_);
            } else {
               return p_186720_1_.func_177229_b(BlockTallGrass.field_176497_a) == BlockTallGrass.EnumType.DEAD_BUSH ? 16777215 : ColorizerGrass.func_77480_a(0.5D, 1.0D);
            }
         }
      }, Blocks.field_150329_H);
      blockcolors.func_186722_a(new IBlockColor() {
         public int func_186720_a(IBlockState p_186720_1_, @Nullable IBlockAccess p_186720_2_, @Nullable BlockPos p_186720_3_, int p_186720_4_) {
            return p_186720_2_ != null && p_186720_3_ != null ? BiomeColorHelper.func_180287_b(p_186720_2_, p_186720_3_) : ColorizerFoliage.func_77468_c();
         }
      }, Blocks.field_150395_bd);
      blockcolors.func_186722_a(new IBlockColor() {
         public int func_186720_a(IBlockState p_186720_1_, @Nullable IBlockAccess p_186720_2_, @Nullable BlockPos p_186720_3_, int p_186720_4_) {
            return p_186720_2_ != null && p_186720_3_ != null ? 2129968 : 7455580;
         }
      }, Blocks.field_150392_bi);
      return blockcolors;
   }

   public int func_189991_a(IBlockState p_189991_1_, World p_189991_2_, BlockPos p_189991_3_) {
      IBlockColor iblockcolor = this.field_186725_a.func_148745_a(Block.func_149682_b(p_189991_1_.func_177230_c()));
      if (iblockcolor != null) {
         return iblockcolor.func_186720_a(p_189991_1_, (IBlockAccess)null, (BlockPos)null, 0);
      } else {
         MapColor mapcolor = p_189991_1_.func_185909_g(p_189991_2_, p_189991_3_);
         return mapcolor != null ? mapcolor.field_76291_p : -1;
      }
   }

   public int func_186724_a(IBlockState p_186724_1_, @Nullable IBlockAccess p_186724_2_, @Nullable BlockPos p_186724_3_, int p_186724_4_) {
      IBlockColor iblockcolor = this.field_186725_a.func_148745_a(Block.func_149682_b(p_186724_1_.func_177230_c()));
      return iblockcolor == null ? -1 : iblockcolor.func_186720_a(p_186724_1_, p_186724_2_, p_186724_3_, p_186724_4_);
   }

   public void func_186722_a(IBlockColor p_186722_1_, Block... p_186722_2_) {
      for(Block block : p_186722_2_) {
         this.field_186725_a.func_148746_a(p_186722_1_, Block.func_149682_b(block));
      }

   }
}
