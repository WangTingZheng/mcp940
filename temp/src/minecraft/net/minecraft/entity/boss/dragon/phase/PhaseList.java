package net.minecraft.entity.boss.dragon.phase;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import net.minecraft.entity.boss.EntityDragon;

public class PhaseList<T extends IPhase> {
   private static PhaseList<?>[] field_188752_l = new PhaseList[0];
   public static final PhaseList<PhaseHoldingPattern> field_188741_a = func_188735_a(PhaseHoldingPattern.class, "HoldingPattern");
   public static final PhaseList<PhaseStrafePlayer> field_188742_b = func_188735_a(PhaseStrafePlayer.class, "StrafePlayer");
   public static final PhaseList<PhaseLandingApproach> field_188743_c = func_188735_a(PhaseLandingApproach.class, "LandingApproach");
   public static final PhaseList<PhaseLanding> field_188744_d = func_188735_a(PhaseLanding.class, "Landing");
   public static final PhaseList<PhaseTakeoff> field_188745_e = func_188735_a(PhaseTakeoff.class, "Takeoff");
   public static final PhaseList<PhaseSittingFlaming> field_188746_f = func_188735_a(PhaseSittingFlaming.class, "SittingFlaming");
   public static final PhaseList<PhaseSittingScanning> field_188747_g = func_188735_a(PhaseSittingScanning.class, "SittingScanning");
   public static final PhaseList<PhaseSittingAttacking> field_188748_h = func_188735_a(PhaseSittingAttacking.class, "SittingAttacking");
   public static final PhaseList<PhaseChargingPlayer> field_188749_i = func_188735_a(PhaseChargingPlayer.class, "ChargingPlayer");
   public static final PhaseList<PhaseDying> field_188750_j = func_188735_a(PhaseDying.class, "Dying");
   public static final PhaseList<PhaseHover> field_188751_k = func_188735_a(PhaseHover.class, "Hover");
   private final Class<? extends IPhase> field_188753_m;
   private final int field_188754_n;
   private final String field_188755_o;

   private PhaseList(int p_i46782_1_, Class<? extends IPhase> p_i46782_2_, String p_i46782_3_) {
      this.field_188754_n = p_i46782_1_;
      this.field_188753_m = p_i46782_2_;
      this.field_188755_o = p_i46782_3_;
   }

   public IPhase func_188736_a(EntityDragon p_188736_1_) {
      try {
         Constructor<? extends IPhase> constructor = this.func_188737_a();
         return constructor.newInstance(p_188736_1_);
      } catch (Exception exception) {
         throw new Error(exception);
      }
   }

   protected Constructor<? extends IPhase> func_188737_a() throws NoSuchMethodException {
      return this.field_188753_m.getConstructor(EntityDragon.class);
   }

   public int func_188740_b() {
      return this.field_188754_n;
   }

   public String toString() {
      return this.field_188755_o + " (#" + this.field_188754_n + ")";
   }

   public static PhaseList<?> func_188738_a(int p_188738_0_) {
      return p_188738_0_ >= 0 && p_188738_0_ < field_188752_l.length ? field_188752_l[p_188738_0_] : field_188741_a;
   }

   public static int func_188739_c() {
      return field_188752_l.length;
   }

   private static <T extends IPhase> PhaseList<T> func_188735_a(Class<T> p_188735_0_, String p_188735_1_) {
      PhaseList<T> phaselist = new PhaseList<T>(field_188752_l.length, p_188735_0_, p_188735_1_);
      field_188752_l = (PhaseList[])Arrays.copyOf(field_188752_l, field_188752_l.length + 1);
      field_188752_l[phaselist.func_188740_b()] = phaselist;
      return phaselist;
   }
}
