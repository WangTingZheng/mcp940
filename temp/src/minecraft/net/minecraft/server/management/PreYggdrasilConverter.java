package net.minecraft.server.management;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PreYggdrasilConverter {
   private static final Logger field_152732_e = LogManager.getLogger();
   public static final File field_152728_a = new File("banned-ips.txt");
   public static final File field_152729_b = new File("banned-players.txt");
   public static final File field_152730_c = new File("ops.txt");
   public static final File field_152731_d = new File("white-list.txt");

   private static void func_152717_a(MinecraftServer p_152717_0_, Collection<String> p_152717_1_, ProfileLookupCallback p_152717_2_) {
      String[] astring = (String[])Iterators.toArray(Iterators.filter(p_152717_1_.iterator(), new Predicate<String>() {
         public boolean apply(@Nullable String p_apply_1_) {
            return !StringUtils.func_151246_b(p_apply_1_);
         }
      }), String.class);
      if (p_152717_0_.func_71266_T()) {
         p_152717_0_.func_152359_aw().findProfilesByNames(astring, Agent.MINECRAFT, p_152717_2_);
      } else {
         for(String s : astring) {
            UUID uuid = EntityPlayer.func_146094_a(new GameProfile((UUID)null, s));
            GameProfile gameprofile = new GameProfile(uuid, s);
            p_152717_2_.onProfileLookupSucceeded(gameprofile);
         }
      }

   }

   public static String func_187473_a(final MinecraftServer p_187473_0_, String p_187473_1_) {
      if (!StringUtils.func_151246_b(p_187473_1_) && p_187473_1_.length() <= 16) {
         GameProfile gameprofile = p_187473_0_.func_152358_ax().func_152655_a(p_187473_1_);
         if (gameprofile != null && gameprofile.getId() != null) {
            return gameprofile.getId().toString();
         } else if (!p_187473_0_.func_71264_H() && p_187473_0_.func_71266_T()) {
            final List<GameProfile> list = Lists.<GameProfile>newArrayList();
            ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
               public void onProfileLookupSucceeded(GameProfile p_onProfileLookupSucceeded_1_) {
                  p_187473_0_.func_152358_ax().func_152649_a(p_onProfileLookupSucceeded_1_);
                  list.add(p_onProfileLookupSucceeded_1_);
               }

               public void onProfileLookupFailed(GameProfile p_onProfileLookupFailed_1_, Exception p_onProfileLookupFailed_2_) {
                  PreYggdrasilConverter.field_152732_e.warn("Could not lookup user whitelist entry for {}", p_onProfileLookupFailed_1_.getName(), p_onProfileLookupFailed_2_);
               }
            };
            func_152717_a(p_187473_0_, Lists.newArrayList(p_187473_1_), profilelookupcallback);
            return !list.isEmpty() && ((GameProfile)list.get(0)).getId() != null ? ((GameProfile)list.get(0)).getId().toString() : "";
         } else {
            return EntityPlayer.func_146094_a(new GameProfile((UUID)null, p_187473_1_)).toString();
         }
      } else {
         return p_187473_1_;
      }
   }
}
