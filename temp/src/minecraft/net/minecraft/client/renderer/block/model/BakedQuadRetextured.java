package net.minecraft.client.renderer.block.model;

import java.util.Arrays;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class BakedQuadRetextured extends BakedQuad {
   private final TextureAtlasSprite field_178218_d;

   public BakedQuadRetextured(BakedQuad p_i46217_1_, TextureAtlasSprite p_i46217_2_) {
      super(Arrays.copyOf(p_i46217_1_.func_178209_a(), p_i46217_1_.func_178209_a().length), p_i46217_1_.field_178213_b, FaceBakery.func_178410_a(p_i46217_1_.func_178209_a()), p_i46217_1_.func_187508_a());
      this.field_178218_d = p_i46217_2_;
      this.func_178217_e();
   }

   private void func_178217_e() {
      for(int i = 0; i < 4; ++i) {
         int j = 7 * i;
         this.field_178215_a[j + 4] = Float.floatToRawIntBits(this.field_178218_d.func_94214_a((double)this.field_187509_d.func_188537_a(Float.intBitsToFloat(this.field_178215_a[j + 4]))));
         this.field_178215_a[j + 4 + 1] = Float.floatToRawIntBits(this.field_178218_d.func_94207_b((double)this.field_187509_d.func_188536_b(Float.intBitsToFloat(this.field_178215_a[j + 4 + 1]))));
      }

   }
}
