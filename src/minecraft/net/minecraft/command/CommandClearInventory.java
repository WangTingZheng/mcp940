package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandClearInventory extends CommandBase
{
    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "clear";
    }

    /**
     * Gets the usage string for the command.
     */
    public String getUsage(ICommandSender sender)
    {
        return "commands.clear.usage";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        EntityPlayerMP entityplayermp = args.length == 0 ? getCommandSenderAsPlayer(sender) : getPlayer(server, sender, args[0]);
        Item item = args.length >= 2 ? getItemByText(sender, args[1]) : null;
        int i = args.length >= 3 ? parseInt(args[2], -1) : -1;
        int j = args.length >= 4 ? parseInt(args[3], -1) : -1;
        NBTTagCompound nbttagcompound = null;

        if (args.length >= 5)
        {
            try
            {
                nbttagcompound = JsonToNBT.getTagFromJson(buildString(args, 4));
            }
            catch (NBTException nbtexception)
            {
                throw new CommandException("commands.clear.tagError", new Object[] {nbtexception.getMessage()});
            }
        }

        if (args.length >= 2 && item == null)
        {
            throw new CommandException("commands.clear.failure", new Object[] {entityplayermp.getName()});
        }
        else
        {
            int k = entityplayermp.inventory.clearMatchingItems(item, i, j, nbttagcompound);
            entityplayermp.inventoryContainer.detectAndSendChanges();

            if (!entityplayermp.capabilities.isCreativeMode)
            {
                entityplayermp.updateHeldItem();
            }

            sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, k);

            if (k == 0)
            {
                throw new CommandException("commands.clear.failure", new Object[] {entityplayermp.getName()});
            }
            else
            {
                if (j == 0)
                {
                    sender.sendMessage(new TextComponentTranslation("commands.clear.testing", new Object[] {entityplayermp.getName(), k}));
                }
                else
                {
                    notifyCommandListener(sender, this, "commands.clear.success", new Object[] {entityplayermp.getName(), k});
                }
            }
        }
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        if (args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        }
        else
        {
            return args.length == 2 ? getListOfStringsMatchingLastWord(args, Item.REGISTRY.getKeys()) : Collections.emptyList();
        }
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 0;
    }
}
