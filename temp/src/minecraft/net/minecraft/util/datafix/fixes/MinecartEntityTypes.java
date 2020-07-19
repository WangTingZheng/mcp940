package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class MinecartEntityTypes implements IFixableData {
   private static final List<String> field_188222_a = Lists.newArrayList("MinecartRideable", "MinecartChest", "MinecartFurnace", "MinecartTNT", "MinecartSpawner", "MinecartHopper", "MinecartCommandBlock");

   public int func_188216_a() {
      return 106;
   }

   public NBTTagCompound func_188217_a(NBTTagCompound p_188217_1_) {
      if ("Minecart".equals(p_188217_1_.func_74779_i("id"))) {
         String s = "MinecartRideable";
         int i = p_188217_1_.func_74762_e("Type");
         if (i > 0 && i < field_188222_a.size()) {
            s = field_188222_a.get(i);
         }

         p_188217_1_.func_74778_a("id", s);
         p_188217_1_.func_82580_o("Type");
      }

      return p_188217_1_;
   }
}
