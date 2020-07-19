package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.world.World;

public class ListChunkFactory implements IRenderChunkFactory {
   public RenderChunk func_189565_a(World p_189565_1_, RenderGlobal p_189565_2_, int p_189565_3_) {
      return new ListedRenderChunk(p_189565_1_, p_189565_2_, p_189565_3_);
   }
}
