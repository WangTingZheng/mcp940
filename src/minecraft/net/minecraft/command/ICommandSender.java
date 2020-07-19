package net.minecraft.command;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public interface ICommandSender
{
    /**
     * Get the name of this object. For players this returns their username
     */
    String getName();

default ITextComponent getDisplayName()
    {
        return new TextComponentString(this.getName());
    }

default void sendMessage(ITextComponent component)
    {
    }

    /**
     * Returns {@code true} if the CommandSender is allowed to execute the command, {@code false} if not
     */
    boolean canUseCommand(int permLevel, String commandName);

default BlockPos getPosition()
    {
        return BlockPos.ORIGIN;
    }

default Vec3d getPositionVector()
    {
        return Vec3d.ZERO;
    }

    /**
     * Get the world, if available. <b>{@code null} is not allowed!</b> If you are not an entity in the world, return
     * the overworld
     */
    World getEntityWorld();

    @Nullable

default Entity getCommandSenderEntity()
    {
        return null;
    }

default boolean sendCommandFeedback()
    {
        return false;
    }

default void setCommandStat(CommandResultStats.Type type, int amount)
    {
    }

    @Nullable

    /**
     * Get the Minecraft server instance
     */
    MinecraftServer getServer();
}
