package net.minecraft.client.resources.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.registry.RegistrySimple;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;

public class MetadataSerializer {
   private final IRegistry<String, MetadataSerializer.Registration<? extends IMetadataSection>> field_110508_a = new RegistrySimple<String, MetadataSerializer.Registration<? extends IMetadataSection>>();
   private final GsonBuilder field_110506_b = new GsonBuilder();
   private Gson field_110507_c;

   public MetadataSerializer() {
      this.field_110506_b.registerTypeHierarchyAdapter(ITextComponent.class, new ITextComponent.Serializer());
      this.field_110506_b.registerTypeHierarchyAdapter(Style.class, new Style.Serializer());
      this.field_110506_b.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
   }

   public <T extends IMetadataSection> void func_110504_a(IMetadataSectionSerializer<T> p_110504_1_, Class<T> p_110504_2_) {
      this.field_110508_a.func_82595_a(p_110504_1_.func_110483_a(), new MetadataSerializer.Registration(p_110504_1_, p_110504_2_));
      this.field_110506_b.registerTypeAdapter(p_110504_2_, p_110504_1_);
      this.field_110507_c = null;
   }

   public <T extends IMetadataSection> T func_110503_a(String p_110503_1_, JsonObject p_110503_2_) {
      if (p_110503_1_ == null) {
         throw new IllegalArgumentException("Metadata section name cannot be null");
      } else if (!p_110503_2_.has(p_110503_1_)) {
         return (T)null;
      } else if (!p_110503_2_.get(p_110503_1_).isJsonObject()) {
         throw new IllegalArgumentException("Invalid metadata for '" + p_110503_1_ + "' - expected object, found " + p_110503_2_.get(p_110503_1_));
      } else {
         MetadataSerializer.Registration<?> registration = (MetadataSerializer.Registration)this.field_110508_a.func_82594_a(p_110503_1_);
         if (registration == null) {
            throw new IllegalArgumentException("Don't know how to handle metadata section '" + p_110503_1_ + "'");
         } else {
            return (T)(this.func_110505_a().fromJson(p_110503_2_.getAsJsonObject(p_110503_1_), registration.field_110500_b));
         }
      }
   }

   private Gson func_110505_a() {
      if (this.field_110507_c == null) {
         this.field_110507_c = this.field_110506_b.create();
      }

      return this.field_110507_c;
   }

   class Registration<T extends IMetadataSection> {
      final IMetadataSectionSerializer<T> field_110502_a;
      final Class<T> field_110500_b;

      private Registration(IMetadataSectionSerializer<T> p_i1305_2_, Class<T> p_i1305_3_) {
         this.field_110502_a = p_i1305_2_;
         this.field_110500_b = p_i1305_3_;
      }
   }
}
