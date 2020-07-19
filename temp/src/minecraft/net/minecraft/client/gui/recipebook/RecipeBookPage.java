package net.minecraft.client.gui.recipebook;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButtonToggle;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.stats.RecipeBook;

public class RecipeBookPage {
   private List<GuiButtonRecipe> field_193743_h = Lists.<GuiButtonRecipe>newArrayListWithCapacity(20);
   private GuiButtonRecipe field_194201_b;
   private GuiRecipeOverlay field_194202_c = new GuiRecipeOverlay();
   private Minecraft field_193754_s;
   private List<IRecipeUpdateListener> field_193757_v = Lists.<IRecipeUpdateListener>newArrayList();
   private List<RecipeList> field_194203_f;
   private GuiButtonToggle field_193740_e;
   private GuiButtonToggle field_193741_f;
   private int field_193737_b;
   private int field_193738_c;
   private RecipeBook field_194204_k;
   private IRecipe field_194205_l;
   private RecipeList field_194206_m;

   public RecipeBookPage() {
      for(int i = 0; i < 20; ++i) {
         this.field_193743_h.add(new GuiButtonRecipe());
      }

   }

   public void func_194194_a(Minecraft p_194194_1_, int p_194194_2_, int p_194194_3_) {
      this.field_193754_s = p_194194_1_;
      this.field_194204_k = p_194194_1_.field_71439_g.func_192035_E();

      for(int i = 0; i < this.field_193743_h.size(); ++i) {
         ((GuiButtonRecipe)this.field_193743_h.get(i)).func_191770_c(p_194194_2_ + 11 + 25 * (i % 5), p_194194_3_ + 31 + 25 * (i / 5));
      }

      this.field_193740_e = new GuiButtonToggle(0, p_194194_2_ + 93, p_194194_3_ + 137, 12, 17, false);
      this.field_193740_e.func_191751_a(1, 208, 13, 18, GuiRecipeBook.field_191894_a);
      this.field_193741_f = new GuiButtonToggle(0, p_194194_2_ + 38, p_194194_3_ + 137, 12, 17, true);
      this.field_193741_f.func_191751_a(1, 208, 13, 18, GuiRecipeBook.field_191894_a);
   }

   public void func_193732_a(GuiRecipeBook p_193732_1_) {
      this.field_193757_v.remove(p_193732_1_);
      this.field_193757_v.add(p_193732_1_);
   }

   public void func_194192_a(List<RecipeList> p_194192_1_, boolean p_194192_2_) {
      this.field_194203_f = p_194192_1_;
      this.field_193737_b = (int)Math.ceil((double)p_194192_1_.size() / 20.0D);
      if (this.field_193737_b <= this.field_193738_c || p_194192_2_) {
         this.field_193738_c = 0;
      }

      this.func_194198_d();
   }

   private void func_194198_d() {
      int i = 20 * this.field_193738_c;

      for(int j = 0; j < this.field_193743_h.size(); ++j) {
         GuiButtonRecipe guibuttonrecipe = this.field_193743_h.get(j);
         if (i + j < this.field_194203_f.size()) {
            RecipeList recipelist = this.field_194203_f.get(i + j);
            guibuttonrecipe.func_193928_a(recipelist, this, this.field_194204_k);
            guibuttonrecipe.field_146125_m = true;
         } else {
            guibuttonrecipe.field_146125_m = false;
         }
      }

      this.func_194197_e();
   }

   private void func_194197_e() {
      this.field_193740_e.field_146125_m = this.field_193737_b > 1 && this.field_193738_c < this.field_193737_b - 1;
      this.field_193741_f.field_146125_m = this.field_193737_b > 1 && this.field_193738_c > 0;
   }

