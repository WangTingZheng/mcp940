package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.JsonUtils;

public class DamagePredicate {
   public static DamagePredicate field_192366_a = new DamagePredicate();
   private final MinMaxBounds field_192367_b;
   private final MinMaxBounds field_192368_c;
   private final EntityPredicate field_192369_d;
   private final Boolean field_192370_e;
   private final DamageSourcePredicate field_192371_f;

   public DamagePredicate() {
      this.field_192367_b = MinMaxBounds.field_192516_a;
      this.field_192368_c = MinMaxBounds.field_192516_a;
      this.field_192369_d = EntityPredicate.field_192483_a;
      this.field_192370_e = null;
      this.field_192371_f = DamageSourcePredicate.field_192449_a;
   }

   public DamagePredicate(MinMaxBounds p_i47464_1_, MinMaxBounds p_i47464_2_, EntityPredicate p_i47464_3_, @Nullable Boolean p_i47464_4_, DamageSourcePredicate p_i47464_5_) {
      this.field_192367_b = p_i47464_1_;
      this.field_192368_c = p_i47464_2_;
      this.field_192369_d = p_i47464_3_;
      this.field_192370_e = p_i47464_4_;
      this.field_192371_f = p_i47464_5_;
   }

   public boolean func_192365_a(EntityPlayerMP p_192365_1_, DamageSource p_192365_2_, float p_192365_3_, float p_192365_4_, boolean p_192365_5_) {
      if (this == field_192366_a) {
         return true;
      } else if (!this.field_192367_b.func_192514_a(p_192365_3_)) {
         return false;
      } else if (!this.field_192368_c.func_192514_a(p_192365_4_)) {
         return false;
      } else if (!this.field_192369_d.func_192482_a(p_192365_1_, p_192365_2_.func_76346_g())) {
         return false;
      } else if (this.field_192370_e != null && this.field_192370_e.booleanValue() != p_192365_5_) {
         return false;
      } else {
         return this.field_192371_f.func_193418_a(p_192365_1_, p_192365_2_);
      }
   }

   public static DamagePredicate func_192364_a(@Nullable JsonElement p_192364_0_) {
      if (p_192364_0_ != null && !p_192364_0_.isJsonNull()) {
         JsonObject jsonobject = JsonUtils.func_151210_l(p_192364_0_, "damage");
         MinMaxBounds minmaxbounds = MinMaxBounds.func_192515_a(jsonobject.get("dealt"));
         MinMaxBounds minmaxbounds1 = MinMaxBounds.func_192515_a(jsonobject.get("taken"));
         Boolean obool = jsonobject.has("blocked") ? JsonUtils.func_151212_i(jsonobject, "blocked") : null;
         EntityPredicate entitypredicate = EntityPredicate.func_192481_a(jsonobject.get("source_entity"));
         DamageSourcePredicate damagesourcepredicate = DamageSourcePredicate.func_192447_a(jsonobject.get("type"));
         return new DamagePredicate(minmaxbounds, minmaxbounds1, entitypredicate, obool, damagesourcepredicate);
      } else {
         return field_192366_a;
      }
   }
}
