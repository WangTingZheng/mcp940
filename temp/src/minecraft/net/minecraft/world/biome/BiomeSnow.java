package net.minecraft.world.biome;

import java.util.Iterator;
import java.util.Random;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenIcePath;
import net.minecraft.world.gen.feature.WorldGenIceSpike;
import net.minecraft.world.gen.feature.WorldGenTaiga2;

public class BiomeSnow extends Biome {
   private final boolean field_150615_aC;
   private final WorldGenIceSpike field_150616_aD = new WorldGenIceSpike();
   private final WorldGenIcePath field_150617_aE = new WorldGenIcePath(4);

   public BiomeSnow(boolean p_i46706_1_, Biome.BiomeProperties p_i46706_2_) {
      super(p_i46706_2_);
      this.field_150615_aC = p_i46706_1_;
      if (p_i46706_1_) {
         this.field_76752_A = Blocks.field_150433_aE.func_176223_P();
      }

      this.field_76762_K.clear();
      this.field_76762_K.add(new Biome.SpawnListEntry(EntityRabbit.class, 10, 2, 3));
      this.field_76762_K.add(new Biome.SpawnListEntry(EntityPolarBear.class, 1, 1, 2));
      Iterator<Biome.SpawnListEntry> iterator = this.field_76761_J.iterator();

      while(iterator.hasNext()) {
         Biome.SpawnListEntry biome$spawnlistentry = iterator.next();
         if (biome$spawnlistentry.field_76300_b == EntitySkeleton.class) {
            iterator.remove();
         }
      }

      this.field_76761_J.add(new Biome.SpawnListEntry(EntitySkeleton.class, 20, 4, 4));
      this.field_76761_J.add(new Biome.SpawnListEntry(EntityStray.class, 80, 4, 4));
   }

   public float func_76741_f() {
      return 0.07F;
   }

   public void func_180624_a(World p_180624_1_, Random p_180624_2_, BlockPos p_180624_3_) {
      if (this.field_150615_aC) {
         for(int i = 0; i < 3; ++i) {
            int j = p_180624_2_.nextInt(16) + 8;
            int k = p_180624_2_.nextInt(16) + 8;
            this.field_150616_aD.func_180709_b(p_180624_1_, p_180624_2_, p_180624_1_.func_175645_m(p_180624_3_.func_177982_a(j, 0, k)));
         }

         for(int l = 0; l < 2; ++l) {
            int i1 = p_180624_2_.nextInt(16) + 8;
            int j1 = p_180624_2_.nextInt(16) + 8;
            this.field_150617_aE.func_180709_b(p_180624_1_, p_180624_2_, p_180624_1_.func_175645_m(p_180624_3_.func_177982_a(i1, 0, j1)));
         }
      }

      super.func_180624_a(p_180624_1_, p_180624_2_, p_180624_3_);
   }

   public WorldGenAbstractTree func_150567_a(Random p_150567_1_) {
      return new WorldGenTaiga2(false);
   }
}
