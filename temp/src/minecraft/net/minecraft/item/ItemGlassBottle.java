package net.minecraft.item;

import com.google.common.base.Predicate;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemGlassBottle extends Item {
   public ItemGlassBottle() {
      this.func_77637_a(CreativeTabs.field_78038_k);
   }

   public ActionResult<ItemStack> func_77659_a(World p_77659_1_, EntityPlayer p_77659_2_, EnumHand p_77659_3_) {
      List<EntityAreaEffectCloud> list = p_77659_1_.<EntityAreaEffectCloud>func_175647_a(EntityAreaEffectCloud.class, p_77659_2_.func_174813_aQ().func_186662_g(2.0D), new Predicate<EntityAreaEffectCloud>() {
         public boolean apply(@Nullable EntityAreaEffectCloud p_apply_1_) {
            return p_apply_1_ != null && p_apply_1_.func_70089_S() && p_apply_1_.func_184494_w() instanceof EntityDragon;
         }
      });
      ItemStack itemstack = p_77659_2_.func_184586_b(p_77659_3_);
      if (!list.isEmpty()) {
         EntityAreaEffectCloud entityareaeffectcloud = list.get(0);
         entityareaeffectcloud.func_184483_a(entityareaeffectcloud.func_184490_j() - 0.5F);
         p_77659_1_.func_184148_a((EntityPlayer)null, p_77659_2_.field_70165_t, p_77659_2_.field_70163_u, p_77659_2_.field_70161_v, SoundEvents.field_187618_I, SoundCategory.NEUTRAL, 1.0F, 1.0F);
         return new ActionResult(EnumActionResult.SUCCESS, this.func_185061_a(itemstack, p_77659_2_, new ItemStack(Items.field_185157_bK)));
      } else {
         RayTraceResult raytraceresult = this.func_77621_a(p_77659_1_, p_77659_2_, true);
         if (raytraceresult == null) {
            return new ActionResult(EnumActionResult.PASS, itemstack);
         } else {
            if (raytraceresult.field_72313_a == RayTraceResult.Type.BLOCK) {
               BlockPos blockpos = raytraceresult.func_178782_a();
               if (!p_77659_1_.func_175660_a(p_77659_2_, blockpos) || !p_77659_2_.func_175151_a(blockpos.func_177972_a(raytraceresult.field_178784_b), raytraceresult.field_178784_b, itemstack)) {
                  return new ActionResult(EnumActionResult.PASS, itemstack);
               }

               if (p_77659_1_.func_180495_p(blockpos).func_185904_a() == Material.field_151586_h) {
                  p_77659_1_.func_184148_a(p_77659_2_, p_77659_2_.field_70165_t, p_77659_2_.field_70163_u, p_77659_2_.field_70161_v, SoundEvents.field_187615_H, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                  return new ActionResult(EnumActionResult.SUCCESS, this.func_185061_a(itemstack, p_77659_2_, PotionUtils.func_185188_a(new ItemStack(Items.field_151068_bn), PotionTypes.field_185230_b)));
               }
            }

            return new ActionResult(EnumActionResult.PASS, itemstack);
         }
      }
   }

   protected ItemStack func_185061_a(ItemStack p_185061_1_, EntityPlayer p_185061_2_, ItemStack p_185061_3_) {
      p_185061_1_.func_190918_g(1);
      p_185061_2_.func_71029_a(StatList.func_188057_b(this));
      if (p_185061_1_.func_190926_b()) {
         return p_185061_3_;
      } else {
         if (!p_185061_2_.field_71071_by.func_70441_a(p_185061_3_)) {
            p_185061_2_.func_71019_a(p_185061_3_, false);
         }

         return p_185061_1_;
      }
   }
}
