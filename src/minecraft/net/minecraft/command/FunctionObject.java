package net.minecraft.command;

import com.google.common.collect.Lists;
import java.util.ArrayDeque;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.FunctionManager;
import net.minecraft.util.ResourceLocation;

public class FunctionObject
{
    private final FunctionObject.Entry[] entries;

    public FunctionObject(FunctionObject.Entry[] entriesIn)
    {
        this.entries = entriesIn;
    }

    public FunctionObject.Entry[] getEntries()
    {
        return this.entries;
    }

    /**
     * Create a Function from the given function definition.
     */
    public static FunctionObject create(FunctionManager functionManagerIn, List<String> commands)
    {
        List<FunctionObject.Entry> list = Lists.<FunctionObject.Entry>newArrayListWithCapacity(commands.size());

        for (String s : commands)
        {
            s = s.trim();

            if (!s.startsWith("#") && !s.isEmpty())
            {
                String[] astring = s.split(" ", 2);
                String s1 = astring[0];

                if (!functionManagerIn.getCommandManager().getCommands().containsKey(s1))
                {
                    if (s1.startsWith("//"))
                    {
                        throw new IllegalArgumentException("Unknown or invalid command '" + s1 + "' (if you intended to make a comment, use '#' not '//')");
                    }

                    if (s1.startsWith("/") && s1.length() > 1)
                    {
                        throw new IllegalArgumentException("Unknown or invalid command '" + s1 + "' (did you mean '" + s1.substring(1) + "'? Do not use a preceding forwards slash.)");
                    }

                    throw new IllegalArgumentException("Unknown or invalid command '" + s1 + "'");
                }

                list.add(new FunctionObject.CommandEntry(s));
            }
        }

        return new FunctionObject((FunctionObject.Entry[])list.toArray(new FunctionObject.Entry[list.size()]));
    }

    public static class CacheableFunction
    {
        public static final FunctionObject.CacheableFunction EMPTY = new FunctionObject.CacheableFunction((ResourceLocation)null);
        @Nullable
        private final ResourceLocation id;
        private boolean isValid;
        private FunctionObject function;

        public CacheableFunction(@Nullable ResourceLocation idIn)
        {
            this.id = idIn;
        }

        public CacheableFunction(FunctionObject functionIn)
        {
            this.id = null;
            this.function = functionIn;
        }

        @Nullable
        public FunctionObject get(FunctionManager functionManagerIn)
        {
            if (!this.isValid)
            {
                if (this.id != null)
                {
                    this.function = functionManagerIn.getFunction(this.id);
                }

                this.isValid = true;
            }

            return this.function;
        }

        public String toString()
        {
            return String.valueOf((Object)this.id);
        }
    }

    public static class CommandEntry implements FunctionObject.Entry
    {
        private final String command;

        public CommandEntry(String p_i47534_1_)
        {
            this.command = p_i47534_1_;
        }

        public void execute(FunctionManager functionManagerIn, ICommandSender sender, ArrayDeque<FunctionManager.QueuedCommand> commandQueue, int maxCommandChainLength)
        {
            functionManagerIn.getCommandManager().executeCommand(sender, this.command);
        }

        public String toString()
        {
            return "/" + this.command;
        }
    }

    public interface Entry
    {
        void execute(FunctionManager functionManagerIn, ICommandSender sender, ArrayDeque<FunctionManager.QueuedCommand> commandQueue, int maxCommandChainLength);
    }

    public static class FunctionEntry implements FunctionObject.Entry
    {
        private final FunctionObject.CacheableFunction function;

        public FunctionEntry(FunctionObject functionIn)
        {
            this.function = new FunctionObject.CacheableFunction(functionIn);
        }

        public void execute(FunctionManager functionManagerIn, ICommandSender sender, ArrayDeque<FunctionManager.QueuedCommand> commandQueue, int maxCommandChainLength)
        {
            FunctionObject functionobject = this.function.get(functionManagerIn);

            if (functionobject != null)
            {
                FunctionObject.Entry[] afunctionobject$entry = functionobject.getEntries();
                int i = maxCommandChainLength - commandQueue.size();
                int j = Math.min(afunctionobject$entry.length, i);

                for (int k = j - 1; k >= 0; --k)
                {
                    commandQueue.addFirst(new FunctionManager.QueuedCommand(functionManagerIn, sender, afunctionobject$entry[k]));
                }
            }
        }

        public String toString()
        {
            return "/function " + this.function;
        }
    }
}
