package net.minecraft.world.gen.structure.template;

import com.google.common.collect.Maps;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import org.apache.commons.io.IOUtils;

public class TemplateManager {
   private final Map<String, Template> field_186240_a = Maps.<String, Template>newHashMap();
   private final String field_186241_b;
   private final DataFixer field_191154_c;

   public TemplateManager(String p_i47239_1_, DataFixer p_i47239_2_) {
      this.field_186241_b = p_i47239_1_;
      this.field_191154_c = p_i47239_2_;
   }

   public Template func_186237_a(@Nullable MinecraftServer p_186237_1_, ResourceLocation p_186237_2_) {
      Template template = this.func_189942_b(p_186237_1_, p_186237_2_);
      if (template == null) {
         template = new Template();
         this.field_186240_a.put(p_186237_2_.func_110623_a(), template);
      }

      return template;
   }

   @Nullable
   public Template func_189942_b(@Nullable MinecraftServer p_189942_1_, ResourceLocation p_189942_2_) {
      String s = p_189942_2_.func_110623_a();
      if (this.field_186240_a.containsKey(s)) {
         return this.field_186240_a.get(s);
      } else {
         if (p_189942_1_ == null) {
            this.func_186236_a(p_189942_2_);
         } else {
            this.func_186235_b(p_189942_2_);
         }

         return this.field_186240_a.containsKey(s) ? (Template)this.field_186240_a.get(s) : null;
      }
   }

   public boolean func_186235_b(ResourceLocation p_186235_1_) {
      String s = p_186235_1_.func_110623_a();
      File file1 = new File(this.field_186241_b, s + ".nbt");
      if (!file1.exists()) {
         return this.func_186236_a(p_186235_1_);
      } else {
         InputStream inputstream = null;

         boolean flag;
         try {
            inputstream = new FileInputStream(file1);
            this.func_186239_a(s, inputstream);
            return true;
         } catch (Throwable var10) {
            flag = false;
         } finally {
            IOUtils.closeQuietly(inputstream);
         }

         return flag;
      }
   }

   private boolean func_186236_a(ResourceLocation p_186236_1_) {
      String s = p_186236_1_.func_110624_b();
      String s1 = p_186236_1_.func_110623_a();
      InputStream inputstream = null;

      boolean flag;
      try {
         inputstream = MinecraftServer.class.getResourceAsStream("/assets/" + s + "/structures/" + s1 + ".nbt");
         this.func_186239_a(s1, inputstream);
         return true;
      } catch (Throwable var10) {
         flag = false;
      } finally {
         IOUtils.closeQuietly(inputstream);
      }

      return flag;
   }

   private void func_186239_a(String p_186239_1_, InputStream p_186239_2_) throws IOException {
      NBTTagCompound nbttagcompound = CompressedStreamTools.func_74796_a(p_186239_2_);
      if (!nbttagcompound.func_150297_b("DataVersion", 99)) {
         nbttagcompound.func_74768_a("DataVersion", 500);
      }

      Template template = new Template();
      template.func_186256_b(this.field_191154_c.func_188257_a(FixTypes.STRUCTURE, nbttagcompound));
      this.field_186240_a.put(p_186239_1_, template);
   }

   public boolean func_186238_c(@Nullable MinecraftServer p_186238_1_, ResourceLocation p_186238_2_) {
      String s = p_186238_2_.func_110623_a();
      if (p_186238_1_ != null && this.field_186240_a.containsKey(s)) {
         File file1 = new File(this.field_186241_b);
         if (!file1.exists()) {
            if (!file1.mkdirs()) {
               return false;
            }
         } else if (!file1.isDirectory()) {
            return false;
         }

         File file2 = new File(file1, s + ".nbt");
         Template template = this.field_186240_a.get(s);
         OutputStream outputstream = null;

         boolean flag;
         try {
            NBTTagCompound nbttagcompound = template.func_189552_a(new NBTTagCompound());
            outputstream = new FileOutputStream(file2);
            CompressedStreamTools.func_74799_a(nbttagcompound, outputstream);
            return true;
         } catch (Throwable var13) {
            flag = false;
         } finally {
            IOUtils.closeQuietly(outputstream);
         }

         return flag;
      } else {
         return false;
      }
   }

   public void func_189941_a(ResourceLocation p_189941_1_) {
      this.field_186240_a.remove(p_189941_1_.func_110623_a());
   }
}
