package net.minecraft.item;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapData;

public class ItemMap extends ItemMapBase {
   protected ItemMap() {
      this.func_77627_a(true);
   }

   public static ItemStack func_190906_a(World p_190906_0_, double p_190906_1_, double p_190906_3_, byte p_190906_5_, boolean p_190906_6_, boolean p_190906_7_) {
      ItemStack itemstack = new ItemStack(Items.field_151098_aY, 1, p_190906_0_.func_72841_b("map"));
      String s = "map_" + itemstack.func_77960_j();
      MapData mapdata = new MapData(s);
      p_190906_0_.func_72823_a(s, mapdata);
      mapdata.field_76197_d = p_190906_5_;
      mapdata.func_176054_a(p_190906_1_, p_190906_3_, mapdata.field_76197_d);
      mapdata.field_76200_c = (byte)p_190906_0_.field_73011_w.func_186058_p().func_186068_a();
      mapdata.field_186210_e = p_190906_6_;
      mapdata.field_191096_f = p_190906_7_;
      mapdata.func_76185_a();
      return itemstack;
   }

   @Nullable
   public static MapData func_150912_a(int p_150912_0_, World p_150912_1_) {
      String s = "map_" + p_150912_0_;
      return (MapData)p_150912_1_.func_72943_a(MapData.class, s);
   }

   @Nullable
   public MapData func_77873_a(ItemStack p_77873_1_, World p_77873_2_) {
      String s = "map_" + p_77873_1_.func_77960_j();
      MapData mapdata = (MapData)p_77873_2_.func_72943_a(MapData.class, s);
      if (mapdata == null && !p_77873_2_.field_72995_K) {
         p_77873_1_.func_77964_b(p_77873_2_.func_72841_b("map"));
         s = "map_" + p_77873_1_.func_77960_j();
         mapdata = new MapData(s);
         mapdata.field_76197_d = 3;
         mapdata.func_176054_a((double)p_77873_2_.func_72912_H().func_76079_c(), (double)p_77873_2_.func_72912_H().func_76074_e(), mapdata.field_76197_d);
         mapdata.field_76200_c = (byte)p_77873_2_.field_73011_w.func_186058_p().func_186068_a();
         mapdata.func_76185_a();
         p_77873_2_.func_72823_a(s, mapdata);
      }

      return mapdata;
   }

