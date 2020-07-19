package net.minecraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;

public class TileEntityEnderChest extends TileEntity implements ITickable {
   public float field_145972_a;
   public float field_145975_i;
   public int field_145973_j;
   private int field_145974_k;

   public void func_73660_a() {
      if (++this.field_145974_k % 20 * 4 == 0) {
         this.field_145850_b.func_175641_c(this.field_174879_c, Blocks.field_150477_bB, 1, this.field_145973_j);
      }

      this.field_145975_i = this.field_145972_a;
      int i = this.field_174879_c.func_177958_n();
      int j = this.field_174879_c.func_177956_o();
      int k = this.field_174879_c.func_177952_p();
      float f = 0.1F;
      if (this.field_145973_j > 0 && this.field_145972_a == 0.0F) {
         double d0 = (double)i + 0.5D;
         double d1 = (double)k + 0.5D;
         this.field_145850_b.func_184148_a((EntityPlayer)null, d0, (double)j + 0.5D, d1, SoundEvents.field_187520_aJ, SoundCategory.BLOCKS, 0.5F, this.field_145850_b.field_73012_v.nextFloat() * 0.1F + 0.9F);
      }

      if (this.field_145973_j == 0 && this.field_145972_a > 0.0F || this.field_145973_j > 0 && this.field_145972_a < 1.0F) {
         float f2 = this.field_145972_a;
         if (this.field_145973_j > 0) {
            this.field_145972_a += 0.1F;
         } else {
            this.field_145972_a -= 0.1F;
         }

         if (this.field_145972_a > 1.0F) {
            this.field_145972_a = 1.0F;
         }

         float f1 = 0.5F;
         if (this.field_145972_a < 0.5F && f2 >= 0.5F) {
            double d3 = (double)i + 0.5D;
            double d2 = (double)k + 0.5D;
            this.field_145850_b.func_184148_a((EntityPlayer)null, d3, (double)j + 0.5D, d2, SoundEvents.field_187519_aI, SoundCategory.BLOCKS, 0.5F, this.field_145850_b.field_73012_v.nextFloat() * 0.1F + 0.9F);
         }

         if (this.field_145972_a < 0.0F) {
            this.field_145972_a = 0.0F;
         }
      }

   }

   public boolean func_145842_c(int p_145842_1_, int p_145842_2_) {
      if (p_145842_1_ == 1) {
         this.field_145973_j = p_145842_2_;
         return true;
      } else {
         return super.func_145842_c(p_145842_1_, p_145842_2_);
      }
   }

   public void func_145843_s() {
      this.func_145836_u();
      super.func_145843_s();
   }

   public void func_145969_a() {
      ++this.field_145973_j;
      this.field_145850_b.func_175641_c(this.field_174879_c, Blocks.field_150477_bB, 1, this.field_145973_j);
   }

   public void func_145970_b() {
      --this.field_145973_j;
      this.field_145850_b.func_175641_c(this.field_174879_c, Blocks.field_150477_bB, 1, this.field_145973_j);
   }

   public boolean func_145971_a(EntityPlayer p_145971_1_) {
      if (this.field_145850_b.func_175625_s(this.field_174879_c) != this) {
         return false;
      } else {
         return p_145971_1_.func_70092_e((double)this.field_174879_c.func_177958_n() + 0.5D, (double)this.field_174879_c.func_177956_o() + 0.5D, (double)this.field_174879_c.func_177952_p() + 0.5D) <= 64.0D;
      }
   }
}
