package net.minecraft.src;

import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

public class SpriteDependencies
{
    public static TextureAtlasSprite resolveDependencies(List<TextureAtlasSprite> p_resolveDependencies_0_, int p_resolveDependencies_1_, TextureMap p_resolveDependencies_2_)
    {
        TextureAtlasSprite textureatlassprite;

        for (textureatlassprite = p_resolveDependencies_0_.get(p_resolveDependencies_1_); resolveOne(p_resolveDependencies_0_, p_resolveDependencies_1_, textureatlassprite, p_resolveDependencies_2_); textureatlassprite = p_resolveDependencies_0_.get(p_resolveDependencies_1_))
        {
            ;
        }

        textureatlassprite.isDependencyParent = false;
        return textureatlassprite;
    }

    private static boolean resolveOne(List<TextureAtlasSprite> p_resolveOne_0_, int p_resolveOne_1_, TextureAtlasSprite p_resolveOne_2_, TextureMap p_resolveOne_3_)
    {
        int i = 0;

        for (ResourceLocation resourcelocation : p_resolveOne_2_.getDependencies())
        {
            TextureAtlasSprite textureatlassprite = p_resolveOne_3_.getRegisteredSprite(resourcelocation);

            if (textureatlassprite == null)
            {
                textureatlassprite = p_resolveOne_3_.registerSprite(resourcelocation);
            }
            else
            {
                int j = p_resolveOne_0_.indexOf(textureatlassprite);

                if (j <= p_resolveOne_1_ + i)
                {
                    continue;
                }

                if (textureatlassprite.isDependencyParent)
                {
                    String s = "circular dependency: " + p_resolveOne_2_.getIconName() + " -> " + textureatlassprite.getIconName();
                    ResourceLocation resourcelocation1 = p_resolveOne_3_.getResourceLocation(p_resolveOne_2_);
                    ReflectorForge.FMLClientHandler_trackBrokenTexture(resourcelocation1, s);
                    break;
                }

                p_resolveOne_0_.remove(j);
            }

            p_resolveOne_2_.isDependencyParent = true;
            p_resolveOne_0_.add(p_resolveOne_1_ + i, textureatlassprite);
            ++i;
        }

        return i > 0;
    }
}
