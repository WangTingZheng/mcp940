package net.minecraft.command.server;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommandTestForBlock extends CommandBase
{
    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "testforblock";
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
        return "commands.testforblock.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length < 4)
        {
            throw new WrongUsageException("commands.testforblock.usage", new Object[0]);
        }
        else
        {
            sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
            BlockPos blockpos = parseBlockPos(sender, args, 0, false);
            Block block = getBlockByText(sender, args[3]);

            if (block == null)
            {
                throw new NumberInvalidException("commands.setblock.notFound", new Object[] {args[3]});
            }
            else
            {
                World world = sender.getEntityWorld();

                if (!world.isBlockLoaded(blockpos))
                {
                    throw new CommandException("commands.testforblock.outOfWorld", new Object[0]);
                }
                else
                {
                    NBTTagCompound nbttagcompound = new NBTTagCompound();
                    boolean flag = false;

                    if (args.length >= 6 && block.hasTileEntity())
                    {
                        String s = buildString(args, 5);

                        try
                        {
                            nbttagcompound = JsonToNBT.getTagFromJson(s);
                            flag = true;
                        }
                        catch (NBTException nbtexception)
                        {
                            throw new CommandException("commands.setblock.tagError", new Object[] {nbtexception.getMessage()});
                        }
                    }

                    IBlockState iblockstate = world.getBlockState(blockpos);
                    Block block1 = iblockstate.getBlock();

                    if (block1 != block)
                    {
                        throw new CommandException("commands.testforblock.failed.tile", new Object[] {blockpos.getX(), blockpos.getY(), blockpos.getZ(), block1.getLocalizedName(), block.getLocalizedName()});
                    }
                    else if (args.length >= 5 && !CommandBase.convertArgToBlockStatePredicate(block, args[4]).apply(iblockstate))
                    {
                        try
                        {
                            int i = iblockstate.getBlock().getMetaFromState(iblockstate);
                            throw new CommandException("commands.testforblock.failed.data", new Object[] {blockpos.getX(), blockpos.getY(), blockpos.getZ(), i, Integer.parseInt(args[4])});
                        }
                        catch (NumberFormatException var13)
                        {
                            throw new CommandException("commands.testforblock.failed.data", new Object[] {blockpos.getX(), blockpos.getY(), blockpos.getZ(), iblockstate.toString(), args[4]});
                        }
                    }
                    else
                    {
                        if (flag)
                        {
                            TileEntity tileentity = world.getTileEntity(blockpos);

                            if (tileentity == null)
                            {
                                throw new CommandException("commands.testforblock.failed.tileEntity", new Object[] {blockpos.getX(), blockpos.getY(), blockpos.getZ()});
                            }

                            NBTTagCompound nbttagcompound1 = tileentity.writeToNBT(new NBTTagCompound());

                            if (!NBTUtil.areNBTEquals(nbttagcompound, nbttagcompound1, true))
                            {
                                throw new CommandException("commands.testforblock.failed.nbt", new Object[] {blockpos.getX(), blockpos.getY(), blockpos.getZ()});
                            }
                        }

                        sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
                        notifyCommandListener(sender, this, "commands.testforblock.success", new Object[] {blockpos.getX(), blockpos.getY(), blockpos.getZ()});
                    }
                }
            }
        }
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        if (args.length > 0 && args.length <= 3)
        {
            return getTabCompletionCoordinate(args, 0, targetPos);
        }
        else
        {
            return args.length == 4 ? getListOfStringsMatchingLastWord(args, Block.REGISTRY.getKeys()) : Collections.emptyList();
        }
    }
}
