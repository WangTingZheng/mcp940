package net.minecraft.potion;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.RegistryNamespaced;

public class Potion {
   public static final RegistryNamespaced<ResourceLocation, Potion> field_188414_b = new RegistryNamespaced<ResourceLocation, Potion>();
   private final Map<IAttribute, AttributeModifier> field_111188_I = Maps.<IAttribute, AttributeModifier>newHashMap();
   private final boolean field_76418_K;
   private final int field_76414_N;
   private String field_76416_I = "";
   private int field_76417_J = -1;
   private double field_76412_L;
   private boolean field_188415_h;

   @Nullable
   public static Potion func_188412_a(int p_188412_0_) {
      return field_188414_b.func_148754_a(p_188412_0_);
   }

   public static int func_188409_a(Potion p_188409_0_) {
      return field_188414_b.func_148757_b(p_188409_0_);
   }

   @Nullable
   public static Potion func_180142_b(String p_180142_0_) {
      return field_188414_b.func_82594_a(new ResourceLocation(p_180142_0_));
   }

   protected Potion(boolean p_i46815_1_, int p_i46815_2_) {
      this.field_76418_K = p_i46815_1_;
      if (p_i46815_1_) {
         this.field_76412_L = 0.5D;
      } else {
         this.field_76412_L = 1.0D;
      }

      this.field_76414_N = p_i46815_2_;
   }

   protected Potion func_76399_b(int p_76399_1_, int p_76399_2_) {
      this.field_76417_J = p_76399_1_ + p_76399_2_ * 8;
      return this;
   }

   public void func_76394_a(EntityLivingBase p_76394_1_, int p_76394_2_) {
      if (this == MobEffects.field_76428_l) {
         if (p_76394_1_.func_110143_aJ() < p_76394_1_.func_110138_aP()) {
            p_76394_1_.func_70691_i(1.0F);
         }
      } else if (this == MobEffects.field_76436_u) {
         if (p_76394_1_.func_110143_aJ() > 1.0F) {
            p_76394_1_.func_70097_a(DamageSource.field_76376_m, 1.0F);
         }
      } else if (this == MobEffects.field_82731_v) {
         p_76394_1_.func_70097_a(DamageSource.field_82727_n, 1.0F);
      } else if (this == MobEffects.field_76438_s && p_76394_1_ instanceof EntityPlayer) {
         ((EntityPlayer)p_76394_1_).func_71020_j(0.005F * (float)(p_76394_2_ + 1));
      } else if (this == MobEffects.field_76443_y && p_76394_1_ instanceof EntityPlayer) {
         if (!p_76394_1_.field_70170_p.field_72995_K) {
            ((EntityPlayer)p_76394_1_).func_71024_bL().func_75122_a(p_76394_2_ + 1, 1.0F);
         }
      } else if ((this != MobEffects.field_76432_h || p_76394_1_.func_70662_br()) && (this != MobEffects.field_76433_i || !p_76394_1_.func_70662_br())) {
         if (this == MobEffects.field_76433_i && !p_76394_1_.func_70662_br() || this == MobEffects.field_76432_h && p_76394_1_.func_70662_br()) {
            p_76394_1_.func_70097_a(DamageSource.field_76376_m, (float)(6 << p_76394_2_));
         }
      } else {
         p_76394_1_.func_70691_i((float)Math.max(4 << p_76394_2_, 0));
      }

   }

   public void func_180793_a(@Nullable Entity p_180793_1_, @Nullable Entity p_180793_2_, EntityLivingBase p_180793_3_, int p_180793_4_, double p_180793_5_) {
      if ((this != MobEffects.field_76432_h || p_180793_3_.func_70662_br()) && (this != MobEffects.field_76433_i || !p_180793_3_.func_70662_br())) {
         if (this == MobEffects.field_76433_i && !p_180793_3_.func_70662_br() || this == MobEffects.field_76432_h && p_180793_3_.func_70662_br()) {
            int j = (int)(p_180793_5_ * (double)(6 << p_180793_4_) + 0.5D);
            if (p_180793_1_ == null) {
               p_180793_3_.func_70097_a(DamageSource.field_76376_m, (float)j);
            } else {
               p_180793_3_.func_70097_a(DamageSource.func_76354_b(p_180793_1_, p_180793_2_), (float)j);
            }
         }
      } else {
         int i = (int)(p_180793_5_ * (double)(4 << p_180793_4_) + 0.5D);
         p_180793_3_.func_70691_i((float)i);
      }

   }

   public boolean func_76397_a(int p_76397_1_, int p_76397_2_) {
      if (this == MobEffects.field_76428_l) {
         int k = 50 >> p_76397_2_;
         if (k > 0) {
            return p_76397_1_ % k == 0;
         } else {
            return true;
         }
      } else if (this == MobEffects.field_76436_u) {
         int j = 25 >> p_76397_2_;
         if (j > 0) {
            return p_76397_1_ % j == 0;
         } else {
            return true;
         }
      } else if (this == MobEffects.field_82731_v) {
         int i = 40 >> p_76397_2_;
         if (i > 0) {
            return p_76397_1_ % i == 0;
         } else {
            return true;
         }
      } else {
         return this == MobEffects.field_76438_s;
      }
   }

