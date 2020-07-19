package net.minecraft.item;

import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.end.DragonFightManager;

public class ItemEndCrystal extends Item {
   public ItemEndCrystal() {
      this.func_77655_b("end_crystal");
      this.func_77637_a(CreativeTabs.field_78031_c);
   }

   public EnumActionResult func_180614_a(EntityPlayer p_180614_1_, World p_180614_2_, BlockPos p_180614_3_, EnumHand p_180614_4_, EnumFacing p_180614_5_, float p_180614_6_, float p_180614_7_, float p_180614_8_) {
      IBlockState iblockstate = p_180614_2_.func_180495_p(p_180614_3_);
      if (iblockstate.func_177230_c() != Blocks.field_150343_Z && iblockstate.func_177230_c() != Blocks.field_150357_h) {
         return EnumActionResult.FAIL;
      } else {
         BlockPos blockpos = p_180614_3_.func_177984_a();
         ItemStack itemstack = p_180614_1_.func_184586_b(p_180614_4_);
         if (!p_180614_1_.func_175151_a(blockpos, p_180614_5_, itemstack)) {
            return EnumActionResult.FAIL;
         } else {
            BlockPos blockpos1 = blockpos.func_177984_a();
            boolean flag = !p_180614_2_.func_175623_d(blockpos) && !p_180614_2_.func_180495_p(blockpos).func_177230_c().func_176200_f(p_180614_2_, blockpos);
            flag = flag | (!p_180614_2_.func_175623_d(blockpos1) && !p_180614_2_.func_180495_p(blockpos1).func_177230_c().func_176200_f(p_180614_2_, blockpos1));
            if (flag) {
               return EnumActionResult.FAIL;
            } else {
               double d0 = (double)blockpos.func_177958_n();
               double d1 = (double)blockpos.func_177956_o();
               double d2 = (double)blockpos.func_177952_p();
               List<Entity> list = p_180614_2_.func_72839_b((Entity)null, new AxisAlignedBB(d0, d1, d2, d0 + 1.0D, d1 + 2.0D, d2 + 1.0D));
               if (!list.isEmpty()) {
                  return EnumActionResult.FAIL;
               } else {
                  if (!p_180614_2_.field_72995_K) {
                     EntityEnderCrystal entityendercrystal = new EntityEnderCrystal(p_180614_2_, (double)((float)p_180614_3_.func_177958_n() + 0.5F), (double)(p_180614_3_.func_177956_o() + 1), (double)((float)p_180614_3_.func_177952_p() + 0.5F));
                     entityendercrystal.func_184517_a(false);
                     p_180614_2_.func_72838_d(entityendercrystal);
                     if (p_180614_2_.field_73011_w instanceof WorldProviderEnd) {
                        DragonFightManager dragonfightmanager = ((WorldProviderEnd)p_180614_2_.field_73011_w).func_186063_s();
                        dragonfightmanager.func_186106_e();
                     }
                  }

                  itemstack.func_190918_g(1);
                  return EnumActionResult.SUCCESS;
               }
            }
         }
      }
   }

   public boolean func_77636_d(ItemStack p_77636_1_) {
      return true;
   }
}
