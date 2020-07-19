package net.minecraft.client.gui.recipebook;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButtonToggle;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.stats.RecipeBook;

public class RecipeBookPage
{
    private List<GuiButtonRecipe> buttons = Lists.<GuiButtonRecipe>newArrayListWithCapacity(20);
    private GuiButtonRecipe hoveredButton;
    private GuiRecipeOverlay overlay = new GuiRecipeOverlay();
    private Minecraft minecraft;
    private List<IRecipeUpdateListener> listeners = Lists.<IRecipeUpdateListener>newArrayList();
    private List<RecipeList> recipeLists;
    private GuiButtonToggle forwardButton;
    private GuiButtonToggle backButton;
    private int totalPages;
    private int currentPage;
    private RecipeBook recipeBook;
    private IRecipe lastClickedRecipe;
    private RecipeList lastClickedRecipeList;

    public RecipeBookPage()
    {
        for (int i = 0; i < 20; ++i)
        {
            this.buttons.add(new GuiButtonRecipe());
        }
    }

    public void init(Minecraft p_194194_1_, int p_194194_2_, int p_194194_3_)
    {
        this.minecraft = p_194194_1_;
        this.recipeBook = p_194194_1_.player.getRecipeBook();

        for (int i = 0; i < this.buttons.size(); ++i)
        {
            ((GuiButtonRecipe)this.buttons.get(i)).setPosition(p_194194_2_ + 11 + 25 * (i % 5), p_194194_3_ + 31 + 25 * (i / 5));
        }

        this.forwardButton = new GuiButtonToggle(0, p_194194_2_ + 93, p_194194_3_ + 137, 12, 17, false);
        this.forwardButton.initTextureValues(1, 208, 13, 18, GuiRecipeBook.RECIPE_BOOK);
        this.backButton = new GuiButtonToggle(0, p_194194_2_ + 38, p_194194_3_ + 137, 12, 17, true);
        this.backButton.initTextureValues(1, 208, 13, 18, GuiRecipeBook.RECIPE_BOOK);
    }

    public void addListener(GuiRecipeBook p_193732_1_)
    {
        this.listeners.remove(p_193732_1_);
        this.listeners.add(p_193732_1_);
    }

    public void updateLists(List<RecipeList> p_194192_1_, boolean p_194192_2_)
    {
        this.recipeLists = p_194192_1_;
        this.totalPages = (int)Math.ceil((double)p_194192_1_.size() / 20.0D);

        if (this.totalPages <= this.currentPage || p_194192_2_)
        {
            this.currentPage = 0;
        }

        this.updateButtonsForPage();
    }

    private void updateButtonsForPage()
    {
        int i = 20 * this.currentPage;

        for (int j = 0; j < this.buttons.size(); ++j)
        {
            GuiButtonRecipe guibuttonrecipe = this.buttons.get(j);

            if (i + j < this.recipeLists.size())
            {
                RecipeList recipelist = this.recipeLists.get(i + j);
                guibuttonrecipe.init(recipelist, this, this.recipeBook);
                guibuttonrecipe.visible = true;
            }
            else
            {
                guibuttonrecipe.visible = false;
            }
        }

        this.updateArrowButtons();
    }

    private void updateArrowButtons()
    {
        this.forwardButton.visible = this.totalPages > 1 && this.currentPage < this.totalPages - 1;
        this.backButton.visible = this.totalPages > 1 && this.currentPage > 0;
    }

