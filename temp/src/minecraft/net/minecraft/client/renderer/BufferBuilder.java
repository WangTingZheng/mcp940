package net.minecraft.client.renderer;

import com.google.common.primitives.Floats;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BufferBuilder {
   private static final Logger field_187316_a = LogManager.getLogger();
   private ByteBuffer field_179001_a;
   private IntBuffer field_178999_b;
   private ShortBuffer field_181676_c;
   private FloatBuffer field_179000_c;
   private int field_178997_d;
   private VertexFormatElement field_181677_f;
   private int field_181678_g;
   private boolean field_78939_q;
   private int field_179006_k;
   private double field_179004_l;
   private double field_179005_m;
   private double field_179002_n;
   private VertexFormat field_179011_q;
   private boolean field_179010_r;

   public BufferBuilder(int p_i46275_1_) {
      this.field_179001_a = GLAllocation.func_74524_c(p_i46275_1_ * 4);
      this.field_178999_b = this.field_179001_a.asIntBuffer();
      this.field_181676_c = this.field_179001_a.asShortBuffer();
      this.field_179000_c = this.field_179001_a.asFloatBuffer();
   }

   private void func_181670_b(int p_181670_1_) {
      if (MathHelper.func_154354_b(p_181670_1_, 4) / 4 > this.field_178999_b.remaining() || this.field_178997_d * this.field_179011_q.func_177338_f() + p_181670_1_ > this.field_179001_a.capacity()) {
         int i = this.field_179001_a.capacity();
         int j = i + MathHelper.func_154354_b(p_181670_1_, 2097152);
         field_187316_a.debug("Needed to grow BufferBuilder buffer: Old size {} bytes, new size {} bytes.", Integer.valueOf(i), Integer.valueOf(j));
         int k = this.field_178999_b.position();
         ByteBuffer bytebuffer = GLAllocation.func_74524_c(j);
         this.field_179001_a.position(0);
         bytebuffer.put(this.field_179001_a);
         bytebuffer.rewind();
         this.field_179001_a = bytebuffer;
         this.field_179000_c = this.field_179001_a.asFloatBuffer().asReadOnlyBuffer();
         this.field_178999_b = this.field_179001_a.asIntBuffer();
         this.field_178999_b.position(k);
         this.field_181676_c = this.field_179001_a.asShortBuffer();
         this.field_181676_c.position(k << 1);
      }
   }

   public void func_181674_a(float p_181674_1_, float p_181674_2_, float p_181674_3_) {
      int i = this.field_178997_d / 4;
      final float[] afloat = new float[i];

      for(int j = 0; j < i; ++j) {
         afloat[j] = func_181665_a(this.field_179000_c, (float)((double)p_181674_1_ + this.field_179004_l), (float)((double)p_181674_2_ + this.field_179005_m), (float)((double)p_181674_3_ + this.field_179002_n), this.field_179011_q.func_181719_f(), j * this.field_179011_q.func_177338_f());
      }

      Integer[] ainteger = new Integer[i];

      for(int k = 0; k < ainteger.length; ++k) {
         ainteger[k] = k;
      }

      Arrays.sort(ainteger, new Comparator<Integer>() {
         public int compare(Integer p_compare_1_, Integer p_compare_2_) {
            return Floats.compare(afloat[p_compare_2_.intValue()], afloat[p_compare_1_.intValue()]);
         }
      });
      BitSet bitset = new BitSet();
      int l = this.field_179011_q.func_177338_f();
      int[] aint = new int[l];

      for(int i1 = bitset.nextClearBit(0); i1 < ainteger.length; i1 = bitset.nextClearBit(i1 + 1)) {
         int j1 = ainteger[i1].intValue();
         if (j1 != i1) {
            this.field_178999_b.limit(j1 * l + l);
            this.field_178999_b.position(j1 * l);
            this.field_178999_b.get(aint);
            int k1 = j1;

            for(int l1 = ainteger[j1].intValue(); k1 != i1; l1 = ainteger[l1].intValue()) {
               this.field_178999_b.limit(l1 * l + l);
               this.field_178999_b.position(l1 * l);
               IntBuffer intbuffer = this.field_178999_b.slice();
               this.field_178999_b.limit(k1 * l + l);
               this.field_178999_b.position(k1 * l);
               this.field_178999_b.put(intbuffer);
               bitset.set(k1);
               k1 = l1;
            }

            this.field_178999_b.limit(i1 * l + l);
            this.field_178999_b.position(i1 * l);
            this.field_178999_b.put(aint);
         }

         bitset.set(i1);
      }

   }

   public BufferBuilder.State func_181672_a() {
      this.field_178999_b.rewind();
      int i = this.func_181664_j();
      this.field_178999_b.limit(i);
      int[] aint = new int[i];
      this.field_178999_b.get(aint);
      this.field_178999_b.limit(this.field_178999_b.capacity());
      this.field_178999_b.position(i);
      return new BufferBuilder.State(aint, new VertexFormat(this.field_179011_q));
   }

   private int func_181664_j() {
      return this.field_178997_d * this.field_179011_q.func_181719_f();
   }

   private static float func_181665_a(FloatBuffer p_181665_0_, float p_181665_1_, float p_181665_2_, float p_181665_3_, int p_181665_4_, int p_181665_5_) {
      float f = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 0 + 0);
      float f1 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 0 + 1);
      float f2 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 0 + 2);
      float f3 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 1 + 0);
      float f4 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 1 + 1);
      float f5 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 1 + 2);
      float f6 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 2 + 0);
      float f7 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 2 + 1);
      float f8 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 2 + 2);
      float f9 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 3 + 0);
      float f10 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 3 + 1);
      float f11 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 3 + 2);
      float f12 = (f + f3 + f6 + f9) * 0.25F - p_181665_1_;
      float f13 = (f1 + f4 + f7 + f10) * 0.25F - p_181665_2_;
      float f14 = (f2 + f5 + f8 + f11) * 0.25F - p_181665_3_;
      return f12 * f12 + f13 * f13 + f14 * f14;
   }

   public void func_178993_a(BufferBuilder.State p_178993_1_) {
      this.field_178999_b.clear();
      this.func_181670_b(p_178993_1_.func_179013_a().length * 4);
      this.field_178999_b.put(p_178993_1_.func_179013_a());
      this.field_178997_d = p_178993_1_.func_179014_c();
      this.field_179011_q = new VertexFormat(p_178993_1_.func_179016_d());
   }

   public void func_178965_a() {
      this.field_178997_d = 0;
      this.field_181677_f = null;
      this.field_181678_g = 0;
   }

   public void func_181668_a(int p_181668_1_, VertexFormat p_181668_2_) {
      if (this.field_179010_r) {
         throw new IllegalStateException("Already building!");
      } else {
         this.field_179010_r = true;
         this.func_178965_a();
         this.field_179006_k = p_181668_1_;
         this.field_179011_q = p_181668_2_;
         this.field_181677_f = p_181668_2_.func_177348_c(this.field_181678_g);
         this.field_78939_q = false;
         this.field_179001_a.limit(this.field_179001_a.capacity());
      }
   }

   public BufferBuilder func_187315_a(double p_187315_1_, double p_187315_3_) {
      int i = this.field_178997_d * this.field_179011_q.func_177338_f() + this.field_179011_q.func_181720_d(this.field_181678_g);
      switch(this.field_181677_f.func_177367_b()) {
      case FLOAT:
         this.field_179001_a.putFloat(i, (float)p_187315_1_);
         this.field_179001_a.putFloat(i + 4, (float)p_187315_3_);
         break;
      case UINT:
      case INT:
         this.field_179001_a.putInt(i, (int)p_187315_1_);
         this.field_179001_a.putInt(i + 4, (int)p_187315_3_);
         break;
      case USHORT:
      case SHORT:
         this.field_179001_a.putShort(i, (short)((int)p_187315_3_));
         this.field_179001_a.putShort(i + 2, (short)((int)p_187315_1_));
         break;
      case UBYTE:
      case BYTE:
         this.field_179001_a.put(i, (byte)((int)p_187315_3_));
         this.field_179001_a.put(i + 1, (byte)((int)p_187315_1_));
      }

      this.func_181667_k();
      return this;
   }

   public BufferBuilder func_187314_a(int p_187314_1_, int p_187314_2_) {
      int i = this.field_178997_d * this.field_179011_q.func_177338_f() + this.field_179011_q.func_181720_d(this.field_181678_g);
      switch(this.field_181677_f.func_177367_b()) {
      case FLOAT:
         this.field_179001_a.putFloat(i, (float)p_187314_1_);
         this.field_179001_a.putFloat(i + 4, (float)p_187314_2_);
         break;
      case UINT:
      case INT:
         this.field_179001_a.putInt(i, p_187314_1_);
         this.field_179001_a.putInt(i + 4, p_187314_2_);
         break;
      case USHORT:
      case SHORT:
         this.field_179001_a.putShort(i, (short)p_187314_2_);
         this.field_179001_a.putShort(i + 2, (short)p_187314_1_);
         break;
      case UBYTE:
      case BYTE:
         this.field_179001_a.put(i, (byte)p_187314_2_);
         this.field_179001_a.put(i + 1, (byte)p_187314_1_);
      }

      this.func_181667_k();
      return this;
   }

   public void func_178962_a(int p_178962_1_, int p_178962_2_, int p_178962_3_, int p_178962_4_) {
      int i = (this.field_178997_d - 4) * this.field_179011_q.func_181719_f() + this.field_179011_q.func_177344_b(1) / 4;
      int j = this.field_179011_q.func_177338_f() >> 2;
      this.field_178999_b.put(i, p_178962_1_);
      this.field_178999_b.put(i + j, p_178962_2_);
      this.field_178999_b.put(i + j * 2, p_178962_3_);
      this.field_178999_b.put(i + j * 3, p_178962_4_);
   }

   public void func_178987_a(double p_178987_1_, double p_178987_3_, double p_178987_5_) {
      int i = this.field_179011_q.func_181719_f();
      int j = (this.field_178997_d - 4) * i;

      for(int k = 0; k < 4; ++k) {
         int l = j + k * i;
         int i1 = l + 1;
         int j1 = i1 + 1;
         this.field_178999_b.put(l, Float.floatToRawIntBits((float)(p_178987_1_ + this.field_179004_l) + Float.intBitsToFloat(this.field_178999_b.get(l))));
         this.field_178999_b.put(i1, Float.floatToRawIntBits((float)(p_178987_3_ + this.field_179005_m) + Float.intBitsToFloat(this.field_178999_b.get(i1))));
         this.field_178999_b.put(j1, Float.floatToRawIntBits((float)(p_178987_5_ + this.field_179002_n) + Float.intBitsToFloat(this.field_178999_b.get(j1))));
      }

   }

   private int func_78909_a(int p_78909_1_) {
      return ((this.field_178997_d - p_78909_1_) * this.field_179011_q.func_177338_f() + this.field_179011_q.func_177340_e()) / 4;
   }

   public void func_178978_a(float p_178978_1_, float p_178978_2_, float p_178978_3_, int p_178978_4_) {
      int i = this.func_78909_a(p_178978_4_);
      int j = -1;
      if (!this.field_78939_q) {
         j = this.field_178999_b.get(i);
         if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            int k = (int)((float)(j & 255) * p_178978_1_);
            int l = (int)((float)(j >> 8 & 255) * p_178978_2_);
            int i1 = (int)((float)(j >> 16 & 255) * p_178978_3_);
            j = j & -16777216;
            j = j | i1 << 16 | l << 8 | k;
         } else {
            int j1 = (int)((float)(j >> 24 & 255) * p_178978_1_);
            int k1 = (int)((float)(j >> 16 & 255) * p_178978_2_);
            int l1 = (int)((float)(j >> 8 & 255) * p_178978_3_);
            j = j & 255;
            j = j | j1 << 24 | k1 << 16 | l1 << 8;
         }
      }

      this.field_178999_b.put(i, j);
   }

   private void func_192836_a(int p_192836_1_, int p_192836_2_) {
      int i = this.func_78909_a(p_192836_2_);
      int j = p_192836_1_ >> 16 & 255;
      int k = p_192836_1_ >> 8 & 255;
      int l = p_192836_1_ & 255;
      this.func_178972_a(i, j, k, l);
   }

   public void func_178994_b(float p_178994_1_, float p_178994_2_, float p_178994_3_, int p_178994_4_) {
      int i = this.func_78909_a(p_178994_4_);
      int j = MathHelper.func_76125_a((int)(p_178994_1_ * 255.0F), 0, 255);
      int k = MathHelper.func_76125_a((int)(p_178994_2_ * 255.0F), 0, 255);
      int l = MathHelper.func_76125_a((int)(p_178994_3_ * 255.0F), 0, 255);
      this.func_178972_a(i, j, k, l);
   }

   private void func_178972_a(int p_178972_1_, int p_178972_2_, int p_178972_3_, int p_178972_4_) {
      if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
         this.field_178999_b.put(p_178972_1_, -16777216 | p_178972_4_ << 16 | p_178972_3_ << 8 | p_178972_2_);
      } else {
         this.field_178999_b.put(p_178972_1_, p_178972_2_ << 24 | p_178972_3_ << 16 | p_178972_4_ << 8 | 255);
      }

   }

   public void func_78914_f() {
      this.field_78939_q = true;
   }

   public BufferBuilder func_181666_a(float p_181666_1_, float p_181666_2_, float p_181666_3_, float p_181666_4_) {
      return this.func_181669_b((int)(p_181666_1_ * 255.0F), (int)(p_181666_2_ * 255.0F), (int)(p_181666_3_ * 255.0F), (int)(p_181666_4_ * 255.0F));
   }

   public BufferBuilder func_181669_b(int p_181669_1_, int p_181669_2_, int p_181669_3_, int p_181669_4_) {
      if (this.field_78939_q) {
         return this;
      } else {
         int i = this.field_178997_d * this.field_179011_q.func_177338_f() + this.field_179011_q.func_181720_d(this.field_181678_g);
         switch(this.field_181677_f.func_177367_b()) {
         case FLOAT:
            this.field_179001_a.putFloat(i, (float)p_181669_1_ / 255.0F);
            this.field_179001_a.putFloat(i + 4, (float)p_181669_2_ / 255.0F);
            this.field_179001_a.putFloat(i + 8, (float)p_181669_3_ / 255.0F);
            this.field_179001_a.putFloat(i + 12, (float)p_181669_4_ / 255.0F);
            break;
         case UINT:
         case INT:
            this.field_179001_a.putFloat(i, (float)p_181669_1_);
            this.field_179001_a.putFloat(i + 4, (float)p_181669_2_);
            this.field_179001_a.putFloat(i + 8, (float)p_181669_3_);
            this.field_179001_a.putFloat(i + 12, (float)p_181669_4_);
            break;
         case USHORT:
         case SHORT:
            this.field_179001_a.putShort(i, (short)p_181669_1_);
            this.field_179001_a.putShort(i + 2, (short)p_181669_2_);
            this.field_179001_a.putShort(i + 4, (short)p_181669_3_);
            this.field_179001_a.putShort(i + 6, (short)p_181669_4_);
            break;
         case UBYTE:
         case BYTE:
            if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
               this.field_179001_a.put(i, (byte)p_181669_1_);
               this.field_179001_a.put(i + 1, (byte)p_181669_2_);
               this.field_179001_a.put(i + 2, (byte)p_181669_3_);
               this.field_179001_a.put(i + 3, (byte)p_181669_4_);
            } else {
               this.field_179001_a.put(i, (byte)p_181669_4_);
               this.field_179001_a.put(i + 1, (byte)p_181669_3_);
               this.field_179001_a.put(i + 2, (byte)p_181669_2_);
               this.field_179001_a.put(i + 3, (byte)p_181669_1_);
            }
         }

         this.func_181667_k();
         return this;
      }
   }

   public void func_178981_a(int[] p_178981_1_) {
      this.func_181670_b(p_178981_1_.length * 4);
      this.field_178999_b.position(this.func_181664_j());
      this.field_178999_b.put(p_178981_1_);
      this.field_178997_d += p_178981_1_.length / this.field_179011_q.func_181719_f();
   }

   public void func_181675_d() {
      ++this.field_178997_d;
      this.func_181670_b(this.field_179011_q.func_177338_f());
   }

   public BufferBuilder func_181662_b(double p_181662_1_, double p_181662_3_, double p_181662_5_) {
      int i = this.field_178997_d * this.field_179011_q.func_177338_f() + this.field_179011_q.func_181720_d(this.field_181678_g);
      switch(this.field_181677_f.func_177367_b()) {
      case FLOAT:
         this.field_179001_a.putFloat(i, (float)(p_181662_1_ + this.field_179004_l));
         this.field_179001_a.putFloat(i + 4, (float)(p_181662_3_ + this.field_179005_m));
         this.field_179001_a.putFloat(i + 8, (float)(p_181662_5_ + this.field_179002_n));
         break;
      case UINT:
      case INT:
         this.field_179001_a.putInt(i, Float.floatToRawIntBits((float)(p_181662_1_ + this.field_179004_l)));
         this.field_179001_a.putInt(i + 4, Float.floatToRawIntBits((float)(p_181662_3_ + this.field_179005_m)));
         this.field_179001_a.putInt(i + 8, Float.floatToRawIntBits((float)(p_181662_5_ + this.field_179002_n)));
         break;
      case USHORT:
      case SHORT:
         this.field_179001_a.putShort(i, (short)((int)(p_181662_1_ + this.field_179004_l)));
         this.field_179001_a.putShort(i + 2, (short)((int)(p_181662_3_ + this.field_179005_m)));
         this.field_179001_a.putShort(i + 4, (short)((int)(p_181662_5_ + this.field_179002_n)));
         break;
      case UBYTE:
      case BYTE:
         this.field_179001_a.put(i, (byte)((int)(p_181662_1_ + this.field_179004_l)));
         this.field_179001_a.put(i + 1, (byte)((int)(p_181662_3_ + this.field_179005_m)));
         this.field_179001_a.put(i + 2, (byte)((int)(p_181662_5_ + this.field_179002_n)));
      }

      this.func_181667_k();
      return this;
   }

   public void func_178975_e(float p_178975_1_, float p_178975_2_, float p_178975_3_) {
      int i = (byte)((int)(p_178975_1_ * 127.0F)) & 255;
      int j = (byte)((int)(p_178975_2_ * 127.0F)) & 255;
      int k = (byte)((int)(p_178975_3_ * 127.0F)) & 255;
      int l = i | j << 8 | k << 16;
      int i1 = this.field_179011_q.func_177338_f() >> 2;
      int j1 = (this.field_178997_d - 4) * i1 + this.field_179011_q.func_177342_c() / 4;
      this.field_178999_b.put(j1, l);
      this.field_178999_b.put(j1 + i1, l);
      this.field_178999_b.put(j1 + i1 * 2, l);
      this.field_178999_b.put(j1 + i1 * 3, l);
   }

   private void func_181667_k() {
      ++this.field_181678_g;
      this.field_181678_g %= this.field_179011_q.func_177345_h();
      this.field_181677_f = this.field_179011_q.func_177348_c(this.field_181678_g);
      if (this.field_181677_f.func_177375_c() == VertexFormatElement.EnumUsage.PADDING) {
         this.func_181667_k();
      }

   }

   public BufferBuilder func_181663_c(float p_181663_1_, float p_181663_2_, float p_181663_3_) {
      int i = this.field_178997_d * this.field_179011_q.func_177338_f() + this.field_179011_q.func_181720_d(this.field_181678_g);
      switch(this.field_181677_f.func_177367_b()) {
      case FLOAT:
         this.field_179001_a.putFloat(i, p_181663_1_);
         this.field_179001_a.putFloat(i + 4, p_181663_2_);
         this.field_179001_a.putFloat(i + 8, p_181663_3_);
         break;
      case UINT:
      case INT:
         this.field_179001_a.putInt(i, (int)p_181663_1_);
         this.field_179001_a.putInt(i + 4, (int)p_181663_2_);
         this.field_179001_a.putInt(i + 8, (int)p_181663_3_);
         break;
      case USHORT:
      case SHORT:
         this.field_179001_a.putShort(i, (short)((int)p_181663_1_ * 32767 & '\uffff'));
         this.field_179001_a.putShort(i + 2, (short)((int)p_181663_2_ * 32767 & '\uffff'));
         this.field_179001_a.putShort(i + 4, (short)((int)p_181663_3_ * 32767 & '\uffff'));
         break;
      case UBYTE:
      case BYTE:
         this.field_179001_a.put(i, (byte)((int)p_181663_1_ * 127 & 255));
         this.field_179001_a.put(i + 1, (byte)((int)p_181663_2_ * 127 & 255));
         this.field_179001_a.put(i + 2, (byte)((int)p_181663_3_ * 127 & 255));
      }

      this.func_181667_k();
      return this;
   }

   public void func_178969_c(double p_178969_1_, double p_178969_3_, double p_178969_5_) {
      this.field_179004_l = p_178969_1_;
      this.field_179005_m = p_178969_3_;
      this.field_179002_n = p_178969_5_;
   }

   public void func_178977_d() {
      if (!this.field_179010_r) {
         throw new IllegalStateException("Not building!");
      } else {
         this.field_179010_r = false;
         this.field_179001_a.position(0);
         this.field_179001_a.limit(this.func_181664_j() * 4);
      }
   }

   public ByteBuffer func_178966_f() {
      return this.field_179001_a;
   }

   public VertexFormat func_178973_g() {
      return this.field_179011_q;
   }

   public int func_178989_h() {
      return this.field_178997_d;
   }

   public int func_178979_i() {
      return this.field_179006_k;
   }

   public void func_178968_d(int p_178968_1_) {
      for(int i = 0; i < 4; ++i) {
         this.func_192836_a(p_178968_1_, i + 1);
      }

   }

   public void func_178990_f(float p_178990_1_, float p_178990_2_, float p_178990_3_) {
      for(int i = 0; i < 4; ++i) {
         this.func_178994_b(p_178990_1_, p_178990_2_, p_178990_3_, i + 1);
      }

   }

   public class State {
      private final int[] field_179019_b;
      private final VertexFormat field_179018_e;

      public State(int[] p_i46453_2_, VertexFormat p_i46453_3_) {
         this.field_179019_b = p_i46453_2_;
         this.field_179018_e = p_i46453_3_;
      }

      public int[] func_179013_a() {
         return this.field_179019_b;
      }

      public int func_179014_c() {
         return this.field_179019_b.length / this.field_179018_e.func_181719_f();
      }

      public VertexFormat func_179016_d() {
         return this.field_179018_e;
      }
   }
}
