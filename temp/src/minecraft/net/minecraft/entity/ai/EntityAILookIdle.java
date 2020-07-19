package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityAILookIdle extends EntityAIBase {
   private final EntityLiving field_75258_a;
   private double field_75256_b;
   private double field_75257_c;
   private int field_75255_d;

   public EntityAILookIdle(EntityLiving p_i1647_1_) {
      this.field_75258_a = p_i1647_1_;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      return this.field_75258_a.func_70681_au().nextFloat() < 0.02F;
   }

   public boolean func_75253_b() {
      return this.field_75255_d >= 0;
   }

   public void func_75249_e() {
      double d0 = 6.283185307179586D * this.field_75258_a.func_70681_au().nextDouble();
      this.field_75256_b = Math.cos(d0);
      this.field_75257_c = Math.sin(d0);
      this.field_75255_d = 20 + this.field_75258_a.func_70681_au().nextInt(20);
   }

   public void func_75246_d() {
      --this.field_75255_d;
      this.field_75258_a.func_70671_ap().func_75650_a(this.field_75258_a.field_70165_t + this.field_75256_b, this.field_75258_a.field_70163_u + (double)this.field_75258_a.func_70047_e(), this.field_75258_a.field_70161_v + this.field_75257_c, (float)this.field_75258_a.func_184649_cE(), (float)this.field_75258_a.func_70646_bf());
   }
}
