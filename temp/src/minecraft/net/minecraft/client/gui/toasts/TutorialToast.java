package net.minecraft.client.gui.toasts;

import javax.annotation.Nullable;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class TutorialToast implements IToast {
   private final TutorialToast.Icons field_193671_c;
   private final String field_193672_d;
   private final String field_193673_e;
   private IToast.Visibility field_193674_f = IToast.Visibility.SHOW;
   private long field_193675_g;
   private float field_193676_h;
   private float field_193677_i;
   private final boolean field_193678_j;

   public TutorialToast(TutorialToast.Icons p_i47487_1_, ITextComponent p_i47487_2_, @Nullable ITextComponent p_i47487_3_, boolean p_i47487_4_) {
      this.field_193671_c = p_i47487_1_;
      this.field_193672_d = p_i47487_2_.func_150254_d();
      this.field_193673_e = p_i47487_3_ == null ? null : p_i47487_3_.func_150254_d();
      this.field_193678_j = p_i47487_4_;
   }

   public IToast.Visibility func_193653_a(GuiToast p_193653_1_, long p_193653_2_) {
      p_193653_1_.func_192989_b().func_110434_K().func_110577_a(field_193654_a);
      GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
      p_193653_1_.func_73729_b(0, 0, 0, 96, 160, 32);
      this.field_193671_c.func_193697_a(p_193653_1_, 6, 6);
      if (this.field_193673_e == null) {
         p_193653_1_.func_192989_b().field_71466_p.func_78276_b(this.field_193672_d, 30, 12, -11534256);
      } else {
         p_193653_1_.func_192989_b().field_71466_p.func_78276_b(this.field_193672_d, 30, 7, -11534256);
         p_193653_1_.func_192989_b().field_71466_p.func_78276_b(this.field_193673_e, 30, 18, -16777216);
      }

      if (this.field_193678_j) {
         Gui.func_73734_a(3, 28, 157, 29, -1);
         float f = (float)MathHelper.func_151238_b((double)this.field_193676_h, (double)this.field_193677_i, (double)((float)(p_193653_2_ - this.field_193675_g) / 100.0F));
         int i;
         if (this.field_193677_i >= this.field_193676_h) {
            i = -16755456;
         } else {
            i = -11206656;
         }

         Gui.func_73734_a(3, 28, (int)(3.0F + 154.0F * f), 29, i);
         this.field_193676_h = f;
         this.field_193675_g = p_193653_2_;
      }

      return this.field_193674_f;
   }

   public void func_193670_a() {
      this.field_193674_f = IToast.Visibility.HIDE;
   }

   public void func_193669_a(float p_193669_1_) {
      this.field_193677_i = p_193669_1_;
   }

   public static enum Icons {
      MOVEMENT_KEYS(0, 0),
      MOUSE(1, 0),
      TREE(2, 0),
      RECIPE_BOOK(0, 1),
      WOODEN_PLANKS(1, 1);

      private final int field_193703_f;
      private final int field_193704_g;

      private Icons(int p_i47576_3_, int p_i47576_4_) {
         this.field_193703_f = p_i47576_3_;
         this.field_193704_g = p_i47576_4_;
      }

      public void func_193697_a(Gui p_193697_1_, int p_193697_2_, int p_193697_3_) {
         GlStateManager.func_179147_l();
         p_193697_1_.func_73729_b(p_193697_2_, p_193697_3_, 176 + this.field_193703_f * 20, this.field_193704_g * 20, 20, 20);
         GlStateManager.func_179147_l();
      }
   }
}
