package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemLead extends Item {
   public ItemLead() {
      this.func_77637_a(CreativeTabs.field_78040_i);
   }

   public EnumActionResult func_180614_a(EntityPlayer p_180614_1_, World p_180614_2_, BlockPos p_180614_3_, EnumHand p_180614_4_, EnumFacing p_180614_5_, float p_180614_6_, float p_180614_7_, float p_180614_8_) {
      Block block = p_180614_2_.func_180495_p(p_180614_3_).func_177230_c();
      if (!(block instanceof BlockFence)) {
         return EnumActionResult.PASS;
      } else {
         if (!p_180614_2_.field_72995_K) {
            func_180618_a(p_180614_1_, p_180614_2_, p_180614_3_);
         }

         return EnumActionResult.SUCCESS;
      }
   }

   public static boolean func_180618_a(EntityPlayer p_180618_0_, World p_180618_1_, BlockPos p_180618_2_) {
      EntityLeashKnot entityleashknot = EntityLeashKnot.func_174863_b(p_180618_1_, p_180618_2_);
      boolean flag = false;
      double d0 = 7.0D;
      int i = p_180618_2_.func_177958_n();
      int j = p_180618_2_.func_177956_o();
      int k = p_180618_2_.func_177952_p();

      for(EntityLiving entityliving : p_180618_1_.func_72872_a(EntityLiving.class, new AxisAlignedBB((double)i - 7.0D, (double)j - 7.0D, (double)k - 7.0D, (double)i + 7.0D, (double)j + 7.0D, (double)k + 7.0D))) {
         if (entityliving.func_110167_bD() && entityliving.func_110166_bE() == p_180618_0_) {
            if (entityleashknot == null) {
               entityleashknot = EntityLeashKnot.func_174862_a(p_180618_1_, p_180618_2_);
            }

            entityliving.func_110162_b(entityleashknot, true);
            flag = true;
         }
      }

      return flag;
   }
}
