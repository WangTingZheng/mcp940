package net.minecraft.world;

import java.util.UUID;
import net.minecraft.util.text.ITextComponent;

public abstract class BossInfo {
   private final UUID field_186756_h;
   protected ITextComponent field_186749_a;
   protected float field_186750_b;
   protected BossInfo.Color field_186751_c;
   protected BossInfo.Overlay field_186752_d;
   protected boolean field_186753_e;
   protected boolean field_186754_f;
   protected boolean field_186755_g;

   public BossInfo(UUID p_i46824_1_, ITextComponent p_i46824_2_, BossInfo.Color p_i46824_3_, BossInfo.Overlay p_i46824_4_) {
      this.field_186756_h = p_i46824_1_;
      this.field_186749_a = p_i46824_2_;
      this.field_186751_c = p_i46824_3_;
      this.field_186752_d = p_i46824_4_;
      this.field_186750_b = 1.0F;
   }

   public UUID func_186737_d() {
      return this.field_186756_h;
   }

   public ITextComponent func_186744_e() {
      return this.field_186749_a;
   }

   public void func_186739_a(ITextComponent p_186739_1_) {
      this.field_186749_a = p_186739_1_;
   }

   public float func_186738_f() {
      return this.field_186750_b;
   }

   public void func_186735_a(float p_186735_1_) {
      this.field_186750_b = p_186735_1_;
   }

   public BossInfo.Color func_186736_g() {
      return this.field_186751_c;
   }

   public void func_186745_a(BossInfo.Color p_186745_1_) {
      this.field_186751_c = p_186745_1_;
   }

   public BossInfo.Overlay func_186740_h() {
      return this.field_186752_d;
   }

   public void func_186746_a(BossInfo.Overlay p_186746_1_) {
      this.field_186752_d = p_186746_1_;
   }

   public boolean func_186734_i() {
      return this.field_186753_e;
   }

   public BossInfo func_186741_a(boolean p_186741_1_) {
      this.field_186753_e = p_186741_1_;
      return this;
   }

   public boolean func_186747_j() {
      return this.field_186754_f;
   }

   public BossInfo func_186742_b(boolean p_186742_1_) {
      this.field_186754_f = p_186742_1_;
      return this;
   }

   public BossInfo func_186743_c(boolean p_186743_1_) {
      this.field_186755_g = p_186743_1_;
      return this;
   }

   public boolean func_186748_k() {
      return this.field_186755_g;
   }

   public static enum Color {
      PINK,
      BLUE,
      RED,
      GREEN,
      YELLOW,
      PURPLE,
      WHITE;
   }

   public static enum Overlay {
      PROGRESS,
      NOTCHED_6,
      NOTCHED_10,
      NOTCHED_12,
      NOTCHED_20;
   }
}
