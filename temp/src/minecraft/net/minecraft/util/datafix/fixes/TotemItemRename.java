package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class TotemItemRename implements IFixableData {
   public int func_188216_a() {
      return 820;
   }

   public NBTTagCompound func_188217_a(NBTTagCompound p_188217_1_) {
      if ("minecraft:totem".equals(p_188217_1_.func_74779_i("id"))) {
         p_188217_1_.func_74778_a("id", "minecraft:totem_of_undying");
      }

      return p_188217_1_;
   }
}
