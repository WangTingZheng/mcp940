package net.minecraft.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;

public class CommandHelp extends CommandBase {
   private static final String[] field_184901_a = new String[]{"Yolo", "Ask for help on twitter", "/deop @p", "Scoreboard deleted, commands blocked", "Contact helpdesk for help", "/testfornoob @p", "/trigger warning", "Oh my god, it's full of stats", "/kill @p[name=!Searge]", "Have you tried turning it off and on again?", "Sorry, no help today"};
   private final Random field_184902_b = new Random();

   public String func_71517_b() {
      return "help";
   }

   public int func_82362_a() {
      return 0;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.help.usage";
   }

   public List<String> func_71514_a() {
      return Arrays.<String>asList("?");
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_2_ instanceof CommandBlockBaseLogic) {
         p_184881_2_.func_145747_a((new TextComponentString("Searge says: ")).func_150258_a(field_184901_a[this.field_184902_b.nextInt(field_184901_a.length) % field_184901_a.length]));
      } else {
         List<ICommand> list = this.func_184900_a(p_184881_2_, p_184881_1_);
         int i = 7;
         int j = (list.size() - 1) / 7;
         int k = 0;

         try {
            k = p_184881_3_.length == 0 ? 0 : func_175764_a(p_184881_3_[0], 1, j + 1) - 1;
         } catch (NumberInvalidException numberinvalidexception) {
            Map<String, ICommand> map = this.func_184899_a(p_184881_1_);
            ICommand icommand = map.get(p_184881_3_[0]);
            if (icommand != null) {
               throw new WrongUsageException(icommand.func_71518_a(p_184881_2_), new Object[0]);
            }

            if (MathHelper.func_82715_a(p_184881_3_[0], -1) == -1 && MathHelper.func_82715_a(p_184881_3_[0], -2) == -2) {
               throw new CommandNotFoundException();
            }

            throw numberinvalidexception;
         }

         int l = Math.min((k + 1) * 7, list.size());
         TextComponentTranslation textcomponenttranslation1 = new TextComponentTranslation("commands.help.header", new Object[]{k + 1, j + 1});
         textcomponenttranslation1.func_150256_b().func_150238_a(TextFormatting.DARK_GREEN);
         p_184881_2_.func_145747_a(textcomponenttranslation1);

         for(int i1 = k * 7; i1 < l; ++i1) {
            ICommand icommand1 = list.get(i1);
            TextComponentTranslation textcomponenttranslation = new TextComponentTranslation(icommand1.func_71518_a(p_184881_2_), new Object[0]);
            textcomponenttranslation.func_150256_b().func_150241_a(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + icommand1.func_71517_b() + " "));
            p_184881_2_.func_145747_a(textcomponenttranslation);
         }

         if (k == 0) {
            TextComponentTranslation textcomponenttranslation2 = new TextComponentTranslation("commands.help.footer", new Object[0]);
            textcomponenttranslation2.func_150256_b().func_150238_a(TextFormatting.GREEN);
            p_184881_2_.func_145747_a(textcomponenttranslation2);
         }

      }
   }

   protected List<ICommand> func_184900_a(ICommandSender p_184900_1_, MinecraftServer p_184900_2_) {
      List<ICommand> list = p_184900_2_.func_71187_D().func_71557_a(p_184900_1_);
      Collections.sort(list);
      return list;
   }

   protected Map<String, ICommand> func_184899_a(MinecraftServer p_184899_1_) {
      return p_184899_1_.func_71187_D().func_71555_a();
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length == 1) {
         Set<String> set = this.func_184899_a(p_184883_1_).keySet();
         return func_71530_a(p_184883_3_, (String[])set.toArray(new String[set.size()]));
      } else {
         return Collections.<String>emptyList();
      }
   }
}
