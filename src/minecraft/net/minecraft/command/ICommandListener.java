package net.minecraft.command;

public interface ICommandListener
{
    /**
     * Send an informative message to the server operators
     */
    void notifyListener(ICommandSender sender, ICommand command, int flags, String translationKey, Object... translationArgs);
}
