package net.minecraft.client.particle;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleDragonBreath extends Particle {
   private final float field_187143_a;
   private boolean field_187144_G;

   protected ParticleDragonBreath(World p_i46581_1_, double p_i46581_2_, double p_i46581_4_, double p_i46581_6_, double p_i46581_8_, double p_i46581_10_, double p_i46581_12_) {
      super(p_i46581_1_, p_i46581_2_, p_i46581_4_, p_i46581_6_, p_i46581_8_, p_i46581_10_, p_i46581_12_);
      this.field_187129_i = p_i46581_8_;
      this.field_187130_j = p_i46581_10_;
      this.field_187131_k = p_i46581_12_;
      this.field_70552_h = MathHelper.func_151240_a(this.field_187136_p, 0.7176471F, 0.8745098F);
      this.field_70553_i = MathHelper.func_151240_a(this.field_187136_p, 0.0F, 0.0F);
      this.field_70551_j = MathHelper.func_151240_a(this.field_187136_p, 0.8235294F, 0.9764706F);
      this.field_70544_f *= 0.75F;
      this.field_187143_a = this.field_70544_f;
      this.field_70547_e = (int)(20.0D / ((double)this.field_187136_p.nextFloat() * 0.8D + 0.2D));
      this.field_187144_G = false;
   }

   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      if (this.field_70546_d++ >= this.field_70547_e) {
         this.func_187112_i();
      } else {
         this.func_70536_a(3 * this.field_70546_d / this.field_70547_e + 5);
         if (this.field_187132_l) {
            this.field_187130_j = 0.0D;
            this.field_187144_G = true;
         }

         if (this.field_187144_G) {
            this.field_187130_j += 0.002D;
         }

         this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
         if (this.field_187127_g == this.field_187124_d) {
            this.field_187129_i *= 1.1D;
            this.field_187131_k *= 1.1D;
         }

         this.field_187129_i *= 0.9599999785423279D;
         this.field_187131_k *= 0.9599999785423279D;
         if (this.field_187144_G) {
            this.field_187130_j *= 0.9599999785423279D;
         }

      }
   }

   public void func_180434_a(BufferBuilder p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
      this.field_70544_f = this.field_187143_a * MathHelper.func_76131_a(((float)this.field_70546_d + p_180434_3_) / (float)this.field_70547_e * 32.0F, 0.0F, 1.0F);
      super.func_180434_a(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleDragonBreath(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
      }
   }
}
