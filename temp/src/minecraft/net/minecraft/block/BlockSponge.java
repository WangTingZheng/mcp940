package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class BlockSponge extends Block {
   public static final PropertyBool field_176313_a = PropertyBool.func_177716_a("wet");

   protected BlockSponge() {
      super(Material.field_151583_m);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176313_a, Boolean.valueOf(false)));
      this.func_149647_a(CreativeTabs.field_78030_b);
   }

   public String func_149732_F() {
      return I18n.func_74838_a(this.func_149739_a() + ".dry.name");
   }

   public int func_180651_a(IBlockState p_180651_1_) {
      return ((Boolean)p_180651_1_.func_177229_b(field_176313_a)).booleanValue() ? 1 : 0;
   }

   public void func_176213_c(World p_176213_1_, BlockPos p_176213_2_, IBlockState p_176213_3_) {
      this.func_176311_e(p_176213_1_, p_176213_2_, p_176213_3_);
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      this.func_176311_e(p_189540_2_, p_189540_3_, p_189540_1_);
      super.func_189540_a(p_189540_1_, p_189540_2_, p_189540_3_, p_189540_4_, p_189540_5_);
   }

   protected void func_176311_e(World p_176311_1_, BlockPos p_176311_2_, IBlockState p_176311_3_) {
      if (!((Boolean)p_176311_3_.func_177229_b(field_176313_a)).booleanValue() && this.func_176312_d(p_176311_1_, p_176311_2_)) {
         p_176311_1_.func_180501_a(p_176311_2_, p_176311_3_.func_177226_a(field_176313_a, Boolean.valueOf(true)), 2);
         p_176311_1_.func_175718_b(2001, p_176311_2_, Block.func_149682_b(Blocks.field_150355_j));
      }

   }

   private boolean func_176312_d(World p_176312_1_, BlockPos p_176312_2_) {
      Queue<Tuple<BlockPos, Integer>> queue = Lists.<Tuple<BlockPos, Integer>>newLinkedList();
      List<BlockPos> list = Lists.<BlockPos>newArrayList();
      queue.add(new Tuple(p_176312_2_, Integer.valueOf(0)));
      int i = 0;

      while(!queue.isEmpty()) {
         Tuple<BlockPos, Integer> tuple = (Tuple)queue.poll();
         BlockPos blockpos = tuple.func_76341_a();
         int j = ((Integer)tuple.func_76340_b()).intValue();

         for(EnumFacing enumfacing : EnumFacing.values()) {
            BlockPos blockpos1 = blockpos.func_177972_a(enumfacing);
            if (p_176312_1_.func_180495_p(blockpos1).func_185904_a() == Material.field_151586_h) {
               p_176312_1_.func_180501_a(blockpos1, Blocks.field_150350_a.func_176223_P(), 2);
               list.add(blockpos1);
               ++i;
               if (j < 6) {
                  queue.add(new Tuple(blockpos1, j + 1));
               }
            }
         }

         if (i > 64) {
            break;
         }
      }

      for(BlockPos blockpos2 : list) {
         p_176312_1_.func_175685_c(blockpos2, Blocks.field_150350_a, false);
      }

      return i > 0;
   }

   public void func_149666_a(CreativeTabs p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
      p_149666_2_.add(new ItemStack(this, 1, 0));
      p_149666_2_.add(new ItemStack(this, 1, 1));
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176313_a, Boolean.valueOf((p_176203_1_ & 1) == 1));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((Boolean)p_176201_1_.func_177229_b(field_176313_a)).booleanValue() ? 1 : 0;
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176313_a});
   }

   public void func_180655_c(IBlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
      if (((Boolean)p_180655_1_.func_177229_b(field_176313_a)).booleanValue()) {
         EnumFacing enumfacing = EnumFacing.func_176741_a(p_180655_4_);
         if (enumfacing != EnumFacing.UP && !p_180655_2_.func_180495_p(p_180655_3_.func_177972_a(enumfacing)).func_185896_q()) {
            double d0 = (double)p_180655_3_.func_177958_n();
            double d1 = (double)p_180655_3_.func_177956_o();
            double d2 = (double)p_180655_3_.func_177952_p();
            if (enumfacing == EnumFacing.DOWN) {
               d1 = d1 - 0.05D;
               d0 += p_180655_4_.nextDouble();
               d2 += p_180655_4_.nextDouble();
            } else {
               d1 = d1 + p_180655_4_.nextDouble() * 0.8D;
               if (enumfacing.func_176740_k() == EnumFacing.Axis.X) {
                  d2 += p_180655_4_.nextDouble();
                  if (enumfacing == EnumFacing.EAST) {
                     ++d0;
                  } else {
                     d0 += 0.05D;
                  }
               } else {
                  d0 += p_180655_4_.nextDouble();
                  if (enumfacing == EnumFacing.SOUTH) {
                     ++d2;
                  } else {
                     d2 += 0.05D;
                  }
               }
            }

            p_180655_2_.func_175688_a(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
         }
      }
   }
}
