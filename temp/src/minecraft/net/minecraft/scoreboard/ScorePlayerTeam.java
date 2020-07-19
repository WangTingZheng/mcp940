package net.minecraft.scoreboard;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.text.TextFormatting;

public class ScorePlayerTeam extends Team {
   private final Scoreboard field_96677_a;
   private final String field_96675_b;
   private final Set<String> field_96676_c = Sets.<String>newHashSet();
   private String field_96673_d;
   private String field_96674_e = "";
   private String field_96671_f = "";
   private boolean field_96672_g = true;
   private boolean field_98301_h = true;
   private Team.EnumVisible field_178778_i = Team.EnumVisible.ALWAYS;
   private Team.EnumVisible field_178776_j = Team.EnumVisible.ALWAYS;
   private TextFormatting field_178777_k = TextFormatting.RESET;
   private Team.CollisionRule field_186683_l = Team.CollisionRule.ALWAYS;

   public ScorePlayerTeam(Scoreboard p_i2308_1_, String p_i2308_2_) {
      this.field_96677_a = p_i2308_1_;
      this.field_96675_b = p_i2308_2_;
      this.field_96673_d = p_i2308_2_;
   }

   public String func_96661_b() {
      return this.field_96675_b;
   }

   public String func_96669_c() {
      return this.field_96673_d;
   }

   public void func_96664_a(String p_96664_1_) {
      if (p_96664_1_ == null) {
         throw new IllegalArgumentException("Name cannot be null");
      } else {
         this.field_96673_d = p_96664_1_;
         this.field_96677_a.func_96538_b(this);
      }
   }

   public Collection<String> func_96670_d() {
      return this.field_96676_c;
   }

   public String func_96668_e() {
      return this.field_96674_e;
   }

   public void func_96666_b(String p_96666_1_) {
      if (p_96666_1_ == null) {
         throw new IllegalArgumentException("Prefix cannot be null");
      } else {
         this.field_96674_e = p_96666_1_;
         this.field_96677_a.func_96538_b(this);
      }
   }

   public String func_96663_f() {
      return this.field_96671_f;
   }

   public void func_96662_c(String p_96662_1_) {
      this.field_96671_f = p_96662_1_;
      this.field_96677_a.func_96538_b(this);
   }

   public String func_142053_d(String p_142053_1_) {
      return this.func_96668_e() + p_142053_1_ + this.func_96663_f();
   }

   public static String func_96667_a(@Nullable Team p_96667_0_, String p_96667_1_) {
      return p_96667_0_ == null ? p_96667_1_ : p_96667_0_.func_142053_d(p_96667_1_);
   }

   public boolean func_96665_g() {
      return this.field_96672_g;
   }

   public void func_96660_a(boolean p_96660_1_) {
      this.field_96672_g = p_96660_1_;
      this.field_96677_a.func_96538_b(this);
   }

   public boolean func_98297_h() {
      return this.field_98301_h;
   }

   public void func_98300_b(boolean p_98300_1_) {
      this.field_98301_h = p_98300_1_;
      this.field_96677_a.func_96538_b(this);
   }

   public Team.EnumVisible func_178770_i() {
      return this.field_178778_i;
   }

   public Team.EnumVisible func_178771_j() {
      return this.field_178776_j;
   }

   public void func_178772_a(Team.EnumVisible p_178772_1_) {
      this.field_178778_i = p_178772_1_;
      this.field_96677_a.func_96538_b(this);
   }

   public void func_178773_b(Team.EnumVisible p_178773_1_) {
      this.field_178776_j = p_178773_1_;
      this.field_96677_a.func_96538_b(this);
   }

   public Team.CollisionRule func_186681_k() {
      return this.field_186683_l;
   }

   public void func_186682_a(Team.CollisionRule p_186682_1_) {
      this.field_186683_l = p_186682_1_;
      this.field_96677_a.func_96538_b(this);
   }

   public int func_98299_i() {
      int i = 0;
      if (this.func_96665_g()) {
         i |= 1;
      }

      if (this.func_98297_h()) {
         i |= 2;
      }

      return i;
   }

   public void func_98298_a(int p_98298_1_) {
      this.func_96660_a((p_98298_1_ & 1) > 0);
      this.func_98300_b((p_98298_1_ & 2) > 0);
   }

   public void func_178774_a(TextFormatting p_178774_1_) {
      this.field_178777_k = p_178774_1_;
   }

   public TextFormatting func_178775_l() {
      return this.field_178777_k;
   }
}
