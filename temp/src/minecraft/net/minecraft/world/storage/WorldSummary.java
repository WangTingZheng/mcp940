package net.minecraft.world.storage;

import net.minecraft.util.StringUtils;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.GameType;

public class WorldSummary implements Comparable<WorldSummary> {
   private final String field_75797_a;
   private final String field_75795_b;
   private final long field_75796_c;
   private final long field_75793_d;
   private final boolean field_75794_e;
   private final GameType field_75791_f;
   private final boolean field_75792_g;
   private final boolean field_75798_h;
   private final String field_186358_i;
   private final int field_186359_j;
   private final boolean field_186360_k;

   public WorldSummary(WorldInfo p_i46646_1_, String p_i46646_2_, String p_i46646_3_, long p_i46646_4_, boolean p_i46646_6_) {
      this.field_75797_a = p_i46646_2_;
      this.field_75795_b = p_i46646_3_;
      this.field_75796_c = p_i46646_1_.func_76057_l();
      this.field_75793_d = p_i46646_4_;
      this.field_75791_f = p_i46646_1_.func_76077_q();
      this.field_75794_e = p_i46646_6_;
      this.field_75792_g = p_i46646_1_.func_76093_s();
      this.field_75798_h = p_i46646_1_.func_76086_u();
      this.field_186358_i = p_i46646_1_.func_186346_M();
      this.field_186359_j = p_i46646_1_.func_186344_K();
      this.field_186360_k = p_i46646_1_.func_186343_L();
   }

   public String func_75786_a() {
      return this.field_75797_a;
   }

   public String func_75788_b() {
      return this.field_75795_b;
   }

   public long func_154336_c() {
      return this.field_75793_d;
   }

   public boolean func_75785_d() {
      return this.field_75794_e;
   }

   public long func_75784_e() {
      return this.field_75796_c;
   }

   public int compareTo(WorldSummary p_compareTo_1_) {
      if (this.field_75796_c < p_compareTo_1_.field_75796_c) {
         return 1;
      } else {
         return this.field_75796_c > p_compareTo_1_.field_75796_c ? -1 : this.field_75797_a.compareTo(p_compareTo_1_.field_75797_a);
      }
   }

   public GameType func_75790_f() {
      return this.field_75791_f;
   }

   public boolean func_75789_g() {
      return this.field_75792_g;
   }

   public boolean func_75783_h() {
      return this.field_75798_h;
   }

   public String func_186357_i() {
      return StringUtils.func_151246_b(this.field_186358_i) ? I18n.func_74838_a("selectWorld.versionUnknown") : this.field_186358_i;
   }

   public boolean func_186355_l() {
      return this.func_186356_m();
   }

   public boolean func_186356_m() {
      return this.field_186359_j > 1139;
   }
}
