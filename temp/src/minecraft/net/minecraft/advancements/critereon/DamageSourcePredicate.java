package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.JsonUtils;

public class DamageSourcePredicate {
   public static DamageSourcePredicate field_192449_a = new DamageSourcePredicate();
   private final Boolean field_192450_b;
   private final Boolean field_192451_c;
   private final Boolean field_192452_d;
   private final Boolean field_192453_e;
   private final Boolean field_192454_f;
   private final Boolean field_192455_g;
   private final Boolean field_192456_h;
   private final EntityPredicate field_193419_i;
   private final EntityPredicate field_193420_j;

   public DamageSourcePredicate() {
      this.field_192450_b = null;
      this.field_192451_c = null;
      this.field_192452_d = null;
      this.field_192453_e = null;
      this.field_192454_f = null;
      this.field_192455_g = null;
      this.field_192456_h = null;
      this.field_193419_i = EntityPredicate.field_192483_a;
      this.field_193420_j = EntityPredicate.field_192483_a;
   }

   public DamageSourcePredicate(@Nullable Boolean p_i47543_1_, @Nullable Boolean p_i47543_2_, @Nullable Boolean p_i47543_3_, @Nullable Boolean p_i47543_4_, @Nullable Boolean p_i47543_5_, @Nullable Boolean p_i47543_6_, @Nullable Boolean p_i47543_7_, EntityPredicate p_i47543_8_, EntityPredicate p_i47543_9_) {
      this.field_192450_b = p_i47543_1_;
      this.field_192451_c = p_i47543_2_;
      this.field_192452_d = p_i47543_3_;
      this.field_192453_e = p_i47543_4_;
      this.field_192454_f = p_i47543_5_;
      this.field_192455_g = p_i47543_6_;
      this.field_192456_h = p_i47543_7_;
      this.field_193419_i = p_i47543_8_;
      this.field_193420_j = p_i47543_9_;
   }

   public boolean func_193418_a(EntityPlayerMP p_193418_1_, DamageSource p_193418_2_) {
      if (this == field_192449_a) {
         return true;
      } else if (this.field_192450_b != null && this.field_192450_b.booleanValue() != p_193418_2_.func_76352_a()) {
         return false;
      } else if (this.field_192451_c != null && this.field_192451_c.booleanValue() != p_193418_2_.func_94541_c()) {
         return false;
      } else if (this.field_192452_d != null && this.field_192452_d.booleanValue() != p_193418_2_.func_76363_c()) {
         return false;
      } else if (this.field_192453_e != null && this.field_192453_e.booleanValue() != p_193418_2_.func_76357_e()) {
         return false;
      } else if (this.field_192454_f != null && this.field_192454_f.booleanValue() != p_193418_2_.func_151517_h()) {
         return false;
      } else if (this.field_192455_g != null && this.field_192455_g.booleanValue() != p_193418_2_.func_76347_k()) {
         return false;
      } else if (this.field_192456_h != null && this.field_192456_h.booleanValue() != p_193418_2_.func_82725_o()) {
         return false;
      } else if (!this.field_193419_i.func_192482_a(p_193418_1_, p_193418_2_.func_76364_f())) {
         return false;
      } else {
         return this.field_193420_j.func_192482_a(p_193418_1_, p_193418_2_.func_76346_g());
      }
   }

   public static DamageSourcePredicate func_192447_a(@Nullable JsonElement p_192447_0_) {
      if (p_192447_0_ != null && !p_192447_0_.isJsonNull()) {
         JsonObject jsonobject = JsonUtils.func_151210_l(p_192447_0_, "damage type");
         Boolean obool = func_192448_a(jsonobject, "is_projectile");
         Boolean obool1 = func_192448_a(jsonobject, "is_explosion");
         Boolean obool2 = func_192448_a(jsonobject, "bypasses_armor");
         Boolean obool3 = func_192448_a(jsonobject, "bypasses_invulnerability");
         Boolean obool4 = func_192448_a(jsonobject, "bypasses_magic");
         Boolean obool5 = func_192448_a(jsonobject, "is_fire");
         Boolean obool6 = func_192448_a(jsonobject, "is_magic");
         EntityPredicate entitypredicate = EntityPredicate.func_192481_a(jsonobject.get("direct_entity"));
         EntityPredicate entitypredicate1 = EntityPredicate.func_192481_a(jsonobject.get("source_entity"));
         return new DamageSourcePredicate(obool, obool1, obool2, obool3, obool4, obool5, obool6, entitypredicate, entitypredicate1);
      } else {
         return field_192449_a;
      }
   }

   @Nullable
   private static Boolean func_192448_a(JsonObject p_192448_0_, String p_192448_1_) {
      return p_192448_0_.has(p_192448_1_) ? JsonUtils.func_151212_i(p_192448_0_, p_192448_1_) : null;
   }
}
