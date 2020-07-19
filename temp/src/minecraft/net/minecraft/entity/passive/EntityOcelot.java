package net.minecraft.entity.passive;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOcelotAttack;
import net.minecraft.entity.ai.EntityAIOcelotSit;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
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
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityOcelot extends EntityTameable {
   private static final DataParameter<Integer> field_184757_bz = EntityDataManager.<Integer>func_187226_a(EntityOcelot.class, DataSerializers.field_187192_b);
   private EntityAIAvoidEntity<EntityPlayer> field_175545_bm;
   private EntityAITempt field_70914_e;

   public EntityOcelot(World p_i1688_1_) {
      super(p_i1688_1_);
      this.func_70105_a(0.6F, 0.7F);
   }

   protected void func_184651_r() {
      this.field_70911_d = new EntityAISit(this);
      this.field_70914_e = new EntityAITempt(this, 0.6D, Items.field_151115_aP, true);
      this.field_70714_bg.func_75776_a(1, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(2, this.field_70911_d);
      this.field_70714_bg.func_75776_a(3, this.field_70914_e);
      this.field_70714_bg.func_75776_a(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 5.0F));
      this.field_70714_bg.func_75776_a(6, new EntityAIOcelotSit(this, 0.8D));
      this.field_70714_bg.func_75776_a(7, new EntityAILeapAtTarget(this, 0.3F));
      this.field_70714_bg.func_75776_a(8, new EntityAIOcelotAttack(this));
      this.field_70714_bg.func_75776_a(9, new EntityAIMate(this, 0.8D));
      this.field_70714_bg.func_75776_a(10, new EntityAIWanderAvoidWater(this, 0.8D, 1.0000001E-5F));
      this.field_70714_bg.func_75776_a(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
      this.field_70715_bh.func_75776_a(1, new EntityAITargetNonTamed(this, EntityChicken.class, false, (Predicate)null));
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184757_bz, Integer.valueOf(0));
   }

   public void func_70619_bc() {
      if (this.func_70605_aq().func_75640_a()) {
         double d0 = this.func_70605_aq().func_75638_b();
         if (d0 == 0.6D) {
            this.func_70095_a(true);
            this.func_70031_b(false);
         } else if (d0 == 1.33D) {
            this.func_70095_a(false);
            this.func_70031_b(true);
         } else {
            this.func_70095_a(false);
            this.func_70031_b(false);
         }
      } else {
         this.func_70095_a(false);
         this.func_70031_b(false);
      }

   }

   protected boolean func_70692_ba() {
      return !this.func_70909_n() && this.field_70173_aa > 2400;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.30000001192092896D);
   }

   public void func_180430_e(float p_180430_1_, float p_180430_2_) {
   }

   public static void func_189787_b(DataFixer p_189787_0_) {
      EntityLiving.func_189752_a(p_189787_0_, EntityOcelot.class);
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74768_a("CatType", this.func_70913_u());
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      this.func_70912_b(p_70037_1_.func_74762_e("CatType"));
   }

   @Nullable
   protected SoundEvent func_184639_G() {
      if (this.func_70909_n()) {
         if (this.func_70880_s()) {
            return SoundEvents.field_187645_R;
         } else {
            return this.field_70146_Z.nextInt(4) == 0 ? SoundEvents.field_187648_S : SoundEvents.field_187636_O;
         }
      } else {
         return null;
      }
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_187642_Q;
   }

   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187639_P;
   }

   protected float func_70599_aP() {
      return 0.4F;
   }

   public boolean func_70652_k(Entity p_70652_1_) {
      return p_70652_1_.func_70097_a(DamageSource.func_76358_a(this), 3.0F);
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if (this.func_180431_b(p_70097_1_)) {
         return false;
      } else {
         if (this.field_70911_d != null) {
            this.field_70911_d.func_75270_a(false);
         }

         return super.func_70097_a(p_70097_1_, p_70097_2_);
      }
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186402_J;
   }

   public boolean func_184645_a(EntityPlayer p_184645_1_, EnumHand p_184645_2_) {
      ItemStack itemstack = p_184645_1_.func_184586_b(p_184645_2_);
      if (this.func_70909_n()) {
         if (this.func_152114_e(p_184645_1_) && !this.field_70170_p.field_72995_K && !this.func_70877_b(itemstack)) {
            this.field_70911_d.func_75270_a(!this.func_70906_o());
         }
      } else if ((this.field_70914_e == null || this.field_70914_e.func_75277_f()) && itemstack.func_77973_b() == Items.field_151115_aP && p_184645_1_.func_70068_e(this) < 9.0D) {
         if (!p_184645_1_.field_71075_bZ.field_75098_d) {
            itemstack.func_190918_g(1);
         }

         if (!this.field_70170_p.field_72995_K) {
            if (this.field_70146_Z.nextInt(3) == 0) {
               this.func_193101_c(p_184645_1_);
               this.func_70912_b(1 + this.field_70170_p.field_73012_v.nextInt(3));
               this.func_70908_e(true);
               this.field_70911_d.func_75270_a(true);
               this.field_70170_p.func_72960_a(this, (byte)7);
            } else {
               this.func_70908_e(false);
               this.field_70170_p.func_72960_a(this, (byte)6);
            }
         }

         return true;
      }

      return super.func_184645_a(p_184645_1_, p_184645_2_);
   }

   public EntityOcelot func_90011_a(EntityAgeable p_90011_1_) {
      EntityOcelot entityocelot = new EntityOcelot(this.field_70170_p);
      if (this.func_70909_n()) {
         entityocelot.func_184754_b(this.func_184753_b());
         entityocelot.func_70903_f(true);
         entityocelot.func_70912_b(this.func_70913_u());
      }

      return entityocelot;
   }

   public boolean func_70877_b(ItemStack p_70877_1_) {
      return p_70877_1_.func_77973_b() == Items.field_151115_aP;
   }

   public boolean func_70878_b(EntityAnimal p_70878_1_) {
      if (p_70878_1_ == this) {
         return false;
      } else if (!this.func_70909_n()) {
         return false;
      } else if (!(p_70878_1_ instanceof EntityOcelot)) {
         return false;
      } else {
         EntityOcelot entityocelot = (EntityOcelot)p_70878_1_;
         if (!entityocelot.func_70909_n()) {
            return false;
         } else {
            return this.func_70880_s() && entityocelot.func_70880_s();
         }
      }
   }

   public int func_70913_u() {
      return ((Integer)this.field_70180_af.func_187225_a(field_184757_bz)).intValue();
   }

   public void func_70912_b(int p_70912_1_) {
      this.field_70180_af.func_187227_b(field_184757_bz, Integer.valueOf(p_70912_1_));
   }

   public boolean func_70601_bi() {
      return this.field_70170_p.field_73012_v.nextInt(3) != 0;
   }

   public boolean func_70058_J() {
      if (this.field_70170_p.func_72917_a(this.func_174813_aQ(), this) && this.field_70170_p.func_184144_a(this, this.func_174813_aQ()).isEmpty() && !this.field_70170_p.func_72953_d(this.func_174813_aQ())) {
         BlockPos blockpos = new BlockPos(this.field_70165_t, this.func_174813_aQ().field_72338_b, this.field_70161_v);
         if (blockpos.func_177956_o() < this.field_70170_p.func_181545_F()) {
            return false;
         }

         IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos.func_177977_b());
         Block block = iblockstate.func_177230_c();
         if (block == Blocks.field_150349_c || iblockstate.func_185904_a() == Material.field_151584_j) {
            return true;
         }
      }

      return false;
   }

   public String func_70005_c_() {
      if (this.func_145818_k_()) {
         return this.func_95999_t();
      } else {
         return this.func_70909_n() ? I18n.func_74838_a("entity.Cat.name") : super.func_70005_c_();
      }
   }

   protected void func_175544_ck() {
      if (this.field_175545_bm == null) {
         this.field_175545_bm = new EntityAIAvoidEntity<EntityPlayer>(this, EntityPlayer.class, 16.0F, 0.8D, 1.33D);
      }

      this.field_70714_bg.func_85156_a(this.field_175545_bm);
      if (!this.func_70909_n()) {
         this.field_70714_bg.func_75776_a(4, this.field_175545_bm);
      }

   }

   @Nullable
   public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, @Nullable IEntityLivingData p_180482_2_) {
      p_180482_2_ = super.func_180482_a(p_180482_1_, p_180482_2_);
      if (this.func_70913_u() == 0 && this.field_70170_p.field_73012_v.nextInt(7) == 0) {
         for(int i = 0; i < 2; ++i) {
            EntityOcelot entityocelot = new EntityOcelot(this.field_70170_p);
            entityocelot.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, 0.0F);
            entityocelot.func_70873_a(-24000);
            this.field_70170_p.func_72838_d(entityocelot);
         }
      }

      return p_180482_2_;
   }
}
