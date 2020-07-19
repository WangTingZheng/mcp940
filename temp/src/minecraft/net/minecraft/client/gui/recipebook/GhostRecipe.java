package net.minecraft.client.gui.recipebook;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.MathHelper;

public class GhostRecipe {
   private IRecipe field_192687_a;
   private final List<GhostRecipe.GhostIngredient> field_192688_b = Lists.<GhostRecipe.GhostIngredient>newArrayList();
   private float field_194190_c;

   public void func_192682_a() {
      this.field_192687_a = null;
      this.field_192688_b.clear();
      this.field_194190_c = 0.0F;
   }

   public void func_194187_a(Ingredient p_194187_1_, int p_194187_2_, int p_194187_3_) {
      this.field_192688_b.add(new GhostRecipe.GhostIngredient(p_194187_1_, p_194187_2_, p_194187_3_));
   }

   public GhostRecipe.GhostIngredient func_192681_a(int p_192681_1_) {
      return this.field_192688_b.get(p_192681_1_);
   }

   public int func_192684_b() {
      return this.field_192688_b.size();
   }

   @Nullable
   public IRecipe func_192686_c() {
      return this.field_192687_a;
   }

   public void func_192685_a(IRecipe p_192685_1_) {
      this.field_192687_a = p_192685_1_;
   }

   public void func_194188_a(Minecraft p_194188_1_, int p_194188_2_, int p_194188_3_, boolean p_194188_4_, float p_194188_5_) {
      if (!GuiScreen.func_146271_m()) {
         this.field_194190_c += p_194188_5_;
      }

      RenderHelper.func_74520_c();
      GlStateManager.func_179140_f();

      for(int i = 0; i < this.field_192688_b.size(); ++i) {
         GhostRecipe.GhostIngredient ghostrecipe$ghostingredient = this.field_192688_b.get(i);
         int j = ghostrecipe$ghostingredient.func_193713_b() + p_194188_2_;
         int k = ghostrecipe$ghostingredient.func_193712_c() + p_194188_3_;
         if (i == 0 && p_194188_4_) {
            Gui.func_73734_a(j - 4, k - 4, j + 20, k + 20, 822018048);
         } else {
            Gui.func_73734_a(j, k, j + 16, k + 16, 822018048);
         }

         GlStateManager.func_179140_f();
         ItemStack itemstack = ghostrecipe$ghostingredient.func_194184_c();
         RenderItem renderitem = p_194188_1_.func_175599_af();
         renderitem.func_184391_a(p_194188_1_.field_71439_g, itemstack, j, k);
         GlStateManager.func_179143_c(516);
         Gui.func_73734_a(j, k, j + 16, k + 16, 822083583);
         GlStateManager.func_179143_c(515);
         if (i == 0) {
            renderitem.func_175030_a(p_194188_1_.field_71466_p, itemstack, j, k);
         }

         GlStateManager.func_179145_e();
      }

      RenderHelper.func_74518_a();
   }

   public class GhostIngredient {
      private final Ingredient field_194186_b;
      private final int field_192678_b;
      private final int field_192679_c;

      public GhostIngredient(Ingredient p_i47604_2_, int p_i47604_3_, int p_i47604_4_) {
         this.field_194186_b = p_i47604_2_;
         this.field_192678_b = p_i47604_3_;
         this.field_192679_c = p_i47604_4_;
      }

      public int func_193713_b() {
         return this.field_192678_b;
      }

      public int func_193712_c() {
         return this.field_192679_c;
      }

      public ItemStack func_194184_c() {
         ItemStack[] aitemstack = this.field_194186_b.func_193365_a();
         return aitemstack[MathHelper.func_76141_d(GhostRecipe.this.field_194190_c / 30.0F) % aitemstack.length];
      }
   }
}
