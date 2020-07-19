package net.minecraft.world.storage;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

public class WorldInfo {
   private String field_186349_b;
   private int field_186350_c;
   private boolean field_186351_d;
   public static final EnumDifficulty field_176156_a = EnumDifficulty.NORMAL;
   private long field_76100_a;
   private WorldType field_76098_b = WorldType.field_77137_b;
   private String field_82576_c = "";
   private int field_76099_c;
   private int field_76096_d;
   private int field_76097_e;
   private long field_82575_g;
   private long field_76094_f;
   private long field_76095_g;
   private long field_76107_h;
   private NBTTagCompound field_76108_i;
   private int field_76105_j;
   private String field_76106_k;
   private int field_76103_l;
   private int field_176157_p;
   private boolean field_76104_m;
   private int field_76101_n;
   private boolean field_76102_o;
   private int field_76114_p;
   private GameType field_76113_q;
   private boolean field_76112_r;
   private boolean field_76111_s;
   private boolean field_76110_t;
   private boolean field_76109_u;
   private EnumDifficulty field_176158_z;
   private boolean field_176150_A;
   private double field_176151_B;
   private double field_176152_C;
   private double field_176146_D = 6.0E7D;
   private long field_176147_E;
   private double field_176148_F;
   private double field_176149_G = 5.0D;
   private double field_176153_H = 0.2D;
   private int field_176154_I = 5;
   private int field_176155_J = 15;
   private final Map<DimensionType, NBTTagCompound> field_186348_N = Maps.newEnumMap(DimensionType.class);
   private GameRules field_82577_x = new GameRules();

   protected WorldInfo() {
   }

   public static void func_189967_a(DataFixer p_189967_0_) {
      p_189967_0_.func_188258_a(FixTypes.LEVEL, new IDataWalker() {
         public NBTTagCompound func_188266_a(IDataFixer p_188266_1_, NBTTagCompound p_188266_2_, int p_188266_3_) {
            if (p_188266_2_.func_150297_b("Player", 10)) {
               p_188266_2_.func_74782_a("Player", p_188266_1_.func_188251_a(FixTypes.PLAYER, p_188266_2_.func_74775_l("Player"), p_188266_3_));
            }

            return p_188266_2_;
         }
      });
   }

