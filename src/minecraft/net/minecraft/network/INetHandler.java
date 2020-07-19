package net.minecraft.network;

import net.minecraft.util.text.ITextComponent;

public interface INetHandler
{
    /**
     * Invoked when disconnecting, the parameter is a ChatComponent describing the reason for termination
     */
    void onDisconnect(ITextComponent reason);
}
