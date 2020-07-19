package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;

public class BiomeSavanna extends Biome {
   private static final WorldGenSavannaTree field_150627_aC = new WorldGenSavannaTree(false);

   protected BiomeSavanna(Biome.BiomeProperties p_i46697_1_) {
      super(p_i46697_1_);
      this.field_76762_K.add(new Biome.SpawnListEntry(EntityHorse.class, 1, 2, 6));
      this.field_76762_K.add(new Biome.SpawnListEntry(EntityDonkey.class, 1, 1, 1));
      if (this.func_185355_j() > 1.1F) {
         this.field_76762_K.add(new Biome.SpawnListEntry(EntityLlama.class, 8, 4, 4));
      }

      this.field_76760_I.field_76832_z = 1;
      this.field_76760_I.field_76802_A = 4;
      this.field_76760_I.field_76803_B = 20;
   }

   public WorldGenAbstractTree func_150567_a(Random p_150567_1_) {
      return (WorldGenAbstractTree)(p_150567_1_.nextInt(5) > 0 ? field_150627_aC : field_76757_N);
   }

   public void func_180624_a(World p_180624_1_, Random p_180624_2_, BlockPos p_180624_3_) {
      field_180280_ag.func_180710_a(BlockDoublePlant.EnumPlantType.GRASS);

      for(int i = 0; i < 7; ++i) {
         int j = p_180624_2_.nextInt(16) + 8;
         int k = p_180624_2_.nextInt(16) + 8;
         int l = p_180624_2_.nextInt(p_180624_1_.func_175645_m(p_180624_3_.func_177982_a(j, 0, k)).func_177956_o() + 32);
         field_180280_ag.func_180709_b(p_180624_1_, p_180624_2_, p_180624_3_.func_177982_a(j, l, k));
      }

      super.func_180624_a(p_180624_1_, p_180624_2_, p_180624_3_);
   }

   public Class<? extends Biome> func_150562_l() {
      return BiomeSavanna.class;
   }
}
