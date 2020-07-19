package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeForestMutated extends BiomeForest {
   public BiomeForestMutated(Biome.BiomeProperties p_i46702_1_) {
      super(BiomeForest.Type.BIRCH, p_i46702_1_);
   }

   public WorldGenAbstractTree func_150567_a(Random p_150567_1_) {
      return p_150567_1_.nextBoolean() ? BiomeForest.field_150629_aC : BiomeForest.field_150630_aD;
   }
}
