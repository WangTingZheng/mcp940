package net.minecraft.util.datafix.fixes;

import java.util.UUID;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class StringToUUID implements IFixableData {
   public int func_188216_a() {
      return 108;
   }

   public NBTTagCompound func_188217_a(NBTTagCompound p_188217_1_) {
      if (p_188217_1_.func_150297_b("UUID", 8)) {
         p_188217_1_.func_186854_a("UUID", UUID.fromString(p_188217_1_.func_74779_i("UUID")));
      }

      return p_188217_1_;
   }
}
