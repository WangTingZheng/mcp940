package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;

public class RenderVillager extends RenderLiving<EntityVillager> {
   private static final ResourceLocation field_110903_f = new ResourceLocation("textures/entity/villager/villager.png");
   private static final ResourceLocation field_110904_g = new ResourceLocation("textures/entity/villager/farmer.png");
   private static final ResourceLocation field_110908_h = new ResourceLocation("textures/entity/villager/librarian.png");
   private static final ResourceLocation field_110907_k = new ResourceLocation("textures/entity/villager/priest.png");
   private static final ResourceLocation field_110905_l = new ResourceLocation("textures/entity/villager/smith.png");
   private static final ResourceLocation field_110906_m = new ResourceLocation("textures/entity/villager/butcher.png");

   public RenderVillager(RenderManager p_i46132_1_) {
      super(p_i46132_1_, new ModelVillager(0.0F), 0.5F);
      this.func_177094_a(new LayerCustomHead(this.func_177087_b().field_78191_a));
   }

   public ModelVillager func_177087_b() {
      return (ModelVillager)super.func_177087_b();
   }

   protected ResourceLocation func_110775_a(EntityVillager p_110775_1_) {
      switch(p_110775_1_.func_70946_n()) {
      case 0:
         return field_110904_g;
      case 1:
         return field_110908_h;
      case 2:
         return field_110907_k;
      case 3:
         return field_110905_l;
      case 4:
         return field_110906_m;
      case 5:
      default:
         return field_110903_f;
      }
   }

   protected void func_77041_b(EntityVillager p_77041_1_, float p_77041_2_) {
      float f = 0.9375F;
      if (p_77041_1_.func_70874_b() < 0) {
         f = (float)((double)f * 0.5D);
         this.field_76989_e = 0.25F;
      } else {
         this.field_76989_e = 0.5F;
      }

      GlStateManager.func_179152_a(f, f, f);
   }
}
