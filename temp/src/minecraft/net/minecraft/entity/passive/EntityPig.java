package net.minecraft.entity.passive;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityPig extends EntityAnimal {
   private static final DataParameter<Boolean> field_184763_bv = EntityDataManager.<Boolean>func_187226_a(EntityPig.class, DataSerializers.field_187198_h);
   private static final DataParameter<Integer> field_191520_bx = EntityDataManager.<Integer>func_187226_a(EntityPig.class, DataSerializers.field_187192_b);
   private static final Set<Item> field_184764_bw = Sets.newHashSet(Items.field_151172_bF, Items.field_151174_bG, Items.field_185164_cV);
   private boolean field_184765_bx;
   private int field_184766_bz;
   private int field_184767_bA;

   public EntityPig(World p_i1689_1_) {
      super(p_i1689_1_);
      this.func_70105_a(0.9F, 0.9F);
   }

   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new EntityAIPanic(this, 1.25D));
      this.field_70714_bg.func_75776_a(3, new EntityAIMate(this, 1.0D));
      this.field_70714_bg.func_75776_a(4, new EntityAITempt(this, 1.2D, Items.field_151146_bM, false));
      this.field_70714_bg.func_75776_a(4, new EntityAITempt(this, 1.2D, false, field_184764_bw));
      this.field_70714_bg.func_75776_a(5, new EntityAIFollowParent(this, 1.1D));
      this.field_70714_bg.func_75776_a(6, new EntityAIWanderAvoidWater(this, 1.0D));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.field_70714_bg.func_75776_a(8, new EntityAILookIdle(this));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
   }

   @Nullable
   public Entity func_184179_bs() {
      return this.func_184188_bt().isEmpty() ? null : (Entity)this.func_184188_bt().get(0);
   }

   public boolean func_82171_bF() {
      Entity entity = this.func_184179_bs();
      if (!(entity instanceof EntityPlayer)) {
         return false;
      } else {
         EntityPlayer entityplayer = (EntityPlayer)entity;
         return entityplayer.func_184614_ca().func_77973_b() == Items.field_151146_bM || entityplayer.func_184592_cb().func_77973_b() == Items.field_151146_bM;
      }
   }

   public void func_184206_a(DataParameter<?> p_184206_1_) {
      if (field_191520_bx.equals(p_184206_1_) && this.field_70170_p.field_72995_K) {
         this.field_184765_bx = true;
         this.field_184766_bz = 0;
         this.field_184767_bA = ((Integer)this.field_70180_af.func_187225_a(field_191520_bx)).intValue();
      }

      super.func_184206_a(p_184206_1_);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184763_bv, Boolean.valueOf(false));
      this.field_70180_af.func_187214_a(field_191520_bx, Integer.valueOf(0));
   }

   public static void func_189792_b(DataFixer p_189792_0_) {
      EntityLiving.func_189752_a(p_189792_0_, EntityPig.class);
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74757_a("Saddle", this.func_70901_n());
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      this.func_70900_e(p_70037_1_.func_74767_n("Saddle"));
   }

   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187697_dL;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_187703_dN;
   }

   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187700_dM;
   }

   protected void func_180429_a(BlockPos p_180429_1_, Block p_180429_2_) {
      this.func_184185_a(SoundEvents.field_187709_dP, 0.15F, 1.0F);
   }

   public boolean func_184645_a(EntityPlayer p_184645_1_, EnumHand p_184645_2_) {
      if (!super.func_184645_a(p_184645_1_, p_184645_2_)) {
         ItemStack itemstack = p_184645_1_.func_184586_b(p_184645_2_);
         if (itemstack.func_77973_b() == Items.field_151057_cb) {
            itemstack.func_111282_a(p_184645_1_, this, p_184645_2_);
            return true;
         } else if (this.func_70901_n() && !this.func_184207_aI()) {
            if (!this.field_70170_p.field_72995_K) {
               p_184645_1_.func_184220_m(this);
            }

            return true;
         } else if (itemstack.func_77973_b() == Items.field_151141_av) {
            itemstack.func_111282_a(p_184645_1_, this, p_184645_2_);
            return true;
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   public void func_70645_a(DamageSource p_70645_1_) {
      super.func_70645_a(p_70645_1_);
      if (!this.field_70170_p.field_72995_K) {
         if (this.func_70901_n()) {
            this.func_145779_a(Items.field_151141_av, 1);
         }

      }
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186395_C;
   }

   public boolean func_70901_n() {
      return ((Boolean)this.field_70180_af.func_187225_a(field_184763_bv)).booleanValue();
   }

   public void func_70900_e(boolean p_70900_1_) {
      if (p_70900_1_) {
         this.field_70180_af.func_187227_b(field_184763_bv, Boolean.valueOf(true));
      } else {
         this.field_70180_af.func_187227_b(field_184763_bv, Boolean.valueOf(false));
      }

   }

   public void func_70077_a(EntityLightningBolt p_70077_1_) {
      if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
         EntityPigZombie entitypigzombie = new EntityPigZombie(this.field_70170_p);
         entitypigzombie.func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151010_B));
         entitypigzombie.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
         entitypigzombie.func_94061_f(this.func_175446_cd());
         if (this.func_145818_k_()) {
            entitypigzombie.func_96094_a(this.func_95999_t());
            entitypigzombie.func_174805_g(this.func_174833_aM());
         }

         this.field_70170_p.func_72838_d(entitypigzombie);
         this.func_70106_y();
      }
   }

   public void func_191986_a(float p_191986_1_, float p_191986_2_, float p_191986_3_) {
      Entity entity = this.func_184188_bt().isEmpty() ? null : (Entity)this.func_184188_bt().get(0);
      if (this.func_184207_aI() && this.func_82171_bF()) {
         this.field_70177_z = entity.field_70177_z;
         this.field_70126_B = this.field_70177_z;
         this.field_70125_A = entity.field_70125_A * 0.5F;
         this.func_70101_b(this.field_70177_z, this.field_70125_A);
         this.field_70761_aq = this.field_70177_z;
         this.field_70759_as = this.field_70177_z;
         this.field_70138_W = 1.0F;
         this.field_70747_aH = this.func_70689_ay() * 0.1F;
         if (this.field_184765_bx && this.field_184766_bz++ > this.field_184767_bA) {
            this.field_184765_bx = false;
         }

         if (this.func_184186_bw()) {
            float f = (float)this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e() * 0.225F;
            if (this.field_184765_bx) {
               f += f * 1.15F * MathHelper.func_76126_a((float)this.field_184766_bz / (float)this.field_184767_bA * 3.1415927F);
            }

            this.func_70659_e(f);
            super.func_191986_a(0.0F, 0.0F, 1.0F);
         } else {
            this.field_70159_w = 0.0D;
            this.field_70181_x = 0.0D;
            this.field_70179_y = 0.0D;
         }

         this.field_184618_aE = this.field_70721_aZ;
         double d1 = this.field_70165_t - this.field_70169_q;
         double d0 = this.field_70161_v - this.field_70166_s;
         float f1 = MathHelper.func_76133_a(d1 * d1 + d0 * d0) * 4.0F;
         if (f1 > 1.0F) {
            f1 = 1.0F;
         }

         this.field_70721_aZ += (f1 - this.field_70721_aZ) * 0.4F;
         this.field_184619_aG += this.field_70721_aZ;
      } else {
         this.field_70138_W = 0.5F;
         this.field_70747_aH = 0.02F;
         super.func_191986_a(p_191986_1_, p_191986_2_, p_191986_3_);
      }
   }

   public boolean func_184762_da() {
      if (this.field_184765_bx) {
         return false;
      } else {
         this.field_184765_bx = true;
         this.field_184766_bz = 0;
         this.field_184767_bA = this.func_70681_au().nextInt(841) + 140;
         this.func_184212_Q().func_187227_b(field_191520_bx, Integer.valueOf(this.field_184767_bA));
         return true;
      }
   }

   public EntityPig func_90011_a(EntityAgeable p_90011_1_) {
      return new EntityPig(this.field_70170_p);
   }

   public boolean func_70877_b(ItemStack p_70877_1_) {
      return field_184764_bw.contains(p_70877_1_.func_77973_b());
   }
}
