package net.minecraft.command.server;

import com.mojang.authlib.GameProfile;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListBansEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandBanPlayer extends CommandBase {
   public String func_71517_b() {
      return "ban";
   }

   public int func_82362_a() {
      return 3;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.ban.usage";
   }

   public boolean func_184882_a(MinecraftServer p_184882_1_, ICommandSender p_184882_2_) {
      return p_184882_1_.func_184103_al().func_152608_h().func_152689_b() && super.func_184882_a(p_184882_1_, p_184882_2_);
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length >= 1 && p_184881_3_[0].length() > 0) {
         GameProfile gameprofile = p_184881_1_.func_152358_ax().func_152655_a(p_184881_3_[0]);
         if (gameprofile == null) {
            throw new CommandException("commands.ban.failed", new Object[]{p_184881_3_[0]});
         } else {
            String s = null;
            if (p_184881_3_.length >= 2) {
               s = func_147178_a(p_184881_2_, p_184881_3_, 1).func_150260_c();
            }

            UserListBansEntry userlistbansentry = new UserListBansEntry(gameprofile, (Date)null, p_184881_2_.func_70005_c_(), (Date)null, s);
            p_184881_1_.func_184103_al().func_152608_h().func_152687_a(userlistbansentry);
            EntityPlayerMP entityplayermp = p_184881_1_.func_184103_al().func_152612_a(p_184881_3_[0]);
            if (entityplayermp != null) {
               entityplayermp.field_71135_a.func_194028_b(new TextComponentTranslation("multiplayer.disconnect.banned", new Object[0]));
            }

            func_152373_a(p_184881_2_, this, "commands.ban.success", new Object[]{p_184881_3_[0]});
         }
      } else {
         throw new WrongUsageException("commands.ban.usage", new Object[0]);
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      return p_184883_3_.length >= 1 ? func_71530_a(p_184883_3_, p_184883_1_.func_71213_z()) : Collections.emptyList();
   }
}
