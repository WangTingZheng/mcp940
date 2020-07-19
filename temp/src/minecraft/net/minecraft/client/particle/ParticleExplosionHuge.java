package net.minecraft.client.particle;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ParticleExplosionHuge extends Particle {
   private int field_70579_a;
   private final int field_70580_aq = 8;

   protected ParticleExplosionHuge(World p_i1214_1_, double p_i1214_2_, double p_i1214_4_, double p_i1214_6_, double p_i1214_8_, double p_i1214_10_, double p_i1214_12_) {
      super(p_i1214_1_, p_i1214_2_, p_i1214_4_, p_i1214_6_, 0.0D, 0.0D, 0.0D);
   }

   public void func_180434_a(BufferBuilder p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
   }

   public void func_189213_a() {
      for(int i = 0; i < 6; ++i) {
         double d0 = this.field_187126_f + (this.field_187136_p.nextDouble() - this.field_187136_p.nextDouble()) * 4.0D;
         double d1 = this.field_187127_g + (this.field_187136_p.nextDouble() - this.field_187136_p.nextDouble()) * 4.0D;
         double d2 = this.field_187128_h + (this.field_187136_p.nextDouble() - this.field_187136_p.nextDouble()) * 4.0D;
         this.field_187122_b.func_175688_a(EnumParticleTypes.EXPLOSION_LARGE, d0, d1, d2, (double)((float)this.field_70579_a / (float)this.field_70580_aq), 0.0D, 0.0D);
      }

      ++this.field_70579_a;
      if (this.field_70579_a == this.field_70580_aq) {
         this.func_187112_i();
      }

   }

   public int func_70537_b() {
      return 1;
   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleExplosionHuge(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
      }
   }
}
