package shadersmod.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.src.Config;
import net.minecraft.src.GuiScreenOF;
import net.minecraft.src.Lang;
import net.minecraft.src.StrUtils;

public class GuiShaderOptions extends GuiScreenOF
{
    private GuiScreen prevScreen;
    protected String title;
    private GameSettings settings;
    private int lastMouseX;
    private int lastMouseY;
    private long mouseStillTime;
    private String screenName;
    private String screenText;
    private boolean changed;
    public static final String OPTION_PROFILE = "<profile>";
    public static final String OPTION_EMPTY = "<empty>";
    public static final String OPTION_REST = "*";

    public GuiShaderOptions(GuiScreen guiscreen, GameSettings gamesettings)
    {
        this.lastMouseX = 0;
        this.lastMouseY = 0;
        this.mouseStillTime = 0L;
        this.screenName = null;
        this.screenText = null;
        this.changed = false;
        this.title = "Shader Options";
        this.prevScreen = guiscreen;
        this.settings = gamesettings;
    }

    public GuiShaderOptions(GuiScreen guiscreen, GameSettings gamesettings, String screenName)
    {
        this(guiscreen, gamesettings);
        this.screenName = screenName;

        if (screenName != null)
        {
            this.screenText = Shaders.translate("screen." + screenName, screenName);
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.title = I18n.format("of.options.shaderOptionsTitle");
        int i = 100;
        int j = 0;
        int k = 30;
        int l = 20;
        int i1 = this.width - 130;
        int j1 = 120;
        int k1 = 20;
        int l1 = 2;
        ShaderOption[] ashaderoption = Shaders.getShaderPackOptions(this.screenName);

        if (ashaderoption != null)
        {
            if (ashaderoption.length > 18)
            {
                l1 = ashaderoption.length / 9 + 1;
            }

            for (int i2 = 0; i2 < ashaderoption.length; ++i2)
            {
                ShaderOption shaderoption = ashaderoption[i2];

                if (shaderoption != null && shaderoption.isVisible())
                {
                    int j2 = i2 % l1;
                    int k2 = i2 / l1;
                    int l2 = Math.min(this.width / l1, 200);
                    j = (this.width - l2 * l1) / 2;
                    int i3 = j2 * l2 + 5 + j;
                    int j3 = k + k2 * l;
                    int k3 = l2 - 10;
                    String s = getButtonText(shaderoption, k3);
                    GuiButtonShaderOption guibuttonshaderoption;

                    if (Shaders.isShaderPackOptionSlider(shaderoption.getName()))
                    {
                        guibuttonshaderoption = new GuiSliderShaderOption(i + i2, i3, j3, k3, k1, shaderoption, s);
                    }
                    else
                    {
                        guibuttonshaderoption = new GuiButtonShaderOption(i + i2, i3, j3, k3, k1, shaderoption, s);
                    }

                    guibuttonshaderoption.enabled = shaderoption.isEnabled();
                    this.buttonList.add(guibuttonshaderoption);
                }
            }
        }

        this.buttonList.add(new GuiButton(201, this.width / 2 - j1 - 20, this.height / 6 + 168 + 11, j1, k1, I18n.format("controls.reset")));
        this.buttonList.add(new GuiButton(200, this.width / 2 + 20, this.height / 6 + 168 + 11, j1, k1, I18n.format("gui.done")));
    }

    public static String getButtonText(ShaderOption so, int btnWidth)
    {
        String s = so.getNameText();

        if (so instanceof ShaderOptionScreen)
        {
            ShaderOptionScreen shaderoptionscreen = (ShaderOptionScreen)so;
            return s + "...";
        }
        else
        {
            FontRenderer fontrenderer = Config.getMinecraft().fontRenderer;

            for (int i = fontrenderer.getStringWidth(": " + Lang.getOff()) + 5; fontrenderer.getStringWidth(s) + i >= btnWidth && s.length() > 0; s = s.substring(0, s.length() - 1))
            {
                ;
            }

            String s1 = so.isChanged() ? so.getValueColor(so.getValue()) : "";
            String s2 = so.getValueText(so.getValue());
            return s + ": " + s1 + s2;
        }
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton guibutton)
    {
        if (guibutton.enabled)
        {
            if (guibutton.id < 200 && guibutton instanceof GuiButtonShaderOption)
            {
                GuiButtonShaderOption guibuttonshaderoption = (GuiButtonShaderOption)guibutton;
                ShaderOption shaderoption = guibuttonshaderoption.getShaderOption();

                if (shaderoption instanceof ShaderOptionScreen)
                {
                    String s = shaderoption.getName();
                    GuiShaderOptions guishaderoptions = new GuiShaderOptions(this, this.settings, s);
                    this.mc.displayGuiScreen(guishaderoptions);
                    return;
                }

                if (isShiftKeyDown())
                {
                    shaderoption.resetValue();
                }
                else
                {
                    shaderoption.nextValue();
                }

                this.updateAllButtons();
                this.changed = true;
            }

            if (guibutton.id == 201)
            {
                ShaderOption[] ashaderoption = Shaders.getChangedOptions(Shaders.getShaderPackOptions());

                for (int i = 0; i < ashaderoption.length; ++i)
                {
                    ShaderOption shaderoption1 = ashaderoption[i];
                    shaderoption1.resetValue();
                    this.changed = true;
                }

                this.updateAllButtons();
            }

            if (guibutton.id == 200)
            {
                if (this.changed)
                {
                    Shaders.saveShaderPackOptions();
                    this.changed = false;
                    Shaders.uninit();
                }

                this.mc.displayGuiScreen(this.prevScreen);
            }
        }
    }

    protected void actionPerformedRightClick(GuiButton btn)
    {
        if (btn instanceof GuiButtonShaderOption)
        {
            GuiButtonShaderOption guibuttonshaderoption = (GuiButtonShaderOption)btn;
            ShaderOption shaderoption = guibuttonshaderoption.getShaderOption();

            if (isShiftKeyDown())
            {
                shaderoption.resetValue();
            }
            else
            {
                shaderoption.prevValue();
            }

            this.updateAllButtons();
            this.changed = true;
        }
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        super.onGuiClosed();

        if (this.changed)
        {
            Shaders.saveShaderPackOptions();
            this.changed = false;
            Shaders.uninit();
        }
    }

    private void updateAllButtons()
    {
        for (GuiButton guibutton : this.buttonList)
        {
            if (guibutton instanceof GuiButtonShaderOption)
            {
                GuiButtonShaderOption guibuttonshaderoption = (GuiButtonShaderOption)guibutton;
                ShaderOption shaderoption = guibuttonshaderoption.getShaderOption();

                if (shaderoption instanceof ShaderOptionProfile)
                {
                    ShaderOptionProfile shaderoptionprofile = (ShaderOptionProfile)shaderoption;
                    shaderoptionprofile.updateProfile();
                }

                guibuttonshaderoption.displayString = getButtonText(shaderoption, guibuttonshaderoption.getButtonWidth());
                guibuttonshaderoption.valueChanged();
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int x, int y, float f)
    {
        this.drawDefaultBackground();

        if (this.screenText != null)
        {
            this.drawCenteredString(this.fontRenderer, this.screenText, this.width / 2, 15, 16777215);
        }
        else
        {
            this.drawCenteredString(this.fontRenderer, this.title, this.width / 2, 15, 16777215);
        }

        super.drawScreen(x, y, f);

        if (Math.abs(x - this.lastMouseX) <= 5 && Math.abs(y - this.lastMouseY) <= 5)
        {
            this.drawTooltips(x, y, this.buttonList);
        }
        else
        {
            this.lastMouseX = x;
            this.lastMouseY = y;
            this.mouseStillTime = System.currentTimeMillis();
        }
    }

    private void drawTooltips(int x, int y, List buttons)
    {
        int i = 700;

        if (System.currentTimeMillis() >= this.mouseStillTime + (long)i)
        {
            int j = this.width / 2 - 150;
            int k = this.height / 6 - 7;

            if (y <= k + 98)
            {
                k += 105;
            }

            int l = j + 150 + 150;
            int i1 = k + 84 + 10;
            GuiButton guibutton = getSelectedButton(buttons, x, y);

            if (guibutton instanceof GuiButtonShaderOption)
            {
                GuiButtonShaderOption guibuttonshaderoption = (GuiButtonShaderOption)guibutton;
                ShaderOption shaderoption = guibuttonshaderoption.getShaderOption();
                String[] astring = this.makeTooltipLines(shaderoption, l - j);

                if (astring == null)
                {
                    return;
                }

                this.drawGradientRect(j, k, l, i1, -536870912, -536870912);

                for (int j1 = 0; j1 < astring.length; ++j1)
                {
                    String s = astring[j1];
                    int k1 = 14540253;

                    if (s.endsWith("!"))
                    {
                        k1 = 16719904;
                    }

                    this.fontRenderer.drawStringWithShadow(s, (float)(j + 5), (float)(k + 5 + j1 * 11), k1);
                }
            }
        }
    }

    private String[] makeTooltipLines(ShaderOption so, int width)
    {
        if (so instanceof ShaderOptionProfile)
        {
            return null;
        }
        else
        {
            String s = so.getNameText();
            String s1 = Config.normalize(so.getDescriptionText()).trim();
            String[] astring = this.splitDescription(s1);
            String s2 = null;

            if (!s.equals(so.getName()) && this.settings.advancedItemTooltips)
            {
                s2 = "\u00a78" + Lang.get("of.general.id") + ": " + so.getName();
            }

            String s3 = null;

            if (so.getPaths() != null && this.settings.advancedItemTooltips)
            {
                s3 = "\u00a78" + Lang.get("of.general.from") + ": " + Config.arrayToString((Object[])so.getPaths());
            }

            String s4 = null;

            if (so.getValueDefault() != null && this.settings.advancedItemTooltips)
            {
                String s5 = so.isEnabled() ? so.getValueText(so.getValueDefault()) : Lang.get("of.general.ambiguous");
                s4 = "\u00a78" + Lang.getDefault() + ": " + s5;
            }

            List<String> list = new ArrayList<String>();
            list.add(s);
            list.addAll(Arrays.asList(astring));

            if (s2 != null)
            {
                list.add(s2);
            }

            if (s3 != null)
            {
                list.add(s3);
            }

            if (s4 != null)
            {
                list.add(s4);
            }

            String[] astring1 = this.makeTooltipLines(width, list);
            return astring1;
        }
    }

    private String[] splitDescription(String desc)
    {
        if (desc.length() <= 0)
        {
            return new String[0];
        }
        else
        {
            desc = StrUtils.removePrefix(desc, "//");
            String[] astring = desc.split("\\. ");

            for (int i = 0; i < astring.length; ++i)
            {
                astring[i] = "- " + astring[i].trim();
                astring[i] = StrUtils.removeSuffix(astring[i], ".");
            }

            return astring;
        }
    }

    private String[] makeTooltipLines(int width, List<String> args)
    {
        FontRenderer fontrenderer = Config.getMinecraft().fontRenderer;
        List<String> list = new ArrayList<String>();

        for (int i = 0; i < args.size(); ++i)
        {
            String s = args.get(i);

            if (s != null && s.length() > 0)
            {
                for (String s1 : fontrenderer.listFormattedStringToWidth(s, width))
                {
                    list.add(s1);
                }
            }
        }

        String[] astring = (String[])list.toArray(new String[list.size()]);
        return astring;
    }
}
