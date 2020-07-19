package net.minecraft.world.biome;

public class BiomeVoid extends Biome {
   public BiomeVoid(Biome.BiomeProperties p_i46692_1_) {
      super(p_i46692_1_);
      this.field_76761_J.clear();
      this.field_76762_K.clear();
      this.field_76755_L.clear();
      this.field_82914_M.clear();
      this.field_76760_I = new BiomeVoidDecorator();
   }

   public boolean func_185352_i() {
      return true;
   }
}
