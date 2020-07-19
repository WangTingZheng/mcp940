package net.minecraft.client.gui.inventory;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.CreativeSettings;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.HotbarSnapshot;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiContainerCreative extends InventoryEffectRenderer
{
    /** The location of the creative inventory tabs texture */
    private static final ResourceLocation CREATIVE_INVENTORY_TABS = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
    private static final InventoryBasic basicInventory = new InventoryBasic("tmp", true, 45);

    /** Currently selected creative inventory tab index. */
    private static int selectedTabIndex = CreativeTabs.BUILDING_BLOCKS.getTabIndex();

    /** Amount scrolled in Creative mode inventory (0 = top, 1 = bottom) */
    private float currentScroll;

    /** True if the scrollbar is being dragged */
    private boolean isScrolling;

    /**
     * True if the left mouse button was held down last time drawScreen was called.
     */
    private boolean wasClicking;
    private GuiTextField searchField;
    private List<Slot> originalSlots;
    private Slot destroyItemSlot;
    private boolean clearSearch;
    private CreativeCrafting listener;

    public GuiContainerCreative(EntityPlayer player)
    {
        super(new GuiContainerCreative.ContainerCreative(player));
        player.openContainer = this.inventorySlots;
        this.allowUserInput = true;
        this.ySize = 136;
        this.xSize = 195;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        if (!this.mc.playerController.isInCreativeMode())
        {
            this.mc.displayGuiScreen(new GuiInventory(this.mc.player));
        }
    }

    /**
     * Called when the mouse is clicked over a slot or outside the gui.
     */
    protected void handleMouseClick(@Nullable Slot slotIn, int slotId, int mouseButton, ClickType type)
    {
        this.clearSearch = true;
        boolean flag = type == ClickType.QUICK_MOVE;
        type = slotId == -999 && type == ClickType.PICKUP ? ClickType.THROW : type;

        if (slotIn == null && selectedTabIndex != CreativeTabs.INVENTORY.getTabIndex() && type != ClickType.QUICK_CRAFT)
        {
            InventoryPlayer inventoryplayer1 = this.mc.player.inventory;

            if (!inventoryplayer1.getItemStack().isEmpty())
            {
                if (mouseButton == 0)
                {
                    this.mc.player.dropItem(inventoryplayer1.getItemStack(), true);
                    this.mc.playerController.sendPacketDropItem(inventoryplayer1.getItemStack());
                    inventoryplayer1.setItemStack(ItemStack.EMPTY);
                }

                if (mouseButton == 1)
                {
                    ItemStack itemstack6 = inventoryplayer1.getItemStack().splitStack(1);
                    this.mc.player.dropItem(itemstack6, true);
                    this.mc.playerController.sendPacketDropItem(itemstack6);
                }
            }
        }
        else
        {
            if (slotIn != null && !slotIn.canTakeStack(this.mc.player))
            {
                return;
            }

            if (slotIn == this.destroyItemSlot && flag)
            {
                for (int j = 0; j < this.mc.player.inventoryContainer.getInventory().size(); ++j)
                {
                    this.mc.playerController.sendSlotPacket(ItemStack.EMPTY, j);
                }
            }
            else if (selectedTabIndex == CreativeTabs.INVENTORY.getTabIndex())
            {
                if (slotIn == this.destroyItemSlot)
                {
                    this.mc.player.inventory.setItemStack(ItemStack.EMPTY);
                }
                else if (type == ClickType.THROW && slotIn != null && slotIn.getHasStack())
                {
                    ItemStack itemstack = slotIn.decrStackSize(mouseButton == 0 ? 1 : slotIn.getStack().getMaxStackSize());
                    ItemStack itemstack1 = slotIn.getStack();
                    this.mc.player.dropItem(itemstack, true);
                    this.mc.playerController.sendPacketDropItem(itemstack);
                    this.mc.playerController.sendSlotPacket(itemstack1, ((GuiContainerCreative.CreativeSlot)slotIn).slot.slotNumber);
                }
                else if (type == ClickType.THROW && !this.mc.player.inventory.getItemStack().isEmpty())
                {
                    this.mc.player.dropItem(this.mc.player.inventory.getItemStack(), true);
                    this.mc.playerController.sendPacketDropItem(this.mc.player.inventory.getItemStack());
                    this.mc.player.inventory.setItemStack(ItemStack.EMPTY);
                }
                else
                {
                    this.mc.player.inventoryContainer.slotClick(slotIn == null ? slotId : ((GuiContainerCreative.CreativeSlot)slotIn).slot.slotNumber, mouseButton, type, this.mc.player);
                    this.mc.player.inventoryContainer.detectAndSendChanges();
                }
            }
            else if (type != ClickType.QUICK_CRAFT && slotIn.inventory == basicInventory)
            {
                InventoryPlayer inventoryplayer = this.mc.player.inventory;
                ItemStack itemstack5 = inventoryplayer.getItemStack();
                ItemStack itemstack7 = slotIn.getStack();

                if (type == ClickType.SWAP)
                {
                    if (!itemstack7.isEmpty() && mouseButton >= 0 && mouseButton < 9)
                    {
                        ItemStack itemstack10 = itemstack7.copy();
                        itemstack10.setCount(itemstack10.getMaxStackSize());
                        this.mc.player.inventory.setInventorySlotContents(mouseButton, itemstack10);
                        this.mc.player.inventoryContainer.detectAndSendChanges();
                    }

                    return;
                }

                if (type == ClickType.CLONE)
                {
                    if (inventoryplayer.getItemStack().isEmpty() && slotIn.getHasStack())
                    {
                        ItemStack itemstack9 = slotIn.getStack().copy();
                        itemstack9.setCount(itemstack9.getMaxStackSize());
                        inventoryplayer.setItemStack(itemstack9);
                    }

                    return;
                }

                if (type == ClickType.THROW)
                {
                    if (!itemstack7.isEmpty())
                    {
                        ItemStack itemstack8 = itemstack7.copy();
                        itemstack8.setCount(mouseButton == 0 ? 1 : itemstack8.getMaxStackSize());
                        this.mc.player.dropItem(itemstack8, true);
                        this.mc.playerController.sendPacketDropItem(itemstack8);
                    }

                    return;
                }

                if (!itemstack5.isEmpty() && !itemstack7.isEmpty() && itemstack5.isItemEqual(itemstack7) && ItemStack.areItemStackTagsEqual(itemstack5, itemstack7))
                {
                    if (mouseButton == 0)
                    {
                        if (flag)
                        {
                            itemstack5.setCount(itemstack5.getMaxStackSize());
                        }
                        else if (itemstack5.getCount() < itemstack5.getMaxStackSize())
                        {
                            itemstack5.grow(1);
                        }
                    }
                    else
                    {
                        itemstack5.shrink(1);
                    }
                }
                else if (!itemstack7.isEmpty() && itemstack5.isEmpty())
                {
                    inventoryplayer.setItemStack(itemstack7.copy());
                    itemstack5 = inventoryplayer.getItemStack();

                    if (flag)
                    {
                        itemstack5.setCount(itemstack5.getMaxStackSize());
                    }
                }
                else if (mouseButton == 0)
                {
                    inventoryplayer.setItemStack(ItemStack.EMPTY);
                }
                else
                {
                    inventoryplayer.getItemStack().shrink(1);
                }
            }
            else if (this.inventorySlots != null)
            {
                ItemStack itemstack3 = slotIn == null ? ItemStack.EMPTY : this.inventorySlots.getSlot(slotIn.slotNumber).getStack();
                this.inventorySlots.slotClick(slotIn == null ? slotId : slotIn.slotNumber, mouseButton, type, this.mc.player);

                if (Container.getDragEvent(mouseButton) == 2)
                {
                    for (int k = 0; k < 9; ++k)
                    {
                        this.mc.playerController.sendSlotPacket(this.inventorySlots.getSlot(45 + k).getStack(), 36 + k);
                    }
                }
                else if (slotIn != null)
                {
                    ItemStack itemstack4 = this.inventorySlots.getSlot(slotIn.slotNumber).getStack();
                    this.mc.playerController.sendSlotPacket(itemstack4, slotIn.slotNumber - this.inventorySlots.inventorySlots.size() + 9 + 36);
                    int i = 45 + mouseButton;

                    if (type == ClickType.SWAP)
                    {
                        this.mc.playerController.sendSlotPacket(itemstack3, i - this.inventorySlots.inventorySlots.size() + 9 + 36);
                    }
                    else if (type == ClickType.THROW && !itemstack3.isEmpty())
                    {
                        ItemStack itemstack2 = itemstack3.copy();
                        itemstack2.setCount(mouseButton == 0 ? 1 : itemstack2.getMaxStackSize());
                        this.mc.player.dropItem(itemstack2, true);
                        this.mc.playerController.sendPacketDropItem(itemstack2);
                    }

                    this.mc.player.inventoryContainer.detectAndSendChanges();
                }
            }
        }
    }

    protected void updateActivePotionEffects()
    {
        int i = this.guiLeft;
        super.updateActivePotionEffects();

        if (this.searchField != null && this.guiLeft != i)
        {
            this.searchField.x = this.guiLeft + 82;
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        if (this.mc.playerController.isInCreativeMode())
        {
            super.initGui();
            this.buttonList.clear();
            Keyboard.enableRepeatEvents(true);
            this.searchField = new GuiTextField(0, this.fontRenderer, this.guiLeft + 82, this.guiTop + 6, 80, this.fontRenderer.FONT_HEIGHT);
            this.searchField.setMaxStringLength(50);
            this.searchField.setEnableBackgroundDrawing(false);
            this.searchField.setVisible(false);
            this.searchField.setTextColor(16777215);
            int i = selectedTabIndex;
            selectedTabIndex = -1;
            this.setCurrentCreativeTab(CreativeTabs.CREATIVE_TAB_ARRAY[i]);
            this.listener = new CreativeCrafting(this.mc);
            this.mc.player.inventoryContainer.addListener(this.listener);
        }
        else
        {
            this.mc.displayGuiScreen(new GuiInventory(this.mc.player));
        }
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        super.onGuiClosed();

        if (this.mc.player != null && this.mc.player.inventory != null)
        {
            this.mc.player.inventoryContainer.removeListener(this.listener);
        }

        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (selectedTabIndex != CreativeTabs.SEARCH.getTabIndex())
        {
            if (GameSettings.isKeyDown(this.mc.gameSettings.keyBindChat))
            {
                this.setCurrentCreativeTab(CreativeTabs.SEARCH);
            }
            else
            {
                super.keyTyped(typedChar, keyCode);
            }
        }
        else
        {
            if (this.clearSearch)
            {
                this.clearSearch = false;
                this.searchField.setText("");
            }

            if (!this.checkHotbarKeys(keyCode))
            {
                if (this.searchField.textboxKeyTyped(typedChar, keyCode))
                {
                    this.updateCreativeSearch();
                }
                else
                {
                    super.keyTyped(typedChar, keyCode);
                }
            }
        }
    }

    private void updateCreativeSearch()
    {
        GuiContainerCreative.ContainerCreative guicontainercreative$containercreative = (GuiContainerCreative.ContainerCreative)this.inventorySlots;
        guicontainercreative$containercreative.itemList.clear();

        if (this.searchField.getText().isEmpty())
        {
            for (Item item : Item.REGISTRY)
            {
                item.getSubItems(CreativeTabs.SEARCH, guicontainercreative$containercreative.itemList);
            }
        }
        else
        {
            guicontainercreative$containercreative.itemList.addAll(this.mc.getSearchTree(SearchTreeManager.ITEMS).search(this.searchField.getText().toLowerCase(Locale.ROOT)));
        }

        this.currentScroll = 0.0F;
        guicontainercreative$containercreative.scrollTo(0.0F);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        CreativeTabs creativetabs = CreativeTabs.CREATIVE_TAB_ARRAY[selectedTabIndex];

        if (creativetabs.drawInForegroundOfTab())
        {
            GlStateManager.disableBlend();
            this.fontRenderer.drawString(I18n.format(creativetabs.getTranslatedTabLabel()), 8, 6, 4210752);
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        if (mouseButton == 0)
        {
            int i = mouseX - this.guiLeft;
            int j = mouseY - this.guiTop;

            for (CreativeTabs creativetabs : CreativeTabs.CREATIVE_TAB_ARRAY)
            {
                if (this.isMouseOverTab(creativetabs, i, j))
                {
                    return;
                }
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Called when a mouse button is released.
     */
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        if (state == 0)
        {
            int i = mouseX - this.guiLeft;
            int j = mouseY - this.guiTop;

            for (CreativeTabs creativetabs : CreativeTabs.CREATIVE_TAB_ARRAY)
            {
                if (this.isMouseOverTab(creativetabs, i, j))
                {
                    this.setCurrentCreativeTab(creativetabs);
                    return;
                }
            }
        }

        super.mouseReleased(mouseX, mouseY, state);
    }

    /**
     * returns (if you are not on the inventoryTab) and (the flag isn't set) and (you have more than 1 page of items)
     */
    private boolean needsScrollBars()
    {
        return selectedTabIndex != CreativeTabs.INVENTORY.getTabIndex() && CreativeTabs.CREATIVE_TAB_ARRAY[selectedTabIndex].shouldHidePlayerInventory() && ((GuiContainerCreative.ContainerCreative)this.inventorySlots).canScroll();
    }

    /**
     * Sets the current creative tab, restructuring the GUI as needed.
     */
    private void setCurrentCreativeTab(CreativeTabs tab)
    {
        int i = selectedTabIndex;
        selectedTabIndex = tab.getTabIndex();
        GuiContainerCreative.ContainerCreative guicontainercreative$containercreative = (GuiContainerCreative.ContainerCreative)this.inventorySlots;
        this.dragSplittingSlots.clear();
        guicontainercreative$containercreative.itemList.clear();

        if (tab == CreativeTabs.HOTBAR)
        {
            for (int j = 0; j < 9; ++j)
            {
                HotbarSnapshot hotbarsnapshot = this.mc.creativeSettings.getHotbarSnapshot(j);

                if (hotbarsnapshot.isEmpty())
                {
                    for (int k = 0; k < 9; ++k)
                    {
                        if (k == j)
                        {
                            ItemStack itemstack = new ItemStack(Items.PAPER);
                            itemstack.getOrCreateSubCompound("CustomCreativeLock");
                            String s = GameSettings.getKeyDisplayString(this.mc.gameSettings.keyBindsHotbar[j].getKeyCode());
                            String s1 = GameSettings.getKeyDisplayString(this.mc.gameSettings.keyBindSaveToolbar.getKeyCode());
                            itemstack.setStackDisplayName((new TextComponentTranslation("inventory.hotbarInfo", new Object[] {s1, s})).getUnformattedText());
                            guicontainercreative$containercreative.itemList.add(itemstack);
                        }
                        else
                        {
                            guicontainercreative$containercreative.itemList.add(ItemStack.EMPTY);
                        }
                    }
                }
                else
                {
                    guicontainercreative$containercreative.itemList.addAll(hotbarsnapshot);
                }
            }
        }
        else if (tab != CreativeTabs.SEARCH)
        {
            tab.displayAllRelevantItems(guicontainercreative$containercreative.itemList);
        }

        if (tab == CreativeTabs.INVENTORY)
        {
            Container container = this.mc.player.inventoryContainer;

            if (this.originalSlots == null)
            {
                this.originalSlots = guicontainercreative$containercreative.inventorySlots;
            }

            guicontainercreative$containercreative.inventorySlots = Lists.<Slot>newArrayList();

            for (int l = 0; l < container.inventorySlots.size(); ++l)
            {
                Slot slot = new GuiContainerCreative.CreativeSlot(container.inventorySlots.get(l), l);
                guicontainercreative$containercreative.inventorySlots.add(slot);

                if (l >= 5 && l < 9)
                {
                    int j1 = l - 5;
                    int l1 = j1 / 2;
                    int j2 = j1 % 2;
                    slot.xPos = 54 + l1 * 54;
                    slot.yPos = 6 + j2 * 27;
                }
                else if (l >= 0 && l < 5)
                {
                    slot.xPos = -2000;
                    slot.yPos = -2000;
                }
                else if (l == 45)
                {
                    slot.xPos = 35;
                    slot.yPos = 20;
                }
                else if (l < container.inventorySlots.size())
                {
                    int i1 = l - 9;
                    int k1 = i1 % 9;
                    int i2 = i1 / 9;
                    slot.xPos = 9 + k1 * 18;

                    if (l >= 36)
                    {
                        slot.yPos = 112;
                    }
                    else
                    {
                        slot.yPos = 54 + i2 * 18;
                    }
                }
            }

            this.destroyItemSlot = new Slot(basicInventory, 0, 173, 112);
            guicontainercreative$containercreative.inventorySlots.add(this.destroyItemSlot);
        }
        else if (i == CreativeTabs.INVENTORY.getTabIndex())
        {
            guicontainercreative$containercreative.inventorySlots = this.originalSlots;
            this.originalSlots = null;
        }

        if (this.searchField != null)
        {
            if (tab == CreativeTabs.SEARCH)
            {
                this.searchField.setVisible(true);
                this.searchField.setCanLoseFocus(false);
                this.searchField.setFocused(true);
                this.searchField.setText("");
                this.updateCreativeSearch();
            }
            else
            {
                this.searchField.setVisible(false);
                this.searchField.setCanLoseFocus(true);
                this.searchField.setFocused(false);
            }
        }

        this.currentScroll = 0.0F;
        guicontainercreative$containercreative.scrollTo(0.0F);
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();

        if (i != 0 && this.needsScrollBars())
        {
            int j = (((GuiContainerCreative.ContainerCreative)this.inventorySlots).itemList.size() + 9 - 1) / 9 - 5;

            if (i > 0)
            {
                i = 1;
            }

            if (i < 0)
            {
                i = -1;
            }

            this.currentScroll = (float)((double)this.currentScroll - (double)i / (double)j);
            this.currentScroll = MathHelper.clamp(this.currentScroll, 0.0F, 1.0F);
            ((GuiContainerCreative.ContainerCreative)this.inventorySlots).scrollTo(this.currentScroll);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        boolean flag = Mouse.isButtonDown(0);
        int i = this.guiLeft;
        int j = this.guiTop;
        int k = i + 175;
        int l = j + 18;
        int i1 = k + 14;
        int j1 = l + 112;

        if (!this.wasClicking && flag && mouseX >= k && mouseY >= l && mouseX < i1 && mouseY < j1)
        {
            this.isScrolling = this.needsScrollBars();
        }

        if (!flag)
        {
            this.isScrolling = false;
        }

        this.wasClicking = flag;

        if (this.isScrolling)
        {
            this.currentScroll = ((float)(mouseY - l) - 7.5F) / ((float)(j1 - l) - 15.0F);
            this.currentScroll = MathHelper.clamp(this.currentScroll, 0.0F, 1.0F);
            ((GuiContainerCreative.ContainerCreative)this.inventorySlots).scrollTo(this.currentScroll);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);

        for (CreativeTabs creativetabs : CreativeTabs.CREATIVE_TAB_ARRAY)
        {
            if (this.renderCreativeInventoryHoveringText(creativetabs, mouseX, mouseY))
            {
                break;
            }
        }

        if (this.destroyItemSlot != null && selectedTabIndex == CreativeTabs.INVENTORY.getTabIndex() && this.isPointInRegion(this.destroyItemSlot.xPos, this.destroyItemSlot.yPos, 16, 16, mouseX, mouseY))
        {
            this.drawHoveringText(I18n.format("inventory.binSlot"), mouseX, mouseY);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    protected void renderToolTip(ItemStack stack, int x, int y)
    {
        if (selectedTabIndex == CreativeTabs.SEARCH.getTabIndex())
        {
            List<String> list = stack.getTooltip(this.mc.player, this.mc.gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);
            CreativeTabs creativetabs = stack.getItem().getCreativeTab();

            if (creativetabs == null && stack.getItem() == Items.ENCHANTED_BOOK)
            {
                Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack);

                if (map.size() == 1)
                {
                    Enchantment enchantment = map.keySet().iterator().next();

                    for (CreativeTabs creativetabs1 : CreativeTabs.CREATIVE_TAB_ARRAY)
                    {
                        if (creativetabs1.hasRelevantEnchantmentType(enchantment.type))
                        {
                            creativetabs = creativetabs1;
                            break;
                        }
                    }
                }
            }

            if (creativetabs != null)
            {
                list.add(1, "" + TextFormatting.BOLD + TextFormatting.BLUE + I18n.format(creativetabs.getTranslatedTabLabel()));
            }

            for (int i = 0; i < list.size(); ++i)
            {
                if (i == 0)
                {
                    list.set(i, stack.getRarity().rarityColor + (String)list.get(i));
                }
                else
                {
                    list.set(i, TextFormatting.GRAY + (String)list.get(i));
                }
            }

            this.drawHoveringText(list, x, y);
        }
        else
        {
            super.renderToolTip(stack, x, y);
        }
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        RenderHelper.enableGUIStandardItemLighting();
        CreativeTabs creativetabs = CreativeTabs.CREATIVE_TAB_ARRAY[selectedTabIndex];

        for (CreativeTabs creativetabs1 : CreativeTabs.CREATIVE_TAB_ARRAY)
        {
            this.mc.getTextureManager().bindTexture(CREATIVE_INVENTORY_TABS);

            if (creativetabs1.getTabIndex() != selectedTabIndex)
            {
                this.drawTab(creativetabs1);
            }
        }

        this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tab_" + creativetabs.getBackgroundImageName()));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        this.searchField.drawTextBox();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.guiLeft + 175;
        int j = this.guiTop + 18;
        int k = j + 112;
        this.mc.getTextureManager().bindTexture(CREATIVE_INVENTORY_TABS);

        if (creativetabs.shouldHidePlayerInventory())
        {
            this.drawTexturedModalRect(i, j + (int)((float)(k - j - 17) * this.currentScroll), 232 + (this.needsScrollBars() ? 0 : 12), 0, 12, 15);
        }

        this.drawTab(creativetabs);

        if (creativetabs == CreativeTabs.INVENTORY)
        {
            GuiInventory.drawEntityOnScreen(this.guiLeft + 88, this.guiTop + 45, 20, (float)(this.guiLeft + 88 - mouseX), (float)(this.guiTop + 45 - 30 - mouseY), this.mc.player);
        }
    }

    /**
     * Checks if the mouse is over the given tab. Returns true if so.
     */
    protected boolean isMouseOverTab(CreativeTabs tab, int mouseX, int mouseY)
    {
        int i = tab.getTabColumn();
        int j = 28 * i;
        int k = 0;

        if (tab.isAlignedRight())
        {
            j = this.xSize - 28 * (6 - i) + 2;
        }
        else if (i > 0)
        {
            j += i;
        }

        if (tab.isTabInFirstRow())
        {
            k = k - 32;
        }
        else
        {
            k = k + this.ySize;
        }

        return mouseX >= j && mouseX <= j + 28 && mouseY >= k && mouseY <= k + 32;
    }

    /**
     * Renders the creative inventory hovering text if mouse is over it. Returns true if did render or false otherwise.
     * Params: current creative tab to be checked, current mouse x position, current mouse y position.
     */
    protected boolean renderCreativeInventoryHoveringText(CreativeTabs tab, int mouseX, int mouseY)
    {
        int i = tab.getTabColumn();
        int j = 28 * i;
        int k = 0;

        if (tab.isAlignedRight())
        {
            j = this.xSize - 28 * (6 - i) + 2;
        }
        else if (i > 0)
        {
            j += i;
        }

        if (tab.isTabInFirstRow())
        {
            k = k - 32;
        }
        else
        {
            k = k + this.ySize;
        }

        if (this.isPointInRegion(j + 3, k + 3, 23, 27, mouseX, mouseY))
        {
            this.drawHoveringText(I18n.format(tab.getTranslatedTabLabel()), mouseX, mouseY);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Draws the given tab and its background, deciding whether to highlight the tab or not based off of the selected
     * index.
     */
    protected void drawTab(CreativeTabs tab)
    {
        boolean flag = tab.getTabIndex() == selectedTabIndex;
        boolean flag1 = tab.isTabInFirstRow();
        int i = tab.getTabColumn();
        int j = i * 28;
        int k = 0;
        int l = this.guiLeft + 28 * i;
        int i1 = this.guiTop;
        int j1 = 32;

        if (flag)
        {
            k += 32;
        }

        if (tab.isAlignedRight())
        {
            l = this.guiLeft + this.xSize - 28 * (6 - i);
        }
        else if (i > 0)
        {
            l += i;
        }

        if (flag1)
        {
            i1 = i1 - 28;
        }
        else
        {
            k += 64;
            i1 = i1 + (this.ySize - 4);
        }

        GlStateManager.disableLighting();
        this.drawTexturedModalRect(l, i1, j, k, 28, 32);
        this.zLevel = 100.0F;
        this.itemRender.zLevel = 100.0F;
        l = l + 6;
        i1 = i1 + 8 + (flag1 ? 1 : -1);
        GlStateManager.enableLighting();
        GlStateManager.enableRescaleNormal();
        ItemStack itemstack = tab.getIconItemStack();
        this.itemRender.renderItemAndEffectIntoGUI(itemstack, l, i1);
        this.itemRender.renderItemOverlays(this.fontRenderer, itemstack, l, i1);
        GlStateManager.disableLighting();
        this.itemRender.zLevel = 0.0F;
        this.zLevel = 0.0F;
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 1)
        {
            this.mc.displayGuiScreen(new GuiStats(this, this.mc.player.getStatFileWriter()));
        }
    }

    /**
     * Returns the index of the currently selected tab.
     */
    public int getSelectedTabIndex()
    {
        return selectedTabIndex;
    }

    public static void handleHotbarSnapshots(Minecraft p_192044_0_, int p_192044_1_, boolean p_192044_2_, boolean p_192044_3_)
    {
        EntityPlayerSP entityplayersp = p_192044_0_.player;
        CreativeSettings creativesettings = p_192044_0_.creativeSettings;
        HotbarSnapshot hotbarsnapshot = creativesettings.getHotbarSnapshot(p_192044_1_);

        if (p_192044_2_)
        {
            for (int i = 0; i < InventoryPlayer.getHotbarSize(); ++i)
            {
                ItemStack itemstack = ((ItemStack)hotbarsnapshot.get(i)).copy();
                entityplayersp.inventory.setInventorySlotContents(i, itemstack);
                p_192044_0_.playerController.sendSlotPacket(itemstack, 36 + i);
            }

            entityplayersp.inventoryContainer.detectAndSendChanges();
        }
        else if (p_192044_3_)
        {
            for (int j = 0; j < InventoryPlayer.getHotbarSize(); ++j)
            {
                hotbarsnapshot.set(j, entityplayersp.inventory.getStackInSlot(j).copy());
            }

            String s = GameSettings.getKeyDisplayString(p_192044_0_.gameSettings.keyBindsHotbar[p_192044_1_].getKeyCode());
            String s1 = GameSettings.getKeyDisplayString(p_192044_0_.gameSettings.keyBindLoadToolbar.getKeyCode());
            p_192044_0_.ingameGUI.setOverlayMessage(new TextComponentTranslation("inventory.hotbarSaved", new Object[] {s1, s}), false);
            creativesettings.write();
        }
    }

    public static class ContainerCreative extends Container
    {
        public NonNullList<ItemStack> itemList = NonNullList.<ItemStack>create();

        public ContainerCreative(EntityPlayer player)
        {
            InventoryPlayer inventoryplayer = player.inventory;

            for (int i = 0; i < 5; ++i)
            {
                for (int j = 0; j < 9; ++j)
                {
                    this.addSlotToContainer(new GuiContainerCreative.LockedSlot(GuiContainerCreative.basicInventory, i * 9 + j, 9 + j * 18, 18 + i * 18));
                }
            }

            for (int k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(inventoryplayer, k, 9 + k * 18, 112));
            }

            this.scrollTo(0.0F);
        }

        public boolean canInteractWith(EntityPlayer playerIn)
        {
            return true;
        }

        public void scrollTo(float pos)
        {
            int i = (this.itemList.size() + 9 - 1) / 9 - 5;
            int j = (int)((double)(pos * (float)i) + 0.5D);

            if (j < 0)
            {
                j = 0;
            }

            for (int k = 0; k < 5; ++k)
            {
                for (int l = 0; l < 9; ++l)
                {
                    int i1 = l + (k + j) * 9;

                    if (i1 >= 0 && i1 < this.itemList.size())
                    {
                        GuiContainerCreative.basicInventory.setInventorySlotContents(l + k * 9, this.itemList.get(i1));
                    }
                    else
                    {
                        GuiContainerCreative.basicInventory.setInventorySlotContents(l + k * 9, ItemStack.EMPTY);
                    }
                }
            }
        }

        public boolean canScroll()
        {
            return this.itemList.size() > 45;
        }

        public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
        {
            if (index >= this.inventorySlots.size() - 9 && index < this.inventorySlots.size())
            {
                Slot slot = this.inventorySlots.get(index);

                if (slot != null && slot.getHasStack())
                {
                    slot.putStack(ItemStack.EMPTY);
                }
            }

            return ItemStack.EMPTY;
        }

        public boolean canMergeSlot(ItemStack stack, Slot slotIn)
        {
            return slotIn.yPos > 90;
        }

        public boolean canDragIntoSlot(Slot slotIn)
        {
            return slotIn.inventory instanceof InventoryPlayer || slotIn.yPos > 90 && slotIn.xPos <= 162;
        }
    }

    class CreativeSlot extends Slot
    {
        private final Slot slot;

        public CreativeSlot(Slot p_i46313_2_, int index)
        {
            super(p_i46313_2_.inventory, index, 0, 0);
            this.slot = p_i46313_2_;
        }

        public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack)
        {
            this.slot.onTake(thePlayer, stack);
            return stack;
        }

        public boolean isItemValid(ItemStack stack)
        {
            return this.slot.isItemValid(stack);
        }

        public ItemStack getStack()
        {
            return this.slot.getStack();
        }

        public boolean getHasStack()
        {
            return this.slot.getHasStack();
        }

        public void putStack(ItemStack stack)
        {
            this.slot.putStack(stack);
        }

        public void onSlotChanged()
        {
            this.slot.onSlotChanged();
        }

        public int getSlotStackLimit()
        {
            return this.slot.getSlotStackLimit();
        }

        public int getItemStackLimit(ItemStack stack)
        {
            return this.slot.getItemStackLimit(stack);
        }

        @Nullable
        public String getSlotTexture()
        {
            return this.slot.getSlotTexture();
        }

        public ItemStack decrStackSize(int amount)
        {
            return this.slot.decrStackSize(amount);
        }

        public boolean isHere(IInventory inv, int slotIn)
        {
            return this.slot.isHere(inv, slotIn);
        }

        public boolean isEnabled()
        {
            return this.slot.isEnabled();
        }

        public boolean canTakeStack(EntityPlayer playerIn)
        {
            return this.slot.canTakeStack(playerIn);
        }
    }

    static class LockedSlot extends Slot
    {
        public LockedSlot(IInventory p_i47453_1_, int p_i47453_2_, int p_i47453_3_, int p_i47453_4_)
        {
            super(p_i47453_1_, p_i47453_2_, p_i47453_3_, p_i47453_4_);
        }

        public boolean canTakeStack(EntityPlayer playerIn)
        {
            if (super.canTakeStack(playerIn) && this.getHasStack())
            {
                return this.getStack().getSubCompound("CustomCreativeLock") == null;
            }
            else
            {
                return !this.getHasStack();
            }
        }
    }
}
