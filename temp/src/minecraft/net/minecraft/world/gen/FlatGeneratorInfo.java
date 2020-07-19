package net.minecraft.world.gen;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;

public class FlatGeneratorInfo {
   private final List<FlatLayerInfo> field_82655_a = Lists.<FlatLayerInfo>newArrayList();
   private final Map<String, Map<String, String>> field_82653_b = Maps.<String, Map<String, String>>newHashMap();
   private int field_82654_c;

   public int func_82648_a() {
      return this.field_82654_c;
   }

   public void func_82647_a(int p_82647_1_) {
      this.field_82654_c = p_82647_1_;
   }

   public Map<String, Map<String, String>> func_82644_b() {
      return this.field_82653_b;
   }

   public List<FlatLayerInfo> func_82650_c() {
      return this.field_82655_a;
   }

   public void func_82645_d() {
      int i = 0;

      for(FlatLayerInfo flatlayerinfo : this.field_82655_a) {
         flatlayerinfo.func_82660_d(i);
         i += flatlayerinfo.func_82657_a();
      }

   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append((int)3);
      stringbuilder.append(";");

      for(int i = 0; i < this.field_82655_a.size(); ++i) {
         if (i > 0) {
            stringbuilder.append(",");
         }

         stringbuilder.append(this.field_82655_a.get(i));
      }

      stringbuilder.append(";");
      stringbuilder.append(this.field_82654_c);
      if (this.field_82653_b.isEmpty()) {
         stringbuilder.append(";");
      } else {
         stringbuilder.append(";");
         int k = 0;

         for(Entry<String, Map<String, String>> entry : this.field_82653_b.entrySet()) {
            if (k++ > 0) {
               stringbuilder.append(",");
            }

            stringbuilder.append(((String)entry.getKey()).toLowerCase(Locale.ROOT));
            Map<String, String> map = (Map)entry.getValue();
            if (!map.isEmpty()) {
               stringbuilder.append("(");
               int j = 0;

               for(Entry<String, String> entry1 : map.entrySet()) {
                  if (j++ > 0) {
                     stringbuilder.append(" ");
                  }

                  stringbuilder.append(entry1.getKey());
                  stringbuilder.append("=");
                  stringbuilder.append(entry1.getValue());
               }

               stringbuilder.append(")");
            }
         }
      }

      return stringbuilder.toString();
   }

   private static FlatLayerInfo func_180715_a(int p_180715_0_, String p_180715_1_, int p_180715_2_) {
      String[] astring = p_180715_0_ >= 3 ? p_180715_1_.split("\\*", 2) : p_180715_1_.split("x", 2);
      int i = 1;
      int j = 0;
      if (astring.length == 2) {
         try {
            i = Integer.parseInt(astring[0]);
            if (p_180715_2_ + i >= 256) {
               i = 256 - p_180715_2_;
            }

            if (i < 0) {
               i = 0;
            }
         } catch (Throwable var8) {
            return null;
         }
      }

      Block block;
      try {
         String s = astring[astring.length - 1];
         if (p_180715_0_ < 3) {
            astring = s.split(":", 2);
            if (astring.length > 1) {
               j = Integer.parseInt(astring[1]);
            }

            block = Block.func_149729_e(Integer.parseInt(astring[0]));
         } else {
            astring = s.split(":", 3);
            block = astring.length > 1 ? Block.func_149684_b(astring[0] + ":" + astring[1]) : null;
            if (block != null) {
               j = astring.length > 2 ? Integer.parseInt(astring[2]) : 0;
            } else {
               block = Block.func_149684_b(astring[0]);
               if (block != null) {
                  j = astring.length > 1 ? Integer.parseInt(astring[1]) : 0;
               }
            }

            if (block == null) {
               return null;
            }
         }

         if (block == Blocks.field_150350_a) {
            j = 0;
         }

         if (j < 0 || j > 15) {
            j = 0;
         }
      } catch (Throwable var9) {
         return null;
      }

      FlatLayerInfo flatlayerinfo = new FlatLayerInfo(p_180715_0_, i, block, j);
      flatlayerinfo.func_82660_d(p_180715_2_);
      return flatlayerinfo;
   }

