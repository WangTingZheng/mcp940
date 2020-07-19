package net.minecraft.entity.monster;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityPigZombie extends EntityZombie {
   private static final UUID field_110189_bq = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
   private static final AttributeModifier field_110190_br = (new AttributeModifier(field_110189_bq, "Attacking speed boost", 0.05D, 0)).func_111168_a(false);
   private int field_70837_d;
   private int field_70838_e;
   private UUID field_175459_bn;

   public EntityPigZombie(World p_i1739_1_) {
      super(p_i1739_1_);
      this.field_70178_ae = true;
   }

   public void func_70604_c(@Nullable EntityLivingBase p_70604_1_) {
      super.func_70604_c(p_70604_1_);
      if (p_70604_1_ != null) {
         this.field_175459_bn = p_70604_1_.func_110124_au();
      }

   }

   protected void func_175456_n() {
      this.field_70715_bh.func_75776_a(1, new EntityPigZombie.AIHurtByAggressor(this));
      this.field_70715_bh.func_75776_a(2, new EntityPigZombie.AITargetAggressor(this));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(field_110186_bp).func_111128_a(0.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.23000000417232513D);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(5.0D);
   }

   protected void func_70619_bc() {
      IAttributeInstance iattributeinstance = this.func_110148_a(SharedMonsterAttributes.field_111263_d);
      if (this.func_175457_ck()) {
         if (!this.func_70631_g_() && !iattributeinstance.func_180374_a(field_110190_br)) {
            iattributeinstance.func_111121_a(field_110190_br);
         }

         --this.field_70837_d;
      } else if (iattributeinstance.func_180374_a(field_110190_br)) {
         iattributeinstance.func_111124_b(field_110190_br);
      }

      if (this.field_70838_e > 0 && --this.field_70838_e == 0) {
         this.func_184185_a(SoundEvents.field_187936_hj, this.func_70599_aP() * 2.0F, ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F) * 1.8F);
      }

      if (this.field_70837_d > 0 && this.field_175459_bn != null && this.func_70643_av() == null) {
         EntityPlayer entityplayer = this.field_70170_p.func_152378_a(this.field_175459_bn);
         this.func_70604_c(entityplayer);
         this.field_70717_bb = entityplayer;
         this.field_70718_bc = this.func_142015_aE();
      }

      super.func_70619_bc();
   }

   public boolean func_70601_bi() {
      return this.field_70170_p.func_175659_aa() != EnumDifficulty.PEACEFUL;
   }

   public boolean func_70058_J() {
      return this.field_70170_p.func_72917_a(this.func_174813_aQ(), this) && this.field_70170_p.func_184144_a(this, this.func_174813_aQ()).isEmpty() && !this.field_70170_p.func_72953_d(this.func_174813_aQ());
   }

   public static void func_189781_b(DataFixer p_189781_0_) {
      EntityLiving.func_189752_a(p_189781_0_, EntityPigZombie.class);
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74777_a("Anger", (short)this.field_70837_d);
      if (this.field_175459_bn != null) {
         p_70014_1_.func_74778_a("HurtBy", this.field_175459_bn.toString());
      } else {
         p_70014_1_.func_74778_a("HurtBy", "");
      }

   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      this.field_70837_d = p_70037_1_.func_74765_d("Anger");
      String s = p_70037_1_.func_74779_i("HurtBy");
      if (!s.isEmpty()) {
         this.field_175459_bn = UUID.fromString(s);
         EntityPlayer entityplayer = this.field_70170_p.func_152378_a(this.field_175459_bn);
         this.func_70604_c(entityplayer);
         if (entityplayer != null) {
            this.field_70717_bb = entityplayer;
            this.field_70718_bc = this.func_142015_aE();
         }
      }

   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if (this.func_180431_b(p_70097_1_)) {
         return false;
      } else {
         Entity entity = p_70097_1_.func_76346_g();
         if (entity instanceof EntityPlayer) {
            this.func_70835_c(entity);
         }

         return super.func_70097_a(p_70097_1_, p_70097_2_);
      }
   }

   private void func_70835_c(Entity p_70835_1_) {
      this.field_70837_d = 400 + this.field_70146_Z.nextInt(400);
      this.field_70838_e = this.field_70146_Z.nextInt(40);
      if (p_70835_1_ instanceof EntityLivingBase) {
         this.func_70604_c((EntityLivingBase)p_70835_1_);
      }

   }

   public boolean func_175457_ck() {
      return this.field_70837_d > 0;
   }

   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187935_hi;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_187938_hl;
   }

   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187937_hk;
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186384_ai;
   }

   public boolean func_184645_a(EntityPlayer p_184645_1_, EnumHand p_184645_2_) {
      return false;
   }

   protected void func_180481_a(DifficultyInstance p_180481_1_) {
      this.func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151010_B));
   }

   protected ItemStack func_190732_dj() {
      return ItemStack.field_190927_a;
   }

   public boolean func_191990_c(EntityPlayer p_191990_1_) {
      return this.func_175457_ck();
   }

   static class AIHurtByAggressor extends EntityAIHurtByTarget {
      public AIHurtByAggressor(EntityPigZombie p_i45828_1_) {
         super(p_i45828_1_, true);
      }

      protected void func_179446_a(EntityCreature p_179446_1_, EntityLivingBase p_179446_2_) {
         super.func_179446_a(p_179446_1_, p_179446_2_);
         if (p_179446_1_ instanceof EntityPigZombie) {
            ((EntityPigZombie)p_179446_1_).func_70835_c(p_179446_2_);
         }

      }
   }

   static class AITargetAggressor extends EntityAINearestAttackableTarget<EntityPlayer> {
      public AITargetAggressor(EntityPigZombie p_i45829_1_) {
         super(p_i45829_1_, EntityPlayer.class, true);
      }

      public boolean func_75250_a() {
         return ((EntityPigZombie)this.field_75299_d).func_175457_ck() && super.func_75250_a();
      }
   }
}
