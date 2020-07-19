package net.minecraft.entity.item;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

public class EntityItemFrame extends EntityHanging {
   private static final DataParameter<ItemStack> field_184525_c = EntityDataManager.<ItemStack>func_187226_a(EntityItemFrame.class, DataSerializers.field_187196_f);
   private static final DataParameter<Integer> field_184526_d = EntityDataManager.<Integer>func_187226_a(EntityItemFrame.class, DataSerializers.field_187192_b);
   private float field_82337_e = 1.0F;

   public EntityItemFrame(World p_i1590_1_) {
      super(p_i1590_1_);
   }

   public EntityItemFrame(World p_i45852_1_, BlockPos p_i45852_2_, EnumFacing p_i45852_3_) {
      super(p_i45852_1_, p_i45852_2_);
      this.func_174859_a(p_i45852_3_);
   }

   protected void func_70088_a() {
      this.func_184212_Q().func_187214_a(field_184525_c, ItemStack.field_190927_a);
      this.func_184212_Q().func_187214_a(field_184526_d, Integer.valueOf(0));
   }

   public float func_70111_Y() {
      return 0.0F;
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if (this.func_180431_b(p_70097_1_)) {
         return false;
      } else if (!p_70097_1_.func_94541_c() && !this.func_82335_i().func_190926_b()) {
         if (!this.field_70170_p.field_72995_K) {
            this.func_146065_b(p_70097_1_.func_76346_g(), false);
            this.func_184185_a(SoundEvents.field_187629_cO, 1.0F, 1.0F);
            this.func_82334_a(ItemStack.field_190927_a);
         }

         return true;
      } else {
         return super.func_70097_a(p_70097_1_, p_70097_2_);
      }
   }

   public int func_82329_d() {
      return 12;
   }

   public int func_82330_g() {
      return 12;
   }

   public boolean func_70112_a(double p_70112_1_) {
      double d0 = 16.0D;
      d0 = d0 * 64.0D * func_184183_bd();
      return p_70112_1_ < d0 * d0;
   }

   public void func_110128_b(@Nullable Entity p_110128_1_) {
      this.func_184185_a(SoundEvents.field_187623_cM, 1.0F, 1.0F);
      this.func_146065_b(p_110128_1_, true);
   }

   public void func_184523_o() {
      this.func_184185_a(SoundEvents.field_187626_cN, 1.0F, 1.0F);
   }

