package net.minecraft.command;

public class NumberInvalidException extends CommandException
{
    public NumberInvalidException()
    {
        this("commands.generic.num.invalid");
    }

    public NumberInvalidException(String message, Object... replacements)
    {
        super(message, replacements);
    }

    public synchronized Throwable fillInStackTrace()
    {
        return this;
    }
}
