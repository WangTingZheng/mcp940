package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAITargetNonTamed<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {
   private final EntityTameable field_75310_g;

   public EntityAITargetNonTamed(EntityTameable p_i45876_1_, Class<T> p_i45876_2_, boolean p_i45876_3_, Predicate<? super T> p_i45876_4_) {
      super(p_i45876_1_, p_i45876_2_, 10, p_i45876_3_, false, p_i45876_4_);
      this.field_75310_g = p_i45876_1_;
   }

   public boolean func_75250_a() {
      return !this.field_75310_g.func_70909_n() && super.func_75250_a();
   }
}
