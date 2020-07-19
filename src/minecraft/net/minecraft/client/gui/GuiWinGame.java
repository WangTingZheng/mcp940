package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiWinGame extends GuiScreen
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ResourceLocation MINECRAFT_LOGO = new ResourceLocation("textures/gui/title/minecraft.png");
    private static final ResourceLocation VIGNETTE_TEXTURE = new ResourceLocation("textures/misc/vignette.png");
    private final boolean poem;
    private final Runnable onFinished;
    private float time;
    private List<String> lines;
    private int totalScrollLength;
    private final float scrollSpeed = 0.5F;

    public GuiWinGame(boolean poemIn, Runnable onFinishedIn)
    {
        this.poem = poemIn;
        this.onFinished = onFinishedIn;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.mc.getMusicTicker().update();
        this.mc.getSoundHandler().update();
        float f = (float)(this.totalScrollLength + this.height + this.height + 24) / 0.5F;

        if (this.time > f)
        {
            this.sendRespawnPacket();
        }
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (keyCode == 1)
        {
            this.sendRespawnPacket();
        }
    }

    private void sendRespawnPacket()
    {
        this.onFinished.run();
        this.mc.displayGuiScreen((GuiScreen)null);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return true;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        if (this.lines == null)
        {
            this.lines = Lists.<String>newArrayList();
            IResource iresource = null;

            try
            {
                String s = "" + TextFormatting.WHITE + TextFormatting.OBFUSCATED + TextFormatting.GREEN + TextFormatting.AQUA;
                int i = 274;

                if (this.poem)
                {
                    iresource = this.mc.getResourceManager().getResource(new ResourceLocation("texts/end.txt"));
                    InputStream inputstream = iresource.getInputStream();
                    BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
                    Random random = new Random(8124371L);
                    String s1;

                    while ((s1 = bufferedreader.readLine()) != null)
                    {
                        String s2;
                        String s3;

                        for (s1 = s1.replaceAll("PLAYERNAME", this.mc.getSession().getUsername()); s1.contains(s); s1 = s2 + TextFormatting.WHITE + TextFormatting.OBFUSCATED + "XXXXXXXX".substring(0, random.nextInt(4) + 3) + s3)
                        {
                            int j = s1.indexOf(s);
                            s2 = s1.substring(0, j);
                            s3 = s1.substring(j + s.length());
                        }

                        this.lines.addAll(this.mc.fontRenderer.listFormattedStringToWidth(s1, 274));
                        this.lines.add("");
                    }

                    inputstream.close();

                    for (int k = 0; k < 8; ++k)
                    {
                        this.lines.add("");
                    }
                }

                InputStream inputstream1 = this.mc.getResourceManager().getResource(new ResourceLocation("texts/credits.txt")).getInputStream();
                BufferedReader bufferedreader1 = new BufferedReader(new InputStreamReader(inputstream1, StandardCharsets.UTF_8));
                String s4;

                while ((s4 = bufferedreader1.readLine()) != null)
                {
                    s4 = s4.replaceAll("PLAYERNAME", this.mc.getSession().getUsername());
                    s4 = s4.replaceAll("\t", "    ");
                    this.lines.addAll(this.mc.fontRenderer.listFormattedStringToWidth(s4, 274));
                    this.lines.add("");
                }

                inputstream1.close();
                this.totalScrollLength = this.lines.size() * 12;
            }
            catch (Exception exception)
            {
                LOGGER.error("Couldn't load credits", (Throwable)exception);
            }
            finally
            {
                IOUtils.closeQuietly((Closeable)iresource);
            }
        }
    }

    private void drawWinGameScreen(int p_146575_1_, int p_146575_2_, float p_146575_3_)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        this.mc.getTextureManager().bindTexture(Gui.OPTIONS_BACKGROUND);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        int i = this.width;
        float f = -this.time * 0.5F * 0.5F;
        float f1 = (float)this.height - this.time * 0.5F * 0.5F;
        float f2 = 0.015625F;
        float f3 = this.time * 0.02F;
        float f4 = (float)(this.totalScrollLength + this.height + this.height + 24) / 0.5F;
        float f5 = (f4 - 20.0F - this.time) * 0.005F;

        if (f5 < f3)
        {
            f3 = f5;
        }

        if (f3 > 1.0F)
        {
            f3 = 1.0F;
        }

        f3 = f3 * f3;
        f3 = f3 * 96.0F / 255.0F;
        bufferbuilder.pos(0.0D, (double)this.height, (double)this.zLevel).tex(0.0D, (double)(f * 0.015625F)).color(f3, f3, f3, 1.0F).endVertex();
        bufferbuilder.pos((double)i, (double)this.height, (double)this.zLevel).tex((double)((float)i * 0.015625F), (double)(f * 0.015625F)).color(f3, f3, f3, 1.0F).endVertex();
        bufferbuilder.pos((double)i, 0.0D, (double)this.zLevel).tex((double)((float)i * 0.015625F), (double)(f1 * 0.015625F)).color(f3, f3, f3, 1.0F).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, (double)this.zLevel).tex(0.0D, (double)(f1 * 0.015625F)).color(f3, f3, f3, 1.0F).endVertex();
        tessellator.draw();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawWinGameScreen(mouseX, mouseY, partialTicks);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        int i = 274;
        int j = this.width / 2 - 137;
        int k = this.height + 50;
        this.time += partialTicks;
        float f = -this.time * 0.5F;
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, f, 0.0F);
        this.mc.getTextureManager().bindTexture(MINECRAFT_LOGO);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableAlpha();
        this.drawTexturedModalRect(j, k, 0, 0, 155, 44);
        this.drawTexturedModalRect(j + 155, k, 0, 45, 155, 44);
        GlStateManager.disableAlpha();
        int l = k + 100;

        for (int i1 = 0; i1 < this.lines.size(); ++i1)
        {
            if (i1 == this.lines.size() - 1)
            {
                float f1 = (float)l + f - (float)(this.height / 2 - 6);

                if (f1 < 0.0F)
                {
                    GlStateManager.translate(0.0F, -f1, 0.0F);
                }
            }

            if ((float)l + f + 12.0F + 8.0F > 0.0F && (float)l + f < (float)this.height)
            {
                String s = this.lines.get(i1);

                if (s.startsWith("[C]"))
                {
                    this.fontRenderer.drawStringWithShadow(s.substring(3), (float)(j + (274 - this.fontRenderer.getStringWidth(s.substring(3))) / 2), (float)l, 16777215);
                }
                else
                {
                    this.fontRenderer.fontRandom.setSeed((long)((float)((long)i1 * 4238972211L) + this.time / 4.0F));
                    this.fontRenderer.drawStringWithShadow(s, (float)j, (float)l, 16777215);
                }
            }

            l += 12;
        }

        GlStateManager.popMatrix();
        this.mc.getTextureManager().bindTexture(VIGNETTE_TEXTURE);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR);
        int j1 = this.width;
        int k1 = this.height;
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(0.0D, (double)k1, (double)this.zLevel).tex(0.0D, 1.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        bufferbuilder.pos((double)j1, (double)k1, (double)this.zLevel).tex(1.0D, 1.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        bufferbuilder.pos((double)j1, 0.0D, (double)this.zLevel).tex(1.0D, 0.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, (double)this.zLevel).tex(0.0D, 0.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
