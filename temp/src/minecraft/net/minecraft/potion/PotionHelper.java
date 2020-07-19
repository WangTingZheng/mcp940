package net.minecraft.potion;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class PotionHelper {
   private static final List<PotionHelper.MixPredicate<PotionType>> field_185213_a = Lists.<PotionHelper.MixPredicate<PotionType>>newArrayList();
   private static final List<PotionHelper.MixPredicate<Item>> field_185214_b = Lists.<PotionHelper.MixPredicate<Item>>newArrayList();
   private static final List<Ingredient> field_185215_c = Lists.<Ingredient>newArrayList();
   private static final Predicate<ItemStack> field_185216_d = new Predicate<ItemStack>() {
      public boolean apply(ItemStack p_apply_1_) {
         for(Ingredient ingredient : PotionHelper.field_185215_c) {
            if (ingredient.apply(p_apply_1_)) {
               return true;
            }
         }

         return false;
      }
   };

   public static boolean func_185205_a(ItemStack p_185205_0_) {
      return func_185203_b(p_185205_0_) || func_185211_c(p_185205_0_);
   }

   protected static boolean func_185203_b(ItemStack p_185203_0_) {
      int i = 0;

      for(int j = field_185214_b.size(); i < j; ++i) {
         if ((field_185214_b.get(i)).field_185199_b.apply(p_185203_0_)) {
            return true;
         }
      }

      return false;
   }

   protected static boolean func_185211_c(ItemStack p_185211_0_) {
      int i = 0;

      for(int j = field_185213_a.size(); i < j; ++i) {
         if ((field_185213_a.get(i)).field_185199_b.apply(p_185211_0_)) {
            return true;
         }
      }

      return false;
   }

   public static boolean func_185208_a(ItemStack p_185208_0_, ItemStack p_185208_1_) {
      if (!field_185216_d.apply(p_185208_0_)) {
         return false;
      } else {
         return func_185206_b(p_185208_0_, p_185208_1_) || func_185209_c(p_185208_0_, p_185208_1_);
      }
   }

   protected static boolean func_185206_b(ItemStack p_185206_0_, ItemStack p_185206_1_) {
      Item item = p_185206_0_.func_77973_b();
      int i = 0;

      for(int j = field_185214_b.size(); i < j; ++i) {
         PotionHelper.MixPredicate<Item> mixpredicate = (PotionHelper.MixPredicate)field_185214_b.get(i);
         if (mixpredicate.field_185198_a == item && mixpredicate.field_185199_b.apply(p_185206_1_)) {
            return true;
         }
      }

      return false;
   }

   protected static boolean func_185209_c(ItemStack p_185209_0_, ItemStack p_185209_1_) {
      PotionType potiontype = PotionUtils.func_185191_c(p_185209_0_);
      int i = 0;

      for(int j = field_185213_a.size(); i < j; ++i) {
         PotionHelper.MixPredicate<PotionType> mixpredicate = (PotionHelper.MixPredicate)field_185213_a.get(i);
         if (mixpredicate.field_185198_a == potiontype && mixpredicate.field_185199_b.apply(p_185209_1_)) {
            return true;
         }
      }

      return false;
   }

   public static ItemStack func_185212_d(ItemStack p_185212_0_, ItemStack p_185212_1_) {
      if (!p_185212_1_.func_190926_b()) {
         PotionType potiontype = PotionUtils.func_185191_c(p_185212_1_);
         Item item = p_185212_1_.func_77973_b();
         int i = 0;

         for(int j = field_185214_b.size(); i < j; ++i) {
            PotionHelper.MixPredicate<Item> mixpredicate = (PotionHelper.MixPredicate)field_185214_b.get(i);
            if (mixpredicate.field_185198_a == item && mixpredicate.field_185199_b.apply(p_185212_0_)) {
               return PotionUtils.func_185188_a(new ItemStack((Item)mixpredicate.field_185200_c), potiontype);
            }
         }

         i = 0;

         for(int k = field_185213_a.size(); i < k; ++i) {
            PotionHelper.MixPredicate<PotionType> mixpredicate1 = (PotionHelper.MixPredicate)field_185213_a.get(i);
            if (mixpredicate1.field_185198_a == potiontype && mixpredicate1.field_185199_b.apply(p_185212_0_)) {
               return PotionUtils.func_185188_a(new ItemStack(item), (PotionType)mixpredicate1.field_185200_c);
            }
         }
      }

      return p_185212_1_;
   }

   public static void func_185207_a() {
      func_193354_a(Items.field_151068_bn);
      func_193354_a(Items.field_185155_bH);
      func_193354_a(Items.field_185156_bI);
      func_193355_a(Items.field_151068_bn, Items.field_151016_H, Items.field_185155_bH);
      func_193355_a(Items.field_185155_bH, Items.field_185157_bK, Items.field_185156_bI);
      func_193357_a(PotionTypes.field_185230_b, Items.field_151060_bw, PotionTypes.field_185231_c);
      func_193357_a(PotionTypes.field_185230_b, Items.field_151073_bk, PotionTypes.field_185231_c);
      func_193357_a(PotionTypes.field_185230_b, Items.field_179556_br, PotionTypes.field_185231_c);
      func_193357_a(PotionTypes.field_185230_b, Items.field_151065_br, PotionTypes.field_185231_c);
      func_193357_a(PotionTypes.field_185230_b, Items.field_151070_bp, PotionTypes.field_185231_c);
      func_193357_a(PotionTypes.field_185230_b, Items.field_151102_aT, PotionTypes.field_185231_c);
      func_193357_a(PotionTypes.field_185230_b, Items.field_151064_bs, PotionTypes.field_185231_c);
      func_193357_a(PotionTypes.field_185230_b, Items.field_151114_aO, PotionTypes.field_185232_d);
      func_193357_a(PotionTypes.field_185230_b, Items.field_151137_ax, PotionTypes.field_185231_c);
      func_193357_a(PotionTypes.field_185230_b, Items.field_151075_bm, PotionTypes.field_185233_e);
      func_193357_a(PotionTypes.field_185233_e, Items.field_151150_bK, PotionTypes.field_185234_f);
      func_193357_a(PotionTypes.field_185234_f, Items.field_151137_ax, PotionTypes.field_185235_g);
      func_193357_a(PotionTypes.field_185234_f, Items.field_151071_bq, PotionTypes.field_185236_h);
      func_193357_a(PotionTypes.field_185235_g, Items.field_151071_bq, PotionTypes.field_185237_i);
      func_193357_a(PotionTypes.field_185236_h, Items.field_151137_ax, PotionTypes.field_185237_i);
      func_193357_a(PotionTypes.field_185233_e, Items.field_151064_bs, PotionTypes.field_185241_m);
      func_193357_a(PotionTypes.field_185241_m, Items.field_151137_ax, PotionTypes.field_185242_n);
      func_193357_a(PotionTypes.field_185233_e, Items.field_179556_br, PotionTypes.field_185238_j);
      func_193357_a(PotionTypes.field_185238_j, Items.field_151137_ax, PotionTypes.field_185239_k);
      func_193357_a(PotionTypes.field_185238_j, Items.field_151114_aO, PotionTypes.field_185240_l);
      func_193357_a(PotionTypes.field_185238_j, Items.field_151071_bq, PotionTypes.field_185246_r);
      func_193357_a(PotionTypes.field_185239_k, Items.field_151071_bq, PotionTypes.field_185247_s);
      func_193357_a(PotionTypes.field_185246_r, Items.field_151137_ax, PotionTypes.field_185247_s);
      func_193357_a(PotionTypes.field_185243_o, Items.field_151071_bq, PotionTypes.field_185246_r);
      func_193357_a(PotionTypes.field_185244_p, Items.field_151071_bq, PotionTypes.field_185247_s);
      func_193357_a(PotionTypes.field_185233_e, Items.field_151102_aT, PotionTypes.field_185243_o);
      func_193357_a(PotionTypes.field_185243_o, Items.field_151137_ax, PotionTypes.field_185244_p);
      func_193357_a(PotionTypes.field_185243_o, Items.field_151114_aO, PotionTypes.field_185245_q);
      func_193356_a(PotionTypes.field_185233_e, Ingredient.func_193369_a(new ItemStack(Items.field_151115_aP, 1, ItemFishFood.FishType.PUFFERFISH.func_150976_a())), PotionTypes.field_185248_t);
      func_193357_a(PotionTypes.field_185248_t, Items.field_151137_ax, PotionTypes.field_185249_u);
      func_193357_a(PotionTypes.field_185233_e, Items.field_151060_bw, PotionTypes.field_185250_v);
      func_193357_a(PotionTypes.field_185250_v, Items.field_151114_aO, PotionTypes.field_185251_w);
      func_193357_a(PotionTypes.field_185250_v, Items.field_151071_bq, PotionTypes.field_185252_x);
      func_193357_a(PotionTypes.field_185251_w, Items.field_151071_bq, PotionTypes.field_185253_y);
      func_193357_a(PotionTypes.field_185252_x, Items.field_151114_aO, PotionTypes.field_185253_y);
      func_193357_a(PotionTypes.field_185254_z, Items.field_151071_bq, PotionTypes.field_185252_x);
      func_193357_a(PotionTypes.field_185218_A, Items.field_151071_bq, PotionTypes.field_185252_x);
      func_193357_a(PotionTypes.field_185219_B, Items.field_151071_bq, PotionTypes.field_185253_y);
      func_193357_a(PotionTypes.field_185233_e, Items.field_151070_bp, PotionTypes.field_185254_z);
      func_193357_a(PotionTypes.field_185254_z, Items.field_151137_ax, PotionTypes.field_185218_A);
      func_193357_a(PotionTypes.field_185254_z, Items.field_151114_aO, PotionTypes.field_185219_B);
      func_193357_a(PotionTypes.field_185233_e, Items.field_151073_bk, PotionTypes.field_185220_C);
      func_193357_a(PotionTypes.field_185220_C, Items.field_151137_ax, PotionTypes.field_185221_D);
      func_193357_a(PotionTypes.field_185220_C, Items.field_151114_aO, PotionTypes.field_185222_E);
      func_193357_a(PotionTypes.field_185233_e, Items.field_151065_br, PotionTypes.field_185223_F);
      func_193357_a(PotionTypes.field_185223_F, Items.field_151137_ax, PotionTypes.field_185224_G);
      func_193357_a(PotionTypes.field_185223_F, Items.field_151114_aO, PotionTypes.field_185225_H);
      func_193357_a(PotionTypes.field_185230_b, Items.field_151071_bq, PotionTypes.field_185226_I);
      func_193357_a(PotionTypes.field_185226_I, Items.field_151137_ax, PotionTypes.field_185227_J);
   }

   private static void func_193355_a(ItemPotion p_193355_0_, Item p_193355_1_, ItemPotion p_193355_2_) {
      field_185214_b.add(new PotionHelper.MixPredicate(p_193355_0_, Ingredient.func_193368_a(p_193355_1_), p_193355_2_));
   }

   private static void func_193354_a(ItemPotion p_193354_0_) {
      field_185215_c.add(Ingredient.func_193368_a(p_193354_0_));
   }

   private static void func_193357_a(PotionType p_193357_0_, Item p_193357_1_, PotionType p_193357_2_) {
      func_193356_a(p_193357_0_, Ingredient.func_193368_a(p_193357_1_), p_193357_2_);
   }

   private static void func_193356_a(PotionType p_193356_0_, Ingredient p_193356_1_, PotionType p_193356_2_) {
      field_185213_a.add(new PotionHelper.MixPredicate(p_193356_0_, p_193356_1_, p_193356_2_));
   }

   static class MixPredicate<T> {
      final T field_185198_a;
      final Ingredient field_185199_b;
      final T field_185200_c;

      public MixPredicate(T p_i47570_1_, Ingredient p_i47570_2_, T p_i47570_3_) {
         this.field_185198_a = p_i47570_1_;
         this.field_185199_b = p_i47570_2_;
         this.field_185200_c = p_i47570_3_;
      }
   }
}
