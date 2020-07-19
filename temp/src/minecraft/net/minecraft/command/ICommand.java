package net.minecraft.command;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public interface ICommand extends Comparable<ICommand> {
   String func_71517_b();

   String func_71518_a(ICommandSender var1);

   List<String> func_71514_a();

   void func_184881_a(MinecraftServer var1, ICommandSender var2, String[] var3) throws CommandException;

   boolean func_184882_a(MinecraftServer var1, ICommandSender var2);

   List<String> func_184883_a(MinecraftServer var1, ICommandSender var2, String[] var3, @Nullable BlockPos var4);

   boolean func_82358_a(String[] var1, int var2);
}
