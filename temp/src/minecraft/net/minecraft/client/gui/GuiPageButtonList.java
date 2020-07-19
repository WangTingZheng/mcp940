package net.minecraft.client.gui;

import com.google.common.base.MoreObjects;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IntHashMap;

public class GuiPageButtonList extends GuiListExtended {
   private final List<GuiPageButtonList.GuiEntry> field_178074_u = Lists.<GuiPageButtonList.GuiEntry>newArrayList();
   private final IntHashMap<Gui> field_178073_v = new IntHashMap<Gui>();
   private final List<GuiTextField> field_178072_w = Lists.<GuiTextField>newArrayList();
   private final GuiPageButtonList.GuiListEntry[][] field_178078_x;
   private int field_178077_y;
   private final GuiPageButtonList.GuiResponder field_178076_z;
   private Gui field_178075_A;

   public GuiPageButtonList(Minecraft p_i45536_1_, int p_i45536_2_, int p_i45536_3_, int p_i45536_4_, int p_i45536_5_, int p_i45536_6_, GuiPageButtonList.GuiResponder p_i45536_7_, GuiPageButtonList.GuiListEntry[]... p_i45536_8_) {
      super(p_i45536_1_, p_i45536_2_, p_i45536_3_, p_i45536_4_, p_i45536_5_, p_i45536_6_);
      this.field_178076_z = p_i45536_7_;
      this.field_178078_x = p_i45536_8_;
      this.field_148163_i = false;
      this.func_178069_s();
      this.func_178055_t();
   }

   private void func_178069_s() {
      for(GuiPageButtonList.GuiListEntry[] aguipagebuttonlist$guilistentry : this.field_178078_x) {
         for(int i = 0; i < aguipagebuttonlist$guilistentry.length; i += 2) {
            GuiPageButtonList.GuiListEntry guipagebuttonlist$guilistentry = aguipagebuttonlist$guilistentry[i];
            GuiPageButtonList.GuiListEntry guipagebuttonlist$guilistentry1 = i < aguipagebuttonlist$guilistentry.length - 1 ? aguipagebuttonlist$guilistentry[i + 1] : null;
            Gui gui = this.func_178058_a(guipagebuttonlist$guilistentry, 0, guipagebuttonlist$guilistentry1 == null);
            Gui gui1 = this.func_178058_a(guipagebuttonlist$guilistentry1, 160, guipagebuttonlist$guilistentry == null);
            GuiPageButtonList.GuiEntry guipagebuttonlist$guientry = new GuiPageButtonList.GuiEntry(gui, gui1);
            this.field_178074_u.add(guipagebuttonlist$guientry);
            if (guipagebuttonlist$guilistentry != null && gui != null) {
               this.field_178073_v.func_76038_a(guipagebuttonlist$guilistentry.func_178935_b(), gui);
               if (gui instanceof GuiTextField) {
                  this.field_178072_w.add((GuiTextField)gui);
               }
            }

            if (guipagebuttonlist$guilistentry1 != null && gui1 != null) {
               this.field_178073_v.func_76038_a(guipagebuttonlist$guilistentry1.func_178935_b(), gui1);
               if (gui1 instanceof GuiTextField) {
                  this.field_178072_w.add((GuiTextField)gui1);
               }
            }
         }
      }

   }

   private void func_178055_t() {
      this.field_178074_u.clear();

      for(int i = 0; i < this.field_178078_x[this.field_178077_y].length; i += 2) {
         GuiPageButtonList.GuiListEntry guipagebuttonlist$guilistentry = this.field_178078_x[this.field_178077_y][i];
         GuiPageButtonList.GuiListEntry guipagebuttonlist$guilistentry1 = i < this.field_178078_x[this.field_178077_y].length - 1 ? this.field_178078_x[this.field_178077_y][i + 1] : null;
         Gui gui = this.field_178073_v.func_76041_a(guipagebuttonlist$guilistentry.func_178935_b());
         Gui gui1 = guipagebuttonlist$guilistentry1 != null ? (Gui)this.field_178073_v.func_76041_a(guipagebuttonlist$guilistentry1.func_178935_b()) : null;
         GuiPageButtonList.GuiEntry guipagebuttonlist$guientry = new GuiPageButtonList.GuiEntry(gui, gui1);
         this.field_178074_u.add(guipagebuttonlist$guientry);
      }

   }

