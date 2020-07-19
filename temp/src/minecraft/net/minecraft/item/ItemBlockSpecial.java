package net.minecraft.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBlockSpecial extends Item {
   private final Block field_150935_a;

   public ItemBlockSpecial(Block p_i45329_1_) {
      this.field_150935_a = p_i45329_1_;
   }

   public EnumActionResult func_180614_a(EntityPlayer p_180614_1_, World p_180614_2_, BlockPos p_180614_3_, EnumHand p_180614_4_, EnumFacing p_180614_5_, float p_180614_6_, float p_180614_7_, float p_180614_8_) {
      IBlockState iblockstate = p_180614_2_.func_180495_p(p_180614_3_);
      Block block = iblockstate.func_177230_c();
      if (block == Blocks.field_150431_aC && ((Integer)iblockstate.func_177229_b(BlockSnow.field_176315_a)).intValue() < 1) {
         p_180614_5_ = EnumFacing.UP;
      } else if (!block.func_176200_f(p_180614_2_, p_180614_3_)) {
         p_180614_3_ = p_180614_3_.func_177972_a(p_180614_5_);
      }

      ItemStack itemstack = p_180614_1_.func_184586_b(p_180614_4_);
      if (!itemstack.func_190926_b() && p_180614_1_.func_175151_a(p_180614_3_, p_180614_5_, itemstack) && p_180614_2_.func_190527_a(this.field_150935_a, p_180614_3_, false, p_180614_5_, (Entity)null)) {
         IBlockState iblockstate1 = this.field_150935_a.func_180642_a(p_180614_2_, p_180614_3_, p_180614_5_, p_180614_6_, p_180614_7_, p_180614_8_, 0, p_180614_1_);
         if (!p_180614_2_.func_180501_a(p_180614_3_, iblockstate1, 11)) {
            return EnumActionResult.FAIL;
         } else {
            iblockstate1 = p_180614_2_.func_180495_p(p_180614_3_);
            if (iblockstate1.func_177230_c() == this.field_150935_a) {
               ItemBlock.func_179224_a(p_180614_2_, p_180614_1_, p_180614_3_, itemstack);
               iblockstate1.func_177230_c().func_180633_a(p_180614_2_, p_180614_3_, iblockstate1, p_180614_1_, itemstack);
               if (p_180614_1_ instanceof EntityPlayerMP) {
                  CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)p_180614_1_, p_180614_3_, itemstack);
               }
            }

            SoundType soundtype = this.field_150935_a.func_185467_w();
            p_180614_2_.func_184133_a(p_180614_1_, p_180614_3_, soundtype.func_185841_e(), SoundCategory.BLOCKS, (soundtype.func_185843_a() + 1.0F) / 2.0F, soundtype.func_185847_b() * 0.8F);
            itemstack.func_190918_g(1);
            return EnumActionResult.SUCCESS;
         }
      } else {
         return EnumActionResult.FAIL;
      }
   }
}
