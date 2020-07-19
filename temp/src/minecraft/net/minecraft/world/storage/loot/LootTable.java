package net.minecraft.world.storage.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTable {
   private static final Logger field_186465_b = LogManager.getLogger();
   public static final LootTable field_186464_a = new LootTable(new LootPool[0]);
   private final LootPool[] field_186466_c;

   public LootTable(LootPool[] p_i46641_1_) {
      this.field_186466_c = p_i46641_1_;
   }

   public List<ItemStack> func_186462_a(Random p_186462_1_, LootContext p_186462_2_) {
      List<ItemStack> list = Lists.<ItemStack>newArrayList();
      if (p_186462_2_.func_186496_a(this)) {
         for(LootPool lootpool : this.field_186466_c) {
            lootpool.func_186449_b(list, p_186462_1_, p_186462_2_);
         }

         p_186462_2_.func_186490_b(this);
      } else {
         field_186465_b.warn("Detected infinite loop in loot tables");
      }

      return list;
   }

   public void func_186460_a(IInventory p_186460_1_, Random p_186460_2_, LootContext p_186460_3_) {
      List<ItemStack> list = this.func_186462_a(p_186460_2_, p_186460_3_);
      List<Integer> list1 = this.func_186459_a(p_186460_1_, p_186460_2_);
      this.func_186463_a(list, list1.size(), p_186460_2_);

      for(ItemStack itemstack : list) {
         if (list1.isEmpty()) {
            field_186465_b.warn("Tried to over-fill a container");
            return;
         }

         if (itemstack.func_190926_b()) {
            p_186460_1_.func_70299_a(((Integer)list1.remove(list1.size() - 1)).intValue(), ItemStack.field_190927_a);
         } else {
            p_186460_1_.func_70299_a(((Integer)list1.remove(list1.size() - 1)).intValue(), itemstack);
         }
      }

   }

   private void func_186463_a(List<ItemStack> p_186463_1_, int p_186463_2_, Random p_186463_3_) {
      List<ItemStack> list = Lists.<ItemStack>newArrayList();
      Iterator<ItemStack> iterator = p_186463_1_.iterator();

      while(iterator.hasNext()) {
         ItemStack itemstack = iterator.next();
         if (itemstack.func_190926_b()) {
            iterator.remove();
         } else if (itemstack.func_190916_E() > 1) {
            list.add(itemstack);
            iterator.remove();
         }
      }

      p_186463_2_ = p_186463_2_ - p_186463_1_.size();

      while(p_186463_2_ > 0 && !list.isEmpty()) {
         ItemStack itemstack2 = list.remove(MathHelper.func_76136_a(p_186463_3_, 0, list.size() - 1));
         int i = MathHelper.func_76136_a(p_186463_3_, 1, itemstack2.func_190916_E() / 2);
         ItemStack itemstack1 = itemstack2.func_77979_a(i);
         if (itemstack2.func_190916_E() > 1 && p_186463_3_.nextBoolean()) {
            list.add(itemstack2);
         } else {
            p_186463_1_.add(itemstack2);
         }

         if (itemstack1.func_190916_E() > 1 && p_186463_3_.nextBoolean()) {
            list.add(itemstack1);
         } else {
            p_186463_1_.add(itemstack1);
         }
      }

      p_186463_1_.addAll(list);
      Collections.shuffle(p_186463_1_, p_186463_3_);
   }

   private List<Integer> func_186459_a(IInventory p_186459_1_, Random p_186459_2_) {
      List<Integer> list = Lists.<Integer>newArrayList();

      for(int i = 0; i < p_186459_1_.func_70302_i_(); ++i) {
         if (p_186459_1_.func_70301_a(i).func_190926_b()) {
            list.add(Integer.valueOf(i));
         }
      }

      Collections.shuffle(list, p_186459_2_);
      return list;
   }

   public static class Serializer implements JsonDeserializer<LootTable>, JsonSerializer<LootTable> {
      public LootTable deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         JsonObject jsonobject = JsonUtils.func_151210_l(p_deserialize_1_, "loot table");
         LootPool[] alootpool = (LootPool[])JsonUtils.func_188177_a(jsonobject, "pools", new LootPool[0], p_deserialize_3_, LootPool[].class);
         return new LootTable(alootpool);
      }

      public JsonElement serialize(LootTable p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
         JsonObject jsonobject = new JsonObject();
         jsonobject.add("pools", p_serialize_3_.serialize(p_serialize_1_.field_186466_c));
         return jsonobject;
      }
   }
}
