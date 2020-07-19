package net.minecraft.block.material;

import net.minecraft.item.EnumDyeColor;

public class MapColor {
   public static final MapColor[] field_76281_a = new MapColor[64];
   public static final MapColor[] field_193575_b = new MapColor[16];
   public static final MapColor field_151660_b = new MapColor(0, 0);
   public static final MapColor field_151661_c = new MapColor(1, 8368696);
   public static final MapColor field_151658_d = new MapColor(2, 16247203);
   public static final MapColor field_151659_e = new MapColor(3, 13092807);
   public static final MapColor field_151656_f = new MapColor(4, 16711680);
   public static final MapColor field_151657_g = new MapColor(5, 10526975);
   public static final MapColor field_151668_h = new MapColor(6, 10987431);
   public static final MapColor field_151669_i = new MapColor(7, 31744);
   public static final MapColor field_151666_j = new MapColor(8, 16777215);
   public static final MapColor field_151667_k = new MapColor(9, 10791096);
   public static final MapColor field_151664_l = new MapColor(10, 9923917);
   public static final MapColor field_151665_m = new MapColor(11, 7368816);
   public static final MapColor field_151662_n = new MapColor(12, 4210943);
   public static final MapColor field_151663_o = new MapColor(13, 9402184);
   public static final MapColor field_151677_p = new MapColor(14, 16776437);
   public static final MapColor field_151676_q = new MapColor(15, 14188339);
   public static final MapColor field_151675_r = new MapColor(16, 11685080);
   public static final MapColor field_151674_s = new MapColor(17, 6724056);
   public static final MapColor field_151673_t = new MapColor(18, 15066419);
   public static final MapColor field_151672_u = new MapColor(19, 8375321);
   public static final MapColor field_151671_v = new MapColor(20, 15892389);
   public static final MapColor field_151670_w = new MapColor(21, 5000268);
   public static final MapColor field_151680_x = new MapColor(22, 10066329);
   public static final MapColor field_151679_y = new MapColor(23, 5013401);
   public static final MapColor field_151678_z = new MapColor(24, 8339378);
   public static final MapColor field_151649_A = new MapColor(25, 3361970);
   public static final MapColor field_151650_B = new MapColor(26, 6704179);
   public static final MapColor field_151651_C = new MapColor(27, 6717235);
   public static final MapColor field_151645_D = new MapColor(28, 10040115);
   public static final MapColor field_151646_E = new MapColor(29, 1644825);
   public static final MapColor field_151647_F = new MapColor(30, 16445005);
   public static final MapColor field_151648_G = new MapColor(31, 6085589);
   public static final MapColor field_151652_H = new MapColor(32, 4882687);
   public static final MapColor field_151653_I = new MapColor(33, 55610);
   public static final MapColor field_151654_J = new MapColor(34, 8476209);
   public static final MapColor field_151655_K = new MapColor(35, 7340544);
   public static final MapColor field_193561_M = new MapColor(36, 13742497);
   public static final MapColor field_193562_N = new MapColor(37, 10441252);
   public static final MapColor field_193563_O = new MapColor(38, 9787244);
   public static final MapColor field_193564_P = new MapColor(39, 7367818);
   public static final MapColor field_193565_Q = new MapColor(40, 12223780);
   public static final MapColor field_193566_R = new MapColor(41, 6780213);
   public static final MapColor field_193567_S = new MapColor(42, 10505550);
   public static final MapColor field_193568_T = new MapColor(43, 3746083);
   public static final MapColor field_193569_U = new MapColor(44, 8874850);
   public static final MapColor field_193570_V = new MapColor(45, 5725276);
   public static final MapColor field_193571_W = new MapColor(46, 8014168);
   public static final MapColor field_193572_X = new MapColor(47, 4996700);
   public static final MapColor field_193573_Y = new MapColor(48, 4993571);
   public static final MapColor field_193574_Z = new MapColor(49, 5001770);
   public static final MapColor field_193559_aa = new MapColor(50, 9321518);
   public static final MapColor field_193560_ab = new MapColor(51, 2430480);
   public final int field_76291_p;
   public final int field_76290_q;

   private MapColor(int p_i2117_1_, int p_i2117_2_) {
      if (p_i2117_1_ >= 0 && p_i2117_1_ <= 63) {
         this.field_76290_q = p_i2117_1_;
         this.field_76291_p = p_i2117_2_;
         field_76281_a[p_i2117_1_] = this;
      } else {
         throw new IndexOutOfBoundsException("Map colour ID must be between 0 and 63 (inclusive)");
      }
   }

   public int func_151643_b(int p_151643_1_) {
      int i = 220;
      if (p_151643_1_ == 3) {
         i = 135;
      }

      if (p_151643_1_ == 2) {
         i = 255;
      }

      if (p_151643_1_ == 1) {
         i = 220;
      }

      if (p_151643_1_ == 0) {
         i = 180;
      }

      int j = (this.field_76291_p >> 16 & 255) * i / 255;
      int k = (this.field_76291_p >> 8 & 255) * i / 255;
      int l = (this.field_76291_p & 255) * i / 255;
      return -16777216 | j << 16 | k << 8 | l;
   }

   public static MapColor func_193558_a(EnumDyeColor p_193558_0_) {
      return field_193575_b[p_193558_0_.func_176765_a()];
   }

   static {
      field_193575_b[EnumDyeColor.WHITE.func_176765_a()] = field_151666_j;
      field_193575_b[EnumDyeColor.ORANGE.func_176765_a()] = field_151676_q;
      field_193575_b[EnumDyeColor.MAGENTA.func_176765_a()] = field_151675_r;
      field_193575_b[EnumDyeColor.LIGHT_BLUE.func_176765_a()] = field_151674_s;
      field_193575_b[EnumDyeColor.YELLOW.func_176765_a()] = field_151673_t;
      field_193575_b[EnumDyeColor.LIME.func_176765_a()] = field_151672_u;
      field_193575_b[EnumDyeColor.PINK.func_176765_a()] = field_151671_v;
      field_193575_b[EnumDyeColor.GRAY.func_176765_a()] = field_151670_w;
      field_193575_b[EnumDyeColor.SILVER.func_176765_a()] = field_151680_x;
      field_193575_b[EnumDyeColor.CYAN.func_176765_a()] = field_151679_y;
      field_193575_b[EnumDyeColor.PURPLE.func_176765_a()] = field_151678_z;
      field_193575_b[EnumDyeColor.BLUE.func_176765_a()] = field_151649_A;
      field_193575_b[EnumDyeColor.BROWN.func_176765_a()] = field_151650_B;
      field_193575_b[EnumDyeColor.GREEN.func_176765_a()] = field_151651_C;
      field_193575_b[EnumDyeColor.RED.func_176765_a()] = field_151645_D;
      field_193575_b[EnumDyeColor.BLACK.func_176765_a()] = field_151646_E;
   }
}
