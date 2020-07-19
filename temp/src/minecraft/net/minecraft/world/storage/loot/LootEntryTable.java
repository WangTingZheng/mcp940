package net.minecraft.world.storage.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Collection;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class LootEntryTable extends LootEntry {
   protected final ResourceLocation field_186371_a;

   public LootEntryTable(ResourceLocation p_i46639_1_, int p_i46639_2_, int p_i46639_3_, LootCondition[] p_i46639_4_) {
      super(p_i46639_2_, p_i46639_3_, p_i46639_4_);
      this.field_186371_a = p_i46639_1_;
   }

   public void func_186363_a(Collection<ItemStack> p_186363_1_, Random p_186363_2_, LootContext p_186363_3_) {
      LootTable loottable = p_186363_3_.func_186497_e().func_186521_a(this.field_186371_a);
      Collection<ItemStack> collection = loottable.func_186462_a(p_186363_2_, p_186363_3_);
      p_186363_1_.addAll(collection);
   }

   protected void func_186362_a(JsonObject p_186362_1_, JsonSerializationContext p_186362_2_) {
      p_186362_1_.addProperty("name", this.field_186371_a.toString());
   }

   public static LootEntryTable func_186370_a(JsonObject p_186370_0_, JsonDeserializationContext p_186370_1_, int p_186370_2_, int p_186370_3_, LootCondition[] p_186370_4_) {
      ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.func_151200_h(p_186370_0_, "name"));
      return new LootEntryTable(resourcelocation, p_186370_2_, p_186370_3_, p_186370_4_);
   }
}
