package net.minecraft.util;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class MouseHelper {
   public int field_74377_a;
   public int field_74375_b;

   public void func_74372_a() {
      Mouse.setGrabbed(true);
      this.field_74377_a = 0;
      this.field_74375_b = 0;
   }

   public void func_74373_b() {
      Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
      Mouse.setGrabbed(false);
   }

   public void func_74374_c() {
      this.field_74377_a = Mouse.getDX();
      this.field_74375_b = Mouse.getDY();
   }
}
