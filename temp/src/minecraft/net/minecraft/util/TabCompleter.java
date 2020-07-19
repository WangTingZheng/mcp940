package net.minecraft.util;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.util.math.BlockPos;

public abstract class TabCompleter {
   protected final GuiTextField field_186844_a;
   protected final boolean field_186845_b;
   protected boolean field_186846_c;
   protected boolean field_186847_d;
   protected int field_186848_e;
   protected List<String> field_186849_f = Lists.<String>newArrayList();

   public TabCompleter(GuiTextField p_i46597_1_, boolean p_i46597_2_) {
      this.field_186844_a = p_i46597_1_;
      this.field_186845_b = p_i46597_2_;
   }

   public void func_186841_a() {
      if (this.field_186846_c) {
         this.field_186844_a.func_146175_b(0);
         this.field_186844_a.func_146175_b(this.field_186844_a.func_146197_a(-1, this.field_186844_a.func_146198_h(), false) - this.field_186844_a.func_146198_h());
         if (this.field_186848_e >= this.field_186849_f.size()) {
            this.field_186848_e = 0;
         }
      } else {
         int i = this.field_186844_a.func_146197_a(-1, this.field_186844_a.func_146198_h(), false);
         this.field_186849_f.clear();
         this.field_186848_e = 0;
         String s = this.field_186844_a.func_146179_b().substring(0, this.field_186844_a.func_146198_h());
         this.func_186838_a(s);
         if (this.field_186849_f.isEmpty()) {
            return;
         }

         this.field_186846_c = true;
         this.field_186844_a.func_146175_b(i - this.field_186844_a.func_146198_h());
      }

      this.field_186844_a.func_146191_b(this.field_186849_f.get(this.field_186848_e++));
   }

   private void func_186838_a(String p_186838_1_) {
      if (p_186838_1_.length() >= 1) {
         Minecraft.func_71410_x().field_71439_g.field_71174_a.func_147297_a(new CPacketTabComplete(p_186838_1_, this.func_186839_b(), this.field_186845_b));
         this.field_186847_d = true;
      }
   }

   @Nullable
   public abstract BlockPos func_186839_b();

   public void func_186840_a(String... p_186840_1_) {
      if (this.field_186847_d) {
         this.field_186846_c = false;
         this.field_186849_f.clear();

         for(String s : p_186840_1_) {
            if (!s.isEmpty()) {
               this.field_186849_f.add(s);
            }
         }

         String s1 = this.field_186844_a.func_146179_b().substring(this.field_186844_a.func_146197_a(-1, this.field_186844_a.func_146198_h(), false));
         String s2 = org.apache.commons.lang3.StringUtils.getCommonPrefix(p_186840_1_);
         if (!s2.isEmpty() && !s1.equalsIgnoreCase(s2)) {
            this.field_186844_a.func_146175_b(0);
            this.field_186844_a.func_146175_b(this.field_186844_a.func_146197_a(-1, this.field_186844_a.func_146198_h(), false) - this.field_186844_a.func_146198_h());
            this.field_186844_a.func_146191_b(s2);
         } else if (!this.field_186849_f.isEmpty()) {
            this.field_186846_c = true;
            this.func_186841_a();
         }

      }
   }

   public void func_186842_c() {
      this.field_186846_c = false;
   }

   public void func_186843_d() {
      this.field_186847_d = false;
   }
}
