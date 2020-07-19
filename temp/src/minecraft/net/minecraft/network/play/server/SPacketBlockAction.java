package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.math.BlockPos;

public class SPacketBlockAction implements Packet<INetHandlerPlayClient> {
   private BlockPos field_179826_a;
   private int field_148872_d;
   private int field_148873_e;
   private Block field_148871_f;

   public SPacketBlockAction() {
   }

   public SPacketBlockAction(BlockPos p_i46966_1_, Block p_i46966_2_, int p_i46966_3_, int p_i46966_4_) {
      this.field_179826_a = p_i46966_1_;
      this.field_148872_d = p_i46966_3_;
      this.field_148873_e = p_i46966_4_;
      this.field_148871_f = p_i46966_2_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_179826_a = p_148837_1_.func_179259_c();
      this.field_148872_d = p_148837_1_.readUnsignedByte();
      this.field_148873_e = p_148837_1_.readUnsignedByte();
      this.field_148871_f = Block.func_149729_e(p_148837_1_.func_150792_a() & 4095);
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_179255_a(this.field_179826_a);
      p_148840_1_.writeByte(this.field_148872_d);
      p_148840_1_.writeByte(this.field_148873_e);
      p_148840_1_.func_150787_b(Block.func_149682_b(this.field_148871_f) & 4095);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147261_a(this);
   }

   public BlockPos func_179825_a() {
      return this.field_179826_a;
   }

   public int func_148869_g() {
      return this.field_148872_d;
   }

   public int func_148864_h() {
      return this.field_148873_e;
   }

   public Block func_148868_c() {
      return this.field_148871_f;
   }
}
