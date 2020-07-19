package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommandBlockData extends CommandBase
{
    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "blockdata";
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
        return "commands.blockdata.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length < 4)
        {
            throw new WrongUsageException("commands.blockdata.usage", new Object[0]);
        }
        else
        {
            sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
            BlockPos blockpos = parseBlockPos(sender, args, 0, false);
            World world = sender.getEntityWorld();

            if (!world.isBlockLoaded(blockpos))
            {
                throw new CommandException("commands.blockdata.outOfWorld", new Object[0]);
            }
            else
            {
                IBlockState iblockstate = world.getBlockState(blockpos);
                TileEntity tileentity = world.getTileEntity(blockpos);

                if (tileentity == null)
                {
                    throw new CommandException("commands.blockdata.notValid", new Object[0]);
                }
                else
                {
                    NBTTagCompound nbttagcompound = tileentity.writeToNBT(new NBTTagCompound());
                    NBTTagCompound nbttagcompound1 = nbttagcompound.copy();
                    NBTTagCompound nbttagcompound2;

                    try
                    {
                        nbttagcompound2 = JsonToNBT.getTagFromJson(buildString(args, 3));
                    }
                    catch (NBTException nbtexception)
                    {
                        throw new CommandException("commands.blockdata.tagError", new Object[] {nbtexception.getMessage()});
                    }

                    nbttagcompound.merge(nbttagcompound2);
                    nbttagcompound.setInteger("x", blockpos.getX());
                    nbttagcompound.setInteger("y", blockpos.getY());
                    nbttagcompound.setInteger("z", blockpos.getZ());

                    if (nbttagcompound.equals(nbttagcompound1))
                    {
                        throw new CommandException("commands.blockdata.failed", new Object[] {nbttagcompound.toString()});
                    }
                    else
                    {
                        tileentity.readFromNBT(nbttagcompound);
                        tileentity.markDirty();
                        world.notifyBlockUpdate(blockpos, iblockstate, iblockstate, 3);
                        sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
                        notifyCommandListener(sender, this, "commands.blockdata.success", new Object[] {nbttagcompound.toString()});
                    }
                }
            }
        }
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        return args.length > 0 && args.length <= 3 ? getTabCompletionCoordinate(args, 0, targetPos) : Collections.emptyList();
    }
}
