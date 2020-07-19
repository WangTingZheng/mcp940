package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ISoundEventListener;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class GuiSubtitleOverlay extends Gui implements ISoundEventListener {
   private final Minecraft field_184069_a;
   private final List<GuiSubtitleOverlay.Subtitle> field_184070_f = Lists.<GuiSubtitleOverlay.Subtitle>newArrayList();
   private boolean field_184071_g;

   public GuiSubtitleOverlay(Minecraft p_i46603_1_) {
      this.field_184069_a = p_i46603_1_;
   }

   public void func_184068_a(ScaledResolution p_184068_1_) {
      if (!this.field_184071_g && this.field_184069_a.field_71474_y.field_186717_N) {
         this.field_184069_a.func_147118_V().func_184402_a(this);
         this.field_184071_g = true;
      } else if (this.field_184071_g && !this.field_184069_a.field_71474_y.field_186717_N) {
         this.field_184069_a.func_147118_V().func_184400_b(this);
         this.field_184071_g = false;
      }

      if (this.field_184071_g && !this.field_184070_f.isEmpty()) {
         GlStateManager.func_179094_E();
         GlStateManager.func_179147_l();
         GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
         Vec3d vec3d = new Vec3d(this.field_184069_a.field_71439_g.field_70165_t, this.field_184069_a.field_71439_g.field_70163_u + (double)this.field_184069_a.field_71439_g.func_70047_e(), this.field_184069_a.field_71439_g.field_70161_v);
         Vec3d vec3d1 = (new Vec3d(0.0D, 0.0D, -1.0D)).func_178789_a(-this.field_184069_a.field_71439_g.field_70125_A * 0.017453292F).func_178785_b(-this.field_184069_a.field_71439_g.field_70177_z * 0.017453292F);
         Vec3d vec3d2 = (new Vec3d(0.0D, 1.0D, 0.0D)).func_178789_a(-this.field_184069_a.field_71439_g.field_70125_A * 0.017453292F).func_178785_b(-this.field_184069_a.field_71439_g.field_70177_z * 0.017453292F);
         Vec3d vec3d3 = vec3d1.func_72431_c(vec3d2);
         int i = 0;
         int j = 0;
         Iterator<GuiSubtitleOverlay.Subtitle> iterator = this.field_184070_f.iterator();

         while(iterator.hasNext()) {
            GuiSubtitleOverlay.Subtitle guisubtitleoverlay$subtitle = iterator.next();
            if (guisubtitleoverlay$subtitle.func_186825_b() + 3000L <= Minecraft.func_71386_F()) {
               iterator.remove();
            } else {
               j = Math.max(j, this.field_184069_a.field_71466_p.func_78256_a(guisubtitleoverlay$subtitle.func_186824_a()));
            }
         }

         j = j + this.field_184069_a.field_71466_p.func_78256_a("<") + this.field_184069_a.field_71466_p.func_78256_a(" ") + this.field_184069_a.field_71466_p.func_78256_a(">") + this.field_184069_a.field_71466_p.func_78256_a(" ");

         for(GuiSubtitleOverlay.Subtitle guisubtitleoverlay$subtitle1 : this.field_184070_f) {
            int k = 255;
            String s = guisubtitleoverlay$subtitle1.func_186824_a();
            Vec3d vec3d4 = guisubtitleoverlay$subtitle1.func_186826_c().func_178788_d(vec3d).func_72432_b();
            double d0 = -vec3d3.func_72430_b(vec3d4);
            double d1 = -vec3d1.func_72430_b(vec3d4);
            boolean flag = d1 > 0.5D;
            int l = j / 2;
            int i1 = this.field_184069_a.field_71466_p.field_78288_b;
            int j1 = i1 / 2;
            float f = 1.0F;
            int k1 = this.field_184069_a.field_71466_p.func_78256_a(s);
            int l1 = MathHelper.func_76128_c(MathHelper.func_151238_b(255.0D, 75.0D, (double)((float)(Minecraft.func_71386_F() - guisubtitleoverlay$subtitle1.func_186825_b()) / 3000.0F)));
            int i2 = l1 << 16 | l1 << 8 | l1;
            GlStateManager.func_179094_E();
            GlStateManager.func_179109_b((float)p_184068_1_.func_78326_a() - (float)l * 1.0F - 2.0F, (float)(p_184068_1_.func_78328_b() - 30) - (float)(i * (i1 + 1)) * 1.0F, 0.0F);
            GlStateManager.func_179152_a(1.0F, 1.0F, 1.0F);
            func_73734_a(-l - 1, -j1 - 1, l + 1, j1 + 1, -872415232);
            GlStateManager.func_179147_l();
            if (!flag) {
               if (d0 > 0.0D) {
                  this.field_184069_a.field_71466_p.func_78276_b(">", l - this.field_184069_a.field_71466_p.func_78256_a(">"), -j1, i2 + -16777216);
               } else if (d0 < 0.0D) {
                  this.field_184069_a.field_71466_p.func_78276_b("<", -l, -j1, i2 + -16777216);
               }
            }

            this.field_184069_a.field_71466_p.func_78276_b(s, -k1 / 2, -j1, i2 + -16777216);
            GlStateManager.func_179121_F();
            ++i;
         }

         GlStateManager.func_179084_k();
         GlStateManager.func_179121_F();
      }
   }

   public void func_184067_a(ISound p_184067_1_, SoundEventAccessor p_184067_2_) {
      if (p_184067_2_.func_188712_c() != null) {
         String s = p_184067_2_.func_188712_c().func_150254_d();
         if (!this.field_184070_f.isEmpty()) {
            for(GuiSubtitleOverlay.Subtitle guisubtitleoverlay$subtitle : this.field_184070_f) {
               if (guisubtitleoverlay$subtitle.func_186824_a().equals(s)) {
                  guisubtitleoverlay$subtitle.func_186823_a(new Vec3d((double)p_184067_1_.func_147649_g(), (double)p_184067_1_.func_147654_h(), (double)p_184067_1_.func_147651_i()));
                  return;
               }
            }
         }

         this.field_184070_f.add(new GuiSubtitleOverlay.Subtitle(s, new Vec3d((double)p_184067_1_.func_147649_g(), (double)p_184067_1_.func_147654_h(), (double)p_184067_1_.func_147651_i())));
      }
   }

   public class Subtitle {
      private final String field_186828_b;
      private long field_186829_c;
      private Vec3d field_186830_d;

      public Subtitle(String p_i47104_2_, Vec3d p_i47104_3_) {
         this.field_186828_b = p_i47104_2_;
         this.field_186830_d = p_i47104_3_;
         this.field_186829_c = Minecraft.func_71386_F();
      }

      public String func_186824_a() {
         return this.field_186828_b;
      }

      public long func_186825_b() {
         return this.field_186829_c;
      }

      public Vec3d func_186826_c() {
         return this.field_186830_d;
      }

      public void func_186823_a(Vec3d p_186823_1_) {
         this.field_186830_d = p_186823_1_;
         this.field_186829_c = Minecraft.func_71386_F();
      }
   }
}
