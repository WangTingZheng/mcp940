package net.minecraft.village;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MerchantRecipe {
   private ItemStack field_77403_a;
   private ItemStack field_77401_b;
   private ItemStack field_77402_c;
   private int field_77400_d;
   private int field_82786_e;
   private boolean field_180323_f;

   public MerchantRecipe(NBTTagCompound p_i1940_1_) {
      this.field_77403_a = ItemStack.field_190927_a;
      this.field_77401_b = ItemStack.field_190927_a;
      this.field_77402_c = ItemStack.field_190927_a;
      this.func_77390_a(p_i1940_1_);
   }

   public MerchantRecipe(ItemStack p_i1941_1_, ItemStack p_i1941_2_, ItemStack p_i1941_3_) {
      this(p_i1941_1_, p_i1941_2_, p_i1941_3_, 0, 7);
   }

   public MerchantRecipe(ItemStack p_i45760_1_, ItemStack p_i45760_2_, ItemStack p_i45760_3_, int p_i45760_4_, int p_i45760_5_) {
      this.field_77403_a = ItemStack.field_190927_a;
      this.field_77401_b = ItemStack.field_190927_a;
      this.field_77402_c = ItemStack.field_190927_a;
      this.field_77403_a = p_i45760_1_;
      this.field_77401_b = p_i45760_2_;
      this.field_77402_c = p_i45760_3_;
      this.field_77400_d = p_i45760_4_;
      this.field_82786_e = p_i45760_5_;
      this.field_180323_f = true;
   }

   public MerchantRecipe(ItemStack p_i1942_1_, ItemStack p_i1942_2_) {
      this(p_i1942_1_, ItemStack.field_190927_a, p_i1942_2_);
   }

   public MerchantRecipe(ItemStack p_i1943_1_, Item p_i1943_2_) {
      this(p_i1943_1_, new ItemStack(p_i1943_2_));
   }

   public ItemStack func_77394_a() {
      return this.field_77403_a;
   }

   public ItemStack func_77396_b() {
      return this.field_77401_b;
   }

   public boolean func_77398_c() {
      return !this.field_77401_b.func_190926_b();
   }

   public ItemStack func_77397_d() {
      return this.field_77402_c;
   }

   public int func_180321_e() {
      return this.field_77400_d;
   }

   public int func_180320_f() {
      return this.field_82786_e;
   }

   public void func_77399_f() {
      ++this.field_77400_d;
   }

   public void func_82783_a(int p_82783_1_) {
      this.field_82786_e += p_82783_1_;
   }

   public boolean func_82784_g() {
      return this.field_77400_d >= this.field_82786_e;
   }

   public void func_82785_h() {
      this.field_77400_d = this.field_82786_e;
   }

   public boolean func_180322_j() {
      return this.field_180323_f;
   }

   public void func_77390_a(NBTTagCompound p_77390_1_) {
      NBTTagCompound nbttagcompound = p_77390_1_.func_74775_l("buy");
      this.field_77403_a = new ItemStack(nbttagcompound);
      NBTTagCompound nbttagcompound1 = p_77390_1_.func_74775_l("sell");
      this.field_77402_c = new ItemStack(nbttagcompound1);
      if (p_77390_1_.func_150297_b("buyB", 10)) {
         this.field_77401_b = new ItemStack(p_77390_1_.func_74775_l("buyB"));
      }

      if (p_77390_1_.func_150297_b("uses", 99)) {
         this.field_77400_d = p_77390_1_.func_74762_e("uses");
      }

      if (p_77390_1_.func_150297_b("maxUses", 99)) {
         this.field_82786_e = p_77390_1_.func_74762_e("maxUses");
      } else {
         this.field_82786_e = 7;
      }

      if (p_77390_1_.func_150297_b("rewardExp", 1)) {
         this.field_180323_f = p_77390_1_.func_74767_n("rewardExp");
      } else {
         this.field_180323_f = true;
      }

   }

   public NBTTagCompound func_77395_g() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.func_74782_a("buy", this.field_77403_a.func_77955_b(new NBTTagCompound()));
      nbttagcompound.func_74782_a("sell", this.field_77402_c.func_77955_b(new NBTTagCompound()));
      if (!this.field_77401_b.func_190926_b()) {
         nbttagcompound.func_74782_a("buyB", this.field_77401_b.func_77955_b(new NBTTagCompound()));
      }

      nbttagcompound.func_74768_a("uses", this.field_77400_d);
      nbttagcompound.func_74768_a("maxUses", this.field_82786_e);
      nbttagcompound.func_74757_a("rewardExp", this.field_180323_f);
      return nbttagcompound;
   }
}
