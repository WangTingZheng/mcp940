package net.minecraft.client.renderer.debug;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;

public class DebugRenderer {
   public final DebugRenderer.IDebugRenderer field_188286_a;
   public final DebugRenderer.IDebugRenderer field_188287_b;
   public final DebugRenderer.IDebugRenderer field_190077_c;
   public final DebugRenderer.IDebugRenderer field_190078_d;
   public final DebugRenderer.IDebugRenderer field_191325_e;
   public final DebugRenderer.IDebugRenderer field_191557_f;
   public final DebugRenderer.IDebugRenderer field_193852_g;
   private boolean field_190079_e;
   private boolean field_190080_f;
   private boolean field_190081_g;
   private boolean field_190082_h;
   private boolean field_191326_j;
   private boolean field_191558_l;
   private boolean field_193853_n;

   public DebugRenderer(Minecraft p_i46557_1_) {
      this.field_188286_a = new DebugRendererPathfinding(p_i46557_1_);
      this.field_188287_b = new DebugRendererWater(p_i46557_1_);
      this.field_190077_c = new DebugRendererChunkBorder(p_i46557_1_);
      this.field_190078_d = new DebugRendererHeightMap(p_i46557_1_);
      this.field_191325_e = new DebugRendererCollisionBox(p_i46557_1_);
      this.field_191557_f = new DebugRendererNeighborsUpdate(p_i46557_1_);
      this.field_193852_g = new DebugRendererSolidFace(p_i46557_1_);
   }

   public boolean func_190074_a() {
      return this.field_190079_e || this.field_190080_f || this.field_190081_g || this.field_190082_h || this.field_191326_j || this.field_191558_l || this.field_193853_n;
   }

   public boolean func_190075_b() {
      this.field_190079_e = !this.field_190079_e;
      return this.field_190079_e;
   }

   public void func_190073_a(float p_190073_1_, long p_190073_2_) {
      if (this.field_190080_f) {
         this.field_188286_a.func_190060_a(p_190073_1_, p_190073_2_);
      }

      if (this.field_190079_e && !Minecraft.func_71410_x().func_189648_am()) {
         this.field_190077_c.func_190060_a(p_190073_1_, p_190073_2_);
      }

      if (this.field_190081_g) {
         this.field_188287_b.func_190060_a(p_190073_1_, p_190073_2_);
      }

      if (this.field_190082_h) {
         this.field_190078_d.func_190060_a(p_190073_1_, p_190073_2_);
      }

      if (this.field_191326_j) {
         this.field_191325_e.func_190060_a(p_190073_1_, p_190073_2_);
      }

      if (this.field_191558_l) {
         this.field_191557_f.func_190060_a(p_190073_1_, p_190073_2_);
      }

      if (this.field_193853_n) {
         this.field_193852_g.func_190060_a(p_190073_1_, p_190073_2_);
      }

   }

   public static void func_191556_a(String p_191556_0_, int p_191556_1_, int p_191556_2_, int p_191556_3_, float p_191556_4_, int p_191556_5_) {
      func_190076_a(p_191556_0_, (double)p_191556_1_ + 0.5D, (double)p_191556_2_ + 0.5D, (double)p_191556_3_ + 0.5D, p_191556_4_, p_191556_5_);
   }

   public static void func_190076_a(String p_190076_0_, double p_190076_1_, double p_190076_3_, double p_190076_5_, float p_190076_7_, int p_190076_8_) {
      Minecraft minecraft = Minecraft.func_71410_x();
      if (minecraft.field_71439_g != null && minecraft.func_175598_ae() != null && minecraft.func_175598_ae().field_78733_k != null) {
         FontRenderer fontrenderer = minecraft.field_71466_p;
         EntityPlayer entityplayer = minecraft.field_71439_g;
         double d0 = entityplayer.field_70142_S + (entityplayer.field_70165_t - entityplayer.field_70142_S) * (double)p_190076_7_;
         double d1 = entityplayer.field_70137_T + (entityplayer.field_70163_u - entityplayer.field_70137_T) * (double)p_190076_7_;
         double d2 = entityplayer.field_70136_U + (entityplayer.field_70161_v - entityplayer.field_70136_U) * (double)p_190076_7_;
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b((float)(p_190076_1_ - d0), (float)(p_190076_3_ - d1) + 0.07F, (float)(p_190076_5_ - d2));
         GlStateManager.func_187432_a(0.0F, 1.0F, 0.0F);
         GlStateManager.func_179152_a(0.02F, -0.02F, 0.02F);
         RenderManager rendermanager = minecraft.func_175598_ae();
         GlStateManager.func_179114_b(-rendermanager.field_78735_i, 0.0F, 1.0F, 0.0F);
         GlStateManager.func_179114_b((float)(rendermanager.field_78733_k.field_74320_O == 2 ? 1 : -1) * rendermanager.field_78732_j, 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179140_f();
         GlStateManager.func_179098_w();
         GlStateManager.func_179126_j();
         GlStateManager.func_179132_a(true);
         GlStateManager.func_179152_a(-1.0F, 1.0F, 1.0F);
         fontrenderer.func_78276_b(p_190076_0_, -fontrenderer.func_78256_a(p_190076_0_) / 2, 0, p_190076_8_);
         GlStateManager.func_179145_e();
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179121_F();
      }
   }

   public interface IDebugRenderer {
      void func_190060_a(float var1, long var2);
   }
}
