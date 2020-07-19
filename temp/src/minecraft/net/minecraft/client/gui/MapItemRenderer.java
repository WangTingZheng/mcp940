package net.minecraft.client.gui;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;

public class MapItemRenderer {
   private static final ResourceLocation field_148253_a = new ResourceLocation("textures/map/map_icons.png");
   private final TextureManager field_148251_b;
   private final Map<String, MapItemRenderer.Instance> field_148252_c = Maps.<String, MapItemRenderer.Instance>newHashMap();

   public MapItemRenderer(TextureManager p_i45009_1_) {
      this.field_148251_b = p_i45009_1_;
   }

   public void func_148246_a(MapData p_148246_1_) {
      this.func_148248_b(p_148246_1_).func_148236_a();
   }

   public void func_148250_a(MapData p_148250_1_, boolean p_148250_2_) {
      this.func_148248_b(p_148250_1_).func_148237_a(p_148250_2_);
   }

   private MapItemRenderer.Instance func_148248_b(MapData p_148248_1_) {
      MapItemRenderer.Instance mapitemrenderer$instance = this.field_148252_c.get(p_148248_1_.field_76190_i);
      if (mapitemrenderer$instance == null) {
         mapitemrenderer$instance = new MapItemRenderer.Instance(p_148248_1_);
         this.field_148252_c.put(p_148248_1_.field_76190_i, mapitemrenderer$instance);
      }

      return mapitemrenderer$instance;
   }

   @Nullable
   public MapItemRenderer.Instance func_191205_a(String p_191205_1_) {
      return this.field_148252_c.get(p_191205_1_);
   }

   public void func_148249_a() {
      for(MapItemRenderer.Instance mapitemrenderer$instance : this.field_148252_c.values()) {
         this.field_148251_b.func_147645_c(mapitemrenderer$instance.field_148240_d);
      }

      this.field_148252_c.clear();
   }

   @Nullable
   public MapData func_191207_a(@Nullable MapItemRenderer.Instance p_191207_1_) {
      return p_191207_1_ != null ? p_191207_1_.field_148242_b : null;
   }

   class Instance {
      private final MapData field_148242_b;
      private final DynamicTexture field_148243_c;
      private final ResourceLocation field_148240_d;
      private final int[] field_148241_e;

      private Instance(MapData p_i45007_2_) {
         this.field_148242_b = p_i45007_2_;
         this.field_148243_c = new DynamicTexture(128, 128);
         this.field_148241_e = this.field_148243_c.func_110565_c();
         this.field_148240_d = MapItemRenderer.this.field_148251_b.func_110578_a("map/" + p_i45007_2_.field_76190_i, this.field_148243_c);

         for(int i = 0; i < this.field_148241_e.length; ++i) {
            this.field_148241_e[i] = 0;
         }

      }

      private void func_148236_a() {
         for(int i = 0; i < 16384; ++i) {
            int j = this.field_148242_b.field_76198_e[i] & 255;
            if (j / 4 == 0) {
               this.field_148241_e[i] = (i + i / 128 & 1) * 8 + 16 << 24;
            } else {
               this.field_148241_e[i] = MapColor.field_76281_a[j / 4].func_151643_b(j & 3);
            }
         }

         this.field_148243_c.func_110564_a();
      }

      private void func_148237_a(boolean p_148237_1_) {
         int i = 0;
         int j = 0;
         Tessellator tessellator = Tessellator.func_178181_a();
         BufferBuilder bufferbuilder = tessellator.func_178180_c();
         float f = 0.0F;
         MapItemRenderer.this.field_148251_b.func_110577_a(this.field_148240_d);
         GlStateManager.func_179147_l();
         GlStateManager.func_187428_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
         GlStateManager.func_179118_c();
         bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
         bufferbuilder.func_181662_b(0.0D, 128.0D, -0.009999999776482582D).func_187315_a(0.0D, 1.0D).func_181675_d();
         bufferbuilder.func_181662_b(128.0D, 128.0D, -0.009999999776482582D).func_187315_a(1.0D, 1.0D).func_181675_d();
         bufferbuilder.func_181662_b(128.0D, 0.0D, -0.009999999776482582D).func_187315_a(1.0D, 0.0D).func_181675_d();
         bufferbuilder.func_181662_b(0.0D, 0.0D, -0.009999999776482582D).func_187315_a(0.0D, 0.0D).func_181675_d();
         tessellator.func_78381_a();
         GlStateManager.func_179141_d();
         GlStateManager.func_179084_k();
         MapItemRenderer.this.field_148251_b.func_110577_a(MapItemRenderer.field_148253_a);
         int k = 0;

         for(MapDecoration mapdecoration : this.field_148242_b.field_76203_h.values()) {
            if (!p_148237_1_ || mapdecoration.func_191180_f()) {
               GlStateManager.func_179094_E();
               GlStateManager.func_179109_b(0.0F + (float)mapdecoration.func_176112_b() / 2.0F + 64.0F, 0.0F + (float)mapdecoration.func_176113_c() / 2.0F + 64.0F, -0.02F);
               GlStateManager.func_179114_b((float)(mapdecoration.func_176111_d() * 360) / 16.0F, 0.0F, 0.0F, 1.0F);
               GlStateManager.func_179152_a(4.0F, 4.0F, 3.0F);
               GlStateManager.func_179109_b(-0.125F, 0.125F, 0.0F);
               byte b0 = mapdecoration.func_176110_a();
               float f1 = (float)(b0 % 4 + 0) / 4.0F;
               float f2 = (float)(b0 / 4 + 0) / 4.0F;
               float f3 = (float)(b0 % 4 + 1) / 4.0F;
               float f4 = (float)(b0 / 4 + 1) / 4.0F;
               bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
               float f5 = -0.001F;
               bufferbuilder.func_181662_b(-1.0D, 1.0D, (double)((float)k * -0.001F)).func_187315_a((double)f1, (double)f2).func_181675_d();
               bufferbuilder.func_181662_b(1.0D, 1.0D, (double)((float)k * -0.001F)).func_187315_a((double)f3, (double)f2).func_181675_d();
               bufferbuilder.func_181662_b(1.0D, -1.0D, (double)((float)k * -0.001F)).func_187315_a((double)f3, (double)f4).func_181675_d();
               bufferbuilder.func_181662_b(-1.0D, -1.0D, (double)((float)k * -0.001F)).func_187315_a((double)f1, (double)f4).func_181675_d();
               tessellator.func_78381_a();
               GlStateManager.func_179121_F();
               ++k;
            }
         }

         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b(0.0F, 0.0F, -0.04F);
         GlStateManager.func_179152_a(1.0F, 1.0F, 1.0F);
         GlStateManager.func_179121_F();
      }
   }
}
