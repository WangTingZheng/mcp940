package net.minecraft.client.renderer.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelMinecart;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RenderMinecart<T extends EntityMinecart> extends Render<T> {
   private static final ResourceLocation field_110804_g = new ResourceLocation("textures/entity/minecart.png");
   protected ModelBase field_77013_a = new ModelMinecart();

   public RenderMinecart(RenderManager p_i46155_1_) {
      super(p_i46155_1_);
      this.field_76989_e = 0.5F;
   }

   public void func_76986_a(T p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      GlStateManager.func_179094_E();
      this.func_180548_c(p_76986_1_);
      long i = (long)p_76986_1_.func_145782_y() * 493286711L;
      i = i * i * 4392167121L + i * 98761L;
      float f = (((float)(i >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      float f1 = (((float)(i >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      float f2 = (((float)(i >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      GlStateManager.func_179109_b(f, f1, f2);
      double d0 = p_76986_1_.field_70142_S + (p_76986_1_.field_70165_t - p_76986_1_.field_70142_S) * (double)p_76986_9_;
      double d1 = p_76986_1_.field_70137_T + (p_76986_1_.field_70163_u - p_76986_1_.field_70137_T) * (double)p_76986_9_;
      double d2 = p_76986_1_.field_70136_U + (p_76986_1_.field_70161_v - p_76986_1_.field_70136_U) * (double)p_76986_9_;
      double d3 = 0.30000001192092896D;
      Vec3d vec3d = p_76986_1_.func_70489_a(d0, d1, d2);
      float f3 = p_76986_1_.field_70127_C + (p_76986_1_.field_70125_A - p_76986_1_.field_70127_C) * p_76986_9_;
      if (vec3d != null) {
         Vec3d vec3d1 = p_76986_1_.func_70495_a(d0, d1, d2, 0.30000001192092896D);
         Vec3d vec3d2 = p_76986_1_.func_70495_a(d0, d1, d2, -0.30000001192092896D);
         if (vec3d1 == null) {
            vec3d1 = vec3d;
         }

         if (vec3d2 == null) {
            vec3d2 = vec3d;
         }

         p_76986_2_ += vec3d.field_72450_a - d0;
         p_76986_4_ += (vec3d1.field_72448_b + vec3d2.field_72448_b) / 2.0D - d1;
         p_76986_6_ += vec3d.field_72449_c - d2;
         Vec3d vec3d3 = vec3d2.func_72441_c(-vec3d1.field_72450_a, -vec3d1.field_72448_b, -vec3d1.field_72449_c);
         if (vec3d3.func_72433_c() != 0.0D) {
            vec3d3 = vec3d3.func_72432_b();
            p_76986_8_ = (float)(Math.atan2(vec3d3.field_72449_c, vec3d3.field_72450_a) * 180.0D / 3.141592653589793D);
            f3 = (float)(Math.atan(vec3d3.field_72448_b) * 73.0D);
         }
      }

      GlStateManager.func_179109_b((float)p_76986_2_, (float)p_76986_4_ + 0.375F, (float)p_76986_6_);
      GlStateManager.func_179114_b(180.0F - p_76986_8_, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(-f3, 0.0F, 0.0F, 1.0F);
      float f5 = (float)p_76986_1_.func_70496_j() - p_76986_9_;
      float f6 = p_76986_1_.func_70491_i() - p_76986_9_;
      if (f6 < 0.0F) {
         f6 = 0.0F;
      }

      if (f5 > 0.0F) {
         GlStateManager.func_179114_b(MathHelper.func_76126_a(f5) * f5 * f6 / 10.0F * (float)p_76986_1_.func_70493_k(), 1.0F, 0.0F, 0.0F);
      }

      int j = p_76986_1_.func_94099_q();
      if (this.field_188301_f) {
         GlStateManager.func_179142_g();
         GlStateManager.func_187431_e(this.func_188298_c(p_76986_1_));
      }

      IBlockState iblockstate = p_76986_1_.func_174897_t();
      if (iblockstate.func_185901_i() != EnumBlockRenderType.INVISIBLE) {
         GlStateManager.func_179094_E();
         this.func_110776_a(TextureMap.field_110575_b);
         float f4 = 0.75F;
         GlStateManager.func_179152_a(0.75F, 0.75F, 0.75F);
         GlStateManager.func_179109_b(-0.5F, (float)(j - 8) / 16.0F, 0.5F);
         this.func_188319_a(p_76986_1_, p_76986_9_, iblockstate);
         GlStateManager.func_179121_F();
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_180548_c(p_76986_1_);
      }

      GlStateManager.func_179152_a(-1.0F, -1.0F, 1.0F);
      this.field_77013_a.func_78088_a(p_76986_1_, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.func_179121_F();
      if (this.field_188301_f) {
         GlStateManager.func_187417_n();
         GlStateManager.func_179119_h();
      }

      super.func_76986_a(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   protected ResourceLocation func_110775_a(T p_110775_1_) {
      return field_110804_g;
   }

   protected void func_188319_a(T p_188319_1_, float p_188319_2_, IBlockState p_188319_3_) {
      GlStateManager.func_179094_E();
      Minecraft.func_71410_x().func_175602_ab().func_175016_a(p_188319_3_, p_188319_1_.func_70013_c());
      GlStateManager.func_179121_F();
   }
}
