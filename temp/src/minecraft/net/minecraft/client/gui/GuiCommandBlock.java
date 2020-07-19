package net.minecraft.client.gui;

import io.netty.buffer.Unpooled;
import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.ITabCompleter;
import net.minecraft.util.TabCompleter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.input.Keyboard;

public class GuiCommandBlock extends GuiScreen implements ITabCompleter {
   private GuiTextField field_146485_f;
   private GuiTextField field_146486_g;
   private final TileEntityCommandBlock field_184078_g;
   private GuiButton field_146490_i;
   private GuiButton field_146487_r;
   private GuiButton field_175390_s;
   private GuiButton field_184079_s;
   private GuiButton field_184080_t;
   private GuiButton field_184081_u;
   private boolean field_175389_t;
   private TileEntityCommandBlock.Mode field_184082_w = TileEntityCommandBlock.Mode.REDSTONE;
   private TabCompleter field_184083_x;
   private boolean field_184084_y;
   private boolean field_184085_z;

   public GuiCommandBlock(TileEntityCommandBlock p_i46596_1_) {
      this.field_184078_g = p_i46596_1_;
   }

   public void func_73876_c() {
      this.field_146485_f.func_146178_a();
   }

   public void func_73866_w_() {
      final CommandBlockBaseLogic commandblockbaselogic = this.field_184078_g.func_145993_a();
      Keyboard.enableRepeatEvents(true);
      this.field_146292_n.clear();
      this.field_146490_i = this.func_189646_b(new GuiButton(0, this.field_146294_l / 2 - 4 - 150, this.field_146295_m / 4 + 120 + 12, 150, 20, I18n.func_135052_a("gui.done")));
      this.field_146487_r = this.func_189646_b(new GuiButton(1, this.field_146294_l / 2 + 4, this.field_146295_m / 4 + 120 + 12, 150, 20, I18n.func_135052_a("gui.cancel")));
      this.field_175390_s = this.func_189646_b(new GuiButton(4, this.field_146294_l / 2 + 150 - 20, 135, 20, 20, "O"));
      this.field_184079_s = this.func_189646_b(new GuiButton(5, this.field_146294_l / 2 - 50 - 100 - 4, 165, 100, 20, I18n.func_135052_a("advMode.mode.sequence")));
      this.field_184080_t = this.func_189646_b(new GuiButton(6, this.field_146294_l / 2 - 50, 165, 100, 20, I18n.func_135052_a("advMode.mode.unconditional")));
      this.field_184081_u = this.func_189646_b(new GuiButton(7, this.field_146294_l / 2 + 50 + 4, 165, 100, 20, I18n.func_135052_a("advMode.mode.redstoneTriggered")));
      this.field_146485_f = new GuiTextField(2, this.field_146289_q, this.field_146294_l / 2 - 150, 50, 300, 20);
      this.field_146485_f.func_146203_f(32500);
      this.field_146485_f.func_146195_b(true);
      this.field_146486_g = new GuiTextField(3, this.field_146289_q, this.field_146294_l / 2 - 150, 135, 276, 20);
      this.field_146486_g.func_146203_f(32500);
      this.field_146486_g.func_146184_c(false);
      this.field_146486_g.func_146180_a("-");
      this.field_146490_i.field_146124_l = false;
      this.field_175390_s.field_146124_l = false;
      this.field_184079_s.field_146124_l = false;
      this.field_184080_t.field_146124_l = false;
      this.field_184081_u.field_146124_l = false;
      this.field_184083_x = new TabCompleter(this.field_146485_f, true) {
         @Nullable
         public BlockPos func_186839_b() {
            return commandblockbaselogic.func_180425_c();
         }
      };
   }

   public void func_184075_a() {
      CommandBlockBaseLogic commandblockbaselogic = this.field_184078_g.func_145993_a();
      this.field_146485_f.func_146180_a(commandblockbaselogic.func_145753_i());
      this.field_175389_t = commandblockbaselogic.func_175571_m();
      this.field_184082_w = this.field_184078_g.func_184251_i();
      this.field_184084_y = this.field_184078_g.func_184258_j();
      this.field_184085_z = this.field_184078_g.func_184254_e();
      this.func_175388_a();
      this.func_184073_g();
      this.func_184077_i();
      this.func_184076_j();
      this.field_146490_i.field_146124_l = true;
      this.field_175390_s.field_146124_l = true;
      this.field_184079_s.field_146124_l = true;
      this.field_184080_t.field_146124_l = true;
      this.field_184081_u.field_146124_l = true;
   }

