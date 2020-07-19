package net.minecraft.block.material;

public class Material {
   public static final Material field_151579_a = new MaterialTransparent(MapColor.field_151660_b);
   public static final Material field_151577_b = new Material(MapColor.field_151661_c);
   public static final Material field_151578_c = new Material(MapColor.field_151664_l);
   public static final Material field_151575_d = (new Material(MapColor.field_151663_o)).func_76226_g();
   public static final Material field_151576_e = (new Material(MapColor.field_151665_m)).func_76221_f();
   public static final Material field_151573_f = (new Material(MapColor.field_151668_h)).func_76221_f();
   public static final Material field_151574_g = (new Material(MapColor.field_151668_h)).func_76221_f().func_76225_o();
   public static final Material field_151586_h = (new MaterialLiquid(MapColor.field_151662_n)).func_76219_n();
   public static final Material field_151587_i = (new MaterialLiquid(MapColor.field_151656_f)).func_76219_n();
   public static final Material field_151584_j = (new Material(MapColor.field_151669_i)).func_76226_g().func_76223_p().func_76219_n();
   public static final Material field_151585_k = (new MaterialLogic(MapColor.field_151669_i)).func_76219_n();
   public static final Material field_151582_l = (new MaterialLogic(MapColor.field_151669_i)).func_76226_g().func_76219_n().func_76231_i();
   public static final Material field_151583_m = new Material(MapColor.field_151673_t);
   public static final Material field_151580_n = (new Material(MapColor.field_151659_e)).func_76226_g();
   public static final Material field_151581_o = (new MaterialTransparent(MapColor.field_151660_b)).func_76219_n();
   public static final Material field_151595_p = new Material(MapColor.field_151658_d);
   public static final Material field_151594_q = (new MaterialLogic(MapColor.field_151660_b)).func_76219_n();
   public static final Material field_151593_r = (new MaterialLogic(MapColor.field_151659_e)).func_76226_g();
   public static final Material field_151592_s = (new Material(MapColor.field_151660_b)).func_76223_p().func_85158_p();
   public static final Material field_151591_t = (new Material(MapColor.field_151660_b)).func_85158_p();
   public static final Material field_151590_u = (new Material(MapColor.field_151656_f)).func_76226_g().func_76223_p();
   public static final Material field_151589_v = (new Material(MapColor.field_151669_i)).func_76219_n();
   public static final Material field_151588_w = (new Material(MapColor.field_151657_g)).func_76223_p().func_85158_p();
   public static final Material field_151598_x = (new Material(MapColor.field_151657_g)).func_85158_p();
   public static final Material field_151597_y = (new MaterialLogic(MapColor.field_151666_j)).func_76231_i().func_76223_p().func_76221_f().func_76219_n();
   public static final Material field_151596_z = (new Material(MapColor.field_151666_j)).func_76221_f();
   public static final Material field_151570_A = (new Material(MapColor.field_151669_i)).func_76223_p().func_76219_n();
   public static final Material field_151571_B = new Material(MapColor.field_151667_k);
   public static final Material field_151572_C = (new Material(MapColor.field_151669_i)).func_76219_n();
   public static final Material field_151566_D = (new Material(MapColor.field_151669_i)).func_76219_n();
   public static final Material field_151567_E = (new MaterialPortal(MapColor.field_151660_b)).func_76225_o();
   public static final Material field_151568_F = (new Material(MapColor.field_151660_b)).func_76219_n();
   public static final Material field_151569_G = (new Material(MapColor.field_151659_e) {
      public boolean func_76230_c() {
         return false;
      }
   }).func_76221_f().func_76219_n();
   public static final Material field_76233_E = (new Material(MapColor.field_151665_m)).func_76225_o();
   public static final Material field_175972_I = (new Material(MapColor.field_151660_b)).func_76221_f().func_76225_o();
   public static final Material field_189963_J = new MaterialTransparent(MapColor.field_151660_b);
   private boolean field_76235_G;
   private boolean field_76239_H;
   private boolean field_76240_I;
   private final MapColor field_76234_F;
   private boolean field_76241_J = true;
   private EnumPushReaction field_76242_K = EnumPushReaction.NORMAL;
   private boolean field_85159_M;

   public Material(MapColor p_i2116_1_) {
      this.field_76234_F = p_i2116_1_;
   }

   public boolean func_76224_d() {
      return false;
   }

   public boolean func_76220_a() {
      return true;
   }

   public boolean func_76228_b() {
      return true;
   }

   public boolean func_76230_c() {
      return true;
   }

   private Material func_76223_p() {
      this.field_76240_I = true;
      return this;
   }

   protected Material func_76221_f() {
      this.field_76241_J = false;
      return this;
   }

   protected Material func_76226_g() {
      this.field_76235_G = true;
      return this;
   }

   public boolean func_76217_h() {
      return this.field_76235_G;
   }

   public Material func_76231_i() {
      this.field_76239_H = true;
      return this;
   }

   public boolean func_76222_j() {
      return this.field_76239_H;
   }

   public boolean func_76218_k() {
      return this.field_76240_I ? false : this.func_76230_c();
   }

   public boolean func_76229_l() {
      return this.field_76241_J;
   }

   public EnumPushReaction func_186274_m() {
      return this.field_76242_K;
   }

   protected Material func_76219_n() {
      this.field_76242_K = EnumPushReaction.DESTROY;
      return this;
   }

   protected Material func_76225_o() {
      this.field_76242_K = EnumPushReaction.BLOCK;
      return this;
   }

   protected Material func_85158_p() {
      this.field_85159_M = true;
      return this;
   }

   public MapColor func_151565_r() {
      return this.field_76234_F;
   }
}
