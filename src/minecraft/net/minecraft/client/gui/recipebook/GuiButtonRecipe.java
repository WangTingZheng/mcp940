package net.minecraft.client.gui.recipebook;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.stats.RecipeBook;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiButtonRecipe extends GuiButton
{
    private static final ResourceLocation RECIPE_BOOK = new ResourceLocation("textures/gui/recipe_book.png");
    private RecipeBook book;
    private RecipeList list;
    private float time;
    private float animationTime;
    private int currentIndex;

    public GuiButtonRecipe()
    {
        super(0, 0, 0, 25, 25, "");
    }

    public void init(RecipeList p_193928_1_, RecipeBookPage p_193928_2_, RecipeBook p_193928_3_)
    {
        this.list = p_193928_1_;
        this.book = p_193928_3_;
        List<IRecipe> list = p_193928_1_.getRecipes(p_193928_3_.isFilteringCraftable());

        for (IRecipe irecipe : list)
        {
            if (p_193928_3_.isRecipeUnseen(irecipe))
            {
                p_193928_2_.recipesShown(list);
                this.animationTime = 15.0F;
                break;
            }
        }
    }

    public RecipeList getList()
    {
        return this.list;
    }

    public void setPosition(int p_191770_1_, int p_191770_2_)
    {
        this.x = p_191770_1_;
        this.y = p_191770_2_;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        if (this.visible)
        {
            if (!GuiScreen.isCtrlKeyDown())
            {
                this.time += partialTicks;
            }

            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            RenderHelper.enableGUIStandardItemLighting();
            mc.getTextureManager().bindTexture(RECIPE_BOOK);
            GlStateManager.disableLighting();
            int i = 29;

            if (!this.list.containsCraftableRecipes())
            {
                i += 25;
            }

            int j = 206;

            if (this.list.getRecipes(this.book.isFilteringCraftable()).size() > 1)
            {
                j += 25;
            }

            boolean flag = this.animationTime > 0.0F;

            if (flag)
            {
                float f = 1.0F + 0.1F * (float)Math.sin((double)(this.animationTime / 15.0F * (float)Math.PI));
                GlStateManager.pushMatrix();
                GlStateManager.translate((float)(this.x + 8), (float)(this.y + 12), 0.0F);
                GlStateManager.scale(f, f, 1.0F);
                GlStateManager.translate((float)(-(this.x + 8)), (float)(-(this.y + 12)), 0.0F);
                this.animationTime -= partialTicks;
            }

            this.drawTexturedModalRect(this.x, this.y, i, j, this.width, this.height);
            List<IRecipe> list = this.getOrderedRecipes();
            this.currentIndex = MathHelper.floor(this.time / 30.0F) % list.size();
            ItemStack itemstack = ((IRecipe)list.get(this.currentIndex)).getRecipeOutput();
            int k = 4;

            if (this.list.hasSingleResultItem() && this.getOrderedRecipes().size() > 1)
            {
                mc.getRenderItem().renderItemAndEffectIntoGUI(itemstack, this.x + k + 1, this.y + k + 1);
                --k;
            }

            mc.getRenderItem().renderItemAndEffectIntoGUI(itemstack, this.x + k, this.y + k);

            if (flag)
            {
                GlStateManager.popMatrix();
            }

            GlStateManager.enableLighting();
            RenderHelper.disableStandardItemLighting();
        }
    }

    private List<IRecipe> getOrderedRecipes()
    {
        List<IRecipe> list = this.list.getDisplayRecipes(true);

        if (!this.book.isFilteringCraftable())
        {
            list.addAll(this.list.getDisplayRecipes(false));
        }

        return list;
    }

    public boolean isOnlyOption()
    {
        return this.getOrderedRecipes().size() == 1;
    }

    public IRecipe getRecipe()
    {
        List<IRecipe> list = this.getOrderedRecipes();
        return list.get(this.currentIndex);
    }

    public List<String> getToolTipText(GuiScreen p_191772_1_)
    {
        ItemStack itemstack = ((IRecipe)this.getOrderedRecipes().get(this.currentIndex)).getRecipeOutput();
        List<String> list = p_191772_1_.getItemToolTip(itemstack);

        if (this.list.getRecipes(this.book.isFilteringCraftable()).size() > 1)
        {
            list.add(I18n.format("gui.recipebook.moreRecipes"));
        }

        return list;
    }

    public int getButtonWidth()
    {
        return 25;
    }
}
