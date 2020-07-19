package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityShoulderRiding;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAILandOnOwnersShoulder extends EntityAIBase {
   private final EntityShoulderRiding field_192382_a;
   private EntityPlayer field_192383_b;
   private boolean field_192384_c;

   public EntityAILandOnOwnersShoulder(EntityShoulderRiding p_i47415_1_) {
      this.field_192382_a = p_i47415_1_;
   }

   public boolean func_75250_a() {
      EntityLivingBase entitylivingbase = this.field_192382_a.func_70902_q();
      boolean flag = entitylivingbase != null && !((EntityPlayer)entitylivingbase).func_175149_v() && !((EntityPlayer)entitylivingbase).field_71075_bZ.field_75100_b && !entitylivingbase.func_70090_H();
      return !this.field_192382_a.func_70906_o() && flag && this.field_192382_a.func_191995_du();
   }

   public boolean func_75252_g() {
      return !this.field_192384_c;
   }

   public void func_75249_e() {
      this.field_192383_b = (EntityPlayer)this.field_192382_a.func_70902_q();
      this.field_192384_c = false;
   }

   public void func_75246_d() {
      if (!this.field_192384_c && !this.field_192382_a.func_70906_o() && !this.field_192382_a.func_110167_bD()) {
         if (this.field_192382_a.func_174813_aQ().func_72326_a(this.field_192383_b.func_174813_aQ())) {
            this.field_192384_c = this.field_192382_a.func_191994_f(this.field_192383_b);
         }

      }
   }
}
