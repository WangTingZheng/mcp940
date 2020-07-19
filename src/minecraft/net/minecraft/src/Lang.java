package net.minecraft.src;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

public class Lang
{
    private static final Splitter splitter = Splitter.on('=').limit(2);
    private static final Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");

    public static void resourcesReloaded()
    {
        Map map = I18n.getLocaleProperties();
        List<String> list = new ArrayList<String>();
        String s = "optifine/lang/";
        String s1 = "en_us";
        String s2 = ".lang";
        list.add(s + s1 + s2);

        if (!Config.getGameSettings().language.equals(s1))
        {
            list.add(s + Config.getGameSettings().language + s2);
        }

        String[] astring = (String[])list.toArray(new String[list.size()]);
        loadResources(Config.getDefaultResourcePack(), astring, map);
        IResourcePack[] airesourcepack = Config.getResourcePacks();

        for (int i = 0; i < airesourcepack.length; ++i)
        {
            IResourcePack iresourcepack = airesourcepack[i];
            loadResources(iresourcepack, astring, map);
        }
    }

    private static void loadResources(IResourcePack p_loadResources_0_, String[] p_loadResources_1_, Map p_loadResources_2_)
    {
        try
        {
            for (int i = 0; i < p_loadResources_1_.length; ++i)
            {
                String s = p_loadResources_1_[i];
                ResourceLocation resourcelocation = new ResourceLocation(s);

                if (p_loadResources_0_.resourceExists(resourcelocation))
                {
                    InputStream inputstream = p_loadResources_0_.getInputStream(resourcelocation);

                    if (inputstream != null)
                    {
                        loadLocaleData(inputstream, p_loadResources_2_);
                    }
                }
            }
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    public static void loadLocaleData(InputStream p_loadLocaleData_0_, Map p_loadLocaleData_1_) throws IOException
    {
        for (String s : IOUtils.readLines(p_loadLocaleData_0_, Charsets.UTF_8))
        {
            if (!s.isEmpty() && s.charAt(0) != '#')
            {
                String[] astring = (String[])Iterables.toArray(splitter.split(s), String.class);

                if (astring != null && astring.length == 2)
                {
                    String s1 = astring[0];
                    String s2 = pattern.matcher(astring[1]).replaceAll("%$1s");
                    p_loadLocaleData_1_.put(s1, s2);
                }
            }
        }
    }

    public static String get(String p_get_0_)
    {
        return I18n.format(p_get_0_);
    }

    public static String get(String p_get_0_, String p_get_1_)
    {
        String s = I18n.format(p_get_0_);
        return s != null && !s.equals(p_get_0_) ? s : p_get_1_;
    }

    public static String getOn()
    {
        return I18n.format("options.on");
    }

    public static String getOff()
    {
        return I18n.format("options.off");
    }

    public static String getFast()
    {
        return I18n.format("options.graphics.fast");
    }

    public static String getFancy()
    {
        return I18n.format("options.graphics.fancy");
    }

    public static String getDefault()
    {
        return I18n.format("generator.default");
    }
}
