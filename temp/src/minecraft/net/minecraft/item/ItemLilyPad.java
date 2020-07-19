package net.minecraft.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemLilyPad extends ItemColored {
   public ItemLilyPad(Block p_i45357_1_) {
      super(p_i45357_1_, false);
   }

   public ActionResult<ItemStack> func_77659_a(World p_77659_1_, EntityPlayer p_77659_2_, EnumHand p_77659_3_) {
      ItemStack itemstack = p_77659_2_.func_184586_b(p_77659_3_);
      RayTraceResult raytraceresult = this.func_77621_a(p_77659_1_, p_77659_2_, true);
      if (raytraceresult == null) {
         return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
      } else {
         if (raytraceresult.field_72313_a == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos = raytraceresult.func_178782_a();
            if (!p_77659_1_.func_175660_a(p_77659_2_, blockpos) || !p_77659_2_.func_175151_a(blockpos.func_177972_a(raytraceresult.field_178784_b), raytraceresult.field_178784_b, itemstack)) {
               return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
            }

            BlockPos blockpos1 = blockpos.func_177984_a();
            IBlockState iblockstate = p_77659_1_.func_180495_p(blockpos);
            if (iblockstate.func_185904_a() == Material.field_151586_h && ((Integer)iblockstate.func_177229_b(BlockLiquid.field_176367_b)).intValue() == 0 && p_77659_1_.func_175623_d(blockpos1)) {
               p_77659_1_.func_180501_a(blockpos1, Blocks.field_150392_bi.func_176223_P(), 11);
               if (p_77659_2_ instanceof EntityPlayerMP) {
                  CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)p_77659_2_, blockpos1, itemstack);
               }

               if (!p_77659_2_.field_71075_bZ.field_75098_d) {
                  itemstack.func_190918_g(1);
               }

               p_77659_2_.func_71029_a(StatList.func_188057_b(this));
               p_77659_1_.func_184133_a(p_77659_2_, blockpos, SoundEvents.field_187916_gp, SoundCategory.BLOCKS, 1.0F, 1.0F);
               return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
            }
         }

         return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
      }
   }
}
