package net.minecraft.client.gui.inventory;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiBrewingStand extends GuiContainer {
   private static final ResourceLocation field_147014_u = new ResourceLocation("textures/gui/container/brewing_stand.png");
   private static final int[] field_184857_v = new int[]{29, 24, 20, 16, 11, 6, 0};
   private final InventoryPlayer field_175384_v;
   private final IInventory field_147013_v;

   public GuiBrewingStand(InventoryPlayer p_i45506_1_, IInventory p_i45506_2_) {
      super(new ContainerBrewingStand(p_i45506_1_, p_i45506_2_));
      this.field_175384_v = p_i45506_1_;
      this.field_147013_v = p_i45506_2_;
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
      this.func_191948_b(p_73863_1_, p_73863_2_);
   }

   protected void func_146979_b(int p_146979_1_, int p_146979_2_) {
      String s = this.field_147013_v.func_145748_c_().func_150260_c();
      this.field_146289_q.func_78276_b(s, this.field_146999_f / 2 - this.field_146289_q.func_78256_a(s) / 2, 6, 4210752);
      this.field_146289_q.func_78276_b(this.field_175384_v.func_145748_c_().func_150260_c(), 8, this.field_147000_g - 96 + 2, 4210752);
   }

   protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(field_147014_u);
      int i = (this.field_146294_l - this.field_146999_f) / 2;
      int j = (this.field_146295_m - this.field_147000_g) / 2;
      this.func_73729_b(i, j, 0, 0, this.field_146999_f, this.field_147000_g);
      int k = this.field_147013_v.func_174887_a_(1);
      int l = MathHelper.func_76125_a((18 * k + 20 - 1) / 20, 0, 18);
      if (l > 0) {
         this.func_73729_b(i + 60, j + 44, 176, 29, l, 4);
      }

      int i1 = this.field_147013_v.func_174887_a_(0);
      if (i1 > 0) {
         int j1 = (int)(28.0F * (1.0F - (float)i1 / 400.0F));
         if (j1 > 0) {
            this.func_73729_b(i + 97, j + 16, 176, 0, 9, j1);
         }

         j1 = field_184857_v[i1 / 2 % 7];
         if (j1 > 0) {
            this.func_73729_b(i + 63, j + 14 + 29 - j1, 185, 29 - j1, 12, j1);
         }
      }

   }
}
