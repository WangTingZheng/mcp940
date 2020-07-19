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
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class BredAnimalsTrigger implements ICriterionTrigger<BredAnimalsTrigger.Instance> {
   private static final ResourceLocation field_192171_a = new ResourceLocation("bred_animals");
   private final Map<PlayerAdvancements, BredAnimalsTrigger.Listeners> field_192172_b = Maps.<PlayerAdvancements, BredAnimalsTrigger.Listeners>newHashMap();

   public ResourceLocation func_192163_a() {
      return field_192171_a;
   }

   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<BredAnimalsTrigger.Instance> p_192165_2_) {
      BredAnimalsTrigger.Listeners bredanimalstrigger$listeners = this.field_192172_b.get(p_192165_1_);
      if (bredanimalstrigger$listeners == null) {
         bredanimalstrigger$listeners = new BredAnimalsTrigger.Listeners(p_192165_1_);
         this.field_192172_b.put(p_192165_1_, bredanimalstrigger$listeners);
      }

      bredanimalstrigger$listeners.func_192343_a(p_192165_2_);
   }

   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<BredAnimalsTrigger.Instance> p_192164_2_) {
      BredAnimalsTrigger.Listeners bredanimalstrigger$listeners = this.field_192172_b.get(p_192164_1_);
      if (bredanimalstrigger$listeners != null) {
         bredanimalstrigger$listeners.func_192340_b(p_192164_2_);
         if (bredanimalstrigger$listeners.func_192341_a()) {
            this.field_192172_b.remove(p_192164_1_);
         }
      }

   }

   public void func_192167_a(PlayerAdvancements p_192167_1_) {
      this.field_192172_b.remove(p_192167_1_);
   }

   public BredAnimalsTrigger.Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
      EntityPredicate entitypredicate = EntityPredicate.func_192481_a(p_192166_1_.get("parent"));
      EntityPredicate entitypredicate1 = EntityPredicate.func_192481_a(p_192166_1_.get("partner"));
      EntityPredicate entitypredicate2 = EntityPredicate.func_192481_a(p_192166_1_.get("child"));
      return new BredAnimalsTrigger.Instance(entitypredicate, entitypredicate1, entitypredicate2);
   }

   public void func_192168_a(EntityPlayerMP p_192168_1_, EntityAnimal p_192168_2_, EntityAnimal p_192168_3_, EntityAgeable p_192168_4_) {
      BredAnimalsTrigger.Listeners bredanimalstrigger$listeners = this.field_192172_b.get(p_192168_1_.func_192039_O());
      if (bredanimalstrigger$listeners != null) {
         bredanimalstrigger$listeners.func_192342_a(p_192168_1_, p_192168_2_, p_192168_3_, p_192168_4_);
      }

   }

   public static class Instance extends AbstractCriterionInstance {
      private final EntityPredicate field_192247_a;
      private final EntityPredicate field_192248_b;
      private final EntityPredicate field_192249_c;

      public Instance(EntityPredicate p_i47408_1_, EntityPredicate p_i47408_2_, EntityPredicate p_i47408_3_) {
         super(BredAnimalsTrigger.field_192171_a);
         this.field_192247_a = p_i47408_1_;
         this.field_192248_b = p_i47408_2_;
         this.field_192249_c = p_i47408_3_;
      }

      public boolean func_192246_a(EntityPlayerMP p_192246_1_, EntityAnimal p_192246_2_, EntityAnimal p_192246_3_, EntityAgeable p_192246_4_) {
         if (!this.field_192249_c.func_192482_a(p_192246_1_, p_192246_4_)) {
            return false;
         } else {
            return this.field_192247_a.func_192482_a(p_192246_1_, p_192246_2_) && this.field_192248_b.func_192482_a(p_192246_1_, p_192246_3_) || this.field_192247_a.func_192482_a(p_192246_1_, p_192246_3_) && this.field_192248_b.func_192482_a(p_192246_1_, p_192246_2_);
         }
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192344_a;
      private final Set<ICriterionTrigger.Listener<BredAnimalsTrigger.Instance>> field_192345_b = Sets.<ICriterionTrigger.Listener<BredAnimalsTrigger.Instance>>newHashSet();

      public Listeners(PlayerAdvancements p_i47409_1_) {
         this.field_192344_a = p_i47409_1_;
      }

      public boolean func_192341_a() {
         return this.field_192345_b.isEmpty();
      }

      public void func_192343_a(ICriterionTrigger.Listener<BredAnimalsTrigger.Instance> p_192343_1_) {
         this.field_192345_b.add(p_192343_1_);
      }

      public void func_192340_b(ICriterionTrigger.Listener<BredAnimalsTrigger.Instance> p_192340_1_) {
         this.field_192345_b.remove(p_192340_1_);
      }

      public void func_192342_a(EntityPlayerMP p_192342_1_, EntityAnimal p_192342_2_, EntityAnimal p_192342_3_, EntityAgeable p_192342_4_) {
         List<ICriterionTrigger.Listener<BredAnimalsTrigger.Instance>> list = null;

         for(ICriterionTrigger.Listener<BredAnimalsTrigger.Instance> listener : this.field_192345_b) {
            if (((BredAnimalsTrigger.Instance)listener.func_192158_a()).func_192246_a(p_192342_1_, p_192342_2_, p_192342_3_, p_192342_4_)) {
               if (list == null) {
                  list = Lists.<ICriterionTrigger.Listener<BredAnimalsTrigger.Instance>>newArrayList();
               }

               list.add(listener);
            }
         }

         if (list != null) {
            for(ICriterionTrigger.Listener<BredAnimalsTrigger.Instance> listener1 : list) {
               listener1.func_192159_a(this.field_192344_a);
            }
         }

      }
   }
}
