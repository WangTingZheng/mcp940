package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class UserListBans extends UserList<GameProfile, UserListBansEntry> {
   public UserListBans(File p_i1138_1_) {
      super(p_i1138_1_);
   }

   protected UserListEntry<GameProfile> func_152682_a(JsonObject p_152682_1_) {
      return new UserListBansEntry(p_152682_1_);
   }

   public boolean func_152702_a(GameProfile p_152702_1_) {
      return this.func_152692_d(p_152702_1_);
   }

   public String[] func_152685_a() {
      String[] astring = new String[this.func_152688_e().size()];
      int i = 0;

      for(UserListBansEntry userlistbansentry : this.func_152688_e().values()) {
         astring[i++] = ((GameProfile)userlistbansentry.func_152640_f()).getName();
      }

      return astring;
   }

   protected String func_152681_a(GameProfile p_152681_1_) {
      return p_152681_1_.getId().toString();
   }

   public GameProfile func_152703_a(String p_152703_1_) {
      for(UserListBansEntry userlistbansentry : this.func_152688_e().values()) {
         if (p_152703_1_.equalsIgnoreCase(((GameProfile)userlistbansentry.func_152640_f()).getName())) {
            return (GameProfile)userlistbansentry.func_152640_f();
         }
      }

      return null;
   }
}
