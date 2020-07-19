package net.minecraft.world;

import net.minecraft.util.text.ITextComponent;

public interface IWorldNameable
{
    /**
     * Get the name of this object. For players this returns their username
     */
    String getName();

    /**
     * Returns true if this thing is named
     */
    boolean hasCustomName();

    /**
     * Get the formatted ChatComponent that will be used for the sender's username in chat
     */
    ITextComponent getDisplayName();
}
