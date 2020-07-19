package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAITradePlayer extends EntityAIBase {
   private final EntityVillager field_75276_a;

   public EntityAITradePlayer(EntityVillager p_i1658_1_) {
      this.field_75276_a = p_i1658_1_;
      this.func_75248_a(5);
   }

   public boolean func_75250_a() {
      if (!this.field_75276_a.func_70089_S()) {
         return false;
      } else if (this.field_75276_a.func_70090_H()) {
         return false;
      } else if (!this.field_75276_a.field_70122_E) {
         return false;
      } else if (this.field_75276_a.field_70133_I) {
         return false;
      } else {
         EntityPlayer entityplayer = this.field_75276_a.func_70931_l_();
         if (entityplayer == null) {
            return false;
         } else if (this.field_75276_a.func_70068_e(entityplayer) > 16.0D) {
            return false;
         } else {
            return entityplayer.field_71070_bA != null;
         }
      }
   }

   public void func_75249_e() {
      this.field_75276_a.func_70661_as().func_75499_g();
   }

   public void func_75251_c() {
      this.field_75276_a.func_70932_a_((EntityPlayer)null);
   }
}
