package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FallbackResourceManager implements IResourceManager {
   private static final Logger field_177246_b = LogManager.getLogger();
   protected final List<IResourcePack> field_110540_a = Lists.<IResourcePack>newArrayList();
   private final MetadataSerializer field_110539_b;

   public FallbackResourceManager(MetadataSerializer p_i1289_1_) {
      this.field_110539_b = p_i1289_1_;
   }

   public void func_110538_a(IResourcePack p_110538_1_) {
      this.field_110540_a.add(p_110538_1_);
   }

   public Set<String> func_135055_a() {
      return Collections.<String>emptySet();
   }

   public IResource func_110536_a(ResourceLocation p_110536_1_) throws IOException {
      this.func_188552_d(p_110536_1_);
      IResourcePack iresourcepack = null;
      ResourceLocation resourcelocation = func_110537_b(p_110536_1_);

      for(int i = this.field_110540_a.size() - 1; i >= 0; --i) {
         IResourcePack iresourcepack1 = this.field_110540_a.get(i);
         if (iresourcepack == null && iresourcepack1.func_110589_b(resourcelocation)) {
            iresourcepack = iresourcepack1;
         }

         if (iresourcepack1.func_110589_b(p_110536_1_)) {
            InputStream inputstream = null;
            if (iresourcepack != null) {
               inputstream = this.func_177245_a(resourcelocation, iresourcepack);
            }

            return new SimpleResource(iresourcepack1.func_130077_b(), p_110536_1_, this.func_177245_a(p_110536_1_, iresourcepack1), inputstream, this.field_110539_b);
         }
      }

      throw new FileNotFoundException(p_110536_1_.toString());
   }

   protected InputStream func_177245_a(ResourceLocation p_177245_1_, IResourcePack p_177245_2_) throws IOException {
      InputStream inputstream = p_177245_2_.func_110590_a(p_177245_1_);
      return (InputStream)(field_177246_b.isDebugEnabled() ? new FallbackResourceManager.InputStreamLeakedResourceLogger(inputstream, p_177245_1_, p_177245_2_.func_130077_b()) : inputstream);
   }

   private void func_188552_d(ResourceLocation p_188552_1_) throws IOException {
      if (p_188552_1_.func_110623_a().contains("..")) {
         throw new IOException("Invalid relative path to resource: " + p_188552_1_);
      }
   }

   public List<IResource> func_135056_b(ResourceLocation p_135056_1_) throws IOException {
      this.func_188552_d(p_135056_1_);
      List<IResource> list = Lists.<IResource>newArrayList();
      ResourceLocation resourcelocation = func_110537_b(p_135056_1_);

      for(IResourcePack iresourcepack : this.field_110540_a) {
         if (iresourcepack.func_110589_b(p_135056_1_)) {
            InputStream inputstream = iresourcepack.func_110589_b(resourcelocation) ? this.func_177245_a(resourcelocation, iresourcepack) : null;
            list.add(new SimpleResource(iresourcepack.func_130077_b(), p_135056_1_, this.func_177245_a(p_135056_1_, iresourcepack), inputstream, this.field_110539_b));
         }
      }

      if (list.isEmpty()) {
         throw new FileNotFoundException(p_135056_1_.toString());
      } else {
         return list;
      }
   }

   static ResourceLocation func_110537_b(ResourceLocation p_110537_0_) {
      return new ResourceLocation(p_110537_0_.func_110624_b(), p_110537_0_.func_110623_a() + ".mcmeta");
   }

   static class InputStreamLeakedResourceLogger extends InputStream {
      private final InputStream field_177330_a;
      private final String field_177328_b;
      private boolean field_177329_c;

      public InputStreamLeakedResourceLogger(InputStream p_i46093_1_, ResourceLocation p_i46093_2_, String p_i46093_3_) {
         this.field_177330_a = p_i46093_1_;
         ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
         (new Exception()).printStackTrace(new PrintStream(bytearrayoutputstream));
         this.field_177328_b = "Leaked resource: '" + p_i46093_2_ + "' loaded from pack: '" + p_i46093_3_ + "'\n" + bytearrayoutputstream;
      }

      public void close() throws IOException {
         this.field_177330_a.close();
         this.field_177329_c = true;
      }

      protected void finalize() throws Throwable {
         if (!this.field_177329_c) {
            FallbackResourceManager.field_177246_b.warn(this.field_177328_b);
         }

         super.finalize();
      }

      public int read() throws IOException {
         return this.field_177330_a.read();
      }
   }
}
