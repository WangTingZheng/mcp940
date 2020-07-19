package net.minecraft.entity.item;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityMinecartTNT extends EntityMinecart {
   private int field_94106_a = -1;

   public EntityMinecartTNT(World p_i1727_1_) {
      super(p_i1727_1_);
   }

   public EntityMinecartTNT(World p_i1728_1_, double p_i1728_2_, double p_i1728_4_, double p_i1728_6_) {
      super(p_i1728_1_, p_i1728_2_, p_i1728_4_, p_i1728_6_);
   }

   public static void func_189674_a(DataFixer p_189674_0_) {
      EntityMinecart.func_189669_a(p_189674_0_, EntityMinecartTNT.class);
   }

   public EntityMinecart.Type func_184264_v() {
      return EntityMinecart.Type.TNT;
   }

   public IBlockState func_180457_u() {
      return Blocks.field_150335_W.func_176223_P();
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_94106_a > 0) {
         --this.field_94106_a;
         this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, this.field_70165_t, this.field_70163_u + 0.5D, this.field_70161_v, 0.0D, 0.0D, 0.0D);
      } else if (this.field_94106_a == 0) {
         this.func_94103_c(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
      }

      if (this.field_70123_F) {
         double d0 = this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y;
         if (d0 >= 0.009999999776482582D) {
            this.func_94103_c(d0);
         }
      }

   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      Entity entity = p_70097_1_.func_76364_f();
      if (entity instanceof EntityArrow) {
         EntityArrow entityarrow = (EntityArrow)entity;
         if (entityarrow.func_70027_ad()) {
            this.func_94103_c(entityarrow.field_70159_w * entityarrow.field_70159_w + entityarrow.field_70181_x * entityarrow.field_70181_x + entityarrow.field_70179_y * entityarrow.field_70179_y);
         }
      }

      return super.func_70097_a(p_70097_1_, p_70097_2_);
   }

   public void func_94095_a(DamageSource p_94095_1_) {
      double d0 = this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y;
      if (!p_94095_1_.func_76347_k() && !p_94095_1_.func_94541_c() && d0 < 0.009999999776482582D) {
         super.func_94095_a(p_94095_1_);
         if (!p_94095_1_.func_94541_c() && this.field_70170_p.func_82736_K().func_82766_b("doEntityDrops")) {
            this.func_70099_a(new ItemStack(Blocks.field_150335_W, 1), 0.0F);
         }

      } else {
         if (this.field_94106_a < 0) {
            this.func_94105_c();
            this.field_94106_a = this.field_70146_Z.nextInt(20) + this.field_70146_Z.nextInt(20);
         }

      }
   }

   protected void func_94103_c(double p_94103_1_) {
      if (!this.field_70170_p.field_72995_K) {
         double d0 = Math.sqrt(p_94103_1_);
         if (d0 > 5.0D) {
            d0 = 5.0D;
         }

         this.field_70170_p.func_72876_a(this, this.field_70165_t, this.field_70163_u, this.field_70161_v, (float)(4.0D + this.field_70146_Z.nextDouble() * 1.5D * d0), true);
         this.func_70106_y();
      }

   }

   public void func_180430_e(float p_180430_1_, float p_180430_2_) {
      if (p_180430_1_ >= 3.0F) {
         float f = p_180430_1_ / 10.0F;
         this.func_94103_c((double)(f * f));
      }

      super.func_180430_e(p_180430_1_, p_180430_2_);
   }

   public void func_96095_a(int p_96095_1_, int p_96095_2_, int p_96095_3_, boolean p_96095_4_) {
      if (p_96095_4_ && this.field_94106_a < 0) {
         this.func_94105_c();
      }

   }

   public void func_70103_a(byte p_70103_1_) {
      if (p_70103_1_ == 10) {
         this.func_94105_c();
      } else {
         super.func_70103_a(p_70103_1_);
      }

   }

   public void func_94105_c() {
      this.field_94106_a = 80;
      if (!this.field_70170_p.field_72995_K) {
         this.field_70170_p.func_72960_a(this, (byte)10);
         if (!this.func_174814_R()) {
            this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187904_gd, SoundCategory.BLOCKS, 1.0F, 1.0F);
         }
      }

   }

   public int func_94104_d() {
      return this.field_94106_a;
   }

   public boolean func_96096_ay() {
      return this.field_94106_a > -1;
   }

   public float func_180428_a(Explosion p_180428_1_, World p_180428_2_, BlockPos p_180428_3_, IBlockState p_180428_4_) {
      return !this.func_96096_ay() || !BlockRailBase.func_176563_d(p_180428_4_) && !BlockRailBase.func_176562_d(p_180428_2_, p_180428_3_.func_177984_a()) ? super.func_180428_a(p_180428_1_, p_180428_2_, p_180428_3_, p_180428_4_) : 0.0F;
   }

   public boolean func_174816_a(Explosion p_174816_1_, World p_174816_2_, BlockPos p_174816_3_, IBlockState p_174816_4_, float p_174816_5_) {
      return !this.func_96096_ay() || !BlockRailBase.func_176563_d(p_174816_4_) && !BlockRailBase.func_176562_d(p_174816_2_, p_174816_3_.func_177984_a()) ? super.func_174816_a(p_174816_1_, p_174816_2_, p_174816_3_, p_174816_4_, p_174816_5_) : false;
   }

   protected void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      if (p_70037_1_.func_150297_b("TNTFuse", 99)) {
         this.field_94106_a = p_70037_1_.func_74762_e("TNTFuse");
      }

   }

   protected void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74768_a("TNTFuse", this.field_94106_a);
   }
}
