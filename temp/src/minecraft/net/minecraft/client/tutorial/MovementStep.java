package net.minecraft.client.tutorial;

import net.minecraft.client.gui.toasts.TutorialToast;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;

public class MovementStep implements ITutorialStep {
   private static final ITextComponent field_193254_a = new TextComponentTranslation("tutorial.move.title", new Object[]{Tutorial.func_193291_a("forward"), Tutorial.func_193291_a("left"), Tutorial.func_193291_a("back"), Tutorial.func_193291_a("right")});
   private static final ITextComponent field_193255_b = new TextComponentTranslation("tutorial.move.description", new Object[]{Tutorial.func_193291_a("jump")});
   private static final ITextComponent field_193256_c = new TextComponentTranslation("tutorial.look.title", new Object[0]);
   private static final ITextComponent field_193257_d = new TextComponentTranslation("tutorial.look.description", new Object[0]);
   private final Tutorial field_193258_e;
   private TutorialToast field_193259_f;
   private TutorialToast field_193260_g;
   private int field_193261_h;
   private int field_193262_i;
   private int field_193263_j;
   private boolean field_193264_k;
   private boolean field_193265_l;
   private int field_193266_m = -1;
   private int field_193267_n = -1;

   public MovementStep(Tutorial p_i47581_1_) {
      this.field_193258_e = p_i47581_1_;
   }

   public void func_193245_a() {
      ++this.field_193261_h;
      if (this.field_193264_k) {
         ++this.field_193262_i;
         this.field_193264_k = false;
      }

      if (this.field_193265_l) {
         ++this.field_193263_j;
         this.field_193265_l = false;
      }

      if (this.field_193266_m == -1 && this.field_193262_i > 40) {
         if (this.field_193259_f != null) {
            this.field_193259_f.func_193670_a();
            this.field_193259_f = null;
         }

         this.field_193266_m = this.field_193261_h;
      }

      if (this.field_193267_n == -1 && this.field_193263_j > 40) {
         if (this.field_193260_g != null) {
            this.field_193260_g.func_193670_a();
            this.field_193260_g = null;
         }

         this.field_193267_n = this.field_193261_h;
      }

      if (this.field_193266_m != -1 && this.field_193267_n != -1) {
         if (this.field_193258_e.func_194072_f() == GameType.SURVIVAL) {
            this.field_193258_e.func_193292_a(TutorialSteps.FIND_TREE);
         } else {
            this.field_193258_e.func_193292_a(TutorialSteps.NONE);
         }
      }

      if (this.field_193259_f != null) {
         this.field_193259_f.func_193669_a((float)this.field_193262_i / 40.0F);
      }

      if (this.field_193260_g != null) {
         this.field_193260_g.func_193669_a((float)this.field_193263_j / 40.0F);
      }

      if (this.field_193261_h >= 100) {
         if (this.field_193266_m == -1 && this.field_193259_f == null) {
            this.field_193259_f = new TutorialToast(TutorialToast.Icons.MOVEMENT_KEYS, field_193254_a, field_193255_b, true);
            this.field_193258_e.func_193295_e().func_193033_an().func_192988_a(this.field_193259_f);
         } else if (this.field_193266_m != -1 && this.field_193261_h - this.field_193266_m >= 20 && this.field_193267_n == -1 && this.field_193260_g == null) {
            this.field_193260_g = new TutorialToast(TutorialToast.Icons.MOUSE, field_193256_c, field_193257_d, true);
            this.field_193258_e.func_193295_e().func_193033_an().func_192988_a(this.field_193260_g);
         }
      }

   }

   public void func_193248_b() {
      if (this.field_193259_f != null) {
         this.field_193259_f.func_193670_a();
         this.field_193259_f = null;
      }

      if (this.field_193260_g != null) {
         this.field_193260_g.func_193670_a();
         this.field_193260_g = null;
      }

   }

   public void func_193247_a(MovementInput p_193247_1_) {
      if (p_193247_1_.field_187255_c || p_193247_1_.field_187256_d || p_193247_1_.field_187257_e || p_193247_1_.field_187258_f || p_193247_1_.field_78901_c) {
         this.field_193264_k = true;
      }

   }

   public void func_193249_a(MouseHelper p_193249_1_) {
      if ((double)MathHelper.func_76130_a(p_193249_1_.field_74377_a) > 0.01D || (double)MathHelper.func_76130_a(p_193249_1_.field_74375_b) > 0.01D) {
         this.field_193265_l = true;
      }

   }
}
