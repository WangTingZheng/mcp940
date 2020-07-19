package net.minecraft.block.state;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPistonStructureHelper {
   private final World field_177261_a;
   private final BlockPos field_177259_b;
   private final BlockPos field_177260_c;
   private final EnumFacing field_177257_d;
   private final List<BlockPos> field_177258_e = Lists.<BlockPos>newArrayList();
   private final List<BlockPos> field_177256_f = Lists.<BlockPos>newArrayList();

   public BlockPistonStructureHelper(World p_i45664_1_, BlockPos p_i45664_2_, EnumFacing p_i45664_3_, boolean p_i45664_4_) {
      this.field_177261_a = p_i45664_1_;
      this.field_177259_b = p_i45664_2_;
      if (p_i45664_4_) {
         this.field_177257_d = p_i45664_3_;
         this.field_177260_c = p_i45664_2_.func_177972_a(p_i45664_3_);
      } else {
         this.field_177257_d = p_i45664_3_.func_176734_d();
         this.field_177260_c = p_i45664_2_.func_177967_a(p_i45664_3_, 2);
      }

   }

   public boolean func_177253_a() {
      this.field_177258_e.clear();
      this.field_177256_f.clear();
      IBlockState iblockstate = this.field_177261_a.func_180495_p(this.field_177260_c);
      if (!BlockPistonBase.func_185646_a(iblockstate, this.field_177261_a, this.field_177260_c, this.field_177257_d, false, this.field_177257_d)) {
         if (iblockstate.func_185905_o() == EnumPushReaction.DESTROY) {
            this.field_177256_f.add(this.field_177260_c);
            return true;
         } else {
            return false;
         }
      } else if (!this.func_177251_a(this.field_177260_c, this.field_177257_d)) {
         return false;
      } else {
         for(int i = 0; i < this.field_177258_e.size(); ++i) {
            BlockPos blockpos = this.field_177258_e.get(i);
            if (this.field_177261_a.func_180495_p(blockpos).func_177230_c() == Blocks.field_180399_cE && !this.func_177250_b(blockpos)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean func_177251_a(BlockPos p_177251_1_, EnumFacing p_177251_2_) {
      IBlockState iblockstate = this.field_177261_a.func_180495_p(p_177251_1_);
      Block block = iblockstate.func_177230_c();
      if (iblockstate.func_185904_a() == Material.field_151579_a) {
         return true;
      } else if (!BlockPistonBase.func_185646_a(iblockstate, this.field_177261_a, p_177251_1_, this.field_177257_d, false, p_177251_2_)) {
         return true;
      } else if (p_177251_1_.equals(this.field_177259_b)) {
         return true;
      } else if (this.field_177258_e.contains(p_177251_1_)) {
         return true;
      } else {
         int i = 1;
         if (i + this.field_177258_e.size() > 12) {
            return false;
         } else {
            while(block == Blocks.field_180399_cE) {
               BlockPos blockpos = p_177251_1_.func_177967_a(this.field_177257_d.func_176734_d(), i);
               iblockstate = this.field_177261_a.func_180495_p(blockpos);
               block = iblockstate.func_177230_c();
               if (iblockstate.func_185904_a() == Material.field_151579_a || !BlockPistonBase.func_185646_a(iblockstate, this.field_177261_a, blockpos, this.field_177257_d, false, this.field_177257_d.func_176734_d()) || blockpos.equals(this.field_177259_b)) {
                  break;
               }

               ++i;
               if (i + this.field_177258_e.size() > 12) {
                  return false;
               }
            }

            int i1 = 0;

            for(int j = i - 1; j >= 0; --j) {
               this.field_177258_e.add(p_177251_1_.func_177967_a(this.field_177257_d.func_176734_d(), j));
               ++i1;
            }

            int j1 = 1;

            while(true) {
               BlockPos blockpos1 = p_177251_1_.func_177967_a(this.field_177257_d, j1);
               int k = this.field_177258_e.indexOf(blockpos1);
               if (k > -1) {
                  this.func_177255_a(i1, k);

                  for(int l = 0; l <= k + i1; ++l) {
                     BlockPos blockpos2 = this.field_177258_e.get(l);
                     if (this.field_177261_a.func_180495_p(blockpos2).func_177230_c() == Blocks.field_180399_cE && !this.func_177250_b(blockpos2)) {
                        return false;
                     }
                  }

                  return true;
               }

               iblockstate = this.field_177261_a.func_180495_p(blockpos1);
               if (iblockstate.func_185904_a() == Material.field_151579_a) {
                  return true;
               }

               if (!BlockPistonBase.func_185646_a(iblockstate, this.field_177261_a, blockpos1, this.field_177257_d, true, this.field_177257_d) || blockpos1.equals(this.field_177259_b)) {
                  return false;
               }

               if (iblockstate.func_185905_o() == EnumPushReaction.DESTROY) {
                  this.field_177256_f.add(blockpos1);
                  return true;
               }

               if (this.field_177258_e.size() >= 12) {
                  return false;
               }

               this.field_177258_e.add(blockpos1);
               ++i1;
               ++j1;
            }
         }
      }
   }

   private void func_177255_a(int p_177255_1_, int p_177255_2_) {
      List<BlockPos> list = Lists.<BlockPos>newArrayList();
      List<BlockPos> list1 = Lists.<BlockPos>newArrayList();
      List<BlockPos> list2 = Lists.<BlockPos>newArrayList();
      list.addAll(this.field_177258_e.subList(0, p_177255_2_));
      list1.addAll(this.field_177258_e.subList(this.field_177258_e.size() - p_177255_1_, this.field_177258_e.size()));
      list2.addAll(this.field_177258_e.subList(p_177255_2_, this.field_177258_e.size() - p_177255_1_));
      this.field_177258_e.clear();
      this.field_177258_e.addAll(list);
      this.field_177258_e.addAll(list1);
      this.field_177258_e.addAll(list2);
   }

   private boolean func_177250_b(BlockPos p_177250_1_) {
      for(EnumFacing enumfacing : EnumFacing.values()) {
         if (enumfacing.func_176740_k() != this.field_177257_d.func_176740_k() && !this.func_177251_a(p_177250_1_.func_177972_a(enumfacing), enumfacing)) {
            return false;
         }
      }

      return true;
   }

   public List<BlockPos> func_177254_c() {
      return this.field_177258_e;
   }

   public List<BlockPos> func_177252_d() {
      return this.field_177256_f;
   }
}
