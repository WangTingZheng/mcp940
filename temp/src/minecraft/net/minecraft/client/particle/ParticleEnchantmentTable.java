package net.minecraft.client.particle;

import net.minecraft.world.World;

public class ParticleEnchantmentTable extends Particle {
   private final float field_70565_a;
   private final double field_70568_aq;
   private final double field_70567_ar;
   private final double field_70566_as;

   protected ParticleEnchantmentTable(World p_i1204_1_, double p_i1204_2_, double p_i1204_4_, double p_i1204_6_, double p_i1204_8_, double p_i1204_10_, double p_i1204_12_) {
      super(p_i1204_1_, p_i1204_2_, p_i1204_4_, p_i1204_6_, p_i1204_8_, p_i1204_10_, p_i1204_12_);
      this.field_187129_i = p_i1204_8_;
      this.field_187130_j = p_i1204_10_;
      this.field_187131_k = p_i1204_12_;
      this.field_70568_aq = p_i1204_2_;
      this.field_70567_ar = p_i1204_4_;
      this.field_70566_as = p_i1204_6_;
      this.field_187123_c = p_i1204_2_ + p_i1204_8_;
      this.field_187124_d = p_i1204_4_ + p_i1204_10_;
      this.field_187125_e = p_i1204_6_ + p_i1204_12_;
      this.field_187126_f = this.field_187123_c;
      this.field_187127_g = this.field_187124_d;
      this.field_187128_h = this.field_187125_e;
      float f = this.field_187136_p.nextFloat() * 0.6F + 0.4F;
      this.field_70544_f = this.field_187136_p.nextFloat() * 0.5F + 0.2F;
      this.field_70565_a = this.field_70544_f;
      this.field_70552_h = 0.9F * f;
      this.field_70553_i = 0.9F * f;
      this.field_70551_j = f;
      this.field_70547_e = (int)(Math.random() * 10.0D) + 30;
      this.func_70536_a((int)(Math.random() * 26.0D + 1.0D + 224.0D));
   }

   public void func_187110_a(double p_187110_1_, double p_187110_3_, double p_187110_5_) {
      this.func_187108_a(this.func_187116_l().func_72317_d(p_187110_1_, p_187110_3_, p_187110_5_));
      this.func_187118_j();
   }

   public int func_189214_a(float p_189214_1_) {
      int i = super.func_189214_a(p_189214_1_);
      float f = (float)this.field_70546_d / (float)this.field_70547_e;
      f = f * f;
      f = f * f;
      int j = i & 255;
      int k = i >> 16 & 255;
      k = k + (int)(f * 15.0F * 16.0F);
      if (k > 240) {
         k = 240;
      }

      return j | k << 16;
   }

   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      float f = (float)this.field_70546_d / (float)this.field_70547_e;
      f = 1.0F - f;
      float f1 = 1.0F - f;
      f1 = f1 * f1;
      f1 = f1 * f1;
      this.field_187126_f = this.field_70568_aq + this.field_187129_i * (double)f;
      this.field_187127_g = this.field_70567_ar + this.field_187130_j * (double)f - (double)(f1 * 1.2F);
      this.field_187128_h = this.field_70566_as + this.field_187131_k * (double)f;
      if (this.field_70546_d++ >= this.field_70547_e) {
         this.func_187112_i();
      }

   }

   public static class EnchantmentTable implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleEnchantmentTable(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
      }
   }
}
