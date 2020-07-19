package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class UserListWhitelist extends UserList<GameProfile, UserListWhitelistEntry> {
   public UserListWhitelist(File p_i1132_1_) {
      super(p_i1132_1_);
   }

   protected UserListEntry<GameProfile> func_152682_a(JsonObject p_152682_1_) {
      return new UserListWhitelistEntry(p_152682_1_);
   }

   public String[] func_152685_a() {
      String[] astring = new String[this.func_152688_e().size()];
      int i = 0;

      for(UserListWhitelistEntry userlistwhitelistentry : this.func_152688_e().values()) {
         astring[i++] = ((GameProfile)userlistwhitelistentry.func_152640_f()).getName();
      }

      return astring;
   }

   protected String func_152681_a(GameProfile p_152681_1_) {
      return p_152681_1_.getId().toString();
   }

   public GameProfile func_152706_a(String p_152706_1_) {
      for(UserListWhitelistEntry userlistwhitelistentry : this.func_152688_e().values()) {
         if (p_152706_1_.equalsIgnoreCase(((GameProfile)userlistwhitelistentry.func_152640_f()).getName())) {
            return (GameProfile)userlistwhitelistentry.func_152640_f();
         }
      }

      return null;
   }
}
