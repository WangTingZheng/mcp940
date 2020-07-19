package net.minecraft.world.gen;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;

public class ChunkGeneratorDebug implements IChunkGenerator {
   private static final List<IBlockState> field_177464_a = Lists.<IBlockState>newArrayList();
   private static final int field_177462_b;
   private static final int field_181039_c;
   protected static final IBlockState field_185934_a = Blocks.field_150350_a.func_176223_P();
   protected static final IBlockState field_185935_b = Blocks.field_180401_cv.func_176223_P();
   private final World field_177463_c;

   public ChunkGeneratorDebug(World p_i45638_1_) {
      this.field_177463_c = p_i45638_1_;
   }

   public Chunk func_185932_a(int p_185932_1_, int p_185932_2_) {
      ChunkPrimer chunkprimer = new ChunkPrimer();

      for(int i = 0; i < 16; ++i) {
         for(int j = 0; j < 16; ++j) {
            int k = p_185932_1_ * 16 + i;
            int l = p_185932_2_ * 16 + j;
            chunkprimer.func_177855_a(i, 60, j, field_185935_b);
            IBlockState iblockstate = func_177461_b(k, l);
            if (iblockstate != null) {
               chunkprimer.func_177855_a(i, 70, j, iblockstate);
            }
         }
      }

      Chunk chunk = new Chunk(this.field_177463_c, chunkprimer, p_185932_1_, p_185932_2_);
      chunk.func_76603_b();
      Biome[] abiome = this.field_177463_c.func_72959_q().func_76933_b((Biome[])null, p_185932_1_ * 16, p_185932_2_ * 16, 16, 16);
      byte[] abyte = chunk.func_76605_m();

      for(int i1 = 0; i1 < abyte.length; ++i1) {
         abyte[i1] = (byte)Biome.func_185362_a(abiome[i1]);
      }

      chunk.func_76603_b();
      return chunk;
   }

   public static IBlockState func_177461_b(int p_177461_0_, int p_177461_1_) {
      IBlockState iblockstate = field_185934_a;
      if (p_177461_0_ > 0 && p_177461_1_ > 0 && p_177461_0_ % 2 != 0 && p_177461_1_ % 2 != 0) {
         p_177461_0_ = p_177461_0_ / 2;
         p_177461_1_ = p_177461_1_ / 2;
         if (p_177461_0_ <= field_177462_b && p_177461_1_ <= field_181039_c) {
            int i = MathHelper.func_76130_a(p_177461_0_ * field_177462_b + p_177461_1_);
            if (i < field_177464_a.size()) {
               iblockstate = field_177464_a.get(i);
            }
         }
      }

      return iblockstate;
   }

   public void func_185931_b(int p_185931_1_, int p_185931_2_) {
   }

   public boolean func_185933_a(Chunk p_185933_1_, int p_185933_2_, int p_185933_3_) {
      return false;
   }

   public List<Biome.SpawnListEntry> func_177458_a(EnumCreatureType p_177458_1_, BlockPos p_177458_2_) {
      Biome biome = this.field_177463_c.func_180494_b(p_177458_2_);
      return biome.func_76747_a(p_177458_1_);
   }

   @Nullable
   public BlockPos func_180513_a(World p_180513_1_, String p_180513_2_, BlockPos p_180513_3_, boolean p_180513_4_) {
      return null;
   }

   public boolean func_193414_a(World p_193414_1_, String p_193414_2_, BlockPos p_193414_3_) {
      return false;
   }

   public void func_180514_a(Chunk p_180514_1_, int p_180514_2_, int p_180514_3_) {
   }

   static {
      for(Block block : Block.field_149771_c) {
         field_177464_a.addAll(block.func_176194_O().func_177619_a());
      }

      field_177462_b = MathHelper.func_76123_f(MathHelper.func_76129_c((float)field_177464_a.size()));
      field_181039_c = MathHelper.func_76123_f((float)field_177464_a.size() / (float)field_177462_b);
   }
}
