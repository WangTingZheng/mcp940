package net.minecraft.command;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public interface ICommand extends Comparable<ICommand>
{
    /**
     * Gets the name of the command
     */
    String getName();

    /**
     * Gets the usage string for the command.
     */
    String getUsage(ICommandSender sender);

    List<String> getAliases();

    /**
     * Callback for when the command is executed
     */
    void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException;

    /**
     * Check if the given ICommandSender has permission to execute this command
     */
    boolean checkPermission(MinecraftServer server, ICommandSender sender);

    List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos);

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    boolean isUsernameIndex(String[] args, int index);
}
