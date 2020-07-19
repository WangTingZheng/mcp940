package net.minecraft.util;

public class TupleIntJsonSerializable {
   private int field_151192_a;
   private IJsonSerializable field_151191_b;

   public int func_151189_a() {
      return this.field_151192_a;
   }

   public void func_151188_a(int p_151188_1_) {
      this.field_151192_a = p_151188_1_;
   }

   public <T extends IJsonSerializable> T func_151187_b() {
      return (T)this.field_151191_b;
   }

   public void func_151190_a(IJsonSerializable p_151190_1_) {
      this.field_151191_b = p_151190_1_;
   }
}
