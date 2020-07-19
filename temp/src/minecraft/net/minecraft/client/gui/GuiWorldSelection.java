package net.minecraft.client.gui;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.resources.I18n;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiWorldSelection extends GuiScreen {
   private static final Logger field_184868_g = LogManager.getLogger();
   protected GuiScreen field_184864_a;
   protected String field_184867_f = "Select world";
   private String field_184869_h;
   private GuiButton field_146642_y;
   private GuiButton field_146641_z;
   private GuiButton field_146630_A;
   private GuiButton field_184865_t;
   private GuiListWorldSelection field_184866_u;

   public GuiWorldSelection(GuiScreen p_i46592_1_) {
      this.field_184864_a = p_i46592_1_;
   }

   public void func_73866_w_() {
      this.field_184867_f = I18n.func_135052_a("selectWorld.title");
      this.field_184866_u = new GuiListWorldSelection(this, this.field_146297_k, this.field_146294_l, this.field_146295_m, 32, this.field_146295_m - 64, 36);
      this.func_184862_a();
   }

   public void func_146274_d() throws IOException {
      super.func_146274_d();
      this.field_184866_u.func_178039_p();
   }

   public void func_184862_a() {
      this.field_146641_z = this.func_189646_b(new GuiButton(1, this.field_146294_l / 2 - 154, this.field_146295_m - 52, 150, 20, I18n.func_135052_a("selectWorld.select")));
      this.func_189646_b(new GuiButton(3, this.field_146294_l / 2 + 4, this.field_146295_m - 52, 150, 20, I18n.func_135052_a("selectWorld.create")));
      this.field_146630_A = this.func_189646_b(new GuiButton(4, this.field_146294_l / 2 - 154, this.field_146295_m - 28, 72, 20, I18n.func_135052_a("selectWorld.edit")));
      this.field_146642_y = this.func_189646_b(new GuiButton(2, this.field_146294_l / 2 - 76, this.field_146295_m - 28, 72, 20, I18n.func_135052_a("selectWorld.delete")));
      this.field_184865_t = this.func_189646_b(new GuiButton(5, this.field_146294_l / 2 + 4, this.field_146295_m - 28, 72, 20, I18n.func_135052_a("selectWorld.recreate")));
      this.func_189646_b(new GuiButton(0, this.field_146294_l / 2 + 82, this.field_146295_m - 28, 72, 20, I18n.func_135052_a("gui.cancel")));
      this.field_146641_z.field_146124_l = false;
      this.field_146642_y.field_146124_l = false;
      this.field_146630_A.field_146124_l = false;
      this.field_184865_t.field_146124_l = false;
   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
      if (p_146284_1_.field_146124_l) {
         GuiListWorldSelectionEntry guilistworldselectionentry = this.field_184866_u.func_186794_f();
         if (p_146284_1_.field_146127_k == 2) {
            if (guilistworldselectionentry != null) {
               guilistworldselectionentry.func_186776_b();
            }
         } else if (p_146284_1_.field_146127_k == 1) {
            if (guilistworldselectionentry != null) {
               guilistworldselectionentry.func_186774_a();
            }
         } else if (p_146284_1_.field_146127_k == 3) {
            this.field_146297_k.func_147108_a(new GuiCreateWorld(this));
         } else if (p_146284_1_.field_146127_k == 4) {
            if (guilistworldselectionentry != null) {
               guilistworldselectionentry.func_186778_c();
            }
         } else if (p_146284_1_.field_146127_k == 0) {
            this.field_146297_k.func_147108_a(this.field_184864_a);
         } else if (p_146284_1_.field_146127_k == 5 && guilistworldselectionentry != null) {
            guilistworldselectionentry.func_186779_d();
         }

      }
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.field_184869_h = null;
      this.field_184866_u.func_148128_a(p_73863_1_, p_73863_2_, p_73863_3_);
      this.func_73732_a(this.field_146289_q, this.field_184867_f, this.field_146294_l / 2, 20, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
      if (this.field_184869_h != null) {
         this.func_146283_a(Lists.newArrayList(Splitter.on("\n").split(this.field_184869_h)), p_73863_1_, p_73863_2_);
      }

   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) throws IOException {
      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
      this.field_184866_u.func_148179_a(p_73864_1_, p_73864_2_, p_73864_3_);
   }

   protected void func_146286_b(int p_146286_1_, int p_146286_2_, int p_146286_3_) {
      super.func_146286_b(p_146286_1_, p_146286_2_, p_146286_3_);
      this.field_184866_u.func_148181_b(p_146286_1_, p_146286_2_, p_146286_3_);
   }

   public void func_184861_a(String p_184861_1_) {
      this.field_184869_h = p_184861_1_;
   }

   public void func_184863_a(@Nullable GuiListWorldSelectionEntry p_184863_1_) {
      boolean flag = p_184863_1_ != null;
      this.field_146641_z.field_146124_l = flag;
      this.field_146642_y.field_146124_l = flag;
      this.field_146630_A.field_146124_l = flag;
      this.field_184865_t.field_146124_l = flag;
   }
}
