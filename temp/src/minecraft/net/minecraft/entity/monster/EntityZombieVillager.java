package net.minecraft.entity.monster;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityZombieVillager extends EntityZombie {
   private static final DataParameter<Boolean> field_184739_bx = EntityDataManager.<Boolean>func_187226_a(EntityZombieVillager.class, DataSerializers.field_187198_h);
   private static final DataParameter<Integer> field_190739_c = EntityDataManager.<Integer>func_187226_a(EntityZombieVillager.class, DataSerializers.field_187192_b);
   private int field_82234_d;
   private UUID field_191992_by;

   public EntityZombieVillager(World p_i47277_1_) {
      super(p_i47277_1_);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184739_bx, Boolean.valueOf(false));
      this.field_70180_af.func_187214_a(field_190739_c, Integer.valueOf(0));
   }

   public void func_190733_a(int p_190733_1_) {
      this.field_70180_af.func_187227_b(field_190739_c, Integer.valueOf(p_190733_1_));
   }

   public int func_190736_dl() {
      return Math.max(((Integer)this.field_70180_af.func_187225_a(field_190739_c)).intValue() % 6, 0);
   }

   public static void func_190737_b(DataFixer p_190737_0_) {
      EntityLiving.func_189752_a(p_190737_0_, EntityZombieVillager.class);
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74768_a("Profession", this.func_190736_dl());
      p_70014_1_.func_74768_a("ConversionTime", this.func_82230_o() ? this.field_82234_d : -1);
      if (this.field_191992_by != null) {
         p_70014_1_.func_186854_a("ConversionPlayer", this.field_191992_by);
      }

   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      this.func_190733_a(p_70037_1_.func_74762_e("Profession"));
      if (p_70037_1_.func_150297_b("ConversionTime", 99) && p_70037_1_.func_74762_e("ConversionTime") > -1) {
         this.func_191991_a(p_70037_1_.func_186855_b("ConversionPlayer") ? p_70037_1_.func_186857_a("ConversionPlayer") : null, p_70037_1_.func_74762_e("ConversionTime"));
      }

   }

   @Nullable
   public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, @Nullable IEntityLivingData p_180482_2_) {
      this.func_190733_a(this.field_70170_p.field_73012_v.nextInt(6));
      return super.func_180482_a(p_180482_1_, p_180482_2_);
   }

   public void func_70071_h_() {
      if (!this.field_70170_p.field_72995_K && this.func_82230_o()) {
         int i = this.func_190735_dq();
         this.field_82234_d -= i;
         if (this.field_82234_d <= 0) {
            this.func_190738_dp();
         }
      }

      super.func_70071_h_();
   }

   public boolean func_184645_a(EntityPlayer p_184645_1_, EnumHand p_184645_2_) {
      ItemStack itemstack = p_184645_1_.func_184586_b(p_184645_2_);
      if (itemstack.func_77973_b() == Items.field_151153_ao && itemstack.func_77960_j() == 0 && this.func_70644_a(MobEffects.field_76437_t)) {
         if (!p_184645_1_.field_71075_bZ.field_75098_d) {
            itemstack.func_190918_g(1);
         }

         if (!this.field_70170_p.field_72995_K) {
            this.func_191991_a(p_184645_1_.func_110124_au(), this.field_70146_Z.nextInt(2401) + 3600);
         }

         return true;
      } else {
         return false;
      }
   }

   protected boolean func_70692_ba() {
      return !this.func_82230_o();
   }

   public boolean func_82230_o() {
      return ((Boolean)this.func_184212_Q().func_187225_a(field_184739_bx)).booleanValue();
   }

   protected void func_191991_a(@Nullable UUID p_191991_1_, int p_191991_2_) {
      this.field_191992_by = p_191991_1_;
      this.field_82234_d = p_191991_2_;
      this.func_184212_Q().func_187227_b(field_184739_bx, Boolean.valueOf(true));
      this.func_184589_d(MobEffects.field_76437_t);
      this.func_70690_d(new PotionEffect(MobEffects.field_76420_g, p_191991_2_, Math.min(this.field_70170_p.func_175659_aa().func_151525_a() - 1, 0)));
      this.field_70170_p.func_72960_a(this, (byte)16);
   }

   public void func_70103_a(byte p_70103_1_) {
      if (p_70103_1_ == 16) {
         if (!this.func_174814_R()) {
            this.field_70170_p.func_184134_a(this.field_70165_t + 0.5D, this.field_70163_u + 0.5D, this.field_70161_v + 0.5D, SoundEvents.field_187942_hp, this.func_184176_by(), 1.0F + this.field_70146_Z.nextFloat(), this.field_70146_Z.nextFloat() * 0.7F + 0.3F, false);
         }
      } else {
         super.func_70103_a(p_70103_1_);
      }

   }

   protected void func_190738_dp() {
      EntityVillager entityvillager = new EntityVillager(this.field_70170_p);
      entityvillager.func_82149_j(this);
      entityvillager.func_70938_b(this.func_190736_dl());
      entityvillager.func_190672_a(this.field_70170_p.func_175649_E(new BlockPos(entityvillager)), (IEntityLivingData)null, false);
      entityvillager.func_82187_q();
      if (this.func_70631_g_()) {
         entityvillager.func_70873_a(-24000);
      }

      this.field_70170_p.func_72900_e(this);
      entityvillager.func_94061_f(this.func_175446_cd());
      if (this.func_145818_k_()) {
         entityvillager.func_96094_a(this.func_95999_t());
         entityvillager.func_174805_g(this.func_174833_aM());
      }

      this.field_70170_p.func_72838_d(entityvillager);
      if (this.field_191992_by != null) {
         EntityPlayer entityplayer = this.field_70170_p.func_152378_a(this.field_191992_by);
         if (entityplayer instanceof EntityPlayerMP) {
            CriteriaTriggers.field_192137_q.func_192183_a((EntityPlayerMP)entityplayer, this, entityvillager);
         }
      }

      entityvillager.func_70690_d(new PotionEffect(MobEffects.field_76431_k, 200, 0));
      this.field_70170_p.func_180498_a((EntityPlayer)null, 1027, new BlockPos((int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v), 0);
   }

   protected int func_190735_dq() {
      int i = 1;
      if (this.field_70146_Z.nextFloat() < 0.01F) {
         int j = 0;
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

         for(int k = (int)this.field_70165_t - 4; k < (int)this.field_70165_t + 4 && j < 14; ++k) {
            for(int l = (int)this.field_70163_u - 4; l < (int)this.field_70163_u + 4 && j < 14; ++l) {
               for(int i1 = (int)this.field_70161_v - 4; i1 < (int)this.field_70161_v + 4 && j < 14; ++i1) {
                  Block block = this.field_70170_p.func_180495_p(blockpos$mutableblockpos.func_181079_c(k, l, i1)).func_177230_c();
                  if (block == Blocks.field_150411_aY || block == Blocks.field_150324_C) {
                     if (this.field_70146_Z.nextFloat() < 0.3F) {
                        ++i;
                     }

                     ++j;
                  }
               }
            }
         }
      }

      return i;
   }

   protected float func_70647_i() {
      return this.func_70631_g_() ? (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 2.0F : (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F;
   }

   public SoundEvent func_184639_G() {
      return SoundEvents.field_187940_hn;
   }

   public SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_187944_hr;
   }

   public SoundEvent func_184615_bR() {
      return SoundEvents.field_187943_hq;
   }

   public SoundEvent func_190731_di() {
      return SoundEvents.field_187946_ht;
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_191183_as;
   }

   protected ItemStack func_190732_dj() {
      return ItemStack.field_190927_a;
   }
}
