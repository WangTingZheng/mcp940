package net.minecraft.command;

public class WrongUsageException extends SyntaxErrorException
{
    public WrongUsageException(String message, Object... replacements)
    {
        super(message, replacements);
    }

    public synchronized Throwable fillInStackTrace()
    {
        return this;
    }
}
