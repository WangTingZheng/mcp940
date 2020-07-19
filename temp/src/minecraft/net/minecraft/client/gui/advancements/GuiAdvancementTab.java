package net.minecraft.client.gui.advancements;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiAdvancementTab extends Gui {
   private final Minecraft field_191802_a;
   private final GuiScreenAdvancements field_193938_f;
   private final AdvancementTabType field_191803_f;
   private final int field_191804_g;
   private final Advancement field_191805_h;
   private final DisplayInfo field_191806_i;
   private final ItemStack field_191807_j;
   private final String field_191808_k;
   private final GuiAdvancement field_191809_l;
   private final Map<Advancement, GuiAdvancement> field_191810_m = Maps.<Advancement, GuiAdvancement>newLinkedHashMap();
   private int field_191811_n;
   private int field_191812_o;
   private int field_193939_q = Integer.MAX_VALUE;
   private int field_193940_r = Integer.MAX_VALUE;
   private int field_191813_p = Integer.MIN_VALUE;
   private int field_191814_q = Integer.MIN_VALUE;
   private float field_191815_r;
   private boolean field_192992_s;

   public GuiAdvancementTab(Minecraft p_i47589_1_, GuiScreenAdvancements p_i47589_2_, AdvancementTabType p_i47589_3_, int p_i47589_4_, Advancement p_i47589_5_, DisplayInfo p_i47589_6_) {
      this.field_191802_a = p_i47589_1_;
      this.field_193938_f = p_i47589_2_;
      this.field_191803_f = p_i47589_3_;
      this.field_191804_g = p_i47589_4_;
      this.field_191805_h = p_i47589_5_;
      this.field_191806_i = p_i47589_6_;
      this.field_191807_j = p_i47589_6_.func_192298_b();
      this.field_191808_k = p_i47589_6_.func_192297_a().func_150254_d();
      this.field_191809_l = new GuiAdvancement(this, p_i47589_1_, p_i47589_5_, p_i47589_6_);
      this.func_193937_a(this.field_191809_l, p_i47589_5_);
   }

   public Advancement func_193935_c() {
      return this.field_191805_h;
   }

   public String func_191795_d() {
      return this.field_191808_k;
   }

   public void func_191798_a(int p_191798_1_, int p_191798_2_, boolean p_191798_3_) {
      this.field_191803_f.func_192651_a(this, p_191798_1_, p_191798_2_, p_191798_3_, this.field_191804_g);
   }

   public void func_191796_a(int p_191796_1_, int p_191796_2_, RenderItem p_191796_3_) {
      this.field_191803_f.func_192652_a(p_191796_1_, p_191796_2_, this.field_191804_g, p_191796_3_, this.field_191807_j);
   }

   public void func_191799_a() {
      if (!this.field_192992_s) {
         this.field_191811_n = 117 - (this.field_191813_p + this.field_193939_q) / 2;
         this.field_191812_o = 56 - (this.field_191814_q + this.field_193940_r) / 2;
         this.field_192992_s = true;
      }

      GlStateManager.func_179143_c(518);
      func_73734_a(0, 0, 234, 113, -16777216);
      GlStateManager.func_179143_c(515);
      ResourceLocation resourcelocation = this.field_191806_i.func_192293_c();
      if (resourcelocation != null) {
         this.field_191802_a.func_110434_K().func_110577_a(resourcelocation);
      } else {
         this.field_191802_a.func_110434_K().func_110577_a(TextureManager.field_194008_a);
      }

      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      int i = this.field_191811_n % 16;
      int j = this.field_191812_o % 16;

      for(int k = -1; k <= 15; ++k) {
         for(int l = -1; l <= 8; ++l) {
            func_146110_a(i + 16 * k, j + 16 * l, 0.0F, 0.0F, 16, 16, 16.0F, 16.0F);
         }
      }

      this.field_191809_l.func_191819_a(this.field_191811_n, this.field_191812_o, true);
      this.field_191809_l.func_191819_a(this.field_191811_n, this.field_191812_o, false);
      this.field_191809_l.func_191817_b(this.field_191811_n, this.field_191812_o);
   }

   public void func_192991_a(int p_192991_1_, int p_192991_2_, int p_192991_3_, int p_192991_4_) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b(0.0F, 0.0F, 200.0F);
      func_73734_a(0, 0, 234, 113, MathHelper.func_76141_d(this.field_191815_r * 255.0F) << 24);
      boolean flag = false;
      if (p_192991_1_ > 0 && p_192991_1_ < 234 && p_192991_2_ > 0 && p_192991_2_ < 113) {
         for(GuiAdvancement guiadvancement : this.field_191810_m.values()) {
            if (guiadvancement.func_191816_c(this.field_191811_n, this.field_191812_o, p_192991_1_, p_192991_2_)) {
               flag = true;
               guiadvancement.func_191821_a(this.field_191811_n, this.field_191812_o, this.field_191815_r, p_192991_3_, p_192991_4_);
               break;
            }
         }
      }

      GlStateManager.func_179121_F();
      if (flag) {
         this.field_191815_r = MathHelper.func_76131_a(this.field_191815_r + 0.02F, 0.0F, 0.3F);
      } else {
         this.field_191815_r = MathHelper.func_76131_a(this.field_191815_r - 0.04F, 0.0F, 1.0F);
      }

   }

   public boolean func_191793_c(int p_191793_1_, int p_191793_2_, int p_191793_3_, int p_191793_4_) {
      return this.field_191803_f.func_192654_a(p_191793_1_, p_191793_2_, this.field_191804_g, p_191793_3_, p_191793_4_);
   }

   @Nullable
   public static GuiAdvancementTab func_193936_a(Minecraft p_193936_0_, GuiScreenAdvancements p_193936_1_, int p_193936_2_, Advancement p_193936_3_) {
      if (p_193936_3_.func_192068_c() == null) {
         return null;
      } else {
         for(AdvancementTabType advancementtabtype : AdvancementTabType.values()) {
            if (p_193936_2_ < advancementtabtype.func_192650_a()) {
               return new GuiAdvancementTab(p_193936_0_, p_193936_1_, advancementtabtype, p_193936_2_, p_193936_3_, p_193936_3_.func_192068_c());
            }

            p_193936_2_ -= advancementtabtype.func_192650_a();
         }

         return null;
      }
   }

   public void func_191797_b(int p_191797_1_, int p_191797_2_) {
      if (this.field_191813_p - this.field_193939_q > 234) {
         this.field_191811_n = MathHelper.func_76125_a(this.field_191811_n + p_191797_1_, -(this.field_191813_p - 234), 0);
      }

      if (this.field_191814_q - this.field_193940_r > 113) {
         this.field_191812_o = MathHelper.func_76125_a(this.field_191812_o + p_191797_2_, -(this.field_191814_q - 113), 0);
      }

   }

   public void func_191800_a(Advancement p_191800_1_) {
      if (p_191800_1_.func_192068_c() != null) {
         GuiAdvancement guiadvancement = new GuiAdvancement(this, this.field_191802_a, p_191800_1_, p_191800_1_.func_192068_c());
         this.func_193937_a(guiadvancement, p_191800_1_);
      }
   }

   private void func_193937_a(GuiAdvancement p_193937_1_, Advancement p_193937_2_) {
      this.field_191810_m.put(p_193937_2_, p_193937_1_);
      int i = p_193937_1_.func_191823_d();
      int j = i + 28;
      int k = p_193937_1_.func_191820_c();
      int l = k + 27;
      this.field_193939_q = Math.min(this.field_193939_q, i);
      this.field_191813_p = Math.max(this.field_191813_p, j);
      this.field_193940_r = Math.min(this.field_193940_r, k);
      this.field_191814_q = Math.max(this.field_191814_q, l);

      for(GuiAdvancement guiadvancement : this.field_191810_m.values()) {
         guiadvancement.func_191825_b();
      }

   }

   @Nullable
   public GuiAdvancement func_191794_b(Advancement p_191794_1_) {
      return this.field_191810_m.get(p_191794_1_);
   }

   public GuiScreenAdvancements func_193934_g() {
      return this.field_193938_f;
   }
}
