package net.minecraft.client.player.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IInteractionObject;

public class LocalBlockIntercommunication implements IInteractionObject {
   private final String field_175126_a;
   private final ITextComponent field_175125_b;

   public LocalBlockIntercommunication(String p_i46277_1_, ITextComponent p_i46277_2_) {
      this.field_175126_a = p_i46277_1_;
      this.field_175125_b = p_i46277_2_;
   }

   public Container func_174876_a(InventoryPlayer p_174876_1_, EntityPlayer p_174876_2_) {
      throw new UnsupportedOperationException();
   }

   public String func_70005_c_() {
      return this.field_175125_b.func_150260_c();
   }

   public boolean func_145818_k_() {
      return true;
   }

   public String func_174875_k() {
      return this.field_175126_a;
   }

   public ITextComponent func_145748_c_() {
      return this.field_175125_b;
   }
}