   public void func_77872_a(World p_77872_1_, Entity p_77872_2_, MapData p_77872_3_) {
      if (p_77872_1_.field_73011_w.func_186058_p().func_186068_a() == p_77872_3_.field_76200_c && p_77872_2_ instanceof EntityPlayer) {
         int i = 1 << p_77872_3_.field_76197_d;
         int j = p_77872_3_.field_76201_a;
         int k = p_77872_3_.field_76199_b;
         int l = MathHelper.func_76128_c(p_77872_2_.field_70165_t - (double)j) / i + 64;
         int i1 = MathHelper.func_76128_c(p_77872_2_.field_70161_v - (double)k) / i + 64;
         int j1 = 128 / i;
         if (p_77872_1_.field_73011_w.func_177495_o()) {
            j1 /= 2;
         }

         MapData.MapInfo mapdata$mapinfo = p_77872_3_.func_82568_a((EntityPlayer)p_77872_2_);
         ++mapdata$mapinfo.field_82569_d;
         boolean flag = false;

         for(int k1 = l - j1 + 1; k1 < l + j1; ++k1) {
            if ((k1 & 15) == (mapdata$mapinfo.field_82569_d & 15) || flag) {
               flag = false;
               double d0 = 0.0D;

               for(int l1 = i1 - j1 - 1; l1 < i1 + j1; ++l1) {
                  if (k1 >= 0 && l1 >= -1 && k1 < 128 && l1 < 128) {
                     int i2 = k1 - l;
                     int j2 = l1 - i1;
                     boolean flag1 = i2 * i2 + j2 * j2 > (j1 - 2) * (j1 - 2);
                     int k2 = (j / i + k1 - 64) * i;
                     int l2 = (k / i + l1 - 64) * i;
                     Multiset<MapColor> multiset = HashMultiset.<MapColor>create();
                     Chunk chunk = p_77872_1_.func_175726_f(new BlockPos(k2, 0, l2));
                     if (!chunk.func_76621_g()) {
                        int i3 = k2 & 15;
                        int j3 = l2 & 15;
                        int k3 = 0;
                        double d1 = 0.0D;
                        if (p_77872_1_.field_73011_w.func_177495_o()) {
                           int l3 = k2 + l2 * 231871;
                           l3 = l3 * l3 * 31287121 + l3 * 11;
                           if ((l3 >> 20 & 1) == 0) {
                              multiset.add(Blocks.field_150346_d.func_176223_P().func_177226_a(BlockDirt.field_176386_a, BlockDirt.DirtType.DIRT).func_185909_g(p_77872_1_, BlockPos.field_177992_a), 10);
                           } else {
                              multiset.add(Blocks.field_150348_b.func_176223_P().func_177226_a(BlockStone.field_176247_a, BlockStone.EnumType.STONE).func_185909_g(p_77872_1_, BlockPos.field_177992_a), 100);
                           }

                           d1 = 100.0D;
                        } else {
                           BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                           for(int i4 = 0; i4 < i; ++i4) {
                              for(int j4 = 0; j4 < i; ++j4) {
                                 int k4 = chunk.func_76611_b(i4 + i3, j4 + j3) + 1;
                                 IBlockState iblockstate = Blocks.field_150350_a.func_176223_P();
                                 if (k4 <= 1) {
                                    iblockstate = Blocks.field_150357_h.func_176223_P();
                                 } else {
                                    label175: {
                                       while(true) {
                                          --k4;
                                          iblockstate = chunk.func_186032_a(i4 + i3, k4, j4 + j3);
                                          blockpos$mutableblockpos.func_181079_c((chunk.field_76635_g << 4) + i4 + i3, k4, (chunk.field_76647_h << 4) + j4 + j3);
                                          if (iblockstate.func_185909_g(p_77872_1_, blockpos$mutableblockpos) != MapColor.field_151660_b || k4 <= 0) {
                                             break;
                                          }
                                       }

                                       if (k4 > 0 && iblockstate.func_185904_a().func_76224_d()) {
                                          int l4 = k4 - 1;

                                          while(true) {
                                             IBlockState iblockstate1 = chunk.func_186032_a(i4 + i3, l4--, j4 + j3);
                                             ++k3;
                                             if (l4 <= 0 || !iblockstate1.func_185904_a().func_76224_d()) {
                                                break label175;
                                             }
                                          }
                                       }
                                    }
                                 }

                                 d1 += (double)k4 / (double)(i * i);
                                 multiset.add(iblockstate.func_185909_g(p_77872_1_, blockpos$mutableblockpos));
                              }
                           }
                        }

                        k3 = k3 / (i * i);
                        double d2 = (d1 - d0) * 4.0D / (double)(i + 4) + ((double)(k1 + l1 & 1) - 0.5D) * 0.4D;
                        int i5 = 1;
                        if (d2 > 0.6D) {
                           i5 = 2;
                        }

                        if (d2 < -0.6D) {
                           i5 = 0;
                        }

                        MapColor mapcolor = (MapColor)Iterables.getFirst(Multisets.copyHighestCountFirst(multiset), MapColor.field_151660_b);
                        if (mapcolor == MapColor.field_151662_n) {
                           d2 = (double)k3 * 0.1D + (double)(k1 + l1 & 1) * 0.2D;
                           i5 = 1;
                           if (d2 < 0.5D) {
                              i5 = 2;
                           }

                           if (d2 > 0.9D) {
                              i5 = 0;
                           }
                        }

                        d0 = d1;
                        if (l1 >= 0 && i2 * i2 + j2 * j2 < j1 * j1 && (!flag1 || (k1 + l1 & 1) != 0)) {
                           byte b0 = p_77872_3_.field_76198_e[k1 + l1 * 128];
                           byte b1 = (byte)(mapcolor.field_76290_q * 4 + i5);
                           if (b0 != b1) {
                              p_77872_3_.field_76198_e[k1 + l1 * 128] = b1;
                              p_77872_3_.func_176053_a(k1, l1);
                              flag = true;
                           }
                        }
                     }
                  }
               }
            }
         }

      }
   }