   private static List<FlatLayerInfo> func_180716_a(int p_180716_0_, String p_180716_1_) {
      if (p_180716_1_ != null && p_180716_1_.length() >= 1) {
         List<FlatLayerInfo> list = Lists.<FlatLayerInfo>newArrayList();
         String[] astring = p_180716_1_.split(",");
         int i = 0;

         for(String s : astring) {
            FlatLayerInfo flatlayerinfo = func_180715_a(p_180716_0_, s, i);
            if (flatlayerinfo == null) {
               return null;
            }

            list.add(flatlayerinfo);
            i += flatlayerinfo.func_82657_a();
         }

         return list;
      } else {
         return null;
      }
   }

   public static FlatGeneratorInfo func_82651_a(String p_82651_0_) {
      if (p_82651_0_ == null) {
         return func_82649_e();
      } else {
         String[] astring = p_82651_0_.split(";", -1);
         int i = astring.length == 1 ? 0 : MathHelper.func_82715_a(astring[0], 0);
         if (i >= 0 && i <= 3) {
            FlatGeneratorInfo flatgeneratorinfo = new FlatGeneratorInfo();
            int j = astring.length == 1 ? 0 : 1;
            List<FlatLayerInfo> list = func_180716_a(i, astring[j++]);
            if (list != null && !list.isEmpty()) {
               flatgeneratorinfo.func_82650_c().addAll(list);
               flatgeneratorinfo.func_82645_d();
               int k = Biome.func_185362_a(Biomes.field_76772_c);
               if (i > 0 && astring.length > j) {
                  k = MathHelper.func_82715_a(astring[j++], k);
               }

               flatgeneratorinfo.func_82647_a(k);
               if (i > 0 && astring.length > j) {
                  String[] astring1 = astring[j++].toLowerCase(Locale.ROOT).split(",");

                  for(String s : astring1) {
                     String[] astring2 = s.split("\\(", 2);
                     Map<String, String> map = Maps.<String, String>newHashMap();
                     if (!astring2[0].isEmpty()) {
                        flatgeneratorinfo.func_82644_b().put(astring2[0], map);
                        if (astring2.length > 1 && astring2[1].endsWith(")") && astring2[1].length() > 1) {
                           String[] astring3 = astring2[1].substring(0, astring2[1].length() - 1).split(" ");

                           for(String s1 : astring3) {
                              String[] astring4 = s1.split("=", 2);
                              if (astring4.length == 2) {
                                 map.put(astring4[0], astring4[1]);
                              }
                           }
                        }
                     }
                  }
               } else {
                  flatgeneratorinfo.func_82644_b().put("village", Maps.newHashMap());
               }

               return flatgeneratorinfo;
            } else {
               return func_82649_e();
            }
         } else {
            return func_82649_e();
         }
      }
   }

   public static FlatGeneratorInfo func_82649_e() {
      FlatGeneratorInfo flatgeneratorinfo = new FlatGeneratorInfo();
      flatgeneratorinfo.func_82647_a(Biome.func_185362_a(Biomes.field_76772_c));
      flatgeneratorinfo.func_82650_c().add(new FlatLayerInfo(1, Blocks.field_150357_h));
      flatgeneratorinfo.func_82650_c().add(new FlatLayerInfo(2, Blocks.field_150346_d));
      flatgeneratorinfo.func_82650_c().add(new FlatLayerInfo(1, Blocks.field_150349_c));
      flatgeneratorinfo.func_82645_d();
      flatgeneratorinfo.func_82644_b().put("village", Maps.newHashMap());
      return flatgeneratorinfo;
   }
}
