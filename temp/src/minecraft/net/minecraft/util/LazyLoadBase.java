package net.minecraft.util;

public abstract class LazyLoadBase<T> {
   private T field_179283_a;
   private boolean field_179282_b;

   public T func_179281_c() {
      if (!this.field_179282_b) {
         this.field_179282_b = true;
         this.field_179283_a = (T)this.func_179280_b();
      }

      return this.field_179283_a;
   }

   protected abstract T func_179280_b();
}
