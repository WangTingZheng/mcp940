package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ByteProcessor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

public class PacketBuffer extends ByteBuf {
   private final ByteBuf field_150794_a;

   public PacketBuffer(ByteBuf p_i45154_1_) {
      this.field_150794_a = p_i45154_1_;
   }

   public static int func_150790_a(int p_150790_0_) {
      for(int i = 1; i < 5; ++i) {
         if ((p_150790_0_ & -1 << i * 7) == 0) {
            return i;
         }
      }

      return 5;
   }

   public PacketBuffer func_179250_a(byte[] p_179250_1_) {
      this.func_150787_b(p_179250_1_.length);
      this.writeBytes(p_179250_1_);
      return this;
   }

   public byte[] func_179251_a() {
      return this.func_189425_b(this.readableBytes());
   }

   public byte[] func_189425_b(int p_189425_1_) {
      int i = this.func_150792_a();
      if (i > p_189425_1_) {
         throw new DecoderException("ByteArray with size " + i + " is bigger than allowed " + p_189425_1_);
      } else {
         byte[] abyte = new byte[i];
         this.readBytes(abyte);
         return abyte;
      }
   }

   public PacketBuffer func_186875_a(int[] p_186875_1_) {
      this.func_150787_b(p_186875_1_.length);

      for(int i : p_186875_1_) {
         this.func_150787_b(i);
      }

      return this;
   }

   public int[] func_186863_b() {
      return this.func_189424_c(this.readableBytes());
   }

   public int[] func_189424_c(int p_189424_1_) {
      int i = this.func_150792_a();
      if (i > p_189424_1_) {
         throw new DecoderException("VarIntArray with size " + i + " is bigger than allowed " + p_189424_1_);
      } else {
         int[] aint = new int[i];

         for(int j = 0; j < aint.length; ++j) {
            aint[j] = this.func_150792_a();
         }

         return aint;
      }
   }

   public PacketBuffer func_186865_a(long[] p_186865_1_) {
      this.func_150787_b(p_186865_1_.length);

      for(long i : p_186865_1_) {
         this.writeLong(i);
      }

      return this;
   }

   public long[] func_186873_b(@Nullable long[] p_186873_1_) {
      return this.func_189423_a(p_186873_1_, this.readableBytes() / 8);
   }

   public long[] func_189423_a(@Nullable long[] p_189423_1_, int p_189423_2_) {
      int i = this.func_150792_a();
      if (p_189423_1_ == null || p_189423_1_.length != i) {
         if (i > p_189423_2_) {
            throw new DecoderException("LongArray with size " + i + " is bigger than allowed " + p_189423_2_);
         }

         p_189423_1_ = new long[i];
      }

      for(int j = 0; j < p_189423_1_.length; ++j) {
         p_189423_1_[j] = this.readLong();
      }

      return p_189423_1_;
   }

   public BlockPos func_179259_c() {
      return BlockPos.func_177969_a(this.readLong());
   }

   public PacketBuffer func_179255_a(BlockPos p_179255_1_) {
      this.writeLong(p_179255_1_.func_177986_g());
      return this;
   }

   public ITextComponent func_179258_d() throws IOException {
      return ITextComponent.Serializer.func_150699_a(this.func_150789_c(32767));
   }

   public PacketBuffer func_179256_a(ITextComponent p_179256_1_) {
      return this.func_180714_a(ITextComponent.Serializer.func_150696_a(p_179256_1_));
   }

   public <T extends Enum<T>> T func_179257_a(Class<T> p_179257_1_) {
      return (T)((Enum[])p_179257_1_.getEnumConstants())[this.func_150792_a()];
   }

   public PacketBuffer func_179249_a(Enum<?> p_179249_1_) {
      return this.func_150787_b(p_179249_1_.ordinal());
   }

   public int func_150792_a() {
      int i = 0;
      int j = 0;

      while(true) {
         byte b0 = this.readByte();
         i |= (b0 & 127) << j++ * 7;
         if (j > 5) {
            throw new RuntimeException("VarInt too big");
         }

         if ((b0 & 128) != 128) {
            break;
         }
      }

      return i;
   }

   public long func_179260_f() {
      long i = 0L;
      int j = 0;

      while(true) {
         byte b0 = this.readByte();
         i |= (long)(b0 & 127) << j++ * 7;
         if (j > 10) {
            throw new RuntimeException("VarLong too big");
         }

         if ((b0 & 128) != 128) {
            break;
         }
      }

      return i;
   }

