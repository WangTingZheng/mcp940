package net.minecraft.advancements.critereon;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class KilledTrigger implements ICriterionTrigger<KilledTrigger.Instance> {
   private final Map<PlayerAdvancements, KilledTrigger.Listeners> field_192213_a = Maps.<PlayerAdvancements, KilledTrigger.Listeners>newHashMap();
   private final ResourceLocation field_192214_b;

   public KilledTrigger(ResourceLocation p_i47433_1_) {
      this.field_192214_b = p_i47433_1_;
   }

   public ResourceLocation func_192163_a() {
      return this.field_192214_b;
   }

   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<KilledTrigger.Instance> p_192165_2_) {
      KilledTrigger.Listeners killedtrigger$listeners = this.field_192213_a.get(p_192165_1_);
      if (killedtrigger$listeners == null) {
         killedtrigger$listeners = new KilledTrigger.Listeners(p_192165_1_);
         this.field_192213_a.put(p_192165_1_, killedtrigger$listeners);
      }

      killedtrigger$listeners.func_192504_a(p_192165_2_);
   }

   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<KilledTrigger.Instance> p_192164_2_) {
      KilledTrigger.Listeners killedtrigger$listeners = this.field_192213_a.get(p_192164_1_);
      if (killedtrigger$listeners != null) {
         killedtrigger$listeners.func_192501_b(p_192164_2_);
         if (killedtrigger$listeners.func_192502_a()) {
            this.field_192213_a.remove(p_192164_1_);
         }
      }

   }

   public void func_192167_a(PlayerAdvancements p_192167_1_) {
      this.field_192213_a.remove(p_192167_1_);
   }

   public KilledTrigger.Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
      return new KilledTrigger.Instance(this.field_192214_b, EntityPredicate.func_192481_a(p_192166_1_.get("entity")), DamageSourcePredicate.func_192447_a(p_192166_1_.get("killing_blow")));
   }

   public void func_192211_a(EntityPlayerMP p_192211_1_, Entity p_192211_2_, DamageSource p_192211_3_) {
      KilledTrigger.Listeners killedtrigger$listeners = this.field_192213_a.get(p_192211_1_.func_192039_O());
      if (killedtrigger$listeners != null) {
         killedtrigger$listeners.func_192503_a(p_192211_1_, p_192211_2_, p_192211_3_);
      }

   }

   public static class Instance extends AbstractCriterionInstance {
      private final EntityPredicate field_192271_a;
      private final DamageSourcePredicate field_192272_b;

      public Instance(ResourceLocation p_i47454_1_, EntityPredicate p_i47454_2_, DamageSourcePredicate p_i47454_3_) {
         super(p_i47454_1_);
         this.field_192271_a = p_i47454_2_;
         this.field_192272_b = p_i47454_3_;
      }

      public boolean func_192270_a(EntityPlayerMP p_192270_1_, Entity p_192270_2_, DamageSource p_192270_3_) {
         return !this.field_192272_b.func_193418_a(p_192270_1_, p_192270_3_) ? false : this.field_192271_a.func_192482_a(p_192270_1_, p_192270_2_);
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192505_a;
      private final Set<ICriterionTrigger.Listener<KilledTrigger.Instance>> field_192506_b = Sets.<ICriterionTrigger.Listener<KilledTrigger.Instance>>newHashSet();

      public Listeners(PlayerAdvancements p_i47455_1_) {
         this.field_192505_a = p_i47455_1_;
      }

      public boolean func_192502_a() {
         return this.field_192506_b.isEmpty();
      }

      public void func_192504_a(ICriterionTrigger.Listener<KilledTrigger.Instance> p_192504_1_) {
         this.field_192506_b.add(p_192504_1_);
      }

      public void func_192501_b(ICriterionTrigger.Listener<KilledTrigger.Instance> p_192501_1_) {
         this.field_192506_b.remove(p_192501_1_);
      }

      public void func_192503_a(EntityPlayerMP p_192503_1_, Entity p_192503_2_, DamageSource p_192503_3_) {
         List<ICriterionTrigger.Listener<KilledTrigger.Instance>> list = null;

         for(ICriterionTrigger.Listener<KilledTrigger.Instance> listener : this.field_192506_b) {
            if (((KilledTrigger.Instance)listener.func_192158_a()).func_192270_a(p_192503_1_, p_192503_2_, p_192503_3_)) {
               if (list == null) {
                  list = Lists.<ICriterionTrigger.Listener<KilledTrigger.Instance>>newArrayList();
               }

               list.add(listener);
            }
         }

         if (list != null) {
            for(ICriterionTrigger.Listener<KilledTrigger.Instance> listener1 : list) {
               listener1.func_192159_a(this.field_192505_a);
            }
         }

      }
   }
}
