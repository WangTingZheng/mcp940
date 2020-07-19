package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;

public class CommandWeather extends CommandBase
{
    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "weather";
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
        return "commands.weather.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length >= 1 && args.length <= 2)
        {
            int i = (300 + (new Random()).nextInt(600)) * 20;

            if (args.length >= 2)
            {
                i = parseInt(args[1], 1, 1000000) * 20;
            }

            World world = server.worlds[0];
            WorldInfo worldinfo = world.getWorldInfo();

            if ("clear".equalsIgnoreCase(args[0]))
            {
                worldinfo.setCleanWeatherTime(i);
                worldinfo.setRainTime(0);
                worldinfo.setThunderTime(0);
                worldinfo.setRaining(false);
                worldinfo.setThundering(false);
                notifyCommandListener(sender, this, "commands.weather.clear", new Object[0]);
            }
            else if ("rain".equalsIgnoreCase(args[0]))
            {
                worldinfo.setCleanWeatherTime(0);
                worldinfo.setRainTime(i);
                worldinfo.setThunderTime(i);
                worldinfo.setRaining(true);
                worldinfo.setThundering(false);
                notifyCommandListener(sender, this, "commands.weather.rain", new Object[0]);
            }
            else
            {
                if (!"thunder".equalsIgnoreCase(args[0]))
                {
                    throw new WrongUsageException("commands.weather.usage", new Object[0]);
                }

                worldinfo.setCleanWeatherTime(0);
                worldinfo.setRainTime(i);
                worldinfo.setThunderTime(i);
                worldinfo.setRaining(true);
                worldinfo.setThundering(true);
                notifyCommandListener(sender, this, "commands.weather.thunder", new Object[0]);
            }
        }
        else
        {
            throw new WrongUsageException("commands.weather.usage", new Object[0]);
        }
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, new String[] {"clear", "rain", "thunder"}) : Collections.emptyList();
    }
}
