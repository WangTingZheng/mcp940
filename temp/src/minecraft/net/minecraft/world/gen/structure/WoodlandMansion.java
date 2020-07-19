package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorOverworld;

public class WoodlandMansion extends MapGenStructure {
   private final int field_191073_b = 80;
   private final int field_191074_d = 20;
   public static final List<Biome> field_191072_a = Arrays.<Biome>asList(Biomes.field_150585_R, Biomes.field_185430_ab);
   private final ChunkGeneratorOverworld field_191075_h;

   public WoodlandMansion(ChunkGeneratorOverworld p_i47240_1_) {
      this.field_191075_h = p_i47240_1_;
   }

   public String func_143025_a() {
      return "Mansion";
   }

   protected boolean func_75047_a(int p_75047_1_, int p_75047_2_) {
      int i = p_75047_1_;
      int j = p_75047_2_;
      if (p_75047_1_ < 0) {
         i = p_75047_1_ - 79;
      }

      if (p_75047_2_ < 0) {
         j = p_75047_2_ - 79;
      }

      int k = i / 80;
      int l = j / 80;
      Random random = this.field_75039_c.func_72843_D(k, l, 10387319);
      k = k * 80;
      l = l * 80;
      k = k + (random.nextInt(60) + random.nextInt(60)) / 2;
      l = l + (random.nextInt(60) + random.nextInt(60)) / 2;
      if (p_75047_1_ == k && p_75047_2_ == l) {
         boolean flag = this.field_75039_c.func_72959_q().func_76940_a(p_75047_1_ * 16 + 8, p_75047_2_ * 16 + 8, 32, field_191072_a);
         if (flag) {
            return true;
         }
      }

      return false;
   }

   public BlockPos func_180706_b(World p_180706_1_, BlockPos p_180706_2_, boolean p_180706_3_) {
      this.field_75039_c = p_180706_1_;
      BiomeProvider biomeprovider = p_180706_1_.func_72959_q();
      return biomeprovider.func_190944_c() && biomeprovider.func_190943_d() != Biomes.field_150585_R ? null : func_191069_a(p_180706_1_, this, p_180706_2_, 80, 20, 10387319, true, 100, p_180706_3_);
   }

   protected StructureStart func_75049_b(int p_75049_1_, int p_75049_2_) {
      return new WoodlandMansion.Start(this.field_75039_c, this.field_191075_h, this.field_75038_b, p_75049_1_, p_75049_2_);
   }

   public static class Start extends StructureStart {
      private boolean field_191093_c;

      public Start() {
      }

      public Start(World p_i47235_1_, ChunkGeneratorOverworld p_i47235_2_, Random p_i47235_3_, int p_i47235_4_, int p_i47235_5_) {
         super(p_i47235_4_, p_i47235_5_);
         this.func_191092_a(p_i47235_1_, p_i47235_2_, p_i47235_3_, p_i47235_4_, p_i47235_5_);
      }

      private void func_191092_a(World p_191092_1_, ChunkGeneratorOverworld p_191092_2_, Random p_191092_3_, int p_191092_4_, int p_191092_5_) {
         Rotation rotation = Rotation.values()[p_191092_3_.nextInt(Rotation.values().length)];
         ChunkPrimer chunkprimer = new ChunkPrimer();
         p_191092_2_.func_185976_a(p_191092_4_, p_191092_5_, chunkprimer);
         int i = 5;
         int j = 5;
         if (rotation == Rotation.CLOCKWISE_90) {
            i = -5;
         } else if (rotation == Rotation.CLOCKWISE_180) {
            i = -5;
            j = -5;
         } else if (rotation == Rotation.COUNTERCLOCKWISE_90) {
            j = -5;
         }

         int k = chunkprimer.func_186138_a(7, 7);
         int l = chunkprimer.func_186138_a(7, 7 + j);
         int i1 = chunkprimer.func_186138_a(7 + i, 7);
         int j1 = chunkprimer.func_186138_a(7 + i, 7 + j);
         int k1 = Math.min(Math.min(k, l), Math.min(i1, j1));
         if (k1 < 60) {
            this.field_191093_c = false;
         } else {
            BlockPos blockpos = new BlockPos(p_191092_4_ * 16 + 8, k1 + 1, p_191092_5_ * 16 + 8);
            List<WoodlandMansionPieces.MansionTemplate> list = Lists.<WoodlandMansionPieces.MansionTemplate>newLinkedList();
            WoodlandMansionPieces.func_191152_a(p_191092_1_.func_72860_G().func_186340_h(), blockpos, rotation, list, p_191092_3_);
            this.field_75075_a.addAll(list);
            this.func_75072_c();
            this.field_191093_c = true;
         }
      }

      public void func_75068_a(World p_75068_1_, Random p_75068_2_, StructureBoundingBox p_75068_3_) {
         super.func_75068_a(p_75068_1_, p_75068_2_, p_75068_3_);
         int i = this.field_75074_b.field_78895_b;

         for(int j = p_75068_3_.field_78897_a; j <= p_75068_3_.field_78893_d; ++j) {
            for(int k = p_75068_3_.field_78896_c; k <= p_75068_3_.field_78892_f; ++k) {
               BlockPos blockpos = new BlockPos(j, i, k);
               if (!p_75068_1_.func_175623_d(blockpos) && this.field_75074_b.func_175898_b(blockpos)) {
                  boolean flag = false;

                  for(StructureComponent structurecomponent : this.field_75075_a) {
                     if (structurecomponent.field_74887_e.func_175898_b(blockpos)) {
                        flag = true;
                        break;
                     }
                  }

                  if (flag) {
                     for(int l = i - 1; l > 1; --l) {
                        BlockPos blockpos1 = new BlockPos(j, l, k);
                        if (!p_75068_1_.func_175623_d(blockpos1) && !p_75068_1_.func_180495_p(blockpos1).func_185904_a().func_76224_d()) {
                           break;
                        }

                        p_75068_1_.func_180501_a(blockpos1, Blocks.field_150347_e.func_176223_P(), 2);
                     }
                  }
               }
            }
         }

      }

      public boolean func_75069_d() {
         return this.field_191093_c;
      }
   }
}
