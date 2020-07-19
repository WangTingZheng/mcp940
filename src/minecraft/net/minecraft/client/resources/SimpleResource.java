package net.minecraft.client.resources;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

public class SimpleResource implements IResource
{
    private final Map<String, IMetadataSection> mapMetadataSections = Maps.<String, IMetadataSection>newHashMap();
    private final String resourcePackName;
    private final ResourceLocation srResourceLocation;
    private final InputStream resourceInputStream;
    private final InputStream mcmetaInputStream;
    private final MetadataSerializer srMetadataSerializer;
    private boolean mcmetaJsonChecked;
    private JsonObject mcmetaJson;

    public SimpleResource(String resourcePackNameIn, ResourceLocation srResourceLocationIn, InputStream resourceInputStreamIn, InputStream mcmetaInputStreamIn, MetadataSerializer srMetadataSerializerIn)
    {
        this.resourcePackName = resourcePackNameIn;
        this.srResourceLocation = srResourceLocationIn;
        this.resourceInputStream = resourceInputStreamIn;
        this.mcmetaInputStream = mcmetaInputStreamIn;
        this.srMetadataSerializer = srMetadataSerializerIn;
    }

    public ResourceLocation getResourceLocation()
    {
        return this.srResourceLocation;
    }

    public InputStream getInputStream()
    {
        return this.resourceInputStream;
    }

    public boolean hasMetadata()
    {
        return this.mcmetaInputStream != null;
    }

    @Nullable
    public <T extends IMetadataSection> T getMetadata(String sectionName)
    {
        if (!this.hasMetadata())
        {
            return (T)null;
        }
        else
        {
            if (this.mcmetaJson == null && !this.mcmetaJsonChecked)
            {
                this.mcmetaJsonChecked = true;
                BufferedReader bufferedreader = null;

                try
                {
                    bufferedreader = new BufferedReader(new InputStreamReader(this.mcmetaInputStream, StandardCharsets.UTF_8));
                    this.mcmetaJson = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
                }
                finally
                {
                    IOUtils.closeQuietly((Reader)bufferedreader);
                }
            }

            T t = (T)this.mapMetadataSections.get(sectionName);

            if (t == null)
            {
                t = this.srMetadataSerializer.parseMetadataSection(sectionName, this.mcmetaJson);
            }

            return t;
        }
    }

    public String getResourcePackName()
    {
        return this.resourcePackName;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (!(p_equals_1_ instanceof SimpleResource))
        {
            return false;
        }
        else
        {
            SimpleResource simpleresource = (SimpleResource)p_equals_1_;

            if (this.srResourceLocation != null)
            {
                if (!this.srResourceLocation.equals(simpleresource.srResourceLocation))
                {
                    return false;
                }
            }
            else if (simpleresource.srResourceLocation != null)
            {
                return false;
            }

            if (this.resourcePackName != null)
            {
                if (!this.resourcePackName.equals(simpleresource.resourcePackName))
                {
                    return false;
                }
            }
            else if (simpleresource.resourcePackName != null)
            {
                return false;
            }

            return true;
        }
    }

    public int hashCode()
    {
        int i = this.resourcePackName != null ? this.resourcePackName.hashCode() : 0;
        i = 31 * i + (this.srResourceLocation != null ? this.srResourceLocation.hashCode() : 0);
        return i;
    }

    public void close() throws IOException
    {
        this.resourceInputStream.close();

        if (this.mcmetaInputStream != null)
        {
            this.mcmetaInputStream.close();
        }
    }
}
