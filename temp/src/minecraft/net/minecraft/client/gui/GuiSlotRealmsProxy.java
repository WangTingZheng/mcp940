package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.realms.RealmsScrolledSelectionList;

public class GuiSlotRealmsProxy extends GuiSlot {
   private final RealmsScrolledSelectionList field_154340_k;

   public GuiSlotRealmsProxy(RealmsScrolledSelectionList p_i1085_1_, int p_i1085_2_, int p_i1085_3_, int p_i1085_4_, int p_i1085_5_, int p_i1085_6_) {
      super(Minecraft.func_71410_x(), p_i1085_2_, p_i1085_3_, p_i1085_4_, p_i1085_5_, p_i1085_6_);
      this.field_154340_k = p_i1085_1_;
   }

   protected int func_148127_b() {
      return this.field_154340_k.getItemCount();
   }

   protected void func_148144_a(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_) {
      this.field_154340_k.selectItem(p_148144_1_, p_148144_2_, p_148144_3_, p_148144_4_);
   }

   protected boolean func_148131_a(int p_148131_1_) {
      return this.field_154340_k.isSelectedItem(p_148131_1_);
   }

   protected void func_148123_a() {
      this.field_154340_k.renderBackground();
   }

   protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_) {
      this.field_154340_k.renderItem(p_192637_1_, p_192637_2_, p_192637_3_, p_192637_4_, p_192637_5_, p_192637_6_);
   }

   public int func_154338_k() {
      return this.field_148155_a;
   }

   public int func_154339_l() {
      return this.field_148162_h;
   }

   public int func_154337_m() {
      return this.field_148150_g;
   }

   protected int func_148138_e() {
      return this.field_154340_k.getMaxPosition();
   }

   protected int func_148137_d() {
      return this.field_154340_k.getScrollbarPosition();
   }

   public void func_178039_p() {
      super.func_178039_p();
   }
}
