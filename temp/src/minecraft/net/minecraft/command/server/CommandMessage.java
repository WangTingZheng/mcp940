package net.minecraft.command.server;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class CommandMessage extends CommandBase {
   public List<String> func_71514_a() {
      return Arrays.<String>asList("w", "msg");
   }

   public String func_71517_b() {
      return "tell";
   }

   public int func_82362_a() {
      return 0;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.message.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 2) {
         throw new WrongUsageException("commands.message.usage", new Object[0]);
      } else {
         EntityPlayer entityplayer = func_184888_a(p_184881_1_, p_184881_2_, p_184881_3_[0]);
         if (entityplayer == p_184881_2_) {
            throw new PlayerNotFoundException("commands.message.sameTarget");
         } else {
            ITextComponent itextcomponent = func_147176_a(p_184881_2_, p_184881_3_, 1, !(p_184881_2_ instanceof EntityPlayer));
            TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("commands.message.display.incoming", new Object[]{p_184881_2_.func_145748_c_(), itextcomponent.func_150259_f()});
            TextComponentTranslation textcomponenttranslation1 = new TextComponentTranslation("commands.message.display.outgoing", new Object[]{entityplayer.func_145748_c_(), itextcomponent.func_150259_f()});
            textcomponenttranslation.func_150256_b().func_150238_a(TextFormatting.GRAY).func_150217_b(Boolean.valueOf(true));
            textcomponenttranslation1.func_150256_b().func_150238_a(TextFormatting.GRAY).func_150217_b(Boolean.valueOf(true));
            entityplayer.func_145747_a(textcomponenttranslation);
            p_184881_2_.func_145747_a(textcomponenttranslation1);
         }
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      return func_71530_a(p_184883_3_, p_184883_1_.func_71213_z());
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return p_82358_2_ == 0;
   }
}
