package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.JsonUtils;

public class NBTPredicate {
   public static final NBTPredicate field_193479_a = new NBTPredicate((NBTTagCompound)null);
   @Nullable
   private final NBTTagCompound field_193480_b;

   public NBTPredicate(@Nullable NBTTagCompound p_i47536_1_) {
      this.field_193480_b = p_i47536_1_;
   }

   public boolean func_193478_a(ItemStack p_193478_1_) {
      return this == field_193479_a ? true : this.func_193477_a(p_193478_1_.func_77978_p());
   }

   public boolean func_193475_a(Entity p_193475_1_) {
      return this == field_193479_a ? true : this.func_193477_a(CommandBase.func_184887_a(p_193475_1_));
   }

   public boolean func_193477_a(@Nullable NBTBase p_193477_1_) {
      if (p_193477_1_ == null) {
         return this == field_193479_a;
      } else {
         return this.field_193480_b == null || NBTUtil.func_181123_a(this.field_193480_b, p_193477_1_, true);
      }
   }

   public static NBTPredicate func_193476_a(@Nullable JsonElement p_193476_0_) {
      if (p_193476_0_ != null && !p_193476_0_.isJsonNull()) {
         NBTTagCompound nbttagcompound;
         try {
            nbttagcompound = JsonToNBT.func_180713_a(JsonUtils.func_151206_a(p_193476_0_, "nbt"));
         } catch (NBTException nbtexception) {
            throw new JsonSyntaxException("Invalid nbt tag: " + nbtexception.getMessage());
         }

         return new NBTPredicate(nbttagcompound);
      } else {
         return field_193479_a;
      }
   }
}
