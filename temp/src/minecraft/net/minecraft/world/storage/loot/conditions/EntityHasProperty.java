package net.minecraft.world.storage.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.properties.EntityProperty;
import net.minecraft.world.storage.loot.properties.EntityPropertyManager;

public class EntityHasProperty implements LootCondition {
   private final EntityProperty[] field_186623_a;
   private final LootContext.EntityTarget field_186624_b;

   public EntityHasProperty(EntityProperty[] p_i46617_1_, LootContext.EntityTarget p_i46617_2_) {
      this.field_186623_a = p_i46617_1_;
      this.field_186624_b = p_i46617_2_;
   }

   public boolean func_186618_a(Random p_186618_1_, LootContext p_186618_2_) {
      Entity entity = p_186618_2_.func_186494_a(this.field_186624_b);
      if (entity == null) {
         return false;
      } else {
         for(EntityProperty entityproperty : this.field_186623_a) {
            if (!entityproperty.func_186657_a(p_186618_1_, entity)) {
               return false;
            }
         }

         return true;
      }
   }

   public static class Serializer extends LootCondition.Serializer<EntityHasProperty> {
      protected Serializer() {
         super(new ResourceLocation("entity_properties"), EntityHasProperty.class);
      }

      public void func_186605_a(JsonObject p_186605_1_, EntityHasProperty p_186605_2_, JsonSerializationContext p_186605_3_) {
         JsonObject jsonobject = new JsonObject();

         for(EntityProperty entityproperty : p_186605_2_.field_186623_a) {
            EntityProperty.Serializer<EntityProperty> serializer = EntityPropertyManager.<EntityProperty>func_186645_a(entityproperty);
            jsonobject.add(serializer.func_186649_a().toString(), serializer.func_186650_a(entityproperty, p_186605_3_));
         }

         p_186605_1_.add("properties", jsonobject);
         p_186605_1_.add("entity", p_186605_3_.serialize(p_186605_2_.field_186624_b));
      }

      public EntityHasProperty func_186603_b(JsonObject p_186603_1_, JsonDeserializationContext p_186603_2_) {
         Set<Entry<String, JsonElement>> set = JsonUtils.func_152754_s(p_186603_1_, "properties").entrySet();
         EntityProperty[] aentityproperty = new EntityProperty[set.size()];
         int i = 0;

         for(Entry<String, JsonElement> entry : set) {
            aentityproperty[i++] = EntityPropertyManager.func_186646_a(new ResourceLocation(entry.getKey())).func_186652_a(entry.getValue(), p_186603_2_);
         }

         return new EntityHasProperty(aentityproperty, (LootContext.EntityTarget)JsonUtils.func_188174_a(p_186603_1_, "entity", p_186603_2_, LootContext.EntityTarget.class));
      }
   }
}
