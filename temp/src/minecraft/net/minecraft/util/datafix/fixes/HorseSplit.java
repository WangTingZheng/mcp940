package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class HorseSplit implements IFixableData {
   public int func_188216_a() {
      return 703;
   }

   public NBTTagCompound func_188217_a(NBTTagCompound p_188217_1_) {
      if ("EntityHorse".equals(p_188217_1_.func_74779_i("id"))) {
         int i = p_188217_1_.func_74762_e("Type");
         switch(i) {
         case 0:
         default:
            p_188217_1_.func_74778_a("id", "Horse");
            break;
         case 1:
            p_188217_1_.func_74778_a("id", "Donkey");
            break;
         case 2:
            p_188217_1_.func_74778_a("id", "Mule");
            break;
         case 3:
            p_188217_1_.func_74778_a("id", "ZombieHorse");
            break;
         case 4:
            p_188217_1_.func_74778_a("id", "SkeletonHorse");
         }

         p_188217_1_.func_82580_o("Type");
      }

      return p_188217_1_;
   }
}
