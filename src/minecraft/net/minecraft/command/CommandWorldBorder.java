package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.border.WorldBorder;

public class CommandWorldBorder extends CommandBase
{
    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "worldborder";
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
        return "commands.worldborder.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length < 1)
        {
            throw new WrongUsageException("commands.worldborder.usage", new Object[0]);
        }
        else
        {
            WorldBorder worldborder = this.getWorldBorder(server);

            if ("set".equals(args[0]))
            {
                if (args.length != 2 && args.length != 3)
                {
                    throw new WrongUsageException("commands.worldborder.set.usage", new Object[0]);
                }

                double d0 = worldborder.getTargetSize();
                double d2 = parseDouble(args[1], 1.0D, 6.0E7D);
                long i = args.length > 2 ? parseLong(args[2], 0L, 9223372036854775L) * 1000L : 0L;

                if (i > 0L)
                {
                    worldborder.setTransition(d0, d2, i);

                    if (d0 > d2)
                    {
                        notifyCommandListener(sender, this, "commands.worldborder.setSlowly.shrink.success", new Object[] {String.format("%.1f", d2), String.format("%.1f", d0), Long.toString(i / 1000L)});
                    }
                    else
                    {
                        notifyCommandListener(sender, this, "commands.worldborder.setSlowly.grow.success", new Object[] {String.format("%.1f", d2), String.format("%.1f", d0), Long.toString(i / 1000L)});
                    }
                }
                else
                {
                    worldborder.setTransition(d2);
                    notifyCommandListener(sender, this, "commands.worldborder.set.success", new Object[] {String.format("%.1f", d2), String.format("%.1f", d0)});
                }
            }
            else if ("add".equals(args[0]))
            {
                if (args.length != 2 && args.length != 3)
                {
                    throw new WrongUsageException("commands.worldborder.add.usage", new Object[0]);
                }

                double d4 = worldborder.getDiameter();
                double d8 = d4 + parseDouble(args[1], -d4, 6.0E7D - d4);
                long j1 = worldborder.getTimeUntilTarget() + (args.length > 2 ? parseLong(args[2], 0L, 9223372036854775L) * 1000L : 0L);

                if (j1 > 0L)
                {
                    worldborder.setTransition(d4, d8, j1);

                    if (d4 > d8)
                    {
                        notifyCommandListener(sender, this, "commands.worldborder.setSlowly.shrink.success", new Object[] {String.format("%.1f", d8), String.format("%.1f", d4), Long.toString(j1 / 1000L)});
                    }
                    else
                    {
                        notifyCommandListener(sender, this, "commands.worldborder.setSlowly.grow.success", new Object[] {String.format("%.1f", d8), String.format("%.1f", d4), Long.toString(j1 / 1000L)});
                    }
                }
                else
                {
                    worldborder.setTransition(d8);
                    notifyCommandListener(sender, this, "commands.worldborder.set.success", new Object[] {String.format("%.1f", d8), String.format("%.1f", d4)});
                }
            }
            else if ("center".equals(args[0]))
            {
                if (args.length != 3)
                {
                    throw new WrongUsageException("commands.worldborder.center.usage", new Object[0]);
                }

                BlockPos blockpos = sender.getPosition();
                double d1 = parseDouble((double)blockpos.getX() + 0.5D, args[1], true);
                double d3 = parseDouble((double)blockpos.getZ() + 0.5D, args[2], true);
                worldborder.setCenter(d1, d3);
                notifyCommandListener(sender, this, "commands.worldborder.center.success", new Object[] {d1, d3});
            }
            else if ("damage".equals(args[0]))
            {
                if (args.length < 2)
                {
                    throw new WrongUsageException("commands.worldborder.damage.usage", new Object[0]);
                }

                if ("buffer".equals(args[1]))
                {
                    if (args.length != 3)
                    {
                        throw new WrongUsageException("commands.worldborder.damage.buffer.usage", new Object[0]);
                    }

                    double d5 = parseDouble(args[2], 0.0D);
                    double d9 = worldborder.getDamageBuffer();
                    worldborder.setDamageBuffer(d5);
                    notifyCommandListener(sender, this, "commands.worldborder.damage.buffer.success", new Object[] {String.format("%.1f", d5), String.format("%.1f", d9)});
                }
                else if ("amount".equals(args[1]))
                {
                    if (args.length != 3)
                    {
                        throw new WrongUsageException("commands.worldborder.damage.amount.usage", new Object[0]);
                    }

                    double d6 = parseDouble(args[2], 0.0D);
                    double d10 = worldborder.getDamageAmount();
                    worldborder.setDamageAmount(d6);
                    notifyCommandListener(sender, this, "commands.worldborder.damage.amount.success", new Object[] {String.format("%.2f", d6), String.format("%.2f", d10)});
                }
            }
            else if ("warning".equals(args[0]))
            {
                if (args.length < 2)
                {
                    throw new WrongUsageException("commands.worldborder.warning.usage", new Object[0]);
                }

                if ("time".equals(args[1]))
                {
                    if (args.length != 3)
                    {
                        throw new WrongUsageException("commands.worldborder.warning.time.usage", new Object[0]);
                    }

                    int j = parseInt(args[2], 0);
                    int l = worldborder.getWarningTime();
                    worldborder.setWarningTime(j);
                    notifyCommandListener(sender, this, "commands.worldborder.warning.time.success", new Object[] {j, l});
                }
                else if ("distance".equals(args[1]))
                {
                    if (args.length != 3)
                    {
                        throw new WrongUsageException("commands.worldborder.warning.distance.usage", new Object[0]);
                    }

                    int k = parseInt(args[2], 0);
                    int i1 = worldborder.getWarningDistance();
                    worldborder.setWarningDistance(k);
                    notifyCommandListener(sender, this, "commands.worldborder.warning.distance.success", new Object[] {k, i1});
                }
            }
            else
            {
                if (!"get".equals(args[0]))
                {
                    throw new WrongUsageException("commands.worldborder.usage", new Object[0]);
                }

                double d7 = worldborder.getDiameter();
                sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, MathHelper.floor(d7 + 0.5D));
                sender.sendMessage(new TextComponentTranslation("commands.worldborder.get.success", new Object[] {String.format("%.0f", d7)}));
            }
        }
    }

    protected WorldBorder getWorldBorder(MinecraftServer server)
    {
        return server.worlds[0].getWorldBorder();
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        if (args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, new String[] {"set", "center", "damage", "warning", "add", "get"});
        }
        else if (args.length == 2 && "damage".equals(args[0]))
        {
            return getListOfStringsMatchingLastWord(args, new String[] {"buffer", "amount"});
        }
        else if (args.length >= 2 && args.length <= 3 && "center".equals(args[0]))
        {
            return getTabCompletionCoordinateXZ(args, 1, targetPos);
        }
        else
        {
            return args.length == 2 && "warning".equals(args[0]) ? getListOfStringsMatchingLastWord(args, new String[] {"time", "distance"}) : Collections.emptyList();
        }
    }
}
