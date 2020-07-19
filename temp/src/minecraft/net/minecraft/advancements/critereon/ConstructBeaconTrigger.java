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
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.ResourceLocation;

public class ConstructBeaconTrigger implements ICriterionTrigger<ConstructBeaconTrigger.Instance> {
   private static final ResourceLocation field_192181_a = new ResourceLocation("construct_beacon");
   private final Map<PlayerAdvancements, ConstructBeaconTrigger.Listeners> field_192182_b = Maps.<PlayerAdvancements, ConstructBeaconTrigger.Listeners>newHashMap();

   public ResourceLocation func_192163_a() {
      return field_192181_a;
   }

   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance> p_192165_2_) {
      ConstructBeaconTrigger.Listeners constructbeacontrigger$listeners = this.field_192182_b.get(p_192165_1_);
      if (constructbeacontrigger$listeners == null) {
         constructbeacontrigger$listeners = new ConstructBeaconTrigger.Listeners(p_192165_1_);
         this.field_192182_b.put(p_192165_1_, constructbeacontrigger$listeners);
      }

      constructbeacontrigger$listeners.func_192355_a(p_192165_2_);
   }

   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance> p_192164_2_) {
      ConstructBeaconTrigger.Listeners constructbeacontrigger$listeners = this.field_192182_b.get(p_192164_1_);
      if (constructbeacontrigger$listeners != null) {
         constructbeacontrigger$listeners.func_192353_b(p_192164_2_);
         if (constructbeacontrigger$listeners.func_192354_a()) {
            this.field_192182_b.remove(p_192164_1_);
         }
      }

   }

   public void func_192167_a(PlayerAdvancements p_192167_1_) {
      this.field_192182_b.remove(p_192167_1_);
   }

   public ConstructBeaconTrigger.Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
      MinMaxBounds minmaxbounds = MinMaxBounds.func_192515_a(p_192166_1_.get("level"));
      return new ConstructBeaconTrigger.Instance(minmaxbounds);
   }

   public void func_192180_a(EntityPlayerMP p_192180_1_, TileEntityBeacon p_192180_2_) {
      ConstructBeaconTrigger.Listeners constructbeacontrigger$listeners = this.field_192182_b.get(p_192180_1_.func_192039_O());
      if (constructbeacontrigger$listeners != null) {
         constructbeacontrigger$listeners.func_192352_a(p_192180_2_);
      }

   }

   public static class Instance extends AbstractCriterionInstance {
      private final MinMaxBounds field_192253_a;

      public Instance(MinMaxBounds p_i47373_1_) {
         super(ConstructBeaconTrigger.field_192181_a);
         this.field_192253_a = p_i47373_1_;
      }

      public boolean func_192252_a(TileEntityBeacon p_192252_1_) {
         return this.field_192253_a.func_192514_a((float)p_192252_1_.func_191979_s());
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192356_a;
      private final Set<ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance>> field_192357_b = Sets.<ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance>>newHashSet();

      public Listeners(PlayerAdvancements p_i47374_1_) {
         this.field_192356_a = p_i47374_1_;
      }

      public boolean func_192354_a() {
         return this.field_192357_b.isEmpty();
      }

      public void func_192355_a(ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance> p_192355_1_) {
         this.field_192357_b.add(p_192355_1_);
      }

      public void func_192353_b(ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance> p_192353_1_) {
         this.field_192357_b.remove(p_192353_1_);
      }

      public void func_192352_a(TileEntityBeacon p_192352_1_) {
         List<ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance>> list = null;

         for(ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance> listener : this.field_192357_b) {
            if (((ConstructBeaconTrigger.Instance)listener.func_192158_a()).func_192252_a(p_192352_1_)) {
               if (list == null) {
                  list = Lists.<ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance>>newArrayList();
               }

               list.add(listener);
            }
         }

         if (list != null) {
            for(ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance> listener1 : list) {
               listener1.func_192159_a(this.field_192356_a);
            }
         }

      }
   }
}
