package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

public class GuiButton extends Gui {
   protected static final ResourceLocation field_146122_a = new ResourceLocation("textures/gui/widgets.png");
   protected int field_146120_f;
   protected int field_146121_g;
   public int field_146128_h;
   public int field_146129_i;
   public String field_146126_j;
   public int field_146127_k;
   public boolean field_146124_l;
   public boolean field_146125_m;
   protected boolean field_146123_n;

   public GuiButton(int p_i1020_1_, int p_i1020_2_, int p_i1020_3_, String p_i1020_4_) {
      this(p_i1020_1_, p_i1020_2_, p_i1020_3_, 200, 20, p_i1020_4_);
   }

   public GuiButton(int p_i46323_1_, int p_i46323_2_, int p_i46323_3_, int p_i46323_4_, int p_i46323_5_, String p_i46323_6_) {
      this.field_146120_f = 200;
      this.field_146121_g = 20;
      this.field_146124_l = true;
      this.field_146125_m = true;
      this.field_146127_k = p_i46323_1_;
      this.field_146128_h = p_i46323_2_;
      this.field_146129_i = p_i46323_3_;
      this.field_146120_f = p_i46323_4_;
      this.field_146121_g = p_i46323_5_;
      this.field_146126_j = p_i46323_6_;
   }

   protected int func_146114_a(boolean p_146114_1_) {
      int i = 1;
      if (!this.field_146124_l) {
         i = 0;
      } else if (p_146114_1_) {
         i = 2;
      }

      return i;
   }

   public void func_191745_a(Minecraft p_191745_1_, int p_191745_2_, int p_191745_3_, float p_191745_4_) {
      if (this.field_146125_m) {
         FontRenderer fontrenderer = p_191745_1_.field_71466_p;
         p_191745_1_.func_110434_K().func_110577_a(field_146122_a);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_146123_n = p_191745_2_ >= this.field_146128_h && p_191745_3_ >= this.field_146129_i && p_191745_2_ < this.field_146128_h + this.field_146120_f && p_191745_3_ < this.field_146129_i + this.field_146121_g;
         int i = this.func_146114_a(this.field_146123_n);
         GlStateManager.func_179147_l();
         GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
         GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
         this.func_73729_b(this.field_146128_h, this.field_146129_i, 0, 46 + i * 20, this.field_146120_f / 2, this.field_146121_g);
         this.func_73729_b(this.field_146128_h + this.field_146120_f / 2, this.field_146129_i, 200 - this.field_146120_f / 2, 46 + i * 20, this.field_146120_f / 2, this.field_146121_g);
         this.func_146119_b(p_191745_1_, p_191745_2_, p_191745_3_);
         int j = 14737632;
         if (!this.field_146124_l) {
            j = 10526880;
         } else if (this.field_146123_n) {
            j = 16777120;
         }

         this.func_73732_a(fontrenderer, this.field_146126_j, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + (this.field_146121_g - 8) / 2, j);
      }
   }

   protected void func_146119_b(Minecraft p_146119_1_, int p_146119_2_, int p_146119_3_) {
   }

   public void func_146118_a(int p_146118_1_, int p_146118_2_) {
   }

   public boolean func_146116_c(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_) {
      return this.field_146124_l && this.field_146125_m && p_146116_2_ >= this.field_146128_h && p_146116_3_ >= this.field_146129_i && p_146116_2_ < this.field_146128_h + this.field_146120_f && p_146116_3_ < this.field_146129_i + this.field_146121_g;
   }

   public boolean func_146115_a() {
      return this.field_146123_n;
   }

   public void func_146111_b(int p_146111_1_, int p_146111_2_) {
   }

   public void func_146113_a(SoundHandler p_146113_1_) {
      p_146113_1_.func_147682_a(PositionedSoundRecord.func_184371_a(SoundEvents.field_187909_gi, 1.0F));
   }

   public int func_146117_b() {
      return this.field_146120_f;
   }

   public void func_175211_a(int p_175211_1_) {
      this.field_146120_f = p_175211_1_;
   }
}
