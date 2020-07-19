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
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ConsumeItemTrigger implements ICriterionTrigger<ConsumeItemTrigger.Instance> {
   private static final ResourceLocation field_193149_a = new ResourceLocation("consume_item");
   private final Map<PlayerAdvancements, ConsumeItemTrigger.Listeners> field_193150_b = Maps.<PlayerAdvancements, ConsumeItemTrigger.Listeners>newHashMap();

   public ResourceLocation func_192163_a() {
      return field_193149_a;
   }

   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<ConsumeItemTrigger.Instance> p_192165_2_) {
      ConsumeItemTrigger.Listeners consumeitemtrigger$listeners = this.field_193150_b.get(p_192165_1_);
      if (consumeitemtrigger$listeners == null) {
         consumeitemtrigger$listeners = new ConsumeItemTrigger.Listeners(p_192165_1_);
         this.field_193150_b.put(p_192165_1_, consumeitemtrigger$listeners);
      }

      consumeitemtrigger$listeners.func_193239_a(p_192165_2_);
   }

   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<ConsumeItemTrigger.Instance> p_192164_2_) {
      ConsumeItemTrigger.Listeners consumeitemtrigger$listeners = this.field_193150_b.get(p_192164_1_);
      if (consumeitemtrigger$listeners != null) {
         consumeitemtrigger$listeners.func_193237_b(p_192164_2_);
         if (consumeitemtrigger$listeners.func_193238_a()) {
            this.field_193150_b.remove(p_192164_1_);
         }
      }

   }

   public void func_192167_a(PlayerAdvancements p_192167_1_) {
      this.field_193150_b.remove(p_192167_1_);
   }

   public ConsumeItemTrigger.Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
      ItemPredicate itempredicate = ItemPredicate.func_192492_a(p_192166_1_.get("item"));
      return new ConsumeItemTrigger.Instance(itempredicate);
   }

   public void func_193148_a(EntityPlayerMP p_193148_1_, ItemStack p_193148_2_) {
      ConsumeItemTrigger.Listeners consumeitemtrigger$listeners = this.field_193150_b.get(p_193148_1_.func_192039_O());
      if (consumeitemtrigger$listeners != null) {
         consumeitemtrigger$listeners.func_193240_a(p_193148_2_);
      }

   }

   public static class Instance extends AbstractCriterionInstance {
      private final ItemPredicate field_193194_a;

      public Instance(ItemPredicate p_i47562_1_) {
         super(ConsumeItemTrigger.field_193149_a);
         this.field_193194_a = p_i47562_1_;
      }

      public boolean func_193193_a(ItemStack p_193193_1_) {
         return this.field_193194_a.func_192493_a(p_193193_1_);
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_193241_a;
      private final Set<ICriterionTrigger.Listener<ConsumeItemTrigger.Instance>> field_193242_b = Sets.<ICriterionTrigger.Listener<ConsumeItemTrigger.Instance>>newHashSet();

      public Listeners(PlayerAdvancements p_i47563_1_) {
         this.field_193241_a = p_i47563_1_;
      }

      public boolean func_193238_a() {
         return this.field_193242_b.isEmpty();
      }

      public void func_193239_a(ICriterionTrigger.Listener<ConsumeItemTrigger.Instance> p_193239_1_) {
         this.field_193242_b.add(p_193239_1_);
      }

      public void func_193237_b(ICriterionTrigger.Listener<ConsumeItemTrigger.Instance> p_193237_1_) {
         this.field_193242_b.remove(p_193237_1_);
      }

      public void func_193240_a(ItemStack p_193240_1_) {
         List<ICriterionTrigger.Listener<ConsumeItemTrigger.Instance>> list = null;

         for(ICriterionTrigger.Listener<ConsumeItemTrigger.Instance> listener : this.field_193242_b) {
            if (((ConsumeItemTrigger.Instance)listener.func_192158_a()).func_193193_a(p_193240_1_)) {
               if (list == null) {
                  list = Lists.<ICriterionTrigger.Listener<ConsumeItemTrigger.Instance>>newArrayList();
               }

               list.add(listener);
            }
         }

         if (list != null) {
            for(ICriterionTrigger.Listener<ConsumeItemTrigger.Instance> listener1 : list) {
               listener1.func_192159_a(this.field_193241_a);
            }
         }

      }
   }
}
