package net.minecraft.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;

public class CommandHelp extends CommandBase
{
    private static final String[] SEARGE_SAYS = new String[] {"Yolo", "Ask for help on twitter", "/deop @p", "Scoreboard deleted, commands blocked", "Contact helpdesk for help", "/testfornoob @p", "/trigger warning", "Oh my god, it's full of stats", "/kill @p[name=!Searge]", "Have you tried turning it off and on again?", "Sorry, no help today"};
    private final Random rand = new Random();

    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "help";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    /**
     * Gets the usage string for the command.
     */
    public String getUsage(ICommandSender sender)
    {
        return "commands.help.usage";
    }

    public List<String> getAliases()
    {
        return Arrays.<String>asList("?");
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (sender instanceof CommandBlockBaseLogic)
        {
            sender.sendMessage((new TextComponentString("Searge says: ")).appendText(SEARGE_SAYS[this.rand.nextInt(SEARGE_SAYS.length) % SEARGE_SAYS.length]));
        }
        else
        {
            List<ICommand> list = this.getSortedPossibleCommands(sender, server);
            int i = 7;
            int j = (list.size() - 1) / 7;
            int k = 0;

            try
            {
                k = args.length == 0 ? 0 : parseInt(args[0], 1, j + 1) - 1;
            }
            catch (NumberInvalidException numberinvalidexception)
            {
                Map<String, ICommand> map = this.getCommandMap(server);
                ICommand icommand = map.get(args[0]);

                if (icommand != null)
                {
                    throw new WrongUsageException(icommand.getUsage(sender), new Object[0]);
                }

                if (MathHelper.getInt(args[0], -1) == -1 && MathHelper.getInt(args[0], -2) == -2)
                {
                    throw new CommandNotFoundException();
                }

                throw numberinvalidexception;
            }

            int l = Math.min((k + 1) * 7, list.size());
            TextComponentTranslation textcomponenttranslation1 = new TextComponentTranslation("commands.help.header", new Object[] {k + 1, j + 1});
            textcomponenttranslation1.getStyle().setColor(TextFormatting.DARK_GREEN);
            sender.sendMessage(textcomponenttranslation1);

            for (int i1 = k * 7; i1 < l; ++i1)
            {
                ICommand icommand1 = list.get(i1);
                TextComponentTranslation textcomponenttranslation = new TextComponentTranslation(icommand1.getUsage(sender), new Object[0]);
                textcomponenttranslation.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + icommand1.getName() + " "));
                sender.sendMessage(textcomponenttranslation);
            }

            if (k == 0)
            {
                TextComponentTranslation textcomponenttranslation2 = new TextComponentTranslation("commands.help.footer", new Object[0]);
                textcomponenttranslation2.getStyle().setColor(TextFormatting.GREEN);
                sender.sendMessage(textcomponenttranslation2);
            }
        }
    }

    protected List<ICommand> getSortedPossibleCommands(ICommandSender sender, MinecraftServer server)
    {
        List<ICommand> list = server.getCommandManager().getPossibleCommands(sender);
        Collections.sort(list);
        return list;
    }

    protected Map<String, ICommand> getCommandMap(MinecraftServer server)
    {
        return server.getCommandManager().getCommands();
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        if (args.length == 1)
        {
            Set<String> set = this.getCommandMap(server).keySet();
            return getListOfStringsMatchingLastWord(args, (String[])set.toArray(new String[set.size()]));
        }
        else
        {
            return Collections.<String>emptyList();
        }
    }
}
