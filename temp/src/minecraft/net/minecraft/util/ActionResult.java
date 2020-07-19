package net.minecraft.util;

public class ActionResult<T> {
   private final EnumActionResult field_188399_a;
   private final T field_188400_b;

   public ActionResult(EnumActionResult p_i46821_1_, T p_i46821_2_) {
      this.field_188399_a = p_i46821_1_;
      this.field_188400_b = p_i46821_2_;
   }

   public EnumActionResult func_188397_a() {
      return this.field_188399_a;
   }

   public T func_188398_b() {
      return this.field_188400_b;
   }
}
