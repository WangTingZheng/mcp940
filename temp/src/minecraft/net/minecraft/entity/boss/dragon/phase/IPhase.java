package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public interface IPhase {
   boolean func_188654_a();

   void func_188657_b();

   void func_188659_c();

   void func_188655_a(EntityEnderCrystal var1, BlockPos var2, DamageSource var3, @Nullable EntityPlayer var4);

   void func_188660_d();

   void func_188658_e();

   float func_188651_f();

   float func_188653_h();

   PhaseList<? extends IPhase> func_188652_i();

   @Nullable
   Vec3d func_188650_g();

   float func_188656_a(MultiPartEntityPart var1, DamageSource var2, float var3);
}
