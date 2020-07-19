package net.minecraft.client.resources;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileResourcePack extends AbstractResourcePack implements Closeable
{
    public static final Splitter ENTRY_NAME_SPLITTER = Splitter.on('/').omitEmptyStrings().limit(3);
    private ZipFile resourcePackZipFile;

    public FileResourcePack(File resourcePackFileIn)
    {
        super(resourcePackFileIn);
    }

    private ZipFile getResourcePackZipFile() throws IOException
    {
        if (this.resourcePackZipFile == null)
        {
            this.resourcePackZipFile = new ZipFile(this.resourcePackFile);
        }

        return this.resourcePackZipFile;
    }

    protected InputStream getInputStreamByName(String name) throws IOException
    {
        ZipFile zipfile = this.getResourcePackZipFile();
        ZipEntry zipentry = zipfile.getEntry(name);

        if (zipentry == null)
        {
            throw new ResourcePackFileNotFoundException(this.resourcePackFile, name);
        }
        else
        {
            return zipfile.getInputStream(zipentry);
        }
    }

    public boolean hasResourceName(String name)
    {
        try
        {
            return this.getResourcePackZipFile().getEntry(name) != null;
        }
        catch (IOException var3)
        {
            return false;
        }
    }

    public Set<String> getResourceDomains()
    {
        ZipFile zipfile;

        try
        {
            zipfile = this.getResourcePackZipFile();
        }
        catch (IOException var8)
        {
            return Collections.<String>emptySet();
        }

        Enumeration <? extends ZipEntry > enumeration = zipfile.entries();
        Set<String> set = Sets.<String>newHashSet();

        while (enumeration.hasMoreElements())
        {
            ZipEntry zipentry = enumeration.nextElement();
            String s = zipentry.getName();

            if (s.startsWith("assets/"))
            {
                List<String> list = Lists.newArrayList(ENTRY_NAME_SPLITTER.split(s));

                if (list.size() > 1)
                {
                    String s1 = list.get(1);

                    if (s1.equals(s1.toLowerCase(java.util.Locale.ROOT)))
                    {
                        set.add(s1);
                    }
                    else
                    {
                        this.logNameNotLowercase(s1);
                    }
                }
            }
        }

        return set;
    }

    protected void finalize() throws Throwable
    {
        this.close();
        super.finalize();
    }

    public void close() throws IOException
    {
        if (this.resourcePackZipFile != null)
        {
            this.resourcePackZipFile.close();
            this.resourcePackZipFile = null;
        }
    }
}
