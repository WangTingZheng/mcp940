package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public abstract class PhaseBase implements IPhase {
   protected final EntityDragon field_188661_a;

   public PhaseBase(EntityDragon p_i46795_1_) {
      this.field_188661_a = p_i46795_1_;
   }

   public boolean func_188654_a() {
      return false;
   }

   public void func_188657_b() {
   }

   public void func_188659_c() {
   }

   public void func_188655_a(EntityEnderCrystal p_188655_1_, BlockPos p_188655_2_, DamageSource p_188655_3_, @Nullable EntityPlayer p_188655_4_) {
   }

   public void func_188660_d() {
   }

   public void func_188658_e() {
   }

   public float func_188651_f() {
      return 0.6F;
   }

   @Nullable
   public Vec3d func_188650_g() {
      return null;
   }

   public float func_188656_a(MultiPartEntityPart p_188656_1_, DamageSource p_188656_2_, float p_188656_3_) {
      return p_188656_3_;
   }

   public float func_188653_h() {
      float f = MathHelper.func_76133_a(this.field_188661_a.field_70159_w * this.field_188661_a.field_70159_w + this.field_188661_a.field_70179_y * this.field_188661_a.field_70179_y) + 1.0F;
      float f1 = Math.min(f, 40.0F);
      return 0.7F / f1 / f;
   }
}
