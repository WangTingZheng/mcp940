package net.minecraft.command;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandDebug extends CommandBase {
   private static final Logger field_147208_a = LogManager.getLogger();
   private long field_147206_b;
   private int field_147207_c;

   public String func_71517_b() {
      return "debug";
   }

   public int func_82362_a() {
      return 3;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.debug.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 1) {
         throw new WrongUsageException("commands.debug.usage", new Object[0]);
      } else {
         if ("start".equals(p_184881_3_[0])) {
            if (p_184881_3_.length != 1) {
               throw new WrongUsageException("commands.debug.usage", new Object[0]);
            }

            func_152373_a(p_184881_2_, this, "commands.debug.start", new Object[0]);
            p_184881_1_.func_71223_ag();
            this.field_147206_b = MinecraftServer.func_130071_aq();
            this.field_147207_c = p_184881_1_.func_71259_af();
         } else {
            if (!"stop".equals(p_184881_3_[0])) {
               throw new WrongUsageException("commands.debug.usage", new Object[0]);
            }

            if (p_184881_3_.length != 1) {
               throw new WrongUsageException("commands.debug.usage", new Object[0]);
            }

            if (!p_184881_1_.field_71304_b.field_76327_a) {
               throw new CommandException("commands.debug.notStarted", new Object[0]);
            }

            long i = MinecraftServer.func_130071_aq();
            int j = p_184881_1_.func_71259_af();
            long k = i - this.field_147206_b;
            int l = j - this.field_147207_c;
            this.func_184894_a(k, l, p_184881_1_);
            p_184881_1_.field_71304_b.field_76327_a = false;
            func_152373_a(p_184881_2_, this, "commands.debug.stop", new Object[]{String.format("%.2f", (float)k / 1000.0F), l});
         }

      }
   }

   private void func_184894_a(long p_184894_1_, int p_184894_3_, MinecraftServer p_184894_4_) {
      File file1 = new File(p_184894_4_.func_71209_f("debug"), "profile-results-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + ".txt");
      file1.getParentFile().mkdirs();
      Writer writer = null;

      try {
         writer = new OutputStreamWriter(new FileOutputStream(file1), StandardCharsets.UTF_8);
         writer.write(this.func_184893_b(p_184894_1_, p_184894_3_, p_184894_4_));
      } catch (Throwable throwable) {
         field_147208_a.error("Could not save profiler results to {}", file1, throwable);
      } finally {
         IOUtils.closeQuietly(writer);
      }

   }

   private String func_184893_b(long p_184893_1_, int p_184893_3_, MinecraftServer p_184893_4_) {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append("---- Minecraft Profiler Results ----\n");
      stringbuilder.append("// ");
      stringbuilder.append(func_147203_d());
      stringbuilder.append("\n\n");
      stringbuilder.append("Time span: ").append(p_184893_1_).append(" ms\n");
      stringbuilder.append("Tick span: ").append(p_184893_3_).append(" ticks\n");
      stringbuilder.append("// This is approximately ").append(String.format("%.2f", (float)p_184893_3_ / ((float)p_184893_1_ / 1000.0F))).append(" ticks per second. It should be ").append((int)20).append(" ticks per second\n\n");
      stringbuilder.append("--- BEGIN PROFILE DUMP ---\n\n");
      this.func_184895_a(0, "root", stringbuilder, p_184893_4_);
      stringbuilder.append("--- END PROFILE DUMP ---\n\n");
      return stringbuilder.toString();
   }

   private void func_184895_a(int p_184895_1_, String p_184895_2_, StringBuilder p_184895_3_, MinecraftServer p_184895_4_) {
      List<Profiler.Result> list = p_184895_4_.field_71304_b.func_76321_b(p_184895_2_);
      if (list != null && list.size() >= 3) {
         for(int i = 1; i < list.size(); ++i) {
            Profiler.Result profiler$result = list.get(i);
            p_184895_3_.append(String.format("[%02d] ", p_184895_1_));

            for(int j = 0; j < p_184895_1_; ++j) {
               p_184895_3_.append("|   ");
            }

            p_184895_3_.append(profiler$result.field_76331_c).append(" - ").append(String.format("%.2f", profiler$result.field_76332_a)).append("%/").append(String.format("%.2f", profiler$result.field_76330_b)).append("%\n");
            if (!"unspecified".equals(profiler$result.field_76331_c)) {
               try {
                  this.func_184895_a(p_184895_1_ + 1, p_184895_2_ + "." + profiler$result.field_76331_c, p_184895_3_, p_184895_4_);
               } catch (Exception exception) {
                  p_184895_3_.append("[[ EXCEPTION ").append((Object)exception).append(" ]]");
               }
            }
         }

      }
   }

   private static String func_147203_d() {
      String[] astring = new String[]{"Shiny numbers!", "Am I not running fast enough? :(", "I'm working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it'll have more motivation to work faster! Poor server."};

      try {
         return astring[(int)(System.nanoTime() % (long)astring.length)];
      } catch (Throwable var2) {
         return "Witty comment unavailable :(";
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      return p_184883_3_.length == 1 ? func_71530_a(p_184883_3_, new String[]{"start", "stop"}) : Collections.emptyList();
   }
}
