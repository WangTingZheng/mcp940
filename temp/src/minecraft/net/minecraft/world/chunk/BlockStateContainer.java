package net.minecraft.world.chunk;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BitArray;
import net.minecraft.util.math.MathHelper;

public class BlockStateContainer implements IBlockStatePaletteResizer {
   private static final IBlockStatePalette field_186023_d = new BlockStatePaletteRegistry();
   protected static final IBlockState field_186020_a = Blocks.field_150350_a.func_176223_P();
   protected BitArray field_186021_b;
   protected IBlockStatePalette field_186022_c;
   private int field_186024_e;

   public BlockStateContainer() {
      this.func_186012_b(4);
   }

   private static int func_186011_b(int p_186011_0_, int p_186011_1_, int p_186011_2_) {
      return p_186011_1_ << 8 | p_186011_2_ << 4 | p_186011_0_;
   }

   private void func_186012_b(int p_186012_1_) {
      if (p_186012_1_ != this.field_186024_e) {
         this.field_186024_e = p_186012_1_;
         if (this.field_186024_e <= 4) {
            this.field_186024_e = 4;
            this.field_186022_c = new BlockStatePaletteLinear(this.field_186024_e, this);
         } else if (this.field_186024_e <= 8) {
            this.field_186022_c = new BlockStatePaletteHashMap(this.field_186024_e, this);
         } else {
            this.field_186022_c = field_186023_d;
            this.field_186024_e = MathHelper.func_151241_e(Block.field_176229_d.func_186804_a());
         }

         this.field_186022_c.func_186041_a(field_186020_a);
         this.field_186021_b = new BitArray(this.field_186024_e, 4096);
      }
   }

   public int func_186008_a(int p_186008_1_, IBlockState p_186008_2_) {
      BitArray bitarray = this.field_186021_b;
      IBlockStatePalette iblockstatepalette = this.field_186022_c;
      this.func_186012_b(p_186008_1_);

      for(int i = 0; i < bitarray.func_188144_b(); ++i) {
         IBlockState iblockstate = iblockstatepalette.func_186039_a(bitarray.func_188142_a(i));
         if (iblockstate != null) {
            this.func_186014_b(i, iblockstate);
         }
      }

      return this.field_186022_c.func_186041_a(p_186008_2_);
   }

   public void func_186013_a(int p_186013_1_, int p_186013_2_, int p_186013_3_, IBlockState p_186013_4_) {
      this.func_186014_b(func_186011_b(p_186013_1_, p_186013_2_, p_186013_3_), p_186013_4_);
   }

   protected void func_186014_b(int p_186014_1_, IBlockState p_186014_2_) {
      int i = this.field_186022_c.func_186041_a(p_186014_2_);
      this.field_186021_b.func_188141_a(p_186014_1_, i);
   }

   public IBlockState func_186016_a(int p_186016_1_, int p_186016_2_, int p_186016_3_) {
      return this.func_186015_a(func_186011_b(p_186016_1_, p_186016_2_, p_186016_3_));
   }

   protected IBlockState func_186015_a(int p_186015_1_) {
      IBlockState iblockstate = this.field_186022_c.func_186039_a(this.field_186021_b.func_188142_a(p_186015_1_));
      return iblockstate == null ? field_186020_a : iblockstate;
   }

   public void func_186010_a(PacketBuffer p_186010_1_) {
      int i = p_186010_1_.readByte();
      if (this.field_186024_e != i) {
         this.func_186012_b(i);
      }

      this.field_186022_c.func_186038_a(p_186010_1_);
      p_186010_1_.func_186873_b(this.field_186021_b.func_188143_a());
   }

   public void func_186009_b(PacketBuffer p_186009_1_) {
      p_186009_1_.writeByte(this.field_186024_e);
      this.field_186022_c.func_186037_b(p_186009_1_);
      p_186009_1_.func_186865_a(this.field_186021_b.func_188143_a());
   }

   @Nullable
   public NibbleArray func_186017_a(byte[] p_186017_1_, NibbleArray p_186017_2_) {
      NibbleArray nibblearray = null;

      for(int i = 0; i < 4096; ++i) {
         int j = Block.field_176229_d.func_148747_b(this.func_186015_a(i));
         int k = i & 15;
         int l = i >> 8 & 15;
         int i1 = i >> 4 & 15;
         if ((j >> 12 & 15) != 0) {
            if (nibblearray == null) {
               nibblearray = new NibbleArray();
            }

            nibblearray.func_76581_a(k, l, i1, j >> 12 & 15);
         }

         p_186017_1_[i] = (byte)(j >> 4 & 255);
         p_186017_2_.func_76581_a(k, l, i1, j & 15);
      }

      return nibblearray;
   }

   public void func_186019_a(byte[] p_186019_1_, NibbleArray p_186019_2_, @Nullable NibbleArray p_186019_3_) {
      for(int i = 0; i < 4096; ++i) {
         int j = i & 15;
         int k = i >> 8 & 15;
         int l = i >> 4 & 15;
         int i1 = p_186019_3_ == null ? 0 : p_186019_3_.func_76582_a(j, k, l);
         int j1 = i1 << 12 | (p_186019_1_[i] & 255) << 4 | p_186019_2_.func_76582_a(j, k, l);
         this.func_186014_b(i, Block.field_176229_d.func_148745_a(j1));
      }

   }

   public int func_186018_a() {
      return 1 + this.field_186022_c.func_186040_a() + PacketBuffer.func_150790_a(this.field_186021_b.func_188144_b()) + this.field_186021_b.func_188143_a().length * 8;
   }
}
