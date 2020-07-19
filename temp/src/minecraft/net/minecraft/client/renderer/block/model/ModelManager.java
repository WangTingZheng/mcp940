package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.registry.IRegistry;

public class ModelManager implements IResourceManagerReloadListener {
   private IRegistry<ModelResourceLocation, IBakedModel> field_174958_a;
   private final TextureMap field_174956_b;
   private final BlockModelShapes field_174957_c;
   private IBakedModel field_174955_d;

   public ModelManager(TextureMap p_i46082_1_) {
      this.field_174956_b = p_i46082_1_;
      this.field_174957_c = new BlockModelShapes(this);
   }

   public void func_110549_a(IResourceManager p_110549_1_) {
      ModelBakery modelbakery = new ModelBakery(p_110549_1_, this.field_174956_b, this.field_174957_c);
      this.field_174958_a = modelbakery.func_177570_a();
      this.field_174955_d = this.field_174958_a.func_82594_a(ModelBakery.field_177604_a);
      this.field_174957_c.func_178124_c();
   }

   public IBakedModel func_174953_a(ModelResourceLocation p_174953_1_) {
      if (p_174953_1_ == null) {
         return this.field_174955_d;
      } else {
         IBakedModel ibakedmodel = this.field_174958_a.func_82594_a(p_174953_1_);
         return ibakedmodel == null ? this.field_174955_d : ibakedmodel;
      }
   }

   public IBakedModel func_174951_a() {
      return this.field_174955_d;
   }

   public TextureMap func_174952_b() {
      return this.field_174956_b;
   }

   public BlockModelShapes func_174954_c() {
      return this.field_174957_c;
   }
}