   public void func_194191_a(int p_194191_1_, int p_194191_2_, int p_194191_3_, int p_194191_4_, float p_194191_5_) {
      if (this.field_193737_b > 1) {
         String s = this.field_193738_c + 1 + "/" + this.field_193737_b;
         int i = this.field_193754_s.field_71466_p.func_78256_a(s);
         this.field_193754_s.field_71466_p.func_78276_b(s, p_194191_1_ - i / 2 + 73, p_194191_2_ + 141, -1);
      }

      RenderHelper.func_74518_a();
      this.field_194201_b = null;

      for(GuiButtonRecipe guibuttonrecipe : this.field_193743_h) {
         guibuttonrecipe.func_191745_a(this.field_193754_s, p_194191_3_, p_194191_4_, p_194191_5_);
         if (guibuttonrecipe.field_146125_m && guibuttonrecipe.func_146115_a()) {
            this.field_194201_b = guibuttonrecipe;
         }
      }

      this.field_193741_f.func_191745_a(this.field_193754_s, p_194191_3_, p_194191_4_, p_194191_5_);
      this.field_193740_e.func_191745_a(this.field_193754_s, p_194191_3_, p_194191_4_, p_194191_5_);
      this.field_194202_c.func_191842_a(p_194191_3_, p_194191_4_, p_194191_5_);
   }

   public void func_193721_a(int p_193721_1_, int p_193721_2_) {
      if (this.field_193754_s.field_71462_r != null && this.field_194201_b != null && !this.field_194202_c.func_191839_a()) {
         this.field_193754_s.field_71462_r.func_146283_a(this.field_194201_b.func_191772_a(this.field_193754_s.field_71462_r), p_193721_1_, p_193721_2_);
      }

   }

   @Nullable
   public IRecipe func_194193_a() {
      return this.field_194205_l;
   }

   @Nullable
   public RecipeList func_194199_b() {
      return this.field_194206_m;
   }

   public void func_194200_c() {
      this.field_194202_c.func_192999_a(false);
   }

   public boolean func_194196_a(int p_194196_1_, int p_194196_2_, int p_194196_3_, int p_194196_4_, int p_194196_5_, int p_194196_6_, int p_194196_7_) {
      this.field_194205_l = null;
      this.field_194206_m = null;
      if (this.field_194202_c.func_191839_a()) {
         if (this.field_194202_c.func_193968_a(p_194196_1_, p_194196_2_, p_194196_3_)) {
            this.field_194205_l = this.field_194202_c.func_193967_b();
            this.field_194206_m = this.field_194202_c.func_193971_a();
         } else {
            this.field_194202_c.func_192999_a(false);
         }

         return true;
      } else if (this.field_193740_e.func_146116_c(this.field_193754_s, p_194196_1_, p_194196_2_) && p_194196_3_ == 0) {
         this.field_193740_e.func_146113_a(this.field_193754_s.func_147118_V());
         ++this.field_193738_c;
         this.func_194198_d();
         return true;
      } else if (this.field_193741_f.func_146116_c(this.field_193754_s, p_194196_1_, p_194196_2_) && p_194196_3_ == 0) {
         this.field_193741_f.func_146113_a(this.field_193754_s.func_147118_V());
         --this.field_193738_c;
         this.func_194198_d();
         return true;
      } else {
         for(GuiButtonRecipe guibuttonrecipe : this.field_193743_h) {
            if (guibuttonrecipe.func_146116_c(this.field_193754_s, p_194196_1_, p_194196_2_)) {
               guibuttonrecipe.func_146113_a(this.field_193754_s.func_147118_V());
               if (p_194196_3_ == 0) {
                  this.field_194205_l = guibuttonrecipe.func_193760_e();
                  this.field_194206_m = guibuttonrecipe.func_191771_c();
               } else if (!this.field_194202_c.func_191839_a() && !guibuttonrecipe.func_193929_d()) {
                  this.field_194202_c.func_191845_a(this.field_193754_s, guibuttonrecipe.func_191771_c(), guibuttonrecipe.field_146128_h, guibuttonrecipe.field_146129_i, p_194196_4_ + p_194196_6_ / 2, p_194196_5_ + 13 + p_194196_7_ / 2, (float)guibuttonrecipe.func_146117_b(), this.field_194204_k);
               }

               return true;
            }
         }

         return false;
      }
   }

   public void func_194195_a(List<IRecipe> p_194195_1_) {
      for(IRecipeUpdateListener irecipeupdatelistener : this.field_193757_v) {
         irecipeupdatelistener.func_193001_a(p_194195_1_);
      }

   }
}
