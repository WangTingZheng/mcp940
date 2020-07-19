package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class CommandSaveOff extends CommandBase
{
    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "save-off";
    }

    /**
     * Gets the usage string for the command.
     */
    public String getUsage(ICommandSender sender)
    {
        return "commands.save-off.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        boolean flag = false;

        for (int i = 0; i < server.worlds.length; ++i)
        {
            if (server.worlds[i] != null)
            {
                WorldServer worldserver = server.worlds[i];

                if (!worldserver.disableLevelSaving)
                {
                    worldserver.disableLevelSaving = true;
                    flag = true;
                }
            }
        }

        if (flag)
        {
            notifyCommandListener(sender, this, "commands.save.disabled", new Object[0]);
        }
        else
        {
            throw new CommandException("commands.save-off.alreadyOff", new Object[0]);
        }
    }
}
