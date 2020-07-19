package net.minecraft.command;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class CommandTP extends CommandBase
{
    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "tp";
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
        return "commands.tp.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length < 1)
        {
            throw new WrongUsageException("commands.tp.usage", new Object[0]);
        }
        else
        {
            int i = 0;
            Entity entity;

            if (args.length != 2 && args.length != 4 && args.length != 6)
            {
                entity = getCommandSenderAsPlayer(sender);
            }
            else
            {
                entity = getEntity(server, sender, args[0]);
                i = 1;
            }

            if (args.length != 1 && args.length != 2)
            {
                if (args.length < i + 3)
                {
                    throw new WrongUsageException("commands.tp.usage", new Object[0]);
                }
                else if (entity.world != null)
                {
                    int j = 4096;
                    int k = i + 1;
                    CommandBase.CoordinateArg commandbase$coordinatearg = parseCoordinate(entity.posX, args[i], true);
                    CommandBase.CoordinateArg commandbase$coordinatearg1 = parseCoordinate(entity.posY, args[k++], -4096, 4096, false);
                    CommandBase.CoordinateArg commandbase$coordinatearg2 = parseCoordinate(entity.posZ, args[k++], true);
                    CommandBase.CoordinateArg commandbase$coordinatearg3 = parseCoordinate((double)entity.rotationYaw, args.length > k ? args[k++] : "~", false);
                    CommandBase.CoordinateArg commandbase$coordinatearg4 = parseCoordinate((double)entity.rotationPitch, args.length > k ? args[k] : "~", false);
                    teleportEntityToCoordinates(entity, commandbase$coordinatearg, commandbase$coordinatearg1, commandbase$coordinatearg2, commandbase$coordinatearg3, commandbase$coordinatearg4);
                    notifyCommandListener(sender, this, "commands.tp.success.coordinates", new Object[] {entity.getName(), commandbase$coordinatearg.getResult(), commandbase$coordinatearg1.getResult(), commandbase$coordinatearg2.getResult()});
                }
            }
            else
            {
                Entity entity1 = getEntity(server, sender, args[args.length - 1]);

                if (entity1.world != entity.world)
                {
                    throw new CommandException("commands.tp.notSameDimension", new Object[0]);
                }
                else
                {
                    entity.dismountRidingEntity();

                    if (entity instanceof EntityPlayerMP)
                    {
                        ((EntityPlayerMP)entity).connection.setPlayerLocation(entity1.posX, entity1.posY, entity1.posZ, entity1.rotationYaw, entity1.rotationPitch);
                    }
                    else
                    {
                        entity.setLocationAndAngles(entity1.posX, entity1.posY, entity1.posZ, entity1.rotationYaw, entity1.rotationPitch);
                    }

                    notifyCommandListener(sender, this, "commands.tp.success", new Object[] {entity.getName(), entity1.getName()});
                }
            }
        }
    }

    /**
     * Teleports an entity to the specified coordinates
     */
    private static void teleportEntityToCoordinates(Entity p_189863_0_, CommandBase.CoordinateArg p_189863_1_, CommandBase.CoordinateArg p_189863_2_, CommandBase.CoordinateArg p_189863_3_, CommandBase.CoordinateArg p_189863_4_, CommandBase.CoordinateArg p_189863_5_)
    {
        if (p_189863_0_ instanceof EntityPlayerMP)
        {
            Set<SPacketPlayerPosLook.EnumFlags> set = EnumSet.<SPacketPlayerPosLook.EnumFlags>noneOf(SPacketPlayerPosLook.EnumFlags.class);

            if (p_189863_1_.isRelative())
            {
                set.add(SPacketPlayerPosLook.EnumFlags.X);
            }

            if (p_189863_2_.isRelative())
            {
                set.add(SPacketPlayerPosLook.EnumFlags.Y);
            }

            if (p_189863_3_.isRelative())
            {
                set.add(SPacketPlayerPosLook.EnumFlags.Z);
            }

            if (p_189863_5_.isRelative())
            {
                set.add(SPacketPlayerPosLook.EnumFlags.X_ROT);
            }

            if (p_189863_4_.isRelative())
            {
                set.add(SPacketPlayerPosLook.EnumFlags.Y_ROT);
            }

            float f = (float)p_189863_4_.getAmount();

            if (!p_189863_4_.isRelative())
            {
                f = MathHelper.wrapDegrees(f);
            }

            float f1 = (float)p_189863_5_.getAmount();

            if (!p_189863_5_.isRelative())
            {
                f1 = MathHelper.wrapDegrees(f1);
            }

            p_189863_0_.dismountRidingEntity();
            ((EntityPlayerMP)p_189863_0_).connection.setPlayerLocation(p_189863_1_.getAmount(), p_189863_2_.getAmount(), p_189863_3_.getAmount(), f, f1, set);
            p_189863_0_.setRotationYawHead(f);
        }
        else
        {
            float f2 = (float)MathHelper.wrapDegrees(p_189863_4_.getResult());
            float f3 = (float)MathHelper.wrapDegrees(p_189863_5_.getResult());
            f3 = MathHelper.clamp(f3, -90.0F, 90.0F);
            p_189863_0_.setLocationAndAngles(p_189863_1_.getResult(), p_189863_2_.getResult(), p_189863_3_.getResult(), f2, f3);
            p_189863_0_.setRotationYawHead(f2);
        }

        if (!(p_189863_0_ instanceof EntityLivingBase) || !((EntityLivingBase)p_189863_0_).isElytraFlying())
        {
            p_189863_0_.motionY = 0.0D;
            p_189863_0_.onGround = true;
        }
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        return args.length != 1 && args.length != 2 ? Collections.emptyList() : getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 0;
    }
}
