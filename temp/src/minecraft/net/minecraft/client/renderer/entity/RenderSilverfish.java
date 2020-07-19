package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSilverfish;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.util.ResourceLocation;

public class RenderSilverfish extends RenderLiving<EntitySilverfish> {
   private static final ResourceLocation field_110882_a = new ResourceLocation("textures/entity/silverfish.png");

   public RenderSilverfish(RenderManager p_i46144_1_) {
      super(p_i46144_1_, new ModelSilverfish(), 0.3F);
   }

   protected float func_77037_a(EntitySilverfish p_77037_1_) {
      return 180.0F;
   }

   protected ResourceLocation func_110775_a(EntitySilverfish p_110775_1_) {
      return field_110882_a;
   }
}
