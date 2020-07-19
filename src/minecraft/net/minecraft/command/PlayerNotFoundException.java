package net.minecraft.command;

public class PlayerNotFoundException extends CommandException
{
    public PlayerNotFoundException(String p_i47330_1_)
    {
        super(p_i47330_1_);
    }

    public PlayerNotFoundException(String message, Object... replacements)
    {
        super(message, replacements);
    }

    public synchronized Throwable fillInStackTrace()
    {
        return this;
    }
}
