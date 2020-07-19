package net.minecraft.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityVex extends EntityMob {
   protected static final DataParameter<Byte> field_190664_a = EntityDataManager.<Byte>func_187226_a(EntityVex.class, DataSerializers.field_187191_a);
   private EntityLiving field_190665_b;
   @Nullable
   private BlockPos field_190666_c;
   private boolean field_190667_bw;
   private int field_190668_bx;

   public EntityVex(World p_i47280_1_) {
      super(p_i47280_1_);
      this.field_70178_ae = true;
      this.field_70765_h = new EntityVex.AIMoveControl(this);
      this.func_70105_a(0.4F, 0.8F);
      this.field_70728_aV = 3;
   }

   public void func_70091_d(MoverType p_70091_1_, double p_70091_2_, double p_70091_4_, double p_70091_6_) {
      super.func_70091_d(p_70091_1_, p_70091_2_, p_70091_4_, p_70091_6_);
      this.func_145775_I();
   }

   public void func_70071_h_() {
      this.field_70145_X = true;
      super.func_70071_h_();
      this.field_70145_X = false;
      this.func_189654_d(true);
      if (this.field_190667_bw && --this.field_190668_bx <= 0) {
         this.field_190668_bx = 20;
         this.func_70097_a(DamageSource.field_76366_f, 1.0F);
      }

   }

   protected void func_184651_r() {
      super.func_184651_r();
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(4, new EntityVex.AIChargeAttack());
      this.field_70714_bg.func_75776_a(8, new EntityVex.AIMoveRandom());
      this.field_70714_bg.func_75776_a(9, new EntityAIWatchClosest(this, EntityPlayer.class, 3.0F, 1.0F));
      this.field_70714_bg.func_75776_a(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
      this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, true, new Class[]{EntityVex.class}));
      this.field_70715_bh.func_75776_a(2, new EntityVex.AICopyOwnerTarget(this));
      this.field_70715_bh.func_75776_a(3, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(14.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(4.0D);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_190664_a, Byte.valueOf((byte)0));
   }

   public static void func_190663_b(DataFixer p_190663_0_) {
      EntityLiving.func_189752_a(p_190663_0_, EntityVex.class);
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      if (p_70037_1_.func_74764_b("BoundX")) {
         this.field_190666_c = new BlockPos(p_70037_1_.func_74762_e("BoundX"), p_70037_1_.func_74762_e("BoundY"), p_70037_1_.func_74762_e("BoundZ"));
      }

      if (p_70037_1_.func_74764_b("LifeTicks")) {
         this.func_190653_a(p_70037_1_.func_74762_e("LifeTicks"));
      }

   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      if (this.field_190666_c != null) {
         p_70014_1_.func_74768_a("BoundX", this.field_190666_c.func_177958_n());
         p_70014_1_.func_74768_a("BoundY", this.field_190666_c.func_177956_o());
         p_70014_1_.func_74768_a("BoundZ", this.field_190666_c.func_177952_p());
      }

      if (this.field_190667_bw) {
         p_70014_1_.func_74768_a("LifeTicks", this.field_190668_bx);
      }

   }

   public EntityLiving func_190645_o() {
      return this.field_190665_b;
   }

   @Nullable
   public BlockPos func_190646_di() {
      return this.field_190666_c;
   }

   public void func_190651_g(@Nullable BlockPos p_190651_1_) {
      this.field_190666_c = p_190651_1_;
   }

   private boolean func_190656_b(int p_190656_1_) {
      int i = ((Byte)this.field_70180_af.func_187225_a(field_190664_a)).byteValue();
      return (i & p_190656_1_) != 0;
   }

   private void func_190660_a(int p_190660_1_, boolean p_190660_2_) {
      int i = ((Byte)this.field_70180_af.func_187225_a(field_190664_a)).byteValue();
      if (p_190660_2_) {
         i = i | p_190660_1_;
      } else {
         i = i & ~p_190660_1_;
      }

      this.field_70180_af.func_187227_b(field_190664_a, Byte.valueOf((byte)(i & 255)));
   }

   public boolean func_190647_dj() {
      return this.func_190656_b(1);
   }

   public void func_190648_a(boolean p_190648_1_) {
      this.func_190660_a(1, p_190648_1_);
   }

   public void func_190658_a(EntityLiving p_190658_1_) {
      this.field_190665_b = p_190658_1_;
   }

   public void func_190653_a(int p_190653_1_) {
      this.field_190667_bw = true;
      this.field_190668_bx = p_190653_1_;
   }

   protected SoundEvent func_184639_G() {
      return SoundEvents.field_191264_hc;
   }

   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_191266_he;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_191267_hf;
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_191188_ax;
   }

   public int func_70070_b() {
      return 15728880;
   }

   public float func_70013_c() {
      return 1.0F;
   }

   @Nullable
   public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, @Nullable IEntityLivingData p_180482_2_) {
      this.func_180481_a(p_180482_1_);
      this.func_180483_b(p_180482_1_);
      return super.func_180482_a(p_180482_1_, p_180482_2_);
   }

   protected void func_180481_a(DifficultyInstance p_180481_1_) {
      this.func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151040_l));
      this.func_184642_a(EntityEquipmentSlot.MAINHAND, 0.0F);
   }

   class AIChargeAttack extends EntityAIBase {
      public AIChargeAttack() {
         this.func_75248_a(1);
      }

      public boolean func_75250_a() {
         if (EntityVex.this.func_70638_az() != null && !EntityVex.this.func_70605_aq().func_75640_a() && EntityVex.this.field_70146_Z.nextInt(7) == 0) {
            return EntityVex.this.func_70068_e(EntityVex.this.func_70638_az()) > 4.0D;
         } else {
            return false;
         }
      }

      public boolean func_75253_b() {
         return EntityVex.this.func_70605_aq().func_75640_a() && EntityVex.this.func_190647_dj() && EntityVex.this.func_70638_az() != null && EntityVex.this.func_70638_az().func_70089_S();
      }

      public void func_75249_e() {
         EntityLivingBase entitylivingbase = EntityVex.this.func_70638_az();
         Vec3d vec3d = entitylivingbase.func_174824_e(1.0F);
         EntityVex.this.field_70765_h.func_75642_a(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c, 1.0D);
         EntityVex.this.func_190648_a(true);
         EntityVex.this.func_184185_a(SoundEvents.field_191265_hd, 1.0F, 1.0F);
      }

      public void func_75251_c() {
         EntityVex.this.func_190648_a(false);
      }

      public void func_75246_d() {
         EntityLivingBase entitylivingbase = EntityVex.this.func_70638_az();
         if (EntityVex.this.func_174813_aQ().func_72326_a(entitylivingbase.func_174813_aQ())) {
            EntityVex.this.func_70652_k(entitylivingbase);
            EntityVex.this.func_190648_a(false);
         } else {
            double d0 = EntityVex.this.func_70068_e(entitylivingbase);
            if (d0 < 9.0D) {
               Vec3d vec3d = entitylivingbase.func_174824_e(1.0F);
               EntityVex.this.field_70765_h.func_75642_a(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c, 1.0D);
            }
         }

      }
   }

   class AICopyOwnerTarget extends EntityAITarget {
      public AICopyOwnerTarget(EntityCreature p_i47231_2_) {
         super(p_i47231_2_, false);
      }

      public boolean func_75250_a() {
         return EntityVex.this.field_190665_b != null && EntityVex.this.field_190665_b.func_70638_az() != null && this.func_75296_a(EntityVex.this.field_190665_b.func_70638_az(), false);
      }

      public void func_75249_e() {
         EntityVex.this.func_70624_b(EntityVex.this.field_190665_b.func_70638_az());
         super.func_75249_e();
      }
   }

   class AIMoveControl extends EntityMoveHelper {
      public AIMoveControl(EntityVex p_i47230_2_) {
         super(p_i47230_2_);
      }

      public void func_75641_c() {
         if (this.field_188491_h == EntityMoveHelper.Action.MOVE_TO) {
            double d0 = this.field_75646_b - EntityVex.this.field_70165_t;
            double d1 = this.field_75647_c - EntityVex.this.field_70163_u;
            double d2 = this.field_75644_d - EntityVex.this.field_70161_v;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            d3 = (double)MathHelper.func_76133_a(d3);
            if (d3 < EntityVex.this.func_174813_aQ().func_72320_b()) {
               this.field_188491_h = EntityMoveHelper.Action.WAIT;
               EntityVex.this.field_70159_w *= 0.5D;
               EntityVex.this.field_70181_x *= 0.5D;
               EntityVex.this.field_70179_y *= 0.5D;
            } else {
               EntityVex.this.field_70159_w += d0 / d3 * 0.05D * this.field_75645_e;
               EntityVex.this.field_70181_x += d1 / d3 * 0.05D * this.field_75645_e;
               EntityVex.this.field_70179_y += d2 / d3 * 0.05D * this.field_75645_e;
               if (EntityVex.this.func_70638_az() == null) {
                  EntityVex.this.field_70177_z = -((float)MathHelper.func_181159_b(EntityVex.this.field_70159_w, EntityVex.this.field_70179_y)) * 57.295776F;
                  EntityVex.this.field_70761_aq = EntityVex.this.field_70177_z;
               } else {
                  double d4 = EntityVex.this.func_70638_az().field_70165_t - EntityVex.this.field_70165_t;
                  double d5 = EntityVex.this.func_70638_az().field_70161_v - EntityVex.this.field_70161_v;
                  EntityVex.this.field_70177_z = -((float)MathHelper.func_181159_b(d4, d5)) * 57.295776F;
                  EntityVex.this.field_70761_aq = EntityVex.this.field_70177_z;
               }
            }

         }
      }
   }

   class AIMoveRandom extends EntityAIBase {
      public AIMoveRandom() {
         this.func_75248_a(1);
      }

      public boolean func_75250_a() {
         return !EntityVex.this.func_70605_aq().func_75640_a() && EntityVex.this.field_70146_Z.nextInt(7) == 0;
      }

      public boolean func_75253_b() {
         return false;
      }

      public void func_75246_d() {
         BlockPos blockpos = EntityVex.this.func_190646_di();
         if (blockpos == null) {
            blockpos = new BlockPos(EntityVex.this);
         }

         for(int i = 0; i < 3; ++i) {
            BlockPos blockpos1 = blockpos.func_177982_a(EntityVex.this.field_70146_Z.nextInt(15) - 7, EntityVex.this.field_70146_Z.nextInt(11) - 5, EntityVex.this.field_70146_Z.nextInt(15) - 7);
            if (EntityVex.this.field_70170_p.func_175623_d(blockpos1)) {
               EntityVex.this.field_70765_h.func_75642_a((double)blockpos1.func_177958_n() + 0.5D, (double)blockpos1.func_177956_o() + 0.5D, (double)blockpos1.func_177952_p() + 0.5D, 0.25D);
               if (EntityVex.this.func_70638_az() == null) {
                  EntityVex.this.func_70671_ap().func_75650_a((double)blockpos1.func_177958_n() + 0.5D, (double)blockpos1.func_177956_o() + 0.5D, (double)blockpos1.func_177952_p() + 0.5D, 180.0F, 20.0F);
               }
               break;
            }
         }

      }
   }
}
