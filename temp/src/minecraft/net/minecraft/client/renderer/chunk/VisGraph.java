package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Queues;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Queue;
import java.util.Set;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IntegerCache;
import net.minecraft.util.math.BlockPos;

public class VisGraph {
   private static final int field_178616_a = (int)Math.pow(16.0D, 0.0D);
   private static final int field_178614_b = (int)Math.pow(16.0D, 1.0D);
   private static final int field_178615_c = (int)Math.pow(16.0D, 2.0D);
   private final BitSet field_178612_d = new BitSet(4096);
   private static final int[] field_178613_e = new int[1352];
   private int field_178611_f = 4096;

   public void func_178606_a(BlockPos p_178606_1_) {
      this.field_178612_d.set(func_178608_c(p_178606_1_), true);
      --this.field_178611_f;
   }

   private static int func_178608_c(BlockPos p_178608_0_) {
      return func_178605_a(p_178608_0_.func_177958_n() & 15, p_178608_0_.func_177956_o() & 15, p_178608_0_.func_177952_p() & 15);
   }

   private static int func_178605_a(int p_178605_0_, int p_178605_1_, int p_178605_2_) {
      return p_178605_0_ << 0 | p_178605_1_ << 8 | p_178605_2_ << 4;
   }

   public SetVisibility func_178607_a() {
      SetVisibility setvisibility = new SetVisibility();
      if (4096 - this.field_178611_f < 256) {
         setvisibility.func_178618_a(true);
      } else if (this.field_178611_f == 0) {
         setvisibility.func_178618_a(false);
      } else {
         for(int i : field_178613_e) {
            if (!this.field_178612_d.get(i)) {
               setvisibility.func_178620_a(this.func_178604_a(i));
            }
         }
      }

      return setvisibility;
   }

   public Set<EnumFacing> func_178609_b(BlockPos p_178609_1_) {
      return this.func_178604_a(func_178608_c(p_178609_1_));
   }

   private Set<EnumFacing> func_178604_a(int p_178604_1_) {
      Set<EnumFacing> set = EnumSet.<EnumFacing>noneOf(EnumFacing.class);
      Queue<Integer> queue = Queues.<Integer>newArrayDeque();
      queue.add(IntegerCache.func_181756_a(p_178604_1_));
      this.field_178612_d.set(p_178604_1_, true);

      while(!queue.isEmpty()) {
         int i = ((Integer)queue.poll()).intValue();
         this.func_178610_a(i, set);

         for(EnumFacing enumfacing : EnumFacing.values()) {
            int j = this.func_178603_a(i, enumfacing);
            if (j >= 0 && !this.field_178612_d.get(j)) {
               this.field_178612_d.set(j, true);
               queue.add(IntegerCache.func_181756_a(j));
            }
         }
      }

      return set;
   }

   private void func_178610_a(int p_178610_1_, Set<EnumFacing> p_178610_2_) {
      int i = p_178610_1_ >> 0 & 15;
      if (i == 0) {
         p_178610_2_.add(EnumFacing.WEST);
      } else if (i == 15) {
         p_178610_2_.add(EnumFacing.EAST);
      }

      int j = p_178610_1_ >> 8 & 15;
      if (j == 0) {
         p_178610_2_.add(EnumFacing.DOWN);
      } else if (j == 15) {
         p_178610_2_.add(EnumFacing.UP);
      }

      int k = p_178610_1_ >> 4 & 15;
      if (k == 0) {
         p_178610_2_.add(EnumFacing.NORTH);
      } else if (k == 15) {
         p_178610_2_.add(EnumFacing.SOUTH);
      }

   }

   private int func_178603_a(int p_178603_1_, EnumFacing p_178603_2_) {
      switch(p_178603_2_) {
      case DOWN:
         if ((p_178603_1_ >> 8 & 15) == 0) {
            return -1;
         }

         return p_178603_1_ - field_178615_c;
      case UP:
         if ((p_178603_1_ >> 8 & 15) == 15) {
            return -1;
         }

         return p_178603_1_ + field_178615_c;
      case NORTH:
         if ((p_178603_1_ >> 4 & 15) == 0) {
            return -1;
         }

         return p_178603_1_ - field_178614_b;
      case SOUTH:
         if ((p_178603_1_ >> 4 & 15) == 15) {
            return -1;
         }

         return p_178603_1_ + field_178614_b;
      case WEST:
         if ((p_178603_1_ >> 0 & 15) == 0) {
            return -1;
         }

         return p_178603_1_ - field_178616_a;
      case EAST:
         if ((p_178603_1_ >> 0 & 15) == 15) {
            return -1;
         }

         return p_178603_1_ + field_178616_a;
      default:
         return -1;
      }
   }

   static {
      int i = 0;
      int j = 15;
      int k = 0;

      for(int l = 0; l < 16; ++l) {
         for(int i1 = 0; i1 < 16; ++i1) {
            for(int j1 = 0; j1 < 16; ++j1) {
               if (l == 0 || l == 15 || i1 == 0 || i1 == 15 || j1 == 0 || j1 == 15) {
                  field_178613_e[k++] = func_178605_a(l, i1, j1);
               }
            }
         }
      }

   }
}
