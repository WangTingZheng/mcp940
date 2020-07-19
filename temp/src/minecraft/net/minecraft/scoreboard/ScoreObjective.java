package net.minecraft.scoreboard;

public class ScoreObjective {
   private final Scoreboard field_96686_a;
   private final String field_96684_b;
   private final IScoreCriteria field_96685_c;
   private IScoreCriteria.EnumRenderType field_178768_d;
   private String field_96683_d;

   public ScoreObjective(Scoreboard p_i2307_1_, String p_i2307_2_, IScoreCriteria p_i2307_3_) {
      this.field_96686_a = p_i2307_1_;
      this.field_96684_b = p_i2307_2_;
      this.field_96685_c = p_i2307_3_;
      this.field_96683_d = p_i2307_2_;
      this.field_178768_d = p_i2307_3_.func_178790_c();
   }

   public Scoreboard func_96682_a() {
      return this.field_96686_a;
   }

   public String func_96679_b() {
      return this.field_96684_b;
   }

   public IScoreCriteria func_96680_c() {
      return this.field_96685_c;
   }

   public String func_96678_d() {
      return this.field_96683_d;
   }

   public void func_96681_a(String p_96681_1_) {
      this.field_96683_d = p_96681_1_;
      this.field_96686_a.func_96532_b(this);
   }

   public IScoreCriteria.EnumRenderType func_178766_e() {
      return this.field_178768_d;
   }

   public void func_178767_a(IScoreCriteria.EnumRenderType p_178767_1_) {
      this.field_178768_d = p_178767_1_;
      this.field_96686_a.func_96532_b(this);
   }
}
