package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class SPacketChunkData implements Packet<INetHandlerPlayClient> {
   private int field_149284_a;
   private int field_149282_b;
   private int field_186948_c;
   private byte[] field_186949_d;
   private List<NBTTagCompound> field_189557_e;
   private boolean field_149279_g;

   public SPacketChunkData() {
   }

   public SPacketChunkData(Chunk p_i47124_1_, int p_i47124_2_) {
      this.field_149284_a = p_i47124_1_.field_76635_g;
      this.field_149282_b = p_i47124_1_.field_76647_h;
      this.field_149279_g = p_i47124_2_ == 65535;
      boolean flag = p_i47124_1_.func_177412_p().field_73011_w.func_191066_m();
      this.field_186949_d = new byte[this.func_189556_a(p_i47124_1_, flag, p_i47124_2_)];
      this.field_186948_c = this.func_189555_a(new PacketBuffer(this.func_186945_f()), p_i47124_1_, flag, p_i47124_2_);
      this.field_189557_e = Lists.<NBTTagCompound>newArrayList();

      for(Entry<BlockPos, TileEntity> entry : p_i47124_1_.func_177434_r().entrySet()) {
         BlockPos blockpos = entry.getKey();
         TileEntity tileentity = entry.getValue();
         int i = blockpos.func_177956_o() >> 4;
         if (this.func_149274_i() || (p_i47124_2_ & 1 << i) != 0) {
            NBTTagCompound nbttagcompound = tileentity.func_189517_E_();
            this.field_189557_e.add(nbttagcompound);
         }
      }

   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149284_a = p_148837_1_.readInt();
      this.field_149282_b = p_148837_1_.readInt();
      this.field_149279_g = p_148837_1_.readBoolean();
      this.field_186948_c = p_148837_1_.func_150792_a();
      int i = p_148837_1_.func_150792_a();
      if (i > 2097152) {
         throw new RuntimeException("Chunk Packet trying to allocate too much memory on read.");
      } else {
         this.field_186949_d = new byte[i];
         p_148837_1_.readBytes(this.field_186949_d);
         int j = p_148837_1_.func_150792_a();
         this.field_189557_e = Lists.<NBTTagCompound>newArrayList();

         for(int k = 0; k < j; ++k) {
            this.field_189557_e.add(p_148837_1_.func_150793_b());
         }

      }
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeInt(this.field_149284_a);
      p_148840_1_.writeInt(this.field_149282_b);
      p_148840_1_.writeBoolean(this.field_149279_g);
      p_148840_1_.func_150787_b(this.field_186948_c);
      p_148840_1_.func_150787_b(this.field_186949_d.length);
      p_148840_1_.writeBytes(this.field_186949_d);
      p_148840_1_.func_150787_b(this.field_189557_e.size());

      for(NBTTagCompound nbttagcompound : this.field_189557_e) {
         p_148840_1_.func_150786_a(nbttagcompound);
      }

   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147263_a(this);
   }

   public PacketBuffer func_186946_a() {
      return new PacketBuffer(Unpooled.wrappedBuffer(this.field_186949_d));
   }

   private ByteBuf func_186945_f() {
      ByteBuf bytebuf = Unpooled.wrappedBuffer(this.field_186949_d);
      bytebuf.writerIndex(0);
      return bytebuf;
   }

   public int func_189555_a(PacketBuffer p_189555_1_, Chunk p_189555_2_, boolean p_189555_3_, int p_189555_4_) {
      int i = 0;
      ExtendedBlockStorage[] aextendedblockstorage = p_189555_2_.func_76587_i();
      int j = 0;

      for(int k = aextendedblockstorage.length; j < k; ++j) {
         ExtendedBlockStorage extendedblockstorage = aextendedblockstorage[j];
         if (extendedblockstorage != Chunk.field_186036_a && (!this.func_149274_i() || !extendedblockstorage.func_76663_a()) && (p_189555_4_ & 1 << j) != 0) {
            i |= 1 << j;
            extendedblockstorage.func_186049_g().func_186009_b(p_189555_1_);
            p_189555_1_.writeBytes(extendedblockstorage.func_76661_k().func_177481_a());
            if (p_189555_3_) {
               p_189555_1_.writeBytes(extendedblockstorage.func_76671_l().func_177481_a());
            }
         }
      }

      if (this.func_149274_i()) {
         p_189555_1_.writeBytes(p_189555_2_.func_76605_m());
      }

      return i;
   }

   protected int func_189556_a(Chunk p_189556_1_, boolean p_189556_2_, int p_189556_3_) {
      int i = 0;
      ExtendedBlockStorage[] aextendedblockstorage = p_189556_1_.func_76587_i();
      int j = 0;

      for(int k = aextendedblockstorage.length; j < k; ++j) {
         ExtendedBlockStorage extendedblockstorage = aextendedblockstorage[j];
         if (extendedblockstorage != Chunk.field_186036_a && (!this.func_149274_i() || !extendedblockstorage.func_76663_a()) && (p_189556_3_ & 1 << j) != 0) {
            i = i + extendedblockstorage.func_186049_g().func_186018_a();
            i = i + extendedblockstorage.func_76661_k().func_177481_a().length;
            if (p_189556_2_) {
               i += extendedblockstorage.func_76671_l().func_177481_a().length;
            }
         }
      }

      if (this.func_149274_i()) {
         i += p_189556_1_.func_76605_m().length;
      }

      return i;
   }

   public int func_149273_e() {
      return this.field_149284_a;
   }

   public int func_149271_f() {
      return this.field_149282_b;
   }

   public int func_149276_g() {
      return this.field_186948_c;
   }

   public boolean func_149274_i() {
      return this.field_149279_g;
   }

   public List<NBTTagCompound> func_189554_f() {
      return this.field_189557_e;
   }
}
