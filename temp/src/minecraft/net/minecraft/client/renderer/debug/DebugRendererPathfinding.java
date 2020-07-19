package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

public class DebugRendererPathfinding implements DebugRenderer.IDebugRenderer {
   private final Minecraft field_188290_a;
   private final Map<Integer, Path> field_188291_b = Maps.<Integer, Path>newHashMap();
   private final Map<Integer, Float> field_188292_c = Maps.<Integer, Float>newHashMap();
   private final Map<Integer, Long> field_188293_d = Maps.<Integer, Long>newHashMap();
   private EntityPlayer field_190068_e;
   private double field_190069_f;
   private double field_190070_g;
   private double field_190071_h;

   public DebugRendererPathfinding(Minecraft p_i46556_1_) {
      this.field_188290_a = p_i46556_1_;
   }

   public void func_188289_a(int p_188289_1_, Path p_188289_2_, float p_188289_3_) {
      this.field_188291_b.put(Integer.valueOf(p_188289_1_), p_188289_2_);
      this.field_188293_d.put(Integer.valueOf(p_188289_1_), Long.valueOf(System.currentTimeMillis()));
      this.field_188292_c.put(Integer.valueOf(p_188289_1_), Float.valueOf(p_188289_3_));
   }

   public void func_190060_a(float p_190060_1_, long p_190060_2_) {
      if (!this.field_188291_b.isEmpty()) {
         long i = System.currentTimeMillis();
         this.field_190068_e = this.field_188290_a.field_71439_g;
         this.field_190069_f = this.field_190068_e.field_70142_S + (this.field_190068_e.field_70165_t - this.field_190068_e.field_70142_S) * (double)p_190060_1_;
         this.field_190070_g = this.field_190068_e.field_70137_T + (this.field_190068_e.field_70163_u - this.field_190068_e.field_70137_T) * (double)p_190060_1_;
         this.field_190071_h = this.field_190068_e.field_70136_U + (this.field_190068_e.field_70161_v - this.field_190068_e.field_70136_U) * (double)p_190060_1_;
         GlStateManager.func_179094_E();
         GlStateManager.func_179147_l();
         GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
         GlStateManager.func_179131_c(0.0F, 1.0F, 0.0F, 0.75F);
         GlStateManager.func_179090_x();
         GlStateManager.func_187441_d(6.0F);

         for(Integer integer : this.field_188291_b.keySet()) {
            Path path = this.field_188291_b.get(integer);
            float f = ((Float)this.field_188292_c.get(integer)).floatValue();
            this.func_190067_a(p_190060_1_, path);
            PathPoint pathpoint = path.func_189964_i();
            if (this.func_190066_a(pathpoint) <= 40.0F) {
               RenderGlobal.func_189696_b((new AxisAlignedBB((double)((float)pathpoint.field_75839_a + 0.25F), (double)((float)pathpoint.field_75837_b + 0.25F), (double)pathpoint.field_75838_c + 0.25D, (double)((float)pathpoint.field_75839_a + 0.75F), (double)((float)pathpoint.field_75837_b + 0.75F), (double)((float)pathpoint.field_75838_c + 0.75F))).func_72317_d(-this.field_190069_f, -this.field_190070_g, -this.field_190071_h), 0.0F, 1.0F, 0.0F, 0.5F);

               for(int j = 0; j < path.func_75874_d(); ++j) {
                  PathPoint pathpoint1 = path.func_75877_a(j);
                  if (this.func_190066_a(pathpoint1) <= 40.0F) {
                     float f1 = j == path.func_75873_e() ? 1.0F : 0.0F;
                     float f2 = j == path.func_75873_e() ? 0.0F : 1.0F;
                     RenderGlobal.func_189696_b((new AxisAlignedBB((double)((float)pathpoint1.field_75839_a + 0.5F - f), (double)((float)pathpoint1.field_75837_b + 0.01F * (float)j), (double)((float)pathpoint1.field_75838_c + 0.5F - f), (double)((float)pathpoint1.field_75839_a + 0.5F + f), (double)((float)pathpoint1.field_75837_b + 0.25F + 0.01F * (float)j), (double)((float)pathpoint1.field_75838_c + 0.5F + f))).func_72317_d(-this.field_190069_f, -this.field_190070_g, -this.field_190071_h), f1, 0.0F, f2, 0.5F);
                  }
               }
            }
         }

         for(Integer integer1 : this.field_188291_b.keySet()) {
            Path path1 = this.field_188291_b.get(integer1);

            for(PathPoint pathpoint3 : path1.func_189965_h()) {
               if (this.func_190066_a(pathpoint3) <= 40.0F) {
                  DebugRenderer.func_190076_a(String.format("%s", pathpoint3.field_186287_m), (double)pathpoint3.field_75839_a + 0.5D, (double)pathpoint3.field_75837_b + 0.75D, (double)pathpoint3.field_75838_c + 0.5D, p_190060_1_, -65536);
                  DebugRenderer.func_190076_a(String.format("%.2f", pathpoint3.field_186286_l), (double)pathpoint3.field_75839_a + 0.5D, (double)pathpoint3.field_75837_b + 0.25D, (double)pathpoint3.field_75838_c + 0.5D, p_190060_1_, -65536);
               }
            }

            for(PathPoint pathpoint4 : path1.func_189966_g()) {
               if (this.func_190066_a(pathpoint4) <= 40.0F) {
                  DebugRenderer.func_190076_a(String.format("%s", pathpoint4.field_186287_m), (double)pathpoint4.field_75839_a + 0.5D, (double)pathpoint4.field_75837_b + 0.75D, (double)pathpoint4.field_75838_c + 0.5D, p_190060_1_, -16776961);
                  DebugRenderer.func_190076_a(String.format("%.2f", pathpoint4.field_186286_l), (double)pathpoint4.field_75839_a + 0.5D, (double)pathpoint4.field_75837_b + 0.25D, (double)pathpoint4.field_75838_c + 0.5D, p_190060_1_, -16776961);
               }
            }

            for(int k = 0; k < path1.func_75874_d(); ++k) {
               PathPoint pathpoint2 = path1.func_75877_a(k);
               if (this.func_190066_a(pathpoint2) <= 40.0F) {
                  DebugRenderer.func_190076_a(String.format("%s", pathpoint2.field_186287_m), (double)pathpoint2.field_75839_a + 0.5D, (double)pathpoint2.field_75837_b + 0.75D, (double)pathpoint2.field_75838_c + 0.5D, p_190060_1_, -1);
                  DebugRenderer.func_190076_a(String.format("%.2f", pathpoint2.field_186286_l), (double)pathpoint2.field_75839_a + 0.5D, (double)pathpoint2.field_75837_b + 0.25D, (double)pathpoint2.field_75838_c + 0.5D, p_190060_1_, -1);
               }
            }
         }

         for(Integer integer2 : (Integer[])this.field_188293_d.keySet().toArray(new Integer[0])) {
            if (i - ((Long)this.field_188293_d.get(integer2)).longValue() > 20000L) {
               this.field_188291_b.remove(integer2);
               this.field_188293_d.remove(integer2);
            }
         }

         GlStateManager.func_179098_w();
         GlStateManager.func_179084_k();
         GlStateManager.func_179121_F();
      }
   }

