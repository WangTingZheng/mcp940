package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class PotionWater implements IFixableData {
   public int func_188216_a() {
      return 806;
   }

   public NBTTagCompound func_188217_a(NBTTagCompound p_188217_1_) {
      String s = p_188217_1_.func_74779_i("id");
      if ("minecraft:potion".equals(s) || "minecraft:splash_potion".equals(s) || "minecraft:lingering_potion".equals(s) || "minecraft:tipped_arrow".equals(s)) {
         NBTTagCompound nbttagcompound = p_188217_1_.func_74775_l("tag");
         if (!nbttagcompound.func_150297_b("Potion", 8)) {
            nbttagcompound.func_74778_a("Potion", "minecraft:water");
         }

         if (!p_188217_1_.func_150297_b("tag", 10)) {
            p_188217_1_.func_74782_a("tag", nbttagcompound);
         }
      }

      return p_188217_1_;
   }
}
