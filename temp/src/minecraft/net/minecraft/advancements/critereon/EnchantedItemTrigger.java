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

public class EnchantedItemTrigger implements ICriterionTrigger<EnchantedItemTrigger.Instance> {
   private static final ResourceLocation field_192191_a = new ResourceLocation("enchanted_item");
   private final Map<PlayerAdvancements, EnchantedItemTrigger.Listeners> field_192192_b = Maps.<PlayerAdvancements, EnchantedItemTrigger.Listeners>newHashMap();

   public ResourceLocation func_192163_a() {
      return field_192191_a;
   }

   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<EnchantedItemTrigger.Instance> p_192165_2_) {
      EnchantedItemTrigger.Listeners enchanteditemtrigger$listeners = this.field_192192_b.get(p_192165_1_);
      if (enchanteditemtrigger$listeners == null) {
         enchanteditemtrigger$listeners = new EnchantedItemTrigger.Listeners(p_192165_1_);
         this.field_192192_b.put(p_192165_1_, enchanteditemtrigger$listeners);
      }

      enchanteditemtrigger$listeners.func_192460_a(p_192165_2_);
   }

   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<EnchantedItemTrigger.Instance> p_192164_2_) {
      EnchantedItemTrigger.Listeners enchanteditemtrigger$listeners = this.field_192192_b.get(p_192164_1_);
      if (enchanteditemtrigger$listeners != null) {
         enchanteditemtrigger$listeners.func_192457_b(p_192164_2_);
         if (enchanteditemtrigger$listeners.func_192458_a()) {
            this.field_192192_b.remove(p_192164_1_);
         }
      }

   }

   public void func_192167_a(PlayerAdvancements p_192167_1_) {
      this.field_192192_b.remove(p_192167_1_);
   }

   public EnchantedItemTrigger.Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
      ItemPredicate itempredicate = ItemPredicate.func_192492_a(p_192166_1_.get("item"));
      MinMaxBounds minmaxbounds = MinMaxBounds.func_192515_a(p_192166_1_.get("levels"));
      return new EnchantedItemTrigger.Instance(itempredicate, minmaxbounds);
   }

   public void func_192190_a(EntityPlayerMP p_192190_1_, ItemStack p_192190_2_, int p_192190_3_) {
      EnchantedItemTrigger.Listeners enchanteditemtrigger$listeners = this.field_192192_b.get(p_192190_1_.func_192039_O());
      if (enchanteditemtrigger$listeners != null) {
         enchanteditemtrigger$listeners.func_192459_a(p_192190_2_, p_192190_3_);
      }

   }

   public static class Instance extends AbstractCriterionInstance {
      private final ItemPredicate field_192258_a;
      private final MinMaxBounds field_192259_b;

      public Instance(ItemPredicate p_i47376_1_, MinMaxBounds p_i47376_2_) {
         super(EnchantedItemTrigger.field_192191_a);
         this.field_192258_a = p_i47376_1_;
         this.field_192259_b = p_i47376_2_;
      }

      public boolean func_192257_a(ItemStack p_192257_1_, int p_192257_2_) {
         if (!this.field_192258_a.func_192493_a(p_192257_1_)) {
            return false;
         } else {
            return this.field_192259_b.func_192514_a((float)p_192257_2_);
         }
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192461_a;
      private final Set<ICriterionTrigger.Listener<EnchantedItemTrigger.Instance>> field_192462_b = Sets.<ICriterionTrigger.Listener<EnchantedItemTrigger.Instance>>newHashSet();

      public Listeners(PlayerAdvancements p_i47377_1_) {
         this.field_192461_a = p_i47377_1_;
      }

      public boolean func_192458_a() {
         return this.field_192462_b.isEmpty();
      }

      public void func_192460_a(ICriterionTrigger.Listener<EnchantedItemTrigger.Instance> p_192460_1_) {
         this.field_192462_b.add(p_192460_1_);
      }

      public void func_192457_b(ICriterionTrigger.Listener<EnchantedItemTrigger.Instance> p_192457_1_) {
         this.field_192462_b.remove(p_192457_1_);
      }

      public void func_192459_a(ItemStack p_192459_1_, int p_192459_2_) {
         List<ICriterionTrigger.Listener<EnchantedItemTrigger.Instance>> list = null;

         for(ICriterionTrigger.Listener<EnchantedItemTrigger.Instance> listener : this.field_192462_b) {
            if (((EnchantedItemTrigger.Instance)listener.func_192158_a()).func_192257_a(p_192459_1_, p_192459_2_)) {
               if (list == null) {
                  list = Lists.<ICriterionTrigger.Listener<EnchantedItemTrigger.Instance>>newArrayList();
               }

               list.add(listener);
            }
         }

         if (list != null) {
            for(ICriterionTrigger.Listener<EnchantedItemTrigger.Instance> listener1 : list) {
               listener1.func_192159_a(this.field_192461_a);
            }
         }

      }
   }
}
