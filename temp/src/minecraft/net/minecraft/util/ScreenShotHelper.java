package net.minecraft.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.IntBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.event.ClickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;

public class ScreenShotHelper {
   private static final Logger field_148261_a = LogManager.getLogger();
   private static final DateFormat field_74295_a = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
   private static IntBuffer field_74293_b;
   private static int[] field_74294_c;

   public static ITextComponent func_148260_a(File p_148260_0_, int p_148260_1_, int p_148260_2_, Framebuffer p_148260_3_) {
      return func_148259_a(p_148260_0_, (String)null, p_148260_1_, p_148260_2_, p_148260_3_);
   }

   public static ITextComponent func_148259_a(File p_148259_0_, @Nullable String p_148259_1_, int p_148259_2_, int p_148259_3_, Framebuffer p_148259_4_) {
      try {
         File file1 = new File(p_148259_0_, "screenshots");
         file1.mkdir();
         BufferedImage bufferedimage = func_186719_a(p_148259_2_, p_148259_3_, p_148259_4_);
         File file2;
         if (p_148259_1_ == null) {
            file2 = func_74290_a(file1);
         } else {
            file2 = new File(file1, p_148259_1_);
         }

         ImageIO.write(bufferedimage, "png", file2);
         ITextComponent itextcomponent = new TextComponentString(file2.getName());
         itextcomponent.func_150256_b().func_150241_a(new ClickEvent(ClickEvent.Action.OPEN_FILE, file2.getAbsolutePath()));
         itextcomponent.func_150256_b().func_150228_d(Boolean.valueOf(true));
         return new TextComponentTranslation("screenshot.success", new Object[]{itextcomponent});
      } catch (Exception exception) {
         field_148261_a.warn("Couldn't save screenshot", (Throwable)exception);
         return new TextComponentTranslation("screenshot.failure", new Object[]{exception.getMessage()});
      }
   }

   public static BufferedImage func_186719_a(int p_186719_0_, int p_186719_1_, Framebuffer p_186719_2_) {
      if (OpenGlHelper.func_148822_b()) {
         p_186719_0_ = p_186719_2_.field_147622_a;
         p_186719_1_ = p_186719_2_.field_147620_b;
      }

      int i = p_186719_0_ * p_186719_1_;
      if (field_74293_b == null || field_74293_b.capacity() < i) {
         field_74293_b = BufferUtils.createIntBuffer(i);
         field_74294_c = new int[i];
      }

      GlStateManager.func_187425_g(3333, 1);
      GlStateManager.func_187425_g(3317, 1);
      field_74293_b.clear();
      if (OpenGlHelper.func_148822_b()) {
         GlStateManager.func_179144_i(p_186719_2_.field_147617_g);
         GlStateManager.func_187433_a(3553, 0, 32993, 33639, field_74293_b);
      } else {
         GlStateManager.func_187413_a(0, 0, p_186719_0_, p_186719_1_, 32993, 33639, field_74293_b);
      }

      field_74293_b.get(field_74294_c);
      TextureUtil.func_147953_a(field_74294_c, p_186719_0_, p_186719_1_);
      BufferedImage bufferedimage = new BufferedImage(p_186719_0_, p_186719_1_, 1);
      bufferedimage.setRGB(0, 0, p_186719_0_, p_186719_1_, field_74294_c, 0, p_186719_0_);
      return bufferedimage;
   }

   private static File func_74290_a(File p_74290_0_) {
      String s = field_74295_a.format(new Date()).toString();
      int i = 1;

      while(true) {
         File file1 = new File(p_74290_0_, s + (i == 1 ? "" : "_" + i) + ".png");
         if (!file1.exists()) {
            return file1;
         }

         ++i;
      }
   }
}
