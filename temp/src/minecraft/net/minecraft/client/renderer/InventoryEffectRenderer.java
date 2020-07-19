package net.minecraft.client.renderer;

import com.google.common.collect.Ordering;
import java.util.Collection;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public abstract class InventoryEffectRenderer extends GuiContainer {
   protected boolean field_147045_u;

   public InventoryEffectRenderer(Container p_i1089_1_) {
      super(p_i1089_1_);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.func_175378_g();
   }

   protected void func_175378_g() {
      if (this.field_146297_k.field_71439_g.func_70651_bq().isEmpty()) {
         this.field_147003_i = (this.field_146294_l - this.field_146999_f) / 2;
         this.field_147045_u = false;
      } else {
         this.field_147003_i = 160 + (this.field_146294_l - this.field_146999_f - 200) / 2;
         this.field_147045_u = true;
      }

   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
      if (this.field_147045_u) {
         this.func_147044_g();
      }

   }

   private void func_147044_g() {
      int i = this.field_147003_i - 124;
      int j = this.field_147009_r;
      int k = 166;
      Collection<PotionEffect> collection = this.field_146297_k.field_71439_g.func_70651_bq();
      if (!collection.isEmpty()) {
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179140_f();
         int l = 33;
         if (collection.size() > 5) {
            l = 132 / (collection.size() - 1);
         }

         for(PotionEffect potioneffect : Ordering.natural().sortedCopy(collection)) {
            Potion potion = potioneffect.func_188419_a();
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146297_k.func_110434_K().func_110577_a(field_147001_a);
            this.func_73729_b(i, j, 0, 166, 140, 32);
            if (potion.func_76400_d()) {
               int i1 = potion.func_76392_e();
               this.func_73729_b(i + 6, j + 7, 0 + i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
            }

            String s1 = I18n.func_135052_a(potion.func_76393_a());
            if (potioneffect.func_76458_c() == 1) {
               s1 = s1 + " " + I18n.func_135052_a("enchantment.level.2");
            } else if (potioneffect.func_76458_c() == 2) {
               s1 = s1 + " " + I18n.func_135052_a("enchantment.level.3");
            } else if (potioneffect.func_76458_c() == 3) {
               s1 = s1 + " " + I18n.func_135052_a("enchantment.level.4");
            }

            this.field_146289_q.func_175063_a(s1, (float)(i + 10 + 18), (float)(j + 6), 16777215);
            String s = Potion.func_188410_a(potioneffect, 1.0F);
            this.field_146289_q.func_175063_a(s, (float)(i + 10 + 18), (float)(j + 6 + 10), 8355711);
            j += l;
         }

      }
   }
}
