package net.minecraft.client.gui.advancements;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ClientAdvancementManager;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.CPacketSeenAdvancements;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class GuiScreenAdvancements extends GuiScreen implements ClientAdvancementManager.IListener {
   private static final ResourceLocation field_191943_f = new ResourceLocation("textures/gui/advancements/window.png");
   private static final ResourceLocation field_191945_g = new ResourceLocation("textures/gui/advancements/tabs.png");
   private final ClientAdvancementManager field_191946_h;
   private final Map<Advancement, GuiAdvancementTab> field_191947_i = Maps.<Advancement, GuiAdvancementTab>newLinkedHashMap();
   private GuiAdvancementTab field_191940_s;
   private int field_191941_t;
   private int field_191942_u;
   private boolean field_191944_v;

   public GuiScreenAdvancements(ClientAdvancementManager p_i47383_1_) {
      this.field_191946_h = p_i47383_1_;
   }

   public void func_73866_w_() {
      this.field_191947_i.clear();
      this.field_191940_s = null;
      this.field_191946_h.func_192798_a(this);
      if (this.field_191940_s == null && !this.field_191947_i.isEmpty()) {
         this.field_191946_h.func_194230_a(((GuiAdvancementTab)this.field_191947_i.values().iterator().next()).func_193935_c(), true);
      } else {
         this.field_191946_h.func_194230_a(this.field_191940_s == null ? null : this.field_191940_s.func_193935_c(), true);
      }

   }

   public void func_146281_b() {
      this.field_191946_h.func_192798_a((ClientAdvancementManager.IListener)null);
      NetHandlerPlayClient nethandlerplayclient = this.field_146297_k.func_147114_u();
      if (nethandlerplayclient != null) {
         nethandlerplayclient.func_147297_a(CPacketSeenAdvancements.func_194164_a());
      }

   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) throws IOException {
      if (p_73864_3_ == 0) {
         int i = (this.field_146294_l - 252) / 2;
         int j = (this.field_146295_m - 140) / 2;

         for(GuiAdvancementTab guiadvancementtab : this.field_191947_i.values()) {
            if (guiadvancementtab.func_191793_c(i, j, p_73864_1_, p_73864_2_)) {
               this.field_191946_h.func_194230_a(guiadvancementtab.func_193935_c(), true);
               break;
            }
         }
      }

      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) throws IOException {
      if (p_73869_2_ == this.field_146297_k.field_71474_y.field_194146_ao.func_151463_i()) {
         this.field_146297_k.func_147108_a((GuiScreen)null);
         this.field_146297_k.func_71381_h();
      } else {
         super.func_73869_a(p_73869_1_, p_73869_2_);
      }

   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      int i = (this.field_146294_l - 252) / 2;
      int j = (this.field_146295_m - 140) / 2;
      if (Mouse.isButtonDown(0)) {
         if (!this.field_191944_v) {
            this.field_191944_v = true;
         } else if (this.field_191940_s != null) {
            this.field_191940_s.func_191797_b(p_73863_1_ - this.field_191941_t, p_73863_2_ - this.field_191942_u);
         }

         this.field_191941_t = p_73863_1_;
         this.field_191942_u = p_73863_2_;
      } else {
         this.field_191944_v = false;
      }

      this.func_146276_q_();
      this.func_191936_c(p_73863_1_, p_73863_2_, i, j);
      this.func_191934_b(i, j);
      this.func_191937_d(p_73863_1_, p_73863_2_, i, j);
   }

   private void func_191936_c(int p_191936_1_, int p_191936_2_, int p_191936_3_, int p_191936_4_) {
      GuiAdvancementTab guiadvancementtab = this.field_191940_s;
      if (guiadvancementtab == null) {
         func_73734_a(p_191936_3_ + 9, p_191936_4_ + 18, p_191936_3_ + 9 + 234, p_191936_4_ + 18 + 113, -16777216);
         String s = I18n.func_135052_a("advancements.empty");
         int i = this.field_146289_q.func_78256_a(s);
         this.field_146289_q.func_78276_b(s, p_191936_3_ + 9 + 117 - i / 2, p_191936_4_ + 18 + 56 - this.field_146289_q.field_78288_b / 2, -1);
         this.field_146289_q.func_78276_b(":(", p_191936_3_ + 9 + 117 - this.field_146289_q.func_78256_a(":(") / 2, p_191936_4_ + 18 + 113 - this.field_146289_q.field_78288_b, -1);
      } else {
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b((float)(p_191936_3_ + 9), (float)(p_191936_4_ + 18), -400.0F);
         GlStateManager.func_179126_j();
         guiadvancementtab.func_191799_a();
         GlStateManager.func_179121_F();
         GlStateManager.func_179143_c(515);
         GlStateManager.func_179097_i();
      }
   }

   public void func_191934_b(int p_191934_1_, int p_191934_2_) {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179147_l();
      RenderHelper.func_74518_a();
      this.field_146297_k.func_110434_K().func_110577_a(field_191943_f);
      this.func_73729_b(p_191934_1_, p_191934_2_, 0, 0, 252, 140);
      if (this.field_191947_i.size() > 1) {
         this.field_146297_k.func_110434_K().func_110577_a(field_191945_g);

         for(GuiAdvancementTab guiadvancementtab : this.field_191947_i.values()) {
            guiadvancementtab.func_191798_a(p_191934_1_, p_191934_2_, guiadvancementtab == this.field_191940_s);
         }

         GlStateManager.func_179091_B();
         GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
         RenderHelper.func_74520_c();

         for(GuiAdvancementTab guiadvancementtab1 : this.field_191947_i.values()) {
            guiadvancementtab1.func_191796_a(p_191934_1_, p_191934_2_, this.field_146296_j);
         }

         GlStateManager.func_179084_k();
      }

      this.field_146289_q.func_78276_b(I18n.func_135052_a("gui.advancements"), p_191934_1_ + 8, p_191934_2_ + 6, 4210752);
   }

   private void func_191937_d(int p_191937_1_, int p_191937_2_, int p_191937_3_, int p_191937_4_) {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.field_191940_s != null) {
         GlStateManager.func_179094_E();
         GlStateManager.func_179126_j();
         GlStateManager.func_179109_b((float)(p_191937_3_ + 9), (float)(p_191937_4_ + 18), 400.0F);
         this.field_191940_s.func_192991_a(p_191937_1_ - p_191937_3_ - 9, p_191937_2_ - p_191937_4_ - 18, p_191937_3_, p_191937_4_);
         GlStateManager.func_179097_i();
         GlStateManager.func_179121_F();
      }

      if (this.field_191947_i.size() > 1) {
         for(GuiAdvancementTab guiadvancementtab : this.field_191947_i.values()) {
            if (guiadvancementtab.func_191793_c(p_191937_3_, p_191937_4_, p_191937_1_, p_191937_2_)) {
               this.func_146279_a(guiadvancementtab.func_191795_d(), p_191937_1_, p_191937_2_);
            }
         }
      }

   }

   public void func_191931_a(Advancement p_191931_1_) {
      GuiAdvancementTab guiadvancementtab = GuiAdvancementTab.func_193936_a(this.field_146297_k, this, this.field_191947_i.size(), p_191931_1_);
      if (guiadvancementtab != null) {
         this.field_191947_i.put(p_191931_1_, guiadvancementtab);
      }
   }

   public void func_191928_b(Advancement p_191928_1_) {
   }

   public void func_191932_c(Advancement p_191932_1_) {
      GuiAdvancementTab guiadvancementtab = this.func_191935_f(p_191932_1_);
      if (guiadvancementtab != null) {
         guiadvancementtab.func_191800_a(p_191932_1_);
      }

   }

   public void func_191929_d(Advancement p_191929_1_) {
   }

   public void func_191933_a(Advancement p_191933_1_, AdvancementProgress p_191933_2_) {
      GuiAdvancement guiadvancement = this.func_191938_e(p_191933_1_);
      if (guiadvancement != null) {
         guiadvancement.func_191824_a(p_191933_2_);
      }

   }

   public void func_193982_e(@Nullable Advancement p_193982_1_) {
      this.field_191940_s = this.field_191947_i.get(p_193982_1_);
   }

   public void func_191930_a() {
      this.field_191947_i.clear();
      this.field_191940_s = null;
   }

   @Nullable
   public GuiAdvancement func_191938_e(Advancement p_191938_1_) {
      GuiAdvancementTab guiadvancementtab = this.func_191935_f(p_191938_1_);
      return guiadvancementtab == null ? null : guiadvancementtab.func_191794_b(p_191938_1_);
   }

   @Nullable
   private GuiAdvancementTab func_191935_f(Advancement p_191935_1_) {
      while(p_191935_1_.func_192070_b() != null) {
         p_191935_1_ = p_191935_1_.func_192070_b();
      }

      return this.field_191947_i.get(p_191935_1_);
   }
}
