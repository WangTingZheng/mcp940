package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;

public class CommandDefaultGameMode extends CommandGameMode {
   public String func_71517_b() {
      return "defaultgamemode";
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.defaultgamemode.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length <= 0) {
         throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
      } else {
         GameType gametype = this.func_71539_b(p_184881_2_, p_184881_3_[0]);
         this.func_184896_a(gametype, p_184881_1_);
         func_152373_a(p_184881_2_, this, "commands.defaultgamemode.success", new Object[]{new TextComponentTranslation("gameMode." + gametype.func_77149_b(), new Object[0])});
      }
   }

   protected void func_184896_a(GameType p_184896_1_, MinecraftServer p_184896_2_) {
      p_184896_2_.func_71235_a(p_184896_1_);
      if (p_184896_2_.func_104056_am()) {
         for(EntityPlayerMP entityplayermp : p_184896_2_.func_184103_al().func_181057_v()) {
            entityplayermp.func_71033_a(p_184896_1_);
         }
      }

   }
}
