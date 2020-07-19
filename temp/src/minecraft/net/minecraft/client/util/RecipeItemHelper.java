package net.minecraft.client.util;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntAVLTreeSet;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.BitSet;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;

public class RecipeItemHelper {
   public final Int2IntMap field_194124_a = new Int2IntOpenHashMap();

   public void func_194112_a(ItemStack p_194112_1_) {
      if (!p_194112_1_.func_190926_b() && !p_194112_1_.func_77951_h() && !p_194112_1_.func_77948_v() && !p_194112_1_.func_82837_s()) {
         int i = func_194113_b(p_194112_1_);
         int j = p_194112_1_.func_190916_E();
         this.func_194117_b(i, j);
      }

   }

   public static int func_194113_b(ItemStack p_194113_0_) {
      Item item = p_194113_0_.func_77973_b();
      int i = item.func_77614_k() ? p_194113_0_.func_77960_j() : 0;
      return Item.field_150901_e.func_148757_b(item) << 16 | i & '\uffff';
   }

   public boolean func_194120_a(int p_194120_1_) {
      return this.field_194124_a.get(p_194120_1_) > 0;
   }

   public int func_194122_a(int p_194122_1_, int p_194122_2_) {
      int i = this.field_194124_a.get(p_194122_1_);
      if (i >= p_194122_2_) {
         this.field_194124_a.put(p_194122_1_, i - p_194122_2_);
         return p_194122_1_;
      } else {
         return 0;
      }
   }

   private void func_194117_b(int p_194117_1_, int p_194117_2_) {
      this.field_194124_a.put(p_194117_1_, this.field_194124_a.get(p_194117_1_) + p_194117_2_);
   }

   public boolean func_194116_a(IRecipe p_194116_1_, @Nullable IntList p_194116_2_) {
      return this.func_194118_a(p_194116_1_, p_194116_2_, 1);
   }

   public boolean func_194118_a(IRecipe p_194118_1_, @Nullable IntList p_194118_2_, int p_194118_3_) {
      return (new RecipeItemHelper.RecipePicker(p_194118_1_)).func_194092_a(p_194118_3_, p_194118_2_);
   }

   public int func_194114_b(IRecipe p_194114_1_, @Nullable IntList p_194114_2_) {
      return this.func_194121_a(p_194114_1_, Integer.MAX_VALUE, p_194114_2_);
   }

   public int func_194121_a(IRecipe p_194121_1_, int p_194121_2_, @Nullable IntList p_194121_3_) {
      return (new RecipeItemHelper.RecipePicker(p_194121_1_)).func_194102_b(p_194121_2_, p_194121_3_);
   }

   public static ItemStack func_194115_b(int p_194115_0_) {
      return p_194115_0_ == 0 ? ItemStack.field_190927_a : new ItemStack(Item.func_150899_d(p_194115_0_ >> 16 & '\uffff'), 1, p_194115_0_ & '\uffff');
   }

   public void func_194119_a() {
      this.field_194124_a.clear();
   }

   class RecipePicker {
      private final IRecipe field_194105_b;
      private final List<Ingredient> field_194106_c = Lists.<Ingredient>newArrayList();
      private final int field_194107_d;
      private final int[] field_194108_e;
      private final int field_194109_f;
      private final BitSet field_194110_g;
      private IntList field_194111_h = new IntArrayList();

      public RecipePicker(IRecipe p_i47608_2_) {
         this.field_194105_b = p_i47608_2_;
         this.field_194106_c.addAll(p_i47608_2_.func_192400_c());
         this.field_194106_c.removeIf((p_194103_0_) -> {
            return p_194103_0_ == Ingredient.field_193370_a;
         });
         this.field_194107_d = this.field_194106_c.size();
         this.field_194108_e = this.func_194097_a();
         this.field_194109_f = this.field_194108_e.length;
         this.field_194110_g = new BitSet(this.field_194107_d + this.field_194109_f + this.field_194107_d + this.field_194107_d * this.field_194109_f);

         for(int i = 0; i < this.field_194106_c.size(); ++i) {
            IntList intlist = ((Ingredient)this.field_194106_c.get(i)).func_194139_b();

            for(int j = 0; j < this.field_194109_f; ++j) {
               if (intlist.contains(this.field_194108_e[j])) {
                  this.field_194110_g.set(this.func_194095_d(true, j, i));
               }
            }
         }

      }

      public boolean func_194092_a(int p_194092_1_, @Nullable IntList p_194092_2_) {
         if (p_194092_1_ <= 0) {
            return true;
         } else {
            int k;
            for(k = 0; this.func_194098_a(p_194092_1_); ++k) {
               RecipeItemHelper.this.func_194122_a(this.field_194108_e[this.field_194111_h.getInt(0)], p_194092_1_);
               int l = this.field_194111_h.size() - 1;
               this.func_194096_c(this.field_194111_h.getInt(l));

               for(int i1 = 0; i1 < l; ++i1) {
                  this.func_194089_c((i1 & 1) == 0, ((Integer)this.field_194111_h.get(i1)).intValue(), ((Integer)this.field_194111_h.get(i1 + 1)).intValue());
               }

               this.field_194111_h.clear();
               this.field_194110_g.clear(0, this.field_194107_d + this.field_194109_f);
            }

            boolean flag = k == this.field_194107_d;
            boolean flag1 = flag && p_194092_2_ != null;
            if (flag1) {
               p_194092_2_.clear();
            }

            this.field_194110_g.clear(0, this.field_194107_d + this.field_194109_f + this.field_194107_d);
            int j1 = 0;
            List<Ingredient> list = this.field_194105_b.func_192400_c();

            for(int k1 = 0; k1 < list.size(); ++k1) {
               if (flag1 && list.get(k1) == Ingredient.field_193370_a) {
                  p_194092_2_.add(0);
               } else {
                  for(int l1 = 0; l1 < this.field_194109_f; ++l1) {
                     if (this.func_194100_b(false, j1, l1)) {
                        this.func_194089_c(true, l1, j1);
                        RecipeItemHelper.this.func_194117_b(this.field_194108_e[l1], p_194092_1_);
                        if (flag1) {
                           p_194092_2_.add(this.field_194108_e[l1]);
                        }
                     }
                  }

                  ++j1;
               }
            }

            return flag;
         }
      }

