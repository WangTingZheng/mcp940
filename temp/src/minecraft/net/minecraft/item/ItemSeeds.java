package net.minecraft.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSeeds extends Item {
   private final Block field_150925_a;
   private final Block field_77838_b;

   public ItemSeeds(Block p_i45352_1_, Block p_i45352_2_) {
      this.field_150925_a = p_i45352_1_;
      this.field_77838_b = p_i45352_2_;
      this.func_77637_a(CreativeTabs.field_78035_l);
   }

   public EnumActionResult func_180614_a(EntityPlayer p_180614_1_, World p_180614_2_, BlockPos p_180614_3_, EnumHand p_180614_4_, EnumFacing p_180614_5_, float p_180614_6_, float p_180614_7_, float p_180614_8_) {
      ItemStack itemstack = p_180614_1_.func_184586_b(p_180614_4_);
      if (p_180614_5_ == EnumFacing.UP && p_180614_1_.func_175151_a(p_180614_3_.func_177972_a(p_180614_5_), p_180614_5_, itemstack) && p_180614_2_.func_180495_p(p_180614_3_).func_177230_c() == this.field_77838_b && p_180614_2_.func_175623_d(p_180614_3_.func_177984_a())) {
         p_180614_2_.func_175656_a(p_180614_3_.func_177984_a(), this.field_150925_a.func_176223_P());
         if (p_180614_1_ instanceof EntityPlayerMP) {
            CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)p_180614_1_, p_180614_3_.func_177984_a(), itemstack);
         }

         itemstack.func_190918_g(1);
         return EnumActionResult.SUCCESS;
      } else {
         return EnumActionResult.FAIL;
      }
   }
}
