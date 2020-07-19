package net.minecraft.client.renderer;

import net.minecraft.util.BlockRenderLayer;

public class RegionRenderCacheBuilder {
   private final BufferBuilder[] field_179040_a = new BufferBuilder[BlockRenderLayer.values().length];

   public RegionRenderCacheBuilder() {
      this.field_179040_a[BlockRenderLayer.SOLID.ordinal()] = new BufferBuilder(2097152);
      this.field_179040_a[BlockRenderLayer.CUTOUT.ordinal()] = new BufferBuilder(131072);
      this.field_179040_a[BlockRenderLayer.CUTOUT_MIPPED.ordinal()] = new BufferBuilder(131072);
      this.field_179040_a[BlockRenderLayer.TRANSLUCENT.ordinal()] = new BufferBuilder(262144);
   }

   public BufferBuilder func_179038_a(BlockRenderLayer p_179038_1_) {
      return this.field_179040_a[p_179038_1_.ordinal()];
   }

   public BufferBuilder func_179039_a(int p_179039_1_) {
      return this.field_179040_a[p_179039_1_];
   }
}