   public WorldInfo(NBTTagCompound p_i2157_1_) {
      if (p_i2157_1_.func_150297_b("Version", 10)) {
         NBTTagCompound nbttagcompound = p_i2157_1_.func_74775_l("Version");
         this.field_186349_b = nbttagcompound.func_74779_i("Name");
         this.field_186350_c = nbttagcompound.func_74762_e("Id");
         this.field_186351_d = nbttagcompound.func_74767_n("Snapshot");
      }

      this.field_76100_a = p_i2157_1_.func_74763_f("RandomSeed");
      if (p_i2157_1_.func_150297_b("generatorName", 8)) {
         String s1 = p_i2157_1_.func_74779_i("generatorName");
         this.field_76098_b = WorldType.func_77130_a(s1);
         if (this.field_76098_b == null) {
            this.field_76098_b = WorldType.field_77137_b;
         } else if (this.field_76098_b.func_77125_e()) {
            int i = 0;
            if (p_i2157_1_.func_150297_b("generatorVersion", 99)) {
               i = p_i2157_1_.func_74762_e("generatorVersion");
            }

            this.field_76098_b = this.field_76098_b.func_77132_a(i);
         }

         if (p_i2157_1_.func_150297_b("generatorOptions", 8)) {
            this.field_82576_c = p_i2157_1_.func_74779_i("generatorOptions");
         }
      }

      this.field_76113_q = GameType.func_77146_a(p_i2157_1_.func_74762_e("GameType"));
      if (p_i2157_1_.func_150297_b("MapFeatures", 99)) {
         this.field_76112_r = p_i2157_1_.func_74767_n("MapFeatures");
      } else {
         this.field_76112_r = true;
      }

      this.field_76099_c = p_i2157_1_.func_74762_e("SpawnX");
      this.field_76096_d = p_i2157_1_.func_74762_e("SpawnY");
      this.field_76097_e = p_i2157_1_.func_74762_e("SpawnZ");
      this.field_82575_g = p_i2157_1_.func_74763_f("Time");
      if (p_i2157_1_.func_150297_b("DayTime", 99)) {
         this.field_76094_f = p_i2157_1_.func_74763_f("DayTime");
      } else {
         this.field_76094_f = this.field_82575_g;
      }

      this.field_76095_g = p_i2157_1_.func_74763_f("LastPlayed");
      this.field_76107_h = p_i2157_1_.func_74763_f("SizeOnDisk");
      this.field_76106_k = p_i2157_1_.func_74779_i("LevelName");
      this.field_76103_l = p_i2157_1_.func_74762_e("version");
      this.field_176157_p = p_i2157_1_.func_74762_e("clearWeatherTime");
      this.field_76101_n = p_i2157_1_.func_74762_e("rainTime");
      this.field_76104_m = p_i2157_1_.func_74767_n("raining");
      this.field_76114_p = p_i2157_1_.func_74762_e("thunderTime");
      this.field_76102_o = p_i2157_1_.func_74767_n("thundering");
      this.field_76111_s = p_i2157_1_.func_74767_n("hardcore");
      if (p_i2157_1_.func_150297_b("initialized", 99)) {
         this.field_76109_u = p_i2157_1_.func_74767_n("initialized");
      } else {
         this.field_76109_u = true;
      }

      if (p_i2157_1_.func_150297_b("allowCommands", 99)) {
         this.field_76110_t = p_i2157_1_.func_74767_n("allowCommands");
      } else {
         this.field_76110_t = this.field_76113_q == GameType.CREATIVE;
      }

      if (p_i2157_1_.func_150297_b("Player", 10)) {
         this.field_76108_i = p_i2157_1_.func_74775_l("Player");
         this.field_76105_j = this.field_76108_i.func_74762_e("Dimension");
      }

      if (p_i2157_1_.func_150297_b("GameRules", 10)) {
         this.field_82577_x.func_82768_a(p_i2157_1_.func_74775_l("GameRules"));
      }

      if (p_i2157_1_.func_150297_b("Difficulty", 99)) {
         this.field_176158_z = EnumDifficulty.func_151523_a(p_i2157_1_.func_74771_c("Difficulty"));
      }

      if (p_i2157_1_.func_150297_b("DifficultyLocked", 1)) {
         this.field_176150_A = p_i2157_1_.func_74767_n("DifficultyLocked");
      }

      if (p_i2157_1_.func_150297_b("BorderCenterX", 99)) {
         this.field_176151_B = p_i2157_1_.func_74769_h("BorderCenterX");
      }

      if (p_i2157_1_.func_150297_b("BorderCenterZ", 99)) {
         this.field_176152_C = p_i2157_1_.func_74769_h("BorderCenterZ");
      }

      if (p_i2157_1_.func_150297_b("BorderSize", 99)) {
         this.field_176146_D = p_i2157_1_.func_74769_h("BorderSize");
      }

      if (p_i2157_1_.func_150297_b("BorderSizeLerpTime", 99)) {
         this.field_176147_E = p_i2157_1_.func_74763_f("BorderSizeLerpTime");
      }

      if (p_i2157_1_.func_150297_b("BorderSizeLerpTarget", 99)) {
         this.field_176148_F = p_i2157_1_.func_74769_h("BorderSizeLerpTarget");
      }

      if (p_i2157_1_.func_150297_b("BorderSafeZone", 99)) {
         this.field_176149_G = p_i2157_1_.func_74769_h("BorderSafeZone");
      }

      if (p_i2157_1_.func_150297_b("BorderDamagePerBlock", 99)) {
         this.field_176153_H = p_i2157_1_.func_74769_h("BorderDamagePerBlock");
      }

      if (p_i2157_1_.func_150297_b("BorderWarningBlocks", 99)) {
         this.field_176154_I = p_i2157_1_.func_74762_e("BorderWarningBlocks");
      }

      if (p_i2157_1_.func_150297_b("BorderWarningTime", 99)) {
         this.field_176155_J = p_i2157_1_.func_74762_e("BorderWarningTime");
      }

      if (p_i2157_1_.func_150297_b("DimensionData", 10)) {
         NBTTagCompound nbttagcompound1 = p_i2157_1_.func_74775_l("DimensionData");

         for(String s : nbttagcompound1.func_150296_c()) {
            this.field_186348_N.put(DimensionType.func_186069_a(Integer.parseInt(s)), nbttagcompound1.func_74775_l(s));
         }
      }

   }

