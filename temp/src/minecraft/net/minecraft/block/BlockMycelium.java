package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMycelium extends Block {
   public static final PropertyBool field_176384_a = PropertyBool.func_177716_a("snowy");

   protected BlockMycelium() {
      super(Material.field_151577_b, MapColor.field_151678_z);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176384_a, Boolean.valueOf(false)));
      this.func_149675_a(true);
      this.func_149647_a(CreativeTabs.field_78030_b);
   }

   public IBlockState func_176221_a(IBlockState p_176221_1_, IBlockAccess p_176221_2_, BlockPos p_176221_3_) {
      Block block = p_176221_2_.func_180495_p(p_176221_3_.func_177984_a()).func_177230_c();
      return p_176221_1_.func_177226_a(field_176384_a, Boolean.valueOf(block == Blocks.field_150433_aE || block == Blocks.field_150431_aC));
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      if (!p_180650_1_.field_72995_K) {
         if (p_180650_1_.func_175671_l(p_180650_2_.func_177984_a()) < 4 && p_180650_1_.func_180495_p(p_180650_2_.func_177984_a()).func_185891_c() > 2) {
            p_180650_1_.func_175656_a(p_180650_2_, Blocks.field_150346_d.func_176223_P().func_177226_a(BlockDirt.field_176386_a, BlockDirt.DirtType.DIRT));
         } else {
            if (p_180650_1_.func_175671_l(p_180650_2_.func_177984_a()) >= 9) {
               for(int i = 0; i < 4; ++i) {
                  BlockPos blockpos = p_180650_2_.func_177982_a(p_180650_4_.nextInt(3) - 1, p_180650_4_.nextInt(5) - 3, p_180650_4_.nextInt(3) - 1);
                  IBlockState iblockstate = p_180650_1_.func_180495_p(blockpos);
                  IBlockState iblockstate1 = p_180650_1_.func_180495_p(blockpos.func_177984_a());
                  if (iblockstate.func_177230_c() == Blocks.field_150346_d && iblockstate.func_177229_b(BlockDirt.field_176386_a) == BlockDirt.DirtType.DIRT && p_180650_1_.func_175671_l(blockpos.func_177984_a()) >= 4 && iblockstate1.func_185891_c() <= 2) {
                     p_180650_1_.func_175656_a(blockpos, this.func_176223_P());
                  }
               }
            }

         }
      }
   }

   public void func_180655_c(IBlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
      super.func_180655_c(p_180655_1_, p_180655_2_, p_180655_3_, p_180655_4_);
      if (p_180655_4_.nextInt(10) == 0) {
         p_180655_2_.func_175688_a(EnumParticleTypes.TOWN_AURA, (double)((float)p_180655_3_.func_177958_n() + p_180655_4_.nextFloat()), (double)((float)p_180655_3_.func_177956_o() + 1.1F), (double)((float)p_180655_3_.func_177952_p() + p_180655_4_.nextFloat()), 0.0D, 0.0D, 0.0D);
      }

   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Blocks.field_150346_d.func_180660_a(Blocks.field_150346_d.func_176223_P().func_177226_a(BlockDirt.field_176386_a, BlockDirt.DirtType.DIRT), p_180660_2_, p_180660_3_);
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return 0;
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176384_a});
   }
}
