package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class CommandPlaySound extends CommandBase
{
    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "playsound";
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
        return "commands.playsound.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length < 2)
        {
            throw new WrongUsageException(this.getUsage(sender), new Object[0]);
        }
        else
        {
            int i = 0;
            String s = args[i++];
            String s1 = args[i++];
            SoundCategory soundcategory = SoundCategory.getByName(s1);

            if (soundcategory == null)
            {
                throw new CommandException("commands.playsound.unknownSoundSource", new Object[] {s1});
            }
            else
            {
                EntityPlayerMP entityplayermp = getPlayer(server, sender, args[i++]);
                Vec3d vec3d = sender.getPositionVector();
                double d0 = args.length > i ? parseDouble(vec3d.x, args[i++], true) : vec3d.x;
                double d1 = args.length > i ? parseDouble(vec3d.y, args[i++], 0, 0, false) : vec3d.y;
                double d2 = args.length > i ? parseDouble(vec3d.z, args[i++], true) : vec3d.z;
                double d3 = args.length > i ? parseDouble(args[i++], 0.0D, 3.4028234663852886E38D) : 1.0D;
                double d4 = args.length > i ? parseDouble(args[i++], 0.0D, 2.0D) : 1.0D;
                double d5 = args.length > i ? parseDouble(args[i], 0.0D, 1.0D) : 0.0D;
                double d6 = d3 > 1.0D ? d3 * 16.0D : 16.0D;
                double d7 = entityplayermp.getDistance(d0, d1, d2);

                if (d7 > d6)
                {
                    if (d5 <= 0.0D)
                    {
                        throw new CommandException("commands.playsound.playerTooFar", new Object[] {entityplayermp.getName()});
                    }

                    double d8 = d0 - entityplayermp.posX;
                    double d9 = d1 - entityplayermp.posY;
                    double d10 = d2 - entityplayermp.posZ;
                    double d11 = Math.sqrt(d8 * d8 + d9 * d9 + d10 * d10);

                    if (d11 > 0.0D)
                    {
                        d0 = entityplayermp.posX + d8 / d11 * 2.0D;
                        d1 = entityplayermp.posY + d9 / d11 * 2.0D;
                        d2 = entityplayermp.posZ + d10 / d11 * 2.0D;
                    }

                    d3 = d5;
                }

                entityplayermp.connection.sendPacket(new SPacketCustomSound(s, soundcategory, d0, d1, d2, (float)d3, (float)d4));
                notifyCommandListener(sender, this, "commands.playsound.success", new Object[] {s, entityplayermp.getName()});
            }
        }
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        if (args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, SoundEvent.REGISTRY.getKeys());
        }
        else if (args.length == 2)
        {
            return getListOfStringsMatchingLastWord(args, SoundCategory.getSoundCategoryNames());
        }
        else if (args.length == 3)
        {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        }
        else
        {
            return args.length > 3 && args.length <= 6 ? getTabCompletionCoordinate(args, 3, targetPos) : Collections.emptyList();
        }
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 2;
    }
}
