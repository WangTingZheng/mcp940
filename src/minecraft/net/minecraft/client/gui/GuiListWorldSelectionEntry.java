package net.minecraft.client.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraft.world.storage.WorldSummary;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiListWorldSelectionEntry implements GuiListExtended.IGuiListEntry
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat();
    private static final ResourceLocation ICON_MISSING = new ResourceLocation("textures/misc/unknown_server.png");
    private static final ResourceLocation ICON_OVERLAY_LOCATION = new ResourceLocation("textures/gui/world_selection.png");
    private final Minecraft client;
    private final GuiWorldSelection worldSelScreen;
    private final WorldSummary worldSummary;
    private final ResourceLocation iconLocation;
    private final GuiListWorldSelection containingListSel;
    private File iconFile;
    private DynamicTexture icon;
    private long lastClickTime;

    public GuiListWorldSelectionEntry(GuiListWorldSelection listWorldSelIn, WorldSummary worldSummaryIn, ISaveFormat saveFormat)
    {
        this.containingListSel = listWorldSelIn;
        this.worldSelScreen = listWorldSelIn.getGuiWorldSelection();
        this.worldSummary = worldSummaryIn;
        this.client = Minecraft.getMinecraft();
        this.iconLocation = new ResourceLocation("worlds/" + worldSummaryIn.getFileName() + "/icon");
        this.iconFile = saveFormat.getFile(worldSummaryIn.getFileName(), "icon.png");

        if (!this.iconFile.isFile())
        {
            this.iconFile = null;
        }

        this.loadServerIcon();
    }

    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, float partialTicks)
    {
        String s = this.worldSummary.getDisplayName();
        String s1 = this.worldSummary.getFileName() + " (" + DATE_FORMAT.format(new Date(this.worldSummary.getLastTimePlayed())) + ")";
        String s2 = "";

        if (StringUtils.isEmpty(s))
        {
            s = I18n.format("selectWorld.world") + " " + (slotIndex + 1);
        }

        if (this.worldSummary.requiresConversion())
        {
            s2 = I18n.format("selectWorld.conversion") + " " + s2;
        }
        else
        {
            s2 = I18n.format("gameMode." + this.worldSummary.getEnumGameType().getName());

            if (this.worldSummary.isHardcoreModeEnabled())
            {
                s2 = TextFormatting.DARK_RED + I18n.format("gameMode.hardcore") + TextFormatting.RESET;
            }

            if (this.worldSummary.getCheatsEnabled())
            {
                s2 = s2 + ", " + I18n.format("selectWorld.cheats");
            }

            String s3 = this.worldSummary.getVersionName();

            if (this.worldSummary.markVersionInList())
            {
                if (this.worldSummary.askToOpenWorld())
                {
                    s2 = s2 + ", " + I18n.format("selectWorld.version") + " " + TextFormatting.RED + s3 + TextFormatting.RESET;
                }
                else
                {
                    s2 = s2 + ", " + I18n.format("selectWorld.version") + " " + TextFormatting.ITALIC + s3 + TextFormatting.RESET;
                }
            }
            else
            {
                s2 = s2 + ", " + I18n.format("selectWorld.version") + " " + s3;
            }
        }

        this.client.fontRenderer.drawString(s, x + 32 + 3, y + 1, 16777215);
        this.client.fontRenderer.drawString(s1, x + 32 + 3, y + this.client.fontRenderer.FONT_HEIGHT + 3, 8421504);
        this.client.fontRenderer.drawString(s2, x + 32 + 3, y + this.client.fontRenderer.FONT_HEIGHT + this.client.fontRenderer.FONT_HEIGHT + 3, 8421504);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.client.getTextureManager().bindTexture(this.icon != null ? this.iconLocation : ICON_MISSING);
        GlStateManager.enableBlend();
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
        GlStateManager.disableBlend();

        if (this.client.gameSettings.touchscreen || isSelected)
        {
            this.client.getTextureManager().bindTexture(ICON_OVERLAY_LOCATION);
            Gui.drawRect(x, y, x + 32, y + 32, -1601138544);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            int j = mouseX - x;
            int i = j < 32 ? 32 : 0;

            if (this.worldSummary.markVersionInList())
            {
                Gui.drawModalRectWithCustomSizedTexture(x, y, 32.0F, (float)i, 32, 32, 256.0F, 256.0F);

                if (this.worldSummary.askToOpenWorld())
                {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0F, (float)i, 32, 32, 256.0F, 256.0F);

                    if (j < 32)
                    {
                        this.worldSelScreen.setVersionTooltip(TextFormatting.RED + I18n.format("selectWorld.tooltip.fromNewerVersion1") + "\n" + TextFormatting.RED + I18n.format("selectWorld.tooltip.fromNewerVersion2"));
                    }
                }
                else
                {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0F, (float)i, 32, 32, 256.0F, 256.0F);

                    if (j < 32)
                    {
                        this.worldSelScreen.setVersionTooltip(TextFormatting.GOLD + I18n.format("selectWorld.tooltip.snapshot1") + "\n" + TextFormatting.GOLD + I18n.format("selectWorld.tooltip.snapshot2"));
                    }
                }
            }
            else
            {
                Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, (float)i, 32, 32, 256.0F, 256.0F);
            }
        }
    }

    /**
     * Called when the mouse is clicked within this entry. Returning true means that something within this entry was
     * clicked and the list should not be dragged.
     */
    public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY)
    {
        this.containingListSel.selectWorld(slotIndex);

        if (relativeX <= 32 && relativeX < 32)
        {
            this.joinWorld();
            return true;
        }
        else if (Minecraft.getSystemTime() - this.lastClickTime < 250L)
        {
            this.joinWorld();
            return true;
        }
        else
        {
            this.lastClickTime = Minecraft.getSystemTime();
            return false;
        }
    }

    public void joinWorld()
    {
        if (this.worldSummary.askToOpenWorld())
        {
            this.client.displayGuiScreen(new GuiYesNo(new GuiYesNoCallback()
            {
                public void confirmClicked(boolean result, int id)
                {
                    if (result)
                    {
                        GuiListWorldSelectionEntry.this.loadWorld();
                    }
                    else
                    {
                        GuiListWorldSelectionEntry.this.client.displayGuiScreen(GuiListWorldSelectionEntry.this.worldSelScreen);
                    }
                }
            }, I18n.format("selectWorld.versionQuestion"), I18n.format("selectWorld.versionWarning", this.worldSummary.getVersionName()), I18n.format("selectWorld.versionJoinButton"), I18n.format("gui.cancel"), 0));
        }
        else
        {
            this.loadWorld();
        }
    }

    public void deleteWorld()
    {
        this.client.displayGuiScreen(new GuiYesNo(new GuiYesNoCallback()
        {
            public void confirmClicked(boolean result, int id)
            {
                if (result)
                {
                    GuiListWorldSelectionEntry.this.client.displayGuiScreen(new GuiScreenWorking());
                    ISaveFormat isaveformat = GuiListWorldSelectionEntry.this.client.getSaveLoader();
                    isaveformat.flushCache();
                    isaveformat.deleteWorldDirectory(GuiListWorldSelectionEntry.this.worldSummary.getFileName());
                    GuiListWorldSelectionEntry.this.containingListSel.refreshList();
                }

                GuiListWorldSelectionEntry.this.client.displayGuiScreen(GuiListWorldSelectionEntry.this.worldSelScreen);
            }
        }, I18n.format("selectWorld.deleteQuestion"), "'" + this.worldSummary.getDisplayName() + "' " + I18n.format("selectWorld.deleteWarning"), I18n.format("selectWorld.deleteButton"), I18n.format("gui.cancel"), 0));
    }

    public void editWorld()
    {
        this.client.displayGuiScreen(new GuiWorldEdit(this.worldSelScreen, this.worldSummary.getFileName()));
    }

    public void recreateWorld()
    {
        this.client.displayGuiScreen(new GuiScreenWorking());
        GuiCreateWorld guicreateworld = new GuiCreateWorld(this.worldSelScreen);
        ISaveHandler isavehandler = this.client.getSaveLoader().getSaveLoader(this.worldSummary.getFileName(), false);
        WorldInfo worldinfo = isavehandler.loadWorldInfo();
        isavehandler.flush();

        if (worldinfo != null)
        {
            guicreateworld.recreateFromExistingWorld(worldinfo);
            this.client.displayGuiScreen(guicreateworld);
        }
    }

    private void loadWorld()
    {
        this.client.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));

        if (this.client.getSaveLoader().canLoadWorld(this.worldSummary.getFileName()))
        {
            this.client.launchIntegratedServer(this.worldSummary.getFileName(), this.worldSummary.getDisplayName(), (WorldSettings)null);
        }
    }

    private void loadServerIcon()
    {
        boolean flag = this.iconFile != null && this.iconFile.isFile();

        if (flag)
        {
            BufferedImage bufferedimage;

            try
            {
                bufferedimage = ImageIO.read(this.iconFile);
                Validate.validState(bufferedimage.getWidth() == 64, "Must be 64 pixels wide");
                Validate.validState(bufferedimage.getHeight() == 64, "Must be 64 pixels high");
            }
            catch (Throwable throwable)
            {
                LOGGER.error("Invalid icon for world {}", this.worldSummary.getFileName(), throwable);
                this.iconFile = null;
                return;
            }

            if (this.icon == null)
            {
                this.icon = new DynamicTexture(bufferedimage.getWidth(), bufferedimage.getHeight());
                this.client.getTextureManager().loadTexture(this.iconLocation, this.icon);
            }

            bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), this.icon.getTextureData(), 0, bufferedimage.getWidth());
            this.icon.updateDynamicTexture();
        }
        else if (!flag)
        {
            this.client.getTextureManager().deleteTexture(this.iconLocation);
            this.icon = null;
        }
    }

    /**
     * Fired when the mouse button is released. Arguments: index, x, y, mouseEvent, relativeX, relativeY
     */
    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY)
    {
    }

    public void updatePosition(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_)
    {
    }
}
