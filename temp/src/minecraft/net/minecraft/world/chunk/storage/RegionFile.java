package net.minecraft.world.chunk.storage;

import com.google.common.collect.Lists;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;

public class RegionFile {
   private static final byte[] field_76720_a = new byte[4096];
   private final File field_76718_b;
   private RandomAccessFile field_76719_c;
   private final int[] field_76716_d = new int[1024];
   private final int[] field_76717_e = new int[1024];
   private List<Boolean> field_76714_f;
   private int field_76715_g;
   private long field_76721_h;

   public RegionFile(File p_i2001_1_) {
      this.field_76718_b = p_i2001_1_;
      this.field_76715_g = 0;

      try {
         if (p_i2001_1_.exists()) {
            this.field_76721_h = p_i2001_1_.lastModified();
         }

         this.field_76719_c = new RandomAccessFile(p_i2001_1_, "rw");
         if (this.field_76719_c.length() < 4096L) {
            this.field_76719_c.write(field_76720_a);
            this.field_76719_c.write(field_76720_a);
            this.field_76715_g += 8192;
         }

         if ((this.field_76719_c.length() & 4095L) != 0L) {
            for(int i = 0; (long)i < (this.field_76719_c.length() & 4095L); ++i) {
               this.field_76719_c.write(0);
            }
         }

         int i1 = (int)this.field_76719_c.length() / 4096;
         this.field_76714_f = Lists.<Boolean>newArrayListWithCapacity(i1);

         for(int j = 0; j < i1; ++j) {
            this.field_76714_f.add(Boolean.valueOf(true));
         }

         this.field_76714_f.set(0, Boolean.valueOf(false));
         this.field_76714_f.set(1, Boolean.valueOf(false));
         this.field_76719_c.seek(0L);

         for(int j1 = 0; j1 < 1024; ++j1) {
            int k = this.field_76719_c.readInt();
            this.field_76716_d[j1] = k;
            if (k != 0 && (k >> 8) + (k & 255) <= this.field_76714_f.size()) {
               for(int l = 0; l < (k & 255); ++l) {
                  this.field_76714_f.set((k >> 8) + l, Boolean.valueOf(false));
               }
            }
         }

         for(int k1 = 0; k1 < 1024; ++k1) {
            int l1 = this.field_76719_c.readInt();
            this.field_76717_e[k1] = l1;
         }
      } catch (IOException ioexception) {
         ioexception.printStackTrace();
      }

   }

