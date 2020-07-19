package net.minecraft.world.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class SetNBT extends LootFunction {
   private final NBTTagCompound field_186570_a;

   public SetNBT(LootCondition[] p_i46620_1_, NBTTagCompound p_i46620_2_) {
      super(p_i46620_1_);
      this.field_186570_a = p_i46620_2_;
   }

   public ItemStack func_186553_a(ItemStack p_186553_1_, Random p_186553_2_, LootContext p_186553_3_) {
      NBTTagCompound nbttagcompound = p_186553_1_.func_77978_p();
      if (nbttagcompound == null) {
         nbttagcompound = this.field_186570_a.func_74737_b();
      } else {
         nbttagcompound.func_179237_a(this.field_186570_a);
      }

      p_186553_1_.func_77982_d(nbttagcompound);
      return p_186553_1_;
   }

   public static class Serializer extends LootFunction.Serializer<SetNBT> {
      public Serializer() {
         super(new ResourceLocation("set_nbt"), SetNBT.class);
      }

      public void func_186532_a(JsonObject p_186532_1_, SetNBT p_186532_2_, JsonSerializationContext p_186532_3_) {
         p_186532_1_.addProperty("tag", p_186532_2_.field_186570_a.toString());
      }

      public SetNBT func_186530_b(JsonObject p_186530_1_, JsonDeserializationContext p_186530_2_, LootCondition[] p_186530_3_) {
         try {
            NBTTagCompound nbttagcompound = JsonToNBT.func_180713_a(JsonUtils.func_151200_h(p_186530_1_, "tag"));
            return new SetNBT(p_186530_3_, nbttagcompound);
         } catch (NBTException nbtexception) {
            throw new JsonSyntaxException(nbtexception);
         }
      }
   }
}
