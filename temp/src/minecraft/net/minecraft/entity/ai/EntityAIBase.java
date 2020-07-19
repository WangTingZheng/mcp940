package net.minecraft.entity.ai;

public abstract class EntityAIBase {
   private int field_75254_a;

   public abstract boolean func_75250_a();

   public boolean func_75253_b() {
      return this.func_75250_a();
   }

   public boolean func_75252_g() {
      return true;
   }

   public void func_75249_e() {
   }

   public void func_75251_c() {
   }

   public void func_75246_d() {
   }

   public void func_75248_a(int p_75248_1_) {
      this.field_75254_a = p_75248_1_;
   }

   public int func_75247_h() {
      return this.field_75254_a;
   }
}
