package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.UUID;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class SPacketSpawnPainting implements Packet<INetHandlerPlayClient> {
   private int field_148973_a;
   private UUID field_186896_b;
   private BlockPos field_179838_b;
   private EnumFacing field_179839_c;
   private String field_148968_f;

   public SPacketSpawnPainting() {
   }

   public SPacketSpawnPainting(EntityPainting p_i46972_1_) {
      this.field_148973_a = p_i46972_1_.func_145782_y();
      this.field_186896_b = p_i46972_1_.func_110124_au();
      this.field_179838_b = p_i46972_1_.func_174857_n();
      this.field_179839_c = p_i46972_1_.field_174860_b;
      this.field_148968_f = p_i46972_1_.field_70522_e.field_75702_A;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_148973_a = p_148837_1_.func_150792_a();
      this.field_186896_b = p_148837_1_.func_179253_g();
      this.field_148968_f = p_148837_1_.func_150789_c(EntityPainting.EnumArt.field_180001_A);
      this.field_179838_b = p_148837_1_.func_179259_c();
      this.field_179839_c = EnumFacing.func_176731_b(p_148837_1_.readUnsignedByte());
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_148973_a);
      p_148840_1_.func_179252_a(this.field_186896_b);
      p_148840_1_.func_180714_a(this.field_148968_f);
      p_148840_1_.func_179255_a(this.field_179838_b);
      p_148840_1_.writeByte(this.field_179839_c.func_176736_b());
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147288_a(this);
   }

   public int func_148965_c() {
      return this.field_148973_a;
   }

   public UUID func_186895_b() {
      return this.field_186896_b;
   }

   public BlockPos func_179837_b() {
      return this.field_179838_b;
   }

   public EnumFacing func_179836_c() {
      return this.field_179839_c;
   }

   public String func_148961_h() {
      return this.field_148968_f;
   }
}
