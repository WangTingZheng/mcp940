package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;

public class SPacketServerDifficulty implements Packet<INetHandlerPlayClient> {
   private EnumDifficulty field_179833_a;
   private boolean field_179832_b;

   public SPacketServerDifficulty() {
   }

   public SPacketServerDifficulty(EnumDifficulty p_i46963_1_, boolean p_i46963_2_) {
      this.field_179833_a = p_i46963_1_;
      this.field_179832_b = p_i46963_2_;
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_175101_a(this);
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_179833_a = EnumDifficulty.func_151523_a(p_148837_1_.readUnsignedByte());
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeByte(this.field_179833_a.func_151525_a());
   }

   public boolean func_179830_a() {
      return this.field_179832_b;
   }

   public EnumDifficulty func_179831_b() {
      return this.field_179833_a;
   }
}
