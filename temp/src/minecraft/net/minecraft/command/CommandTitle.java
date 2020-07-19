package net.minecraft.command;

import com.google.gson.JsonParseException;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandTitle extends CommandBase {
   private static final Logger field_175774_a = LogManager.getLogger();

   public String func_71517_b() {
      return "title";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.title.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 2) {
         throw new WrongUsageException("commands.title.usage", new Object[0]);
      } else {
         if (p_184881_3_.length < 3) {
            if ("title".equals(p_184881_3_[1]) || "subtitle".equals(p_184881_3_[1]) || "actionbar".equals(p_184881_3_[1])) {
               throw new WrongUsageException("commands.title.usage.title", new Object[0]);
            }

            if ("times".equals(p_184881_3_[1])) {
               throw new WrongUsageException("commands.title.usage.times", new Object[0]);
            }
         }

         EntityPlayerMP entityplayermp = func_184888_a(p_184881_1_, p_184881_2_, p_184881_3_[0]);
         SPacketTitle.Type spackettitle$type = SPacketTitle.Type.func_179969_a(p_184881_3_[1]);
         if (spackettitle$type != SPacketTitle.Type.CLEAR && spackettitle$type != SPacketTitle.Type.RESET) {
            if (spackettitle$type == SPacketTitle.Type.TIMES) {
               if (p_184881_3_.length != 5) {
                  throw new WrongUsageException("commands.title.usage", new Object[0]);
               } else {
                  int i = func_175755_a(p_184881_3_[2]);
                  int j = func_175755_a(p_184881_3_[3]);
                  int k = func_175755_a(p_184881_3_[4]);
                  SPacketTitle spackettitle2 = new SPacketTitle(i, j, k);
                  entityplayermp.field_71135_a.func_147359_a(spackettitle2);
                  func_152373_a(p_184881_2_, this, "commands.title.success", new Object[0]);
               }
            } else if (p_184881_3_.length < 3) {
               throw new WrongUsageException("commands.title.usage", new Object[0]);
            } else {
               String s = func_180529_a(p_184881_3_, 2);

               ITextComponent itextcomponent;
               try {
                  itextcomponent = ITextComponent.Serializer.func_150699_a(s);
               } catch (JsonParseException jsonparseexception) {
                  throw func_184889_a(jsonparseexception);
               }

               SPacketTitle spackettitle1 = new SPacketTitle(spackettitle$type, TextComponentUtils.func_179985_a(p_184881_2_, itextcomponent, entityplayermp));
               entityplayermp.field_71135_a.func_147359_a(spackettitle1);
               func_152373_a(p_184881_2_, this, "commands.title.success", new Object[0]);
            }
         } else if (p_184881_3_.length != 2) {
            throw new WrongUsageException("commands.title.usage", new Object[0]);
         } else {
            SPacketTitle spackettitle = new SPacketTitle(spackettitle$type, (ITextComponent)null);
            entityplayermp.field_71135_a.func_147359_a(spackettitle);
            func_152373_a(p_184881_2_, this, "commands.title.success", new Object[0]);
         }
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length == 1) {
         return func_71530_a(p_184883_3_, p_184883_1_.func_71213_z());
      } else {
         return p_184883_3_.length == 2 ? func_71530_a(p_184883_3_, SPacketTitle.Type.func_179971_a()) : Collections.emptyList();
      }
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return p_82358_2_ == 0;
   }
}
