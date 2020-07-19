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
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class EntityHurtPlayerTrigger implements ICriterionTrigger<EntityHurtPlayerTrigger.Instance> {
   private static final ResourceLocation field_192201_a = new ResourceLocation("entity_hurt_player");
   private final Map<PlayerAdvancements, EntityHurtPlayerTrigger.Listeners> field_192202_b = Maps.<PlayerAdvancements, EntityHurtPlayerTrigger.Listeners>newHashMap();

   public ResourceLocation func_192163_a() {
      return field_192201_a;
   }

   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance> p_192165_2_) {
      EntityHurtPlayerTrigger.Listeners entityhurtplayertrigger$listeners = this.field_192202_b.get(p_192165_1_);
      if (entityhurtplayertrigger$listeners == null) {
         entityhurtplayertrigger$listeners = new EntityHurtPlayerTrigger.Listeners(p_192165_1_);
         this.field_192202_b.put(p_192165_1_, entityhurtplayertrigger$listeners);
      }

      entityhurtplayertrigger$listeners.func_192477_a(p_192165_2_);
   }

   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance> p_192164_2_) {
      EntityHurtPlayerTrigger.Listeners entityhurtplayertrigger$listeners = this.field_192202_b.get(p_192164_1_);
      if (entityhurtplayertrigger$listeners != null) {
         entityhurtplayertrigger$listeners.func_192475_b(p_192164_2_);
         if (entityhurtplayertrigger$listeners.func_192476_a()) {
            this.field_192202_b.remove(p_192164_1_);
         }
      }

   }

   public void func_192167_a(PlayerAdvancements p_192167_1_) {
      this.field_192202_b.remove(p_192167_1_);
   }

   public EntityHurtPlayerTrigger.Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
      DamagePredicate damagepredicate = DamagePredicate.func_192364_a(p_192166_1_.get("damage"));
      return new EntityHurtPlayerTrigger.Instance(damagepredicate);
   }

   public void func_192200_a(EntityPlayerMP p_192200_1_, DamageSource p_192200_2_, float p_192200_3_, float p_192200_4_, boolean p_192200_5_) {
      EntityHurtPlayerTrigger.Listeners entityhurtplayertrigger$listeners = this.field_192202_b.get(p_192200_1_.func_192039_O());
      if (entityhurtplayertrigger$listeners != null) {
         entityhurtplayertrigger$listeners.func_192478_a(p_192200_1_, p_192200_2_, p_192200_3_, p_192200_4_, p_192200_5_);
      }

   }

   public static class Instance extends AbstractCriterionInstance {
      private final DamagePredicate field_192264_a;

      public Instance(DamagePredicate p_i47438_1_) {
         super(EntityHurtPlayerTrigger.field_192201_a);
         this.field_192264_a = p_i47438_1_;
      }

      public boolean func_192263_a(EntityPlayerMP p_192263_1_, DamageSource p_192263_2_, float p_192263_3_, float p_192263_4_, boolean p_192263_5_) {
         return this.field_192264_a.func_192365_a(p_192263_1_, p_192263_2_, p_192263_3_, p_192263_4_, p_192263_5_);
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192479_a;
      private final Set<ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance>> field_192480_b = Sets.<ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance>>newHashSet();

      public Listeners(PlayerAdvancements p_i47439_1_) {
         this.field_192479_a = p_i47439_1_;
      }

      public boolean func_192476_a() {
         return this.field_192480_b.isEmpty();
      }

      public void func_192477_a(ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance> p_192477_1_) {
         this.field_192480_b.add(p_192477_1_);
      }

      public void func_192475_b(ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance> p_192475_1_) {
         this.field_192480_b.remove(p_192475_1_);
      }

      public void func_192478_a(EntityPlayerMP p_192478_1_, DamageSource p_192478_2_, float p_192478_3_, float p_192478_4_, boolean p_192478_5_) {
         List<ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance>> list = null;

         for(ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance> listener : this.field_192480_b) {
            if (((EntityHurtPlayerTrigger.Instance)listener.func_192158_a()).func_192263_a(p_192478_1_, p_192478_2_, p_192478_3_, p_192478_4_, p_192478_5_)) {
               if (list == null) {
                  list = Lists.<ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance>>newArrayList();
               }

               list.add(listener);
            }
         }

         if (list != null) {
            for(ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance> listener1 : list) {
               listener1.func_192159_a(this.field_192479_a);
            }
         }

      }
   }
}
