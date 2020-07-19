package net.minecraft.advancements.critereon;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class MobEffectsPredicate {
   public static final MobEffectsPredicate field_193473_a = new MobEffectsPredicate(Collections.emptyMap());
   private final Map<Potion, MobEffectsPredicate.InstancePredicate> field_193474_b;

   public MobEffectsPredicate(Map<Potion, MobEffectsPredicate.InstancePredicate> p_i47538_1_) {
      this.field_193474_b = p_i47538_1_;
   }

   public boolean func_193469_a(Entity p_193469_1_) {
      if (this == field_193473_a) {
         return true;
      } else {
         return p_193469_1_ instanceof EntityLivingBase ? this.func_193470_a(((EntityLivingBase)p_193469_1_).func_193076_bZ()) : false;
      }
   }

   public boolean func_193472_a(EntityLivingBase p_193472_1_) {
      return this == field_193473_a ? true : this.func_193470_a(p_193472_1_.func_193076_bZ());
   }

   public boolean func_193470_a(Map<Potion, PotionEffect> p_193470_1_) {
      if (this == field_193473_a) {
         return true;
      } else {
         for(Entry<Potion, MobEffectsPredicate.InstancePredicate> entry : this.field_193474_b.entrySet()) {
            PotionEffect potioneffect = p_193470_1_.get(entry.getKey());
            if (!((MobEffectsPredicate.InstancePredicate)entry.getValue()).func_193463_a(potioneffect)) {
               return false;
            }
         }

         return true;
      }
   }

   public static MobEffectsPredicate func_193471_a(@Nullable JsonElement p_193471_0_) {
      if (p_193471_0_ != null && !p_193471_0_.isJsonNull()) {
         JsonObject jsonobject = JsonUtils.func_151210_l(p_193471_0_, "effects");
         Map<Potion, MobEffectsPredicate.InstancePredicate> map = Maps.<Potion, MobEffectsPredicate.InstancePredicate>newHashMap();

         for(Entry<String, JsonElement> entry : jsonobject.entrySet()) {
            ResourceLocation resourcelocation = new ResourceLocation(entry.getKey());
            Potion potion = Potion.field_188414_b.func_82594_a(resourcelocation);
            if (potion == null) {
               throw new JsonSyntaxException("Unknown effect '" + resourcelocation + "'");
            }

            MobEffectsPredicate.InstancePredicate mobeffectspredicate$instancepredicate = MobEffectsPredicate.InstancePredicate.func_193464_a(JsonUtils.func_151210_l(entry.getValue(), entry.getKey()));
            map.put(potion, mobeffectspredicate$instancepredicate);
         }

         return new MobEffectsPredicate(map);
      } else {
         return field_193473_a;
      }
   }

   public static class InstancePredicate {
      private final MinMaxBounds field_193465_a;
      private final MinMaxBounds field_193466_b;
      @Nullable
      private final Boolean field_193467_c;
      @Nullable
      private final Boolean field_193468_d;

      public InstancePredicate(MinMaxBounds p_i47497_1_, MinMaxBounds p_i47497_2_, @Nullable Boolean p_i47497_3_, @Nullable Boolean p_i47497_4_) {
         this.field_193465_a = p_i47497_1_;
         this.field_193466_b = p_i47497_2_;
         this.field_193467_c = p_i47497_3_;
         this.field_193468_d = p_i47497_4_;
      }

      public boolean func_193463_a(@Nullable PotionEffect p_193463_1_) {
         if (p_193463_1_ == null) {
            return false;
         } else if (!this.field_193465_a.func_192514_a((float)p_193463_1_.func_76458_c())) {
            return false;
         } else if (!this.field_193466_b.func_192514_a((float)p_193463_1_.func_76459_b())) {
            return false;
         } else if (this.field_193467_c != null && this.field_193467_c.booleanValue() != p_193463_1_.func_82720_e()) {
            return false;
         } else {
            return this.field_193468_d == null || this.field_193468_d.booleanValue() == p_193463_1_.func_188418_e();
         }
      }

      public static MobEffectsPredicate.InstancePredicate func_193464_a(JsonObject p_193464_0_) {
         MinMaxBounds minmaxbounds = MinMaxBounds.func_192515_a(p_193464_0_.get("amplifier"));
         MinMaxBounds minmaxbounds1 = MinMaxBounds.func_192515_a(p_193464_0_.get("duration"));
         Boolean obool = p_193464_0_.has("ambient") ? JsonUtils.func_151212_i(p_193464_0_, "ambient") : null;
         Boolean obool1 = p_193464_0_.has("visible") ? JsonUtils.func_151212_i(p_193464_0_, "visible") : null;
         return new MobEffectsPredicate.InstancePredicate(minmaxbounds, minmaxbounds1, obool, obool1);
      }
   }
}
