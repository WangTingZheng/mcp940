package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class GuiLockIconButton extends GuiButton {
   private boolean field_175231_o;

   public GuiLockIconButton(int p_i45538_1_, int p_i45538_2_, int p_i45538_3_) {
      super(p_i45538_1_, p_i45538_2_, p_i45538_3_, 20, 20, "");
   }

   public boolean func_175230_c() {
      return this.field_175231_o;
   }

   public void func_175229_b(boolean p_175229_1_) {
      this.field_175231_o = p_175229_1_;
   }

   public void func_191745_a(Minecraft p_191745_1_, int p_191745_2_, int p_191745_3_, float p_191745_4_) {
      if (this.field_146125_m) {
         p_191745_1_.func_110434_K().func_110577_a(GuiButton.field_146122_a);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         boolean flag = p_191745_2_ >= this.field_146128_h && p_191745_3_ >= this.field_146129_i && p_191745_2_ < this.field_146128_h + this.field_146120_f && p_191745_3_ < this.field_146129_i + this.field_146121_g;
         GuiLockIconButton.Icon guilockiconbutton$icon;
         if (this.field_175231_o) {
            if (!this.field_146124_l) {
               guilockiconbutton$icon = GuiLockIconButton.Icon.LOCKED_DISABLED;
            } else if (flag) {
               guilockiconbutton$icon = GuiLockIconButton.Icon.LOCKED_HOVER;
            } else {
               guilockiconbutton$icon = GuiLockIconButton.Icon.LOCKED;
            }
         } else if (!this.field_146124_l) {
            guilockiconbutton$icon = GuiLockIconButton.Icon.UNLOCKED_DISABLED;
         } else if (flag) {
            guilockiconbutton$icon = GuiLockIconButton.Icon.UNLOCKED_HOVER;
         } else {
            guilockiconbutton$icon = GuiLockIconButton.Icon.UNLOCKED;
         }

         this.func_73729_b(this.field_146128_h, this.field_146129_i, guilockiconbutton$icon.func_178910_a(), guilockiconbutton$icon.func_178912_b(), this.field_146120_f, this.field_146121_g);
      }
   }

   static enum Icon {
      LOCKED(0, 146),
      LOCKED_HOVER(0, 166),
      LOCKED_DISABLED(0, 186),
      UNLOCKED(20, 146),
      UNLOCKED_HOVER(20, 166),
      UNLOCKED_DISABLED(20, 186);

      private final int field_178914_g;
      private final int field_178920_h;

      private Icon(int p_i45537_3_, int p_i45537_4_) {
         this.field_178914_g = p_i45537_3_;
         this.field_178920_h = p_i45537_4_;
      }

      public int func_178910_a() {
         return this.field_178914_g;
      }

      public int func_178912_b() {
         return this.field_178920_h;
      }
   }
}
