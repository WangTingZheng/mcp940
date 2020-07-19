package net.minecraft.client.gui;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerDetector;
import net.minecraft.client.network.LanServerInfo;
import net.minecraft.client.network.ServerPinger;
import net.minecraft.client.resources.I18n;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiMultiplayer extends GuiScreen {
   private static final Logger field_146802_a = LogManager.getLogger();
   private final ServerPinger field_146797_f = new ServerPinger();
   private final GuiScreen field_146798_g;
   private ServerSelectionList field_146803_h;
   private ServerList field_146804_i;
   private GuiButton field_146810_r;
   private GuiButton field_146809_s;
   private GuiButton field_146808_t;
   private boolean field_146807_u;
   private boolean field_146806_v;
   private boolean field_146805_w;
   private boolean field_146813_x;
   private String field_146812_y;
   private ServerData field_146811_z;
   private LanServerDetector.LanServerList field_146799_A;
   private LanServerDetector.ThreadLanServerFind field_146800_B;
   private boolean field_146801_C;

   public GuiMultiplayer(GuiScreen p_i1040_1_) {
      this.field_146798_g = p_i1040_1_;
   }

   public void func_73866_w_() {
      Keyboard.enableRepeatEvents(true);
      this.field_146292_n.clear();
      if (this.field_146801_C) {
         this.field_146803_h.func_148122_a(this.field_146294_l, this.field_146295_m, 32, this.field_146295_m - 64);
      } else {
         this.field_146801_C = true;
         this.field_146804_i = new ServerList(this.field_146297_k);
         this.field_146804_i.func_78853_a();
         this.field_146799_A = new LanServerDetector.LanServerList();

         try {
            this.field_146800_B = new LanServerDetector.ThreadLanServerFind(this.field_146799_A);
            this.field_146800_B.start();
         } catch (Exception exception) {
            field_146802_a.warn("Unable to start LAN server detection: {}", (Object)exception.getMessage());
         }

         this.field_146803_h = new ServerSelectionList(this, this.field_146297_k, this.field_146294_l, this.field_146295_m, 32, this.field_146295_m - 64, 36);
         this.field_146803_h.func_148195_a(this.field_146804_i);
      }

      this.func_146794_g();
   }

   public void func_146274_d() throws IOException {
      super.func_146274_d();
      this.field_146803_h.func_178039_p();
   }

   public void func_146794_g() {
      this.field_146810_r = this.func_189646_b(new GuiButton(7, this.field_146294_l / 2 - 154, this.field_146295_m - 28, 70, 20, I18n.func_135052_a("selectServer.edit")));
      this.field_146808_t = this.func_189646_b(new GuiButton(2, this.field_146294_l / 2 - 74, this.field_146295_m - 28, 70, 20, I18n.func_135052_a("selectServer.delete")));
      this.field_146809_s = this.func_189646_b(new GuiButton(1, this.field_146294_l / 2 - 154, this.field_146295_m - 52, 100, 20, I18n.func_135052_a("selectServer.select")));
      this.field_146292_n.add(new GuiButton(4, this.field_146294_l / 2 - 50, this.field_146295_m - 52, 100, 20, I18n.func_135052_a("selectServer.direct")));
      this.field_146292_n.add(new GuiButton(3, this.field_146294_l / 2 + 4 + 50, this.field_146295_m - 52, 100, 20, I18n.func_135052_a("selectServer.add")));
      this.field_146292_n.add(new GuiButton(8, this.field_146294_l / 2 + 4, this.field_146295_m - 28, 70, 20, I18n.func_135052_a("selectServer.refresh")));
      this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 + 4 + 76, this.field_146295_m - 28, 75, 20, I18n.func_135052_a("gui.cancel")));
      this.func_146790_a(this.field_146803_h.func_148193_k());
   }

   public void func_73876_c() {
      super.func_73876_c();
      if (this.field_146799_A.func_77553_a()) {
         List<LanServerInfo> list = this.field_146799_A.func_77554_c();
         this.field_146799_A.func_77552_b();
         this.field_146803_h.func_148194_a(list);
      }

      this.field_146797_f.func_147223_a();
   }

   public void func_146281_b() {
      Keyboard.enableRepeatEvents(false);
      if (this.field_146800_B != null) {
         this.field_146800_B.interrupt();
         this.field_146800_B = null;
      }

      this.field_146797_f.func_147226_b();
   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
      if (p_146284_1_.field_146124_l) {
         GuiListExtended.IGuiListEntry guilistextended$iguilistentry = this.field_146803_h.func_148193_k() < 0 ? null : this.field_146803_h.func_148180_b(this.field_146803_h.func_148193_k());
         if (p_146284_1_.field_146127_k == 2 && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
            String s4 = ((ServerListEntryNormal)guilistextended$iguilistentry).func_148296_a().field_78847_a;
            if (s4 != null) {
               this.field_146807_u = true;
               String s = I18n.func_135052_a("selectServer.deleteQuestion");
               String s1 = "'" + s4 + "' " + I18n.func_135052_a("selectServer.deleteWarning");
               String s2 = I18n.func_135052_a("selectServer.deleteButton");
               String s3 = I18n.func_135052_a("gui.cancel");
               GuiYesNo guiyesno = new GuiYesNo(this, s, s1, s2, s3, this.field_146803_h.func_148193_k());
               this.field_146297_k.func_147108_a(guiyesno);
            }
         } else if (p_146284_1_.field_146127_k == 1) {
            this.func_146796_h();
         } else if (p_146284_1_.field_146127_k == 4) {
            this.field_146813_x = true;
            this.field_146811_z = new ServerData(I18n.func_135052_a("selectServer.defaultName"), "", false);
            this.field_146297_k.func_147108_a(new GuiScreenServerList(this, this.field_146811_z));
         } else if (p_146284_1_.field_146127_k == 3) {
            this.field_146806_v = true;
            this.field_146811_z = new ServerData(I18n.func_135052_a("selectServer.defaultName"), "", false);
            this.field_146297_k.func_147108_a(new GuiScreenAddServer(this, this.field_146811_z));
         } else if (p_146284_1_.field_146127_k == 7 && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
            this.field_146805_w = true;
            ServerData serverdata = ((ServerListEntryNormal)guilistextended$iguilistentry).func_148296_a();
            this.field_146811_z = new ServerData(serverdata.field_78847_a, serverdata.field_78845_b, false);
            this.field_146811_z.func_152583_a(serverdata);
            this.field_146297_k.func_147108_a(new GuiScreenAddServer(this, this.field_146811_z));
         } else if (p_146284_1_.field_146127_k == 0) {
            this.field_146297_k.func_147108_a(this.field_146798_g);
         } else if (p_146284_1_.field_146127_k == 8) {
            this.func_146792_q();
         }

      }
   }

   private void func_146792_q() {
      this.field_146297_k.func_147108_a(new GuiMultiplayer(this.field_146798_g));
   }

   public void func_73878_a(boolean p_73878_1_, int p_73878_2_) {
      GuiListExtended.IGuiListEntry guilistextended$iguilistentry = this.field_146803_h.func_148193_k() < 0 ? null : this.field_146803_h.func_148180_b(this.field_146803_h.func_148193_k());
      if (this.field_146807_u) {
         this.field_146807_u = false;
         if (p_73878_1_ && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
            this.field_146804_i.func_78851_b(this.field_146803_h.func_148193_k());
            this.field_146804_i.func_78855_b();
            this.field_146803_h.func_148192_c(-1);
            this.field_146803_h.func_148195_a(this.field_146804_i);
         }

         this.field_146297_k.func_147108_a(this);
      } else if (this.field_146813_x) {
         this.field_146813_x = false;
         if (p_73878_1_) {
            this.func_146791_a(this.field_146811_z);
         } else {
            this.field_146297_k.func_147108_a(this);
         }
      } else if (this.field_146806_v) {
         this.field_146806_v = false;
         if (p_73878_1_) {
            this.field_146804_i.func_78849_a(this.field_146811_z);
            this.field_146804_i.func_78855_b();
            this.field_146803_h.func_148192_c(-1);
            this.field_146803_h.func_148195_a(this.field_146804_i);
         }

         this.field_146297_k.func_147108_a(this);
      } else if (this.field_146805_w) {
         this.field_146805_w = false;
         if (p_73878_1_ && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
            ServerData serverdata = ((ServerListEntryNormal)guilistextended$iguilistentry).func_148296_a();
            serverdata.field_78847_a = this.field_146811_z.field_78847_a;
            serverdata.field_78845_b = this.field_146811_z.field_78845_b;
            serverdata.func_152583_a(this.field_146811_z);
            this.field_146804_i.func_78855_b();
            this.field_146803_h.func_148195_a(this.field_146804_i);
         }

         this.field_146297_k.func_147108_a(this);
      }

   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) throws IOException {
      int i = this.field_146803_h.func_148193_k();
      GuiListExtended.IGuiListEntry guilistextended$iguilistentry = i < 0 ? null : this.field_146803_h.func_148180_b(i);
      if (p_73869_2_ == 63) {
         this.func_146792_q();
      } else {
         if (i >= 0) {
            if (p_73869_2_ == 200) {
               if (func_146272_n()) {
                  if (i > 0 && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
                     this.field_146804_i.func_78857_a(i, i - 1);
                     this.func_146790_a(this.field_146803_h.func_148193_k() - 1);
                     this.field_146803_h.func_148145_f(-this.field_146803_h.func_148146_j());
                     this.field_146803_h.func_148195_a(this.field_146804_i);
                  }
               } else if (i > 0) {
                  this.func_146790_a(this.field_146803_h.func_148193_k() - 1);
                  this.field_146803_h.func_148145_f(-this.field_146803_h.func_148146_j());
                  if (this.field_146803_h.func_148180_b(this.field_146803_h.func_148193_k()) instanceof ServerListEntryLanScan) {
                     if (this.field_146803_h.func_148193_k() > 0) {
                        this.func_146790_a(this.field_146803_h.func_148127_b() - 1);
                        this.field_146803_h.func_148145_f(-this.field_146803_h.func_148146_j());
                     } else {
                        this.func_146790_a(-1);
                     }
                  }
               } else {
                  this.func_146790_a(-1);
               }
            } else if (p_73869_2_ == 208) {
               if (func_146272_n()) {
                  if (i < this.field_146804_i.func_78856_c() - 1) {
                     this.field_146804_i.func_78857_a(i, i + 1);
                     this.func_146790_a(i + 1);
                     this.field_146803_h.func_148145_f(this.field_146803_h.func_148146_j());
                     this.field_146803_h.func_148195_a(this.field_146804_i);
                  }
               } else if (i < this.field_146803_h.func_148127_b()) {
                  this.func_146790_a(this.field_146803_h.func_148193_k() + 1);
                  this.field_146803_h.func_148145_f(this.field_146803_h.func_148146_j());
                  if (this.field_146803_h.func_148180_b(this.field_146803_h.func_148193_k()) instanceof ServerListEntryLanScan) {
                     if (this.field_146803_h.func_148193_k() < this.field_146803_h.func_148127_b() - 1) {
                        this.func_146790_a(this.field_146803_h.func_148127_b() + 1);
                        this.field_146803_h.func_148145_f(this.field_146803_h.func_148146_j());
                     } else {
                        this.func_146790_a(-1);
                     }
                  }
               } else {
                  this.func_146790_a(-1);
               }
            } else if (p_73869_2_ != 28 && p_73869_2_ != 156) {
               super.func_73869_a(p_73869_1_, p_73869_2_);
            } else {
               this.func_146284_a(this.field_146292_n.get(2));
            }
         } else {
            super.func_73869_a(p_73869_1_, p_73869_2_);
         }

      }
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.field_146812_y = null;
      this.func_146276_q_();
      this.field_146803_h.func_148128_a(p_73863_1_, p_73863_2_, p_73863_3_);
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("multiplayer.title"), this.field_146294_l / 2, 20, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
      if (this.field_146812_y != null) {
         this.func_146283_a(Lists.newArrayList(Splitter.on("\n").split(this.field_146812_y)), p_73863_1_, p_73863_2_);
      }

   }

   public void func_146796_h() {
      GuiListExtended.IGuiListEntry guilistextended$iguilistentry = this.field_146803_h.func_148193_k() < 0 ? null : this.field_146803_h.func_148180_b(this.field_146803_h.func_148193_k());
      if (guilistextended$iguilistentry instanceof ServerListEntryNormal) {
         this.func_146791_a(((ServerListEntryNormal)guilistextended$iguilistentry).func_148296_a());
      } else if (guilistextended$iguilistentry instanceof ServerListEntryLanDetected) {
         LanServerInfo lanserverinfo = ((ServerListEntryLanDetected)guilistextended$iguilistentry).func_189995_a();
         this.func_146791_a(new ServerData(lanserverinfo.func_77487_a(), lanserverinfo.func_77488_b(), true));
      }

   }

   private void func_146791_a(ServerData p_146791_1_) {
      this.field_146297_k.func_147108_a(new GuiConnecting(this, this.field_146297_k, p_146791_1_));
   }

   public void func_146790_a(int p_146790_1_) {
      this.field_146803_h.func_148192_c(p_146790_1_);
      GuiListExtended.IGuiListEntry guilistextended$iguilistentry = p_146790_1_ < 0 ? null : this.field_146803_h.func_148180_b(p_146790_1_);
      this.field_146809_s.field_146124_l = false;
      this.field_146810_r.field_146124_l = false;
      this.field_146808_t.field_146124_l = false;
      if (guilistextended$iguilistentry != null && !(guilistextended$iguilistentry instanceof ServerListEntryLanScan)) {
         this.field_146809_s.field_146124_l = true;
         if (guilistextended$iguilistentry instanceof ServerListEntryNormal) {
            this.field_146810_r.field_146124_l = true;
            this.field_146808_t.field_146124_l = true;
         }
      }

   }

   public ServerPinger func_146789_i() {
      return this.field_146797_f;
   }

   public void func_146793_a(String p_146793_1_) {
      this.field_146812_y = p_146793_1_;
   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) throws IOException {
      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
      this.field_146803_h.func_148179_a(p_73864_1_, p_73864_2_, p_73864_3_);
   }

   protected void func_146286_b(int p_146286_1_, int p_146286_2_, int p_146286_3_) {
      super.func_146286_b(p_146286_1_, p_146286_2_, p_146286_3_);
      this.field_146803_h.func_148181_b(p_146286_1_, p_146286_2_, p_146286_3_);
   }

   public ServerList func_146795_p() {
      return this.field_146804_i;
   }

   public boolean func_175392_a(ServerListEntryNormal p_175392_1_, int p_175392_2_) {
      return p_175392_2_ > 0;
   }

   public boolean func_175394_b(ServerListEntryNormal p_175394_1_, int p_175394_2_) {
      return p_175394_2_ < this.field_146804_i.func_78856_c() - 1;
   }

   public void func_175391_a(ServerListEntryNormal p_175391_1_, int p_175391_2_, boolean p_175391_3_) {
      int i = p_175391_3_ ? 0 : p_175391_2_ - 1;
      this.field_146804_i.func_78857_a(p_175391_2_, i);
      if (this.field_146803_h.func_148193_k() == p_175391_2_) {
         this.func_146790_a(i);
      }

      this.field_146803_h.func_148195_a(this.field_146804_i);
   }

   public void func_175393_b(ServerListEntryNormal p_175393_1_, int p_175393_2_, boolean p_175393_3_) {
      int i = p_175393_3_ ? this.field_146804_i.func_78856_c() - 1 : p_175393_2_ + 1;
      this.field_146804_i.func_78857_a(p_175393_2_, i);
      if (this.field_146803_h.func_148193_k() == p_175393_2_) {
         this.func_146790_a(i);
      }

      this.field_146803_h.func_148195_a(this.field_146804_i);
   }
}
