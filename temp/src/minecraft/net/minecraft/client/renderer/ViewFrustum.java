package net.minecraft.client.renderer;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ViewFrustum {
   protected final RenderGlobal field_178169_a;
   protected final World field_178167_b;
   protected int field_178168_c;
   protected int field_178165_d;
   protected int field_178166_e;
   public RenderChunk[] field_178164_f;

   public ViewFrustum(World p_i46246_1_, int p_i46246_2_, RenderGlobal p_i46246_3_, IRenderChunkFactory p_i46246_4_) {
      this.field_178169_a = p_i46246_3_;
      this.field_178167_b = p_i46246_1_;
      this.func_178159_a(p_i46246_2_);
      this.func_178158_a(p_i46246_4_);
   }

   protected void func_178158_a(IRenderChunkFactory p_178158_1_) {
      int i = this.field_178165_d * this.field_178168_c * this.field_178166_e;
      this.field_178164_f = new RenderChunk[i];
      int j = 0;

      for(int k = 0; k < this.field_178165_d; ++k) {
         for(int l = 0; l < this.field_178168_c; ++l) {
            for(int i1 = 0; i1 < this.field_178166_e; ++i1) {
               int j1 = (i1 * this.field_178168_c + l) * this.field_178165_d + k;
               this.field_178164_f[j1] = p_178158_1_.func_189565_a(this.field_178167_b, this.field_178169_a, j++);
               this.field_178164_f[j1].func_189562_a(k * 16, l * 16, i1 * 16);
            }
         }
      }

   }

   public void func_178160_a() {
      for(RenderChunk renderchunk : this.field_178164_f) {
         renderchunk.func_178566_a();
      }

   }

   protected void func_178159_a(int p_178159_1_) {
      int i = p_178159_1_ * 2 + 1;
      this.field_178165_d = i;
      this.field_178168_c = 16;
      this.field_178166_e = i;
   }

   public void func_178163_a(double p_178163_1_, double p_178163_3_) {
      int i = MathHelper.func_76128_c(p_178163_1_) - 8;
      int j = MathHelper.func_76128_c(p_178163_3_) - 8;
      int k = this.field_178165_d * 16;

      for(int l = 0; l < this.field_178165_d; ++l) {
         int i1 = this.func_178157_a(i, k, l);

         for(int j1 = 0; j1 < this.field_178166_e; ++j1) {
            int k1 = this.func_178157_a(j, k, j1);

            for(int l1 = 0; l1 < this.field_178168_c; ++l1) {
               int i2 = l1 * 16;
               RenderChunk renderchunk = this.field_178164_f[(j1 * this.field_178168_c + l1) * this.field_178165_d + l];
               renderchunk.func_189562_a(i1, i2, k1);
            }
         }
      }

   }

   private int func_178157_a(int p_178157_1_, int p_178157_2_, int p_178157_3_) {
      int i = p_178157_3_ * 16;
      int j = i - p_178157_1_ + p_178157_2_ / 2;
      if (j < 0) {
         j -= p_178157_2_ - 1;
      }

      return i - j / p_178157_2_ * p_178157_2_;
   }

   public void func_187474_a(int p_187474_1_, int p_187474_2_, int p_187474_3_, int p_187474_4_, int p_187474_5_, int p_187474_6_, boolean p_187474_7_) {
      int i = MathHelper.func_76137_a(p_187474_1_, 16);
      int j = MathHelper.func_76137_a(p_187474_2_, 16);
      int k = MathHelper.func_76137_a(p_187474_3_, 16);
      int l = MathHelper.func_76137_a(p_187474_4_, 16);
      int i1 = MathHelper.func_76137_a(p_187474_5_, 16);
      int j1 = MathHelper.func_76137_a(p_187474_6_, 16);

      for(int k1 = i; k1 <= l; ++k1) {
         int l1 = k1 % this.field_178165_d;
         if (l1 < 0) {
            l1 += this.field_178165_d;
         }

         for(int i2 = j; i2 <= i1; ++i2) {
            int j2 = i2 % this.field_178168_c;
            if (j2 < 0) {
               j2 += this.field_178168_c;
            }

            for(int k2 = k; k2 <= j1; ++k2) {
               int l2 = k2 % this.field_178166_e;
               if (l2 < 0) {
                  l2 += this.field_178166_e;
               }

               int i3 = (l2 * this.field_178168_c + j2) * this.field_178165_d + l1;
               RenderChunk renderchunk = this.field_178164_f[i3];
               renderchunk.func_178575_a(p_187474_7_);
            }
         }
      }

   }

   @Nullable
   protected RenderChunk func_178161_a(BlockPos p_178161_1_) {
      int i = MathHelper.func_76137_a(p_178161_1_.func_177958_n(), 16);
      int j = MathHelper.func_76137_a(p_178161_1_.func_177956_o(), 16);
      int k = MathHelper.func_76137_a(p_178161_1_.func_177952_p(), 16);
      if (j >= 0 && j < this.field_178168_c) {
         i = i % this.field_178165_d;
         if (i < 0) {
            i += this.field_178165_d;
         }

         k = k % this.field_178166_e;
         if (k < 0) {
            k += this.field_178166_e;
         }

         int l = (k * this.field_178168_c + j) * this.field_178165_d + i;
         return this.field_178164_f[l];
      } else {
         return null;
      }
   }
}
