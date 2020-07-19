package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;

public class CommandDefaultGameMode extends CommandGameMode
{
    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "defaultgamemode";
    }

    /**
     * Gets the usage string for the command.
     */
    public String getUsage(ICommandSender sender)
    {
        return "commands.defaultgamemode.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length <= 0)
        {
            throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
        }
        else
        {
            GameType gametype = this.getGameModeFromCommand(sender, args[0]);
            this.setDefaultGameType(gametype, server);
            notifyCommandListener(sender, this, "commands.defaultgamemode.success", new Object[] {new TextComponentTranslation("gameMode." + gametype.getName(), new Object[0])});
        }
    }

    /**
     * Set the default game type for the server. Also propogate the changes to all players if the server is set to force
     * game mode
     */
    protected void setDefaultGameType(GameType gameType, MinecraftServer server)
    {
        server.setGameType(gameType);

        if (server.getForceGamemode())
        {
            for (EntityPlayerMP entityplayermp : server.getPlayerList().getPlayers())
            {
                entityplayermp.setGameType(gameType);
            }
        }
    }
}
