package net.minecraft.util;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;

public class EntityDamageSource extends DamageSource {
   @Nullable
   protected Entity field_76386_o;
   private boolean field_180140_r;

   public EntityDamageSource(String p_i1567_1_, @Nullable Entity p_i1567_2_) {
      super(p_i1567_1_);
      this.field_76386_o = p_i1567_2_;
   }

   public EntityDamageSource func_180138_v() {
      this.field_180140_r = true;
      return this;
   }

   public boolean func_180139_w() {
      return this.field_180140_r;
   }

   @Nullable
   public Entity func_76346_g() {
      return this.field_76386_o;
   }

   public ITextComponent func_151519_b(EntityLivingBase p_151519_1_) {
      ItemStack itemstack = this.field_76386_o instanceof EntityLivingBase ? ((EntityLivingBase)this.field_76386_o).func_184614_ca() : ItemStack.field_190927_a;
      String s = "death.attack." + this.field_76373_n;
      String s1 = s + ".item";
      return !itemstack.func_190926_b() && itemstack.func_82837_s() && I18n.func_94522_b(s1) ? new TextComponentTranslation(s1, new Object[]{p_151519_1_.func_145748_c_(), this.field_76386_o.func_145748_c_(), itemstack.func_151000_E()}) : new TextComponentTranslation(s, new Object[]{p_151519_1_.func_145748_c_(), this.field_76386_o.func_145748_c_()});
   }

   public boolean func_76350_n() {
      return this.field_76386_o != null && this.field_76386_o instanceof EntityLivingBase && !(this.field_76386_o instanceof EntityPlayer);
   }

   @Nullable
   public Vec3d func_188404_v() {
      return new Vec3d(this.field_76386_o.field_70165_t, this.field_76386_o.field_70163_u, this.field_76386_o.field_70161_v);
   }
}