   public static void func_190905_a(World p_190905_0_, ItemStack p_190905_1_) {
      if (p_190905_1_.func_77973_b() == Items.field_151098_aY) {
         MapData mapdata = Items.field_151098_aY.func_77873_a(p_190905_1_, p_190905_0_);
         if (mapdata != null) {
            if (p_190905_0_.field_73011_w.func_186058_p().func_186068_a() == mapdata.field_76200_c) {
               int i = 1 << mapdata.field_76197_d;
               int j = mapdata.field_76201_a;
               int k = mapdata.field_76199_b;
               Biome[] abiome = p_190905_0_.func_72959_q().func_76931_a((Biome[])null, (j / i - 64) * i, (k / i - 64) * i, 128 * i, 128 * i, false);

               for(int l = 0; l < 128; ++l) {
                  for(int i1 = 0; i1 < 128; ++i1) {
                     int j1 = l * i;
                     int k1 = i1 * i;
                     Biome biome = abiome[j1 + k1 * 128 * i];
                     MapColor mapcolor = MapColor.field_151660_b;
                     int l1 = 3;
                     int i2 = 8;
                     if (l > 0 && i1 > 0 && l < 127 && i1 < 127) {
                        if (abiome[(l - 1) * i + (i1 - 1) * i * 128 * i].func_185355_j() >= 0.0F) {
                           --i2;
                        }

                        if (abiome[(l - 1) * i + (i1 + 1) * i * 128 * i].func_185355_j() >= 0.0F) {
                           --i2;
                        }

                        if (abiome[(l - 1) * i + i1 * i * 128 * i].func_185355_j() >= 0.0F) {
                           --i2;
                        }

                        if (abiome[(l + 1) * i + (i1 - 1) * i * 128 * i].func_185355_j() >= 0.0F) {
                           --i2;
                        }

                        if (abiome[(l + 1) * i + (i1 + 1) * i * 128 * i].func_185355_j() >= 0.0F) {
                           --i2;
                        }

                        if (abiome[(l + 1) * i + i1 * i * 128 * i].func_185355_j() >= 0.0F) {
                           --i2;
                        }

                        if (abiome[l * i + (i1 - 1) * i * 128 * i].func_185355_j() >= 0.0F) {
                           --i2;
                        }

                        if (abiome[l * i + (i1 + 1) * i * 128 * i].func_185355_j() >= 0.0F) {
                           --i2;
                        }

                        if (biome.func_185355_j() < 0.0F) {
                           mapcolor = MapColor.field_151676_q;
                           if (i2 > 7 && i1 % 2 == 0) {
                              l1 = (l + (int)(MathHelper.func_76126_a((float)i1 + 0.0F) * 7.0F)) / 8 % 5;
                              if (l1 == 3) {
                                 l1 = 1;
                              } else if (l1 == 4) {
                                 l1 = 0;
                              }
                           } else if (i2 > 7) {
                              mapcolor = MapColor.field_151660_b;
                           } else if (i2 > 5) {
                              l1 = 1;
                           } else if (i2 > 3) {
                              l1 = 0;
                           } else if (i2 > 1) {
                              l1 = 0;
                           }
                        } else if (i2 > 0) {
                           mapcolor = MapColor.field_151650_B;
                           if (i2 > 3) {
                              l1 = 1;
                           } else {
                              l1 = 3;
                           }
                        }
                     }

                     if (mapcolor != MapColor.field_151660_b) {
                        mapdata.field_76198_e[l + i1 * 128] = (byte)(mapcolor.field_76290_q * 4 + l1);
                        mapdata.func_176053_a(l, i1);
                     }
                  }
               }

            }
         }
      }
   }

