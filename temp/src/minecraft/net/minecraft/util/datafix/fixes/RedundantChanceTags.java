package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.datafix.IFixableData;

public class RedundantChanceTags implements IFixableData {
   public int func_188216_a() {
      return 113;
   }

   public NBTTagCompound func_188217_a(NBTTagCompound p_188217_1_) {
      if (p_188217_1_.func_150297_b("HandDropChances", 9)) {
         NBTTagList nbttaglist = p_188217_1_.func_150295_c("HandDropChances", 5);
         if (nbttaglist.func_74745_c() == 2 && nbttaglist.func_150308_e(0) == 0.0F && nbttaglist.func_150308_e(1) == 0.0F) {
            p_188217_1_.func_82580_o("HandDropChances");
         }
      }

      if (p_188217_1_.func_150297_b("ArmorDropChances", 9)) {
         NBTTagList nbttaglist1 = p_188217_1_.func_150295_c("ArmorDropChances", 5);
         if (nbttaglist1.func_74745_c() == 4 && nbttaglist1.func_150308_e(0) == 0.0F && nbttaglist1.func_150308_e(1) == 0.0F && nbttaglist1.func_150308_e(2) == 0.0F && nbttaglist1.func_150308_e(3) == 0.0F) {
            p_188217_1_.func_82580_o("ArmorDropChances");
         }
      }

      return p_188217_1_;
   }
}
