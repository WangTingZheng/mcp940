package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.TreeMap;
import java.util.Map.Entry;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GuiSnooper extends GuiScreen {
   private final GuiScreen field_146608_a;
   private final GameSettings field_146603_f;
   private final java.util.List<String> field_146604_g = Lists.<String>newArrayList();
   private final java.util.List<String> field_146609_h = Lists.<String>newArrayList();
   private String field_146610_i;
   private String[] field_146607_r;
   private GuiSnooper.List field_146606_s;
   private GuiButton field_146605_t;

   public GuiSnooper(GuiScreen p_i1061_1_, GameSettings p_i1061_2_) {
      this.field_146608_a = p_i1061_1_;
      this.field_146603_f = p_i1061_2_;
   }

   public void func_73866_w_() {
      this.field_146610_i = I18n.func_135052_a("options.snooper.title");
      String s = I18n.func_135052_a("options.snooper.desc");
      java.util.List<String> list = Lists.<String>newArrayList();

      for(String s1 : this.field_146289_q.func_78271_c(s, this.field_146294_l - 30)) {
         list.add(s1);
      }

      this.field_146607_r = (String[])list.toArray(new String[list.size()]);
      this.field_146604_g.clear();
      this.field_146609_h.clear();
      this.field_146605_t = this.func_189646_b(new GuiButton(1, this.field_146294_l / 2 - 152, this.field_146295_m - 30, 150, 20, this.field_146603_f.func_74297_c(GameSettings.Options.SNOOPER_ENABLED)));
      this.field_146292_n.add(new GuiButton(2, this.field_146294_l / 2 + 2, this.field_146295_m - 30, 150, 20, I18n.func_135052_a("gui.done")));
      boolean flag = this.field_146297_k.func_71401_C() != null && this.field_146297_k.func_71401_C().func_80003_ah() != null;

      for(Entry<String, String> entry : (new TreeMap(this.field_146297_k.func_71378_E().func_76465_c())).entrySet()) {
         this.field_146604_g.add((flag ? "C " : "") + (String)entry.getKey());
         this.field_146609_h.add(this.field_146289_q.func_78269_a(entry.getValue(), this.field_146294_l - 220));
      }

      if (flag) {
         for(Entry<String, String> entry1 : (new TreeMap(this.field_146297_k.func_71401_C().func_80003_ah().func_76465_c())).entrySet()) {
            this.field_146604_g.add("S " + (String)entry1.getKey());
            this.field_146609_h.add(this.field_146289_q.func_78269_a(entry1.getValue(), this.field_146294_l - 220));
         }
      }

      this.field_146606_s = new GuiSnooper.List();
   }

   public void func_146274_d() throws IOException {
      super.func_146274_d();
      this.field_146606_s.func_178039_p();
   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
      if (p_146284_1_.field_146124_l) {
         if (p_146284_1_.field_146127_k == 2) {
            this.field_146603_f.func_74303_b();
            this.field_146603_f.func_74303_b();
            this.field_146297_k.func_147108_a(this.field_146608_a);
         }

         if (p_146284_1_.field_146127_k == 1) {
            this.field_146603_f.func_74306_a(GameSettings.Options.SNOOPER_ENABLED, 1);
            this.field_146605_t.field_146126_j = this.field_146603_f.func_74297_c(GameSettings.Options.SNOOPER_ENABLED);
         }

      }
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.field_146606_s.func_148128_a(p_73863_1_, p_73863_2_, p_73863_3_);
      this.func_73732_a(this.field_146289_q, this.field_146610_i, this.field_146294_l / 2, 8, 16777215);
      int i = 22;

      for(String s : this.field_146607_r) {
         this.func_73732_a(this.field_146289_q, s, this.field_146294_l / 2, i, 8421504);
         i += this.field_146289_q.field_78288_b;
      }

      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   class List extends GuiSlot {
      public List() {
         super(GuiSnooper.this.field_146297_k, GuiSnooper.this.field_146294_l, GuiSnooper.this.field_146295_m, 80, GuiSnooper.this.field_146295_m - 40, GuiSnooper.this.field_146289_q.field_78288_b + 1);
      }

      protected int func_148127_b() {
         return GuiSnooper.this.field_146604_g.size();
      }

      protected void func_148144_a(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_) {
      }

      protected boolean func_148131_a(int p_148131_1_) {
         return false;
      }

      protected void func_148123_a() {
      }

      protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_) {
         GuiSnooper.this.field_146289_q.func_78276_b(GuiSnooper.this.field_146604_g.get(p_192637_1_), 10, p_192637_3_, 16777215);
         GuiSnooper.this.field_146289_q.func_78276_b(GuiSnooper.this.field_146609_h.get(p_192637_1_), 230, p_192637_3_, 16777215);
      }

      protected int func_148137_d() {
         return this.field_148155_a - 10;
      }
   }
}
