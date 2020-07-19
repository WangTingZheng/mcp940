package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;

public class LayerSheepWool implements LayerRenderer<EntitySheep> {
   private static final ResourceLocation field_177165_a = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
   private final RenderSheep field_177163_b;
   private final ModelSheep1 field_177164_c = new ModelSheep1();

   public LayerSheepWool(RenderSheep p_i46112_1_) {
      this.field_177163_b = p_i46112_1_;
   }

   public void func_177141_a(EntitySheep p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
      if (!p_177141_1_.func_70892_o() && !p_177141_1_.func_82150_aj()) {
         this.field_177163_b.func_110776_a(field_177165_a);
         if (p_177141_1_.func_145818_k_() && "jeb_".equals(p_177141_1_.func_95999_t())) {
            int i1 = 25;
            int i = p_177141_1_.field_70173_aa / 25 + p_177141_1_.func_145782_y();
            int j = EnumDyeColor.values().length;
            int k = i % j;
            int l = (i + 1) % j;
            float f = ((float)(p_177141_1_.field_70173_aa % 25) + p_177141_4_) / 25.0F;
            float[] afloat1 = EntitySheep.func_175513_a(EnumDyeColor.func_176764_b(k));
            float[] afloat2 = EntitySheep.func_175513_a(EnumDyeColor.func_176764_b(l));
            GlStateManager.func_179124_c(afloat1[0] * (1.0F - f) + afloat2[0] * f, afloat1[1] * (1.0F - f) + afloat2[1] * f, afloat1[2] * (1.0F - f) + afloat2[2] * f);
         } else {
            float[] afloat = EntitySheep.func_175513_a(p_177141_1_.func_175509_cj());
            GlStateManager.func_179124_c(afloat[0], afloat[1], afloat[2]);
         }

         this.field_177164_c.func_178686_a(this.field_177163_b.func_177087_b());
         this.field_177164_c.func_78086_a(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_);
         this.field_177164_c.func_78088_a(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
      }
   }

   public boolean func_177142_b() {
      return true;
   }
}
