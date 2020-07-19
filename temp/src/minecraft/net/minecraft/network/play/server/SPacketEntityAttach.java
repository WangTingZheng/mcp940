package net.minecraft.network.play.server;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketEntityAttach implements Packet<INetHandlerPlayClient> {
   private int field_149406_b;
   private int field_149407_c;

   public SPacketEntityAttach() {
   }

   public SPacketEntityAttach(Entity p_i46916_1_, @Nullable Entity p_i46916_2_) {
      this.field_149406_b = p_i46916_1_.func_145782_y();
      this.field_149407_c = p_i46916_2_ != null ? p_i46916_2_.func_145782_y() : -1;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149406_b = p_148837_1_.readInt();
      this.field_149407_c = p_148837_1_.readInt();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeInt(this.field_149406_b);
      p_148840_1_.writeInt(this.field_149407_c);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147243_a(this);
   }

   public int func_149403_d() {
      return this.field_149406_b;
   }

   public int func_149402_e() {
      return this.field_149407_c;
   }
}
