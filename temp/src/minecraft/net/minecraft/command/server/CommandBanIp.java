package net.minecraft.command.server;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListIPBansEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandBanIp extends CommandBase {
   public static final Pattern field_147211_a = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

   public String func_71517_b() {
      return "ban-ip";
   }

   public int func_82362_a() {
      return 3;
   }

   public boolean func_184882_a(MinecraftServer p_184882_1_, ICommandSender p_184882_2_) {
      return p_184882_1_.func_184103_al().func_72363_f().func_152689_b() && super.func_184882_a(p_184882_1_, p_184882_2_);
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.banip.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length >= 1 && p_184881_3_[0].length() > 1) {
         ITextComponent itextcomponent = p_184881_3_.length >= 2 ? func_147178_a(p_184881_2_, p_184881_3_, 1) : null;
         Matcher matcher = field_147211_a.matcher(p_184881_3_[0]);
         if (matcher.matches()) {
            this.func_184892_a(p_184881_1_, p_184881_2_, p_184881_3_[0], itextcomponent == null ? null : itextcomponent.func_150260_c());
         } else {
            EntityPlayerMP entityplayermp = p_184881_1_.func_184103_al().func_152612_a(p_184881_3_[0]);
            if (entityplayermp == null) {
               throw new PlayerNotFoundException("commands.banip.invalid");
            }

            this.func_184892_a(p_184881_1_, p_184881_2_, entityplayermp.func_71114_r(), itextcomponent == null ? null : itextcomponent.func_150260_c());
         }

      } else {
         throw new WrongUsageException("commands.banip.usage", new Object[0]);
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      return p_184883_3_.length == 1 ? func_71530_a(p_184883_3_, p_184883_1_.func_71213_z()) : Collections.emptyList();
   }

   protected void func_184892_a(MinecraftServer p_184892_1_, ICommandSender p_184892_2_, String p_184892_3_, @Nullable String p_184892_4_) {
      UserListIPBansEntry userlistipbansentry = new UserListIPBansEntry(p_184892_3_, (Date)null, p_184892_2_.func_70005_c_(), (Date)null, p_184892_4_);
      p_184892_1_.func_184103_al().func_72363_f().func_152687_a(userlistipbansentry);
      List<EntityPlayerMP> list = p_184892_1_.func_184103_al().func_72382_j(p_184892_3_);
      String[] astring = new String[list.size()];
      int i = 0;

      for(EntityPlayerMP entityplayermp : list) {
         entityplayermp.field_71135_a.func_194028_b(new TextComponentTranslation("multiplayer.disconnect.ip_banned", new Object[0]));
         astring[i++] = entityplayermp.func_70005_c_();
      }

      if (list.isEmpty()) {
         func_152373_a(p_184892_2_, this, "commands.banip.success", new Object[]{p_184892_3_});
      } else {
         func_152373_a(p_184892_2_, this, "commands.banip.success.players", new Object[]{p_184892_3_, func_71527_a(astring)});
      }

   }
}
