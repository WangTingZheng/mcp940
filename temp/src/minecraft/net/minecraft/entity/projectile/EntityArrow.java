package net.minecraft.entity.projectile;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class EntityArrow extends Entity implements IProjectile {
   private static final Predicate<Entity> field_184553_f = Predicates.and(EntitySelectors.field_180132_d, EntitySelectors.field_94557_a, new Predicate<Entity>() {
      public boolean apply(@Nullable Entity p_apply_1_) {
         return p_apply_1_.func_70067_L();
      }
   });
   private static final DataParameter<Byte> field_184554_g = EntityDataManager.<Byte>func_187226_a(EntityArrow.class, DataSerializers.field_187191_a);
   private int field_145791_d;
   private int field_145792_e;
   private int field_145789_f;
   private Block field_145790_g;
   private int field_70253_h;
   protected boolean field_70254_i;
   protected int field_184552_b;
   public EntityArrow.PickupStatus field_70251_a;
   public int field_70249_b;
   public Entity field_70250_c;
   private int field_70252_j;
   private int field_70257_an;
   private double field_70255_ao;
   private int field_70256_ap;

   public EntityArrow(World p_i1753_1_) {
      super(p_i1753_1_);
      this.field_145791_d = -1;
      this.field_145792_e = -1;
      this.field_145789_f = -1;
      this.field_70251_a = EntityArrow.PickupStatus.DISALLOWED;
      this.field_70255_ao = 2.0D;
      this.func_70105_a(0.5F, 0.5F);
   }

   public EntityArrow(World p_i1754_1_, double p_i1754_2_, double p_i1754_4_, double p_i1754_6_) {
      this(p_i1754_1_);
      this.func_70107_b(p_i1754_2_, p_i1754_4_, p_i1754_6_);
   }

   public EntityArrow(World p_i46777_1_, EntityLivingBase p_i46777_2_) {
      this(p_i46777_1_, p_i46777_2_.field_70165_t, p_i46777_2_.field_70163_u + (double)p_i46777_2_.func_70047_e() - 0.10000000149011612D, p_i46777_2_.field_70161_v);
      this.field_70250_c = p_i46777_2_;
      if (p_i46777_2_ instanceof EntityPlayer) {
         this.field_70251_a = EntityArrow.PickupStatus.ALLOWED;
      }

   }

   public boolean func_70112_a(double p_70112_1_) {
      double d0 = this.func_174813_aQ().func_72320_b() * 10.0D;
      if (Double.isNaN(d0)) {
         d0 = 1.0D;
      }

      d0 = d0 * 64.0D * func_184183_bd();
      return p_70112_1_ < d0 * d0;
   }

   protected void func_70088_a() {
      this.field_70180_af.func_187214_a(field_184554_g, Byte.valueOf((byte)0));
   }

   public void func_184547_a(Entity p_184547_1_, float p_184547_2_, float p_184547_3_, float p_184547_4_, float p_184547_5_, float p_184547_6_) {
      float f = -MathHelper.func_76126_a(p_184547_3_ * 0.017453292F) * MathHelper.func_76134_b(p_184547_2_ * 0.017453292F);
      float f1 = -MathHelper.func_76126_a(p_184547_2_ * 0.017453292F);
      float f2 = MathHelper.func_76134_b(p_184547_3_ * 0.017453292F) * MathHelper.func_76134_b(p_184547_2_ * 0.017453292F);
      this.func_70186_c((double)f, (double)f1, (double)f2, p_184547_5_, p_184547_6_);
      this.field_70159_w += p_184547_1_.field_70159_w;
      this.field_70179_y += p_184547_1_.field_70179_y;
      if (!p_184547_1_.field_70122_E) {
         this.field_70181_x += p_184547_1_.field_70181_x;
      }

   }

   public void func_70186_c(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_) {
      float f = MathHelper.func_76133_a(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
      p_70186_1_ = p_70186_1_ / (double)f;
      p_70186_3_ = p_70186_3_ / (double)f;
      p_70186_5_ = p_70186_5_ / (double)f;
      p_70186_1_ = p_70186_1_ + this.field_70146_Z.nextGaussian() * 0.007499999832361937D * (double)p_70186_8_;
      p_70186_3_ = p_70186_3_ + this.field_70146_Z.nextGaussian() * 0.007499999832361937D * (double)p_70186_8_;
      p_70186_5_ = p_70186_5_ + this.field_70146_Z.nextGaussian() * 0.007499999832361937D * (double)p_70186_8_;
      p_70186_1_ = p_70186_1_ * (double)p_70186_7_;
      p_70186_3_ = p_70186_3_ * (double)p_70186_7_;
      p_70186_5_ = p_70186_5_ * (double)p_70186_7_;
      this.field_70159_w = p_70186_1_;
      this.field_70181_x = p_70186_3_;
      this.field_70179_y = p_70186_5_;
      float f1 = MathHelper.func_76133_a(p_70186_1_ * p_70186_1_ + p_70186_5_ * p_70186_5_);
      this.field_70177_z = (float)(MathHelper.func_181159_b(p_70186_1_, p_70186_5_) * 57.2957763671875D);
      this.field_70125_A = (float)(MathHelper.func_181159_b(p_70186_3_, (double)f1) * 57.2957763671875D);
      this.field_70126_B = this.field_70177_z;
      this.field_70127_C = this.field_70125_A;
      this.field_70252_j = 0;
   }

   public void func_180426_a(double p_180426_1_, double p_180426_3_, double p_180426_5_, float p_180426_7_, float p_180426_8_, int p_180426_9_, boolean p_180426_10_) {
      this.func_70107_b(p_180426_1_, p_180426_3_, p_180426_5_);
      this.func_70101_b(p_180426_7_, p_180426_8_);
   }

   public void func_70016_h(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
      this.field_70159_w = p_70016_1_;
      this.field_70181_x = p_70016_3_;
      this.field_70179_y = p_70016_5_;
      if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
         float f = MathHelper.func_76133_a(p_70016_1_ * p_70016_1_ + p_70016_5_ * p_70016_5_);
         this.field_70125_A = (float)(MathHelper.func_181159_b(p_70016_3_, (double)f) * 57.2957763671875D);
         this.field_70177_z = (float)(MathHelper.func_181159_b(p_70016_1_, p_70016_5_) * 57.2957763671875D);
         this.field_70127_C = this.field_70125_A;
         this.field_70126_B = this.field_70177_z;
         this.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
         this.field_70252_j = 0;
      }

   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
         float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
         this.field_70177_z = (float)(MathHelper.func_181159_b(this.field_70159_w, this.field_70179_y) * 57.2957763671875D);
         this.field_70125_A = (float)(MathHelper.func_181159_b(this.field_70181_x, (double)f) * 57.2957763671875D);
         this.field_70126_B = this.field_70177_z;
         this.field_70127_C = this.field_70125_A;
      }

      BlockPos blockpos = new BlockPos(this.field_145791_d, this.field_145792_e, this.field_145789_f);
      IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
      Block block = iblockstate.func_177230_c();
      if (iblockstate.func_185904_a() != Material.field_151579_a) {
         AxisAlignedBB axisalignedbb = iblockstate.func_185890_d(this.field_70170_p, blockpos);
         if (axisalignedbb != Block.field_185506_k && axisalignedbb.func_186670_a(blockpos).func_72318_a(new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v))) {
            this.field_70254_i = true;
         }
      }

      if (this.field_70249_b > 0) {
         --this.field_70249_b;
      }

      if (this.field_70254_i) {
         int j = block.func_176201_c(iblockstate);
         if ((block != this.field_145790_g || j != this.field_70253_h) && !this.field_70170_p.func_184143_b(this.func_174813_aQ().func_186662_g(0.05D))) {
            this.field_70254_i = false;
            this.field_70159_w *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
            this.field_70181_x *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
            this.field_70179_y *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
            this.field_70252_j = 0;
            this.field_70257_an = 0;
         } else {
            ++this.field_70252_j;
            if (this.field_70252_j >= 1200) {
               this.func_70106_y();
            }
         }

         ++this.field_184552_b;
      } else {
         this.field_184552_b = 0;
         ++this.field_70257_an;
         Vec3d vec3d1 = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
         Vec3d vec3d = new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
         RayTraceResult raytraceresult = this.field_70170_p.func_147447_a(vec3d1, vec3d, false, true, false);
         vec3d1 = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
         vec3d = new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
         if (raytraceresult != null) {
            vec3d = new Vec3d(raytraceresult.field_72307_f.field_72450_a, raytraceresult.field_72307_f.field_72448_b, raytraceresult.field_72307_f.field_72449_c);
         }

         Entity entity = this.func_184551_a(vec3d1, vec3d);
         if (entity != null) {
            raytraceresult = new RayTraceResult(entity);
         }

         if (raytraceresult != null && raytraceresult.field_72308_g instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)raytraceresult.field_72308_g;
            if (this.field_70250_c instanceof EntityPlayer && !((EntityPlayer)this.field_70250_c).func_96122_a(entityplayer)) {
               raytraceresult = null;
            }
         }

         if (raytraceresult != null) {
            this.func_184549_a(raytraceresult);
         }

         if (this.func_70241_g()) {
            for(int k = 0; k < 4; ++k) {
               this.field_70170_p.func_175688_a(EnumParticleTypes.CRIT, this.field_70165_t + this.field_70159_w * (double)k / 4.0D, this.field_70163_u + this.field_70181_x * (double)k / 4.0D, this.field_70161_v + this.field_70179_y * (double)k / 4.0D, -this.field_70159_w, -this.field_70181_x + 0.2D, -this.field_70179_y);
            }
         }

         this.field_70165_t += this.field_70159_w;
         this.field_70163_u += this.field_70181_x;
         this.field_70161_v += this.field_70179_y;
         float f4 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
         this.field_70177_z = (float)(MathHelper.func_181159_b(this.field_70159_w, this.field_70179_y) * 57.2957763671875D);

         for(this.field_70125_A = (float)(MathHelper.func_181159_b(this.field_70181_x, (double)f4) * 57.2957763671875D); this.field_70125_A - this.field_70127_C < -180.0F; this.field_70127_C -= 360.0F) {
            ;
         }

         while(this.field_70125_A - this.field_70127_C >= 180.0F) {
            this.field_70127_C += 360.0F;
         }

         while(this.field_70177_z - this.field_70126_B < -180.0F) {
            this.field_70126_B -= 360.0F;
         }

         while(this.field_70177_z - this.field_70126_B >= 180.0F) {
            this.field_70126_B += 360.0F;
         }

         this.field_70125_A = this.field_70127_C + (this.field_70125_A - this.field_70127_C) * 0.2F;
         this.field_70177_z = this.field_70126_B + (this.field_70177_z - this.field_70126_B) * 0.2F;
         float f1 = 0.99F;
         float f2 = 0.05F;
         if (this.func_70090_H()) {
            for(int i = 0; i < 4; ++i) {
               float f3 = 0.25F;
               this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_BUBBLE, this.field_70165_t - this.field_70159_w * 0.25D, this.field_70163_u - this.field_70181_x * 0.25D, this.field_70161_v - this.field_70179_y * 0.25D, this.field_70159_w, this.field_70181_x, this.field_70179_y);
            }

            f1 = 0.6F;
         }

         if (this.func_70026_G()) {
            this.func_70066_B();
         }

         this.field_70159_w *= (double)f1;
         this.field_70181_x *= (double)f1;
         this.field_70179_y *= (double)f1;
         if (!this.func_189652_ae()) {
            this.field_70181_x -= 0.05000000074505806D;
         }

         this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
         this.func_145775_I();
      }
   }

   protected void func_184549_a(RayTraceResult p_184549_1_) {
      Entity entity = p_184549_1_.field_72308_g;
      if (entity != null) {
         float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
         int i = MathHelper.func_76143_f((double)f * this.field_70255_ao);
         if (this.func_70241_g()) {
            i += this.field_70146_Z.nextInt(i / 2 + 2);
         }

         DamageSource damagesource;
         if (this.field_70250_c == null) {
            damagesource = DamageSource.func_76353_a(this, this);
         } else {
            damagesource = DamageSource.func_76353_a(this, this.field_70250_c);
         }

         if (this.func_70027_ad() && !(entity instanceof EntityEnderman)) {
            entity.func_70015_d(5);
         }

         if (entity.func_70097_a(damagesource, (float)i)) {
            if (entity instanceof EntityLivingBase) {
               EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
               if (!this.field_70170_p.field_72995_K) {
                  entitylivingbase.func_85034_r(entitylivingbase.func_85035_bI() + 1);
               }

               if (this.field_70256_ap > 0) {
                  float f1 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
                  if (f1 > 0.0F) {
                     entitylivingbase.func_70024_g(this.field_70159_w * (double)this.field_70256_ap * 0.6000000238418579D / (double)f1, 0.1D, this.field_70179_y * (double)this.field_70256_ap * 0.6000000238418579D / (double)f1);
                  }
               }

               if (this.field_70250_c instanceof EntityLivingBase) {
                  EnchantmentHelper.func_151384_a(entitylivingbase, this.field_70250_c);
                  EnchantmentHelper.func_151385_b((EntityLivingBase)this.field_70250_c, entitylivingbase);
               }

               this.func_184548_a(entitylivingbase);
               if (this.field_70250_c != null && entitylivingbase != this.field_70250_c && entitylivingbase instanceof EntityPlayer && this.field_70250_c instanceof EntityPlayerMP) {
                  ((EntityPlayerMP)this.field_70250_c).field_71135_a.func_147359_a(new SPacketChangeGameState(6, 0.0F));
               }
            }

            this.func_184185_a(SoundEvents.field_187731_t, 1.0F, 1.2F / (this.field_70146_Z.nextFloat() * 0.2F + 0.9F));
            if (!(entity instanceof EntityEnderman)) {
               this.func_70106_y();
            }
         } else {
            this.field_70159_w *= -0.10000000149011612D;
            this.field_70181_x *= -0.10000000149011612D;
            this.field_70179_y *= -0.10000000149011612D;
            this.field_70177_z += 180.0F;
            this.field_70126_B += 180.0F;
            this.field_70257_an = 0;
            if (!this.field_70170_p.field_72995_K && this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y < 0.0010000000474974513D) {
               if (this.field_70251_a == EntityArrow.PickupStatus.ALLOWED) {
                  this.func_70099_a(this.func_184550_j(), 0.1F);
               }

               this.func_70106_y();
            }
         }
      } else {
         BlockPos blockpos = p_184549_1_.func_178782_a();
         this.field_145791_d = blockpos.func_177958_n();
         this.field_145792_e = blockpos.func_177956_o();
         this.field_145789_f = blockpos.func_177952_p();
         IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
         this.field_145790_g = iblockstate.func_177230_c();
         this.field_70253_h = this.field_145790_g.func_176201_c(iblockstate);
         this.field_70159_w = (double)((float)(p_184549_1_.field_72307_f.field_72450_a - this.field_70165_t));
         this.field_70181_x = (double)((float)(p_184549_1_.field_72307_f.field_72448_b - this.field_70163_u));
         this.field_70179_y = (double)((float)(p_184549_1_.field_72307_f.field_72449_c - this.field_70161_v));
         float f2 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
         this.field_70165_t -= this.field_70159_w / (double)f2 * 0.05000000074505806D;
         this.field_70163_u -= this.field_70181_x / (double)f2 * 0.05000000074505806D;
         this.field_70161_v -= this.field_70179_y / (double)f2 * 0.05000000074505806D;
         this.func_184185_a(SoundEvents.field_187731_t, 1.0F, 1.2F / (this.field_70146_Z.nextFloat() * 0.2F + 0.9F));
         this.field_70254_i = true;
         this.field_70249_b = 7;
         this.func_70243_d(false);
         if (iblockstate.func_185904_a() != Material.field_151579_a) {
            this.field_145790_g.func_180634_a(this.field_70170_p, blockpos, iblockstate, this);
         }
      }

   }

   public void func_70091_d(MoverType p_70091_1_, double p_70091_2_, double p_70091_4_, double p_70091_6_) {
      super.func_70091_d(p_70091_1_, p_70091_2_, p_70091_4_, p_70091_6_);
      if (this.field_70254_i) {
         this.field_145791_d = MathHelper.func_76128_c(this.field_70165_t);
         this.field_145792_e = MathHelper.func_76128_c(this.field_70163_u);
         this.field_145789_f = MathHelper.func_76128_c(this.field_70161_v);
      }

   }

   protected void func_184548_a(EntityLivingBase p_184548_1_) {
   }

   @Nullable
   protected Entity func_184551_a(Vec3d p_184551_1_, Vec3d p_184551_2_) {
      Entity entity = null;
      List<Entity> list = this.field_70170_p.func_175674_a(this, this.func_174813_aQ().func_72321_a(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_186662_g(1.0D), field_184553_f);
      double d0 = 0.0D;

      for(int i = 0; i < list.size(); ++i) {
         Entity entity1 = list.get(i);
         if (entity1 != this.field_70250_c || this.field_70257_an >= 5) {
            AxisAlignedBB axisalignedbb = entity1.func_174813_aQ().func_186662_g(0.30000001192092896D);
            RayTraceResult raytraceresult = axisalignedbb.func_72327_a(p_184551_1_, p_184551_2_);
            if (raytraceresult != null) {
               double d1 = p_184551_1_.func_72436_e(raytraceresult.field_72307_f);
               if (d1 < d0 || d0 == 0.0D) {
                  entity = entity1;
                  d0 = d1;
               }
            }
         }
      }

      return entity;
   }

   public static void func_189657_a(DataFixer p_189657_0_, String p_189657_1_) {
   }

   public static void func_189658_a(DataFixer p_189658_0_) {
      func_189657_a(p_189658_0_, "Arrow");
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      p_70014_1_.func_74768_a("xTile", this.field_145791_d);
      p_70014_1_.func_74768_a("yTile", this.field_145792_e);
      p_70014_1_.func_74768_a("zTile", this.field_145789_f);
      p_70014_1_.func_74777_a("life", (short)this.field_70252_j);
      ResourceLocation resourcelocation = Block.field_149771_c.func_177774_c(this.field_145790_g);
      p_70014_1_.func_74778_a("inTile", resourcelocation == null ? "" : resourcelocation.toString());
      p_70014_1_.func_74774_a("inData", (byte)this.field_70253_h);
      p_70014_1_.func_74774_a("shake", (byte)this.field_70249_b);
      p_70014_1_.func_74774_a("inGround", (byte)(this.field_70254_i ? 1 : 0));
      p_70014_1_.func_74774_a("pickup", (byte)this.field_70251_a.ordinal());
      p_70014_1_.func_74780_a("damage", this.field_70255_ao);
      p_70014_1_.func_74757_a("crit", this.func_70241_g());
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      this.field_145791_d = p_70037_1_.func_74762_e("xTile");
      this.field_145792_e = p_70037_1_.func_74762_e("yTile");
      this.field_145789_f = p_70037_1_.func_74762_e("zTile");
      this.field_70252_j = p_70037_1_.func_74765_d("life");
      if (p_70037_1_.func_150297_b("inTile", 8)) {
         this.field_145790_g = Block.func_149684_b(p_70037_1_.func_74779_i("inTile"));
      } else {
         this.field_145790_g = Block.func_149729_e(p_70037_1_.func_74771_c("inTile") & 255);
      }

      this.field_70253_h = p_70037_1_.func_74771_c("inData") & 255;
      this.field_70249_b = p_70037_1_.func_74771_c("shake") & 255;
      this.field_70254_i = p_70037_1_.func_74771_c("inGround") == 1;
      if (p_70037_1_.func_150297_b("damage", 99)) {
         this.field_70255_ao = p_70037_1_.func_74769_h("damage");
      }

      if (p_70037_1_.func_150297_b("pickup", 99)) {
         this.field_70251_a = EntityArrow.PickupStatus.func_188795_a(p_70037_1_.func_74771_c("pickup"));
      } else if (p_70037_1_.func_150297_b("player", 99)) {
         this.field_70251_a = p_70037_1_.func_74767_n("player") ? EntityArrow.PickupStatus.ALLOWED : EntityArrow.PickupStatus.DISALLOWED;
      }

      this.func_70243_d(p_70037_1_.func_74767_n("crit"));
   }

   public void func_70100_b_(EntityPlayer p_70100_1_) {
      if (!this.field_70170_p.field_72995_K && this.field_70254_i && this.field_70249_b <= 0) {
         boolean flag = this.field_70251_a == EntityArrow.PickupStatus.ALLOWED || this.field_70251_a == EntityArrow.PickupStatus.CREATIVE_ONLY && p_70100_1_.field_71075_bZ.field_75098_d;
         if (this.field_70251_a == EntityArrow.PickupStatus.ALLOWED && !p_70100_1_.field_71071_by.func_70441_a(this.func_184550_j())) {
            flag = false;
         }

         if (flag) {
            p_70100_1_.func_71001_a(this, 1);
            this.func_70106_y();
         }

      }
   }

   protected abstract ItemStack func_184550_j();

   protected boolean func_70041_e_() {
      return false;
   }

   public void func_70239_b(double p_70239_1_) {
      this.field_70255_ao = p_70239_1_;
   }

   public double func_70242_d() {
      return this.field_70255_ao;
   }

   public void func_70240_a(int p_70240_1_) {
      this.field_70256_ap = p_70240_1_;
   }

   public boolean func_70075_an() {
      return false;
   }

   public float func_70047_e() {
      return 0.0F;
   }

   public void func_70243_d(boolean p_70243_1_) {
      byte b0 = ((Byte)this.field_70180_af.func_187225_a(field_184554_g)).byteValue();
      if (p_70243_1_) {
         this.field_70180_af.func_187227_b(field_184554_g, Byte.valueOf((byte)(b0 | 1)));
      } else {
         this.field_70180_af.func_187227_b(field_184554_g, Byte.valueOf((byte)(b0 & -2)));
      }

   }

   public boolean func_70241_g() {
      byte b0 = ((Byte)this.field_70180_af.func_187225_a(field_184554_g)).byteValue();
      return (b0 & 1) != 0;
   }

   public void func_190547_a(EntityLivingBase p_190547_1_, float p_190547_2_) {
      int i = EnchantmentHelper.func_185284_a(Enchantments.field_185309_u, p_190547_1_);
      int j = EnchantmentHelper.func_185284_a(Enchantments.field_185310_v, p_190547_1_);
      this.func_70239_b((double)(p_190547_2_ * 2.0F) + this.field_70146_Z.nextGaussian() * 0.25D + (double)((float)this.field_70170_p.func_175659_aa().func_151525_a() * 0.11F));
      if (i > 0) {
         this.func_70239_b(this.func_70242_d() + (double)i * 0.5D + 0.5D);
      }

      if (j > 0) {
         this.func_70240_a(j);
      }

      if (EnchantmentHelper.func_185284_a(Enchantments.field_185311_w, p_190547_1_) > 0) {
         this.func_70015_d(100);
      }

   }

   public static enum PickupStatus {
      DISALLOWED,
      ALLOWED,
      CREATIVE_ONLY;

      public static EntityArrow.PickupStatus func_188795_a(int p_188795_0_) {
         if (p_188795_0_ < 0 || p_188795_0_ > values().length) {
            p_188795_0_ = 0;
         }

         return values()[p_188795_0_];
      }
   }
}
