package net.minecraft.tileentity;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockHopper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class TileEntityHopper extends TileEntityLockableLoot implements IHopper, ITickable {
   private NonNullList<ItemStack> field_145900_a = NonNullList.<ItemStack>func_191197_a(5, ItemStack.field_190927_a);
   private int field_145901_j = -1;
   private long field_190578_g;

   public static void func_189683_a(DataFixer p_189683_0_) {
      p_189683_0_.func_188258_a(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileEntityHopper.class, new String[]{"Items"}));
   }

   public void func_145839_a(NBTTagCompound p_145839_1_) {
      super.func_145839_a(p_145839_1_);
      this.field_145900_a = NonNullList.<ItemStack>func_191197_a(this.func_70302_i_(), ItemStack.field_190927_a);
      if (!this.func_184283_b(p_145839_1_)) {
         ItemStackHelper.func_191283_b(p_145839_1_, this.field_145900_a);
      }

      if (p_145839_1_.func_150297_b("CustomName", 8)) {
         this.field_190577_o = p_145839_1_.func_74779_i("CustomName");
      }

      this.field_145901_j = p_145839_1_.func_74762_e("TransferCooldown");
   }

   public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_) {
      super.func_189515_b(p_189515_1_);
      if (!this.func_184282_c(p_189515_1_)) {
         ItemStackHelper.func_191282_a(p_189515_1_, this.field_145900_a);
      }

      p_189515_1_.func_74768_a("TransferCooldown", this.field_145901_j);
      if (this.func_145818_k_()) {
         p_189515_1_.func_74778_a("CustomName", this.field_190577_o);
      }

      return p_189515_1_;
   }

   public int func_70302_i_() {
      return this.field_145900_a.size();
   }

   public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_) {
      this.func_184281_d((EntityPlayer)null);
      ItemStack itemstack = ItemStackHelper.func_188382_a(this.func_190576_q(), p_70298_1_, p_70298_2_);
      return itemstack;
   }

   public void func_70299_a(int p_70299_1_, ItemStack p_70299_2_) {
      this.func_184281_d((EntityPlayer)null);
      this.func_190576_q().set(p_70299_1_, p_70299_2_);
      if (p_70299_2_.func_190916_E() > this.func_70297_j_()) {
         p_70299_2_.func_190920_e(this.func_70297_j_());
      }

   }

   public String func_70005_c_() {
      return this.func_145818_k_() ? this.field_190577_o : "container.hopper";
   }

   public int func_70297_j_() {
      return 64;
   }

   public void func_73660_a() {
      if (this.field_145850_b != null && !this.field_145850_b.field_72995_K) {
         --this.field_145901_j;
         this.field_190578_g = this.field_145850_b.func_82737_E();
         if (!this.func_145888_j()) {
            this.func_145896_c(0);
            this.func_145887_i();
         }

      }
   }

   private boolean func_145887_i() {
      if (this.field_145850_b != null && !this.field_145850_b.field_72995_K) {
         if (!this.func_145888_j() && BlockHopper.func_149917_c(this.func_145832_p())) {
            boolean flag = false;
            if (!this.func_152104_k()) {
               flag = this.func_145883_k();
            }

            if (!this.func_152105_l()) {
               flag = func_145891_a(this) || flag;
            }

            if (flag) {
               this.func_145896_c(8);
               this.func_70296_d();
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean func_152104_k() {
      for(ItemStack itemstack : this.field_145900_a) {
         if (!itemstack.func_190926_b()) {
            return false;
         }
      }

      return true;
   }

   public boolean func_191420_l() {
      return this.func_152104_k();
   }

   private boolean func_152105_l() {
      for(ItemStack itemstack : this.field_145900_a) {
         if (itemstack.func_190926_b() || itemstack.func_190916_E() != itemstack.func_77976_d()) {
            return false;
         }
      }

      return true;
   }

   private boolean func_145883_k() {
      IInventory iinventory = this.func_145895_l();
      if (iinventory == null) {
         return false;
      } else {
         EnumFacing enumfacing = BlockHopper.func_176428_b(this.func_145832_p()).func_176734_d();
         if (this.func_174919_a(iinventory, enumfacing)) {
            return false;
         } else {
            for(int i = 0; i < this.func_70302_i_(); ++i) {
               if (!this.func_70301_a(i).func_190926_b()) {
                  ItemStack itemstack = this.func_70301_a(i).func_77946_l();
                  ItemStack itemstack1 = func_174918_a(this, iinventory, this.func_70298_a(i, 1), enumfacing);
                  if (itemstack1.func_190926_b()) {
                     iinventory.func_70296_d();
                     return true;
                  }

                  this.func_70299_a(i, itemstack);
               }
            }

            return false;
         }
      }
   }

   private boolean func_174919_a(IInventory p_174919_1_, EnumFacing p_174919_2_) {
      if (p_174919_1_ instanceof ISidedInventory) {
         ISidedInventory isidedinventory = (ISidedInventory)p_174919_1_;
         int[] aint = isidedinventory.func_180463_a(p_174919_2_);

         for(int k : aint) {
            ItemStack itemstack1 = isidedinventory.func_70301_a(k);
            if (itemstack1.func_190926_b() || itemstack1.func_190916_E() != itemstack1.func_77976_d()) {
               return false;
            }
         }
      } else {
         int i = p_174919_1_.func_70302_i_();

         for(int j = 0; j < i; ++j) {
            ItemStack itemstack = p_174919_1_.func_70301_a(j);
            if (itemstack.func_190926_b() || itemstack.func_190916_E() != itemstack.func_77976_d()) {
               return false;
            }
         }
      }

      return true;
   }

   private static boolean func_174917_b(IInventory p_174917_0_, EnumFacing p_174917_1_) {
      if (p_174917_0_ instanceof ISidedInventory) {
         ISidedInventory isidedinventory = (ISidedInventory)p_174917_0_;
         int[] aint = isidedinventory.func_180463_a(p_174917_1_);

         for(int i : aint) {
            if (!isidedinventory.func_70301_a(i).func_190926_b()) {
               return false;
            }
         }
      } else {
         int j = p_174917_0_.func_70302_i_();

         for(int k = 0; k < j; ++k) {
            if (!p_174917_0_.func_70301_a(k).func_190926_b()) {
               return false;
            }
         }
      }

      return true;
   }

   public static boolean func_145891_a(IHopper p_145891_0_) {
      IInventory iinventory = func_145884_b(p_145891_0_);
      if (iinventory != null) {
         EnumFacing enumfacing = EnumFacing.DOWN;
         if (func_174917_b(iinventory, enumfacing)) {
            return false;
         }

         if (iinventory instanceof ISidedInventory) {
            ISidedInventory isidedinventory = (ISidedInventory)iinventory;
            int[] aint = isidedinventory.func_180463_a(enumfacing);

            for(int i : aint) {
               if (func_174915_a(p_145891_0_, iinventory, i, enumfacing)) {
                  return true;
               }
            }
         } else {
            int j = iinventory.func_70302_i_();

            for(int k = 0; k < j; ++k) {
               if (func_174915_a(p_145891_0_, iinventory, k, enumfacing)) {
                  return true;
               }
            }
         }
      } else {
         for(EntityItem entityitem : func_184292_a(p_145891_0_.func_145831_w(), p_145891_0_.func_96107_aA(), p_145891_0_.func_96109_aB(), p_145891_0_.func_96108_aC())) {
            if (func_145898_a((IInventory)null, p_145891_0_, entityitem)) {
               return true;
            }
         }
      }

      return false;
   }

   private static boolean func_174915_a(IHopper p_174915_0_, IInventory p_174915_1_, int p_174915_2_, EnumFacing p_174915_3_) {
      ItemStack itemstack = p_174915_1_.func_70301_a(p_174915_2_);
      if (!itemstack.func_190926_b() && func_174921_b(p_174915_1_, itemstack, p_174915_2_, p_174915_3_)) {
         ItemStack itemstack1 = itemstack.func_77946_l();
         ItemStack itemstack2 = func_174918_a(p_174915_1_, p_174915_0_, p_174915_1_.func_70298_a(p_174915_2_, 1), (EnumFacing)null);
         if (itemstack2.func_190926_b()) {
            p_174915_1_.func_70296_d();
            return true;
         }

         p_174915_1_.func_70299_a(p_174915_2_, itemstack1);
      }

      return false;
   }

   public static boolean func_145898_a(IInventory p_145898_0_, IInventory p_145898_1_, EntityItem p_145898_2_) {
      boolean flag = false;
      if (p_145898_2_ == null) {
         return false;
      } else {
         ItemStack itemstack = p_145898_2_.func_92059_d().func_77946_l();
         ItemStack itemstack1 = func_174918_a(p_145898_0_, p_145898_1_, itemstack, (EnumFacing)null);
         if (itemstack1.func_190926_b()) {
            flag = true;
            p_145898_2_.func_70106_y();
         } else {
            p_145898_2_.func_92058_a(itemstack1);
         }

         return flag;
      }
   }

   public static ItemStack func_174918_a(IInventory p_174918_0_, IInventory p_174918_1_, ItemStack p_174918_2_, @Nullable EnumFacing p_174918_3_) {
      if (p_174918_1_ instanceof ISidedInventory && p_174918_3_ != null) {
         ISidedInventory isidedinventory = (ISidedInventory)p_174918_1_;
         int[] aint = isidedinventory.func_180463_a(p_174918_3_);

         for(int k = 0; k < aint.length && !p_174918_2_.func_190926_b(); ++k) {
            p_174918_2_ = func_174916_c(p_174918_0_, p_174918_1_, p_174918_2_, aint[k], p_174918_3_);
         }
      } else {
         int i = p_174918_1_.func_70302_i_();

         for(int j = 0; j < i && !p_174918_2_.func_190926_b(); ++j) {
            p_174918_2_ = func_174916_c(p_174918_0_, p_174918_1_, p_174918_2_, j, p_174918_3_);
         }
      }

      return p_174918_2_;
   }

   private static boolean func_174920_a(IInventory p_174920_0_, ItemStack p_174920_1_, int p_174920_2_, EnumFacing p_174920_3_) {
      if (!p_174920_0_.func_94041_b(p_174920_2_, p_174920_1_)) {
         return false;
      } else {
         return !(p_174920_0_ instanceof ISidedInventory) || ((ISidedInventory)p_174920_0_).func_180462_a(p_174920_2_, p_174920_1_, p_174920_3_);
      }
   }

   private static boolean func_174921_b(IInventory p_174921_0_, ItemStack p_174921_1_, int p_174921_2_, EnumFacing p_174921_3_) {
      return !(p_174921_0_ instanceof ISidedInventory) || ((ISidedInventory)p_174921_0_).func_180461_b(p_174921_2_, p_174921_1_, p_174921_3_);
   }

   private static ItemStack func_174916_c(IInventory p_174916_0_, IInventory p_174916_1_, ItemStack p_174916_2_, int p_174916_3_, EnumFacing p_174916_4_) {
      ItemStack itemstack = p_174916_1_.func_70301_a(p_174916_3_);
      if (func_174920_a(p_174916_1_, p_174916_2_, p_174916_3_, p_174916_4_)) {
         boolean flag = false;
         boolean flag1 = p_174916_1_.func_191420_l();
         if (itemstack.func_190926_b()) {
            p_174916_1_.func_70299_a(p_174916_3_, p_174916_2_);
            p_174916_2_ = ItemStack.field_190927_a;
            flag = true;
         } else if (func_145894_a(itemstack, p_174916_2_)) {
            int i = p_174916_2_.func_77976_d() - itemstack.func_190916_E();
            int j = Math.min(p_174916_2_.func_190916_E(), i);
            p_174916_2_.func_190918_g(j);
            itemstack.func_190917_f(j);
            flag = j > 0;
         }

         if (flag) {
            if (flag1 && p_174916_1_ instanceof TileEntityHopper) {
               TileEntityHopper tileentityhopper1 = (TileEntityHopper)p_174916_1_;
               if (!tileentityhopper1.func_174914_o()) {
                  int k = 0;
                  if (p_174916_0_ != null && p_174916_0_ instanceof TileEntityHopper) {
                     TileEntityHopper tileentityhopper = (TileEntityHopper)p_174916_0_;
                     if (tileentityhopper1.field_190578_g >= tileentityhopper.field_190578_g) {
                        k = 1;
                     }
                  }

                  tileentityhopper1.func_145896_c(8 - k);
               }
            }

            p_174916_1_.func_70296_d();
         }
      }

      return p_174916_2_;
   }

   private IInventory func_145895_l() {
      EnumFacing enumfacing = BlockHopper.func_176428_b(this.func_145832_p());
      return func_145893_b(this.func_145831_w(), this.func_96107_aA() + (double)enumfacing.func_82601_c(), this.func_96109_aB() + (double)enumfacing.func_96559_d(), this.func_96108_aC() + (double)enumfacing.func_82599_e());
   }

   public static IInventory func_145884_b(IHopper p_145884_0_) {
      return func_145893_b(p_145884_0_.func_145831_w(), p_145884_0_.func_96107_aA(), p_145884_0_.func_96109_aB() + 1.0D, p_145884_0_.func_96108_aC());
   }

   public static List<EntityItem> func_184292_a(World p_184292_0_, double p_184292_1_, double p_184292_3_, double p_184292_5_) {
      return p_184292_0_.<EntityItem>func_175647_a(EntityItem.class, new AxisAlignedBB(p_184292_1_ - 0.5D, p_184292_3_, p_184292_5_ - 0.5D, p_184292_1_ + 0.5D, p_184292_3_ + 1.5D, p_184292_5_ + 0.5D), EntitySelectors.field_94557_a);
   }

   public static IInventory func_145893_b(World p_145893_0_, double p_145893_1_, double p_145893_3_, double p_145893_5_) {
      IInventory iinventory = null;
      int i = MathHelper.func_76128_c(p_145893_1_);
      int j = MathHelper.func_76128_c(p_145893_3_);
      int k = MathHelper.func_76128_c(p_145893_5_);
      BlockPos blockpos = new BlockPos(i, j, k);
      Block block = p_145893_0_.func_180495_p(blockpos).func_177230_c();
      if (block.func_149716_u()) {
         TileEntity tileentity = p_145893_0_.func_175625_s(blockpos);
         if (tileentity instanceof IInventory) {
            iinventory = (IInventory)tileentity;
            if (iinventory instanceof TileEntityChest && block instanceof BlockChest) {
               iinventory = ((BlockChest)block).func_189418_a(p_145893_0_, blockpos, true);
            }
         }
      }

      if (iinventory == null) {
         List<Entity> list = p_145893_0_.func_175674_a((Entity)null, new AxisAlignedBB(p_145893_1_ - 0.5D, p_145893_3_ - 0.5D, p_145893_5_ - 0.5D, p_145893_1_ + 0.5D, p_145893_3_ + 0.5D, p_145893_5_ + 0.5D), EntitySelectors.field_96566_b);
         if (!list.isEmpty()) {
            iinventory = (IInventory)list.get(p_145893_0_.field_73012_v.nextInt(list.size()));
         }
      }

      return iinventory;
   }

   private static boolean func_145894_a(ItemStack p_145894_0_, ItemStack p_145894_1_) {
      if (p_145894_0_.func_77973_b() != p_145894_1_.func_77973_b()) {
         return false;
      } else if (p_145894_0_.func_77960_j() != p_145894_1_.func_77960_j()) {
         return false;
      } else if (p_145894_0_.func_190916_E() > p_145894_0_.func_77976_d()) {
         return false;
      } else {
         return ItemStack.func_77970_a(p_145894_0_, p_145894_1_);
      }
   }

   public double func_96107_aA() {
      return (double)this.field_174879_c.func_177958_n() + 0.5D;
   }

   public double func_96109_aB() {
      return (double)this.field_174879_c.func_177956_o() + 0.5D;
   }

   public double func_96108_aC() {
      return (double)this.field_174879_c.func_177952_p() + 0.5D;
   }

   private void func_145896_c(int p_145896_1_) {
      this.field_145901_j = p_145896_1_;
   }

   private boolean func_145888_j() {
      return this.field_145901_j > 0;
   }

   private boolean func_174914_o() {
      return this.field_145901_j > 8;
   }

   public String func_174875_k() {
      return "minecraft:hopper";
   }

   public Container func_174876_a(InventoryPlayer p_174876_1_, EntityPlayer p_174876_2_) {
      this.func_184281_d(p_174876_2_);
      return new ContainerHopper(p_174876_1_, this, p_174876_2_);
   }

   protected NonNullList<ItemStack> func_190576_q() {
      return this.field_145900_a;
   }
}
