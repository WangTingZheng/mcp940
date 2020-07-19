package net.minecraft.command.server;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandEmote extends CommandBase {
   public String func_71517_b() {
      return "me";
   }

   public int func_82362_a() {
      return 0;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.me.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length <= 0) {
         throw new WrongUsageException("commands.me.usage", new Object[0]);
      } else {
         ITextComponent itextcomponent = func_147176_a(p_184881_2_, p_184881_3_, 0, !(p_184881_2_ instanceof EntityPlayer));
         p_184881_1_.func_184103_al().func_148539_a(new TextComponentTranslation("chat.type.emote", new Object[]{p_184881_2_.func_145748_c_(), itextcomponent}));
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      return func_71530_a(p_184883_3_, p_184883_1_.func_71213_z());
   }
}
