package net.minecraft.client.gui.recipebook;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButtonToggle;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.util.RecipeBookClient;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.network.play.client.CPacketRecipeInfo;
import net.minecraft.network.play.client.CPacketRecipePlacement;
import net.minecraft.stats.RecipeBook;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiRecipeBook extends Gui implements IRecipeUpdateListener
{
    protected static final ResourceLocation RECIPE_BOOK = new ResourceLocation("textures/gui/recipe_book.png");
    private int xOffset;
    private int width;
    private int height;
    private static final Logger LOGGER = LogManager.getLogger();
    private final GhostRecipe ghostRecipe = new GhostRecipe();
    private final List<GuiButtonRecipeTab> recipeTabs = Lists.newArrayList(new GuiButtonRecipeTab(0, CreativeTabs.SEARCH), new GuiButtonRecipeTab(0, CreativeTabs.TOOLS), new GuiButtonRecipeTab(0, CreativeTabs.BUILDING_BLOCKS), new GuiButtonRecipeTab(0, CreativeTabs.MISC), new GuiButtonRecipeTab(0, CreativeTabs.REDSTONE));
    private GuiButtonRecipeTab currentTab;

    /**
     * This button toggles between showing all recipes and showing only craftable recipes
     */
    private GuiButtonToggle toggleRecipesBtn;
    private Container container;
    private InventoryCrafting craftingSlots;
    private Minecraft mc;
    private GuiTextField searchBar;
    private String lastSearch = "";
    private RecipeBook recipeBook;
    private final RecipeBookPage recipeBookPage = new RecipeBookPage();
    private RecipeItemHelper stackedContents = new RecipeItemHelper();
    private int timesInventoryChanged;

    public void init(int p_191856_1_, int p_191856_2_, Minecraft p_191856_3_, boolean p_191856_4_, Container p_191856_5_, InventoryCrafting p_191856_6_)
    {
        this.mc = p_191856_3_;
        this.width = p_191856_1_;
        this.height = p_191856_2_;
        this.container = p_191856_5_;
        this.craftingSlots = p_191856_6_;
        this.recipeBook = p_191856_3_.player.getRecipeBook();
        this.timesInventoryChanged = p_191856_3_.player.inventory.getTimesChanged();
        this.currentTab = this.recipeTabs.get(0);
        this.currentTab.setStateTriggered(true);

        if (this.isVisible())
        {
            this.initVisuals(p_191856_4_, p_191856_6_);
        }

        Keyboard.enableRepeatEvents(true);
    }

    public void initVisuals(boolean p_193014_1_, InventoryCrafting p_193014_2_)
    {
        this.xOffset = p_193014_1_ ? 0 : 86;
        int i = (this.width - 147) / 2 - this.xOffset;
        int j = (this.height - 166) / 2;
        this.stackedContents.clear();
        this.mc.player.inventory.fillStackedContents(this.stackedContents, false);
        p_193014_2_.fillStackedContents(this.stackedContents);
        this.searchBar = new GuiTextField(0, this.mc.fontRenderer, i + 25, j + 14, 80, this.mc.fontRenderer.FONT_HEIGHT + 5);
        this.searchBar.setMaxStringLength(50);
        this.searchBar.setEnableBackgroundDrawing(false);
        this.searchBar.setVisible(true);
        this.searchBar.setTextColor(16777215);
        this.recipeBookPage.init(this.mc, i, j);
        this.recipeBookPage.addListener(this);
        this.toggleRecipesBtn = new GuiButtonToggle(0, i + 110, j + 12, 26, 16, this.recipeBook.isFilteringCraftable());
        this.toggleRecipesBtn.initTextureValues(152, 41, 28, 18, RECIPE_BOOK);
        this.updateCollections(false);
        this.updateTabs();
    }

    public void removed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    public int updateScreenPosition(boolean p_193011_1_, int p_193011_2_, int p_193011_3_)
    {
        int i;

        if (this.isVisible() && !p_193011_1_)
        {
            i = 177 + (p_193011_2_ - p_193011_3_ - 200) / 2;
        }
        else
        {
            i = (p_193011_2_ - p_193011_3_) / 2;
        }

        return i;
    }

    public void toggleVisibility()
    {
        this.setVisible(!this.isVisible());
    }

    public boolean isVisible()
    {
        return this.recipeBook.isGuiOpen();
    }

    private void setVisible(boolean p_193006_1_)
    {
        this.recipeBook.setGuiOpen(p_193006_1_);

        if (!p_193006_1_)
        {
            this.recipeBookPage.setInvisible();
        }

        this.sendUpdateSettings();
    }

    public void slotClicked(@Nullable Slot slotIn)
    {
        if (slotIn != null && slotIn.slotNumber <= 9)
        {
            this.ghostRecipe.clear();

            if (this.isVisible())
            {
                this.updateStackedContents();
            }
        }
    }

    private void updateCollections(boolean p_193003_1_)
    {
        List<RecipeList> list = (List)RecipeBookClient.RECIPES_BY_TAB.get(this.currentTab.getCategory());
        list.forEach((p_193944_1_) ->
        {
            p_193944_1_.canCraft(this.stackedContents, this.craftingSlots.getWidth(), this.craftingSlots.getHeight(), this.recipeBook);
        });
        List<RecipeList> list1 = Lists.newArrayList(list);
        list1.removeIf((p_193952_0_) ->
        {
            return !p_193952_0_.isNotEmpty();
        });
        list1.removeIf((p_193953_0_) ->
        {
            return !p_193953_0_.containsValidRecipes();
        });
        String s = this.searchBar.getText();

        if (!s.isEmpty())
        {
            ObjectSet<RecipeList> objectset = new ObjectLinkedOpenHashSet<RecipeList>(this.mc.getSearchTree(SearchTreeManager.RECIPES).search(s.toLowerCase(Locale.ROOT)));
            list1.removeIf((p_193947_1_) ->
            {
                return !objectset.contains(p_193947_1_);
            });
        }

        if (this.recipeBook.isFilteringCraftable())
        {
            list1.removeIf((p_193958_0_) ->
            {
                return !p_193958_0_.containsCraftableRecipes();
            });
        }

        this.recipeBookPage.updateLists(list1, p_193003_1_);
    }

    private void updateTabs()
    {
        int i = (this.width - 147) / 2 - this.xOffset - 30;
        int j = (this.height - 166) / 2 + 3;
        int k = 27;
        int l = 0;

        for (GuiButtonRecipeTab guibuttonrecipetab : this.recipeTabs)
        {
            CreativeTabs creativetabs = guibuttonrecipetab.getCategory();

            if (creativetabs == CreativeTabs.SEARCH)
            {
                guibuttonrecipetab.visible = true;
                guibuttonrecipetab.setPosition(i, j + 27 * l++);
            }
            else if (guibuttonrecipetab.updateVisibility())
            {
                guibuttonrecipetab.setPosition(i, j + 27 * l++);
                guibuttonrecipetab.startAnimation(this.mc);
            }
        }
    }

    public void tick()
    {
        if (this.isVisible())
        {
            if (this.timesInventoryChanged != this.mc.player.inventory.getTimesChanged())
            {
                this.updateStackedContents();
                this.timesInventoryChanged = this.mc.player.inventory.getTimesChanged();
            }
        }
    }

    private void updateStackedContents()
    {
        this.stackedContents.clear();
        this.mc.player.inventory.fillStackedContents(this.stackedContents, false);
        this.craftingSlots.fillStackedContents(this.stackedContents);
        this.updateCollections(false);
    }

    public void render(int mouseX, int mouseY, float partialTicks)
    {
        if (this.isVisible())
        {
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.disableLighting();
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 0.0F, 100.0F);
            this.mc.getTextureManager().bindTexture(RECIPE_BOOK);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            int i = (this.width - 147) / 2 - this.xOffset;
            int j = (this.height - 166) / 2;
            this.drawTexturedModalRect(i, j, 1, 1, 147, 166);
            this.searchBar.drawTextBox();
            RenderHelper.disableStandardItemLighting();

            for (GuiButtonRecipeTab guibuttonrecipetab : this.recipeTabs)
            {
                guibuttonrecipetab.drawButton(this.mc, mouseX, mouseY, partialTicks);
            }

            this.toggleRecipesBtn.drawButton(this.mc, mouseX, mouseY, partialTicks);
            this.recipeBookPage.render(i, j, mouseX, mouseY, partialTicks);
            GlStateManager.popMatrix();
        }
    }

    public void renderTooltip(int p_191876_1_, int p_191876_2_, int p_191876_3_, int p_191876_4_)
    {
        if (this.isVisible())
        {
            this.recipeBookPage.renderTooltip(p_191876_3_, p_191876_4_);

            if (this.toggleRecipesBtn.isMouseOver())
            {
                String s1 = I18n.format(this.toggleRecipesBtn.isStateTriggered() ? "gui.recipebook.toggleRecipes.craftable" : "gui.recipebook.toggleRecipes.all");

                if (this.mc.currentScreen != null)
                {
                    this.mc.currentScreen.drawHoveringText(s1, p_191876_3_, p_191876_4_);
                }
            }

            this.renderGhostRecipeTooltip(p_191876_1_, p_191876_2_, p_191876_3_, p_191876_4_);
        }
    }

    private void renderGhostRecipeTooltip(int p_193015_1_, int p_193015_2_, int p_193015_3_, int p_193015_4_)
    {
        ItemStack itemstack = null;

        for (int i = 0; i < this.ghostRecipe.size(); ++i)
        {
            GhostRecipe.GhostIngredient ghostrecipe$ghostingredient = this.ghostRecipe.get(i);
            int j = ghostrecipe$ghostingredient.getX() + p_193015_1_;
            int k = ghostrecipe$ghostingredient.getY() + p_193015_2_;

            if (p_193015_3_ >= j && p_193015_4_ >= k && p_193015_3_ < j + 16 && p_193015_4_ < k + 16)
            {
                itemstack = ghostrecipe$ghostingredient.getItem();
            }
        }

        if (itemstack != null && this.mc.currentScreen != null)
        {
            this.mc.currentScreen.drawHoveringText(this.mc.currentScreen.getItemToolTip(itemstack), p_193015_3_, p_193015_4_);
        }
    }

    public void renderGhostRecipe(int p_191864_1_, int p_191864_2_, boolean p_191864_3_, float p_191864_4_)
    {
        this.ghostRecipe.render(this.mc, p_191864_1_, p_191864_2_, p_191864_3_, p_191864_4_);
    }

    public boolean mouseClicked(int p_191862_1_, int p_191862_2_, int p_191862_3_)
    {
        if (this.isVisible() && !this.mc.player.isSpectator())
        {
            if (this.recipeBookPage.mouseClicked(p_191862_1_, p_191862_2_, p_191862_3_, (this.width - 147) / 2 - this.xOffset, (this.height - 166) / 2, 147, 166))
            {
                IRecipe irecipe = this.recipeBookPage.getLastClickedRecipe();
                RecipeList recipelist = this.recipeBookPage.getLastClickedRecipeList();

                if (irecipe != null && recipelist != null)
                {
                    this.setContainerRecipe(irecipe, recipelist);

                    if (!this.isOffsetNextToMainGUI() && p_191862_3_ == 0)
                    {
                        this.setVisible(false);
                    }
                }

                return true;
            }
            else if (p_191862_3_ != 0)
            {
                return false;
            }
            else if (this.searchBar.mouseClicked(p_191862_1_, p_191862_2_, p_191862_3_))
            {
                return true;
            }
            else if (this.toggleRecipesBtn.mousePressed(this.mc, p_191862_1_, p_191862_2_))
            {
                boolean flag = !this.recipeBook.isFilteringCraftable();
                this.recipeBook.setFilteringCraftable(flag);
                this.toggleRecipesBtn.setStateTriggered(flag);
                this.toggleRecipesBtn.playPressSound(this.mc.getSoundHandler());
                this.sendUpdateSettings();
                this.updateCollections(false);
                return true;
            }
            else
            {
                for (GuiButtonRecipeTab guibuttonrecipetab : this.recipeTabs)
                {
                    if (guibuttonrecipetab.mousePressed(this.mc, p_191862_1_, p_191862_2_))
                    {
                        if (this.currentTab != guibuttonrecipetab)
                        {
                            guibuttonrecipetab.playPressSound(this.mc.getSoundHandler());
                            this.currentTab.setStateTriggered(false);
                            this.currentTab = guibuttonrecipetab;
                            this.currentTab.setStateTriggered(true);
                            this.updateCollections(true);
                        }

                        return true;
                    }
                }

                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public boolean hasClickedOutside(int p_193955_1_, int p_193955_2_, int p_193955_3_, int p_193955_4_, int p_193955_5_, int p_193955_6_)
    {
        if (!this.isVisible())
        {
            return true;
        }
        else
        {
            boolean flag = p_193955_1_ < p_193955_3_ || p_193955_2_ < p_193955_4_ || p_193955_1_ >= p_193955_3_ + p_193955_5_ || p_193955_2_ >= p_193955_4_ + p_193955_6_;
            boolean flag1 = p_193955_3_ - 147 < p_193955_1_ && p_193955_1_ < p_193955_3_ && p_193955_4_ < p_193955_2_ && p_193955_2_ < p_193955_4_ + p_193955_6_;
            return flag && !flag1 && !this.currentTab.mousePressed(this.mc, p_193955_1_, p_193955_2_);
        }
    }

    public boolean keyPressed(char typedChar, int keycode)
    {
        if (this.isVisible() && !this.mc.player.isSpectator())
        {
            if (keycode == 1 && !this.isOffsetNextToMainGUI())
            {
                this.setVisible(false);
                return true;
            }
            else
            {
                if (GameSettings.isKeyDown(this.mc.gameSettings.keyBindChat) && !this.searchBar.isFocused())
                {
                    this.searchBar.setFocused(true);
                }
                else if (this.searchBar.textboxKeyTyped(typedChar, keycode))
                {
                    String s1 = this.searchBar.getText().toLowerCase(Locale.ROOT);
                    this.pirateRecipe(s1);

                    if (!s1.equals(this.lastSearch))
                    {
                        this.updateCollections(false);
                        this.lastSearch = s1;
                    }

                    return true;
                }

                return false;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * "Check if we should activate the pirate speak easter egg"
     *  
     * @param text 'if equal to "excitedze", activate the easter egg'
     */
    private void pirateRecipe(String text)
    {
        if ("excitedze".equals(text))
        {
            LanguageManager languagemanager = this.mc.getLanguageManager();
            Language language = languagemanager.getLanguage("en_pt");

            if (languagemanager.getCurrentLanguage().compareTo(language) == 0)
            {
                return;
            }

            languagemanager.setCurrentLanguage(language);
            this.mc.gameSettings.language = language.getLanguageCode();
            this.mc.refreshResources();
            this.mc.fontRenderer.setUnicodeFlag(this.mc.getLanguageManager().isCurrentLocaleUnicode() || this.mc.gameSettings.forceUnicodeFont);
            this.mc.fontRenderer.setBidiFlag(languagemanager.isCurrentLanguageBidirectional());
            this.mc.gameSettings.saveOptions();
        }
    }

    private boolean isOffsetNextToMainGUI()
    {
        return this.xOffset == 86;
    }

    public void recipesUpdated()
    {
        this.updateTabs();

        if (this.isVisible())
        {
            this.updateCollections(false);
        }
    }

    public void recipesShown(List<IRecipe> recipes)
    {
        for (IRecipe irecipe : recipes)
        {
            this.mc.player.removeRecipeHighlight(irecipe);
        }
    }

    private void setContainerRecipe(IRecipe recipe, RecipeList recipes)
    {
        boolean flag = recipes.isCraftable(recipe);
        InventoryCraftResult inventorycraftresult = null;

        if (this.container instanceof ContainerWorkbench)
        {
            inventorycraftresult = ((ContainerWorkbench)this.container).craftResult;
        }
        else if (this.container instanceof ContainerPlayer)
        {
            inventorycraftresult = ((ContainerPlayer)this.container).craftResult;
        }

        if (inventorycraftresult != null)
        {
            if (!flag && this.ghostRecipe.getRecipe() == recipe)
            {
                return;
            }

            if (!this.testClearCraftingGrid() && !this.mc.player.isCreative())
            {
                return;
            }

            if (flag)
            {
                this.handleRecipeClicked(recipe, this.container.inventorySlots, this.container.windowId, inventorycraftresult);
            }
            else
            {
                List<CPacketRecipePlacement.ItemMove> list2 = this.clearCraftingGrid(inventorycraftresult);
                this.setupGhostRecipe(recipe, this.container.inventorySlots);

                if (!list2.isEmpty())
                {
                    this.mc.playerController.handleRecipePlacement(this.container.windowId, list2, Lists.newArrayList(), this.mc.player);

                    if (this.recipeBook.isFilteringCraftable())
                    {
                        this.mc.player.inventory.markDirty();
                    }
                }
            }

            if (!this.isOffsetNextToMainGUI())
            {
                this.toggleVisibility();
            }
        }
    }

    private void handleRecipeClicked(IRecipe p_193950_1_, List<Slot> p_193950_2_, int p_193950_3_, InventoryCraftResult p_193950_4_)
    {
        boolean flag = p_193950_1_.matches(this.craftingSlots, this.mc.world);
        int i = this.stackedContents.getBiggestCraftableStack(p_193950_1_, (IntList)null);

        if (flag)
        {
            boolean flag1 = true;

            for (int j = 0; j < this.craftingSlots.getSizeInventory(); ++j)
            {
                ItemStack itemstack = this.craftingSlots.getStackInSlot(j);

                if (!itemstack.isEmpty() && i > itemstack.getCount())
                {
                    flag1 = false;
                }
            }

            if (flag1)
            {
                return;
            }
        }

        int i1 = this.getStackSize(i, flag);
        IntList intlist = new IntArrayList();

        if (this.stackedContents.canCraft(p_193950_1_, intlist, i1))
        {
            int j1 = i1;
            IntListIterator lvt_10_1_ = intlist.iterator();

            while (lvt_10_1_.hasNext())
            {
                int k = ((Integer)lvt_10_1_.next()).intValue();
                int l = RecipeItemHelper.unpack(k).getMaxStackSize();

                if (l < j1)
                {
                    j1 = l;
                }
            }

            if (this.stackedContents.canCraft(p_193950_1_, intlist, j1))
            {
                List<CPacketRecipePlacement.ItemMove> list2 = this.clearCraftingGrid(p_193950_4_);
                List<CPacketRecipePlacement.ItemMove> list3 = Lists.<CPacketRecipePlacement.ItemMove>newArrayList();
                this.placeRecipe(p_193950_1_, p_193950_2_, j1, intlist, list3);
                this.mc.playerController.handleRecipePlacement(p_193950_3_, list2, list3, this.mc.player);
                this.mc.player.inventory.markDirty();
            }
        }
    }

    private List<CPacketRecipePlacement.ItemMove> clearCraftingGrid(InventoryCraftResult p_193954_1_)
    {
        this.ghostRecipe.clear();
        InventoryPlayer inventoryplayer = this.mc.player.inventory;
        List<CPacketRecipePlacement.ItemMove> list2 = Lists.<CPacketRecipePlacement.ItemMove>newArrayList();

        for (int i = 0; i < this.craftingSlots.getSizeInventory(); ++i)
        {
            ItemStack itemstack = this.craftingSlots.getStackInSlot(i);

            if (!itemstack.isEmpty())
            {
                while (itemstack.getCount() > 0)
                {
                    int j = inventoryplayer.storeItemStack(itemstack);

                    if (j == -1)
                    {
                        j = inventoryplayer.getFirstEmptyStack();
                    }

                    ItemStack itemstack1 = itemstack.copy();
                    itemstack1.setCount(1);

                    if (inventoryplayer.add(j, itemstack1))
                    {
                        itemstack1.grow(1);
                    }
                    else
                    {
                        LOGGER.error("Can't find any space for item in inventory");
                    }

                    this.craftingSlots.decrStackSize(i, 1);
                    int k = i + 1;
                    list2.add(new CPacketRecipePlacement.ItemMove(itemstack1.copy(), k, j));
                }
            }
        }

        this.craftingSlots.clear();
        p_193954_1_.clear();
        return list2;
    }

    private int getStackSize(int p_193943_1_, boolean p_193943_2_)
    {
        int i = 1;

        if (GuiScreen.isShiftKeyDown())
        {
            i = p_193943_1_;
        }
        else if (p_193943_2_)
        {
            i = 64;

            for (int j = 0; j < this.craftingSlots.getSizeInventory(); ++j)
            {
                ItemStack itemstack = this.craftingSlots.getStackInSlot(j);

                if (!itemstack.isEmpty() && i > itemstack.getCount())
                {
                    i = itemstack.getCount();
                }
            }

            if (i < 64)
            {
                ++i;
            }
        }

        return i;
    }

    private void placeRecipe(IRecipe p_193013_1_, List<Slot> p_193013_2_, int p_193013_3_, IntList p_193013_4_, List<CPacketRecipePlacement.ItemMove> p_193013_5_)
    {
        int i = this.craftingSlots.getWidth();
        int j = this.craftingSlots.getHeight();

        if (p_193013_1_ instanceof ShapedRecipes)
        {
            ShapedRecipes shapedrecipes = (ShapedRecipes)p_193013_1_;
            i = shapedrecipes.getWidth();
            j = shapedrecipes.getHeight();
        }

        int j1 = 1;
        Iterator<Integer> iterator = p_193013_4_.iterator();

        for (int k = 0; k < this.craftingSlots.getWidth() && j != k; ++k)
        {
            for (int l = 0; l < this.craftingSlots.getHeight(); ++l)
            {
                if (i == l || !iterator.hasNext())
                {
                    j1 += this.craftingSlots.getWidth() - l;
                    break;
                }

                Slot slot = p_193013_2_.get(j1);
                ItemStack itemstack = RecipeItemHelper.unpack(((Integer)iterator.next()).intValue());

                if (itemstack.isEmpty())
                {
                    ++j1;
                }
                else
                {
                    for (int i1 = 0; i1 < p_193013_3_; ++i1)
                    {
                        CPacketRecipePlacement.ItemMove cpacketrecipeplacement$itemmove = this.findSpot(j1, slot, itemstack);

                        if (cpacketrecipeplacement$itemmove != null)
                        {
                            p_193013_5_.add(cpacketrecipeplacement$itemmove);
                        }
                    }

                    ++j1;
                }
            }

            if (!iterator.hasNext())
            {
                break;
            }
        }
    }

    @Nullable
    private CPacketRecipePlacement.ItemMove findSpot(int p_193946_1_, Slot p_193946_2_, ItemStack p_193946_3_)
    {
        InventoryPlayer inventoryplayer = this.mc.player.inventory;
        int i = inventoryplayer.findSlotMatchingUnusedItem(p_193946_3_);

        if (i == -1)
        {
            return null;
        }
        else
        {
            ItemStack itemstack = inventoryplayer.getStackInSlot(i).copy();

            if (itemstack.isEmpty())
            {
                LOGGER.error("Matched: " + p_193946_3_.getUnlocalizedName() + " with empty item.");
                return null;
            }
            else
            {
                if (itemstack.getCount() > 1)
                {
                    inventoryplayer.decrStackSize(i, 1);
                }
                else
                {
                    inventoryplayer.removeStackFromSlot(i);
                }

                itemstack.setCount(1);

                if (p_193946_2_.getStack().isEmpty())
                {
                    p_193946_2_.putStack(itemstack);
                }
                else
                {
                    p_193946_2_.getStack().grow(1);
                }

                return new CPacketRecipePlacement.ItemMove(itemstack, p_193946_1_, i);
            }
        }
    }

    private boolean testClearCraftingGrid()
    {
        InventoryPlayer inventoryplayer = this.mc.player.inventory;

        for (int i = 0; i < this.craftingSlots.getSizeInventory(); ++i)
        {
            ItemStack itemstack = this.craftingSlots.getStackInSlot(i);

            if (!itemstack.isEmpty())
            {
                int j = inventoryplayer.storeItemStack(itemstack);

                if (j == -1)
                {
                    j = inventoryplayer.getFirstEmptyStack();
                }

                if (j == -1)
                {
                    return false;
                }
            }
        }

        return true;
    }

    private void setupGhostRecipe(IRecipe p_193951_1_, List<Slot> p_193951_2_)
    {
        ItemStack itemstack = p_193951_1_.getRecipeOutput();
        this.ghostRecipe.setRecipe(p_193951_1_);
        this.ghostRecipe.addIngredient(Ingredient.fromStacks(itemstack), (p_193951_2_.get(0)).xPos, (p_193951_2_.get(0)).yPos);
        int i = this.craftingSlots.getWidth();
        int j = this.craftingSlots.getHeight();
        int k = p_193951_1_ instanceof ShapedRecipes ? ((ShapedRecipes)p_193951_1_).getWidth() : i;
        int l = 1;
        Iterator<Ingredient> iterator = p_193951_1_.getIngredients().iterator();

        for (int i1 = 0; i1 < j; ++i1)
        {
            for (int j1 = 0; j1 < k; ++j1)
            {
                if (!iterator.hasNext())
                {
                    return;
                }

                Ingredient ingredient = iterator.next();

                if (ingredient != Ingredient.EMPTY)
                {
                    Slot slot = p_193951_2_.get(l);
                    this.ghostRecipe.addIngredient(ingredient, slot.xPos, slot.yPos);
                }

                ++l;
            }

            if (k < i)
            {
                l += i - k;
            }
        }
    }

    private void sendUpdateSettings()
    {
        if (this.mc.getConnection() != null)
        {
            this.mc.getConnection().sendPacket(new CPacketRecipeInfo(this.isVisible(), this.recipeBook.isFilteringCraftable()));
        }
    }
}
