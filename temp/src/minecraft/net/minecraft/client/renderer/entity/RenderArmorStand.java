package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelArmorStand;
import net.minecraft.client.model.ModelArmorStandArmor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderArmorStand extends RenderLivingBase<EntityArmorStand> {
   public static final ResourceLocation field_177103_a = new ResourceLocation("textures/entity/armorstand/wood.png");

   public RenderArmorStand(RenderManager p_i46195_1_) {
      super(p_i46195_1_, new ModelArmorStand(), 0.0F);
      LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this) {
         protected void func_177177_a() {
            this.field_177189_c = (T)(new ModelArmorStandArmor(0.5F));
            this.field_177186_d = (T)(new ModelArmorStandArmor(1.0F));
         }
      };
      this.func_177094_a(layerbipedarmor);
      this.func_177094_a(new LayerHeldItem(this));
      this.func_177094_a(new LayerElytra(this));
      this.func_177094_a(new LayerCustomHead(this.func_177087_b().field_78116_c));
   }

   protected ResourceLocation func_110775_a(EntityArmorStand p_110775_1_) {
      return field_177103_a;
   }

   public ModelArmorStand func_177087_b() {
      return (ModelArmorStand)super.func_177087_b();
   }

   protected void func_77043_a(EntityArmorStand p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_) {
      GlStateManager.func_179114_b(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
      float f = (float)(p_77043_1_.field_70170_p.func_82737_E() - p_77043_1_.field_175437_i) + p_77043_4_;
      if (f < 5.0F) {
         GlStateManager.func_179114_b(MathHelper.func_76126_a(f / 1.5F * 3.1415927F) * 3.0F, 0.0F, 1.0F, 0.0F);
      }

   }

   protected boolean func_177070_b(EntityArmorStand p_177070_1_) {
      return p_177070_1_.func_174833_aM();
   }

   public void func_76986_a(EntityArmorStand p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      if (p_76986_1_.func_181026_s()) {
         this.field_188323_j = true;
      }

      super.func_76986_a(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
      if (p_76986_1_.func_181026_s()) {
         this.field_188323_j = false;
      }

   }
}