   public void func_146281_b() {
      Keyboard.enableRepeatEvents(false);
   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
      if (p_146284_1_.field_146124_l) {
         CommandBlockBaseLogic commandblockbaselogic = this.field_184078_g.func_145993_a();
         if (p_146284_1_.field_146127_k == 1) {
            commandblockbaselogic.func_175573_a(this.field_175389_t);
            this.field_146297_k.func_147108_a((GuiScreen)null);
         } else if (p_146284_1_.field_146127_k == 0) {
            PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
            commandblockbaselogic.func_145757_a(packetbuffer);
            packetbuffer.func_180714_a(this.field_146485_f.func_146179_b());
            packetbuffer.writeBoolean(commandblockbaselogic.func_175571_m());
            packetbuffer.func_180714_a(this.field_184082_w.name());
            packetbuffer.writeBoolean(this.field_184084_y);
            packetbuffer.writeBoolean(this.field_184085_z);
            this.field_146297_k.func_147114_u().func_147297_a(new CPacketCustomPayload("MC|AutoCmd", packetbuffer));
            if (!commandblockbaselogic.func_175571_m()) {
               commandblockbaselogic.func_145750_b((ITextComponent)null);
            }

            this.field_146297_k.func_147108_a((GuiScreen)null);
         } else if (p_146284_1_.field_146127_k == 4) {
            commandblockbaselogic.func_175573_a(!commandblockbaselogic.func_175571_m());
            this.func_175388_a();
         } else if (p_146284_1_.field_146127_k == 5) {
            this.func_184074_h();
            this.func_184073_g();
         } else if (p_146284_1_.field_146127_k == 6) {
            this.field_184084_y = !this.field_184084_y;
            this.func_184077_i();
         } else if (p_146284_1_.field_146127_k == 7) {
            this.field_184085_z = !this.field_184085_z;
            this.func_184076_j();
         }

      }
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) throws IOException {
      this.field_184083_x.func_186843_d();
      if (p_73869_2_ == 15) {
         this.field_184083_x.func_186841_a();
      } else {
         this.field_184083_x.func_186842_c();
      }

      this.field_146485_f.func_146201_a(p_73869_1_, p_73869_2_);
      this.field_146486_g.func_146201_a(p_73869_1_, p_73869_2_);
      if (p_73869_2_ != 28 && p_73869_2_ != 156) {
         if (p_73869_2_ == 1) {
            this.func_146284_a(this.field_146487_r);
         }
      } else {
         this.func_146284_a(this.field_146490_i);
      }

   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) throws IOException {
      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
      this.field_146485_f.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
      this.field_146486_g.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("advMode.setCommand"), this.field_146294_l / 2, 20, 16777215);
      this.func_73731_b(this.field_146289_q, I18n.func_135052_a("advMode.command"), this.field_146294_l / 2 - 150, 40, 10526880);
      this.field_146485_f.func_146194_f();
      int i = 75;
      int j = 0;
      this.func_73731_b(this.field_146289_q, I18n.func_135052_a("advMode.nearestPlayer"), this.field_146294_l / 2 - 140, i + j++ * this.field_146289_q.field_78288_b, 10526880);
      this.func_73731_b(this.field_146289_q, I18n.func_135052_a("advMode.randomPlayer"), this.field_146294_l / 2 - 140, i + j++ * this.field_146289_q.field_78288_b, 10526880);
      this.func_73731_b(this.field_146289_q, I18n.func_135052_a("advMode.allPlayers"), this.field_146294_l / 2 - 140, i + j++ * this.field_146289_q.field_78288_b, 10526880);
      this.func_73731_b(this.field_146289_q, I18n.func_135052_a("advMode.allEntities"), this.field_146294_l / 2 - 140, i + j++ * this.field_146289_q.field_78288_b, 10526880);
      this.func_73731_b(this.field_146289_q, I18n.func_135052_a("advMode.self"), this.field_146294_l / 2 - 140, i + j++ * this.field_146289_q.field_78288_b, 10526880);
      if (!this.field_146486_g.func_146179_b().isEmpty()) {
         i = i + j * this.field_146289_q.field_78288_b + 1;
         this.func_73731_b(this.field_146289_q, I18n.func_135052_a("advMode.previousOutput"), this.field_146294_l / 2 - 150, i + 4, 10526880);
         this.field_146486_g.func_146194_f();
      }

      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   private void func_175388_a() {
      CommandBlockBaseLogic commandblockbaselogic = this.field_184078_g.func_145993_a();
      if (commandblockbaselogic.func_175571_m()) {
         this.field_175390_s.field_146126_j = "O";
         if (commandblockbaselogic.func_145749_h() != null) {
            this.field_146486_g.func_146180_a(commandblockbaselogic.func_145749_h().func_150260_c());
         }
      } else {
         this.field_175390_s.field_146126_j = "X";
         this.field_146486_g.func_146180_a("-");
      }

   }

   private void func_184073_g() {
      switch(this.field_184082_w) {
      case SEQUENCE:
         this.field_184079_s.field_146126_j = I18n.func_135052_a("advMode.mode.sequence");
         break;
      case AUTO:
         this.field_184079_s.field_146126_j = I18n.func_135052_a("advMode.mode.auto");
         break;
      case REDSTONE:
         this.field_184079_s.field_146126_j = I18n.func_135052_a("advMode.mode.redstone");
      }

   }

   private void func_184074_h() {
      switch(this.field_184082_w) {
      case SEQUENCE:
         this.field_184082_w = TileEntityCommandBlock.Mode.AUTO;
         break;
      case AUTO:
         this.field_184082_w = TileEntityCommandBlock.Mode.REDSTONE;
         break;
      case REDSTONE:
         this.field_184082_w = TileEntityCommandBlock.Mode.SEQUENCE;
      }

   }

   private void func_184077_i() {
      if (this.field_184084_y) {
         this.field_184080_t.field_146126_j = I18n.func_135052_a("advMode.mode.conditional");
      } else {
         this.field_184080_t.field_146126_j = I18n.func_135052_a("advMode.mode.unconditional");
      }

   }

   private void func_184076_j() {
      if (this.field_184085_z) {
         this.field_184081_u.field_146126_j = I18n.func_135052_a("advMode.mode.autoexec.bat");
      } else {
         this.field_184081_u.field_146126_j = I18n.func_135052_a("advMode.mode.redstoneTriggered");
      }

   }

   public void func_184072_a(String... p_184072_1_) {
      this.field_184083_x.func_186840_a(p_184072_1_);
   }
}
