package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class UserListOps extends UserList<GameProfile, UserListOpsEntry> {
   public UserListOps(File p_i1152_1_) {
      super(p_i1152_1_);
   }

   protected UserListEntry<GameProfile> func_152682_a(JsonObject p_152682_1_) {
      return new UserListOpsEntry(p_152682_1_);
   }

   public String[] func_152685_a() {
      String[] astring = new String[this.func_152688_e().size()];
      int i = 0;

      for(UserListOpsEntry userlistopsentry : this.func_152688_e().values()) {
         astring[i++] = ((GameProfile)userlistopsentry.func_152640_f()).getName();
      }

      return astring;
   }

   public int func_187452_a(GameProfile p_187452_1_) {
      UserListOpsEntry userlistopsentry = (UserListOpsEntry)this.func_152683_b(p_187452_1_);
      return userlistopsentry != null ? userlistopsentry.func_152644_a() : 0;
   }

   public boolean func_183026_b(GameProfile p_183026_1_) {
      UserListOpsEntry userlistopsentry = (UserListOpsEntry)this.func_152683_b(p_183026_1_);
      return userlistopsentry != null ? userlistopsentry.func_183024_b() : false;
   }

   protected String func_152681_a(GameProfile p_152681_1_) {
      return p_152681_1_.getId().toString();
   }

   public GameProfile func_152700_a(String p_152700_1_) {
      for(UserListOpsEntry userlistopsentry : this.func_152688_e().values()) {
         if (p_152700_1_.equalsIgnoreCase(((GameProfile)userlistopsentry.func_152640_f()).getName())) {
            return (GameProfile)userlistopsentry.func_152640_f();
         }
      }

      return null;
   }
}
