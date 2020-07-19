package net.minecraft.world.biome;

import java.util.Iterator;
import java.util.Random;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenDesertWells;
import net.minecraft.world.gen.feature.WorldGenFossils;

public class BiomeDesert extends Biome {
   public BiomeDesert(Biome.BiomeProperties p_i46711_1_) {
      super(p_i46711_1_);
      this.field_76762_K.clear();
      this.field_76752_A = Blocks.field_150354_m.func_176223_P();
      this.field_76753_B = Blocks.field_150354_m.func_176223_P();
      this.field_76760_I.field_76832_z = -999;
      this.field_76760_I.field_76804_C = 2;
      this.field_76760_I.field_76799_E = 50;
      this.field_76760_I.field_76800_F = 10;
      this.field_76762_K.clear();
      this.field_76762_K.add(new Biome.SpawnListEntry(EntityRabbit.class, 4, 2, 3));
      Iterator<Biome.SpawnListEntry> iterator = this.field_76761_J.iterator();

      while(iterator.hasNext()) {
         Biome.SpawnListEntry biome$spawnlistentry = iterator.next();
         if (biome$spawnlistentry.field_76300_b == EntityZombie.class || biome$spawnlistentry.field_76300_b == EntityZombieVillager.class) {
            iterator.remove();
         }
      }

      this.field_76761_J.add(new Biome.SpawnListEntry(EntityZombie.class, 19, 4, 4));
      this.field_76761_J.add(new Biome.SpawnListEntry(EntityZombieVillager.class, 1, 1, 1));
      this.field_76761_J.add(new Biome.SpawnListEntry(EntityHusk.class, 80, 4, 4));
   }

   public void func_180624_a(World p_180624_1_, Random p_180624_2_, BlockPos p_180624_3_) {
      super.func_180624_a(p_180624_1_, p_180624_2_, p_180624_3_);
      if (p_180624_2_.nextInt(1000) == 0) {
         int i = p_180624_2_.nextInt(16) + 8;
         int j = p_180624_2_.nextInt(16) + 8;
         BlockPos blockpos = p_180624_1_.func_175645_m(p_180624_3_.func_177982_a(i, 0, j)).func_177984_a();
         (new WorldGenDesertWells()).func_180709_b(p_180624_1_, p_180624_2_, blockpos);
      }

      if (p_180624_2_.nextInt(64) == 0) {
         (new WorldGenFossils()).func_180709_b(p_180624_1_, p_180624_2_, p_180624_3_);
      }

   }
}
