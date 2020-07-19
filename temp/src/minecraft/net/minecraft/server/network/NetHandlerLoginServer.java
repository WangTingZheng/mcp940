package net.minecraft.server.network;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.INetHandlerLoginServer;
import net.minecraft.network.login.client.CPacketEncryptionResponse;
import net.minecraft.network.login.client.CPacketLoginStart;
import net.minecraft.network.login.server.SPacketDisconnect;
import net.minecraft.network.login.server.SPacketEnableCompression;
import net.minecraft.network.login.server.SPacketEncryptionRequest;
import net.minecraft.network.login.server.SPacketLoginSuccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.CryptManager;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerLoginServer implements INetHandlerLoginServer, ITickable {
   private static final AtomicInteger field_147331_b = new AtomicInteger(0);
   private static final Logger field_147332_c = LogManager.getLogger();
   private static final Random field_147329_d = new Random();
   private final byte[] field_147330_e = new byte[4];
   private final MinecraftServer field_147327_f;
   public final NetworkManager field_147333_a;
   private NetHandlerLoginServer.LoginState field_147328_g = NetHandlerLoginServer.LoginState.HELLO;
   private int field_147336_h;
   private GameProfile field_147337_i;
   private final String field_147334_j = "";
   private SecretKey field_147335_k;
   private EntityPlayerMP field_181025_l;

   public NetHandlerLoginServer(MinecraftServer p_i45298_1_, NetworkManager p_i45298_2_) {
      this.field_147327_f = p_i45298_1_;
      this.field_147333_a = p_i45298_2_;
      field_147329_d.nextBytes(this.field_147330_e);
   }

   public void func_73660_a() {
      if (this.field_147328_g == NetHandlerLoginServer.LoginState.READY_TO_ACCEPT) {
         this.func_147326_c();
      } else if (this.field_147328_g == NetHandlerLoginServer.LoginState.DELAY_ACCEPT) {
         EntityPlayerMP entityplayermp = this.field_147327_f.func_184103_al().func_177451_a(this.field_147337_i.getId());
         if (entityplayermp == null) {
            this.field_147328_g = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
            this.field_147327_f.func_184103_al().func_72355_a(this.field_147333_a, this.field_181025_l);
            this.field_181025_l = null;
         }
      }

      if (this.field_147336_h++ == 600) {
         this.func_194026_b(new TextComponentTranslation("multiplayer.disconnect.slow_login", new Object[0]));
      }

   }

   public void func_194026_b(ITextComponent p_194026_1_) {
      try {
         field_147332_c.info("Disconnecting {}: {}", this.func_147317_d(), p_194026_1_.func_150260_c());
         this.field_147333_a.func_179290_a(new SPacketDisconnect(p_194026_1_));
         this.field_147333_a.func_150718_a(p_194026_1_);
      } catch (Exception exception) {
         field_147332_c.error("Error whilst disconnecting player", (Throwable)exception);
      }

   }

   public void func_147326_c() {
      if (!this.field_147337_i.isComplete()) {
         this.field_147337_i = this.func_152506_a(this.field_147337_i);
      }

      String s = this.field_147327_f.func_184103_al().func_148542_a(this.field_147333_a.func_74430_c(), this.field_147337_i);
      if (s != null) {
         this.func_194026_b(new TextComponentTranslation(s, new Object[0]));
      } else {
         this.field_147328_g = NetHandlerLoginServer.LoginState.ACCEPTED;
         if (this.field_147327_f.func_175577_aI() >= 0 && !this.field_147333_a.func_150731_c()) {
            this.field_147333_a.func_179288_a(new SPacketEnableCompression(this.field_147327_f.func_175577_aI()), new ChannelFutureListener() {
               public void operationComplete(ChannelFuture p_operationComplete_1_) throws Exception {
                  NetHandlerLoginServer.this.field_147333_a.func_179289_a(NetHandlerLoginServer.this.field_147327_f.func_175577_aI());
               }
            });
         }

         this.field_147333_a.func_179290_a(new SPacketLoginSuccess(this.field_147337_i));
         EntityPlayerMP entityplayermp = this.field_147327_f.func_184103_al().func_177451_a(this.field_147337_i.getId());
         if (entityplayermp != null) {
            this.field_147328_g = NetHandlerLoginServer.LoginState.DELAY_ACCEPT;
            this.field_181025_l = this.field_147327_f.func_184103_al().func_148545_a(this.field_147337_i);
         } else {
            this.field_147327_f.func_184103_al().func_72355_a(this.field_147333_a, this.field_147327_f.func_184103_al().func_148545_a(this.field_147337_i));
         }
      }

   }

   public void func_147231_a(ITextComponent p_147231_1_) {
      field_147332_c.info("{} lost connection: {}", this.func_147317_d(), p_147231_1_.func_150260_c());
   }

   public String func_147317_d() {
      return this.field_147337_i != null ? this.field_147337_i + " (" + this.field_147333_a.func_74430_c() + ")" : String.valueOf((Object)this.field_147333_a.func_74430_c());
   }

   public void func_147316_a(CPacketLoginStart p_147316_1_) {
      Validate.validState(this.field_147328_g == NetHandlerLoginServer.LoginState.HELLO, "Unexpected hello packet");
      this.field_147337_i = p_147316_1_.func_149304_c();
      if (this.field_147327_f.func_71266_T() && !this.field_147333_a.func_150731_c()) {
         this.field_147328_g = NetHandlerLoginServer.LoginState.KEY;
         this.field_147333_a.func_179290_a(new SPacketEncryptionRequest("", this.field_147327_f.func_71250_E().getPublic(), this.field_147330_e));
      } else {
         this.field_147328_g = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
      }

   }

   public void func_147315_a(CPacketEncryptionResponse p_147315_1_) {
      Validate.validState(this.field_147328_g == NetHandlerLoginServer.LoginState.KEY, "Unexpected key packet");
      PrivateKey privatekey = this.field_147327_f.func_71250_E().getPrivate();
      if (!Arrays.equals(this.field_147330_e, p_147315_1_.func_149299_b(privatekey))) {
         throw new IllegalStateException("Invalid nonce!");
      } else {
         this.field_147335_k = p_147315_1_.func_149300_a(privatekey);
         this.field_147328_g = NetHandlerLoginServer.LoginState.AUTHENTICATING;
         this.field_147333_a.func_150727_a(this.field_147335_k);
         (new Thread("User Authenticator #" + field_147331_b.incrementAndGet()) {
            public void run() {
               GameProfile gameprofile = NetHandlerLoginServer.this.field_147337_i;

               try {
                  String s = (new BigInteger(CryptManager.func_75895_a("", NetHandlerLoginServer.this.field_147327_f.func_71250_E().getPublic(), NetHandlerLoginServer.this.field_147335_k))).toString(16);
                  NetHandlerLoginServer.this.field_147337_i = NetHandlerLoginServer.this.field_147327_f.func_147130_as().hasJoinedServer(new GameProfile((UUID)null, gameprofile.getName()), s, this.func_191235_a());
                  if (NetHandlerLoginServer.this.field_147337_i != null) {
                     NetHandlerLoginServer.field_147332_c.info("UUID of player {} is {}", NetHandlerLoginServer.this.field_147337_i.getName(), NetHandlerLoginServer.this.field_147337_i.getId());
                     NetHandlerLoginServer.this.field_147328_g = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
                  } else if (NetHandlerLoginServer.this.field_147327_f.func_71264_H()) {
                     NetHandlerLoginServer.field_147332_c.warn("Failed to verify username but will let them in anyway!");
                     NetHandlerLoginServer.this.field_147337_i = NetHandlerLoginServer.this.func_152506_a(gameprofile);
                     NetHandlerLoginServer.this.field_147328_g = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
                  } else {
                     NetHandlerLoginServer.this.func_194026_b(new TextComponentTranslation("multiplayer.disconnect.unverified_username", new Object[0]));
                     NetHandlerLoginServer.field_147332_c.error("Username '{}' tried to join with an invalid session", (Object)gameprofile.getName());
                  }
               } catch (AuthenticationUnavailableException var3) {
                  if (NetHandlerLoginServer.this.field_147327_f.func_71264_H()) {
                     NetHandlerLoginServer.field_147332_c.warn("Authentication servers are down but will let them in anyway!");
                     NetHandlerLoginServer.this.field_147337_i = NetHandlerLoginServer.this.func_152506_a(gameprofile);
                     NetHandlerLoginServer.this.field_147328_g = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
                  } else {
                     NetHandlerLoginServer.this.func_194026_b(new TextComponentTranslation("multiplayer.disconnect.authservers_down", new Object[0]));
                     NetHandlerLoginServer.field_147332_c.error("Couldn't verify username because servers are unavailable");
                  }
               }

            }

            @Nullable
            private InetAddress func_191235_a() {
               SocketAddress socketaddress = NetHandlerLoginServer.this.field_147333_a.func_74430_c();
               return NetHandlerLoginServer.this.field_147327_f.func_190518_ac() && socketaddress instanceof InetSocketAddress ? ((InetSocketAddress)socketaddress).getAddress() : null;
            }
         }).start();
      }
   }

   protected GameProfile func_152506_a(GameProfile p_152506_1_) {
      UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + p_152506_1_.getName()).getBytes(StandardCharsets.UTF_8));
      return new GameProfile(uuid, p_152506_1_.getName());
   }

   static enum LoginState {
      HELLO,
      KEY,
      AUTHENTICATING,
      READY_TO_ACCEPT,
      DELAY_ACCEPT,
      ACCEPTED;
   }
}
