package net.minecraft.client.network;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.math.BigInteger;
import java.security.PublicKey;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenRealmsProxy;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.network.login.client.CPacketEncryptionResponse;
import net.minecraft.network.login.server.SPacketDisconnect;
import net.minecraft.network.login.server.SPacketEnableCompression;
import net.minecraft.network.login.server.SPacketEncryptionRequest;
import net.minecraft.network.login.server.SPacketLoginSuccess;
import net.minecraft.realms.DisconnectedRealmsScreen;
import net.minecraft.util.CryptManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerLoginClient implements INetHandlerLoginClient {
   private static final Logger field_147396_a = LogManager.getLogger();
   private final Minecraft field_147394_b;
   @Nullable
   private final GuiScreen field_147395_c;
   private final NetworkManager field_147393_d;
   private GameProfile field_175091_e;

   public NetHandlerLoginClient(NetworkManager p_i45059_1_, Minecraft p_i45059_2_, @Nullable GuiScreen p_i45059_3_) {
      this.field_147393_d = p_i45059_1_;
      this.field_147394_b = p_i45059_2_;
      this.field_147395_c = p_i45059_3_;
   }

   public void func_147389_a(SPacketEncryptionRequest p_147389_1_) {
      final SecretKey secretkey = CryptManager.func_75890_a();
      String s = p_147389_1_.func_149609_c();
      PublicKey publickey = p_147389_1_.func_149608_d();
      String s1 = (new BigInteger(CryptManager.func_75895_a(s, publickey, secretkey))).toString(16);
      if (this.field_147394_b.func_147104_D() != null && this.field_147394_b.func_147104_D().func_181041_d()) {
         try {
            this.func_147391_c().joinServer(this.field_147394_b.func_110432_I().func_148256_e(), this.field_147394_b.func_110432_I().func_148254_d(), s1);
         } catch (AuthenticationException var10) {
            field_147396_a.warn("Couldn't connect to auth servers but will continue to join LAN");
         }
      } else {
         try {
            this.func_147391_c().joinServer(this.field_147394_b.func_110432_I().func_148256_e(), this.field_147394_b.func_110432_I().func_148254_d(), s1);
         } catch (AuthenticationUnavailableException var7) {
            this.field_147393_d.func_150718_a(new TextComponentTranslation("disconnect.loginFailedInfo", new Object[]{new TextComponentTranslation("disconnect.loginFailedInfo.serversUnavailable", new Object[0])}));
            return;
         } catch (InvalidCredentialsException var8) {
            this.field_147393_d.func_150718_a(new TextComponentTranslation("disconnect.loginFailedInfo", new Object[]{new TextComponentTranslation("disconnect.loginFailedInfo.invalidSession", new Object[0])}));
            return;
         } catch (AuthenticationException authenticationexception) {
            this.field_147393_d.func_150718_a(new TextComponentTranslation("disconnect.loginFailedInfo", new Object[]{authenticationexception.getMessage()}));
            return;
         }
      }

      this.field_147393_d.func_179288_a(new CPacketEncryptionResponse(secretkey, publickey, p_147389_1_.func_149607_e()), new GenericFutureListener<Future<? super Void>>() {
         public void operationComplete(Future<? super Void> p_operationComplete_1_) throws Exception {
            NetHandlerLoginClient.this.field_147393_d.func_150727_a(secretkey);
         }
      });
   }

   private MinecraftSessionService func_147391_c() {
      return this.field_147394_b.func_152347_ac();
   }

   public void func_147390_a(SPacketLoginSuccess p_147390_1_) {
      this.field_175091_e = p_147390_1_.func_179730_a();
      this.field_147393_d.func_150723_a(EnumConnectionState.PLAY);
      this.field_147393_d.func_150719_a(new NetHandlerPlayClient(this.field_147394_b, this.field_147395_c, this.field_147393_d, this.field_175091_e));
   }

   public void func_147231_a(ITextComponent p_147231_1_) {
      if (this.field_147395_c != null && this.field_147395_c instanceof GuiScreenRealmsProxy) {
         this.field_147394_b.func_147108_a((new DisconnectedRealmsScreen(((GuiScreenRealmsProxy)this.field_147395_c).func_154321_a(), "connect.failed", p_147231_1_)).getProxy());
      } else {
         this.field_147394_b.func_147108_a(new GuiDisconnected(this.field_147395_c, "connect.failed", p_147231_1_));
      }

   }

   public void func_147388_a(SPacketDisconnect p_147388_1_) {
      this.field_147393_d.func_150718_a(p_147388_1_.func_149603_c());
   }

   public void func_180464_a(SPacketEnableCompression p_180464_1_) {
      if (!this.field_147393_d.func_150731_c()) {
         this.field_147393_d.func_179289_a(p_180464_1_.func_179731_a());
      }

   }
}
