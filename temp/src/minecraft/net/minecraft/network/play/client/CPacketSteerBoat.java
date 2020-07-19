package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketSteerBoat implements Packet<INetHandlerPlayServer> {
   private boolean field_187015_a;
   private boolean field_187016_b;

   public CPacketSteerBoat() {
   }

   public CPacketSteerBoat(boolean p_i46873_1_, boolean p_i46873_2_) {
      this.field_187015_a = p_i46873_1_;
      this.field_187016_b = p_i46873_2_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_187015_a = p_148837_1_.readBoolean();
      this.field_187016_b = p_148837_1_.readBoolean();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeBoolean(this.field_187015_a);
      p_148840_1_.writeBoolean(this.field_187016_b);
   }

   public void func_148833_a(INetHandlerPlayServer p_148833_1_) {
      p_148833_1_.func_184340_a(this);
   }

   public boolean func_187012_a() {
      return this.field_187015_a;
   }

   public boolean func_187014_b() {
      return this.field_187016_b;
   }
}