   public WorldInfo(WorldSettings p_i2158_1_, String p_i2158_2_) {
      this.func_176127_a(p_i2158_1_);
      this.field_76106_k = p_i2158_2_;
      this.field_176158_z = field_176156_a;
      this.field_76109_u = false;
   }

   public void func_176127_a(WorldSettings p_176127_1_) {
      this.field_76100_a = p_176127_1_.func_77160_d();
      this.field_76113_q = p_176127_1_.func_77162_e();
      this.field_76112_r = p_176127_1_.func_77164_g();
      this.field_76111_s = p_176127_1_.func_77158_f();
      this.field_76098_b = p_176127_1_.func_77165_h();
      this.field_82576_c = p_176127_1_.func_82749_j();
      this.field_76110_t = p_176127_1_.func_77163_i();
   }

   public WorldInfo(WorldInfo p_i2159_1_) {
      this.field_76100_a = p_i2159_1_.field_76100_a;
      this.field_76098_b = p_i2159_1_.field_76098_b;
      this.field_82576_c = p_i2159_1_.field_82576_c;
      this.field_76113_q = p_i2159_1_.field_76113_q;
      this.field_76112_r = p_i2159_1_.field_76112_r;
      this.field_76099_c = p_i2159_1_.field_76099_c;
      this.field_76096_d = p_i2159_1_.field_76096_d;
      this.field_76097_e = p_i2159_1_.field_76097_e;
      this.field_82575_g = p_i2159_1_.field_82575_g;
      this.field_76094_f = p_i2159_1_.field_76094_f;
      this.field_76095_g = p_i2159_1_.field_76095_g;
      this.field_76107_h = p_i2159_1_.field_76107_h;
      this.field_76108_i = p_i2159_1_.field_76108_i;
      this.field_76105_j = p_i2159_1_.field_76105_j;
      this.field_76106_k = p_i2159_1_.field_76106_k;
      this.field_76103_l = p_i2159_1_.field_76103_l;
      this.field_76101_n = p_i2159_1_.field_76101_n;
      this.field_76104_m = p_i2159_1_.field_76104_m;
      this.field_76114_p = p_i2159_1_.field_76114_p;
      this.field_76102_o = p_i2159_1_.field_76102_o;
      this.field_76111_s = p_i2159_1_.field_76111_s;
      this.field_76110_t = p_i2159_1_.field_76110_t;
      this.field_76109_u = p_i2159_1_.field_76109_u;
      this.field_82577_x = p_i2159_1_.field_82577_x;
      this.field_176158_z = p_i2159_1_.field_176158_z;
      this.field_176150_A = p_i2159_1_.field_176150_A;
      this.field_176151_B = p_i2159_1_.field_176151_B;
      this.field_176152_C = p_i2159_1_.field_176152_C;
      this.field_176146_D = p_i2159_1_.field_176146_D;
      this.field_176147_E = p_i2159_1_.field_176147_E;
      this.field_176148_F = p_i2159_1_.field_176148_F;
      this.field_176149_G = p_i2159_1_.field_176149_G;
      this.field_176153_H = p_i2159_1_.field_176153_H;
      this.field_176155_J = p_i2159_1_.field_176155_J;
      this.field_176154_I = p_i2159_1_.field_176154_I;
   }

   public NBTTagCompound func_76082_a(@Nullable NBTTagCompound p_76082_1_) {
      if (p_76082_1_ == null) {
         p_76082_1_ = this.field_76108_i;
      }

      NBTTagCompound nbttagcompound = new NBTTagCompound();
      this.func_76064_a(nbttagcompound, p_76082_1_);
      return nbttagcompound;
   }

