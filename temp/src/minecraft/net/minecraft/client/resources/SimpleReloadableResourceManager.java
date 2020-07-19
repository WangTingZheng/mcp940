package net.minecraft.client.resources;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleReloadableResourceManager implements IReloadableResourceManager {
   private static final Logger field_147967_a = LogManager.getLogger();
   private static final Joiner field_130074_a = Joiner.on(", ");
   private final Map<String, FallbackResourceManager> field_110548_a = Maps.<String, FallbackResourceManager>newHashMap();
   private final List<IResourceManagerReloadListener> field_110546_b = Lists.<IResourceManagerReloadListener>newArrayList();
   private final Set<String> field_135057_d = Sets.<String>newLinkedHashSet();
   private final MetadataSerializer field_110547_c;

   public SimpleReloadableResourceManager(MetadataSerializer p_i1299_1_) {
      this.field_110547_c = p_i1299_1_;
   }

   public void func_110545_a(IResourcePack p_110545_1_) {
      for(String s : p_110545_1_.func_110587_b()) {
         this.field_135057_d.add(s);
         FallbackResourceManager fallbackresourcemanager = this.field_110548_a.get(s);
         if (fallbackresourcemanager == null) {
            fallbackresourcemanager = new FallbackResourceManager(this.field_110547_c);
            this.field_110548_a.put(s, fallbackresourcemanager);
         }

         fallbackresourcemanager.func_110538_a(p_110545_1_);
      }

   }

   public Set<String> func_135055_a() {
      return this.field_135057_d;
   }

   public IResource func_110536_a(ResourceLocation p_110536_1_) throws IOException {
      IResourceManager iresourcemanager = this.field_110548_a.get(p_110536_1_.func_110624_b());
      if (iresourcemanager != null) {
         return iresourcemanager.func_110536_a(p_110536_1_);
      } else {
         throw new FileNotFoundException(p_110536_1_.toString());
      }
   }

   public List<IResource> func_135056_b(ResourceLocation p_135056_1_) throws IOException {
      IResourceManager iresourcemanager = this.field_110548_a.get(p_135056_1_.func_110624_b());
      if (iresourcemanager != null) {
         return iresourcemanager.func_135056_b(p_135056_1_);
      } else {
         throw new FileNotFoundException(p_135056_1_.toString());
      }
   }

   private void func_110543_a() {
      this.field_110548_a.clear();
      this.field_135057_d.clear();
   }

   public void func_110541_a(List<IResourcePack> p_110541_1_) {
      this.func_110543_a();
      field_147967_a.info("Reloading ResourceManager: {}", (Object)field_130074_a.join(Iterables.transform(p_110541_1_, new Function<IResourcePack, String>() {
         public String apply(@Nullable IResourcePack p_apply_1_) {
            return p_apply_1_ == null ? "<NULL>" : p_apply_1_.func_130077_b();
         }
      })));

      for(IResourcePack iresourcepack : p_110541_1_) {
         this.func_110545_a(iresourcepack);
      }

      this.func_110544_b();
   }

   public void func_110542_a(IResourceManagerReloadListener p_110542_1_) {
      this.field_110546_b.add(p_110542_1_);
      p_110542_1_.func_110549_a(this);
   }

   private void func_110544_b() {
      for(IResourceManagerReloadListener iresourcemanagerreloadlistener : this.field_110546_b) {
         iresourcemanagerreloadlistener.func_110549_a(this);
      }

   }
}