   public void func_181156_c(int p_181156_1_) {
      if (p_181156_1_ != this.field_178077_y) {
         int i = this.field_178077_y;
         this.field_178077_y = p_181156_1_;
         this.func_178055_t();
         this.func_178060_e(i, p_181156_1_);
         this.field_148169_q = 0.0F;
      }
   }

   public int func_178059_e() {
      return this.field_178077_y;
   }

   public int func_178057_f() {
      return this.field_178078_x.length;
   }

   public Gui func_178056_g() {
      return this.field_178075_A;
   }

   public void func_178071_h() {
      if (this.field_178077_y > 0) {
         this.func_181156_c(this.field_178077_y - 1);
      }

   }

   public void func_178064_i() {
      if (this.field_178077_y < this.field_178078_x.length - 1) {
         this.func_181156_c(this.field_178077_y + 1);
      }

   }

   public Gui func_178061_c(int p_178061_1_) {
      return this.field_178073_v.func_76041_a(p_178061_1_);
   }

   private void func_178060_e(int p_178060_1_, int p_178060_2_) {
      for(GuiPageButtonList.GuiListEntry guipagebuttonlist$guilistentry : this.field_178078_x[p_178060_1_]) {
         if (guipagebuttonlist$guilistentry != null) {
            this.func_178066_a(this.field_178073_v.func_76041_a(guipagebuttonlist$guilistentry.func_178935_b()), false);
         }
      }

      for(GuiPageButtonList.GuiListEntry guipagebuttonlist$guilistentry1 : this.field_178078_x[p_178060_2_]) {
         if (guipagebuttonlist$guilistentry1 != null) {
            this.func_178066_a(this.field_178073_v.func_76041_a(guipagebuttonlist$guilistentry1.func_178935_b()), true);
         }
      }

   }

   private void func_178066_a(Gui p_178066_1_, boolean p_178066_2_) {
      if (p_178066_1_ instanceof GuiButton) {
         ((GuiButton)p_178066_1_).field_146125_m = p_178066_2_;
      } else if (p_178066_1_ instanceof GuiTextField) {
         ((GuiTextField)p_178066_1_).func_146189_e(p_178066_2_);
      } else if (p_178066_1_ instanceof GuiLabel) {
         ((GuiLabel)p_178066_1_).field_146172_j = p_178066_2_;
      }

   }

   @Nullable
   private Gui func_178058_a(@Nullable GuiPageButtonList.GuiListEntry p_178058_1_, int p_178058_2_, boolean p_178058_3_) {
      if (p_178058_1_ instanceof GuiPageButtonList.GuiSlideEntry) {
         return this.func_178067_a(this.field_148155_a / 2 - 155 + p_178058_2_, 0, (GuiPageButtonList.GuiSlideEntry)p_178058_1_);
      } else if (p_178058_1_ instanceof GuiPageButtonList.GuiButtonEntry) {
         return this.func_178065_a(this.field_148155_a / 2 - 155 + p_178058_2_, 0, (GuiPageButtonList.GuiButtonEntry)p_178058_1_);
      } else if (p_178058_1_ instanceof GuiPageButtonList.EditBoxEntry) {
         return this.func_178068_a(this.field_148155_a / 2 - 155 + p_178058_2_, 0, (GuiPageButtonList.EditBoxEntry)p_178058_1_);
      } else {
         return p_178058_1_ instanceof GuiPageButtonList.GuiLabelEntry ? this.func_178063_a(this.field_148155_a / 2 - 155 + p_178058_2_, 0, (GuiPageButtonList.GuiLabelEntry)p_178058_1_, p_178058_3_) : null;
      }
   }

