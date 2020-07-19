package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;

public class ListedRenderChunk extends RenderChunk {
   private final int field_178601_d = GLAllocation.func_74526_a(BlockRenderLayer.values().length);

   public ListedRenderChunk(World p_i47121_1_, RenderGlobal p_i47121_2_, int p_i47121_3_) {
      super(p_i47121_1_, p_i47121_2_, p_i47121_3_);
   }

   public int func_178600_a(BlockRenderLayer p_178600_1_, CompiledChunk p_178600_2_) {
      return !p_178600_2_.func_178491_b(p_178600_1_) ? this.field_178601_d + p_178600_1_.ordinal() : -1;
   }

   public void func_178566_a() {
      super.func_178566_a();
      GLAllocation.func_178874_a(this.field_178601_d, BlockRenderLayer.values().length);
   }
}
