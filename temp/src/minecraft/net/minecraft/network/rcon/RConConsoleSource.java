package net.minecraft.network.rcon;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class RConConsoleSource implements ICommandSender {
   private final StringBuffer field_70009_b = new StringBuffer();
   private final MinecraftServer field_184171_b;

   public RConConsoleSource(MinecraftServer p_i46835_1_) {
      this.field_184171_b = p_i46835_1_;
   }

   public String func_70005_c_() {
      return "Rcon";
   }

   public void func_145747_a(ITextComponent p_145747_1_) {
      this.field_70009_b.append(p_145747_1_.func_150260_c());
   }

   public boolean func_70003_b(int p_70003_1_, String p_70003_2_) {
      return true;
   }

   public World func_130014_f_() {
      return this.field_184171_b.func_130014_f_();
   }

   public boolean func_174792_t_() {
      return true;
   }

   public MinecraftServer func_184102_h() {
      return this.field_184171_b;
   }
}
