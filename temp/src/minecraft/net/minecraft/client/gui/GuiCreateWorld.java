package net.minecraft.client.gui;

import java.io.IOException;
import java.util.Random;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

public class GuiCreateWorld extends GuiScreen {
   private final GuiScreen field_146332_f;
   private GuiTextField field_146333_g;
   private GuiTextField field_146335_h;
   private String field_146336_i;
   private String field_146342_r = "survival";
   private String field_175300_s;
   private boolean field_146341_s = true;
   private boolean field_146340_t;
   private boolean field_146339_u;
   private boolean field_146338_v;
   private boolean field_146337_w;
   private boolean field_146345_x;
   private boolean field_146344_y;
   private GuiButton field_146343_z;
   private GuiButton field_146324_A;
   private GuiButton field_146325_B;
   private GuiButton field_146326_C;
   private GuiButton field_146320_D;
   private GuiButton field_146321_E;
   private GuiButton field_146322_F;
   private String field_146323_G;
   private String field_146328_H;
   private String field_146329_I;
   private String field_146330_J;
   private int field_146331_K;
   public String field_146334_a = "";
   private static final String[] field_146327_L = new String[]{"CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"};

   public GuiCreateWorld(GuiScreen p_i46320_1_) {
      this.field_146332_f = p_i46320_1_;
      this.field_146329_I = "";
      this.field_146330_J = I18n.func_135052_a("selectWorld.newWorld");
   }

   public void func_73876_c() {
      this.field_146333_g.func_146178_a();
      this.field_146335_h.func_146178_a();
   }

