package net.minecraft.client.gui;

import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.src.Config;
import net.minecraft.src.CustomColors;
import net.minecraft.src.FontUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

public class FontRenderer implements IResourceManagerReloadListener
{
    private static final ResourceLocation[] UNICODE_PAGE_LOCATIONS = new ResourceLocation[256];

    /** Array of width of all the characters in default.png */
    private final int[] charWidth = new int[256];

    /** the height in pixels of default text */
    public int FONT_HEIGHT = 9;
    public Random fontRandom = new Random();

    /**
     * Array of the start/end column (in upper/lower nibble) for every glyph in the /font directory.
     */
    private final byte[] glyphWidth = new byte[65536];

    /**
     * Array of RGB triplets defining the 16 standard chat colors followed by 16 darker version of the same colors for
     * drop shadows.
     */
    private final int[] colorCode = new int[32];
    private ResourceLocation locationFontTexture;

    /** The RenderEngine used to load and setup glyph textures. */
    private final TextureManager renderEngine;

    /** Current X coordinate at which to draw the next character. */
    private float posX;

    /** Current Y coordinate at which to draw the next character. */
    private float posY;

    /**
     * If true, strings should be rendered with Unicode fonts instead of the default.png font
     */
    private boolean unicodeFlag;

    /**
     * If true, the Unicode Bidirectional Algorithm should be run before rendering any string.
     */
    private boolean bidiFlag;

    /** Used to specify new red value for the current color. */
    private float red;

    /** Used to specify new blue value for the current color. */
    private float blue;

    /** Used to specify new green value for the current color. */
    private float green;

    /** Used to speify new alpha value for the current color. */
    private float alpha;

    /** Text color of the currently rendering string. */
    private int textColor;

    /** Set if the "k" style (random) is active in currently rendering string */
    private boolean randomStyle;

    /** Set if the "l" style (bold) is active in currently rendering string */
    private boolean boldStyle;

    /** Set if the "o" style (italic) is active in currently rendering string */
    private boolean italicStyle;

    /**
     * Set if the "n" style (underlined) is active in currently rendering string
     */
    private boolean underlineStyle;

    /**
     * Set if the "m" style (strikethrough) is active in currently rendering string
     */
    private boolean strikethroughStyle;
    public GameSettings gameSettings;
    public ResourceLocation locationFontTextureBase;
    public boolean enabled = true;
    public float offsetBold = 1.0F;
    private float[] charWidthFloat = new float[256];

    public FontRenderer(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn, boolean unicode)
    {
        this.gameSettings = gameSettingsIn;
        this.locationFontTextureBase = location;
        this.locationFontTexture = location;
        this.renderEngine = textureManagerIn;
        this.unicodeFlag = unicode;
        this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase);
        this.bindTexture(this.locationFontTexture);

        for (int i = 0; i < 32; ++i)
        {
            int j = (i >> 3 & 1) * 85;
            int k = (i >> 2 & 1) * 170 + j;
            int l = (i >> 1 & 1) * 170 + j;
            int i1 = (i >> 0 & 1) * 170 + j;

            if (i == 6)
            {
                k += 85;
            }

            if (gameSettingsIn.anaglyph)
            {
                int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
                int k1 = (k * 30 + l * 70) / 100;
                int l1 = (k * 30 + i1 * 70) / 100;
                k = j1;
                l = k1;
                i1 = l1;
            }

            if (i >= 16)
            {
                k /= 4;
                l /= 4;
                i1 /= 4;
            }

            this.colorCode[i] = (k & 255) << 16 | (l & 255) << 8 | i1 & 255;
        }

