package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.datafix.IFixableData;

public class BannerItemColor implements IFixableData {
   public int func_188216_a() {
      return 804;
   }

   public NBTTagCompound func_188217_a(NBTTagCompound p_188217_1_) {
      if ("minecraft:banner".equals(p_188217_1_.func_74779_i("id")) && p_188217_1_.func_150297_b("tag", 10)) {
         NBTTagCompound nbttagcompound = p_188217_1_.func_74775_l("tag");
         if (nbttagcompound.func_150297_b("BlockEntityTag", 10)) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.func_74775_l("BlockEntityTag");
            if (nbttagcompound1.func_150297_b("Base", 99)) {
               p_188217_1_.func_74777_a("Damage", (short)(nbttagcompound1.func_74765_d("Base") & 15));
               if (nbttagcompound.func_150297_b("display", 10)) {
                  NBTTagCompound nbttagcompound2 = nbttagcompound.func_74775_l("display");
                  if (nbttagcompound2.func_150297_b("Lore", 9)) {
                     NBTTagList nbttaglist = nbttagcompound2.func_150295_c("Lore", 8);
                     if (nbttaglist.func_74745_c() == 1 && "(+NBT)".equals(nbttaglist.func_150307_f(0))) {
                        return p_188217_1_;
                     }
                  }
               }

               nbttagcompound1.func_82580_o("Base");
               if (nbttagcompound1.func_82582_d()) {
                  nbttagcompound.func_82580_o("BlockEntityTag");
               }

               if (nbttagcompound.func_82582_d()) {
                  p_188217_1_.func_82580_o("tag");
               }
            }
         }
      }

      return p_188217_1_;
   }
}
