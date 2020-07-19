package net.minecraft.client.gui.advancements;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

enum AdvancementTabType {
   ABOVE(0, 0, 28, 32, 8),
   BELOW(84, 0, 28, 32, 8),
   LEFT(0, 64, 32, 28, 5),
   RIGHT(96, 64, 32, 28, 5);

   public static final int field_192659_e;
   private final int field_192660_f;
   private final int field_192661_g;
   private final int field_192662_h;
   private final int field_192663_i;
   private final int field_192664_j;

   private AdvancementTabType(int p_i47386_3_, int p_i47386_4_, int p_i47386_5_, int p_i47386_6_, int p_i47386_7_) {
      this.field_192660_f = p_i47386_3_;
      this.field_192661_g = p_i47386_4_;
      this.field_192662_h = p_i47386_5_;
      this.field_192663_i = p_i47386_6_;
      this.field_192664_j = p_i47386_7_;
   }

   public int func_192650_a() {
      return this.field_192664_j;
   }

   public void func_192651_a(Gui p_192651_1_, int p_192651_2_, int p_192651_3_, boolean p_192651_4_, int p_192651_5_) {
      int i = this.field_192660_f;
      if (p_192651_5_ > 0) {
         i += this.field_192662_h;
      }

      if (p_192651_5_ == this.field_192664_j - 1) {
         i += this.field_192662_h;
      }

      int j = p_192651_4_ ? this.field_192661_g + this.field_192663_i : this.field_192661_g;
      p_192651_1_.func_73729_b(p_192651_2_ + this.func_192648_a(p_192651_5_), p_192651_3_ + this.func_192653_b(p_192651_5_), i, j, this.field_192662_h, this.field_192663_i);
   }

   public void func_192652_a(int p_192652_1_, int p_192652_2_, int p_192652_3_, RenderItem p_192652_4_, ItemStack p_192652_5_) {
      int i = p_192652_1_ + this.func_192648_a(p_192652_3_);
      int j = p_192652_2_ + this.func_192653_b(p_192652_3_);
      switch(this) {
      case ABOVE:
         i += 6;
         j += 9;
         break;
      case BELOW:
         i += 6;
         j += 6;
         break;
      case LEFT:
         i += 10;
         j += 5;
         break;
      case RIGHT:
         i += 6;
         j += 5;
      }

      p_192652_4_.func_184391_a((EntityLivingBase)null, p_192652_5_, i, j);
   }

   public int func_192648_a(int p_192648_1_) {
      switch(this) {
      case ABOVE:
         return (this.field_192662_h + 4) * p_192648_1_;
      case BELOW:
         return (this.field_192662_h + 4) * p_192648_1_;
      case LEFT:
         return -this.field_192662_h + 4;
      case RIGHT:
         return 248;
      default:
         throw new UnsupportedOperationException("Don't know what this tab type is!" + this);
      }
   }

   public int func_192653_b(int p_192653_1_) {
      switch(this) {
      case ABOVE:
         return -this.field_192663_i + 4;
      case BELOW:
         return 136;
      case LEFT:
         return this.field_192663_i * p_192653_1_;
      case RIGHT:
         return this.field_192663_i * p_192653_1_;
      default:
         throw new UnsupportedOperationException("Don't know what this tab type is!" + this);
      }
   }

   public boolean func_192654_a(int p_192654_1_, int p_192654_2_, int p_192654_3_, int p_192654_4_, int p_192654_5_) {
      int i = p_192654_1_ + this.func_192648_a(p_192654_3_);
      int j = p_192654_2_ + this.func_192653_b(p_192654_3_);
      return p_192654_4_ > i && p_192654_4_ < i + this.field_192662_h && p_192654_5_ > j && p_192654_5_ < j + this.field_192663_i;
   }

   static {
      int i = 0;

      for(AdvancementTabType advancementtabtype : values()) {
         i += advancementtabtype.field_192664_j;
      }

      field_192659_e = i;
   }
}