    public void render(int p_194191_1_, int p_194191_2_, int p_194191_3_, int p_194191_4_, float p_194191_5_)
    {
        if (this.totalPages > 1)
        {
            String s = this.currentPage + 1 + "/" + this.totalPages;
            int i = this.minecraft.fontRenderer.getStringWidth(s);
            this.minecraft.fontRenderer.drawString(s, p_194191_1_ - i / 2 + 73, p_194191_2_ + 141, -1);
        }

        RenderHelper.disableStandardItemLighting();
        this.hoveredButton = null;

        for (GuiButtonRecipe guibuttonrecipe : this.buttons)
        {
            guibuttonrecipe.drawButton(this.minecraft, p_194191_3_, p_194191_4_, p_194191_5_);

            if (guibuttonrecipe.visible && guibuttonrecipe.isMouseOver())
            {
                this.hoveredButton = guibuttonrecipe;
            }
        }

        this.backButton.drawButton(this.minecraft, p_194191_3_, p_194191_4_, p_194191_5_);
        this.forwardButton.drawButton(this.minecraft, p_194191_3_, p_194191_4_, p_194191_5_);
        this.overlay.render(p_194191_3_, p_194191_4_, p_194191_5_);
    }

    public void renderTooltip(int p_193721_1_, int p_193721_2_)
    {
        if (this.minecraft.currentScreen != null && this.hoveredButton != null && !this.overlay.isVisible())
        {
            this.minecraft.currentScreen.drawHoveringText(this.hoveredButton.getToolTipText(this.minecraft.currentScreen), p_193721_1_, p_193721_2_);
        }
    }

    @Nullable
    public IRecipe getLastClickedRecipe()
    {
        return this.lastClickedRecipe;
    }

    @Nullable
    public RecipeList getLastClickedRecipeList()
    {
        return this.lastClickedRecipeList;
    }

    public void setInvisible()
    {
        this.overlay.setVisible(false);
    }

    public boolean mouseClicked(int p_194196_1_, int p_194196_2_, int p_194196_3_, int p_194196_4_, int p_194196_5_, int p_194196_6_, int p_194196_7_)
    {
        this.lastClickedRecipe = null;
        this.lastClickedRecipeList = null;

        if (this.overlay.isVisible())
        {
            if (this.overlay.buttonClicked(p_194196_1_, p_194196_2_, p_194196_3_))
            {
                this.lastClickedRecipe = this.overlay.getLastRecipeClicked();
                this.lastClickedRecipeList = this.overlay.getRecipeList();
            }
            else
            {
                this.overlay.setVisible(false);
            }

            return true;
        }
        else if (this.forwardButton.mousePressed(this.minecraft, p_194196_1_, p_194196_2_) && p_194196_3_ == 0)
        {
            this.forwardButton.playPressSound(this.minecraft.getSoundHandler());
            ++this.currentPage;
            this.updateButtonsForPage();
            return true;
        }
        else if (this.backButton.mousePressed(this.minecraft, p_194196_1_, p_194196_2_) && p_194196_3_ == 0)
        {
            this.backButton.playPressSound(this.minecraft.getSoundHandler());
            --this.currentPage;
            this.updateButtonsForPage();
            return true;
        }
        else
        {
            for (GuiButtonRecipe guibuttonrecipe : this.buttons)
            {
                if (guibuttonrecipe.mousePressed(this.minecraft, p_194196_1_, p_194196_2_))
                {
                    guibuttonrecipe.playPressSound(this.minecraft.getSoundHandler());

                    if (p_194196_3_ == 0)
                    {
                        this.lastClickedRecipe = guibuttonrecipe.getRecipe();
                        this.lastClickedRecipeList = guibuttonrecipe.getList();
                    }
                    else if (!this.overlay.isVisible() && !guibuttonrecipe.isOnlyOption())
                    {
                        this.overlay.init(this.minecraft, guibuttonrecipe.getList(), guibuttonrecipe.x, guibuttonrecipe.y, p_194196_4_ + p_194196_6_ / 2, p_194196_5_ + 13 + p_194196_7_ / 2, (float)guibuttonrecipe.getButtonWidth(), this.recipeBook);
                    }

                    return true;
                }
            }

            return false;
        }
    }

    public void recipesShown(List<IRecipe> p_194195_1_)
    {
        for (IRecipeUpdateListener irecipeupdatelistener : this.listeners)
        {
            irecipeupdatelistener.recipesShown(p_194195_1_);
        }
    }
}
