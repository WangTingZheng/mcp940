package net.minecraft.scoreboard;

import net.minecraft.util.text.TextFormatting;

public class ScoreCriteriaColored implements IScoreCriteria {
   private final String field_178794_j;

   public ScoreCriteriaColored(String p_i45549_1_, TextFormatting p_i45549_2_) {
      this.field_178794_j = p_i45549_1_ + p_i45549_2_.func_96297_d();
      IScoreCriteria.field_96643_a.put(this.field_178794_j, this);
   }

   public String func_96636_a() {
      return this.field_178794_j;
   }

   public boolean func_96637_b() {
      return false;
   }

   public IScoreCriteria.EnumRenderType func_178790_c() {
      return IScoreCriteria.EnumRenderType.INTEGER;
   }
}
