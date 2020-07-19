package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class WorldGeneratorBonusChest extends WorldGenerator {
   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      for(IBlockState iblockstate = p_180709_1_.func_180495_p(p_180709_3_); (iblockstate.func_185904_a() == Material.field_151579_a || iblockstate.func_185904_a() == Material.field_151584_j) && p_180709_3_.func_177956_o() > 1; iblockstate = p_180709_1_.func_180495_p(p_180709_3_)) {
         p_180709_3_ = p_180709_3_.func_177977_b();
      }

      if (p_180709_3_.func_177956_o() < 1) {
         return false;
      } else {
         p_180709_3_ = p_180709_3_.func_177984_a();

         for(int i = 0; i < 4; ++i) {
            BlockPos blockpos = p_180709_3_.func_177982_a(p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(3) - p_180709_2_.nextInt(3), p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4));
            if (p_180709_1_.func_175623_d(blockpos) && p_180709_1_.func_180495_p(blockpos.func_177977_b()).func_185896_q()) {
               p_180709_1_.func_180501_a(blockpos, Blocks.field_150486_ae.func_176223_P(), 2);
               TileEntity tileentity = p_180709_1_.func_175625_s(blockpos);
               if (tileentity instanceof TileEntityChest) {
                  ((TileEntityChest)tileentity).func_189404_a(LootTableList.field_186420_b, p_180709_2_.nextLong());
               }

               BlockPos blockpos1 = blockpos.func_177974_f();
               BlockPos blockpos2 = blockpos.func_177976_e();
               BlockPos blockpos3 = blockpos.func_177978_c();
               BlockPos blockpos4 = blockpos.func_177968_d();
               if (p_180709_1_.func_175623_d(blockpos2) && p_180709_1_.func_180495_p(blockpos2.func_177977_b()).func_185896_q()) {
                  p_180709_1_.func_180501_a(blockpos2, Blocks.field_150478_aa.func_176223_P(), 2);
               }

               if (p_180709_1_.func_175623_d(blockpos1) && p_180709_1_.func_180495_p(blockpos1.func_177977_b()).func_185896_q()) {
                  p_180709_1_.func_180501_a(blockpos1, Blocks.field_150478_aa.func_176223_P(), 2);
               }

               if (p_180709_1_.func_175623_d(blockpos3) && p_180709_1_.func_180495_p(blockpos3.func_177977_b()).func_185896_q()) {
                  p_180709_1_.func_180501_a(blockpos3, Blocks.field_150478_aa.func_176223_P(), 2);
               }

               if (p_180709_1_.func_175623_d(blockpos4) && p_180709_1_.func_180495_p(blockpos4.func_177977_b()).func_185896_q()) {
                  p_180709_1_.func_180501_a(blockpos4, Blocks.field_150478_aa.func_176223_P(), 2);
               }

               return true;
            }
         }

         return false;
      }
   }
}
