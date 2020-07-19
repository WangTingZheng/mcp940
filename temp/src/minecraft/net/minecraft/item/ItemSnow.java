package net.minecraft.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSnow extends ItemBlock {
   public ItemSnow(Block p_i45781_1_) {
      super(p_i45781_1_);
      this.func_77656_e(0);
   }

   public EnumActionResult func_180614_a(EntityPlayer p_180614_1_, World p_180614_2_, BlockPos p_180614_3_, EnumHand p_180614_4_, EnumFacing p_180614_5_, float p_180614_6_, float p_180614_7_, float p_180614_8_) {
      ItemStack itemstack = p_180614_1_.func_184586_b(p_180614_4_);
      if (!itemstack.func_190926_b() && p_180614_1_.func_175151_a(p_180614_3_, p_180614_5_, itemstack)) {
         IBlockState iblockstate = p_180614_2_.func_180495_p(p_180614_3_);
         Block block = iblockstate.func_177230_c();
         BlockPos blockpos = p_180614_3_;
         if ((p_180614_5_ != EnumFacing.UP || block != this.field_150939_a) && !block.func_176200_f(p_180614_2_, p_180614_3_)) {
            blockpos = p_180614_3_.func_177972_a(p_180614_5_);
            iblockstate = p_180614_2_.func_180495_p(blockpos);
            block = iblockstate.func_177230_c();
         }

         if (block == this.field_150939_a) {
            int i = ((Integer)iblockstate.func_177229_b(BlockSnow.field_176315_a)).intValue();
            if (i < 8) {
               IBlockState iblockstate1 = iblockstate.func_177226_a(BlockSnow.field_176315_a, Integer.valueOf(i + 1));
               AxisAlignedBB axisalignedbb = iblockstate1.func_185890_d(p_180614_2_, blockpos);
               if (axisalignedbb != Block.field_185506_k && p_180614_2_.func_72855_b(axisalignedbb.func_186670_a(blockpos)) && p_180614_2_.func_180501_a(blockpos, iblockstate1, 10)) {
                  SoundType soundtype = this.field_150939_a.func_185467_w();
                  p_180614_2_.func_184133_a(p_180614_1_, blockpos, soundtype.func_185841_e(), SoundCategory.BLOCKS, (soundtype.func_185843_a() + 1.0F) / 2.0F, soundtype.func_185847_b() * 0.8F);
                  if (p_180614_1_ instanceof EntityPlayerMP) {
                     CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)p_180614_1_, p_180614_3_, itemstack);
                  }

                  itemstack.func_190918_g(1);
                  return EnumActionResult.SUCCESS;
               }
            }
         }

         return super.func_180614_a(p_180614_1_, p_180614_2_, p_180614_3_, p_180614_4_, p_180614_5_, p_180614_6_, p_180614_7_, p_180614_8_);
      } else {
         return EnumActionResult.FAIL;
      }
   }

   public int func_77647_b(int p_77647_1_) {
      return p_77647_1_;
   }
}
