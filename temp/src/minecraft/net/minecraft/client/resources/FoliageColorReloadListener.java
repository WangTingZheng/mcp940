package net.minecraft.client.resources;

import java.io.IOException;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ColorizerFoliage;

public class FoliageColorReloadListener implements IResourceManagerReloadListener {
   private static final ResourceLocation field_130079_a = new ResourceLocation("textures/colormap/foliage.png");

   public void func_110549_a(IResourceManager p_110549_1_) {
      try {
         ColorizerFoliage.func_77467_a(TextureUtil.func_110986_a(p_110549_1_, field_130079_a));
      } catch (IOException var3) {
         ;
      }

   }
}
