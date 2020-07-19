package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButtonImage extends GuiButton {
   private final ResourceLocation field_191750_o;
   private final int field_191747_p;
   private final int field_191748_q;
   private final int field_191749_r;

   public GuiButtonImage(int p_i47392_1_, int p_i47392_2_, int p_i47392_3_, int p_i47392_4_, int p_i47392_5_, int p_i47392_6_, int p_i47392_7_, int p_i47392_8_, ResourceLocation p_i47392_9_) {
      super(p_i47392_1_, p_i47392_2_, p_i47392_3_, p_i47392_4_, p_i47392_5_, "");
      this.field_191747_p = p_i47392_6_;
      this.field_191748_q = p_i47392_7_;
      this.field_191749_r = p_i47392_8_;
      this.field_191750_o = p_i47392_9_;
   }

   public void func_191746_c(int p_191746_1_, int p_191746_2_) {
      this.field_146128_h = p_191746_1_;
      this.field_146129_i = p_191746_2_;
   }

   public void func_191745_a(Minecraft p_191745_1_, int p_191745_2_, int p_191745_3_, float p_191745_4_) {
      if (this.field_146125_m) {
         this.field_146123_n = p_191745_2_ >= this.field_146128_h && p_191745_3_ >= this.field_146129_i && p_191745_2_ < this.field_146128_h + this.field_146120_f && p_191745_3_ < this.field_146129_i + this.field_146121_g;
         p_191745_1_.func_110434_K().func_110577_a(this.field_191750_o);
         GlStateManager.func_179097_i();
         int i = this.field_191747_p;
         int j = this.field_191748_q;
         if (this.field_146123_n) {
            j += this.field_191749_r;
         }

         this.func_73729_b(this.field_146128_h, this.field_146129_i, i, j, this.field_146120_f, this.field_146121_g);
         GlStateManager.func_179126_j();
      }
   }
}
