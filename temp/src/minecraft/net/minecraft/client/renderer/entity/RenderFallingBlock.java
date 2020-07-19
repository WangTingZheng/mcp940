package net.minecraft.client.renderer.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class RenderFallingBlock extends Render<EntityFallingBlock> {
   public RenderFallingBlock(RenderManager p_i46177_1_) {
      super(p_i46177_1_);
      this.field_76989_e = 0.5F;
   }

   public void func_76986_a(EntityFallingBlock p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      if (p_76986_1_.func_175131_l() != null) {
         IBlockState iblockstate = p_76986_1_.func_175131_l();
         if (iblockstate.func_185901_i() == EnumBlockRenderType.MODEL) {
            World world = p_76986_1_.func_145807_e();
            if (iblockstate != world.func_180495_p(new BlockPos(p_76986_1_)) && iblockstate.func_185901_i() != EnumBlockRenderType.INVISIBLE) {
               this.func_110776_a(TextureMap.field_110575_b);
               GlStateManager.func_179094_E();
               GlStateManager.func_179140_f();
               Tessellator tessellator = Tessellator.func_178181_a();
               BufferBuilder bufferbuilder = tessellator.func_178180_c();
               if (this.field_188301_f) {
                  GlStateManager.func_179142_g();
                  GlStateManager.func_187431_e(this.func_188298_c(p_76986_1_));
               }

               bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_176600_a);
               BlockPos blockpos = new BlockPos(p_76986_1_.field_70165_t, p_76986_1_.func_174813_aQ().field_72337_e, p_76986_1_.field_70161_v);
               GlStateManager.func_179109_b((float)(p_76986_2_ - (double)blockpos.func_177958_n() - 0.5D), (float)(p_76986_4_ - (double)blockpos.func_177956_o()), (float)(p_76986_6_ - (double)blockpos.func_177952_p() - 0.5D));
               BlockRendererDispatcher blockrendererdispatcher = Minecraft.func_71410_x().func_175602_ab();
               blockrendererdispatcher.func_175019_b().func_187493_a(world, blockrendererdispatcher.func_184389_a(iblockstate), iblockstate, blockpos, bufferbuilder, false, MathHelper.func_180186_a(p_76986_1_.func_184531_j()));
               tessellator.func_78381_a();
               if (this.field_188301_f) {
                  GlStateManager.func_187417_n();
                  GlStateManager.func_179119_h();
               }

               GlStateManager.func_179145_e();
               GlStateManager.func_179121_F();
               super.func_76986_a(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
            }
         }
      }
   }

   protected ResourceLocation func_110775_a(EntityFallingBlock p_110775_1_) {
      return TextureMap.field_110575_b;
   }
}
