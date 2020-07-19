package net.minecraft.server.integrated;

import net.minecraft.command.ServerCommandManager;

public class IntegratedServerCommandManager extends ServerCommandManager
{
    public IntegratedServerCommandManager(IntegratedServer server)
    {
        super(server);
    }
}
