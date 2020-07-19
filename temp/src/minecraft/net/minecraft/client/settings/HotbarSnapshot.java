package net.minecraft.client.settings;

import java.util.ArrayList;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class HotbarSnapshot extends ArrayList<ItemStack> {
   public static final int field_192835_a = InventoryPlayer.func_70451_h();

   public HotbarSnapshot() {
      this.ensureCapacity(field_192835_a);

      for(int i = 0; i < field_192835_a; ++i) {
         this.add(ItemStack.field_190927_a);
      }

   }

   public NBTTagList func_192834_a() {
      NBTTagList nbttaglist = new NBTTagList();

      for(int i = 0; i < field_192835_a; ++i) {
         nbttaglist.func_74742_a(((ItemStack)this.get(i)).func_77955_b(new NBTTagCompound()));
      }

      return nbttaglist;
   }

   public void func_192833_a(NBTTagList p_192833_1_) {
      for(int i = 0; i < field_192835_a; ++i) {
         this.set(i, new ItemStack(p_192833_1_.func_150305_b(i)));
      }

   }

   public boolean isEmpty() {
      for(int i = 0; i < field_192835_a; ++i) {
         if (!((ItemStack)this.get(i)).func_190926_b()) {
            return false;
         }
      }

      return true;
   }
}
