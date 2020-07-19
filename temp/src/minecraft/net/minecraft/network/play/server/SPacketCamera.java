package net.minecraft.network.play.server;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class SPacketCamera implements Packet<INetHandlerPlayClient> {
   public int field_179781_a;

   public SPacketCamera() {
   }

   public SPacketCamera(Entity p_i46920_1_) {
      this.field_179781_a = p_i46920_1_.func_145782_y();
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_179781_a = p_148837_1_.func_150792_a();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_179781_a);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_175094_a(this);
   }

   @Nullable
   public Entity func_179780_a(World p_179780_1_) {
      return p_179780_1_.func_73045_a(this.field_179781_a);
   }
}