      private int[] func_194097_a() {
         IntCollection intcollection = new IntAVLTreeSet();

         for(Ingredient ingredient : this.field_194106_c) {
            intcollection.addAll(ingredient.func_194139_b());
         }

         IntIterator intiterator = intcollection.iterator();

         while(intiterator.hasNext()) {
            if (!RecipeItemHelper.this.func_194120_a(intiterator.nextInt())) {
               intiterator.remove();
            }
         }

         return intcollection.toIntArray();
      }

      private boolean func_194098_a(int p_194098_1_) {
         int k = this.field_194109_f;

         for(int l = 0; l < k; ++l) {
            if (RecipeItemHelper.this.field_194124_a.get(this.field_194108_e[l]) >= p_194098_1_) {
               this.func_194088_a(false, l);

               while(!this.field_194111_h.isEmpty()) {
                  int i1 = this.field_194111_h.size();
                  boolean flag = (i1 & 1) == 1;
                  int j1 = this.field_194111_h.getInt(i1 - 1);
                  if (!flag && !this.func_194091_b(j1)) {
                     break;
                  }

                  int k1 = flag ? this.field_194107_d : k;

                  for(int l1 = 0; l1 < k1; ++l1) {
                     if (!this.func_194101_b(flag, l1) && this.func_194093_a(flag, j1, l1) && this.func_194100_b(flag, j1, l1)) {
                        this.func_194088_a(flag, l1);
                        break;
                     }
                  }

                  int i2 = this.field_194111_h.size();
                  if (i2 == i1) {
                     this.field_194111_h.removeInt(i2 - 1);
                  }
               }

               if (!this.field_194111_h.isEmpty()) {
                  return true;
               }
            }
         }

         return false;
      }

      private boolean func_194091_b(int p_194091_1_) {
         return this.field_194110_g.get(this.func_194094_d(p_194091_1_));
      }

      private void func_194096_c(int p_194096_1_) {
         this.field_194110_g.set(this.func_194094_d(p_194096_1_));
      }

      private int func_194094_d(int p_194094_1_) {
         return this.field_194107_d + this.field_194109_f + p_194094_1_;
      }

      private boolean func_194093_a(boolean p_194093_1_, int p_194093_2_, int p_194093_3_) {
         return this.field_194110_g.get(this.func_194095_d(p_194093_1_, p_194093_2_, p_194093_3_));
      }

      private boolean func_194100_b(boolean p_194100_1_, int p_194100_2_, int p_194100_3_) {
         return p_194100_1_ != this.field_194110_g.get(1 + this.func_194095_d(p_194100_1_, p_194100_2_, p_194100_3_));
      }

      private void func_194089_c(boolean p_194089_1_, int p_194089_2_, int p_194089_3_) {
         this.field_194110_g.flip(1 + this.func_194095_d(p_194089_1_, p_194089_2_, p_194089_3_));
      }

      private int func_194095_d(boolean p_194095_1_, int p_194095_2_, int p_194095_3_) {
         int k = p_194095_1_ ? p_194095_2_ * this.field_194107_d + p_194095_3_ : p_194095_3_ * this.field_194107_d + p_194095_2_;
         return this.field_194107_d + this.field_194109_f + this.field_194107_d + 2 * k;
      }

      private void func_194088_a(boolean p_194088_1_, int p_194088_2_) {
         this.field_194110_g.set(this.func_194099_c(p_194088_1_, p_194088_2_));
         this.field_194111_h.add(p_194088_2_);
      }

      private boolean func_194101_b(boolean p_194101_1_, int p_194101_2_) {
         return this.field_194110_g.get(this.func_194099_c(p_194101_1_, p_194101_2_));
      }

      private int func_194099_c(boolean p_194099_1_, int p_194099_2_) {
         return (p_194099_1_ ? 0 : this.field_194107_d) + p_194099_2_;
      }

      public int func_194102_b(int p_194102_1_, @Nullable IntList p_194102_2_) {
         int k = 0;
         int l = Math.min(p_194102_1_, this.func_194090_b()) + 1;

         while(true) {
            int i1 = (k + l) / 2;
            if (this.func_194092_a(i1, (IntList)null)) {
               if (l - k <= 1) {
                  if (i1 > 0) {
                     this.func_194092_a(i1, p_194102_2_);
                  }

                  return i1;
               }

               k = i1;
            } else {
               l = i1;
            }
         }
      }

      private int func_194090_b() {
         int k = Integer.MAX_VALUE;

         for(Ingredient ingredient : this.field_194106_c) {
            int l = 0;

            int i1;
            for(IntListIterator intlistiterator = ingredient.func_194139_b().iterator(); intlistiterator.hasNext(); l = Math.max(l, RecipeItemHelper.this.field_194124_a.get(i1))) {
               i1 = ((Integer)intlistiterator.next()).intValue();
            }

            if (k > 0) {
               k = Math.min(k, l);
            }
         }

         return k;
      }
   }
}