   private void func_76064_a(NBTTagCompound p_76064_1_, NBTTagCompound p_76064_2_) {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.func_74778_a("Name", "1.12");
      nbttagcompound.func_74768_a("Id", 1139);
      nbttagcompound.func_74757_a("Snapshot", false);
      p_76064_1_.func_74782_a("Version", nbttagcompound);
      p_76064_1_.func_74768_a("DataVersion", 1139);
      p_76064_1_.func_74772_a("RandomSeed", this.field_76100_a);
      p_76064_1_.func_74778_a("generatorName", this.field_76098_b.func_77127_a());
      p_76064_1_.func_74768_a("generatorVersion", this.field_76098_b.func_77131_c());
      p_76064_1_.func_74778_a("generatorOptions", this.field_82576_c);
      p_76064_1_.func_74768_a("GameType", this.field_76113_q.func_77148_a());
      p_76064_1_.func_74757_a("MapFeatures", this.field_76112_r);
      p_76064_1_.func_74768_a("SpawnX", this.field_76099_c);
      p_76064_1_.func_74768_a("SpawnY", this.field_76096_d);
      p_76064_1_.func_74768_a("SpawnZ", this.field_76097_e);
      p_76064_1_.func_74772_a("Time", this.field_82575_g);
      p_76064_1_.func_74772_a("DayTime", this.field_76094_f);
      p_76064_1_.func_74772_a("SizeOnDisk", this.field_76107_h);
      p_76064_1_.func_74772_a("LastPlayed", MinecraftServer.func_130071_aq());
      p_76064_1_.func_74778_a("LevelName", this.field_76106_k);
      p_76064_1_.func_74768_a("version", this.field_76103_l);
      p_76064_1_.func_74768_a("clearWeatherTime", this.field_176157_p);
      p_76064_1_.func_74768_a("rainTime", this.field_76101_n);
      p_76064_1_.func_74757_a("raining", this.field_76104_m);
      p_76064_1_.func_74768_a("thunderTime", this.field_76114_p);
      p_76064_1_.func_74757_a("thundering", this.field_76102_o);
      p_76064_1_.func_74757_a("hardcore", this.field_76111_s);
      p_76064_1_.func_74757_a("allowCommands", this.field_76110_t);
      p_76064_1_.func_74757_a("initialized", this.field_76109_u);
      p_76064_1_.func_74780_a("BorderCenterX", this.field_176151_B);
      p_76064_1_.func_74780_a("BorderCenterZ", this.field_176152_C);
      p_76064_1_.func_74780_a("BorderSize", this.field_176146_D);
      p_76064_1_.func_74772_a("BorderSizeLerpTime", this.field_176147_E);
      p_76064_1_.func_74780_a("BorderSafeZone", this.field_176149_G);
      p_76064_1_.func_74780_a("BorderDamagePerBlock", this.field_176153_H);
      p_76064_1_.func_74780_a("BorderSizeLerpTarget", this.field_176148_F);
      p_76064_1_.func_74780_a("BorderWarningBlocks", (double)this.field_176154_I);
      p_76064_1_.func_74780_a("BorderWarningTime", (double)this.field_176155_J);
      if (this.field_176158_z != null) {
         p_76064_1_.func_74774_a("Difficulty", (byte)this.field_176158_z.func_151525_a());
      }

      p_76064_1_.func_74757_a("DifficultyLocked", this.field_176150_A);
      p_76064_1_.func_74782_a("GameRules", this.field_82577_x.func_82770_a());
      NBTTagCompound nbttagcompound1 = new NBTTagCompound();

      for(Entry<DimensionType, NBTTagCompound> entry : this.field_186348_N.entrySet()) {
         nbttagcompound1.func_74782_a(String.valueOf(((DimensionType)entry.getKey()).func_186068_a()), entry.getValue());
      }

      p_76064_1_.func_74782_a("DimensionData", nbttagcompound1);
      if (p_76064_2_ != null) {
         p_76064_1_.func_74782_a("Player", p_76064_2_);
      }

   }

   public long func_76063_b() {
      return this.field_76100_a;
   }

   public int func_76079_c() {
      return this.field_76099_c;
   }

   public int func_76075_d() {
      return this.field_76096_d;
   }

   public int func_76074_e() {
      return this.field_76097_e;
   }

   public long func_82573_f() {
      return this.field_82575_g;
   }

   public long func_76073_f() {
      return this.field_76094_f;
   }

   public long func_76092_g() {
      return this.field_76107_h;
   }

