package net.minecraft.util.datafix.fixes;

import java.util.Locale;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class OptionsLowerCaseLanguage implements IFixableData {
   public int func_188216_a() {
      return 816;
   }

   public NBTTagCompound func_188217_a(NBTTagCompound p_188217_1_) {
      if (p_188217_1_.func_150297_b("lang", 8)) {
         p_188217_1_.func_74778_a("lang", p_188217_1_.func_74779_i("lang").toLowerCase(Locale.ROOT));
      }

      return p_188217_1_;
   }
}
