package net.minecraft.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.WorldInfo;

public class CommandToggleDownfall extends CommandBase {
   public String func_71517_b() {
      return "toggledownfall";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.downfall.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      this.func_184930_a(p_184881_1_);
      func_152373_a(p_184881_2_, this, "commands.downfall.success", new Object[0]);
   }

   protected void func_184930_a(MinecraftServer p_184930_1_) {
      WorldInfo worldinfo = p_184930_1_.field_71305_c[0].func_72912_H();
      worldinfo.func_76084_b(!worldinfo.func_76059_o());
   }
}
