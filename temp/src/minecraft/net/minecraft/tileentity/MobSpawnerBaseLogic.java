package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;

public abstract class MobSpawnerBaseLogic {
   private int field_98286_b = 20;
   private final List<WeightedSpawnerEntity> field_98285_e = Lists.<WeightedSpawnerEntity>newArrayList();
   private WeightedSpawnerEntity field_98282_f = new WeightedSpawnerEntity();
   private double field_98287_c;
   private double field_98284_d;
   private int field_98283_g = 200;
   private int field_98293_h = 800;
   private int field_98294_i = 4;
   private Entity field_98291_j;
   private int field_98292_k = 6;
   private int field_98289_l = 16;
   private int field_98290_m = 4;

   @Nullable
   private ResourceLocation func_190895_g() {
      String s = this.field_98282_f.func_185277_b().func_74779_i("id");
      return StringUtils.func_151246_b(s) ? null : new ResourceLocation(s);
   }

   public void func_190894_a(@Nullable ResourceLocation p_190894_1_) {
      if (p_190894_1_ != null) {
         this.field_98282_f.func_185277_b().func_74778_a("id", p_190894_1_.toString());
      }

   }

   private boolean func_98279_f() {
      BlockPos blockpos = this.func_177221_b();
      return this.func_98271_a().func_175636_b((double)blockpos.func_177958_n() + 0.5D, (double)blockpos.func_177956_o() + 0.5D, (double)blockpos.func_177952_p() + 0.5D, (double)this.field_98289_l);
   }

   public void func_98278_g() {
      if (!this.func_98279_f()) {
         this.field_98284_d = this.field_98287_c;
      } else {
         BlockPos blockpos = this.func_177221_b();
         if (this.func_98271_a().field_72995_K) {
            double d3 = (double)((float)blockpos.func_177958_n() + this.func_98271_a().field_73012_v.nextFloat());
            double d4 = (double)((float)blockpos.func_177956_o() + this.func_98271_a().field_73012_v.nextFloat());
            double d5 = (double)((float)blockpos.func_177952_p() + this.func_98271_a().field_73012_v.nextFloat());
            this.func_98271_a().func_175688_a(EnumParticleTypes.SMOKE_NORMAL, d3, d4, d5, 0.0D, 0.0D, 0.0D);
            this.func_98271_a().func_175688_a(EnumParticleTypes.FLAME, d3, d4, d5, 0.0D, 0.0D, 0.0D);
            if (this.field_98286_b > 0) {
               --this.field_98286_b;
            }

            this.field_98284_d = this.field_98287_c;
            this.field_98287_c = (this.field_98287_c + (double)(1000.0F / ((float)this.field_98286_b + 200.0F))) % 360.0D;
         } else {
            if (this.field_98286_b == -1) {
               this.func_98273_j();
            }

            if (this.field_98286_b > 0) {
               --this.field_98286_b;
               return;
            }

            boolean flag = false;

            for(int i = 0; i < this.field_98294_i; ++i) {
               NBTTagCompound nbttagcompound = this.field_98282_f.func_185277_b();
               NBTTagList nbttaglist = nbttagcompound.func_150295_c("Pos", 6);
               World world = this.func_98271_a();
               int j = nbttaglist.func_74745_c();
               double d0 = j >= 1 ? nbttaglist.func_150309_d(0) : (double)blockpos.func_177958_n() + (world.field_73012_v.nextDouble() - world.field_73012_v.nextDouble()) * (double)this.field_98290_m + 0.5D;
               double d1 = j >= 2 ? nbttaglist.func_150309_d(1) : (double)(blockpos.func_177956_o() + world.field_73012_v.nextInt(3) - 1);
               double d2 = j >= 3 ? nbttaglist.func_150309_d(2) : (double)blockpos.func_177952_p() + (world.field_73012_v.nextDouble() - world.field_73012_v.nextDouble()) * (double)this.field_98290_m + 0.5D;
               Entity entity = AnvilChunkLoader.func_186054_a(nbttagcompound, world, d0, d1, d2, false);
               if (entity == null) {
                  return;
               }

               int k = world.func_72872_a(entity.getClass(), (new AxisAlignedBB((double)blockpos.func_177958_n(), (double)blockpos.func_177956_o(), (double)blockpos.func_177952_p(), (double)(blockpos.func_177958_n() + 1), (double)(blockpos.func_177956_o() + 1), (double)(blockpos.func_177952_p() + 1))).func_186662_g((double)this.field_98290_m)).size();
               if (k >= this.field_98292_k) {
                  this.func_98273_j();
                  return;
               }

               EntityLiving entityliving = entity instanceof EntityLiving ? (EntityLiving)entity : null;
               entity.func_70012_b(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, world.field_73012_v.nextFloat() * 360.0F, 0.0F);
               if (entityliving == null || entityliving.func_70601_bi() && entityliving.func_70058_J()) {
                  if (this.field_98282_f.func_185277_b().func_186856_d() == 1 && this.field_98282_f.func_185277_b().func_150297_b("id", 8) && entity instanceof EntityLiving) {
                     ((EntityLiving)entity).func_180482_a(world.func_175649_E(new BlockPos(entity)), (IEntityLivingData)null);
                  }

                  AnvilChunkLoader.func_186052_a(entity, world);
                  world.func_175718_b(2004, blockpos, 0);
                  if (entityliving != null) {
                     entityliving.func_70656_aK();
                  }

                  flag = true;
               }
            }

            if (flag) {
               this.func_98273_j();
            }
         }

      }
   }

