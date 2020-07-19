package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityEnderEye extends Entity {
   private double field_70224_b;
   private double field_70225_c;
   private double field_70222_d;
   private int field_70223_e;
   private boolean field_70221_f;

   public EntityEnderEye(World p_i1757_1_) {
      super(p_i1757_1_);
      this.func_70105_a(0.25F, 0.25F);
   }

   protected void func_70088_a() {
   }

   public boolean func_70112_a(double p_70112_1_) {
      double d0 = this.func_174813_aQ().func_72320_b() * 4.0D;
      if (Double.isNaN(d0)) {
         d0 = 4.0D;
      }

      d0 = d0 * 64.0D;
      return p_70112_1_ < d0 * d0;
   }

   public EntityEnderEye(World p_i1758_1_, double p_i1758_2_, double p_i1758_4_, double p_i1758_6_) {
      super(p_i1758_1_);
      this.field_70223_e = 0;
      this.func_70105_a(0.25F, 0.25F);
      this.func_70107_b(p_i1758_2_, p_i1758_4_, p_i1758_6_);
   }

   public void func_180465_a(BlockPos p_180465_1_) {
      double d0 = (double)p_180465_1_.func_177958_n();
      int i = p_180465_1_.func_177956_o();
      double d1 = (double)p_180465_1_.func_177952_p();
      double d2 = d0 - this.field_70165_t;
      double d3 = d1 - this.field_70161_v;
      float f = MathHelper.func_76133_a(d2 * d2 + d3 * d3);
      if (f > 12.0F) {
         this.field_70224_b = this.field_70165_t + d2 / (double)f * 12.0D;
         this.field_70222_d = this.field_70161_v + d3 / (double)f * 12.0D;
         this.field_70225_c = this.field_70163_u + 8.0D;
      } else {
         this.field_70224_b = d0;
         this.field_70225_c = (double)i;
         this.field_70222_d = d1;
      }

      this.field_70223_e = 0;
      this.field_70221_f = this.field_70146_Z.nextInt(5) > 0;
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
      this.field_70165_t += this.field_70159_w;
      this.field_70163_u += this.field_70181_x;
      this.field_70161_v += this.field_70179_y;
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
      if (!this.field_70170_p.field_72995_K) {
         double d0 = this.field_70224_b - this.field_70165_t;
         double d1 = this.field_70222_d - this.field_70161_v;
         float f1 = (float)Math.sqrt(d0 * d0 + d1 * d1);
         float f2 = (float)MathHelper.func_181159_b(d1, d0);
         double d2 = (double)f + (double)(f1 - f) * 0.0025D;
         if (f1 < 1.0F) {
            d2 *= 0.8D;
            this.field_70181_x *= 0.8D;
         }

         this.field_70159_w = Math.cos((double)f2) * d2;
         this.field_70179_y = Math.sin((double)f2) * d2;
         if (this.field_70163_u < this.field_70225_c) {
            this.field_70181_x += (1.0D - this.field_70181_x) * 0.014999999664723873D;
         } else {
            this.field_70181_x += (-1.0D - this.field_70181_x) * 0.014999999664723873D;
         }
      }

      float f3 = 0.25F;
      if (this.func_70090_H()) {
         for(int i = 0; i < 4; ++i) {
            this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_BUBBLE, this.field_70165_t - this.field_70159_w * 0.25D, this.field_70163_u - this.field_70181_x * 0.25D, this.field_70161_v - this.field_70179_y * 0.25D, this.field_70159_w, this.field_70181_x, this.field_70179_y);
         }
      } else {
         this.field_70170_p.func_175688_a(EnumParticleTypes.PORTAL, this.field_70165_t - this.field_70159_w * 0.25D + this.field_70146_Z.nextDouble() * 0.6D - 0.3D, this.field_70163_u - this.field_70181_x * 0.25D - 0.5D, this.field_70161_v - this.field_70179_y * 0.25D + this.field_70146_Z.nextDouble() * 0.6D - 0.3D, this.field_70159_w, this.field_70181_x, this.field_70179_y);
      }

      if (!this.field_70170_p.field_72995_K) {
         this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
         ++this.field_70223_e;
         if (this.field_70223_e > 80 && !this.field_70170_p.field_72995_K) {
            this.func_184185_a(SoundEvents.field_193777_bb, 1.0F, 1.0F);
            this.func_70106_y();
            if (this.field_70221_f) {
               this.field_70170_p.func_72838_d(new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, new ItemStack(Items.field_151061_bv)));
            } else {
               this.field_70170_p.func_175718_b(2003, new BlockPos(this), 0);
            }
         }
      }

   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
   }

   public float func_70013_c() {
      return 1.0F;
   }

   public int func_70070_b() {
      return 15728880;
   }

   public boolean func_70075_an() {
      return false;
   }
}
