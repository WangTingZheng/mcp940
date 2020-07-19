package net.minecraft.inventory;

import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;

public class ItemStackHelper {
   public static ItemStack func_188382_a(List<ItemStack> p_188382_0_, int p_188382_1_, int p_188382_2_) {
      return p_188382_1_ >= 0 && p_188382_1_ < p_188382_0_.size() && !((ItemStack)p_188382_0_.get(p_188382_1_)).func_190926_b() && p_188382_2_ > 0 ? ((ItemStack)p_188382_0_.get(p_188382_1_)).func_77979_a(p_188382_2_) : ItemStack.field_190927_a;
   }

   public static ItemStack func_188383_a(List<ItemStack> p_188383_0_, int p_188383_1_) {
      return p_188383_1_ >= 0 && p_188383_1_ < p_188383_0_.size() ? (ItemStack)p_188383_0_.set(p_188383_1_, ItemStack.field_190927_a) : ItemStack.field_190927_a;
   }

   public static NBTTagCompound func_191282_a(NBTTagCompound p_191282_0_, NonNullList<ItemStack> p_191282_1_) {
      return func_191281_a(p_191282_0_, p_191282_1_, true);
   }

   public static NBTTagCompound func_191281_a(NBTTagCompound p_191281_0_, NonNullList<ItemStack> p_191281_1_, boolean p_191281_2_) {
      NBTTagList nbttaglist = new NBTTagList();

      for(int i = 0; i < p_191281_1_.size(); ++i) {
         ItemStack itemstack = p_191281_1_.get(i);
         if (!itemstack.func_190926_b()) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.func_74774_a("Slot", (byte)i);
            itemstack.func_77955_b(nbttagcompound);
            nbttaglist.func_74742_a(nbttagcompound);
         }
      }

      if (!nbttaglist.func_82582_d() || p_191281_2_) {
         p_191281_0_.func_74782_a("Items", nbttaglist);
      }

      return p_191281_0_;
   }

   public static void func_191283_b(NBTTagCompound p_191283_0_, NonNullList<ItemStack> p_191283_1_) {
      NBTTagList nbttaglist = p_191283_0_.func_150295_c("Items", 10);

      for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = nbttaglist.func_150305_b(i);
         int j = nbttagcompound.func_74771_c("Slot") & 255;
         if (j >= 0 && j < p_191283_1_.size()) {
            p_191283_1_.set(j, new ItemStack(nbttagcompound));
         }
      }

   }
}
