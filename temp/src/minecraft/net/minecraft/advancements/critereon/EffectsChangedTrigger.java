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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class EffectsChangedTrigger implements ICriterionTrigger<EffectsChangedTrigger.Instance> {
   private static final ResourceLocation field_193154_a = new ResourceLocation("effects_changed");
   private final Map<PlayerAdvancements, EffectsChangedTrigger.Listeners> field_193155_b = Maps.<PlayerAdvancements, EffectsChangedTrigger.Listeners>newHashMap();

   public ResourceLocation func_192163_a() {
      return field_193154_a;
   }

   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<EffectsChangedTrigger.Instance> p_192165_2_) {
      EffectsChangedTrigger.Listeners effectschangedtrigger$listeners = this.field_193155_b.get(p_192165_1_);
      if (effectschangedtrigger$listeners == null) {
         effectschangedtrigger$listeners = new EffectsChangedTrigger.Listeners(p_192165_1_);
         this.field_193155_b.put(p_192165_1_, effectschangedtrigger$listeners);
      }

      effectschangedtrigger$listeners.func_193431_a(p_192165_2_);
   }

   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<EffectsChangedTrigger.Instance> p_192164_2_) {
      EffectsChangedTrigger.Listeners effectschangedtrigger$listeners = this.field_193155_b.get(p_192164_1_);
      if (effectschangedtrigger$listeners != null) {
         effectschangedtrigger$listeners.func_193429_b(p_192164_2_);
         if (effectschangedtrigger$listeners.func_193430_a()) {
            this.field_193155_b.remove(p_192164_1_);
         }
      }

   }

   public void func_192167_a(PlayerAdvancements p_192167_1_) {
      this.field_193155_b.remove(p_192167_1_);
   }

   public EffectsChangedTrigger.Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
      MobEffectsPredicate mobeffectspredicate = MobEffectsPredicate.func_193471_a(p_192166_1_.get("effects"));
      return new EffectsChangedTrigger.Instance(mobeffectspredicate);
   }

   public void func_193153_a(EntityPlayerMP p_193153_1_) {
      EffectsChangedTrigger.Listeners effectschangedtrigger$listeners = this.field_193155_b.get(p_193153_1_.func_192039_O());
      if (effectschangedtrigger$listeners != null) {
         effectschangedtrigger$listeners.func_193432_a(p_193153_1_);
      }

   }

   public static class Instance extends AbstractCriterionInstance {
      private final MobEffectsPredicate field_193196_a;

      public Instance(MobEffectsPredicate p_i47545_1_) {
         super(EffectsChangedTrigger.field_193154_a);
         this.field_193196_a = p_i47545_1_;
      }

      public boolean func_193195_a(EntityPlayerMP p_193195_1_) {
         return this.field_193196_a.func_193472_a(p_193195_1_);
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_193433_a;
      private final Set<ICriterionTrigger.Listener<EffectsChangedTrigger.Instance>> field_193434_b = Sets.<ICriterionTrigger.Listener<EffectsChangedTrigger.Instance>>newHashSet();

      public Listeners(PlayerAdvancements p_i47546_1_) {
         this.field_193433_a = p_i47546_1_;
      }

      public boolean func_193430_a() {
         return this.field_193434_b.isEmpty();
      }

      public void func_193431_a(ICriterionTrigger.Listener<EffectsChangedTrigger.Instance> p_193431_1_) {
         this.field_193434_b.add(p_193431_1_);
      }

      public void func_193429_b(ICriterionTrigger.Listener<EffectsChangedTrigger.Instance> p_193429_1_) {
         this.field_193434_b.remove(p_193429_1_);
      }

      public void func_193432_a(EntityPlayerMP p_193432_1_) {
         List<ICriterionTrigger.Listener<EffectsChangedTrigger.Instance>> list = null;

         for(ICriterionTrigger.Listener<EffectsChangedTrigger.Instance> listener : this.field_193434_b) {
            if (((EffectsChangedTrigger.Instance)listener.func_192158_a()).func_193195_a(p_193432_1_)) {
               if (list == null) {
                  list = Lists.<ICriterionTrigger.Listener<EffectsChangedTrigger.Instance>>newArrayList();
               }

               list.add(listener);
            }
         }

         if (list != null) {
            for(ICriterionTrigger.Listener<EffectsChangedTrigger.Instance> listener1 : list) {
               listener1.func_192159_a(this.field_193433_a);
            }
         }

      }
   }
}
