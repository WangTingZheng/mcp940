package net.minecraft.client.gui.recipebook;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButtonToggle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.util.RecipeBookClient;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.stats.RecipeBook;

public class GuiButtonRecipeTab extends GuiButtonToggle {
   private final CreativeTabs field_193921_u;
   private float field_193922_v;

   public GuiButtonRecipeTab(int p_i47588_1_, CreativeTabs p_i47588_2_) {
      super(p_i47588_1_, 0, 0, 35, 27, false);
      this.field_193921_u = p_i47588_2_;
      this.func_191751_a(153, 2, 35, 0, GuiRecipeBook.field_191894_a);
   }

   public void func_193918_a(Minecraft p_193918_1_) {
      RecipeBook recipebook = p_193918_1_.field_71439_g.func_192035_E();

      label21:
      for(RecipeList recipelist : RecipeBookClient.field_194086_e.get(this.field_193921_u)) {
         Iterator iterator = recipelist.func_194208_a(recipebook.func_192815_c()).iterator();

         while(true) {
            if (!iterator.hasNext()) {
               continue label21;
            }

            IRecipe irecipe = (IRecipe)iterator.next();
            if (recipebook.func_194076_e(irecipe)) {
               break;
            }
         }

         this.field_193922_v = 15.0F;
         return;
      }

   }

   public void func_191745_a(Minecraft p_191745_1_, int p_191745_2_, int p_191745_3_, float p_191745_4_) {
      if (this.field_146125_m) {
         if (this.field_193922_v > 0.0F) {
            float f = 1.0F + 0.1F * (float)Math.sin((double)(this.field_193922_v / 15.0F * 3.1415927F));
            GlStateManager.func_179094_E();
            GlStateManager.func_179109_b((float)(this.field_146128_h + 8), (float)(this.field_146129_i + 12), 0.0F);
            GlStateManager.func_179152_a(1.0F, f, 1.0F);
            GlStateManager.func_179109_b((float)(-(this.field_146128_h + 8)), (float)(-(this.field_146129_i + 12)), 0.0F);
         }

         this.field_146123_n = p_191745_2_ >= this.field_146128_h && p_191745_3_ >= this.field_146129_i && p_191745_2_ < this.field_146128_h + this.field_146120_f && p_191745_3_ < this.field_146129_i + this.field_146121_g;
         p_191745_1_.func_110434_K().func_110577_a(this.field_191760_o);
         GlStateManager.func_179097_i();
         int k = this.field_191756_q;
         int i = this.field_191757_r;
         if (this.field_191755_p) {
            k += this.field_191758_s;
         }

         if (this.field_146123_n) {
            i += this.field_191759_t;
         }

         int j = this.field_146128_h;
         if (this.field_191755_p) {
            j -= 2;
         }

         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_73729_b(j, this.field_146129_i, k, i, this.field_146120_f, this.field_146121_g);
         GlStateManager.func_179126_j();
         RenderHelper.func_74520_c();
         GlStateManager.func_179140_f();
         this.func_193920_a(p_191745_1_.func_175599_af());
         GlStateManager.func_179145_e();
         RenderHelper.func_74518_a();
         if (this.field_193922_v > 0.0F) {
            GlStateManager.func_179121_F();
            this.field_193922_v -= p_191745_4_;
         }

      }
   }

   private void func_193920_a(RenderItem p_193920_1_) {
      ItemStack itemstack = this.field_193921_u.func_151244_d();
      if (this.field_193921_u == CreativeTabs.field_78040_i) {
         p_193920_1_.func_180450_b(itemstack, this.field_146128_h + 3, this.field_146129_i + 5);
         p_193920_1_.func_180450_b(CreativeTabs.field_78037_j.func_151244_d(), this.field_146128_h + 14, this.field_146129_i + 5);
      } else if (this.field_193921_u == CreativeTabs.field_78026_f) {
         p_193920_1_.func_180450_b(itemstack, this.field_146128_h + 3, this.field_146129_i + 5);
         p_193920_1_.func_180450_b(CreativeTabs.field_78039_h.func_151244_d(), this.field_146128_h + 14, this.field_146129_i + 5);
      } else {
         p_193920_1_.func_180450_b(itemstack, this.field_146128_h + 9, this.field_146129_i + 5);
      }

   }

   public CreativeTabs func_191764_e() {
      return this.field_193921_u;
   }

   public boolean func_193919_e() {
      List<RecipeList> list = (List)RecipeBookClient.field_194086_e.get(this.field_193921_u);
      this.field_146125_m = false;

      for(RecipeList recipelist : list) {
         if (recipelist.func_194209_a() && recipelist.func_194212_c()) {
            this.field_146125_m = true;
            break;
         }
      }

      return this.field_146125_m;
   }
}
