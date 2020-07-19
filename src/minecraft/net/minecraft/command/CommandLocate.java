package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandLocate extends CommandBase
{
    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "locate";
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
        return "commands.locate.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length != 1)
        {
            throw new WrongUsageException("commands.locate.usage", new Object[0]);
        }
        else
        {
            String s = args[0];
            BlockPos blockpos = sender.getEntityWorld().findNearestStructure(s, sender.getPosition(), false);

            if (blockpos != null)
            {
                sender.sendMessage(new TextComponentTranslation("commands.locate.success", new Object[] {s, blockpos.getX(), blockpos.getZ()}));
            }
            else
            {
                throw new CommandException("commands.locate.failure", new Object[] {s});
            }
        }
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, new String[] {"Stronghold", "Monument", "Village", "Mansion", "EndCity", "Fortress", "Temple", "Mineshaft"}) : Collections.emptyList();
    }
}
