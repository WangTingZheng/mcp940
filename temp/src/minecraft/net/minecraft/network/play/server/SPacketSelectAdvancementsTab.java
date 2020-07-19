package net.minecraft.network.play.server;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.ResourceLocation;

public class SPacketSelectAdvancementsTab implements Packet<INetHandlerPlayClient> {
   @Nullable
   private ResourceLocation field_194155_a;

   public SPacketSelectAdvancementsTab() {
   }

   public SPacketSelectAdvancementsTab(@Nullable ResourceLocation p_i47596_1_) {
      this.field_194155_a = p_i47596_1_;
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_194022_a(this);
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      if (p_148837_1_.readBoolean()) {
         this.field_194155_a = p_148837_1_.func_192575_l();
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeBoolean(this.field_194155_a != null);
      if (this.field_194155_a != null) {
         p_148840_1_.func_192572_a(this.field_194155_a);
      }

   }

   @Nullable
   public ResourceLocation func_194154_a() {
      return this.field_194155_a;
   }
}
