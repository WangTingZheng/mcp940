package net.minecraft.dispenser;

public class PositionImpl implements IPosition {
   protected final double field_82630_a;
   protected final double field_82628_b;
   protected final double field_82629_c;

   public PositionImpl(double p_i1368_1_, double p_i1368_3_, double p_i1368_5_) {
      this.field_82630_a = p_i1368_1_;
      this.field_82628_b = p_i1368_3_;
      this.field_82629_c = p_i1368_5_;
   }

   public double func_82615_a() {
      return this.field_82630_a;
   }

   public double func_82617_b() {
      return this.field_82628_b;
   }

   public double func_82616_c() {
      return this.field_82629_c;
   }
}
