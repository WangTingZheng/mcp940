package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class ZombieSplit implements IFixableData {
   public int func_188216_a() {
      return 702;
   }

   public NBTTagCompound func_188217_a(NBTTagCompound p_188217_1_) {
      if ("Zombie".equals(p_188217_1_.func_74779_i("id"))) {
         int i = p_188217_1_.func_74762_e("ZombieType");
         switch(i) {
         case 0:
         default:
            break;
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
            p_188217_1_.func_74778_a("id", "ZombieVillager");
            p_188217_1_.func_74768_a("Profession", i - 1);
            break;
         case 6:
            p_188217_1_.func_74778_a("id", "Husk");
         }

         p_188217_1_.func_82580_o("ZombieType");
      }

      return p_188217_1_;
   }
}
