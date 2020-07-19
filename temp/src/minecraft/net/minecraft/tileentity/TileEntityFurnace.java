package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.MathHelper;

public class TileEntityFurnace extends TileEntityLockable implements ITickable, ISidedInventory {
   private static final int[] field_145962_k = new int[]{0};
   private static final int[] field_145959_l = new int[]{2, 1};
   private static final int[] field_145960_m = new int[]{1};
   private NonNullList<ItemStack> field_145957_n = NonNullList.<ItemStack>func_191197_a(3, ItemStack.field_190927_a);
   private int field_145956_a;
   private int field_145963_i;
   private int field_174906_k;
   private int field_174905_l;
   private String field_145958_o;

   public int func_70302_i_() {
      return this.field_145957_n.size();
   }

   public boolean func_191420_l() {
      for(ItemStack itemstack : this.field_145957_n) {
         if (!itemstack.func_190926_b()) {
            return false;
         }
      }

      return true;
   }

   public ItemStack func_70301_a(int p_70301_1_) {
      return this.field_145957_n.get(p_70301_1_);
   }

   public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_) {
      return ItemStackHelper.func_188382_a(this.field_145957_n, p_70298_1_, p_70298_2_);
   }

   public ItemStack func_70304_b(int p_70304_1_) {
      return ItemStackHelper.func_188383_a(this.field_145957_n, p_70304_1_);
   }

   public void func_70299_a(int p_70299_1_, ItemStack p_70299_2_) {
      ItemStack itemstack = this.field_145957_n.get(p_70299_1_);
      boolean flag = !p_70299_2_.func_190926_b() && p_70299_2_.func_77969_a(itemstack) && ItemStack.func_77970_a(p_70299_2_, itemstack);
      this.field_145957_n.set(p_70299_1_, p_70299_2_);
      if (p_70299_2_.func_190916_E() > this.func_70297_j_()) {
         p_70299_2_.func_190920_e(this.func_70297_j_());
      }

      if (p_70299_1_ == 0 && !flag) {
         this.field_174905_l = this.func_174904_a(p_70299_2_);
         this.field_174906_k = 0;
         this.func_70296_d();
      }

   }

   public String func_70005_c_() {
      return this.func_145818_k_() ? this.field_145958_o : "container.furnace";
   }

   public boolean func_145818_k_() {
      return this.field_145958_o != null && !this.field_145958_o.isEmpty();
   }

   public void func_145951_a(String p_145951_1_) {
      this.field_145958_o = p_145951_1_;
   }

   public static void func_189676_a(DataFixer p_189676_0_) {
      p_189676_0_.func_188258_a(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileEntityFurnace.class, new String[]{"Items"}));
   }

   public void func_145839_a(NBTTagCompound p_145839_1_) {
      super.func_145839_a(p_145839_1_);
      this.field_145957_n = NonNullList.<ItemStack>func_191197_a(this.func_70302_i_(), ItemStack.field_190927_a);
      ItemStackHelper.func_191283_b(p_145839_1_, this.field_145957_n);
      this.field_145956_a = p_145839_1_.func_74765_d("BurnTime");
      this.field_174906_k = p_145839_1_.func_74765_d("CookTime");
      this.field_174905_l = p_145839_1_.func_74765_d("CookTimeTotal");
      this.field_145963_i = func_145952_a(this.field_145957_n.get(1));
      if (p_145839_1_.func_150297_b("CustomName", 8)) {
         this.field_145958_o = p_145839_1_.func_74779_i("CustomName");
      }

   }

   public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_) {
      super.func_189515_b(p_189515_1_);
      p_189515_1_.func_74777_a("BurnTime", (short)this.field_145956_a);
      p_189515_1_.func_74777_a("CookTime", (short)this.field_174906_k);
      p_189515_1_.func_74777_a("CookTimeTotal", (short)this.field_174905_l);
      ItemStackHelper.func_191282_a(p_189515_1_, this.field_145957_n);
      if (this.func_145818_k_()) {
         p_189515_1_.func_74778_a("CustomName", this.field_145958_o);
      }

      return p_189515_1_;
   }

   public int func_70297_j_() {
      return 64;
   }

   public boolean func_145950_i() {
      return this.field_145956_a > 0;
   }

   public static boolean func_174903_a(IInventory p_174903_0_) {
      return p_174903_0_.func_174887_a_(0) > 0;
   }

   public void func_73660_a() {
      boolean flag = this.func_145950_i();
      boolean flag1 = false;
      if (this.func_145950_i()) {
         --this.field_145956_a;
      }

      if (!this.field_145850_b.field_72995_K) {
         ItemStack itemstack = this.field_145957_n.get(1);
         if (this.func_145950_i() || !itemstack.func_190926_b() && !((ItemStack)this.field_145957_n.get(0)).func_190926_b()) {
            if (!this.func_145950_i() && this.func_145948_k()) {
               this.field_145956_a = func_145952_a(itemstack);
               this.field_145963_i = this.field_145956_a;
               if (this.func_145950_i()) {
                  flag1 = true;
                  if (!itemstack.func_190926_b()) {
                     Item item = itemstack.func_77973_b();
                     itemstack.func_190918_g(1);
                     if (itemstack.func_190926_b()) {
                        Item item1 = item.func_77668_q();
                        this.field_145957_n.set(1, item1 == null ? ItemStack.field_190927_a : new ItemStack(item1));
                     }
                  }
               }
            }

            if (this.func_145950_i() && this.func_145948_k()) {
               ++this.field_174906_k;
               if (this.field_174906_k == this.field_174905_l) {
                  this.field_174906_k = 0;
                  this.field_174905_l = this.func_174904_a(this.field_145957_n.get(0));
                  this.func_145949_j();
                  flag1 = true;
               }
            } else {
               this.field_174906_k = 0;
            }
         } else if (!this.func_145950_i() && this.field_174906_k > 0) {
            this.field_174906_k = MathHelper.func_76125_a(this.field_174906_k - 2, 0, this.field_174905_l);
         }

         if (flag != this.func_145950_i()) {
            flag1 = true;
            BlockFurnace.func_176446_a(this.func_145950_i(), this.field_145850_b, this.field_174879_c);
         }
      }

      if (flag1) {
         this.func_70296_d();
      }

   }

   public int func_174904_a(ItemStack p_174904_1_) {
      return 200;
   }

   private boolean func_145948_k() {
      if (((ItemStack)this.field_145957_n.get(0)).func_190926_b()) {
         return false;
      } else {
         ItemStack itemstack = FurnaceRecipes.func_77602_a().func_151395_a(this.field_145957_n.get(0));
         if (itemstack.func_190926_b()) {
            return false;
         } else {
            ItemStack itemstack1 = this.field_145957_n.get(2);
            if (itemstack1.func_190926_b()) {
               return true;
            } else if (!itemstack1.func_77969_a(itemstack)) {
               return false;
            } else if (itemstack1.func_190916_E() < this.func_70297_j_() && itemstack1.func_190916_E() < itemstack1.func_77976_d()) {
               return true;
            } else {
               return itemstack1.func_190916_E() < itemstack.func_77976_d();
            }
         }
      }
   }

   public void func_145949_j() {
      if (this.func_145948_k()) {
         ItemStack itemstack = this.field_145957_n.get(0);
         ItemStack itemstack1 = FurnaceRecipes.func_77602_a().func_151395_a(itemstack);
         ItemStack itemstack2 = this.field_145957_n.get(2);
         if (itemstack2.func_190926_b()) {
            this.field_145957_n.set(2, itemstack1.func_77946_l());
         } else if (itemstack2.func_77973_b() == itemstack1.func_77973_b()) {
            itemstack2.func_190917_f(1);
         }

         if (itemstack.func_77973_b() == Item.func_150898_a(Blocks.field_150360_v) && itemstack.func_77960_j() == 1 && !((ItemStack)this.field_145957_n.get(1)).func_190926_b() && ((ItemStack)this.field_145957_n.get(1)).func_77973_b() == Items.field_151133_ar) {
            this.field_145957_n.set(1, new ItemStack(Items.field_151131_as));
         }

         itemstack.func_190918_g(1);
      }
   }

   public static int func_145952_a(ItemStack p_145952_0_) {
      if (p_145952_0_.func_190926_b()) {
         return 0;
      } else {
         Item item = p_145952_0_.func_77973_b();
         if (item == Item.func_150898_a(Blocks.field_150376_bx)) {
            return 150;
         } else if (item == Item.func_150898_a(Blocks.field_150325_L)) {
            return 100;
         } else if (item == Item.func_150898_a(Blocks.field_150404_cg)) {
            return 67;
         } else if (item == Item.func_150898_a(Blocks.field_150468_ap)) {
            return 300;
         } else if (item == Item.func_150898_a(Blocks.field_150471_bO)) {
            return 100;
         } else if (Block.func_149634_a(item).func_176223_P().func_185904_a() == Material.field_151575_d) {
            return 300;
         } else if (item == Item.func_150898_a(Blocks.field_150402_ci)) {
            return 16000;
         } else if (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).func_77861_e())) {
            return 200;
         } else if (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).func_150932_j())) {
            return 200;
         } else if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).func_77842_f())) {
            return 200;
         } else if (item == Items.field_151055_y) {
            return 100;
         } else if (item != Items.field_151031_f && item != Items.field_151112_aM) {
            if (item == Items.field_151155_ap) {
               return 200;
            } else if (item == Items.field_151044_h) {
               return 1600;
            } else if (item == Items.field_151129_at) {
               return 20000;
            } else if (item != Item.func_150898_a(Blocks.field_150345_g) && item != Items.field_151054_z) {
               if (item == Items.field_151072_bj) {
                  return 2400;
               } else if (item instanceof ItemDoor && item != Items.field_151139_aw) {
                  return 200;
               } else {
                  return item instanceof ItemBoat ? 400 : 0;
               }
            } else {
               return 100;
            }
         } else {
            return 300;
         }
      }
   }

   public static boolean func_145954_b(ItemStack p_145954_0_) {
      return func_145952_a(p_145954_0_) > 0;
   }

   public boolean func_70300_a(EntityPlayer p_70300_1_) {
      if (this.field_145850_b.func_175625_s(this.field_174879_c) != this) {
         return false;
      } else {
         return p_70300_1_.func_70092_e((double)this.field_174879_c.func_177958_n() + 0.5D, (double)this.field_174879_c.func_177956_o() + 0.5D, (double)this.field_174879_c.func_177952_p() + 0.5D) <= 64.0D;
      }
   }

   public void func_174889_b(EntityPlayer p_174889_1_) {
   }

   public void func_174886_c(EntityPlayer p_174886_1_) {
   }

   public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_) {
      if (p_94041_1_ == 2) {
         return false;
      } else if (p_94041_1_ != 1) {
         return true;
      } else {
         ItemStack itemstack = this.field_145957_n.get(1);
         return func_145954_b(p_94041_2_) || SlotFurnaceFuel.func_178173_c_(p_94041_2_) && itemstack.func_77973_b() != Items.field_151133_ar;
      }
   }

   public int[] func_180463_a(EnumFacing p_180463_1_) {
      if (p_180463_1_ == EnumFacing.DOWN) {
         return field_145959_l;
      } else {
         return p_180463_1_ == EnumFacing.UP ? field_145962_k : field_145960_m;
      }
   }

   public boolean func_180462_a(int p_180462_1_, ItemStack p_180462_2_, EnumFacing p_180462_3_) {
      return this.func_94041_b(p_180462_1_, p_180462_2_);
   }

   public boolean func_180461_b(int p_180461_1_, ItemStack p_180461_2_, EnumFacing p_180461_3_) {
      if (p_180461_3_ == EnumFacing.DOWN && p_180461_1_ == 1) {
         Item item = p_180461_2_.func_77973_b();
         if (item != Items.field_151131_as && item != Items.field_151133_ar) {
            return false;
         }
      }

      return true;
   }

   public String func_174875_k() {
      return "minecraft:furnace";
   }

   public Container func_174876_a(InventoryPlayer p_174876_1_, EntityPlayer p_174876_2_) {
      return new ContainerFurnace(p_174876_1_, this);
   }

   public int func_174887_a_(int p_174887_1_) {
      switch(p_174887_1_) {
      case 0:
         return this.field_145956_a;
      case 1:
         return this.field_145963_i;
      case 2:
         return this.field_174906_k;
      case 3:
         return this.field_174905_l;
      default:
         return 0;
      }
   }

   public void func_174885_b(int p_174885_1_, int p_174885_2_) {
      switch(p_174885_1_) {
      case 0:
         this.field_145956_a = p_174885_2_;
         break;
      case 1:
         this.field_145963_i = p_174885_2_;
         break;
      case 2:
         this.field_174906_k = p_174885_2_;
         break;
      case 3:
         this.field_174905_l = p_174885_2_;
      }

   }

   public int func_174890_g() {
      return 4;
   }

   public void func_174888_l() {
      this.field_145957_n.clear();
   }
}
