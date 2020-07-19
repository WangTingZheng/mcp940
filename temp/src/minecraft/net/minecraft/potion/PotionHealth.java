package net.minecraft.potion;

public class PotionHealth extends Potion {
   public PotionHealth(boolean p_i46816_1_, int p_i46816_2_) {
      super(p_i46816_1_, p_i46816_2_);
   }

   public boolean func_76403_b() {
      return true;
   }

   public boolean func_76397_a(int p_76397_1_, int p_76397_2_) {
      return p_76397_1_ >= 1;
   }
}
