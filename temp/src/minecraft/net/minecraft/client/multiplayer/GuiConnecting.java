package net.minecraft.client.multiplayer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.CPacketLoginStart;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiConnecting extends GuiScreen {
   private static final AtomicInteger field_146372_a = new AtomicInteger(0);
   private static final Logger field_146370_f = LogManager.getLogger();
   private NetworkManager field_146371_g;
   private boolean field_146373_h;
   private final GuiScreen field_146374_i;

   public GuiConnecting(GuiScreen p_i1181_1_, Minecraft p_i1181_2_, ServerData p_i1181_3_) {
      this.field_146297_k = p_i1181_2_;
      this.field_146374_i = p_i1181_1_;
      ServerAddress serveraddress = ServerAddress.func_78860_a(p_i1181_3_.field_78845_b);
      p_i1181_2_.func_71403_a((WorldClient)null);
      p_i1181_2_.func_71351_a(p_i1181_3_);
      this.func_146367_a(serveraddress.func_78861_a(), serveraddress.func_78864_b());
   }

   public GuiConnecting(GuiScreen p_i1182_1_, Minecraft p_i1182_2_, String p_i1182_3_, int p_i1182_4_) {
      this.field_146297_k = p_i1182_2_;
      this.field_146374_i = p_i1182_1_;
      p_i1182_2_.func_71403_a((WorldClient)null);
      this.func_146367_a(p_i1182_3_, p_i1182_4_);
   }

   private void func_146367_a(final String p_146367_1_, final int p_146367_2_) {
      field_146370_f.info("Connecting to {}, {}", p_146367_1_, Integer.valueOf(p_146367_2_));
      (new Thread("Server Connector #" + field_146372_a.incrementAndGet()) {
         public void run() {
            InetAddress inetaddress = null;

            try {
               if (GuiConnecting.this.field_146373_h) {
                  return;
               }

               inetaddress = InetAddress.getByName(p_146367_1_);
               GuiConnecting.this.field_146371_g = NetworkManager.func_181124_a(inetaddress, p_146367_2_, GuiConnecting.this.field_146297_k.field_71474_y.func_181148_f());
               GuiConnecting.this.field_146371_g.func_150719_a(new NetHandlerLoginClient(GuiConnecting.this.field_146371_g, GuiConnecting.this.field_146297_k, GuiConnecting.this.field_146374_i));
               GuiConnecting.this.field_146371_g.func_179290_a(new C00Handshake(335, p_146367_1_, p_146367_2_, EnumConnectionState.LOGIN));
               GuiConnecting.this.field_146371_g.func_179290_a(new CPacketLoginStart(GuiConnecting.this.field_146297_k.func_110432_I().func_148256_e()));
            } catch (UnknownHostException unknownhostexception) {
               if (GuiConnecting.this.field_146373_h) {
                  return;
               }

               GuiConnecting.field_146370_f.error("Couldn't connect to server", (Throwable)unknownhostexception);
               GuiConnecting.this.field_146297_k.func_147108_a(new GuiDisconnected(GuiConnecting.this.field_146374_i, "connect.failed", new TextComponentTranslation("disconnect.genericReason", new Object[]{"Unknown host"})));
            } catch (Exception exception) {
               if (GuiConnecting.this.field_146373_h) {
                  return;
               }

               GuiConnecting.field_146370_f.error("Couldn't connect to server", (Throwable)exception);
               String s = exception.toString();
               if (inetaddress != null) {
                  String s1 = inetaddress + ":" + p_146367_2_;
                  s = s.replaceAll(s1, "");
               }

               GuiConnecting.this.field_146297_k.func_147108_a(new GuiDisconnected(GuiConnecting.this.field_146374_i, "connect.failed", new TextComponentTranslation("disconnect.genericReason", new Object[]{s})));
            }

         }
      }).start();
   }

   public void func_73876_c() {
      if (this.field_146371_g != null) {
         if (this.field_146371_g.func_150724_d()) {
            this.field_146371_g.func_74428_b();
         } else {
            this.field_146371_g.func_179293_l();
         }
      }

   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) throws IOException {
   }

   public void func_73866_w_() {
      this.field_146292_n.clear();
      this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 120 + 12, I18n.func_135052_a("gui.cancel")));
   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
      if (p_146284_1_.field_146127_k == 0) {
         this.field_146373_h = true;
         if (this.field_146371_g != null) {
            this.field_146371_g.func_150718_a(new TextComponentString("Aborted"));
         }

         this.field_146297_k.func_147108_a(this.field_146374_i);
      }

   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      if (this.field_146371_g == null) {
         this.func_73732_a(this.field_146289_q, I18n.func_135052_a("connect.connecting"), this.field_146294_l / 2, this.field_146295_m / 2 - 50, 16777215);
      } else {
         this.func_73732_a(this.field_146289_q, I18n.func_135052_a("connect.authorizing"), this.field_146294_l / 2, this.field_146295_m / 2 - 50, 16777215);
      }

      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }
}
