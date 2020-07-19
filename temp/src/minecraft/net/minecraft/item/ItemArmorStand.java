package net.minecraft.item;

import java.util.List;
import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Rotations;
import net.minecraft.world.World;

public class ItemArmorStand extends Item {
   public ItemArmorStand() {
      this.func_77637_a(CreativeTabs.field_78031_c);
   }

   public EnumActionResult func_180614_a(EntityPlayer p_180614_1_, World p_180614_2_, BlockPos p_180614_3_, EnumHand p_180614_4_, EnumFacing p_180614_5_, float p_180614_6_, float p_180614_7_, float p_180614_8_) {
      if (p_180614_5_ == EnumFacing.DOWN) {
         return EnumActionResult.FAIL;
      } else {
         boolean flag = p_180614_2_.func_180495_p(p_180614_3_).func_177230_c().func_176200_f(p_180614_2_, p_180614_3_);
         BlockPos blockpos = flag ? p_180614_3_ : p_180614_3_.func_177972_a(p_180614_5_);
         ItemStack itemstack = p_180614_1_.func_184586_b(p_180614_4_);
         if (!p_180614_1_.func_175151_a(blockpos, p_180614_5_, itemstack)) {
            return EnumActionResult.FAIL;
         } else {
            BlockPos blockpos1 = blockpos.func_177984_a();
            boolean flag1 = !p_180614_2_.func_175623_d(blockpos) && !p_180614_2_.func_180495_p(blockpos).func_177230_c().func_176200_f(p_180614_2_, blockpos);
            flag1 = flag1 | (!p_180614_2_.func_175623_d(blockpos1) && !p_180614_2_.func_180495_p(blockpos1).func_177230_c().func_176200_f(p_180614_2_, blockpos1));
            if (flag1) {
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
                     p_180614_2_.func_175698_g(blockpos);
                     p_180614_2_.func_175698_g(blockpos1);
                     EntityArmorStand entityarmorstand = new EntityArmorStand(p_180614_2_, d0 + 0.5D, d1, d2 + 0.5D);
                     float f = (float)MathHelper.func_76141_d((MathHelper.func_76142_g(p_180614_1_.field_70177_z - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                     entityarmorstand.func_70012_b(d0 + 0.5D, d1, d2 + 0.5D, f, 0.0F);
                     this.func_179221_a(entityarmorstand, p_180614_2_.field_73012_v);
                     ItemMonsterPlacer.func_185079_a(p_180614_2_, p_180614_1_, itemstack, entityarmorstand);
                     p_180614_2_.func_72838_d(entityarmorstand);
                     p_180614_2_.func_184148_a((EntityPlayer)null, entityarmorstand.field_70165_t, entityarmorstand.field_70163_u, entityarmorstand.field_70161_v, SoundEvents.field_187710_m, SoundCategory.BLOCKS, 0.75F, 0.8F);
                  }

                  itemstack.func_190918_g(1);
                  return EnumActionResult.SUCCESS;
               }
            }
         }
      }
   }

   private void func_179221_a(EntityArmorStand p_179221_1_, Random p_179221_2_) {
      Rotations rotations = p_179221_1_.func_175418_s();
      float f = p_179221_2_.nextFloat() * 5.0F;
      float f1 = p_179221_2_.nextFloat() * 20.0F - 10.0F;
      Rotations rotations1 = new Rotations(rotations.func_179415_b() + f, rotations.func_179416_c() + f1, rotations.func_179413_d());
      p_179221_1_.func_175415_a(rotations1);
      rotations = p_179221_1_.func_175408_t();
      f = p_179221_2_.nextFloat() * 10.0F - 5.0F;
      rotations1 = new Rotations(rotations.func_179415_b(), rotations.func_179416_c() + f, rotations.func_179413_d());
      p_179221_1_.func_175424_b(rotations1);
   }
}
