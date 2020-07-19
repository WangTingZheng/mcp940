package net.minecraft.client.gui.inventory;

import io.netty.buffer.Unpooled;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiBeacon extends GuiContainer {
   private static final Logger field_147026_u = LogManager.getLogger();
   private static final ResourceLocation field_147025_v = new ResourceLocation("textures/gui/container/beacon.png");
   private final IInventory field_147024_w;
   private GuiBeacon.ConfirmButton field_147028_x;
   private boolean field_147027_y;

   public GuiBeacon(InventoryPlayer p_i45507_1_, IInventory p_i45507_2_) {
      super(new ContainerBeacon(p_i45507_1_, p_i45507_2_));
      this.field_147024_w = p_i45507_2_;
      this.field_146999_f = 230;
      this.field_147000_g = 219;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.field_147028_x = new GuiBeacon.ConfirmButton(-1, this.field_147003_i + 164, this.field_147009_r + 107);
      this.field_146292_n.add(this.field_147028_x);
      this.field_146292_n.add(new GuiBeacon.CancelButton(-2, this.field_147003_i + 190, this.field_147009_r + 107));
      this.field_147027_y = true;
      this.field_147028_x.field_146124_l = false;
   }

   public void func_73876_c() {
      super.func_73876_c();
      int i = this.field_147024_w.func_174887_a_(0);
      Potion potion = Potion.func_188412_a(this.field_147024_w.func_174887_a_(1));
      Potion potion1 = Potion.func_188412_a(this.field_147024_w.func_174887_a_(2));
      if (this.field_147027_y && i >= 0) {
         this.field_147027_y = false;
         int j = 100;

         for(int k = 0; k <= 2; ++k) {
            int l = TileEntityBeacon.field_146009_a[k].length;
            int i1 = l * 22 + (l - 1) * 2;

            for(int j1 = 0; j1 < l; ++j1) {
               Potion potion2 = TileEntityBeacon.field_146009_a[k][j1];
               GuiBeacon.PowerButton guibeacon$powerbutton = new GuiBeacon.PowerButton(j++, this.field_147003_i + 76 + j1 * 24 - i1 / 2, this.field_147009_r + 22 + k * 25, potion2, k);
               this.field_146292_n.add(guibeacon$powerbutton);
               if (k >= i) {
                  guibeacon$powerbutton.field_146124_l = false;
               } else if (potion2 == potion) {
                  guibeacon$powerbutton.func_146140_b(true);
               }
            }
         }

         int k1 = 3;
         int l1 = TileEntityBeacon.field_146009_a[3].length + 1;
         int i2 = l1 * 22 + (l1 - 1) * 2;

         for(int j2 = 0; j2 < l1 - 1; ++j2) {
            Potion potion3 = TileEntityBeacon.field_146009_a[3][j2];
            GuiBeacon.PowerButton guibeacon$powerbutton2 = new GuiBeacon.PowerButton(j++, this.field_147003_i + 167 + j2 * 24 - i2 / 2, this.field_147009_r + 47, potion3, 3);
            this.field_146292_n.add(guibeacon$powerbutton2);
            if (3 >= i) {
               guibeacon$powerbutton2.field_146124_l = false;
            } else if (potion3 == potion1) {
               guibeacon$powerbutton2.func_146140_b(true);
            }
         }

         if (potion != null) {
            GuiBeacon.PowerButton guibeacon$powerbutton1 = new GuiBeacon.PowerButton(j++, this.field_147003_i + 167 + (l1 - 1) * 24 - i2 / 2, this.field_147009_r + 47, potion, 3);
            this.field_146292_n.add(guibeacon$powerbutton1);
            if (3 >= i) {
               guibeacon$powerbutton1.field_146124_l = false;
            } else if (potion == potion1) {
               guibeacon$powerbutton1.func_146140_b(true);
            }
         }
      }

      this.field_147028_x.field_146124_l = !this.field_147024_w.func_70301_a(0).func_190926_b() && potion != null;
   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
      if (p_146284_1_.field_146127_k == -2) {
         this.field_146297_k.field_71439_g.field_71174_a.func_147297_a(new CPacketCloseWindow(this.field_146297_k.field_71439_g.field_71070_bA.field_75152_c));
         this.field_146297_k.func_147108_a((GuiScreen)null);
      } else if (p_146284_1_.field_146127_k == -1) {
         String s = "MC|Beacon";
         PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
         packetbuffer.writeInt(this.field_147024_w.func_174887_a_(1));
         packetbuffer.writeInt(this.field_147024_w.func_174887_a_(2));
         this.field_146297_k.func_147114_u().func_147297_a(new CPacketCustomPayload("MC|Beacon", packetbuffer));
         this.field_146297_k.field_71439_g.field_71174_a.func_147297_a(new CPacketCloseWindow(this.field_146297_k.field_71439_g.field_71070_bA.field_75152_c));
         this.field_146297_k.func_147108_a((GuiScreen)null);
      } else if (p_146284_1_ instanceof GuiBeacon.PowerButton) {
         GuiBeacon.PowerButton guibeacon$powerbutton = (GuiBeacon.PowerButton)p_146284_1_;
         if (guibeacon$powerbutton.func_146141_c()) {
            return;
         }

         int i = Potion.func_188409_a(guibeacon$powerbutton.field_184066_p);
         if (guibeacon$powerbutton.field_146148_q < 3) {
            this.field_147024_w.func_174885_b(1, i);
         } else {
            this.field_147024_w.func_174885_b(2, i);
         }

         this.field_146292_n.clear();
         this.func_73866_w_();
         this.func_73876_c();
      }

   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
      this.func_191948_b(p_73863_1_, p_73863_2_);
   }

   protected void func_146979_b(int p_146979_1_, int p_146979_2_) {
      RenderHelper.func_74518_a();
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("tile.beacon.primary"), 62, 10, 14737632);
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("tile.beacon.secondary"), 169, 10, 14737632);

      for(GuiButton guibutton : this.field_146292_n) {
         if (guibutton.func_146115_a()) {
            guibutton.func_146111_b(p_146979_1_ - this.field_147003_i, p_146979_2_ - this.field_147009_r);
            break;
         }
      }

      RenderHelper.func_74520_c();
   }

   protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(field_147025_v);
      int i = (this.field_146294_l - this.field_146999_f) / 2;
      int j = (this.field_146295_m - this.field_147000_g) / 2;
      this.func_73729_b(i, j, 0, 0, this.field_146999_f, this.field_147000_g);
      this.field_146296_j.field_77023_b = 100.0F;
      this.field_146296_j.func_180450_b(new ItemStack(Items.field_151166_bC), i + 42, j + 109);
      this.field_146296_j.func_180450_b(new ItemStack(Items.field_151045_i), i + 42 + 22, j + 109);
      this.field_146296_j.func_180450_b(new ItemStack(Items.field_151043_k), i + 42 + 44, j + 109);
      this.field_146296_j.func_180450_b(new ItemStack(Items.field_151042_j), i + 42 + 66, j + 109);
      this.field_146296_j.field_77023_b = 0.0F;
   }

   static class Button extends GuiButton {
      private final ResourceLocation field_146145_o;
      private final int field_146144_p;
      private final int field_146143_q;
      private boolean field_146142_r;

      protected Button(int p_i1077_1_, int p_i1077_2_, int p_i1077_3_, ResourceLocation p_i1077_4_, int p_i1077_5_, int p_i1077_6_) {
         super(p_i1077_1_, p_i1077_2_, p_i1077_3_, 22, 22, "");
         this.field_146145_o = p_i1077_4_;
         this.field_146144_p = p_i1077_5_;
         this.field_146143_q = p_i1077_6_;
      }

      public void func_191745_a(Minecraft p_191745_1_, int p_191745_2_, int p_191745_3_, float p_191745_4_) {
         if (this.field_146125_m) {
            p_191745_1_.func_110434_K().func_110577_a(GuiBeacon.field_147025_v);
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = p_191745_2_ >= this.field_146128_h && p_191745_3_ >= this.field_146129_i && p_191745_2_ < this.field_146128_h + this.field_146120_f && p_191745_3_ < this.field_146129_i + this.field_146121_g;
            int i = 219;
            int j = 0;
            if (!this.field_146124_l) {
               j += this.field_146120_f * 2;
            } else if (this.field_146142_r) {
               j += this.field_146120_f * 1;
            } else if (this.field_146123_n) {
               j += this.field_146120_f * 3;
            }

            this.func_73729_b(this.field_146128_h, this.field_146129_i, j, 219, this.field_146120_f, this.field_146121_g);
            if (!GuiBeacon.field_147025_v.equals(this.field_146145_o)) {
               p_191745_1_.func_110434_K().func_110577_a(this.field_146145_o);
            }

            this.func_73729_b(this.field_146128_h + 2, this.field_146129_i + 2, this.field_146144_p, this.field_146143_q, 18, 18);
         }
      }

      public boolean func_146141_c() {
         return this.field_146142_r;
      }

      public void func_146140_b(boolean p_146140_1_) {
         this.field_146142_r = p_146140_1_;
      }
   }

   class CancelButton extends GuiBeacon.Button {
      public CancelButton(int p_i1074_2_, int p_i1074_3_, int p_i1074_4_) {
         super(p_i1074_2_, p_i1074_3_, p_i1074_4_, GuiBeacon.field_147025_v, 112, 220);
      }

      public void func_146111_b(int p_146111_1_, int p_146111_2_) {
         GuiBeacon.this.func_146279_a(I18n.func_135052_a("gui.cancel"), p_146111_1_, p_146111_2_);
      }
   }

   class ConfirmButton extends GuiBeacon.Button {
      public ConfirmButton(int p_i1075_2_, int p_i1075_3_, int p_i1075_4_) {
         super(p_i1075_2_, p_i1075_3_, p_i1075_4_, GuiBeacon.field_147025_v, 90, 220);
      }

      public void func_146111_b(int p_146111_1_, int p_146111_2_) {
         GuiBeacon.this.func_146279_a(I18n.func_135052_a("gui.done"), p_146111_1_, p_146111_2_);
      }
   }

   class PowerButton extends GuiBeacon.Button {
      private final Potion field_184066_p;
      private final int field_146148_q;

      public PowerButton(int p_i47045_2_, int p_i47045_3_, int p_i47045_4_, Potion p_i47045_5_, int p_i47045_6_) {
         super(p_i47045_2_, p_i47045_3_, p_i47045_4_, GuiContainer.field_147001_a, p_i47045_5_.func_76392_e() % 8 * 18, 198 + p_i47045_5_.func_76392_e() / 8 * 18);
         this.field_184066_p = p_i47045_5_;
         this.field_146148_q = p_i47045_6_;
      }

      public void func_146111_b(int p_146111_1_, int p_146111_2_) {
         String s = I18n.func_135052_a(this.field_184066_p.func_76393_a());
         if (this.field_146148_q >= 3 && this.field_184066_p != MobEffects.field_76428_l) {
            s = s + " II";
         }

         GuiBeacon.this.func_146279_a(s, p_146111_1_, p_146111_2_);
      }
   }
}
