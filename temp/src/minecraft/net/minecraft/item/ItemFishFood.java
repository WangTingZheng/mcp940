package net.minecraft.item;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemFishFood extends ItemFood {
   private final boolean field_150907_b;

   public ItemFishFood(boolean p_i45338_1_) {
      super(0, 0.0F, false);
      this.field_150907_b = p_i45338_1_;
   }

   public int func_150905_g(ItemStack p_150905_1_) {
      ItemFishFood.FishType itemfishfood$fishtype = ItemFishFood.FishType.func_150978_a(p_150905_1_);
      return this.field_150907_b && itemfishfood$fishtype.func_150973_i() ? itemfishfood$fishtype.func_150970_e() : itemfishfood$fishtype.func_150975_c();
   }

   public float func_150906_h(ItemStack p_150906_1_) {
      ItemFishFood.FishType itemfishfood$fishtype = ItemFishFood.FishType.func_150978_a(p_150906_1_);
      return this.field_150907_b && itemfishfood$fishtype.func_150973_i() ? itemfishfood$fishtype.func_150977_f() : itemfishfood$fishtype.func_150967_d();
   }

   protected void func_77849_c(ItemStack p_77849_1_, World p_77849_2_, EntityPlayer p_77849_3_) {
      ItemFishFood.FishType itemfishfood$fishtype = ItemFishFood.FishType.func_150978_a(p_77849_1_);
      if (itemfishfood$fishtype == ItemFishFood.FishType.PUFFERFISH) {
         p_77849_3_.func_70690_d(new PotionEffect(MobEffects.field_76436_u, 1200, 3));
         p_77849_3_.func_70690_d(new PotionEffect(MobEffects.field_76438_s, 300, 2));
         p_77849_3_.func_70690_d(new PotionEffect(MobEffects.field_76431_k, 300, 1));
      }

      super.func_77849_c(p_77849_1_, p_77849_2_, p_77849_3_);
   }

   public void func_150895_a(CreativeTabs p_150895_1_, NonNullList<ItemStack> p_150895_2_) {
      if (this.func_194125_a(p_150895_1_)) {
         for(ItemFishFood.FishType itemfishfood$fishtype : ItemFishFood.FishType.values()) {
            if (!this.field_150907_b || itemfishfood$fishtype.func_150973_i()) {
               p_150895_2_.add(new ItemStack(this, 1, itemfishfood$fishtype.func_150976_a()));
            }
         }
      }

   }

   public String func_77667_c(ItemStack p_77667_1_) {
      ItemFishFood.FishType itemfishfood$fishtype = ItemFishFood.FishType.func_150978_a(p_77667_1_);
      return this.func_77658_a() + "." + itemfishfood$fishtype.func_150972_b() + "." + (this.field_150907_b && itemfishfood$fishtype.func_150973_i() ? "cooked" : "raw");
   }

   public static enum FishType {
      COD(0, "cod", 2, 0.1F, 5, 0.6F),
      SALMON(1, "salmon", 2, 0.1F, 6, 0.8F),
      CLOWNFISH(2, "clownfish", 1, 0.1F),
      PUFFERFISH(3, "pufferfish", 1, 0.1F);

      private static final Map<Integer, ItemFishFood.FishType> field_150983_e = Maps.<Integer, ItemFishFood.FishType>newHashMap();
      private final int field_150980_f;
      private final String field_150981_g;
      private final int field_150991_j;
      private final float field_150992_k;
      private final int field_150989_l;
      private final float field_150990_m;
      private final boolean field_150987_n;

      private FishType(int p_i45336_3_, String p_i45336_4_, int p_i45336_5_, float p_i45336_6_, int p_i45336_7_, float p_i45336_8_) {
         this.field_150980_f = p_i45336_3_;
         this.field_150981_g = p_i45336_4_;
         this.field_150991_j = p_i45336_5_;
         this.field_150992_k = p_i45336_6_;
         this.field_150989_l = p_i45336_7_;
         this.field_150990_m = p_i45336_8_;
         this.field_150987_n = true;
      }

      private FishType(int p_i45337_3_, String p_i45337_4_, int p_i45337_5_, float p_i45337_6_) {
         this.field_150980_f = p_i45337_3_;
         this.field_150981_g = p_i45337_4_;
         this.field_150991_j = p_i45337_5_;
         this.field_150992_k = p_i45337_6_;
         this.field_150989_l = 0;
         this.field_150990_m = 0.0F;
         this.field_150987_n = false;
      }

      public int func_150976_a() {
         return this.field_150980_f;
      }

      public String func_150972_b() {
         return this.field_150981_g;
      }

      public int func_150975_c() {
         return this.field_150991_j;
      }

      public float func_150967_d() {
         return this.field_150992_k;
      }

      public int func_150970_e() {
         return this.field_150989_l;
      }

      public float func_150977_f() {
         return this.field_150990_m;
      }

      public boolean func_150973_i() {
         return this.field_150987_n;
      }

      public static ItemFishFood.FishType func_150974_a(int p_150974_0_) {
         ItemFishFood.FishType itemfishfood$fishtype = field_150983_e.get(Integer.valueOf(p_150974_0_));
         return itemfishfood$fishtype == null ? COD : itemfishfood$fishtype;
      }

      public static ItemFishFood.FishType func_150978_a(ItemStack p_150978_0_) {
         return p_150978_0_.func_77973_b() instanceof ItemFishFood ? func_150974_a(p_150978_0_.func_77960_j()) : COD;
      }

      static {
         for(ItemFishFood.FishType itemfishfood$fishtype : values()) {
            field_150983_e.put(Integer.valueOf(itemfishfood$fishtype.func_150976_a()), itemfishfood$fishtype);
         }

      }
   }
}
