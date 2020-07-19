package net.minecraft.client.gui.toasts;

import java.util.List;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.MathHelper;

public class AdvancementToast implements IToast {
   private final Advancement field_193679_c;
   private boolean field_194168_d = false;

   public AdvancementToast(Advancement p_i47490_1_) {
      this.field_193679_c = p_i47490_1_;
   }

   public IToast.Visibility func_193653_a(GuiToast p_193653_1_, long p_193653_2_) {
      p_193653_1_.func_192989_b().func_110434_K().func_110577_a(field_193654_a);
      GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
      DisplayInfo displayinfo = this.field_193679_c.func_192068_c();
      p_193653_1_.func_73729_b(0, 0, 0, 0, 160, 32);
      if (displayinfo != null) {
         List<String> list = p_193653_1_.func_192989_b().field_71466_p.func_78271_c(displayinfo.func_192297_a().func_150254_d(), 125);
         int i = displayinfo.func_192291_d() == FrameType.CHALLENGE ? 16746751 : 16776960;
         if (list.size() == 1) {
            p_193653_1_.func_192989_b().field_71466_p.func_78276_b(I18n.func_135052_a("advancements.toast." + displayinfo.func_192291_d().func_192307_a()), 30, 7, i | -16777216);
            p_193653_1_.func_192989_b().field_71466_p.func_78276_b(displayinfo.func_192297_a().func_150254_d(), 30, 18, -1);
         } else {
            int j = 1500;
            float f = 300.0F;
            if (p_193653_2_ < 1500L) {
               int k = MathHelper.func_76141_d(MathHelper.func_76131_a((float)(1500L - p_193653_2_) / 300.0F, 0.0F, 1.0F) * 255.0F) << 24 | 67108864;
               p_193653_1_.func_192989_b().field_71466_p.func_78276_b(I18n.func_135052_a("advancements.toast." + displayinfo.func_192291_d().func_192307_a()), 30, 11, i | k);
            } else {
               int i1 = MathHelper.func_76141_d(MathHelper.func_76131_a((float)(p_193653_2_ - 1500L) / 300.0F, 0.0F, 1.0F) * 252.0F) << 24 | 67108864;
               int l = 16 - list.size() * p_193653_1_.func_192989_b().field_71466_p.field_78288_b / 2;

               for(String s : list) {
                  p_193653_1_.func_192989_b().field_71466_p.func_78276_b(s, 30, l, 16777215 | i1);
                  l += p_193653_1_.func_192989_b().field_71466_p.field_78288_b;
               }
            }
         }

         if (!this.field_194168_d && p_193653_2_ > 0L) {
            this.field_194168_d = true;
            if (displayinfo.func_192291_d() == FrameType.CHALLENGE) {
               p_193653_1_.func_192989_b().func_147118_V().func_147682_a(PositionedSoundRecord.func_194007_a(SoundEvents.field_194228_if, 1.0F, 1.0F));
            }
         }

         RenderHelper.func_74520_c();
         p_193653_1_.func_192989_b().func_175599_af().func_184391_a((EntityLivingBase)null, displayinfo.func_192298_b(), 8, 8);
         return p_193653_2_ >= 5000L ? IToast.Visibility.HIDE : IToast.Visibility.SHOW;
      } else {
         return IToast.Visibility.HIDE;
      }
   }
}
