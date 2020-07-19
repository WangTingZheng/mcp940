package net.minecraft.client.gui.recipebook;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButtonToggle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.util.RecipeBookClient;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.stats.RecipeBook;

public class GuiButtonRecipeTab extends GuiButtonToggle
{
    private final CreativeTabs category;
    private float animationTime;

    public GuiButtonRecipeTab(int p_i47588_1_, CreativeTabs p_i47588_2_)
    {
        super(p_i47588_1_, 0, 0, 35, 27, false);
        this.category = p_i47588_2_;
        this.initTextureValues(153, 2, 35, 0, GuiRecipeBook.RECIPE_BOOK);
    }

    public void startAnimation(Minecraft p_193918_1_)
    {
        RecipeBook recipebook = p_193918_1_.player.getRecipeBook();
        label21:

        for (RecipeList recipelist : RecipeBookClient.RECIPES_BY_TAB.get(this.category))
        {
            Iterator iterator = recipelist.getRecipes(recipebook.isFilteringCraftable()).iterator();

            while (true)
            {
                if (!iterator.hasNext())
                {
                    continue label21;
                }

                IRecipe irecipe = (IRecipe)iterator.next();

                if (recipebook.isRecipeUnseen(irecipe))
                {
                    break;
                }
            }

            this.animationTime = 15.0F;
            return;
        }
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        if (this.visible)
        {
            if (this.animationTime > 0.0F)
            {
                float f = 1.0F + 0.1F * (float)Math.sin((double)(this.animationTime / 15.0F * (float)Math.PI));
                GlStateManager.pushMatrix();
                GlStateManager.translate((float)(this.x + 8), (float)(this.y + 12), 0.0F);
                GlStateManager.scale(1.0F, f, 1.0F);
                GlStateManager.translate((float)(-(this.x + 8)), (float)(-(this.y + 12)), 0.0F);
            }

            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            mc.getTextureManager().bindTexture(this.resourceLocation);
            GlStateManager.disableDepth();
            int k = this.xTexStart;
            int i = this.yTexStart;

            if (this.stateTriggered)
            {
                k += this.xDiffTex;
            }

            if (this.hovered)
            {
                i += this.yDiffTex;
            }

            int j = this.x;

            if (this.stateTriggered)
            {
                j -= 2;
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(j, this.y, k, i, this.width, this.height);
            GlStateManager.enableDepth();
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.disableLighting();
            this.renderIcon(mc.getRenderItem());
            GlStateManager.enableLighting();
            RenderHelper.disableStandardItemLighting();

            if (this.animationTime > 0.0F)
            {
                GlStateManager.popMatrix();
                this.animationTime -= partialTicks;
            }
        }
    }

    private void renderIcon(RenderItem p_193920_1_)
    {
        ItemStack itemstack = this.category.getIconItemStack();

        if (this.category == CreativeTabs.TOOLS)
        {
            p_193920_1_.renderItemAndEffectIntoGUI(itemstack, this.x + 3, this.y + 5);
            p_193920_1_.renderItemAndEffectIntoGUI(CreativeTabs.COMBAT.getIconItemStack(), this.x + 14, this.y + 5);
        }
        else if (this.category == CreativeTabs.MISC)
        {
            p_193920_1_.renderItemAndEffectIntoGUI(itemstack, this.x + 3, this.y + 5);
            p_193920_1_.renderItemAndEffectIntoGUI(CreativeTabs.FOOD.getIconItemStack(), this.x + 14, this.y + 5);
        }
        else
        {
            p_193920_1_.renderItemAndEffectIntoGUI(itemstack, this.x + 9, this.y + 5);
        }
    }

    public CreativeTabs getCategory()
    {
        return this.category;
    }

    public boolean updateVisibility()
    {
        List<RecipeList> list = (List)RecipeBookClient.RECIPES_BY_TAB.get(this.category);
        this.visible = false;

        for (RecipeList recipelist : list)
        {
            if (recipelist.isNotEmpty() && recipelist.containsValidRecipes())
            {
                this.visible = true;
                break;
            }
        }

        return this.visible;
    }
}
