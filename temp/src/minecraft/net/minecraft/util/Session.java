package net.minecraft.util;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;

public class Session {
   private final String field_74286_b;
   private final String field_148257_b;
   private final String field_148258_c;
   private final Session.Type field_152429_d;

   public Session(String p_i1098_1_, String p_i1098_2_, String p_i1098_3_, String p_i1098_4_) {
      this.field_74286_b = p_i1098_1_;
      this.field_148257_b = p_i1098_2_;
      this.field_148258_c = p_i1098_3_;
      this.field_152429_d = Session.Type.func_152421_a(p_i1098_4_);
   }

   public String func_111286_b() {
      return "token:" + this.field_148258_c + ":" + this.field_148257_b;
   }

   public String func_148255_b() {
      return this.field_148257_b;
   }

   public String func_111285_a() {
      return this.field_74286_b;
   }

   public String func_148254_d() {
      return this.field_148258_c;
   }

   public GameProfile func_148256_e() {
      try {
         UUID uuid = UUIDTypeAdapter.fromString(this.func_148255_b());
         return new GameProfile(uuid, this.func_111285_a());
      } catch (IllegalArgumentException var2) {
         return new GameProfile((UUID)null, this.func_111285_a());
      }
   }

   public static enum Type {
      LEGACY("legacy"),
      MOJANG("mojang");

      private static final Map<String, Session.Type> field_152425_c = Maps.<String, Session.Type>newHashMap();
      private final String field_152426_d;

      private Type(String p_i1096_3_) {
         this.field_152426_d = p_i1096_3_;
      }

      @Nullable
      public static Session.Type func_152421_a(String p_152421_0_) {
         return field_152425_c.get(p_152421_0_.toLowerCase(Locale.ROOT));
      }

      static {
         for(Session.Type session$type : values()) {
            field_152425_c.put(session$type.field_152426_d, session$type);
         }

      }
   }
}
