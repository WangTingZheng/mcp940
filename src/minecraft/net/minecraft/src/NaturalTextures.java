package net.minecraft.src;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class NaturalTextures
{
    private static NaturalProperties[] propertiesByIndex = new NaturalProperties[0];

    public static void update()
    {
        propertiesByIndex = new NaturalProperties[0];

        if (Config.isNaturalTextures())
        {
            String s = "optifine/natural.properties";

            try
            {
                ResourceLocation resourcelocation = new ResourceLocation(s);

                if (!Config.hasResource(resourcelocation))
                {
                    Config.dbg("NaturalTextures: configuration \"" + s + "\" not found");
                    return;
                }

                boolean flag = Config.isFromDefaultResourcePack(resourcelocation);
                InputStream inputstream = Config.getResourceStream(resourcelocation);
                ArrayList arraylist = new ArrayList(256);
                String s1 = Config.readInputStream(inputstream);
                inputstream.close();
                String[] astring = Config.tokenize(s1, "\n\r");

                if (flag)
                {
                    Config.dbg("Natural Textures: Parsing default configuration \"" + s + "\"");
                    Config.dbg("Natural Textures: Valid only for textures from default resource pack");
                }
                else
                {
                    Config.dbg("Natural Textures: Parsing configuration \"" + s + "\"");
                }

                TextureMap texturemap = TextureUtils.getTextureMapBlocks();

                for (int i = 0; i < astring.length; ++i)
                {
                    String s2 = astring[i].trim();

                    if (!s2.startsWith("#"))
                    {
                        String[] astring1 = Config.tokenize(s2, "=");

                        if (astring1.length != 2)
                        {
                            Config.warn("Natural Textures: Invalid \"" + s + "\" line: " + s2);
                        }
                        else
                        {
                            String s3 = astring1[0].trim();
                            String s4 = astring1[1].trim();
                            TextureAtlasSprite textureatlassprite = texturemap.getSpriteSafe("minecraft:blocks/" + s3);

                            if (textureatlassprite == null)
                            {
                                Config.warn("Natural Textures: Texture not found: \"" + s + "\" line: " + s2);
                            }
                            else
                            {
                                int j = textureatlassprite.getIndexInMap();

                                if (j < 0)
                                {
                                    Config.warn("Natural Textures: Invalid \"" + s + "\" line: " + s2);
                                }
                                else
                                {
                                    if (flag && !Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/" + s3 + ".png")))
                                    {
                                        return;
                                    }

                                    NaturalProperties naturalproperties = new NaturalProperties(s4);

                                    if (naturalproperties.isValid())
                                    {
                                        while (arraylist.size() <= j)
                                        {
                                            arraylist.add((Object)null);
                                        }

                                        arraylist.set(j, naturalproperties);
                                        Config.dbg("NaturalTextures: " + s3 + " = " + s4);
                                    }
                                }
                            }
                        }
                    }
                }

                propertiesByIndex = (NaturalProperties[])arraylist.toArray(new NaturalProperties[arraylist.size()]);
            }
            catch (FileNotFoundException var17)
            {
                Config.warn("NaturalTextures: configuration \"" + s + "\" not found");
                return;
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }

    public static BakedQuad getNaturalTexture(BlockPos p_getNaturalTexture_0_, BakedQuad p_getNaturalTexture_1_)
    {
        TextureAtlasSprite textureatlassprite = p_getNaturalTexture_1_.getSprite();

        if (textureatlassprite == null)
        {
            return p_getNaturalTexture_1_;
        }
        else
        {
            NaturalProperties naturalproperties = getNaturalProperties(textureatlassprite);

            if (naturalproperties == null)
            {
                return p_getNaturalTexture_1_;
            }
            else
            {
                int i = ConnectedTextures.getSide(p_getNaturalTexture_1_.getFace());
                int j = Config.getRandom(p_getNaturalTexture_0_, i);
                int k = 0;
                boolean flag = false;

                if (naturalproperties.rotation > 1)
                {
                    k = j & 3;
                }

                if (naturalproperties.rotation == 2)
                {
                    k = k / 2 * 2;
                }

                if (naturalproperties.flip)
                {
                    flag = (j & 4) != 0;
                }

                return naturalproperties.getQuad(p_getNaturalTexture_1_, k, flag);
            }
        }
    }

    public static NaturalProperties getNaturalProperties(TextureAtlasSprite p_getNaturalProperties_0_)
    {
        if (!(p_getNaturalProperties_0_ instanceof TextureAtlasSprite))
        {
            return null;
        }
        else
        {
            int i = p_getNaturalProperties_0_.getIndexInMap();

            if (i >= 0 && i < propertiesByIndex.length)
            {
                NaturalProperties naturalproperties = propertiesByIndex[i];
                return naturalproperties;
            }
            else
            {
                return null;
            }
        }
    }
}
