package net.minecraft.command;

import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;

public interface ICommandManager {
   int func_71556_a(ICommandSender var1, String var2);

   List<String> func_180524_a(ICommandSender var1, String var2, @Nullable BlockPos var3);

   List<ICommand> func_71557_a(ICommandSender var1);

   Map<String, ICommand> func_71555_a();
}
