package net.minecraft.entity.ai;

import com.google.common.base.Predicates;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntitySelectors;

public class EntityAIWatchClosest extends EntityAIBase {
   protected EntityLiving field_75332_b;
   protected Entity field_75334_a;
   protected float field_75333_c;
   private int field_75330_d;
   private final float field_75331_e;
   protected Class<? extends Entity> field_75329_f;

   public EntityAIWatchClosest(EntityLiving p_i1631_1_, Class<? extends Entity> p_i1631_2_, float p_i1631_3_) {
      this.field_75332_b = p_i1631_1_;
      this.field_75329_f = p_i1631_2_;
      this.field_75333_c = p_i1631_3_;
      this.field_75331_e = 0.02F;
      this.func_75248_a(2);
   }

   public EntityAIWatchClosest(EntityLiving p_i1632_1_, Class<? extends Entity> p_i1632_2_, float p_i1632_3_, float p_i1632_4_) {
      this.field_75332_b = p_i1632_1_;
      this.field_75329_f = p_i1632_2_;
      this.field_75333_c = p_i1632_3_;
      this.field_75331_e = p_i1632_4_;
      this.func_75248_a(2);
   }

   public boolean func_75250_a() {
      if (this.field_75332_b.func_70681_au().nextFloat() >= this.field_75331_e) {
         return false;
      } else {
         if (this.field_75332_b.func_70638_az() != null) {
            this.field_75334_a = this.field_75332_b.func_70638_az();
         }

         if (this.field_75329_f == EntityPlayer.class) {
            this.field_75334_a = this.field_75332_b.field_70170_p.func_190525_a(this.field_75332_b.field_70165_t, this.field_75332_b.field_70163_u, this.field_75332_b.field_70161_v, (double)this.field_75333_c, Predicates.and(EntitySelectors.field_180132_d, EntitySelectors.func_191324_b(this.field_75332_b)));
         } else {
            this.field_75334_a = this.field_75332_b.field_70170_p.func_72857_a(this.field_75329_f, this.field_75332_b.func_174813_aQ().func_72314_b((double)this.field_75333_c, 3.0D, (double)this.field_75333_c), this.field_75332_b);
         }

         return this.field_75334_a != null;
      }
   }

   public boolean func_75253_b() {
      if (!this.field_75334_a.func_70089_S()) {
         return false;
      } else if (this.field_75332_b.func_70068_e(this.field_75334_a) > (double)(this.field_75333_c * this.field_75333_c)) {
         return false;
      } else {
         return this.field_75330_d > 0;
      }
   }

   public void func_75249_e() {
      this.field_75330_d = 40 + this.field_75332_b.func_70681_au().nextInt(40);
   }

   public void func_75251_c() {
      this.field_75334_a = null;
   }

   public void func_75246_d() {
      this.field_75332_b.func_70671_ap().func_75650_a(this.field_75334_a.field_70165_t, this.field_75334_a.field_70163_u + (double)this.field_75334_a.func_70047_e(), this.field_75334_a.field_70161_v, (float)this.field_75332_b.func_184649_cE(), (float)this.field_75332_b.func_70646_bf());
      --this.field_75330_d;
   }
}
