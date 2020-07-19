package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackData;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityFireworkRocket extends Entity {
   private static final DataParameter<ItemStack> field_184566_a = EntityDataManager.<ItemStack>func_187226_a(EntityFireworkRocket.class, DataSerializers.field_187196_f);
   private static final DataParameter<Integer> field_191512_b = EntityDataManager.<Integer>func_187226_a(EntityFireworkRocket.class, DataSerializers.field_187192_b);
   private int field_92056_a;
   private int field_92055_b;
   private EntityLivingBase field_191513_e;

   public EntityFireworkRocket(World p_i1762_1_) {
      super(p_i1762_1_);
      this.func_70105_a(0.25F, 0.25F);
   }

   protected void func_70088_a() {
      this.field_70180_af.func_187214_a(field_184566_a, ItemStack.field_190927_a);
      this.field_70180_af.func_187214_a(field_191512_b, Integer.valueOf(0));
   }

   public boolean func_70112_a(double p_70112_1_) {
      return p_70112_1_ < 4096.0D && !this.func_191511_j();
   }

   public boolean func_145770_h(double p_145770_1_, double p_145770_3_, double p_145770_5_) {
      return super.func_145770_h(p_145770_1_, p_145770_3_, p_145770_5_) && !this.func_191511_j();
   }

   public EntityFireworkRocket(World p_i1763_1_, double p_i1763_2_, double p_i1763_4_, double p_i1763_6_, ItemStack p_i1763_8_) {
      super(p_i1763_1_);
      this.field_92056_a = 0;
      this.func_70105_a(0.25F, 0.25F);
      this.func_70107_b(p_i1763_2_, p_i1763_4_, p_i1763_6_);
      int i = 1;
      if (!p_i1763_8_.func_190926_b() && p_i1763_8_.func_77942_o()) {
         this.field_70180_af.func_187227_b(field_184566_a, p_i1763_8_.func_77946_l());
         NBTTagCompound nbttagcompound = p_i1763_8_.func_77978_p();
         NBTTagCompound nbttagcompound1 = nbttagcompound.func_74775_l("Fireworks");
         i += nbttagcompound1.func_74771_c("Flight");
      }

      this.field_70159_w = this.field_70146_Z.nextGaussian() * 0.001D;
      this.field_70179_y = this.field_70146_Z.nextGaussian() * 0.001D;
      this.field_70181_x = 0.05D;
      this.field_92055_b = 10 * i + this.field_70146_Z.nextInt(6) + this.field_70146_Z.nextInt(7);
   }

   public EntityFireworkRocket(World p_i47367_1_, ItemStack p_i47367_2_, EntityLivingBase p_i47367_3_) {
      this(p_i47367_1_, p_i47367_3_.field_70165_t, p_i47367_3_.field_70163_u, p_i47367_3_.field_70161_v, p_i47367_2_);
      this.field_70180_af.func_187227_b(field_191512_b, Integer.valueOf(p_i47367_3_.func_145782_y()));
      this.field_191513_e = p_i47367_3_;
   }

   public void func_70016_h(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
      this.field_70159_w = p_70016_1_;
      this.field_70181_x = p_70016_3_;
      this.field_70179_y = p_70016_5_;
      if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
         float f = MathHelper.func_76133_a(p_70016_1_ * p_70016_1_ + p_70016_5_ * p_70016_5_);
         this.field_70177_z = (float)(MathHelper.func_181159_b(p_70016_1_, p_70016_5_) * 57.2957763671875D);
         this.field_70125_A = (float)(MathHelper.func_181159_b(p_70016_3_, (double)f) * 57.2957763671875D);
         this.field_70126_B = this.field_70177_z;
         this.field_70127_C = this.field_70125_A;
      }

   }

   public void func_70071_h_() {
      this.field_70142_S = this.field_70165_t;
      this.field_70137_T = this.field_70163_u;
      this.field_70136_U = this.field_70161_v;
      super.func_70071_h_();
      if (this.func_191511_j()) {
         if (this.field_191513_e == null) {
            Entity entity = this.field_70170_p.func_73045_a(((Integer)this.field_70180_af.func_187225_a(field_191512_b)).intValue());
            if (entity instanceof EntityLivingBase) {
               this.field_191513_e = (EntityLivingBase)entity;
            }
         }

         if (this.field_191513_e != null) {
            if (this.field_191513_e.func_184613_cA()) {
               Vec3d vec3d = this.field_191513_e.func_70040_Z();
               double d0 = 1.5D;
               double d1 = 0.1D;
               this.field_191513_e.field_70159_w += vec3d.field_72450_a * 0.1D + (vec3d.field_72450_a * 1.5D - this.field_191513_e.field_70159_w) * 0.5D;
               this.field_191513_e.field_70181_x += vec3d.field_72448_b * 0.1D + (vec3d.field_72448_b * 1.5D - this.field_191513_e.field_70181_x) * 0.5D;
               this.field_191513_e.field_70179_y += vec3d.field_72449_c * 0.1D + (vec3d.field_72449_c * 1.5D - this.field_191513_e.field_70179_y) * 0.5D;
            }

            this.func_70107_b(this.field_191513_e.field_70165_t, this.field_191513_e.field_70163_u, this.field_191513_e.field_70161_v);
            this.field_70159_w = this.field_191513_e.field_70159_w;
            this.field_70181_x = this.field_191513_e.field_70181_x;
            this.field_70179_y = this.field_191513_e.field_70179_y;
         }
      } else {
         this.field_70159_w *= 1.15D;
         this.field_70179_y *= 1.15D;
         this.field_70181_x += 0.04D;
         this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
      }

      float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
      this.field_70177_z = (float)(MathHelper.func_181159_b(this.field_70159_w, this.field_70179_y) * 57.2957763671875D);

      for(this.field_70125_A = (float)(MathHelper.func_181159_b(this.field_70181_x, (double)f) * 57.2957763671875D); this.field_70125_A - this.field_70127_C < -180.0F; this.field_70127_C -= 360.0F) {
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
      if (this.field_92056_a == 0 && !this.func_174814_R()) {
         this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187631_bo, SoundCategory.AMBIENT, 3.0F, 1.0F);
      }

      ++this.field_92056_a;
      if (this.field_70170_p.field_72995_K && this.field_92056_a % 2 < 2) {
         this.field_70170_p.func_175688_a(EnumParticleTypes.FIREWORKS_SPARK, this.field_70165_t, this.field_70163_u - 0.3D, this.field_70161_v, this.field_70146_Z.nextGaussian() * 0.05D, -this.field_70181_x * 0.5D, this.field_70146_Z.nextGaussian() * 0.05D);
      }

      if (!this.field_70170_p.field_72995_K && this.field_92056_a > this.field_92055_b) {
         this.field_70170_p.func_72960_a(this, (byte)17);
         this.func_191510_k();
         this.func_70106_y();
      }

   }

   private void func_191510_k() {
      float f = 0.0F;
      ItemStack itemstack = (ItemStack)this.field_70180_af.func_187225_a(field_184566_a);
      NBTTagCompound nbttagcompound = itemstack.func_190926_b() ? null : itemstack.func_179543_a("Fireworks");
      NBTTagList nbttaglist = nbttagcompound != null ? nbttagcompound.func_150295_c("Explosions", 10) : null;
      if (nbttaglist != null && !nbttaglist.func_82582_d()) {
         f = (float)(5 + nbttaglist.func_74745_c() * 2);
      }

      if (f > 0.0F) {
         if (this.field_191513_e != null) {
            this.field_191513_e.func_70097_a(DamageSource.field_191552_t, (float)(5 + nbttaglist.func_74745_c() * 2));
         }

         double d0 = 5.0D;
         Vec3d vec3d = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);

         for(EntityLivingBase entitylivingbase : this.field_70170_p.func_72872_a(EntityLivingBase.class, this.func_174813_aQ().func_186662_g(5.0D))) {
            if (entitylivingbase != this.field_191513_e && this.func_70068_e(entitylivingbase) <= 25.0D) {
               boolean flag = false;

               for(int i = 0; i < 2; ++i) {
                  RayTraceResult raytraceresult = this.field_70170_p.func_147447_a(vec3d, new Vec3d(entitylivingbase.field_70165_t, entitylivingbase.field_70163_u + (double)entitylivingbase.field_70131_O * 0.5D * (double)i, entitylivingbase.field_70161_v), false, true, false);
                  if (raytraceresult == null || raytraceresult.field_72313_a == RayTraceResult.Type.MISS) {
                     flag = true;
                     break;
                  }
               }

               if (flag) {
                  float f1 = f * (float)Math.sqrt((5.0D - (double)this.func_70032_d(entitylivingbase)) / 5.0D);
                  entitylivingbase.func_70097_a(DamageSource.field_191552_t, f1);
               }
            }
         }
      }

   }

   public boolean func_191511_j() {
      return ((Integer)this.field_70180_af.func_187225_a(field_191512_b)).intValue() > 0;
   }

   public void func_70103_a(byte p_70103_1_) {
      if (p_70103_1_ == 17 && this.field_70170_p.field_72995_K) {
         ItemStack itemstack = (ItemStack)this.field_70180_af.func_187225_a(field_184566_a);
         NBTTagCompound nbttagcompound = itemstack.func_190926_b() ? null : itemstack.func_179543_a("Fireworks");
         this.field_70170_p.func_92088_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70159_w, this.field_70181_x, this.field_70179_y, nbttagcompound);
      }

      super.func_70103_a(p_70103_1_);
   }

   public static void func_189656_a(DataFixer p_189656_0_) {
      p_189656_0_.func_188258_a(FixTypes.ENTITY, new ItemStackData(EntityFireworkRocket.class, new String[]{"FireworksItem"}));
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      p_70014_1_.func_74768_a("Life", this.field_92056_a);
      p_70014_1_.func_74768_a("LifeTime", this.field_92055_b);
      ItemStack itemstack = (ItemStack)this.field_70180_af.func_187225_a(field_184566_a);
      if (!itemstack.func_190926_b()) {
         p_70014_1_.func_74782_a("FireworksItem", itemstack.func_77955_b(new NBTTagCompound()));
      }

   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      this.field_92056_a = p_70037_1_.func_74762_e("Life");
      this.field_92055_b = p_70037_1_.func_74762_e("LifeTime");
      NBTTagCompound nbttagcompound = p_70037_1_.func_74775_l("FireworksItem");
      if (nbttagcompound != null) {
         ItemStack itemstack = new ItemStack(nbttagcompound);
         if (!itemstack.func_190926_b()) {
            this.field_70180_af.func_187227_b(field_184566_a, itemstack);
         }
      }

   }

   public boolean func_70075_an() {
      return false;
   }
}
