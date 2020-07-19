package net.minecraft.client.renderer.block.model;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandom;

public class WeightedBakedModel implements IBakedModel {
   private final int field_177567_a;
   private final List<WeightedBakedModel.WeightedModel> field_177565_b;
   private final IBakedModel field_177566_c;

   public WeightedBakedModel(List<WeightedBakedModel.WeightedModel> p_i46073_1_) {
      this.field_177565_b = p_i46073_1_;
      this.field_177567_a = WeightedRandom.func_76272_a(p_i46073_1_);
      this.field_177566_c = (p_i46073_1_.get(0)).field_185281_b;
   }

   private IBakedModel func_188627_a(long p_188627_1_) {
      return ((WeightedBakedModel.WeightedModel)WeightedRandom.func_180166_a(this.field_177565_b, Math.abs((int)p_188627_1_ >> 16) % this.field_177567_a)).field_185281_b;
   }

   public List<BakedQuad> func_188616_a(@Nullable IBlockState p_188616_1_, @Nullable EnumFacing p_188616_2_, long p_188616_3_) {
      return this.func_188627_a(p_188616_3_).func_188616_a(p_188616_1_, p_188616_2_, p_188616_3_);
   }

   public boolean func_177555_b() {
      return this.field_177566_c.func_177555_b();
   }

   public boolean func_177556_c() {
      return this.field_177566_c.func_177556_c();
   }

   public boolean func_188618_c() {
      return this.field_177566_c.func_188618_c();
   }

   public TextureAtlasSprite func_177554_e() {
      return this.field_177566_c.func_177554_e();
   }

   public ItemCameraTransforms func_177552_f() {
      return this.field_177566_c.func_177552_f();
   }

   public ItemOverrideList func_188617_f() {
      return this.field_177566_c.func_188617_f();
   }

   public static class Builder {
      private final List<WeightedBakedModel.WeightedModel> field_177678_a = Lists.<WeightedBakedModel.WeightedModel>newArrayList();

      public WeightedBakedModel.Builder func_177677_a(IBakedModel p_177677_1_, int p_177677_2_) {
         this.field_177678_a.add(new WeightedBakedModel.WeightedModel(p_177677_1_, p_177677_2_));
         return this;
      }

      public WeightedBakedModel func_177676_a() {
         Collections.sort(this.field_177678_a);
         return new WeightedBakedModel(this.field_177678_a);
      }

      public IBakedModel func_177675_b() {
         return (this.field_177678_a.get(0)).field_185281_b;
      }
   }

   static class WeightedModel extends WeightedRandom.Item implements Comparable<WeightedBakedModel.WeightedModel> {
      protected final IBakedModel field_185281_b;

      public WeightedModel(IBakedModel p_i46763_1_, int p_i46763_2_) {
         super(p_i46763_2_);
         this.field_185281_b = p_i46763_1_;
      }

      public int compareTo(WeightedBakedModel.WeightedModel p_compareTo_1_) {
         return ComparisonChain.start().compare(p_compareTo_1_.field_76292_a, this.field_76292_a).result();
      }

      public String toString() {
         return "MyWeighedRandomItem{weight=" + this.field_76292_a + ", model=" + this.field_185281_b + '}';
      }
   }
}
