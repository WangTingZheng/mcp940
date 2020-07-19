package net.minecraft.util.datafix.walkers;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.IDataFixer;

public class ItemStackData extends Filtered {
   private final String[] field_188274_a;

   public ItemStackData(Class<?> p_i47311_1_, String... p_i47311_2_) {
      super(p_i47311_1_);
      this.field_188274_a = p_i47311_2_;
   }

   NBTTagCompound func_188271_b(IDataFixer p_188271_1_, NBTTagCompound p_188271_2_, int p_188271_3_) {
      for(String s : this.field_188274_a) {
         p_188271_2_ = DataFixesManager.func_188277_a(p_188271_1_, p_188271_2_, p_188271_3_, s);
      }

      return p_188271_2_;
   }
}