   public void func_146065_b(@Nullable Entity p_146065_1_, boolean p_146065_2_) {
      if (this.field_70170_p.func_82736_K().func_82766_b("doEntityDrops")) {
         ItemStack itemstack = this.func_82335_i();
         if (p_146065_1_ instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)p_146065_1_;
            if (entityplayer.field_71075_bZ.field_75098_d) {
               this.func_110131_b(itemstack);
               return;
            }
         }

         if (p_146065_2_) {
            this.func_70099_a(new ItemStack(Items.field_151160_bD), 0.0F);
         }

         if (!itemstack.func_190926_b() && this.field_70146_Z.nextFloat() < this.field_82337_e) {
            itemstack = itemstack.func_77946_l();
            this.func_110131_b(itemstack);
            this.func_70099_a(itemstack, 0.0F);
         }

      }
   }

   private void func_110131_b(ItemStack p_110131_1_) {
      if (!p_110131_1_.func_190926_b()) {
         if (p_110131_1_.func_77973_b() == Items.field_151098_aY) {
            MapData mapdata = ((ItemMap)p_110131_1_.func_77973_b()).func_77873_a(p_110131_1_, this.field_70170_p);
            mapdata.field_76203_h.remove("frame-" + this.func_145782_y());
         }

         p_110131_1_.func_82842_a((EntityItemFrame)null);
      }
   }

   public ItemStack func_82335_i() {
      return (ItemStack)this.func_184212_Q().func_187225_a(field_184525_c);
   }

   public void func_82334_a(ItemStack p_82334_1_) {
      this.func_174864_a(p_82334_1_, true);
   }

   private void func_174864_a(ItemStack p_174864_1_, boolean p_174864_2_) {
      if (!p_174864_1_.func_190926_b()) {
         p_174864_1_ = p_174864_1_.func_77946_l();
         p_174864_1_.func_190920_e(1);
         p_174864_1_.func_82842_a(this);
      }

      this.func_184212_Q().func_187227_b(field_184525_c, p_174864_1_);
      this.func_184212_Q().func_187217_b(field_184525_c);
      if (!p_174864_1_.func_190926_b()) {
         this.func_184185_a(SoundEvents.field_187620_cL, 1.0F, 1.0F);
      }

      if (p_174864_2_ && this.field_174861_a != null) {
         this.field_70170_p.func_175666_e(this.field_174861_a, Blocks.field_150350_a);
      }

   }

   public void func_184206_a(DataParameter<?> p_184206_1_) {
      if (p_184206_1_.equals(field_184525_c)) {
         ItemStack itemstack = this.func_82335_i();
         if (!itemstack.func_190926_b() && itemstack.func_82836_z() != this) {
            itemstack.func_82842_a(this);
         }
      }

   }

   public int func_82333_j() {
      return ((Integer)this.func_184212_Q().func_187225_a(field_184526_d)).intValue();
   }

   public void func_82336_g(int p_82336_1_) {
      this.func_174865_a(p_82336_1_, true);
   }

   private void func_174865_a(int p_174865_1_, boolean p_174865_2_) {
      this.func_184212_Q().func_187227_b(field_184526_d, Integer.valueOf(p_174865_1_ % 8));
      if (p_174865_2_ && this.field_174861_a != null) {
         this.field_70170_p.func_175666_e(this.field_174861_a, Blocks.field_150350_a);
      }

   }

   public static void func_189738_a(DataFixer p_189738_0_) {
      p_189738_0_.func_188258_a(FixTypes.ENTITY, new ItemStackData(EntityItemFrame.class, new String[]{"Item"}));
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      if (!this.func_82335_i().func_190926_b()) {
         p_70014_1_.func_74782_a("Item", this.func_82335_i().func_77955_b(new NBTTagCompound()));
         p_70014_1_.func_74774_a("ItemRotation", (byte)this.func_82333_j());
         p_70014_1_.func_74776_a("ItemDropChance", this.field_82337_e);
      }

      super.func_70014_b(p_70014_1_);
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      NBTTagCompound nbttagcompound = p_70037_1_.func_74775_l("Item");
      if (nbttagcompound != null && !nbttagcompound.func_82582_d()) {
         this.func_174864_a(new ItemStack(nbttagcompound), false);
         this.func_174865_a(p_70037_1_.func_74771_c("ItemRotation"), false);
         if (p_70037_1_.func_150297_b("ItemDropChance", 99)) {
            this.field_82337_e = p_70037_1_.func_74760_g("ItemDropChance");
         }
      }

      super.func_70037_a(p_70037_1_);
   }

   public boolean func_184230_a(EntityPlayer p_184230_1_, EnumHand p_184230_2_) {
      ItemStack itemstack = p_184230_1_.func_184586_b(p_184230_2_);
      if (!this.field_70170_p.field_72995_K) {
         if (this.func_82335_i().func_190926_b()) {
            if (!itemstack.func_190926_b()) {
               this.func_82334_a(itemstack);
               if (!p_184230_1_.field_71075_bZ.field_75098_d) {
                  itemstack.func_190918_g(1);
               }
            }
         } else {
            this.func_184185_a(SoundEvents.field_187632_cP, 1.0F, 1.0F);
            this.func_82336_g(this.func_82333_j() + 1);
         }
      }

      return true;
   }

   public int func_174866_q() {
      return this.func_82335_i().func_190926_b() ? 0 : this.func_82333_j() % 8 + 1;
   }
}
