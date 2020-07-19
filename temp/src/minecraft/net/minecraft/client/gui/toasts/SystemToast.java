package net.minecraft.client.gui.toasts;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.ITextComponent;

public class SystemToast implements IToast {
   private final SystemToast.Type field_193659_c;
   private String field_193660_d;
   private String field_193661_e;
   private long field_193662_f;
   private boolean field_193663_g;

   public SystemToast(SystemToast.Type p_i47488_1_, ITextComponent p_i47488_2_, @Nullable ITextComponent p_i47488_3_) {
      this.field_193659_c = p_i47488_1_;
      this.field_193660_d = p_i47488_2_.func_150260_c();
      this.field_193661_e = p_i47488_3_ == null ? null : p_i47488_3_.func_150260_c();
   }

   public IToast.Visibility func_193653_a(GuiToast p_193653_1_, long p_193653_2_) {
      if (this.field_193663_g) {
         this.field_193662_f = p_193653_2_;
         this.field_193663_g = false;
      }

      p_193653_1_.func_192989_b().func_110434_K().func_110577_a(field_193654_a);
      GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
      p_193653_1_.func_73729_b(0, 0, 0, 64, 160, 32);
      if (this.field_193661_e == null) {
         p_193653_1_.func_192989_b().field_71466_p.func_78276_b(this.field_193660_d, 18, 12, -256);
      } else {
         p_193653_1_.func_192989_b().field_71466_p.func_78276_b(this.field_193660_d, 18, 7, -256);
         p_193653_1_.func_192989_b().field_71466_p.func_78276_b(this.field_193661_e, 18, 18, -1);
      }

      return p_193653_2_ - this.field_193662_f < 5000L ? IToast.Visibility.SHOW : IToast.Visibility.HIDE;
   }

   public void func_193656_a(ITextComponent p_193656_1_, @Nullable ITextComponent p_193656_2_) {
      this.field_193660_d = p_193656_1_.func_150260_c();
      this.field_193661_e = p_193656_2_ == null ? null : p_193656_2_.func_150260_c();
      this.field_193663_g = true;
   }

   public SystemToast.Type func_193652_b() {
      return this.field_193659_c;
   }

   public static void func_193657_a(GuiToast p_193657_0_, SystemToast.Type p_193657_1_, ITextComponent p_193657_2_, @Nullable ITextComponent p_193657_3_) {
      SystemToast systemtoast = (SystemToast)p_193657_0_.func_192990_a(SystemToast.class, p_193657_1_);
      if (systemtoast == null) {
         p_193657_0_.func_192988_a(new SystemToast(p_193657_1_, p_193657_2_, p_193657_3_));
      } else {
         systemtoast.func_193656_a(p_193657_2_, p_193657_3_);
      }

   }

   public static enum Type {
      TUTORIAL_HINT,
      NARRATOR_TOGGLE;
   }
}
