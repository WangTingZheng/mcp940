package net.minecraft.client.gui.spectator;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.categories.SpectatorDetails;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class SpectatorMenu
{
    private static final ISpectatorMenuObject CLOSE_ITEM = new SpectatorMenu.EndSpectatorObject();
    private static final ISpectatorMenuObject SCROLL_LEFT = new SpectatorMenu.MoveMenuObject(-1, true);
    private static final ISpectatorMenuObject SCROLL_RIGHT_ENABLED = new SpectatorMenu.MoveMenuObject(1, true);
    private static final ISpectatorMenuObject SCROLL_RIGHT_DISABLED = new SpectatorMenu.MoveMenuObject(1, false);
    public static final ISpectatorMenuObject EMPTY_SLOT = new ISpectatorMenuObject()
    {
        public void selectItem(SpectatorMenu menu)
        {
        }
        public ITextComponent getSpectatorName()
        {
            return new TextComponentString("");
        }
        public void renderIcon(float brightness, int alpha)
        {
        }
        public boolean isEnabled()
        {
            return false;
        }
    };
    private final ISpectatorMenuRecipient listener;
    private final List<SpectatorDetails> previousCategories = Lists.<SpectatorDetails>newArrayList();
    private ISpectatorMenuView category = new BaseSpectatorGroup();
    private int selectedSlot = -1;
    private int page;

    public SpectatorMenu(ISpectatorMenuRecipient menu)
    {
        this.listener = menu;
    }

    public ISpectatorMenuObject getItem(int index)
    {
        int i = index + this.page * 6;

        if (this.page > 0 && index == 0)
        {
            return SCROLL_LEFT;
        }
        else if (index == 7)
        {
            return i < this.category.getItems().size() ? SCROLL_RIGHT_ENABLED : SCROLL_RIGHT_DISABLED;
        }
        else if (index == 8)
        {
            return CLOSE_ITEM;
        }
        else
        {
            return i >= 0 && i < this.category.getItems().size() ? (ISpectatorMenuObject)MoreObjects.firstNonNull(this.category.getItems().get(i), EMPTY_SLOT) : EMPTY_SLOT;
        }
    }

    public List<ISpectatorMenuObject> getItems()
    {
        List<ISpectatorMenuObject> list = Lists.<ISpectatorMenuObject>newArrayList();

        for (int i = 0; i <= 8; ++i)
        {
            list.add(this.getItem(i));
        }

        return list;
    }

    public ISpectatorMenuObject getSelectedItem()
    {
        return this.getItem(this.selectedSlot);
    }

    public ISpectatorMenuView getSelectedCategory()
    {
        return this.category;
    }

    public void selectSlot(int slotIn)
    {
        ISpectatorMenuObject ispectatormenuobject = this.getItem(slotIn);

        if (ispectatormenuobject != EMPTY_SLOT)
        {
            if (this.selectedSlot == slotIn && ispectatormenuobject.isEnabled())
            {
                ispectatormenuobject.selectItem(this);
            }
            else
            {
                this.selectedSlot = slotIn;
            }
        }
    }

    public void exit()
    {
        this.listener.onSpectatorMenuClosed(this);
    }

    public int getSelectedSlot()
    {
        return this.selectedSlot;
    }

    public void selectCategory(ISpectatorMenuView menuView)
    {
        this.previousCategories.add(this.getCurrentPage());
        this.category = menuView;
        this.selectedSlot = -1;
        this.page = 0;
    }

    public SpectatorDetails getCurrentPage()
    {
        return new SpectatorDetails(this.category, this.getItems(), this.selectedSlot);
    }

    static class EndSpectatorObject implements ISpectatorMenuObject
    {
        private EndSpectatorObject()
        {
        }

        public void selectItem(SpectatorMenu menu)
        {
            menu.exit();
        }

        public ITextComponent getSpectatorName()
        {
            return new TextComponentTranslation("spectatorMenu.close", new Object[0]);
        }

        public void renderIcon(float brightness, int alpha)
        {
            Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.SPECTATOR_WIDGETS);
            Gui.drawModalRectWithCustomSizedTexture(0, 0, 128.0F, 0.0F, 16, 16, 256.0F, 256.0F);
        }

        public boolean isEnabled()
        {
            return true;
        }
    }

    static class MoveMenuObject implements ISpectatorMenuObject
    {
        private final int direction;
        private final boolean enabled;

        public MoveMenuObject(int p_i45495_1_, boolean p_i45495_2_)
        {
            this.direction = p_i45495_1_;
            this.enabled = p_i45495_2_;
        }

        public void selectItem(SpectatorMenu menu)
        {
            menu.page = menu.page + this.direction;
        }

        public ITextComponent getSpectatorName()
        {
            return this.direction < 0 ? new TextComponentTranslation("spectatorMenu.previous_page", new Object[0]) : new TextComponentTranslation("spectatorMenu.next_page", new Object[0]);
        }

        public void renderIcon(float brightness, int alpha)
        {
            Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.SPECTATOR_WIDGETS);

            if (this.direction < 0)
            {
                Gui.drawModalRectWithCustomSizedTexture(0, 0, 144.0F, 0.0F, 16, 16, 256.0F, 256.0F);
            }
            else
            {
                Gui.drawModalRectWithCustomSizedTexture(0, 0, 160.0F, 0.0F, 16, 16, 256.0F, 256.0F);
            }
        }

        public boolean isEnabled()
        {
            return this.enabled;
        }
    }
}
