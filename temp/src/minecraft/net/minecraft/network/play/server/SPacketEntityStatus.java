package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class SPacketEntityStatus implements Packet<INetHandlerPlayClient> {
   private int field_149164_a;
   private byte field_149163_b;

   public SPacketEntityStatus() {
   }

   public SPacketEntityStatus(Entity p_i46946_1_, byte p_i46946_2_) {
      this.field_149164_a = p_i46946_1_.func_145782_y();
      this.field_149163_b = p_i46946_2_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149164_a = p_148837_1_.readInt();
      this.field_149163_b = p_148837_1_.readByte();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeInt(this.field_149164_a);
      p_148840_1_.writeByte(this.field_149163_b);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147236_a(this);
   }

   public Entity func_149161_a(World p_149161_1_) {
      return p_149161_1_.func_73045_a(this.field_149164_a);
   }

   public byte func_149160_c() {
      return this.field_149163_b;
   }
}
