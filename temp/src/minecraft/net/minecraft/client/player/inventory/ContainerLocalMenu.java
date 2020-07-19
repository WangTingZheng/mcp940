package net.minecraft.client.player.inventory;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

public class ContainerLocalMenu extends InventoryBasic implements ILockableContainer {
   private final String field_174896_a;
   private final Map<Integer, Integer> field_174895_b = Maps.<Integer, Integer>newHashMap();

   public ContainerLocalMenu(String p_i46276_1_, ITextComponent p_i46276_2_, int p_i46276_3_) {
      super(p_i46276_2_, p_i46276_3_);
      this.field_174896_a = p_i46276_1_;
   }

   public int func_174887_a_(int p_174887_1_) {
      return this.field_174895_b.containsKey(Integer.valueOf(p_174887_1_)) ? ((Integer)this.field_174895_b.get(Integer.valueOf(p_174887_1_))).intValue() : 0;
   }

   public void func_174885_b(int p_174885_1_, int p_174885_2_) {
      this.field_174895_b.put(Integer.valueOf(p_174885_1_), Integer.valueOf(p_174885_2_));
   }

   public int func_174890_g() {
      return this.field_174895_b.size();
   }

   public boolean func_174893_q_() {
      return false;
   }

   public void func_174892_a(LockCode p_174892_1_) {
   }

   public LockCode func_174891_i() {
      return LockCode.field_180162_a;
   }

   public String func_174875_k() {
      return this.field_174896_a;
   }

   public Container func_174876_a(InventoryPlayer p_174876_1_, EntityPlayer p_174876_2_) {
      throw new UnsupportedOperationException();
   }
}