   @Nullable
   public synchronized DataInputStream func_76704_a(int p_76704_1_, int p_76704_2_) {
      if (this.func_76705_d(p_76704_1_, p_76704_2_)) {
         return null;
      } else {
         try {
            int i = this.func_76707_e(p_76704_1_, p_76704_2_);
            if (i == 0) {
               return null;
            } else {
               int j = i >> 8;
               int k = i & 255;
               if (j + k > this.field_76714_f.size()) {
                  return null;
               } else {
                  this.field_76719_c.seek((long)(j * 4096));
                  int l = this.field_76719_c.readInt();
                  if (l > 4096 * k) {
                     return null;
                  } else if (l <= 0) {
                     return null;
                  } else {
                     byte b0 = this.field_76719_c.readByte();
                     if (b0 == 1) {
                        byte[] abyte1 = new byte[l - 1];
                        this.field_76719_c.read(abyte1);
                        return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(abyte1))));
                     } else if (b0 == 2) {
                        byte[] abyte = new byte[l - 1];
                        this.field_76719_c.read(abyte);
                        return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(abyte))));
                     } else {
                        return null;
                     }
                  }
               }
            }
         } catch (IOException var9) {
            return null;
         }
      }
   }

   @Nullable
   public DataOutputStream func_76710_b(int p_76710_1_, int p_76710_2_) {
      return this.func_76705_d(p_76710_1_, p_76710_2_) ? null : new DataOutputStream(new BufferedOutputStream(new DeflaterOutputStream(new RegionFile.ChunkBuffer(p_76710_1_, p_76710_2_))));
   }

   protected synchronized void func_76706_a(int p_76706_1_, int p_76706_2_, byte[] p_76706_3_, int p_76706_4_) {
      try {
         int i = this.func_76707_e(p_76706_1_, p_76706_2_);
         int j = i >> 8;
         int k = i & 255;
         int l = (p_76706_4_ + 5) / 4096 + 1;
         if (l >= 256) {
            return;
         }

         if (j != 0 && k == l) {
            this.func_76712_a(j, p_76706_3_, p_76706_4_);
         } else {
            for(int i1 = 0; i1 < k; ++i1) {
               this.field_76714_f.set(j + i1, Boolean.valueOf(true));
            }

            int l1 = this.field_76714_f.indexOf(Boolean.valueOf(true));
            int j1 = 0;
            if (l1 != -1) {
               for(int k1 = l1; k1 < this.field_76714_f.size(); ++k1) {
                  if (j1 != 0) {
                     if (((Boolean)this.field_76714_f.get(k1)).booleanValue()) {
                        ++j1;
                     } else {
                        j1 = 0;
                     }
                  } else if (((Boolean)this.field_76714_f.get(k1)).booleanValue()) {
                     l1 = k1;
                     j1 = 1;
                  }

                  if (j1 >= l) {
                     break;
                  }
               }
            }

            if (j1 >= l) {
               j = l1;
               this.func_76711_a(p_76706_1_, p_76706_2_, l1 << 8 | l);

               for(int j2 = 0; j2 < l; ++j2) {
                  this.field_76714_f.set(j + j2, Boolean.valueOf(false));
               }

               this.func_76712_a(j, p_76706_3_, p_76706_4_);
            } else {
               this.field_76719_c.seek(this.field_76719_c.length());
               j = this.field_76714_f.size();

               for(int i2 = 0; i2 < l; ++i2) {
                  this.field_76719_c.write(field_76720_a);
                  this.field_76714_f.add(Boolean.valueOf(false));
               }

               this.field_76715_g += 4096 * l;
               this.func_76712_a(j, p_76706_3_, p_76706_4_);
               this.func_76711_a(p_76706_1_, p_76706_2_, j << 8 | l);
            }
         }

         this.func_76713_b(p_76706_1_, p_76706_2_, (int)(MinecraftServer.func_130071_aq() / 1000L));
      } catch (IOException ioexception) {
         ioexception.printStackTrace();
      }

   }

   private void func_76712_a(int p_76712_1_, byte[] p_76712_2_, int p_76712_3_) throws IOException {
      this.field_76719_c.seek((long)(p_76712_1_ * 4096));
      this.field_76719_c.writeInt(p_76712_3_ + 1);
      this.field_76719_c.writeByte(2);
      this.field_76719_c.write(p_76712_2_, 0, p_76712_3_);
   }

   private boolean func_76705_d(int p_76705_1_, int p_76705_2_) {
      return p_76705_1_ < 0 || p_76705_1_ >= 32 || p_76705_2_ < 0 || p_76705_2_ >= 32;
   }

   private int func_76707_e(int p_76707_1_, int p_76707_2_) {
      return this.field_76716_d[p_76707_1_ + p_76707_2_ * 32];
   }

   public boolean func_76709_c(int p_76709_1_, int p_76709_2_) {
      return this.func_76707_e(p_76709_1_, p_76709_2_) != 0;
   }

   private void func_76711_a(int p_76711_1_, int p_76711_2_, int p_76711_3_) throws IOException {
      this.field_76716_d[p_76711_1_ + p_76711_2_ * 32] = p_76711_3_;
      this.field_76719_c.seek((long)((p_76711_1_ + p_76711_2_ * 32) * 4));
      this.field_76719_c.writeInt(p_76711_3_);
   }

   private void func_76713_b(int p_76713_1_, int p_76713_2_, int p_76713_3_) throws IOException {
      this.field_76717_e[p_76713_1_ + p_76713_2_ * 32] = p_76713_3_;
      this.field_76719_c.seek((long)(4096 + (p_76713_1_ + p_76713_2_ * 32) * 4));
      this.field_76719_c.writeInt(p_76713_3_);
   }

   public void func_76708_c() throws IOException {
      if (this.field_76719_c != null) {
         this.field_76719_c.close();
      }

   }

   class ChunkBuffer extends ByteArrayOutputStream {
      private final int field_76722_b;
      private final int field_76723_c;

      public ChunkBuffer(int p_i2000_2_, int p_i2000_3_) {
         super(8096);
         this.field_76722_b = p_i2000_2_;
         this.field_76723_c = p_i2000_3_;
      }

      public void close() throws IOException {
         RegionFile.this.func_76706_a(this.field_76722_b, this.field_76723_c, this.buf, this.count);
      }
   }
}
