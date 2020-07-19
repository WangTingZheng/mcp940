package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;

public class GuiCreateFlatWorld extends GuiScreen {
   private final GuiCreateWorld field_146385_f;
   private FlatGeneratorInfo field_146387_g = FlatGeneratorInfo.func_82649_e();
   private String field_146393_h;
   private String field_146394_i;
   private String field_146391_r;
   private GuiCreateFlatWorld.Details field_146390_s;
   private GuiButton field_146389_t;
   private GuiButton field_146388_u;
   private GuiButton field_146386_v;

   public GuiCreateFlatWorld(GuiCreateWorld p_i1029_1_, String p_i1029_2_) {
      this.field_146385_f = p_i1029_1_;
      this.func_146383_a(p_i1029_2_);
   }

   public String func_146384_e() {
      return this.field_146387_g.toString();
   }

   public void func_146383_a(String p_146383_1_) {
      this.field_146387_g = FlatGeneratorInfo.func_82651_a(p_146383_1_);
   }

   public void func_73866_w_() {
      this.field_146292_n.clear();
      this.field_146393_h = I18n.func_135052_a("createWorld.customize.flat.title");
      this.field_146394_i = I18n.func_135052_a("createWorld.customize.flat.tile");
      this.field_146391_r = I18n.func_135052_a("createWorld.customize.flat.height");
      this.field_146390_s = new GuiCreateFlatWorld.Details();
      this.field_146389_t = this.func_189646_b(new GuiButton(2, this.field_146294_l / 2 - 154, this.field_146295_m - 52, 100, 20, I18n.func_135052_a("createWorld.customize.flat.addLayer") + " (NYI)"));
      this.field_146388_u = this.func_189646_b(new GuiButton(3, this.field_146294_l / 2 - 50, this.field_146295_m - 52, 100, 20, I18n.func_135052_a("createWorld.customize.flat.editLayer") + " (NYI)"));
      this.field_146386_v = this.func_189646_b(new GuiButton(4, this.field_146294_l / 2 - 155, this.field_146295_m - 52, 150, 20, I18n.func_135052_a("createWorld.customize.flat.removeLayer")));
      this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 155, this.field_146295_m - 28, 150, 20, I18n.func_135052_a("gui.done")));
      this.field_146292_n.add(new GuiButton(5, this.field_146294_l / 2 + 5, this.field_146295_m - 52, 150, 20, I18n.func_135052_a("createWorld.customize.presets")));
      this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 + 5, this.field_146295_m - 28, 150, 20, I18n.func_135052_a("gui.cancel")));
      this.field_146389_t.field_146125_m = false;
      this.field_146388_u.field_146125_m = false;
      this.field_146387_g.func_82645_d();
      this.func_146375_g();
   }

   public void func_146274_d() throws IOException {
      super.func_146274_d();
      this.field_146390_s.func_178039_p();
   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
      int i = this.field_146387_g.func_82650_c().size() - this.field_146390_s.field_148228_k - 1;
      if (p_146284_1_.field_146127_k == 1) {
         this.field_146297_k.func_147108_a(this.field_146385_f);
      } else if (p_146284_1_.field_146127_k == 0) {
         this.field_146385_f.field_146334_a = this.func_146384_e();
         this.field_146297_k.func_147108_a(this.field_146385_f);
      } else if (p_146284_1_.field_146127_k == 5) {
         this.field_146297_k.func_147108_a(new GuiFlatPresets(this));
      } else if (p_146284_1_.field_146127_k == 4 && this.func_146382_i()) {
         this.field_146387_g.func_82650_c().remove(i);
         this.field_146390_s.field_148228_k = Math.min(this.field_146390_s.field_148228_k, this.field_146387_g.func_82650_c().size() - 1);
      }

      this.field_146387_g.func_82645_d();
      this.func_146375_g();
   }

   public void func_146375_g() {
      boolean flag = this.func_146382_i();
      this.field_146386_v.field_146124_l = flag;
      this.field_146388_u.field_146124_l = flag;
      this.field_146388_u.field_146124_l = false;
      this.field_146389_t.field_146124_l = false;
   }

   private boolean func_146382_i() {
      return this.field_146390_s.field_148228_k > -1 && this.field_146390_s.field_148228_k < this.field_146387_g.func_82650_c().size();
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.field_146390_s.func_148128_a(p_73863_1_, p_73863_2_, p_73863_3_);
      this.func_73732_a(this.field_146289_q, this.field_146393_h, this.field_146294_l / 2, 8, 16777215);
      int i = this.field_146294_l / 2 - 92 - 16;
      this.func_73731_b(this.field_146289_q, this.field_146394_i, i, 32, 16777215);
      this.func_73731_b(this.field_146289_q, this.field_146391_r, i + 2 + 213 - this.field_146289_q.func_78256_a(this.field_146391_r), 32, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   class Details extends GuiSlot {
      public int field_148228_k = -1;

      public Details() {
         super(GuiCreateFlatWorld.this.field_146297_k, GuiCreateFlatWorld.this.field_146294_l, GuiCreateFlatWorld.this.field_146295_m, 43, GuiCreateFlatWorld.this.field_146295_m - 60, 24);
      }

      private void func_148225_a(int p_148225_1_, int p_148225_2_, ItemStack p_148225_3_) {
         this.func_148226_e(p_148225_1_ + 1, p_148225_2_ + 1);
         GlStateManager.func_179091_B();
         if (!p_148225_3_.func_190926_b()) {
            RenderHelper.func_74520_c();
            GuiCreateFlatWorld.this.field_146296_j.func_175042_a(p_148225_3_, p_148225_1_ + 2, p_148225_2_ + 2);
            RenderHelper.func_74518_a();
         }

         GlStateManager.func_179101_C();
      }

      private void func_148226_e(int p_148226_1_, int p_148226_2_) {
         this.func_148224_c(p_148226_1_, p_148226_2_, 0, 0);
      }

      private void func_148224_c(int p_148224_1_, int p_148224_2_, int p_148224_3_, int p_148224_4_) {
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_148161_k.func_110434_K().func_110577_a(Gui.field_110323_l);
         float f = 0.0078125F;
         float f1 = 0.0078125F;
         int i = 18;
         int j = 18;
         Tessellator tessellator = Tessellator.func_178181_a();
         BufferBuilder bufferbuilder = tessellator.func_178180_c();
         bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
         bufferbuilder.func_181662_b((double)(p_148224_1_ + 0), (double)(p_148224_2_ + 18), (double)GuiCreateFlatWorld.this.field_73735_i).func_187315_a((double)((float)(p_148224_3_ + 0) * 0.0078125F), (double)((float)(p_148224_4_ + 18) * 0.0078125F)).func_181675_d();
         bufferbuilder.func_181662_b((double)(p_148224_1_ + 18), (double)(p_148224_2_ + 18), (double)GuiCreateFlatWorld.this.field_73735_i).func_187315_a((double)((float)(p_148224_3_ + 18) * 0.0078125F), (double)((float)(p_148224_4_ + 18) * 0.0078125F)).func_181675_d();
         bufferbuilder.func_181662_b((double)(p_148224_1_ + 18), (double)(p_148224_2_ + 0), (double)GuiCreateFlatWorld.this.field_73735_i).func_187315_a((double)((float)(p_148224_3_ + 18) * 0.0078125F), (double)((float)(p_148224_4_ + 0) * 0.0078125F)).func_181675_d();
         bufferbuilder.func_181662_b((double)(p_148224_1_ + 0), (double)(p_148224_2_ + 0), (double)GuiCreateFlatWorld.this.field_73735_i).func_187315_a((double)((float)(p_148224_3_ + 0) * 0.0078125F), (double)((float)(p_148224_4_ + 0) * 0.0078125F)).func_181675_d();
         tessellator.func_78381_a();
      }

      protected int func_148127_b() {
         return GuiCreateFlatWorld.this.field_146387_g.func_82650_c().size();
      }

      protected void func_148144_a(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_) {
         this.field_148228_k = p_148144_1_;
         GuiCreateFlatWorld.this.func_146375_g();
      }

      protected boolean func_148131_a(int p_148131_1_) {
         return p_148131_1_ == this.field_148228_k;
      }

      protected void func_148123_a() {
      }

      protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_) {
         FlatLayerInfo flatlayerinfo = (FlatLayerInfo)GuiCreateFlatWorld.this.field_146387_g.func_82650_c().get(GuiCreateFlatWorld.this.field_146387_g.func_82650_c().size() - p_192637_1_ - 1);
         IBlockState iblockstate = flatlayerinfo.func_175900_c();
         Block block = iblockstate.func_177230_c();
         Item item = Item.func_150898_a(block);
         if (item == Items.field_190931_a) {
            if (block != Blocks.field_150355_j && block != Blocks.field_150358_i) {
               if (block == Blocks.field_150353_l || block == Blocks.field_150356_k) {
                  item = Items.field_151129_at;
               }
            } else {
               item = Items.field_151131_as;
            }
         }

         ItemStack itemstack = new ItemStack(item, 1, item.func_77614_k() ? block.func_176201_c(iblockstate) : 0);
         String s = item.func_77653_i(itemstack);
         this.func_148225_a(p_192637_2_, p_192637_3_, itemstack);
         GuiCreateFlatWorld.this.field_146289_q.func_78276_b(s, p_192637_2_ + 18 + 5, p_192637_3_ + 3, 16777215);
         String s1;
         if (p_192637_1_ == 0) {
            s1 = I18n.func_135052_a("createWorld.customize.flat.layer.top", flatlayerinfo.func_82657_a());
         } else if (p_192637_1_ == GuiCreateFlatWorld.this.field_146387_g.func_82650_c().size() - 1) {
            s1 = I18n.func_135052_a("createWorld.customize.flat.layer.bottom", flatlayerinfo.func_82657_a());
         } else {
            s1 = I18n.func_135052_a("createWorld.customize.flat.layer", flatlayerinfo.func_82657_a());
         }

         GuiCreateFlatWorld.this.field_146289_q.func_78276_b(s1, p_192637_2_ + 2 + 213 - GuiCreateFlatWorld.this.field_146289_q.func_78256_a(s1), p_192637_3_ + 3, 16777215);
      }

      protected int func_148137_d() {
         return this.field_148155_a - 70;
      }
   }
}
