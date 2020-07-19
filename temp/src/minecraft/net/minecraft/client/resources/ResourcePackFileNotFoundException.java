package net.minecraft.client.resources;

import java.io.File;
import java.io.FileNotFoundException;

public class ResourcePackFileNotFoundException extends FileNotFoundException {
   public ResourcePackFileNotFoundException(File p_i1294_1_, String p_i1294_2_) {
      super(String.format("'%s' in ResourcePack '%s'", p_i1294_2_, p_i1294_1_));
   }
}
