package net.minecraft.world;

import net.minecraft.entity.player.PlayerCapabilities;

public enum GameType {
   NOT_SET(-1, "", ""),
   SURVIVAL(0, "survival", "s"),
   CREATIVE(1, "creative", "c"),
   ADVENTURE(2, "adventure", "a"),
   SPECTATOR(3, "spectator", "sp");

   int field_77154_e;
   String field_77151_f;
   String field_185330_h;

   private GameType(int p_i46718_3_, String p_i46718_4_, String p_i46718_5_) {
      this.field_77154_e = p_i46718_3_;
      this.field_77151_f = p_i46718_4_;
      this.field_185330_h = p_i46718_5_;
   }

   public int func_77148_a() {
      return this.field_77154_e;
   }

   public String func_77149_b() {
      return this.field_77151_f;
   }

   public void func_77147_a(PlayerCapabilities p_77147_1_) {
      if (this == CREATIVE) {
         p_77147_1_.field_75101_c = true;
         p_77147_1_.field_75098_d = true;
         p_77147_1_.field_75102_a = true;
      } else if (this == SPECTATOR) {
         p_77147_1_.field_75101_c = true;
         p_77147_1_.field_75098_d = false;
         p_77147_1_.field_75102_a = true;
         p_77147_1_.field_75100_b = true;
      } else {
         p_77147_1_.field_75101_c = false;
         p_77147_1_.field_75098_d = false;
         p_77147_1_.field_75102_a = false;
         p_77147_1_.field_75100_b = false;
      }

      p_77147_1_.field_75099_e = !this.func_82752_c();
   }

   public boolean func_82752_c() {
      return this == ADVENTURE || this == SPECTATOR;
   }

   public boolean func_77145_d() {
      return this == CREATIVE;
   }

   public boolean func_77144_e() {
      return this == SURVIVAL || this == ADVENTURE;
   }

   public static GameType func_77146_a(int p_77146_0_) {
      return func_185329_a(p_77146_0_, SURVIVAL);
   }

   public static GameType func_185329_a(int p_185329_0_, GameType p_185329_1_) {
      for(GameType gametype : values()) {
         if (gametype.field_77154_e == p_185329_0_) {
            return gametype;
         }
      }

      return p_185329_1_;
   }

   public static GameType func_77142_a(String p_77142_0_) {
      return func_185328_a(p_77142_0_, SURVIVAL);
   }

   public static GameType func_185328_a(String p_185328_0_, GameType p_185328_1_) {
      for(GameType gametype : values()) {
         if (gametype.field_77151_f.equals(p_185328_0_) || gametype.field_185330_h.equals(p_185328_0_)) {
            return gametype;
         }
      }

      return p_185328_1_;
   }
}
