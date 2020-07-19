package net.minecraft.command;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class RecipeCommand extends CommandBase
{
    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "recipe";
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
        return "commands.recipe.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length < 2)
        {
            throw new WrongUsageException("commands.recipe.usage", new Object[0]);
        }
        else
        {
            boolean flag = "give".equalsIgnoreCase(args[0]);
            boolean flag1 = "take".equalsIgnoreCase(args[0]);

            if (!flag && !flag1)
            {
                throw new WrongUsageException("commands.recipe.usage", new Object[0]);
            }
            else
            {
                for (EntityPlayerMP entityplayermp : getPlayers(server, sender, args[1]))
                {
                    if ("*".equals(args[2]))
                    {
                        if (flag)
                        {
                            entityplayermp.unlockRecipes(this.getRecipes());
                            notifyCommandListener(sender, this, "commands.recipe.give.success.all", new Object[] {entityplayermp.getName()});
                        }
                        else
                        {
                            entityplayermp.resetRecipes(this.getRecipes());
                            notifyCommandListener(sender, this, "commands.recipe.take.success.all", new Object[] {entityplayermp.getName()});
                        }
                    }
                    else
                    {
                        IRecipe irecipe = CraftingManager.getRecipe(new ResourceLocation(args[2]));

                        if (irecipe == null)
                        {
                            throw new CommandException("commands.recipe.unknownrecipe", new Object[] {args[2]});
                        }

                        if (irecipe.isHidden())
                        {
                            throw new CommandException("commands.recipe.unsupported", new Object[] {args[2]});
                        }

                        List<IRecipe> list = Lists.newArrayList(irecipe);

                        if (flag == entityplayermp.getRecipeBook().containsRecipe(irecipe))
                        {
                            String s = flag ? "commands.recipe.alreadyHave" : "commands.recipe.dontHave";
                            throw new CommandException(s, new Object[] {entityplayermp.getName(), irecipe.getRecipeOutput().getDisplayName()});
                        }

                        if (flag)
                        {
                            entityplayermp.unlockRecipes(list);
                            notifyCommandListener(sender, this, "commands.recipe.give.success.one", new Object[] {entityplayermp.getName(), irecipe.getRecipeOutput().getDisplayName()});
                        }
                        else
                        {
                            entityplayermp.resetRecipes(list);
                            notifyCommandListener(sender, this, "commands.recipe.take.success.one", new Object[] {irecipe.getRecipeOutput().getDisplayName(), entityplayermp.getName()});
                        }
                    }
                }
            }
        }
    }

    private List<IRecipe> getRecipes()
    {
        return Lists.newArrayList(CraftingManager.REGISTRY);
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        if (args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, new String[] {"give", "take"});
        }
        else if (args.length == 2)
        {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        }
        else
        {
            return args.length == 3 ? getListOfStringsMatchingLastWord(args, CraftingManager.REGISTRY.getKeys()) : Collections.emptyList();
        }
    }
}
