package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.SoundEvents;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemFishingRod extends Item {
   public ItemFishingRod() {
      this.func_77656_e(64);
      this.func_77625_d(1);
      this.func_77637_a(CreativeTabs.field_78040_i);
      this.func_185043_a(new ResourceLocation("cast"), new IItemPropertyGetter() {
         public float func_185085_a(ItemStack p_185085_1_, @Nullable World p_185085_2_, @Nullable EntityLivingBase p_185085_3_) {
            if (p_185085_3_ == null) {
               return 0.0F;
            } else {
               boolean flag = p_185085_3_.func_184614_ca() == p_185085_1_;
               boolean flag1 = p_185085_3_.func_184592_cb() == p_185085_1_;
               if (p_185085_3_.func_184614_ca().func_77973_b() instanceof ItemFishingRod) {
                  flag1 = false;
               }

               return (flag || flag1) && p_185085_3_ instanceof EntityPlayer && ((EntityPlayer)p_185085_3_).field_71104_cf != null ? 1.0F : 0.0F;
            }
         }
      });
   }

   public boolean func_77662_d() {
      return true;
   }

   public boolean func_77629_n_() {
      return true;
   }

   public ActionResult<ItemStack> func_77659_a(World p_77659_1_, EntityPlayer p_77659_2_, EnumHand p_77659_3_) {
      ItemStack itemstack = p_77659_2_.func_184586_b(p_77659_3_);
      if (p_77659_2_.field_71104_cf != null) {
         int i = p_77659_2_.field_71104_cf.func_146034_e();
         itemstack.func_77972_a(i, p_77659_2_);
         p_77659_2_.func_184609_a(p_77659_3_);
         p_77659_1_.func_184148_a((EntityPlayer)null, p_77659_2_.field_70165_t, p_77659_2_.field_70163_u, p_77659_2_.field_70161_v, SoundEvents.field_193780_J, SoundCategory.NEUTRAL, 1.0F, 0.4F / (field_77697_d.nextFloat() * 0.4F + 0.8F));
      } else {
         p_77659_1_.func_184148_a((EntityPlayer)null, p_77659_2_.field_70165_t, p_77659_2_.field_70163_u, p_77659_2_.field_70161_v, SoundEvents.field_187612_G, SoundCategory.NEUTRAL, 0.5F, 0.4F / (field_77697_d.nextFloat() * 0.4F + 0.8F));
         if (!p_77659_1_.field_72995_K) {
            EntityFishHook entityfishhook = new EntityFishHook(p_77659_1_, p_77659_2_);
            int j = EnchantmentHelper.func_191528_c(itemstack);
            if (j > 0) {
               entityfishhook.func_191516_a(j);
            }

            int k = EnchantmentHelper.func_191529_b(itemstack);
            if (k > 0) {
               entityfishhook.func_191517_b(k);
            }

            p_77659_1_.func_72838_d(entityfishhook);
         }

         p_77659_2_.func_184609_a(p_77659_3_);
         p_77659_2_.func_71029_a(StatList.func_188057_b(this));
      }

      return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
   }

   public int func_77619_b() {
      return 1;
   }
}
