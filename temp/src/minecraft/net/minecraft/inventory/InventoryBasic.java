package net.minecraft.inventory;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class InventoryBasic implements IInventory {
   private String field_70483_a;
   private final int field_70481_b;
   private final NonNullList<ItemStack> field_70482_c;
   private List<IInventoryChangedListener> field_70480_d;
   private boolean field_94051_e;

   public InventoryBasic(String p_i1561_1_, boolean p_i1561_2_, int p_i1561_3_) {
      this.field_70483_a = p_i1561_1_;
      this.field_94051_e = p_i1561_2_;
      this.field_70481_b = p_i1561_3_;
      this.field_70482_c = NonNullList.<ItemStack>func_191197_a(p_i1561_3_, ItemStack.field_190927_a);
   }

   public InventoryBasic(ITextComponent p_i45902_1_, int p_i45902_2_) {
      this(p_i45902_1_.func_150260_c(), true, p_i45902_2_);
   }

   public void func_110134_a(IInventoryChangedListener p_110134_1_) {
      if (this.field_70480_d == null) {
         this.field_70480_d = Lists.<IInventoryChangedListener>newArrayList();
      }

      this.field_70480_d.add(p_110134_1_);
   }

   public void func_110132_b(IInventoryChangedListener p_110132_1_) {
      this.field_70480_d.remove(p_110132_1_);
   }

   public ItemStack func_70301_a(int p_70301_1_) {
      return p_70301_1_ >= 0 && p_70301_1_ < this.field_70482_c.size() ? (ItemStack)this.field_70482_c.get(p_70301_1_) : ItemStack.field_190927_a;
   }

   public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_) {
      ItemStack itemstack = ItemStackHelper.func_188382_a(this.field_70482_c, p_70298_1_, p_70298_2_);
      if (!itemstack.func_190926_b()) {
         this.func_70296_d();
      }

      return itemstack;
   }

   public ItemStack func_174894_a(ItemStack p_174894_1_) {
      ItemStack itemstack = p_174894_1_.func_77946_l();

      for(int i = 0; i < this.field_70481_b; ++i) {
         ItemStack itemstack1 = this.func_70301_a(i);
         if (itemstack1.func_190926_b()) {
            this.func_70299_a(i, itemstack);
            this.func_70296_d();
            return ItemStack.field_190927_a;
         }

         if (ItemStack.func_179545_c(itemstack1, itemstack)) {
            int j = Math.min(this.func_70297_j_(), itemstack1.func_77976_d());
            int k = Math.min(itemstack.func_190916_E(), j - itemstack1.func_190916_E());
            if (k > 0) {
               itemstack1.func_190917_f(k);
               itemstack.func_190918_g(k);
               if (itemstack.func_190926_b()) {
                  this.func_70296_d();
                  return ItemStack.field_190927_a;
               }
            }
         }
      }

      if (itemstack.func_190916_E() != p_174894_1_.func_190916_E()) {
         this.func_70296_d();
      }

      return itemstack;
   }

   public ItemStack func_70304_b(int p_70304_1_) {
      ItemStack itemstack = this.field_70482_c.get(p_70304_1_);
      if (itemstack.func_190926_b()) {
         return ItemStack.field_190927_a;
      } else {
         this.field_70482_c.set(p_70304_1_, ItemStack.field_190927_a);
         return itemstack;
      }
   }

   public void func_70299_a(int p_70299_1_, ItemStack p_70299_2_) {
      this.field_70482_c.set(p_70299_1_, p_70299_2_);
      if (!p_70299_2_.func_190926_b() && p_70299_2_.func_190916_E() > this.func_70297_j_()) {
         p_70299_2_.func_190920_e(this.func_70297_j_());
      }

      this.func_70296_d();
   }

   public int func_70302_i_() {
      return this.field_70481_b;
   }

   public boolean func_191420_l() {
      for(ItemStack itemstack : this.field_70482_c) {
         if (!itemstack.func_190926_b()) {
            return false;
         }
      }

      return true;
   }

   public String func_70005_c_() {
      return this.field_70483_a;
   }

   public boolean func_145818_k_() {
      return this.field_94051_e;
   }

   public void func_110133_a(String p_110133_1_) {
      this.field_94051_e = true;
      this.field_70483_a = p_110133_1_;
   }

   public ITextComponent func_145748_c_() {
      return (ITextComponent)(this.func_145818_k_() ? new TextComponentString(this.func_70005_c_()) : new TextComponentTranslation(this.func_70005_c_(), new Object[0]));
   }

   public int func_70297_j_() {
      return 64;
   }

   public void func_70296_d() {
      if (this.field_70480_d != null) {
         for(int i = 0; i < this.field_70480_d.size(); ++i) {
            ((IInventoryChangedListener)this.field_70480_d.get(i)).func_76316_a(this);
         }
      }

   }

   public boolean func_70300_a(EntityPlayer p_70300_1_) {
      return true;
   }

   public void func_174889_b(EntityPlayer p_174889_1_) {
   }

   public void func_174886_c(EntityPlayer p_174886_1_) {
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

   public void func_174888_l() {
      this.field_70482_c.clear();
   }
}
