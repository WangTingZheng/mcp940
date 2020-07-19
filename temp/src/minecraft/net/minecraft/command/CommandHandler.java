package net.minecraft.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class CommandHandler implements ICommandManager {
   private static final Logger field_147175_a = LogManager.getLogger();
   private final Map<String, ICommand> field_71562_a = Maps.<String, ICommand>newHashMap();
   private final Set<ICommand> field_71561_b = Sets.<ICommand>newHashSet();

   public int func_71556_a(ICommandSender p_71556_1_, String p_71556_2_) {
      p_71556_2_ = p_71556_2_.trim();
      if (p_71556_2_.startsWith("/")) {
         p_71556_2_ = p_71556_2_.substring(1);
      }

      String[] astring = p_71556_2_.split(" ");
      String s = astring[0];
      astring = func_71559_a(astring);
      ICommand icommand = this.field_71562_a.get(s);
      int i = 0;

      try {
         int j = this.func_82370_a(icommand, astring);
         if (icommand == null) {
            TextComponentTranslation textcomponenttranslation1 = new TextComponentTranslation("commands.generic.notFound", new Object[0]);
            textcomponenttranslation1.func_150256_b().func_150238_a(TextFormatting.RED);
            p_71556_1_.func_145747_a(textcomponenttranslation1);
         } else if (icommand.func_184882_a(this.func_184879_a(), p_71556_1_)) {
            if (j > -1) {
               List<Entity> list = EntitySelector.<Entity>func_179656_b(p_71556_1_, astring[j], Entity.class);
               String s1 = astring[j];
               p_71556_1_.func_174794_a(CommandResultStats.Type.AFFECTED_ENTITIES, list.size());
               if (list.isEmpty()) {
                  throw new PlayerNotFoundException("commands.generic.selector.notFound", new Object[]{astring[j]});
               }

               for(Entity entity : list) {
                  astring[j] = entity.func_189512_bd();
                  if (this.func_175786_a(p_71556_1_, astring, icommand, p_71556_2_)) {
                     ++i;
                  }
               }

               astring[j] = s1;
            } else {
               p_71556_1_.func_174794_a(CommandResultStats.Type.AFFECTED_ENTITIES, 1);
               if (this.func_175786_a(p_71556_1_, astring, icommand, p_71556_2_)) {
                  ++i;
               }
            }
         } else {
            TextComponentTranslation textcomponenttranslation2 = new TextComponentTranslation("commands.generic.permission", new Object[0]);
            textcomponenttranslation2.func_150256_b().func_150238_a(TextFormatting.RED);
            p_71556_1_.func_145747_a(textcomponenttranslation2);
         }
      } catch (CommandException commandexception) {
         TextComponentTranslation textcomponenttranslation = new TextComponentTranslation(commandexception.getMessage(), commandexception.func_74844_a());
         textcomponenttranslation.func_150256_b().func_150238_a(TextFormatting.RED);
         p_71556_1_.func_145747_a(textcomponenttranslation);
      }

      p_71556_1_.func_174794_a(CommandResultStats.Type.SUCCESS_COUNT, i);
      return i;
   }

   protected boolean func_175786_a(ICommandSender p_175786_1_, String[] p_175786_2_, ICommand p_175786_3_, String p_175786_4_) {
      try {
         p_175786_3_.func_184881_a(this.func_184879_a(), p_175786_1_, p_175786_2_);
         return true;
      } catch (WrongUsageException wrongusageexception) {
         TextComponentTranslation textcomponenttranslation2 = new TextComponentTranslation("commands.generic.usage", new Object[]{new TextComponentTranslation(wrongusageexception.getMessage(), wrongusageexception.func_74844_a())});
         textcomponenttranslation2.func_150256_b().func_150238_a(TextFormatting.RED);
         p_175786_1_.func_145747_a(textcomponenttranslation2);
      } catch (CommandException commandexception) {
         TextComponentTranslation textcomponenttranslation1 = new TextComponentTranslation(commandexception.getMessage(), commandexception.func_74844_a());
         textcomponenttranslation1.func_150256_b().func_150238_a(TextFormatting.RED);
         p_175786_1_.func_145747_a(textcomponenttranslation1);
      } catch (Throwable throwable) {
         TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("commands.generic.exception", new Object[0]);
         textcomponenttranslation.func_150256_b().func_150238_a(TextFormatting.RED);
         p_175786_1_.func_145747_a(textcomponenttranslation);
         field_147175_a.warn("Couldn't process command: " + p_175786_4_, throwable);
      }

      return false;
   }

   protected abstract MinecraftServer func_184879_a();

   public ICommand func_71560_a(ICommand p_71560_1_) {
      this.field_71562_a.put(p_71560_1_.func_71517_b(), p_71560_1_);
      this.field_71561_b.add(p_71560_1_);

      for(String s : p_71560_1_.func_71514_a()) {
         ICommand icommand = this.field_71562_a.get(s);
         if (icommand == null || !icommand.func_71517_b().equals(s)) {
            this.field_71562_a.put(s, p_71560_1_);
         }
      }

      return p_71560_1_;
   }

   private static String[] func_71559_a(String[] p_71559_0_) {
      String[] astring = new String[p_71559_0_.length - 1];
      System.arraycopy(p_71559_0_, 1, astring, 0, p_71559_0_.length - 1);
      return astring;
   }

   public List<String> func_180524_a(ICommandSender p_180524_1_, String p_180524_2_, @Nullable BlockPos p_180524_3_) {
      String[] astring = p_180524_2_.split(" ", -1);
      String s = astring[0];
      if (astring.length == 1) {
         List<String> list = Lists.<String>newArrayList();

         for(Entry<String, ICommand> entry : this.field_71562_a.entrySet()) {
            if (CommandBase.func_71523_a(s, entry.getKey()) && ((ICommand)entry.getValue()).func_184882_a(this.func_184879_a(), p_180524_1_)) {
               list.add(entry.getKey());
            }
         }

         return list;
      } else {
         if (astring.length > 1) {
            ICommand icommand = this.field_71562_a.get(s);
            if (icommand != null && icommand.func_184882_a(this.func_184879_a(), p_180524_1_)) {
               return icommand.func_184883_a(this.func_184879_a(), p_180524_1_, func_71559_a(astring), p_180524_3_);
            }
         }

         return Collections.<String>emptyList();
      }
   }

   public List<ICommand> func_71557_a(ICommandSender p_71557_1_) {
      List<ICommand> list = Lists.<ICommand>newArrayList();

      for(ICommand icommand : this.field_71561_b) {
         if (icommand.func_184882_a(this.func_184879_a(), p_71557_1_)) {
            list.add(icommand);
         }
      }

      return list;
   }

   public Map<String, ICommand> func_71555_a() {
      return this.field_71562_a;
   }

   private int func_82370_a(ICommand p_82370_1_, String[] p_82370_2_) throws CommandException {
      if (p_82370_1_ == null) {
         return -1;
      } else {
         for(int i = 0; i < p_82370_2_.length; ++i) {
            if (p_82370_1_.func_82358_a(p_82370_2_, i) && EntitySelector.func_82377_a(p_82370_2_[i])) {
               return i;
            }
         }

         return -1;
      }
   }
}
