package shadersmod.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.src.StrUtils;

public class ShaderPackZip implements IShaderPack
{
    protected File packFile;
    protected ZipFile packZipFile;

    public ShaderPackZip(String name, File file)
    {
        this.packFile = file;
        this.packZipFile = null;
    }

    public void close()
    {
        if (this.packZipFile != null)
        {
            try
            {
                this.packZipFile.close();
            }
            catch (Exception var2)
            {
                ;
            }

            this.packZipFile = null;
        }
    }

    public InputStream getResourceAsStream(String resName)
    {
        try
        {
            if (this.packZipFile == null)
            {
                this.packZipFile = new ZipFile(this.packFile);
            }

            String s = StrUtils.removePrefix(resName, "/");
            ZipEntry zipentry = this.packZipFile.getEntry(s);
            return zipentry == null ? null : this.packZipFile.getInputStream(zipentry);
        }
        catch (Exception var4)
        {
            return null;
        }
    }

    public boolean hasDirectory(String resName)
    {
        try
        {
            if (this.packZipFile == null)
            {
                this.packZipFile = new ZipFile(this.packFile);
            }

            String s = StrUtils.removePrefix(resName, "/");
            ZipEntry zipentry = this.packZipFile.getEntry(s);
            return zipentry != null;
        }
        catch (IOException var4)
        {
            return false;
        }
    }

    public String getName()
    {
        return this.packFile.getName();
    }
}
