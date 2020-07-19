package net.minecraft.client.resources;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

public class SimpleResource implements IResource {
   private final Map<String, IMetadataSection> field_110535_a = Maps.<String, IMetadataSection>newHashMap();
   private final String field_177242_b;
   private final ResourceLocation field_110533_b;
   private final InputStream field_110534_c;
   private final InputStream field_110531_d;
   private final MetadataSerializer field_110532_e;
   private boolean field_110529_f;
   private JsonObject field_110530_g;

   public SimpleResource(String p_i46090_1_, ResourceLocation p_i46090_2_, InputStream p_i46090_3_, InputStream p_i46090_4_, MetadataSerializer p_i46090_5_) {
      this.field_177242_b = p_i46090_1_;
      this.field_110533_b = p_i46090_2_;
      this.field_110534_c = p_i46090_3_;
      this.field_110531_d = p_i46090_4_;
      this.field_110532_e = p_i46090_5_;
   }

   public ResourceLocation func_177241_a() {
      return this.field_110533_b;
   }

   public InputStream func_110527_b() {
      return this.field_110534_c;
   }

   public boolean func_110528_c() {
      return this.field_110531_d != null;
   }

   @Nullable
   public <T extends IMetadataSection> T func_110526_a(String p_110526_1_) {
      if (!this.func_110528_c()) {
         return (T)null;
      } else {
         if (this.field_110530_g == null && !this.field_110529_f) {
            this.field_110529_f = true;
            BufferedReader bufferedreader = null;

            try {
               bufferedreader = new BufferedReader(new InputStreamReader(this.field_110531_d, StandardCharsets.UTF_8));
               this.field_110530_g = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
            } finally {
               IOUtils.closeQuietly((Reader)bufferedreader);
            }
         }

         T t = this.field_110535_a.get(p_110526_1_);
         if (t == null) {
            t = this.field_110532_e.func_110503_a(p_110526_1_, this.field_110530_g);
         }

         return t;
      }
   }

   public String func_177240_d() {
      return this.field_177242_b;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof SimpleResource)) {
         return false;
      } else {
         SimpleResource simpleresource = (SimpleResource)p_equals_1_;
         if (this.field_110533_b != null) {
            if (!this.field_110533_b.equals(simpleresource.field_110533_b)) {
               return false;
            }
         } else if (simpleresource.field_110533_b != null) {
            return false;
         }

         if (this.field_177242_b != null) {
            if (!this.field_177242_b.equals(simpleresource.field_177242_b)) {
               return false;
            }
         } else if (simpleresource.field_177242_b != null) {
            return false;
         }

         return true;
      }
   }

   public int hashCode() {
      int i = this.field_177242_b != null ? this.field_177242_b.hashCode() : 0;
      i = 31 * i + (this.field_110533_b != null ? this.field_110533_b.hashCode() : 0);
      return i;
   }

   public void close() throws IOException {
      this.field_110534_c.close();
      if (this.field_110531_d != null) {
         this.field_110531_d.close();
      }

   }
}
