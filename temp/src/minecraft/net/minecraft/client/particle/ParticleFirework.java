package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemDye;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleFirework {
   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         ParticleFirework.Spark particlefirework$spark = new ParticleFirework.Spark(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_, Minecraft.func_71410_x().field_71452_i);
         particlefirework$spark.func_82338_g(0.99F);
         return particlefirework$spark;
      }
   }

   public static class Overlay extends Particle {
      protected Overlay(World p_i46466_1_, double p_i46466_2_, double p_i46466_4_, double p_i46466_6_) {
         super(p_i46466_1_, p_i46466_2_, p_i46466_4_, p_i46466_6_);
         this.field_70547_e = 4;
      }

      public void func_180434_a(BufferBuilder p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
         float f = 0.25F;
         float f1 = 0.5F;
         float f2 = 0.125F;
         float f3 = 0.375F;
         float f4 = 7.1F * MathHelper.func_76126_a(((float)this.field_70546_d + p_180434_3_ - 1.0F) * 0.25F * 3.1415927F);
         this.func_82338_g(0.6F - ((float)this.field_70546_d + p_180434_3_ - 1.0F) * 0.25F * 0.5F);
         float f5 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * (double)p_180434_3_ - field_70556_an);
         float f6 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * (double)p_180434_3_ - field_70554_ao);
         float f7 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * (double)p_180434_3_ - field_70555_ap);
         int i = this.func_189214_a(p_180434_3_);
         int j = i >> 16 & '\uffff';
         int k = i & '\uffff';
         p_180434_1_.func_181662_b((double)(f5 - p_180434_4_ * f4 - p_180434_7_ * f4), (double)(f6 - p_180434_5_ * f4), (double)(f7 - p_180434_6_ * f4 - p_180434_8_ * f4)).func_187315_a(0.5D, 0.375D).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as).func_187314_a(j, k).func_181675_d();
         p_180434_1_.func_181662_b((double)(f5 - p_180434_4_ * f4 + p_180434_7_ * f4), (double)(f6 + p_180434_5_ * f4), (double)(f7 - p_180434_6_ * f4 + p_180434_8_ * f4)).func_187315_a(0.5D, 0.125D).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as).func_187314_a(j, k).func_181675_d();
         p_180434_1_.func_181662_b((double)(f5 + p_180434_4_ * f4 + p_180434_7_ * f4), (double)(f6 + p_180434_5_ * f4), (double)(f7 + p_180434_6_ * f4 + p_180434_8_ * f4)).func_187315_a(0.25D, 0.125D).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as).func_187314_a(j, k).func_181675_d();
         p_180434_1_.func_181662_b((double)(f5 + p_180434_4_ * f4 - p_180434_7_ * f4), (double)(f6 - p_180434_5_ * f4), (double)(f7 + p_180434_6_ * f4 - p_180434_8_ * f4)).func_187315_a(0.25D, 0.375D).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as).func_187314_a(j, k).func_181675_d();
      }
   }

   public static class Spark extends ParticleSimpleAnimated {
      private boolean field_92054_ax;
      private boolean field_92048_ay;
      private final ParticleManager field_92047_az;
      private float field_92050_aA;
      private float field_92051_aB;
      private float field_92052_aC;
      private boolean field_92053_aD;

      public Spark(World p_i46465_1_, double p_i46465_2_, double p_i46465_4_, double p_i46465_6_, double p_i46465_8_, double p_i46465_10_, double p_i46465_12_, ParticleManager p_i46465_14_) {
         super(p_i46465_1_, p_i46465_2_, p_i46465_4_, p_i46465_6_, 160, 8, -0.004F);
         this.field_187129_i = p_i46465_8_;
         this.field_187130_j = p_i46465_10_;
         this.field_187131_k = p_i46465_12_;
         this.field_92047_az = p_i46465_14_;
         this.field_70544_f *= 0.75F;
         this.field_70547_e = 48 + this.field_187136_p.nextInt(12);
      }

      public void func_92045_e(boolean p_92045_1_) {
         this.field_92054_ax = p_92045_1_;
      }

      public void func_92043_f(boolean p_92043_1_) {
         this.field_92048_ay = p_92043_1_;
      }

      public boolean func_187111_c() {
         return true;
      }

      public void func_180434_a(BufferBuilder p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
         if (!this.field_92048_ay || this.field_70546_d < this.field_70547_e / 3 || (this.field_70546_d + this.field_70547_e) / 3 % 2 == 0) {
            super.func_180434_a(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
         }

      }

      public void func_189213_a() {
         super.func_189213_a();
         if (this.field_92054_ax && this.field_70546_d < this.field_70547_e / 2 && (this.field_70546_d + this.field_70547_e) % 2 == 0) {
            ParticleFirework.Spark particlefirework$spark = new ParticleFirework.Spark(this.field_187122_b, this.field_187126_f, this.field_187127_g, this.field_187128_h, 0.0D, 0.0D, 0.0D, this.field_92047_az);
            particlefirework$spark.func_82338_g(0.99F);
            particlefirework$spark.func_70538_b(this.field_70552_h, this.field_70553_i, this.field_70551_j);
            particlefirework$spark.field_70546_d = particlefirework$spark.field_70547_e / 2;
            if (this.field_92053_aD) {
               particlefirework$spark.field_92053_aD = true;
               particlefirework$spark.field_92050_aA = this.field_92050_aA;
               particlefirework$spark.field_92051_aB = this.field_92051_aB;
               particlefirework$spark.field_92052_aC = this.field_92052_aC;
            }

            particlefirework$spark.field_92048_ay = this.field_92048_ay;
            this.field_92047_az.func_78873_a(particlefirework$spark);
         }

      }
   }

   public static class Starter extends Particle {
      private int field_92042_ax;
      private final ParticleManager field_92040_ay;
      private NBTTagList field_92039_az;
      boolean field_92041_a;

      public Starter(World p_i46464_1_, double p_i46464_2_, double p_i46464_4_, double p_i46464_6_, double p_i46464_8_, double p_i46464_10_, double p_i46464_12_, ParticleManager p_i46464_14_, @Nullable NBTTagCompound p_i46464_15_) {
         super(p_i46464_1_, p_i46464_2_, p_i46464_4_, p_i46464_6_, 0.0D, 0.0D, 0.0D);
         this.field_187129_i = p_i46464_8_;
         this.field_187130_j = p_i46464_10_;
         this.field_187131_k = p_i46464_12_;
         this.field_92040_ay = p_i46464_14_;
         this.field_70547_e = 8;
         if (p_i46464_15_ != null) {
            this.field_92039_az = p_i46464_15_.func_150295_c("Explosions", 10);
            if (this.field_92039_az.func_82582_d()) {
               this.field_92039_az = null;
            } else {
               this.field_70547_e = this.field_92039_az.func_74745_c() * 2 - 1;

               for(int i = 0; i < this.field_92039_az.func_74745_c(); ++i) {
                  NBTTagCompound nbttagcompound = this.field_92039_az.func_150305_b(i);
                  if (nbttagcompound.func_74767_n("Flicker")) {
                     this.field_92041_a = true;
                     this.field_70547_e += 15;
                     break;
                  }
               }
            }
         }

      }

      public void func_180434_a(BufferBuilder p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
      }

      public void func_189213_a() {
         if (this.field_92042_ax == 0 && this.field_92039_az != null) {
            boolean flag = this.func_92037_i();
            boolean flag1 = false;
            if (this.field_92039_az.func_74745_c() >= 3) {
               flag1 = true;
            } else {
               for(int i = 0; i < this.field_92039_az.func_74745_c(); ++i) {
                  NBTTagCompound nbttagcompound = this.field_92039_az.func_150305_b(i);
                  if (nbttagcompound.func_74771_c("Type") == 1) {
                     flag1 = true;
                     break;
                  }
               }
            }

            SoundEvent soundevent1;
            if (flag1) {
               soundevent1 = flag ? SoundEvents.field_187628_bn : SoundEvents.field_187625_bm;
            } else {
               soundevent1 = flag ? SoundEvents.field_187622_bl : SoundEvents.field_187619_bk;
            }

            this.field_187122_b.func_184134_a(this.field_187126_f, this.field_187127_g, this.field_187128_h, soundevent1, SoundCategory.AMBIENT, 20.0F, 0.95F + this.field_187136_p.nextFloat() * 0.1F, true);
         }

         if (this.field_92042_ax % 2 == 0 && this.field_92039_az != null && this.field_92042_ax / 2 < this.field_92039_az.func_74745_c()) {
            int k = this.field_92042_ax / 2;
            NBTTagCompound nbttagcompound1 = this.field_92039_az.func_150305_b(k);
            int l = nbttagcompound1.func_74771_c("Type");
            boolean flag4 = nbttagcompound1.func_74767_n("Trail");
            boolean flag2 = nbttagcompound1.func_74767_n("Flicker");
            int[] aint = nbttagcompound1.func_74759_k("Colors");
            int[] aint1 = nbttagcompound1.func_74759_k("FadeColors");
            if (aint.length == 0) {
               aint = new int[]{ItemDye.field_150922_c[0]};
            }

            if (l == 1) {
               this.func_92035_a(0.5D, 4, aint, aint1, flag4, flag2);
            } else if (l == 2) {
               this.func_92038_a(0.5D, new double[][]{{0.0D, 1.0D}, {0.3455D, 0.309D}, {0.9511D, 0.309D}, {0.3795918367346939D, -0.12653061224489795D}, {0.6122448979591837D, -0.8040816326530612D}, {0.0D, -0.35918367346938773D}}, aint, aint1, flag4, flag2, false);
            } else if (l == 3) {
               this.func_92038_a(0.5D, new double[][]{{0.0D, 0.2D}, {0.2D, 0.2D}, {0.2D, 0.6D}, {0.6D, 0.6D}, {0.6D, 0.2D}, {0.2D, 0.2D}, {0.2D, 0.0D}, {0.4D, 0.0D}, {0.4D, -0.6D}, {0.2D, -0.6D}, {0.2D, -0.4D}, {0.0D, -0.4D}}, aint, aint1, flag4, flag2, true);
            } else if (l == 4) {
               this.func_92036_a(aint, aint1, flag4, flag2);
            } else {
               this.func_92035_a(0.25D, 2, aint, aint1, flag4, flag2);
            }

            int j = aint[0];
            float f = (float)((j & 16711680) >> 16) / 255.0F;
            float f1 = (float)((j & '\uff00') >> 8) / 255.0F;
            float f2 = (float)((j & 255) >> 0) / 255.0F;
            ParticleFirework.Overlay particlefirework$overlay = new ParticleFirework.Overlay(this.field_187122_b, this.field_187126_f, this.field_187127_g, this.field_187128_h);
            particlefirework$overlay.func_70538_b(f, f1, f2);
            this.field_92040_ay.func_78873_a(particlefirework$overlay);
         }

         ++this.field_92042_ax;
         if (this.field_92042_ax > this.field_70547_e) {
            if (this.field_92041_a) {
               boolean flag3 = this.func_92037_i();
               SoundEvent soundevent = flag3 ? SoundEvents.field_187640_br : SoundEvents.field_187637_bq;
               this.field_187122_b.func_184134_a(this.field_187126_f, this.field_187127_g, this.field_187128_h, soundevent, SoundCategory.AMBIENT, 20.0F, 0.9F + this.field_187136_p.nextFloat() * 0.15F, true);
            }

            this.func_187112_i();
         }

      }

      private boolean func_92037_i() {
         Minecraft minecraft = Minecraft.func_71410_x();
         return minecraft == null || minecraft.func_175606_aa() == null || minecraft.func_175606_aa().func_70092_e(this.field_187126_f, this.field_187127_g, this.field_187128_h) >= 256.0D;
      }

      private void func_92034_a(double p_92034_1_, double p_92034_3_, double p_92034_5_, double p_92034_7_, double p_92034_9_, double p_92034_11_, int[] p_92034_13_, int[] p_92034_14_, boolean p_92034_15_, boolean p_92034_16_) {
         ParticleFirework.Spark particlefirework$spark = new ParticleFirework.Spark(this.field_187122_b, p_92034_1_, p_92034_3_, p_92034_5_, p_92034_7_, p_92034_9_, p_92034_11_, this.field_92040_ay);
         particlefirework$spark.func_82338_g(0.99F);
         particlefirework$spark.func_92045_e(p_92034_15_);
         particlefirework$spark.func_92043_f(p_92034_16_);
         int i = this.field_187136_p.nextInt(p_92034_13_.length);
         particlefirework$spark.func_187146_c(p_92034_13_[i]);
         if (p_92034_14_ != null && p_92034_14_.length > 0) {
            particlefirework$spark.func_187145_d(p_92034_14_[this.field_187136_p.nextInt(p_92034_14_.length)]);
         }

         this.field_92040_ay.func_78873_a(particlefirework$spark);
      }

      private void func_92035_a(double p_92035_1_, int p_92035_3_, int[] p_92035_4_, int[] p_92035_5_, boolean p_92035_6_, boolean p_92035_7_) {
         double d0 = this.field_187126_f;
         double d1 = this.field_187127_g;
         double d2 = this.field_187128_h;

         for(int i = -p_92035_3_; i <= p_92035_3_; ++i) {
            for(int j = -p_92035_3_; j <= p_92035_3_; ++j) {
               for(int k = -p_92035_3_; k <= p_92035_3_; ++k) {
                  double d3 = (double)j + (this.field_187136_p.nextDouble() - this.field_187136_p.nextDouble()) * 0.5D;
                  double d4 = (double)i + (this.field_187136_p.nextDouble() - this.field_187136_p.nextDouble()) * 0.5D;
                  double d5 = (double)k + (this.field_187136_p.nextDouble() - this.field_187136_p.nextDouble()) * 0.5D;
                  double d6 = (double)MathHelper.func_76133_a(d3 * d3 + d4 * d4 + d5 * d5) / p_92035_1_ + this.field_187136_p.nextGaussian() * 0.05D;
                  this.func_92034_a(d0, d1, d2, d3 / d6, d4 / d6, d5 / d6, p_92035_4_, p_92035_5_, p_92035_6_, p_92035_7_);
                  if (i != -p_92035_3_ && i != p_92035_3_ && j != -p_92035_3_ && j != p_92035_3_) {
                     k += p_92035_3_ * 2 - 1;
                  }
               }
            }
         }

      }

      private void func_92038_a(double p_92038_1_, double[][] p_92038_3_, int[] p_92038_4_, int[] p_92038_5_, boolean p_92038_6_, boolean p_92038_7_, boolean p_92038_8_) {
         double d0 = p_92038_3_[0][0];
         double d1 = p_92038_3_[0][1];
         this.func_92034_a(this.field_187126_f, this.field_187127_g, this.field_187128_h, d0 * p_92038_1_, d1 * p_92038_1_, 0.0D, p_92038_4_, p_92038_5_, p_92038_6_, p_92038_7_);
         float f = this.field_187136_p.nextFloat() * 3.1415927F;
         double d2 = p_92038_8_ ? 0.034D : 0.34D;

         for(int i = 0; i < 3; ++i) {
            double d3 = (double)f + (double)((float)i * 3.1415927F) * d2;
            double d4 = d0;
            double d5 = d1;

            for(int j = 1; j < p_92038_3_.length; ++j) {
               double d6 = p_92038_3_[j][0];
               double d7 = p_92038_3_[j][1];

               for(double d8 = 0.25D; d8 <= 1.0D; d8 += 0.25D) {
                  double d9 = (d4 + (d6 - d4) * d8) * p_92038_1_;
                  double d10 = (d5 + (d7 - d5) * d8) * p_92038_1_;
                  double d11 = d9 * Math.sin(d3);
                  d9 = d9 * Math.cos(d3);

                  for(double d12 = -1.0D; d12 <= 1.0D; d12 += 2.0D) {
                     this.func_92034_a(this.field_187126_f, this.field_187127_g, this.field_187128_h, d9 * d12, d10, d11 * d12, p_92038_4_, p_92038_5_, p_92038_6_, p_92038_7_);
                  }
               }

               d4 = d6;
               d5 = d7;
            }
         }

      }

      private void func_92036_a(int[] p_92036_1_, int[] p_92036_2_, boolean p_92036_3_, boolean p_92036_4_) {
         double d0 = this.field_187136_p.nextGaussian() * 0.05D;
         double d1 = this.field_187136_p.nextGaussian() * 0.05D;

         for(int i = 0; i < 70; ++i) {
            double d2 = this.field_187129_i * 0.5D + this.field_187136_p.nextGaussian() * 0.15D + d0;
            double d3 = this.field_187131_k * 0.5D + this.field_187136_p.nextGaussian() * 0.15D + d1;
            double d4 = this.field_187130_j * 0.5D + this.field_187136_p.nextDouble() * 0.5D;
            this.func_92034_a(this.field_187126_f, this.field_187127_g, this.field_187128_h, d2, d4, d3, p_92036_1_, p_92036_2_, p_92036_3_, p_92036_4_);
         }

      }

      public int func_70537_b() {
         return 0;
      }
   }
}
