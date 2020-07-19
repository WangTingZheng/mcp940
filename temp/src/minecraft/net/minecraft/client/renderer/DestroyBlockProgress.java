package net.minecraft.client.renderer;

import net.minecraft.util.math.BlockPos;

public class DestroyBlockProgress {
   private final int field_73115_a;
   private final BlockPos field_180247_b;
   private int field_73112_e;
   private int field_82745_f;

   public DestroyBlockProgress(int p_i45925_1_, BlockPos p_i45925_2_) {
      this.field_73115_a = p_i45925_1_;
      this.field_180247_b = p_i45925_2_;
   }

   public BlockPos func_180246_b() {
      return this.field_180247_b;
   }

   public void func_73107_a(int p_73107_1_) {
      if (p_73107_1_ > 10) {
         p_73107_1_ = 10;
      }

      this.field_73112_e = p_73107_1_;
   }

   public int func_73106_e() {
      return this.field_73112_e;
   }

   public void func_82744_b(int p_82744_1_) {
      this.field_82745_f = p_82744_1_;
   }

   public int func_82743_f() {
      return this.field_82745_f;
   }
}
