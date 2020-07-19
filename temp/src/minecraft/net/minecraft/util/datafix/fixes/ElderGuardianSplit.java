package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class ElderGuardianSplit implements IFixableData {
   public int func_188216_a() {
      return 700;
   }

   public NBTTagCompound func_188217_a(NBTTagCompound p_188217_1_) {
      if ("Guardian".equals(p_188217_1_.func_74779_i("id"))) {
         if (p_188217_1_.func_74767_n("Elder")) {
            p_188217_1_.func_74778_a("id", "ElderGuardian");
         }

         p_188217_1_.func_82580_o("Elder");
      }

      return p_188217_1_;
   }
}
