package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.world.World;

public class ParticleSpell extends Particle {
   private static final Random field_174848_a = new Random();
   private int field_70590_a = 128;

   protected ParticleSpell(World p_i1229_1_, double p_i1229_2_, double p_i1229_4_, double p_i1229_6_, double p_i1229_8_, double p_i1229_10_, double p_i1229_12_) {
      super(p_i1229_1_, p_i1229_2_, p_i1229_4_, p_i1229_6_, 0.5D - field_174848_a.nextDouble(), p_i1229_10_, 0.5D - field_174848_a.nextDouble());
      this.field_187130_j *= 0.20000000298023224D;
      if (p_i1229_8_ == 0.0D && p_i1229_12_ == 0.0D) {
         this.field_187129_i *= 0.10000000149011612D;
         this.field_187131_k *= 0.10000000149011612D;
      }

      this.field_70544_f *= 0.75F;
      this.field_70547_e = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
   }

   public boolean func_187111_c() {
      return true;
   }

   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      if (this.field_70546_d++ >= this.field_70547_e) {
         this.func_187112_i();
      }

      this.func_70536_a(this.field_70590_a + (7 - this.field_70546_d * 8 / this.field_70547_e));
      this.field_187130_j += 0.004D;
      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      if (this.field_187127_g == this.field_187124_d) {
         this.field_187129_i *= 1.1D;
         this.field_187131_k *= 1.1D;
      }

      this.field_187129_i *= 0.9599999785423279D;
      this.field_187130_j *= 0.9599999785423279D;
      this.field_187131_k *= 0.9599999785423279D;
      if (this.field_187132_l) {
         this.field_187129_i *= 0.699999988079071D;
         this.field_187131_k *= 0.699999988079071D;
      }

   }

   public void func_70589_b(int p_70589_1_) {
      this.field_70590_a = p_70589_1_;
   }

   public static class AmbientMobFactory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         Particle particle = new ParticleSpell(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
         particle.func_82338_g(0.15F);
         particle.func_70538_b((float)p_178902_9_, (float)p_178902_11_, (float)p_178902_13_);
         return particle;
      }
   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleSpell(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
      }
   }

   public static class InstantFactory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         Particle particle = new ParticleSpell(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
         ((ParticleSpell)particle).func_70589_b(144);
         return particle;
      }
   }

   public static class MobFactory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         Particle particle = new ParticleSpell(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
         particle.func_70538_b((float)p_178902_9_, (float)p_178902_11_, (float)p_178902_13_);
         return particle;
      }
   }

   public static class WitchFactory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         Particle particle = new ParticleSpell(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
         ((ParticleSpell)particle).func_70589_b(144);
         float f = p_178902_2_.field_73012_v.nextFloat() * 0.5F + 0.35F;
         particle.func_70538_b(1.0F * f, 0.0F * f, 1.0F * f);
         return particle;
      }
   }
}
