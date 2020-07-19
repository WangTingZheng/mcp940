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
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class TameAnimalTrigger implements ICriterionTrigger<TameAnimalTrigger.Instance> {
   private static final ResourceLocation field_193179_a = new ResourceLocation("tame_animal");
   private final Map<PlayerAdvancements, TameAnimalTrigger.Listeners> field_193180_b = Maps.<PlayerAdvancements, TameAnimalTrigger.Listeners>newHashMap();

   public ResourceLocation func_192163_a() {
      return field_193179_a;
   }

   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<TameAnimalTrigger.Instance> p_192165_2_) {
      TameAnimalTrigger.Listeners tameanimaltrigger$listeners = this.field_193180_b.get(p_192165_1_);
      if (tameanimaltrigger$listeners == null) {
         tameanimaltrigger$listeners = new TameAnimalTrigger.Listeners(p_192165_1_);
         this.field_193180_b.put(p_192165_1_, tameanimaltrigger$listeners);
      }

      tameanimaltrigger$listeners.func_193496_a(p_192165_2_);
   }

   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<TameAnimalTrigger.Instance> p_192164_2_) {
      TameAnimalTrigger.Listeners tameanimaltrigger$listeners = this.field_193180_b.get(p_192164_1_);
      if (tameanimaltrigger$listeners != null) {
         tameanimaltrigger$listeners.func_193494_b(p_192164_2_);
         if (tameanimaltrigger$listeners.func_193495_a()) {
            this.field_193180_b.remove(p_192164_1_);
         }
      }

   }

   public void func_192167_a(PlayerAdvancements p_192167_1_) {
      this.field_193180_b.remove(p_192167_1_);
   }

   public TameAnimalTrigger.Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
      EntityPredicate entitypredicate = EntityPredicate.func_192481_a(p_192166_1_.get("entity"));
      return new TameAnimalTrigger.Instance(entitypredicate);
   }

   public void func_193178_a(EntityPlayerMP p_193178_1_, EntityAnimal p_193178_2_) {
      TameAnimalTrigger.Listeners tameanimaltrigger$listeners = this.field_193180_b.get(p_193178_1_.func_192039_O());
      if (tameanimaltrigger$listeners != null) {
         tameanimaltrigger$listeners.func_193497_a(p_193178_1_, p_193178_2_);
      }

   }

   public static class Instance extends AbstractCriterionInstance {
      private final EntityPredicate field_193217_a;

      public Instance(EntityPredicate p_i47513_1_) {
         super(TameAnimalTrigger.field_193179_a);
         this.field_193217_a = p_i47513_1_;
      }

      public boolean func_193216_a(EntityPlayerMP p_193216_1_, EntityAnimal p_193216_2_) {
         return this.field_193217_a.func_192482_a(p_193216_1_, p_193216_2_);
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_193498_a;
      private final Set<ICriterionTrigger.Listener<TameAnimalTrigger.Instance>> field_193499_b = Sets.<ICriterionTrigger.Listener<TameAnimalTrigger.Instance>>newHashSet();

      public Listeners(PlayerAdvancements p_i47514_1_) {
         this.field_193498_a = p_i47514_1_;
      }

      public boolean func_193495_a() {
         return this.field_193499_b.isEmpty();
      }

      public void func_193496_a(ICriterionTrigger.Listener<TameAnimalTrigger.Instance> p_193496_1_) {
         this.field_193499_b.add(p_193496_1_);
      }

      public void func_193494_b(ICriterionTrigger.Listener<TameAnimalTrigger.Instance> p_193494_1_) {
         this.field_193499_b.remove(p_193494_1_);
      }

      public void func_193497_a(EntityPlayerMP p_193497_1_, EntityAnimal p_193497_2_) {
         List<ICriterionTrigger.Listener<TameAnimalTrigger.Instance>> list = null;

         for(ICriterionTrigger.Listener<TameAnimalTrigger.Instance> listener : this.field_193499_b) {
            if (((TameAnimalTrigger.Instance)listener.func_192158_a()).func_193216_a(p_193497_1_, p_193497_2_)) {
               if (list == null) {
                  list = Lists.<ICriterionTrigger.Listener<TameAnimalTrigger.Instance>>newArrayList();
               }

               list.add(listener);
            }
         }

         if (list != null) {
            for(ICriterionTrigger.Listener<TameAnimalTrigger.Instance> listener1 : list) {
               listener1.func_192159_a(this.field_193498_a);
            }
         }

      }
   }
}
