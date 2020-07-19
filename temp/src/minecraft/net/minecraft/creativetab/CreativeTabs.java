package net.minecraft.creativetab;

import javax.annotation.Nullable;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;

public abstract class CreativeTabs {
   public static final CreativeTabs[] field_78032_a = new CreativeTabs[12];
   public static final CreativeTabs field_78030_b = new CreativeTabs(0, "buildingBlocks") {
      public ItemStack func_78016_d() {
         return new ItemStack(Item.func_150898_a(Blocks.field_150336_V));
      }
   };
   public static final CreativeTabs field_78031_c = new CreativeTabs(1, "decorations") {
      public ItemStack func_78016_d() {
         return new ItemStack(Item.func_150898_a(Blocks.field_150398_cm), 1, BlockDoublePlant.EnumPlantType.PAEONIA.func_176936_a());
      }
   };
   public static final CreativeTabs field_78028_d = new CreativeTabs(2, "redstone") {
      public ItemStack func_78016_d() {
         return new ItemStack(Items.field_151137_ax);
      }
   };
   public static final CreativeTabs field_78029_e = new CreativeTabs(3, "transportation") {
      public ItemStack func_78016_d() {
         return new ItemStack(Item.func_150898_a(Blocks.field_150318_D));
      }
   };
   public static final CreativeTabs field_78026_f = new CreativeTabs(6, "misc") {
      public ItemStack func_78016_d() {
         return new ItemStack(Items.field_151129_at);
      }
   };
   public static final CreativeTabs field_78027_g = (new CreativeTabs(5, "search") {
      public ItemStack func_78016_d() {
         return new ItemStack(Items.field_151111_aL);
      }
   }).func_78025_a("item_search.png");
   public static final CreativeTabs field_78039_h = new CreativeTabs(7, "food") {
      public ItemStack func_78016_d() {
         return new ItemStack(Items.field_151034_e);
      }
   };
   public static final CreativeTabs field_78040_i = (new CreativeTabs(8, "tools") {
      public ItemStack func_78016_d() {
         return new ItemStack(Items.field_151036_c);
      }
   }).func_111229_a(new EnumEnchantmentType[]{EnumEnchantmentType.ALL, EnumEnchantmentType.DIGGER, EnumEnchantmentType.FISHING_ROD, EnumEnchantmentType.BREAKABLE});
   public static final CreativeTabs field_78037_j = (new CreativeTabs(9, "combat") {
      public ItemStack func_78016_d() {
         return new ItemStack(Items.field_151010_B);
      }
   }).func_111229_a(new EnumEnchantmentType[]{EnumEnchantmentType.ALL, EnumEnchantmentType.ARMOR, EnumEnchantmentType.ARMOR_FEET, EnumEnchantmentType.ARMOR_HEAD, EnumEnchantmentType.ARMOR_LEGS, EnumEnchantmentType.ARMOR_CHEST, EnumEnchantmentType.BOW, EnumEnchantmentType.WEAPON, EnumEnchantmentType.WEARABLE, EnumEnchantmentType.BREAKABLE});
   public static final CreativeTabs field_78038_k = new CreativeTabs(10, "brewing") {
      public ItemStack func_78016_d() {
         return PotionUtils.func_185188_a(new ItemStack(Items.field_151068_bn), PotionTypes.field_185230_b);
      }
   };
   public static final CreativeTabs field_78035_l = field_78026_f;
   public static final CreativeTabs field_192395_m = new CreativeTabs(4, "hotbar") {
      public ItemStack func_78016_d() {
         return new ItemStack(Blocks.field_150342_X);
      }

      public void func_78018_a(NonNullList<ItemStack> p_78018_1_) {
         throw new RuntimeException("Implement exception client-side.");
      }

      public boolean func_192394_m() {
         return true;
      }
   };
   public static final CreativeTabs field_78036_m = (new CreativeTabs(11, "inventory") {
      public ItemStack func_78016_d() {
         return new ItemStack(Item.func_150898_a(Blocks.field_150486_ae));
      }
   }).func_78025_a("inventory.png").func_78022_j().func_78014_h();
   private final int field_78033_n;
   private final String field_78034_o;
   private String field_78043_p = "items.png";
   private boolean field_78042_q = true;
   private boolean field_78041_r = true;
   private EnumEnchantmentType[] field_111230_s = new EnumEnchantmentType[0];
   private ItemStack field_151245_t;

   public CreativeTabs(int p_i1853_1_, String p_i1853_2_) {
      this.field_78033_n = p_i1853_1_;
      this.field_78034_o = p_i1853_2_;
      this.field_151245_t = ItemStack.field_190927_a;
      field_78032_a[p_i1853_1_] = this;
   }

   public int func_78021_a() {
      return this.field_78033_n;
   }

   public String func_78013_b() {
      return this.field_78034_o;
   }

   public String func_78024_c() {
      return "itemGroup." + this.func_78013_b();
   }

   public ItemStack func_151244_d() {
      if (this.field_151245_t.func_190926_b()) {
         this.field_151245_t = this.func_78016_d();
      }

      return this.field_151245_t;
   }

   public abstract ItemStack func_78016_d();

   public String func_78015_f() {
      return this.field_78043_p;
   }

   public CreativeTabs func_78025_a(String p_78025_1_) {
      this.field_78043_p = p_78025_1_;
      return this;
   }

   public boolean func_78019_g() {
      return this.field_78041_r;
   }

   public CreativeTabs func_78014_h() {
      this.field_78041_r = false;
      return this;
   }

   public boolean func_78017_i() {
      return this.field_78042_q;
   }

   public CreativeTabs func_78022_j() {
      this.field_78042_q = false;
      return this;
   }

   public int func_78020_k() {
      return this.field_78033_n % 6;
   }

   public boolean func_78023_l() {
      return this.field_78033_n < 6;
   }

   public boolean func_192394_m() {
      return this.func_78020_k() == 5;
   }

   public EnumEnchantmentType[] func_111225_m() {
      return this.field_111230_s;
   }

   public CreativeTabs func_111229_a(EnumEnchantmentType... p_111229_1_) {
      this.field_111230_s = p_111229_1_;
      return this;
   }

   public boolean func_111226_a(@Nullable EnumEnchantmentType p_111226_1_) {
      if (p_111226_1_ != null) {
         for(EnumEnchantmentType enumenchantmenttype : this.field_111230_s) {
            if (enumenchantmenttype == p_111226_1_) {
               return true;
            }
         }
      }

      return false;
   }

   public void func_78018_a(NonNullList<ItemStack> p_78018_1_) {
      for(Item item : Item.field_150901_e) {
         item.func_150895_a(this, p_78018_1_);
      }

   }
}
