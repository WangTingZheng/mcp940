package net.minecraft.entity.passive;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class EntityAnimal extends EntityAgeable implements IAnimals {
   protected Block field_175506_bl = Blocks.field_150349_c;
   private int field_70881_d;
   private UUID field_146084_br;

   public EntityAnimal(World p_i1681_1_) {
      super(p_i1681_1_);
   }

   protected void func_70619_bc() {
      if (this.func_70874_b() != 0) {
         this.field_70881_d = 0;
      }

      super.func_70619_bc();
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (this.func_70874_b() != 0) {
         this.field_70881_d = 0;
      }

      if (this.field_70881_d > 0) {
         --this.field_70881_d;
         if (this.field_70881_d % 10 == 0) {
            double d0 = this.field_70146_Z.nextGaussian() * 0.02D;
            double d1 = this.field_70146_Z.nextGaussian() * 0.02D;
            double d2 = this.field_70146_Z.nextGaussian() * 0.02D;
            this.field_70170_p.func_175688_a(EnumParticleTypes.HEART, this.field_70165_t + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, this.field_70163_u + 0.5D + (double)(this.field_70146_Z.nextFloat() * this.field_70131_O), this.field_70161_v + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, d0, d1, d2);
         }
      }

   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if (this.func_180431_b(p_70097_1_)) {
         return false;
      } else {
         this.field_70881_d = 0;
         return super.func_70097_a(p_70097_1_, p_70097_2_);
      }
   }

   public float func_180484_a(BlockPos p_180484_1_) {
      return this.field_70170_p.func_180495_p(p_180484_1_.func_177977_b()).func_177230_c() == this.field_175506_bl ? 10.0F : this.field_70170_p.func_175724_o(p_180484_1_) - 0.5F;
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74768_a("InLove", this.field_70881_d);
      if (this.field_146084_br != null) {
         p_70014_1_.func_186854_a("LoveCause", this.field_146084_br);
      }

   }

   public double func_70033_W() {
      return 0.14D;
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      this.field_70881_d = p_70037_1_.func_74762_e("InLove");
      this.field_146084_br = p_70037_1_.func_186855_b("LoveCause") ? p_70037_1_.func_186857_a("LoveCause") : null;
   }

   public boolean func_70601_bi() {
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int j = MathHelper.func_76128_c(this.func_174813_aQ().field_72338_b);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      BlockPos blockpos = new BlockPos(i, j, k);
      return this.field_70170_p.func_180495_p(blockpos.func_177977_b()).func_177230_c() == this.field_175506_bl && this.field_70170_p.func_175699_k(blockpos) > 8 && super.func_70601_bi();
   }

   public int func_70627_aG() {
      return 120;
   }

   protected boolean func_70692_ba() {
      return false;
   }

   protected int func_70693_a(EntityPlayer p_70693_1_) {
      return 1 + this.field_70170_p.field_73012_v.nextInt(3);
   }

   public boolean func_70877_b(ItemStack p_70877_1_) {
      return p_70877_1_.func_77973_b() == Items.field_151015_O;
   }

   public boolean func_184645_a(EntityPlayer p_184645_1_, EnumHand p_184645_2_) {
      ItemStack itemstack = p_184645_1_.func_184586_b(p_184645_2_);
      if (!itemstack.func_190926_b()) {
         if (this.func_70877_b(itemstack) && this.func_70874_b() == 0 && this.field_70881_d <= 0) {
            this.func_175505_a(p_184645_1_, itemstack);
            this.func_146082_f(p_184645_1_);
            return true;
         }

         if (this.func_70631_g_() && this.func_70877_b(itemstack)) {
            this.func_175505_a(p_184645_1_, itemstack);
            this.func_175501_a((int)((float)(-this.func_70874_b() / 20) * 0.1F), true);
            return true;
         }
      }

      return super.func_184645_a(p_184645_1_, p_184645_2_);
   }

   protected void func_175505_a(EntityPlayer p_175505_1_, ItemStack p_175505_2_) {
      if (!p_175505_1_.field_71075_bZ.field_75098_d) {
         p_175505_2_.func_190918_g(1);
      }

   }

   public void func_146082_f(@Nullable EntityPlayer p_146082_1_) {
      this.field_70881_d = 600;
      if (p_146082_1_ != null) {
         this.field_146084_br = p_146082_1_.func_110124_au();
      }

      this.field_70170_p.func_72960_a(this, (byte)18);
   }

   @Nullable
   public EntityPlayerMP func_191993_do() {
      if (this.field_146084_br == null) {
         return null;
      } else {
         EntityPlayer entityplayer = this.field_70170_p.func_152378_a(this.field_146084_br);
         return entityplayer instanceof EntityPlayerMP ? (EntityPlayerMP)entityplayer : null;
      }
   }

   public boolean func_70880_s() {
      return this.field_70881_d > 0;
   }

   public void func_70875_t() {
      this.field_70881_d = 0;
   }

   public boolean func_70878_b(EntityAnimal p_70878_1_) {
      if (p_70878_1_ == this) {
         return false;
      } else if (p_70878_1_.getClass() != this.getClass()) {
         return false;
      } else {
         return this.func_70880_s() && p_70878_1_.func_70880_s();
      }
   }

   public void func_70103_a(byte p_70103_1_) {
      if (p_70103_1_ == 18) {
         for(int i = 0; i < 7; ++i) {
            double d0 = this.field_70146_Z.nextGaussian() * 0.02D;
            double d1 = this.field_70146_Z.nextGaussian() * 0.02D;
            double d2 = this.field_70146_Z.nextGaussian() * 0.02D;
            this.field_70170_p.func_175688_a(EnumParticleTypes.HEART, this.field_70165_t + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, this.field_70163_u + 0.5D + (double)(this.field_70146_Z.nextFloat() * this.field_70131_O), this.field_70161_v + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, d0, d1, d2);
         }
      } else {
         super.func_70103_a(p_70103_1_);
      }

   }
}
