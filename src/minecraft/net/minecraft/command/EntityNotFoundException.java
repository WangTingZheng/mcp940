package net.minecraft.command;

public class EntityNotFoundException extends CommandException
{
    public EntityNotFoundException(String p_i47332_1_)
    {
        this("commands.generic.entity.notFound", p_i47332_1_);
    }

    public EntityNotFoundException(String message, Object... args)
    {
        super(message, args);
    }

    public synchronized Throwable fillInStackTrace()
    {
        return this;
    }
}
