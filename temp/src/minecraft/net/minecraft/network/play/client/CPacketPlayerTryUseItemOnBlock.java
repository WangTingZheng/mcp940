package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class CPacketPlayerTryUseItemOnBlock implements Packet<INetHandlerPlayServer> {
   private BlockPos field_179725_b;
   private EnumFacing field_149579_d;
   private EnumHand field_187027_c;
   private float field_149577_f;
   private float field_149578_g;
   private float field_149584_h;

   public CPacketPlayerTryUseItemOnBlock() {
   }

   public CPacketPlayerTryUseItemOnBlock(BlockPos p_i46858_1_, EnumFacing p_i46858_2_, EnumHand p_i46858_3_, float p_i46858_4_, float p_i46858_5_, float p_i46858_6_) {
      this.field_179725_b = p_i46858_1_;
      this.field_149579_d = p_i46858_2_;
      this.field_187027_c = p_i46858_3_;
      this.field_149577_f = p_i46858_4_;
      this.field_149578_g = p_i46858_5_;
      this.field_149584_h = p_i46858_6_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_179725_b = p_148837_1_.func_179259_c();
      this.field_149579_d = (EnumFacing)p_148837_1_.func_179257_a(EnumFacing.class);
      this.field_187027_c = (EnumHand)p_148837_1_.func_179257_a(EnumHand.class);
      this.field_149577_f = p_148837_1_.readFloat();
      this.field_149578_g = p_148837_1_.readFloat();
      this.field_149584_h = p_148837_1_.readFloat();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_179255_a(this.field_179725_b);
      p_148840_1_.func_179249_a(this.field_149579_d);
      p_148840_1_.func_179249_a(this.field_187027_c);
      p_148840_1_.writeFloat(this.field_149577_f);
      p_148840_1_.writeFloat(this.field_149578_g);
      p_148840_1_.writeFloat(this.field_149584_h);
   }

   public void func_148833_a(INetHandlerPlayServer p_148833_1_) {
      p_148833_1_.func_184337_a(this);
   }

   public BlockPos func_187023_a() {
      return this.field_179725_b;
   }

   public EnumFacing func_187024_b() {
      return this.field_149579_d;
   }

   public EnumHand func_187022_c() {
      return this.field_187027_c;
   }

   public float func_187026_d() {
      return this.field_149577_f;
   }

   public float func_187025_e() {
      return this.field_149578_g;
   }

   public float func_187020_f() {
      return this.field_149584_h;
   }
}
