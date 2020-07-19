package net.minecraft.util;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.ITextComponent;

public class CombatEntry {
   private final DamageSource field_94569_a;
   private final int field_94567_b;
   private final float field_94568_c;
   private final float field_94565_d;
   private final String field_94566_e;
   private final float field_94564_f;

   public CombatEntry(DamageSource p_i1564_1_, int p_i1564_2_, float p_i1564_3_, float p_i1564_4_, String p_i1564_5_, float p_i1564_6_) {
      this.field_94569_a = p_i1564_1_;
      this.field_94567_b = p_i1564_2_;
      this.field_94568_c = p_i1564_4_;
      this.field_94565_d = p_i1564_3_;
      this.field_94566_e = p_i1564_5_;
      this.field_94564_f = p_i1564_6_;
   }

   public DamageSource func_94560_a() {
      return this.field_94569_a;
   }

   public float func_94563_c() {
      return this.field_94568_c;
   }

   public boolean func_94559_f() {
      return this.field_94569_a.func_76346_g() instanceof EntityLivingBase;
   }

   @Nullable
   public String func_94562_g() {
      return this.field_94566_e;
   }

   @Nullable
   public ITextComponent func_151522_h() {
      return this.func_94560_a().func_76346_g() == null ? null : this.func_94560_a().func_76346_g().func_145748_c_();
   }

   public float func_94561_i() {
      return this.field_94569_a == DamageSource.field_76380_i ? Float.MAX_VALUE : this.field_94564_f;
   }
}
