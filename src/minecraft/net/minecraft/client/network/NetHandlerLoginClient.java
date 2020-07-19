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

public class NetHandlerLoginClient implements INetHandlerLoginClient
{
    private static final Logger LOGGER = LogManager.getLogger();
    private final Minecraft mc;
    @Nullable
    private final GuiScreen previousGuiScreen;
    private final NetworkManager networkManager;
    private GameProfile gameProfile;

    public NetHandlerLoginClient(NetworkManager networkManagerIn, Minecraft mcIn, @Nullable GuiScreen previousScreenIn)
    {
        this.networkManager = networkManagerIn;
        this.mc = mcIn;
        this.previousGuiScreen = previousScreenIn;
    }

    public void handleEncryptionRequest(SPacketEncryptionRequest packetIn)
    {
        final SecretKey secretkey = CryptManager.createNewSharedKey();
        String s = packetIn.getServerId();
        PublicKey publickey = packetIn.getPublicKey();
        String s1 = (new BigInteger(CryptManager.getServerIdHash(s, publickey, secretkey))).toString(16);

        if (this.mc.getCurrentServerData() != null && this.mc.getCurrentServerData().isOnLAN())
        {
            try
            {
                this.getSessionService().joinServer(this.mc.getSession().getProfile(), this.mc.getSession().getToken(), s1);
            }
            catch (AuthenticationException var10)
            {
                LOGGER.warn("Couldn't connect to auth servers but will continue to join LAN");
            }
        }
        else
        {
            try
            {
                this.getSessionService().joinServer(this.mc.getSession().getProfile(), this.mc.getSession().getToken(), s1);
            }
            catch (AuthenticationUnavailableException var7)
            {
                this.networkManager.closeChannel(new TextComponentTranslation("disconnect.loginFailedInfo", new Object[] {new TextComponentTranslation("disconnect.loginFailedInfo.serversUnavailable", new Object[0])}));
                return;
            }
            catch (InvalidCredentialsException var8)
            {
                this.networkManager.closeChannel(new TextComponentTranslation("disconnect.loginFailedInfo", new Object[] {new TextComponentTranslation("disconnect.loginFailedInfo.invalidSession", new Object[0])}));
                return;
            }
            catch (AuthenticationException authenticationexception)
            {
                this.networkManager.closeChannel(new TextComponentTranslation("disconnect.loginFailedInfo", new Object[] {authenticationexception.getMessage()}));
                return;
            }
        }

        this.networkManager.sendPacket(new CPacketEncryptionResponse(secretkey, publickey, packetIn.getVerifyToken()), new GenericFutureListener < Future <? super Void >> ()
        {
            public void operationComplete(Future <? super Void > p_operationComplete_1_) throws Exception
            {
                NetHandlerLoginClient.this.networkManager.enableEncryption(secretkey);
            }
        });
    }

    private MinecraftSessionService getSessionService()
    {
        return this.mc.getSessionService();
    }

    public void handleLoginSuccess(SPacketLoginSuccess packetIn)
    {
        this.gameProfile = packetIn.getProfile();
        this.networkManager.setConnectionState(EnumConnectionState.PLAY);
        this.networkManager.setNetHandler(new NetHandlerPlayClient(this.mc, this.previousGuiScreen, this.networkManager, this.gameProfile));
    }

    /**
     * Invoked when disconnecting, the parameter is a ChatComponent describing the reason for termination
     */
    public void onDisconnect(ITextComponent reason)
    {
        if (this.previousGuiScreen != null && this.previousGuiScreen instanceof GuiScreenRealmsProxy)
        {
            this.mc.displayGuiScreen((new DisconnectedRealmsScreen(((GuiScreenRealmsProxy)this.previousGuiScreen).getProxy(), "connect.failed", reason)).getProxy());
        }
        else
        {
            this.mc.displayGuiScreen(new GuiDisconnected(this.previousGuiScreen, "connect.failed", reason));
        }
    }

    public void handleDisconnect(SPacketDisconnect packetIn)
    {
        this.networkManager.closeChannel(packetIn.getReason());
    }

    public void handleEnableCompression(SPacketEnableCompression packetIn)
    {
        if (!this.networkManager.isLocalChannel())
        {
            this.networkManager.setCompressionThreshold(packetIn.getCompressionThreshold());
        }
    }
}
