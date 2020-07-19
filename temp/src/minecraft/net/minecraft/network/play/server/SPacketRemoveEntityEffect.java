package net.minecraft.network.play.server;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

public class SPacketRemoveEntityEffect implements Packet<INetHandlerPlayClient> {
   private int field_149079_a;
   private Potion field_149078_b;

   public SPacketRemoveEntityEffect() {
   }

   public SPacketRemoveEntityEffect(int p_i46925_1_, Potion p_i46925_2_) {
      this.field_149079_a = p_i46925_1_;
      this.field_149078_b = p_i46925_2_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149079_a = p_148837_1_.func_150792_a();
      this.field_149078_b = Potion.func_188412_a(p_148837_1_.readUnsignedByte());
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_149079_a);
      p_148840_1_.writeByte(Potion.func_188409_a(this.field_149078_b));
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147262_a(this);
   }

   @Nullable
   public Entity func_186967_a(World p_186967_1_) {
      return p_186967_1_.func_73045_a(this.field_149079_a);
   }

   @Nullable
   public Potion func_186968_a() {
      return this.field_149078_b;
   }
}
