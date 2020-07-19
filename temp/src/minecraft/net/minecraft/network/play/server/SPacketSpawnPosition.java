package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.math.BlockPos;

public class SPacketSpawnPosition implements Packet<INetHandlerPlayClient> {
   private BlockPos field_179801_a;

   public SPacketSpawnPosition() {
   }

   public SPacketSpawnPosition(BlockPos p_i46903_1_) {
      this.field_179801_a = p_i46903_1_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_179801_a = p_148837_1_.func_179259_c();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_179255_a(this.field_179801_a);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147271_a(this);
   }

   public BlockPos func_179800_a() {
      return this.field_179801_a;
   }
}
