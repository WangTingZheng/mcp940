package net.minecraft.client.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;

public class LegacyV2Adapter implements IResourcePack {
   private final IResourcePack field_191383_a;

   public LegacyV2Adapter(IResourcePack p_i47182_1_) {
      this.field_191383_a = p_i47182_1_;
   }

   public InputStream func_110590_a(ResourceLocation p_110590_1_) throws IOException {
      return this.field_191383_a.func_110590_a(this.func_191382_c(p_110590_1_));
   }

   private ResourceLocation func_191382_c(ResourceLocation p_191382_1_) {
      String s = p_191382_1_.func_110623_a();
      if (!"lang/swg_de.lang".equals(s) && s.startsWith("lang/") && s.endsWith(".lang")) {
         int i = s.indexOf(95);
         if (i != -1) {
            final String s1 = s.substring(0, i + 1) + s.substring(i + 1, s.indexOf(46, i)).toUpperCase() + ".lang";
            return new ResourceLocation(p_191382_1_.func_110624_b(), "") {
               public String func_110623_a() {
                  return s1;
               }
            };
         }
      }

      return p_191382_1_;
   }

   public boolean func_110589_b(ResourceLocation p_110589_1_) {
      return this.field_191383_a.func_110589_b(this.func_191382_c(p_110589_1_));
   }

   public Set<String> func_110587_b() {
      return this.field_191383_a.func_110587_b();
   }

   @Nullable
   public <T extends IMetadataSection> T func_135058_a(MetadataSerializer p_135058_1_, String p_135058_2_) throws IOException {
      return (T)this.field_191383_a.func_135058_a(p_135058_1_, p_135058_2_);
   }

   public BufferedImage func_110586_a() throws IOException {
      return this.field_191383_a.func_110586_a();
   }

   public String func_130077_b() {
      return this.field_191383_a.func_130077_b();
   }
}
