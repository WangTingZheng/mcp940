package net.minecraft.command.server;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldServer;

public class CommandSaveAll extends CommandBase
{
    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "save-all";
    }

    /**
     * Gets the usage string for the command.
     */
    public String getUsage(ICommandSender sender)
    {
        return "commands.save.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        sender.sendMessage(new TextComponentTranslation("commands.save.start", new Object[0]));

        if (server.getPlayerList() != null)
        {
            server.getPlayerList().saveAllPlayerData();
        }

        try
        {
            for (int i = 0; i < server.worlds.length; ++i)
            {
                if (server.worlds[i] != null)
                {
                    WorldServer worldserver = server.worlds[i];
                    boolean flag = worldserver.disableLevelSaving;
                    worldserver.disableLevelSaving = false;
                    worldserver.saveAllChunks(true, (IProgressUpdate)null);
                    worldserver.disableLevelSaving = flag;
                }
            }

            if (args.length > 0 && "flush".equals(args[0]))
            {
                sender.sendMessage(new TextComponentTranslation("commands.save.flushStart", new Object[0]));

                for (int j = 0; j < server.worlds.length; ++j)
                {
                    if (server.worlds[j] != null)
                    {
                        WorldServer worldserver1 = server.worlds[j];
                        boolean flag1 = worldserver1.disableLevelSaving;
                        worldserver1.disableLevelSaving = false;
                        worldserver1.flushToDisk();
                        worldserver1.disableLevelSaving = flag1;
                    }
                }

                sender.sendMessage(new TextComponentTranslation("commands.save.flushEnd", new Object[0]));
            }
        }
        catch (MinecraftException minecraftexception)
        {
            notifyCommandListener(sender, this, "commands.save.failed", new Object[] {minecraftexception.getMessage()});
            return;
        }

        notifyCommandListener(sender, this, "commands.save.success", new Object[0]);
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, new String[] {"flush"}) : Collections.emptyList();
    }
}
