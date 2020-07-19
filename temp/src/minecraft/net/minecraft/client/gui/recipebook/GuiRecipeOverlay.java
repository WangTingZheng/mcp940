package net.minecraft.client.gui.recipebook;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.stats.RecipeBook;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiRecipeOverlay extends Gui {
   private static final ResourceLocation field_191847_a = new ResourceLocation("textures/gui/recipe_book.png");
   private final List<GuiRecipeOverlay.Button> field_193972_f = Lists.<GuiRecipeOverlay.Button>newArrayList();
   private boolean field_191850_h;
   private int field_191851_i;
   private int field_191852_j;
   private Minecraft field_191853_k;
   private RecipeList field_191848_f;
   private IRecipe field_193973_l;
   private float field_193974_m;

   public void func_191845_a(Minecraft p_191845_1_, RecipeList p_191845_2_, int p_191845_3_, int p_191845_4_, int p_191845_5_, int p_191845_6_, float p_191845_7_, RecipeBook p_191845_8_) {
      this.field_191853_k = p_191845_1_;
      this.field_191848_f = p_191845_2_;
      boolean flag = p_191845_8_.func_192815_c();
      List<IRecipe> list = p_191845_2_.func_194207_b(true);
      List<IRecipe> list1 = flag ? Collections.emptyList() : p_191845_2_.func_194207_b(false);
      int i = list.size();
      int j = i + list1.size();
      int k = j <= 16 ? 4 : 5;
      int l = (int)Math.ceil((double)((float)j / (float)k));
      this.field_191851_i = p_191845_3_;
      this.field_191852_j = p_191845_4_;
      int i1 = 25;
      float f = (float)(this.field_191851_i + Math.min(j, k) * 25);
      float f1 = (float)(p_191845_5_ + 50);
      if (f > f1) {
         this.field_191851_i = (int)((float)this.field_191851_i - p_191845_7_ * (float)((int)((f - f1) / p_191845_7_)));
      }

      float f2 = (float)(this.field_191852_j + l * 25);
      float f3 = (float)(p_191845_6_ + 50);
      if (f2 > f3) {
         this.field_191852_j = (int)((float)this.field_191852_j - p_191845_7_ * (float)MathHelper.func_76123_f((f2 - f3) / p_191845_7_));
      }

      float f4 = (float)this.field_191852_j;
      float f5 = (float)(p_191845_6_ - 100);
      if (f4 < f5) {
         this.field_191852_j = (int)((float)this.field_191852_j - p_191845_7_ * (float)MathHelper.func_76123_f((f4 - f5) / p_191845_7_));
      }

      this.field_191850_h = true;
      this.field_193972_f.clear();

      for(int j1 = 0; j1 < j; ++j1) {
         boolean flag1 = j1 < i;
         this.field_193972_f.add(new GuiRecipeOverlay.Button(this.field_191851_i + 4 + 25 * (j1 % k), this.field_191852_j + 5 + 25 * (j1 / k), flag1 ? (IRecipe)list.get(j1) : (IRecipe)list1.get(j1 - i), flag1));
      }

      this.field_193973_l = null;
   }

   public RecipeList func_193971_a() {
      return this.field_191848_f;
   }

   public IRecipe func_193967_b() {
      return this.field_193973_l;
   }

   public boolean func_193968_a(int p_193968_1_, int p_193968_2_, int p_193968_3_) {
      if (p_193968_3_ != 0) {
         return false;
      } else {
         for(GuiRecipeOverlay.Button guirecipeoverlay$button : this.field_193972_f) {
            if (guirecipeoverlay$button.func_146116_c(this.field_191853_k, p_193968_1_, p_193968_2_)) {
               this.field_193973_l = guirecipeoverlay$button.field_193924_p;
               return true;
            }
         }

         return false;
      }
   }

   public void func_191842_a(int p_191842_1_, int p_191842_2_, float p_191842_3_) {
      if (this.field_191850_h) {
         this.field_193974_m += p_191842_3_;
         RenderHelper.func_74520_c();
         GlStateManager.func_179147_l();
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_191853_k.func_110434_K().func_110577_a(field_191847_a);
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b(0.0F, 0.0F, 170.0F);
         int i = this.field_193972_f.size() <= 16 ? 4 : 5;
         int j = Math.min(this.field_193972_f.size(), i);
         int k = MathHelper.func_76123_f((float)this.field_193972_f.size() / (float)i);
         int l = 24;
         int i1 = 4;
         int j1 = 82;
         int k1 = 208;
         this.func_191846_c(j, k, 24, 4, 82, 208);
         GlStateManager.func_179084_k();
         RenderHelper.func_74518_a();

         for(GuiRecipeOverlay.Button guirecipeoverlay$button : this.field_193972_f) {
            guirecipeoverlay$button.func_191745_a(this.field_191853_k, p_191842_1_, p_191842_2_, p_191842_3_);
         }

         GlStateManager.func_179121_F();
      }
   }

   private void func_191846_c(int p_191846_1_, int p_191846_2_, int p_191846_3_, int p_191846_4_, int p_191846_5_, int p_191846_6_) {
      this.func_73729_b(this.field_191851_i, this.field_191852_j, p_191846_5_, p_191846_6_, p_191846_4_, p_191846_4_);
      this.func_73729_b(this.field_191851_i + p_191846_4_ * 2 + p_191846_1_ * p_191846_3_, this.field_191852_j, p_191846_5_ + p_191846_3_ + p_191846_4_, p_191846_6_, p_191846_4_, p_191846_4_);
      this.func_73729_b(this.field_191851_i, this.field_191852_j + p_191846_4_ * 2 + p_191846_2_ * p_191846_3_, p_191846_5_, p_191846_6_ + p_191846_3_ + p_191846_4_, p_191846_4_, p_191846_4_);
      this.func_73729_b(this.field_191851_i + p_191846_4_ * 2 + p_191846_1_ * p_191846_3_, this.field_191852_j + p_191846_4_ * 2 + p_191846_2_ * p_191846_3_, p_191846_5_ + p_191846_3_ + p_191846_4_, p_191846_6_ + p_191846_3_ + p_191846_4_, p_191846_4_, p_191846_4_);

      for(int i = 0; i < p_191846_1_; ++i) {
         this.func_73729_b(this.field_191851_i + p_191846_4_ + i * p_191846_3_, this.field_191852_j, p_191846_5_ + p_191846_4_, p_191846_6_, p_191846_3_, p_191846_4_);
         this.func_73729_b(this.field_191851_i + p_191846_4_ + (i + 1) * p_191846_3_, this.field_191852_j, p_191846_5_ + p_191846_4_, p_191846_6_, p_191846_4_, p_191846_4_);

         for(int j = 0; j < p_191846_2_; ++j) {
            if (i == 0) {
               this.func_73729_b(this.field_191851_i, this.field_191852_j + p_191846_4_ + j * p_191846_3_, p_191846_5_, p_191846_6_ + p_191846_4_, p_191846_4_, p_191846_3_);
               this.func_73729_b(this.field_191851_i, this.field_191852_j + p_191846_4_ + (j + 1) * p_191846_3_, p_191846_5_, p_191846_6_ + p_191846_4_, p_191846_4_, p_191846_4_);
            }

            this.func_73729_b(this.field_191851_i + p_191846_4_ + i * p_191846_3_, this.field_191852_j + p_191846_4_ + j * p_191846_3_, p_191846_5_ + p_191846_4_, p_191846_6_ + p_191846_4_, p_191846_3_, p_191846_3_);
            this.func_73729_b(this.field_191851_i + p_191846_4_ + (i + 1) * p_191846_3_, this.field_191852_j + p_191846_4_ + j * p_191846_3_, p_191846_5_ + p_191846_4_, p_191846_6_ + p_191846_4_, p_191846_4_, p_191846_3_);
            this.func_73729_b(this.field_191851_i + p_191846_4_ + i * p_191846_3_, this.field_191852_j + p_191846_4_ + (j + 1) * p_191846_3_, p_191846_5_ + p_191846_4_, p_191846_6_ + p_191846_4_, p_191846_3_, p_191846_4_);
            this.func_73729_b(this.field_191851_i + p_191846_4_ + (i + 1) * p_191846_3_ - 1, this.field_191852_j + p_191846_4_ + (j + 1) * p_191846_3_ - 1, p_191846_5_ + p_191846_4_, p_191846_6_ + p_191846_4_, p_191846_4_ + 1, p_191846_4_ + 1);
            if (i == p_191846_1_ - 1) {
               this.func_73729_b(this.field_191851_i + p_191846_4_ * 2 + p_191846_1_ * p_191846_3_, this.field_191852_j + p_191846_4_ + j * p_191846_3_, p_191846_5_ + p_191846_3_ + p_191846_4_, p_191846_6_ + p_191846_4_, p_191846_4_, p_191846_3_);
               this.func_73729_b(this.field_191851_i + p_191846_4_ * 2 + p_191846_1_ * p_191846_3_, this.field_191852_j + p_191846_4_ + (j + 1) * p_191846_3_, p_191846_5_ + p_191846_3_ + p_191846_4_, p_191846_6_ + p_191846_4_, p_191846_4_, p_191846_4_);
            }
         }

         this.func_73729_b(this.field_191851_i + p_191846_4_ + i * p_191846_3_, this.field_191852_j + p_191846_4_ * 2 + p_191846_2_ * p_191846_3_, p_191846_5_ + p_191846_4_, p_191846_6_ + p_191846_3_ + p_191846_4_, p_191846_3_, p_191846_4_);
         this.func_73729_b(this.field_191851_i + p_191846_4_ + (i + 1) * p_191846_3_, this.field_191852_j + p_191846_4_ * 2 + p_191846_2_ * p_191846_3_, p_191846_5_ + p_191846_4_, p_191846_6_ + p_191846_3_ + p_191846_4_, p_191846_4_, p_191846_4_);
      }

   }

   public void func_192999_a(boolean p_192999_1_) {
      this.field_191850_h = p_192999_1_;
   }

   public boolean func_191839_a() {
      return this.field_191850_h;
   }

   class Button extends GuiButton {
      private final IRecipe field_193924_p;
      private final boolean field_193925_q;

      public Button(int p_i47594_2_, int p_i47594_3_, IRecipe p_i47594_4_, boolean p_i47594_5_) {
         super(0, p_i47594_2_, p_i47594_3_, "");
         this.field_146120_f = 24;
         this.field_146121_g = 24;
         this.field_193924_p = p_i47594_4_;
         this.field_193925_q = p_i47594_5_;
      }

      public void func_191745_a(Minecraft p_191745_1_, int p_191745_2_, int p_191745_3_, float p_191745_4_) {
         RenderHelper.func_74520_c();
         GlStateManager.func_179141_d();
         p_191745_1_.func_110434_K().func_110577_a(GuiRecipeOverlay.field_191847_a);
         this.field_146123_n = p_191745_2_ >= this.field_146128_h && p_191745_3_ >= this.field_146129_i && p_191745_2_ < this.field_146128_h + this.field_146120_f && p_191745_3_ < this.field_146129_i + this.field_146121_g;
         int i = 152;
         if (!this.field_193925_q) {
            i += 26;
         }

         int j = 78;
         if (this.field_146123_n) {
            j += 26;
         }

         this.func_73729_b(this.field_146128_h, this.field_146129_i, i, j, this.field_146120_f, this.field_146121_g);
         int k = 3;
         int l = 3;
         if (this.field_193924_p instanceof ShapedRecipes) {
            ShapedRecipes shapedrecipes = (ShapedRecipes)this.field_193924_p;
            k = shapedrecipes.func_192403_f();
            l = shapedrecipes.func_192404_g();
         }

         Iterator<Ingredient> iterator = this.field_193924_p.func_192400_c().iterator();

         for(int i1 = 0; i1 < l; ++i1) {
            int j1 = 3 + i1 * 7;

            for(int k1 = 0; k1 < k; ++k1) {
               if (iterator.hasNext()) {
                  ItemStack[] aitemstack = ((Ingredient)iterator.next()).func_193365_a();
                  if (aitemstack.length != 0) {
                     int l1 = 3 + k1 * 7;
                     GlStateManager.func_179094_E();
                     float f = 0.42F;
                     int i2 = (int)((float)(this.field_146128_h + l1) / 0.42F - 3.0F);
                     int j2 = (int)((float)(this.field_146129_i + j1) / 0.42F - 3.0F);
                     GlStateManager.func_179152_a(0.42F, 0.42F, 1.0F);
                     GlStateManager.func_179145_e();
                     p_191745_1_.func_175599_af().func_180450_b(aitemstack[MathHelper.func_76141_d(GuiRecipeOverlay.this.field_193974_m / 30.0F) % aitemstack.length], i2, j2);
                     GlStateManager.func_179140_f();
                     GlStateManager.func_179121_F();
                  }
               }
            }
         }

         GlStateManager.func_179118_c();
         RenderHelper.func_74518_a();
      }
   }
}
