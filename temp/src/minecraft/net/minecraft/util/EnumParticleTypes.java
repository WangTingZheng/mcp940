package net.minecraft.util;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

public enum EnumParticleTypes {
   EXPLOSION_NORMAL("explode", 0, true),
   EXPLOSION_LARGE("largeexplode", 1, true),
   EXPLOSION_HUGE("hugeexplosion", 2, true),
   FIREWORKS_SPARK("fireworksSpark", 3, false),
   WATER_BUBBLE("bubble", 4, false),
   WATER_SPLASH("splash", 5, false),
   WATER_WAKE("wake", 6, false),
   SUSPENDED("suspended", 7, false),
   SUSPENDED_DEPTH("depthsuspend", 8, false),
   CRIT("crit", 9, false),
   CRIT_MAGIC("magicCrit", 10, false),
   SMOKE_NORMAL("smoke", 11, false),
   SMOKE_LARGE("largesmoke", 12, false),
   SPELL("spell", 13, false),
   SPELL_INSTANT("instantSpell", 14, false),
   SPELL_MOB("mobSpell", 15, false),
   SPELL_MOB_AMBIENT("mobSpellAmbient", 16, false),
   SPELL_WITCH("witchMagic", 17, false),
   DRIP_WATER("dripWater", 18, false),
   DRIP_LAVA("dripLava", 19, false),
   VILLAGER_ANGRY("angryVillager", 20, false),
   VILLAGER_HAPPY("happyVillager", 21, false),
   TOWN_AURA("townaura", 22, false),
   NOTE("note", 23, false),
   PORTAL("portal", 24, false),
   ENCHANTMENT_TABLE("enchantmenttable", 25, false),
   FLAME("flame", 26, false),
   LAVA("lava", 27, false),
   FOOTSTEP("footstep", 28, false),
   CLOUD("cloud", 29, false),
   REDSTONE("reddust", 30, false),
   SNOWBALL("snowballpoof", 31, false),
   SNOW_SHOVEL("snowshovel", 32, false),
   SLIME("slime", 33, false),
   HEART("heart", 34, false),
   BARRIER("barrier", 35, false),
   ITEM_CRACK("iconcrack", 36, false, 2),
   BLOCK_CRACK("blockcrack", 37, false, 1),
   BLOCK_DUST("blockdust", 38, false, 1),
   WATER_DROP("droplet", 39, false),
   ITEM_TAKE("take", 40, false),
   MOB_APPEARANCE("mobappearance", 41, true),
   DRAGON_BREATH("dragonbreath", 42, false),
   END_ROD("endRod", 43, false),
   DAMAGE_INDICATOR("damageIndicator", 44, true),
   SWEEP_ATTACK("sweepAttack", 45, true),
   FALLING_DUST("fallingdust", 46, false, 1),
   TOTEM("totem", 47, false),
   SPIT("spit", 48, true);

   private final String field_179369_Q;
   private final int field_179372_R;
   private final boolean field_179371_S;
   private final int field_179366_T;
   private static final Map<Integer, EnumParticleTypes> field_179365_U = Maps.<Integer, EnumParticleTypes>newHashMap();
   private static final Map<String, EnumParticleTypes> field_186837_Z = Maps.<String, EnumParticleTypes>newHashMap();

   private EnumParticleTypes(String p_i46011_3_, int p_i46011_4_, boolean p_i46011_5_, int p_i46011_6_) {
      this.field_179369_Q = p_i46011_3_;
      this.field_179372_R = p_i46011_4_;
      this.field_179371_S = p_i46011_5_;
      this.field_179366_T = p_i46011_6_;
   }

   private EnumParticleTypes(String p_i46012_3_, int p_i46012_4_, boolean p_i46012_5_) {
      this(p_i46012_3_, p_i46012_4_, p_i46012_5_, 0);
   }

   public static Set<String> func_186832_a() {
      return field_186837_Z.keySet();
   }

   public String func_179346_b() {
      return this.field_179369_Q;
   }

   public int func_179348_c() {
      return this.field_179372_R;
   }

   public int func_179345_d() {
      return this.field_179366_T;
   }

   public boolean func_179344_e() {
      return this.field_179371_S;
   }

   @Nullable
   public static EnumParticleTypes func_179342_a(int p_179342_0_) {
      return field_179365_U.get(Integer.valueOf(p_179342_0_));
   }

   @Nullable
   public static EnumParticleTypes func_186831_a(String p_186831_0_) {
      return field_186837_Z.get(p_186831_0_);
   }

   static {
      for(EnumParticleTypes enumparticletypes : values()) {
         field_179365_U.put(Integer.valueOf(enumparticletypes.func_179348_c()), enumparticletypes);
         field_186837_Z.put(enumparticletypes.func_179346_b(), enumparticletypes);
      }

   }
}
