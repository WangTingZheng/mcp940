package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class Barrier extends Particle {
   protected Barrier(World p_i46286_1_, double p_i46286_2_, double p_i46286_4_, double p_i46286_6_, Item p_i46286_8_) {
      super(p_i46286_1_, p_i46286_2_, p_i46286_4_, p_i46286_6_, 0.0D, 0.0D, 0.0D);
      this.func_187117_a(Minecraft.func_71410_x().func_175599_af().func_175037_a().func_178082_a(p_i46286_8_));
      this.field_70552_h = 1.0F;
      this.field_70553_i = 1.0F;
      this.field_70551_j = 1.0F;
      this.field_187129_i = 0.0D;
      this.field_187130_j = 0.0D;
      this.field_187131_k = 0.0D;
      this.field_70545_g = 0.0F;
      this.field_70547_e = 80;
   }

   public int func_70537_b() {
      return 1;
   }

   public void func_180434_a(BufferBuilder p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
      float f = this.field_187119_C.func_94209_e();
      float f1 = this.field_187119_C.func_94212_f();
      float f2 = this.field_187119_C.func_94206_g();
      float f3 = this.field_187119_C.func_94210_h();
      float f4 = 0.5F;
      float f5 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * (double)p_180434_3_ - field_70556_an);
      float f6 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * (double)p_180434_3_ - field_70554_ao);
      float f7 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * (double)p_180434_3_ - field_70555_ap);
      int i = this.func_189214_a(p_180434_3_);
      int j = i >> 16 & '\uffff';
      int k = i & '\uffff';
      p_180434_1_.func_181662_b((double)(f5 - p_180434_4_ * 0.5F - p_180434_7_ * 0.5F), (double)(f6 - p_180434_5_ * 0.5F), (double)(f7 - p_180434_6_ * 0.5F - p_180434_8_ * 0.5F)).func_187315_a((double)f1, (double)f3).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F).func_187314_a(j, k).func_181675_d();
      p_180434_1_.func_181662_b((double)(f5 - p_180434_4_ * 0.5F + p_180434_7_ * 0.5F), (double)(f6 + p_180434_5_ * 0.5F), (double)(f7 - p_180434_6_ * 0.5F + p_180434_8_ * 0.5F)).func_187315_a((double)f1, (double)f2).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F).func_187314_a(j, k).func_181675_d();
      p_180434_1_.func_181662_b((double)(f5 + p_180434_4_ * 0.5F + p_180434_7_ * 0.5F), (double)(f6 + p_180434_5_ * 0.5F), (double)(f7 + p_180434_6_ * 0.5F + p_180434_8_ * 0.5F)).func_187315_a((double)f, (double)f2).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F).func_187314_a(j, k).func_181675_d();
      p_180434_1_.func_181662_b((double)(f5 + p_180434_4_ * 0.5F - p_180434_7_ * 0.5F), (double)(f6 - p_180434_5_ * 0.5F), (double)(f7 + p_180434_6_ * 0.5F - p_180434_8_ * 0.5F)).func_187315_a((double)f, (double)f3).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F).func_187314_a(j, k).func_181675_d();
   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return new Barrier(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, Item.func_150898_a(Blocks.field_180401_cv));
      }
   }
}
