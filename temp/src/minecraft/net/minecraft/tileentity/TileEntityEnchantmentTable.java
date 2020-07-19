package net.minecraft.tileentity;

import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;

public class TileEntityEnchantmentTable extends TileEntity implements ITickable, IInteractionObject {
   public int field_145926_a;
   public float field_145933_i;
   public float field_145931_j;
   public float field_145932_k;
   public float field_145929_l;
   public float field_145930_m;
   public float field_145927_n;
   public float field_145928_o;
   public float field_145925_p;
   public float field_145924_q;
   private static final Random field_145923_r = new Random();
   private String field_145922_s;

   public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_) {
      super.func_189515_b(p_189515_1_);
      if (this.func_145818_k_()) {
         p_189515_1_.func_74778_a("CustomName", this.field_145922_s);
      }

      return p_189515_1_;
   }

   public void func_145839_a(NBTTagCompound p_145839_1_) {
      super.func_145839_a(p_145839_1_);
      if (p_145839_1_.func_150297_b("CustomName", 8)) {
         this.field_145922_s = p_145839_1_.func_74779_i("CustomName");
      }

   }

   public void func_73660_a() {
      this.field_145927_n = this.field_145930_m;
      this.field_145925_p = this.field_145928_o;
      EntityPlayer entityplayer = this.field_145850_b.func_184137_a((double)((float)this.field_174879_c.func_177958_n() + 0.5F), (double)((float)this.field_174879_c.func_177956_o() + 0.5F), (double)((float)this.field_174879_c.func_177952_p() + 0.5F), 3.0D, false);
      if (entityplayer != null) {
         double d0 = entityplayer.field_70165_t - (double)((float)this.field_174879_c.func_177958_n() + 0.5F);
         double d1 = entityplayer.field_70161_v - (double)((float)this.field_174879_c.func_177952_p() + 0.5F);
         this.field_145924_q = (float)MathHelper.func_181159_b(d1, d0);
         this.field_145930_m += 0.1F;
         if (this.field_145930_m < 0.5F || field_145923_r.nextInt(40) == 0) {
            float f1 = this.field_145932_k;

            while(true) {
               this.field_145932_k += (float)(field_145923_r.nextInt(4) - field_145923_r.nextInt(4));
               if (f1 != this.field_145932_k) {
                  break;
               }
            }
         }
      } else {
         this.field_145924_q += 0.02F;
         this.field_145930_m -= 0.1F;
      }

      while(this.field_145928_o >= 3.1415927F) {
         this.field_145928_o -= 6.2831855F;
      }

      while(this.field_145928_o < -3.1415927F) {
         this.field_145928_o += 6.2831855F;
      }

      while(this.field_145924_q >= 3.1415927F) {
         this.field_145924_q -= 6.2831855F;
      }

      while(this.field_145924_q < -3.1415927F) {
         this.field_145924_q += 6.2831855F;
      }

      float f2;
      for(f2 = this.field_145924_q - this.field_145928_o; f2 >= 3.1415927F; f2 -= 6.2831855F) {
         ;
      }

      while(f2 < -3.1415927F) {
         f2 += 6.2831855F;
      }

      this.field_145928_o += f2 * 0.4F;
      this.field_145930_m = MathHelper.func_76131_a(this.field_145930_m, 0.0F, 1.0F);
      ++this.field_145926_a;
      this.field_145931_j = this.field_145933_i;
      float f = (this.field_145932_k - this.field_145933_i) * 0.4F;
      float f3 = 0.2F;
      f = MathHelper.func_76131_a(f, -0.2F, 0.2F);
      this.field_145929_l += (f - this.field_145929_l) * 0.9F;
      this.field_145933_i += this.field_145929_l;
   }

   public String func_70005_c_() {
      return this.func_145818_k_() ? this.field_145922_s : "container.enchant";
   }

   public boolean func_145818_k_() {
      return this.field_145922_s != null && !this.field_145922_s.isEmpty();
   }

   public void func_145920_a(String p_145920_1_) {
      this.field_145922_s = p_145920_1_;
   }

   public ITextComponent func_145748_c_() {
      return (ITextComponent)(this.func_145818_k_() ? new TextComponentString(this.func_70005_c_()) : new TextComponentTranslation(this.func_70005_c_(), new Object[0]));
   }

   public Container func_174876_a(InventoryPlayer p_174876_1_, EntityPlayer p_174876_2_) {
      return new ContainerEnchantment(p_174876_1_, this.field_145850_b, this.field_174879_c);
   }

   public String func_174875_k() {
      return "minecraft:enchanting_table";
   }
}
