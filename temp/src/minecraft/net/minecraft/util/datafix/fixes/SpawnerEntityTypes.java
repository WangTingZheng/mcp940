package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.datafix.IFixableData;

public class SpawnerEntityTypes implements IFixableData {
   public int func_188216_a() {
      return 107;
   }

   public NBTTagCompound func_188217_a(NBTTagCompound p_188217_1_) {
      if (!"MobSpawner".equals(p_188217_1_.func_74779_i("id"))) {
         return p_188217_1_;
      } else {
         if (p_188217_1_.func_150297_b("EntityId", 8)) {
            String s = p_188217_1_.func_74779_i("EntityId");
            NBTTagCompound nbttagcompound = p_188217_1_.func_74775_l("SpawnData");
            nbttagcompound.func_74778_a("id", s.isEmpty() ? "Pig" : s);
            p_188217_1_.func_74782_a("SpawnData", nbttagcompound);
            p_188217_1_.func_82580_o("EntityId");
         }

         if (p_188217_1_.func_150297_b("SpawnPotentials", 9)) {
            NBTTagList nbttaglist = p_188217_1_.func_150295_c("SpawnPotentials", 10);

            for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
               NBTTagCompound nbttagcompound1 = nbttaglist.func_150305_b(i);
               if (nbttagcompound1.func_150297_b("Type", 8)) {
                  NBTTagCompound nbttagcompound2 = nbttagcompound1.func_74775_l("Properties");
                  nbttagcompound2.func_74778_a("id", nbttagcompound1.func_74779_i("Type"));
                  nbttagcompound1.func_74782_a("Entity", nbttagcompound2);
                  nbttagcompound1.func_82580_o("Type");
                  nbttagcompound1.func_82580_o("Properties");
               }
            }
         }

         return p_188217_1_;
      }
   }
}
