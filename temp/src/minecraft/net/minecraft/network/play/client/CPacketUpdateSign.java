package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

public class CPacketUpdateSign implements Packet<INetHandlerPlayServer> {
   private BlockPos field_179723_a;
   private String[] field_149590_d;

   public CPacketUpdateSign() {
   }

   public CPacketUpdateSign(BlockPos p_i46861_1_, ITextComponent[] p_i46861_2_) {
      this.field_179723_a = p_i46861_1_;
      this.field_149590_d = new String[]{p_i46861_2_[0].func_150260_c(), p_i46861_2_[1].func_150260_c(), p_i46861_2_[2].func_150260_c(), p_i46861_2_[3].func_150260_c()};
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_179723_a = p_148837_1_.func_179259_c();
      this.field_149590_d = new String[4];

      for(int i = 0; i < 4; ++i) {
         this.field_149590_d[i] = p_148837_1_.func_150789_c(384);
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_179255_a(this.field_179723_a);

      for(int i = 0; i < 4; ++i) {
         p_148840_1_.func_180714_a(this.field_149590_d[i]);
      }

   }

   public void func_148833_a(INetHandlerPlayServer p_148833_1_) {
      p_148833_1_.func_147343_a(this);
   }

   public BlockPos func_179722_a() {
      return this.field_179723_a;
   }

   public String[] func_187017_b() {
      return this.field_149590_d;
   }
}
