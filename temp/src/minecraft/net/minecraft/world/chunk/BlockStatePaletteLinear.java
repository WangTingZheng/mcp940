package net.minecraft.world.chunk;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.PacketBuffer;

public class BlockStatePaletteLinear implements IBlockStatePalette {
   private final IBlockState[] field_186042_a;
   private final IBlockStatePaletteResizer field_186043_b;
   private final int field_186044_c;
   private int field_186045_d;

   public BlockStatePaletteLinear(int p_i47088_1_, IBlockStatePaletteResizer p_i47088_2_) {
      this.field_186042_a = new IBlockState[1 << p_i47088_1_];
      this.field_186044_c = p_i47088_1_;
      this.field_186043_b = p_i47088_2_;
   }

   public int func_186041_a(IBlockState p_186041_1_) {
      for(int i = 0; i < this.field_186045_d; ++i) {
         if (this.field_186042_a[i] == p_186041_1_) {
            return i;
         }
      }

      int j = this.field_186045_d;
      if (j < this.field_186042_a.length) {
         this.field_186042_a[j] = p_186041_1_;
         ++this.field_186045_d;
         return j;
      } else {
         return this.field_186043_b.func_186008_a(this.field_186044_c + 1, p_186041_1_);
      }
   }

   @Nullable
   public IBlockState func_186039_a(int p_186039_1_) {
      return p_186039_1_ >= 0 && p_186039_1_ < this.field_186045_d ? this.field_186042_a[p_186039_1_] : null;
   }

   public void func_186038_a(PacketBuffer p_186038_1_) {
      this.field_186045_d = p_186038_1_.func_150792_a();

      for(int i = 0; i < this.field_186045_d; ++i) {
         this.field_186042_a[i] = Block.field_176229_d.func_148745_a(p_186038_1_.func_150792_a());
      }

   }

   public void func_186037_b(PacketBuffer p_186037_1_) {
      p_186037_1_.func_150787_b(this.field_186045_d);

      for(int i = 0; i < this.field_186045_d; ++i) {
         p_186037_1_.func_150787_b(Block.field_176229_d.func_148747_b(this.field_186042_a[i]));
      }

   }

   public int func_186040_a() {
      int i = PacketBuffer.func_150790_a(this.field_186045_d);

      for(int j = 0; j < this.field_186045_d; ++j) {
         i += PacketBuffer.func_150790_a(Block.field_176229_d.func_148747_b(this.field_186042_a[j]));
      }

      return i;
   }
}
