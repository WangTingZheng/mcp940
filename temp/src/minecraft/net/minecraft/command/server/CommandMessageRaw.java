package net.minecraft.command.server;

import com.google.gson.JsonParseException;
import java.util.Collections;
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
import net.minecraft.util.text.TextComponentUtils;

public class CommandMessageRaw extends CommandBase {
   public String func_71517_b() {
      return "tellraw";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.tellraw.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 2) {
         throw new WrongUsageException("commands.tellraw.usage", new Object[0]);
      } else {
         EntityPlayer entityplayer = func_184888_a(p_184881_1_, p_184881_2_, p_184881_3_[0]);
         String s = func_180529_a(p_184881_3_, 1);

         try {
            ITextComponent itextcomponent = ITextComponent.Serializer.func_150699_a(s);
            entityplayer.func_145747_a(TextComponentUtils.func_179985_a(p_184881_2_, itextcomponent, entityplayer));
         } catch (JsonParseException jsonparseexception) {
            throw func_184889_a(jsonparseexception);
         }
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      return p_184883_3_.length == 1 ? func_71530_a(p_184883_3_, p_184883_1_.func_71213_z()) : Collections.emptyList();
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return p_82358_2_ == 0;
   }
}
