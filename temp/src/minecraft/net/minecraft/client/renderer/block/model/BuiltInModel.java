package net.minecraft.client.renderer.block.model;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class BuiltInModel implements IBakedModel {
   private final ItemCameraTransforms field_177557_a;
   private final ItemOverrideList field_188619_b;

   public BuiltInModel(ItemCameraTransforms p_i46537_1_, ItemOverrideList p_i46537_2_) {
      this.field_177557_a = p_i46537_1_;
      this.field_188619_b = p_i46537_2_;
   }

   public List<BakedQuad> func_188616_a(@Nullable IBlockState p_188616_1_, @Nullable EnumFacing p_188616_2_, long p_188616_3_) {
      return Collections.<BakedQuad>emptyList();
   }

   public boolean func_177555_b() {
      return false;
   }

   public boolean func_177556_c() {
      return true;
   }

   public boolean func_188618_c() {
      return true;
   }

   public TextureAtlasSprite func_177554_e() {
      return null;
   }

   public ItemCameraTransforms func_177552_f() {
      return this.field_177557_a;
   }

   public ItemOverrideList func_188617_f() {
      return this.field_188619_b;
   }
}
