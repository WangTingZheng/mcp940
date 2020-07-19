package net.minecraft.client.network;

import net.minecraft.client.Minecraft;

public class LanServerInfo {
   private final String field_77492_a;
   private final String field_77490_b;
   private long field_77491_c;

   public LanServerInfo(String p_i47130_1_, String p_i47130_2_) {
      this.field_77492_a = p_i47130_1_;
      this.field_77490_b = p_i47130_2_;
      this.field_77491_c = Minecraft.func_71386_F();
   }

   public String func_77487_a() {
      return this.field_77492_a;
   }

   public String func_77488_b() {
      return this.field_77490_b;
   }

   public void func_77489_c() {
      this.field_77491_c = Minecraft.func_71386_F();
   }
}
