package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketRecipeBook implements Packet<INetHandlerPlayClient>
{
    private SPacketRecipeBook.State state;
    private List<IRecipe> recipes;
    private List<IRecipe> displayedRecipes;
    private boolean guiOpen;
    private boolean filteringCraftable;

    public SPacketRecipeBook()
    {
    }

    public SPacketRecipeBook(SPacketRecipeBook.State stateIn, List<IRecipe> recipesIn, List<IRecipe> displayedRecipesIn, boolean isGuiOpen, boolean p_i47597_5_)
    {
        this.state = stateIn;
        this.recipes = recipesIn;
        this.displayedRecipes = displayedRecipesIn;
        this.guiOpen = isGuiOpen;
        this.filteringCraftable = p_i47597_5_;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleRecipeBook(this);
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.state = (SPacketRecipeBook.State)buf.readEnumValue(SPacketRecipeBook.State.class);
        this.guiOpen = buf.readBoolean();
        this.filteringCraftable = buf.readBoolean();
        int i = buf.readVarInt();
        this.recipes = Lists.<IRecipe>newArrayList();

        for (int j = 0; j < i; ++j)
        {
            this.recipes.add(CraftingManager.getRecipeById(buf.readVarInt()));
        }

        if (this.state == SPacketRecipeBook.State.INIT)
        {
            i = buf.readVarInt();
            this.displayedRecipes = Lists.<IRecipe>newArrayList();

            for (int k = 0; k < i; ++k)
            {
                this.displayedRecipes.add(CraftingManager.getRecipeById(buf.readVarInt()));
            }
        }
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeEnumValue(this.state);
        buf.writeBoolean(this.guiOpen);
        buf.writeBoolean(this.filteringCraftable);
        buf.writeVarInt(this.recipes.size());

        for (IRecipe irecipe : this.recipes)
        {
            buf.writeVarInt(CraftingManager.getIDForRecipe(irecipe));
        }

        if (this.state == SPacketRecipeBook.State.INIT)
        {
            buf.writeVarInt(this.displayedRecipes.size());

            for (IRecipe irecipe1 : this.displayedRecipes)
            {
                buf.writeVarInt(CraftingManager.getIDForRecipe(irecipe1));
            }
        }
    }

    public List<IRecipe> getRecipes()
    {
        return this.recipes;
    }

    public List<IRecipe> getDisplayedRecipes()
    {
        return this.displayedRecipes;
    }

    public boolean isGuiOpen()
    {
        return this.guiOpen;
    }

    public boolean isFilteringCraftable()
    {
        return this.filteringCraftable;
    }

    public SPacketRecipeBook.State getState()
    {
        return this.state;
    }

    public static enum State
    {
        INIT,
        ADD,
        REMOVE;
    }
}
