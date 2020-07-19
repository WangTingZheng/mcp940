package net.minecraft.src;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.LegacyV2Adapter;
import net.minecraft.util.ResourceLocation;

public class ResUtils
{
    public static String[] collectFiles(String p_collectFiles_0_, String p_collectFiles_1_)
    {
        return collectFiles(new String[] {p_collectFiles_0_}, new String[] {p_collectFiles_1_});
    }

    public static String[] collectFiles(String[] p_collectFiles_0_, String[] p_collectFiles_1_)
    {
        Set<String> set = new LinkedHashSet<String>();
        IResourcePack[] airesourcepack = Config.getResourcePacks();

        for (int i = 0; i < airesourcepack.length; ++i)
        {
            IResourcePack iresourcepack = airesourcepack[i];
            String[] astring = collectFiles(iresourcepack, p_collectFiles_0_, p_collectFiles_1_, (String[])null);
            set.addAll(Arrays.asList(astring));
        }

        String[] astring1 = (String[])set.toArray(new String[set.size()]);
        return astring1;
    }

    public static String[] collectFiles(IResourcePack p_collectFiles_0_, String p_collectFiles_1_, String p_collectFiles_2_, String[] p_collectFiles_3_)
    {
        return collectFiles(p_collectFiles_0_, new String[] {p_collectFiles_1_}, new String[] {p_collectFiles_2_}, p_collectFiles_3_);
    }

    public static String[] collectFiles(IResourcePack p_collectFiles_0_, String[] p_collectFiles_1_, String[] p_collectFiles_2_)
    {
        return collectFiles(p_collectFiles_0_, p_collectFiles_1_, p_collectFiles_2_, (String[])null);
    }

    public static String[] collectFiles(IResourcePack p_collectFiles_0_, String[] p_collectFiles_1_, String[] p_collectFiles_2_, String[] p_collectFiles_3_)
    {
        if (p_collectFiles_0_ instanceof DefaultResourcePack)
        {
            return collectFilesFixed(p_collectFiles_0_, p_collectFiles_3_);
        }
        else
        {
            if (p_collectFiles_0_ instanceof LegacyV2Adapter)
            {
                IResourcePack iresourcepack = (IResourcePack)Reflector.getFieldValue(p_collectFiles_0_, Reflector.LegacyV2Adapter_pack);

                if (iresourcepack == null)
                {
                    Config.warn("LegacyV2Adapter base resource pack not found: " + p_collectFiles_0_);
                    return new String[0];
                }

                p_collectFiles_0_ = iresourcepack;
            }

            if (!(p_collectFiles_0_ instanceof AbstractResourcePack))
            {
                Config.warn("Unknown resource pack type: " + p_collectFiles_0_);
                return new String[0];
            }
            else
            {
                AbstractResourcePack abstractresourcepack = (AbstractResourcePack)p_collectFiles_0_;
                File file1 = abstractresourcepack.resourcePackFile;

                if (file1 == null)
                {
                    return new String[0];
                }
                else if (file1.isDirectory())
                {
                    return collectFilesFolder(file1, "", p_collectFiles_1_, p_collectFiles_2_);
                }
                else if (file1.isFile())
                {
                    return collectFilesZIP(file1, p_collectFiles_1_, p_collectFiles_2_);
                }
                else
                {
                    Config.warn("Unknown resource pack file: " + file1);
                    return new String[0];
                }
            }
        }
    }

    private static String[] collectFilesFixed(IResourcePack p_collectFilesFixed_0_, String[] p_collectFilesFixed_1_)
    {
        if (p_collectFilesFixed_1_ == null)
        {
            return new String[0];
        }
        else
        {
            List list = new ArrayList();

            for (int i = 0; i < p_collectFilesFixed_1_.length; ++i)
            {
                String s = p_collectFilesFixed_1_[i];

                if (!isLowercase(s))
                {
                    Config.warn("Skipping non-lowercase path: " + s);
                }
                else
                {
                    ResourceLocation resourcelocation = new ResourceLocation(s);

                    if (p_collectFilesFixed_0_.resourceExists(resourcelocation))
                    {
                        list.add(s);
                    }
                }
            }

            String[] astring = (String[])list.toArray(new String[list.size()]);
            return astring;
        }
    }

    private static String[] collectFilesFolder(File p_collectFilesFolder_0_, String p_collectFilesFolder_1_, String[] p_collectFilesFolder_2_, String[] p_collectFilesFolder_3_)
    {
        List list = new ArrayList();
        String s = "assets/minecraft/";
        File[] afile = p_collectFilesFolder_0_.listFiles();

        if (afile == null)
        {
            return new String[0];
        }
        else
        {
            for (int i = 0; i < afile.length; ++i)
            {
                File file1 = afile[i];

                if (file1.isFile())
                {
                    String s3 = p_collectFilesFolder_1_ + file1.getName();

                    if (s3.startsWith(s))
                    {
                        s3 = s3.substring(s.length());

                        if (StrUtils.startsWith(s3, p_collectFilesFolder_2_) && StrUtils.endsWith(s3, p_collectFilesFolder_3_))
                        {
                            if (!isLowercase(s3))
                            {
                                Config.warn("Skipping non-lowercase path: " + s3);
                            }
                            else
                            {
                                list.add(s3);
                            }
                        }
                    }
                }
                else if (file1.isDirectory())
                {
                    String s1 = p_collectFilesFolder_1_ + file1.getName() + "/";
                    String[] astring = collectFilesFolder(file1, s1, p_collectFilesFolder_2_, p_collectFilesFolder_3_);

                    for (int j = 0; j < astring.length; ++j)
                    {
                        String s2 = astring[j];
                        list.add(s2);
                    }
                }
            }

            String[] astring1 = (String[])list.toArray(new String[list.size()]);
            return astring1;
        }
    }

    private static String[] collectFilesZIP(File p_collectFilesZIP_0_, String[] p_collectFilesZIP_1_, String[] p_collectFilesZIP_2_)
    {
        List list = new ArrayList();
        String s = "assets/minecraft/";

        try
        {
            ZipFile zipfile = new ZipFile(p_collectFilesZIP_0_);
            Enumeration enumeration = zipfile.entries();

            while (enumeration.hasMoreElements())
            {
                ZipEntry zipentry = (ZipEntry)enumeration.nextElement();
                String s1 = zipentry.getName();

                if (s1.startsWith(s))
                {
                    s1 = s1.substring(s.length());

                    if (StrUtils.startsWith(s1, p_collectFilesZIP_1_) && StrUtils.endsWith(s1, p_collectFilesZIP_2_))
                    {
                        if (!isLowercase(s1))
                        {
                            Config.warn("Skipping non-lowercase path: " + s1);
                        }
                        else
                        {
                            list.add(s1);
                        }
                    }
                }
            }

            zipfile.close();
            String[] astring = (String[])list.toArray(new String[list.size()]);
            return astring;
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
            return new String[0];
        }
    }

    private static boolean isLowercase(String p_isLowercase_0_)
    {
        return p_isLowercase_0_.equals(p_isLowercase_0_.toLowerCase(Locale.ROOT));
    }
}
