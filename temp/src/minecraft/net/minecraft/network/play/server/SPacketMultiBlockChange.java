package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;

public class SPacketMultiBlockChange implements Packet<INetHandlerPlayClient> {
   private ChunkPos field_148925_b;
   private SPacketMultiBlockChange.BlockUpdateData[] field_179845_b;

   public SPacketMultiBlockChange() {
   }

   public SPacketMultiBlockChange(int p_i46959_1_, short[] p_i46959_2_, Chunk p_i46959_3_) {
      this.field_148925_b = new ChunkPos(p_i46959_3_.field_76635_g, p_i46959_3_.field_76647_h);
      this.field_179845_b = new SPacketMultiBlockChange.BlockUpdateData[p_i46959_1_];

      for(int i = 0; i < this.field_179845_b.length; ++i) {
         this.field_179845_b[i] = new SPacketMultiBlockChange.BlockUpdateData(p_i46959_2_[i], p_i46959_3_);
      }

   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_148925_b = new ChunkPos(p_148837_1_.readInt(), p_148837_1_.readInt());
      this.field_179845_b = new SPacketMultiBlockChange.BlockUpdateData[p_148837_1_.func_150792_a()];

      for(int i = 0; i < this.field_179845_b.length; ++i) {
         this.field_179845_b[i] = new SPacketMultiBlockChange.BlockUpdateData(p_148837_1_.readShort(), Block.field_176229_d.func_148745_a(p_148837_1_.func_150792_a()));
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeInt(this.field_148925_b.field_77276_a);
      p_148840_1_.writeInt(this.field_148925_b.field_77275_b);
      p_148840_1_.func_150787_b(this.field_179845_b.length);

      for(SPacketMultiBlockChange.BlockUpdateData spacketmultiblockchange$blockupdatedata : this.field_179845_b) {
         p_148840_1_.writeShort(spacketmultiblockchange$blockupdatedata.func_180089_b());
         p_148840_1_.func_150787_b(Block.field_176229_d.func_148747_b(spacketmultiblockchange$blockupdatedata.func_180088_c()));
      }

   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147287_a(this);
   }

   public SPacketMultiBlockChange.BlockUpdateData[] func_179844_a() {
      return this.field_179845_b;
   }

   public class BlockUpdateData {
      private final short field_180091_b;
      private final IBlockState field_180092_c;

      public BlockUpdateData(short p_i46544_2_, IBlockState p_i46544_3_) {
         this.field_180091_b = p_i46544_2_;
         this.field_180092_c = p_i46544_3_;
      }

      public BlockUpdateData(short p_i46545_2_, Chunk p_i46545_3_) {
         this.field_180091_b = p_i46545_2_;
         this.field_180092_c = p_i46545_3_.func_177435_g(this.func_180090_a());
      }

      public BlockPos func_180090_a() {
         return new BlockPos(SPacketMultiBlockChange.this.field_148925_b.func_180331_a(this.field_180091_b >> 12 & 15, this.field_180091_b & 255, this.field_180091_b >> 8 & 15));
      }

      public short func_180089_b() {
         return this.field_180091_b;
      }

      public IBlockState func_180088_c() {
         return this.field_180092_c;
      }
   }
}
