package net.minecraft.scoreboard;

public class ScoreCriteria implements IScoreCriteria {
   private final String field_96644_g;

   public ScoreCriteria(String p_i2311_1_) {
      this.field_96644_g = p_i2311_1_;
      IScoreCriteria.field_96643_a.put(p_i2311_1_, this);
   }

   public String func_96636_a() {
      return this.field_96644_g;
   }

   public boolean func_96637_b() {
      return false;
   }

   public IScoreCriteria.EnumRenderType func_178790_c() {
      return IScoreCriteria.EnumRenderType.INTEGER;
   }
}
