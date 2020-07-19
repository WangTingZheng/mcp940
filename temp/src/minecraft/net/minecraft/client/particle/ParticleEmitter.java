package net.minecraft.client.particle;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ParticleEmitter extends Particle {
   private final Entity field_174851_a;
   private int field_174852_ax;
   private final int field_174850_ay;
   private final EnumParticleTypes field_174849_az;

   public ParticleEmitter(World p_i46279_1_, Entity p_i46279_2_, EnumParticleTypes p_i46279_3_) {
      this(p_i46279_1_, p_i46279_2_, p_i46279_3_, 3);
   }

   public ParticleEmitter(World p_i47219_1_, Entity p_i47219_2_, EnumParticleTypes p_i47219_3_, int p_i47219_4_) {
      super(p_i47219_1_, p_i47219_2_.field_70165_t, p_i47219_2_.func_174813_aQ().field_72338_b + (double)(p_i47219_2_.field_70131_O / 2.0F), p_i47219_2_.field_70161_v, p_i47219_2_.field_70159_w, p_i47219_2_.field_70181_x, p_i47219_2_.field_70179_y);
      this.field_174851_a = p_i47219_2_;
      this.field_174850_ay = p_i47219_4_;
      this.field_174849_az = p_i47219_3_;
      this.func_189213_a();
   }

   public void func_180434_a(BufferBuilder p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
   }

   public void func_189213_a() {
      for(int i = 0; i < 16; ++i) {
         double d0 = (double)(this.field_187136_p.nextFloat() * 2.0F - 1.0F);
         double d1 = (double)(this.field_187136_p.nextFloat() * 2.0F - 1.0F);
         double d2 = (double)(this.field_187136_p.nextFloat() * 2.0F - 1.0F);
         if (d0 * d0 + d1 * d1 + d2 * d2 <= 1.0D) {
            double d3 = this.field_174851_a.field_70165_t + d0 * (double)this.field_174851_a.field_70130_N / 4.0D;
            double d4 = this.field_174851_a.func_174813_aQ().field_72338_b + (double)(this.field_174851_a.field_70131_O / 2.0F) + d1 * (double)this.field_174851_a.field_70131_O / 4.0D;
            double d5 = this.field_174851_a.field_70161_v + d2 * (double)this.field_174851_a.field_70130_N / 4.0D;
            this.field_187122_b.func_175682_a(this.field_174849_az, false, d3, d4, d5, d0, d1 + 0.2D, d2);
         }
      }

      ++this.field_174852_ax;
      if (this.field_174852_ax >= this.field_174850_ay) {
         this.func_187112_i();
      }

   }

   public int func_70537_b() {
      return 3;
   }
}
