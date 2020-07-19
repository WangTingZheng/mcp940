package net.minecraft.realms;

import java.net.InetAddress;
import java.net.UnknownHostException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.CPacketLoginStart;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsConnect {
   private static final Logger LOGGER = LogManager.getLogger();
   private final RealmsScreen onlineScreen;
   private volatile boolean aborted;
   private NetworkManager connection;

   public RealmsConnect(RealmsScreen p_i1079_1_) {
      this.onlineScreen = p_i1079_1_;
   }

   public void connect(final String p_connect_1_, final int p_connect_2_) {
      Realms.setConnectedToRealms(true);
      (new Thread("Realms-connect-task") {
         public void run() {
            InetAddress inetaddress = null;

            try {
               inetaddress = InetAddress.getByName(p_connect_1_);
               if (RealmsConnect.this.aborted) {
                  return;
               }

               RealmsConnect.this.connection = NetworkManager.func_181124_a(inetaddress, p_connect_2_, Minecraft.func_71410_x().field_71474_y.func_181148_f());
               if (RealmsConnect.this.aborted) {
                  return;
               }

               RealmsConnect.this.connection.func_150719_a(new NetHandlerLoginClient(RealmsConnect.this.connection, Minecraft.func_71410_x(), RealmsConnect.this.onlineScreen.getProxy()));
               if (RealmsConnect.this.aborted) {
                  return;
               }

               RealmsConnect.this.connection.func_179290_a(new C00Handshake(335, p_connect_1_, p_connect_2_, EnumConnectionState.LOGIN));
               if (RealmsConnect.this.aborted) {
                  return;
               }

               RealmsConnect.this.connection.func_179290_a(new CPacketLoginStart(Minecraft.func_71410_x().func_110432_I().func_148256_e()));
            } catch (UnknownHostException unknownhostexception) {
               Realms.clearResourcePack();
               if (RealmsConnect.this.aborted) {
                  return;
               }

               RealmsConnect.LOGGER.error("Couldn't connect to world", (Throwable)unknownhostexception);
               Realms.setScreen(new DisconnectedRealmsScreen(RealmsConnect.this.onlineScreen, "connect.failed", new TextComponentTranslation("disconnect.genericReason", new Object[]{"Unknown host '" + p_connect_1_ + "'"})));
            } catch (Exception exception) {
               Realms.clearResourcePack();
               if (RealmsConnect.this.aborted) {
                  return;
               }

               RealmsConnect.LOGGER.error("Couldn't connect to world", (Throwable)exception);
               String s = exception.toString();
               if (inetaddress != null) {
                  String s1 = inetaddress + ":" + p_connect_2_;
                  s = s.replaceAll(s1, "");
               }

               Realms.setScreen(new DisconnectedRealmsScreen(RealmsConnect.this.onlineScreen, "connect.failed", new TextComponentTranslation("disconnect.genericReason", new Object[]{s})));
            }

         }
      }).start();
   }

   public void abort() {
      this.aborted = true;
      if (this.connection != null && this.connection.func_150724_d()) {
         this.connection.func_150718_a(new TextComponentTranslation("disconnect.genericReason", new Object[0]));
         this.connection.func_179293_l();
      }

   }

   public void tick() {
      if (this.connection != null) {
         if (this.connection.func_150724_d()) {
            this.connection.func_74428_b();
         } else {
            this.connection.func_179293_l();
         }
      }

   }
}
