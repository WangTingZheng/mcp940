package net.minecraft.src;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class PlayerConfigurationParser
{
    private String player = null;
    public static final String CONFIG_ITEMS = "items";
    public static final String ITEM_TYPE = "type";
    public static final String ITEM_ACTIVE = "active";

    public PlayerConfigurationParser(String p_i67_1_)
    {
        this.player = p_i67_1_;
    }

    public PlayerConfiguration parsePlayerConfiguration(JsonElement p_parsePlayerConfiguration_1_)
    {
        if (p_parsePlayerConfiguration_1_ == null)
        {
            throw new JsonParseException("JSON object is null, player: " + this.player);
        }
        else
        {
            JsonObject jsonobject = (JsonObject)p_parsePlayerConfiguration_1_;
            PlayerConfiguration playerconfiguration = new PlayerConfiguration();
            JsonArray jsonarray = (JsonArray)jsonobject.get("items");

            if (jsonarray != null)
            {
                for (int i = 0; i < jsonarray.size(); ++i)
                {
                    JsonObject jsonobject1 = (JsonObject)jsonarray.get(i);
                    boolean flag = Json.getBoolean(jsonobject1, "active", true);

                    if (flag)
                    {
                        String s = Json.getString(jsonobject1, "type");

                        if (s == null)
                        {
                            Config.warn("Item type is null, player: " + this.player);
                        }
                        else
                        {
                            String s1 = Json.getString(jsonobject1, "model");

                            if (s1 == null)
                            {
                                s1 = "items/" + s + "/model.cfg";
                            }

                            PlayerItemModel playeritemmodel = this.downloadModel(s1);

                            if (playeritemmodel != null)
                            {
                                if (!playeritemmodel.isUsePlayerTexture())
                                {
                                    String s2 = Json.getString(jsonobject1, "texture");

                                    if (s2 == null)
                                    {
                                        s2 = "items/" + s + "/users/" + this.player + ".png";
                                    }

                                    BufferedImage bufferedimage = this.downloadTextureImage(s2);

                                    if (bufferedimage == null)
                                    {
                                        continue;
                                    }

                                    playeritemmodel.setTextureImage(bufferedimage);
                                    ResourceLocation resourcelocation = new ResourceLocation("optifine.net", s2);
                                    playeritemmodel.setTextureLocation(resourcelocation);
                                }

                                playerconfiguration.addPlayerItemModel(playeritemmodel);
                            }
                        }
                    }
                }
            }

            return playerconfiguration;
        }
    }

    private BufferedImage downloadTextureImage(String p_downloadTextureImage_1_)
    {
        String s = "http://s.optifine.net/" + p_downloadTextureImage_1_;

        try
        {
            byte[] abyte = HttpPipeline.get(s, Minecraft.getMinecraft().getProxy());
            BufferedImage bufferedimage = ImageIO.read(new ByteArrayInputStream(abyte));
            return bufferedimage;
        }
        catch (IOException ioexception)
        {
            Config.warn("Error loading item texture " + p_downloadTextureImage_1_ + ": " + ioexception.getClass().getName() + ": " + ioexception.getMessage());
            return null;
        }
    }

    private PlayerItemModel downloadModel(String p_downloadModel_1_)
    {
        String s = "http://s.optifine.net/" + p_downloadModel_1_;

        try
        {
            byte[] abyte = HttpPipeline.get(s, Minecraft.getMinecraft().getProxy());
            String s1 = new String(abyte, "ASCII");
            JsonParser jsonparser = new JsonParser();
            JsonObject jsonobject = (JsonObject)jsonparser.parse(s1);
            PlayerItemModel playeritemmodel = PlayerItemParser.parseItemModel(jsonobject);
            return playeritemmodel;
        }
        catch (Exception exception)
        {
            Config.warn("Error loading item model " + p_downloadModel_1_ + ": " + exception.getClass().getName() + ": " + exception.getMessage());
            return null;
        }
    }
}
