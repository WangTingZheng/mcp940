package net.minecraft.client.particle;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ParticleLava extends Particle {
   private final float field_70586_a;

   protected ParticleLava(World p_i1215_1_, double p_i1215_2_, double p_i1215_4_, double p_i1215_6_) {
      super(p_i1215_1_, p_i1215_2_, p_i1215_4_, p_i1215_6_, 0.0D, 0.0D, 0.0D);
      this.field_187129_i *= 0.800000011920929D;
      this.field_187130_j *= 0.800000011920929D;
      this.field_187131_k *= 0.800000011920929D;
      this.field_187130_j = (double)(this.field_187136_p.nextFloat() * 0.4F + 0.05F);
      this.field_70552_h = 1.0F;
      this.field_70553_i = 1.0F;
      this.field_70551_j = 1.0F;
      this.field_70544_f *= this.field_187136_p.nextFloat() * 2.0F + 0.2F;
      this.field_70586_a = this.field_70544_f;
      this.field_70547_e = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
      this.func_70536_a(49);
   }

   public int func_189214_a(float p_189214_1_) {
      int i = super.func_189214_a(p_189214_1_);
      int j = 240;
      int k = i >> 16 & 255;
      return 240 | k << 16;
   }

   public void func_180434_a(BufferBuilder p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
      float f = ((float)this.field_70546_d + p_180434_3_) / (float)this.field_70547_e;
      this.field_70544_f = this.field_70586_a * (1.0F - f * f);
      super.func_180434_a(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
   }

   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      if (this.field_70546_d++ >= this.field_70547_e) {
         this.func_187112_i();
      }

      float f = (float)this.field_70546_d / (float)this.field_70547_e;
      if (this.field_187136_p.nextFloat() > f) {
         this.field_187122_b.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, this.field_187126_f, this.field_187127_g, this.field_187128_h, this.field_187129_i, this.field_187130_j, this.field_187131_k);
      }

      this.field_187130_j -= 0.03D;
      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      this.field_187129_i *= 0.9990000128746033D;
      this.field_187130_j *= 0.9990000128746033D;
      this.field_187131_k *= 0.9990000128746033D;
      if (this.field_187132_l) {
         this.field_187129_i *= 0.699999988079071D;
         this.field_187131_k *= 0.699999988079071D;
      }

   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleLava(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_);
      }
   }
}
