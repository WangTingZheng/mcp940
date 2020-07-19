package net.minecraft.client.gui;

import io.netty.buffer.Unpooled;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiMerchant extends GuiContainer
{
    private static final Logger LOGGER = LogManager.getLogger();

    /** The GUI texture for the villager merchant GUI. */
    private static final ResourceLocation MERCHANT_GUI_TEXTURE = new ResourceLocation("textures/gui/container/villager.png");

    /** The current IMerchant instance in use for this specific merchant. */
    private final IMerchant merchant;

    /** The button which proceeds to the next available merchant recipe. */
    private GuiMerchant.MerchantButton nextButton;

    /** Returns to the previous Merchant recipe if one is applicable. */
    private GuiMerchant.MerchantButton previousButton;

    /**
     * The integer value corresponding to the currently selected merchant recipe.
     */
    private int selectedMerchantRecipe;

    /** The chat component utilized by this GuiMerchant instance. */
    private final ITextComponent chatComponent;

    public GuiMerchant(InventoryPlayer p_i45500_1_, IMerchant p_i45500_2_, World worldIn)
    {
        super(new ContainerMerchant(p_i45500_1_, p_i45500_2_, worldIn));
        this.merchant = p_i45500_2_;
        this.chatComponent = p_i45500_2_.getDisplayName();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        super.initGui();
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.nextButton = (GuiMerchant.MerchantButton)this.addButton(new GuiMerchant.MerchantButton(1, i + 120 + 27, j + 24 - 1, true));
        this.previousButton = (GuiMerchant.MerchantButton)this.addButton(new GuiMerchant.MerchantButton(2, i + 36 - 19, j + 24 - 1, false));
        this.nextButton.enabled = false;
        this.previousButton.enabled = false;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = this.chatComponent.getUnformattedText();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        MerchantRecipeList merchantrecipelist = this.merchant.getRecipes(this.mc.player);

        if (merchantrecipelist != null)
        {
            this.nextButton.enabled = this.selectedMerchantRecipe < merchantrecipelist.size() - 1;
            this.previousButton.enabled = this.selectedMerchantRecipe > 0;
        }
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        boolean flag = false;

        if (button == this.nextButton)
        {
            ++this.selectedMerchantRecipe;
            MerchantRecipeList merchantrecipelist = this.merchant.getRecipes(this.mc.player);

            if (merchantrecipelist != null && this.selectedMerchantRecipe >= merchantrecipelist.size())
            {
                this.selectedMerchantRecipe = merchantrecipelist.size() - 1;
            }

            flag = true;
        }
        else if (button == this.previousButton)
        {
            --this.selectedMerchantRecipe;

            if (this.selectedMerchantRecipe < 0)
            {
                this.selectedMerchantRecipe = 0;
            }

            flag = true;
        }

        if (flag)
        {
            ((ContainerMerchant)this.inventorySlots).setCurrentRecipeIndex(this.selectedMerchantRecipe);
            PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
            packetbuffer.writeInt(this.selectedMerchantRecipe);
            this.mc.getConnection().sendPacket(new CPacketCustomPayload("MC|TrSel", packetbuffer));
        }
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        MerchantRecipeList merchantrecipelist = this.merchant.getRecipes(this.mc.player);

        if (merchantrecipelist != null && !merchantrecipelist.isEmpty())
        {
            int k = this.selectedMerchantRecipe;

            if (k < 0 || k >= merchantrecipelist.size())
            {
                return;
            }

            MerchantRecipe merchantrecipe = (MerchantRecipe)merchantrecipelist.get(k);

            if (merchantrecipe.isRecipeDisabled())
            {
                this.mc.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.disableLighting();
                this.drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 21, 212, 0, 28, 21);
                this.drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 51, 212, 0, 28, 21);
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        MerchantRecipeList merchantrecipelist = this.merchant.getRecipes(this.mc.player);

        if (merchantrecipelist != null && !merchantrecipelist.isEmpty())
        {
            int i = (this.width - this.xSize) / 2;
            int j = (this.height - this.ySize) / 2;
            int k = this.selectedMerchantRecipe;
            MerchantRecipe merchantrecipe = (MerchantRecipe)merchantrecipelist.get(k);
            ItemStack itemstack = merchantrecipe.getItemToBuy();
            ItemStack itemstack1 = merchantrecipe.getSecondItemToBuy();
            ItemStack itemstack2 = merchantrecipe.getItemToSell();
            GlStateManager.pushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.disableLighting();
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableColorMaterial();
            GlStateManager.enableLighting();
            this.itemRender.zLevel = 100.0F;
            this.itemRender.renderItemAndEffectIntoGUI(itemstack, i + 36, j + 24);
            this.itemRender.renderItemOverlays(this.fontRenderer, itemstack, i + 36, j + 24);

            if (!itemstack1.isEmpty())
            {
                this.itemRender.renderItemAndEffectIntoGUI(itemstack1, i + 62, j + 24);
                this.itemRender.renderItemOverlays(this.fontRenderer, itemstack1, i + 62, j + 24);
            }

            this.itemRender.renderItemAndEffectIntoGUI(itemstack2, i + 120, j + 24);
            this.itemRender.renderItemOverlays(this.fontRenderer, itemstack2, i + 120, j + 24);
            this.itemRender.zLevel = 0.0F;
            GlStateManager.disableLighting();

            if (this.isPointInRegion(36, 24, 16, 16, mouseX, mouseY) && !itemstack.isEmpty())
            {
                this.renderToolTip(itemstack, mouseX, mouseY);
            }
            else if (!itemstack1.isEmpty() && this.isPointInRegion(62, 24, 16, 16, mouseX, mouseY) && !itemstack1.isEmpty())
            {
                this.renderToolTip(itemstack1, mouseX, mouseY);
            }
            else if (!itemstack2.isEmpty() && this.isPointInRegion(120, 24, 16, 16, mouseX, mouseY) && !itemstack2.isEmpty())
            {
                this.renderToolTip(itemstack2, mouseX, mouseY);
            }
            else if (merchantrecipe.isRecipeDisabled() && (this.isPointInRegion(83, 21, 28, 21, mouseX, mouseY) || this.isPointInRegion(83, 51, 28, 21, mouseX, mouseY)))
            {
                this.drawHoveringText(I18n.format("merchant.deprecated"), mouseX, mouseY);
            }

            GlStateManager.popMatrix();
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            RenderHelper.enableStandardItemLighting();
        }

        this.renderHoveredToolTip(mouseX, mouseY);
    }

    public IMerchant getMerchant()
    {
        return this.merchant;
    }

    static class MerchantButton extends GuiButton
    {
        private final boolean forward;

        public MerchantButton(int buttonID, int x, int y, boolean p_i1095_4_)
        {
            super(buttonID, x, y, 12, 19, "");
            this.forward = p_i1095_4_;
        }

        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            if (this.visible)
            {
                mc.getTextureManager().bindTexture(GuiMerchant.MERCHANT_GUI_TEXTURE);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                int i = 0;
                int j = 176;

                if (!this.enabled)
                {
                    j += this.width * 2;
                }
                else if (flag)
                {
                    j += this.width;
                }

                if (!this.forward)
                {
                    i += this.height;
                }

                this.drawTexturedModalRect(this.x, this.y, j, i, this.width, this.height);
            }
        }
    }
}
