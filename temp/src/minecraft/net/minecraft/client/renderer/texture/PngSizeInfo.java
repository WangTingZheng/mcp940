package net.minecraft.client.renderer.texture;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.client.resources.IResource;
import org.apache.commons.io.IOUtils;

public class PngSizeInfo {
   public final int field_188533_a;
   public final int field_188534_b;

   public PngSizeInfo(InputStream p_i46543_1_) throws IOException {
      DataInputStream datainputstream = new DataInputStream(p_i46543_1_);
      if (datainputstream.readLong() != -8552249625308161526L) {
         throw new IOException("Bad PNG Signature");
      } else if (datainputstream.readInt() != 13) {
         throw new IOException("Bad length for IHDR chunk!");
      } else if (datainputstream.readInt() != 1229472850) {
         throw new IOException("Bad type for IHDR chunk!");
      } else {
         this.field_188533_a = datainputstream.readInt();
         this.field_188534_b = datainputstream.readInt();
         IOUtils.closeQuietly((InputStream)datainputstream);
      }
   }

   public static PngSizeInfo func_188532_a(IResource p_188532_0_) throws IOException {
      PngSizeInfo pngsizeinfo;
      try {
         pngsizeinfo = new PngSizeInfo(p_188532_0_.func_110527_b());
      } finally {
         IOUtils.closeQuietly((Closeable)p_188532_0_);
      }

      return pngsizeinfo;
   }
}
