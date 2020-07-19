package net.minecraft.client.gui.toasts;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public class RecipeToast implements IToast
{
    private final List<ItemStack> recipesOutputs = Lists.<ItemStack>newArrayList();
    private long firstDrawTime;
    private boolean hasNewOutputs;

    public RecipeToast(ItemStack p_i47489_1_)
    {
        this.recipesOutputs.add(p_i47489_1_);
    }

    public IToast.Visibility draw(GuiToast toastGui, long delta)
    {
        if (this.hasNewOutputs)
        {
            this.firstDrawTime = delta;
            this.hasNewOutputs = false;
        }

        if (this.recipesOutputs.isEmpty())
        {
            return IToast.Visibility.HIDE;
        }
        else
        {
            toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            toastGui.drawTexturedModalRect(0, 0, 0, 32, 160, 32);
            toastGui.getMinecraft().fontRenderer.drawString(I18n.format("recipe.toast.title"), 30, 7, -11534256);
            toastGui.getMinecraft().fontRenderer.drawString(I18n.format("recipe.toast.description"), 30, 18, -16777216);
            RenderHelper.enableGUIStandardItemLighting();
            toastGui.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI((EntityLivingBase)null, this.recipesOutputs.get((int)(delta / (5000L / (long)this.recipesOutputs.size()) % (long)this.recipesOutputs.size())), 8, 8);
            return delta - this.firstDrawTime >= 5000L ? IToast.Visibility.HIDE : IToast.Visibility.SHOW;
        }
    }

    public void addRecipeOutput(ItemStack output)
    {
        if (this.recipesOutputs.add(output))
        {
            this.hasNewOutputs = true;
        }
    }

    public static void addOrUpdate(GuiToast p_193665_0_, IRecipe p_193665_1_)
    {
        RecipeToast recipetoast = (RecipeToast)p_193665_0_.getToast(RecipeToast.class, NO_TOKEN);

        if (recipetoast == null)
        {
            p_193665_0_.add(new RecipeToast(p_193665_1_.getRecipeOutput()));
        }
        else
        {
            recipetoast.addRecipeOutput(p_193665_1_.getRecipeOutput());
        }
    }
}
