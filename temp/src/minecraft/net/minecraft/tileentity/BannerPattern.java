package net.minecraft.tileentity;

import javax.annotation.Nullable;
import net.minecraft.block.BlockFlower;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public enum BannerPattern {
   BASE("base", "b"),
   SQUARE_BOTTOM_LEFT("square_bottom_left", "bl", "   ", "   ", "#  "),
   SQUARE_BOTTOM_RIGHT("square_bottom_right", "br", "   ", "   ", "  #"),
   SQUARE_TOP_LEFT("square_top_left", "tl", "#  ", "   ", "   "),
   SQUARE_TOP_RIGHT("square_top_right", "tr", "  #", "   ", "   "),
   STRIPE_BOTTOM("stripe_bottom", "bs", "   ", "   ", "###"),
   STRIPE_TOP("stripe_top", "ts", "###", "   ", "   "),
   STRIPE_LEFT("stripe_left", "ls", "#  ", "#  ", "#  "),
   STRIPE_RIGHT("stripe_right", "rs", "  #", "  #", "  #"),
   STRIPE_CENTER("stripe_center", "cs", " # ", " # ", " # "),
   STRIPE_MIDDLE("stripe_middle", "ms", "   ", "###", "   "),
   STRIPE_DOWNRIGHT("stripe_downright", "drs", "#  ", " # ", "  #"),
   STRIPE_DOWNLEFT("stripe_downleft", "dls", "  #", " # ", "#  "),
   STRIPE_SMALL("small_stripes", "ss", "# #", "# #", "   "),
   CROSS("cross", "cr", "# #", " # ", "# #"),
   STRAIGHT_CROSS("straight_cross", "sc", " # ", "###", " # "),
   TRIANGLE_BOTTOM("triangle_bottom", "bt", "   ", " # ", "# #"),
   TRIANGLE_TOP("triangle_top", "tt", "# #", " # ", "   "),
   TRIANGLES_BOTTOM("triangles_bottom", "bts", "   ", "# #", " # "),
   TRIANGLES_TOP("triangles_top", "tts", " # ", "# #", "   "),
   DIAGONAL_LEFT("diagonal_left", "ld", "## ", "#  ", "   "),
   DIAGONAL_RIGHT("diagonal_up_right", "rd", "   ", "  #", " ##"),
   DIAGONAL_LEFT_MIRROR("diagonal_up_left", "lud", "   ", "#  ", "## "),
   DIAGONAL_RIGHT_MIRROR("diagonal_right", "rud", " ##", "  #", "   "),
   CIRCLE_MIDDLE("circle", "mc", "   ", " # ", "   "),
   RHOMBUS_MIDDLE("rhombus", "mr", " # ", "# #", " # "),
   HALF_VERTICAL("half_vertical", "vh", "## ", "## ", "## "),
   HALF_HORIZONTAL("half_horizontal", "hh", "###", "###", "   "),
   HALF_VERTICAL_MIRROR("half_vertical_right", "vhr", " ##", " ##", " ##"),
   HALF_HORIZONTAL_MIRROR("half_horizontal_bottom", "hhb", "   ", "###", "###"),
   BORDER("border", "bo", "###", "# #", "###"),
   CURLY_BORDER("curly_border", "cbo", new ItemStack(Blocks.field_150395_bd)),
   CREEPER("creeper", "cre", new ItemStack(Items.field_151144_bL, 1, 4)),
   GRADIENT("gradient", "gra", "# #", " # ", " # "),
   GRADIENT_UP("gradient_up", "gru", " # ", " # ", "# #"),
   BRICKS("bricks", "bri", new ItemStack(Blocks.field_150336_V)),
   SKULL("skull", "sku", new ItemStack(Items.field_151144_bL, 1, 1)),
   FLOWER("flower", "flo", new ItemStack(Blocks.field_150328_O, 1, BlockFlower.EnumFlowerType.OXEYE_DAISY.func_176968_b())),
   MOJANG("mojang", "moj", new ItemStack(Items.field_151153_ao, 1, 1));

   private final String field_191014_N;
   private final String field_191015_O;
   private final String[] field_191016_P;
   private ItemStack field_191017_Q;

   private BannerPattern(String p_i47245_3_, String p_i47245_4_) {
      this.field_191016_P = new String[3];
      this.field_191017_Q = ItemStack.field_190927_a;
      this.field_191014_N = p_i47245_3_;
      this.field_191015_O = p_i47245_4_;
   }

   private BannerPattern(String p_i47246_3_, String p_i47246_4_, ItemStack p_i47246_5_) {
      this(p_i47246_3_, p_i47246_4_);
      this.field_191017_Q = p_i47246_5_;
   }

   private BannerPattern(String p_i47247_3_, String p_i47247_4_, String p_i47247_5_, String p_i47247_6_, String p_i47247_7_) {
      this(p_i47247_3_, p_i47247_4_);
      this.field_191016_P[0] = p_i47247_5_;
      this.field_191016_P[1] = p_i47247_6_;
      this.field_191016_P[2] = p_i47247_7_;
   }

   public String func_190997_a() {
      return this.field_191014_N;
   }

   public String func_190993_b() {
      return this.field_191015_O;
   }

   public String[] func_190996_c() {
      return this.field_191016_P;
   }

   public boolean func_191000_d() {
      return !this.field_191017_Q.func_190926_b() || this.field_191016_P[0] != null;
   }

   public boolean func_190999_e() {
      return !this.field_191017_Q.func_190926_b();
   }

   public ItemStack func_190998_f() {
      return this.field_191017_Q;
   }

   @Nullable
   public static BannerPattern func_190994_a(String p_190994_0_) {
      for(BannerPattern bannerpattern : values()) {
         if (bannerpattern.field_191015_O.equals(p_190994_0_)) {
            return bannerpattern;
         }
      }

      return null;
   }
}
