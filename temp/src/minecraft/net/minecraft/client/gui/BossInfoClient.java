package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketUpdateBossInfo;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;

public class BossInfoClient extends BossInfo {
   protected float field_186766_h;
   protected long field_186767_i;

   public BossInfoClient(SPacketUpdateBossInfo p_i46605_1_) {
      super(p_i46605_1_.func_186908_a(), p_i46605_1_.func_186907_c(), p_i46605_1_.func_186900_e(), p_i46605_1_.func_186904_f());
      this.field_186766_h = p_i46605_1_.func_186906_d();
      this.field_186750_b = p_i46605_1_.func_186906_d();
      this.field_186767_i = Minecraft.func_71386_F();
      this.func_186741_a(p_i46605_1_.func_186909_g());
      this.func_186742_b(p_i46605_1_.func_186910_h());
      this.func_186743_c(p_i46605_1_.func_186901_i());
   }

   public void func_186735_a(float p_186735_1_) {
      this.field_186750_b = this.func_186738_f();
      this.field_186766_h = p_186735_1_;
      this.field_186767_i = Minecraft.func_71386_F();
   }

   public float func_186738_f() {
      long i = Minecraft.func_71386_F() - this.field_186767_i;
      float f = MathHelper.func_76131_a((float)i / 100.0F, 0.0F, 1.0F);
      return this.field_186750_b + (this.field_186766_h - this.field_186750_b) * f;
   }

   public void func_186765_a(SPacketUpdateBossInfo p_186765_1_) {
      switch(p_186765_1_.func_186902_b()) {
      case UPDATE_NAME:
         this.func_186739_a(p_186765_1_.func_186907_c());
         break;
      case UPDATE_PCT:
         this.func_186735_a(p_186765_1_.func_186906_d());
         break;
      case UPDATE_STYLE:
         this.func_186745_a(p_186765_1_.func_186900_e());
         this.func_186746_a(p_186765_1_.func_186904_f());
         break;
      case UPDATE_PROPERTIES:
         this.func_186741_a(p_186765_1_.func_186909_g());
         this.func_186742_b(p_186765_1_.func_186910_h());
      }

   }
}
