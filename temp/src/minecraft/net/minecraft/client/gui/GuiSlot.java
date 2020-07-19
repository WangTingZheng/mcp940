package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Mouse;

public abstract class GuiSlot {
   protected final Minecraft field_148161_k;
   protected int field_148155_a;
   protected int field_148158_l;
   protected int field_148153_b;
   protected int field_148154_c;
   protected int field_148151_d;
   protected int field_148152_e;
   protected final int field_148149_f;
   private int field_148159_m;
   private int field_148156_n;
   protected int field_148150_g;
   protected int field_148162_h;
   protected boolean field_148163_i = true;
   protected int field_148157_o = -2;
   protected float field_148170_p;
   protected float field_148169_q;
   protected int field_148168_r = -1;
   protected long field_148167_s;
   protected boolean field_178041_q = true;
   protected boolean field_148166_t = true;
   protected boolean field_148165_u;
   protected int field_148160_j;
   private boolean field_148164_v = true;

   public GuiSlot(Minecraft p_i1052_1_, int p_i1052_2_, int p_i1052_3_, int p_i1052_4_, int p_i1052_5_, int p_i1052_6_) {
      this.field_148161_k = p_i1052_1_;
      this.field_148155_a = p_i1052_2_;
      this.field_148158_l = p_i1052_3_;
      this.field_148153_b = p_i1052_4_;
      this.field_148154_c = p_i1052_5_;
      this.field_148149_f = p_i1052_6_;
      this.field_148152_e = 0;
      this.field_148151_d = p_i1052_2_;
   }

   public void func_148122_a(int p_148122_1_, int p_148122_2_, int p_148122_3_, int p_148122_4_) {
      this.field_148155_a = p_148122_1_;
      this.field_148158_l = p_148122_2_;
      this.field_148153_b = p_148122_3_;
      this.field_148154_c = p_148122_4_;
      this.field_148152_e = 0;
      this.field_148151_d = p_148122_1_;
   }

   public void func_193651_b(boolean p_193651_1_) {
      this.field_148166_t = p_193651_1_;
   }

   protected void func_148133_a(boolean p_148133_1_, int p_148133_2_) {
      this.field_148165_u = p_148133_1_;
      this.field_148160_j = p_148133_2_;
      if (!p_148133_1_) {
         this.field_148160_j = 0;
      }

   }

   protected abstract int func_148127_b();

   protected abstract void func_148144_a(int var1, boolean var2, int var3, int var4);

   protected abstract boolean func_148131_a(int var1);

   protected int func_148138_e() {
      return this.func_148127_b() * this.field_148149_f + this.field_148160_j;
   }

   protected abstract void func_148123_a();

   protected void func_192639_a(int p_192639_1_, int p_192639_2_, int p_192639_3_, float p_192639_4_) {
   }

   protected abstract void func_192637_a(int var1, int var2, int var3, int var4, int var5, int var6, float var7);

   protected void func_148129_a(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_) {
   }

   protected void func_148132_a(int p_148132_1_, int p_148132_2_) {
   }

   protected void func_148142_b(int p_148142_1_, int p_148142_2_) {
   }

   public int func_148124_c(int p_148124_1_, int p_148124_2_) {
      int i = this.field_148152_e + this.field_148155_a / 2 - this.func_148139_c() / 2;
      int j = this.field_148152_e + this.field_148155_a / 2 + this.func_148139_c() / 2;
      int k = p_148124_2_ - this.field_148153_b - this.field_148160_j + (int)this.field_148169_q - 4;
      int l = k / this.field_148149_f;
      return p_148124_1_ < this.func_148137_d() && p_148124_1_ >= i && p_148124_1_ <= j && l >= 0 && k >= 0 && l < this.func_148127_b() ? l : -1;
   }

   public void func_148134_d(int p_148134_1_, int p_148134_2_) {
      this.field_148159_m = p_148134_1_;
      this.field_148156_n = p_148134_2_;
   }

   protected void func_148121_k() {
      this.field_148169_q = MathHelper.func_76131_a(this.field_148169_q, 0.0F, (float)this.func_148135_f());
   }

   public int func_148135_f() {
      return Math.max(0, this.func_148138_e() - (this.field_148154_c - this.field_148153_b - 4));
   }

   public int func_148148_g() {
      return (int)this.field_148169_q;
   }

   public boolean func_148141_e(int p_148141_1_) {
      return p_148141_1_ >= this.field_148153_b && p_148141_1_ <= this.field_148154_c && this.field_148150_g >= this.field_148152_e && this.field_148150_g <= this.field_148151_d;
   }

