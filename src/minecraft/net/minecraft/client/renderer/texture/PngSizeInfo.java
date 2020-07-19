package net.minecraft.client.renderer.texture;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.client.resources.IResource;
import org.apache.commons.io.IOUtils;

public class PngSizeInfo
{
    public final int pngWidth;
    public final int pngHeight;

    public PngSizeInfo(InputStream stream) throws IOException
    {
        DataInputStream datainputstream = new DataInputStream(stream);

        if (datainputstream.readLong() != -8552249625308161526L)
        {
            throw new IOException("Bad PNG Signature");
        }
        else if (datainputstream.readInt() != 13)
        {
            throw new IOException("Bad length for IHDR chunk!");
        }
        else if (datainputstream.readInt() != 1229472850)
        {
            throw new IOException("Bad type for IHDR chunk!");
        }
        else
        {
            this.pngWidth = datainputstream.readInt();
            this.pngHeight = datainputstream.readInt();
            IOUtils.closeQuietly((InputStream)datainputstream);
        }
    }

    public static PngSizeInfo makeFromResource(IResource resource) throws IOException
    {
        PngSizeInfo pngsizeinfo;

        try
        {
            pngsizeinfo = new PngSizeInfo(resource.getInputStream());
        }
        finally
        {
            IOUtils.closeQuietly((Closeable)resource);
        }

        return pngsizeinfo;
    }
}