   public void func_73866_w_() {
      Keyboard.enableRepeatEvents(true);
      this.field_146292_n.clear();
      this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 155, this.field_146295_m - 28, 150, 20, I18n.func_135052_a("selectWorld.create")));
      this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 + 5, this.field_146295_m - 28, 150, 20, I18n.func_135052_a("gui.cancel")));
      this.field_146343_z = this.func_189646_b(new GuiButton(2, this.field_146294_l / 2 - 75, 115, 150, 20, I18n.func_135052_a("selectWorld.gameMode")));
      this.field_146324_A = this.func_189646_b(new GuiButton(3, this.field_146294_l / 2 - 75, 187, 150, 20, I18n.func_135052_a("selectWorld.moreWorldOptions")));
      this.field_146325_B = this.func_189646_b(new GuiButton(4, this.field_146294_l / 2 - 155, 100, 150, 20, I18n.func_135052_a("selectWorld.mapFeatures")));
      this.field_146325_B.field_146125_m = false;
      this.field_146326_C = this.func_189646_b(new GuiButton(7, this.field_146294_l / 2 + 5, 151, 150, 20, I18n.func_135052_a("selectWorld.bonusItems")));
      this.field_146326_C.field_146125_m = false;
      this.field_146320_D = this.func_189646_b(new GuiButton(5, this.field_146294_l / 2 + 5, 100, 150, 20, I18n.func_135052_a("selectWorld.mapType")));
      this.field_146320_D.field_146125_m = false;
      this.field_146321_E = this.func_189646_b(new GuiButton(6, this.field_146294_l / 2 - 155, 151, 150, 20, I18n.func_135052_a("selectWorld.allowCommands")));
      this.field_146321_E.field_146125_m = false;
      this.field_146322_F = this.func_189646_b(new GuiButton(8, this.field_146294_l / 2 + 5, 120, 150, 20, I18n.func_135052_a("selectWorld.customizeType")));
      this.field_146322_F.field_146125_m = false;
      this.field_146333_g = new GuiTextField(9, this.field_146289_q, this.field_146294_l / 2 - 100, 60, 200, 20);
      this.field_146333_g.func_146195_b(true);
      this.field_146333_g.func_146180_a(this.field_146330_J);
      this.field_146335_h = new GuiTextField(10, this.field_146289_q, this.field_146294_l / 2 - 100, 60, 200, 20);
      this.field_146335_h.func_146180_a(this.field_146329_I);
      this.func_146316_a(this.field_146344_y);
      this.func_146314_g();
      this.func_146319_h();
   }

   private void func_146314_g() {
      this.field_146336_i = this.field_146333_g.func_146179_b().trim();

      for(char c0 : ChatAllowedCharacters.field_71567_b) {
         this.field_146336_i = this.field_146336_i.replace(c0, '_');
      }

      if (StringUtils.isEmpty(this.field_146336_i)) {
         this.field_146336_i = "World";
      }

      this.field_146336_i = func_146317_a(this.field_146297_k.func_71359_d(), this.field_146336_i);
   }

   private void func_146319_h() {
      this.field_146343_z.field_146126_j = I18n.func_135052_a("selectWorld.gameMode") + ": " + I18n.func_135052_a("selectWorld.gameMode." + this.field_146342_r);
      this.field_146323_G = I18n.func_135052_a("selectWorld.gameMode." + this.field_146342_r + ".line1");
      this.field_146328_H = I18n.func_135052_a("selectWorld.gameMode." + this.field_146342_r + ".line2");
      this.field_146325_B.field_146126_j = I18n.func_135052_a("selectWorld.mapFeatures") + " ";
      if (this.field_146341_s) {
         this.field_146325_B.field_146126_j = this.field_146325_B.field_146126_j + I18n.func_135052_a("options.on");
      } else {
         this.field_146325_B.field_146126_j = this.field_146325_B.field_146126_j + I18n.func_135052_a("options.off");
      }

      this.field_146326_C.field_146126_j = I18n.func_135052_a("selectWorld.bonusItems") + " ";
      if (this.field_146338_v && !this.field_146337_w) {
         this.field_146326_C.field_146126_j = this.field_146326_C.field_146126_j + I18n.func_135052_a("options.on");
      } else {
         this.field_146326_C.field_146126_j = this.field_146326_C.field_146126_j + I18n.func_135052_a("options.off");
      }

      this.field_146320_D.field_146126_j = I18n.func_135052_a("selectWorld.mapType") + " " + I18n.func_135052_a(WorldType.field_77139_a[this.field_146331_K].func_77128_b());
      this.field_146321_E.field_146126_j = I18n.func_135052_a("selectWorld.allowCommands") + " ";
      if (this.field_146340_t && !this.field_146337_w) {
         this.field_146321_E.field_146126_j = this.field_146321_E.field_146126_j + I18n.func_135052_a("options.on");
      } else {
         this.field_146321_E.field_146126_j = this.field_146321_E.field_146126_j + I18n.func_135052_a("options.off");
      }

   }

   public static String func_146317_a(ISaveFormat p_146317_0_, String p_146317_1_) {
      p_146317_1_ = p_146317_1_.replaceAll("[\\./\"]", "_");

      for(String s : field_146327_L) {
         if (p_146317_1_.equalsIgnoreCase(s)) {
            p_146317_1_ = "_" + p_146317_1_ + "_";
         }
      }

      while(p_146317_0_.func_75803_c(p_146317_1_) != null) {
         p_146317_1_ = p_146317_1_ + "-";
      }

      return p_146317_1_;
   }

   public void func_146281_b() {
      Keyboard.enableRepeatEvents(false);
   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
      if (p_146284_1_.field_146124_l) {
         if (p_146284_1_.field_146127_k == 1) {
            this.field_146297_k.func_147108_a(this.field_146332_f);
         } else if (p_146284_1_.field_146127_k == 0) {
            this.field_146297_k.func_147108_a((GuiScreen)null);
            if (this.field_146345_x) {
               return;
            }

            this.field_146345_x = true;
            long i = (new Random()).nextLong();
            String s = this.field_146335_h.func_146179_b();
            if (!StringUtils.isEmpty(s)) {
               try {
                  long j = Long.parseLong(s);
                  if (j != 0L) {
                     i = j;
                  }
               } catch (NumberFormatException var7) {
                  i = (long)s.hashCode();
               }
            }

            WorldSettings worldsettings = new WorldSettings(i, GameType.func_77142_a(this.field_146342_r), this.field_146341_s, this.field_146337_w, WorldType.field_77139_a[this.field_146331_K]);
            worldsettings.func_82750_a(this.field_146334_a);
            if (this.field_146338_v && !this.field_146337_w) {
               worldsettings.func_77159_a();
            }

            if (this.field_146340_t && !this.field_146337_w) {
               worldsettings.func_77166_b();
            }

            this.field_146297_k.func_71371_a(this.field_146336_i, this.field_146333_g.func_146179_b().trim(), worldsettings);
         } else if (p_146284_1_.field_146127_k == 3) {
            this.func_146315_i();
         } else if (p_146284_1_.field_146127_k == 2) {
            if ("survival".equals(this.field_146342_r)) {
               if (!this.field_146339_u) {
                  this.field_146340_t = false;
               }

               this.field_146337_w = false;
               this.field_146342_r = "hardcore";
               this.field_146337_w = true;
               this.field_146321_E.field_146124_l = false;
               this.field_146326_C.field_146124_l = false;
               this.func_146319_h();
            } else if ("hardcore".equals(this.field_146342_r)) {
               if (!this.field_146339_u) {
                  this.field_146340_t = true;
               }

               this.field_146337_w = false;
               this.field_146342_r = "creative";
               this.func_146319_h();
               this.field_146337_w = false;
               this.field_146321_E.field_146124_l = true;
               this.field_146326_C.field_146124_l = true;
            } else {
               if (!this.field_146339_u) {
                  this.field_146340_t = false;
               }

               this.field_146342_r = "survival";
               this.func_146319_h();
               this.field_146321_E.field_146124_l = true;
               this.field_146326_C.field_146124_l = true;
               this.field_146337_w = false;
            }

            this.func_146319_h();
         } else if (p_146284_1_.field_146127_k == 4) {
            this.field_146341_s = !this.field_146341_s;
            this.func_146319_h();
         } else if (p_146284_1_.field_146127_k == 7) {
            this.field_146338_v = !this.field_146338_v;
            this.func_146319_h();
         } else if (p_146284_1_.field_146127_k == 5) {
            ++this.field_146331_K;
            if (this.field_146331_K >= WorldType.field_77139_a.length) {
               this.field_146331_K = 0;
            }

            while(!this.func_175299_g()) {
               ++this.field_146331_K;
               if (this.field_146331_K >= WorldType.field_77139_a.length) {
                  this.field_146331_K = 0;
               }
            }

            this.field_146334_a = "";
            this.func_146319_h();
            this.func_146316_a(this.field_146344_y);
         } else if (p_146284_1_.field_146127_k == 6) {
            this.field_146339_u = true;
            this.field_146340_t = !this.field_146340_t;
            this.func_146319_h();
         } else if (p_146284_1_.field_146127_k == 8) {
            if (WorldType.field_77139_a[this.field_146331_K] == WorldType.field_77138_c) {
               this.field_146297_k.func_147108_a(new GuiCreateFlatWorld(this, this.field_146334_a));
            } else {
               this.field_146297_k.func_147108_a(new GuiCustomizeWorldScreen(this, this.field_146334_a));
            }
         }

      }
   }

   private boolean func_175299_g() {
      WorldType worldtype = WorldType.field_77139_a[this.field_146331_K];
      if (worldtype != null && worldtype.func_77126_d()) {
         return worldtype == WorldType.field_180272_g ? func_146272_n() : true;
      } else {
         return false;
      }
   }

   private void func_146315_i() {
      this.func_146316_a(!this.field_146344_y);
   }

   private void func_146316_a(boolean p_146316_1_) {
      this.field_146344_y = p_146316_1_;
      if (WorldType.field_77139_a[this.field_146331_K] == WorldType.field_180272_g) {
         this.field_146343_z.field_146125_m = !this.field_146344_y;
         this.field_146343_z.field_146124_l = false;
         if (this.field_175300_s == null) {
            this.field_175300_s = this.field_146342_r;
         }

         this.field_146342_r = "spectator";
         this.field_146325_B.field_146125_m = false;
         this.field_146326_C.field_146125_m = false;
         this.field_146320_D.field_146125_m = this.field_146344_y;
         this.field_146321_E.field_146125_m = false;
         this.field_146322_F.field_146125_m = false;
      } else {
         this.field_146343_z.field_146125_m = !this.field_146344_y;
         this.field_146343_z.field_146124_l = true;
         if (this.field_175300_s != null) {
            this.field_146342_r = this.field_175300_s;
            this.field_175300_s = null;
         }

         this.field_146325_B.field_146125_m = this.field_146344_y && WorldType.field_77139_a[this.field_146331_K] != WorldType.field_180271_f;
         this.field_146326_C.field_146125_m = this.field_146344_y;
         this.field_146320_D.field_146125_m = this.field_146344_y;
         this.field_146321_E.field_146125_m = this.field_146344_y;
         this.field_146322_F.field_146125_m = this.field_146344_y && (WorldType.field_77139_a[this.field_146331_K] == WorldType.field_77138_c || WorldType.field_77139_a[this.field_146331_K] == WorldType.field_180271_f);
      }

      this.func_146319_h();
      if (this.field_146344_y) {
         this.field_146324_A.field_146126_j = I18n.func_135052_a("gui.done");
      } else {
         this.field_146324_A.field_146126_j = I18n.func_135052_a("selectWorld.moreWorldOptions");
      }

   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) throws IOException {
      if (this.field_146333_g.func_146206_l() && !this.field_146344_y) {
         this.field_146333_g.func_146201_a(p_73869_1_, p_73869_2_);
         this.field_146330_J = this.field_146333_g.func_146179_b();
      } else if (this.field_146335_h.func_146206_l() && this.field_146344_y) {
         this.field_146335_h.func_146201_a(p_73869_1_, p_73869_2_);
         this.field_146329_I = this.field_146335_h.func_146179_b();
      }

      if (p_73869_2_ == 28 || p_73869_2_ == 156) {
         this.func_146284_a(this.field_146292_n.get(0));
      }

      (this.field_146292_n.get(0)).field_146124_l = !this.field_146333_g.func_146179_b().isEmpty();
      this.func_146314_g();
   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) throws IOException {
      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
      if (this.field_146344_y) {
         this.field_146335_h.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
      } else {
         this.field_146333_g.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
      }

   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("selectWorld.create"), this.field_146294_l / 2, 20, -1);
      if (this.field_146344_y) {
         this.func_73731_b(this.field_146289_q, I18n.func_135052_a("selectWorld.enterSeed"), this.field_146294_l / 2 - 100, 47, -6250336);
         this.func_73731_b(this.field_146289_q, I18n.func_135052_a("selectWorld.seedInfo"), this.field_146294_l / 2 - 100, 85, -6250336);
         if (this.field_146325_B.field_146125_m) {
            this.func_73731_b(this.field_146289_q, I18n.func_135052_a("selectWorld.mapFeatures.info"), this.field_146294_l / 2 - 150, 122, -6250336);
         }

         if (this.field_146321_E.field_146125_m) {
            this.func_73731_b(this.field_146289_q, I18n.func_135052_a("selectWorld.allowCommands.info"), this.field_146294_l / 2 - 150, 172, -6250336);
         }

         this.field_146335_h.func_146194_f();
         if (WorldType.field_77139_a[this.field_146331_K].func_151357_h()) {
            this.field_146289_q.func_78279_b(I18n.func_135052_a(WorldType.field_77139_a[this.field_146331_K].func_151359_c()), this.field_146320_D.field_146128_h + 2, this.field_146320_D.field_146129_i + 22, this.field_146320_D.func_146117_b(), 10526880);
         }
      } else {
         this.func_73731_b(this.field_146289_q, I18n.func_135052_a("selectWorld.enterName"), this.field_146294_l / 2 - 100, 47, -6250336);
         this.func_73731_b(this.field_146289_q, I18n.func_135052_a("selectWorld.resultFolder") + " " + this.field_146336_i, this.field_146294_l / 2 - 100, 85, -6250336);
         this.field_146333_g.func_146194_f();
         this.func_73731_b(this.field_146289_q, this.field_146323_G, this.field_146294_l / 2 - 100, 137, -6250336);
         this.func_73731_b(this.field_146289_q, this.field_146328_H, this.field_146294_l / 2 - 100, 149, -6250336);
      }

      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   public void func_146318_a(WorldInfo p_146318_1_) {
      this.field_146330_J = I18n.func_135052_a("selectWorld.newWorld.copyOf", p_146318_1_.func_76065_j());
      this.field_146329_I = p_146318_1_.func_76063_b() + "";
      this.field_146331_K = p_146318_1_.func_76067_t().func_82747_f();
      this.field_146334_a = p_146318_1_.func_82571_y();
      this.field_146341_s = p_146318_1_.func_76089_r();
      this.field_146340_t = p_146318_1_.func_76086_u();
      if (p_146318_1_.func_76093_s()) {
         this.field_146342_r = "hardcore";
      } else if (p_146318_1_.func_76077_q().func_77144_e()) {
         this.field_146342_r = "survival";
      } else if (p_146318_1_.func_76077_q().func_77145_d()) {
         this.field_146342_r = "creative";
      }

   }
}