   private void func_98273_j() {
      if (this.field_98293_h <= this.field_98283_g) {
         this.field_98286_b = this.field_98283_g;
      } else {
         int i = this.field_98293_h - this.field_98283_g;
         this.field_98286_b = this.field_98283_g + this.func_98271_a().field_73012_v.nextInt(i);
      }

      if (!this.field_98285_e.isEmpty()) {
         this.func_184993_a((WeightedSpawnerEntity)WeightedRandom.func_76271_a(this.func_98271_a().field_73012_v, this.field_98285_e));
      }

      this.func_98267_a(1);
   }

   public void func_98270_a(NBTTagCompound p_98270_1_) {
      this.field_98286_b = p_98270_1_.func_74765_d("Delay");
      this.field_98285_e.clear();
      if (p_98270_1_.func_150297_b("SpawnPotentials", 9)) {
         NBTTagList nbttaglist = p_98270_1_.func_150295_c("SpawnPotentials", 10);

         for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
            this.field_98285_e.add(new WeightedSpawnerEntity(nbttaglist.func_150305_b(i)));
         }
      }

      if (p_98270_1_.func_150297_b("SpawnData", 10)) {
         this.func_184993_a(new WeightedSpawnerEntity(1, p_98270_1_.func_74775_l("SpawnData")));
      } else if (!this.field_98285_e.isEmpty()) {
         this.func_184993_a((WeightedSpawnerEntity)WeightedRandom.func_76271_a(this.func_98271_a().field_73012_v, this.field_98285_e));
      }

      if (p_98270_1_.func_150297_b("MinSpawnDelay", 99)) {
         this.field_98283_g = p_98270_1_.func_74765_d("MinSpawnDelay");
         this.field_98293_h = p_98270_1_.func_74765_d("MaxSpawnDelay");
         this.field_98294_i = p_98270_1_.func_74765_d("SpawnCount");
      }

      if (p_98270_1_.func_150297_b("MaxNearbyEntities", 99)) {
         this.field_98292_k = p_98270_1_.func_74765_d("MaxNearbyEntities");
         this.field_98289_l = p_98270_1_.func_74765_d("RequiredPlayerRange");
      }

      if (p_98270_1_.func_150297_b("SpawnRange", 99)) {
         this.field_98290_m = p_98270_1_.func_74765_d("SpawnRange");
      }

      if (this.func_98271_a() != null) {
         this.field_98291_j = null;
      }

   }

   public NBTTagCompound func_189530_b(NBTTagCompound p_189530_1_) {
      ResourceLocation resourcelocation = this.func_190895_g();
      if (resourcelocation == null) {
         return p_189530_1_;
      } else {
         p_189530_1_.func_74777_a("Delay", (short)this.field_98286_b);
         p_189530_1_.func_74777_a("MinSpawnDelay", (short)this.field_98283_g);
         p_189530_1_.func_74777_a("MaxSpawnDelay", (short)this.field_98293_h);
         p_189530_1_.func_74777_a("SpawnCount", (short)this.field_98294_i);
         p_189530_1_.func_74777_a("MaxNearbyEntities", (short)this.field_98292_k);
         p_189530_1_.func_74777_a("RequiredPlayerRange", (short)this.field_98289_l);
         p_189530_1_.func_74777_a("SpawnRange", (short)this.field_98290_m);
         p_189530_1_.func_74782_a("SpawnData", this.field_98282_f.func_185277_b().func_74737_b());
         NBTTagList nbttaglist = new NBTTagList();
         if (this.field_98285_e.isEmpty()) {
            nbttaglist.func_74742_a(this.field_98282_f.func_185278_a());
         } else {
            for(WeightedSpawnerEntity weightedspawnerentity : this.field_98285_e) {
               nbttaglist.func_74742_a(weightedspawnerentity.func_185278_a());
            }
         }

         p_189530_1_.func_74782_a("SpawnPotentials", nbttaglist);
         return p_189530_1_;
      }
   }

   public Entity func_184994_d() {
      if (this.field_98291_j == null) {
         this.field_98291_j = AnvilChunkLoader.func_186051_a(this.field_98282_f.func_185277_b(), this.func_98271_a(), false);
         if (this.field_98282_f.func_185277_b().func_186856_d() == 1 && this.field_98282_f.func_185277_b().func_150297_b("id", 8) && this.field_98291_j instanceof EntityLiving) {
            ((EntityLiving)this.field_98291_j).func_180482_a(this.func_98271_a().func_175649_E(new BlockPos(this.field_98291_j)), (IEntityLivingData)null);
         }
      }

      return this.field_98291_j;
   }

   public boolean func_98268_b(int p_98268_1_) {
      if (p_98268_1_ == 1 && this.func_98271_a().field_72995_K) {
         this.field_98286_b = this.field_98283_g;
         return true;
      } else {
         return false;
      }
   }

   public void func_184993_a(WeightedSpawnerEntity p_184993_1_) {
      this.field_98282_f = p_184993_1_;
   }

   public abstract void func_98267_a(int var1);

   public abstract World func_98271_a();

   public abstract BlockPos func_177221_b();

   public double func_177222_d() {
      return this.field_98287_c;
   }

   public double func_177223_e() {
      return this.field_98284_d;
   }
}
