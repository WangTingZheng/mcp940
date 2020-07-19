package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameType;

public class CommandPublishLocalServer extends CommandBase
{
    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "publish";
    }

    /**
     * Gets the usage string for the command.
     */
    public String getUsage(ICommandSender sender)
    {
        return "commands.publish.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        String s = server.shareToLAN(GameType.SURVIVAL, false);

        if (s != null)
        {
            notifyCommandListener(sender, this, "commands.publish.started", new Object[] {s});
        }
        else
        {
            notifyCommandListener(sender, this, "commands.publish.failed", new Object[0]);
        }
    }
}
