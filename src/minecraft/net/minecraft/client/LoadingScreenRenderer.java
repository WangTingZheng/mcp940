package net.minecraft.client;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MinecraftError;

public class LoadingScreenRenderer implements IProgressUpdate
{
    private String message = "";

    /** A reference to the Minecraft object. */
    private final Minecraft mc;

    /**
     * The text currently displayed (i.e. the argument to the last call to printText or displayString)
     */
    private String currentlyDisplayedText = "";

    /** The system's time represented in milliseconds. */
    private long systemTime = Minecraft.getSystemTime();

    /** True if the loading ended with a success */
    private boolean loadingSuccess;
    private final ScaledResolution scaledResolution;
    private final Framebuffer framebuffer;

    public LoadingScreenRenderer(Minecraft mcIn)
    {
        this.mc = mcIn;
        this.scaledResolution = new ScaledResolution(mcIn);
        this.framebuffer = new Framebuffer(mcIn.displayWidth, mcIn.displayHeight, false);
        this.framebuffer.setFramebufferFilter(9728);
    }

    /**
     * this string, followed by "working..." and then the "% complete" are the 3 lines shown. This resets progress to 0,
     * and the WorkingString to "working...".
     */
    public void resetProgressAndMessage(String message)
    {
        this.loadingSuccess = false;
        this.displayString(message);
    }

    /**
     * Shows the 'Saving level' string.
     */
    public void displaySavingString(String message)
    {
        this.loadingSuccess = true;
        this.displayString(message);
    }

    private void displayString(String message)
    {
        this.currentlyDisplayedText = message;

        if (!this.mc.running)
        {
            if (!this.loadingSuccess)
            {
                throw new MinecraftError();
            }
        }
        else
        {
            GlStateManager.clear(256);
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();

            if (OpenGlHelper.isFramebufferEnabled())
            {
                int i = this.scaledResolution.getScaleFactor();
                GlStateManager.ortho(0.0D, (double)(this.scaledResolution.getScaledWidth() * i), (double)(this.scaledResolution.getScaledHeight() * i), 0.0D, 100.0D, 300.0D);
            }
            else
            {
                ScaledResolution scaledresolution = new ScaledResolution(this.mc);
                GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
            }

            GlStateManager.matrixMode(5888);
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0F, 0.0F, -200.0F);
        }
    }

    /**
     * Displays a string on the loading screen supposed to indicate what is being done currently.
     */
    public void displayLoadingString(String message)
    {
        if (!this.mc.running)
        {
            if (!this.loadingSuccess)
            {
                throw new MinecraftError();
            }
        }
        else
        {
            this.systemTime = 0L;
            this.message = message;
            this.setLoadingProgress(-1);
            this.systemTime = 0L;
        }
    }

    /**
     * Updates the progress bar on the loading screen to the specified amount.
     */
    public void setLoadingProgress(int progress)
    {
        if (!this.mc.running)
        {
            if (!this.loadingSuccess)
            {
                throw new MinecraftError();
            }
        }
        else
        {
            long i = Minecraft.getSystemTime();

            if (i - this.systemTime >= 100L)
            {
                this.systemTime = i;
                ScaledResolution scaledresolution = new ScaledResolution(this.mc);
                int j = scaledresolution.getScaleFactor();
                int k = scaledresolution.getScaledWidth();
                int l = scaledresolution.getScaledHeight();

                if (OpenGlHelper.isFramebufferEnabled())
                {
                    this.framebuffer.framebufferClear();
                }
                else
                {
                    GlStateManager.clear(256);
                }

                this.framebuffer.bindFramebuffer(false);
                GlStateManager.matrixMode(5889);
                GlStateManager.loadIdentity();
                GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
                GlStateManager.matrixMode(5888);
                GlStateManager.loadIdentity();
                GlStateManager.translate(0.0F, 0.0F, -200.0F);

                if (!OpenGlHelper.isFramebufferEnabled())
                {
                    GlStateManager.clear(16640);
                }

                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferbuilder = tessellator.getBuffer();
                this.mc.getTextureManager().bindTexture(Gui.OPTIONS_BACKGROUND);
                float f = 32.0F;
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                bufferbuilder.pos(0.0D, (double)l, 0.0D).tex(0.0D, (double)((float)l / 32.0F)).color(64, 64, 64, 255).endVertex();
                bufferbuilder.pos((double)k, (double)l, 0.0D).tex((double)((float)k / 32.0F), (double)((float)l / 32.0F)).color(64, 64, 64, 255).endVertex();
                bufferbuilder.pos((double)k, 0.0D, 0.0D).tex((double)((float)k / 32.0F), 0.0D).color(64, 64, 64, 255).endVertex();
                bufferbuilder.pos(0.0D, 0.0D, 0.0D).tex(0.0D, 0.0D).color(64, 64, 64, 255).endVertex();
                tessellator.draw();

                if (progress >= 0)
                {
                    int i1 = 100;
                    int j1 = 2;
                    int k1 = k / 2 - 50;
                    int l1 = l / 2 + 16;
                    GlStateManager.disableTexture2D();
                    bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
                    bufferbuilder.pos((double)k1, (double)l1, 0.0D).color(128, 128, 128, 255).endVertex();
                    bufferbuilder.pos((double)k1, (double)(l1 + 2), 0.0D).color(128, 128, 128, 255).endVertex();
                    bufferbuilder.pos((double)(k1 + 100), (double)(l1 + 2), 0.0D).color(128, 128, 128, 255).endVertex();
                    bufferbuilder.pos((double)(k1 + 100), (double)l1, 0.0D).color(128, 128, 128, 255).endVertex();
                    bufferbuilder.pos((double)k1, (double)l1, 0.0D).color(128, 255, 128, 255).endVertex();
                    bufferbuilder.pos((double)k1, (double)(l1 + 2), 0.0D).color(128, 255, 128, 255).endVertex();
                    bufferbuilder.pos((double)(k1 + progress), (double)(l1 + 2), 0.0D).color(128, 255, 128, 255).endVertex();
                    bufferbuilder.pos((double)(k1 + progress), (double)l1, 0.0D).color(128, 255, 128, 255).endVertex();
                    tessellator.draw();
                    GlStateManager.enableTexture2D();
                }

                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                this.mc.fontRenderer.drawStringWithShadow(this.currentlyDisplayedText, (float)((k - this.mc.fontRenderer.getStringWidth(this.currentlyDisplayedText)) / 2), (float)(l / 2 - 4 - 16), 16777215);
                this.mc.fontRenderer.drawStringWithShadow(this.message, (float)((k - this.mc.fontRenderer.getStringWidth(this.message)) / 2), (float)(l / 2 - 4 + 8), 16777215);
                this.framebuffer.unbindFramebuffer();

                if (OpenGlHelper.isFramebufferEnabled())
                {
                    this.framebuffer.framebufferRender(k * j, l * j);
                }

                this.mc.updateDisplay();

                try
                {
                    Thread.yield();
                }
                catch (Exception var15)
                {
                    ;
                }
            }
        }
    }

    public void setDoneWorking()
    {
    }
}
