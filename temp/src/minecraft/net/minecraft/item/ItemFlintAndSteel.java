package net.minecraft.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFlintAndSteel extends Item {
   public ItemFlintAndSteel() {
      this.field_77777_bU = 1;
      this.func_77656_e(64);
      this.func_77637_a(CreativeTabs.field_78040_i);
   }

   public EnumActionResult func_180614_a(EntityPlayer p_180614_1_, World p_180614_2_, BlockPos p_180614_3_, EnumHand p_180614_4_, EnumFacing p_180614_5_, float p_180614_6_, float p_180614_7_, float p_180614_8_) {
      p_180614_3_ = p_180614_3_.func_177972_a(p_180614_5_);
      ItemStack itemstack = p_180614_1_.func_184586_b(p_180614_4_);
      if (!p_180614_1_.func_175151_a(p_180614_3_, p_180614_5_, itemstack)) {
         return EnumActionResult.FAIL;
      } else {
         if (p_180614_2_.func_180495_p(p_180614_3_).func_185904_a() == Material.field_151579_a) {
            p_180614_2_.func_184133_a(p_180614_1_, p_180614_3_, SoundEvents.field_187649_bu, SoundCategory.BLOCKS, 1.0F, field_77697_d.nextFloat() * 0.4F + 0.8F);
            p_180614_2_.func_180501_a(p_180614_3_, Blocks.field_150480_ab.func_176223_P(), 11);
         }

         if (p_180614_1_ instanceof EntityPlayerMP) {
            CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)p_180614_1_, p_180614_3_, itemstack);
         }

         itemstack.func_77972_a(1, p_180614_1_);
         return EnumActionResult.SUCCESS;
      }
   }
}