   public NBTTagCompound func_76072_h() {
      return this.field_76108_i;
   }

   public void func_76058_a(int p_76058_1_) {
      this.field_76099_c = p_76058_1_;
   }

   public void func_76056_b(int p_76056_1_) {
      this.field_76096_d = p_76056_1_;
   }

   public void func_76087_c(int p_76087_1_) {
      this.field_76097_e = p_76087_1_;
   }

   public void func_82572_b(long p_82572_1_) {
      this.field_82575_g = p_82572_1_;
   }

   public void func_76068_b(long p_76068_1_) {
      this.field_76094_f = p_76068_1_;
   }

   public void func_176143_a(BlockPos p_176143_1_) {
      this.field_76099_c = p_176143_1_.func_177958_n();
      this.field_76096_d = p_176143_1_.func_177956_o();
      this.field_76097_e = p_176143_1_.func_177952_p();
   }

   public String func_76065_j() {
      return this.field_76106_k;
   }

   public void func_76062_a(String p_76062_1_) {
      this.field_76106_k = p_76062_1_;
   }

   public int func_76088_k() {
      return this.field_76103_l;
   }

   public void func_76078_e(int p_76078_1_) {
      this.field_76103_l = p_76078_1_;
   }

   public long func_76057_l() {
      return this.field_76095_g;
   }

   public int func_176133_A() {
      return this.field_176157_p;
   }

   public void func_176142_i(int p_176142_1_) {
      this.field_176157_p = p_176142_1_;
   }

   public boolean func_76061_m() {
      return this.field_76102_o;
   }

   public void func_76069_a(boolean p_76069_1_) {
      this.field_76102_o = p_76069_1_;
   }

   public int func_76071_n() {
      return this.field_76114_p;
   }

   public void func_76090_f(int p_76090_1_) {
      this.field_76114_p = p_76090_1_;
   }

   public boolean func_76059_o() {
      return this.field_76104_m;
   }

   public void func_76084_b(boolean p_76084_1_) {
      this.field_76104_m = p_76084_1_;
   }

   public int func_76083_p() {
      return this.field_76101_n;
   }

   public void func_76080_g(int p_76080_1_) {
      this.field_76101_n = p_76080_1_;
   }

   public GameType func_76077_q() {
      return this.field_76113_q;
   }

   public boolean func_76089_r() {
      return this.field_76112_r;
   }

   public void func_176128_f(boolean p_176128_1_) {
      this.field_76112_r = p_176128_1_;
   }

   public void func_76060_a(GameType p_76060_1_) {
      this.field_76113_q = p_76060_1_;
   }

   public boolean func_76093_s() {
      return this.field_76111_s;
   }

   public void func_176119_g(boolean p_176119_1_) {
      this.field_76111_s = p_176119_1_;
   }

   public WorldType func_76067_t() {
      return this.field_76098_b;
   }

   public void func_76085_a(WorldType p_76085_1_) {
      this.field_76098_b = p_76085_1_;
   }

   public String func_82571_y() {
      return this.field_82576_c == null ? "" : this.field_82576_c;
   }

   public boolean func_76086_u() {
      return this.field_76110_t;
   }

   public void func_176121_c(boolean p_176121_1_) {
      this.field_76110_t = p_176121_1_;
   }

   public boolean func_76070_v() {
      return this.field_76109_u;
   }

   public void func_76091_d(boolean p_76091_1_) {
      this.field_76109_u = p_76091_1_;
   }

   public GameRules func_82574_x() {
      return this.field_82577_x;
   }

   public double func_176120_C() {
      return this.field_176151_B;
   }

   public double func_176126_D() {
      return this.field_176152_C;
   }

   public double func_176137_E() {
      return this.field_176146_D;
   }

   public void func_176145_a(double p_176145_1_) {
      this.field_176146_D = p_176145_1_;
   }

   public long func_176134_F() {
      return this.field_176147_E;
   }

   public void func_176135_e(long p_176135_1_) {
      this.field_176147_E = p_176135_1_;
   }

   public double func_176132_G() {
      return this.field_176148_F;
   }

   public void func_176118_b(double p_176118_1_) {
      this.field_176148_F = p_176118_1_;
   }

