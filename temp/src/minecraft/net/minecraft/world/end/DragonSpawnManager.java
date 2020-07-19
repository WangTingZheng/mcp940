package net.minecraft.world.end;

import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeEndDecorator;
import net.minecraft.world.gen.feature.WorldGenSpikes;

public enum DragonSpawnManager {
   START {
      public void func_186079_a(WorldServer p_186079_1_, DragonFightManager p_186079_2_, List<EntityEnderCrystal> p_186079_3_, int p_186079_4_, BlockPos p_186079_5_) {
         BlockPos blockpos = new BlockPos(0, 128, 0);

         for(EntityEnderCrystal entityendercrystal : p_186079_3_) {
            entityendercrystal.func_184516_a(blockpos);
         }

         p_186079_2_.func_186095_a(PREPARING_TO_SUMMON_PILLARS);
      }
   },
   PREPARING_TO_SUMMON_PILLARS {
      public void func_186079_a(WorldServer p_186079_1_, DragonFightManager p_186079_2_, List<EntityEnderCrystal> p_186079_3_, int p_186079_4_, BlockPos p_186079_5_) {
         if (p_186079_4_ < 100) {
            if (p_186079_4_ == 0 || p_186079_4_ == 50 || p_186079_4_ == 51 || p_186079_4_ == 52 || p_186079_4_ >= 95) {
               p_186079_1_.func_175718_b(3001, new BlockPos(0, 128, 0), 0);
            }
         } else {
            p_186079_2_.func_186095_a(SUMMONING_PILLARS);
         }

      }
   },
   SUMMONING_PILLARS {
      public void func_186079_a(WorldServer p_186079_1_, DragonFightManager p_186079_2_, List<EntityEnderCrystal> p_186079_3_, int p_186079_4_, BlockPos p_186079_5_) {
         int i = 40;
         boolean flag = p_186079_4_ % 40 == 0;
         boolean flag1 = p_186079_4_ % 40 == 39;
         if (flag || flag1) {
            WorldGenSpikes.EndSpike[] aworldgenspikes$endspike = BiomeEndDecorator.func_185426_a(p_186079_1_);
            int j = p_186079_4_ / 40;
            if (j < aworldgenspikes$endspike.length) {
               WorldGenSpikes.EndSpike worldgenspikes$endspike = aworldgenspikes$endspike[j];
               if (flag) {
                  for(EntityEnderCrystal entityendercrystal : p_186079_3_) {
                     entityendercrystal.func_184516_a(new BlockPos(worldgenspikes$endspike.func_186151_a(), worldgenspikes$endspike.func_186149_d() + 1, worldgenspikes$endspike.func_186152_b()));
                  }
               } else {
                  int k = 10;

                  for(BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.func_177975_b(new BlockPos(worldgenspikes$endspike.func_186151_a() - 10, worldgenspikes$endspike.func_186149_d() - 10, worldgenspikes$endspike.func_186152_b() - 10), new BlockPos(worldgenspikes$endspike.func_186151_a() + 10, worldgenspikes$endspike.func_186149_d() + 10, worldgenspikes$endspike.func_186152_b() + 10))) {
                     p_186079_1_.func_175698_g(blockpos$mutableblockpos);
                  }

                  p_186079_1_.func_72876_a((Entity)null, (double)((float)worldgenspikes$endspike.func_186151_a() + 0.5F), (double)worldgenspikes$endspike.func_186149_d(), (double)((float)worldgenspikes$endspike.func_186152_b() + 0.5F), 5.0F, true);
                  WorldGenSpikes worldgenspikes = new WorldGenSpikes();
                  worldgenspikes.func_186143_a(worldgenspikes$endspike);
                  worldgenspikes.func_186144_a(true);
                  worldgenspikes.func_186142_a(new BlockPos(0, 128, 0));
                  worldgenspikes.func_180709_b(p_186079_1_, new Random(), new BlockPos(worldgenspikes$endspike.func_186151_a(), 45, worldgenspikes$endspike.func_186152_b()));
               }
            } else if (flag) {
               p_186079_2_.func_186095_a(SUMMONING_DRAGON);
            }
         }

      }
   },
   SUMMONING_DRAGON {
      public void func_186079_a(WorldServer p_186079_1_, DragonFightManager p_186079_2_, List<EntityEnderCrystal> p_186079_3_, int p_186079_4_, BlockPos p_186079_5_) {
         if (p_186079_4_ >= 100) {
            p_186079_2_.func_186095_a(END);
            p_186079_2_.func_186087_f();

            for(EntityEnderCrystal entityendercrystal : p_186079_3_) {
               entityendercrystal.func_184516_a((BlockPos)null);
               p_186079_1_.func_72876_a(entityendercrystal, entityendercrystal.field_70165_t, entityendercrystal.field_70163_u, entityendercrystal.field_70161_v, 6.0F, false);
               entityendercrystal.func_70106_y();
            }
         } else if (p_186079_4_ >= 80) {
            p_186079_1_.func_175718_b(3001, new BlockPos(0, 128, 0), 0);
         } else if (p_186079_4_ == 0) {
            for(EntityEnderCrystal entityendercrystal1 : p_186079_3_) {
               entityendercrystal1.func_184516_a(new BlockPos(0, 128, 0));
            }
         } else if (p_186079_4_ < 5) {
            p_186079_1_.func_175718_b(3001, new BlockPos(0, 128, 0), 0);
         }

      }
   },
   END {
      public void func_186079_a(WorldServer p_186079_1_, DragonFightManager p_186079_2_, List<EntityEnderCrystal> p_186079_3_, int p_186079_4_, BlockPos p_186079_5_) {
      }
   };

   private DragonSpawnManager() {
   }

   public abstract void func_186079_a(WorldServer var1, DragonFightManager var2, List<EntityEnderCrystal> var3, int var4, BlockPos var5);
}
