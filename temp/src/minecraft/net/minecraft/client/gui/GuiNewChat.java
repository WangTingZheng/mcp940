package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiNewChat extends Gui {
   private static final Logger field_146249_a = LogManager.getLogger();
   private final Minecraft field_146247_f;
   private final List<String> field_146248_g = Lists.<String>newArrayList();
   private final List<ChatLine> field_146252_h = Lists.<ChatLine>newArrayList();
   private final List<ChatLine> field_146253_i = Lists.<ChatLine>newArrayList();
   private int field_146250_j;
   private boolean field_146251_k;

   public GuiNewChat(Minecraft p_i1022_1_) {
      this.field_146247_f = p_i1022_1_;
   }

   public void func_146230_a(int p_146230_1_) {
      if (this.field_146247_f.field_71474_y.field_74343_n != EntityPlayer.EnumChatVisibility.HIDDEN) {
         int i = this.func_146232_i();
         int j = this.field_146253_i.size();
         float f = this.field_146247_f.field_71474_y.field_74357_r * 0.9F + 0.1F;
         if (j > 0) {
            boolean flag = false;
            if (this.func_146241_e()) {
               flag = true;
            }

            float f1 = this.func_146244_h();
            int k = MathHelper.func_76123_f((float)this.func_146228_f() / f1);
            GlStateManager.func_179094_E();
            GlStateManager.func_179109_b(2.0F, 8.0F, 0.0F);
            GlStateManager.func_179152_a(f1, f1, 1.0F);
            int l = 0;

            for(int i1 = 0; i1 + this.field_146250_j < this.field_146253_i.size() && i1 < i; ++i1) {
               ChatLine chatline = this.field_146253_i.get(i1 + this.field_146250_j);
               if (chatline != null) {
                  int j1 = p_146230_1_ - chatline.func_74540_b();
                  if (j1 < 200 || flag) {
                     double d0 = (double)j1 / 200.0D;
                     d0 = 1.0D - d0;
                     d0 = d0 * 10.0D;
                     d0 = MathHelper.func_151237_a(d0, 0.0D, 1.0D);
                     d0 = d0 * d0;
                     int l1 = (int)(255.0D * d0);
                     if (flag) {
                        l1 = 255;
                     }

                     l1 = (int)((float)l1 * f);
                     ++l;
                     if (l1 > 3) {
                        int i2 = 0;
                        int j2 = -i1 * 9;
                        func_73734_a(-2, j2 - 9, 0 + k + 4, j2, l1 / 2 << 24);
                        String s = chatline.func_151461_a().func_150254_d();
                        GlStateManager.func_179147_l();
                        this.field_146247_f.field_71466_p.func_175063_a(s, 0.0F, (float)(j2 - 8), 16777215 + (l1 << 24));
                        GlStateManager.func_179118_c();
                        GlStateManager.func_179084_k();
                     }
                  }
               }
            }

            if (flag) {
               int k2 = this.field_146247_f.field_71466_p.field_78288_b;
               GlStateManager.func_179109_b(-3.0F, 0.0F, 0.0F);
               int l2 = j * k2 + j;
               int i3 = l * k2 + l;
               int j3 = this.field_146250_j * i3 / j;
               int k1 = i3 * i3 / l2;
               if (l2 != i3) {
                  int k3 = j3 > 0 ? 170 : 96;
                  int l3 = this.field_146251_k ? 13382451 : 3355562;
                  func_73734_a(0, -j3, 2, -j3 - k1, l3 + (k3 << 24));
                  func_73734_a(2, -j3, 1, -j3 - k1, 13421772 + (k3 << 24));
               }
            }

            GlStateManager.func_179121_F();
         }
      }
   }

   public void func_146231_a(boolean p_146231_1_) {
      this.field_146253_i.clear();
      this.field_146252_h.clear();
      if (p_146231_1_) {
         this.field_146248_g.clear();
      }

   }

   public void func_146227_a(ITextComponent p_146227_1_) {
      this.func_146234_a(p_146227_1_, 0);
   }

   public void func_146234_a(ITextComponent p_146234_1_, int p_146234_2_) {
      this.func_146237_a(p_146234_1_, p_146234_2_, this.field_146247_f.field_71456_v.func_73834_c(), false);
      field_146249_a.info("[CHAT] {}", (Object)p_146234_1_.func_150260_c().replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n"));
   }

   private void func_146237_a(ITextComponent p_146237_1_, int p_146237_2_, int p_146237_3_, boolean p_146237_4_) {
      if (p_146237_2_ != 0) {
         this.func_146242_c(p_146237_2_);
      }

      int i = MathHelper.func_76141_d((float)this.func_146228_f() / this.func_146244_h());
      List<ITextComponent> list = GuiUtilRenderComponents.func_178908_a(p_146237_1_, i, this.field_146247_f.field_71466_p, false, false);
      boolean flag = this.func_146241_e();

      for(ITextComponent itextcomponent : list) {
         if (flag && this.field_146250_j > 0) {
            this.field_146251_k = true;
            this.func_146229_b(1);
         }

         this.field_146253_i.add(0, new ChatLine(p_146237_3_, itextcomponent, p_146237_2_));
      }

      while(this.field_146253_i.size() > 100) {
         this.field_146253_i.remove(this.field_146253_i.size() - 1);
      }

      if (!p_146237_4_) {
         this.field_146252_h.add(0, new ChatLine(p_146237_3_, p_146237_1_, p_146237_2_));

         while(this.field_146252_h.size() > 100) {
            this.field_146252_h.remove(this.field_146252_h.size() - 1);
         }
      }

   }

   public void func_146245_b() {
      this.field_146253_i.clear();
      this.func_146240_d();

      for(int i = this.field_146252_h.size() - 1; i >= 0; --i) {
         ChatLine chatline = this.field_146252_h.get(i);
         this.func_146237_a(chatline.func_151461_a(), chatline.func_74539_c(), chatline.func_74540_b(), true);
      }

   }

   public List<String> func_146238_c() {
      return this.field_146248_g;
   }

   public void func_146239_a(String p_146239_1_) {
      if (this.field_146248_g.isEmpty() || !((String)this.field_146248_g.get(this.field_146248_g.size() - 1)).equals(p_146239_1_)) {
         this.field_146248_g.add(p_146239_1_);
      }

   }

   public void func_146240_d() {
      this.field_146250_j = 0;
      this.field_146251_k = false;
   }

   public void func_146229_b(int p_146229_1_) {
      this.field_146250_j += p_146229_1_;
      int i = this.field_146253_i.size();
      if (this.field_146250_j > i - this.func_146232_i()) {
         this.field_146250_j = i - this.func_146232_i();
      }

      if (this.field_146250_j <= 0) {
         this.field_146250_j = 0;
         this.field_146251_k = false;
      }

   }

   @Nullable
   public ITextComponent func_146236_a(int p_146236_1_, int p_146236_2_) {
      if (!this.func_146241_e()) {
         return null;
      } else {
         ScaledResolution scaledresolution = new ScaledResolution(this.field_146247_f);
         int i = scaledresolution.func_78325_e();
         float f = this.func_146244_h();
         int j = p_146236_1_ / i - 2;
         int k = p_146236_2_ / i - 40;
         j = MathHelper.func_76141_d((float)j / f);
         k = MathHelper.func_76141_d((float)k / f);
         if (j >= 0 && k >= 0) {
            int l = Math.min(this.func_146232_i(), this.field_146253_i.size());
            if (j <= MathHelper.func_76141_d((float)this.func_146228_f() / this.func_146244_h()) && k < this.field_146247_f.field_71466_p.field_78288_b * l + l) {
               int i1 = k / this.field_146247_f.field_71466_p.field_78288_b + this.field_146250_j;
               if (i1 >= 0 && i1 < this.field_146253_i.size()) {
                  ChatLine chatline = this.field_146253_i.get(i1);
                  int j1 = 0;

                  for(ITextComponent itextcomponent : chatline.func_151461_a()) {
                     if (itextcomponent instanceof TextComponentString) {
                        j1 += this.field_146247_f.field_71466_p.func_78256_a(GuiUtilRenderComponents.func_178909_a(((TextComponentString)itextcomponent).func_150265_g(), false));
                        if (j1 > j) {
                           return itextcomponent;
                        }
                     }
                  }
               }

               return null;
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   public boolean func_146241_e() {
      return this.field_146247_f.field_71462_r instanceof GuiChat;
   }

   public void func_146242_c(int p_146242_1_) {
      Iterator<ChatLine> iterator = this.field_146253_i.iterator();

      while(iterator.hasNext()) {
         ChatLine chatline = iterator.next();
         if (chatline.func_74539_c() == p_146242_1_) {
            iterator.remove();
         }
      }

      iterator = this.field_146252_h.iterator();

      while(iterator.hasNext()) {
         ChatLine chatline1 = iterator.next();
         if (chatline1.func_74539_c() == p_146242_1_) {
            iterator.remove();
            break;
         }
      }

   }

   public int func_146228_f() {
      return func_146233_a(this.field_146247_f.field_71474_y.field_96692_F);
   }

   public int func_146246_g() {
      return func_146243_b(this.func_146241_e() ? this.field_146247_f.field_71474_y.field_96694_H : this.field_146247_f.field_71474_y.field_96693_G);
   }

   public float func_146244_h() {
      return this.field_146247_f.field_71474_y.field_96691_E;
   }

   public static int func_146233_a(float p_146233_0_) {
      int i = 320;
      int j = 40;
      return MathHelper.func_76141_d(p_146233_0_ * 280.0F + 40.0F);
   }

   public static int func_146243_b(float p_146243_0_) {
      int i = 180;
      int j = 20;
      return MathHelper.func_76141_d(p_146243_0_ * 160.0F + 20.0F);
   }

   public int func_146232_i() {
      return this.func_146246_g() / 9;
   }
}
