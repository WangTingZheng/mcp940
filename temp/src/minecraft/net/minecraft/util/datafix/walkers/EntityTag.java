package net.minecraft.util.datafix.walkers;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityTag implements IDataWalker {
   private static final Logger field_188270_a = LogManager.getLogger();

   public NBTTagCompound func_188266_a(IDataFixer p_188266_1_, NBTTagCompound p_188266_2_, int p_188266_3_) {
      NBTTagCompound nbttagcompound = p_188266_2_.func_74775_l("tag");
      if (nbttagcompound.func_150297_b("EntityTag", 10)) {
         NBTTagCompound nbttagcompound1 = nbttagcompound.func_74775_l("EntityTag");
         String s = p_188266_2_.func_74779_i("id");
         String s1;
         if ("minecraft:armor_stand".equals(s)) {
            s1 = p_188266_3_ < 515 ? "ArmorStand" : "minecraft:armor_stand";
         } else {
            if (!"minecraft:spawn_egg".equals(s)) {
               return p_188266_2_;
            }

            s1 = nbttagcompound1.func_74779_i("id");
         }

         boolean flag;
         if (s1 == null) {
            field_188270_a.warn("Unable to resolve Entity for ItemInstance: {}", (Object)s);
            flag = false;
         } else {
            flag = !nbttagcompound1.func_150297_b("id", 8);
            nbttagcompound1.func_74778_a("id", s1);
         }

         p_188266_1_.func_188251_a(FixTypes.ENTITY, nbttagcompound1, p_188266_3_);
         if (flag) {
            nbttagcompound1.func_82580_o("id");
         }
      }

      return p_188266_2_;
   }
}
