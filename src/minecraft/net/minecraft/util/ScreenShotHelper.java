package net.minecraft.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.IntBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.event.ClickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;

public class ScreenShotHelper
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

    /** A buffer to hold pixel values returned by OpenGL. */
    private static IntBuffer pixelBuffer;

    /**
     * The built-up array that contains all the pixel values returned by OpenGL.
     */
    private static int[] pixelValues;

    /**
     * Saves a screenshot in the game directory with a time-stamped filename.
     * Returns an ITextComponent indicating the success/failure of the saving.
     */
    public static ITextComponent saveScreenshot(File gameDirectory, int width, int height, Framebuffer buffer)
    {
        return saveScreenshot(gameDirectory, (String)null, width, height, buffer);
    }

    /**
     * Saves a screenshot in the game directory with the given file name (or null to generate a time-stamped name).
     * Returns an ITextComponent indicating the success/failure of the saving.
     */
    public static ITextComponent saveScreenshot(File gameDirectory, @Nullable String screenshotName, int width, int height, Framebuffer buffer)
    {
        try
        {
            File file1 = new File(gameDirectory, "screenshots");
            file1.mkdir();
            Minecraft minecraft = Minecraft.getMinecraft();
            int i = Config.getGameSettings().guiScale;
            ScaledResolution scaledresolution = new ScaledResolution(minecraft);
            int j = scaledresolution.getScaleFactor();
            int k = Config.getScreenshotSize();
            boolean flag = OpenGlHelper.isFramebufferEnabled() && k > 1;

            if (flag)
            {
                Config.getGameSettings().guiScale = j * k;
                resize(width * k, height * k);
                GlStateManager.pushMatrix();
                GlStateManager.clear(16640);
                minecraft.getFramebuffer().bindFramebuffer(true);
                minecraft.entityRenderer.updateCameraAndRender(minecraft.getRenderPartialTicks(), System.nanoTime());
            }

            BufferedImage bufferedimage = createScreenshot(width, height, buffer);

            if (flag)
            {
                minecraft.getFramebuffer().unbindFramebuffer();
                GlStateManager.popMatrix();
                Config.getGameSettings().guiScale = i;
                resize(width, height);
            }

            File file2;

            if (screenshotName == null)
            {
                file2 = getTimestampedPNGFileForDirectory(file1);
            }
            else
            {
                file2 = new File(file1, screenshotName);
            }

            file2 = file2.getCanonicalFile();
            Object object = null;

            if (Reflector.ForgeHooksClient_onScreenshot.exists())
            {
                object = Reflector.call(Reflector.ForgeHooksClient_onScreenshot, bufferedimage, file2);

                if (Reflector.callBoolean(object, Reflector.Event_isCanceled))
                {
                    return (ITextComponent)Reflector.call(object, Reflector.ScreenshotEvent_getCancelMessage);
                }

                file2 = (File)Reflector.call(object, Reflector.ScreenshotEvent_getScreenshotFile);
            }

            ImageIO.write(bufferedimage, "png", file2);
            ITextComponent itextcomponent = new TextComponentString(file2.getName());
            itextcomponent.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file2.getAbsolutePath()));
            itextcomponent.getStyle().setUnderlined(Boolean.valueOf(true));

            if (object != null)
            {
                ITextComponent itextcomponent1 = (ITextComponent)Reflector.call(object, Reflector.ScreenshotEvent_getResultMessage);

                if (itextcomponent1 != null)
                {
                    return itextcomponent1;
                }
            }

            return new TextComponentTranslation("screenshot.success", new Object[] {itextcomponent});
        }
        catch (Exception exception1)
        {
            LOGGER.warn("Couldn't save screenshot", (Throwable)exception1);
            return new TextComponentTranslation("screenshot.failure", new Object[] {exception1.getMessage()});
        }
    }

    public static BufferedImage createScreenshot(int width, int height, Framebuffer framebufferIn)
    {
        if (OpenGlHelper.isFramebufferEnabled())
        {
            width = framebufferIn.framebufferTextureWidth;
            height = framebufferIn.framebufferTextureHeight;
        }

        int i = width * height;

        if (pixelBuffer == null || pixelBuffer.capacity() < i)
        {
            pixelBuffer = BufferUtils.createIntBuffer(i);
            pixelValues = new int[i];
        }

        GlStateManager.glPixelStorei(3333, 1);
        GlStateManager.glPixelStorei(3317, 1);
        pixelBuffer.clear();

        if (OpenGlHelper.isFramebufferEnabled())
        {
            GlStateManager.bindTexture(framebufferIn.framebufferTexture);
            GlStateManager.glGetTexImage(3553, 0, 32993, 33639, pixelBuffer);
        }
        else
        {
            GlStateManager.glReadPixels(0, 0, width, height, 32993, 33639, pixelBuffer);
        }

        pixelBuffer.get(pixelValues);
        TextureUtil.processPixelValues(pixelValues, width, height);
        BufferedImage bufferedimage = new BufferedImage(width, height, 1);
        bufferedimage.setRGB(0, 0, width, height, pixelValues, 0, width);
        return bufferedimage;
    }

    /**
     * Creates a unique PNG file in the given directory named by a timestamp.  Handles cases where the timestamp alone
     * is not enough to create a uniquely named file, though it still might suffer from an unlikely race condition where
     * the filename was unique when this method was called, but another process or thread created a file at the same
     * path immediately after this method returned.
     */
    private static File getTimestampedPNGFileForDirectory(File gameDirectory)
    {
        String s = DATE_FORMAT.format(new Date()).toString();
        int i = 1;

        while (true)
        {
            File file1 = new File(gameDirectory, s + (i == 1 ? "" : "_" + i) + ".png");

            if (!file1.exists())
            {
                return file1;
            }

            ++i;
        }
    }

    private static void resize(int p_resize_0_, int p_resize_1_)
    {
        Minecraft minecraft = Minecraft.getMinecraft();
        minecraft.displayWidth = Math.max(1, p_resize_0_);
        minecraft.displayHeight = Math.max(1, p_resize_1_);

        if (minecraft.currentScreen != null)
        {
            ScaledResolution scaledresolution = new ScaledResolution(minecraft);
            minecraft.currentScreen.onResize(minecraft, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight());
        }

        updateFramebufferSize();
    }

    private static void updateFramebufferSize()
    {
        Minecraft minecraft = Minecraft.getMinecraft();
        minecraft.getFramebuffer().createBindFramebuffer(minecraft.displayWidth, minecraft.displayHeight);

        if (minecraft.entityRenderer != null)
        {
            minecraft.entityRenderer.updateShaderGroupSize(minecraft.displayWidth, minecraft.displayHeight);
        }
    }
}
