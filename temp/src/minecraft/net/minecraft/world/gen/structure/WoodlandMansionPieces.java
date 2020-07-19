package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class WoodlandMansionPieces {
   public static void func_191153_a() {
      MapGenStructureIO.func_143031_a(WoodlandMansionPieces.MansionTemplate.class, "WMP");
   }

   public static void func_191152_a(TemplateManager p_191152_0_, BlockPos p_191152_1_, Rotation p_191152_2_, List<WoodlandMansionPieces.MansionTemplate> p_191152_3_, Random p_191152_4_) {
      WoodlandMansionPieces.Grid woodlandmansionpieces$grid = new WoodlandMansionPieces.Grid(p_191152_4_);
      WoodlandMansionPieces.Placer woodlandmansionpieces$placer = new WoodlandMansionPieces.Placer(p_191152_0_, p_191152_4_);
      woodlandmansionpieces$placer.func_191125_a(p_191152_1_, p_191152_2_, p_191152_3_, woodlandmansionpieces$grid);
   }

   static class FirstFloor extends WoodlandMansionPieces.RoomCollection {
      private FirstFloor() {
      }

      public String func_191104_a(Random p_191104_1_) {
         return "1x1_a" + (p_191104_1_.nextInt(5) + 1);
      }

      public String func_191099_b(Random p_191099_1_) {
         return "1x1_as" + (p_191099_1_.nextInt(4) + 1);
      }

      public String func_191100_a(Random p_191100_1_, boolean p_191100_2_) {
         return "1x2_a" + (p_191100_1_.nextInt(9) + 1);
      }

      public String func_191098_b(Random p_191098_1_, boolean p_191098_2_) {
         return "1x2_b" + (p_191098_1_.nextInt(5) + 1);
      }

      public String func_191102_c(Random p_191102_1_) {
         return "1x2_s" + (p_191102_1_.nextInt(2) + 1);
      }

      public String func_191101_d(Random p_191101_1_) {
         return "2x2_a" + (p_191101_1_.nextInt(4) + 1);
      }

      public String func_191103_e(Random p_191103_1_) {
         return "2x2_s1";
      }
   }

   static class Grid {
      private final Random field_191117_a;
      private final WoodlandMansionPieces.SimpleGrid field_191118_b;
      private final WoodlandMansionPieces.SimpleGrid field_191119_c;
      private final WoodlandMansionPieces.SimpleGrid[] field_191120_d;
      private final int field_191121_e;
      private final int field_191122_f;

      public Grid(Random p_i47362_1_) {
         this.field_191117_a = p_i47362_1_;
         int i = 11;
         this.field_191121_e = 7;
         this.field_191122_f = 4;
         this.field_191118_b = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
         this.field_191118_b.func_191142_a(this.field_191121_e, this.field_191122_f, this.field_191121_e + 1, this.field_191122_f + 1, 3);
         this.field_191118_b.func_191142_a(this.field_191121_e - 1, this.field_191122_f, this.field_191121_e - 1, this.field_191122_f + 1, 2);
         this.field_191118_b.func_191142_a(this.field_191121_e + 2, this.field_191122_f - 2, this.field_191121_e + 3, this.field_191122_f + 3, 5);
         this.field_191118_b.func_191142_a(this.field_191121_e + 1, this.field_191122_f - 2, this.field_191121_e + 1, this.field_191122_f - 1, 1);
         this.field_191118_b.func_191142_a(this.field_191121_e + 1, this.field_191122_f + 2, this.field_191121_e + 1, this.field_191122_f + 3, 1);
         this.field_191118_b.func_191144_a(this.field_191121_e - 1, this.field_191122_f - 1, 1);
         this.field_191118_b.func_191144_a(this.field_191121_e - 1, this.field_191122_f + 2, 1);
         this.field_191118_b.func_191142_a(0, 0, 11, 1, 5);
         this.field_191118_b.func_191142_a(0, 9, 11, 11, 5);
         this.func_191110_a(this.field_191118_b, this.field_191121_e, this.field_191122_f - 2, EnumFacing.WEST, 6);
         this.func_191110_a(this.field_191118_b, this.field_191121_e, this.field_191122_f + 3, EnumFacing.WEST, 6);
         this.func_191110_a(this.field_191118_b, this.field_191121_e - 2, this.field_191122_f - 1, EnumFacing.WEST, 3);
         this.func_191110_a(this.field_191118_b, this.field_191121_e - 2, this.field_191122_f + 2, EnumFacing.WEST, 3);

         while(this.func_191111_a(this.field_191118_b)) {
            ;
         }

         this.field_191120_d = new WoodlandMansionPieces.SimpleGrid[3];
         this.field_191120_d[0] = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
         this.field_191120_d[1] = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
         this.field_191120_d[2] = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
         this.func_191116_a(this.field_191118_b, this.field_191120_d[0]);
         this.func_191116_a(this.field_191118_b, this.field_191120_d[1]);
         this.field_191120_d[0].func_191142_a(this.field_191121_e + 1, this.field_191122_f, this.field_191121_e + 1, this.field_191122_f + 1, 8388608);
         this.field_191120_d[1].func_191142_a(this.field_191121_e + 1, this.field_191122_f, this.field_191121_e + 1, this.field_191122_f + 1, 8388608);
         this.field_191119_c = new WoodlandMansionPieces.SimpleGrid(this.field_191118_b.field_191149_b, this.field_191118_b.field_191150_c, 5);
         this.func_191115_b();
         this.func_191116_a(this.field_191119_c, this.field_191120_d[2]);
      }

      public static boolean func_191109_a(WoodlandMansionPieces.SimpleGrid p_191109_0_, int p_191109_1_, int p_191109_2_) {
         int i = p_191109_0_.func_191145_a(p_191109_1_, p_191109_2_);
         return i == 1 || i == 2 || i == 3 || i == 4;
      }

      public boolean func_191114_a(WoodlandMansionPieces.SimpleGrid p_191114_1_, int p_191114_2_, int p_191114_3_, int p_191114_4_, int p_191114_5_) {
         return (this.field_191120_d[p_191114_4_].func_191145_a(p_191114_2_, p_191114_3_) & '\uffff') == p_191114_5_;
      }

      @Nullable
      public EnumFacing func_191113_b(WoodlandMansionPieces.SimpleGrid p_191113_1_, int p_191113_2_, int p_191113_3_, int p_191113_4_, int p_191113_5_) {
         for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL.func_179516_a()) {
            if (this.func_191114_a(p_191113_1_, p_191113_2_ + enumfacing.func_82601_c(), p_191113_3_ + enumfacing.func_82599_e(), p_191113_4_, p_191113_5_)) {
               return enumfacing;
            }
         }

         return null;
      }

      private void func_191110_a(WoodlandMansionPieces.SimpleGrid p_191110_1_, int p_191110_2_, int p_191110_3_, EnumFacing p_191110_4_, int p_191110_5_) {
         if (p_191110_5_ > 0) {
            p_191110_1_.func_191144_a(p_191110_2_, p_191110_3_, 1);
            p_191110_1_.func_191141_a(p_191110_2_ + p_191110_4_.func_82601_c(), p_191110_3_ + p_191110_4_.func_82599_e(), 0, 1);

            for(int i = 0; i < 8; ++i) {
               EnumFacing enumfacing = EnumFacing.func_176731_b(this.field_191117_a.nextInt(4));
               if (enumfacing != p_191110_4_.func_176734_d() && (enumfacing != EnumFacing.EAST || !this.field_191117_a.nextBoolean())) {
                  int j = p_191110_2_ + p_191110_4_.func_82601_c();
                  int k = p_191110_3_ + p_191110_4_.func_82599_e();
                  if (p_191110_1_.func_191145_a(j + enumfacing.func_82601_c(), k + enumfacing.func_82599_e()) == 0 && p_191110_1_.func_191145_a(j + enumfacing.func_82601_c() * 2, k + enumfacing.func_82599_e() * 2) == 0) {
                     this.func_191110_a(p_191110_1_, p_191110_2_ + p_191110_4_.func_82601_c() + enumfacing.func_82601_c(), p_191110_3_ + p_191110_4_.func_82599_e() + enumfacing.func_82599_e(), enumfacing, p_191110_5_ - 1);
                     break;
                  }
               }
            }

            EnumFacing enumfacing1 = p_191110_4_.func_176746_e();
            EnumFacing enumfacing2 = p_191110_4_.func_176735_f();
            p_191110_1_.func_191141_a(p_191110_2_ + enumfacing1.func_82601_c(), p_191110_3_ + enumfacing1.func_82599_e(), 0, 2);
            p_191110_1_.func_191141_a(p_191110_2_ + enumfacing2.func_82601_c(), p_191110_3_ + enumfacing2.func_82599_e(), 0, 2);
            p_191110_1_.func_191141_a(p_191110_2_ + p_191110_4_.func_82601_c() + enumfacing1.func_82601_c(), p_191110_3_ + p_191110_4_.func_82599_e() + enumfacing1.func_82599_e(), 0, 2);
            p_191110_1_.func_191141_a(p_191110_2_ + p_191110_4_.func_82601_c() + enumfacing2.func_82601_c(), p_191110_3_ + p_191110_4_.func_82599_e() + enumfacing2.func_82599_e(), 0, 2);
            p_191110_1_.func_191141_a(p_191110_2_ + p_191110_4_.func_82601_c() * 2, p_191110_3_ + p_191110_4_.func_82599_e() * 2, 0, 2);
            p_191110_1_.func_191141_a(p_191110_2_ + enumfacing1.func_82601_c() * 2, p_191110_3_ + enumfacing1.func_82599_e() * 2, 0, 2);
            p_191110_1_.func_191141_a(p_191110_2_ + enumfacing2.func_82601_c() * 2, p_191110_3_ + enumfacing2.func_82599_e() * 2, 0, 2);
         }
      }

      private boolean func_191111_a(WoodlandMansionPieces.SimpleGrid p_191111_1_) {
         boolean flag = false;

         for(int i = 0; i < p_191111_1_.field_191150_c; ++i) {
            for(int j = 0; j < p_191111_1_.field_191149_b; ++j) {
               if (p_191111_1_.func_191145_a(j, i) == 0) {
                  int k = 0;
                  k = k + (func_191109_a(p_191111_1_, j + 1, i) ? 1 : 0);
                  k = k + (func_191109_a(p_191111_1_, j - 1, i) ? 1 : 0);
                  k = k + (func_191109_a(p_191111_1_, j, i + 1) ? 1 : 0);
                  k = k + (func_191109_a(p_191111_1_, j, i - 1) ? 1 : 0);
                  if (k >= 3) {
                     p_191111_1_.func_191144_a(j, i, 2);
                     flag = true;
                  } else if (k == 2) {
                     int l = 0;
                     l = l + (func_191109_a(p_191111_1_, j + 1, i + 1) ? 1 : 0);
                     l = l + (func_191109_a(p_191111_1_, j - 1, i + 1) ? 1 : 0);
                     l = l + (func_191109_a(p_191111_1_, j + 1, i - 1) ? 1 : 0);
                     l = l + (func_191109_a(p_191111_1_, j - 1, i - 1) ? 1 : 0);
                     if (l <= 1) {
                        p_191111_1_.func_191144_a(j, i, 2);
                        flag = true;
                     }
                  }
               }
            }
         }

         return flag;
      }

      private void func_191115_b() {
         List<Tuple<Integer, Integer>> list = Lists.<Tuple<Integer, Integer>>newArrayList();
         WoodlandMansionPieces.SimpleGrid woodlandmansionpieces$simplegrid = this.field_191120_d[1];

         for(int i = 0; i < this.field_191119_c.field_191150_c; ++i) {
            for(int j = 0; j < this.field_191119_c.field_191149_b; ++j) {
               int k = woodlandmansionpieces$simplegrid.func_191145_a(j, i);
               int l = k & 983040;
               if (l == 131072 && (k & 2097152) == 2097152) {
                  list.add(new Tuple(j, i));
               }
            }
         }

         if (list.isEmpty()) {
            this.field_191119_c.func_191142_a(0, 0, this.field_191119_c.field_191149_b, this.field_191119_c.field_191150_c, 5);
         } else {
            Tuple<Integer, Integer> tuple = (Tuple)list.get(this.field_191117_a.nextInt(list.size()));
            int l1 = woodlandmansionpieces$simplegrid.func_191145_a(((Integer)tuple.func_76341_a()).intValue(), ((Integer)tuple.func_76340_b()).intValue());
            woodlandmansionpieces$simplegrid.func_191144_a(((Integer)tuple.func_76341_a()).intValue(), ((Integer)tuple.func_76340_b()).intValue(), l1 | 4194304);
            EnumFacing enumfacing1 = this.func_191113_b(this.field_191118_b, ((Integer)tuple.func_76341_a()).intValue(), ((Integer)tuple.func_76340_b()).intValue(), 1, l1 & '\uffff');
            int i2 = ((Integer)tuple.func_76341_a()).intValue() + enumfacing1.func_82601_c();
            int i1 = ((Integer)tuple.func_76340_b()).intValue() + enumfacing1.func_82599_e();

            for(int j1 = 0; j1 < this.field_191119_c.field_191150_c; ++j1) {
               for(int k1 = 0; k1 < this.field_191119_c.field_191149_b; ++k1) {
                  if (!func_191109_a(this.field_191118_b, k1, j1)) {
                     this.field_191119_c.func_191144_a(k1, j1, 5);
                  } else if (k1 == ((Integer)tuple.func_76341_a()).intValue() && j1 == ((Integer)tuple.func_76340_b()).intValue()) {
                     this.field_191119_c.func_191144_a(k1, j1, 3);
                  } else if (k1 == i2 && j1 == i1) {
                     this.field_191119_c.func_191144_a(k1, j1, 3);
                     this.field_191120_d[2].func_191144_a(k1, j1, 8388608);
                  }
               }
            }

            List<EnumFacing> list1 = Lists.<EnumFacing>newArrayList();

            for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL.func_179516_a()) {
               if (this.field_191119_c.func_191145_a(i2 + enumfacing.func_82601_c(), i1 + enumfacing.func_82599_e()) == 0) {
                  list1.add(enumfacing);
               }
            }

            if (list1.isEmpty()) {
               this.field_191119_c.func_191142_a(0, 0, this.field_191119_c.field_191149_b, this.field_191119_c.field_191150_c, 5);
               woodlandmansionpieces$simplegrid.func_191144_a(((Integer)tuple.func_76341_a()).intValue(), ((Integer)tuple.func_76340_b()).intValue(), l1);
            } else {
               EnumFacing enumfacing2 = list1.get(this.field_191117_a.nextInt(list1.size()));
               this.func_191110_a(this.field_191119_c, i2 + enumfacing2.func_82601_c(), i1 + enumfacing2.func_82599_e(), enumfacing2, 4);

               while(this.func_191111_a(this.field_191119_c)) {
                  ;
               }

            }
         }
      }

      private void func_191116_a(WoodlandMansionPieces.SimpleGrid p_191116_1_, WoodlandMansionPieces.SimpleGrid p_191116_2_) {
         List<Tuple<Integer, Integer>> list = Lists.<Tuple<Integer, Integer>>newArrayList();

         for(int i = 0; i < p_191116_1_.field_191150_c; ++i) {
            for(int j = 0; j < p_191116_1_.field_191149_b; ++j) {
               if (p_191116_1_.func_191145_a(j, i) == 2) {
                  list.add(new Tuple(j, i));
               }
            }
         }

         Collections.shuffle(list, this.field_191117_a);
         int k3 = 10;

         for(Tuple<Integer, Integer> tuple : list) {
            int k = ((Integer)tuple.func_76341_a()).intValue();
            int l = ((Integer)tuple.func_76340_b()).intValue();
            if (p_191116_2_.func_191145_a(k, l) == 0) {
               int i1 = k;
               int j1 = k;
               int k1 = l;
               int l1 = l;
               int i2 = 65536;
               if (p_191116_2_.func_191145_a(k + 1, l) == 0 && p_191116_2_.func_191145_a(k, l + 1) == 0 && p_191116_2_.func_191145_a(k + 1, l + 1) == 0 && p_191116_1_.func_191145_a(k + 1, l) == 2 && p_191116_1_.func_191145_a(k, l + 1) == 2 && p_191116_1_.func_191145_a(k + 1, l + 1) == 2) {
                  j1 = k + 1;
                  l1 = l + 1;
                  i2 = 262144;
               } else if (p_191116_2_.func_191145_a(k - 1, l) == 0 && p_191116_2_.func_191145_a(k, l + 1) == 0 && p_191116_2_.func_191145_a(k - 1, l + 1) == 0 && p_191116_1_.func_191145_a(k - 1, l) == 2 && p_191116_1_.func_191145_a(k, l + 1) == 2 && p_191116_1_.func_191145_a(k - 1, l + 1) == 2) {
                  i1 = k - 1;
                  l1 = l + 1;
                  i2 = 262144;
               } else if (p_191116_2_.func_191145_a(k - 1, l) == 0 && p_191116_2_.func_191145_a(k, l - 1) == 0 && p_191116_2_.func_191145_a(k - 1, l - 1) == 0 && p_191116_1_.func_191145_a(k - 1, l) == 2 && p_191116_1_.func_191145_a(k, l - 1) == 2 && p_191116_1_.func_191145_a(k - 1, l - 1) == 2) {
                  i1 = k - 1;
                  k1 = l - 1;
                  i2 = 262144;
               } else if (p_191116_2_.func_191145_a(k + 1, l) == 0 && p_191116_1_.func_191145_a(k + 1, l) == 2) {
                  j1 = k + 1;
                  i2 = 131072;
               } else if (p_191116_2_.func_191145_a(k, l + 1) == 0 && p_191116_1_.func_191145_a(k, l + 1) == 2) {
                  l1 = l + 1;
                  i2 = 131072;
               } else if (p_191116_2_.func_191145_a(k - 1, l) == 0 && p_191116_1_.func_191145_a(k - 1, l) == 2) {
                  i1 = k - 1;
                  i2 = 131072;
               } else if (p_191116_2_.func_191145_a(k, l - 1) == 0 && p_191116_1_.func_191145_a(k, l - 1) == 2) {
                  k1 = l - 1;
                  i2 = 131072;
               }

               int j2 = this.field_191117_a.nextBoolean() ? i1 : j1;
               int k2 = this.field_191117_a.nextBoolean() ? k1 : l1;
               int l2 = 2097152;
               if (!p_191116_1_.func_191147_b(j2, k2, 1)) {
                  j2 = j2 == i1 ? j1 : i1;
                  k2 = k2 == k1 ? l1 : k1;
                  if (!p_191116_1_.func_191147_b(j2, k2, 1)) {
                     k2 = k2 == k1 ? l1 : k1;
                     if (!p_191116_1_.func_191147_b(j2, k2, 1)) {
                        j2 = j2 == i1 ? j1 : i1;
                        k2 = k2 == k1 ? l1 : k1;
                        if (!p_191116_1_.func_191147_b(j2, k2, 1)) {
                           l2 = 0;
                           j2 = i1;
                           k2 = k1;
                        }
                     }
                  }
               }

               for(int i3 = k1; i3 <= l1; ++i3) {
                  for(int j3 = i1; j3 <= j1; ++j3) {
                     if (j3 == j2 && i3 == k2) {
                        p_191116_2_.func_191144_a(j3, i3, 1048576 | l2 | i2 | k3);
                     } else {
                        p_191116_2_.func_191144_a(j3, i3, i2 | k3);
                     }
                  }
               }

               ++k3;
            }
         }

      }
   }

   public static class MansionTemplate extends StructureComponentTemplate {
      private String field_191082_d;
      private Rotation field_191083_e;
      private Mirror field_191084_f;

      public MansionTemplate() {
      }

      public MansionTemplate(TemplateManager p_i47355_1_, String p_i47355_2_, BlockPos p_i47355_3_, Rotation p_i47355_4_) {
         this(p_i47355_1_, p_i47355_2_, p_i47355_3_, p_i47355_4_, Mirror.NONE);
      }

      public MansionTemplate(TemplateManager p_i47356_1_, String p_i47356_2_, BlockPos p_i47356_3_, Rotation p_i47356_4_, Mirror p_i47356_5_) {
         super(0);
         this.field_191082_d = p_i47356_2_;
         this.field_186178_c = p_i47356_3_;
         this.field_191083_e = p_i47356_4_;
         this.field_191084_f = p_i47356_5_;
         this.func_191081_a(p_i47356_1_);
      }

      private void func_191081_a(TemplateManager p_191081_1_) {
         Template template = p_191081_1_.func_186237_a((MinecraftServer)null, new ResourceLocation("mansion/" + this.field_191082_d));
         PlacementSettings placementsettings = (new PlacementSettings()).func_186222_a(true).func_186220_a(this.field_191083_e).func_186214_a(this.field_191084_f);
         this.func_186173_a(template, this.field_186178_c, placementsettings);
      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         super.func_143012_a(p_143012_1_);
         p_143012_1_.func_74778_a("Template", this.field_191082_d);
         p_143012_1_.func_74778_a("Rot", this.field_186177_b.func_186215_c().name());
         p_143012_1_.func_74778_a("Mi", this.field_186177_b.func_186212_b().name());
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         super.func_143011_b(p_143011_1_, p_143011_2_);
         this.field_191082_d = p_143011_1_.func_74779_i("Template");
         this.field_191083_e = Rotation.valueOf(p_143011_1_.func_74779_i("Rot"));
         this.field_191084_f = Mirror.valueOf(p_143011_1_.func_74779_i("Mi"));
         this.func_191081_a(p_143011_2_);
      }

      protected void func_186175_a(String p_186175_1_, BlockPos p_186175_2_, World p_186175_3_, Random p_186175_4_, StructureBoundingBox p_186175_5_) {
         if (p_186175_1_.startsWith("Chest")) {
            Rotation rotation = this.field_186177_b.func_186215_c();
            IBlockState iblockstate = Blocks.field_150486_ae.func_176223_P();
            if ("ChestWest".equals(p_186175_1_)) {
               iblockstate = iblockstate.func_177226_a(BlockChest.field_176459_a, rotation.func_185831_a(EnumFacing.WEST));
            } else if ("ChestEast".equals(p_186175_1_)) {
               iblockstate = iblockstate.func_177226_a(BlockChest.field_176459_a, rotation.func_185831_a(EnumFacing.EAST));
            } else if ("ChestSouth".equals(p_186175_1_)) {
               iblockstate = iblockstate.func_177226_a(BlockChest.field_176459_a, rotation.func_185831_a(EnumFacing.SOUTH));
            } else if ("ChestNorth".equals(p_186175_1_)) {
               iblockstate = iblockstate.func_177226_a(BlockChest.field_176459_a, rotation.func_185831_a(EnumFacing.NORTH));
            }

            this.func_191080_a(p_186175_3_, p_186175_5_, p_186175_4_, p_186175_2_, LootTableList.field_191192_o, iblockstate);
         } else if ("Mage".equals(p_186175_1_)) {
            EntityEvoker entityevoker = new EntityEvoker(p_186175_3_);
            entityevoker.func_110163_bv();
            entityevoker.func_174828_a(p_186175_2_, 0.0F, 0.0F);
            p_186175_3_.func_72838_d(entityevoker);
            p_186175_3_.func_180501_a(p_186175_2_, Blocks.field_150350_a.func_176223_P(), 2);
         } else if ("Warrior".equals(p_186175_1_)) {
            EntityVindicator entityvindicator = new EntityVindicator(p_186175_3_);
            entityvindicator.func_110163_bv();
            entityvindicator.func_174828_a(p_186175_2_, 0.0F, 0.0F);
            entityvindicator.func_180482_a(p_186175_3_.func_175649_E(new BlockPos(entityvindicator)), (IEntityLivingData)null);
            p_186175_3_.func_72838_d(entityvindicator);
            p_186175_3_.func_180501_a(p_186175_2_, Blocks.field_150350_a.func_176223_P(), 2);
         }

      }
   }

   static class PlacementData {
      public Rotation field_191138_a;
      public BlockPos field_191139_b;
      public String field_191140_c;

      private PlacementData() {
      }
   }

   static class Placer {
      private final TemplateManager field_191134_a;
      private final Random field_191135_b;
      private int field_191136_c;
      private int field_191137_d;

      public Placer(TemplateManager p_i47361_1_, Random p_i47361_2_) {
         this.field_191134_a = p_i47361_1_;
         this.field_191135_b = p_i47361_2_;
      }

      public void func_191125_a(BlockPos p_191125_1_, Rotation p_191125_2_, List<WoodlandMansionPieces.MansionTemplate> p_191125_3_, WoodlandMansionPieces.Grid p_191125_4_) {
         WoodlandMansionPieces.PlacementData woodlandmansionpieces$placementdata = new WoodlandMansionPieces.PlacementData();
         woodlandmansionpieces$placementdata.field_191139_b = p_191125_1_;
         woodlandmansionpieces$placementdata.field_191138_a = p_191125_2_;
         woodlandmansionpieces$placementdata.field_191140_c = "wall_flat";
         WoodlandMansionPieces.PlacementData woodlandmansionpieces$placementdata1 = new WoodlandMansionPieces.PlacementData();
         this.func_191133_a(p_191125_3_, woodlandmansionpieces$placementdata);
         woodlandmansionpieces$placementdata1.field_191139_b = woodlandmansionpieces$placementdata.field_191139_b.func_177981_b(8);
         woodlandmansionpieces$placementdata1.field_191138_a = woodlandmansionpieces$placementdata.field_191138_a;
         woodlandmansionpieces$placementdata1.field_191140_c = "wall_window";
         if (!p_191125_3_.isEmpty()) {
            ;
         }

         WoodlandMansionPieces.SimpleGrid woodlandmansionpieces$simplegrid = p_191125_4_.field_191118_b;
         WoodlandMansionPieces.SimpleGrid woodlandmansionpieces$simplegrid1 = p_191125_4_.field_191119_c;
         this.field_191136_c = p_191125_4_.field_191121_e + 1;
         this.field_191137_d = p_191125_4_.field_191122_f + 1;
         int i = p_191125_4_.field_191121_e + 1;
         int j = p_191125_4_.field_191122_f;
         this.func_191130_a(p_191125_3_, woodlandmansionpieces$placementdata, woodlandmansionpieces$simplegrid, EnumFacing.SOUTH, this.field_191136_c, this.field_191137_d, i, j);
         this.func_191130_a(p_191125_3_, woodlandmansionpieces$placementdata1, woodlandmansionpieces$simplegrid, EnumFacing.SOUTH, this.field_191136_c, this.field_191137_d, i, j);
         WoodlandMansionPieces.PlacementData woodlandmansionpieces$placementdata2 = new WoodlandMansionPieces.PlacementData();
         woodlandmansionpieces$placementdata2.field_191139_b = woodlandmansionpieces$placementdata.field_191139_b.func_177981_b(19);
         woodlandmansionpieces$placementdata2.field_191138_a = woodlandmansionpieces$placementdata.field_191138_a;
         woodlandmansionpieces$placementdata2.field_191140_c = "wall_window";
         boolean flag = false;

         for(int k = 0; k < woodlandmansionpieces$simplegrid1.field_191150_c && !flag; ++k) {
            for(int l = woodlandmansionpieces$simplegrid1.field_191149_b - 1; l >= 0 && !flag; --l) {
               if (WoodlandMansionPieces.Grid.func_191109_a(woodlandmansionpieces$simplegrid1, l, k)) {
                  woodlandmansionpieces$placementdata2.field_191139_b = woodlandmansionpieces$placementdata2.field_191139_b.func_177967_a(p_191125_2_.func_185831_a(EnumFacing.SOUTH), 8 + (k - this.field_191137_d) * 8);
                  woodlandmansionpieces$placementdata2.field_191139_b = woodlandmansionpieces$placementdata2.field_191139_b.func_177967_a(p_191125_2_.func_185831_a(EnumFacing.EAST), (l - this.field_191136_c) * 8);
                  this.func_191131_b(p_191125_3_, woodlandmansionpieces$placementdata2);
                  this.func_191130_a(p_191125_3_, woodlandmansionpieces$placementdata2, woodlandmansionpieces$simplegrid1, EnumFacing.SOUTH, l, k, l, k);
                  flag = true;
               }
            }
         }

         this.func_191123_a(p_191125_3_, p_191125_1_.func_177981_b(16), p_191125_2_, woodlandmansionpieces$simplegrid, woodlandmansionpieces$simplegrid1);
         this.func_191123_a(p_191125_3_, p_191125_1_.func_177981_b(27), p_191125_2_, woodlandmansionpieces$simplegrid1, (WoodlandMansionPieces.SimpleGrid)null);
         if (!p_191125_3_.isEmpty()) {
            ;
         }

         WoodlandMansionPieces.RoomCollection[] awoodlandmansionpieces$roomcollection = new WoodlandMansionPieces.RoomCollection[3];
         awoodlandmansionpieces$roomcollection[0] = new WoodlandMansionPieces.FirstFloor();
         awoodlandmansionpieces$roomcollection[1] = new WoodlandMansionPieces.SecondFloor();
         awoodlandmansionpieces$roomcollection[2] = new WoodlandMansionPieces.ThirdFloor();

         for(int l2 = 0; l2 < 3; ++l2) {
            BlockPos blockpos = p_191125_1_.func_177981_b(8 * l2 + (l2 == 2 ? 3 : 0));
            WoodlandMansionPieces.SimpleGrid woodlandmansionpieces$simplegrid2 = p_191125_4_.field_191120_d[l2];
            WoodlandMansionPieces.SimpleGrid woodlandmansionpieces$simplegrid3 = l2 == 2 ? woodlandmansionpieces$simplegrid1 : woodlandmansionpieces$simplegrid;
            String s = l2 == 0 ? "carpet_south" : "carpet_south_2";
            String s1 = l2 == 0 ? "carpet_west" : "carpet_west_2";

            for(int i1 = 0; i1 < woodlandmansionpieces$simplegrid3.field_191150_c; ++i1) {
               for(int j1 = 0; j1 < woodlandmansionpieces$simplegrid3.field_191149_b; ++j1) {
                  if (woodlandmansionpieces$simplegrid3.func_191145_a(j1, i1) == 1) {
                     BlockPos blockpos1 = blockpos.func_177967_a(p_191125_2_.func_185831_a(EnumFacing.SOUTH), 8 + (i1 - this.field_191137_d) * 8);
                     blockpos1 = blockpos1.func_177967_a(p_191125_2_.func_185831_a(EnumFacing.EAST), (j1 - this.field_191136_c) * 8);
                     p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "corridor_floor", blockpos1, p_191125_2_));
                     if (woodlandmansionpieces$simplegrid3.func_191145_a(j1, i1 - 1) == 1 || (woodlandmansionpieces$simplegrid2.func_191145_a(j1, i1 - 1) & 8388608) == 8388608) {
                        p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "carpet_north", blockpos1.func_177967_a(p_191125_2_.func_185831_a(EnumFacing.EAST), 1).func_177984_a(), p_191125_2_));
                     }

                     if (woodlandmansionpieces$simplegrid3.func_191145_a(j1 + 1, i1) == 1 || (woodlandmansionpieces$simplegrid2.func_191145_a(j1 + 1, i1) & 8388608) == 8388608) {
                        p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "carpet_east", blockpos1.func_177967_a(p_191125_2_.func_185831_a(EnumFacing.SOUTH), 1).func_177967_a(p_191125_2_.func_185831_a(EnumFacing.EAST), 5).func_177984_a(), p_191125_2_));
                     }

                     if (woodlandmansionpieces$simplegrid3.func_191145_a(j1, i1 + 1) == 1 || (woodlandmansionpieces$simplegrid2.func_191145_a(j1, i1 + 1) & 8388608) == 8388608) {
                        p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, s, blockpos1.func_177967_a(p_191125_2_.func_185831_a(EnumFacing.SOUTH), 5).func_177967_a(p_191125_2_.func_185831_a(EnumFacing.WEST), 1), p_191125_2_));
                     }

                     if (woodlandmansionpieces$simplegrid3.func_191145_a(j1 - 1, i1) == 1 || (woodlandmansionpieces$simplegrid2.func_191145_a(j1 - 1, i1) & 8388608) == 8388608) {
                        p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, s1, blockpos1.func_177967_a(p_191125_2_.func_185831_a(EnumFacing.WEST), 1).func_177967_a(p_191125_2_.func_185831_a(EnumFacing.NORTH), 1), p_191125_2_));
                     }
                  }
               }
            }

            String s2 = l2 == 0 ? "indoors_wall" : "indoors_wall_2";
            String s3 = l2 == 0 ? "indoors_door" : "indoors_door_2";
            List<EnumFacing> list = Lists.<EnumFacing>newArrayList();

            for(int k1 = 0; k1 < woodlandmansionpieces$simplegrid3.field_191150_c; ++k1) {
               for(int l1 = 0; l1 < woodlandmansionpieces$simplegrid3.field_191149_b; ++l1) {
                  boolean flag1 = l2 == 2 && woodlandmansionpieces$simplegrid3.func_191145_a(l1, k1) == 3;
                  if (woodlandmansionpieces$simplegrid3.func_191145_a(l1, k1) == 2 || flag1) {
                     int i2 = woodlandmansionpieces$simplegrid2.func_191145_a(l1, k1);
                     int j2 = i2 & 983040;
                     int k2 = i2 & '\uffff';
                     flag1 = flag1 && (i2 & 8388608) == 8388608;
                     list.clear();
                     if ((i2 & 2097152) == 2097152) {
                        for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL.func_179516_a()) {
                           if (woodlandmansionpieces$simplegrid3.func_191145_a(l1 + enumfacing.func_82601_c(), k1 + enumfacing.func_82599_e()) == 1) {
                              list.add(enumfacing);
                           }
                        }
                     }

                     EnumFacing enumfacing1 = null;
                     if (!list.isEmpty()) {
                        enumfacing1 = list.get(this.field_191135_b.nextInt(list.size()));
                     } else if ((i2 & 1048576) == 1048576) {
                        enumfacing1 = EnumFacing.UP;
                     }

                     BlockPos blockpos2 = blockpos.func_177967_a(p_191125_2_.func_185831_a(EnumFacing.SOUTH), 8 + (k1 - this.field_191137_d) * 8);
                     blockpos2 = blockpos2.func_177967_a(p_191125_2_.func_185831_a(EnumFacing.EAST), -1 + (l1 - this.field_191136_c) * 8);
                     if (WoodlandMansionPieces.Grid.func_191109_a(woodlandmansionpieces$simplegrid3, l1 - 1, k1) && !p_191125_4_.func_191114_a(woodlandmansionpieces$simplegrid3, l1 - 1, k1, l2, k2)) {
                        p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, enumfacing1 == EnumFacing.WEST ? s3 : s2, blockpos2, p_191125_2_));
                     }

                     if (woodlandmansionpieces$simplegrid3.func_191145_a(l1 + 1, k1) == 1 && !flag1) {
                        BlockPos blockpos3 = blockpos2.func_177967_a(p_191125_2_.func_185831_a(EnumFacing.EAST), 8);
                        p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, enumfacing1 == EnumFacing.EAST ? s3 : s2, blockpos3, p_191125_2_));
                     }

                     if (WoodlandMansionPieces.Grid.func_191109_a(woodlandmansionpieces$simplegrid3, l1, k1 + 1) && !p_191125_4_.func_191114_a(woodlandmansionpieces$simplegrid3, l1, k1 + 1, l2, k2)) {
                        BlockPos blockpos4 = blockpos2.func_177967_a(p_191125_2_.func_185831_a(EnumFacing.SOUTH), 7);
                        blockpos4 = blockpos4.func_177967_a(p_191125_2_.func_185831_a(EnumFacing.EAST), 7);
                        p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, enumfacing1 == EnumFacing.SOUTH ? s3 : s2, blockpos4, p_191125_2_.func_185830_a(Rotation.CLOCKWISE_90)));
                     }

                     if (woodlandmansionpieces$simplegrid3.func_191145_a(l1, k1 - 1) == 1 && !flag1) {
                        BlockPos blockpos5 = blockpos2.func_177967_a(p_191125_2_.func_185831_a(EnumFacing.NORTH), 1);
                        blockpos5 = blockpos5.func_177967_a(p_191125_2_.func_185831_a(EnumFacing.EAST), 7);
                        p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, enumfacing1 == EnumFacing.NORTH ? s3 : s2, blockpos5, p_191125_2_.func_185830_a(Rotation.CLOCKWISE_90)));
                     }

                     if (j2 == 65536) {
                        this.func_191129_a(p_191125_3_, blockpos2, p_191125_2_, enumfacing1, awoodlandmansionpieces$roomcollection[l2]);
                     } else if (j2 == 131072 && enumfacing1 != null) {
                        EnumFacing enumfacing3 = p_191125_4_.func_191113_b(woodlandmansionpieces$simplegrid3, l1, k1, l2, k2);
                        boolean flag2 = (i2 & 4194304) == 4194304;
                        this.func_191132_a(p_191125_3_, blockpos2, p_191125_2_, enumfacing3, enumfacing1, awoodlandmansionpieces$roomcollection[l2], flag2);
                     } else if (j2 == 262144 && enumfacing1 != null && enumfacing1 != EnumFacing.UP) {
                        EnumFacing enumfacing2 = enumfacing1.func_176746_e();
                        if (!p_191125_4_.func_191114_a(woodlandmansionpieces$simplegrid3, l1 + enumfacing2.func_82601_c(), k1 + enumfacing2.func_82599_e(), l2, k2)) {
                           enumfacing2 = enumfacing2.func_176734_d();
                        }

                        this.func_191127_a(p_191125_3_, blockpos2, p_191125_2_, enumfacing2, enumfacing1, awoodlandmansionpieces$roomcollection[l2]);
                     } else if (j2 == 262144 && enumfacing1 == EnumFacing.UP) {
                        this.func_191128_a(p_191125_3_, blockpos2, p_191125_2_, awoodlandmansionpieces$roomcollection[l2]);
                     }
                  }
               }
            }
         }

      }

      private void func_191130_a(List<WoodlandMansionPieces.MansionTemplate> p_191130_1_, WoodlandMansionPieces.PlacementData p_191130_2_, WoodlandMansionPieces.SimpleGrid p_191130_3_, EnumFacing p_191130_4_, int p_191130_5_, int p_191130_6_, int p_191130_7_, int p_191130_8_) {
         int i = p_191130_5_;
         int j = p_191130_6_;
         EnumFacing enumfacing = p_191130_4_;

         while(true) {
            if (!WoodlandMansionPieces.Grid.func_191109_a(p_191130_3_, i + p_191130_4_.func_82601_c(), j + p_191130_4_.func_82599_e())) {
               this.func_191124_c(p_191130_1_, p_191130_2_);
               p_191130_4_ = p_191130_4_.func_176746_e();
               if (i != p_191130_7_ || j != p_191130_8_ || enumfacing != p_191130_4_) {
                  this.func_191131_b(p_191130_1_, p_191130_2_);
               }
            } else if (WoodlandMansionPieces.Grid.func_191109_a(p_191130_3_, i + p_191130_4_.func_82601_c(), j + p_191130_4_.func_82599_e()) && WoodlandMansionPieces.Grid.func_191109_a(p_191130_3_, i + p_191130_4_.func_82601_c() + p_191130_4_.func_176735_f().func_82601_c(), j + p_191130_4_.func_82599_e() + p_191130_4_.func_176735_f().func_82599_e())) {
               this.func_191126_d(p_191130_1_, p_191130_2_);
               i += p_191130_4_.func_82601_c();
               j += p_191130_4_.func_82599_e();
               p_191130_4_ = p_191130_4_.func_176735_f();
            } else {
               i += p_191130_4_.func_82601_c();
               j += p_191130_4_.func_82599_e();
               if (i != p_191130_7_ || j != p_191130_8_ || enumfacing != p_191130_4_) {
                  this.func_191131_b(p_191130_1_, p_191130_2_);
               }
            }

            if (i == p_191130_7_ && j == p_191130_8_ && enumfacing == p_191130_4_) {
               break;
            }
         }

      }

      private void func_191123_a(List<WoodlandMansionPieces.MansionTemplate> p_191123_1_, BlockPos p_191123_2_, Rotation p_191123_3_, WoodlandMansionPieces.SimpleGrid p_191123_4_, @Nullable WoodlandMansionPieces.SimpleGrid p_191123_5_) {
         for(int i = 0; i < p_191123_4_.field_191150_c; ++i) {
            for(int j = 0; j < p_191123_4_.field_191149_b; ++j) {
               BlockPos lvt_8_3_ = p_191123_2_.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.SOUTH), 8 + (i - this.field_191137_d) * 8);
               lvt_8_3_ = lvt_8_3_.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.EAST), (j - this.field_191136_c) * 8);
               boolean flag = p_191123_5_ != null && WoodlandMansionPieces.Grid.func_191109_a(p_191123_5_, j, i);
               if (WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j, i) && !flag) {
                  p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof", lvt_8_3_.func_177981_b(3), p_191123_3_));
                  if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j + 1, i)) {
                     BlockPos blockpos1 = lvt_8_3_.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.EAST), 6);
                     p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_front", blockpos1, p_191123_3_));
                  }

                  if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j - 1, i)) {
                     BlockPos blockpos5 = lvt_8_3_.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.EAST), 0);
                     blockpos5 = blockpos5.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.SOUTH), 7);
                     p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_front", blockpos5, p_191123_3_.func_185830_a(Rotation.CLOCKWISE_180)));
                  }

                  if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j, i - 1)) {
                     BlockPos blockpos6 = lvt_8_3_.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.WEST), 1);
                     p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_front", blockpos6, p_191123_3_.func_185830_a(Rotation.COUNTERCLOCKWISE_90)));
                  }

                  if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j, i + 1)) {
                     BlockPos blockpos7 = lvt_8_3_.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.EAST), 6);
                     blockpos7 = blockpos7.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.SOUTH), 6);
                     p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_front", blockpos7, p_191123_3_.func_185830_a(Rotation.CLOCKWISE_90)));
                  }
               }
            }
         }

         if (p_191123_5_ != null) {
            for(int k = 0; k < p_191123_4_.field_191150_c; ++k) {
               for(int i1 = 0; i1 < p_191123_4_.field_191149_b; ++i1) {
                  BlockPos blockpos3 = p_191123_2_.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.SOUTH), 8 + (k - this.field_191137_d) * 8);
                  blockpos3 = blockpos3.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.EAST), (i1 - this.field_191136_c) * 8);
                  boolean flag1 = WoodlandMansionPieces.Grid.func_191109_a(p_191123_5_, i1, k);
                  if (WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, i1, k) && flag1) {
                     if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, i1 + 1, k)) {
                        BlockPos blockpos8 = blockpos3.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.EAST), 7);
                        p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall", blockpos8, p_191123_3_));
                     }

                     if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, i1 - 1, k)) {
                        BlockPos blockpos9 = blockpos3.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.WEST), 1);
                        blockpos9 = blockpos9.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.SOUTH), 6);
                        p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall", blockpos9, p_191123_3_.func_185830_a(Rotation.CLOCKWISE_180)));
                     }

                     if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, i1, k - 1)) {
                        BlockPos blockpos10 = blockpos3.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.WEST), 0);
                        blockpos10 = blockpos10.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.NORTH), 1);
                        p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall", blockpos10, p_191123_3_.func_185830_a(Rotation.COUNTERCLOCKWISE_90)));
                     }

                     if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, i1, k + 1)) {
                        BlockPos blockpos11 = blockpos3.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.EAST), 6);
                        blockpos11 = blockpos11.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.SOUTH), 7);
                        p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall", blockpos11, p_191123_3_.func_185830_a(Rotation.CLOCKWISE_90)));
                     }

                     if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, i1 + 1, k)) {
                        if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, i1, k - 1)) {
                           BlockPos blockpos12 = blockpos3.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.EAST), 7);
                           blockpos12 = blockpos12.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.NORTH), 2);
                           p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall_corner", blockpos12, p_191123_3_));
                        }

                        if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, i1, k + 1)) {
                           BlockPos blockpos13 = blockpos3.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.EAST), 8);
                           blockpos13 = blockpos13.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.SOUTH), 7);
                           p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall_corner", blockpos13, p_191123_3_.func_185830_a(Rotation.CLOCKWISE_90)));
                        }
                     }

                     if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, i1 - 1, k)) {
                        if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, i1, k - 1)) {
                           BlockPos blockpos14 = blockpos3.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.WEST), 2);
                           blockpos14 = blockpos14.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.NORTH), 1);
                           p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall_corner", blockpos14, p_191123_3_.func_185830_a(Rotation.COUNTERCLOCKWISE_90)));
                        }

                        if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, i1, k + 1)) {
                           BlockPos blockpos15 = blockpos3.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.WEST), 1);
                           blockpos15 = blockpos15.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.SOUTH), 8);
                           p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall_corner", blockpos15, p_191123_3_.func_185830_a(Rotation.CLOCKWISE_180)));
                        }
                     }
                  }
               }
            }
         }

         for(int l = 0; l < p_191123_4_.field_191150_c; ++l) {
            for(int j1 = 0; j1 < p_191123_4_.field_191149_b; ++j1) {
               BlockPos blockpos4 = p_191123_2_.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.SOUTH), 8 + (l - this.field_191137_d) * 8);
               blockpos4 = blockpos4.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.EAST), (j1 - this.field_191136_c) * 8);
               boolean flag2 = p_191123_5_ != null && WoodlandMansionPieces.Grid.func_191109_a(p_191123_5_, j1, l);
               if (WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j1, l) && !flag2) {
                  if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j1 + 1, l)) {
                     BlockPos blockpos16 = blockpos4.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.EAST), 6);
                     if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j1, l + 1)) {
                        BlockPos blockpos2 = blockpos16.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.SOUTH), 6);
                        p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_corner", blockpos2, p_191123_3_));
                     } else if (WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j1 + 1, l + 1)) {
                        BlockPos blockpos18 = blockpos16.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.SOUTH), 5);
                        p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_inner_corner", blockpos18, p_191123_3_));
                     }

                     if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j1, l - 1)) {
                        p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_corner", blockpos16, p_191123_3_.func_185830_a(Rotation.COUNTERCLOCKWISE_90)));
                     } else if (WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j1 + 1, l - 1)) {
                        BlockPos blockpos19 = blockpos4.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.EAST), 9);
                        blockpos19 = blockpos19.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.NORTH), 2);
                        p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_inner_corner", blockpos19, p_191123_3_.func_185830_a(Rotation.CLOCKWISE_90)));
                     }
                  }

                  if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j1 - 1, l)) {
                     BlockPos blockpos17 = blockpos4.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.EAST), 0);
                     blockpos17 = blockpos17.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.SOUTH), 0);
                     if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j1, l + 1)) {
                        BlockPos blockpos20 = blockpos17.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.SOUTH), 6);
                        p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_corner", blockpos20, p_191123_3_.func_185830_a(Rotation.CLOCKWISE_90)));
                     } else if (WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j1 - 1, l + 1)) {
                        BlockPos blockpos21 = blockpos17.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.SOUTH), 8);
                        blockpos21 = blockpos21.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.WEST), 3);
                        p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_inner_corner", blockpos21, p_191123_3_.func_185830_a(Rotation.COUNTERCLOCKWISE_90)));
                     }

                     if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j1, l - 1)) {
                        p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_corner", blockpos17, p_191123_3_.func_185830_a(Rotation.CLOCKWISE_180)));
                     } else if (WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j1 - 1, l - 1)) {
                        BlockPos blockpos22 = blockpos17.func_177967_a(p_191123_3_.func_185831_a(EnumFacing.SOUTH), 1);
                        p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_inner_corner", blockpos22, p_191123_3_.func_185830_a(Rotation.CLOCKWISE_180)));
                     }
                  }
               }
            }
         }

      }

      private void func_191133_a(List<WoodlandMansionPieces.MansionTemplate> p_191133_1_, WoodlandMansionPieces.PlacementData p_191133_2_) {
         EnumFacing enumfacing = p_191133_2_.field_191138_a.func_185831_a(EnumFacing.WEST);
         p_191133_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "entrance", p_191133_2_.field_191139_b.func_177967_a(enumfacing, 9), p_191133_2_.field_191138_a));
         p_191133_2_.field_191139_b = p_191133_2_.field_191139_b.func_177967_a(p_191133_2_.field_191138_a.func_185831_a(EnumFacing.SOUTH), 16);
      }

      private void func_191131_b(List<WoodlandMansionPieces.MansionTemplate> p_191131_1_, WoodlandMansionPieces.PlacementData p_191131_2_) {
         p_191131_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191131_2_.field_191140_c, p_191131_2_.field_191139_b.func_177967_a(p_191131_2_.field_191138_a.func_185831_a(EnumFacing.EAST), 7), p_191131_2_.field_191138_a));
         p_191131_2_.field_191139_b = p_191131_2_.field_191139_b.func_177967_a(p_191131_2_.field_191138_a.func_185831_a(EnumFacing.SOUTH), 8);
      }

      private void func_191124_c(List<WoodlandMansionPieces.MansionTemplate> p_191124_1_, WoodlandMansionPieces.PlacementData p_191124_2_) {
         p_191124_2_.field_191139_b = p_191124_2_.field_191139_b.func_177967_a(p_191124_2_.field_191138_a.func_185831_a(EnumFacing.SOUTH), -1);
         p_191124_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "wall_corner", p_191124_2_.field_191139_b, p_191124_2_.field_191138_a));
         p_191124_2_.field_191139_b = p_191124_2_.field_191139_b.func_177967_a(p_191124_2_.field_191138_a.func_185831_a(EnumFacing.SOUTH), -7);
         p_191124_2_.field_191139_b = p_191124_2_.field_191139_b.func_177967_a(p_191124_2_.field_191138_a.func_185831_a(EnumFacing.WEST), -6);
         p_191124_2_.field_191138_a = p_191124_2_.field_191138_a.func_185830_a(Rotation.CLOCKWISE_90);
      }

      private void func_191126_d(List<WoodlandMansionPieces.MansionTemplate> p_191126_1_, WoodlandMansionPieces.PlacementData p_191126_2_) {
         p_191126_2_.field_191139_b = p_191126_2_.field_191139_b.func_177967_a(p_191126_2_.field_191138_a.func_185831_a(EnumFacing.SOUTH), 6);
         p_191126_2_.field_191139_b = p_191126_2_.field_191139_b.func_177967_a(p_191126_2_.field_191138_a.func_185831_a(EnumFacing.EAST), 8);
         p_191126_2_.field_191138_a = p_191126_2_.field_191138_a.func_185830_a(Rotation.COUNTERCLOCKWISE_90);
      }

      private void func_191129_a(List<WoodlandMansionPieces.MansionTemplate> p_191129_1_, BlockPos p_191129_2_, Rotation p_191129_3_, EnumFacing p_191129_4_, WoodlandMansionPieces.RoomCollection p_191129_5_) {
         Rotation rotation = Rotation.NONE;
         String s = p_191129_5_.func_191104_a(this.field_191135_b);
         if (p_191129_4_ != EnumFacing.EAST) {
            if (p_191129_4_ == EnumFacing.NORTH) {
               rotation = rotation.func_185830_a(Rotation.COUNTERCLOCKWISE_90);
            } else if (p_191129_4_ == EnumFacing.WEST) {
               rotation = rotation.func_185830_a(Rotation.CLOCKWISE_180);
            } else if (p_191129_4_ == EnumFacing.SOUTH) {
               rotation = rotation.func_185830_a(Rotation.CLOCKWISE_90);
            } else {
               s = p_191129_5_.func_191099_b(this.field_191135_b);
            }
         }

         BlockPos blockpos = Template.func_191157_a(new BlockPos(1, 0, 0), Mirror.NONE, rotation, 7, 7);
         rotation = rotation.func_185830_a(p_191129_3_);
         blockpos = blockpos.func_190942_a(p_191129_3_);
         BlockPos blockpos1 = p_191129_2_.func_177982_a(blockpos.func_177958_n(), 0, blockpos.func_177952_p());
         p_191129_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, s, blockpos1, rotation));
      }

      private void func_191132_a(List<WoodlandMansionPieces.MansionTemplate> p_191132_1_, BlockPos p_191132_2_, Rotation p_191132_3_, EnumFacing p_191132_4_, EnumFacing p_191132_5_, WoodlandMansionPieces.RoomCollection p_191132_6_, boolean p_191132_7_) {
         if (p_191132_5_ == EnumFacing.EAST && p_191132_4_ == EnumFacing.SOUTH) {
            BlockPos blockpos13 = p_191132_2_.func_177967_a(p_191132_3_.func_185831_a(EnumFacing.EAST), 1);
            p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191100_a(this.field_191135_b, p_191132_7_), blockpos13, p_191132_3_));
         } else if (p_191132_5_ == EnumFacing.EAST && p_191132_4_ == EnumFacing.NORTH) {
            BlockPos blockpos12 = p_191132_2_.func_177967_a(p_191132_3_.func_185831_a(EnumFacing.EAST), 1);
            blockpos12 = blockpos12.func_177967_a(p_191132_3_.func_185831_a(EnumFacing.SOUTH), 6);
            p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191100_a(this.field_191135_b, p_191132_7_), blockpos12, p_191132_3_, Mirror.LEFT_RIGHT));
         } else if (p_191132_5_ == EnumFacing.WEST && p_191132_4_ == EnumFacing.NORTH) {
            BlockPos blockpos11 = p_191132_2_.func_177967_a(p_191132_3_.func_185831_a(EnumFacing.EAST), 7);
            blockpos11 = blockpos11.func_177967_a(p_191132_3_.func_185831_a(EnumFacing.SOUTH), 6);
            p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191100_a(this.field_191135_b, p_191132_7_), blockpos11, p_191132_3_.func_185830_a(Rotation.CLOCKWISE_180)));
         } else if (p_191132_5_ == EnumFacing.WEST && p_191132_4_ == EnumFacing.SOUTH) {
            BlockPos blockpos10 = p_191132_2_.func_177967_a(p_191132_3_.func_185831_a(EnumFacing.EAST), 7);
            p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191100_a(this.field_191135_b, p_191132_7_), blockpos10, p_191132_3_, Mirror.FRONT_BACK));
         } else if (p_191132_5_ == EnumFacing.SOUTH && p_191132_4_ == EnumFacing.EAST) {
            BlockPos blockpos9 = p_191132_2_.func_177967_a(p_191132_3_.func_185831_a(EnumFacing.EAST), 1);
            p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191100_a(this.field_191135_b, p_191132_7_), blockpos9, p_191132_3_.func_185830_a(Rotation.CLOCKWISE_90), Mirror.LEFT_RIGHT));
         } else if (p_191132_5_ == EnumFacing.SOUTH && p_191132_4_ == EnumFacing.WEST) {
            BlockPos blockpos8 = p_191132_2_.func_177967_a(p_191132_3_.func_185831_a(EnumFacing.EAST), 7);
            p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191100_a(this.field_191135_b, p_191132_7_), blockpos8, p_191132_3_.func_185830_a(Rotation.CLOCKWISE_90)));
         } else if (p_191132_5_ == EnumFacing.NORTH && p_191132_4_ == EnumFacing.WEST) {
            BlockPos blockpos7 = p_191132_2_.func_177967_a(p_191132_3_.func_185831_a(EnumFacing.EAST), 7);
            blockpos7 = blockpos7.func_177967_a(p_191132_3_.func_185831_a(EnumFacing.SOUTH), 6);
            p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191100_a(this.field_191135_b, p_191132_7_), blockpos7, p_191132_3_.func_185830_a(Rotation.CLOCKWISE_90), Mirror.FRONT_BACK));
         } else if (p_191132_5_ == EnumFacing.NORTH && p_191132_4_ == EnumFacing.EAST) {
            BlockPos blockpos6 = p_191132_2_.func_177967_a(p_191132_3_.func_185831_a(EnumFacing.EAST), 1);
            blockpos6 = blockpos6.func_177967_a(p_191132_3_.func_185831_a(EnumFacing.SOUTH), 6);
            p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191100_a(this.field_191135_b, p_191132_7_), blockpos6, p_191132_3_.func_185830_a(Rotation.COUNTERCLOCKWISE_90)));
         } else if (p_191132_5_ == EnumFacing.SOUTH && p_191132_4_ == EnumFacing.NORTH) {
            BlockPos blockpos5 = p_191132_2_.func_177967_a(p_191132_3_.func_185831_a(EnumFacing.EAST), 1);
            blockpos5 = blockpos5.func_177967_a(p_191132_3_.func_185831_a(EnumFacing.NORTH), 8);
            p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191098_b(this.field_191135_b, p_191132_7_), blockpos5, p_191132_3_));
         } else if (p_191132_5_ == EnumFacing.NORTH && p_191132_4_ == EnumFacing.SOUTH) {
            BlockPos blockpos4 = p_191132_2_.func_177967_a(p_191132_3_.func_185831_a(EnumFacing.EAST), 7);
            blockpos4 = blockpos4.func_177967_a(p_191132_3_.func_185831_a(EnumFacing.SOUTH), 14);
            p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191098_b(this.field_191135_b, p_191132_7_), blockpos4, p_191132_3_.func_185830_a(Rotation.CLOCKWISE_180)));
         } else if (p_191132_5_ == EnumFacing.WEST && p_191132_4_ == EnumFacing.EAST) {
            BlockPos blockpos3 = p_191132_2_.func_177967_a(p_191132_3_.func_185831_a(EnumFacing.EAST), 15);
            p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191098_b(this.field_191135_b, p_191132_7_), blockpos3, p_191132_3_.func_185830_a(Rotation.CLOCKWISE_90)));
         } else if (p_191132_5_ == EnumFacing.EAST && p_191132_4_ == EnumFacing.WEST) {
            BlockPos blockpos2 = p_191132_2_.func_177967_a(p_191132_3_.func_185831_a(EnumFacing.WEST), 7);
            blockpos2 = blockpos2.func_177967_a(p_191132_3_.func_185831_a(EnumFacing.SOUTH), 6);
            p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191098_b(this.field_191135_b, p_191132_7_), blockpos2, p_191132_3_.func_185830_a(Rotation.COUNTERCLOCKWISE_90)));
         } else if (p_191132_5_ == EnumFacing.UP && p_191132_4_ == EnumFacing.EAST) {
            BlockPos blockpos1 = p_191132_2_.func_177967_a(p_191132_3_.func_185831_a(EnumFacing.EAST), 15);
            p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191102_c(this.field_191135_b), blockpos1, p_191132_3_.func_185830_a(Rotation.CLOCKWISE_90)));
         } else if (p_191132_5_ == EnumFacing.UP && p_191132_4_ == EnumFacing.SOUTH) {
            BlockPos blockpos = p_191132_2_.func_177967_a(p_191132_3_.func_185831_a(EnumFacing.EAST), 1);
            blockpos = blockpos.func_177967_a(p_191132_3_.func_185831_a(EnumFacing.NORTH), 0);
            p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191102_c(this.field_191135_b), blockpos, p_191132_3_));
         }

      }

      private void func_191127_a(List<WoodlandMansionPieces.MansionTemplate> p_191127_1_, BlockPos p_191127_2_, Rotation p_191127_3_, EnumFacing p_191127_4_, EnumFacing p_191127_5_, WoodlandMansionPieces.RoomCollection p_191127_6_) {
         int i = 0;
         int j = 0;
         Rotation rotation = p_191127_3_;
         Mirror mirror = Mirror.NONE;
         if (p_191127_5_ == EnumFacing.EAST && p_191127_4_ == EnumFacing.SOUTH) {
            i = -7;
         } else if (p_191127_5_ == EnumFacing.EAST && p_191127_4_ == EnumFacing.NORTH) {
            i = -7;
            j = 6;
            mirror = Mirror.LEFT_RIGHT;
         } else if (p_191127_5_ == EnumFacing.NORTH && p_191127_4_ == EnumFacing.EAST) {
            i = 1;
            j = 14;
            rotation = p_191127_3_.func_185830_a(Rotation.COUNTERCLOCKWISE_90);
         } else if (p_191127_5_ == EnumFacing.NORTH && p_191127_4_ == EnumFacing.WEST) {
            i = 7;
            j = 14;
            rotation = p_191127_3_.func_185830_a(Rotation.COUNTERCLOCKWISE_90);
            mirror = Mirror.LEFT_RIGHT;
         } else if (p_191127_5_ == EnumFacing.SOUTH && p_191127_4_ == EnumFacing.WEST) {
            i = 7;
            j = -8;
            rotation = p_191127_3_.func_185830_a(Rotation.CLOCKWISE_90);
         } else if (p_191127_5_ == EnumFacing.SOUTH && p_191127_4_ == EnumFacing.EAST) {
            i = 1;
            j = -8;
            rotation = p_191127_3_.func_185830_a(Rotation.CLOCKWISE_90);
            mirror = Mirror.LEFT_RIGHT;
         } else if (p_191127_5_ == EnumFacing.WEST && p_191127_4_ == EnumFacing.NORTH) {
            i = 15;
            j = 6;
            rotation = p_191127_3_.func_185830_a(Rotation.CLOCKWISE_180);
         } else if (p_191127_5_ == EnumFacing.WEST && p_191127_4_ == EnumFacing.SOUTH) {
            i = 15;
            mirror = Mirror.FRONT_BACK;
         }

         BlockPos blockpos = p_191127_2_.func_177967_a(p_191127_3_.func_185831_a(EnumFacing.EAST), i);
         blockpos = blockpos.func_177967_a(p_191127_3_.func_185831_a(EnumFacing.SOUTH), j);
         p_191127_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191127_6_.func_191101_d(this.field_191135_b), blockpos, rotation, mirror));
      }

      private void func_191128_a(List<WoodlandMansionPieces.MansionTemplate> p_191128_1_, BlockPos p_191128_2_, Rotation p_191128_3_, WoodlandMansionPieces.RoomCollection p_191128_4_) {
         BlockPos blockpos = p_191128_2_.func_177967_a(p_191128_3_.func_185831_a(EnumFacing.EAST), 1);
         p_191128_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191128_4_.func_191103_e(this.field_191135_b), blockpos, p_191128_3_, Mirror.NONE));
      }
   }

   abstract static class RoomCollection {
      private RoomCollection() {
      }

      public abstract String func_191104_a(Random var1);

      public abstract String func_191099_b(Random var1);

      public abstract String func_191100_a(Random var1, boolean var2);

      public abstract String func_191098_b(Random var1, boolean var2);

      public abstract String func_191102_c(Random var1);

      public abstract String func_191101_d(Random var1);

      public abstract String func_191103_e(Random var1);
   }

   static class SecondFloor extends WoodlandMansionPieces.RoomCollection {
      private SecondFloor() {
      }

      public String func_191104_a(Random p_191104_1_) {
         return "1x1_b" + (p_191104_1_.nextInt(4) + 1);
      }

      public String func_191099_b(Random p_191099_1_) {
         return "1x1_as" + (p_191099_1_.nextInt(4) + 1);
      }

      public String func_191100_a(Random p_191100_1_, boolean p_191100_2_) {
         return p_191100_2_ ? "1x2_c_stairs" : "1x2_c" + (p_191100_1_.nextInt(4) + 1);
      }

      public String func_191098_b(Random p_191098_1_, boolean p_191098_2_) {
         return p_191098_2_ ? "1x2_d_stairs" : "1x2_d" + (p_191098_1_.nextInt(5) + 1);
      }

      public String func_191102_c(Random p_191102_1_) {
         return "1x2_se" + (p_191102_1_.nextInt(1) + 1);
      }

      public String func_191101_d(Random p_191101_1_) {
         return "2x2_b" + (p_191101_1_.nextInt(5) + 1);
      }

      public String func_191103_e(Random p_191103_1_) {
         return "2x2_s1";
      }
   }

   static class SimpleGrid {
      private final int[][] field_191148_a;
      private final int field_191149_b;
      private final int field_191150_c;
      private final int field_191151_d;

      public SimpleGrid(int p_i47358_1_, int p_i47358_2_, int p_i47358_3_) {
         this.field_191149_b = p_i47358_1_;
         this.field_191150_c = p_i47358_2_;
         this.field_191151_d = p_i47358_3_;
         this.field_191148_a = new int[p_i47358_1_][p_i47358_2_];
      }

      public void func_191144_a(int p_191144_1_, int p_191144_2_, int p_191144_3_) {
         if (p_191144_1_ >= 0 && p_191144_1_ < this.field_191149_b && p_191144_2_ >= 0 && p_191144_2_ < this.field_191150_c) {
            this.field_191148_a[p_191144_1_][p_191144_2_] = p_191144_3_;
         }

      }

      public void func_191142_a(int p_191142_1_, int p_191142_2_, int p_191142_3_, int p_191142_4_, int p_191142_5_) {
         for(int i = p_191142_2_; i <= p_191142_4_; ++i) {
            for(int j = p_191142_1_; j <= p_191142_3_; ++j) {
               this.func_191144_a(j, i, p_191142_5_);
            }
         }

      }

      public int func_191145_a(int p_191145_1_, int p_191145_2_) {
         return p_191145_1_ >= 0 && p_191145_1_ < this.field_191149_b && p_191145_2_ >= 0 && p_191145_2_ < this.field_191150_c ? this.field_191148_a[p_191145_1_][p_191145_2_] : this.field_191151_d;
      }

      public void func_191141_a(int p_191141_1_, int p_191141_2_, int p_191141_3_, int p_191141_4_) {
         if (this.func_191145_a(p_191141_1_, p_191141_2_) == p_191141_3_) {
            this.func_191144_a(p_191141_1_, p_191141_2_, p_191141_4_);
         }

      }

      public boolean func_191147_b(int p_191147_1_, int p_191147_2_, int p_191147_3_) {
         return this.func_191145_a(p_191147_1_ - 1, p_191147_2_) == p_191147_3_ || this.func_191145_a(p_191147_1_ + 1, p_191147_2_) == p_191147_3_ || this.func_191145_a(p_191147_1_, p_191147_2_ + 1) == p_191147_3_ || this.func_191145_a(p_191147_1_, p_191147_2_ - 1) == p_191147_3_;
      }
   }

   static class ThirdFloor extends WoodlandMansionPieces.SecondFloor {
      private ThirdFloor() {
      }
   }
}
