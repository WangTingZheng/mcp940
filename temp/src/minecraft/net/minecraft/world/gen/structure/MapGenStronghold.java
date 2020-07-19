package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class MapGenStronghold extends MapGenStructure {
   private final List<Biome> field_151546_e;
   private boolean field_75056_f;
   private ChunkPos[] field_75057_g;
   private double field_82671_h;
   private int field_82672_i;

   public MapGenStronghold() {
      this.field_75057_g = new ChunkPos[128];
      this.field_82671_h = 32.0D;
      this.field_82672_i = 3;
      this.field_151546_e = Lists.<Biome>newArrayList();

      for(Biome biome : Biome.field_185377_q) {
         if (biome != null && biome.func_185355_j() > 0.0F) {
            this.field_151546_e.add(biome);
         }
      }

   }

   public MapGenStronghold(Map<String, String> p_i2068_1_) {
      this();

      for(Entry<String, String> entry : p_i2068_1_.entrySet()) {
         if (((String)entry.getKey()).equals("distance")) {
            this.field_82671_h = MathHelper.func_82713_a(entry.getValue(), this.field_82671_h, 1.0D);
         } else if (((String)entry.getKey()).equals("count")) {
            this.field_75057_g = new ChunkPos[MathHelper.func_82714_a(entry.getValue(), this.field_75057_g.length, 1)];
         } else if (((String)entry.getKey()).equals("spread")) {
            this.field_82672_i = MathHelper.func_82714_a(entry.getValue(), this.field_82672_i, 1);
         }
      }

   }

   public String func_143025_a() {
      return "Stronghold";
   }

   public BlockPos func_180706_b(World p_180706_1_, BlockPos p_180706_2_, boolean p_180706_3_) {
      if (!this.field_75056_f) {
         this.func_189104_c();
         this.field_75056_f = true;
      }

      BlockPos blockpos = null;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(0, 0, 0);
      double d0 = Double.MAX_VALUE;

      for(ChunkPos chunkpos : this.field_75057_g) {
         blockpos$mutableblockpos.func_181079_c((chunkpos.field_77276_a << 4) + 8, 32, (chunkpos.field_77275_b << 4) + 8);
         double d1 = blockpos$mutableblockpos.func_177951_i(p_180706_2_);
         if (blockpos == null) {
            blockpos = new BlockPos(blockpos$mutableblockpos);
            d0 = d1;
         } else if (d1 < d0) {
            blockpos = new BlockPos(blockpos$mutableblockpos);
            d0 = d1;
         }
      }

      return blockpos;
   }

   protected boolean func_75047_a(int p_75047_1_, int p_75047_2_) {
      if (!this.field_75056_f) {
         this.func_189104_c();
         this.field_75056_f = true;
      }

      for(ChunkPos chunkpos : this.field_75057_g) {
         if (p_75047_1_ == chunkpos.field_77276_a && p_75047_2_ == chunkpos.field_77275_b) {
            return true;
         }
      }

      return false;
   }

   private void func_189104_c() {
      this.func_143027_a(this.field_75039_c);
      int i = 0;
      ObjectIterator lvt_2_1_ = this.field_75053_d.values().iterator();

      while(lvt_2_1_.hasNext()) {
         StructureStart structurestart = (StructureStart)lvt_2_1_.next();
         if (i < this.field_75057_g.length) {
            this.field_75057_g[i++] = new ChunkPos(structurestart.func_143019_e(), structurestart.func_143018_f());
         }
      }

      Random random = new Random();
      random.setSeed(this.field_75039_c.func_72905_C());
      double d1 = random.nextDouble() * 3.141592653589793D * 2.0D;
      int j = 0;
      int k = 0;
      int l = this.field_75053_d.size();
      if (l < this.field_75057_g.length) {
         for(int i1 = 0; i1 < this.field_75057_g.length; ++i1) {
            double d0 = 4.0D * this.field_82671_h + this.field_82671_h * (double)j * 6.0D + (random.nextDouble() - 0.5D) * this.field_82671_h * 2.5D;
            int j1 = (int)Math.round(Math.cos(d1) * d0);
            int k1 = (int)Math.round(Math.sin(d1) * d0);
            BlockPos blockpos = this.field_75039_c.func_72959_q().func_180630_a((j1 << 4) + 8, (k1 << 4) + 8, 112, this.field_151546_e, random);
            if (blockpos != null) {
               j1 = blockpos.func_177958_n() >> 4;
               k1 = blockpos.func_177952_p() >> 4;
            }

            if (i1 >= l) {
               this.field_75057_g[i1] = new ChunkPos(j1, k1);
            }

            d1 += 6.283185307179586D / (double)this.field_82672_i;
            ++k;
            if (k == this.field_82672_i) {
               ++j;
               k = 0;
               this.field_82672_i += 2 * this.field_82672_i / (j + 1);
               this.field_82672_i = Math.min(this.field_82672_i, this.field_75057_g.length - i1);
               d1 += random.nextDouble() * 3.141592653589793D * 2.0D;
            }
         }
      }

   }

   protected StructureStart func_75049_b(int p_75049_1_, int p_75049_2_) {
      MapGenStronghold.Start mapgenstronghold$start;
      for(mapgenstronghold$start = new MapGenStronghold.Start(this.field_75039_c, this.field_75038_b, p_75049_1_, p_75049_2_); mapgenstronghold$start.func_186161_c().isEmpty() || ((StructureStrongholdPieces.Stairs2)mapgenstronghold$start.func_186161_c().get(0)).field_75025_b == null; mapgenstronghold$start = new MapGenStronghold.Start(this.field_75039_c, this.field_75038_b, p_75049_1_, p_75049_2_)) {
         ;
      }

      return mapgenstronghold$start;
   }

   public static class Start extends StructureStart {
      public Start() {
      }

      public Start(World p_i2067_1_, Random p_i2067_2_, int p_i2067_3_, int p_i2067_4_) {
         super(p_i2067_3_, p_i2067_4_);
         StructureStrongholdPieces.func_75198_a();
         StructureStrongholdPieces.Stairs2 structurestrongholdpieces$stairs2 = new StructureStrongholdPieces.Stairs2(0, p_i2067_2_, (p_i2067_3_ << 4) + 2, (p_i2067_4_ << 4) + 2);
         this.field_75075_a.add(structurestrongholdpieces$stairs2);
         structurestrongholdpieces$stairs2.func_74861_a(structurestrongholdpieces$stairs2, this.field_75075_a, p_i2067_2_);
         List<StructureComponent> list = structurestrongholdpieces$stairs2.field_75026_c;

         while(!list.isEmpty()) {
            int i = p_i2067_2_.nextInt(list.size());
            StructureComponent structurecomponent = list.remove(i);
            structurecomponent.func_74861_a(structurestrongholdpieces$stairs2, this.field_75075_a, p_i2067_2_);
         }

         this.func_75072_c();
         this.func_75067_a(p_i2067_1_, p_i2067_2_, 10);
      }
   }
}
