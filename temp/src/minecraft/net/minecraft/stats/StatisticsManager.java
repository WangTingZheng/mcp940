package net.minecraft.stats;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.TupleIntJsonSerializable;

public class StatisticsManager {
   protected final Map<StatBase, TupleIntJsonSerializable> field_150875_a = Maps.<StatBase, TupleIntJsonSerializable>newConcurrentMap();

   public void func_150871_b(EntityPlayer p_150871_1_, StatBase p_150871_2_, int p_150871_3_) {
      this.func_150873_a(p_150871_1_, p_150871_2_, this.func_77444_a(p_150871_2_) + p_150871_3_);
   }

   public void func_150873_a(EntityPlayer p_150873_1_, StatBase p_150873_2_, int p_150873_3_) {
      TupleIntJsonSerializable tupleintjsonserializable = this.field_150875_a.get(p_150873_2_);
      if (tupleintjsonserializable == null) {
         tupleintjsonserializable = new TupleIntJsonSerializable();
         this.field_150875_a.put(p_150873_2_, tupleintjsonserializable);
      }

      tupleintjsonserializable.func_151188_a(p_150873_3_);
   }

   public int func_77444_a(StatBase p_77444_1_) {
      TupleIntJsonSerializable tupleintjsonserializable = this.field_150875_a.get(p_77444_1_);
      return tupleintjsonserializable == null ? 0 : tupleintjsonserializable.func_151189_a();
   }
}
