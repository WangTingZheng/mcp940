package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ParticleDigging extends Particle {
   private final IBlockState field_174847_a;
   private BlockPos field_181019_az;

   protected ParticleDigging(World p_i46280_1_, double p_i46280_2_, double p_i46280_4_, double p_i46280_6_, double p_i46280_8_, double p_i46280_10_, double p_i46280_12_, IBlockState p_i46280_14_) {
      super(p_i46280_1_, p_i46280_2_, p_i46280_4_, p_i46280_6_, p_i46280_8_, p_i46280_10_, p_i46280_12_);
      this.field_174847_a = p_i46280_14_;
      this.func_187117_a(Minecraft.func_71410_x().func_175602_ab().func_175023_a().func_178122_a(p_i46280_14_));
      this.field_70545_g = p_i46280_14_.func_177230_c().field_149763_I;
      this.field_70552_h = 0.6F;
      this.field_70553_i = 0.6F;
      this.field_70551_j = 0.6F;
      this.field_70544_f /= 2.0F;
   }

   public ParticleDigging func_174846_a(BlockPos p_174846_1_) {
      this.field_181019_az = p_174846_1_;
      if (this.field_174847_a.func_177230_c() == Blocks.field_150349_c) {
         return this;
      } else {
         this.func_187154_b(p_174846_1_);
         return this;
      }
   }

   public ParticleDigging func_174845_l() {
      this.field_181019_az = new BlockPos(this.field_187126_f, this.field_187127_g, this.field_187128_h);
      Block block = this.field_174847_a.func_177230_c();
      if (block == Blocks.field_150349_c) {
         return this;
      } else {
         this.func_187154_b(this.field_181019_az);
         return this;
      }
   }

   protected void func_187154_b(@Nullable BlockPos p_187154_1_) {
      int i = Minecraft.func_71410_x().func_184125_al().func_186724_a(this.field_174847_a, this.field_187122_b, p_187154_1_, 0);
      this.field_70552_h *= (float)(i >> 16 & 255) / 255.0F;
      this.field_70553_i *= (float)(i >> 8 & 255) / 255.0F;
      this.field_70551_j *= (float)(i & 255) / 255.0F;
   }

   public int func_70537_b() {
      return 1;
   }

   public void func_180434_a(BufferBuilder p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
      float f = ((float)this.field_94054_b + this.field_70548_b / 4.0F) / 16.0F;
      float f1 = f + 0.015609375F;
      float f2 = ((float)this.field_94055_c + this.field_70549_c / 4.0F) / 16.0F;
      float f3 = f2 + 0.015609375F;
      float f4 = 0.1F * this.field_70544_f;
      if (this.field_187119_C != null) {
         f = this.field_187119_C.func_94214_a((double)(this.field_70548_b / 4.0F * 16.0F));
         f1 = this.field_187119_C.func_94214_a((double)((this.field_70548_b + 1.0F) / 4.0F * 16.0F));
         f2 = this.field_187119_C.func_94207_b((double)(this.field_70549_c / 4.0F * 16.0F));
         f3 = this.field_187119_C.func_94207_b((double)((this.field_70549_c + 1.0F) / 4.0F * 16.0F));
      }

      float f5 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * (double)p_180434_3_ - field_70556_an);
      float f6 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * (double)p_180434_3_ - field_70554_ao);
      float f7 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * (double)p_180434_3_ - field_70555_ap);
      int i = this.func_189214_a(p_180434_3_);
      int j = i >> 16 & '\uffff';
      int k = i & '\uffff';
      p_180434_1_.func_181662_b((double)(f5 - p_180434_4_ * f4 - p_180434_7_ * f4), (double)(f6 - p_180434_5_ * f4), (double)(f7 - p_180434_6_ * f4 - p_180434_8_ * f4)).func_187315_a((double)f, (double)f3).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F).func_187314_a(j, k).func_181675_d();
      p_180434_1_.func_181662_b((double)(f5 - p_180434_4_ * f4 + p_180434_7_ * f4), (double)(f6 + p_180434_5_ * f4), (double)(f7 - p_180434_6_ * f4 + p_180434_8_ * f4)).func_187315_a((double)f, (double)f2).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F).func_187314_a(j, k).func_181675_d();
      p_180434_1_.func_181662_b((double)(f5 + p_180434_4_ * f4 + p_180434_7_ * f4), (double)(f6 + p_180434_5_ * f4), (double)(f7 + p_180434_6_ * f4 + p_180434_8_ * f4)).func_187315_a((double)f1, (double)f2).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F).func_187314_a(j, k).func_181675_d();
      p_180434_1_.func_181662_b((double)(f5 + p_180434_4_ * f4 - p_180434_7_ * f4), (double)(f6 - p_180434_5_ * f4), (double)(f7 + p_180434_6_ * f4 - p_180434_8_ * f4)).func_187315_a((double)f1, (double)f3).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F).func_187314_a(j, k).func_181675_d();
   }

   public int func_189214_a(float p_189214_1_) {
      int i = super.func_189214_a(p_189214_1_);
      int j = 0;
      if (this.field_187122_b.func_175667_e(this.field_181019_az)) {
         j = this.field_187122_b.func_175626_b(this.field_181019_az, 0);
      }

      return i == 0 ? j : i;
   }

   public static class Factory implements IParticleFactory {
      public Particle func_178902_a(int p_178902_1_, World p_178902_2_, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
         return (new ParticleDigging(p_178902_2_, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_, Block.func_176220_d(p_178902_15_[0]))).func_174845_l();
      }
   }
}
