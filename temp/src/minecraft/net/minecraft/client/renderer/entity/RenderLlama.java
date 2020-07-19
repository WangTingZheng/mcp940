package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelLlama;
import net.minecraft.client.renderer.entity.layers.LayerLlamaDecor;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.util.ResourceLocation;

public class RenderLlama extends RenderLiving<EntityLlama> {
   private static final ResourceLocation[] field_191350_a = new ResourceLocation[]{new ResourceLocation("textures/entity/llama/llama_creamy.png"), new ResourceLocation("textures/entity/llama/llama_white.png"), new ResourceLocation("textures/entity/llama/llama_brown.png"), new ResourceLocation("textures/entity/llama/llama_gray.png")};

   public RenderLlama(RenderManager p_i47203_1_) {
      super(p_i47203_1_, new ModelLlama(0.0F), 0.7F);
      this.func_177094_a(new LayerLlamaDecor(this));
   }

   protected ResourceLocation func_110775_a(EntityLlama p_110775_1_) {
      return field_191350_a[p_110775_1_.func_190719_dM()];
   }
}
