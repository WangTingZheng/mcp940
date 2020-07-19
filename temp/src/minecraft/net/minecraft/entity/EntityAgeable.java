package net.minecraft.entity;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public abstract class EntityAgeable extends EntityCreature {
   private static final DataParameter<Boolean> field_184751_bv = EntityDataManager.<Boolean>func_187226_a(EntityAgeable.class, DataSerializers.field_187198_h);
   protected int field_175504_a;
   protected int field_175502_b;
   protected int field_175503_c;
   private float field_98056_d = -1.0F;
   private float field_98057_e;

   public EntityAgeable(World p_i1578_1_) {
      super(p_i1578_1_);
   }

   @Nullable
   public abstract EntityAgeable func_90011_a(EntityAgeable var1);

   public boolean func_184645_a(EntityPlayer p_184645_1_, EnumHand p_184645_2_) {
      ItemStack itemstack = p_184645_1_.func_184586_b(p_184645_2_);
      if (itemstack.func_77973_b() == Items.field_151063_bx) {
         if (!this.field_70170_p.field_72995_K) {
            Class<? extends Entity> oclass = (Class)EntityList.field_191308_b.func_82594_a(ItemMonsterPlacer.func_190908_h(itemstack));
            if (oclass != null && this.getClass() == oclass) {
               EntityAgeable entityageable = this.func_90011_a(this);
               if (entityageable != null) {
                  entityageable.func_70873_a(-24000);
                  entityageable.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, 0.0F, 0.0F);
                  this.field_70170_p.func_72838_d(entityageable);
                  if (itemstack.func_82837_s()) {
                     entityageable.func_96094_a(itemstack.func_82833_r());
                  }

                  if (!p_184645_1_.field_71075_bZ.field_75098_d) {
                     itemstack.func_190918_g(1);
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   protected boolean func_190669_a(ItemStack p_190669_1_, Class<? extends Entity> p_190669_2_) {
      if (p_190669_1_.func_77973_b() != Items.field_151063_bx) {
         return false;
      } else {
         Class<? extends Entity> oclass = (Class)EntityList.field_191308_b.func_82594_a(ItemMonsterPlacer.func_190908_h(p_190669_1_));
         return oclass != null && p_190669_2_ == oclass;
      }
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184751_bv, Boolean.valueOf(false));
   }

   public int func_70874_b() {
      if (this.field_70170_p.field_72995_K) {
         return ((Boolean)this.field_70180_af.func_187225_a(field_184751_bv)).booleanValue() ? -1 : 1;
      } else {
         return this.field_175504_a;
      }
   }

   public void func_175501_a(int p_175501_1_, boolean p_175501_2_) {
      int i = this.func_70874_b();
      int j = i;
      i = i + p_175501_1_ * 20;
      if (i > 0) {
         i = 0;
         if (j < 0) {
            this.func_175500_n();
         }
      }

      int k = i - j;
      this.func_70873_a(i);
      if (p_175501_2_) {
         this.field_175502_b += k;
         if (this.field_175503_c == 0) {
            this.field_175503_c = 40;
         }
      }

      if (this.func_70874_b() == 0) {
         this.func_70873_a(this.field_175502_b);
      }

   }

   public void func_110195_a(int p_110195_1_) {
      this.func_175501_a(p_110195_1_, false);
   }

   public void func_70873_a(int p_70873_1_) {
      this.field_70180_af.func_187227_b(field_184751_bv, Boolean.valueOf(p_70873_1_ < 0));
      this.field_175504_a = p_70873_1_;
      this.func_98054_a(this.func_70631_g_());
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74768_a("Age", this.func_70874_b());
      p_70014_1_.func_74768_a("ForcedAge", this.field_175502_b);
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      this.func_70873_a(p_70037_1_.func_74762_e("Age"));
      this.field_175502_b = p_70037_1_.func_74762_e("ForcedAge");
   }

   public void func_184206_a(DataParameter<?> p_184206_1_) {
      if (field_184751_bv.equals(p_184206_1_)) {
         this.func_98054_a(this.func_70631_g_());
      }

      super.func_184206_a(p_184206_1_);
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (this.field_70170_p.field_72995_K) {
         if (this.field_175503_c > 0) {
            if (this.field_175503_c % 4 == 0) {
               this.field_70170_p.func_175688_a(EnumParticleTypes.VILLAGER_HAPPY, this.field_70165_t + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, this.field_70163_u + 0.5D + (double)(this.field_70146_Z.nextFloat() * this.field_70131_O), this.field_70161_v + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, 0.0D, 0.0D, 0.0D);
            }

            --this.field_175503_c;
         }
      } else {
         int i = this.func_70874_b();
         if (i < 0) {
            ++i;
            this.func_70873_a(i);
            if (i == 0) {
               this.func_175500_n();
            }
         } else if (i > 0) {
            --i;
            this.func_70873_a(i);
         }
      }

   }

   protected void func_175500_n() {
   }

   public boolean func_70631_g_() {
      return this.func_70874_b() < 0;
   }

   public void func_98054_a(boolean p_98054_1_) {
      this.func_98055_j(p_98054_1_ ? 0.5F : 1.0F);
   }

   protected final void func_70105_a(float p_70105_1_, float p_70105_2_) {
      boolean flag = this.field_98056_d > 0.0F;
      this.field_98056_d = p_70105_1_;
      this.field_98057_e = p_70105_2_;
      if (!flag) {
         this.func_98055_j(1.0F);
      }

   }

   protected final void func_98055_j(float p_98055_1_) {
      super.func_70105_a(this.field_98056_d * p_98055_1_, this.field_98057_e * p_98055_1_);
   }
}
