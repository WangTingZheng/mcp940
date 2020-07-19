package net.minecraft.entity.monster;

import java.util.Calendar;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAttackRangedBow;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public abstract class AbstractSkeleton extends EntityMob implements IRangedAttackMob {
   private static final DataParameter<Boolean> field_184728_b = EntityDataManager.<Boolean>func_187226_a(AbstractSkeleton.class, DataSerializers.field_187198_h);
   private final EntityAIAttackRangedBow<AbstractSkeleton> field_85037_d = new EntityAIAttackRangedBow<AbstractSkeleton>(this, 1.0D, 20, 15.0F);
   private final EntityAIAttackMelee field_85038_e = new EntityAIAttackMelee(this, 1.2D, false) {
      public void func_75251_c() {
         super.func_75251_c();
         AbstractSkeleton.this.func_184724_a(false);
      }

      public void func_75249_e() {
         super.func_75249_e();
         AbstractSkeleton.this.func_184724_a(true);
      }
   };

   public AbstractSkeleton(World p_i47289_1_) {
      super(p_i47289_1_);
      this.func_70105_a(0.6F, 1.99F);
      this.func_85036_m();
   }

   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(1, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(2, new EntityAIRestrictSun(this));
      this.field_70714_bg.func_75776_a(3, new EntityAIFleeSun(this, 1.0D));
      this.field_70714_bg.func_75776_a(3, new EntityAIAvoidEntity(this, EntityWolf.class, 6.0F, 1.0D, 1.2D));
      this.field_70714_bg.func_75776_a(5, new EntityAIWanderAvoidWater(this, 1.0D));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.field_70714_bg.func_75776_a(6, new EntityAILookIdle(this));
      this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, false, new Class[0]));
      this.field_70715_bh.func_75776_a(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
      this.field_70715_bh.func_75776_a(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184728_b, Boolean.valueOf(false));
   }

   protected void func_180429_a(BlockPos p_180429_1_, Block p_180429_2_) {
      this.func_184185_a(this.func_190727_o(), 0.15F, 1.0F);
   }

   abstract SoundEvent func_190727_o();

   public EnumCreatureAttribute func_70668_bt() {
      return EnumCreatureAttribute.UNDEAD;
   }

   public void func_70636_d() {
      if (this.field_70170_p.func_72935_r() && !this.field_70170_p.field_72995_K) {
         float f = this.func_70013_c();
         BlockPos blockpos = this.func_184187_bx() instanceof EntityBoat ? (new BlockPos(this.field_70165_t, (double)Math.round(this.field_70163_u), this.field_70161_v)).func_177984_a() : new BlockPos(this.field_70165_t, (double)Math.round(this.field_70163_u), this.field_70161_v);
         if (f > 0.5F && this.field_70146_Z.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.field_70170_p.func_175678_i(blockpos)) {
            boolean flag = true;
            ItemStack itemstack = this.func_184582_a(EntityEquipmentSlot.HEAD);
            if (!itemstack.func_190926_b()) {
               if (itemstack.func_77984_f()) {
                  itemstack.func_77964_b(itemstack.func_77952_i() + this.field_70146_Z.nextInt(2));
                  if (itemstack.func_77952_i() >= itemstack.func_77958_k()) {
                     this.func_70669_a(itemstack);
                     this.func_184201_a(EntityEquipmentSlot.HEAD, ItemStack.field_190927_a);
                  }
               }

               flag = false;
            }

            if (flag) {
               this.func_70015_d(8);
            }
         }
      }

      super.func_70636_d();
   }

   public void func_70098_U() {
      super.func_70098_U();
      if (this.func_184187_bx() instanceof EntityCreature) {
         EntityCreature entitycreature = (EntityCreature)this.func_184187_bx();
         this.field_70761_aq = entitycreature.field_70761_aq;
      }

   }

   protected void func_180481_a(DifficultyInstance p_180481_1_) {
      super.func_180481_a(p_180481_1_);
      this.func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151031_f));
   }

   @Nullable
   public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, @Nullable IEntityLivingData p_180482_2_) {
      p_180482_2_ = super.func_180482_a(p_180482_1_, p_180482_2_);
      this.func_180481_a(p_180482_1_);
      this.func_180483_b(p_180482_1_);
      this.func_85036_m();
      this.func_98053_h(this.field_70146_Z.nextFloat() < 0.55F * p_180482_1_.func_180170_c());
      if (this.func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b()) {
         Calendar calendar = this.field_70170_p.func_83015_S();
         if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.field_70146_Z.nextFloat() < 0.25F) {
            this.func_184201_a(EntityEquipmentSlot.HEAD, new ItemStack(this.field_70146_Z.nextFloat() < 0.1F ? Blocks.field_150428_aP : Blocks.field_150423_aK));
            this.field_184655_bs[EntityEquipmentSlot.HEAD.func_188454_b()] = 0.0F;
         }
      }

      return p_180482_2_;
   }

   public void func_85036_m() {
      if (this.field_70170_p != null && !this.field_70170_p.field_72995_K) {
         this.field_70714_bg.func_85156_a(this.field_85038_e);
         this.field_70714_bg.func_85156_a(this.field_85037_d);
         ItemStack itemstack = this.func_184614_ca();
         if (itemstack.func_77973_b() == Items.field_151031_f) {
            int i = 20;
            if (this.field_70170_p.func_175659_aa() != EnumDifficulty.HARD) {
               i = 40;
            }

            this.field_85037_d.func_189428_b(i);
            this.field_70714_bg.func_75776_a(4, this.field_85037_d);
         } else {
            this.field_70714_bg.func_75776_a(4, this.field_85038_e);
         }

      }
   }

   public void func_82196_d(EntityLivingBase p_82196_1_, float p_82196_2_) {
      EntityArrow entityarrow = this.func_190726_a(p_82196_2_);
      double d0 = p_82196_1_.field_70165_t - this.field_70165_t;
      double d1 = p_82196_1_.func_174813_aQ().field_72338_b + (double)(p_82196_1_.field_70131_O / 3.0F) - entityarrow.field_70163_u;
      double d2 = p_82196_1_.field_70161_v - this.field_70161_v;
      double d3 = (double)MathHelper.func_76133_a(d0 * d0 + d2 * d2);
      entityarrow.func_70186_c(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (float)(14 - this.field_70170_p.func_175659_aa().func_151525_a() * 4));
      this.func_184185_a(SoundEvents.field_187866_fi, 1.0F, 1.0F / (this.func_70681_au().nextFloat() * 0.4F + 0.8F));
      this.field_70170_p.func_72838_d(entityarrow);
   }

   protected EntityArrow func_190726_a(float p_190726_1_) {
      EntityTippedArrow entitytippedarrow = new EntityTippedArrow(this.field_70170_p, this);
      entitytippedarrow.func_190547_a(this, p_190726_1_);
      return entitytippedarrow;
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      this.func_85036_m();
   }

   public void func_184201_a(EntityEquipmentSlot p_184201_1_, ItemStack p_184201_2_) {
      super.func_184201_a(p_184201_1_, p_184201_2_);
      if (!this.field_70170_p.field_72995_K && p_184201_1_ == EntityEquipmentSlot.MAINHAND) {
         this.func_85036_m();
      }

   }

   public float func_70047_e() {
      return 1.74F;
   }

   public double func_70033_W() {
      return -0.6D;
   }

   public boolean func_184725_db() {
      return ((Boolean)this.field_70180_af.func_187225_a(field_184728_b)).booleanValue();
   }

   public void func_184724_a(boolean p_184724_1_) {
      this.field_70180_af.func_187227_b(field_184728_b, Boolean.valueOf(p_184724_1_));
   }
}