   public PacketBuffer func_179252_a(UUID p_179252_1_) {
      this.writeLong(p_179252_1_.getMostSignificantBits());
      this.writeLong(p_179252_1_.getLeastSignificantBits());
      return this;
   }

   public UUID func_179253_g() {
      return new UUID(this.readLong(), this.readLong());
   }

   public PacketBuffer func_150787_b(int p_150787_1_) {
      while((p_150787_1_ & -128) != 0) {
         this.writeByte(p_150787_1_ & 127 | 128);
         p_150787_1_ >>>= 7;
      }

      this.writeByte(p_150787_1_);
      return this;
   }

   public PacketBuffer func_179254_b(long p_179254_1_) {
      while((p_179254_1_ & -128L) != 0L) {
         this.writeByte((int)(p_179254_1_ & 127L) | 128);
         p_179254_1_ >>>= 7;
      }

      this.writeByte((int)p_179254_1_);
      return this;
   }

   public PacketBuffer func_150786_a(@Nullable NBTTagCompound p_150786_1_) {
      if (p_150786_1_ == null) {
         this.writeByte(0);
      } else {
         try {
            CompressedStreamTools.func_74800_a(p_150786_1_, new ByteBufOutputStream(this));
         } catch (IOException ioexception) {
            throw new EncoderException(ioexception);
         }
      }

      return this;
   }

   @Nullable
   public NBTTagCompound func_150793_b() throws IOException {
      int i = this.readerIndex();
      byte b0 = this.readByte();
      if (b0 == 0) {
         return null;
      } else {
         this.readerIndex(i);

         try {
            return CompressedStreamTools.func_152456_a(new ByteBufInputStream(this), new NBTSizeTracker(2097152L));
         } catch (IOException ioexception) {
            throw new EncoderException(ioexception);
         }
      }
   }

   public PacketBuffer func_150788_a(ItemStack p_150788_1_) {
      if (p_150788_1_.func_190926_b()) {
         this.writeShort(-1);
      } else {
         this.writeShort(Item.func_150891_b(p_150788_1_.func_77973_b()));
         this.writeByte(p_150788_1_.func_190916_E());
         this.writeShort(p_150788_1_.func_77960_j());
         NBTTagCompound nbttagcompound = null;
         if (p_150788_1_.func_77973_b().func_77645_m() || p_150788_1_.func_77973_b().func_77651_p()) {
            nbttagcompound = p_150788_1_.func_77978_p();
         }

         this.func_150786_a(nbttagcompound);
      }

      return this;
   }

   public ItemStack func_150791_c() throws IOException {
      int i = this.readShort();
      if (i < 0) {
         return ItemStack.field_190927_a;
      } else {
         int j = this.readByte();
         int k = this.readShort();
         ItemStack itemstack = new ItemStack(Item.func_150899_d(i), j, k);
         itemstack.func_77982_d(this.func_150793_b());
         return itemstack;
      }
   }

