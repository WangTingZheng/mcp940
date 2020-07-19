package net.minecraft.client.particle;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleCloud extends Particle {
   float field_70569_a;

   protected ParticleCloud(World p_i1221_1_, double p_i1221_2_, double p_i1221_4_, double p_i1221_6_, double p_i1221_8_, double p_i1221_10_, double p_i1221_12_) {
      super(p_i1221_1_, p_i1221_2_, p_i1221_4_, p_i1221_6_, 0.0D, 0.0D, 0.0D);
      float f = 2.5F;
      this.field_187129_i *= 0.10000000149011612D;
      this.field_187130_j *= 0.10000000149011612D;
      this.field_187131_k *= 0.10000000149011612D;
      this.field_187129_i += p_i1221_8_;
      this.field_187130_j += p_i1221_10_;
      this.field_187131_k += p_i1221_12_;
      float f1 = 1.0F - (float)(Math.random() * 0.30000001192092896D);
      this.field_70552_h = f1;
      this.field_70553_i = f1;
      this.field_70551_j = f1;
      this.field_70544_f *= 0.75F;
      this.field_70544_f *= 2.5F;
      this.field_70569_a = this.field_70544_f;
      this.field_70547_e = (int)(8.0D / (Math.random() * 0.8D + 0.3D));
      this.field_70547_e = (int)((float)this.field_70547_e * 2.5F);
   }

   public void func_180434_a(BufferBuilder p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
      float f = ((float)this.field_70546_d + p_180434_3_) / (float)this.field_70547_e * 32.0F;
      f = MathHelper.func_76131_a(f, 0.0F, 1.0F);
      this.field_70544_f = this.field_70569_a * f;
      super.func_180434_a(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
   }

   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      if (this.field_70546_d++ >= this.field_70547_e) {
         this.func_187112_i();
      }

      this.func_70536_a(7 - this.field_70546_d * 8 / this.field_70547_e);
      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      this.field_187129_i *= 0.9599999785423279D;
      this.field_187130_j *= 0.9599999785423279D;
      this.field_187131_k *= 0.9599999785423279D;
      EntityPlayer entityplayer = this.field_187122_b.func_184137_a(this.field_187126_f, this.field_187127_g, this.field_187128_h, 2.0D, false);
      if (entityplayer != null) {
         AxisAlignedBB axisalignedbb = entityplayer.func_174813_aQ();
         if (this.field_187127_g > axisalignedbb.field_72338_b) {
            this.field_187127_g += (axisalignedbb.field_72338_b - this.field_187127_g) * 0.2D;
            this.field_187130_j += (entityplayer.field_70181_x - this.field_187130_j) * 0.2D;
            this.func_187109_b(this.field_187126_f, this.field_187127_g, this.field_187128_h);
         }
      }

      if (this.field_187132_l) {
         this.field_187129_i *= 0.699999988079071D;
         this.field_187131_k *= 0.699999988079071D;
      }

   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new ParticleCloud(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
      }
   }
}
