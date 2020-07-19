package net.minecraft.command;

import net.minecraft.server.MinecraftServer;

public class CommandSetPlayerTimeout extends CommandBase
{
    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "setidletimeout";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 3;
    }

    /**
     * Gets the usage string for the command.
     */
    public String getUsage(ICommandSender sender)
    {
        return "commands.setidletimeout.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length != 1)
        {
            throw new WrongUsageException("commands.setidletimeout.usage", new Object[0]);
        }
        else
        {
            int i = parseInt(args[0], 0);
            server.setPlayerIdleTimeout(i);
            notifyCommandListener(sender, this, "commands.setidletimeout.success", new Object[] {i});
        }
    }
}
