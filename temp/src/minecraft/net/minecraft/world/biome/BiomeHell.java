package net.minecraft.world.biome;

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;

public class BiomeHell extends Biome {
   public BiomeHell(Biome.BiomeProperties p_i46707_1_) {
      super(p_i46707_1_);
      this.field_76761_J.clear();
      this.field_76762_K.clear();
      this.field_76755_L.clear();
      this.field_82914_M.clear();
      this.field_76761_J.add(new Biome.SpawnListEntry(EntityGhast.class, 50, 4, 4));
      this.field_76761_J.add(new Biome.SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
      this.field_76761_J.add(new Biome.SpawnListEntry(EntityMagmaCube.class, 2, 4, 4));
      this.field_76761_J.add(new Biome.SpawnListEntry(EntityEnderman.class, 1, 4, 4));
      this.field_76760_I = new BiomeHellDecorator();
   }
}