   public void func_148145_f(int p_148145_1_) {
      this.field_148169_q += (float)p_148145_1_;
      this.func_148121_k();
      this.field_148157_o = -2;
   }

   public void func_148147_a(GuiButton p_148147_1_) {
      if (p_148147_1_.field_146124_l) {
         if (p_148147_1_.field_146127_k == this.field_148159_m) {
            this.field_148169_q -= (float)(this.field_148149_f * 2 / 3);
            this.field_148157_o = -2;
            this.func_148121_k();
         } else if (p_148147_1_.field_146127_k == this.field_148156_n) {
            this.field_148169_q += (float)(this.field_148149_f * 2 / 3);
            this.field_148157_o = -2;
            this.func_148121_k();
         }

      }
   }

   public void func_148128_a(int p_148128_1_, int p_148128_2_, float p_148128_3_) {
      if (this.field_178041_q) {
         this.field_148150_g = p_148128_1_;
         this.field_148162_h = p_148128_2_;
         this.func_148123_a();
         int i = this.func_148137_d();
         int j = i + 6;
         this.func_148121_k();
         GlStateManager.func_179140_f();
         GlStateManager.func_179106_n();
         Tessellator tessellator = Tessellator.func_178181_a();
         BufferBuilder bufferbuilder = tessellator.func_178180_c();
         this.field_148161_k.func_110434_K().func_110577_a(Gui.field_110325_k);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         float f = 32.0F;
         bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
         bufferbuilder.func_181662_b((double)this.field_148152_e, (double)this.field_148154_c, 0.0D).func_187315_a((double)((float)this.field_148152_e / 32.0F), (double)((float)(this.field_148154_c + (int)this.field_148169_q) / 32.0F)).func_181669_b(32, 32, 32, 255).func_181675_d();
         bufferbuilder.func_181662_b((double)this.field_148151_d, (double)this.field_148154_c, 0.0D).func_187315_a((double)((float)this.field_148151_d / 32.0F), (double)((float)(this.field_148154_c + (int)this.field_148169_q) / 32.0F)).func_181669_b(32, 32, 32, 255).func_181675_d();
         bufferbuilder.func_181662_b((double)this.field_148151_d, (double)this.field_148153_b, 0.0D).func_187315_a((double)((float)this.field_148151_d / 32.0F), (double)((float)(this.field_148153_b + (int)this.field_148169_q) / 32.0F)).func_181669_b(32, 32, 32, 255).func_181675_d();
         bufferbuilder.func_181662_b((double)this.field_148152_e, (double)this.field_148153_b, 0.0D).func_187315_a((double)((float)this.field_148152_e / 32.0F), (double)((float)(this.field_148153_b + (int)this.field_148169_q) / 32.0F)).func_181669_b(32, 32, 32, 255).func_181675_d();
         tessellator.func_78381_a();
         int k = this.field_148152_e + this.field_148155_a / 2 - this.func_148139_c() / 2 + 2;
         int l = this.field_148153_b + 4 - (int)this.field_148169_q;
         if (this.field_148165_u) {
            this.func_148129_a(k, l, tessellator);
         }

         this.func_192638_a(k, l, p_148128_1_, p_148128_2_, p_148128_3_);
         GlStateManager.func_179097_i();
         this.func_148136_c(0, this.field_148153_b, 255, 255);
         this.func_148136_c(this.field_148154_c, this.field_148158_l, 255, 255);
         GlStateManager.func_179147_l();
         GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
         GlStateManager.func_179118_c();
         GlStateManager.func_179103_j(7425);
         GlStateManager.func_179090_x();
         int i1 = 4;
         bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
         bufferbuilder.func_181662_b((double)this.field_148152_e, (double)(this.field_148153_b + 4), 0.0D).func_187315_a(0.0D, 1.0D).func_181669_b(0, 0, 0, 0).func_181675_d();
         bufferbuilder.func_181662_b((double)this.field_148151_d, (double)(this.field_148153_b + 4), 0.0D).func_187315_a(1.0D, 1.0D).func_181669_b(0, 0, 0, 0).func_181675_d();
         bufferbuilder.func_181662_b((double)this.field_148151_d, (double)this.field_148153_b, 0.0D).func_187315_a(1.0D, 0.0D).func_181669_b(0, 0, 0, 255).func_181675_d();
         bufferbuilder.func_181662_b((double)this.field_148152_e, (double)this.field_148153_b, 0.0D).func_187315_a(0.0D, 0.0D).func_181669_b(0, 0, 0, 255).func_181675_d();
         tessellator.func_78381_a();
         bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
         bufferbuilder.func_181662_b((double)this.field_148152_e, (double)this.field_148154_c, 0.0D).func_187315_a(0.0D, 1.0D).func_181669_b(0, 0, 0, 255).func_181675_d();
         bufferbuilder.func_181662_b((double)this.field_148151_d, (double)this.field_148154_c, 0.0D).func_187315_a(1.0D, 1.0D).func_181669_b(0, 0, 0, 255).func_181675_d();
         bufferbuilder.func_181662_b((double)this.field_148151_d, (double)(this.field_148154_c - 4), 0.0D).func_187315_a(1.0D, 0.0D).func_181669_b(0, 0, 0, 0).func_181675_d();
         bufferbuilder.func_181662_b((double)this.field_148152_e, (double)(this.field_148154_c - 4), 0.0D).func_187315_a(0.0D, 0.0D).func_181669_b(0, 0, 0, 0).func_181675_d();
         tessellator.func_78381_a();
         int j1 = this.func_148135_f();
         if (j1 > 0) {
            int k1 = (this.field_148154_c - this.field_148153_b) * (this.field_148154_c - this.field_148153_b) / this.func_148138_e();
            k1 = MathHelper.func_76125_a(k1, 32, this.field_148154_c - this.field_148153_b - 8);
            int l1 = (int)this.field_148169_q * (this.field_148154_c - this.field_148153_b - k1) / j1 + this.field_148153_b;
            if (l1 < this.field_148153_b) {
               l1 = this.field_148153_b;
            }

            bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
            bufferbuilder.func_181662_b((double)i, (double)this.field_148154_c, 0.0D).func_187315_a(0.0D, 1.0D).func_181669_b(0, 0, 0, 255).func_181675_d();
            bufferbuilder.func_181662_b((double)j, (double)this.field_148154_c, 0.0D).func_187315_a(1.0D, 1.0D).func_181669_b(0, 0, 0, 255).func_181675_d();
            bufferbuilder.func_181662_b((double)j, (double)this.field_148153_b, 0.0D).func_187315_a(1.0D, 0.0D).func_181669_b(0, 0, 0, 255).func_181675_d();
            bufferbuilder.func_181662_b((double)i, (double)this.field_148153_b, 0.0D).func_187315_a(0.0D, 0.0D).func_181669_b(0, 0, 0, 255).func_181675_d();
            tessellator.func_78381_a();
            bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
            bufferbuilder.func_181662_b((double)i, (double)(l1 + k1), 0.0D).func_187315_a(0.0D, 1.0D).func_181669_b(128, 128, 128, 255).func_181675_d();
            bufferbuilder.func_181662_b((double)j, (double)(l1 + k1), 0.0D).func_187315_a(1.0D, 1.0D).func_181669_b(128, 128, 128, 255).func_181675_d();
            bufferbuilder.func_181662_b((double)j, (double)l1, 0.0D).func_187315_a(1.0D, 0.0D).func_181669_b(128, 128, 128, 255).func_181675_d();
            bufferbuilder.func_181662_b((double)i, (double)l1, 0.0D).func_187315_a(0.0D, 0.0D).func_181669_b(128, 128, 128, 255).func_181675_d();
            tessellator.func_78381_a();
            bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
            bufferbuilder.func_181662_b((double)i, (double)(l1 + k1 - 1), 0.0D).func_187315_a(0.0D, 1.0D).func_181669_b(192, 192, 192, 255).func_181675_d();
            bufferbuilder.func_181662_b((double)(j - 1), (double)(l1 + k1 - 1), 0.0D).func_187315_a(1.0D, 1.0D).func_181669_b(192, 192, 192, 255).func_181675_d();
            bufferbuilder.func_181662_b((double)(j - 1), (double)l1, 0.0D).func_187315_a(1.0D, 0.0D).func_181669_b(192, 192, 192, 255).func_181675_d();
            bufferbuilder.func_181662_b((double)i, (double)l1, 0.0D).func_187315_a(0.0D, 0.0D).func_181669_b(192, 192, 192, 255).func_181675_d();
            tessellator.func_78381_a();
         }

         this.func_148142_b(p_148128_1_, p_148128_2_);
         GlStateManager.func_179098_w();
         GlStateManager.func_179103_j(7424);
         GlStateManager.func_179141_d();
         GlStateManager.func_179084_k();
      }
   }

