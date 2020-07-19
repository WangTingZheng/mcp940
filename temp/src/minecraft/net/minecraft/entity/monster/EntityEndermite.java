package net.minecraft.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityEndermite extends EntityMob {
   private int field_175497_b;
   private boolean field_175498_c;

   public EntityEndermite(World p_i45840_1_) {
      super(p_i45840_1_);
      this.field_70728_aV = 3;
      this.func_70105_a(0.4F, 0.3F);
   }

   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(1, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(2, new EntityAIAttackMelee(this, 1.0D, false));
      this.field_70714_bg.func_75776_a(3, new EntityAIWanderAvoidWater(this, 1.0D));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.field_70714_bg.func_75776_a(8, new EntityAILookIdle(this));
      this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, true, new Class[0]));
      this.field_70715_bh.func_75776_a(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
   }

   public float func_70047_e() {
      return 0.1F;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(8.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(2.0D);
   }

   protected boolean func_70041_e_() {
      return false;
   }

   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187535_aY;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_187590_ba;
   }

   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187536_aZ;
   }

   protected void func_180429_a(BlockPos p_180429_1_, Block p_180429_2_) {
      this.func_184185_a(SoundEvents.field_187592_bb, 0.15F, 1.0F);
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186382_ag;
   }

   public static void func_189764_b(DataFixer p_189764_0_) {
      EntityLiving.func_189752_a(p_189764_0_, EntityEndermite.class);
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      this.field_175497_b = p_70037_1_.func_74762_e("Lifetime");
      this.field_175498_c = p_70037_1_.func_74767_n("PlayerSpawned");
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74768_a("Lifetime", this.field_175497_b);
      p_70014_1_.func_74757_a("PlayerSpawned", this.field_175498_c);
   }

   public void func_70071_h_() {
      this.field_70761_aq = this.field_70177_z;
      super.func_70071_h_();
   }

   public void func_181013_g(float p_181013_1_) {
      this.field_70177_z = p_181013_1_;
      super.func_181013_g(p_181013_1_);
   }

   public double func_70033_W() {
      return 0.1D;
   }

   public boolean func_175495_n() {
      return this.field_175498_c;
   }

   public void func_175496_a(boolean p_175496_1_) {
      this.field_175498_c = p_175496_1_;
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (this.field_70170_p.field_72995_K) {
         for(int i = 0; i < 2; ++i) {
            this.field_70170_p.func_175688_a(EnumParticleTypes.PORTAL, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, (this.field_70146_Z.nextDouble() - 0.5D) * 2.0D, -this.field_70146_Z.nextDouble(), (this.field_70146_Z.nextDouble() - 0.5D) * 2.0D);
         }
      } else {
         if (!this.func_104002_bU()) {
            ++this.field_175497_b;
         }

         if (this.field_175497_b >= 2400) {
            this.func_70106_y();
         }
      }

   }

   protected boolean func_70814_o() {
      return true;
   }

   public boolean func_70601_bi() {
      if (super.func_70601_bi()) {
         EntityPlayer entityplayer = this.field_70170_p.func_72890_a(this, 5.0D);
         return entityplayer == null;
      } else {
         return false;
      }
   }

   public EnumCreatureAttribute func_70668_bt() {
      return EnumCreatureAttribute.ARTHROPOD;
   }
}
