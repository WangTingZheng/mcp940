package net.minecraft.stats;

import net.minecraft.util.text.ITextComponent;

public class StatBasic extends StatBase {
   public StatBasic(String p_i45303_1_, ITextComponent p_i45303_2_, IStatType p_i45303_3_) {
      super(p_i45303_1_, p_i45303_2_, p_i45303_3_);
   }

   public StatBasic(String p_i45304_1_, ITextComponent p_i45304_2_) {
      super(p_i45304_1_, p_i45304_2_);
   }

   public StatBase func_75971_g() {
      super.func_75971_g();
      StatList.field_188094_c.add(this);
      return this;
   }
}
