package net.minecraft.entity.passive;

import javax.annotation.Nullable;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISkeletonRiders;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntitySkeletonHorse extends AbstractHorse {
   private final EntityAISkeletonRiders field_184792_bN = new EntityAISkeletonRiders(this);
   private boolean field_184793_bU;
   private int field_184794_bV;

   public EntitySkeletonHorse(World p_i47295_1_) {
      super(p_i47295_1_);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(15.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.20000000298023224D);
      this.func_110148_a(field_110271_bv).func_111128_a(this.func_110245_cM());
   }

   protected SoundEvent func_184639_G() {
      super.func_184639_G();
      return SoundEvents.field_187858_fe;
   }

   protected SoundEvent func_184615_bR() {
      super.func_184615_bR();
      return SoundEvents.field_187860_ff;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      super.func_184601_bQ(p_184601_1_);
      return SoundEvents.field_187862_fg;
   }

   public EnumCreatureAttribute func_70668_bt() {
      return EnumCreatureAttribute.UNDEAD;
   }

   public double func_70042_X() {
      return super.func_70042_X() - 0.1875D;
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186398_F;
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (this.func_190690_dh() && this.field_184794_bV++ >= 18000) {
         this.func_70106_y();
      }

   }

   public static void func_190692_b(DataFixer p_190692_0_) {
      AbstractHorse.func_190683_c(p_190692_0_, EntitySkeletonHorse.class);
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74757_a("SkeletonTrap", this.func_190690_dh());
      p_70014_1_.func_74768_a("SkeletonTrapTime", this.field_184794_bV);
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      this.func_190691_p(p_70037_1_.func_74767_n("SkeletonTrap"));
      this.field_184794_bV = p_70037_1_.func_74762_e("SkeletonTrapTime");
   }

   public boolean func_190690_dh() {
      return this.field_184793_bU;
   }

   public void func_190691_p(boolean p_190691_1_) {
      if (p_190691_1_ != this.field_184793_bU) {
         this.field_184793_bU = p_190691_1_;
         if (p_190691_1_) {
            this.field_70714_bg.func_75776_a(1, this.field_184792_bN);
         } else {
            this.field_70714_bg.func_85156_a(this.field_184792_bN);
         }

      }
   }

   public boolean func_184645_a(EntityPlayer p_184645_1_, EnumHand p_184645_2_) {
      ItemStack itemstack = p_184645_1_.func_184586_b(p_184645_2_);
      boolean flag = !itemstack.func_190926_b();
      if (flag && itemstack.func_77973_b() == Items.field_151063_bx) {
         return super.func_184645_a(p_184645_1_, p_184645_2_);
      } else if (!this.func_110248_bS()) {
         return false;
      } else if (this.func_70631_g_()) {
         return super.func_184645_a(p_184645_1_, p_184645_2_);
      } else if (p_184645_1_.func_70093_af()) {
         this.func_110199_f(p_184645_1_);
         return true;
      } else if (this.func_184207_aI()) {
         return super.func_184645_a(p_184645_1_, p_184645_2_);
      } else {
         if (flag) {
            if (itemstack.func_77973_b() == Items.field_151141_av && !this.func_110257_ck()) {
               this.func_110199_f(p_184645_1_);
               return true;
            }

            if (itemstack.func_111282_a(p_184645_1_, this, p_184645_2_)) {
               return true;
            }
         }

         this.func_110237_h(p_184645_1_);
         return true;
      }
   }
}
