package net.minecraft.util;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;

public class EntityDamageSourceIndirect extends EntityDamageSource {
   private final Entity field_76387_p;

   public EntityDamageSourceIndirect(String p_i1568_1_, Entity p_i1568_2_, @Nullable Entity p_i1568_3_) {
      super(p_i1568_1_, p_i1568_2_);
      this.field_76387_p = p_i1568_3_;
   }

   @Nullable
   public Entity func_76364_f() {
      return this.field_76386_o;
   }

   @Nullable
   public Entity func_76346_g() {
      return this.field_76387_p;
   }

   public ITextComponent func_151519_b(EntityLivingBase p_151519_1_) {
      ITextComponent itextcomponent = this.field_76387_p == null ? this.field_76386_o.func_145748_c_() : this.field_76387_p.func_145748_c_();
      ItemStack itemstack = this.field_76387_p instanceof EntityLivingBase ? ((EntityLivingBase)this.field_76387_p).func_184614_ca() : ItemStack.field_190927_a;
      String s = "death.attack." + this.field_76373_n;
      String s1 = s + ".item";
      return !itemstack.func_190926_b() && itemstack.func_82837_s() && I18n.func_94522_b(s1) ? new TextComponentTranslation(s1, new Object[]{p_151519_1_.func_145748_c_(), itextcomponent, itemstack.func_151000_E()}) : new TextComponentTranslation(s, new Object[]{p_151519_1_.func_145748_c_(), itextcomponent});
   }
}
