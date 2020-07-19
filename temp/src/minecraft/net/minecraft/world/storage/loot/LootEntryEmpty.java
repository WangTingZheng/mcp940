package net.minecraft.world.storage.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Collection;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class LootEntryEmpty extends LootEntry {
   public LootEntryEmpty(int p_i46645_1_, int p_i46645_2_, LootCondition[] p_i46645_3_) {
      super(p_i46645_1_, p_i46645_2_, p_i46645_3_);
   }

   public void func_186363_a(Collection<ItemStack> p_186363_1_, Random p_186363_2_, LootContext p_186363_3_) {
   }

   protected void func_186362_a(JsonObject p_186362_1_, JsonSerializationContext p_186362_2_) {
   }

   public static LootEntryEmpty func_186372_a(JsonObject p_186372_0_, JsonDeserializationContext p_186372_1_, int p_186372_2_, int p_186372_3_, LootCondition[] p_186372_4_) {
      return new LootEntryEmpty(p_186372_2_, p_186372_3_, p_186372_4_);
   }
}
