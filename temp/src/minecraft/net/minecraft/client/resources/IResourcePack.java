package net.minecraft.client.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;

public interface IResourcePack {
   InputStream func_110590_a(ResourceLocation var1) throws IOException;

   boolean func_110589_b(ResourceLocation var1);

   Set<String> func_110587_b();

   @Nullable
   <T extends IMetadataSection> T func_135058_a(MetadataSerializer var1, String var2) throws IOException;

   BufferedImage func_110586_a() throws IOException;

   String func_130077_b();
}
