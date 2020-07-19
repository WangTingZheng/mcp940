package net.minecraft.advancements.critereon;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class TickTrigger implements ICriterionTrigger<TickTrigger.Instance> {
   public static final ResourceLocation field_193183_a = new ResourceLocation("tick");
   private final Map<PlayerAdvancements, TickTrigger.Listeners> field_193184_b = Maps.<PlayerAdvancements, TickTrigger.Listeners>newHashMap();

   public ResourceLocation func_192163_a() {
      return field_193183_a;
   }

   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<TickTrigger.Instance> p_192165_2_) {
      TickTrigger.Listeners ticktrigger$listeners = this.field_193184_b.get(p_192165_1_);
      if (ticktrigger$listeners == null) {
         ticktrigger$listeners = new TickTrigger.Listeners(p_192165_1_);
         this.field_193184_b.put(p_192165_1_, ticktrigger$listeners);
      }

      ticktrigger$listeners.func_193502_a(p_192165_2_);
   }

   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<TickTrigger.Instance> p_192164_2_) {
      TickTrigger.Listeners ticktrigger$listeners = this.field_193184_b.get(p_192164_1_);
      if (ticktrigger$listeners != null) {
         ticktrigger$listeners.func_193500_b(p_192164_2_);
         if (ticktrigger$listeners.func_193501_a()) {
            this.field_193184_b.remove(p_192164_1_);
         }
      }

   }

   public void func_192167_a(PlayerAdvancements p_192167_1_) {
      this.field_193184_b.remove(p_192167_1_);
   }

   public TickTrigger.Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
      return new TickTrigger.Instance();
   }

   public void func_193182_a(EntityPlayerMP p_193182_1_) {
      TickTrigger.Listeners ticktrigger$listeners = this.field_193184_b.get(p_193182_1_.func_192039_O());
      if (ticktrigger$listeners != null) {
         ticktrigger$listeners.func_193503_b();
      }

   }

   public static class Instance extends AbstractCriterionInstance {
      public Instance() {
         super(TickTrigger.field_193183_a);
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_193504_a;
      private final Set<ICriterionTrigger.Listener<TickTrigger.Instance>> field_193505_b = Sets.<ICriterionTrigger.Listener<TickTrigger.Instance>>newHashSet();

      public Listeners(PlayerAdvancements p_i47496_1_) {
         this.field_193504_a = p_i47496_1_;
      }

      public boolean func_193501_a() {
         return this.field_193505_b.isEmpty();
      }

      public void func_193502_a(ICriterionTrigger.Listener<TickTrigger.Instance> p_193502_1_) {
         this.field_193505_b.add(p_193502_1_);
      }

      public void func_193500_b(ICriterionTrigger.Listener<TickTrigger.Instance> p_193500_1_) {
         this.field_193505_b.remove(p_193500_1_);
      }

      public void func_193503_b() {
         for(ICriterionTrigger.Listener<TickTrigger.Instance> listener : Lists.newArrayList(this.field_193505_b)) {
            listener.func_192159_a(this.field_193504_a);
         }

      }
   }
}
