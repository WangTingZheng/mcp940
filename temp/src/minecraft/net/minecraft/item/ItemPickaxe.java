package net.minecraft.item;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class ItemPickaxe extends ItemTool {
   private static final Set<Block> field_150915_c = Sets.newHashSet(Blocks.field_150408_cc, Blocks.field_150365_q, Blocks.field_150347_e, Blocks.field_150319_E, Blocks.field_150484_ah, Blocks.field_150482_ag, Blocks.field_150334_T, Blocks.field_150318_D, Blocks.field_150340_R, Blocks.field_150352_o, Blocks.field_150432_aD, Blocks.field_150339_S, Blocks.field_150366_p, Blocks.field_150368_y, Blocks.field_150369_x, Blocks.field_150439_ay, Blocks.field_150341_Y, Blocks.field_150424_aL, Blocks.field_150403_cj, Blocks.field_150448_aq, Blocks.field_150450_ax, Blocks.field_150322_A, Blocks.field_180395_cM, Blocks.field_150348_b, Blocks.field_150333_U, Blocks.field_150430_aB, Blocks.field_150456_au);

   protected ItemPickaxe(Item.ToolMaterial p_i45347_1_) {
      super(1.0F, -2.8F, p_i45347_1_, field_150915_c);
   }

   public boolean func_150897_b(IBlockState p_150897_1_) {
      Block block = p_150897_1_.func_177230_c();
      if (block == Blocks.field_150343_Z) {
         return this.field_77862_b.func_77996_d() == 3;
      } else if (block != Blocks.field_150484_ah && block != Blocks.field_150482_ag) {
         if (block != Blocks.field_150412_bA && block != Blocks.field_150475_bE) {
            if (block != Blocks.field_150340_R && block != Blocks.field_150352_o) {
               if (block != Blocks.field_150339_S && block != Blocks.field_150366_p) {
                  if (block != Blocks.field_150368_y && block != Blocks.field_150369_x) {
                     if (block != Blocks.field_150450_ax && block != Blocks.field_150439_ay) {
                        Material material = p_150897_1_.func_185904_a();
                        if (material == Material.field_151576_e) {
                           return true;
                        } else if (material == Material.field_151573_f) {
                           return true;
                        } else {
                           return material == Material.field_151574_g;
                        }
                     } else {
                        return this.field_77862_b.func_77996_d() >= 2;
                     }
                  } else {
                     return this.field_77862_b.func_77996_d() >= 1;
                  }
               } else {
                  return this.field_77862_b.func_77996_d() >= 1;
               }
            } else {
               return this.field_77862_b.func_77996_d() >= 2;
            }
         } else {
            return this.field_77862_b.func_77996_d() >= 2;
         }
      } else {
         return this.field_77862_b.func_77996_d() >= 2;
      }
   }

   public float func_150893_a(ItemStack p_150893_1_, IBlockState p_150893_2_) {
      Material material = p_150893_2_.func_185904_a();
      return material != Material.field_151573_f && material != Material.field_151574_g && material != Material.field_151576_e ? super.func_150893_a(p_150893_1_, p_150893_2_) : this.field_77864_a;
   }
}
