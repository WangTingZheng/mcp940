package net.minecraft.world.gen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureOceanMonument;

public class ChunkGeneratorFlat implements IChunkGenerator {
   private final World field_73163_a;
   private final Random field_73161_b;
   private final IBlockState[] field_82700_c = new IBlockState[256];
   private final FlatGeneratorInfo field_82699_e;
   private final Map<String, MapGenStructure> field_82696_f = new HashMap<String, MapGenStructure>();
   private final boolean field_82697_g;
   private final boolean field_82702_h;
   private WorldGenLakes field_82703_i;
   private WorldGenLakes field_82701_j;

   public ChunkGeneratorFlat(World p_i2004_1_, long p_i2004_2_, boolean p_i2004_4_, String p_i2004_5_) {
      this.field_73163_a = p_i2004_1_;
      this.field_73161_b = new Random(p_i2004_2_);
      this.field_82699_e = FlatGeneratorInfo.func_82651_a(p_i2004_5_);
      if (p_i2004_4_) {
         Map<String, Map<String, String>> map = this.field_82699_e.func_82644_b();
         if (map.containsKey("village")) {
            Map<String, String> map1 = (Map)map.get("village");
            if (!map1.containsKey("size")) {
               map1.put("size", "1");
            }

            this.field_82696_f.put("Village", new MapGenVillage(map1));
         }

         if (map.containsKey("biome_1")) {
            this.field_82696_f.put("Temple", new MapGenScatteredFeature(map.get("biome_1")));
         }

         if (map.containsKey("mineshaft")) {
            this.field_82696_f.put("Mineshaft", new MapGenMineshaft(map.get("mineshaft")));
         }

         if (map.containsKey("stronghold")) {
            this.field_82696_f.put("Stronghold", new MapGenStronghold(map.get("stronghold")));
         }

         if (map.containsKey("oceanmonument")) {
            this.field_82696_f.put("Monument", new StructureOceanMonument(map.get("oceanmonument")));
         }
      }

      if (this.field_82699_e.func_82644_b().containsKey("lake")) {
         this.field_82703_i = new WorldGenLakes(Blocks.field_150355_j);
      }

      if (this.field_82699_e.func_82644_b().containsKey("lava_lake")) {
         this.field_82701_j = new WorldGenLakes(Blocks.field_150353_l);
      }

      this.field_82702_h = this.field_82699_e.func_82644_b().containsKey("dungeon");
      int j = 0;
      int k = 0;
      boolean flag = true;

      for(FlatLayerInfo flatlayerinfo : this.field_82699_e.func_82650_c()) {
         for(int i = flatlayerinfo.func_82656_d(); i < flatlayerinfo.func_82656_d() + flatlayerinfo.func_82657_a(); ++i) {
            IBlockState iblockstate = flatlayerinfo.func_175900_c();
            if (iblockstate.func_177230_c() != Blocks.field_150350_a) {
               flag = false;
               this.field_82700_c[i] = iblockstate;
            }
         }

         if (flatlayerinfo.func_175900_c().func_177230_c() == Blocks.field_150350_a) {
            k += flatlayerinfo.func_82657_a();
         } else {
            j += flatlayerinfo.func_82657_a() + k;
            k = 0;
         }
      }

      p_i2004_1_.func_181544_b(j);
      this.field_82697_g = flag && this.field_82699_e.func_82648_a() != Biome.func_185362_a(Biomes.field_185440_P) ? false : this.field_82699_e.func_82644_b().containsKey("decoration");
   }

   public Chunk func_185932_a(int p_185932_1_, int p_185932_2_) {
      ChunkPrimer chunkprimer = new ChunkPrimer();

      for(int i = 0; i < this.field_82700_c.length; ++i) {
         IBlockState iblockstate = this.field_82700_c[i];
         if (iblockstate != null) {
            for(int j = 0; j < 16; ++j) {
               for(int k = 0; k < 16; ++k) {
                  chunkprimer.func_177855_a(j, i, k, iblockstate);
               }
            }
         }
      }

      for(MapGenBase mapgenbase : this.field_82696_f.values()) {
         mapgenbase.func_186125_a(this.field_73163_a, p_185932_1_, p_185932_2_, chunkprimer);
      }

      Chunk chunk = new Chunk(this.field_73163_a, chunkprimer, p_185932_1_, p_185932_2_);
      Biome[] abiome = this.field_73163_a.func_72959_q().func_76933_b((Biome[])null, p_185932_1_ * 16, p_185932_2_ * 16, 16, 16);
      byte[] abyte = chunk.func_76605_m();

      for(int l = 0; l < abyte.length; ++l) {
         abyte[l] = (byte)Biome.func_185362_a(abiome[l]);
      }

      chunk.func_76603_b();
      return chunk;
   }

