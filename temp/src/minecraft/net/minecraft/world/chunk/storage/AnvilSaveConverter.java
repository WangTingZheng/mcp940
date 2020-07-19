package net.minecraft.world.chunk.storage;

import com.google.common.collect.Lists;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.init.Biomes;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveFormatOld;
import net.minecraft.world.storage.WorldInfo;
import net.minecraft.world.storage.WorldSummary;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilSaveConverter extends SaveFormatOld {
   private static final Logger field_151480_b = LogManager.getLogger();

   public AnvilSaveConverter(File p_i46649_1_, DataFixer p_i46649_2_) {
      super(p_i46649_1_, p_i46649_2_);
   }

   public String func_154333_a() {
      return "Anvil";
   }

   public List<WorldSummary> func_75799_b() throws AnvilConverterException {
      if (this.field_75808_a != null && this.field_75808_a.exists() && this.field_75808_a.isDirectory()) {
         List<WorldSummary> list = Lists.<WorldSummary>newArrayList();
         File[] afile = this.field_75808_a.listFiles();

         for(File file1 : afile) {
            if (file1.isDirectory()) {
               String s = file1.getName();
               WorldInfo worldinfo = this.func_75803_c(s);
               if (worldinfo != null && (worldinfo.func_76088_k() == 19132 || worldinfo.func_76088_k() == 19133)) {
                  boolean flag = worldinfo.func_76088_k() != this.func_75812_c();
                  String s1 = worldinfo.func_76065_j();
                  if (StringUtils.isEmpty(s1)) {
                     s1 = s;
                  }

                  long i = 0L;
                  list.add(new WorldSummary(worldinfo, s, s1, 0L, flag));
               }
            }
         }

         return list;
      } else {
         throw new AnvilConverterException(I18n.func_74838_a("selectWorld.load_folder_access"));
      }
   }

   protected int func_75812_c() {
      return 19133;
   }

   public void func_75800_d() {
      RegionFileCache.func_76551_a();
   }

   public ISaveHandler func_75804_a(String p_75804_1_, boolean p_75804_2_) {
      return new AnvilSaveHandler(this.field_75808_a, p_75804_1_, p_75804_2_, this.field_186354_b);
   }

   public boolean func_154334_a(String p_154334_1_) {
      WorldInfo worldinfo = this.func_75803_c(p_154334_1_);
      return worldinfo != null && worldinfo.func_76088_k() == 19132;
   }

   public boolean func_75801_b(String p_75801_1_) {
      WorldInfo worldinfo = this.func_75803_c(p_75801_1_);
      return worldinfo != null && worldinfo.func_76088_k() != this.func_75812_c();
   }

   public boolean func_75805_a(String p_75805_1_, IProgressUpdate p_75805_2_) {
      p_75805_2_.func_73718_a(0);
      List<File> list = Lists.<File>newArrayList();
      List<File> list1 = Lists.<File>newArrayList();
      List<File> list2 = Lists.<File>newArrayList();
      File file1 = new File(this.field_75808_a, p_75805_1_);
      File file2 = new File(file1, "DIM-1");
      File file3 = new File(file1, "DIM1");
      field_151480_b.info("Scanning folders...");
      this.func_75810_a(file1, list);
      if (file2.exists()) {
         this.func_75810_a(file2, list1);
      }

      if (file3.exists()) {
         this.func_75810_a(file3, list2);
      }

      int i = list.size() + list1.size() + list2.size();
      field_151480_b.info("Total conversion count is {}", (int)i);
      WorldInfo worldinfo = this.func_75803_c(p_75805_1_);
      BiomeProvider biomeprovider;
      if (worldinfo != null && worldinfo.func_76067_t() == WorldType.field_77138_c) {
         biomeprovider = new BiomeProviderSingle(Biomes.field_76772_c);
      } else {
         biomeprovider = new BiomeProvider(worldinfo);
      }

      this.func_75813_a(new File(file1, "region"), list, biomeprovider, 0, i, p_75805_2_);
      this.func_75813_a(new File(file2, "region"), list1, new BiomeProviderSingle(Biomes.field_76778_j), list.size(), i, p_75805_2_);
      this.func_75813_a(new File(file3, "region"), list2, new BiomeProviderSingle(Biomes.field_76779_k), list.size() + list1.size(), i, p_75805_2_);
      worldinfo.func_76078_e(19133);
      if (worldinfo.func_76067_t() == WorldType.field_77136_e) {
         worldinfo.func_76085_a(WorldType.field_77137_b);
      }

      this.func_75809_f(p_75805_1_);
      ISaveHandler isavehandler = this.func_75804_a(p_75805_1_, false);
      isavehandler.func_75761_a(worldinfo);
      return true;
   }

   private void func_75809_f(String p_75809_1_) {
      File file1 = new File(this.field_75808_a, p_75809_1_);
      if (!file1.exists()) {
         field_151480_b.warn("Unable to create level.dat_mcr backup");
      } else {
         File file2 = new File(file1, "level.dat");
         if (!file2.exists()) {
            field_151480_b.warn("Unable to create level.dat_mcr backup");
         } else {
            File file3 = new File(file1, "level.dat_mcr");
            if (!file2.renameTo(file3)) {
               field_151480_b.warn("Unable to create level.dat_mcr backup");
            }

         }
      }
   }

   private void func_75813_a(File p_75813_1_, Iterable<File> p_75813_2_, BiomeProvider p_75813_3_, int p_75813_4_, int p_75813_5_, IProgressUpdate p_75813_6_) {
      for(File file1 : p_75813_2_) {
         this.func_75811_a(p_75813_1_, file1, p_75813_3_, p_75813_4_, p_75813_5_, p_75813_6_);
         ++p_75813_4_;
         int i = (int)Math.round(100.0D * (double)p_75813_4_ / (double)p_75813_5_);
         p_75813_6_.func_73718_a(i);
      }

   }

   private void func_75811_a(File p_75811_1_, File p_75811_2_, BiomeProvider p_75811_3_, int p_75811_4_, int p_75811_5_, IProgressUpdate p_75811_6_) {
      try {
         String s = p_75811_2_.getName();
         RegionFile regionfile = new RegionFile(p_75811_2_);
         RegionFile regionfile1 = new RegionFile(new File(p_75811_1_, s.substring(0, s.length() - ".mcr".length()) + ".mca"));

         for(int i = 0; i < 32; ++i) {
            for(int j = 0; j < 32; ++j) {
               if (regionfile.func_76709_c(i, j) && !regionfile1.func_76709_c(i, j)) {
                  DataInputStream datainputstream = regionfile.func_76704_a(i, j);
                  if (datainputstream == null) {
                     field_151480_b.warn("Failed to fetch input stream");
                  } else {
                     NBTTagCompound nbttagcompound = CompressedStreamTools.func_74794_a(datainputstream);
                     datainputstream.close();
                     NBTTagCompound nbttagcompound1 = nbttagcompound.func_74775_l("Level");
                     ChunkLoader.AnvilConverterData chunkloader$anvilconverterdata = ChunkLoader.func_76691_a(nbttagcompound1);
                     NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                     NBTTagCompound nbttagcompound3 = new NBTTagCompound();
                     nbttagcompound2.func_74782_a("Level", nbttagcompound3);
                     ChunkLoader.func_76690_a(chunkloader$anvilconverterdata, nbttagcompound3, p_75811_3_);
                     DataOutputStream dataoutputstream = regionfile1.func_76710_b(i, j);
                     CompressedStreamTools.func_74800_a(nbttagcompound2, dataoutputstream);
                     dataoutputstream.close();
                  }
               }
            }

            int k = (int)Math.round(100.0D * (double)(p_75811_4_ * 1024) / (double)(p_75811_5_ * 1024));
            int l = (int)Math.round(100.0D * (double)((i + 1) * 32 + p_75811_4_ * 1024) / (double)(p_75811_5_ * 1024));
            if (l > k) {
               p_75811_6_.func_73718_a(l);
            }
         }

         regionfile.func_76708_c();
         regionfile1.func_76708_c();
      } catch (IOException ioexception) {
         ioexception.printStackTrace();
      }

   }

   private void func_75810_a(File p_75810_1_, Collection<File> p_75810_2_) {
      File file1 = new File(p_75810_1_, "region");
      File[] afile = file1.listFiles(new FilenameFilter() {
         public boolean accept(File p_accept_1_, String p_accept_2_) {
            return p_accept_2_.endsWith(".mcr");
         }
      });
      if (afile != null) {
         Collections.addAll(p_75810_2_, afile);
      }

   }
}
