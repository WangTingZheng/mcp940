package net.minecraft.block.state.pattern;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.state.BlockWorldState;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class FactoryBlockPattern {
   private static final Joiner field_177667_a = Joiner.on(",");
   private final List<String[]> field_177665_b = Lists.<String[]>newArrayList();
   private final Map<Character, Predicate<BlockWorldState>> field_177666_c = Maps.<Character, Predicate<BlockWorldState>>newHashMap();
   private int field_177663_d;
   private int field_177664_e;

   private FactoryBlockPattern() {
      this.field_177666_c.put(Character.valueOf(' '), Predicates.alwaysTrue());
   }

   public FactoryBlockPattern func_177659_a(String... p_177659_1_) {
      if (!ArrayUtils.isEmpty((Object[])p_177659_1_) && !StringUtils.isEmpty(p_177659_1_[0])) {
         if (this.field_177665_b.isEmpty()) {
            this.field_177663_d = p_177659_1_.length;
            this.field_177664_e = p_177659_1_[0].length();
         }

         if (p_177659_1_.length != this.field_177663_d) {
            throw new IllegalArgumentException("Expected aisle with height of " + this.field_177663_d + ", but was given one with a height of " + p_177659_1_.length + ")");
         } else {
            for(String s : p_177659_1_) {
               if (s.length() != this.field_177664_e) {
                  throw new IllegalArgumentException("Not all rows in the given aisle are the correct width (expected " + this.field_177664_e + ", found one with " + s.length() + ")");
               }

               for(char c0 : s.toCharArray()) {
                  if (!this.field_177666_c.containsKey(Character.valueOf(c0))) {
                     this.field_177666_c.put(Character.valueOf(c0), (Object)null);
                  }
               }
            }

            this.field_177665_b.add(p_177659_1_);
            return this;
         }
      } else {
         throw new IllegalArgumentException("Empty pattern for aisle");
      }
   }

   public static FactoryBlockPattern func_177660_a() {
      return new FactoryBlockPattern();
   }

   public FactoryBlockPattern func_177662_a(char p_177662_1_, Predicate<BlockWorldState> p_177662_2_) {
      this.field_177666_c.put(Character.valueOf(p_177662_1_), p_177662_2_);
      return this;
   }

   public BlockPattern func_177661_b() {
      return new BlockPattern(this.func_177658_c());
   }

   private Predicate<BlockWorldState>[][][] func_177658_c() {
      this.func_177657_d();
      Predicate<BlockWorldState>[][][] predicate = (Predicate[][][])((Predicate[][][])Array.newInstance(Predicate.class, this.field_177665_b.size(), this.field_177663_d, this.field_177664_e));

      for(int i = 0; i < this.field_177665_b.size(); ++i) {
         for(int j = 0; j < this.field_177663_d; ++j) {
            for(int k = 0; k < this.field_177664_e; ++k) {
               predicate[i][j][k] = this.field_177666_c.get(Character.valueOf(((String[])this.field_177665_b.get(i))[j].charAt(k)));
            }
         }
      }

      return predicate;
   }

   private void func_177657_d() {
      List<Character> list = Lists.<Character>newArrayList();

      for(Entry<Character, Predicate<BlockWorldState>> entry : this.field_177666_c.entrySet()) {
         if (entry.getValue() == null) {
            list.add(entry.getKey());
         }
      }

      if (!list.isEmpty()) {
         throw new IllegalStateException("Predicates for character(s) " + field_177667_a.join(list) + " are missing");
      }
   }
}
