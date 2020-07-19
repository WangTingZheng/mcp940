package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;

public abstract class GuiListExtended extends GuiSlot {
   public GuiListExtended(Minecraft p_i45010_1_, int p_i45010_2_, int p_i45010_3_, int p_i45010_4_, int p_i45010_5_, int p_i45010_6_) {
      super(p_i45010_1_, p_i45010_2_, p_i45010_3_, p_i45010_4_, p_i45010_5_, p_i45010_6_);
   }

   protected void func_148144_a(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_) {
   }

   protected boolean func_148131_a(int p_148131_1_) {
      return false;
   }

   protected void func_148123_a() {
   }

   protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_) {
      this.func_148180_b(p_192637_1_).func_192634_a(p_192637_1_, p_192637_2_, p_192637_3_, this.func_148139_c(), p_192637_4_, p_192637_5_, p_192637_6_, this.func_148141_e(p_192637_6_) && this.func_148124_c(p_192637_5_, p_192637_6_) == p_192637_1_, p_192637_7_);
   }

   protected void func_192639_a(int p_192639_1_, int p_192639_2_, int p_192639_3_, float p_192639_4_) {
      this.func_148180_b(p_192639_1_).func_192633_a(p_192639_1_, p_192639_2_, p_192639_3_, p_192639_4_);
   }

   public boolean func_148179_a(int p_148179_1_, int p_148179_2_, int p_148179_3_) {
      if (this.func_148141_e(p_148179_2_)) {
         int i = this.func_148124_c(p_148179_1_, p_148179_2_);
         if (i >= 0) {
            int j = this.field_148152_e + this.field_148155_a / 2 - this.func_148139_c() / 2 + 2;
            int k = this.field_148153_b + 4 - this.func_148148_g() + i * this.field_148149_f + this.field_148160_j;
            int l = p_148179_1_ - j;
            int i1 = p_148179_2_ - k;
            if (this.func_148180_b(i).func_148278_a(i, p_148179_1_, p_148179_2_, p_148179_3_, l, i1)) {
               this.func_148143_b(false);
               return true;
            }
         }
      }

      return false;
   }

   public boolean func_148181_b(int p_148181_1_, int p_148181_2_, int p_148181_3_) {
      for(int i = 0; i < this.func_148127_b(); ++i) {
         int j = this.field_148152_e + this.field_148155_a / 2 - this.func_148139_c() / 2 + 2;
         int k = this.field_148153_b + 4 - this.func_148148_g() + i * this.field_148149_f + this.field_148160_j;
         int l = p_148181_1_ - j;
         int i1 = p_148181_2_ - k;
         this.func_148180_b(i).func_148277_b(i, p_148181_1_, p_148181_2_, p_148181_3_, l, i1);
      }

      this.func_148143_b(true);
      return false;
   }

   public abstract GuiListExtended.IGuiListEntry func_148180_b(int var1);

   public interface IGuiListEntry {
      void func_192633_a(int var1, int var2, int var3, float var4);

      void func_192634_a(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8, float var9);

      boolean func_148278_a(int var1, int var2, int var3, int var4, int var5, int var6);

      void func_148277_b(int var1, int var2, int var3, int var4, int var5, int var6);
   }
}
