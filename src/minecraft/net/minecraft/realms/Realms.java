package net.minecraft.realms;

import com.google.common.util.concurrent.ListenableFuture;
import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Session;
import net.minecraft.world.GameType;

public class Realms
{
    public static boolean isTouchScreen()
    {
        return Minecraft.getMinecraft().gameSettings.touchscreen;
    }

    public static Proxy getProxy()
    {
        return Minecraft.getMinecraft().getProxy();
    }

    public static String sessionId()
    {
        Session session = Minecraft.getMinecraft().getSession();
        return session == null ? null : session.getSessionID();
    }

    public static String userName()
    {
        Session session = Minecraft.getMinecraft().getSession();
        return session == null ? null : session.getUsername();
    }

    public static long currentTimeMillis()
    {
        return Minecraft.getSystemTime();
    }

    public static String getSessionId()
    {
        return Minecraft.getMinecraft().getSession().getSessionID();
    }

    public static String getUUID()
    {
        return Minecraft.getMinecraft().getSession().getPlayerID();
    }

    public static String getName()
    {
        return Minecraft.getMinecraft().getSession().getUsername();
    }

    public static String uuidToName(String p_uuidToName_0_)
    {
        return Minecraft.getMinecraft().getSessionService().fillProfileProperties(new GameProfile(UUIDTypeAdapter.fromString(p_uuidToName_0_), (String)null), false).getName();
    }

    public static void setScreen(RealmsScreen p_setScreen_0_)
    {
        Minecraft.getMinecraft().displayGuiScreen(p_setScreen_0_.getProxy());
    }

    public static String getGameDirectoryPath()
    {
        return Minecraft.getMinecraft().mcDataDir.getAbsolutePath();
    }

    public static int survivalId()
    {
        return GameType.SURVIVAL.getID();
    }

    public static int creativeId()
    {
        return GameType.CREATIVE.getID();
    }

    public static int adventureId()
    {
        return GameType.ADVENTURE.getID();
    }

    public static int spectatorId()
    {
        return GameType.SPECTATOR.getID();
    }

    public static void setConnectedToRealms(boolean p_setConnectedToRealms_0_)
    {
        Minecraft.getMinecraft().setConnectedToRealms(p_setConnectedToRealms_0_);
    }

    public static ListenableFuture<Object> downloadResourcePack(String p_downloadResourcePack_0_, String p_downloadResourcePack_1_)
    {
        return Minecraft.getMinecraft().getResourcePackRepository().downloadResourcePack(p_downloadResourcePack_0_, p_downloadResourcePack_1_);
    }

    public static void clearResourcePack()
    {
        Minecraft.getMinecraft().getResourcePackRepository().clearResourcePack();
    }

    public static boolean getRealmsNotificationsEnabled()
    {
        return Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS);
    }

    public static boolean inTitleScreen()
    {
        return Minecraft.getMinecraft().currentScreen != null && Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu;
    }

    public static void deletePlayerTag(File p_deletePlayerTag_0_)
    {
        if (p_deletePlayerTag_0_.exists())
        {
            try
            {
                NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(p_deletePlayerTag_0_));
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
                nbttagcompound1.removeTag("Player");
                CompressedStreamTools.writeCompressed(nbttagcompound, new FileOutputStream(p_deletePlayerTag_0_));
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }
}
