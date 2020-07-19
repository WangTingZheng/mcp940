package net.minecraft.server.management;

import com.google.gson.JsonObject;
import java.io.File;
import java.net.SocketAddress;

public class UserListIPBans extends UserList<String, UserListIPBansEntry> {
   public UserListIPBans(File p_i1490_1_) {
      super(p_i1490_1_);
   }

   protected UserListEntry<String> func_152682_a(JsonObject p_152682_1_) {
      return new UserListIPBansEntry(p_152682_1_);
   }

   public boolean func_152708_a(SocketAddress p_152708_1_) {
      String s = this.func_152707_c(p_152708_1_);
      return this.func_152692_d(s);
   }

   public UserListIPBansEntry func_152709_b(SocketAddress p_152709_1_) {
      String s = this.func_152707_c(p_152709_1_);
      return (UserListIPBansEntry)this.func_152683_b(s);
   }

   private String func_152707_c(SocketAddress p_152707_1_) {
      String s = p_152707_1_.toString();
      if (s.contains("/")) {
         s = s.substring(s.indexOf(47) + 1);
      }

      if (s.contains(":")) {
         s = s.substring(0, s.indexOf(58));
      }

      return s;
   }
}
