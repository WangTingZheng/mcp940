package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketEntityAction implements Packet<INetHandlerPlayServer> {
   private int field_149517_a;
   private CPacketEntityAction.Action field_149515_b;
   private int field_149516_c;

   public CPacketEntityAction() {
   }

   public CPacketEntityAction(Entity p_i46869_1_, CPacketEntityAction.Action p_i46869_2_) {
      this(p_i46869_1_, p_i46869_2_, 0);
   }

   public CPacketEntityAction(Entity p_i46870_1_, CPacketEntityAction.Action p_i46870_2_, int p_i46870_3_) {
      this.field_149517_a = p_i46870_1_.func_145782_y();
      this.field_149515_b = p_i46870_2_;
      this.field_149516_c = p_i46870_3_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149517_a = p_148837_1_.func_150792_a();
      this.field_149515_b = (CPacketEntityAction.Action)p_148837_1_.func_179257_a(CPacketEntityAction.Action.class);
      this.field_149516_c = p_148837_1_.func_150792_a();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_149517_a);
      p_148840_1_.func_179249_a(this.field_149515_b);
      p_148840_1_.func_150787_b(this.field_149516_c);
   }

   public void func_148833_a(INetHandlerPlayServer p_148833_1_) {
      p_148833_1_.func_147357_a(this);
   }

   public CPacketEntityAction.Action func_180764_b() {
      return this.field_149515_b;
   }

   public int func_149512_e() {
      return this.field_149516_c;
   }

   public static enum Action {
      START_SNEAKING,
      STOP_SNEAKING,
      STOP_SLEEPING,
      START_SPRINTING,
      STOP_SPRINTING,
      START_RIDING_JUMP,
      STOP_RIDING_JUMP,
      OPEN_INVENTORY,
      START_FALL_FLYING;
   }
}
