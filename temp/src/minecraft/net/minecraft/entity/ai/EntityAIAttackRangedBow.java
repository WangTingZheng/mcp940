package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.util.EnumHand;

public class EntityAIAttackRangedBow<T extends EntityMob & IRangedAttackMob> extends EntityAIBase {
   private final T field_188499_a;
   private final double field_188500_b;
   private int field_188501_c;
   private final float field_188502_d;
   private int field_188503_e = -1;
   private int field_188504_f;
   private boolean field_188505_g;
   private boolean field_188506_h;
   private int field_188507_i = -1;

   public EntityAIAttackRangedBow(T p_i47515_1_, double p_i47515_2_, int p_i47515_4_, float p_i47515_5_) {
      this.field_188499_a = p_i47515_1_;
      this.field_188500_b = p_i47515_2_;
      this.field_188501_c = p_i47515_4_;
      this.field_188502_d = p_i47515_5_ * p_i47515_5_;
      this.func_75248_a(3);
   }

   public void func_189428_b(int p_189428_1_) {
      this.field_188501_c = p_189428_1_;
   }

   public boolean func_75250_a() {
      return this.field_188499_a.func_70638_az() == null ? false : this.func_188498_f();
   }

   protected boolean func_188498_f() {
      return !this.field_188499_a.func_184614_ca().func_190926_b() && this.field_188499_a.func_184614_ca().func_77973_b() == Items.field_151031_f;
   }

   public boolean func_75253_b() {
      return (this.func_75250_a() || !this.field_188499_a.func_70661_as().func_75500_f()) && this.func_188498_f();
   }

   public void func_75249_e() {
      super.func_75249_e();
      ((IRangedAttackMob)this.field_188499_a).func_184724_a(true);
   }

   public void func_75251_c() {
      super.func_75251_c();
      ((IRangedAttackMob)this.field_188499_a).func_184724_a(false);
      this.field_188504_f = 0;
      this.field_188503_e = -1;
      this.field_188499_a.func_184602_cy();
   }

   public void func_75246_d() {
      EntityLivingBase entitylivingbase = this.field_188499_a.func_70638_az();
      if (entitylivingbase != null) {
         double d0 = this.field_188499_a.func_70092_e(entitylivingbase.field_70165_t, entitylivingbase.func_174813_aQ().field_72338_b, entitylivingbase.field_70161_v);
         boolean flag = this.field_188499_a.func_70635_at().func_75522_a(entitylivingbase);
         boolean flag1 = this.field_188504_f > 0;
         if (flag != flag1) {
            this.field_188504_f = 0;
         }

         if (flag) {
            ++this.field_188504_f;
         } else {
            --this.field_188504_f;
         }

         if (d0 <= (double)this.field_188502_d && this.field_188504_f >= 20) {
            this.field_188499_a.func_70661_as().func_75499_g();
            ++this.field_188507_i;
         } else {
            this.field_188499_a.func_70661_as().func_75497_a(entitylivingbase, this.field_188500_b);
            this.field_188507_i = -1;
         }

         if (this.field_188507_i >= 20) {
            if ((double)this.field_188499_a.func_70681_au().nextFloat() < 0.3D) {
               this.field_188505_g = !this.field_188505_g;
            }

            if ((double)this.field_188499_a.func_70681_au().nextFloat() < 0.3D) {
               this.field_188506_h = !this.field_188506_h;
            }

            this.field_188507_i = 0;
         }

         if (this.field_188507_i > -1) {
            if (d0 > (double)(this.field_188502_d * 0.75F)) {
               this.field_188506_h = false;
            } else if (d0 < (double)(this.field_188502_d * 0.25F)) {
               this.field_188506_h = true;
            }

            this.field_188499_a.func_70605_aq().func_188488_a(this.field_188506_h ? -0.5F : 0.5F, this.field_188505_g ? 0.5F : -0.5F);
            this.field_188499_a.func_70625_a(entitylivingbase, 30.0F, 30.0F);
         } else {
            this.field_188499_a.func_70671_ap().func_75651_a(entitylivingbase, 30.0F, 30.0F);
         }

         if (this.field_188499_a.func_184587_cr()) {
            if (!flag && this.field_188504_f < -60) {
               this.field_188499_a.func_184602_cy();
            } else if (flag) {
               int i = this.field_188499_a.func_184612_cw();
               if (i >= 20) {
                  this.field_188499_a.func_184602_cy();
                  ((IRangedAttackMob)this.field_188499_a).func_82196_d(entitylivingbase, ItemBow.func_185059_b(i));
                  this.field_188503_e = this.field_188501_c;
               }
            }
         } else if (--this.field_188503_e <= 0 && this.field_188504_f >= -60) {
            this.field_188499_a.func_184598_c(EnumHand.MAIN_HAND);
         }

      }
   }
}
