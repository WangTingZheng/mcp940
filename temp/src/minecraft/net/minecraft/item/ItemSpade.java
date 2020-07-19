package net.minecraft.item;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSpade extends ItemTool {
   private static final Set<Block> field_150916_c = Sets.newHashSet(Blocks.field_150435_aG, Blocks.field_150346_d, Blocks.field_150458_ak, Blocks.field_150349_c, Blocks.field_150351_n, Blocks.field_150391_bh, Blocks.field_150354_m, Blocks.field_150433_aE, Blocks.field_150431_aC, Blocks.field_150425_aM, Blocks.field_185774_da, Blocks.field_192444_dS);

   public ItemSpade(Item.ToolMaterial p_i45353_1_) {
      super(1.5F, -3.0F, p_i45353_1_, field_150916_c);
   }

   public boolean func_150897_b(IBlockState p_150897_1_) {
      Block block = p_150897_1_.func_177230_c();
      if (block == Blocks.field_150431_aC) {
         return true;
      } else {
         return block == Blocks.field_150433_aE;
      }
   }

   public EnumActionResult func_180614_a(EntityPlayer p_180614_1_, World p_180614_2_, BlockPos p_180614_3_, EnumHand p_180614_4_, EnumFacing p_180614_5_, float p_180614_6_, float p_180614_7_, float p_180614_8_) {
      ItemStack itemstack = p_180614_1_.func_184586_b(p_180614_4_);
      if (!p_180614_1_.func_175151_a(p_180614_3_.func_177972_a(p_180614_5_), p_180614_5_, itemstack)) {
         return EnumActionResult.FAIL;
      } else {
         IBlockState iblockstate = p_180614_2_.func_180495_p(p_180614_3_);
         Block block = iblockstate.func_177230_c();
         if (p_180614_5_ != EnumFacing.DOWN && p_180614_2_.func_180495_p(p_180614_3_.func_177984_a()).func_185904_a() == Material.field_151579_a && block == Blocks.field_150349_c) {
            IBlockState iblockstate1 = Blocks.field_185774_da.func_176223_P();
            p_180614_2_.func_184133_a(p_180614_1_, p_180614_3_, SoundEvents.field_187771_eN, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!p_180614_2_.field_72995_K) {
               p_180614_2_.func_180501_a(p_180614_3_, iblockstate1, 11);
               itemstack.func_77972_a(1, p_180614_1_);
            }

            return EnumActionResult.SUCCESS;
         } else {
            return EnumActionResult.PASS;
         }
      }
   }
}
