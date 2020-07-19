package net.minecraft.world;

import net.minecraft.world.storage.WorldInfo;

public final class WorldSettings {
   private final long field_77174_a;
   private final GameType field_77172_b;
   private final boolean field_77173_c;
   private final boolean field_77170_d;
   private final WorldType field_77171_e;
   private boolean field_77168_f;
   private boolean field_77169_g;
   private String field_82751_h;

   public WorldSettings(long p_i1957_1_, GameType p_i1957_3_, boolean p_i1957_4_, boolean p_i1957_5_, WorldType p_i1957_6_) {
      this.field_82751_h = "";
      this.field_77174_a = p_i1957_1_;
      this.field_77172_b = p_i1957_3_;
      this.field_77173_c = p_i1957_4_;
      this.field_77170_d = p_i1957_5_;
      this.field_77171_e = p_i1957_6_;
   }

   public WorldSettings(WorldInfo p_i1958_1_) {
      this(p_i1958_1_.func_76063_b(), p_i1958_1_.func_76077_q(), p_i1958_1_.func_76089_r(), p_i1958_1_.func_76093_s(), p_i1958_1_.func_76067_t());
   }

   public WorldSettings func_77159_a() {
      this.field_77169_g = true;
      return this;
   }

   public WorldSettings func_77166_b() {
      this.field_77168_f = true;
      return this;
   }

   public WorldSettings func_82750_a(String p_82750_1_) {
      this.field_82751_h = p_82750_1_;
      return this;
   }

   public boolean func_77167_c() {
      return this.field_77169_g;
   }

   public long func_77160_d() {
      return this.field_77174_a;
   }

   public GameType func_77162_e() {
      return this.field_77172_b;
   }

   public boolean func_77158_f() {
      return this.field_77170_d;
   }

   public boolean func_77164_g() {
      return this.field_77173_c;
   }

   public WorldType func_77165_h() {
      return this.field_77171_e;
   }

   public boolean func_77163_i() {
      return this.field_77168_f;
   }

   public static GameType func_77161_a(int p_77161_0_) {
      return GameType.func_77146_a(p_77161_0_);
   }

   public String func_82749_j() {
      return this.field_82751_h;
   }
}
