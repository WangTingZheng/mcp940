package net.minecraft.entity.player;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ReportedException;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class InventoryPlayer implements IInventory {
   public final NonNullList<ItemStack> field_70462_a = NonNullList.<ItemStack>func_191197_a(36, ItemStack.field_190927_a);
   public final NonNullList<ItemStack> field_70460_b = NonNullList.<ItemStack>func_191197_a(4, ItemStack.field_190927_a);
   public final NonNullList<ItemStack> field_184439_c = NonNullList.<ItemStack>func_191197_a(1, ItemStack.field_190927_a);
   private final List<NonNullList<ItemStack>> field_184440_g;
   public int field_70461_c;
   public EntityPlayer field_70458_d;
   private ItemStack field_70457_g;
   private int field_194017_h;

   public InventoryPlayer(EntityPlayer p_i1750_1_) {
      this.field_184440_g = Arrays.<NonNullList<ItemStack>>asList(this.field_70462_a, this.field_70460_b, this.field_184439_c);
      this.field_70457_g = ItemStack.field_190927_a;
      this.field_70458_d = p_i1750_1_;
   }

   public ItemStack func_70448_g() {
      return func_184435_e(this.field_70461_c) ? (ItemStack)this.field_70462_a.get(this.field_70461_c) : ItemStack.field_190927_a;
   }

   public static int func_70451_h() {
      return 9;
   }

   private boolean func_184436_a(ItemStack p_184436_1_, ItemStack p_184436_2_) {
      return !p_184436_1_.func_190926_b() && this.func_184431_b(p_184436_1_, p_184436_2_) && p_184436_1_.func_77985_e() && p_184436_1_.func_190916_E() < p_184436_1_.func_77976_d() && p_184436_1_.func_190916_E() < this.func_70297_j_();
   }

   private boolean func_184431_b(ItemStack p_184431_1_, ItemStack p_184431_2_) {
      return p_184431_1_.func_77973_b() == p_184431_2_.func_77973_b() && (!p_184431_1_.func_77981_g() || p_184431_1_.func_77960_j() == p_184431_2_.func_77960_j()) && ItemStack.func_77970_a(p_184431_1_, p_184431_2_);
   }

   public int func_70447_i() {
      for(int i = 0; i < this.field_70462_a.size(); ++i) {
         if (((ItemStack)this.field_70462_a.get(i)).func_190926_b()) {
            return i;
         }
      }

      return -1;
   }

   public void func_184434_a(ItemStack p_184434_1_) {
      int i = this.func_184429_b(p_184434_1_);
      if (func_184435_e(i)) {
         this.field_70461_c = i;
      } else {
         if (i == -1) {
            this.field_70461_c = this.func_184433_k();
            if (!((ItemStack)this.field_70462_a.get(this.field_70461_c)).func_190926_b()) {
               int j = this.func_70447_i();
               if (j != -1) {
                  this.field_70462_a.set(j, this.field_70462_a.get(this.field_70461_c));
               }
            }

            this.field_70462_a.set(this.field_70461_c, p_184434_1_);
         } else {
            this.func_184430_d(i);
         }

      }
   }

   public void func_184430_d(int p_184430_1_) {
      this.field_70461_c = this.func_184433_k();
      ItemStack itemstack = this.field_70462_a.get(this.field_70461_c);
      this.field_70462_a.set(this.field_70461_c, this.field_70462_a.get(p_184430_1_));
      this.field_70462_a.set(p_184430_1_, itemstack);
   }

   public static boolean func_184435_e(int p_184435_0_) {
      return p_184435_0_ >= 0 && p_184435_0_ < 9;
   }

   public int func_184429_b(ItemStack p_184429_1_) {
      for(int i = 0; i < this.field_70462_a.size(); ++i) {
         if (!((ItemStack)this.field_70462_a.get(i)).func_190926_b() && this.func_184431_b(p_184429_1_, this.field_70462_a.get(i))) {
            return i;
         }
      }

      return -1;
   }

   public int func_194014_c(ItemStack p_194014_1_) {
      for(int i = 0; i < this.field_70462_a.size(); ++i) {
         ItemStack itemstack = this.field_70462_a.get(i);
         if (!((ItemStack)this.field_70462_a.get(i)).func_190926_b() && this.func_184431_b(p_194014_1_, this.field_70462_a.get(i)) && !((ItemStack)this.field_70462_a.get(i)).func_77951_h() && !itemstack.func_77948_v() && !itemstack.func_82837_s()) {
            return i;
         }
      }

      return -1;
   }

   public int func_184433_k() {
      for(int i = 0; i < 9; ++i) {
         int j = (this.field_70461_c + i) % 9;
         if (((ItemStack)this.field_70462_a.get(j)).func_190926_b()) {
            return j;
         }
      }

      for(int k = 0; k < 9; ++k) {
         int l = (this.field_70461_c + k) % 9;
         if (!((ItemStack)this.field_70462_a.get(l)).func_77948_v()) {
            return l;
         }
      }

      return this.field_70461_c;
   }

   public void func_70453_c(int p_70453_1_) {
      if (p_70453_1_ > 0) {
         p_70453_1_ = 1;
      }

      if (p_70453_1_ < 0) {
         p_70453_1_ = -1;
      }

      for(this.field_70461_c -= p_70453_1_; this.field_70461_c < 0; this.field_70461_c += 9) {
         ;
      }

      while(this.field_70461_c >= 9) {
         this.field_70461_c -= 9;
      }

   }

   public int func_174925_a(@Nullable Item p_174925_1_, int p_174925_2_, int p_174925_3_, @Nullable NBTTagCompound p_174925_4_) {
      int i = 0;

      for(int j = 0; j < this.func_70302_i_(); ++j) {
         ItemStack itemstack = this.func_70301_a(j);
         if (!itemstack.func_190926_b() && (p_174925_1_ == null || itemstack.func_77973_b() == p_174925_1_) && (p_174925_2_ <= -1 || itemstack.func_77960_j() == p_174925_2_) && (p_174925_4_ == null || NBTUtil.func_181123_a(p_174925_4_, itemstack.func_77978_p(), true))) {
            int k = p_174925_3_ <= 0 ? itemstack.func_190916_E() : Math.min(p_174925_3_ - i, itemstack.func_190916_E());
            i += k;
            if (p_174925_3_ != 0) {
               itemstack.func_190918_g(k);
               if (itemstack.func_190926_b()) {
                  this.func_70299_a(j, ItemStack.field_190927_a);
               }

               if (p_174925_3_ > 0 && i >= p_174925_3_) {
                  return i;
               }
            }
         }
      }

      if (!this.field_70457_g.func_190926_b()) {
         if (p_174925_1_ != null && this.field_70457_g.func_77973_b() != p_174925_1_) {
            return i;
         }

         if (p_174925_2_ > -1 && this.field_70457_g.func_77960_j() != p_174925_2_) {
            return i;
         }

         if (p_174925_4_ != null && !NBTUtil.func_181123_a(p_174925_4_, this.field_70457_g.func_77978_p(), true)) {
            return i;
         }

         int l = p_174925_3_ <= 0 ? this.field_70457_g.func_190916_E() : Math.min(p_174925_3_ - i, this.field_70457_g.func_190916_E());
         i += l;
         if (p_174925_3_ != 0) {
            this.field_70457_g.func_190918_g(l);
            if (this.field_70457_g.func_190926_b()) {
               this.field_70457_g = ItemStack.field_190927_a;
            }

            if (p_174925_3_ > 0 && i >= p_174925_3_) {
               return i;
            }
         }
      }

      return i;
   }

   private int func_70452_e(ItemStack p_70452_1_) {
      int i = this.func_70432_d(p_70452_1_);
      if (i == -1) {
         i = this.func_70447_i();
      }

      return i == -1 ? p_70452_1_.func_190916_E() : this.func_191973_d(i, p_70452_1_);
   }

   private int func_191973_d(int p_191973_1_, ItemStack p_191973_2_) {
      Item item = p_191973_2_.func_77973_b();
      int i = p_191973_2_.func_190916_E();
      ItemStack itemstack = this.func_70301_a(p_191973_1_);
      if (itemstack.func_190926_b()) {
         itemstack = new ItemStack(item, 0, p_191973_2_.func_77960_j());
         if (p_191973_2_.func_77942_o()) {
            itemstack.func_77982_d(p_191973_2_.func_77978_p().func_74737_b());
         }

         this.func_70299_a(p_191973_1_, itemstack);
      }

      int j = i;
      if (i > itemstack.func_77976_d() - itemstack.func_190916_E()) {
         j = itemstack.func_77976_d() - itemstack.func_190916_E();
      }

      if (j > this.func_70297_j_() - itemstack.func_190916_E()) {
         j = this.func_70297_j_() - itemstack.func_190916_E();
      }

      if (j == 0) {
         return i;
      } else {
         i = i - j;
         itemstack.func_190917_f(j);
         itemstack.func_190915_d(5);
         return i;
      }
   }

   public int func_70432_d(ItemStack p_70432_1_) {
      if (this.func_184436_a(this.func_70301_a(this.field_70461_c), p_70432_1_)) {
         return this.field_70461_c;
      } else if (this.func_184436_a(this.func_70301_a(40), p_70432_1_)) {
         return 40;
      } else {
         for(int i = 0; i < this.field_70462_a.size(); ++i) {
            if (this.func_184436_a(this.field_70462_a.get(i), p_70432_1_)) {
               return i;
            }
         }

         return -1;
      }
   }

   public void func_70429_k() {
      for(NonNullList<ItemStack> nonnulllist : this.field_184440_g) {
         for(int i = 0; i < nonnulllist.size(); ++i) {
            if (!((ItemStack)nonnulllist.get(i)).func_190926_b()) {
               ((ItemStack)nonnulllist.get(i)).func_77945_a(this.field_70458_d.field_70170_p, this.field_70458_d, i, this.field_70461_c == i);
            }
         }
      }

   }

   public boolean func_70441_a(ItemStack p_70441_1_) {
      return this.func_191971_c(-1, p_70441_1_);
   }

   public boolean func_191971_c(int p_191971_1_, final ItemStack p_191971_2_) {
      if (p_191971_2_.func_190926_b()) {
         return false;
      } else {
         try {
            if (p_191971_2_.func_77951_h()) {
               if (p_191971_1_ == -1) {
                  p_191971_1_ = this.func_70447_i();
               }

               if (p_191971_1_ >= 0) {
                  this.field_70462_a.set(p_191971_1_, p_191971_2_.func_77946_l());
                  ((ItemStack)this.field_70462_a.get(p_191971_1_)).func_190915_d(5);
                  p_191971_2_.func_190920_e(0);
                  return true;
               } else if (this.field_70458_d.field_71075_bZ.field_75098_d) {
                  p_191971_2_.func_190920_e(0);
                  return true;
               } else {
                  return false;
               }
            } else {
               int i;
               while(true) {
                  i = p_191971_2_.func_190916_E();
                  if (p_191971_1_ == -1) {
                     p_191971_2_.func_190920_e(this.func_70452_e(p_191971_2_));
                  } else {
                     p_191971_2_.func_190920_e(this.func_191973_d(p_191971_1_, p_191971_2_));
                  }

                  if (p_191971_2_.func_190926_b() || p_191971_2_.func_190916_E() >= i) {
                     break;
                  }
               }

               if (p_191971_2_.func_190916_E() == i && this.field_70458_d.field_71075_bZ.field_75098_d) {
                  p_191971_2_.func_190920_e(0);
                  return true;
               } else {
                  return p_191971_2_.func_190916_E() < i;
               }
            }
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.func_85055_a(throwable, "Adding item to inventory");
            CrashReportCategory crashreportcategory = crashreport.func_85058_a("Item being added");
            crashreportcategory.func_71507_a("Item ID", Integer.valueOf(Item.func_150891_b(p_191971_2_.func_77973_b())));
            crashreportcategory.func_71507_a("Item data", Integer.valueOf(p_191971_2_.func_77960_j()));
            crashreportcategory.func_189529_a("Item name", new ICrashReportDetail<String>() {
               public String call() throws Exception {
                  return p_191971_2_.func_82833_r();
               }
            });
            throw new ReportedException(crashreport);
         }
      }
   }

   public void func_191975_a(World p_191975_1_, ItemStack p_191975_2_) {
      if (!p_191975_1_.field_72995_K) {
         while(!p_191975_2_.func_190926_b()) {
            int i = this.func_70432_d(p_191975_2_);
            if (i == -1) {
               i = this.func_70447_i();
            }

            if (i == -1) {
               this.field_70458_d.func_71019_a(p_191975_2_, false);
               break;
            }

            int j = p_191975_2_.func_77976_d() - this.func_70301_a(i).func_190916_E();
            if (this.func_191971_c(i, p_191975_2_.func_77979_a(j))) {
               ((EntityPlayerMP)this.field_70458_d).field_71135_a.func_147359_a(new SPacketSetSlot(-2, i, this.func_70301_a(i)));
            }
         }

      }
   }

   public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_) {
      List<ItemStack> list = null;

      for(NonNullList<ItemStack> nonnulllist : this.field_184440_g) {
         if (p_70298_1_ < nonnulllist.size()) {
            list = nonnulllist;
            break;
         }

         p_70298_1_ -= nonnulllist.size();
      }

      return list != null && !((ItemStack)list.get(p_70298_1_)).func_190926_b() ? ItemStackHelper.func_188382_a(list, p_70298_1_, p_70298_2_) : ItemStack.field_190927_a;
   }

   public void func_184437_d(ItemStack p_184437_1_) {
      for(NonNullList<ItemStack> nonnulllist : this.field_184440_g) {
         for(int i = 0; i < nonnulllist.size(); ++i) {
            if (nonnulllist.get(i) == p_184437_1_) {
               nonnulllist.set(i, ItemStack.field_190927_a);
               break;
            }
         }
      }

   }

   public ItemStack func_70304_b(int p_70304_1_) {
      NonNullList<ItemStack> nonnulllist = null;

      for(NonNullList<ItemStack> nonnulllist1 : this.field_184440_g) {
         if (p_70304_1_ < nonnulllist1.size()) {
            nonnulllist = nonnulllist1;
            break;
         }

         p_70304_1_ -= nonnulllist1.size();
      }

      if (nonnulllist != null && !((ItemStack)nonnulllist.get(p_70304_1_)).func_190926_b()) {
         ItemStack itemstack = nonnulllist.get(p_70304_1_);
         nonnulllist.set(p_70304_1_, ItemStack.field_190927_a);
         return itemstack;
      } else {
         return ItemStack.field_190927_a;
      }
   }

   public void func_70299_a(int p_70299_1_, ItemStack p_70299_2_) {
      NonNullList<ItemStack> nonnulllist = null;

      for(NonNullList<ItemStack> nonnulllist1 : this.field_184440_g) {
         if (p_70299_1_ < nonnulllist1.size()) {
            nonnulllist = nonnulllist1;
            break;
         }

         p_70299_1_ -= nonnulllist1.size();
      }

      if (nonnulllist != null) {
         nonnulllist.set(p_70299_1_, p_70299_2_);
      }

   }

   public float func_184438_a(IBlockState p_184438_1_) {
      float f = 1.0F;
      if (!((ItemStack)this.field_70462_a.get(this.field_70461_c)).func_190926_b()) {
         f *= ((ItemStack)this.field_70462_a.get(this.field_70461_c)).func_150997_a(p_184438_1_);
      }

      return f;
   }

   public NBTTagList func_70442_a(NBTTagList p_70442_1_) {
      for(int i = 0; i < this.field_70462_a.size(); ++i) {
         if (!((ItemStack)this.field_70462_a.get(i)).func_190926_b()) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.func_74774_a("Slot", (byte)i);
            ((ItemStack)this.field_70462_a.get(i)).func_77955_b(nbttagcompound);
            p_70442_1_.func_74742_a(nbttagcompound);
         }
      }

      for(int j = 0; j < this.field_70460_b.size(); ++j) {
         if (!((ItemStack)this.field_70460_b.get(j)).func_190926_b()) {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound1.func_74774_a("Slot", (byte)(j + 100));
            ((ItemStack)this.field_70460_b.get(j)).func_77955_b(nbttagcompound1);
            p_70442_1_.func_74742_a(nbttagcompound1);
         }
      }

      for(int k = 0; k < this.field_184439_c.size(); ++k) {
         if (!((ItemStack)this.field_184439_c.get(k)).func_190926_b()) {
            NBTTagCompound nbttagcompound2 = new NBTTagCompound();
            nbttagcompound2.func_74774_a("Slot", (byte)(k + 150));
            ((ItemStack)this.field_184439_c.get(k)).func_77955_b(nbttagcompound2);
            p_70442_1_.func_74742_a(nbttagcompound2);
         }
      }

      return p_70442_1_;
   }

   public void func_70443_b(NBTTagList p_70443_1_) {
      this.field_70462_a.clear();
      this.field_70460_b.clear();
      this.field_184439_c.clear();

      for(int i = 0; i < p_70443_1_.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = p_70443_1_.func_150305_b(i);
         int j = nbttagcompound.func_74771_c("Slot") & 255;
         ItemStack itemstack = new ItemStack(nbttagcompound);
         if (!itemstack.func_190926_b()) {
            if (j >= 0 && j < this.field_70462_a.size()) {
               this.field_70462_a.set(j, itemstack);
            } else if (j >= 100 && j < this.field_70460_b.size() + 100) {
               this.field_70460_b.set(j - 100, itemstack);
            } else if (j >= 150 && j < this.field_184439_c.size() + 150) {
               this.field_184439_c.set(j - 150, itemstack);
            }
         }
      }

   }

   public int func_70302_i_() {
      return this.field_70462_a.size() + this.field_70460_b.size() + this.field_184439_c.size();
   }

   public boolean func_191420_l() {
      for(ItemStack itemstack : this.field_70462_a) {
         if (!itemstack.func_190926_b()) {
            return false;
         }
      }

      for(ItemStack itemstack1 : this.field_70460_b) {
         if (!itemstack1.func_190926_b()) {
            return false;
         }
      }

      for(ItemStack itemstack2 : this.field_184439_c) {
         if (!itemstack2.func_190926_b()) {
            return false;
         }
      }

      return true;
   }

   public ItemStack func_70301_a(int p_70301_1_) {
      List<ItemStack> list = null;

      for(NonNullList<ItemStack> nonnulllist : this.field_184440_g) {
         if (p_70301_1_ < nonnulllist.size()) {
            list = nonnulllist;
            break;
         }

         p_70301_1_ -= nonnulllist.size();
      }

      return list == null ? ItemStack.field_190927_a : (ItemStack)list.get(p_70301_1_);
   }

   public String func_70005_c_() {
      return "container.inventory";
   }

   public boolean func_145818_k_() {
      return false;
   }

   public ITextComponent func_145748_c_() {
      return (ITextComponent)(this.func_145818_k_() ? new TextComponentString(this.func_70005_c_()) : new TextComponentTranslation(this.func_70005_c_(), new Object[0]));
   }

   public int func_70297_j_() {
      return 64;
   }

   public boolean func_184432_b(IBlockState p_184432_1_) {
      if (p_184432_1_.func_185904_a().func_76229_l()) {
         return true;
      } else {
         ItemStack itemstack = this.func_70301_a(this.field_70461_c);
         return !itemstack.func_190926_b() ? itemstack.func_150998_b(p_184432_1_) : false;
      }
   }

   public ItemStack func_70440_f(int p_70440_1_) {
      return this.field_70460_b.get(p_70440_1_);
   }

   public void func_70449_g(float p_70449_1_) {
      p_70449_1_ = p_70449_1_ / 4.0F;
      if (p_70449_1_ < 1.0F) {
         p_70449_1_ = 1.0F;
      }

      for(int i = 0; i < this.field_70460_b.size(); ++i) {
         ItemStack itemstack = this.field_70460_b.get(i);
         if (itemstack.func_77973_b() instanceof ItemArmor) {
            itemstack.func_77972_a((int)p_70449_1_, this.field_70458_d);
         }
      }

   }

   public void func_70436_m() {
      for(List<ItemStack> list : this.field_184440_g) {
         for(int i = 0; i < list.size(); ++i) {
            ItemStack itemstack = list.get(i);
            if (!itemstack.func_190926_b()) {
               this.field_70458_d.func_146097_a(itemstack, true, false);
               list.set(i, ItemStack.field_190927_a);
            }
         }
      }

   }

   public void func_70296_d() {
      ++this.field_194017_h;
   }

   public int func_194015_p() {
      return this.field_194017_h;
   }

   public void func_70437_b(ItemStack p_70437_1_) {
      this.field_70457_g = p_70437_1_;
   }

   public ItemStack func_70445_o() {
      return this.field_70457_g;
   }

   public boolean func_70300_a(EntityPlayer p_70300_1_) {
      if (this.field_70458_d.field_70128_L) {
         return false;
      } else {
         return p_70300_1_.func_70068_e(this.field_70458_d) <= 64.0D;
      }
   }

   public boolean func_70431_c(ItemStack p_70431_1_) {
      label23:
      for(List<ItemStack> list : this.field_184440_g) {
         Iterator iterator = list.iterator();

         while(true) {
            if (!iterator.hasNext()) {
               continue label23;
            }

            ItemStack itemstack = (ItemStack)iterator.next();
            if (!itemstack.func_190926_b() && itemstack.func_77969_a(p_70431_1_)) {
               break;
            }
         }

         return true;
      }

      return false;
   }

   public void func_174889_b(EntityPlayer p_174889_1_) {
   }

   public void func_174886_c(EntityPlayer p_174886_1_) {
   }

   public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_) {
      return true;
   }

   public void func_70455_b(InventoryPlayer p_70455_1_) {
      for(int i = 0; i < this.func_70302_i_(); ++i) {
         this.func_70299_a(i, p_70455_1_.func_70301_a(i));
      }

      this.field_70461_c = p_70455_1_.field_70461_c;
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
      for(List<ItemStack> list : this.field_184440_g) {
         list.clear();
      }

   }

   public void func_194016_a(RecipeItemHelper p_194016_1_, boolean p_194016_2_) {
      for(ItemStack itemstack : this.field_70462_a) {
         p_194016_1_.func_194112_a(itemstack);
      }

      if (p_194016_2_) {
         p_194016_1_.func_194112_a(this.field_184439_c.get(0));
      }

   }
}