   public void func_77663_a(ItemStack p_77663_1_, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
      if (!p_77663_2_.field_72995_K) {
         MapData mapdata = this.func_77873_a(p_77663_1_, p_77663_2_);
         if (p_77663_3_ instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)p_77663_3_;
            mapdata.func_76191_a(entityplayer, p_77663_1_);
         }

         if (p_77663_5_ || p_77663_3_ instanceof EntityPlayer && ((EntityPlayer)p_77663_3_).func_184592_cb() == p_77663_1_) {
            this.func_77872_a(p_77663_2_, p_77663_3_, mapdata);
         }

      }
   }

   @Nullable
   public Packet<?> func_150911_c(ItemStack p_150911_1_, World p_150911_2_, EntityPlayer p_150911_3_) {
      return this.func_77873_a(p_150911_1_, p_150911_2_).func_176052_a(p_150911_1_, p_150911_2_, p_150911_3_);
   }

   public void func_77622_d(ItemStack p_77622_1_, World p_77622_2_, EntityPlayer p_77622_3_) {
      NBTTagCompound nbttagcompound = p_77622_1_.func_77978_p();
      if (nbttagcompound != null) {
         if (nbttagcompound.func_150297_b("map_scale_direction", 99)) {
            func_185063_a(p_77622_1_, p_77622_2_, nbttagcompound.func_74762_e("map_scale_direction"));
            nbttagcompound.func_82580_o("map_scale_direction");
         } else if (nbttagcompound.func_74767_n("map_tracking_position")) {
            func_185064_b(p_77622_1_, p_77622_2_);
            nbttagcompound.func_82580_o("map_tracking_position");
         }
      }

   }

   protected static void func_185063_a(ItemStack p_185063_0_, World p_185063_1_, int p_185063_2_) {
      MapData mapdata = Items.field_151098_aY.func_77873_a(p_185063_0_, p_185063_1_);
      p_185063_0_.func_77964_b(p_185063_1_.func_72841_b("map"));
      MapData mapdata1 = new MapData("map_" + p_185063_0_.func_77960_j());
      if (mapdata != null) {
         mapdata1.field_76197_d = (byte)MathHelper.func_76125_a(mapdata.field_76197_d + p_185063_2_, 0, 4);
         mapdata1.field_186210_e = mapdata.field_186210_e;
         mapdata1.func_176054_a((double)mapdata.field_76201_a, (double)mapdata.field_76199_b, mapdata1.field_76197_d);
         mapdata1.field_76200_c = mapdata.field_76200_c;
         mapdata1.func_76185_a();
         p_185063_1_.func_72823_a("map_" + p_185063_0_.func_77960_j(), mapdata1);
      }

   }

   protected static void func_185064_b(ItemStack p_185064_0_, World p_185064_1_) {
      MapData mapdata = Items.field_151098_aY.func_77873_a(p_185064_0_, p_185064_1_);
      p_185064_0_.func_77964_b(p_185064_1_.func_72841_b("map"));
      MapData mapdata1 = new MapData("map_" + p_185064_0_.func_77960_j());
      mapdata1.field_186210_e = true;
      if (mapdata != null) {
         mapdata1.field_76201_a = mapdata.field_76201_a;
         mapdata1.field_76199_b = mapdata.field_76199_b;
         mapdata1.field_76197_d = mapdata.field_76197_d;
         mapdata1.field_76200_c = mapdata.field_76200_c;
         mapdata1.func_76185_a();
         p_185064_1_.func_72823_a("map_" + p_185064_0_.func_77960_j(), mapdata1);
      }

   }

   public void func_77624_a(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<String> p_77624_3_, ITooltipFlag p_77624_4_) {
      if (p_77624_4_.func_194127_a()) {
         MapData mapdata = p_77624_2_ == null ? null : this.func_77873_a(p_77624_1_, p_77624_2_);
         if (mapdata != null) {
            p_77624_3_.add(I18n.func_74837_a("filled_map.scale", 1 << mapdata.field_76197_d));
            p_77624_3_.add(I18n.func_74837_a("filled_map.level", mapdata.field_76197_d, Integer.valueOf(4)));
         } else {
            p_77624_3_.add(I18n.func_74838_a("filled_map.unknown"));
         }
      }

   }

   public static int func_190907_h(ItemStack p_190907_0_) {
      NBTTagCompound nbttagcompound = p_190907_0_.func_179543_a("display");
      if (nbttagcompound != null && nbttagcompound.func_150297_b("MapColor", 99)) {
         int i = nbttagcompound.func_74762_e("MapColor");
         return -16777216 | i & 16777215;
      } else {
         return -12173266;
      }
   }
}
