package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.gson.JsonParseException;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenBook extends GuiScreen
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ResourceLocation BOOK_GUI_TEXTURES = new ResourceLocation("textures/gui/book.png");

    /** The player editing the book */
    private final EntityPlayer editingPlayer;
    private final ItemStack book;

    /** Whether the book is signed or can still be edited */
    private final boolean bookIsUnsigned;

    /**
     * Whether the book's title or contents has been modified since being opened
     */
    private boolean bookIsModified;

    /** Determines if the signing screen is open */
    private boolean bookGettingSigned;

    /** Update ticks since the gui was opened */
    private int updateCount;
    private final int bookImageWidth = 192;
    private final int bookImageHeight = 192;
    private int bookTotalPages = 1;
    private int currPage;
    private NBTTagList bookPages;
    private String bookTitle = "";
    private List<ITextComponent> cachedComponents;
    private int cachedPage = -1;
    private GuiScreenBook.NextPageButton buttonNextPage;
    private GuiScreenBook.NextPageButton buttonPreviousPage;
    private GuiButton buttonDone;

    /** The GuiButton to sign this book. */
    private GuiButton buttonSign;
    private GuiButton buttonFinalize;
    private GuiButton buttonCancel;

    public GuiScreenBook(EntityPlayer player, ItemStack book, boolean isUnsigned)
    {
        this.editingPlayer = player;
        this.book = book;
        this.bookIsUnsigned = isUnsigned;

        if (book.hasTagCompound())
        {
            NBTTagCompound nbttagcompound = book.getTagCompound();
            this.bookPages = nbttagcompound.getTagList("pages", 8).copy();
            this.bookTotalPages = this.bookPages.tagCount();

            if (this.bookTotalPages < 1)
            {
                this.bookTotalPages = 1;
            }
        }

        if (this.bookPages == null && isUnsigned)
        {
            this.bookPages = new NBTTagList();
            this.bookPages.appendTag(new NBTTagString(""));
            this.bookTotalPages = 1;
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        ++this.updateCount;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);

        if (this.bookIsUnsigned)
        {
            this.buttonSign = this.addButton(new GuiButton(3, this.width / 2 - 100, 196, 98, 20, I18n.format("book.signButton")));
            this.buttonDone = this.addButton(new GuiButton(0, this.width / 2 + 2, 196, 98, 20, I18n.format("gui.done")));
            this.buttonFinalize = this.addButton(new GuiButton(5, this.width / 2 - 100, 196, 98, 20, I18n.format("book.finalizeButton")));
            this.buttonCancel = this.addButton(new GuiButton(4, this.width / 2 + 2, 196, 98, 20, I18n.format("gui.cancel")));
        }
        else
        {
            this.buttonDone = this.addButton(new GuiButton(0, this.width / 2 - 100, 196, 200, 20, I18n.format("gui.done")));
        }

        int i = (this.width - 192) / 2;
        int j = 2;
        this.buttonNextPage = (GuiScreenBook.NextPageButton)this.addButton(new GuiScreenBook.NextPageButton(1, i + 120, 156, true));
        this.buttonPreviousPage = (GuiScreenBook.NextPageButton)this.addButton(new GuiScreenBook.NextPageButton(2, i + 38, 156, false));
        this.updateButtons();
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    private void updateButtons()
    {
        this.buttonNextPage.visible = !this.bookGettingSigned && (this.currPage < this.bookTotalPages - 1 || this.bookIsUnsigned);
        this.buttonPreviousPage.visible = !this.bookGettingSigned && this.currPage > 0;
        this.buttonDone.visible = !this.bookIsUnsigned || !this.bookGettingSigned;

        if (this.bookIsUnsigned)
        {
            this.buttonSign.visible = !this.bookGettingSigned;
            this.buttonCancel.visible = this.bookGettingSigned;
            this.buttonFinalize.visible = this.bookGettingSigned;
            this.buttonFinalize.enabled = !this.bookTitle.trim().isEmpty();
        }
    }

    private void sendBookToServer(boolean publish) throws IOException
    {
        if (this.bookIsUnsigned && this.bookIsModified)
        {
            if (this.bookPages != null)
            {
                while (this.bookPages.tagCount() > 1)
                {
                    String s = this.bookPages.getStringTagAt(this.bookPages.tagCount() - 1);

                    if (!s.isEmpty())
                    {
                        break;
                    }

                    this.bookPages.removeTag(this.bookPages.tagCount() - 1);
                }

                if (this.book.hasTagCompound())
                {
                    NBTTagCompound nbttagcompound = this.book.getTagCompound();
                    nbttagcompound.setTag("pages", this.bookPages);
                }
                else
                {
                    this.book.setTagInfo("pages", this.bookPages);
                }

                String s1 = "MC|BEdit";

                if (publish)
                {
                    s1 = "MC|BSign";
                    this.book.setTagInfo("author", new NBTTagString(this.editingPlayer.getName()));
                    this.book.setTagInfo("title", new NBTTagString(this.bookTitle.trim()));
                }

                PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
                packetbuffer.writeItemStack(this.book);
                this.mc.getConnection().sendPacket(new CPacketCustomPayload(s1, packetbuffer));
            }
        }
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
            if (button.id == 0)
            {
                this.mc.displayGuiScreen((GuiScreen)null);
                this.sendBookToServer(false);
            }
            else if (button.id == 3 && this.bookIsUnsigned)
            {
                this.bookGettingSigned = true;
            }
            else if (button.id == 1)
            {
                if (this.currPage < this.bookTotalPages - 1)
                {
                    ++this.currPage;
                }
                else if (this.bookIsUnsigned)
                {
                    this.addNewPage();

                    if (this.currPage < this.bookTotalPages - 1)
                    {
                        ++this.currPage;
                    }
                }
            }
            else if (button.id == 2)
            {
                if (this.currPage > 0)
                {
                    --this.currPage;
                }
            }
            else if (button.id == 5 && this.bookGettingSigned)
            {
                this.sendBookToServer(true);
                this.mc.displayGuiScreen((GuiScreen)null);
            }
            else if (button.id == 4 && this.bookGettingSigned)
            {
                this.bookGettingSigned = false;
            }

            this.updateButtons();
        }
    }

    private void addNewPage()
    {
        if (this.bookPages != null && this.bookPages.tagCount() < 50)
        {
            this.bookPages.appendTag(new NBTTagString(""));
            ++this.bookTotalPages;
            this.bookIsModified = true;
        }
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);

        if (this.bookIsUnsigned)
        {
            if (this.bookGettingSigned)
            {
                this.keyTypedInTitle(typedChar, keyCode);
            }
            else
            {
                this.keyTypedInBook(typedChar, keyCode);
            }
        }
    }

    /**
     * Processes keystrokes when editing the text of a book
     */
    private void keyTypedInBook(char typedChar, int keyCode)
    {
        if (GuiScreen.isKeyComboCtrlV(keyCode))
        {
            this.pageInsertIntoCurrent(GuiScreen.getClipboardString());
        }
        else
        {
            switch (keyCode)
            {
                case 14:
                    String s = this.pageGetCurrent();

                    if (!s.isEmpty())
                    {
                        this.pageSetCurrent(s.substring(0, s.length() - 1));
                    }

                    return;

                case 28:
                case 156:
                    this.pageInsertIntoCurrent("\n");
                    return;

                default:
                    if (ChatAllowedCharacters.isAllowedCharacter(typedChar))
                    {
                        this.pageInsertIntoCurrent(Character.toString(typedChar));
                    }
            }
        }
    }

    /**
     * Processes keystrokes when editing the title of a book
     */
    private void keyTypedInTitle(char typedChar, int keyCode) throws IOException
    {
        switch (keyCode)
        {
            case 14:
                if (!this.bookTitle.isEmpty())
                {
                    this.bookTitle = this.bookTitle.substring(0, this.bookTitle.length() - 1);
                    this.updateButtons();
                }

                return;

            case 28:
            case 156:
                if (!this.bookTitle.isEmpty())
                {
                    this.sendBookToServer(true);
                    this.mc.displayGuiScreen((GuiScreen)null);
                }

                return;

            default:
                if (this.bookTitle.length() < 16 && ChatAllowedCharacters.isAllowedCharacter(typedChar))
                {
                    this.bookTitle = this.bookTitle + Character.toString(typedChar);
                    this.updateButtons();
                    this.bookIsModified = true;
                }
        }
    }

    /**
     * Returns the entire text of the current page as determined by currPage
     */
    private String pageGetCurrent()
    {
        return this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount() ? this.bookPages.getStringTagAt(this.currPage) : "";
    }

    /**
     * Sets the text of the current page as determined by currPage
     */
    private void pageSetCurrent(String p_146457_1_)
    {
        if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount())
        {
            this.bookPages.set(this.currPage, new NBTTagString(p_146457_1_));
            this.bookIsModified = true;
        }
    }

    /**
     * Processes any text getting inserted into the current page, enforcing the page size limit
     */
    private void pageInsertIntoCurrent(String p_146459_1_)
    {
        String s = this.pageGetCurrent();
        String s1 = s + p_146459_1_;
        int i = this.fontRenderer.getWordWrappedHeight(s1 + "" + TextFormatting.BLACK + "_", 118);

        if (i <= 128 && s1.length() < 256)
        {
            this.pageSetCurrent(s1);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(BOOK_GUI_TEXTURES);
        int i = (this.width - 192) / 2;
        int j = 2;
        this.drawTexturedModalRect(i, 2, 0, 0, 192, 192);

        if (this.bookGettingSigned)
        {
            String s = this.bookTitle;

            if (this.bookIsUnsigned)
            {
                if (this.updateCount / 6 % 2 == 0)
                {
                    s = s + "" + TextFormatting.BLACK + "_";
                }
                else
                {
                    s = s + "" + TextFormatting.GRAY + "_";
                }
            }

            String s1 = I18n.format("book.editTitle");
            int k = this.fontRenderer.getStringWidth(s1);
            this.fontRenderer.drawString(s1, i + 36 + (116 - k) / 2, 34, 0);
            int l = this.fontRenderer.getStringWidth(s);
            this.fontRenderer.drawString(s, i + 36 + (116 - l) / 2, 50, 0);
            String s2 = I18n.format("book.byAuthor", this.editingPlayer.getName());
            int i1 = this.fontRenderer.getStringWidth(s2);
            this.fontRenderer.drawString(TextFormatting.DARK_GRAY + s2, i + 36 + (116 - i1) / 2, 60, 0);
            String s3 = I18n.format("book.finalizeWarning");
            this.fontRenderer.drawSplitString(s3, i + 36, 82, 116, 0);
        }
        else
        {
            String s4 = I18n.format("book.pageIndicator", this.currPage + 1, this.bookTotalPages);
            String s5 = "";

            if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount())
            {
                s5 = this.bookPages.getStringTagAt(this.currPage);
            }

            if (this.bookIsUnsigned)
            {
                if (this.fontRenderer.getBidiFlag())
                {
                    s5 = s5 + "_";
                }
                else if (this.updateCount / 6 % 2 == 0)
                {
                    s5 = s5 + "" + TextFormatting.BLACK + "_";
                }
                else
                {
                    s5 = s5 + "" + TextFormatting.GRAY + "_";
                }
            }
            else if (this.cachedPage != this.currPage)
            {
                if (ItemWrittenBook.validBookTagContents(this.book.getTagCompound()))
                {
                    try
                    {
                        ITextComponent itextcomponent = ITextComponent.Serializer.jsonToComponent(s5);
                        this.cachedComponents = itextcomponent != null ? GuiUtilRenderComponents.splitText(itextcomponent, 116, this.fontRenderer, true, true) : null;
                    }
                    catch (JsonParseException var13)
                    {
                        this.cachedComponents = null;
                    }
                }
                else
                {
                    TextComponentString textcomponentstring = new TextComponentString(TextFormatting.DARK_RED + "* Invalid book tag *");
                    this.cachedComponents = Lists.newArrayList(textcomponentstring);
                }

                this.cachedPage = this.currPage;
            }

            int j1 = this.fontRenderer.getStringWidth(s4);
            this.fontRenderer.drawString(s4, i - j1 + 192 - 44, 18, 0);

            if (this.cachedComponents == null)
            {
                this.fontRenderer.drawSplitString(s5, i + 36, 34, 116, 0);
            }
            else
            {
                int k1 = Math.min(128 / this.fontRenderer.FONT_HEIGHT, this.cachedComponents.size());

                for (int l1 = 0; l1 < k1; ++l1)
                {
                    ITextComponent itextcomponent2 = this.cachedComponents.get(l1);
                    this.fontRenderer.drawString(itextcomponent2.getUnformattedText(), i + 36, 34 + l1 * this.fontRenderer.FONT_HEIGHT, 0);
                }

                ITextComponent itextcomponent1 = this.getClickedComponentAt(mouseX, mouseY);

                if (itextcomponent1 != null)
                {
                    this.handleComponentHover(itextcomponent1, mouseX, mouseY);
                }
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        if (mouseButton == 0)
        {
            ITextComponent itextcomponent = this.getClickedComponentAt(mouseX, mouseY);

            if (itextcomponent != null && this.handleComponentClick(itextcomponent))
            {
                return;
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Executes the click event specified by the given chat component
     */
    public boolean handleComponentClick(ITextComponent component)
    {
        ClickEvent clickevent = component.getStyle().getClickEvent();

        if (clickevent == null)
        {
            return false;
        }
        else if (clickevent.getAction() == ClickEvent.Action.CHANGE_PAGE)
        {
            String s = clickevent.getValue();

            try
            {
                int i = Integer.parseInt(s) - 1;

                if (i >= 0 && i < this.bookTotalPages && i != this.currPage)
                {
                    this.currPage = i;
                    this.updateButtons();
                    return true;
                }
            }
            catch (Throwable var5)
            {
                ;
            }

            return false;
        }
        else
        {
            boolean flag = super.handleComponentClick(component);

            if (flag && clickevent.getAction() == ClickEvent.Action.RUN_COMMAND)
            {
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            return flag;
        }
    }

    @Nullable
    public ITextComponent getClickedComponentAt(int p_175385_1_, int p_175385_2_)
    {
        if (this.cachedComponents == null)
        {
            return null;
        }
        else
        {
            int i = p_175385_1_ - (this.width - 192) / 2 - 36;
            int j = p_175385_2_ - 2 - 16 - 16;

            if (i >= 0 && j >= 0)
            {
                int k = Math.min(128 / this.fontRenderer.FONT_HEIGHT, this.cachedComponents.size());

                if (i <= 116 && j < this.mc.fontRenderer.FONT_HEIGHT * k + k)
                {
                    int l = j / this.mc.fontRenderer.FONT_HEIGHT;

                    if (l >= 0 && l < this.cachedComponents.size())
                    {
                        ITextComponent itextcomponent = this.cachedComponents.get(l);
                        int i1 = 0;

                        for (ITextComponent itextcomponent1 : itextcomponent)
                        {
                            if (itextcomponent1 instanceof TextComponentString)
                            {
                                i1 += this.mc.fontRenderer.getStringWidth(((TextComponentString)itextcomponent1).getText());

                                if (i1 > i)
                                {
                                    return itextcomponent1;
                                }
                            }
                        }
                    }

                    return null;
                }
                else
                {
                    return null;
                }
            }
            else
            {
                return null;
            }
        }
    }

    static class NextPageButton extends GuiButton
    {
        private final boolean isForward;

        public NextPageButton(int buttonId, int x, int y, boolean isForwardIn)
        {
            super(buttonId, x, y, 23, 13, "");
            this.isForward = isForwardIn;
        }

        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            if (this.visible)
            {
                boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(GuiScreenBook.BOOK_GUI_TEXTURES);
                int i = 0;
                int j = 192;

                if (flag)
                {
                    i += 23;
                }

                if (!this.isForward)
                {
                    j += 13;
                }

                this.drawTexturedModalRect(this.x, this.y, i, j, 23, 13);
            }
        }
    }
}