   public void func_185931_b(int p_185931_1_, int p_185931_2_) {
      int i = p_185931_1_ * 16;
      int j = p_185931_2_ * 16;
      BlockPos blockpos = new BlockPos(i, 0, j);
      Biome biome = this.field_73163_a.func_180494_b(new BlockPos(i + 16, 0, j + 16));
      boolean flag = false;
      this.field_73161_b.setSeed(this.field_73163_a.func_72905_C());
      long k = this.field_73161_b.nextLong() / 2L * 2L + 1L;
      long l = this.field_73161_b.nextLong() / 2L * 2L + 1L;
      this.field_73161_b.setSeed((long)p_185931_1_ * k + (long)p_185931_2_ * l ^ this.field_73163_a.func_72905_C());
      ChunkPos chunkpos = new ChunkPos(p_185931_1_, p_185931_2_);

      for(MapGenStructure mapgenstructure : this.field_82696_f.values()) {
         boolean flag1 = mapgenstructure.func_175794_a(this.field_73163_a, this.field_73161_b, chunkpos);
         if (mapgenstructure instanceof MapGenVillage) {
            flag |= flag1;
         }
      }

      if (this.field_82703_i != null && !flag && this.field_73161_b.nextInt(4) == 0) {
         this.field_82703_i.func_180709_b(this.field_73163_a, this.field_73161_b, blockpos.func_177982_a(this.field_73161_b.nextInt(16) + 8, this.field_73161_b.nextInt(256), this.field_73161_b.nextInt(16) + 8));
      }

      if (this.field_82701_j != null && !flag && this.field_73161_b.nextInt(8) == 0) {
         BlockPos blockpos1 = blockpos.func_177982_a(this.field_73161_b.nextInt(16) + 8, this.field_73161_b.nextInt(this.field_73161_b.nextInt(248) + 8), this.field_73161_b.nextInt(16) + 8);
         if (blockpos1.func_177956_o() < this.field_73163_a.func_181545_F() || this.field_73161_b.nextInt(10) == 0) {
            this.field_82701_j.func_180709_b(this.field_73163_a, this.field_73161_b, blockpos1);
         }
      }

      if (this.field_82702_h) {
         for(int i1 = 0; i1 < 8; ++i1) {
            (new WorldGenDungeons()).func_180709_b(this.field_73163_a, this.field_73161_b, blockpos.func_177982_a(this.field_73161_b.nextInt(16) + 8, this.field_73161_b.nextInt(256), this.field_73161_b.nextInt(16) + 8));
         }
      }

      if (this.field_82697_g) {
         biome.func_180624_a(this.field_73163_a, this.field_73161_b, blockpos);
      }

   }

   public boolean func_185933_a(Chunk p_185933_1_, int p_185933_2_, int p_185933_3_) {
      return false;
   }

   public List<Biome.SpawnListEntry> func_177458_a(EnumCreatureType p_177458_1_, BlockPos p_177458_2_) {
      Biome biome = this.field_73163_a.func_180494_b(p_177458_2_);
      return biome.func_76747_a(p_177458_1_);
   }

   @Nullable
   public BlockPos func_180513_a(World p_180513_1_, String p_180513_2_, BlockPos p_180513_3_, boolean p_180513_4_) {
      MapGenStructure mapgenstructure = this.field_82696_f.get(p_180513_2_);
      return mapgenstructure != null ? mapgenstructure.func_180706_b(p_180513_1_, p_180513_3_, p_180513_4_) : null;
   }

   public boolean func_193414_a(World p_193414_1_, String p_193414_2_, BlockPos p_193414_3_) {
      MapGenStructure mapgenstructure = this.field_82696_f.get(p_193414_2_);
      return mapgenstructure != null ? mapgenstructure.func_175795_b(p_193414_3_) : false;
   }

   public void func_180514_a(Chunk p_180514_1_, int p_180514_2_, int p_180514_3_) {
      for(MapGenStructure mapgenstructure : this.field_82696_f.values()) {
         mapgenstructure.func_186125_a(this.field_73163_a, p_180514_2_, p_180514_3_, (ChunkPrimer)null);
      }

   }
}