   public void func_181155_a(boolean p_181155_1_) {
      for(GuiPageButtonList.GuiEntry guipagebuttonlist$guientry : this.field_178074_u) {
         if (guipagebuttonlist$guientry.field_178029_b instanceof GuiButton) {
            ((GuiButton)guipagebuttonlist$guientry.field_178029_b).field_146124_l = p_181155_1_;
         }

         if (guipagebuttonlist$guientry.field_178030_c instanceof GuiButton) {
            ((GuiButton)guipagebuttonlist$guientry.field_178030_c).field_146124_l = p_181155_1_;
         }
      }

   }

   public boolean func_148179_a(int p_148179_1_, int p_148179_2_, int p_148179_3_) {
      boolean flag = super.func_148179_a(p_148179_1_, p_148179_2_, p_148179_3_);
      int i = this.func_148124_c(p_148179_1_, p_148179_2_);
      if (i >= 0) {
         GuiPageButtonList.GuiEntry guipagebuttonlist$guientry = this.func_148180_b(i);
         if (this.field_178075_A != guipagebuttonlist$guientry.field_178028_d && this.field_178075_A != null && this.field_178075_A instanceof GuiTextField) {
            ((GuiTextField)this.field_178075_A).func_146195_b(false);
         }

         this.field_178075_A = guipagebuttonlist$guientry.field_178028_d;
      }

      return flag;
   }

   private GuiSlider func_178067_a(int p_178067_1_, int p_178067_2_, GuiPageButtonList.GuiSlideEntry p_178067_3_) {
      GuiSlider guislider = new GuiSlider(this.field_178076_z, p_178067_3_.func_178935_b(), p_178067_1_, p_178067_2_, p_178067_3_.func_178936_c(), p_178067_3_.func_178943_e(), p_178067_3_.func_178944_f(), p_178067_3_.func_178942_g(), p_178067_3_.func_178945_a());
      guislider.field_146125_m = p_178067_3_.func_178934_d();
      return guislider;
   }

   private GuiListButton func_178065_a(int p_178065_1_, int p_178065_2_, GuiPageButtonList.GuiButtonEntry p_178065_3_) {
      GuiListButton guilistbutton = new GuiListButton(this.field_178076_z, p_178065_3_.func_178935_b(), p_178065_1_, p_178065_2_, p_178065_3_.func_178936_c(), p_178065_3_.func_178940_a());
      guilistbutton.field_146125_m = p_178065_3_.func_178934_d();
      return guilistbutton;
   }

   private GuiTextField func_178068_a(int p_178068_1_, int p_178068_2_, GuiPageButtonList.EditBoxEntry p_178068_3_) {
      GuiTextField guitextfield = new GuiTextField(p_178068_3_.func_178935_b(), this.field_148161_k.field_71466_p, p_178068_1_, p_178068_2_, 150, 20);
      guitextfield.func_146180_a(p_178068_3_.func_178936_c());
      guitextfield.func_175207_a(this.field_178076_z);
      guitextfield.func_146189_e(p_178068_3_.func_178934_d());
      guitextfield.func_175205_a(p_178068_3_.func_178950_a());
      return guitextfield;
   }

   private GuiLabel func_178063_a(int p_178063_1_, int p_178063_2_, GuiPageButtonList.GuiLabelEntry p_178063_3_, boolean p_178063_4_) {
      GuiLabel guilabel;
      if (p_178063_4_) {
         guilabel = new GuiLabel(this.field_148161_k.field_71466_p, p_178063_3_.func_178935_b(), p_178063_1_, p_178063_2_, this.field_148155_a - p_178063_1_ * 2, 20, -1);
      } else {
         guilabel = new GuiLabel(this.field_148161_k.field_71466_p, p_178063_3_.func_178935_b(), p_178063_1_, p_178063_2_, 150, 20, -1);
      }

      guilabel.field_146172_j = p_178063_3_.func_178934_d();
      guilabel.func_175202_a(p_178063_3_.func_178936_c());
      guilabel.func_175203_a();
      return guilabel;
   }

