package net.minecraft.client.gui.inventory;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

public abstract class GuiContainer extends GuiScreen {
   public static final ResourceLocation field_147001_a = new ResourceLocation("textures/gui/container/inventory.png");
   protected int field_146999_f = 176;
   protected int field_147000_g = 166;
   public Container field_147002_h;
   protected int field_147003_i;
   protected int field_147009_r;
   private Slot field_147006_u;
   private Slot field_147005_v;
   private boolean field_147004_w;
   private ItemStack field_147012_x = ItemStack.field_190927_a;
   private int field_147011_y;
   private int field_147010_z;
   private Slot field_146989_A;
   private long field_146990_B;
   private ItemStack field_146991_C = ItemStack.field_190927_a;
   private Slot field_146985_D;
   private long field_146986_E;
   protected final Set<Slot> field_147008_s = Sets.<Slot>newHashSet();
   protected boolean field_147007_t;
   private int field_146987_F;
   private int field_146988_G;
   private boolean field_146995_H;
   private int field_146996_I;
   private long field_146997_J;
   private Slot field_146998_K;
   private int field_146992_L;
   private boolean field_146993_M;
   private ItemStack field_146994_N = ItemStack.field_190927_a;

   public GuiContainer(Container p_i1072_1_) {
      this.field_147002_h = p_i1072_1_;
      this.field_146995_H = true;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.field_146297_k.field_71439_g.field_71070_bA = this.field_147002_h;
      this.field_147003_i = (this.field_146294_l - this.field_146999_f) / 2;
      this.field_147009_r = (this.field_146295_m - this.field_147000_g) / 2;
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      int i = this.field_147003_i;
      int j = this.field_147009_r;
      this.func_146976_a(p_73863_3_, p_73863_1_, p_73863_2_);
      GlStateManager.func_179101_C();
      RenderHelper.func_74518_a();
      GlStateManager.func_179140_f();
      GlStateManager.func_179097_i();
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
      RenderHelper.func_74520_c();
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)i, (float)j, 0.0F);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179091_B();
      this.field_147006_u = null;
      int k = 240;
      int l = 240;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);

      for(int i1 = 0; i1 < this.field_147002_h.field_75151_b.size(); ++i1) {
         Slot slot = this.field_147002_h.field_75151_b.get(i1);
         if (slot.func_111238_b()) {
            this.func_146977_a(slot);
         }

         if (this.func_146981_a(slot, p_73863_1_, p_73863_2_) && slot.func_111238_b()) {
            this.field_147006_u = slot;
            GlStateManager.func_179140_f();
            GlStateManager.func_179097_i();
            int j1 = slot.field_75223_e;
            int k1 = slot.field_75221_f;
            GlStateManager.func_179135_a(true, true, true, false);
            this.func_73733_a(j1, k1, j1 + 16, k1 + 16, -2130706433, -2130706433);
            GlStateManager.func_179135_a(true, true, true, true);
            GlStateManager.func_179145_e();
            GlStateManager.func_179126_j();
         }
      }

      RenderHelper.func_74518_a();
      this.func_146979_b(p_73863_1_, p_73863_2_);
      RenderHelper.func_74520_c();
      InventoryPlayer inventoryplayer = this.field_146297_k.field_71439_g.field_71071_by;
      ItemStack itemstack = this.field_147012_x.func_190926_b() ? inventoryplayer.func_70445_o() : this.field_147012_x;
      if (!itemstack.func_190926_b()) {
         int j2 = 8;
         int k2 = this.field_147012_x.func_190926_b() ? 8 : 16;
         String s = null;
         if (!this.field_147012_x.func_190926_b() && this.field_147004_w) {
            itemstack = itemstack.func_77946_l();
            itemstack.func_190920_e(MathHelper.func_76123_f((float)itemstack.func_190916_E() / 2.0F));
         } else if (this.field_147007_t && this.field_147008_s.size() > 1) {
            itemstack = itemstack.func_77946_l();
            itemstack.func_190920_e(this.field_146996_I);
            if (itemstack.func_190926_b()) {
               s = "" + TextFormatting.YELLOW + "0";
            }
         }

         this.func_146982_a(itemstack, p_73863_1_ - i - 8, p_73863_2_ - j - k2, s);
      }

      if (!this.field_146991_C.func_190926_b()) {
         float f = (float)(Minecraft.func_71386_F() - this.field_146990_B) / 100.0F;
         if (f >= 1.0F) {
            f = 1.0F;
            this.field_146991_C = ItemStack.field_190927_a;
         }

         int l2 = this.field_146989_A.field_75223_e - this.field_147011_y;
         int i3 = this.field_146989_A.field_75221_f - this.field_147010_z;
         int l1 = this.field_147011_y + (int)((float)l2 * f);
         int i2 = this.field_147010_z + (int)((float)i3 * f);
         this.func_146982_a(this.field_146991_C, l1, i2, (String)null);
      }

      GlStateManager.func_179121_F();
      GlStateManager.func_179145_e();
      GlStateManager.func_179126_j();
      RenderHelper.func_74519_b();
   }

   protected void func_191948_b(int p_191948_1_, int p_191948_2_) {
      if (this.field_146297_k.field_71439_g.field_71071_by.func_70445_o().func_190926_b() && this.field_147006_u != null && this.field_147006_u.func_75216_d()) {
         this.func_146285_a(this.field_147006_u.func_75211_c(), p_191948_1_, p_191948_2_);
      }

   }

   private void func_146982_a(ItemStack p_146982_1_, int p_146982_2_, int p_146982_3_, String p_146982_4_) {
      GlStateManager.func_179109_b(0.0F, 0.0F, 32.0F);
      this.field_73735_i = 200.0F;
      this.field_146296_j.field_77023_b = 200.0F;
      this.field_146296_j.func_180450_b(p_146982_1_, p_146982_2_, p_146982_3_);
      this.field_146296_j.func_180453_a(this.field_146289_q, p_146982_1_, p_146982_2_, p_146982_3_ - (this.field_147012_x.func_190926_b() ? 0 : 8), p_146982_4_);
      this.field_73735_i = 0.0F;
      this.field_146296_j.field_77023_b = 0.0F;
   }

   protected void func_146979_b(int p_146979_1_, int p_146979_2_) {
   }

   protected abstract void func_146976_a(float var1, int var2, int var3);

   private void func_146977_a(Slot p_146977_1_) {
      int i = p_146977_1_.field_75223_e;
      int j = p_146977_1_.field_75221_f;
      ItemStack itemstack = p_146977_1_.func_75211_c();
      boolean flag = false;
      boolean flag1 = p_146977_1_ == this.field_147005_v && !this.field_147012_x.func_190926_b() && !this.field_147004_w;
      ItemStack itemstack1 = this.field_146297_k.field_71439_g.field_71071_by.func_70445_o();
      String s = null;
      if (p_146977_1_ == this.field_147005_v && !this.field_147012_x.func_190926_b() && this.field_147004_w && !itemstack.func_190926_b()) {
         itemstack = itemstack.func_77946_l();
         itemstack.func_190920_e(itemstack.func_190916_E() / 2);
      } else if (this.field_147007_t && this.field_147008_s.contains(p_146977_1_) && !itemstack1.func_190926_b()) {
         if (this.field_147008_s.size() == 1) {
            return;
         }

         if (Container.func_94527_a(p_146977_1_, itemstack1, true) && this.field_147002_h.func_94531_b(p_146977_1_)) {
            itemstack = itemstack1.func_77946_l();
            flag = true;
            Container.func_94525_a(this.field_147008_s, this.field_146987_F, itemstack, p_146977_1_.func_75211_c().func_190926_b() ? 0 : p_146977_1_.func_75211_c().func_190916_E());
            int k = Math.min(itemstack.func_77976_d(), p_146977_1_.func_178170_b(itemstack));
            if (itemstack.func_190916_E() > k) {
               s = TextFormatting.YELLOW.toString() + k;
               itemstack.func_190920_e(k);
            }
         } else {
            this.field_147008_s.remove(p_146977_1_);
            this.func_146980_g();
         }
      }

      this.field_73735_i = 100.0F;
      this.field_146296_j.field_77023_b = 100.0F;
      if (itemstack.func_190926_b() && p_146977_1_.func_111238_b()) {
         String s1 = p_146977_1_.func_178171_c();
         if (s1 != null) {
            TextureAtlasSprite textureatlassprite = this.field_146297_k.func_147117_R().func_110572_b(s1);
            GlStateManager.func_179140_f();
            this.field_146297_k.func_110434_K().func_110577_a(TextureMap.field_110575_b);
            this.func_175175_a(i, j, textureatlassprite, 16, 16);
            GlStateManager.func_179145_e();
            flag1 = true;
         }
      }

      if (!flag1) {
         if (flag) {
            func_73734_a(i, j, i + 16, j + 16, -2130706433);
         }

         GlStateManager.func_179126_j();
         this.field_146296_j.func_184391_a(this.field_146297_k.field_71439_g, itemstack, i, j);
         this.field_146296_j.func_180453_a(this.field_146289_q, itemstack, i, j, s);
      }

      this.field_146296_j.field_77023_b = 0.0F;
      this.field_73735_i = 0.0F;
   }

   private void func_146980_g() {
      ItemStack itemstack = this.field_146297_k.field_71439_g.field_71071_by.func_70445_o();
      if (!itemstack.func_190926_b() && this.field_147007_t) {
         if (this.field_146987_F == 2) {
            this.field_146996_I = itemstack.func_77976_d();
         } else {
            this.field_146996_I = itemstack.func_190916_E();

            for(Slot slot : this.field_147008_s) {
               ItemStack itemstack1 = itemstack.func_77946_l();
               ItemStack itemstack2 = slot.func_75211_c();
               int i = itemstack2.func_190926_b() ? 0 : itemstack2.func_190916_E();
               Container.func_94525_a(this.field_147008_s, this.field_146987_F, itemstack1, i);
               int j = Math.min(itemstack1.func_77976_d(), slot.func_178170_b(itemstack1));
               if (itemstack1.func_190916_E() > j) {
                  itemstack1.func_190920_e(j);
               }

               this.field_146996_I -= itemstack1.func_190916_E() - i;
            }

         }
      }
   }

   private Slot func_146975_c(int p_146975_1_, int p_146975_2_) {
      for(int i = 0; i < this.field_147002_h.field_75151_b.size(); ++i) {
         Slot slot = this.field_147002_h.field_75151_b.get(i);
         if (this.func_146981_a(slot, p_146975_1_, p_146975_2_) && slot.func_111238_b()) {
            return slot;
         }
      }

      return null;
   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) throws IOException {
      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
      boolean flag = p_73864_3_ == this.field_146297_k.field_71474_y.field_74322_I.func_151463_i() + 100;
      Slot slot = this.func_146975_c(p_73864_1_, p_73864_2_);
      long i = Minecraft.func_71386_F();
      this.field_146993_M = this.field_146998_K == slot && i - this.field_146997_J < 250L && this.field_146992_L == p_73864_3_;
      this.field_146995_H = false;
      if (p_73864_3_ == 0 || p_73864_3_ == 1 || flag) {
         int j = this.field_147003_i;
         int k = this.field_147009_r;
         boolean flag1 = this.func_193983_c(p_73864_1_, p_73864_2_, j, k);
         int l = -1;
         if (slot != null) {
            l = slot.field_75222_d;
         }

         if (flag1) {
            l = -999;
         }

         if (this.field_146297_k.field_71474_y.field_85185_A && flag1 && this.field_146297_k.field_71439_g.field_71071_by.func_70445_o().func_190926_b()) {
            this.field_146297_k.func_147108_a((GuiScreen)null);
            return;
         }

         if (l != -1) {
            if (this.field_146297_k.field_71474_y.field_85185_A) {
               if (slot != null && slot.func_75216_d()) {
                  this.field_147005_v = slot;
                  this.field_147012_x = ItemStack.field_190927_a;
                  this.field_147004_w = p_73864_3_ == 1;
               } else {
                  this.field_147005_v = null;
               }
            } else if (!this.field_147007_t) {
               if (this.field_146297_k.field_71439_g.field_71071_by.func_70445_o().func_190926_b()) {
                  if (p_73864_3_ == this.field_146297_k.field_71474_y.field_74322_I.func_151463_i() + 100) {
                     this.func_184098_a(slot, l, p_73864_3_, ClickType.CLONE);
                  } else {
                     boolean flag2 = l != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
                     ClickType clicktype = ClickType.PICKUP;
                     if (flag2) {
                        this.field_146994_N = slot != null && slot.func_75216_d() ? slot.func_75211_c().func_77946_l() : ItemStack.field_190927_a;
                        clicktype = ClickType.QUICK_MOVE;
                     } else if (l == -999) {
                        clicktype = ClickType.THROW;
                     }

                     this.func_184098_a(slot, l, p_73864_3_, clicktype);
                  }

                  this.field_146995_H = true;
               } else {
                  this.field_147007_t = true;
                  this.field_146988_G = p_73864_3_;
                  this.field_147008_s.clear();
                  if (p_73864_3_ == 0) {
                     this.field_146987_F = 0;
                  } else if (p_73864_3_ == 1) {
                     this.field_146987_F = 1;
                  } else if (p_73864_3_ == this.field_146297_k.field_71474_y.field_74322_I.func_151463_i() + 100) {
                     this.field_146987_F = 2;
                  }
               }
            }
         }
      }

      this.field_146998_K = slot;
      this.field_146997_J = i;
      this.field_146992_L = p_73864_3_;
   }

   protected boolean func_193983_c(int p_193983_1_, int p_193983_2_, int p_193983_3_, int p_193983_4_) {
      return p_193983_1_ < p_193983_3_ || p_193983_2_ < p_193983_4_ || p_193983_1_ >= p_193983_3_ + this.field_146999_f || p_193983_2_ >= p_193983_4_ + this.field_147000_g;
   }

   protected void func_146273_a(int p_146273_1_, int p_146273_2_, int p_146273_3_, long p_146273_4_) {
      Slot slot = this.func_146975_c(p_146273_1_, p_146273_2_);
      ItemStack itemstack = this.field_146297_k.field_71439_g.field_71071_by.func_70445_o();
      if (this.field_147005_v != null && this.field_146297_k.field_71474_y.field_85185_A) {
         if (p_146273_3_ == 0 || p_146273_3_ == 1) {
            if (this.field_147012_x.func_190926_b()) {
               if (slot != this.field_147005_v && !this.field_147005_v.func_75211_c().func_190926_b()) {
                  this.field_147012_x = this.field_147005_v.func_75211_c().func_77946_l();
               }
            } else if (this.field_147012_x.func_190916_E() > 1 && slot != null && Container.func_94527_a(slot, this.field_147012_x, false)) {
               long i = Minecraft.func_71386_F();
               if (this.field_146985_D == slot) {
                  if (i - this.field_146986_E > 500L) {
                     this.func_184098_a(this.field_147005_v, this.field_147005_v.field_75222_d, 0, ClickType.PICKUP);
                     this.func_184098_a(slot, slot.field_75222_d, 1, ClickType.PICKUP);
                     this.func_184098_a(this.field_147005_v, this.field_147005_v.field_75222_d, 0, ClickType.PICKUP);
                     this.field_146986_E = i + 750L;
                     this.field_147012_x.func_190918_g(1);
                  }
               } else {
                  this.field_146985_D = slot;
                  this.field_146986_E = i;
               }
            }
         }
      } else if (this.field_147007_t && slot != null && !itemstack.func_190926_b() && (itemstack.func_190916_E() > this.field_147008_s.size() || this.field_146987_F == 2) && Container.func_94527_a(slot, itemstack, true) && slot.func_75214_a(itemstack) && this.field_147002_h.func_94531_b(slot)) {
         this.field_147008_s.add(slot);
         this.func_146980_g();
      }

   }

   protected void func_146286_b(int p_146286_1_, int p_146286_2_, int p_146286_3_) {
      Slot slot = this.func_146975_c(p_146286_1_, p_146286_2_);
      int i = this.field_147003_i;
      int j = this.field_147009_r;
      boolean flag = this.func_193983_c(p_146286_1_, p_146286_2_, i, j);
      int k = -1;
      if (slot != null) {
         k = slot.field_75222_d;
      }

      if (flag) {
         k = -999;
      }

      if (this.field_146993_M && slot != null && p_146286_3_ == 0 && this.field_147002_h.func_94530_a(ItemStack.field_190927_a, slot)) {
         if (func_146272_n()) {
            if (!this.field_146994_N.func_190926_b()) {
               for(Slot slot2 : this.field_147002_h.field_75151_b) {
                  if (slot2 != null && slot2.func_82869_a(this.field_146297_k.field_71439_g) && slot2.func_75216_d() && slot2.field_75224_c == slot.field_75224_c && Container.func_94527_a(slot2, this.field_146994_N, true)) {
                     this.func_184098_a(slot2, slot2.field_75222_d, p_146286_3_, ClickType.QUICK_MOVE);
                  }
               }
            }
         } else {
            this.func_184098_a(slot, k, p_146286_3_, ClickType.PICKUP_ALL);
         }

         this.field_146993_M = false;
         this.field_146997_J = 0L;
      } else {
         if (this.field_147007_t && this.field_146988_G != p_146286_3_) {
            this.field_147007_t = false;
            this.field_147008_s.clear();
            this.field_146995_H = true;
            return;
         }

         if (this.field_146995_H) {
            this.field_146995_H = false;
            return;
         }

         if (this.field_147005_v != null && this.field_146297_k.field_71474_y.field_85185_A) {
            if (p_146286_3_ == 0 || p_146286_3_ == 1) {
               if (this.field_147012_x.func_190926_b() && slot != this.field_147005_v) {
                  this.field_147012_x = this.field_147005_v.func_75211_c();
               }

               boolean flag2 = Container.func_94527_a(slot, this.field_147012_x, false);
               if (k != -1 && !this.field_147012_x.func_190926_b() && flag2) {
                  this.func_184098_a(this.field_147005_v, this.field_147005_v.field_75222_d, p_146286_3_, ClickType.PICKUP);
                  this.func_184098_a(slot, k, 0, ClickType.PICKUP);
                  if (this.field_146297_k.field_71439_g.field_71071_by.func_70445_o().func_190926_b()) {
                     this.field_146991_C = ItemStack.field_190927_a;
                  } else {
                     this.func_184098_a(this.field_147005_v, this.field_147005_v.field_75222_d, p_146286_3_, ClickType.PICKUP);
                     this.field_147011_y = p_146286_1_ - i;
                     this.field_147010_z = p_146286_2_ - j;
                     this.field_146989_A = this.field_147005_v;
                     this.field_146991_C = this.field_147012_x;
                     this.field_146990_B = Minecraft.func_71386_F();
                  }
               } else if (!this.field_147012_x.func_190926_b()) {
                  this.field_147011_y = p_146286_1_ - i;
                  this.field_147010_z = p_146286_2_ - j;
                  this.field_146989_A = this.field_147005_v;
                  this.field_146991_C = this.field_147012_x;
                  this.field_146990_B = Minecraft.func_71386_F();
               }

               this.field_147012_x = ItemStack.field_190927_a;
               this.field_147005_v = null;
            }
         } else if (this.field_147007_t && !this.field_147008_s.isEmpty()) {
            this.func_184098_a((Slot)null, -999, Container.func_94534_d(0, this.field_146987_F), ClickType.QUICK_CRAFT);

            for(Slot slot1 : this.field_147008_s) {
               this.func_184098_a(slot1, slot1.field_75222_d, Container.func_94534_d(1, this.field_146987_F), ClickType.QUICK_CRAFT);
            }

            this.func_184098_a((Slot)null, -999, Container.func_94534_d(2, this.field_146987_F), ClickType.QUICK_CRAFT);
         } else if (!this.field_146297_k.field_71439_g.field_71071_by.func_70445_o().func_190926_b()) {
            if (p_146286_3_ == this.field_146297_k.field_71474_y.field_74322_I.func_151463_i() + 100) {
               this.func_184098_a(slot, k, p_146286_3_, ClickType.CLONE);
            } else {
               boolean flag1 = k != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
               if (flag1) {
                  this.field_146994_N = slot != null && slot.func_75216_d() ? slot.func_75211_c().func_77946_l() : ItemStack.field_190927_a;
               }

               this.func_184098_a(slot, k, p_146286_3_, flag1 ? ClickType.QUICK_MOVE : ClickType.PICKUP);
            }
         }
      }

      if (this.field_146297_k.field_71439_g.field_71071_by.func_70445_o().func_190926_b()) {
         this.field_146997_J = 0L;
      }

      this.field_147007_t = false;
   }

   private boolean func_146981_a(Slot p_146981_1_, int p_146981_2_, int p_146981_3_) {
      return this.func_146978_c(p_146981_1_.field_75223_e, p_146981_1_.field_75221_f, 16, 16, p_146981_2_, p_146981_3_);
   }

   protected boolean func_146978_c(int p_146978_1_, int p_146978_2_, int p_146978_3_, int p_146978_4_, int p_146978_5_, int p_146978_6_) {
      int i = this.field_147003_i;
      int j = this.field_147009_r;
      p_146978_5_ = p_146978_5_ - i;
      p_146978_6_ = p_146978_6_ - j;
      return p_146978_5_ >= p_146978_1_ - 1 && p_146978_5_ < p_146978_1_ + p_146978_3_ + 1 && p_146978_6_ >= p_146978_2_ - 1 && p_146978_6_ < p_146978_2_ + p_146978_4_ + 1;
   }

   protected void func_184098_a(Slot p_184098_1_, int p_184098_2_, int p_184098_3_, ClickType p_184098_4_) {
      if (p_184098_1_ != null) {
         p_184098_2_ = p_184098_1_.field_75222_d;
      }

      this.field_146297_k.field_71442_b.func_187098_a(this.field_147002_h.field_75152_c, p_184098_2_, p_184098_3_, p_184098_4_, this.field_146297_k.field_71439_g);
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) throws IOException {
      if (p_73869_2_ == 1 || p_73869_2_ == this.field_146297_k.field_71474_y.field_151445_Q.func_151463_i()) {
         this.field_146297_k.field_71439_g.func_71053_j();
      }

      this.func_146983_a(p_73869_2_);
      if (this.field_147006_u != null && this.field_147006_u.func_75216_d()) {
         if (p_73869_2_ == this.field_146297_k.field_71474_y.field_74322_I.func_151463_i()) {
            this.func_184098_a(this.field_147006_u, this.field_147006_u.field_75222_d, 0, ClickType.CLONE);
         } else if (p_73869_2_ == this.field_146297_k.field_71474_y.field_74316_C.func_151463_i()) {
            this.func_184098_a(this.field_147006_u, this.field_147006_u.field_75222_d, func_146271_m() ? 1 : 0, ClickType.THROW);
         }
      }

   }

   protected boolean func_146983_a(int p_146983_1_) {
      if (this.field_146297_k.field_71439_g.field_71071_by.func_70445_o().func_190926_b() && this.field_147006_u != null) {
         for(int i = 0; i < 9; ++i) {
            if (p_146983_1_ == this.field_146297_k.field_71474_y.field_151456_ac[i].func_151463_i()) {
               this.func_184098_a(this.field_147006_u, this.field_147006_u.field_75222_d, i, ClickType.SWAP);
               return true;
            }
         }
      }

      return false;
   }

   public void func_146281_b() {
      if (this.field_146297_k.field_71439_g != null) {
         this.field_147002_h.func_75134_a(this.field_146297_k.field_71439_g);
      }
   }

   public boolean func_73868_f() {
      return false;
   }

   public void func_73876_c() {
      super.func_73876_c();
      if (!this.field_146297_k.field_71439_g.func_70089_S() || this.field_146297_k.field_71439_g.field_70128_L) {
         this.field_146297_k.field_71439_g.func_71053_j();
      }

   }
}