   public void func_178039_p() {
      if (this.func_148141_e(this.field_148162_h)) {
         if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState() && this.field_148162_h >= this.field_148153_b && this.field_148162_h <= this.field_148154_c) {
            int i = (this.field_148155_a - this.func_148139_c()) / 2;
            int j = (this.field_148155_a + this.func_148139_c()) / 2;
            int k = this.field_148162_h - this.field_148153_b - this.field_148160_j + (int)this.field_148169_q - 4;
            int l = k / this.field_148149_f;
            if (l < this.func_148127_b() && this.field_148150_g >= i && this.field_148150_g <= j && l >= 0 && k >= 0) {
               this.func_148144_a(l, false, this.field_148150_g, this.field_148162_h);
               this.field_148168_r = l;
            } else if (this.field_148150_g >= i && this.field_148150_g <= j && k < 0) {
               this.func_148132_a(this.field_148150_g - i, this.field_148162_h - this.field_148153_b + (int)this.field_148169_q - 4);
            }
         }

         if (Mouse.isButtonDown(0) && this.func_148125_i()) {
            if (this.field_148157_o == -1) {
               boolean flag1 = true;
               if (this.field_148162_h >= this.field_148153_b && this.field_148162_h <= this.field_148154_c) {
                  int j2 = (this.field_148155_a - this.func_148139_c()) / 2;
                  int k2 = (this.field_148155_a + this.func_148139_c()) / 2;
                  int l2 = this.field_148162_h - this.field_148153_b - this.field_148160_j + (int)this.field_148169_q - 4;
                  int i1 = l2 / this.field_148149_f;
                  if (i1 < this.func_148127_b() && this.field_148150_g >= j2 && this.field_148150_g <= k2 && i1 >= 0 && l2 >= 0) {
                     boolean flag = i1 == this.field_148168_r && Minecraft.func_71386_F() - this.field_148167_s < 250L;
                     this.func_148144_a(i1, flag, this.field_148150_g, this.field_148162_h);
                     this.field_148168_r = i1;
                     this.field_148167_s = Minecraft.func_71386_F();
                  } else if (this.field_148150_g >= j2 && this.field_148150_g <= k2 && l2 < 0) {
                     this.func_148132_a(this.field_148150_g - j2, this.field_148162_h - this.field_148153_b + (int)this.field_148169_q - 4);
                     flag1 = false;
                  }

                  int i3 = this.func_148137_d();
                  int j1 = i3 + 6;
                  if (this.field_148150_g >= i3 && this.field_148150_g <= j1) {
                     this.field_148170_p = -1.0F;
                     int k1 = this.func_148135_f();
                     if (k1 < 1) {
                        k1 = 1;
                     }

                     int l1 = (int)((float)((this.field_148154_c - this.field_148153_b) * (this.field_148154_c - this.field_148153_b)) / (float)this.func_148138_e());
                     l1 = MathHelper.func_76125_a(l1, 32, this.field_148154_c - this.field_148153_b - 8);
                     this.field_148170_p /= (float)(this.field_148154_c - this.field_148153_b - l1) / (float)k1;
                  } else {
                     this.field_148170_p = 1.0F;
                  }

                  if (flag1) {
                     this.field_148157_o = this.field_148162_h;
                  } else {
                     this.field_148157_o = -2;
                  }
               } else {
                  this.field_148157_o = -2;
               }
            } else if (this.field_148157_o >= 0) {
               this.field_148169_q -= (float)(this.field_148162_h - this.field_148157_o) * this.field_148170_p;
               this.field_148157_o = this.field_148162_h;
            }
         } else {
            this.field_148157_o = -1;
         }

         int i2 = Mouse.getEventDWheel();
         if (i2 != 0) {
            if (i2 > 0) {
               i2 = -1;
            } else if (i2 < 0) {
               i2 = 1;
            }

            this.field_148169_q += (float)(i2 * this.field_148149_f / 2);
         }

      }
   }

   public void func_148143_b(boolean p_148143_1_) {
      this.field_148164_v = p_148143_1_;
   }

   public boolean func_148125_i() {
      return this.field_148164_v;
   }

   public int func_148139_c() {
      return 220;
   }

   protected void func_192638_a(int p_192638_1_, int p_192638_2_, int p_192638_3_, int p_192638_4_, float p_192638_5_) {
      int i = this.func_148127_b();
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();

      for(int j = 0; j < i; ++j) {
         int k = p_192638_2_ + j * this.field_148149_f + this.field_148160_j;
         int l = this.field_148149_f - 4;
         if (k > this.field_148154_c || k + l < this.field_148153_b) {
            this.func_192639_a(j, p_192638_1_, k, p_192638_5_);
         }

         if (this.field_148166_t && this.func_148131_a(j)) {
            int i1 = this.field_148152_e + (this.field_148155_a / 2 - this.func_148139_c() / 2);
            int j1 = this.field_148152_e + this.field_148155_a / 2 + this.func_148139_c() / 2;
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.func_179090_x();
            bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
            bufferbuilder.func_181662_b((double)i1, (double)(k + l + 2), 0.0D).func_187315_a(0.0D, 1.0D).func_181669_b(128, 128, 128, 255).func_181675_d();
            bufferbuilder.func_181662_b((double)j1, (double)(k + l + 2), 0.0D).func_187315_a(1.0D, 1.0D).func_181669_b(128, 128, 128, 255).func_181675_d();
            bufferbuilder.func_181662_b((double)j1, (double)(k - 2), 0.0D).func_187315_a(1.0D, 0.0D).func_181669_b(128, 128, 128, 255).func_181675_d();
            bufferbuilder.func_181662_b((double)i1, (double)(k - 2), 0.0D).func_187315_a(0.0D, 0.0D).func_181669_b(128, 128, 128, 255).func_181675_d();
            bufferbuilder.func_181662_b((double)(i1 + 1), (double)(k + l + 1), 0.0D).func_187315_a(0.0D, 1.0D).func_181669_b(0, 0, 0, 255).func_181675_d();
            bufferbuilder.func_181662_b((double)(j1 - 1), (double)(k + l + 1), 0.0D).func_187315_a(1.0D, 1.0D).func_181669_b(0, 0, 0, 255).func_181675_d();
            bufferbuilder.func_181662_b((double)(j1 - 1), (double)(k - 1), 0.0D).func_187315_a(1.0D, 0.0D).func_181669_b(0, 0, 0, 255).func_181675_d();
            bufferbuilder.func_181662_b((double)(i1 + 1), (double)(k - 1), 0.0D).func_187315_a(0.0D, 0.0D).func_181669_b(0, 0, 0, 255).func_181675_d();
            tessellator.func_78381_a();
            GlStateManager.func_179098_w();
         }

         this.func_192637_a(j, p_192638_1_, k, l, p_192638_3_, p_192638_4_, p_192638_5_);
      }

   }

   protected int func_148137_d() {
      return this.field_148155_a / 2 + 124;
   }

   protected void func_148136_c(int p_148136_1_, int p_148136_2_, int p_148136_3_, int p_148136_4_) {
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      this.field_148161_k.func_110434_K().func_110577_a(Gui.field_110325_k);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      float f = 32.0F;
      bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
      bufferbuilder.func_181662_b((double)this.field_148152_e, (double)p_148136_2_, 0.0D).func_187315_a(0.0D, (double)((float)p_148136_2_ / 32.0F)).func_181669_b(64, 64, 64, p_148136_4_).func_181675_d();
      bufferbuilder.func_181662_b((double)(this.field_148152_e + this.field_148155_a), (double)p_148136_2_, 0.0D).func_187315_a((double)((float)this.field_148155_a / 32.0F), (double)((float)p_148136_2_ / 32.0F)).func_181669_b(64, 64, 64, p_148136_4_).func_181675_d();
      bufferbuilder.func_181662_b((double)(this.field_148152_e + this.field_148155_a), (double)p_148136_1_, 0.0D).func_187315_a((double)((float)this.field_148155_a / 32.0F), (double)((float)p_148136_1_ / 32.0F)).func_181669_b(64, 64, 64, p_148136_3_).func_181675_d();
      bufferbuilder.func_181662_b((double)this.field_148152_e, (double)p_148136_1_, 0.0D).func_187315_a(0.0D, (double)((float)p_148136_1_ / 32.0F)).func_181669_b(64, 64, 64, p_148136_3_).func_181675_d();
      tessellator.func_78381_a();
   }

   public void func_148140_g(int p_148140_1_) {
      this.field_148152_e = p_148140_1_;
      this.field_148151_d = p_148140_1_ + this.field_148155_a;
   }

   public int func_148146_j() {
      return this.field_148149_f;
   }
}
