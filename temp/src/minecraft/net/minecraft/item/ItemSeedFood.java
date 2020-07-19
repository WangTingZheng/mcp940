package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSeedFood extends ItemFood {
   private final Block field_150908_b;
   private final Block field_82809_c;

   public ItemSeedFood(int p_i45351_1_, float p_i45351_2_, Block p_i45351_3_, Block p_i45351_4_) {
      super(p_i45351_1_, p_i45351_2_, false);
      this.field_150908_b = p_i45351_3_;
      this.field_82809_c = p_i45351_4_;
   }

   public EnumActionResult func_180614_a(EntityPlayer p_180614_1_, World p_180614_2_, BlockPos p_180614_3_, EnumHand p_180614_4_, EnumFacing p_180614_5_, float p_180614_6_, float p_180614_7_, float p_180614_8_) {
      ItemStack itemstack = p_180614_1_.func_184586_b(p_180614_4_);
      if (p_180614_5_ == EnumFacing.UP && p_180614_1_.func_175151_a(p_180614_3_.func_177972_a(p_180614_5_), p_180614_5_, itemstack) && p_180614_2_.func_180495_p(p_180614_3_).func_177230_c() == this.field_82809_c && p_180614_2_.func_175623_d(p_180614_3_.func_177984_a())) {
         p_180614_2_.func_180501_a(p_180614_3_.func_177984_a(), this.field_150908_b.func_176223_P(), 11);
         itemstack.func_190918_g(1);
         return EnumActionResult.SUCCESS;
      } else {
         return EnumActionResult.FAIL;
      }
   }
}
