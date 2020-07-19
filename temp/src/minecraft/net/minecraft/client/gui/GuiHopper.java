package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiHopper extends GuiContainer {
   private static final ResourceLocation field_147085_u = new ResourceLocation("textures/gui/container/hopper.png");
   private final IInventory field_147084_v;
   private final IInventory field_147083_w;

   public GuiHopper(InventoryPlayer p_i1092_1_, IInventory p_i1092_2_) {
      super(new ContainerHopper(p_i1092_1_, p_i1092_2_, Minecraft.func_71410_x().field_71439_g));
      this.field_147084_v = p_i1092_1_;
      this.field_147083_w = p_i1092_2_;
      this.field_146291_p = false;
      this.field_147000_g = 133;
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
      this.func_191948_b(p_73863_1_, p_73863_2_);
   }

   protected void func_146979_b(int p_146979_1_, int p_146979_2_) {
      this.field_146289_q.func_78276_b(this.field_147083_w.func_145748_c_().func_150260_c(), 8, 6, 4210752);
      this.field_146289_q.func_78276_b(this.field_147084_v.func_145748_c_().func_150260_c(), 8, this.field_147000_g - 96 + 2, 4210752);
   }

   protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(field_147085_u);
      int i = (this.field_146294_l - this.field_146999_f) / 2;
      int j = (this.field_146295_m - this.field_147000_g) / 2;
      this.func_73729_b(i, j, 0, 0, this.field_146999_f, this.field_147000_g);
   }
}
