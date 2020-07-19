package net.minecraft.tileentity;

import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;

public class TileEntityDropper extends TileEntityDispenser {
   public static void func_189679_b(DataFixer p_189679_0_) {
      p_189679_0_.func_188258_a(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileEntityDropper.class, new String[]{"Items"}));
   }

   public String func_70005_c_() {
      return this.func_145818_k_() ? this.field_190577_o : "container.dropper";
   }

   public String func_174875_k() {
      return "minecraft:dropper";
   }
}