   public void func_178062_a(char p_178062_1_, int p_178062_2_) {
      if (this.field_178075_A instanceof GuiTextField) {
         GuiTextField guitextfield = (GuiTextField)this.field_178075_A;
         if (!GuiScreen.func_175279_e(p_178062_2_)) {
            if (p_178062_2_ == 15) {
               guitextfield.func_146195_b(false);
               int k = this.field_178072_w.indexOf(this.field_178075_A);
               if (GuiScreen.func_146272_n()) {
                  if (k == 0) {
                     k = this.field_178072_w.size() - 1;
                  } else {
                     --k;
                  }
               } else if (k == this.field_178072_w.size() - 1) {
                  k = 0;
               } else {
                  ++k;
               }

               this.field_178075_A = this.field_178072_w.get(k);
               guitextfield = (GuiTextField)this.field_178075_A;
               guitextfield.func_146195_b(true);
               int l = guitextfield.field_146210_g + this.field_148149_f;
               int i1 = guitextfield.field_146210_g;
               if (l > this.field_148154_c) {
                  this.field_148169_q += (float)(l - this.field_148154_c);
               } else if (i1 < this.field_148153_b) {
                  this.field_148169_q = (float)i1;
               }
            } else {
               guitextfield.func_146201_a(p_178062_1_, p_178062_2_);
            }

         } else {
            String s = GuiScreen.func_146277_j();
            String[] astring = s.split(";");
            int i = this.field_178072_w.indexOf(this.field_178075_A);
            int j = i;

            for(String s1 : astring) {
               GuiTextField guitextfield1 = this.field_178072_w.get(j);
               guitextfield1.func_146180_a(s1);
               guitextfield1.func_190516_a(guitextfield1.func_175206_d(), s1);
               if (j == this.field_178072_w.size() - 1) {
                  j = 0;
               } else {
                  ++j;
               }

               if (j == i) {
                  break;
               }
            }

         }
      }
   }

   public GuiPageButtonList.GuiEntry func_148180_b(int p_148180_1_) {
      return this.field_178074_u.get(p_148180_1_);
   }

   public int func_148127_b() {
      return this.field_178074_u.size();
   }

   public int func_148139_c() {
      return 400;
   }

   protected int func_148137_d() {
      return super.func_148137_d() + 32;
   }

   public static class EditBoxEntry extends GuiPageButtonList.GuiListEntry {
      private final Predicate<String> field_178951_a;

      public EditBoxEntry(int p_i45534_1_, String p_i45534_2_, boolean p_i45534_3_, Predicate<String> p_i45534_4_) {
         super(p_i45534_1_, p_i45534_2_, p_i45534_3_);
         this.field_178951_a = (Predicate)MoreObjects.firstNonNull(p_i45534_4_, Predicates.alwaysTrue());
      }

      public Predicate<String> func_178950_a() {
         return this.field_178951_a;
      }
   }

   public static class GuiButtonEntry extends GuiPageButtonList.GuiListEntry {
      private final boolean field_178941_a;

      public GuiButtonEntry(int p_i45535_1_, String p_i45535_2_, boolean p_i45535_3_, boolean p_i45535_4_) {
         super(p_i45535_1_, p_i45535_2_, p_i45535_3_);
         this.field_178941_a = p_i45535_4_;
      }

      public boolean func_178940_a() {
         return this.field_178941_a;
      }
   }

   public static class GuiEntry implements GuiListExtended.IGuiListEntry {
      private final Minecraft field_178031_a = Minecraft.func_71410_x();
      private final Gui field_178029_b;
      private final Gui field_178030_c;
      private Gui field_178028_d;

