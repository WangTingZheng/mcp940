package net.minecraft.client.renderer.entity;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerDeadmau5Head;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerEntityOnShoulder;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RenderPlayer extends RenderLivingBase<AbstractClientPlayer> {
   private final boolean field_177140_a;

   public RenderPlayer(RenderManager p_i46102_1_) {
      this(p_i46102_1_, false);
   }

   public RenderPlayer(RenderManager p_i46103_1_, boolean p_i46103_2_) {
      super(p_i46103_1_, new ModelPlayer(0.0F, p_i46103_2_), 0.5F);
      this.field_177140_a = p_i46103_2_;
      this.func_177094_a(new LayerBipedArmor(this));
      this.func_177094_a(new LayerHeldItem(this));
      this.func_177094_a(new LayerArrow(this));
      this.func_177094_a(new LayerDeadmau5Head(this));
      this.func_177094_a(new LayerCape(this));
      this.func_177094_a(new LayerCustomHead(this.func_177087_b().field_78116_c));
      this.func_177094_a(new LayerElytra(this));
      this.func_177094_a(new LayerEntityOnShoulder(p_i46103_1_));
   }

   public ModelPlayer func_177087_b() {
      return (ModelPlayer)super.func_177087_b();
   }

   public void func_76986_a(AbstractClientPlayer p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      if (!p_76986_1_.func_175144_cb() || this.field_76990_c.field_78734_h == p_76986_1_) {
         double d0 = p_76986_4_;
         if (p_76986_1_.func_70093_af()) {
            d0 = p_76986_4_ - 0.125D;
         }

         this.func_177137_d(p_76986_1_);
         GlStateManager.func_187408_a(GlStateManager.Profile.PLAYER_SKIN);
         super.func_76986_a(p_76986_1_, p_76986_2_, d0, p_76986_6_, p_76986_8_, p_76986_9_);
         GlStateManager.func_187440_b(GlStateManager.Profile.PLAYER_SKIN);
      }
   }

   private void func_177137_d(AbstractClientPlayer p_177137_1_) {
      ModelPlayer modelplayer = this.func_177087_b();
      if (p_177137_1_.func_175149_v()) {
         modelplayer.func_178719_a(false);
         modelplayer.field_78116_c.field_78806_j = true;
         modelplayer.field_178720_f.field_78806_j = true;
      } else {
         ItemStack itemstack = p_177137_1_.func_184614_ca();
         ItemStack itemstack1 = p_177137_1_.func_184592_cb();
         modelplayer.func_178719_a(true);
         modelplayer.field_178720_f.field_78806_j = p_177137_1_.func_175148_a(EnumPlayerModelParts.HAT);
         modelplayer.field_178730_v.field_78806_j = p_177137_1_.func_175148_a(EnumPlayerModelParts.JACKET);
         modelplayer.field_178733_c.field_78806_j = p_177137_1_.func_175148_a(EnumPlayerModelParts.LEFT_PANTS_LEG);
         modelplayer.field_178731_d.field_78806_j = p_177137_1_.func_175148_a(EnumPlayerModelParts.RIGHT_PANTS_LEG);
         modelplayer.field_178734_a.field_78806_j = p_177137_1_.func_175148_a(EnumPlayerModelParts.LEFT_SLEEVE);
         modelplayer.field_178732_b.field_78806_j = p_177137_1_.func_175148_a(EnumPlayerModelParts.RIGHT_SLEEVE);
         modelplayer.field_78117_n = p_177137_1_.func_70093_af();
         ModelBiped.ArmPose modelbiped$armpose = ModelBiped.ArmPose.EMPTY;
         ModelBiped.ArmPose modelbiped$armpose1 = ModelBiped.ArmPose.EMPTY;
         if (!itemstack.func_190926_b()) {
            modelbiped$armpose = ModelBiped.ArmPose.ITEM;
            if (p_177137_1_.func_184605_cv() > 0) {
               EnumAction enumaction = itemstack.func_77975_n();
               if (enumaction == EnumAction.BLOCK) {
                  modelbiped$armpose = ModelBiped.ArmPose.BLOCK;
               } else if (enumaction == EnumAction.BOW) {
                  modelbiped$armpose = ModelBiped.ArmPose.BOW_AND_ARROW;
               }
            }
         }

         if (!itemstack1.func_190926_b()) {
            modelbiped$armpose1 = ModelBiped.ArmPose.ITEM;
            if (p_177137_1_.func_184605_cv() > 0) {
               EnumAction enumaction1 = itemstack1.func_77975_n();
               if (enumaction1 == EnumAction.BLOCK) {
                  modelbiped$armpose1 = ModelBiped.ArmPose.BLOCK;
               }
            }
         }

         if (p_177137_1_.func_184591_cq() == EnumHandSide.RIGHT) {
            modelplayer.field_187076_m = modelbiped$armpose;
            modelplayer.field_187075_l = modelbiped$armpose1;
         } else {
            modelplayer.field_187076_m = modelbiped$armpose1;
            modelplayer.field_187075_l = modelbiped$armpose;
         }
      }

   }

   public ResourceLocation func_110775_a(AbstractClientPlayer p_110775_1_) {
      return p_110775_1_.func_110306_p();
   }

   public void func_82422_c() {
      GlStateManager.func_179109_b(0.0F, 0.1875F, 0.0F);
   }

   protected void func_77041_b(AbstractClientPlayer p_77041_1_, float p_77041_2_) {
      float f = 0.9375F;
      GlStateManager.func_179152_a(0.9375F, 0.9375F, 0.9375F);
   }

   protected void func_188296_a(AbstractClientPlayer p_188296_1_, double p_188296_2_, double p_188296_4_, double p_188296_6_, String p_188296_8_, double p_188296_9_) {
      if (p_188296_9_ < 100.0D) {
         Scoreboard scoreboard = p_188296_1_.func_96123_co();
         ScoreObjective scoreobjective = scoreboard.func_96539_a(2);
         if (scoreobjective != null) {
            Score score = scoreboard.func_96529_a(p_188296_1_.func_70005_c_(), scoreobjective);
            this.func_147906_a(p_188296_1_, score.func_96652_c() + " " + scoreobjective.func_96678_d(), p_188296_2_, p_188296_4_, p_188296_6_, 64);
            p_188296_4_ += (double)((float)this.func_76983_a().field_78288_b * 1.15F * 0.025F);
         }
      }

      super.func_188296_a(p_188296_1_, p_188296_2_, p_188296_4_, p_188296_6_, p_188296_8_, p_188296_9_);
   }

   public void func_177138_b(AbstractClientPlayer p_177138_1_) {
      float f = 1.0F;
      GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
      float f1 = 0.0625F;
      ModelPlayer modelplayer = this.func_177087_b();
      this.func_177137_d(p_177138_1_);
      GlStateManager.func_179147_l();
      modelplayer.field_78095_p = 0.0F;
      modelplayer.field_78117_n = false;
      modelplayer.func_78087_a(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, p_177138_1_);
      modelplayer.field_178723_h.field_78795_f = 0.0F;
      modelplayer.field_178723_h.func_78785_a(0.0625F);
      modelplayer.field_178732_b.field_78795_f = 0.0F;
      modelplayer.field_178732_b.func_78785_a(0.0625F);
      GlStateManager.func_179084_k();
   }

   public void func_177139_c(AbstractClientPlayer p_177139_1_) {
      float f = 1.0F;
      GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
      float f1 = 0.0625F;
      ModelPlayer modelplayer = this.func_177087_b();
      this.func_177137_d(p_177139_1_);
      GlStateManager.func_179147_l();
      modelplayer.field_78117_n = false;
      modelplayer.field_78095_p = 0.0F;
      modelplayer.func_78087_a(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, p_177139_1_);
      modelplayer.field_178724_i.field_78795_f = 0.0F;
      modelplayer.field_178724_i.func_78785_a(0.0625F);
      modelplayer.field_178734_a.field_78795_f = 0.0F;
      modelplayer.field_178734_a.func_78785_a(0.0625F);
      GlStateManager.func_179084_k();
   }

   protected void func_77039_a(AbstractClientPlayer p_77039_1_, double p_77039_2_, double p_77039_4_, double p_77039_6_) {
      if (p_77039_1_.func_70089_S() && p_77039_1_.func_70608_bn()) {
         super.func_77039_a(p_77039_1_, p_77039_2_ + (double)p_77039_1_.field_71079_bU, p_77039_4_ + (double)p_77039_1_.field_71082_cx, p_77039_6_ + (double)p_77039_1_.field_71089_bV);
      } else {
         super.func_77039_a(p_77039_1_, p_77039_2_, p_77039_4_, p_77039_6_);
      }

   }

   protected void func_77043_a(AbstractClientPlayer p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_) {
      if (p_77043_1_.func_70089_S() && p_77043_1_.func_70608_bn()) {
         GlStateManager.func_179114_b(p_77043_1_.func_71051_bG(), 0.0F, 1.0F, 0.0F);
         GlStateManager.func_179114_b(this.func_77037_a(p_77043_1_), 0.0F, 0.0F, 1.0F);
         GlStateManager.func_179114_b(270.0F, 0.0F, 1.0F, 0.0F);
      } else if (p_77043_1_.func_184613_cA()) {
         super.func_77043_a(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
         float f = (float)p_77043_1_.func_184599_cB() + p_77043_4_;
         float f1 = MathHelper.func_76131_a(f * f / 100.0F, 0.0F, 1.0F);
         GlStateManager.func_179114_b(f1 * (-90.0F - p_77043_1_.field_70125_A), 1.0F, 0.0F, 0.0F);
         Vec3d vec3d = p_77043_1_.func_70676_i(p_77043_4_);
         double d0 = p_77043_1_.field_70159_w * p_77043_1_.field_70159_w + p_77043_1_.field_70179_y * p_77043_1_.field_70179_y;
         double d1 = vec3d.field_72450_a * vec3d.field_72450_a + vec3d.field_72449_c * vec3d.field_72449_c;
         if (d0 > 0.0D && d1 > 0.0D) {
            double d2 = (p_77043_1_.field_70159_w * vec3d.field_72450_a + p_77043_1_.field_70179_y * vec3d.field_72449_c) / (Math.sqrt(d0) * Math.sqrt(d1));
            double d3 = p_77043_1_.field_70159_w * vec3d.field_72449_c - p_77043_1_.field_70179_y * vec3d.field_72450_a;
            GlStateManager.func_179114_b((float)(Math.signum(d3) * Math.acos(d2)) * 180.0F / 3.1415927F, 0.0F, 1.0F, 0.0F);
         }
      } else {
         super.func_77043_a(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
      }

   }
}
