package net.minecraft.client.resources.data;

public class AnimationFrame {
   private final int field_110499_a;
   private final int field_110498_b;

   public AnimationFrame(int p_i1307_1_) {
      this(p_i1307_1_, -1);
   }

   public AnimationFrame(int p_i1308_1_, int p_i1308_2_) {
      this.field_110499_a = p_i1308_1_;
      this.field_110498_b = p_i1308_2_;
   }

   public boolean func_110495_a() {
      return this.field_110498_b == -1;
   }

   public int func_110497_b() {
      return this.field_110498_b;
   }

   public int func_110496_c() {
      return this.field_110499_a;
   }
}
