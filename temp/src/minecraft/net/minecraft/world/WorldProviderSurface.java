package net.minecraft.world;

public class WorldProviderSurface extends WorldProvider {
   public DimensionType func_186058_p() {
      return DimensionType.OVERWORLD;
   }

   public boolean func_186056_c(int p_186056_1_, int p_186056_2_) {
      return !this.field_76579_a.func_72916_c(p_186056_1_, p_186056_2_);
   }
}
