package net.minecraft.command.server;

import com.mojang.authlib.GameProfile;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandDeOp extends CommandBase
{
    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "deop";
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
        return "commands.deop.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length == 1 && args[0].length() > 0)
        {
            GameProfile gameprofile = server.getPlayerList().getOppedPlayers().getGameProfileFromName(args[0]);

            if (gameprofile == null)
            {
                throw new CommandException("commands.deop.failed", new Object[] {args[0]});
            }
            else
            {
                server.getPlayerList().removeOp(gameprofile);
                notifyCommandListener(sender, this, "commands.deop.success", new Object[] {args[0]});
            }
        }
        else
        {
            throw new WrongUsageException("commands.deop.usage", new Object[0]);
        }
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getPlayerList().getOppedPlayerNames()) : Collections.emptyList();
    }
}
