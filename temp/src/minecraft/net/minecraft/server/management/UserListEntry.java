package net.minecraft.server.management;

import com.google.gson.JsonObject;

public class UserListEntry<T> {
   private final T field_152642_a;

   public UserListEntry(T p_i1146_1_) {
      this.field_152642_a = p_i1146_1_;
   }

   protected UserListEntry(T p_i1147_1_, JsonObject p_i1147_2_) {
      this.field_152642_a = p_i1147_1_;
   }

   T func_152640_f() {
      return this.field_152642_a;
   }

   boolean func_73682_e() {
      return false;
   }

   protected void func_152641_a(JsonObject p_152641_1_) {
   }
}
