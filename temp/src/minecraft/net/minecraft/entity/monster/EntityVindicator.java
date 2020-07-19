package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityVindicator extends AbstractIllager {
   private boolean field_190643_b;
   private static final Predicate<Entity> field_190644_c = new Predicate<Entity>() {
      public boolean apply(@Nullable Entity p_apply_1_) {
         return p_apply_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_apply_1_).func_190631_cK();
      }
   };

   public EntityVindicator(World p_i47279_1_) {
      super(p_i47279_1_);
      this.func_70105_a(0.6F, 1.95F);
   }

   public static void func_190641_b(DataFixer p_190641_0_) {
      EntityLiving.func_189752_a(p_190641_0_, EntityVindicator.class);
   }

   protected void func_184651_r() {
      super.func_184651_r();
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(4, new EntityAIAttackMelee(this, 1.0D, false));
      this.field_70714_bg.func_75776_a(8, new EntityAIWander(this, 0.6D));
      this.field_70714_bg.func_75776_a(9, new EntityAIWatchClosest(this, EntityPlayer.class, 3.0F, 1.0F));
      this.field_70714_bg.func_75776_a(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
      this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, true, new Class[]{EntityVindicator.class}));
      this.field_70715_bh.func_75776_a(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
      this.field_70715_bh.func_75776_a(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, true));
      this.field_70715_bh.func_75776_a(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
      this.field_70715_bh.func_75776_a(4, new EntityVindicator.AIJohnnyAttack(this));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3499999940395355D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(12.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(24.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(5.0D);
   }

   protected void func_70088_a() {
      super.func_70088_a();
   }

   protected ResourceLocation func_184647_J() {
      return LootTableList.field_191186_av;
   }

   public boolean func_190639_o() {
      return this.func_193078_a(1);
   }

   public void func_190636_a(boolean p_190636_1_) {
      this.func_193079_a(1, p_190636_1_);
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      if (this.field_190643_b) {
         p_70014_1_.func_74757_a("Johnny", true);
      }

   }

   public AbstractIllager.IllagerArmPose func_193077_p() {
      return this.func_190639_o() ? AbstractIllager.IllagerArmPose.ATTACKING : AbstractIllager.IllagerArmPose.CROSSED;
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      if (p_70037_1_.func_150297_b("Johnny", 99)) {
         this.field_190643_b = p_70037_1_.func_74767_n("Johnny");
      }

   }

   @Nullable
   public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, @Nullable IEntityLivingData p_180482_2_) {
      IEntityLivingData ientitylivingdata = super.func_180482_a(p_180482_1_, p_180482_2_);
      this.func_180481_a(p_180482_1_);
      this.func_180483_b(p_180482_1_);
      return ientitylivingdata;
   }

   protected void func_180481_a(DifficultyInstance p_180481_1_) {
      this.func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151036_c));
   }

   protected void func_70619_bc() {
      super.func_70619_bc();
      this.func_190636_a(this.func_70638_az() != null);
   }

   public boolean func_184191_r(Entity p_184191_1_) {
      if (super.func_184191_r(p_184191_1_)) {
         return true;
      } else if (p_184191_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_184191_1_).func_70668_bt() == EnumCreatureAttribute.ILLAGER) {
         return this.func_96124_cp() == null && p_184191_1_.func_96124_cp() == null;
      } else {
         return false;
      }
   }

   public void func_96094_a(String p_96094_1_) {
      super.func_96094_a(p_96094_1_);
      if (!this.field_190643_b && "Johnny".equals(p_96094_1_)) {
         this.field_190643_b = true;
      }

   }

   protected SoundEvent func_184639_G() {
      return SoundEvents.field_191268_hm;
   }

   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_191269_hn;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_191270_ho;
   }

   static class AIJohnnyAttack extends EntityAINearestAttackableTarget<EntityLivingBase> {
      public AIJohnnyAttack(EntityVindicator p_i47345_1_) {
         super(p_i47345_1_, EntityLivingBase.class, 0, true, true, EntityVindicator.field_190644_c);
      }

      public boolean func_75250_a() {
         return ((EntityVindicator)this.field_75299_d).field_190643_b && super.func_75250_a();
      }
   }
}
