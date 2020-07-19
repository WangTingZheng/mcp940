package net.minecraft.client.main;

import com.mojang.authlib.properties.PropertyMap;
import java.io.File;
import java.net.Proxy;
import javax.annotation.Nullable;
import net.minecraft.client.resources.ResourceIndex;
import net.minecraft.client.resources.ResourceIndexFolder;
import net.minecraft.util.Session;

public class GameConfiguration {
   public final GameConfiguration.UserInformation field_178745_a;
   public final GameConfiguration.DisplayInformation field_178743_b;
   public final GameConfiguration.FolderInformation field_178744_c;
   public final GameConfiguration.GameInformation field_178741_d;
   public final GameConfiguration.ServerInformation field_178742_e;

   public GameConfiguration(GameConfiguration.UserInformation p_i45491_1_, GameConfiguration.DisplayInformation p_i45491_2_, GameConfiguration.FolderInformation p_i45491_3_, GameConfiguration.GameInformation p_i45491_4_, GameConfiguration.ServerInformation p_i45491_5_) {
      this.field_178745_a = p_i45491_1_;
      this.field_178743_b = p_i45491_2_;
      this.field_178744_c = p_i45491_3_;
      this.field_178741_d = p_i45491_4_;
      this.field_178742_e = p_i45491_5_;
   }

   public static class DisplayInformation {
      public final int field_178764_a;
      public final int field_178762_b;
      public final boolean field_178763_c;
      public final boolean field_178761_d;

      public DisplayInformation(int p_i45490_1_, int p_i45490_2_, boolean p_i45490_3_, boolean p_i45490_4_) {
         this.field_178764_a = p_i45490_1_;
         this.field_178762_b = p_i45490_2_;
         this.field_178763_c = p_i45490_3_;
         this.field_178761_d = p_i45490_4_;
      }
   }

   public static class FolderInformation {
      public final File field_178760_a;
      public final File field_178758_b;
      public final File field_178759_c;
      public final String field_178757_d;

      public FolderInformation(File p_i45489_1_, File p_i45489_2_, File p_i45489_3_, @Nullable String p_i45489_4_) {
         this.field_178760_a = p_i45489_1_;
         this.field_178758_b = p_i45489_2_;
         this.field_178759_c = p_i45489_3_;
         this.field_178757_d = p_i45489_4_;
      }

      public ResourceIndex func_187052_a() {
         return (ResourceIndex)(this.field_178757_d == null ? new ResourceIndexFolder(this.field_178759_c) : new ResourceIndex(this.field_178759_c, this.field_178757_d));
      }
   }

   public static class GameInformation {
      public final boolean field_178756_a;
      public final String field_178755_b;
      public final String field_187053_c;

      public GameInformation(boolean p_i46801_1_, String p_i46801_2_, String p_i46801_3_) {
         this.field_178756_a = p_i46801_1_;
         this.field_178755_b = p_i46801_2_;
         this.field_187053_c = p_i46801_3_;
      }
   }

   public static class ServerInformation {
      public final String field_178754_a;
      public final int field_178753_b;

      public ServerInformation(String p_i45487_1_, int p_i45487_2_) {
         this.field_178754_a = p_i45487_1_;
         this.field_178753_b = p_i45487_2_;
      }
   }

   public static class UserInformation {
      public final Session field_178752_a;
      public final PropertyMap field_178750_b;
      public final PropertyMap field_181172_c;
      public final Proxy field_178751_c;

      public UserInformation(Session p_i46375_1_, PropertyMap p_i46375_2_, PropertyMap p_i46375_3_, Proxy p_i46375_4_) {
         this.field_178752_a = p_i46375_1_;
         this.field_178750_b = p_i46375_2_;
         this.field_181172_c = p_i46375_3_;
         this.field_178751_c = p_i46375_4_;
      }
   }
}
