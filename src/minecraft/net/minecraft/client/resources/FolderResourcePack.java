package net.minecraft.client.resources;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Sets;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.Util;
import org.apache.commons.io.filefilter.DirectoryFileFilter;

public class FolderResourcePack extends AbstractResourcePack
{
    private static final boolean ON_WINDOWS = Util.getOSType() == Util.EnumOS.WINDOWS;
    private static final CharMatcher BACKSLASH_MATCHER = CharMatcher.is('\\');

    public FolderResourcePack(File resourcePackFileIn)
    {
        super(resourcePackFileIn);
    }

    protected static boolean validatePath(File p_191384_0_, String p_191384_1_) throws IOException
    {
        String s = p_191384_0_.getCanonicalPath();

        if (ON_WINDOWS)
        {
            s = BACKSLASH_MATCHER.replaceFrom(s, '/');
        }

        return s.endsWith(p_191384_1_);
    }

    protected InputStream getInputStreamByName(String name) throws IOException
    {
        File file1 = this.getFile(name);

        if (file1 == null)
        {
            throw new ResourcePackFileNotFoundException(this.resourcePackFile, name);
        }
        else
        {
            return new BufferedInputStream(new FileInputStream(file1));
        }
    }

    protected boolean hasResourceName(String name)
    {
        return this.getFile(name) != null;
    }

    @Nullable
    private File getFile(String p_191385_1_)
    {
        try
        {
            File file1 = new File(this.resourcePackFile, p_191385_1_);

            if (file1.isFile() && validatePath(file1, p_191385_1_))
            {
                return file1;
            }
        }
        catch (IOException var3)
        {
            ;
        }

        return null;
    }

    public Set<String> getResourceDomains()
    {
        Set<String> set = Sets.<String>newHashSet();
        File file1 = new File(this.resourcePackFile, "assets/");

        if (file1.isDirectory())
        {
            for (File file2 : file1.listFiles((FileFilter)DirectoryFileFilter.DIRECTORY))
            {
                String s = getRelativeName(file1, file2);

                if (s.equals(s.toLowerCase(java.util.Locale.ROOT)))
                {
                    set.add(s.substring(0, s.length() - 1));
                }
                else
                {
                    this.logNameNotLowercase(s);
                }
            }
        }

        return set;
    }
}