   public boolean func_76403_b() {
      return false;
   }

   public Potion func_76390_b(String p_76390_1_) {
      this.field_76416_I = p_76390_1_;
      return this;
   }

   public String func_76393_a() {
      return this.field_76416_I;
   }

   public boolean func_76400_d() {
      return this.field_76417_J >= 0;
   }

   public int func_76392_e() {
      return this.field_76417_J;
   }

   public boolean func_76398_f() {
      return this.field_76418_K;
   }

   public static String func_188410_a(PotionEffect p_188410_0_, float p_188410_1_) {
      if (p_188410_0_.func_100011_g()) {
         return "**:**";
      } else {
         int i = MathHelper.func_76141_d((float)p_188410_0_.func_76459_b() * p_188410_1_);
         return StringUtils.func_76337_a(i);
      }
   }

   protected Potion func_76404_a(double p_76404_1_) {
      this.field_76412_L = p_76404_1_;
      return this;
   }

   public int func_76401_j() {
      return this.field_76414_N;
   }

   public Potion func_111184_a(IAttribute p_111184_1_, String p_111184_2_, double p_111184_3_, int p_111184_5_) {
      AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(p_111184_2_), this.func_76393_a(), p_111184_3_, p_111184_5_);
      this.field_111188_I.put(p_111184_1_, attributemodifier);
      return this;
   }

   public Map<IAttribute, AttributeModifier> func_111186_k() {
      return this.field_111188_I;
   }

   public void func_111187_a(EntityLivingBase p_111187_1_, AbstractAttributeMap p_111187_2_, int p_111187_3_) {
      for(Entry<IAttribute, AttributeModifier> entry : this.field_111188_I.entrySet()) {
         IAttributeInstance iattributeinstance = p_111187_2_.func_111151_a(entry.getKey());
         if (iattributeinstance != null) {
            iattributeinstance.func_111124_b(entry.getValue());
         }
      }

   }

   public void func_111185_a(EntityLivingBase p_111185_1_, AbstractAttributeMap p_111185_2_, int p_111185_3_) {
      for(Entry<IAttribute, AttributeModifier> entry : this.field_111188_I.entrySet()) {
         IAttributeInstance iattributeinstance = p_111185_2_.func_111151_a(entry.getKey());
         if (iattributeinstance != null) {
            AttributeModifier attributemodifier = entry.getValue();
            iattributeinstance.func_111124_b(attributemodifier);
            iattributeinstance.func_111121_a(new AttributeModifier(attributemodifier.func_111167_a(), this.func_76393_a() + " " + p_111185_3_, this.func_111183_a(p_111185_3_, attributemodifier), attributemodifier.func_111169_c()));
         }
      }

   }

   public double func_111183_a(int p_111183_1_, AttributeModifier p_111183_2_) {
      return p_111183_2_.func_111164_d() * (double)(p_111183_1_ + 1);
   }

   public boolean func_188408_i() {
      return this.field_188415_h;
   }

   public Potion func_188413_j() {
      this.field_188415_h = true;
      return this;
   }

   public static void func_188411_k() {
      field_188414_b.func_177775_a(1, new ResourceLocation("speed"), (new Potion(false, 8171462)).func_76390_b("effect.moveSpeed").func_76399_b(0, 0).func_111184_a(SharedMonsterAttributes.field_111263_d, "91AEAA56-376B-4498-935B-2F7F68070635", 0.20000000298023224D, 2).func_188413_j());
      field_188414_b.func_177775_a(2, new ResourceLocation("slowness"), (new Potion(true, 5926017)).func_76390_b("effect.moveSlowdown").func_76399_b(1, 0).func_111184_a(SharedMonsterAttributes.field_111263_d, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15000000596046448D, 2));
      field_188414_b.func_177775_a(3, new ResourceLocation("haste"), (new Potion(false, 14270531)).func_76390_b("effect.digSpeed").func_76399_b(2, 0).func_76404_a(1.5D).func_188413_j().func_111184_a(SharedMonsterAttributes.field_188790_f, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", 0.10000000149011612D, 2));
      field_188414_b.func_177775_a(4, new ResourceLocation("mining_fatigue"), (new Potion(true, 4866583)).func_76390_b("effect.digSlowDown").func_76399_b(3, 0).func_111184_a(SharedMonsterAttributes.field_188790_f, "55FCED67-E92A-486E-9800-B47F202C4386", -0.10000000149011612D, 2));
      field_188414_b.func_177775_a(5, new ResourceLocation("strength"), (new PotionAttackDamage(false, 9643043, 3.0D)).func_76390_b("effect.damageBoost").func_76399_b(4, 0).func_111184_a(SharedMonsterAttributes.field_111264_e, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.0D, 0).func_188413_j());
      field_188414_b.func_177775_a(6, new ResourceLocation("instant_health"), (new PotionHealth(false, 16262179)).func_76390_b("effect.heal").func_188413_j());
      field_188414_b.func_177775_a(7, new ResourceLocation("instant_damage"), (new PotionHealth(true, 4393481)).func_76390_b("effect.harm").func_188413_j());
      field_188414_b.func_177775_a(8, new ResourceLocation("jump_boost"), (new Potion(false, 2293580)).func_76390_b("effect.jump").func_76399_b(2, 1).func_188413_j());
      field_188414_b.func_177775_a(9, new ResourceLocation("nausea"), (new Potion(true, 5578058)).func_76390_b("effect.confusion").func_76399_b(3, 1).func_76404_a(0.25D));
      field_188414_b.func_177775_a(10, new ResourceLocation("regeneration"), (new Potion(false, 13458603)).func_76390_b("effect.regeneration").func_76399_b(7, 0).func_76404_a(0.25D).func_188413_j());
      field_188414_b.func_177775_a(11, new ResourceLocation("resistance"), (new Potion(false, 10044730)).func_76390_b("effect.resistance").func_76399_b(6, 1).func_188413_j());
      field_188414_b.func_177775_a(12, new ResourceLocation("fire_resistance"), (new Potion(false, 14981690)).func_76390_b("effect.fireResistance").func_76399_b(7, 1).func_188413_j());
      field_188414_b.func_177775_a(13, new ResourceLocation("water_breathing"), (new Potion(false, 3035801)).func_76390_b("effect.waterBreathing").func_76399_b(0, 2).func_188413_j());
      field_188414_b.func_177775_a(14, new ResourceLocation("invisibility"), (new Potion(false, 8356754)).func_76390_b("effect.invisibility").func_76399_b(0, 1).func_188413_j());
      field_188414_b.func_177775_a(15, new ResourceLocation("blindness"), (new Potion(true, 2039587)).func_76390_b("effect.blindness").func_76399_b(5, 1).func_76404_a(0.25D));
      field_188414_b.func_177775_a(16, new ResourceLocation("night_vision"), (new Potion(false, 2039713)).func_76390_b("effect.nightVision").func_76399_b(4, 1).func_188413_j());
      field_188414_b.func_177775_a(17, new ResourceLocation("hunger"), (new Potion(true, 5797459)).func_76390_b("effect.hunger").func_76399_b(1, 1));
      field_188414_b.func_177775_a(18, new ResourceLocation("weakness"), (new PotionAttackDamage(true, 4738376, -4.0D)).func_76390_b("effect.weakness").func_76399_b(5, 0).func_111184_a(SharedMonsterAttributes.field_111264_e, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.0D, 0));
      field_188414_b.func_177775_a(19, new ResourceLocation("poison"), (new Potion(true, 5149489)).func_76390_b("effect.poison").func_76399_b(6, 0).func_76404_a(0.25D));
      field_188414_b.func_177775_a(20, new ResourceLocation("wither"), (new Potion(true, 3484199)).func_76390_b("effect.wither").func_76399_b(1, 2).func_76404_a(0.25D));
      field_188414_b.func_177775_a(21, new ResourceLocation("health_boost"), (new PotionHealthBoost(false, 16284963)).func_76390_b("effect.healthBoost").func_76399_b(7, 2).func_111184_a(SharedMonsterAttributes.field_111267_a, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0D, 0).func_188413_j());
      field_188414_b.func_177775_a(22, new ResourceLocation("absorption"), (new PotionAbsorption(false, 2445989)).func_76390_b("effect.absorption").func_76399_b(2, 2).func_188413_j());
      field_188414_b.func_177775_a(23, new ResourceLocation("saturation"), (new PotionHealth(false, 16262179)).func_76390_b("effect.saturation").func_188413_j());
      field_188414_b.func_177775_a(24, new ResourceLocation("glowing"), (new Potion(false, 9740385)).func_76390_b("effect.glowing").func_76399_b(4, 2));
      field_188414_b.func_177775_a(25, new ResourceLocation("levitation"), (new Potion(true, 13565951)).func_76390_b("effect.levitation").func_76399_b(3, 2));
      field_188414_b.func_177775_a(26, new ResourceLocation("luck"), (new Potion(false, 3381504)).func_76390_b("effect.luck").func_76399_b(5, 2).func_188413_j().func_111184_a(SharedMonsterAttributes.field_188792_h, "03C3C89D-7037-4B42-869F-B146BCB64D2E", 1.0D, 0));
      field_188414_b.func_177775_a(27, new ResourceLocation("unluck"), (new Potion(true, 12624973)).func_76390_b("effect.unluck").func_76399_b(6, 2).func_111184_a(SharedMonsterAttributes.field_188792_h, "CC5AF142-2BD2-4215-B636-2605AED11727", -1.0D, 0));
   }
}
