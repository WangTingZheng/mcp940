package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButtonToggle extends GuiButton {
   protected ResourceLocation field_191760_o;
   protected boolean field_191755_p;
   protected int field_191756_q;
   protected int field_191757_r;
   protected int field_191758_s;
   protected int field_191759_t;

   public GuiButtonToggle(int p_i47389_1_, int p_i47389_2_, int p_i47389_3_, int p_i47389_4_, int p_i47389_5_, boolean p_i47389_6_) {
      super(p_i47389_1_, p_i47389_2_, p_i47389_3_, p_i47389_4_, p_i47389_5_, "");
      this.field_191755_p = p_i47389_6_;
   }

   public void func_191751_a(int p_191751_1_, int p_191751_2_, int p_191751_3_, int p_191751_4_, ResourceLocation p_191751_5_) {
      this.field_191756_q = p_191751_1_;
      this.field_191757_r = p_191751_2_;
      this.field_191758_s = p_191751_3_;
      this.field_191759_t = p_191751_4_;
      this.field_191760_o = p_191751_5_;
   }

   public void func_191753_b(boolean p_191753_1_) {
      this.field_191755_p = p_191753_1_;
   }

   public boolean func_191754_c() {
      return this.field_191755_p;
   }

   public void func_191752_c(int p_191752_1_, int p_191752_2_) {
      this.field_146128_h = p_191752_1_;
      this.field_146129_i = p_191752_2_;
   }

   public void func_191745_a(Minecraft p_191745_1_, int p_191745_2_, int p_191745_3_, float p_191745_4_) {
      if (this.field_146125_m) {
         this.field_146123_n = p_191745_2_ >= this.field_146128_h && p_191745_3_ >= this.field_146129_i && p_191745_2_ < this.field_146128_h + this.field_146120_f && p_191745_3_ < this.field_146129_i + this.field_146121_g;
         p_191745_1_.func_110434_K().func_110577_a(this.field_191760_o);
         GlStateManager.func_179097_i();
         int i = this.field_191756_q;
         int j = this.field_191757_r;
         if (this.field_191755_p) {
            i += this.field_191758_s;
         }

         if (this.field_146123_n) {
            j += this.field_191759_t;
         }

         this.func_73729_b(this.field_146128_h, this.field_146129_i, i, j, this.field_146120_f, this.field_146121_g);
         GlStateManager.func_179126_j();
      }
   }
}
