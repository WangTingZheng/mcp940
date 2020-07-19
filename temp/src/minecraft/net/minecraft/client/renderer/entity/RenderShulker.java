package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelShulker;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class RenderShulker extends RenderLiving<EntityShulker> {
   public static final ResourceLocation[] field_188342_a = new ResourceLocation[]{new ResourceLocation("textures/entity/shulker/shulker_white.png"), new ResourceLocation("textures/entity/shulker/shulker_orange.png"), new ResourceLocation("textures/entity/shulker/shulker_magenta.png"), new ResourceLocation("textures/entity/shulker/shulker_light_blue.png"), new ResourceLocation("textures/entity/shulker/shulker_yellow.png"), new ResourceLocation("textures/entity/shulker/shulker_lime.png"), new ResourceLocation("textures/entity/shulker/shulker_pink.png"), new ResourceLocation("textures/entity/shulker/shulker_gray.png"), new ResourceLocation("textures/entity/shulker/shulker_silver.png"), new ResourceLocation("textures/entity/shulker/shulker_cyan.png"), new ResourceLocation("textures/entity/shulker/shulker_purple.png"), new ResourceLocation("textures/entity/shulker/shulker_blue.png"), new ResourceLocation("textures/entity/shulker/shulker_brown.png"), new ResourceLocation("textures/entity/shulker/shulker_green.png"), new ResourceLocation("textures/entity/shulker/shulker_red.png"), new ResourceLocation("textures/entity/shulker/shulker_black.png")};

   public RenderShulker(RenderManager p_i47194_1_) {
      super(p_i47194_1_, new ModelShulker(), 0.0F);
      this.func_177094_a(new RenderShulker.HeadLayer());
   }

   public ModelShulker func_177087_b() {
      return (ModelShulker)super.func_177087_b();
   }

   public void func_76986_a(EntityShulker p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      int i = p_76986_1_.func_184693_dc();
      if (i > 0 && p_76986_1_.func_184697_de()) {
         BlockPos blockpos = p_76986_1_.func_184699_da();
         BlockPos blockpos1 = p_76986_1_.func_184692_dd();
         double d0 = (double)((float)i - p_76986_9_) / 6.0D;
         d0 = d0 * d0;
         double d1 = (double)(blockpos.func_177958_n() - blockpos1.func_177958_n()) * d0;
         double d2 = (double)(blockpos.func_177956_o() - blockpos1.func_177956_o()) * d0;
         double d3 = (double)(blockpos.func_177952_p() - blockpos1.func_177952_p()) * d0;
         super.func_76986_a(p_76986_1_, p_76986_2_ - d1, p_76986_4_ - d2, p_76986_6_ - d3, p_76986_8_, p_76986_9_);
      } else {
         super.func_76986_a(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
      }

   }

   public boolean func_177071_a(EntityShulker p_177071_1_, ICamera p_177071_2_, double p_177071_3_, double p_177071_5_, double p_177071_7_) {
      if (super.func_177071_a(p_177071_1_, p_177071_2_, p_177071_3_, p_177071_5_, p_177071_7_)) {
         return true;
      } else {
         if (p_177071_1_.func_184693_dc() > 0 && p_177071_1_.func_184697_de()) {
            BlockPos blockpos = p_177071_1_.func_184692_dd();
            BlockPos blockpos1 = p_177071_1_.func_184699_da();
            Vec3d vec3d = new Vec3d((double)blockpos1.func_177958_n(), (double)blockpos1.func_177956_o(), (double)blockpos1.func_177952_p());
            Vec3d vec3d1 = new Vec3d((double)blockpos.func_177958_n(), (double)blockpos.func_177956_o(), (double)blockpos.func_177952_p());
            if (p_177071_2_.func_78546_a(new AxisAlignedBB(vec3d1.field_72450_a, vec3d1.field_72448_b, vec3d1.field_72449_c, vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c))) {
               return true;
            }
         }

         return false;
      }
   }

   protected ResourceLocation func_110775_a(EntityShulker p_110775_1_) {
      return field_188342_a[p_110775_1_.func_190769_dn().func_176765_a()];
   }

   protected void func_77043_a(EntityShulker p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_) {
      super.func_77043_a(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
      switch(p_77043_1_.func_184696_cZ()) {
      case DOWN:
      default:
         break;
      case EAST:
         GlStateManager.func_179109_b(0.5F, 0.5F, 0.0F);
         GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179114_b(90.0F, 0.0F, 0.0F, 1.0F);
         break;
      case WEST:
         GlStateManager.func_179109_b(-0.5F, 0.5F, 0.0F);
         GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179114_b(-90.0F, 0.0F, 0.0F, 1.0F);
         break;
      case NORTH:
         GlStateManager.func_179109_b(0.0F, 0.5F, -0.5F);
         GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
         break;
      case SOUTH:
         GlStateManager.func_179109_b(0.0F, 0.5F, 0.5F);
         GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
         break;
      case UP:
         GlStateManager.func_179109_b(0.0F, 1.0F, 0.0F);
         GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
      }

   }

   protected void func_77041_b(EntityShulker p_77041_1_, float p_77041_2_) {
      float f = 0.999F;
      GlStateManager.func_179152_a(0.999F, 0.999F, 0.999F);
   }

   class HeadLayer implements LayerRenderer<EntityShulker> {
      private HeadLayer() {
      }

      public void func_177141_a(EntityShulker p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
         GlStateManager.func_179094_E();
         switch(p_177141_1_.func_184696_cZ()) {
         case DOWN:
         default:
            break;
         case EAST:
            GlStateManager.func_179114_b(90.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179109_b(1.0F, -1.0F, 0.0F);
            GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
            break;
         case WEST:
            GlStateManager.func_179114_b(-90.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179109_b(-1.0F, -1.0F, 0.0F);
            GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
            break;
         case NORTH:
            GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179109_b(0.0F, -1.0F, -1.0F);
            break;
         case SOUTH:
            GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179109_b(0.0F, -1.0F, 1.0F);
            break;
         case UP:
            GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179109_b(0.0F, -2.0F, 0.0F);
         }

         ModelRenderer modelrenderer = RenderShulker.this.func_177087_b().field_187066_a;
         modelrenderer.field_78796_g = p_177141_6_ * 0.017453292F;
         modelrenderer.field_78795_f = p_177141_7_ * 0.017453292F;
         RenderShulker.this.func_110776_a(RenderShulker.field_188342_a[p_177141_1_.func_190769_dn().func_176765_a()]);
         modelrenderer.func_78785_a(p_177141_8_);
         GlStateManager.func_179121_F();
      }

      public boolean func_177142_b() {
         return false;
      }
   }
}
