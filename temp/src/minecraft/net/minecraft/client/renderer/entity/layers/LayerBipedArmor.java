package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;

public class LayerBipedArmor extends LayerArmorBase<ModelBiped> {
   public LayerBipedArmor(RenderLivingBase<?> p_i46116_1_) {
      super(p_i46116_1_);
   }

   protected void func_177177_a() {
      this.field_177189_c = (T)(new ModelBiped(0.5F));
      this.field_177186_d = (T)(new ModelBiped(1.0F));
   }

   protected void func_188359_a(ModelBiped p_188359_1_, EntityEquipmentSlot p_188359_2_) {
      this.func_177194_a(p_188359_1_);
      switch(p_188359_2_) {
      case HEAD:
         p_188359_1_.field_78116_c.field_78806_j = true;
         p_188359_1_.field_178720_f.field_78806_j = true;
         break;
      case CHEST:
         p_188359_1_.field_78115_e.field_78806_j = true;
         p_188359_1_.field_178723_h.field_78806_j = true;
         p_188359_1_.field_178724_i.field_78806_j = true;
         break;
      case LEGS:
         p_188359_1_.field_78115_e.field_78806_j = true;
         p_188359_1_.field_178721_j.field_78806_j = true;
         p_188359_1_.field_178722_k.field_78806_j = true;
         break;
      case FEET:
         p_188359_1_.field_178721_j.field_78806_j = true;
         p_188359_1_.field_178722_k.field_78806_j = true;
      }

   }

   protected void func_177194_a(ModelBiped p_177194_1_) {
      p_177194_1_.func_178719_a(false);
   }
}
