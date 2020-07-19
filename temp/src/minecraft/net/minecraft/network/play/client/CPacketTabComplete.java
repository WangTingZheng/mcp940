package net.minecraft.network.play.client;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.StringUtils;

public class CPacketTabComplete implements Packet<INetHandlerPlayServer> {
   private String field_149420_a;
   private boolean field_186990_b;
   @Nullable
   private BlockPos field_179710_b;

   public CPacketTabComplete() {
   }

   public CPacketTabComplete(String p_i46888_1_, @Nullable BlockPos p_i46888_2_, boolean p_i46888_3_) {
      this.field_149420_a = p_i46888_1_;
      this.field_179710_b = p_i46888_2_;
      this.field_186990_b = p_i46888_3_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149420_a = p_148837_1_.func_150789_c(32767);
      this.field_186990_b = p_148837_1_.readBoolean();
      boolean flag = p_148837_1_.readBoolean();
      if (flag) {
         this.field_179710_b = p_148837_1_.func_179259_c();
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_180714_a(StringUtils.substring(this.field_149420_a, 0, 32767));
      p_148840_1_.writeBoolean(this.field_186990_b);
      boolean flag = this.field_179710_b != null;
      p_148840_1_.writeBoolean(flag);
      if (flag) {
         p_148840_1_.func_179255_a(this.field_179710_b);
      }

   }

   public void func_148833_a(INetHandlerPlayServer p_148833_1_) {
      p_148833_1_.func_147341_a(this);
   }

   public String func_149419_c() {
      return this.field_149420_a;
   }

   @Nullable
   public BlockPos func_179709_b() {
      return this.field_179710_b;
   }

   public boolean func_186989_c() {
      return this.field_186990_b;
   }
}
