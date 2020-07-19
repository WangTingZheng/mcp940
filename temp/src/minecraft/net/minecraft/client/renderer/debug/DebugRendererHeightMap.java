package net.minecraft.client.renderer.debug;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DebugRendererHeightMap implements DebugRenderer.IDebugRenderer {
   private final Minecraft field_190061_a;

   public DebugRendererHeightMap(Minecraft p_i47133_1_) {
      this.field_190061_a = p_i47133_1_;
   }

   public void func_190060_a(float p_190060_1_, long p_190060_2_) {
      EntityPlayer entityplayer = this.field_190061_a.field_71439_g;
      World world = this.field_190061_a.field_71441_e;
      double d0 = entityplayer.field_70142_S + (entityplayer.field_70165_t - entityplayer.field_70142_S) * (double)p_190060_1_;
      double d1 = entityplayer.field_70137_T + (entityplayer.field_70163_u - entityplayer.field_70137_T) * (double)p_190060_1_;
      double d2 = entityplayer.field_70136_U + (entityplayer.field_70161_v - entityplayer.field_70136_U) * (double)p_190060_1_;
      GlStateManager.func_179094_E();
      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      GlStateManager.func_179090_x();
      BlockPos blockpos = new BlockPos(entityplayer.field_70165_t, 0.0D, entityplayer.field_70161_v);
      Iterable<BlockPos> iterable = BlockPos.func_177980_a(blockpos.func_177982_a(-40, 0, -40), blockpos.func_177982_a(40, 0, 40));
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      bufferbuilder.func_181668_a(5, DefaultVertexFormats.field_181706_f);

      for(BlockPos blockpos1 : iterable) {
         int i = world.func_189649_b(blockpos1.func_177958_n(), blockpos1.func_177952_p());
         if (world.func_180495_p(blockpos1.func_177982_a(0, i, 0).func_177977_b()) == Blocks.field_150350_a.func_176223_P()) {
            RenderGlobal.func_189693_b(bufferbuilder, (double)((float)blockpos1.func_177958_n() + 0.25F) - d0, (double)i - d1, (double)((float)blockpos1.func_177952_p() + 0.25F) - d2, (double)((float)blockpos1.func_177958_n() + 0.75F) - d0, (double)i + 0.09375D - d1, (double)((float)blockpos1.func_177952_p() + 0.75F) - d2, 0.0F, 0.0F, 1.0F, 0.5F);
         } else {
            RenderGlobal.func_189693_b(bufferbuilder, (double)((float)blockpos1.func_177958_n() + 0.25F) - d0, (double)i - d1, (double)((float)blockpos1.func_177952_p() + 0.25F) - d2, (double)((float)blockpos1.func_177958_n() + 0.75F) - d0, (double)i + 0.09375D - d1, (double)((float)blockpos1.func_177952_p() + 0.75F) - d2, 0.0F, 1.0F, 0.0F, 0.5F);
         }
      }

      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179121_F();
   }
}
