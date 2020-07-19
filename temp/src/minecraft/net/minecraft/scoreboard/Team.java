package net.minecraft.scoreboard;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.util.text.TextFormatting;

public abstract class Team {
   public boolean func_142054_a(@Nullable Team p_142054_1_) {
      if (p_142054_1_ == null) {
         return false;
      } else {
         return this == p_142054_1_;
      }
   }

   public abstract String func_96661_b();

   public abstract String func_142053_d(String var1);

   public abstract boolean func_98297_h();

   public abstract boolean func_96665_g();

   public abstract Team.EnumVisible func_178770_i();

   public abstract TextFormatting func_178775_l();

   public abstract Collection<String> func_96670_d();

   public abstract Team.EnumVisible func_178771_j();

   public abstract Team.CollisionRule func_186681_k();

   public static enum CollisionRule {
      ALWAYS("always", 0),
      NEVER("never", 1),
      HIDE_FOR_OTHER_TEAMS("pushOtherTeams", 2),
      HIDE_FOR_OWN_TEAM("pushOwnTeam", 3);

      private static final Map<String, Team.CollisionRule> field_186695_g = Maps.<String, Team.CollisionRule>newHashMap();
      public final String field_186693_e;
      public final int field_186694_f;

      public static String[] func_186687_a() {
         return (String[])field_186695_g.keySet().toArray(new String[field_186695_g.size()]);
      }

      @Nullable
      public static Team.CollisionRule func_186686_a(String p_186686_0_) {
         return field_186695_g.get(p_186686_0_);
      }

      private CollisionRule(String p_i47053_3_, int p_i47053_4_) {
         this.field_186693_e = p_i47053_3_;
         this.field_186694_f = p_i47053_4_;
      }

      static {
         for(Team.CollisionRule team$collisionrule : values()) {
            field_186695_g.put(team$collisionrule.field_186693_e, team$collisionrule);
         }

      }
   }

   public static enum EnumVisible {
      ALWAYS("always", 0),
      NEVER("never", 1),
      HIDE_FOR_OTHER_TEAMS("hideForOtherTeams", 2),
      HIDE_FOR_OWN_TEAM("hideForOwnTeam", 3);

      private static final Map<String, Team.EnumVisible> field_186697_g = Maps.<String, Team.EnumVisible>newHashMap();
      public final String field_178830_e;
      public final int field_178827_f;

      public static String[] func_178825_a() {
         return (String[])field_186697_g.keySet().toArray(new String[field_186697_g.size()]);
      }

      @Nullable
      public static Team.EnumVisible func_178824_a(String p_178824_0_) {
         return field_186697_g.get(p_178824_0_);
      }

      private EnumVisible(String p_i45550_3_, int p_i45550_4_) {
         this.field_178830_e = p_i45550_3_;
         this.field_178827_f = p_i45550_4_;
      }

      static {
         for(Team.EnumVisible team$enumvisible : values()) {
            field_186697_g.put(team$enumvisible.field_178830_e, team$enumvisible);
         }

      }
   }
}