   public void func_176141_c(double p_176141_1_) {
      this.field_176152_C = p_176141_1_;
   }

   public void func_176124_d(double p_176124_1_) {
      this.field_176151_B = p_176124_1_;
   }

   public double func_176138_H() {
      return this.field_176149_G;
   }

   public void func_176129_e(double p_176129_1_) {
      this.field_176149_G = p_176129_1_;
   }

   public double func_176140_I() {
      return this.field_176153_H;
   }

   public void func_176125_f(double p_176125_1_) {
      this.field_176153_H = p_176125_1_;
   }

   public int func_176131_J() {
      return this.field_176154_I;
   }

   public int func_176139_K() {
      return this.field_176155_J;
   }

   public void func_176122_j(int p_176122_1_) {
      this.field_176154_I = p_176122_1_;
   }

   public void func_176136_k(int p_176136_1_) {
      this.field_176155_J = p_176136_1_;
   }

   public EnumDifficulty func_176130_y() {
      return this.field_176158_z;
   }

   public void func_176144_a(EnumDifficulty p_176144_1_) {
      this.field_176158_z = p_176144_1_;
   }

   public boolean func_176123_z() {
      return this.field_176150_A;
   }

   public void func_180783_e(boolean p_180783_1_) {
      this.field_176150_A = p_180783_1_;
   }

   public void func_85118_a(CrashReportCategory p_85118_1_) {
      p_85118_1_.func_189529_a("Level seed", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            return String.valueOf(WorldInfo.this.func_76063_b());
         }
      });
      p_85118_1_.func_189529_a("Level generator", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            return String.format("ID %02d - %s, ver %d. Features enabled: %b", WorldInfo.this.field_76098_b.func_82747_f(), WorldInfo.this.field_76098_b.func_77127_a(), WorldInfo.this.field_76098_b.func_77131_c(), WorldInfo.this.field_76112_r);
         }
      });
      p_85118_1_.func_189529_a("Level generator options", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            return WorldInfo.this.field_82576_c;
         }
      });
      p_85118_1_.func_189529_a("Level spawn location", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            return CrashReportCategory.func_184876_a(WorldInfo.this.field_76099_c, WorldInfo.this.field_76096_d, WorldInfo.this.field_76097_e);
         }
      });
      p_85118_1_.func_189529_a("Level time", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            return String.format("%d game time, %d day time", WorldInfo.this.field_82575_g, WorldInfo.this.field_76094_f);
         }
      });
      p_85118_1_.func_189529_a("Level dimension", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            return String.valueOf(WorldInfo.this.field_76105_j);
         }
      });
      p_85118_1_.func_189529_a("Level storage version", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            String s = "Unknown?";

            try {
               switch(WorldInfo.this.field_76103_l) {
               case 19132:
                  s = "McRegion";
                  break;
               case 19133:
                  s = "Anvil";
               }
            } catch (Throwable var3) {
               ;
            }

            return String.format("0x%05X - %s", WorldInfo.this.field_76103_l, s);
         }
      });
      p_85118_1_.func_189529_a("Level weather", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", WorldInfo.this.field_76101_n, WorldInfo.this.field_76104_m, WorldInfo.this.field_76114_p, WorldInfo.this.field_76102_o);
         }
      });
      p_85118_1_.func_189529_a("Level game mode", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", WorldInfo.this.field_76113_q.func_77149_b(), WorldInfo.this.field_76113_q.func_77148_a(), WorldInfo.this.field_76111_s, WorldInfo.this.field_76110_t);
         }
      });
   }

   public NBTTagCompound func_186347_a(DimensionType p_186347_1_) {
      NBTTagCompound nbttagcompound = this.field_186348_N.get(p_186347_1_);
      return nbttagcompound == null ? new NBTTagCompound() : nbttagcompound;
   }

   public void func_186345_a(DimensionType p_186345_1_, NBTTagCompound p_186345_2_) {
      this.field_186348_N.put(p_186345_1_, p_186345_2_);
   }

   public int func_186344_K() {
      return this.field_186350_c;
   }

   public boolean func_186343_L() {
      return this.field_186351_d;
   }

   public String func_186346_M() {
      return this.field_186349_b;
   }
}
