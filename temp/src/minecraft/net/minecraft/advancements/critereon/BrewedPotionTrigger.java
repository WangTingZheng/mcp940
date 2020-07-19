package net.minecraft.advancements.critereon;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionType;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class BrewedPotionTrigger implements ICriterionTrigger<BrewedPotionTrigger.Instance> {
   private static final ResourceLocation field_192176_a = new ResourceLocation("brewed_potion");
   private final Map<PlayerAdvancements, BrewedPotionTrigger.Listeners> field_192177_b = Maps.<PlayerAdvancements, BrewedPotionTrigger.Listeners>newHashMap();

   public ResourceLocation func_192163_a() {
      return field_192176_a;
   }

   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<BrewedPotionTrigger.Instance> p_192165_2_) {
      BrewedPotionTrigger.Listeners brewedpotiontrigger$listeners = this.field_192177_b.get(p_192165_1_);
      if (brewedpotiontrigger$listeners == null) {
         brewedpotiontrigger$listeners = new BrewedPotionTrigger.Listeners(p_192165_1_);
         this.field_192177_b.put(p_192165_1_, brewedpotiontrigger$listeners);
      }

      brewedpotiontrigger$listeners.func_192349_a(p_192165_2_);
   }

   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<BrewedPotionTrigger.Instance> p_192164_2_) {
      BrewedPotionTrigger.Listeners brewedpotiontrigger$listeners = this.field_192177_b.get(p_192164_1_);
      if (brewedpotiontrigger$listeners != null) {
         brewedpotiontrigger$listeners.func_192346_b(p_192164_2_);
         if (brewedpotiontrigger$listeners.func_192347_a()) {
            this.field_192177_b.remove(p_192164_1_);
         }
      }

   }

   public void func_192167_a(PlayerAdvancements p_192167_1_) {
      this.field_192177_b.remove(p_192167_1_);
   }

   public BrewedPotionTrigger.Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
      PotionType potiontype = null;
      if (p_192166_1_.has("potion")) {
         ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.func_151200_h(p_192166_1_, "potion"));
         if (!PotionType.field_185176_a.func_148741_d(resourcelocation)) {
            throw new JsonSyntaxException("Unknown potion '" + resourcelocation + "'");
         }

         potiontype = PotionType.field_185176_a.func_82594_a(resourcelocation);
      }

      return new BrewedPotionTrigger.Instance(potiontype);
   }

   public void func_192173_a(EntityPlayerMP p_192173_1_, PotionType p_192173_2_) {
      BrewedPotionTrigger.Listeners brewedpotiontrigger$listeners = this.field_192177_b.get(p_192173_1_.func_192039_O());
      if (brewedpotiontrigger$listeners != null) {
         brewedpotiontrigger$listeners.func_192348_a(p_192173_2_);
      }

   }

   public static class Instance extends AbstractCriterionInstance {
      private final PotionType field_192251_a;

      public Instance(@Nullable PotionType p_i47398_1_) {
         super(BrewedPotionTrigger.field_192176_a);
         this.field_192251_a = p_i47398_1_;
      }

      public boolean func_192250_a(PotionType p_192250_1_) {
         return this.field_192251_a == null || this.field_192251_a == p_192250_1_;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192350_a;
      private final Set<ICriterionTrigger.Listener<BrewedPotionTrigger.Instance>> field_192351_b = Sets.<ICriterionTrigger.Listener<BrewedPotionTrigger.Instance>>newHashSet();

      public Listeners(PlayerAdvancements p_i47399_1_) {
         this.field_192350_a = p_i47399_1_;
      }

      public boolean func_192347_a() {
         return this.field_192351_b.isEmpty();
      }

      public void func_192349_a(ICriterionTrigger.Listener<BrewedPotionTrigger.Instance> p_192349_1_) {
         this.field_192351_b.add(p_192349_1_);
      }

      public void func_192346_b(ICriterionTrigger.Listener<BrewedPotionTrigger.Instance> p_192346_1_) {
         this.field_192351_b.remove(p_192346_1_);
      }

      public void func_192348_a(PotionType p_192348_1_) {
         List<ICriterionTrigger.Listener<BrewedPotionTrigger.Instance>> list = null;

         for(ICriterionTrigger.Listener<BrewedPotionTrigger.Instance> listener : this.field_192351_b) {
            if (((BrewedPotionTrigger.Instance)listener.func_192158_a()).func_192250_a(p_192348_1_)) {
               if (list == null) {
                  list = Lists.<ICriterionTrigger.Listener<BrewedPotionTrigger.Instance>>newArrayList();
               }

               list.add(listener);
            }
         }

         if (list != null) {
            for(ICriterionTrigger.Listener<BrewedPotionTrigger.Instance> listener1 : list) {
               listener1.func_192159_a(this.field_192350_a);
            }
         }

      }
   }
}
