package net.minecraft.client.resources;

import java.io.File;
import net.minecraft.util.ResourceLocation;

public class ResourceIndexFolder extends ResourceIndex {
   private final File field_188548_a;

   public ResourceIndexFolder(File p_i46540_1_) {
      this.field_188548_a = p_i46540_1_;
   }

   public File func_188547_a(ResourceLocation p_188547_1_) {
      return new File(this.field_188548_a, p_188547_1_.toString().replace(':', '/'));
   }

   public File func_188546_a() {
      return new File(this.field_188548_a, "pack.mcmeta");
   }
}
