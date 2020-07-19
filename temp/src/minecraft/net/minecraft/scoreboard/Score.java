package net.minecraft.scoreboard;

import java.util.Comparator;

public class Score {
   public static final Comparator<Score> field_96658_a = new Comparator<Score>() {
      public int compare(Score p_compare_1_, Score p_compare_2_) {
         if (p_compare_1_.func_96652_c() > p_compare_2_.func_96652_c()) {
            return 1;
         } else {
            return p_compare_1_.func_96652_c() < p_compare_2_.func_96652_c() ? -1 : p_compare_2_.func_96653_e().compareToIgnoreCase(p_compare_1_.func_96653_e());
         }
      }
   };
   private final Scoreboard field_96656_b;
   private final ScoreObjective field_96657_c;
   private final String field_96654_d;
   private int field_96655_e;
   private boolean field_178817_f;
   private boolean field_178818_g;

   public Score(Scoreboard p_i2309_1_, ScoreObjective p_i2309_2_, String p_i2309_3_) {
      this.field_96656_b = p_i2309_1_;
      this.field_96657_c = p_i2309_2_;
      this.field_96654_d = p_i2309_3_;
      this.field_178818_g = true;
   }

   public void func_96649_a(int p_96649_1_) {
      if (this.field_96657_c.func_96680_c().func_96637_b()) {
         throw new IllegalStateException("Cannot modify read-only score");
      } else {
         this.func_96647_c(this.func_96652_c() + p_96649_1_);
      }
   }

   public void func_96646_b(int p_96646_1_) {
      if (this.field_96657_c.func_96680_c().func_96637_b()) {
         throw new IllegalStateException("Cannot modify read-only score");
      } else {
         this.func_96647_c(this.func_96652_c() - p_96646_1_);
      }
   }

   public void func_96648_a() {
      if (this.field_96657_c.func_96680_c().func_96637_b()) {
         throw new IllegalStateException("Cannot modify read-only score");
      } else {
         this.func_96649_a(1);
      }
   }

   public int func_96652_c() {
      return this.field_96655_e;
   }

   public void func_96647_c(int p_96647_1_) {
      int i = this.field_96655_e;
      this.field_96655_e = p_96647_1_;
      if (i != p_96647_1_ || this.field_178818_g) {
         this.field_178818_g = false;
         this.func_96650_f().func_96536_a(this);
      }

   }

   public ScoreObjective func_96645_d() {
      return this.field_96657_c;
   }

   public String func_96653_e() {
      return this.field_96654_d;
   }

   public Scoreboard func_96650_f() {
      return this.field_96656_b;
   }

   public boolean func_178816_g() {
      return this.field_178817_f;
   }

   public void func_178815_a(boolean p_178815_1_) {
      this.field_178817_f = p_178815_1_;
   }
}