   public String func_150789_c(int p_150789_1_) {
      int i = this.func_150792_a();
      if (i > p_150789_1_ * 4) {
         throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + i + " > " + p_150789_1_ * 4 + ")");
      } else if (i < 0) {
         throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
      } else {
         String s = this.toString(this.readerIndex(), i, StandardCharsets.UTF_8);
         this.readerIndex(this.readerIndex() + i);
         if (s.length() > p_150789_1_) {
            throw new DecoderException("The received string length is longer than maximum allowed (" + i + " > " + p_150789_1_ + ")");
         } else {
            return s;
         }
      }
   }

   public PacketBuffer func_180714_a(String p_180714_1_) {
      byte[] abyte = p_180714_1_.getBytes(StandardCharsets.UTF_8);
      if (abyte.length > 32767) {
         throw new EncoderException("String too big (was " + abyte.length + " bytes encoded, max " + 32767 + ")");
      } else {
         this.func_150787_b(abyte.length);
         this.writeBytes(abyte);
         return this;
      }
   }

   public ResourceLocation func_192575_l() {
      return new ResourceLocation(this.func_150789_c(32767));
   }

   public PacketBuffer func_192572_a(ResourceLocation p_192572_1_) {
      this.func_180714_a(p_192572_1_.toString());
      return this;
   }

   public Date func_192573_m() {
      return new Date(this.readLong());
   }

   public PacketBuffer func_192574_a(Date p_192574_1_) {
      this.writeLong(p_192574_1_.getTime());
      return this;
   }

   public int capacity() {
      return this.field_150794_a.capacity();
   }

   public ByteBuf capacity(int p_capacity_1_) {
      return this.field_150794_a.capacity(p_capacity_1_);
   }

   public int maxCapacity() {
      return this.field_150794_a.maxCapacity();
   }

   public ByteBufAllocator alloc() {
      return this.field_150794_a.alloc();
   }

   public ByteOrder order() {
      return this.field_150794_a.order();
   }

   public ByteBuf order(ByteOrder p_order_1_) {
      return this.field_150794_a.order(p_order_1_);
   }

   public ByteBuf unwrap() {
      return this.field_150794_a.unwrap();
   }

   public boolean isDirect() {
      return this.field_150794_a.isDirect();
   }

   public boolean isReadOnly() {
      return this.field_150794_a.isReadOnly();
   }

   public ByteBuf asReadOnly() {
      return this.field_150794_a.asReadOnly();
   }

   public int readerIndex() {
      return this.field_150794_a.readerIndex();
   }

   public ByteBuf readerIndex(int p_readerIndex_1_) {
      return this.field_150794_a.readerIndex(p_readerIndex_1_);
   }

   public int writerIndex() {
      return this.field_150794_a.writerIndex();
   }

   public ByteBuf writerIndex(int p_writerIndex_1_) {
      return this.field_150794_a.writerIndex(p_writerIndex_1_);
   }

   public ByteBuf setIndex(int p_setIndex_1_, int p_setIndex_2_) {
      return this.field_150794_a.setIndex(p_setIndex_1_, p_setIndex_2_);
   }

   public int readableBytes() {
      return this.field_150794_a.readableBytes();
   }

   public int writableBytes() {
      return this.field_150794_a.writableBytes();
   }

   public int maxWritableBytes() {
      return this.field_150794_a.maxWritableBytes();
   }

   public boolean isReadable() {
      return this.field_150794_a.isReadable();
   }

   public boolean isReadable(int p_isReadable_1_) {
      return this.field_150794_a.isReadable(p_isReadable_1_);
   }

   public boolean isWritable() {
      return this.field_150794_a.isWritable();
   }

   public boolean isWritable(int p_isWritable_1_) {
      return this.field_150794_a.isWritable(p_isWritable_1_);
   }

   public ByteBuf clear() {
      return this.field_150794_a.clear();
   }

   public ByteBuf markReaderIndex() {
      return this.field_150794_a.markReaderIndex();
   }

   public ByteBuf resetReaderIndex() {
      return this.field_150794_a.resetReaderIndex();
   }

   public ByteBuf markWriterIndex() {
      return this.field_150794_a.markWriterIndex();
   }

   public ByteBuf resetWriterIndex() {
      return this.field_150794_a.resetWriterIndex();
   }

   public ByteBuf discardReadBytes() {
      return this.field_150794_a.discardReadBytes();
   }

   public ByteBuf discardSomeReadBytes() {
      return this.field_150794_a.discardSomeReadBytes();
   }

   public ByteBuf ensureWritable(int p_ensureWritable_1_) {
      return this.field_150794_a.ensureWritable(p_ensureWritable_1_);
   }

   public int ensureWritable(int p_ensureWritable_1_, boolean p_ensureWritable_2_) {
      return this.field_150794_a.ensureWritable(p_ensureWritable_1_, p_ensureWritable_2_);
   }

   public boolean getBoolean(int p_getBoolean_1_) {
      return this.field_150794_a.getBoolean(p_getBoolean_1_);
   }

   public byte getByte(int p_getByte_1_) {
      return this.field_150794_a.getByte(p_getByte_1_);
   }

   public short getUnsignedByte(int p_getUnsignedByte_1_) {
      return this.field_150794_a.getUnsignedByte(p_getUnsignedByte_1_);
   }

   public short getShort(int p_getShort_1_) {
      return this.field_150794_a.getShort(p_getShort_1_);
   }

   public short getShortLE(int p_getShortLE_1_) {
      return this.field_150794_a.getShortLE(p_getShortLE_1_);
   }

   public int getUnsignedShort(int p_getUnsignedShort_1_) {
      return this.field_150794_a.getUnsignedShort(p_getUnsignedShort_1_);
   }

   public int getUnsignedShortLE(int p_getUnsignedShortLE_1_) {
      return this.field_150794_a.getUnsignedShortLE(p_getUnsignedShortLE_1_);
   }

   public int getMedium(int p_getMedium_1_) {
      return this.field_150794_a.getMedium(p_getMedium_1_);
   }

   public int getMediumLE(int p_getMediumLE_1_) {
      return this.field_150794_a.getMediumLE(p_getMediumLE_1_);
   }

   public int getUnsignedMedium(int p_getUnsignedMedium_1_) {
      return this.field_150794_a.getUnsignedMedium(p_getUnsignedMedium_1_);
   }

   public int getUnsignedMediumLE(int p_getUnsignedMediumLE_1_) {
      return this.field_150794_a.getUnsignedMediumLE(p_getUnsignedMediumLE_1_);
   }

   public int getInt(int p_getInt_1_) {
      return this.field_150794_a.getInt(p_getInt_1_);
   }

   public int getIntLE(int p_getIntLE_1_) {
      return this.field_150794_a.getIntLE(p_getIntLE_1_);
   }

   public long getUnsignedInt(int p_getUnsignedInt_1_) {
      return this.field_150794_a.getUnsignedInt(p_getUnsignedInt_1_);
   }

   public long getUnsignedIntLE(int p_getUnsignedIntLE_1_) {
      return this.field_150794_a.getUnsignedIntLE(p_getUnsignedIntLE_1_);
   }

   public long getLong(int p_getLong_1_) {
      return this.field_150794_a.getLong(p_getLong_1_);
   }

   public long getLongLE(int p_getLongLE_1_) {
      return this.field_150794_a.getLongLE(p_getLongLE_1_);
   }

   public char getChar(int p_getChar_1_) {
      return this.field_150794_a.getChar(p_getChar_1_);
   }

   public float getFloat(int p_getFloat_1_) {
      return this.field_150794_a.getFloat(p_getFloat_1_);
   }

   public double getDouble(int p_getDouble_1_) {
      return this.field_150794_a.getDouble(p_getDouble_1_);
   }

   public ByteBuf getBytes(int p_getBytes_1_, ByteBuf p_getBytes_2_) {
      return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_);
   }

   public ByteBuf getBytes(int p_getBytes_1_, ByteBuf p_getBytes_2_, int p_getBytes_3_) {
      return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
   }

   public ByteBuf getBytes(int p_getBytes_1_, ByteBuf p_getBytes_2_, int p_getBytes_3_, int p_getBytes_4_) {
      return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_4_);
   }

   public ByteBuf getBytes(int p_getBytes_1_, byte[] p_getBytes_2_) {
      return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_);
   }

   public ByteBuf getBytes(int p_getBytes_1_, byte[] p_getBytes_2_, int p_getBytes_3_, int p_getBytes_4_) {
      return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_4_);
   }

   public ByteBuf getBytes(int p_getBytes_1_, ByteBuffer p_getBytes_2_) {
      return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_);
   }

   public ByteBuf getBytes(int p_getBytes_1_, OutputStream p_getBytes_2_, int p_getBytes_3_) throws IOException {
      return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
   }

   public int getBytes(int p_getBytes_1_, GatheringByteChannel p_getBytes_2_, int p_getBytes_3_) throws IOException {
      return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
   }

   public int getBytes(int p_getBytes_1_, FileChannel p_getBytes_2_, long p_getBytes_3_, int p_getBytes_5_) throws IOException {
      return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_5_);
   }

   public CharSequence getCharSequence(int p_getCharSequence_1_, int p_getCharSequence_2_, Charset p_getCharSequence_3_) {
      return this.field_150794_a.getCharSequence(p_getCharSequence_1_, p_getCharSequence_2_, p_getCharSequence_3_);
   }

   public ByteBuf setBoolean(int p_setBoolean_1_, boolean p_setBoolean_2_) {
      return this.field_150794_a.setBoolean(p_setBoolean_1_, p_setBoolean_2_);
   }

   public ByteBuf setByte(int p_setByte_1_, int p_setByte_2_) {
      return this.field_150794_a.setByte(p_setByte_1_, p_setByte_2_);
   }

   public ByteBuf setShort(int p_setShort_1_, int p_setShort_2_) {
      return this.field_150794_a.setShort(p_setShort_1_, p_setShort_2_);
   }

   public ByteBuf setShortLE(int p_setShortLE_1_, int p_setShortLE_2_) {
      return this.field_150794_a.setShortLE(p_setShortLE_1_, p_setShortLE_2_);
   }

   public ByteBuf setMedium(int p_setMedium_1_, int p_setMedium_2_) {
      return this.field_150794_a.setMedium(p_setMedium_1_, p_setMedium_2_);
   }

   public ByteBuf setMediumLE(int p_setMediumLE_1_, int p_setMediumLE_2_) {
      return this.field_150794_a.setMediumLE(p_setMediumLE_1_, p_setMediumLE_2_);
   }

   public ByteBuf setInt(int p_setInt_1_, int p_setInt_2_) {
      return this.field_150794_a.setInt(p_setInt_1_, p_setInt_2_);
   }

   public ByteBuf setIntLE(int p_setIntLE_1_, int p_setIntLE_2_) {
      return this.field_150794_a.setIntLE(p_setIntLE_1_, p_setIntLE_2_);
   }

   public ByteBuf setLong(int p_setLong_1_, long p_setLong_2_) {
      return this.field_150794_a.setLong(p_setLong_1_, p_setLong_2_);
   }

   public ByteBuf setLongLE(int p_setLongLE_1_, long p_setLongLE_2_) {
      return this.field_150794_a.setLongLE(p_setLongLE_1_, p_setLongLE_2_);
   }

   public ByteBuf setChar(int p_setChar_1_, int p_setChar_2_) {
      return this.field_150794_a.setChar(p_setChar_1_, p_setChar_2_);
   }

   public ByteBuf setFloat(int p_setFloat_1_, float p_setFloat_2_) {
      return this.field_150794_a.setFloat(p_setFloat_1_, p_setFloat_2_);
   }

   public ByteBuf setDouble(int p_setDouble_1_, double p_setDouble_2_) {
      return this.field_150794_a.setDouble(p_setDouble_1_, p_setDouble_2_);
   }

   public ByteBuf setBytes(int p_setBytes_1_, ByteBuf p_setBytes_2_) {
      return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_);
   }

   public ByteBuf setBytes(int p_setBytes_1_, ByteBuf p_setBytes_2_, int p_setBytes_3_) {
      return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
   }

   public ByteBuf setBytes(int p_setBytes_1_, ByteBuf p_setBytes_2_, int p_setBytes_3_, int p_setBytes_4_) {
      return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_4_);
   }

   public ByteBuf setBytes(int p_setBytes_1_, byte[] p_setBytes_2_) {
      return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_);
   }

   public ByteBuf setBytes(int p_setBytes_1_, byte[] p_setBytes_2_, int p_setBytes_3_, int p_setBytes_4_) {
      return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_4_);
   }

   public ByteBuf setBytes(int p_setBytes_1_, ByteBuffer p_setBytes_2_) {
      return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_);
   }

   public int setBytes(int p_setBytes_1_, InputStream p_setBytes_2_, int p_setBytes_3_) throws IOException {
      return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
   }

   public int setBytes(int p_setBytes_1_, ScatteringByteChannel p_setBytes_2_, int p_setBytes_3_) throws IOException {
      return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
   }

   public int setBytes(int p_setBytes_1_, FileChannel p_setBytes_2_, long p_setBytes_3_, int p_setBytes_5_) throws IOException {
      return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_5_);
   }

   public ByteBuf setZero(int p_setZero_1_, int p_setZero_2_) {
      return this.field_150794_a.setZero(p_setZero_1_, p_setZero_2_);
   }

   public int setCharSequence(int p_setCharSequence_1_, CharSequence p_setCharSequence_2_, Charset p_setCharSequence_3_) {
      return this.field_150794_a.setCharSequence(p_setCharSequence_1_, p_setCharSequence_2_, p_setCharSequence_3_);
   }

   public boolean readBoolean() {
      return this.field_150794_a.readBoolean();
   }

   public byte readByte() {
      return this.field_150794_a.readByte();
   }

   public short readUnsignedByte() {
      return this.field_150794_a.readUnsignedByte();
   }

   public short readShort() {
      return this.field_150794_a.readShort();
   }

   public short readShortLE() {
      return this.field_150794_a.readShortLE();
   }

   public int readUnsignedShort() {
      return this.field_150794_a.readUnsignedShort();
   }

   public int readUnsignedShortLE() {
      return this.field_150794_a.readUnsignedShortLE();
   }

   public int readMedium() {
      return this.field_150794_a.readMedium();
   }

   public int readMediumLE() {
      return this.field_150794_a.readMediumLE();
   }

   public int readUnsignedMedium() {
      return this.field_150794_a.readUnsignedMedium();
   }

   public int readUnsignedMediumLE() {
      return this.field_150794_a.readUnsignedMediumLE();
   }

   public int readInt() {
      return this.field_150794_a.readInt();
   }

   public int readIntLE() {
      return this.field_150794_a.readIntLE();
   }

   public long readUnsignedInt() {
      return this.field_150794_a.readUnsignedInt();
   }

   public long readUnsignedIntLE() {
      return this.field_150794_a.readUnsignedIntLE();
   }

   public long readLong() {
      return this.field_150794_a.readLong();
   }

   public long readLongLE() {
      return this.field_150794_a.readLongLE();
   }

   public char readChar() {
      return this.field_150794_a.readChar();
   }

   public float readFloat() {
      return this.field_150794_a.readFloat();
   }

   public double readDouble() {
      return this.field_150794_a.readDouble();
   }

   public ByteBuf readBytes(int p_readBytes_1_) {
      return this.field_150794_a.readBytes(p_readBytes_1_);
   }

   public ByteBuf readSlice(int p_readSlice_1_) {
      return this.field_150794_a.readSlice(p_readSlice_1_);
   }

   public ByteBuf readRetainedSlice(int p_readRetainedSlice_1_) {
      return this.field_150794_a.readRetainedSlice(p_readRetainedSlice_1_);
   }

   public ByteBuf readBytes(ByteBuf p_readBytes_1_) {
      return this.field_150794_a.readBytes(p_readBytes_1_);
   }

   public ByteBuf readBytes(ByteBuf p_readBytes_1_, int p_readBytes_2_) {
      return this.field_150794_a.readBytes(p_readBytes_1_, p_readBytes_2_);
   }

   public ByteBuf readBytes(ByteBuf p_readBytes_1_, int p_readBytes_2_, int p_readBytes_3_) {
      return this.field_150794_a.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_3_);
   }

   public ByteBuf readBytes(byte[] p_readBytes_1_) {
      return this.field_150794_a.readBytes(p_readBytes_1_);
   }

   public ByteBuf readBytes(byte[] p_readBytes_1_, int p_readBytes_2_, int p_readBytes_3_) {
      return this.field_150794_a.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_3_);
   }

   public ByteBuf readBytes(ByteBuffer p_readBytes_1_) {
      return this.field_150794_a.readBytes(p_readBytes_1_);
   }

   public ByteBuf readBytes(OutputStream p_readBytes_1_, int p_readBytes_2_) throws IOException {
      return this.field_150794_a.readBytes(p_readBytes_1_, p_readBytes_2_);
   }

   public int readBytes(GatheringByteChannel p_readBytes_1_, int p_readBytes_2_) throws IOException {
      return this.field_150794_a.readBytes(p_readBytes_1_, p_readBytes_2_);
   }

   public CharSequence readCharSequence(int p_readCharSequence_1_, Charset p_readCharSequence_2_) {
      return this.field_150794_a.readCharSequence(p_readCharSequence_1_, p_readCharSequence_2_);
   }

   public int readBytes(FileChannel p_readBytes_1_, long p_readBytes_2_, int p_readBytes_4_) throws IOException {
      return this.field_150794_a.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_4_);
   }

   public ByteBuf skipBytes(int p_skipBytes_1_) {
      return this.field_150794_a.skipBytes(p_skipBytes_1_);
   }

   public ByteBuf writeBoolean(boolean p_writeBoolean_1_) {
      return this.field_150794_a.writeBoolean(p_writeBoolean_1_);
   }

   public ByteBuf writeByte(int p_writeByte_1_) {
      return this.field_150794_a.writeByte(p_writeByte_1_);
   }

   public ByteBuf writeShort(int p_writeShort_1_) {
      return this.field_150794_a.writeShort(p_writeShort_1_);
   }

   public ByteBuf writeShortLE(int p_writeShortLE_1_) {
      return this.field_150794_a.writeShortLE(p_writeShortLE_1_);
   }

   public ByteBuf writeMedium(int p_writeMedium_1_) {
      return this.field_150794_a.writeMedium(p_writeMedium_1_);
   }

   public ByteBuf writeMediumLE(int p_writeMediumLE_1_) {
      return this.field_150794_a.writeMediumLE(p_writeMediumLE_1_);
   }

   public ByteBuf writeInt(int p_writeInt_1_) {
      return this.field_150794_a.writeInt(p_writeInt_1_);
   }

   public ByteBuf writeIntLE(int p_writeIntLE_1_) {
      return this.field_150794_a.writeIntLE(p_writeIntLE_1_);
   }

   public ByteBuf writeLong(long p_writeLong_1_) {
      return this.field_150794_a.writeLong(p_writeLong_1_);
   }

   public ByteBuf writeLongLE(long p_writeLongLE_1_) {
      return this.field_150794_a.writeLongLE(p_writeLongLE_1_);
   }

   public ByteBuf writeChar(int p_writeChar_1_) {
      return this.field_150794_a.writeChar(p_writeChar_1_);
   }

   public ByteBuf writeFloat(float p_writeFloat_1_) {
      return this.field_150794_a.writeFloat(p_writeFloat_1_);
   }

   public ByteBuf writeDouble(double p_writeDouble_1_) {
      return this.field_150794_a.writeDouble(p_writeDouble_1_);
   }

   public ByteBuf writeBytes(ByteBuf p_writeBytes_1_) {
      return this.field_150794_a.writeBytes(p_writeBytes_1_);
   }

   public ByteBuf writeBytes(ByteBuf p_writeBytes_1_, int p_writeBytes_2_) {
      return this.field_150794_a.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
   }

   public ByteBuf writeBytes(ByteBuf p_writeBytes_1_, int p_writeBytes_2_, int p_writeBytes_3_) {
      return this.field_150794_a.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_3_);
   }

   public ByteBuf writeBytes(byte[] p_writeBytes_1_) {
      return this.field_150794_a.writeBytes(p_writeBytes_1_);
   }

   public ByteBuf writeBytes(byte[] p_writeBytes_1_, int p_writeBytes_2_, int p_writeBytes_3_) {
      return this.field_150794_a.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_3_);
   }

   public ByteBuf writeBytes(ByteBuffer p_writeBytes_1_) {
      return this.field_150794_a.writeBytes(p_writeBytes_1_);
   }

   public int writeBytes(InputStream p_writeBytes_1_, int p_writeBytes_2_) throws IOException {
      return this.field_150794_a.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
   }

   public int writeBytes(ScatteringByteChannel p_writeBytes_1_, int p_writeBytes_2_) throws IOException {
      return this.field_150794_a.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
   }

   public int writeBytes(FileChannel p_writeBytes_1_, long p_writeBytes_2_, int p_writeBytes_4_) throws IOException {
      return this.field_150794_a.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_4_);
   }

   public ByteBuf writeZero(int p_writeZero_1_) {
      return this.field_150794_a.writeZero(p_writeZero_1_);
   }

   public int writeCharSequence(CharSequence p_writeCharSequence_1_, Charset p_writeCharSequence_2_) {
      return this.field_150794_a.writeCharSequence(p_writeCharSequence_1_, p_writeCharSequence_2_);
   }

   public int indexOf(int p_indexOf_1_, int p_indexOf_2_, byte p_indexOf_3_) {
      return this.field_150794_a.indexOf(p_indexOf_1_, p_indexOf_2_, p_indexOf_3_);
   }

   public int bytesBefore(byte p_bytesBefore_1_) {
      return this.field_150794_a.bytesBefore(p_bytesBefore_1_);
   }

   public int bytesBefore(int p_bytesBefore_1_, byte p_bytesBefore_2_) {
      return this.field_150794_a.bytesBefore(p_bytesBefore_1_, p_bytesBefore_2_);
   }

   public int bytesBefore(int p_bytesBefore_1_, int p_bytesBefore_2_, byte p_bytesBefore_3_) {
      return this.field_150794_a.bytesBefore(p_bytesBefore_1_, p_bytesBefore_2_, p_bytesBefore_3_);
   }

   public int forEachByte(ByteProcessor p_forEachByte_1_) {
      return this.field_150794_a.forEachByte(p_forEachByte_1_);
   }

   public int forEachByte(int p_forEachByte_1_, int p_forEachByte_2_, ByteProcessor p_forEachByte_3_) {
      return this.field_150794_a.forEachByte(p_forEachByte_1_, p_forEachByte_2_, p_forEachByte_3_);
   }

   public int forEachByteDesc(ByteProcessor p_forEachByteDesc_1_) {
      return this.field_150794_a.forEachByteDesc(p_forEachByteDesc_1_);
   }

   public int forEachByteDesc(int p_forEachByteDesc_1_, int p_forEachByteDesc_2_, ByteProcessor p_forEachByteDesc_3_) {
      return this.field_150794_a.forEachByteDesc(p_forEachByteDesc_1_, p_forEachByteDesc_2_, p_forEachByteDesc_3_);
   }

   public ByteBuf copy() {
      return this.field_150794_a.copy();
   }

   public ByteBuf copy(int p_copy_1_, int p_copy_2_) {
      return this.field_150794_a.copy(p_copy_1_, p_copy_2_);
   }

   public ByteBuf slice() {
      return this.field_150794_a.slice();
   }

   public ByteBuf retainedSlice() {
      return this.field_150794_a.retainedSlice();
   }

   public ByteBuf slice(int p_slice_1_, int p_slice_2_) {
      return this.field_150794_a.slice(p_slice_1_, p_slice_2_);
   }

   public ByteBuf retainedSlice(int p_retainedSlice_1_, int p_retainedSlice_2_) {
      return this.field_150794_a.retainedSlice(p_retainedSlice_1_, p_retainedSlice_2_);
   }

   public ByteBuf duplicate() {
      return this.field_150794_a.duplicate();
   }

   public ByteBuf retainedDuplicate() {
      return this.field_150794_a.retainedDuplicate();
   }

   public int nioBufferCount() {
      return this.field_150794_a.nioBufferCount();
   }

   public ByteBuffer nioBuffer() {
      return this.field_150794_a.nioBuffer();
   }

   public ByteBuffer nioBuffer(int p_nioBuffer_1_, int p_nioBuffer_2_) {
      return this.field_150794_a.nioBuffer(p_nioBuffer_1_, p_nioBuffer_2_);
   }

   public ByteBuffer internalNioBuffer(int p_internalNioBuffer_1_, int p_internalNioBuffer_2_) {
      return this.field_150794_a.internalNioBuffer(p_internalNioBuffer_1_, p_internalNioBuffer_2_);
   }

   public ByteBuffer[] nioBuffers() {
      return this.field_150794_a.nioBuffers();
   }

   public ByteBuffer[] nioBuffers(int p_nioBuffers_1_, int p_nioBuffers_2_) {
      return this.field_150794_a.nioBuffers(p_nioBuffers_1_, p_nioBuffers_2_);
   }

   public boolean hasArray() {
      return this.field_150794_a.hasArray();
   }

   public byte[] array() {
      return this.field_150794_a.array();
   }

   public int arrayOffset() {
      return this.field_150794_a.arrayOffset();
   }

   public boolean hasMemoryAddress() {
      return this.field_150794_a.hasMemoryAddress();
   }

   public long memoryAddress() {
      return this.field_150794_a.memoryAddress();
   }

   public String toString(Charset p_toString_1_) {
      return this.field_150794_a.toString(p_toString_1_);
   }

   public String toString(int p_toString_1_, int p_toString_2_, Charset p_toString_3_) {
      return this.field_150794_a.toString(p_toString_1_, p_toString_2_, p_toString_3_);
   }

   public int hashCode() {
      return this.field_150794_a.hashCode();
   }

   public boolean equals(Object p_equals_1_) {
      return this.field_150794_a.equals(p_equals_1_);
   }

   public int compareTo(ByteBuf p_compareTo_1_) {
      return this.field_150794_a.compareTo(p_compareTo_1_);
   }

   public String toString() {
      return this.field_150794_a.toString();
   }

   public ByteBuf retain(int p_retain_1_) {
      return this.field_150794_a.retain(p_retain_1_);
   }

   public ByteBuf retain() {
      return this.field_150794_a.retain();
   }

   public ByteBuf touch() {
      return this.field_150794_a.touch();
   }

   public ByteBuf touch(Object p_touch_1_) {
      return this.field_150794_a.touch(p_touch_1_);
   }

   public int refCnt() {
      return this.field_150794_a.refCnt();
   }

   public boolean release() {
      return this.field_150794_a.release();
   }

   public boolean release(int p_release_1_) {
      return this.field_150794_a.release(p_release_1_);
   }
}
