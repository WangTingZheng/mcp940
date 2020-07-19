package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

public class InventoryLargeChest implements ILockableContainer {
   private final String field_70479_a;
   private final ILockableContainer field_70477_b;
   private final ILockableContainer field_70478_c;

   public InventoryLargeChest(String p_i45905_1_, ILockableContainer p_i45905_2_, ILockableContainer p_i45905_3_) {
      this.field_70479_a = p_i45905_1_;
      if (p_i45905_2_ == null) {
         p_i45905_2_ = p_i45905_3_;
      }

      if (p_i45905_3_ == null) {
         p_i45905_3_ = p_i45905_2_;
      }

      this.field_70477_b = p_i45905_2_;
      this.field_70478_c = p_i45905_3_;
      if (p_i45905_2_.func_174893_q_()) {
         p_i45905_3_.func_174892_a(p_i45905_2_.func_174891_i());
      } else if (p_i45905_3_.func_174893_q_()) {
         p_i45905_2_.func_174892_a(p_i45905_3_.func_174891_i());
      }

   }

   public int func_70302_i_() {
      return this.field_70477_b.func_70302_i_() + this.field_70478_c.func_70302_i_();
   }

   public boolean func_191420_l() {
      return this.field_70477_b.func_191420_l() && this.field_70478_c.func_191420_l();
   }

   public boolean func_90010_a(IInventory p_90010_1_) {
      return this.field_70477_b == p_90010_1_ || this.field_70478_c == p_90010_1_;
   }

   public String func_70005_c_() {
      if (this.field_70477_b.func_145818_k_()) {
         return this.field_70477_b.func_70005_c_();
      } else {
         return this.field_70478_c.func_145818_k_() ? this.field_70478_c.func_70005_c_() : this.field_70479_a;
      }
   }

   public boolean func_145818_k_() {
      return this.field_70477_b.func_145818_k_() || this.field_70478_c.func_145818_k_();
   }

   public ITextComponent func_145748_c_() {
      return (ITextComponent)(this.func_145818_k_() ? new TextComponentString(this.func_70005_c_()) : new TextComponentTranslation(this.func_70005_c_(), new Object[0]));
   }

   public ItemStack func_70301_a(int p_70301_1_) {
      return p_70301_1_ >= this.field_70477_b.func_70302_i_() ? this.field_70478_c.func_70301_a(p_70301_1_ - this.field_70477_b.func_70302_i_()) : this.field_70477_b.func_70301_a(p_70301_1_);
   }

   public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_) {
      return p_70298_1_ >= this.field_70477_b.func_70302_i_() ? this.field_70478_c.func_70298_a(p_70298_1_ - this.field_70477_b.func_70302_i_(), p_70298_2_) : this.field_70477_b.func_70298_a(p_70298_1_, p_70298_2_);
   }

   public ItemStack func_70304_b(int p_70304_1_) {
      return p_70304_1_ >= this.field_70477_b.func_70302_i_() ? this.field_70478_c.func_70304_b(p_70304_1_ - this.field_70477_b.func_70302_i_()) : this.field_70477_b.func_70304_b(p_70304_1_);
   }

   public void func_70299_a(int p_70299_1_, ItemStack p_70299_2_) {
      if (p_70299_1_ >= this.field_70477_b.func_70302_i_()) {
         this.field_70478_c.func_70299_a(p_70299_1_ - this.field_70477_b.func_70302_i_(), p_70299_2_);
      } else {
         this.field_70477_b.func_70299_a(p_70299_1_, p_70299_2_);
      }

   }

   public int func_70297_j_() {
      return this.field_70477_b.func_70297_j_();
   }

   public void func_70296_d() {
      this.field_70477_b.func_70296_d();
      this.field_70478_c.func_70296_d();
   }

   public boolean func_70300_a(EntityPlayer p_70300_1_) {
      return this.field_70477_b.func_70300_a(p_70300_1_) && this.field_70478_c.func_70300_a(p_70300_1_);
   }

   public void func_174889_b(EntityPlayer p_174889_1_) {
      this.field_70477_b.func_174889_b(p_174889_1_);
      this.field_70478_c.func_174889_b(p_174889_1_);
   }

   public void func_174886_c(EntityPlayer p_174886_1_) {
      this.field_70477_b.func_174886_c(p_174886_1_);
      this.field_70478_c.func_174886_c(p_174886_1_);
   }

   public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_) {
      return true;
   }

   public int func_174887_a_(int p_174887_1_) {
      return 0;
   }

   public void func_174885_b(int p_174885_1_, int p_174885_2_) {
   }

   public int func_174890_g() {
      return 0;
   }

   public boolean func_174893_q_() {
      return this.field_70477_b.func_174893_q_() || this.field_70478_c.func_174893_q_();
   }

   public void func_174892_a(LockCode p_174892_1_) {
      this.field_70477_b.func_174892_a(p_174892_1_);
      this.field_70478_c.func_174892_a(p_174892_1_);
   }

   public LockCode func_174891_i() {
      return this.field_70477_b.func_174891_i();
   }

   public String func_174875_k() {
      return this.field_70477_b.func_174875_k();
   }

   public Container func_174876_a(InventoryPlayer p_174876_1_, EntityPlayer p_174876_2_) {
      return new ContainerChest(p_174876_1_, this, p_174876_2_);
   }

   public void func_174888_l() {
      this.field_70477_b.func_174888_l();
      this.field_70478_c.func_174888_l();
   }
}
