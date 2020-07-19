package net.minecraft.client.gui.inventory;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.recipebook.GuiRecipeBook;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuiCrafting extends GuiContainer implements IRecipeShownListener {
   private static final ResourceLocation field_147019_u = new ResourceLocation("textures/gui/container/crafting_table.png");
   private GuiButtonImage field_192049_w;
   private final GuiRecipeBook field_192050_x;
   private boolean field_193112_y;

   public GuiCrafting(InventoryPlayer p_i45504_1_, World p_i45504_2_) {
      this(p_i45504_1_, p_i45504_2_, BlockPos.field_177992_a);
   }

   public GuiCrafting(InventoryPlayer p_i45505_1_, World p_i45505_2_, BlockPos p_i45505_3_) {
      super(new ContainerWorkbench(p_i45505_1_, p_i45505_2_, p_i45505_3_));
      this.field_192050_x = new GuiRecipeBook();
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.field_193112_y = this.field_146294_l < 379;
      this.field_192050_x.func_191856_a(this.field_146294_l, this.field_146295_m, this.field_146297_k, this.field_193112_y, this.field_147002_h, ((ContainerWorkbench)this.field_147002_h).field_75162_e);
      this.field_147003_i = this.field_192050_x.func_193011_a(this.field_193112_y, this.field_146294_l, this.field_146999_f);
      this.field_192049_w = new GuiButtonImage(10, this.field_147003_i + 5, this.field_146295_m / 2 - 49, 20, 18, 0, 168, 19, field_147019_u);
      this.field_146292_n.add(this.field_192049_w);
   }

   public void func_73876_c() {
      super.func_73876_c();
      this.field_192050_x.func_193957_d();
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      if (this.field_192050_x.func_191878_b() && this.field_193112_y) {
         this.func_146976_a(p_73863_3_, p_73863_1_, p_73863_2_);
         this.field_192050_x.func_191861_a(p_73863_1_, p_73863_2_, p_73863_3_);
      } else {
         this.field_192050_x.func_191861_a(p_73863_1_, p_73863_2_, p_73863_3_);
         super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
         this.field_192050_x.func_191864_a(this.field_147003_i, this.field_147009_r, true, p_73863_3_);
      }

      this.func_191948_b(p_73863_1_, p_73863_2_);
      this.field_192050_x.func_191876_c(this.field_147003_i, this.field_147009_r, p_73863_1_, p_73863_2_);
   }

   protected void func_146979_b(int p_146979_1_, int p_146979_2_) {
      this.field_146289_q.func_78276_b(I18n.func_135052_a("container.crafting"), 28, 6, 4210752);
      this.field_146289_q.func_78276_b(I18n.func_135052_a("container.inventory"), 8, this.field_147000_g - 96 + 2, 4210752);
   }

   protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(field_147019_u);
      int i = this.field_147003_i;
      int j = (this.field_146295_m - this.field_147000_g) / 2;
      this.func_73729_b(i, j, 0, 0, this.field_146999_f, this.field_147000_g);
   }

   protected boolean func_146978_c(int p_146978_1_, int p_146978_2_, int p_146978_3_, int p_146978_4_, int p_146978_5_, int p_146978_6_) {
      return (!this.field_193112_y || !this.field_192050_x.func_191878_b()) && super.func_146978_c(p_146978_1_, p_146978_2_, p_146978_3_, p_146978_4_, p_146978_5_, p_146978_6_);
   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) throws IOException {
      if (!this.field_192050_x.func_191862_a(p_73864_1_, p_73864_2_, p_73864_3_)) {
         if (!this.field_193112_y || !this.field_192050_x.func_191878_b()) {
            super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
         }
      }
   }

   protected boolean func_193983_c(int p_193983_1_, int p_193983_2_, int p_193983_3_, int p_193983_4_) {
      boolean flag = p_193983_1_ < p_193983_3_ || p_193983_2_ < p_193983_4_ || p_193983_1_ >= p_193983_3_ + this.field_146999_f || p_193983_2_ >= p_193983_4_ + this.field_147000_g;
      return this.field_192050_x.func_193955_c(p_193983_1_, p_193983_2_, this.field_147003_i, this.field_147009_r, this.field_146999_f, this.field_147000_g) && flag;
   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
      if (p_146284_1_.field_146127_k == 10) {
         this.field_192050_x.func_193014_a(this.field_193112_y, ((ContainerWorkbench)this.field_147002_h).field_75162_e);
         this.field_192050_x.func_191866_a();
         this.field_147003_i = this.field_192050_x.func_193011_a(this.field_193112_y, this.field_146294_l, this.field_146999_f);
         this.field_192049_w.func_191746_c(this.field_147003_i + 5, this.field_146295_m / 2 - 49);
      }

   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) throws IOException {
      if (!this.field_192050_x.func_191859_a(p_73869_1_, p_73869_2_)) {
         super.func_73869_a(p_73869_1_, p_73869_2_);
      }

   }

   protected void func_184098_a(Slot p_184098_1_, int p_184098_2_, int p_184098_3_, ClickType p_184098_4_) {
      super.func_184098_a(p_184098_1_, p_184098_2_, p_184098_3_, p_184098_4_);
      this.field_192050_x.func_191874_a(p_184098_1_);
   }

   public void func_192043_J_() {
      this.field_192050_x.func_193948_e();
   }

   public void func_146281_b() {
      this.field_192050_x.func_191871_c();
      super.func_146281_b();
   }
}
