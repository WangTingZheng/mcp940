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

public class UsedTotemTrigger implements ICriterionTrigger<UsedTotemTrigger.Instance> {
   private static final ResourceLocation field_193188_a = new ResourceLocation("used_totem");
   private final Map<PlayerAdvancements, UsedTotemTrigger.Listeners> field_193189_b = Maps.<PlayerAdvancements, UsedTotemTrigger.Listeners>newHashMap();

   public ResourceLocation func_192163_a() {
      return field_193188_a;
   }

   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<UsedTotemTrigger.Instance> p_192165_2_) {
      UsedTotemTrigger.Listeners usedtotemtrigger$listeners = this.field_193189_b.get(p_192165_1_);
      if (usedtotemtrigger$listeners == null) {
         usedtotemtrigger$listeners = new UsedTotemTrigger.Listeners(p_192165_1_);
         this.field_193189_b.put(p_192165_1_, usedtotemtrigger$listeners);
      }

      usedtotemtrigger$listeners.func_193508_a(p_192165_2_);
   }

   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<UsedTotemTrigger.Instance> p_192164_2_) {
      UsedTotemTrigger.Listeners usedtotemtrigger$listeners = this.field_193189_b.get(p_192164_1_);
      if (usedtotemtrigger$listeners != null) {
         usedtotemtrigger$listeners.func_193506_b(p_192164_2_);
         if (usedtotemtrigger$listeners.func_193507_a()) {
            this.field_193189_b.remove(p_192164_1_);
         }
      }

   }

   public void func_192167_a(PlayerAdvancements p_192167_1_) {
      this.field_193189_b.remove(p_192167_1_);
   }

   public UsedTotemTrigger.Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
      ItemPredicate itempredicate = ItemPredicate.func_192492_a(p_192166_1_.get("item"));
      return new UsedTotemTrigger.Instance(itempredicate);
   }

   public void func_193187_a(EntityPlayerMP p_193187_1_, ItemStack p_193187_2_) {
      UsedTotemTrigger.Listeners usedtotemtrigger$listeners = this.field_193189_b.get(p_193187_1_.func_192039_O());
      if (usedtotemtrigger$listeners != null) {
         usedtotemtrigger$listeners.func_193509_a(p_193187_2_);
      }

   }

   public static class Instance extends AbstractCriterionInstance {
      private final ItemPredicate field_193219_a;

      public Instance(ItemPredicate p_i47564_1_) {
         super(UsedTotemTrigger.field_193188_a);
         this.field_193219_a = p_i47564_1_;
      }

      public boolean func_193218_a(ItemStack p_193218_1_) {
         return this.field_193219_a.func_192493_a(p_193218_1_);
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_193510_a;
      private final Set<ICriterionTrigger.Listener<UsedTotemTrigger.Instance>> field_193511_b = Sets.<ICriterionTrigger.Listener<UsedTotemTrigger.Instance>>newHashSet();

      public Listeners(PlayerAdvancements p_i47565_1_) {
         this.field_193510_a = p_i47565_1_;
      }

      public boolean func_193507_a() {
         return this.field_193511_b.isEmpty();
      }

      public void func_193508_a(ICriterionTrigger.Listener<UsedTotemTrigger.Instance> p_193508_1_) {
         this.field_193511_b.add(p_193508_1_);
      }

      public void func_193506_b(ICriterionTrigger.Listener<UsedTotemTrigger.Instance> p_193506_1_) {
         this.field_193511_b.remove(p_193506_1_);
      }

      public void func_193509_a(ItemStack p_193509_1_) {
         List<ICriterionTrigger.Listener<UsedTotemTrigger.Instance>> list = null;

         for(ICriterionTrigger.Listener<UsedTotemTrigger.Instance> listener : this.field_193511_b) {
            if (((UsedTotemTrigger.Instance)listener.func_192158_a()).func_193218_a(p_193509_1_)) {
               if (list == null) {
                  list = Lists.<ICriterionTrigger.Listener<UsedTotemTrigger.Instance>>newArrayList();
               }

               list.add(listener);
            }
         }

         if (list != null) {
            for(ICriterionTrigger.Listener<UsedTotemTrigger.Instance> listener1 : list) {
               listener1.func_192159_a(this.field_193510_a);
            }
         }

      }
   }
}
