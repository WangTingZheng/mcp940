package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;

public class PlayerConfigurations
{
    private static Map mapConfigurations = null;
    private static boolean reloadPlayerItems = Boolean.getBoolean("player.items.reload");
    private static long timeReloadPlayerItemsMs = System.currentTimeMillis();

    public static void renderPlayerItems(ModelBiped p_renderPlayerItems_0_, AbstractClientPlayer p_renderPlayerItems_1_, float p_renderPlayerItems_2_, float p_renderPlayerItems_3_)
    {
        PlayerConfiguration playerconfiguration = getPlayerConfiguration(p_renderPlayerItems_1_);

        if (playerconfiguration != null)
        {
            playerconfiguration.renderPlayerItems(p_renderPlayerItems_0_, p_renderPlayerItems_1_, p_renderPlayerItems_2_, p_renderPlayerItems_3_);
        }
    }

    public static synchronized PlayerConfiguration getPlayerConfiguration(AbstractClientPlayer p_getPlayerConfiguration_0_)
    {
        if (reloadPlayerItems && System.currentTimeMillis() > timeReloadPlayerItemsMs + 5000L)
        {
            AbstractClientPlayer abstractclientplayer = Minecraft.getMinecraft().player;

            if (abstractclientplayer != null)
            {
                setPlayerConfiguration(abstractclientplayer.getNameClear(), (PlayerConfiguration)null);
                timeReloadPlayerItemsMs = System.currentTimeMillis();
            }
        }

        String s1 = p_getPlayerConfiguration_0_.getNameClear();

        if (s1 == null)
        {
            return null;
        }
        else
        {
            PlayerConfiguration playerconfiguration = (PlayerConfiguration)getMapConfigurations().get(s1);

            if (playerconfiguration == null)
            {
                playerconfiguration = new PlayerConfiguration();
                getMapConfigurations().put(s1, playerconfiguration);
                PlayerConfigurationReceiver playerconfigurationreceiver = new PlayerConfigurationReceiver(s1);
                String s = "http://s.optifine.net/users/" + s1 + ".cfg";
                FileDownloadThread filedownloadthread = new FileDownloadThread(s, playerconfigurationreceiver);
                filedownloadthread.start();
            }

            return playerconfiguration;
        }
    }

    public static synchronized void setPlayerConfiguration(String p_setPlayerConfiguration_0_, PlayerConfiguration p_setPlayerConfiguration_1_)
    {
        getMapConfigurations().put(p_setPlayerConfiguration_0_, p_setPlayerConfiguration_1_);
    }

    private static Map getMapConfigurations()
    {
        if (mapConfigurations == null)
        {
            mapConfigurations = new HashMap();
        }

        return mapConfigurations;
    }
}
