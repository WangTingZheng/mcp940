package net.minecraft.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemRedstone extends Item {
   public ItemRedstone() {
      this.func_77637_a(CreativeTabs.field_78028_d);
   }

   public EnumActionResult func_180614_a(EntityPlayer p_180614_1_, World p_180614_2_, BlockPos p_180614_3_, EnumHand p_180614_4_, EnumFacing p_180614_5_, float p_180614_6_, float p_180614_7_, float p_180614_8_) {
      boolean flag = p_180614_2_.func_180495_p(p_180614_3_).func_177230_c().func_176200_f(p_180614_2_, p_180614_3_);
      BlockPos blockpos = flag ? p_180614_3_ : p_180614_3_.func_177972_a(p_180614_5_);
      ItemStack itemstack = p_180614_1_.func_184586_b(p_180614_4_);
      if (p_180614_1_.func_175151_a(blockpos, p_180614_5_, itemstack) && p_180614_2_.func_190527_a(p_180614_2_.func_180495_p(blockpos).func_177230_c(), blockpos, false, p_180614_5_, (Entity)null) && Blocks.field_150488_af.func_176196_c(p_180614_2_, blockpos)) {
         p_180614_2_.func_175656_a(blockpos, Blocks.field_150488_af.func_176223_P());
         if (p_180614_1_ instanceof EntityPlayerMP) {
            CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)p_180614_1_, blockpos, itemstack);
         }

         itemstack.func_190918_g(1);
         return EnumActionResult.SUCCESS;
      } else {
         return EnumActionResult.FAIL;
      }
   }
}
