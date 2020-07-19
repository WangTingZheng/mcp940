package net.minecraft.entity.monster;

import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
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
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityCreeper extends EntityMob {
   private static final DataParameter<Integer> field_184713_a = EntityDataManager.<Integer>func_187226_a(EntityCreeper.class, DataSerializers.field_187192_b);
   private static final DataParameter<Boolean> field_184714_b = EntityDataManager.<Boolean>func_187226_a(EntityCreeper.class, DataSerializers.field_187198_h);
   private static final DataParameter<Boolean> field_184715_c = EntityDataManager.<Boolean>func_187226_a(EntityCreeper.class, DataSerializers.field_187198_h);
   private int field_70834_e;
   private int field_70833_d;
   private int field_82225_f = 30;
   private int field_82226_g = 3;
   private int field_175494_bm;

   public EntityCreeper(World p_i1733_1_) {
      super(p_i1733_1_);
      this.func_70105_a(0.6F, 1.7F);
   }

   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(1, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(2, new EntityAICreeperSwell(this));
      this.field_70714_bg.func_75776_a(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
      this.field_70714_bg.func_75776_a(4, new EntityAIAttackMelee(this, 1.0D, false));
      this.field_70714_bg.func_75776_a(5, new EntityAIWanderAvoidWater(this, 0.8D));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.field_70714_bg.func_75776_a(6, new EntityAILookIdle(this));
      this.field_70715_bh.func_75776_a(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
      this.field_70715_bh.func_75776_a(2, new EntityAIHurtByTarget(this, false, new Class[0]));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
   }

   public int func_82143_as() {
      return this.func_70638_az() == null ? 3 : 3 + (int)(this.func_110143_aJ() - 1.0F);
   }

   public void func_180430_e(float p_180430_1_, float p_180430_2_) {
      super.func_180430_e(p_180430_1_, p_180430_2_);
      this.field_70833_d = (int)((float)this.field_70833_d + p_180430_1_ * 1.5F);
      if (this.field_70833_d > this.field_82225_f - 5) {
         this.field_70833_d = this.field_82225_f - 5;
      }

   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184713_a, Integer.valueOf(-1));
      this.field_70180_af.func_187214_a(field_184714_b, Boolean.valueOf(false));
      this.field_70180_af.func_187214_a(field_184715_c, Boolean.valueOf(false));
   }

   public static void func_189762_b(DataFixer p_189762_0_) {
      EntityLiving.func_189752_a(p_189762_0_, EntityCreeper.class);
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      if (((Boolean)this.field_70180_af.func_187225_a(field_184714_b)).booleanValue()) {
         p_70014_1_.func_74757_a("powered", true);
      }

      p_70014_1_.func_74777_a("Fuse", (short)this.field_82225_f);
      p_70014_1_.func_74774_a("ExplosionRadius", (byte)this.field_82226_g);
      p_70014_1_.func_74757_a("ignited", this.func_146078_ca());
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      this.field_70180_af.func_187227_b(field_184714_b, Boolean.valueOf(p_70037_1_.func_74767_n("powered")));
      if (p_70037_1_.func_150297_b("Fuse", 99)) {
         this.field_82225_f = p_70037_1_.func_74765_d("Fuse");
      }

      if (p_70037_1_.func_150297_b("ExplosionRadius", 99)) {
         this.field_82226_g = p_70037_1_.func_74771_c("ExplosionRadius");
      }

      if (p_70037_1_.func_74767_n("ignited")) {
         this.func_146079_cb();
      }

   }

   public void func_70071_h_() {
      if (this.func_70089_S()) {
         this.field_70834_e = this.field_70833_d;
         if (this.func_146078_ca()) {
            this.func_70829_a(1);
         }

         int i = this.func_70832_p();
         if (i > 0 && this.field_70833_d == 0) {
            this.func_184185_a(SoundEvents.field_187572_ar, 1.0F, 0.5F);
         }

         this.field_70833_d += i;
         if (this.field_70833_d < 0) {
            this.field_70833_d = 0;
         }

         if (this.field_70833_d >= this.field_82225_f) {
            this.field_70833_d = this.field_82225_f;
            this.func_146077_cc();
         }
      }

      super.func_70071_h_();
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_187570_aq;
   }

   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187568_ap;
   }

   public void func_70645_a(DamageSource p_70645_1_) {
      super.func_70645_a(p_70645_1_);
      if (this.field_70170_p.func_82736_K().func_82766_b("doMobLoot")) {
         if (p_70645_1_.func_76346_g() instanceof EntitySkeleton) {
            int i = Item.func_150891_b(Items.field_151096_cd);
            int j = Item.func_150891_b(Items.field_151084_co);
            int k = i + this.field_70146_Z.nextInt(j - i + 1);
            this.func_145779_a(Item.func_150899_d(k), 1);
         } else if (p_70645_1_.func_76346_g() instanceof EntityCreeper && p_70645_1_.func_76346_g() != this && ((EntityCreeper)p_70645_1_.func_76346_g()).func_70830_n() && ((EntityCreeper)p_70645_1_.func_76346_g()).func_70650_aV()) {
            ((EntityCreeper)p_70645_1_.func_76346_g()).func_175493_co();
            this.func_70099_a(new ItemStack(Items.field_151144_bL, 1, 4), 0.0F);
         }
      }

   }

   public boolean func_70652_k(Entity p_70652_1_) {
      return true;
   }

   public boolean func_70830_n() {
      return ((Boolean)this.field_70180_af.func_187225_a(field_184714_b)).booleanValue();
   }

   public float func_70831_j(float p_70831_1_) {
      return ((float)this.field_70834_e + (float)(this.field_70833_d - this.field_70834_e) * p_70831_1_) / (float)(this.field_82225_f - 2);
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186434_p;
   }

   public int func_70832_p() {
      return ((Integer)this.field_70180_af.func_187225_a(field_184713_a)).intValue();
   }

   public void func_70829_a(int p_70829_1_) {
      this.field_70180_af.func_187227_b(field_184713_a, Integer.valueOf(p_70829_1_));
   }

   public void func_70077_a(EntityLightningBolt p_70077_1_) {
      super.func_70077_a(p_70077_1_);
      this.field_70180_af.func_187227_b(field_184714_b, Boolean.valueOf(true));
   }

   protected boolean func_184645_a(EntityPlayer p_184645_1_, EnumHand p_184645_2_) {
      ItemStack itemstack = p_184645_1_.func_184586_b(p_184645_2_);
      if (itemstack.func_77973_b() == Items.field_151033_d) {
         this.field_70170_p.func_184148_a(p_184645_1_, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187649_bu, this.func_184176_by(), 1.0F, this.field_70146_Z.nextFloat() * 0.4F + 0.8F);
         p_184645_1_.func_184609_a(p_184645_2_);
         if (!this.field_70170_p.field_72995_K) {
            this.func_146079_cb();
            itemstack.func_77972_a(1, p_184645_1_);
            return true;
         }
      }

      return super.func_184645_a(p_184645_1_, p_184645_2_);
   }

   private void func_146077_cc() {
      if (!this.field_70170_p.field_72995_K) {
         boolean flag = this.field_70170_p.func_82736_K().func_82766_b("mobGriefing");
         float f = this.func_70830_n() ? 2.0F : 1.0F;
         this.field_70729_aU = true;
         this.field_70170_p.func_72876_a(this, this.field_70165_t, this.field_70163_u, this.field_70161_v, (float)this.field_82226_g * f, flag);
         this.func_70106_y();
         this.func_190741_do();
      }

   }

   private void func_190741_do() {
      Collection<PotionEffect> collection = this.func_70651_bq();
      if (!collection.isEmpty()) {
         EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
         entityareaeffectcloud.func_184483_a(2.5F);
         entityareaeffectcloud.func_184495_b(-0.5F);
         entityareaeffectcloud.func_184485_d(10);
         entityareaeffectcloud.func_184486_b(entityareaeffectcloud.func_184489_o() / 2);
         entityareaeffectcloud.func_184487_c(-entityareaeffectcloud.func_184490_j() / (float)entityareaeffectcloud.func_184489_o());

         for(PotionEffect potioneffect : collection) {
            entityareaeffectcloud.func_184496_a(new PotionEffect(potioneffect));
         }

         this.field_70170_p.func_72838_d(entityareaeffectcloud);
      }

   }

   public boolean func_146078_ca() {
      return ((Boolean)this.field_70180_af.func_187225_a(field_184715_c)).booleanValue();
   }

   public void func_146079_cb() {
      this.field_70180_af.func_187227_b(field_184715_c, Boolean.valueOf(true));
   }

   public boolean func_70650_aV() {
      return this.field_175494_bm < 1 && this.field_70170_p.func_82736_K().func_82766_b("doMobLoot");
   }

   public void func_175493_co() {
      ++this.field_175494_bm;
   }
}
