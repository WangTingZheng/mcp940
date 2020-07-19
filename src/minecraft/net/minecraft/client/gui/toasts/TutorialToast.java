package net.minecraft.client.gui.toasts;

import javax.annotation.Nullable;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class TutorialToast implements IToast
{
    private final TutorialToast.Icons icon;
    private final String title;
    private final String subtitle;
    private IToast.Visibility visibility = IToast.Visibility.SHOW;
    private long lastDelta;
    private float displayedProgress;
    private float currentProgress;
    private final boolean hasProgressBar;

    public TutorialToast(TutorialToast.Icons iconIn, ITextComponent titleComponent, @Nullable ITextComponent subtitleComponent, boolean drawProgressBar)
    {
        this.icon = iconIn;
        this.title = titleComponent.getFormattedText();
        this.subtitle = subtitleComponent == null ? null : subtitleComponent.getFormattedText();
        this.hasProgressBar = drawProgressBar;
    }

    public IToast.Visibility draw(GuiToast toastGui, long delta)
    {
        toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        toastGui.drawTexturedModalRect(0, 0, 0, 96, 160, 32);
        this.icon.draw(toastGui, 6, 6);

        if (this.subtitle == null)
        {
            toastGui.getMinecraft().fontRenderer.drawString(this.title, 30, 12, -11534256);
        }
        else
        {
            toastGui.getMinecraft().fontRenderer.drawString(this.title, 30, 7, -11534256);
            toastGui.getMinecraft().fontRenderer.drawString(this.subtitle, 30, 18, -16777216);
        }

        if (this.hasProgressBar)
        {
            Gui.drawRect(3, 28, 157, 29, -1);
            float f = (float)MathHelper.clampedLerp((double)this.displayedProgress, (double)this.currentProgress, (double)((float)(delta - this.lastDelta) / 100.0F));
            int i;

            if (this.currentProgress >= this.displayedProgress)
            {
                i = -16755456;
            }
            else
            {
                i = -11206656;
            }

            Gui.drawRect(3, 28, (int)(3.0F + 154.0F * f), 29, i);
            this.displayedProgress = f;
            this.lastDelta = delta;
        }

        return this.visibility;
    }

    public void hide()
    {
        this.visibility = IToast.Visibility.HIDE;
    }

    public void setProgress(float progress)
    {
        this.currentProgress = progress;
    }

    public static enum Icons
    {
        MOVEMENT_KEYS(0, 0),
        MOUSE(1, 0),
        TREE(2, 0),
        RECIPE_BOOK(0, 1),
        WOODEN_PLANKS(1, 1);

        private final int column;
        private final int row;

        private Icons(int columnIn, int rowIn)
        {
            this.column = columnIn;
            this.row = rowIn;
        }

        public void draw(Gui guiIn, int x, int y)
        {
            GlStateManager.enableBlend();
            guiIn.drawTexturedModalRect(x, y, 176 + this.column * 20, this.row * 20, 20, 20);
            GlStateManager.enableBlend();
        }
    }
}
