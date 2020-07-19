package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelElytra;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class LayerElytra implements LayerRenderer<EntityLivingBase> {
   private static final ResourceLocation field_188355_a = new ResourceLocation("textures/entity/elytra.png");
   protected final RenderLivingBase<?> field_188356_b;
   private final ModelElytra field_188357_c = new ModelElytra();

   public LayerElytra(RenderLivingBase<?> p_i47185_1_) {
      this.field_188356_b = p_i47185_1_;
   }

   public void func_177141_a(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
      ItemStack itemstack = p_177141_1_.func_184582_a(EntityEquipmentSlot.CHEST);
      if (itemstack.func_77973_b() == Items.field_185160_cR) {
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179147_l();
         GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
         if (p_177141_1_ instanceof AbstractClientPlayer) {
            AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)p_177141_1_;
            if (abstractclientplayer.func_184833_s() && abstractclientplayer.func_184834_t() != null) {
               this.field_188356_b.func_110776_a(abstractclientplayer.func_184834_t());
            } else if (abstractclientplayer.func_152122_n() && abstractclientplayer.func_110303_q() != null && abstractclientplayer.func_175148_a(EnumPlayerModelParts.CAPE)) {
               this.field_188356_b.func_110776_a(abstractclientplayer.func_110303_q());
            } else {
               this.field_188356_b.func_110776_a(field_188355_a);
            }
         } else {
            this.field_188356_b.func_110776_a(field_188355_a);
         }

         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b(0.0F, 0.0F, 0.125F);
         this.field_188357_c.func_78087_a(p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_, p_177141_1_);
         this.field_188357_c.func_78088_a(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
         if (itemstack.func_77948_v()) {
            LayerArmorBase.func_188364_a(this.field_188356_b, p_177141_1_, this.field_188357_c, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
         }

         GlStateManager.func_179084_k();
         GlStateManager.func_179121_F();
      }
   }

   public boolean func_177142_b() {
      return false;
   }
}
