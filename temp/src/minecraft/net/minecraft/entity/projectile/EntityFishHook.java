package net.minecraft.entity.projectile;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityFishHook extends Entity {
   private static final DataParameter<Integer> field_184528_c = EntityDataManager.<Integer>func_187226_a(EntityFishHook.class, DataSerializers.field_187192_b);
   private boolean field_146051_au;
   private int field_146049_av;
   private EntityPlayer field_146042_b;
   private int field_146047_aw;
   private int field_146045_ax;
   private int field_146040_ay;
   private int field_146038_az;
   private float field_146054_aA;
   public Entity field_146043_c;
   private EntityFishHook.State field_190627_av = EntityFishHook.State.FLYING;
   private int field_191518_aw;
   private int field_191519_ax;

   public EntityFishHook(World p_i47290_1_, EntityPlayer p_i47290_2_, double p_i47290_3_, double p_i47290_5_, double p_i47290_7_) {
      super(p_i47290_1_);
      this.func_190626_a(p_i47290_2_);
      this.func_70107_b(p_i47290_3_, p_i47290_5_, p_i47290_7_);
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
   }

   public EntityFishHook(World p_i1766_1_, EntityPlayer p_i1766_2_) {
      super(p_i1766_1_);
      this.func_190626_a(p_i1766_2_);
      this.func_190620_n();
   }

   private void func_190626_a(EntityPlayer p_190626_1_) {
      this.func_70105_a(0.25F, 0.25F);
      this.field_70158_ak = true;
      this.field_146042_b = p_190626_1_;
      this.field_146042_b.field_71104_cf = this;
   }

   public void func_191516_a(int p_191516_1_) {
      this.field_191519_ax = p_191516_1_;
   }

   public void func_191517_b(int p_191517_1_) {
      this.field_191518_aw = p_191517_1_;
   }

   private void func_190620_n() {
      float f = this.field_146042_b.field_70127_C + (this.field_146042_b.field_70125_A - this.field_146042_b.field_70127_C);
      float f1 = this.field_146042_b.field_70126_B + (this.field_146042_b.field_70177_z - this.field_146042_b.field_70126_B);
      float f2 = MathHelper.func_76134_b(-f1 * 0.017453292F - 3.1415927F);
      float f3 = MathHelper.func_76126_a(-f1 * 0.017453292F - 3.1415927F);
      float f4 = -MathHelper.func_76134_b(-f * 0.017453292F);
      float f5 = MathHelper.func_76126_a(-f * 0.017453292F);
      double d0 = this.field_146042_b.field_70169_q + (this.field_146042_b.field_70165_t - this.field_146042_b.field_70169_q) - (double)f3 * 0.3D;
      double d1 = this.field_146042_b.field_70167_r + (this.field_146042_b.field_70163_u - this.field_146042_b.field_70167_r) + (double)this.field_146042_b.func_70047_e();
      double d2 = this.field_146042_b.field_70166_s + (this.field_146042_b.field_70161_v - this.field_146042_b.field_70166_s) - (double)f2 * 0.3D;
      this.func_70012_b(d0, d1, d2, f1, f);
      this.field_70159_w = (double)(-f3);
      this.field_70181_x = (double)MathHelper.func_76131_a(-(f5 / f4), -5.0F, 5.0F);
      this.field_70179_y = (double)(-f2);
      float f6 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
      this.field_70159_w *= 0.6D / (double)f6 + 0.5D + this.field_70146_Z.nextGaussian() * 0.0045D;
      this.field_70181_x *= 0.6D / (double)f6 + 0.5D + this.field_70146_Z.nextGaussian() * 0.0045D;
      this.field_70179_y *= 0.6D / (double)f6 + 0.5D + this.field_70146_Z.nextGaussian() * 0.0045D;
      float f7 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
      this.field_70177_z = (float)(MathHelper.func_181159_b(this.field_70159_w, this.field_70179_y) * 57.2957763671875D);
      this.field_70125_A = (float)(MathHelper.func_181159_b(this.field_70181_x, (double)f7) * 57.2957763671875D);
      this.field_70126_B = this.field_70177_z;
      this.field_70127_C = this.field_70125_A;
   }

   protected void func_70088_a() {
      this.func_184212_Q().func_187214_a(field_184528_c, Integer.valueOf(0));
   }

   public void func_184206_a(DataParameter<?> p_184206_1_) {
      if (field_184528_c.equals(p_184206_1_)) {
         int i = ((Integer)this.func_184212_Q().func_187225_a(field_184528_c)).intValue();
         this.field_146043_c = i > 0 ? this.field_70170_p.func_73045_a(i - 1) : null;
      }

      super.func_184206_a(p_184206_1_);
   }

   public boolean func_70112_a(double p_70112_1_) {
      double d0 = 64.0D;
      return p_70112_1_ < 4096.0D;
   }

   public void func_180426_a(double p_180426_1_, double p_180426_3_, double p_180426_5_, float p_180426_7_, float p_180426_8_, int p_180426_9_, boolean p_180426_10_) {
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_146042_b == null) {
         this.func_70106_y();
      } else if (this.field_70170_p.field_72995_K || !this.func_190625_o()) {
         if (this.field_146051_au) {
            ++this.field_146049_av;
            if (this.field_146049_av >= 1200) {
               this.func_70106_y();
               return;
            }
         }

         float f = 0.0F;
         BlockPos blockpos = new BlockPos(this);
         IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
         if (iblockstate.func_185904_a() == Material.field_151586_h) {
            f = BlockLiquid.func_190973_f(iblockstate, this.field_70170_p, blockpos);
         }

         if (this.field_190627_av == EntityFishHook.State.FLYING) {
            if (this.field_146043_c != null) {
               this.field_70159_w = 0.0D;
               this.field_70181_x = 0.0D;
               this.field_70179_y = 0.0D;
               this.field_190627_av = EntityFishHook.State.HOOKED_IN_ENTITY;
               return;
            }

            if (f > 0.0F) {
               this.field_70159_w *= 0.3D;
               this.field_70181_x *= 0.2D;
               this.field_70179_y *= 0.3D;
               this.field_190627_av = EntityFishHook.State.BOBBING;
               return;
            }

            if (!this.field_70170_p.field_72995_K) {
               this.func_190624_r();
            }

            if (!this.field_146051_au && !this.field_70122_E && !this.field_70123_F) {
               ++this.field_146047_aw;
            } else {
               this.field_146047_aw = 0;
               this.field_70159_w = 0.0D;
               this.field_70181_x = 0.0D;
               this.field_70179_y = 0.0D;
            }
         } else {
            if (this.field_190627_av == EntityFishHook.State.HOOKED_IN_ENTITY) {
               if (this.field_146043_c != null) {
                  if (this.field_146043_c.field_70128_L) {
                     this.field_146043_c = null;
                     this.field_190627_av = EntityFishHook.State.FLYING;
                  } else {
                     this.field_70165_t = this.field_146043_c.field_70165_t;
                     double d2 = (double)this.field_146043_c.field_70131_O;
                     this.field_70163_u = this.field_146043_c.func_174813_aQ().field_72338_b + d2 * 0.8D;
                     this.field_70161_v = this.field_146043_c.field_70161_v;
                     this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
                  }
               }

               return;
            }

            if (this.field_190627_av == EntityFishHook.State.BOBBING) {
               this.field_70159_w *= 0.9D;
               this.field_70179_y *= 0.9D;
               double d0 = this.field_70163_u + this.field_70181_x - (double)blockpos.func_177956_o() - (double)f;
               if (Math.abs(d0) < 0.01D) {
                  d0 += Math.signum(d0) * 0.1D;
               }

               this.field_70181_x -= d0 * (double)this.field_70146_Z.nextFloat() * 0.2D;
               if (!this.field_70170_p.field_72995_K && f > 0.0F) {
                  this.func_190621_a(blockpos);
               }
            }
         }

         if (iblockstate.func_185904_a() != Material.field_151586_h) {
            this.field_70181_x -= 0.03D;
         }

         this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
         this.func_190623_q();
         double d1 = 0.92D;
         this.field_70159_w *= 0.92D;
         this.field_70181_x *= 0.92D;
         this.field_70179_y *= 0.92D;
         this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      }
   }

   private boolean func_190625_o() {
      ItemStack itemstack = this.field_146042_b.func_184614_ca();
      ItemStack itemstack1 = this.field_146042_b.func_184592_cb();
      boolean flag = itemstack.func_77973_b() == Items.field_151112_aM;
      boolean flag1 = itemstack1.func_77973_b() == Items.field_151112_aM;
      if (!this.field_146042_b.field_70128_L && this.field_146042_b.func_70089_S() && (flag || flag1) && this.func_70068_e(this.field_146042_b) <= 1024.0D) {
         return false;
      } else {
         this.func_70106_y();
         return true;
      }
   }

   private void func_190623_q() {
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
   }

   private void func_190624_r() {
      Vec3d vec3d = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      Vec3d vec3d1 = new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
      RayTraceResult raytraceresult = this.field_70170_p.func_147447_a(vec3d, vec3d1, false, true, false);
      vec3d = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      vec3d1 = new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
      if (raytraceresult != null) {
         vec3d1 = new Vec3d(raytraceresult.field_72307_f.field_72450_a, raytraceresult.field_72307_f.field_72448_b, raytraceresult.field_72307_f.field_72449_c);
      }

      Entity entity = null;
      List<Entity> list = this.field_70170_p.func_72839_b(this, this.func_174813_aQ().func_72321_a(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_186662_g(1.0D));
      double d0 = 0.0D;

      for(Entity entity1 : list) {
         if (this.func_189739_a(entity1) && (entity1 != this.field_146042_b || this.field_146047_aw >= 5)) {
            AxisAlignedBB axisalignedbb = entity1.func_174813_aQ().func_186662_g(0.30000001192092896D);
            RayTraceResult raytraceresult1 = axisalignedbb.func_72327_a(vec3d, vec3d1);
            if (raytraceresult1 != null) {
               double d1 = vec3d.func_72436_e(raytraceresult1.field_72307_f);
               if (d1 < d0 || d0 == 0.0D) {
                  entity = entity1;
                  d0 = d1;
               }
            }
         }
      }

      if (entity != null) {
         raytraceresult = new RayTraceResult(entity);
      }

      if (raytraceresult != null && raytraceresult.field_72313_a != RayTraceResult.Type.MISS) {
         if (raytraceresult.field_72313_a == RayTraceResult.Type.ENTITY) {
            this.field_146043_c = raytraceresult.field_72308_g;
            this.func_190622_s();
         } else {
            this.field_146051_au = true;
         }
      }

   }

   private void func_190622_s() {
      this.func_184212_Q().func_187227_b(field_184528_c, Integer.valueOf(this.field_146043_c.func_145782_y() + 1));
   }

   private void func_190621_a(BlockPos p_190621_1_) {
      WorldServer worldserver = (WorldServer)this.field_70170_p;
      int i = 1;
      BlockPos blockpos = p_190621_1_.func_177984_a();
      if (this.field_70146_Z.nextFloat() < 0.25F && this.field_70170_p.func_175727_C(blockpos)) {
         ++i;
      }

      if (this.field_70146_Z.nextFloat() < 0.5F && !this.field_70170_p.func_175678_i(blockpos)) {
         --i;
      }

      if (this.field_146045_ax > 0) {
         --this.field_146045_ax;
         if (this.field_146045_ax <= 0) {
            this.field_146040_ay = 0;
            this.field_146038_az = 0;
         } else {
            this.field_70181_x -= 0.2D * (double)this.field_70146_Z.nextFloat() * (double)this.field_70146_Z.nextFloat();
         }
      } else if (this.field_146038_az > 0) {
         this.field_146038_az -= i;
         if (this.field_146038_az > 0) {
            this.field_146054_aA = (float)((double)this.field_146054_aA + this.field_70146_Z.nextGaussian() * 4.0D);
            float f = this.field_146054_aA * 0.017453292F;
            float f1 = MathHelper.func_76126_a(f);
            float f2 = MathHelper.func_76134_b(f);
            double d0 = this.field_70165_t + (double)(f1 * (float)this.field_146038_az * 0.1F);
            double d1 = (double)((float)MathHelper.func_76128_c(this.func_174813_aQ().field_72338_b) + 1.0F);
            double d2 = this.field_70161_v + (double)(f2 * (float)this.field_146038_az * 0.1F);
            Block block = worldserver.func_180495_p(new BlockPos(d0, d1 - 1.0D, d2)).func_177230_c();
            if (block == Blocks.field_150355_j || block == Blocks.field_150358_i) {
               if (this.field_70146_Z.nextFloat() < 0.15F) {
                  worldserver.func_175739_a(EnumParticleTypes.WATER_BUBBLE, d0, d1 - 0.10000000149011612D, d2, 1, (double)f1, 0.1D, (double)f2, 0.0D);
               }

               float f3 = f1 * 0.04F;
               float f4 = f2 * 0.04F;
               worldserver.func_175739_a(EnumParticleTypes.WATER_WAKE, d0, d1, d2, 0, (double)f4, 0.01D, (double)(-f3), 1.0D);
               worldserver.func_175739_a(EnumParticleTypes.WATER_WAKE, d0, d1, d2, 0, (double)(-f4), 0.01D, (double)f3, 1.0D);
            }
         } else {
            this.field_70181_x = (double)(-0.4F * MathHelper.func_151240_a(this.field_70146_Z, 0.6F, 1.0F));
            this.func_184185_a(SoundEvents.field_187609_F, 0.25F, 1.0F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.4F);
            double d3 = this.func_174813_aQ().field_72338_b + 0.5D;
            worldserver.func_175739_a(EnumParticleTypes.WATER_BUBBLE, this.field_70165_t, d3, this.field_70161_v, (int)(1.0F + this.field_70130_N * 20.0F), (double)this.field_70130_N, 0.0D, (double)this.field_70130_N, 0.20000000298023224D);
            worldserver.func_175739_a(EnumParticleTypes.WATER_WAKE, this.field_70165_t, d3, this.field_70161_v, (int)(1.0F + this.field_70130_N * 20.0F), (double)this.field_70130_N, 0.0D, (double)this.field_70130_N, 0.20000000298023224D);
            this.field_146045_ax = MathHelper.func_76136_a(this.field_70146_Z, 20, 40);
         }
      } else if (this.field_146040_ay > 0) {
         this.field_146040_ay -= i;
         float f5 = 0.15F;
         if (this.field_146040_ay < 20) {
            f5 = (float)((double)f5 + (double)(20 - this.field_146040_ay) * 0.05D);
         } else if (this.field_146040_ay < 40) {
            f5 = (float)((double)f5 + (double)(40 - this.field_146040_ay) * 0.02D);
         } else if (this.field_146040_ay < 60) {
            f5 = (float)((double)f5 + (double)(60 - this.field_146040_ay) * 0.01D);
         }

         if (this.field_70146_Z.nextFloat() < f5) {
            float f6 = MathHelper.func_151240_a(this.field_70146_Z, 0.0F, 360.0F) * 0.017453292F;
            float f7 = MathHelper.func_151240_a(this.field_70146_Z, 25.0F, 60.0F);
            double d4 = this.field_70165_t + (double)(MathHelper.func_76126_a(f6) * f7 * 0.1F);
            double d5 = (double)((float)MathHelper.func_76128_c(this.func_174813_aQ().field_72338_b) + 1.0F);
            double d6 = this.field_70161_v + (double)(MathHelper.func_76134_b(f6) * f7 * 0.1F);
            Block block1 = worldserver.func_180495_p(new BlockPos((int)d4, (int)d5 - 1, (int)d6)).func_177230_c();
            if (block1 == Blocks.field_150355_j || block1 == Blocks.field_150358_i) {
               worldserver.func_175739_a(EnumParticleTypes.WATER_SPLASH, d4, d5, d6, 2 + this.field_70146_Z.nextInt(2), 0.10000000149011612D, 0.0D, 0.10000000149011612D, 0.0D);
            }
         }

         if (this.field_146040_ay <= 0) {
            this.field_146054_aA = MathHelper.func_151240_a(this.field_70146_Z, 0.0F, 360.0F);
            this.field_146038_az = MathHelper.func_76136_a(this.field_70146_Z, 20, 80);
         }
      } else {
         this.field_146040_ay = MathHelper.func_76136_a(this.field_70146_Z, 100, 600);
         this.field_146040_ay -= this.field_191519_ax * 20 * 5;
      }

   }

   protected boolean func_189739_a(Entity p_189739_1_) {
      return p_189739_1_.func_70067_L() || p_189739_1_ instanceof EntityItem;
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
   }

   public int func_146034_e() {
      if (!this.field_70170_p.field_72995_K && this.field_146042_b != null) {
         int i = 0;
         if (this.field_146043_c != null) {
            this.func_184527_k();
            this.field_70170_p.func_72960_a(this, (byte)31);
            i = this.field_146043_c instanceof EntityItem ? 3 : 5;
         } else if (this.field_146045_ax > 0) {
            LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer)this.field_70170_p);
            lootcontext$builder.func_186469_a((float)this.field_191518_aw + this.field_146042_b.func_184817_da());

            for(ItemStack itemstack : this.field_70170_p.func_184146_ak().func_186521_a(LootTableList.field_186387_al).func_186462_a(this.field_70146_Z, lootcontext$builder.func_186471_a())) {
               EntityItem entityitem = new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, itemstack);
               double d0 = this.field_146042_b.field_70165_t - this.field_70165_t;
               double d1 = this.field_146042_b.field_70163_u - this.field_70163_u;
               double d2 = this.field_146042_b.field_70161_v - this.field_70161_v;
               double d3 = (double)MathHelper.func_76133_a(d0 * d0 + d1 * d1 + d2 * d2);
               double d4 = 0.1D;
               entityitem.field_70159_w = d0 * 0.1D;
               entityitem.field_70181_x = d1 * 0.1D + (double)MathHelper.func_76133_a(d3) * 0.08D;
               entityitem.field_70179_y = d2 * 0.1D;
               this.field_70170_p.func_72838_d(entityitem);
               this.field_146042_b.field_70170_p.func_72838_d(new EntityXPOrb(this.field_146042_b.field_70170_p, this.field_146042_b.field_70165_t, this.field_146042_b.field_70163_u + 0.5D, this.field_146042_b.field_70161_v + 0.5D, this.field_70146_Z.nextInt(6) + 1));
               Item item = itemstack.func_77973_b();
               if (item == Items.field_151115_aP || item == Items.field_179566_aV) {
                  this.field_146042_b.func_71064_a(StatList.field_188071_E, 1);
               }
            }

            i = 1;
         }

         if (this.field_146051_au) {
            i = 2;
         }

         this.func_70106_y();
         return i;
      } else {
         return 0;
      }
   }

   public void func_70103_a(byte p_70103_1_) {
      if (p_70103_1_ == 31 && this.field_70170_p.field_72995_K && this.field_146043_c instanceof EntityPlayer && ((EntityPlayer)this.field_146043_c).func_175144_cb()) {
         this.func_184527_k();
      }

      super.func_70103_a(p_70103_1_);
   }

   protected void func_184527_k() {
      if (this.field_146042_b != null) {
         double d0 = this.field_146042_b.field_70165_t - this.field_70165_t;
         double d1 = this.field_146042_b.field_70163_u - this.field_70163_u;
         double d2 = this.field_146042_b.field_70161_v - this.field_70161_v;
         double d3 = 0.1D;
         this.field_146043_c.field_70159_w += d0 * 0.1D;
         this.field_146043_c.field_70181_x += d1 * 0.1D;
         this.field_146043_c.field_70179_y += d2 * 0.1D;
      }
   }

   protected boolean func_70041_e_() {
      return false;
   }

   public void func_70106_y() {
      super.func_70106_y();
      if (this.field_146042_b != null) {
         this.field_146042_b.field_71104_cf = null;
      }

   }

   public EntityPlayer func_190619_l() {
      return this.field_146042_b;
   }

   static enum State {
      FLYING,
      HOOKED_IN_ENTITY,
      BOBBING;
   }
}