      public GuiEntry(@Nullable Gui p_i45533_1_, @Nullable Gui p_i45533_2_) {
         this.field_178029_b = p_i45533_1_;
         this.field_178030_c = p_i45533_2_;
      }

      public Gui func_178022_a() {
         return this.field_178029_b;
      }

      public Gui func_178021_b() {
         return this.field_178030_c;
      }

      public void func_192634_a(int p_192634_1_, int p_192634_2_, int p_192634_3_, int p_192634_4_, int p_192634_5_, int p_192634_6_, int p_192634_7_, boolean p_192634_8_, float p_192634_9_) {
         this.func_192636_a(this.field_178029_b, p_192634_3_, p_192634_6_, p_192634_7_, false, p_192634_9_);
         this.func_192636_a(this.field_178030_c, p_192634_3_, p_192634_6_, p_192634_7_, false, p_192634_9_);
      }

      private void func_192636_a(Gui p_192636_1_, int p_192636_2_, int p_192636_3_, int p_192636_4_, boolean p_192636_5_, float p_192636_6_) {
         if (p_192636_1_ != null) {
            if (p_192636_1_ instanceof GuiButton) {
               this.func_192635_a((GuiButton)p_192636_1_, p_192636_2_, p_192636_3_, p_192636_4_, p_192636_5_, p_192636_6_);
            } else if (p_192636_1_ instanceof GuiTextField) {
               this.func_178027_a((GuiTextField)p_192636_1_, p_192636_2_, p_192636_5_);
            } else if (p_192636_1_ instanceof GuiLabel) {
               this.func_178025_a((GuiLabel)p_192636_1_, p_192636_2_, p_192636_3_, p_192636_4_, p_192636_5_);
            }

         }
      }

      private void func_192635_a(GuiButton p_192635_1_, int p_192635_2_, int p_192635_3_, int p_192635_4_, boolean p_192635_5_, float p_192635_6_) {
         p_192635_1_.field_146129_i = p_192635_2_;
         if (!p_192635_5_) {
            p_192635_1_.func_191745_a(this.field_178031_a, p_192635_3_, p_192635_4_, p_192635_6_);
         }

      }

      private void func_178027_a(GuiTextField p_178027_1_, int p_178027_2_, boolean p_178027_3_) {
         p_178027_1_.field_146210_g = p_178027_2_;
         if (!p_178027_3_) {
            p_178027_1_.func_146194_f();
         }

      }

      private void func_178025_a(GuiLabel p_178025_1_, int p_178025_2_, int p_178025_3_, int p_178025_4_, boolean p_178025_5_) {
         p_178025_1_.field_146174_h = p_178025_2_;
         if (!p_178025_5_) {
            p_178025_1_.func_146159_a(this.field_178031_a, p_178025_3_, p_178025_4_);
         }

      }

      public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {
         this.func_192636_a(this.field_178029_b, p_192633_3_, 0, 0, true, p_192633_4_);
         this.func_192636_a(this.field_178030_c, p_192633_3_, 0, 0, true, p_192633_4_);
      }

      public boolean func_148278_a(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
         boolean flag = this.func_178026_a(this.field_178029_b, p_148278_2_, p_148278_3_, p_148278_4_);
         boolean flag1 = this.func_178026_a(this.field_178030_c, p_148278_2_, p_148278_3_, p_148278_4_);
         return flag || flag1;
      }

      private boolean func_178026_a(Gui p_178026_1_, int p_178026_2_, int p_178026_3_, int p_178026_4_) {
         if (p_178026_1_ == null) {
            return false;
         } else if (p_178026_1_ instanceof GuiButton) {
            return this.func_178023_a((GuiButton)p_178026_1_, p_178026_2_, p_178026_3_, p_178026_4_);
         } else {
            if (p_178026_1_ instanceof GuiTextField) {
               this.func_178018_a((GuiTextField)p_178026_1_, p_178026_2_, p_178026_3_, p_178026_4_);
            }

            return false;
         }
      }

