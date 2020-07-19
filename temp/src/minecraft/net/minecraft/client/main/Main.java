package net.minecraft.client.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.properties.PropertyMap.Serializer;
import java.io.File;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.List;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.client.Minecraft;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.Session;

public class Main {
   public static void main(String[] p_main_0_) {
      OptionParser optionparser = new OptionParser();
      optionparser.allowsUnrecognizedOptions();
      optionparser.accepts("demo");
      optionparser.accepts("fullscreen");
      optionparser.accepts("checkGlErrors");
      OptionSpec<String> optionspec = optionparser.accepts("server").withRequiredArg();
      OptionSpec<Integer> optionspec1 = optionparser.accepts("port").withRequiredArg().<Integer>ofType(Integer.class).defaultsTo(Integer.valueOf(25565));
      OptionSpec<File> optionspec2 = optionparser.accepts("gameDir").withRequiredArg().<File>ofType(File.class).defaultsTo(new File("."));
      OptionSpec<File> optionspec3 = optionparser.accepts("assetsDir").withRequiredArg().<File>ofType(File.class);
      OptionSpec<File> optionspec4 = optionparser.accepts("resourcePackDir").withRequiredArg().<File>ofType(File.class);
      OptionSpec<String> optionspec5 = optionparser.accepts("proxyHost").withRequiredArg();
      OptionSpec<Integer> optionspec6 = optionparser.accepts("proxyPort").withRequiredArg().defaultsTo("8080").<Integer>ofType(Integer.class);
      OptionSpec<String> optionspec7 = optionparser.accepts("proxyUser").withRequiredArg();
      OptionSpec<String> optionspec8 = optionparser.accepts("proxyPass").withRequiredArg();
      OptionSpec<String> optionspec9 = optionparser.accepts("username").withRequiredArg().defaultsTo("Player" + Minecraft.func_71386_F() % 1000L);
      OptionSpec<String> optionspec10 = optionparser.accepts("uuid").withRequiredArg();
      OptionSpec<String> optionspec11 = optionparser.accepts("accessToken").withRequiredArg().required();
      OptionSpec<String> optionspec12 = optionparser.accepts("version").withRequiredArg().required();
      OptionSpec<Integer> optionspec13 = optionparser.accepts("width").withRequiredArg().<Integer>ofType(Integer.class).defaultsTo(Integer.valueOf(854));
      OptionSpec<Integer> optionspec14 = optionparser.accepts("height").withRequiredArg().<Integer>ofType(Integer.class).defaultsTo(Integer.valueOf(480));
      OptionSpec<String> optionspec15 = optionparser.accepts("userProperties").withRequiredArg().defaultsTo("{}");
      OptionSpec<String> optionspec16 = optionparser.accepts("profileProperties").withRequiredArg().defaultsTo("{}");
      OptionSpec<String> optionspec17 = optionparser.accepts("assetIndex").withRequiredArg();
      OptionSpec<String> optionspec18 = optionparser.accepts("userType").withRequiredArg().defaultsTo("legacy");
      OptionSpec<String> optionspec19 = optionparser.accepts("versionType").withRequiredArg().defaultsTo("release");
      OptionSpec<String> optionspec20 = optionparser.nonOptions();
      OptionSet optionset = optionparser.parse(p_main_0_);
      List<String> list = optionset.valuesOf(optionspec20);
      if (!list.isEmpty()) {
         System.out.println("Completely ignored arguments: " + list);
      }

      String s = (String)optionset.valueOf(optionspec5);
      Proxy proxy = Proxy.NO_PROXY;
      if (s != null) {
         try {
            proxy = new Proxy(Type.SOCKS, new InetSocketAddress(s, ((Integer)optionset.valueOf(optionspec6)).intValue()));
         } catch (Exception var48) {
            ;
         }
      }

      final String s1 = (String)optionset.valueOf(optionspec7);
      final String s2 = (String)optionset.valueOf(optionspec8);
      if (!proxy.equals(Proxy.NO_PROXY) && func_110121_a(s1) && func_110121_a(s2)) {
         Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(s1, s2.toCharArray());
            }
         });
      }

      int i = ((Integer)optionset.valueOf(optionspec13)).intValue();
      int j = ((Integer)optionset.valueOf(optionspec14)).intValue();
      boolean flag = optionset.has("fullscreen");
      boolean flag1 = optionset.has("checkGlErrors");
      boolean flag2 = optionset.has("demo");
      String s3 = (String)optionset.valueOf(optionspec12);
      Gson gson = (new GsonBuilder()).registerTypeAdapter(PropertyMap.class, new Serializer()).create();
      PropertyMap propertymap = (PropertyMap)JsonUtils.func_188178_a(gson, (String)optionset.valueOf(optionspec15), PropertyMap.class);
      PropertyMap propertymap1 = (PropertyMap)JsonUtils.func_188178_a(gson, (String)optionset.valueOf(optionspec16), PropertyMap.class);
      String s4 = (String)optionset.valueOf(optionspec19);
      File file1 = (File)optionset.valueOf(optionspec2);
      File file2 = optionset.has(optionspec3) ? (File)optionset.valueOf(optionspec3) : new File(file1, "assets/");
      File file3 = optionset.has(optionspec4) ? (File)optionset.valueOf(optionspec4) : new File(file1, "resourcepacks/");
      String s5 = optionset.has(optionspec10) ? (String)optionspec10.value(optionset) : (String)optionspec9.value(optionset);
      String s6 = optionset.has(optionspec17) ? (String)optionspec17.value(optionset) : null;
      String s7 = (String)optionset.valueOf(optionspec);
      Integer integer = (Integer)optionset.valueOf(optionspec1);
      Session session = new Session(optionspec9.value(optionset), s5, optionspec11.value(optionset), optionspec18.value(optionset));
      GameConfiguration gameconfiguration = new GameConfiguration(new GameConfiguration.UserInformation(session, propertymap, propertymap1, proxy), new GameConfiguration.DisplayInformation(i, j, flag, flag1), new GameConfiguration.FolderInformation(file1, file3, file2, s6), new GameConfiguration.GameInformation(flag2, s3, s4), new GameConfiguration.ServerInformation(s7, integer.intValue()));
      Runtime.getRuntime().addShutdownHook(new Thread("Client Shutdown Thread") {
         public void run() {
            Minecraft.func_71363_D();
         }
      });
      Thread.currentThread().setName("Client thread");
      (new Minecraft(gameconfiguration)).func_99999_d();
   }

   private static boolean func_110121_a(String p_110121_0_) {
      return p_110121_0_ != null && !p_110121_0_.isEmpty();
   }
}
