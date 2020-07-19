package net.minecraft.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.WorldInfo;

public class CommandToggleDownfall extends CommandBase
{
    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "toggledownfall";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    /**
     * Gets the usage string for the command.
     */
    public String getUsage(ICommandSender sender)
    {
        return "commands.downfall.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        this.toggleRainfall(server);
        notifyCommandListener(sender, this, "commands.downfall.success", new Object[0]);
    }

    protected void toggleRainfall(MinecraftServer server)
    {
        WorldInfo worldinfo = server.worlds[0].getWorldInfo();
        worldinfo.setRaining(!worldinfo.isRaining());
    }
}
