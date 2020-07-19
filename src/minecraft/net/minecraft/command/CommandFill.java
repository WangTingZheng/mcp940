package net.minecraft.command;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommandFill extends CommandBase
{
    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "fill";
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
        return "commands.fill.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length < 7)
        {
            throw new WrongUsageException("commands.fill.usage", new Object[0]);
        }
        else
        {
            sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
            BlockPos blockpos = parseBlockPos(sender, args, 0, false);
            BlockPos blockpos1 = parseBlockPos(sender, args, 3, false);
            Block block = CommandBase.getBlockByText(sender, args[6]);
            IBlockState iblockstate;

            if (args.length >= 8)
            {
                iblockstate = convertArgToBlockState(block, args[7]);
            }
            else
            {
                iblockstate = block.getDefaultState();
            }

            BlockPos blockpos2 = new BlockPos(Math.min(blockpos.getX(), blockpos1.getX()), Math.min(blockpos.getY(), blockpos1.getY()), Math.min(blockpos.getZ(), blockpos1.getZ()));
            BlockPos blockpos3 = new BlockPos(Math.max(blockpos.getX(), blockpos1.getX()), Math.max(blockpos.getY(), blockpos1.getY()), Math.max(blockpos.getZ(), blockpos1.getZ()));
            int i = (blockpos3.getX() - blockpos2.getX() + 1) * (blockpos3.getY() - blockpos2.getY() + 1) * (blockpos3.getZ() - blockpos2.getZ() + 1);

            if (i > 32768)
            {
                throw new CommandException("commands.fill.tooManyBlocks", new Object[] {i, Integer.valueOf(32768)});
            }
            else if (blockpos2.getY() >= 0 && blockpos3.getY() < 256)
            {
                World world = sender.getEntityWorld();

                for (int j = blockpos2.getZ(); j <= blockpos3.getZ(); j += 16)
                {
                    for (int k = blockpos2.getX(); k <= blockpos3.getX(); k += 16)
                    {
                        if (!world.isBlockLoaded(new BlockPos(k, blockpos3.getY() - blockpos2.getY(), j)))
                        {
                            throw new CommandException("commands.fill.outOfWorld", new Object[0]);
                        }
                    }
                }

                NBTTagCompound nbttagcompound = new NBTTagCompound();
                boolean flag = false;

                if (args.length >= 10 && block.hasTileEntity())
                {
                    String s = buildString(args, 9);

                    try
                    {
                        nbttagcompound = JsonToNBT.getTagFromJson(s);
                        flag = true;
                    }
                    catch (NBTException nbtexception)
                    {
                        throw new CommandException("commands.fill.tagError", new Object[] {nbtexception.getMessage()});
                    }
                }

                List<BlockPos> list = Lists.<BlockPos>newArrayList();
                i = 0;

                for (int l = blockpos2.getZ(); l <= blockpos3.getZ(); ++l)
                {
                    for (int i1 = blockpos2.getY(); i1 <= blockpos3.getY(); ++i1)
                    {
                        for (int j1 = blockpos2.getX(); j1 <= blockpos3.getX(); ++j1)
                        {
                            BlockPos blockpos4 = new BlockPos(j1, i1, l);

                            if (args.length >= 9)
                            {
                                if (!"outline".equals(args[8]) && !"hollow".equals(args[8]))
                                {
                                    if ("destroy".equals(args[8]))
                                    {
                                        world.destroyBlock(blockpos4, true);
                                    }
                                    else if ("keep".equals(args[8]))
                                    {
                                        if (!world.isAirBlock(blockpos4))
                                        {
                                            continue;
                                        }
                                    }
                                    else if ("replace".equals(args[8]) && !block.hasTileEntity() && args.length > 9)
                                    {
                                        Block block1 = CommandBase.getBlockByText(sender, args[9]);

                                        if (world.getBlockState(blockpos4).getBlock() != block1 || args.length > 10 && !"-1".equals(args[10]) && !"*".equals(args[10]) && !CommandBase.convertArgToBlockStatePredicate(block1, args[10]).apply(world.getBlockState(blockpos4)))
                                        {
                                            continue;
                                        }
                                    }
                                }
                                else if (j1 != blockpos2.getX() && j1 != blockpos3.getX() && i1 != blockpos2.getY() && i1 != blockpos3.getY() && l != blockpos2.getZ() && l != blockpos3.getZ())
                                {
                                    if ("hollow".equals(args[8]))
                                    {
                                        world.setBlockState(blockpos4, Blocks.AIR.getDefaultState(), 2);
                                        list.add(blockpos4);
                                    }

                                    continue;
                                }
                            }

                            TileEntity tileentity1 = world.getTileEntity(blockpos4);

                            if (tileentity1 != null && tileentity1 instanceof IInventory)
                            {
                                ((IInventory)tileentity1).clear();
                            }

                            if (world.setBlockState(blockpos4, iblockstate, 2))
                            {
                                list.add(blockpos4);
                                ++i;

                                if (flag)
                                {
                                    TileEntity tileentity = world.getTileEntity(blockpos4);

                                    if (tileentity != null)
                                    {
                                        nbttagcompound.setInteger("x", blockpos4.getX());
                                        nbttagcompound.setInteger("y", blockpos4.getY());
                                        nbttagcompound.setInteger("z", blockpos4.getZ());
                                        tileentity.readFromNBT(nbttagcompound);
                                    }
                                }
                            }
                        }
                    }
                }

                for (BlockPos blockpos5 : list)
                {
                    Block block2 = world.getBlockState(blockpos5).getBlock();
                    world.notifyNeighborsRespectDebug(blockpos5, block2, false);
                }

                if (i <= 0)
                {
                    throw new CommandException("commands.fill.failed", new Object[0]);
                }
                else
                {
                    sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, i);
                    notifyCommandListener(sender, this, "commands.fill.success", new Object[] {i});
                }
            }
            else
            {
                throw new CommandException("commands.fill.outOfWorld", new Object[0]);
            }
        }
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        if (args.length > 0 && args.length <= 3)
        {
            return getTabCompletionCoordinate(args, 0, targetPos);
        }
        else if (args.length > 3 && args.length <= 6)
        {
            return getTabCompletionCoordinate(args, 3, targetPos);
        }
        else if (args.length == 7)
        {
            return getListOfStringsMatchingLastWord(args, Block.REGISTRY.getKeys());
        }
        else if (args.length == 9)
        {
            return getListOfStringsMatchingLastWord(args, new String[] {"replace", "destroy", "keep", "hollow", "outline"});
        }
        else
        {
            return args.length == 10 && "replace".equals(args[8]) ? getListOfStringsMatchingLastWord(args, Block.REGISTRY.getKeys()) : Collections.emptyList();
        }
    }
}
