package net.minecraft.world;

public class WorldType {
   public static final WorldType[] field_77139_a = new WorldType[16];
   public static final WorldType field_77137_b = (new WorldType(0, "default", 1)).func_77129_f();
   public static final WorldType field_77138_c = new WorldType(1, "flat");
   public static final WorldType field_77135_d = new WorldType(2, "largeBiomes");
   public static final WorldType field_151360_e = (new WorldType(3, "amplified")).func_151358_j();
   public static final WorldType field_180271_f = new WorldType(4, "customized");
   public static final WorldType field_180272_g = new WorldType(5, "debug_all_block_states");
   public static final WorldType field_77136_e = (new WorldType(8, "default_1_1", 0)).func_77124_a(false);
   private final int field_82748_f;
   private final String field_77133_f;
   private final int field_77134_g;
   private boolean field_77140_h;
   private boolean field_77141_i;
   private boolean field_151361_l;

   private WorldType(int p_i1959_1_, String p_i1959_2_) {
      this(p_i1959_1_, p_i1959_2_, 0);
   }

   private WorldType(int p_i1960_1_, String p_i1960_2_, int p_i1960_3_) {
      this.field_77133_f = p_i1960_2_;
      this.field_77134_g = p_i1960_3_;
      this.field_77140_h = true;
      this.field_82748_f = p_i1960_1_;
      field_77139_a[p_i1960_1_] = this;
   }

   public String func_77127_a() {
      return this.field_77133_f;
   }

   public String func_77128_b() {
      return "generator." + this.field_77133_f;
   }

   public String func_151359_c() {
      return this.func_77128_b() + ".info";
   }

   public int func_77131_c() {
      return this.field_77134_g;
   }

   public WorldType func_77132_a(int p_77132_1_) {
      return this == field_77137_b && p_77132_1_ == 0 ? field_77136_e : this;
   }

   private WorldType func_77124_a(boolean p_77124_1_) {
      this.field_77140_h = p_77124_1_;
      return this;
   }

   public boolean func_77126_d() {
      return this.field_77140_h;
   }

   private WorldType func_77129_f() {
      this.field_77141_i = true;
      return this;
   }

   public boolean func_77125_e() {
      return this.field_77141_i;
   }

   public static WorldType func_77130_a(String p_77130_0_) {
      for(WorldType worldtype : field_77139_a) {
         if (worldtype != null && worldtype.field_77133_f.equalsIgnoreCase(p_77130_0_)) {
            return worldtype;
         }
      }

      return null;
   }

   public int func_82747_f() {
      return this.field_82748_f;
   }

   public boolean func_151357_h() {
      return this.field_151361_l;
   }

   private WorldType func_151358_j() {
      this.field_151361_l = true;
      return this;
   }
}