      private boolean func_178023_a(GuiButton p_178023_1_, int p_178023_2_, int p_178023_3_, int p_178023_4_) {
         boolean flag = p_178023_1_.func_146116_c(this.field_178031_a, p_178023_2_, p_178023_3_);
         if (flag) {
            this.field_178028_d = p_178023_1_;
         }

         return flag;
      }

      private void func_178018_a(GuiTextField p_178018_1_, int p_178018_2_, int p_178018_3_, int p_178018_4_) {
         p_178018_1_.func_146192_a(p_178018_2_, p_178018_3_, p_178018_4_);
         if (p_178018_1_.func_146206_l()) {
            this.field_178028_d = p_178018_1_;
         }

      }

      public void func_148277_b(int p_148277_1_, int p_148277_2_, int p_148277_3_, int p_148277_4_, int p_148277_5_, int p_148277_6_) {
         this.func_178016_b(this.field_178029_b, p_148277_2_, p_148277_3_, p_148277_4_);
         this.func_178016_b(this.field_178030_c, p_148277_2_, p_148277_3_, p_148277_4_);
      }

      private void func_178016_b(Gui p_178016_1_, int p_178016_2_, int p_178016_3_, int p_178016_4_) {
         if (p_178016_1_ != null) {
            if (p_178016_1_ instanceof GuiButton) {
               this.func_178019_b((GuiButton)p_178016_1_, p_178016_2_, p_178016_3_, p_178016_4_);
            }

         }
      }

      private void func_178019_b(GuiButton p_178019_1_, int p_178019_2_, int p_178019_3_, int p_178019_4_) {
         p_178019_1_.func_146118_a(p_178019_2_, p_178019_3_);
      }
   }

   public static class GuiLabelEntry extends GuiPageButtonList.GuiListEntry {
      public GuiLabelEntry(int p_i45532_1_, String p_i45532_2_, boolean p_i45532_3_) {
         super(p_i45532_1_, p_i45532_2_, p_i45532_3_);
      }
   }

   public static class GuiListEntry {
      private final int field_178939_a;
      private final String field_178937_b;
      private final boolean field_178938_c;

      public GuiListEntry(int p_i45531_1_, String p_i45531_2_, boolean p_i45531_3_) {
         this.field_178939_a = p_i45531_1_;
         this.field_178937_b = p_i45531_2_;
         this.field_178938_c = p_i45531_3_;
      }

      public int func_178935_b() {
         return this.field_178939_a;
      }

      public String func_178936_c() {
         return this.field_178937_b;
      }

      public boolean func_178934_d() {
         return this.field_178938_c;
      }
   }

   public interface GuiResponder {
      void func_175321_a(int var1, boolean var2);

      void func_175320_a(int var1, float var2);

      void func_175319_a(int var1, String var2);
   }

   public static class GuiSlideEntry extends GuiPageButtonList.GuiListEntry {
      private final GuiSlider.FormatHelper field_178949_a;
      private final float field_178947_b;
      private final float field_178948_c;
      private final float field_178946_d;

      public GuiSlideEntry(int p_i45530_1_, String p_i45530_2_, boolean p_i45530_3_, GuiSlider.FormatHelper p_i45530_4_, float p_i45530_5_, float p_i45530_6_, float p_i45530_7_) {
         super(p_i45530_1_, p_i45530_2_, p_i45530_3_);
         this.field_178949_a = p_i45530_4_;
         this.field_178947_b = p_i45530_5_;
         this.field_178948_c = p_i45530_6_;
         this.field_178946_d = p_i45530_7_;
      }

      public GuiSlider.FormatHelper func_178945_a() {
         return this.field_178949_a;
      }

      public float func_178943_e() {
         return this.field_178947_b;
      }

      public float func_178944_f() {
         return this.field_178948_c;
      }

      public float func_178942_g() {
         return this.field_178946_d;
      }
   }
}
