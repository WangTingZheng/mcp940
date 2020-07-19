package net.minecraft.client.gui.recipebook;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButtonToggle;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.util.RecipeBookClient;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.network.play.client.CPacketRecipeInfo;
import net.minecraft.network.play.client.CPacketRecipePlacement;
import net.minecraft.stats.RecipeBook;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiRecipeBook extends Gui implements IRecipeUpdateListener {
   protected static final ResourceLocation field_191894_a = new ResourceLocation("textures/gui/recipe_book.png");
   private int field_191903_n;
   private int field_191904_o;
   private int field_191905_p;
   private static final Logger field_193959_i = LogManager.getLogger();
   private final GhostRecipe field_191915_z = new GhostRecipe();
   private final List<GuiButtonRecipeTab> field_193018_j = Lists.newArrayList(new GuiButtonRecipeTab(0, CreativeTabs.field_78027_g), new GuiButtonRecipeTab(0, CreativeTabs.field_78040_i), new GuiButtonRecipeTab(0, CreativeTabs.field_78030_b), new GuiButtonRecipeTab(0, CreativeTabs.field_78026_f), new GuiButtonRecipeTab(0, CreativeTabs.field_78028_d));
   private GuiButtonRecipeTab field_191913_x;
   private GuiButtonToggle field_193960_m;
   private Container field_191908_s;
   private InventoryCrafting field_193961_o;
   private Minecraft field_191888_F;
   private GuiTextField field_193962_q;
   private String field_193963_r = "";
   private RecipeBook field_193964_s;
   private final RecipeBookPage field_193022_s = new RecipeBookPage();
   private RecipeItemHelper field_193965_u = new RecipeItemHelper();
   private int field_193966_v;

   public void func_191856_a(int p_191856_1_, int p_191856_2_, Minecraft p_191856_3_, boolean p_191856_4_, Container p_191856_5_, InventoryCrafting p_191856_6_) {
      this.field_191888_F = p_191856_3_;
      this.field_191904_o = p_191856_1_;
      this.field_191905_p = p_191856_2_;
      this.field_191908_s = p_191856_5_;
      this.field_193961_o = p_191856_6_;
      this.field_193964_s = p_191856_3_.field_71439_g.func_192035_E();
      this.field_193966_v = p_191856_3_.field_71439_g.field_71071_by.func_194015_p();
      this.field_191913_x = this.field_193018_j.get(0);
      this.field_191913_x.func_191753_b(true);
      if (this.func_191878_b()) {
         this.func_193014_a(p_191856_4_, p_191856_6_);
      }

      Keyboard.enableRepeatEvents(true);
   }

   public void func_193014_a(boolean p_193014_1_, InventoryCrafting p_193014_2_) {
      this.field_191903_n = p_193014_1_ ? 0 : 86;
      int i = (this.field_191904_o - 147) / 2 - this.field_191903_n;
      int j = (this.field_191905_p - 166) / 2;
      this.field_193965_u.func_194119_a();
      this.field_191888_F.field_71439_g.field_71071_by.func_194016_a(this.field_193965_u, false);
      p_193014_2_.func_194018_a(this.field_193965_u);
      this.field_193962_q = new GuiTextField(0, this.field_191888_F.field_71466_p, i + 25, j + 14, 80, this.field_191888_F.field_71466_p.field_78288_b + 5);
      this.field_193962_q.func_146203_f(50);
      this.field_193962_q.func_146185_a(false);
      this.field_193962_q.func_146189_e(true);
      this.field_193962_q.func_146193_g(16777215);
      this.field_193022_s.func_194194_a(this.field_191888_F, i, j);
      this.field_193022_s.func_193732_a(this);
      this.field_193960_m = new GuiButtonToggle(0, i + 110, j + 12, 26, 16, this.field_193964_s.func_192815_c());
      this.field_193960_m.func_191751_a(152, 41, 28, 18, field_191894_a);
      this.func_193003_g(false);
      this.func_193949_f();
   }

   public void func_191871_c() {
      Keyboard.enableRepeatEvents(false);
   }

   public int func_193011_a(boolean p_193011_1_, int p_193011_2_, int p_193011_3_) {
      int i;
      if (this.func_191878_b() && !p_193011_1_) {
         i = 177 + (p_193011_2_ - p_193011_3_ - 200) / 2;
      } else {
         i = (p_193011_2_ - p_193011_3_) / 2;
      }

      return i;
   }

   public void func_191866_a() {
      this.func_193006_a(!this.func_191878_b());
   }

   public boolean func_191878_b() {
      return this.field_193964_s.func_192812_b();
   }

   private void func_193006_a(boolean p_193006_1_) {
      this.field_193964_s.func_192813_a(p_193006_1_);
      if (!p_193006_1_) {
         this.field_193022_s.func_194200_c();
      }

      this.func_193956_j();
   }

   public void func_191874_a(@Nullable Slot p_191874_1_) {
      if (p_191874_1_ != null && p_191874_1_.field_75222_d <= 9) {
         this.field_191915_z.func_192682_a();
         if (this.func_191878_b()) {
            this.func_193942_g();
         }
      }

   }

   private void func_193003_g(boolean p_193003_1_) {
      List<RecipeList> list = (List)RecipeBookClient.field_194086_e.get(this.field_191913_x.func_191764_e());
      list.forEach((p_193944_1_) -> {
         p_193944_1_.func_194210_a(this.field_193965_u, this.field_193961_o.func_174922_i(), this.field_193961_o.func_174923_h(), this.field_193964_s);
      });
      List<RecipeList> list1 = Lists.newArrayList(list);
      list1.removeIf((p_193952_0_) -> {
         return !p_193952_0_.func_194209_a();
      });
      list1.removeIf((p_193953_0_) -> {
         return !p_193953_0_.func_194212_c();
      });
      String s = this.field_193962_q.func_146179_b();
      if (!s.isEmpty()) {
         ObjectSet<RecipeList> objectset = new ObjectLinkedOpenHashSet<RecipeList>(this.field_191888_F.func_193987_a(SearchTreeManager.field_194012_b).func_194038_a(s.toLowerCase(Locale.ROOT)));
         list1.removeIf((p_193947_1_) -> {
            return !p_193947_0_.contains(p_193947_1_);
         });
      }

      if (this.field_193964_s.func_192815_c()) {
         list1.removeIf((p_193958_0_) -> {
            return !p_193958_0_.func_192708_c();
         });
      }

      this.field_193022_s.func_194192_a(list1, p_193003_1_);
   }

   private void func_193949_f() {
      int i = (this.field_191904_o - 147) / 2 - this.field_191903_n - 30;
      int j = (this.field_191905_p - 166) / 2 + 3;
      int k = 27;
      int l = 0;

      for(GuiButtonRecipeTab guibuttonrecipetab : this.field_193018_j) {
         CreativeTabs creativetabs = guibuttonrecipetab.func_191764_e();
         if (creativetabs == CreativeTabs.field_78027_g) {
            guibuttonrecipetab.field_146125_m = true;
            guibuttonrecipetab.func_191752_c(i, j + 27 * l++);
         } else if (guibuttonrecipetab.func_193919_e()) {
            guibuttonrecipetab.func_191752_c(i, j + 27 * l++);
            guibuttonrecipetab.func_193918_a(this.field_191888_F);
         }
      }

   }

   public void func_193957_d() {
      if (this.func_191878_b()) {
         if (this.field_193966_v != this.field_191888_F.field_71439_g.field_71071_by.func_194015_p()) {
            this.func_193942_g();
            this.field_193966_v = this.field_191888_F.field_71439_g.field_71071_by.func_194015_p();
         }

      }
   }

   private void func_193942_g() {
      this.field_193965_u.func_194119_a();
      this.field_191888_F.field_71439_g.field_71071_by.func_194016_a(this.field_193965_u, false);
      this.field_193961_o.func_194018_a(this.field_193965_u);
      this.func_193003_g(false);
   }

   public void func_191861_a(int p_191861_1_, int p_191861_2_, float p_191861_3_) {
      if (this.func_191878_b()) {
         RenderHelper.func_74520_c();
         GlStateManager.func_179140_f();
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b(0.0F, 0.0F, 100.0F);
         this.field_191888_F.func_110434_K().func_110577_a(field_191894_a);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         int i = (this.field_191904_o - 147) / 2 - this.field_191903_n;
         int j = (this.field_191905_p - 166) / 2;
         this.func_73729_b(i, j, 1, 1, 147, 166);
         this.field_193962_q.func_146194_f();
         RenderHelper.func_74518_a();

         for(GuiButtonRecipeTab guibuttonrecipetab : this.field_193018_j) {
            guibuttonrecipetab.func_191745_a(this.field_191888_F, p_191861_1_, p_191861_2_, p_191861_3_);
         }

         this.field_193960_m.func_191745_a(this.field_191888_F, p_191861_1_, p_191861_2_, p_191861_3_);
         this.field_193022_s.func_194191_a(i, j, p_191861_1_, p_191861_2_, p_191861_3_);
         GlStateManager.func_179121_F();
      }
   }

   public void func_191876_c(int p_191876_1_, int p_191876_2_, int p_191876_3_, int p_191876_4_) {
      if (this.func_191878_b()) {
         this.field_193022_s.func_193721_a(p_191876_3_, p_191876_4_);
         if (this.field_193960_m.func_146115_a()) {
            String s1 = I18n.func_135052_a(this.field_193960_m.func_191754_c() ? "gui.recipebook.toggleRecipes.craftable" : "gui.recipebook.toggleRecipes.all");
            if (this.field_191888_F.field_71462_r != null) {
               this.field_191888_F.field_71462_r.func_146279_a(s1, p_191876_3_, p_191876_4_);
            }
         }

         this.func_193015_d(p_191876_1_, p_191876_2_, p_191876_3_, p_191876_4_);
      }
   }

   private void func_193015_d(int p_193015_1_, int p_193015_2_, int p_193015_3_, int p_193015_4_) {
      ItemStack itemstack = null;

      for(int i = 0; i < this.field_191915_z.func_192684_b(); ++i) {
         GhostRecipe.GhostIngredient ghostrecipe$ghostingredient = this.field_191915_z.func_192681_a(i);
         int j = ghostrecipe$ghostingredient.func_193713_b() + p_193015_1_;
         int k = ghostrecipe$ghostingredient.func_193712_c() + p_193015_2_;
         if (p_193015_3_ >= j && p_193015_4_ >= k && p_193015_3_ < j + 16 && p_193015_4_ < k + 16) {
            itemstack = ghostrecipe$ghostingredient.func_194184_c();
         }
      }

      if (itemstack != null && this.field_191888_F.field_71462_r != null) {
         this.field_191888_F.field_71462_r.func_146283_a(this.field_191888_F.field_71462_r.func_191927_a(itemstack), p_193015_3_, p_193015_4_);
      }

   }

   public void func_191864_a(int p_191864_1_, int p_191864_2_, boolean p_191864_3_, float p_191864_4_) {
      this.field_191915_z.func_194188_a(this.field_191888_F, p_191864_1_, p_191864_2_, p_191864_3_, p_191864_4_);
   }

   public boolean func_191862_a(int p_191862_1_, int p_191862_2_, int p_191862_3_) {
      if (this.func_191878_b() && !this.field_191888_F.field_71439_g.func_175149_v()) {
         if (this.field_193022_s.func_194196_a(p_191862_1_, p_191862_2_, p_191862_3_, (this.field_191904_o - 147) / 2 - this.field_191903_n, (this.field_191905_p - 166) / 2, 147, 166)) {
            IRecipe irecipe = this.field_193022_s.func_194193_a();
            RecipeList recipelist = this.field_193022_s.func_194199_b();
            if (irecipe != null && recipelist != null) {
               this.func_193945_a(irecipe, recipelist);
               if (!this.func_191880_f() && p_191862_3_ == 0) {
                  this.func_193006_a(false);
               }
            }

            return true;
         } else if (p_191862_3_ != 0) {
            return false;
         } else if (this.field_193962_q.func_146192_a(p_191862_1_, p_191862_2_, p_191862_3_)) {
            return true;
         } else if (this.field_193960_m.func_146116_c(this.field_191888_F, p_191862_1_, p_191862_2_)) {
            boolean flag = !this.field_193964_s.func_192815_c();
            this.field_193964_s.func_192810_b(flag);
            this.field_193960_m.func_191753_b(flag);
            this.field_193960_m.func_146113_a(this.field_191888_F.func_147118_V());
            this.func_193956_j();
            this.func_193003_g(false);
            return true;
         } else {
            for(GuiButtonRecipeTab guibuttonrecipetab : this.field_193018_j) {
               if (guibuttonrecipetab.func_146116_c(this.field_191888_F, p_191862_1_, p_191862_2_)) {
                  if (this.field_191913_x != guibuttonrecipetab) {
                     guibuttonrecipetab.func_146113_a(this.field_191888_F.func_147118_V());
                     this.field_191913_x.func_191753_b(false);
                     this.field_191913_x = guibuttonrecipetab;
                     this.field_191913_x.func_191753_b(true);
                     this.func_193003_g(true);
                  }

                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   public boolean func_193955_c(int p_193955_1_, int p_193955_2_, int p_193955_3_, int p_193955_4_, int p_193955_5_, int p_193955_6_) {
      if (!this.func_191878_b()) {
         return true;
      } else {
         boolean flag = p_193955_1_ < p_193955_3_ || p_193955_2_ < p_193955_4_ || p_193955_1_ >= p_193955_3_ + p_193955_5_ || p_193955_2_ >= p_193955_4_ + p_193955_6_;
         boolean flag1 = p_193955_3_ - 147 < p_193955_1_ && p_193955_1_ < p_193955_3_ && p_193955_4_ < p_193955_2_ && p_193955_2_ < p_193955_4_ + p_193955_6_;
         return flag && !flag1 && !this.field_191913_x.func_146116_c(this.field_191888_F, p_193955_1_, p_193955_2_);
      }
   }

   public boolean func_191859_a(char p_191859_1_, int p_191859_2_) {
      if (this.func_191878_b() && !this.field_191888_F.field_71439_g.func_175149_v()) {
         if (p_191859_2_ == 1 && !this.func_191880_f()) {
            this.func_193006_a(false);
            return true;
         } else {
            if (GameSettings.func_100015_a(this.field_191888_F.field_71474_y.field_74310_D) && !this.field_193962_q.func_146206_l()) {
               this.field_193962_q.func_146195_b(true);
            } else if (this.field_193962_q.func_146201_a(p_191859_1_, p_191859_2_)) {
               String s1 = this.field_193962_q.func_146179_b().toLowerCase(Locale.ROOT);
               this.func_193716_a(s1);
               if (!s1.equals(this.field_193963_r)) {
                  this.func_193003_g(false);
                  this.field_193963_r = s1;
               }

               return true;
            }

            return false;
         }
      } else {
         return false;
      }
   }

   private void func_193716_a(String p_193716_1_) {
      if ("excitedze".equals(p_193716_1_)) {
         LanguageManager languagemanager = this.field_191888_F.func_135016_M();
         Language language = languagemanager.func_191960_a("en_pt");
         if (languagemanager.func_135041_c().compareTo(language) == 0) {
            return;
         }

         languagemanager.func_135045_a(language);
         this.field_191888_F.field_71474_y.field_74363_ab = language.func_135034_a();
         this.field_191888_F.func_110436_a();
         this.field_191888_F.field_71466_p.func_78264_a(this.field_191888_F.func_135016_M().func_135042_a() || this.field_191888_F.field_71474_y.field_151455_aw);
         this.field_191888_F.field_71466_p.func_78275_b(languagemanager.func_135044_b());
         this.field_191888_F.field_71474_y.func_74303_b();
      }

   }

   private boolean func_191880_f() {
      return this.field_191903_n == 86;
   }

   public void func_193948_e() {
      this.func_193949_f();
      if (this.func_191878_b()) {
         this.func_193003_g(false);
      }

   }

   public void func_193001_a(List<IRecipe> p_193001_1_) {
      for(IRecipe irecipe : p_193001_1_) {
         this.field_191888_F.field_71439_g.func_193103_a(irecipe);
      }

   }

   private void func_193945_a(IRecipe p_193945_1_, RecipeList p_193945_2_) {
      boolean flag = p_193945_2_.func_194213_a(p_193945_1_);
      InventoryCraftResult inventorycraftresult = null;
      if (this.field_191908_s instanceof ContainerWorkbench) {
         inventorycraftresult = ((ContainerWorkbench)this.field_191908_s).field_75160_f;
      } else if (this.field_191908_s instanceof ContainerPlayer) {
         inventorycraftresult = ((ContainerPlayer)this.field_191908_s).field_75179_f;
      }

      if (inventorycraftresult != null) {
         if (!flag && this.field_191915_z.func_192686_c() == p_193945_1_) {
            return;
         }

         if (!this.func_193941_i() && !this.field_191888_F.field_71439_g.func_184812_l_()) {
            return;
         }

         if (flag) {
            this.func_193950_a(p_193945_1_, this.field_191908_s.field_75151_b, this.field_191908_s.field_75152_c, inventorycraftresult);
         } else {
            List<CPacketRecipePlacement.ItemMove> list2 = this.func_193954_a(inventorycraftresult);
            this.func_193951_a(p_193945_1_, this.field_191908_s.field_75151_b);
            if (!list2.isEmpty()) {
               this.field_191888_F.field_71442_b.func_192831_a(this.field_191908_s.field_75152_c, list2, Lists.newArrayList(), this.field_191888_F.field_71439_g);
               if (this.field_193964_s.func_192815_c()) {
                  this.field_191888_F.field_71439_g.field_71071_by.func_70296_d();
               }
            }
         }

         if (!this.func_191880_f()) {
            this.func_191866_a();
         }
      }

   }

   private void func_193950_a(IRecipe p_193950_1_, List<Slot> p_193950_2_, int p_193950_3_, InventoryCraftResult p_193950_4_) {
      boolean flag = p_193950_1_.func_77569_a(this.field_193961_o, this.field_191888_F.field_71441_e);
      int i = this.field_193965_u.func_194114_b(p_193950_1_, (IntList)null);
      if (flag) {
         boolean flag1 = true;

         for(int j = 0; j < this.field_193961_o.func_70302_i_(); ++j) {
            ItemStack itemstack = this.field_193961_o.func_70301_a(j);
            if (!itemstack.func_190926_b() && i > itemstack.func_190916_E()) {
               flag1 = false;
            }
         }

         if (flag1) {
            return;
         }
      }

      int i1 = this.func_193943_a(i, flag);
      IntList intlist = new IntArrayList();
      if (this.field_193965_u.func_194118_a(p_193950_1_, intlist, i1)) {
         int j1 = i1;
         IntListIterator lvt_10_1_ = intlist.iterator();

         while(lvt_10_1_.hasNext()) {
            int k = ((Integer)lvt_10_1_.next()).intValue();
            int l = RecipeItemHelper.func_194115_b(k).func_77976_d();
            if (l < j1) {
               j1 = l;
            }
         }

         if (this.field_193965_u.func_194118_a(p_193950_1_, intlist, j1)) {
            List<CPacketRecipePlacement.ItemMove> list2 = this.func_193954_a(p_193950_4_);
            lvt_10_1_ = Lists.<CPacketRecipePlacement.ItemMove>newArrayList();
            this.func_193013_a(p_193950_1_, p_193950_2_, j1, intlist, lvt_10_1_);
            this.field_191888_F.field_71442_b.func_192831_a(p_193950_3_, list2, lvt_10_1_, this.field_191888_F.field_71439_g);
            this.field_191888_F.field_71439_g.field_71071_by.func_70296_d();
         }
      }
   }

   private List<CPacketRecipePlacement.ItemMove> func_193954_a(InventoryCraftResult p_193954_1_) {
      this.field_191915_z.func_192682_a();
      InventoryPlayer inventoryplayer = this.field_191888_F.field_71439_g.field_71071_by;
      List<CPacketRecipePlacement.ItemMove> list2 = Lists.<CPacketRecipePlacement.ItemMove>newArrayList();

      for(int i = 0; i < this.field_193961_o.func_70302_i_(); ++i) {
         ItemStack itemstack = this.field_193961_o.func_70301_a(i);
         if (!itemstack.func_190926_b()) {
            while(itemstack.func_190916_E() > 0) {
               int j = inventoryplayer.func_70432_d(itemstack);
               if (j == -1) {
                  j = inventoryplayer.func_70447_i();
               }

               ItemStack itemstack1 = itemstack.func_77946_l();
               itemstack1.func_190920_e(1);
               if (inventoryplayer.func_191971_c(j, itemstack1)) {
                  itemstack1.func_190917_f(1);
               } else {
                  field_193959_i.error("Can't find any space for item in inventory");
               }

               this.field_193961_o.func_70298_a(i, 1);
               int k = i + 1;
               list2.add(new CPacketRecipePlacement.ItemMove(itemstack1.func_77946_l(), k, j));
            }
         }
      }

      this.field_193961_o.func_174888_l();
      p_193954_1_.func_174888_l();
      return list2;
   }

   private int func_193943_a(int p_193943_1_, boolean p_193943_2_) {
      int i = 1;
      if (GuiScreen.func_146272_n()) {
         i = p_193943_1_;
      } else if (p_193943_2_) {
         i = 64;

         for(int j = 0; j < this.field_193961_o.func_70302_i_(); ++j) {
            ItemStack itemstack = this.field_193961_o.func_70301_a(j);
            if (!itemstack.func_190926_b() && i > itemstack.func_190916_E()) {
               i = itemstack.func_190916_E();
            }
         }

         if (i < 64) {
            ++i;
         }
      }

      return i;
   }

   private void func_193013_a(IRecipe p_193013_1_, List<Slot> p_193013_2_, int p_193013_3_, IntList p_193013_4_, List<CPacketRecipePlacement.ItemMove> p_193013_5_) {
      int i = this.field_193961_o.func_174922_i();
      int j = this.field_193961_o.func_174923_h();
      if (p_193013_1_ instanceof ShapedRecipes) {
         ShapedRecipes shapedrecipes = (ShapedRecipes)p_193013_1_;
         i = shapedrecipes.func_192403_f();
         j = shapedrecipes.func_192404_g();
      }

      int j1 = 1;
      Iterator<Integer> iterator = p_193013_4_.iterator();

      for(int k = 0; k < this.field_193961_o.func_174922_i() && j != k; ++k) {
         for(int l = 0; l < this.field_193961_o.func_174923_h(); ++l) {
            if (i == l || !iterator.hasNext()) {
               j1 += this.field_193961_o.func_174922_i() - l;
               break;
            }

            Slot slot = p_193013_2_.get(j1);
            ItemStack itemstack = RecipeItemHelper.func_194115_b(((Integer)iterator.next()).intValue());
            if (itemstack.func_190926_b()) {
               ++j1;
            } else {
               for(int i1 = 0; i1 < p_193013_3_; ++i1) {
                  CPacketRecipePlacement.ItemMove cpacketrecipeplacement$itemmove = this.func_193946_a(j1, slot, itemstack);
                  if (cpacketrecipeplacement$itemmove != null) {
                     p_193013_5_.add(cpacketrecipeplacement$itemmove);
                  }
               }

               ++j1;
            }
         }

         if (!iterator.hasNext()) {
            break;
         }
      }

   }

   @Nullable
   private CPacketRecipePlacement.ItemMove func_193946_a(int p_193946_1_, Slot p_193946_2_, ItemStack p_193946_3_) {
      InventoryPlayer inventoryplayer = this.field_191888_F.field_71439_g.field_71071_by;
      int i = inventoryplayer.func_194014_c(p_193946_3_);
      if (i == -1) {
         return null;
      } else {
         ItemStack itemstack = inventoryplayer.func_70301_a(i).func_77946_l();
         if (itemstack.func_190926_b()) {
            field_193959_i.error("Matched: " + p_193946_3_.func_77977_a() + " with empty item.");
            return null;
         } else {
            if (itemstack.func_190916_E() > 1) {
               inventoryplayer.func_70298_a(i, 1);
            } else {
               inventoryplayer.func_70304_b(i);
            }

            itemstack.func_190920_e(1);
            if (p_193946_2_.func_75211_c().func_190926_b()) {
               p_193946_2_.func_75215_d(itemstack);
            } else {
               p_193946_2_.func_75211_c().func_190917_f(1);
            }

            return new CPacketRecipePlacement.ItemMove(itemstack, p_193946_1_, i);
         }
      }
   }

   private boolean func_193941_i() {
      InventoryPlayer inventoryplayer = this.field_191888_F.field_71439_g.field_71071_by;

      for(int i = 0; i < this.field_193961_o.func_70302_i_(); ++i) {
         ItemStack itemstack = this.field_193961_o.func_70301_a(i);
         if (!itemstack.func_190926_b()) {
            int j = inventoryplayer.func_70432_d(itemstack);
            if (j == -1) {
               j = inventoryplayer.func_70447_i();
            }

            if (j == -1) {
               return false;
            }
         }
      }

      return true;
   }

   private void func_193951_a(IRecipe p_193951_1_, List<Slot> p_193951_2_) {
      ItemStack itemstack = p_193951_1_.func_77571_b();
      this.field_191915_z.func_192685_a(p_193951_1_);
      this.field_191915_z.func_194187_a(Ingredient.func_193369_a(itemstack), (p_193951_2_.get(0)).field_75223_e, (p_193951_2_.get(0)).field_75221_f);
      int i = this.field_193961_o.func_174922_i();
      int j = this.field_193961_o.func_174923_h();
      int k = p_193951_1_ instanceof ShapedRecipes ? ((ShapedRecipes)p_193951_1_).func_192403_f() : i;
      int l = 1;
      Iterator<Ingredient> iterator = p_193951_1_.func_192400_c().iterator();

      for(int i1 = 0; i1 < j; ++i1) {
         for(int j1 = 0; j1 < k; ++j1) {
            if (!iterator.hasNext()) {
               return;
            }

            Ingredient ingredient = iterator.next();
            if (ingredient != Ingredient.field_193370_a) {
               Slot slot = p_193951_2_.get(l);
               this.field_191915_z.func_194187_a(ingredient, slot.field_75223_e, slot.field_75221_f);
            }

            ++l;
         }

         if (k < i) {
            l += i - k;
         }
      }

   }

   private void func_193956_j() {
      if (this.field_191888_F.func_147114_u() != null) {
         this.field_191888_F.func_147114_u().func_147297_a(new CPacketRecipeInfo(this.func_191878_b(), this.field_193964_s.func_192815_c()));
      }

   }
}
