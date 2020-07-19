package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelLlama;
import net.minecraft.client.renderer.entity.RenderLlama;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.util.ResourceLocation;

public class LayerLlamaDecor implements LayerRenderer<EntityLlama> {
   private static final ResourceLocation[] field_191364_a = new ResourceLocation[]{new ResourceLocation("textures/entity/llama/decor/decor_white.png"), new ResourceLocation("textures/entity/llama/decor/decor_orange.png"), new ResourceLocation("textures/entity/llama/decor/decor_magenta.png"), new ResourceLocation("textures/entity/llama/decor/decor_light_blue.png"), new ResourceLocation("textures/entity/llama/decor/decor_yellow.png"), new ResourceLocation("textures/entity/llama/decor/decor_lime.png"), new ResourceLocation("textures/entity/llama/decor/decor_pink.png"), new ResourceLocation("textures/entity/llama/decor/decor_gray.png"), new ResourceLocation("textures/entity/llama/decor/decor_silver.png"), new ResourceLocation("textures/entity/llama/decor/decor_cyan.png"), new ResourceLocation("textures/entity/llama/decor/decor_purple.png"), new ResourceLocation("textures/entity/llama/decor/decor_blue.png"), new ResourceLocation("textures/entity/llama/decor/decor_brown.png"), new ResourceLocation("textures/entity/llama/decor/decor_green.png"), new ResourceLocation("textures/entity/llama/decor/decor_red.png"), new ResourceLocation("textures/entity/llama/decor/decor_black.png")};
   private final RenderLlama field_191365_b;
   private final ModelLlama field_191366_c = new ModelLlama(0.5F);

   public LayerLlamaDecor(RenderLlama p_i47184_1_) {
      this.field_191365_b = p_i47184_1_;
   }

   public void func_177141_a(EntityLlama p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
      if (p_177141_1_.func_190717_dN()) {
         this.field_191365_b.func_110776_a(field_191364_a[p_177141_1_.func_190704_dO().func_176765_a()]);
         this.field_191366_c.func_178686_a(this.field_191365_b.func_177087_b());
         this.field_191366_c.func_78088_a(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
      }
   }

   public boolean func_177142_b() {
      return false;
   }
}