        this.readGlyphSizes();
    }

    public void onResourceManagerReload(IResourceManager resourceManager)
    {
        this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase);

        for (int i = 0; i < UNICODE_PAGE_LOCATIONS.length; ++i)
        {
            UNICODE_PAGE_LOCATIONS[i] = null;
        }

        this.readFontTexture();
        this.readGlyphSizes();
    }

    private void readFontTexture()
    {
        IResource iresource = null;
        BufferedImage bufferedimage;

        try
        {
            iresource = this.getResource(this.locationFontTexture);
            bufferedimage = TextureUtil.readBufferedImage(iresource.getInputStream());
        }
        catch (IOException ioexception)
        {
            throw new RuntimeException(ioexception);
        }
        finally
        {
            IOUtils.closeQuietly((Closeable)iresource);
        }

        Properties props = FontUtils.readFontProperties(this.locationFontTexture);
        int imgWidth = bufferedimage.getWidth();
        int imgHeight = bufferedimage.getHeight();
        int charW = imgWidth / 16;
        int charH = imgHeight / 16;
        float kx = (float)imgWidth / 128.0F;
        float boldScaleFactor = Config.limit(kx, 1.0F, 2.0F);
        this.offsetBold = 1.0F / boldScaleFactor;
        float offsetBoldConfig = FontUtils.readFloat(props, "offsetBold", -1.0F);

        if (offsetBoldConfig >= 0.0F)
        {
            this.offsetBold = offsetBoldConfig;
        }

        int[] aint = new int[imgWidth * imgHeight];
        bufferedimage.getRGB(0, 0, imgWidth, imgHeight, aint, 0, imgWidth);

        for (int i1 = 0; i1 < 256; ++i1)
        {
            int j1 = i1 % 16;
            int k1 = i1 / 16;
            int l1 = 0;

            for (l1 = charW - 1; l1 >= 0; --l1)
            {
                int i2 = j1 * charW + l1;
                boolean flag = true;

                for (int j2 = 0; j2 < charH && flag; ++j2)
                {
                    int k2 = (k1 * charH + j2) * imgWidth;
                    int l2 = aint[i2 + k2];
                    int i3 = l2 >> 24 & 255;

                    if (i3 > 16)
                    {
                        flag = false;
                    }
                }

                if (!flag)
                {
                    break;
                }
            }

            if (i1 == 65)
            {
                i1 = i1;
            }

            if (i1 == 32)
            {
                if (charW <= 8)
                {
                    l1 = (int)(2.0F * kx);
                }
                else
                {
                    l1 = (int)(1.5F * kx);
                }
            }

            this.charWidthFloat[i1] = (float)(l1 + 1) / kx + 1.0F;
        }

        FontUtils.readCustomCharWidths(props, this.charWidthFloat);

        for (int j3 = 0; j3 < this.charWidth.length; ++j3)
        {
            this.charWidth[j3] = Math.round(this.charWidthFloat[j3]);
        }
    }

    private void readGlyphSizes()
    {
        IResource iresource = null;

        try
        {
            iresource = this.getResource(new ResourceLocation("font/glyph_sizes.bin"));
            iresource.getInputStream().read(this.glyphWidth);
        }
        catch (IOException ioexception)
        {
            throw new RuntimeException(ioexception);
        }
        finally
        {
            IOUtils.closeQuietly((Closeable)iresource);
        }
    }

    /**
     * Render the given char
     */
    private float renderChar(char ch, boolean italic)
    {
        if (ch != ' ' && ch != 160)
        {
            int i = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(ch);
            return i != -1 && !this.unicodeFlag ? this.renderDefaultChar(i, italic) : this.renderUnicodeChar(ch, italic);
        }
        else
        {
            return !this.unicodeFlag ? this.charWidthFloat[ch] : 4.0F;
        }
    }

    /**
     * Render a single character with the default.png font at current (posX,posY) location...
     */
    private float renderDefaultChar(int ch, boolean italic)
    {
        int i = ch % 16 * 8;
        int j = ch / 16 * 8;
        int k = italic ? 1 : 0;
        this.bindTexture(this.locationFontTexture);
        float f = this.charWidthFloat[ch];
        float f1 = 7.99F;
        GlStateManager.glBegin(5);
        GlStateManager.glTexCoord2f((float)i / 128.0F, (float)j / 128.0F);
        GlStateManager.glVertex3f(this.posX + (float)k, this.posY, 0.0F);
        GlStateManager.glTexCoord2f((float)i / 128.0F, ((float)j + 7.99F) / 128.0F);
        GlStateManager.glVertex3f(this.posX - (float)k, this.posY + 7.99F, 0.0F);
        GlStateManager.glTexCoord2f(((float)i + f1 - 1.0F) / 128.0F, (float)j / 128.0F);
        GlStateManager.glVertex3f(this.posX + f1 - 1.0F + (float)k, this.posY, 0.0F);
        GlStateManager.glTexCoord2f(((float)i + f1 - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F);
        GlStateManager.glVertex3f(this.posX + f1 - 1.0F - (float)k, this.posY + 7.99F, 0.0F);
        GlStateManager.glEnd();
        return f;
    }

    private ResourceLocation getUnicodePageLocation(int page)
    {
        if (UNICODE_PAGE_LOCATIONS[page] == null)
        {
            UNICODE_PAGE_LOCATIONS[page] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", page));
            UNICODE_PAGE_LOCATIONS[page] = FontUtils.getHdFontLocation(UNICODE_PAGE_LOCATIONS[page]);
        }

        return UNICODE_PAGE_LOCATIONS[page];
    }

    /**
     * Load one of the /font/glyph_XX.png into a new GL texture and store the texture ID in glyphTextureName array.
     */
    private void loadGlyphTexture(int page)
    {
        this.bindTexture(this.getUnicodePageLocation(page));
    }

    /**
     * Render a single Unicode character at current (posX,posY) location using one of the /font/glyph_XX.png files...
     */
    private float renderUnicodeChar(char ch, boolean italic)
    {
        int i = this.glyphWidth[ch] & 255;

        if (i == 0)
        {
            return 0.0F;
        }
        else
        {
            int j = ch / 256;
            this.loadGlyphTexture(j);
            int k = i >>> 4;
            int l = i & 15;
            float f = (float)k;
            float f1 = (float)(l + 1);
            float f2 = (float)(ch % 16 * 16) + f;
            float f3 = (float)((ch & 255) / 16 * 16);
            float f4 = f1 - f - 0.02F;
            float f5 = italic ? 1.0F : 0.0F;
            GlStateManager.glBegin(5);
            GlStateManager.glTexCoord2f(f2 / 256.0F, f3 / 256.0F);
            GlStateManager.glVertex3f(this.posX + f5, this.posY, 0.0F);
            GlStateManager.glTexCoord2f(f2 / 256.0F, (f3 + 15.98F) / 256.0F);
            GlStateManager.glVertex3f(this.posX - f5, this.posY + 7.99F, 0.0F);
            GlStateManager.glTexCoord2f((f2 + f4) / 256.0F, f3 / 256.0F);
            GlStateManager.glVertex3f(this.posX + f4 / 2.0F + f5, this.posY, 0.0F);
            GlStateManager.glTexCoord2f((f2 + f4) / 256.0F, (f3 + 15.98F) / 256.0F);
            GlStateManager.glVertex3f(this.posX + f4 / 2.0F - f5, this.posY + 7.99F, 0.0F);
            GlStateManager.glEnd();
            return (f1 - f) / 2.0F + 1.0F;
        }
    }

    /**
     * Draws the specified string with a shadow.
     */
    public int drawStringWithShadow(String text, float x, float y, int color)
    {
        return this.drawString(text, x, y, color, true);
    }

    /**
     * Draws the specified string.
     */
    public int drawString(String text, int x, int y, int color)
    {
        return !this.enabled ? 0 : this.drawString(text, (float)x, (float)y, color, false);
    }

    /**
     * Draws the specified string.
     */
    public int drawString(String text, float x, float y, int color, boolean dropShadow)
    {
        this.enableAlpha();
        this.resetStyles();
        int i;

        if (dropShadow)
        {
            i = this.renderString(text, x + 1.0F, y + 1.0F, color, true);
            i = Math.max(i, this.renderString(text, x, y, color, false));
        }
        else
        {
            i = this.renderString(text, x, y, color, false);
        }

        return i;
    }

    /**
     * Apply Unicode Bidirectional Algorithm to string and return a new possibly reordered string for visual rendering.
     */
    private String bidiReorder(String text)
    {
        try
        {
            Bidi bidi = new Bidi((new ArabicShaping(8)).shape(text), 127);
            bidi.setReorderingMode(0);
            return bidi.writeReordered(2);
        }
        catch (ArabicShapingException var31)
        {
            return text;
        }
    }

    /**
     * Reset all style flag fields in the class to false; called at the start of string rendering
     */
    private void resetStyles()
    {
        this.randomStyle = false;
        this.boldStyle = false;
        this.italicStyle = false;
        this.underlineStyle = false;
        this.strikethroughStyle = false;
    }

    /**
     * Render a single line string at the current (posX,posY) and update posX
     */
    private void renderStringAtPos(String text, boolean shadow)
    {
        for (int i = 0; i < text.length(); ++i)
        {
            char c0 = text.charAt(i);

            if (c0 == 167 && i + 1 < text.length())
            {
                int l = "0123456789abcdefklmnor".indexOf(String.valueOf(text.charAt(i + 1)).toLowerCase(Locale.ROOT).charAt(0));

                if (l < 16)
                {
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;

                    if (l < 0 || l > 15)
                    {
                        l = 15;
                    }

                    if (shadow)
                    {
                        l += 16;
                    }

                    int i1 = this.colorCode[l];

                    if (Config.isCustomColors())
                    {
                        i1 = CustomColors.getTextColor(l, i1);
                    }

                    this.textColor = i1;
                    this.setColor((float)(i1 >> 16) / 255.0F, (float)(i1 >> 8 & 255) / 255.0F, (float)(i1 & 255) / 255.0F, this.alpha);
                }
                else if (l == 16)
                {
                    this.randomStyle = true;
                }
                else if (l == 17)
                {
                    this.boldStyle = true;
                }
                else if (l == 18)
                {
                    this.strikethroughStyle = true;
                }
                else if (l == 19)
                {
                    this.underlineStyle = true;
                }
                else if (l == 20)
                {
                    this.italicStyle = true;
                }
                else if (l == 21)
                {
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;
                    this.setColor(this.red, this.blue, this.green, this.alpha);
                }

                ++i;
            }
            else
            {
                int j = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(c0);

                if (this.randomStyle && j != -1)
                {
                    int k = this.getCharWidth(c0);
                    char c1;

                    while (true)
                    {
                        j = this.fontRandom.nextInt("\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".length());
                        c1 = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".charAt(j);

                        if (k == this.getCharWidth(c1))
                        {
                            break;
                        }
                    }

                    c0 = c1;
                }

                float f1 = j != -1 && !this.unicodeFlag ? this.offsetBold : 0.5F;
                boolean flag = (c0 == 0 || j == -1 || this.unicodeFlag) && shadow;

                if (flag)
                {
                    this.posX -= f1;
                    this.posY -= f1;
                }

                float f = this.renderChar(c0, this.italicStyle);

                if (flag)
                {
                    this.posX += f1;
                    this.posY += f1;
                }

                if (this.boldStyle)
                {
                    this.posX += f1;

                    if (flag)
                    {
                        this.posX -= f1;
                        this.posY -= f1;
                    }

                    this.renderChar(c0, this.italicStyle);
                    this.posX -= f1;

                    if (flag)
                    {
                        this.posX += f1;
                        this.posY += f1;
                    }

                    f += f1;
                }

                this.doDraw(f);
            }
        }
    }

    protected void doDraw(float p_doDraw_1_)
    {
        if (this.strikethroughStyle)
        {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            GlStateManager.disableTexture2D();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
            bufferbuilder.pos((double)this.posX, (double)(this.posY + (float)(this.FONT_HEIGHT / 2)), 0.0D).endVertex();
            bufferbuilder.pos((double)(this.posX + p_doDraw_1_), (double)(this.posY + (float)(this.FONT_HEIGHT / 2)), 0.0D).endVertex();
            bufferbuilder.pos((double)(this.posX + p_doDraw_1_), (double)(this.posY + (float)(this.FONT_HEIGHT / 2) - 1.0F), 0.0D).endVertex();
            bufferbuilder.pos((double)this.posX, (double)(this.posY + (float)(this.FONT_HEIGHT / 2) - 1.0F), 0.0D).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
        }

        if (this.underlineStyle)
        {
            Tessellator tessellator1 = Tessellator.getInstance();
            BufferBuilder bufferbuilder1 = tessellator1.getBuffer();
            GlStateManager.disableTexture2D();
            bufferbuilder1.begin(7, DefaultVertexFormats.POSITION);
            int i = this.underlineStyle ? -1 : 0;
            bufferbuilder1.pos((double)(this.posX + (float)i), (double)(this.posY + (float)this.FONT_HEIGHT), 0.0D).endVertex();
            bufferbuilder1.pos((double)(this.posX + p_doDraw_1_), (double)(this.posY + (float)this.FONT_HEIGHT), 0.0D).endVertex();
            bufferbuilder1.pos((double)(this.posX + p_doDraw_1_), (double)(this.posY + (float)this.FONT_HEIGHT - 1.0F), 0.0D).endVertex();
            bufferbuilder1.pos((double)(this.posX + (float)i), (double)(this.posY + (float)this.FONT_HEIGHT - 1.0F), 0.0D).endVertex();
            tessellator1.draw();
            GlStateManager.enableTexture2D();
        }

        this.posX += p_doDraw_1_;
    }

    /**
     * Render string either left or right aligned depending on bidiFlag
     */
    private int renderStringAligned(String text, int x, int y, int width, int color, boolean dropShadow)
    {
        if (this.bidiFlag)
        {
            int i = this.getStringWidth(this.bidiReorder(text));
            x = x + width - i;
        }

        return this.renderString(text, (float)x, (float)y, color, dropShadow);
    }

    /**
     * Render single line string by setting GL color, current (posX,posY), and calling renderStringAtPos()
     */
    private int renderString(String text, float x, float y, int color, boolean dropShadow)
    {
        if (text == null)
        {
            return 0;
        }
        else
        {
            if (this.bidiFlag)
            {
                text = this.bidiReorder(text);
            }

            if ((color & -67108864) == 0)
            {
                color |= -16777216;
            }

            if (dropShadow)
            {
                color = (color & 16579836) >> 2 | color & -16777216;
            }

            this.red = (float)(color >> 16 & 255) / 255.0F;
            this.blue = (float)(color >> 8 & 255) / 255.0F;
            this.green = (float)(color & 255) / 255.0F;
            this.alpha = (float)(color >> 24 & 255) / 255.0F;
            this.setColor(this.red, this.blue, this.green, this.alpha);
            this.posX = x;
            this.posY = y;
            this.renderStringAtPos(text, dropShadow);
            return (int)this.posX;
        }
    }

    /**
     * Returns the width of this string. Equivalent of FontMetrics.stringWidth(String s).
     */
    public int getStringWidth(String text)
    {
        if (text == null)
        {
            return 0;
        }
        else
        {
            float f = 0.0F;
            boolean flag = false;

            for (int i = 0; i < text.length(); ++i)
            {
                char c0 = text.charAt(i);
                float f1 = this.getCharWidthFloat(c0);

                if (f1 < 0.0F && i < text.length() - 1)
                {
                    ++i;
                    c0 = text.charAt(i);

                    if (c0 != 'l' && c0 != 'L')
                    {
                        if (c0 == 'r' || c0 == 'R')
                        {
                            flag = false;
                        }
                    }
                    else
                    {
                        flag = true;
                    }

                    f1 = 0.0F;
                }

                f += f1;

                if (flag && f1 > 0.0F)
                {
                    f += this.unicodeFlag ? 1.0F : this.offsetBold;
                }
            }

            return (int)f;
        }
    }

    /**
     * Returns the width of this character as rendered.
     */
    public int getCharWidth(char character)
    {
        return Math.round(this.getCharWidthFloat(character));
    }

    private float getCharWidthFloat(char p_getCharWidthFloat_1_)
    {
        if (p_getCharWidthFloat_1_ == 167)
        {
            return -1.0F;
        }
        else if (p_getCharWidthFloat_1_ != ' ' && p_getCharWidthFloat_1_ != 160)
        {
            int i = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(p_getCharWidthFloat_1_);

            if (p_getCharWidthFloat_1_ > 0 && i != -1 && !this.unicodeFlag)
            {
                return this.charWidthFloat[i];
            }
            else if (this.glyphWidth[p_getCharWidthFloat_1_] != 0)
            {
                int j = this.glyphWidth[p_getCharWidthFloat_1_] & 255;
                int k = j >>> 4;
                int l = j & 15;
                ++l;
                return (float)((l - k) / 2 + 1);
            }
            else
            {
                return 0.0F;
            }
        }
        else
        {
            return this.charWidthFloat[32];
        }
    }

    /**
     * Trims a string to fit a specified Width.
     */
    public String trimStringToWidth(String text, int width)
    {
        return this.trimStringToWidth(text, width, false);
    }

    /**
     * Trims a string to a specified width, optionally starting from the end and working backwards.
     * <h3>Samples:</h3>
     * (Assuming that {@link #getCharWidth(char)} returns <code>6</code> for all of the characters in
     * <code>0123456789</code> on the current resource pack)
     * <table>
     * <tr><th>Input</th><th>Returns</th></tr>
     * <tr><td><code>trimStringToWidth("0123456789", 1, false)</code></td><td><samp>""</samp></td></tr>
     * <tr><td><code>trimStringToWidth("0123456789", 6, false)</code></td><td><samp>"0"</samp></td></tr>
     * <tr><td><code>trimStringToWidth("0123456789", 29, false)</code></td><td><samp>"0123"</samp></td></tr>
     * <tr><td><code>trimStringToWidth("0123456789", 30, false)</code></td><td><samp>"01234"</samp></td></tr>
     * <tr><td><code>trimStringToWidth("0123456789", 9001, false)</code></td><td><samp>"0123456789"</samp></td></tr>
     * <tr><td><code>trimStringToWidth("0123456789", 1, true)</code></td><td><samp>""</samp></td></tr>
     * <tr><td><code>trimStringToWidth("0123456789", 6, true)</code></td><td><samp>"9"</samp></td></tr>
     * <tr><td><code>trimStringToWidth("0123456789", 29, true)</code></td><td><samp>"6789"</samp></td></tr>
     * <tr><td><code>trimStringToWidth("0123456789", 30, true)</code></td><td><samp>"56789"</samp></td></tr>
     * <tr><td><code>trimStringToWidth("0123456789", 9001, true)</code></td><td><samp>"0123456789"</samp></td></tr>
     * </table>
     */
    public String trimStringToWidth(String text, int width, boolean reverse)
    {
        StringBuilder stringbuilder = new StringBuilder();
        float f = 0.0F;
        int i = reverse ? text.length() - 1 : 0;
        int j = reverse ? -1 : 1;
        boolean flag = false;
        boolean flag1 = false;

        for (int k = i; k >= 0 && k < text.length() && f < (float)width; k += j)
        {
            char c0 = text.charAt(k);
            float f1 = this.getCharWidthFloat(c0);

            if (flag)
            {
                flag = false;

                if (c0 != 'l' && c0 != 'L')
                {
                    if (c0 == 'r' || c0 == 'R')
                    {
                        flag1 = false;
                    }
                }
                else
                {
                    flag1 = true;
                }
            }
            else if (f1 < 0.0F)
            {
                flag = true;
            }
            else
            {
                f += f1;

                if (flag1)
                {
                    ++f;
                }
            }

            if (f > (float)width)
            {
                break;
            }

            if (reverse)
            {
                stringbuilder.insert(0, c0);
            }
            else
            {
                stringbuilder.append(c0);
            }
        }

        return stringbuilder.toString();
    }

    /**
     * Remove all newline characters from the end of the string
     */
    private String trimStringNewline(String text)
    {
        while (text != null && text.endsWith("\n"))
        {
            text = text.substring(0, text.length() - 1);
        }

        return text;
    }

    /**
     * Splits and draws a String with wordwrap (maximum length is parameter k)
     */
    public void drawSplitString(String str, int x, int y, int wrapWidth, int textColor)
    {
        this.resetStyles();
        this.textColor = textColor;
        str = this.trimStringNewline(str);
        this.renderSplitString(str, x, y, wrapWidth, false);
    }

    /**
     * Perform actual work of rendering a multi-line string with wordwrap and with darker drop shadow color if flag is
     * set
     */
    private void renderSplitString(String str, int x, int y, int wrapWidth, boolean addShadow)
    {
        for (String s : this.listFormattedStringToWidth(str, wrapWidth))
        {
            this.renderStringAligned(s, x, y, wrapWidth, this.textColor, addShadow);
            y += this.FONT_HEIGHT;
        }
    }

    /**
     * Returns the height (in pixels) of the given string if it is wordwrapped to the given max width.
     */
    public int getWordWrappedHeight(String str, int maxLength)
    {
        return this.FONT_HEIGHT * this.listFormattedStringToWidth(str, maxLength).size();
    }

    /**
     * Set unicodeFlag controlling whether strings should be rendered with Unicode fonts instead of the default.png
     * font.
     */
    public void setUnicodeFlag(boolean unicodeFlagIn)
    {
        this.unicodeFlag = unicodeFlagIn;
    }

    /**
     * Get unicodeFlag controlling whether strings should be rendered with Unicode fonts instead of the default.png
     * font.
     */
    public boolean getUnicodeFlag()
    {
        return this.unicodeFlag;
    }

    /**
     * Set bidiFlag to control if the Unicode Bidirectional Algorithm should be run before rendering any string.
     */
    public void setBidiFlag(boolean bidiFlagIn)
    {
        this.bidiFlag = bidiFlagIn;
    }

    public List<String> listFormattedStringToWidth(String str, int wrapWidth)
    {
        return Arrays.<String>asList(this.wrapFormattedStringToWidth(str, wrapWidth).split("\n"));
    }

    /**
     * Inserts newline and formatting into a string to wrap it within the specified width.
     */
    String wrapFormattedStringToWidth(String str, int wrapWidth)
    {
        int i = this.sizeStringToWidth(str, wrapWidth);

        if (str.length() <= i)
        {
            return str;
        }
        else
        {
            String s = str.substring(0, i);
            char c0 = str.charAt(i);
            boolean flag = c0 == ' ' || c0 == '\n';
            String s1 = getFormatFromString(s) + str.substring(i + (flag ? 1 : 0));
            return s + "\n" + this.wrapFormattedStringToWidth(s1, wrapWidth);
        }
    }

    /**
     * Determines how many characters from the string will fit into the specified width.
     */
    private int sizeStringToWidth(String str, int wrapWidth)
    {
        int i = str.length();
        float f = 0.0F;
        int j = 0;
        int k = -1;

        for (boolean flag = false; j < i; ++j)
        {
            char c0 = str.charAt(j);

            switch (c0)
            {
                case '\n':
                    --j;
                    break;

                case ' ':
                    k = j;

                default:
                    f += this.getCharWidthFloat(c0);

                    if (flag)
                    {
                        ++f;
                    }

                    break;

                case '\u00a7':
                    if (j < i - 1)
                    {
                        ++j;
                        char c1 = str.charAt(j);

                        if (c1 != 'l' && c1 != 'L')
                        {
                            if (c1 == 'r' || c1 == 'R' || isFormatColor(c1))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = true;
                        }
                    }
            }

            if (c0 == '\n')
            {
                ++j;
                k = j;
                break;
            }

            if (f > (float)wrapWidth)
            {
                break;
            }
        }

        return j != i && k != -1 && k < j ? k : j;
    }

    /**
     * Checks if the char code is a hexadecimal character, used to set colour.
     */
    private static boolean isFormatColor(char colorChar)
    {
        return colorChar >= '0' && colorChar <= '9' || colorChar >= 'a' && colorChar <= 'f' || colorChar >= 'A' && colorChar <= 'F';
    }

    /**
     * Checks if the char code is O-K...lLrRk-o... used to set special formatting.
     */
    private static boolean isFormatSpecial(char formatChar)
    {
        return formatChar >= 'k' && formatChar <= 'o' || formatChar >= 'K' && formatChar <= 'O' || formatChar == 'r' || formatChar == 'R';
    }

    /**
     * Digests a string for nonprinting formatting characters then returns a string containing only that formatting.
     */
    public static String getFormatFromString(String text)
    {
        String s = "";
        int i = -1;
        int j = text.length();

        while ((i = text.indexOf(167, i + 1)) != -1)
        {
            if (i < j - 1)
            {
                char c0 = text.charAt(i + 1);

                if (isFormatColor(c0))
                {
                    s = "\u00a7" + c0;
                }
                else if (isFormatSpecial(c0))
                {
                    s = s + "\u00a7" + c0;
                }
            }
        }

        return s;
    }

    /**
     * Get bidiFlag that controls if the Unicode Bidirectional Algorithm should be run before rendering any string
     */
    public boolean getBidiFlag()
    {
        return this.bidiFlag;
    }

    public int getColorCode(char character)
    {
        int i = "0123456789abcdef".indexOf(character);

        if (i >= 0 && i < this.colorCode.length)
        {
            int j = this.colorCode[i];

            if (Config.isCustomColors())
            {
                j = CustomColors.getTextColor(i, j);
            }

            return j;
        }
        else
        {
            return 16777215;
        }
    }

    protected void setColor(float p_setColor_1_, float p_setColor_2_, float p_setColor_3_, float p_setColor_4_)
    {
        GlStateManager.color(p_setColor_1_, p_setColor_2_, p_setColor_3_, p_setColor_4_);
    }

    protected void enableAlpha()
    {
        GlStateManager.enableAlpha();
    }

    protected void bindTexture(ResourceLocation p_bindTexture_1_)
    {
        this.renderEngine.bindTexture(p_bindTexture_1_);
    }

    protected IResource getResource(ResourceLocation p_getResource_1_) throws IOException
    {
        return Minecraft.getMinecraft().getResourceManager().getResource(p_getResource_1_);
    }
}
