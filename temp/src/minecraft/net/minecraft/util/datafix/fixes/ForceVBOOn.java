package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class ForceVBOOn implements IFixableData {
   public int func_188216_a() {
      return 505;
   }

   public NBTTagCompound func_188217_a(NBTTagCompound p_188217_1_) {
      p_188217_1_.func_74778_a("useVbo", "true");
      return p_188217_1_;
   }
}
