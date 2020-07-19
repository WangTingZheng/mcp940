package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;

public abstract class ChunkRenderContainer {
   private double field_178008_c;
   private double field_178005_d;
   private double field_178006_e;
   protected List<RenderChunk> field_178009_a = Lists.<RenderChunk>newArrayListWithCapacity(17424);
   protected boolean field_178007_b;

   public void func_178004_a(double p_178004_1_, double p_178004_3_, double p_178004_5_) {
      this.field_178007_b = true;
      this.field_178009_a.clear();
      this.field_178008_c = p_178004_1_;
      this.field_178005_d = p_178004_3_;
      this.field_178006_e = p_178004_5_;
   }

   public void func_178003_a(RenderChunk p_178003_1_) {
      BlockPos blockpos = p_178003_1_.func_178568_j();
      GlStateManager.func_179109_b((float)((double)blockpos.func_177958_n() - this.field_178008_c), (float)((double)blockpos.func_177956_o() - this.field_178005_d), (float)((double)blockpos.func_177952_p() - this.field_178006_e));
   }

   public void func_178002_a(RenderChunk p_178002_1_, BlockRenderLayer p_178002_2_) {
      this.field_178009_a.add(p_178002_1_);
   }

   public abstract void func_178001_a(BlockRenderLayer var1);
}
