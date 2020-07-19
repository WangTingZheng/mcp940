package net.minecraft.client.resources;

import com.google.common.collect.ImmutableSet;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.src.ReflectorForge;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

public class DefaultResourcePack implements IResourcePack
{
    public static final Set<String> DEFAULT_RESOURCE_DOMAINS = ImmutableSet.<String>of("minecraft", "realms");
    private final ResourceIndex resourceIndex;
    private static final boolean ON_WINDOWS = Util.getOSType() == Util.EnumOS.WINDOWS;

    public DefaultResourcePack(ResourceIndex resourceIndexIn)
    {
        this.resourceIndex = resourceIndexIn;
    }

    public InputStream getInputStream(ResourceLocation location) throws IOException
    {
        InputStream inputstream = this.getResourceStream(location);

        if (inputstream != null)
        {
            return inputstream;
        }
        else
        {
            InputStream inputstream1 = this.getInputStreamAssets(location);

            if (inputstream1 != null)
            {
                return inputstream1;
            }
            else
            {
                throw new FileNotFoundException(location.getResourcePath());
            }
        }
    }

    @Nullable
    public InputStream getInputStreamAssets(ResourceLocation location) throws IOException, FileNotFoundException
    {
        File file1 = this.resourceIndex.getFile(location);
        return file1 != null && file1.isFile() ? new FileInputStream(file1) : null;
    }

    @Nullable
    private InputStream getResourceStream(ResourceLocation location)
    {
        String s = "/assets/" + location.getResourceDomain() + "/" + location.getResourcePath();
        InputStream inputstream = ReflectorForge.getOptiFineResourceStream(s);

        if (inputstream != null)
        {
            return inputstream;
        }
        else
        {
            try
            {
                URL url = DefaultResourcePack.class.getResource(s);
                return url != null && this.validatePath(new File(url.getFile()), s) ? DefaultResourcePack.class.getResourceAsStream(s) : null;
            }
            catch (IOException var5)
            {
                return DefaultResourcePack.class.getResourceAsStream(s);
            }
        }
    }

    public boolean resourceExists(ResourceLocation location)
    {
        return this.getResourceStream(location) != null || this.resourceIndex.isFileExisting(location);
    }

    public Set<String> getResourceDomains()
    {
        return DEFAULT_RESOURCE_DOMAINS;
    }

    @Nullable
    public <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException
    {
        try
        {
            InputStream inputstream = new FileInputStream(this.resourceIndex.getPackMcmeta());
            return (T)AbstractResourcePack.readMetadata(metadataSerializer, inputstream, metadataSectionName);
        }
        catch (RuntimeException var4)
        {
            return (T)(null);
        }
        catch (FileNotFoundException var51)
        {
            return (T)(null);
        }
    }

    public BufferedImage getPackImage() throws IOException
    {
        return TextureUtil.readBufferedImage(DefaultResourcePack.class.getResourceAsStream("/" + (new ResourceLocation("pack.png")).getResourcePath()));
    }

    public String getPackName()
    {
        return "Default";
    }

    private boolean validatePath(File p_validatePath_1_, String p_validatePath_2_) throws IOException
    {
        String s = p_validatePath_1_.getPath();

        if (s.startsWith("file:"))
        {
            if (ON_WINDOWS)
            {
                s = s.replace("\\", "/");
            }

            return s.endsWith(p_validatePath_2_);
        }
        else
        {
            return FolderResourcePack.validatePath(p_validatePath_1_, p_validatePath_2_);
        }
    }
}
