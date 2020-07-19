package net.minecraft.client.renderer.entity;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.MapData;

public class RenderItemFrame extends Render<EntityItemFrame> {
   private static final ResourceLocation field_110789_a = new ResourceLocation("textures/map/map_background.png");
   private final Minecraft field_147917_g = Minecraft.func_71410_x();
   private final ModelResourceLocation field_177072_f = new ModelResourceLocation("item_frame", "normal");
   private final ModelResourceLocation field_177073_g = new ModelResourceLocation("item_frame", "map");
   private final RenderItem field_177074_h;

   public RenderItemFrame(RenderManager p_i46166_1_, RenderItem p_i46166_2_) {
      super(p_i46166_1_);
      this.field_177074_h = p_i46166_2_;
   }

   public void func_76986_a(EntityItemFrame p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      GlStateManager.func_179094_E();
      BlockPos blockpos = p_76986_1_.func_174857_n();
      double d0 = (double)blockpos.func_177958_n() - p_76986_1_.field_70165_t + p_76986_2_;
      double d1 = (double)blockpos.func_177956_o() - p_76986_1_.field_70163_u + p_76986_4_;
      double d2 = (double)blockpos.func_177952_p() - p_76986_1_.field_70161_v + p_76986_6_;
      GlStateManager.func_179137_b(d0 + 0.5D, d1 + 0.5D, d2 + 0.5D);
      GlStateManager.func_179114_b(180.0F - p_76986_1_.field_70177_z, 0.0F, 1.0F, 0.0F);
      this.field_76990_c.field_78724_e.func_110577_a(TextureMap.field_110575_b);
      BlockRendererDispatcher blockrendererdispatcher = this.field_147917_g.func_175602_ab();
      ModelManager modelmanager = blockrendererdispatcher.func_175023_a().func_178126_b();
      IBakedModel ibakedmodel;
      if (p_76986_1_.func_82335_i().func_77973_b() == Items.field_151098_aY) {
         ibakedmodel = modelmanager.func_174953_a(this.field_177073_g);
      } else {
         ibakedmodel = modelmanager.func_174953_a(this.field_177072_f);
      }

      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b(-0.5F, -0.5F, -0.5F);
      if (this.field_188301_f) {
         GlStateManager.func_179142_g();
         GlStateManager.func_187431_e(this.func_188298_c(p_76986_1_));
      }

      blockrendererdispatcher.func_175019_b().func_178262_a(ibakedmodel, 1.0F, 1.0F, 1.0F, 1.0F);
      if (this.field_188301_f) {
         GlStateManager.func_187417_n();
         GlStateManager.func_179119_h();
      }

      GlStateManager.func_179121_F();
      GlStateManager.func_179109_b(0.0F, 0.0F, 0.4375F);
      this.func_82402_b(p_76986_1_);
      GlStateManager.func_179121_F();
      this.func_177067_a(p_76986_1_, p_76986_2_ + (double)((float)p_76986_1_.field_174860_b.func_82601_c() * 0.3F), p_76986_4_ - 0.25D, p_76986_6_ + (double)((float)p_76986_1_.field_174860_b.func_82599_e() * 0.3F));
   }

   @Nullable
   protected ResourceLocation func_110775_a(EntityItemFrame p_110775_1_) {
      return null;
   }

   private void func_82402_b(EntityItemFrame p_82402_1_) {
      ItemStack itemstack = p_82402_1_.func_82335_i();
      if (!itemstack.func_190926_b()) {
         GlStateManager.func_179094_E();
         GlStateManager.func_179140_f();
         boolean flag = itemstack.func_77973_b() == Items.field_151098_aY;
         int i = flag ? p_82402_1_.func_82333_j() % 4 * 2 : p_82402_1_.func_82333_j();
         GlStateManager.func_179114_b((float)i * 360.0F / 8.0F, 0.0F, 0.0F, 1.0F);
         if (flag) {
            this.field_76990_c.field_78724_e.func_110577_a(field_110789_a);
            GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
            float f = 0.0078125F;
            GlStateManager.func_179152_a(0.0078125F, 0.0078125F, 0.0078125F);
            GlStateManager.func_179109_b(-64.0F, -64.0F, 0.0F);
            MapData mapdata = Items.field_151098_aY.func_77873_a(itemstack, p_82402_1_.field_70170_p);
            GlStateManager.func_179109_b(0.0F, 0.0F, -1.0F);
            if (mapdata != null) {
               this.field_147917_g.field_71460_t.func_147701_i().func_148250_a(mapdata, true);
            }
         } else {
            GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
            GlStateManager.func_179123_a();
            RenderHelper.func_74519_b();
            this.field_177074_h.func_181564_a(itemstack, ItemCameraTransforms.TransformType.FIXED);
            RenderHelper.func_74518_a();
            GlStateManager.func_179099_b();
         }

         GlStateManager.func_179145_e();
         GlStateManager.func_179121_F();
      }
   }

   protected void func_177067_a(EntityItemFrame p_177067_1_, double p_177067_2_, double p_177067_4_, double p_177067_6_) {
      if (Minecraft.func_71382_s() && !p_177067_1_.func_82335_i().func_190926_b() && p_177067_1_.func_82335_i().func_82837_s() && this.field_76990_c.field_147941_i == p_177067_1_) {
         double d0 = p_177067_1_.func_70068_e(this.field_76990_c.field_78734_h);
         float f = p_177067_1_.func_70093_af() ? 32.0F : 64.0F;
         if (d0 < (double)(f * f)) {
            String s = p_177067_1_.func_82335_i().func_82833_r();
            this.func_147906_a(p_177067_1_, s, p_177067_2_, p_177067_4_, p_177067_6_, 64);
         }
      }
   }
}
