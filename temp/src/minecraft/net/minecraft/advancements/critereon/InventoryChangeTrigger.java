package net.minecraft.advancements.critereon;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class InventoryChangeTrigger implements ICriterionTrigger<InventoryChangeTrigger.Instance> {
   private static final ResourceLocation field_192209_a = new ResourceLocation("inventory_changed");
   private final Map<PlayerAdvancements, InventoryChangeTrigger.Listeners> field_192210_b = Maps.<PlayerAdvancements, InventoryChangeTrigger.Listeners>newHashMap();

   public ResourceLocation func_192163_a() {
      return field_192209_a;
   }

   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<InventoryChangeTrigger.Instance> p_192165_2_) {
      InventoryChangeTrigger.Listeners inventorychangetrigger$listeners = this.field_192210_b.get(p_192165_1_);
      if (inventorychangetrigger$listeners == null) {
         inventorychangetrigger$listeners = new InventoryChangeTrigger.Listeners(p_192165_1_);
         this.field_192210_b.put(p_192165_1_, inventorychangetrigger$listeners);
      }

      inventorychangetrigger$listeners.func_192489_a(p_192165_2_);
   }

   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<InventoryChangeTrigger.Instance> p_192164_2_) {
      InventoryChangeTrigger.Listeners inventorychangetrigger$listeners = this.field_192210_b.get(p_192164_1_);
      if (inventorychangetrigger$listeners != null) {
         inventorychangetrigger$listeners.func_192487_b(p_192164_2_);
         if (inventorychangetrigger$listeners.func_192488_a()) {
            this.field_192210_b.remove(p_192164_1_);
         }
      }

   }

   public void func_192167_a(PlayerAdvancements p_192167_1_) {
      this.field_192210_b.remove(p_192167_1_);
   }

   public InventoryChangeTrigger.Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
      JsonObject jsonobject = JsonUtils.func_151218_a(p_192166_1_, "slots", new JsonObject());
      MinMaxBounds minmaxbounds = MinMaxBounds.func_192515_a(jsonobject.get("occupied"));
      MinMaxBounds minmaxbounds1 = MinMaxBounds.func_192515_a(jsonobject.get("full"));
      MinMaxBounds minmaxbounds2 = MinMaxBounds.func_192515_a(jsonobject.get("empty"));
      ItemPredicate[] aitempredicate = ItemPredicate.func_192494_b(p_192166_1_.get("items"));
      return new InventoryChangeTrigger.Instance(minmaxbounds, minmaxbounds1, minmaxbounds2, aitempredicate);
   }

   public void func_192208_a(EntityPlayerMP p_192208_1_, InventoryPlayer p_192208_2_) {
      InventoryChangeTrigger.Listeners inventorychangetrigger$listeners = this.field_192210_b.get(p_192208_1_.func_192039_O());
      if (inventorychangetrigger$listeners != null) {
         inventorychangetrigger$listeners.func_192486_a(p_192208_2_);
      }

   }

   public static class Instance extends AbstractCriterionInstance {
      private final MinMaxBounds field_192266_a;
      private final MinMaxBounds field_192267_b;
      private final MinMaxBounds field_192268_c;
      private final ItemPredicate[] field_192269_d;

      public Instance(MinMaxBounds p_i47390_1_, MinMaxBounds p_i47390_2_, MinMaxBounds p_i47390_3_, ItemPredicate[] p_i47390_4_) {
         super(InventoryChangeTrigger.field_192209_a);
         this.field_192266_a = p_i47390_1_;
         this.field_192267_b = p_i47390_2_;
         this.field_192268_c = p_i47390_3_;
         this.field_192269_d = p_i47390_4_;
      }

      public boolean func_192265_a(InventoryPlayer p_192265_1_) {
         int i = 0;
         int j = 0;
         int k = 0;
         List<ItemPredicate> list = Lists.newArrayList(this.field_192269_d);

         for(int l = 0; l < p_192265_1_.func_70302_i_(); ++l) {
            ItemStack itemstack = p_192265_1_.func_70301_a(l);
            if (itemstack.func_190926_b()) {
               ++j;
            } else {
               ++k;
               if (itemstack.func_190916_E() >= itemstack.func_77976_d()) {
                  ++i;
               }

               Iterator<ItemPredicate> iterator = list.iterator();

               while(iterator.hasNext()) {
                  ItemPredicate itempredicate = iterator.next();
                  if (itempredicate.func_192493_a(itemstack)) {
                     iterator.remove();
                  }
               }
            }
         }

         if (!this.field_192267_b.func_192514_a((float)i)) {
            return false;
         } else if (!this.field_192268_c.func_192514_a((float)j)) {
            return false;
         } else if (!this.field_192266_a.func_192514_a((float)k)) {
            return false;
         } else if (!list.isEmpty()) {
            return false;
         } else {
            return true;
         }
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192490_a;
      private final Set<ICriterionTrigger.Listener<InventoryChangeTrigger.Instance>> field_192491_b = Sets.<ICriterionTrigger.Listener<InventoryChangeTrigger.Instance>>newHashSet();

      public Listeners(PlayerAdvancements p_i47391_1_) {
         this.field_192490_a = p_i47391_1_;
      }

      public boolean func_192488_a() {
         return this.field_192491_b.isEmpty();
      }

      public void func_192489_a(ICriterionTrigger.Listener<InventoryChangeTrigger.Instance> p_192489_1_) {
         this.field_192491_b.add(p_192489_1_);
      }

      public void func_192487_b(ICriterionTrigger.Listener<InventoryChangeTrigger.Instance> p_192487_1_) {
         this.field_192491_b.remove(p_192487_1_);
      }

      public void func_192486_a(InventoryPlayer p_192486_1_) {
         List<ICriterionTrigger.Listener<InventoryChangeTrigger.Instance>> list = null;

         for(ICriterionTrigger.Listener<InventoryChangeTrigger.Instance> listener : this.field_192491_b) {
            if (((InventoryChangeTrigger.Instance)listener.func_192158_a()).func_192265_a(p_192486_1_)) {
               if (list == null) {
                  list = Lists.<ICriterionTrigger.Listener<InventoryChangeTrigger.Instance>>newArrayList();
               }

               list.add(listener);
            }
         }

         if (list != null) {
            for(ICriterionTrigger.Listener<InventoryChangeTrigger.Instance> listener1 : list) {
               listener1.func_192159_a(this.field_192490_a);
            }
         }

      }
   }
}
