package net.minecraft.util;

import net.minecraft.client.Minecraft;

public class Timer {
   public int field_74280_b;
   public float field_194147_b;
   public float field_194148_c;
   private long field_74277_g;
   private float field_194149_e;

   public Timer(float p_i1018_1_) {
      this.field_194149_e = 1000.0F / p_i1018_1_;
      this.field_74277_g = Minecraft.func_71386_F();
   }

   public void func_74275_a() {
      long i = Minecraft.func_71386_F();
      this.field_194148_c = (float)(i - this.field_74277_g) / this.field_194149_e;
      this.field_74277_g = i;
      this.field_194147_b += this.field_194148_c;
      this.field_74280_b = (int)this.field_194147_b;
      this.field_194147_b -= (float)this.field_74280_b;
   }
}
