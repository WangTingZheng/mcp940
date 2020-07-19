package net.minecraft.world;

import javax.annotation.concurrent.Immutable;
import net.minecraft.nbt.NBTTagCompound;

@Immutable
public class LockCode {
   public static final LockCode field_180162_a = new LockCode("");
   private final String field_180161_b;

   public LockCode(String p_i45903_1_) {
      this.field_180161_b = p_i45903_1_;
   }

   public boolean func_180160_a() {
      return this.field_180161_b == null || this.field_180161_b.isEmpty();
   }

   public String func_180159_b() {
      return this.field_180161_b;
   }

   public void func_180157_a(NBTTagCompound p_180157_1_) {
      p_180157_1_.func_74778_a("Lock", this.field_180161_b);
   }

   public static LockCode func_180158_b(NBTTagCompound p_180158_0_) {
      if (p_180158_0_.func_150297_b("Lock", 8)) {
         String s = p_180158_0_.func_74779_i("Lock");
         return new LockCode(s);
      } else {
         return field_180162_a;
      }
   }
}
