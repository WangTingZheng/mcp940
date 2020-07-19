package net.minecraft.client.renderer.entity.layers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.monster.EntityEnderman;

public class LayerHeldBlock implements LayerRenderer<EntityEnderman> {
   private final RenderEnderman field_177174_a;

   public LayerHeldBlock(RenderEnderman p_i46122_1_) {
      this.field_177174_a = p_i46122_1_;
   }

   public void func_177141_a(EntityEnderman p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
      IBlockState iblockstate = p_177141_1_.func_175489_ck();
      if (iblockstate != null) {
         BlockRendererDispatcher blockrendererdispatcher = Minecraft.func_71410_x().func_175602_ab();
         GlStateManager.func_179091_B();
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b(0.0F, 0.6875F, -0.75F);
         GlStateManager.func_179114_b(20.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179114_b(45.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.func_179109_b(0.25F, 0.1875F, 0.25F);
         float f = 0.5F;
         GlStateManager.func_179152_a(-0.5F, -0.5F, 0.5F);
         int i = p_177141_1_.func_70070_b();
         int j = i % 65536;
         int k = i / 65536;
         OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)j, (float)k);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_177174_a.func_110776_a(TextureMap.field_110575_b);
         blockrendererdispatcher.func_175016_a(iblockstate, 1.0F);
         GlStateManager.func_179121_F();
         GlStateManager.func_179101_C();
      }
   }

   public boolean func_177142_b() {
      return false;
   }
}
