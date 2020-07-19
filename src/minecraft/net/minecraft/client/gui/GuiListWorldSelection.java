package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldSummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiListWorldSelection extends GuiListExtended
{
    private static final Logger LOGGER = LogManager.getLogger();
    private final GuiWorldSelection worldSelection;
    private final List<GuiListWorldSelectionEntry> entries = Lists.<GuiListWorldSelectionEntry>newArrayList();

    /** Index to the currently selected world */
    private int selectedIdx = -1;

    public GuiListWorldSelection(GuiWorldSelection p_i46590_1_, Minecraft clientIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn)
    {
        super(clientIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
        this.worldSelection = p_i46590_1_;
        this.refreshList();
    }

    public void refreshList()
    {
        ISaveFormat isaveformat = this.mc.getSaveLoader();
        List<WorldSummary> list;

        try
        {
            list = isaveformat.getSaveList();
        }
        catch (AnvilConverterException anvilconverterexception)
        {
            LOGGER.error("Couldn't load level list", (Throwable)anvilconverterexception);
            this.mc.displayGuiScreen(new GuiErrorScreen(I18n.format("selectWorld.unable_to_load"), anvilconverterexception.getMessage()));
            return;
        }

        Collections.sort(list);

        for (WorldSummary worldsummary : list)
        {
            this.entries.add(new GuiListWorldSelectionEntry(this, worldsummary, this.mc.getSaveLoader()));
        }
    }

    /**
     * Gets the IGuiListEntry object for the given index
     */
    public GuiListWorldSelectionEntry getListEntry(int index)
    {
        return this.entries.get(index);
    }

    protected int getSize()
    {
        return this.entries.size();
    }

    protected int getScrollBarX()
    {
        return super.getScrollBarX() + 20;
    }

    /**
     * Gets the width of the list
     */
    public int getListWidth()
    {
        return super.getListWidth() + 50;
    }

    public void selectWorld(int idx)
    {
        this.selectedIdx = idx;
        this.worldSelection.selectWorld(this.getSelectedWorld());
    }

    /**
     * Returns true if the element passed in is currently selected
     */
    protected boolean isSelected(int slotIndex)
    {
        return slotIndex == this.selectedIdx;
    }

    @Nullable
    public GuiListWorldSelectionEntry getSelectedWorld()
    {
        return this.selectedIdx >= 0 && this.selectedIdx < this.getSize() ? this.getListEntry(this.selectedIdx) : null;
    }

    public GuiWorldSelection getGuiWorldSelection()
    {
        return this.worldSelection;
    }
}
