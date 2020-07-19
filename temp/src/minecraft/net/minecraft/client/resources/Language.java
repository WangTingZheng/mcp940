package net.minecraft.client.resources;

public class Language implements Comparable<Language> {
   private final String field_135039_a;
   private final String field_135037_b;
   private final String field_135038_c;
   private final boolean field_135036_d;

   public Language(String p_i1303_1_, String p_i1303_2_, String p_i1303_3_, boolean p_i1303_4_) {
      this.field_135039_a = p_i1303_1_;
      this.field_135037_b = p_i1303_2_;
      this.field_135038_c = p_i1303_3_;
      this.field_135036_d = p_i1303_4_;
   }

   public String func_135034_a() {
      return this.field_135039_a;
   }

   public boolean func_135035_b() {
      return this.field_135036_d;
   }

   public String toString() {
      return String.format("%s (%s)", this.field_135038_c, this.field_135037_b);
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else {
         return !(p_equals_1_ instanceof Language) ? false : this.field_135039_a.equals(((Language)p_equals_1_).field_135039_a);
      }
   }

   public int hashCode() {
      return this.field_135039_a.hashCode();
   }

   public int compareTo(Language p_compareTo_1_) {
      return this.field_135039_a.compareTo(p_compareTo_1_.field_135039_a);
   }
}
