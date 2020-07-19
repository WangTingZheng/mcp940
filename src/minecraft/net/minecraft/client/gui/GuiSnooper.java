package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.TreeMap;
import java.util.Map.Entry;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GuiSnooper extends GuiScreen
{
    private final GuiScreen lastScreen;

    /** Reference to the GameSettings object. */
    private final GameSettings game_settings_2;
    private final java.util.List<String> keys = Lists.<String>newArrayList();
    private final java.util.List<String> values = Lists.<String>newArrayList();
    private String title;
    private String[] desc;
    private GuiSnooper.List list;
    private GuiButton toggleButton;

    public GuiSnooper(GuiScreen p_i1061_1_, GameSettings p_i1061_2_)
    {
        this.lastScreen = p_i1061_1_;
        this.game_settings_2 = p_i1061_2_;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.title = I18n.format("options.snooper.title");
        String s = I18n.format("options.snooper.desc");
        java.util.List<String> list = Lists.<String>newArrayList();

        for (String s1 : this.fontRenderer.listFormattedStringToWidth(s, this.width - 30))
        {
            list.add(s1);
        }

        this.desc = (String[])list.toArray(new String[list.size()]);
        this.keys.clear();
        this.values.clear();
        this.toggleButton = this.addButton(new GuiButton(1, this.width / 2 - 152, this.height - 30, 150, 20, this.game_settings_2.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED)));
        this.buttonList.add(new GuiButton(2, this.width / 2 + 2, this.height - 30, 150, 20, I18n.format("gui.done")));
        boolean flag = this.mc.getIntegratedServer() != null && this.mc.getIntegratedServer().getPlayerUsageSnooper() != null;

        for (Entry<String, String> entry : (new TreeMap<String, String>(this.mc.getPlayerUsageSnooper().getCurrentStats())).entrySet())
        {
            this.keys.add((flag ? "C " : "") + (String)entry.getKey());
            this.values.add(this.fontRenderer.trimStringToWidth(entry.getValue(), this.width - 220));
        }

        if (flag)
        {
            for (Entry<String, String> entry1 : (new TreeMap<String, String>(this.mc.getIntegratedServer().getPlayerUsageSnooper().getCurrentStats())).entrySet())
            {
                this.keys.add("S " + (String)entry1.getKey());
                this.values.add(this.fontRenderer.trimStringToWidth(entry1.getValue(), this.width - 220));
            }
        }

        this.list = new GuiSnooper.List();
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        this.list.handleMouseInput();
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
            if (button.id == 2)
            {
                this.game_settings_2.saveOptions();
                this.game_settings_2.saveOptions();
                this.mc.displayGuiScreen(this.lastScreen);
            }

            if (button.id == 1)
            {
                this.game_settings_2.setOptionValue(GameSettings.Options.SNOOPER_ENABLED, 1);
                this.toggleButton.displayString = this.game_settings_2.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED);
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.list.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRenderer, this.title, this.width / 2, 8, 16777215);
        int i = 22;

        for (String s : this.desc)
        {
            this.drawCenteredString(this.fontRenderer, s, this.width / 2, i, 8421504);
            i += this.fontRenderer.FONT_HEIGHT;
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    class List extends GuiSlot
    {
        public List()
        {
            super(GuiSnooper.this.mc, GuiSnooper.this.width, GuiSnooper.this.height, 80, GuiSnooper.this.height - 40, GuiSnooper.this.fontRenderer.FONT_HEIGHT + 1);
        }

        protected int getSize()
        {
            return GuiSnooper.this.keys.size();
        }

        protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY)
        {
        }

        protected boolean isSelected(int slotIndex)
        {
            return false;
        }

        protected void drawBackground()
        {
        }

        protected void drawSlot(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_)
        {
            GuiSnooper.this.fontRenderer.drawString(GuiSnooper.this.keys.get(p_192637_1_), 10, p_192637_3_, 16777215);
            GuiSnooper.this.fontRenderer.drawString(GuiSnooper.this.values.get(p_192637_1_), 230, p_192637_3_, 16777215);
        }

        protected int getScrollBarX()
        {
            return this.width - 10;
        }
    }
}
