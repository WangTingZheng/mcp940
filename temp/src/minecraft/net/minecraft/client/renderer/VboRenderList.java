package net.minecraft.client.renderer;

import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.BlockRenderLayer;

public class VboRenderList extends ChunkRenderContainer {
   public void func_178001_a(BlockRenderLayer p_178001_1_) {
      if (this.field_178007_b) {
         for(RenderChunk renderchunk : this.field_178009_a) {
            VertexBuffer vertexbuffer = renderchunk.func_178565_b(p_178001_1_.ordinal());
            GlStateManager.func_179094_E();
            this.func_178003_a(renderchunk);
            renderchunk.func_178572_f();
            vertexbuffer.func_177359_a();
            this.func_178010_a();
            vertexbuffer.func_177358_a(7);
            GlStateManager.func_179121_F();
         }

         OpenGlHelper.func_176072_g(OpenGlHelper.field_176089_P, 0);
         GlStateManager.func_179117_G();
         this.field_178009_a.clear();
      }
   }

   private void func_178010_a() {
      GlStateManager.func_187420_d(3, 5126, 28, 0);
      GlStateManager.func_187406_e(4, 5121, 28, 12);
      GlStateManager.func_187405_c(2, 5126, 28, 16);
      OpenGlHelper.func_77472_b(OpenGlHelper.field_77476_b);
      GlStateManager.func_187405_c(2, 5122, 28, 24);
      OpenGlHelper.func_77472_b(OpenGlHelper.field_77478_a);
   }
}
