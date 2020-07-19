package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.math.BlockPos;

public class SPacketUpdateTileEntity implements Packet<INetHandlerPlayClient> {
   private BlockPos field_179824_a;
   private int field_148859_d;
   private NBTTagCompound field_148860_e;

   public SPacketUpdateTileEntity() {
   }

   public SPacketUpdateTileEntity(BlockPos p_i46967_1_, int p_i46967_2_, NBTTagCompound p_i46967_3_) {
      this.field_179824_a = p_i46967_1_;
      this.field_148859_d = p_i46967_2_;
      this.field_148860_e = p_i46967_3_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_179824_a = p_148837_1_.func_179259_c();
      this.field_148859_d = p_148837_1_.readUnsignedByte();
      this.field_148860_e = p_148837_1_.func_150793_b();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_179255_a(this.field_179824_a);
      p_148840_1_.writeByte((byte)this.field_148859_d);
      p_148840_1_.func_150786_a(this.field_148860_e);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147273_a(this);
   }

   public BlockPos func_179823_a() {
      return this.field_179824_a;
   }

   public int func_148853_f() {
      return this.field_148859_d;
   }

   public NBTTagCompound func_148857_g() {
      return this.field_148860_e;
   }
}