   public void func_190067_a(float p_190067_1_, Path p_190067_2_) {
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);

      for(int i = 0; i < p_190067_2_.func_75874_d(); ++i) {
         PathPoint pathpoint = p_190067_2_.func_75877_a(i);
         if (this.func_190066_a(pathpoint) <= 40.0F) {
            float f = (float)i / (float)p_190067_2_.func_75874_d() * 0.33F;
            int j = i == 0 ? 0 : MathHelper.func_181758_c(f, 0.9F, 0.9F);
            int k = j >> 16 & 255;
            int l = j >> 8 & 255;
            int i1 = j & 255;
            bufferbuilder.func_181662_b((double)pathpoint.field_75839_a - this.field_190069_f + 0.5D, (double)pathpoint.field_75837_b - this.field_190070_g + 0.5D, (double)pathpoint.field_75838_c - this.field_190071_h + 0.5D).func_181669_b(k, l, i1, 255).func_181675_d();
         }
      }

      tessellator.func_78381_a();
   }

   private float func_190066_a(PathPoint p_190066_1_) {
      return (float)(Math.abs((double)p_190066_1_.field_75839_a - this.field_190068_e.field_70165_t) + Math.abs((double)p_190066_1_.field_75837_b - this.field_190068_e.field_70163_u) + Math.abs((double)p_190066_1_.field_75838_c - this.field_190068_e.field_70161_v));
   }
}
