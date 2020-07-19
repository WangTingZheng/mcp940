package net.minecraft.client.resources;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public abstract class ResourcePackListEntry implements GuiListExtended.IGuiListEntry {
   private static final ResourceLocation field_148316_c = new ResourceLocation("textures/gui/resource_packs.png");
   private static final ITextComponent field_183020_d = new TextComponentTranslation("resourcePack.incompatible", new Object[0]);
   private static final ITextComponent field_183021_e = new TextComponentTranslation("resourcePack.incompatible.old", new Object[0]);
   private static final ITextComponent field_183022_f = new TextComponentTranslation("resourcePack.incompatible.new", new Object[0]);
   protected final Minecraft field_148317_a;
   protected final GuiScreenResourcePacks field_148315_b;

   public ResourcePackListEntry(GuiScreenResourcePacks p_i45051_1_) {
      this.field_148315_b = p_i45051_1_;
      this.field_148317_a = Minecraft.func_71410_x();
   }

   public void func_192634_a(int p_192634_1_, int p_192634_2_, int p_192634_3_, int p_192634_4_, int p_192634_5_, int p_192634_6_, int p_192634_7_, boolean p_192634_8_, float p_192634_9_) {
      int i = this.func_183019_a();
      if (i != 3) {
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         Gui.func_73734_a(p_192634_2_ - 1, p_192634_3_ - 1, p_192634_2_ + p_192634_4_ - 9, p_192634_3_ + p_192634_5_ + 1, -8978432);
      }

      this.func_148313_c();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      Gui.func_146110_a(p_192634_2_, p_192634_3_, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
      String s = this.func_148312_b();
      String s1 = this.func_148311_a();
      if (this.func_148310_d() && (this.field_148317_a.field_71474_y.field_85185_A || p_192634_8_)) {
         this.field_148317_a.func_110434_K().func_110577_a(field_148316_c);
         Gui.func_73734_a(p_192634_2_, p_192634_3_, p_192634_2_ + 32, p_192634_3_ + 32, -1601138544);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         int j = p_192634_6_ - p_192634_2_;
         int k = p_192634_7_ - p_192634_3_;
         if (i < 3) {
            s = field_183020_d.func_150254_d();
            s1 = field_183021_e.func_150254_d();
         } else if (i > 3) {
            s = field_183020_d.func_150254_d();
            s1 = field_183022_f.func_150254_d();
         }

         if (this.func_148309_e()) {
            if (j < 32) {
               Gui.func_146110_a(p_192634_2_, p_192634_3_, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.func_146110_a(p_192634_2_, p_192634_3_, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         } else {
            if (this.func_148308_f()) {
               if (j < 16) {
                  Gui.func_146110_a(p_192634_2_, p_192634_3_, 32.0F, 32.0F, 32, 32, 256.0F, 256.0F);
               } else {
                  Gui.func_146110_a(p_192634_2_, p_192634_3_, 32.0F, 0.0F, 32, 32, 256.0F, 256.0F);
               }
            }

            if (this.func_148314_g()) {
               if (j < 32 && j > 16 && k < 16) {
                  Gui.func_146110_a(p_192634_2_, p_192634_3_, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
               } else {
                  Gui.func_146110_a(p_192634_2_, p_192634_3_, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
               }
            }

            if (this.func_148307_h()) {
               if (j < 32 && j > 16 && k > 16) {
                  Gui.func_146110_a(p_192634_2_, p_192634_3_, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
               } else {
                  Gui.func_146110_a(p_192634_2_, p_192634_3_, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
               }
            }
         }
      }

      int i1 = this.field_148317_a.field_71466_p.func_78256_a(s);
      if (i1 > 157) {
         s = this.field_148317_a.field_71466_p.func_78269_a(s, 157 - this.field_148317_a.field_71466_p.func_78256_a("...")) + "...";
      }

      this.field_148317_a.field_71466_p.func_175063_a(s, (float)(p_192634_2_ + 32 + 2), (float)(p_192634_3_ + 1), 16777215);
      List<String> list = this.field_148317_a.field_71466_p.func_78271_c(s1, 157);

      for(int l = 0; l < 2 && l < list.size(); ++l) {
         this.field_148317_a.field_71466_p.func_175063_a(list.get(l), (float)(p_192634_2_ + 32 + 2), (float)(p_192634_3_ + 12 + 10 * l), 8421504);
      }

   }

   protected abstract int func_183019_a();

   protected abstract String func_148311_a();

   protected abstract String func_148312_b();

   protected abstract void func_148313_c();

   protected boolean func_148310_d() {
      return true;
   }

   protected boolean func_148309_e() {
      return !this.field_148315_b.func_146961_a(this);
   }

   protected boolean func_148308_f() {
      return this.field_148315_b.func_146961_a(this);
   }

   protected boolean func_148314_g() {
      List<ResourcePackListEntry> list = this.field_148315_b.func_146962_b(this);
      int i = list.indexOf(this);
      return i > 0 && ((ResourcePackListEntry)list.get(i - 1)).func_148310_d();
   }

   protected boolean func_148307_h() {
      List<ResourcePackListEntry> list = this.field_148315_b.func_146962_b(this);
      int i = list.indexOf(this);
      return i >= 0 && i < list.size() - 1 && ((ResourcePackListEntry)list.get(i + 1)).func_148310_d();
   }

   public boolean func_148278_a(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
      if (this.func_148310_d() && p_148278_5_ <= 32) {
         if (this.func_148309_e()) {
            this.field_148315_b.func_175288_g();
            final int j = ((ResourcePackListEntry)this.field_148315_b.func_146963_h().get(0)).func_186768_j() ? 1 : 0;
            int l = this.func_183019_a();
            if (l == 3) {
               this.field_148315_b.func_146962_b(this).remove(this);
               this.field_148315_b.func_146963_h().add(j, this);
            } else {
               String s = I18n.func_135052_a("resourcePack.incompatible.confirm.title");
               String s1 = I18n.func_135052_a("resourcePack.incompatible.confirm." + (l > 3 ? "new" : "old"));
               this.field_148317_a.func_147108_a(new GuiYesNo(new GuiYesNoCallback() {
                  public void func_73878_a(boolean p_73878_1_, int p_73878_2_) {
                     List<ResourcePackListEntry> list2 = ResourcePackListEntry.this.field_148315_b.func_146962_b(ResourcePackListEntry.this);
                     ResourcePackListEntry.this.field_148317_a.func_147108_a(ResourcePackListEntry.this.field_148315_b);
                     if (p_73878_1_) {
                        list2.remove(ResourcePackListEntry.this);
                        ResourcePackListEntry.this.field_148315_b.func_146963_h().add(j, ResourcePackListEntry.this);
                     }

                  }
               }, s, s1, 0));
            }

            return true;
         }

         if (p_148278_5_ < 16 && this.func_148308_f()) {
            this.field_148315_b.func_146962_b(this).remove(this);
            this.field_148315_b.func_146964_g().add(0, this);
            this.field_148315_b.func_175288_g();
            return true;
         }

         if (p_148278_5_ > 16 && p_148278_6_ < 16 && this.func_148314_g()) {
            List<ResourcePackListEntry> list1 = this.field_148315_b.func_146962_b(this);
            int k = list1.indexOf(this);
            list1.remove(this);
            list1.add(k - 1, this);
            this.field_148315_b.func_175288_g();
            return true;
         }

         if (p_148278_5_ > 16 && p_148278_6_ > 16 && this.func_148307_h()) {
            List<ResourcePackListEntry> list = this.field_148315_b.func_146962_b(this);
            int i = list.indexOf(this);
            list.remove(this);
            list.add(i + 1, this);
            this.field_148315_b.func_175288_g();
            return true;
         }
      }

      return false;
   }

   public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {
   }

   public void func_148277_b(int p_148277_1_, int p_148277_2_, int p_148277_3_, int p_148277_4_, int p_148277_5_, int p_148277_6_) {
   }

   public boolean func_186768_j() {
      return false;
   }
}
