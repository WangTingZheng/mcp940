package net.minecraft.client.renderer.block.model;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class MultipartBakedModel implements IBakedModel {
   private final Map<Predicate<IBlockState>, IBakedModel> field_188626_f;
   protected final boolean field_188621_a;
   protected final boolean field_188622_b;
   protected final TextureAtlasSprite field_188623_c;
   protected final ItemCameraTransforms field_188624_d;
   protected final ItemOverrideList field_188625_e;

   public MultipartBakedModel(Map<Predicate<IBlockState>, IBakedModel> p_i46536_1_) {
      this.field_188626_f = p_i46536_1_;
      IBakedModel ibakedmodel = p_i46536_1_.values().iterator().next();
      this.field_188621_a = ibakedmodel.func_177555_b();
      this.field_188622_b = ibakedmodel.func_177556_c();
      this.field_188623_c = ibakedmodel.func_177554_e();
      this.field_188624_d = ibakedmodel.func_177552_f();
      this.field_188625_e = ibakedmodel.func_188617_f();
   }

   public List<BakedQuad> func_188616_a(@Nullable IBlockState p_188616_1_, @Nullable EnumFacing p_188616_2_, long p_188616_3_) {
      List<BakedQuad> list = Lists.<BakedQuad>newArrayList();
      if (p_188616_1_ != null) {
         for(Entry<Predicate<IBlockState>, IBakedModel> entry : this.field_188626_f.entrySet()) {
            if (((Predicate)entry.getKey()).apply(p_188616_1_)) {
               list.addAll((entry.getValue()).func_188616_a(p_188616_1_, p_188616_2_, p_188616_3_++));
            }
         }
      }

      return list;
   }

   public boolean func_177555_b() {
      return this.field_188621_a;
   }

   public boolean func_177556_c() {
      return this.field_188622_b;
   }

   public boolean func_188618_c() {
      return false;
   }

   public TextureAtlasSprite func_177554_e() {
      return this.field_188623_c;
   }

   public ItemCameraTransforms func_177552_f() {
      return this.field_188624_d;
   }

   public ItemOverrideList func_188617_f() {
      return this.field_188625_e;
   }

   public static class Builder {
      private final Map<Predicate<IBlockState>, IBakedModel> field_188649_a = Maps.<Predicate<IBlockState>, IBakedModel>newLinkedHashMap();

      public void func_188648_a(Predicate<IBlockState> p_188648_1_, IBakedModel p_188648_2_) {
         this.field_188649_a.put(p_188648_1_, p_188648_2_);
      }

      public IBakedModel func_188647_a() {
         return new MultipartBakedModel(this.field_188649_a);
      }
   }
}
