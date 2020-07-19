package net.minecraft.tileentity;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class TileEntityChest extends TileEntityLockableLoot implements ITickable {
   private NonNullList<ItemStack> field_145985_p = NonNullList.<ItemStack>func_191197_a(27, ItemStack.field_190927_a);
   public boolean field_145984_a;
   public TileEntityChest field_145992_i;
   public TileEntityChest field_145990_j;
   public TileEntityChest field_145991_k;
   public TileEntityChest field_145988_l;
   public float field_145989_m;
   public float field_145986_n;
   public int field_145987_o;
   private int field_145983_q;
   private BlockChest.Type field_145982_r;

   public TileEntityChest() {
   }

   public TileEntityChest(BlockChest.Type p_i46677_1_) {
      this.field_145982_r = p_i46677_1_;
   }

   public int func_70302_i_() {
      return 27;
   }

   public boolean func_191420_l() {
      for(ItemStack itemstack : this.field_145985_p) {
         if (!itemstack.func_190926_b()) {
            return false;
         }
      }

      return true;
   }

   public String func_70005_c_() {
      return this.func_145818_k_() ? this.field_190577_o : "container.chest";
   }

   public static void func_189677_a(DataFixer p_189677_0_) {
      p_189677_0_.func_188258_a(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileEntityChest.class, new String[]{"Items"}));
   }

   public void func_145839_a(NBTTagCompound p_145839_1_) {
      super.func_145839_a(p_145839_1_);
      this.field_145985_p = NonNullList.<ItemStack>func_191197_a(this.func_70302_i_(), ItemStack.field_190927_a);
      if (!this.func_184283_b(p_145839_1_)) {
         ItemStackHelper.func_191283_b(p_145839_1_, this.field_145985_p);
      }

      if (p_145839_1_.func_150297_b("CustomName", 8)) {
         this.field_190577_o = p_145839_1_.func_74779_i("CustomName");
      }

   }

   public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_) {
      super.func_189515_b(p_189515_1_);
      if (!this.func_184282_c(p_189515_1_)) {
         ItemStackHelper.func_191282_a(p_189515_1_, this.field_145985_p);
      }

      if (this.func_145818_k_()) {
         p_189515_1_.func_74778_a("CustomName", this.field_190577_o);
      }

      return p_189515_1_;
   }

   public int func_70297_j_() {
      return 64;
   }

   public void func_145836_u() {
      super.func_145836_u();
      this.field_145984_a = false;
   }

   private void func_174910_a(TileEntityChest p_174910_1_, EnumFacing p_174910_2_) {
      if (p_174910_1_.func_145837_r()) {
         this.field_145984_a = false;
      } else if (this.field_145984_a) {
         switch(p_174910_2_) {
         case NORTH:
            if (this.field_145992_i != p_174910_1_) {
               this.field_145984_a = false;
            }
            break;
         case SOUTH:
            if (this.field_145988_l != p_174910_1_) {
               this.field_145984_a = false;
            }
            break;
         case EAST:
            if (this.field_145990_j != p_174910_1_) {
               this.field_145984_a = false;
            }
            break;
         case WEST:
            if (this.field_145991_k != p_174910_1_) {
               this.field_145984_a = false;
            }
         }
      }

   }

   public void func_145979_i() {
      if (!this.field_145984_a) {
         this.field_145984_a = true;
         this.field_145991_k = this.func_174911_a(EnumFacing.WEST);
         this.field_145990_j = this.func_174911_a(EnumFacing.EAST);
         this.field_145992_i = this.func_174911_a(EnumFacing.NORTH);
         this.field_145988_l = this.func_174911_a(EnumFacing.SOUTH);
      }
   }

   @Nullable
   protected TileEntityChest func_174911_a(EnumFacing p_174911_1_) {
      BlockPos blockpos = this.field_174879_c.func_177972_a(p_174911_1_);
      if (this.func_174912_b(blockpos)) {
         TileEntity tileentity = this.field_145850_b.func_175625_s(blockpos);
         if (tileentity instanceof TileEntityChest) {
            TileEntityChest tileentitychest = (TileEntityChest)tileentity;
            tileentitychest.func_174910_a(this, p_174911_1_.func_176734_d());
            return tileentitychest;
         }
      }

      return null;
   }

   private boolean func_174912_b(BlockPos p_174912_1_) {
      if (this.field_145850_b == null) {
         return false;
      } else {
         Block block = this.field_145850_b.func_180495_p(p_174912_1_).func_177230_c();
         return block instanceof BlockChest && ((BlockChest)block).field_149956_a == this.func_145980_j();
      }
   }

   public void func_73660_a() {
      this.func_145979_i();
      int i = this.field_174879_c.func_177958_n();
      int j = this.field_174879_c.func_177956_o();
      int k = this.field_174879_c.func_177952_p();
      ++this.field_145983_q;
      if (!this.field_145850_b.field_72995_K && this.field_145987_o != 0 && (this.field_145983_q + i + j + k) % 200 == 0) {
         this.field_145987_o = 0;
         float f = 5.0F;

         for(EntityPlayer entityplayer : this.field_145850_b.func_72872_a(EntityPlayer.class, new AxisAlignedBB((double)((float)i - 5.0F), (double)((float)j - 5.0F), (double)((float)k - 5.0F), (double)((float)(i + 1) + 5.0F), (double)((float)(j + 1) + 5.0F), (double)((float)(k + 1) + 5.0F)))) {
            if (entityplayer.field_71070_bA instanceof ContainerChest) {
               IInventory iinventory = ((ContainerChest)entityplayer.field_71070_bA).func_85151_d();
               if (iinventory == this || iinventory instanceof InventoryLargeChest && ((InventoryLargeChest)iinventory).func_90010_a(this)) {
                  ++this.field_145987_o;
               }
            }
         }
      }

      this.field_145986_n = this.field_145989_m;
      float f1 = 0.1F;
      if (this.field_145987_o > 0 && this.field_145989_m == 0.0F && this.field_145992_i == null && this.field_145991_k == null) {
         double d1 = (double)i + 0.5D;
         double d2 = (double)k + 0.5D;
         if (this.field_145988_l != null) {
            d2 += 0.5D;
         }

         if (this.field_145990_j != null) {
            d1 += 0.5D;
         }

         this.field_145850_b.func_184148_a((EntityPlayer)null, d1, (double)j + 0.5D, d2, SoundEvents.field_187657_V, SoundCategory.BLOCKS, 0.5F, this.field_145850_b.field_73012_v.nextFloat() * 0.1F + 0.9F);
      }

      if (this.field_145987_o == 0 && this.field_145989_m > 0.0F || this.field_145987_o > 0 && this.field_145989_m < 1.0F) {
         float f2 = this.field_145989_m;
         if (this.field_145987_o > 0) {
            this.field_145989_m += 0.1F;
         } else {
            this.field_145989_m -= 0.1F;
         }

         if (this.field_145989_m > 1.0F) {
            this.field_145989_m = 1.0F;
         }

         float f3 = 0.5F;
         if (this.field_145989_m < 0.5F && f2 >= 0.5F && this.field_145992_i == null && this.field_145991_k == null) {
            double d3 = (double)i + 0.5D;
            double d0 = (double)k + 0.5D;
            if (this.field_145988_l != null) {
               d0 += 0.5D;
            }

            if (this.field_145990_j != null) {
               d3 += 0.5D;
            }

            this.field_145850_b.func_184148_a((EntityPlayer)null, d3, (double)j + 0.5D, d0, SoundEvents.field_187651_T, SoundCategory.BLOCKS, 0.5F, this.field_145850_b.field_73012_v.nextFloat() * 0.1F + 0.9F);
         }

         if (this.field_145989_m < 0.0F) {
            this.field_145989_m = 0.0F;
         }
      }

   }

   public boolean func_145842_c(int p_145842_1_, int p_145842_2_) {
      if (p_145842_1_ == 1) {
         this.field_145987_o = p_145842_2_;
         return true;
      } else {
         return super.func_145842_c(p_145842_1_, p_145842_2_);
      }
   }

   public void func_174889_b(EntityPlayer p_174889_1_) {
      if (!p_174889_1_.func_175149_v()) {
         if (this.field_145987_o < 0) {
            this.field_145987_o = 0;
         }

         ++this.field_145987_o;
         this.field_145850_b.func_175641_c(this.field_174879_c, this.func_145838_q(), 1, this.field_145987_o);
         this.field_145850_b.func_175685_c(this.field_174879_c, this.func_145838_q(), false);
         if (this.func_145980_j() == BlockChest.Type.TRAP) {
            this.field_145850_b.func_175685_c(this.field_174879_c.func_177977_b(), this.func_145838_q(), false);
         }
      }

   }

   public void func_174886_c(EntityPlayer p_174886_1_) {
      if (!p_174886_1_.func_175149_v() && this.func_145838_q() instanceof BlockChest) {
         --this.field_145987_o;
         this.field_145850_b.func_175641_c(this.field_174879_c, this.func_145838_q(), 1, this.field_145987_o);
         this.field_145850_b.func_175685_c(this.field_174879_c, this.func_145838_q(), false);
         if (this.func_145980_j() == BlockChest.Type.TRAP) {
            this.field_145850_b.func_175685_c(this.field_174879_c.func_177977_b(), this.func_145838_q(), false);
         }
      }

   }

   public void func_145843_s() {
      super.func_145843_s();
      this.func_145836_u();
      this.func_145979_i();
   }

   public BlockChest.Type func_145980_j() {
      if (this.field_145982_r == null) {
         if (this.field_145850_b == null || !(this.func_145838_q() instanceof BlockChest)) {
            return BlockChest.Type.BASIC;
         }

         this.field_145982_r = ((BlockChest)this.func_145838_q()).field_149956_a;
      }

      return this.field_145982_r;
   }

   public String func_174875_k() {
      return "minecraft:chest";
   }

   public Container func_174876_a(InventoryPlayer p_174876_1_, EntityPlayer p_174876_2_) {
      this.func_184281_d(p_174876_2_);
      return new ContainerChest(p_174876_1_, this, p_174876_2_);
   }

   protected NonNullList<ItemStack> func_190576_q() {
      return this.field_145985_p;
   }
}
