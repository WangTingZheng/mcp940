package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiScreenHorseInventory extends GuiContainer
{
    private static final ResourceLocation HORSE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/horse.png");

    /** The player inventory bound to this GUI. */
    private final IInventory playerInventory;

    /** The horse inventory bound to this GUI. */
    private final IInventory horseInventory;

    /** The EntityHorse whose inventory is currently being accessed. */
    private final AbstractHorse horseEntity;

    /** The mouse x-position recorded during the last rendered frame. */
    private float mousePosx;

    /** The mouse y-position recorded during the last renderered frame. */
    private float mousePosY;

    public GuiScreenHorseInventory(IInventory playerInv, IInventory horseInv, AbstractHorse horse)
    {
        super(new ContainerHorseInventory(playerInv, horseInv, horse, Minecraft.getMinecraft().player));
        this.playerInventory = playerInv;
        this.horseInventory = horseInv;
        this.horseEntity = horse;
        this.allowUserInput = false;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString(this.horseInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(HORSE_GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        if (this.horseEntity instanceof AbstractChestHorse)
        {
            AbstractChestHorse abstractchesthorse = (AbstractChestHorse)this.horseEntity;

            if (abstractchesthorse.hasChest())
            {
                this.drawTexturedModalRect(i + 79, j + 17, 0, this.ySize, abstractchesthorse.getInventoryColumns() * 18, 54);
            }
        }

        if (this.horseEntity.canBeSaddled())
        {
            this.drawTexturedModalRect(i + 7, j + 35 - 18, 18, this.ySize + 54, 18, 18);
        }

        if (this.horseEntity.wearsArmor())
        {
            if (this.horseEntity instanceof EntityLlama)
            {
                this.drawTexturedModalRect(i + 7, j + 35, 36, this.ySize + 54, 18, 18);
            }
            else
            {
                this.drawTexturedModalRect(i + 7, j + 35, 0, this.ySize + 54, 18, 18);
            }
        }

        GuiInventory.drawEntityOnScreen(i + 51, j + 60, 17, (float)(i + 51) - this.mousePosx, (float)(j + 75 - 50) - this.mousePosY, this.horseEntity);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.mousePosx = (float)mouseX;
        this.mousePosY = (float)mouseY;
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
