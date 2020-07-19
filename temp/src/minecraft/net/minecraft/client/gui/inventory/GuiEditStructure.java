package net.minecraft.client.gui.inventory;

import com.google.common.collect.Lists;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiEditStructure extends GuiScreen {
   private static final Logger field_189845_a = LogManager.getLogger();
   public static final int[] field_190302_a = new int[]{203, 205, 14, 211, 199, 207};
   private final TileEntityStructure field_189846_f;
   private Mirror field_189847_g = Mirror.NONE;
   private Rotation field_189848_h = Rotation.NONE;
   private TileEntityStructure.Mode field_189849_i = TileEntityStructure.Mode.DATA;
   private boolean field_189850_r;
   private boolean field_189851_s;
   private boolean field_189852_t;
   private GuiTextField field_189853_u;
   private GuiTextField field_189854_v;
   private GuiTextField field_189855_w;
   private GuiTextField field_189856_x;
   private GuiTextField field_189857_y;
   private GuiTextField field_189858_z;
   private GuiTextField field_189825_A;
   private GuiTextField field_189826_B;
   private GuiTextField field_189827_C;
   private GuiTextField field_189828_D;
   private GuiButton field_189829_E;
   private GuiButton field_189830_F;
   private GuiButton field_189831_G;
   private GuiButton field_189832_H;
   private GuiButton field_189833_I;
   private GuiButton field_189834_J;
   private GuiButton field_189835_K;
   private GuiButton field_189836_L;
   private GuiButton field_189837_M;
   private GuiButton field_189838_N;
   private GuiButton field_189839_O;
   private GuiButton field_189840_P;
   private GuiButton field_189841_Q;
   private GuiButton field_189842_R;
   private final List<GuiTextField> field_189843_S = Lists.<GuiTextField>newArrayList();
   private final DecimalFormat field_189844_T = new DecimalFormat("0.0###");

   public GuiEditStructure(TileEntityStructure p_i47142_1_) {
      this.field_189846_f = p_i47142_1_;
      this.field_189844_T.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
   }

   public void func_73876_c() {
      this.field_189853_u.func_146178_a();
      this.field_189854_v.func_146178_a();
      this.field_189855_w.func_146178_a();
      this.field_189856_x.func_146178_a();
      this.field_189857_y.func_146178_a();
      this.field_189858_z.func_146178_a();
      this.field_189825_A.func_146178_a();
      this.field_189826_B.func_146178_a();
      this.field_189827_C.func_146178_a();
      this.field_189828_D.func_146178_a();
   }

   public void func_73866_w_() {
      Keyboard.enableRepeatEvents(true);
      this.field_146292_n.clear();
      this.field_189829_E = this.func_189646_b(new GuiButton(0, this.field_146294_l / 2 - 4 - 150, 210, 150, 20, I18n.func_135052_a("gui.done")));
      this.field_189830_F = this.func_189646_b(new GuiButton(1, this.field_146294_l / 2 + 4, 210, 150, 20, I18n.func_135052_a("gui.cancel")));
      this.field_189831_G = this.func_189646_b(new GuiButton(9, this.field_146294_l / 2 + 4 + 100, 185, 50, 20, I18n.func_135052_a("structure_block.button.save")));
      this.field_189832_H = this.func_189646_b(new GuiButton(10, this.field_146294_l / 2 + 4 + 100, 185, 50, 20, I18n.func_135052_a("structure_block.button.load")));
      this.field_189837_M = this.func_189646_b(new GuiButton(18, this.field_146294_l / 2 - 4 - 150, 185, 50, 20, "MODE"));
      this.field_189838_N = this.func_189646_b(new GuiButton(19, this.field_146294_l / 2 + 4 + 100, 120, 50, 20, I18n.func_135052_a("structure_block.button.detect_size")));
      this.field_189839_O = this.func_189646_b(new GuiButton(20, this.field_146294_l / 2 + 4 + 100, 160, 50, 20, "ENTITIES"));
      this.field_189840_P = this.func_189646_b(new GuiButton(21, this.field_146294_l / 2 - 20, 185, 40, 20, "MIRROR"));
      this.field_189841_Q = this.func_189646_b(new GuiButton(22, this.field_146294_l / 2 + 4 + 100, 80, 50, 20, "SHOWAIR"));
      this.field_189842_R = this.func_189646_b(new GuiButton(23, this.field_146294_l / 2 + 4 + 100, 80, 50, 20, "SHOWBB"));
      this.field_189833_I = this.func_189646_b(new GuiButton(11, this.field_146294_l / 2 - 1 - 40 - 1 - 40 - 20, 185, 40, 20, "0"));
      this.field_189834_J = this.func_189646_b(new GuiButton(12, this.field_146294_l / 2 - 1 - 40 - 20, 185, 40, 20, "90"));
      this.field_189835_K = this.func_189646_b(new GuiButton(13, this.field_146294_l / 2 + 1 + 20, 185, 40, 20, "180"));
      this.field_189836_L = this.func_189646_b(new GuiButton(14, this.field_146294_l / 2 + 1 + 40 + 1 + 20, 185, 40, 20, "270"));
      this.field_189853_u = new GuiTextField(2, this.field_146289_q, this.field_146294_l / 2 - 152, 40, 300, 20);
      this.field_189853_u.func_146203_f(64);
      this.field_189853_u.func_146180_a(this.field_189846_f.func_189715_d());
      this.field_189843_S.add(this.field_189853_u);
      BlockPos blockpos = this.field_189846_f.func_189711_e();
      this.field_189854_v = new GuiTextField(3, this.field_146289_q, this.field_146294_l / 2 - 152, 80, 80, 20);
      this.field_189854_v.func_146203_f(15);
      this.field_189854_v.func_146180_a(Integer.toString(blockpos.func_177958_n()));
      this.field_189843_S.add(this.field_189854_v);
      this.field_189855_w = new GuiTextField(4, this.field_146289_q, this.field_146294_l / 2 - 72, 80, 80, 20);
      this.field_189855_w.func_146203_f(15);
      this.field_189855_w.func_146180_a(Integer.toString(blockpos.func_177956_o()));
      this.field_189843_S.add(this.field_189855_w);
      this.field_189856_x = new GuiTextField(5, this.field_146289_q, this.field_146294_l / 2 + 8, 80, 80, 20);
      this.field_189856_x.func_146203_f(15);
      this.field_189856_x.func_146180_a(Integer.toString(blockpos.func_177952_p()));
      this.field_189843_S.add(this.field_189856_x);
      BlockPos blockpos1 = this.field_189846_f.func_189717_g();
      this.field_189857_y = new GuiTextField(6, this.field_146289_q, this.field_146294_l / 2 - 152, 120, 80, 20);
      this.field_189857_y.func_146203_f(15);
      this.field_189857_y.func_146180_a(Integer.toString(blockpos1.func_177958_n()));
      this.field_189843_S.add(this.field_189857_y);
      this.field_189858_z = new GuiTextField(7, this.field_146289_q, this.field_146294_l / 2 - 72, 120, 80, 20);
      this.field_189858_z.func_146203_f(15);
      this.field_189858_z.func_146180_a(Integer.toString(blockpos1.func_177956_o()));
      this.field_189843_S.add(this.field_189858_z);
      this.field_189825_A = new GuiTextField(8, this.field_146289_q, this.field_146294_l / 2 + 8, 120, 80, 20);
      this.field_189825_A.func_146203_f(15);
      this.field_189825_A.func_146180_a(Integer.toString(blockpos1.func_177952_p()));
      this.field_189843_S.add(this.field_189825_A);
      this.field_189826_B = new GuiTextField(15, this.field_146289_q, this.field_146294_l / 2 - 152, 120, 80, 20);
      this.field_189826_B.func_146203_f(15);
      this.field_189826_B.func_146180_a(this.field_189844_T.format((double)this.field_189846_f.func_189702_n()));
      this.field_189843_S.add(this.field_189826_B);
      this.field_189827_C = new GuiTextField(16, this.field_146289_q, this.field_146294_l / 2 - 72, 120, 80, 20);
      this.field_189827_C.func_146203_f(31);
      this.field_189827_C.func_146180_a(Long.toString(this.field_189846_f.func_189719_o()));
      this.field_189843_S.add(this.field_189827_C);
      this.field_189828_D = new GuiTextField(17, this.field_146289_q, this.field_146294_l / 2 - 152, 120, 240, 20);
      this.field_189828_D.func_146203_f(128);
      this.field_189828_D.func_146180_a(this.field_189846_f.func_189708_j());
      this.field_189843_S.add(this.field_189828_D);
      this.field_189847_g = this.field_189846_f.func_189716_h();
      this.func_189816_h();
      this.field_189848_h = this.field_189846_f.func_189726_i();
      this.func_189824_i();
      this.field_189849_i = this.field_189846_f.func_189700_k();
      this.func_189823_j();
      this.field_189850_r = this.field_189846_f.func_189713_m();
      this.func_189822_a();
      this.field_189851_s = this.field_189846_f.func_189707_H();
      this.func_189814_f();
      this.field_189852_t = this.field_189846_f.func_189721_I();
      this.func_189815_g();
   }

   public void func_146281_b() {
      Keyboard.enableRepeatEvents(false);
   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
      if (p_146284_1_.field_146124_l) {
         if (p_146284_1_.field_146127_k == 1) {
            this.field_189846_f.func_184411_a(this.field_189847_g);
            this.field_189846_f.func_184408_a(this.field_189848_h);
            this.field_189846_f.func_184405_a(this.field_189849_i);
            this.field_189846_f.func_184406_a(this.field_189850_r);
            this.field_189846_f.func_189703_e(this.field_189851_s);
            this.field_189846_f.func_189710_f(this.field_189852_t);
            this.field_146297_k.func_147108_a((GuiScreen)null);
         } else if (p_146284_1_.field_146127_k == 0) {
            if (this.func_189820_b(1)) {
               this.field_146297_k.func_147108_a((GuiScreen)null);
            }
         } else if (p_146284_1_.field_146127_k == 9) {
            if (this.field_189846_f.func_189700_k() == TileEntityStructure.Mode.SAVE) {
               this.func_189820_b(2);
               this.field_146297_k.func_147108_a((GuiScreen)null);
            }
         } else if (p_146284_1_.field_146127_k == 10) {
            if (this.field_189846_f.func_189700_k() == TileEntityStructure.Mode.LOAD) {
               this.func_189820_b(3);
               this.field_146297_k.func_147108_a((GuiScreen)null);
            }
         } else if (p_146284_1_.field_146127_k == 11) {
            this.field_189846_f.func_184408_a(Rotation.NONE);
            this.func_189824_i();
         } else if (p_146284_1_.field_146127_k == 12) {
            this.field_189846_f.func_184408_a(Rotation.CLOCKWISE_90);
            this.func_189824_i();
         } else if (p_146284_1_.field_146127_k == 13) {
            this.field_189846_f.func_184408_a(Rotation.CLOCKWISE_180);
            this.func_189824_i();
         } else if (p_146284_1_.field_146127_k == 14) {
            this.field_189846_f.func_184408_a(Rotation.COUNTERCLOCKWISE_90);
            this.func_189824_i();
         } else if (p_146284_1_.field_146127_k == 18) {
            this.field_189846_f.func_189724_l();
            this.func_189823_j();
         } else if (p_146284_1_.field_146127_k == 19) {
            if (this.field_189846_f.func_189700_k() == TileEntityStructure.Mode.SAVE) {
               this.func_189820_b(4);
               this.field_146297_k.func_147108_a((GuiScreen)null);
            }
         } else if (p_146284_1_.field_146127_k == 20) {
            this.field_189846_f.func_184406_a(!this.field_189846_f.func_189713_m());
            this.func_189822_a();
         } else if (p_146284_1_.field_146127_k == 22) {
            this.field_189846_f.func_189703_e(!this.field_189846_f.func_189707_H());
            this.func_189814_f();
         } else if (p_146284_1_.field_146127_k == 23) {
            this.field_189846_f.func_189710_f(!this.field_189846_f.func_189721_I());
            this.func_189815_g();
         } else if (p_146284_1_.field_146127_k == 21) {
            switch(this.field_189846_f.func_189716_h()) {
            case NONE:
               this.field_189846_f.func_184411_a(Mirror.LEFT_RIGHT);
               break;
            case LEFT_RIGHT:
               this.field_189846_f.func_184411_a(Mirror.FRONT_BACK);
               break;
            case FRONT_BACK:
               this.field_189846_f.func_184411_a(Mirror.NONE);
            }

            this.func_189816_h();
         }

      }
   }

   private void func_189822_a() {
      boolean flag = !this.field_189846_f.func_189713_m();
      if (flag) {
         this.field_189839_O.field_146126_j = I18n.func_135052_a("options.on");
      } else {
         this.field_189839_O.field_146126_j = I18n.func_135052_a("options.off");
      }

   }

   private void func_189814_f() {
      boolean flag = this.field_189846_f.func_189707_H();
      if (flag) {
         this.field_189841_Q.field_146126_j = I18n.func_135052_a("options.on");
      } else {
         this.field_189841_Q.field_146126_j = I18n.func_135052_a("options.off");
      }

   }

   private void func_189815_g() {
      boolean flag = this.field_189846_f.func_189721_I();
      if (flag) {
         this.field_189842_R.field_146126_j = I18n.func_135052_a("options.on");
      } else {
         this.field_189842_R.field_146126_j = I18n.func_135052_a("options.off");
      }

   }

   private void func_189816_h() {
      Mirror mirror = this.field_189846_f.func_189716_h();
      switch(mirror) {
      case NONE:
         this.field_189840_P.field_146126_j = "|";
         break;
      case LEFT_RIGHT:
         this.field_189840_P.field_146126_j = "< >";
         break;
      case FRONT_BACK:
         this.field_189840_P.field_146126_j = "^ v";
      }

   }

   private void func_189824_i() {
      this.field_189833_I.field_146124_l = true;
      this.field_189834_J.field_146124_l = true;
      this.field_189835_K.field_146124_l = true;
      this.field_189836_L.field_146124_l = true;
      switch(this.field_189846_f.func_189726_i()) {
      case NONE:
         this.field_189833_I.field_146124_l = false;
         break;
      case CLOCKWISE_180:
         this.field_189835_K.field_146124_l = false;
         break;
      case COUNTERCLOCKWISE_90:
         this.field_189836_L.field_146124_l = false;
         break;
      case CLOCKWISE_90:
         this.field_189834_J.field_146124_l = false;
      }

   }

   private void func_189823_j() {
      this.field_189853_u.func_146195_b(false);
      this.field_189854_v.func_146195_b(false);
      this.field_189855_w.func_146195_b(false);
      this.field_189856_x.func_146195_b(false);
      this.field_189857_y.func_146195_b(false);
      this.field_189858_z.func_146195_b(false);
      this.field_189825_A.func_146195_b(false);
      this.field_189826_B.func_146195_b(false);
      this.field_189827_C.func_146195_b(false);
      this.field_189828_D.func_146195_b(false);
      this.field_189853_u.func_146189_e(false);
      this.field_189853_u.func_146195_b(false);
      this.field_189854_v.func_146189_e(false);
      this.field_189855_w.func_146189_e(false);
      this.field_189856_x.func_146189_e(false);
      this.field_189857_y.func_146189_e(false);
      this.field_189858_z.func_146189_e(false);
      this.field_189825_A.func_146189_e(false);
      this.field_189826_B.func_146189_e(false);
      this.field_189827_C.func_146189_e(false);
      this.field_189828_D.func_146189_e(false);
      this.field_189831_G.field_146125_m = false;
      this.field_189832_H.field_146125_m = false;
      this.field_189838_N.field_146125_m = false;
      this.field_189839_O.field_146125_m = false;
      this.field_189840_P.field_146125_m = false;
      this.field_189833_I.field_146125_m = false;
      this.field_189834_J.field_146125_m = false;
      this.field_189835_K.field_146125_m = false;
      this.field_189836_L.field_146125_m = false;
      this.field_189841_Q.field_146125_m = false;
      this.field_189842_R.field_146125_m = false;
      switch(this.field_189846_f.func_189700_k()) {
      case SAVE:
         this.field_189853_u.func_146189_e(true);
         this.field_189853_u.func_146195_b(true);
         this.field_189854_v.func_146189_e(true);
         this.field_189855_w.func_146189_e(true);
         this.field_189856_x.func_146189_e(true);
         this.field_189857_y.func_146189_e(true);
         this.field_189858_z.func_146189_e(true);
         this.field_189825_A.func_146189_e(true);
         this.field_189831_G.field_146125_m = true;
         this.field_189838_N.field_146125_m = true;
         this.field_189839_O.field_146125_m = true;
         this.field_189841_Q.field_146125_m = true;
         break;
      case LOAD:
         this.field_189853_u.func_146189_e(true);
         this.field_189853_u.func_146195_b(true);
         this.field_189854_v.func_146189_e(true);
         this.field_189855_w.func_146189_e(true);
         this.field_189856_x.func_146189_e(true);
         this.field_189826_B.func_146189_e(true);
         this.field_189827_C.func_146189_e(true);
         this.field_189832_H.field_146125_m = true;
         this.field_189839_O.field_146125_m = true;
         this.field_189840_P.field_146125_m = true;
         this.field_189833_I.field_146125_m = true;
         this.field_189834_J.field_146125_m = true;
         this.field_189835_K.field_146125_m = true;
         this.field_189836_L.field_146125_m = true;
         this.field_189842_R.field_146125_m = true;
         this.func_189824_i();
         break;
      case CORNER:
         this.field_189853_u.func_146189_e(true);
         this.field_189853_u.func_146195_b(true);
         break;
      case DATA:
         this.field_189828_D.func_146189_e(true);
         this.field_189828_D.func_146195_b(true);
      }

      this.field_189837_M.field_146126_j = I18n.func_135052_a("structure_block.mode." + this.field_189846_f.func_189700_k().func_176610_l());
   }

   private boolean func_189820_b(int p_189820_1_) {
      try {
         PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
         this.field_189846_f.func_189705_a(packetbuffer);
         packetbuffer.writeByte(p_189820_1_);
         packetbuffer.func_180714_a(this.field_189846_f.func_189700_k().toString());
         packetbuffer.func_180714_a(this.field_189853_u.func_146179_b());
         packetbuffer.writeInt(this.func_189817_c(this.field_189854_v.func_146179_b()));
         packetbuffer.writeInt(this.func_189817_c(this.field_189855_w.func_146179_b()));
         packetbuffer.writeInt(this.func_189817_c(this.field_189856_x.func_146179_b()));
         packetbuffer.writeInt(this.func_189817_c(this.field_189857_y.func_146179_b()));
         packetbuffer.writeInt(this.func_189817_c(this.field_189858_z.func_146179_b()));
         packetbuffer.writeInt(this.func_189817_c(this.field_189825_A.func_146179_b()));
         packetbuffer.func_180714_a(this.field_189846_f.func_189716_h().toString());
         packetbuffer.func_180714_a(this.field_189846_f.func_189726_i().toString());
         packetbuffer.func_180714_a(this.field_189828_D.func_146179_b());
         packetbuffer.writeBoolean(this.field_189846_f.func_189713_m());
         packetbuffer.writeBoolean(this.field_189846_f.func_189707_H());
         packetbuffer.writeBoolean(this.field_189846_f.func_189721_I());
         packetbuffer.writeFloat(this.func_189819_b(this.field_189826_B.func_146179_b()));
         packetbuffer.func_179254_b(this.func_189821_a(this.field_189827_C.func_146179_b()));
         this.field_146297_k.func_147114_u().func_147297_a(new CPacketCustomPayload("MC|Struct", packetbuffer));
         return true;
      } catch (Exception exception) {
         field_189845_a.warn("Could not send structure block info", (Throwable)exception);
         return false;
      }
   }

   private long func_189821_a(String p_189821_1_) {
      try {
         return Long.valueOf(p_189821_1_).longValue();
      } catch (NumberFormatException var3) {
         return 0L;
      }
   }

   private float func_189819_b(String p_189819_1_) {
      try {
         return Float.valueOf(p_189819_1_).floatValue();
      } catch (NumberFormatException var3) {
         return 1.0F;
      }
   }

   private int func_189817_c(String p_189817_1_) {
      try {
         return Integer.parseInt(p_189817_1_);
      } catch (NumberFormatException var3) {
         return 0;
      }
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) throws IOException {
      if (this.field_189853_u.func_146176_q() && func_190301_b(p_73869_1_, p_73869_2_)) {
         this.field_189853_u.func_146201_a(p_73869_1_, p_73869_2_);
      }

      if (this.field_189854_v.func_146176_q()) {
         this.field_189854_v.func_146201_a(p_73869_1_, p_73869_2_);
      }

      if (this.field_189855_w.func_146176_q()) {
         this.field_189855_w.func_146201_a(p_73869_1_, p_73869_2_);
      }

      if (this.field_189856_x.func_146176_q()) {
         this.field_189856_x.func_146201_a(p_73869_1_, p_73869_2_);
      }

      if (this.field_189857_y.func_146176_q()) {
         this.field_189857_y.func_146201_a(p_73869_1_, p_73869_2_);
      }

      if (this.field_189858_z.func_146176_q()) {
         this.field_189858_z.func_146201_a(p_73869_1_, p_73869_2_);
      }

      if (this.field_189825_A.func_146176_q()) {
         this.field_189825_A.func_146201_a(p_73869_1_, p_73869_2_);
      }

      if (this.field_189826_B.func_146176_q()) {
         this.field_189826_B.func_146201_a(p_73869_1_, p_73869_2_);
      }

      if (this.field_189827_C.func_146176_q()) {
         this.field_189827_C.func_146201_a(p_73869_1_, p_73869_2_);
      }

      if (this.field_189828_D.func_146176_q()) {
         this.field_189828_D.func_146201_a(p_73869_1_, p_73869_2_);
      }

      if (p_73869_2_ == 15) {
         GuiTextField guitextfield = null;
         GuiTextField guitextfield1 = null;

         for(GuiTextField guitextfield2 : this.field_189843_S) {
            if (guitextfield != null && guitextfield2.func_146176_q()) {
               guitextfield1 = guitextfield2;
               break;
            }

            if (guitextfield2.func_146206_l() && guitextfield2.func_146176_q()) {
               guitextfield = guitextfield2;
            }
         }

         if (guitextfield != null && guitextfield1 == null) {
            for(GuiTextField guitextfield3 : this.field_189843_S) {
               if (guitextfield3.func_146176_q() && guitextfield3 != guitextfield) {
                  guitextfield1 = guitextfield3;
                  break;
               }
            }
         }

         if (guitextfield1 != null && guitextfield1 != guitextfield) {
            guitextfield.func_146195_b(false);
            guitextfield1.func_146195_b(true);
         }
      }

      if (p_73869_2_ != 28 && p_73869_2_ != 156) {
         if (p_73869_2_ == 1) {
            this.func_146284_a(this.field_189830_F);
         }
      } else {
         this.func_146284_a(this.field_189829_E);
      }

   }

   private static boolean func_190301_b(char p_190301_0_, int p_190301_1_) {
      boolean flag = true;

      for(int i : field_190302_a) {
         if (i == p_190301_1_) {
            return true;
         }
      }

      for(char c0 : ChatAllowedCharacters.field_189861_b) {
         if (c0 == p_190301_0_) {
            flag = false;
            break;
         }
      }

      return flag;
   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) throws IOException {
      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
      if (this.field_189853_u.func_146176_q()) {
         this.field_189853_u.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
      }

      if (this.field_189854_v.func_146176_q()) {
         this.field_189854_v.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
      }

      if (this.field_189855_w.func_146176_q()) {
         this.field_189855_w.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
      }

      if (this.field_189856_x.func_146176_q()) {
         this.field_189856_x.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
      }

      if (this.field_189857_y.func_146176_q()) {
         this.field_189857_y.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
      }

      if (this.field_189858_z.func_146176_q()) {
         this.field_189858_z.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
      }

      if (this.field_189825_A.func_146176_q()) {
         this.field_189825_A.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
      }

      if (this.field_189826_B.func_146176_q()) {
         this.field_189826_B.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
      }

      if (this.field_189827_C.func_146176_q()) {
         this.field_189827_C.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
      }

      if (this.field_189828_D.func_146176_q()) {
         this.field_189828_D.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
      }

   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      TileEntityStructure.Mode tileentitystructure$mode = this.field_189846_f.func_189700_k();
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("tile.structureBlock.name"), this.field_146294_l / 2, 10, 16777215);
      if (tileentitystructure$mode != TileEntityStructure.Mode.DATA) {
         this.func_73731_b(this.field_146289_q, I18n.func_135052_a("structure_block.structure_name"), this.field_146294_l / 2 - 153, 30, 10526880);
         this.field_189853_u.func_146194_f();
      }

      if (tileentitystructure$mode == TileEntityStructure.Mode.LOAD || tileentitystructure$mode == TileEntityStructure.Mode.SAVE) {
         this.func_73731_b(this.field_146289_q, I18n.func_135052_a("structure_block.position"), this.field_146294_l / 2 - 153, 70, 10526880);
         this.field_189854_v.func_146194_f();
         this.field_189855_w.func_146194_f();
         this.field_189856_x.func_146194_f();
         String s = I18n.func_135052_a("structure_block.include_entities");
         int i = this.field_146289_q.func_78256_a(s);
         this.func_73731_b(this.field_146289_q, s, this.field_146294_l / 2 + 154 - i, 150, 10526880);
      }

      if (tileentitystructure$mode == TileEntityStructure.Mode.SAVE) {
         this.func_73731_b(this.field_146289_q, I18n.func_135052_a("structure_block.size"), this.field_146294_l / 2 - 153, 110, 10526880);
         this.field_189857_y.func_146194_f();
         this.field_189858_z.func_146194_f();
         this.field_189825_A.func_146194_f();
         String s2 = I18n.func_135052_a("structure_block.detect_size");
         int k = this.field_146289_q.func_78256_a(s2);
         this.func_73731_b(this.field_146289_q, s2, this.field_146294_l / 2 + 154 - k, 110, 10526880);
         String s1 = I18n.func_135052_a("structure_block.show_air");
         int j = this.field_146289_q.func_78256_a(s1);
         this.func_73731_b(this.field_146289_q, s1, this.field_146294_l / 2 + 154 - j, 70, 10526880);
      }

      if (tileentitystructure$mode == TileEntityStructure.Mode.LOAD) {
         this.func_73731_b(this.field_146289_q, I18n.func_135052_a("structure_block.integrity"), this.field_146294_l / 2 - 153, 110, 10526880);
         this.field_189826_B.func_146194_f();
         this.field_189827_C.func_146194_f();
         String s3 = I18n.func_135052_a("structure_block.show_boundingbox");
         int l = this.field_146289_q.func_78256_a(s3);
         this.func_73731_b(this.field_146289_q, s3, this.field_146294_l / 2 + 154 - l, 70, 10526880);
      }

      if (tileentitystructure$mode == TileEntityStructure.Mode.DATA) {
         this.func_73731_b(this.field_146289_q, I18n.func_135052_a("structure_block.custom_data"), this.field_146294_l / 2 - 153, 110, 10526880);
         this.field_189828_D.func_146194_f();
      }

      String s4 = "structure_block.mode_info." + tileentitystructure$mode.func_176610_l();
      this.func_73731_b(this.field_146289_q, I18n.func_135052_a(s4), this.field_146294_l / 2 - 153, 174, 10526880);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   public boolean func_73868_f() {
      return false;
   }
}
