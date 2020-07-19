package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class MapGenScatteredFeature extends MapGenStructure {
   private static final List<Biome> field_75061_e = Arrays.<Biome>asList(Biomes.field_76769_d, Biomes.field_76786_s, Biomes.field_76782_w, Biomes.field_76792_x, Biomes.field_76780_h, Biomes.field_76774_n, Biomes.field_150584_S);
   private final List<Biome.SpawnListEntry> field_82668_f;
   private int field_82669_g;
   private final int field_82670_h;

   public MapGenScatteredFeature() {
      this.field_82668_f = Lists.<Biome.SpawnListEntry>newArrayList();
      this.field_82669_g = 32;
      this.field_82670_h = 8;
      this.field_82668_f.add(new Biome.SpawnListEntry(EntityWitch.class, 1, 1, 1));
   }

   public MapGenScatteredFeature(Map<String, String> p_i2061_1_) {
      this();

      for(Entry<String, String> entry : p_i2061_1_.entrySet()) {
         if (((String)entry.getKey()).equals("distance")) {
            this.field_82669_g = MathHelper.func_82714_a(entry.getValue(), this.field_82669_g, 9);
         }
      }

   }

   public String func_143025_a() {
      return "Temple";
   }

   protected boolean func_75047_a(int p_75047_1_, int p_75047_2_) {
      int i = p_75047_1_;
      int j = p_75047_2_;
      if (p_75047_1_ < 0) {
         p_75047_1_ -= this.field_82669_g - 1;
      }

      if (p_75047_2_ < 0) {
         p_75047_2_ -= this.field_82669_g - 1;
      }

      int k = p_75047_1_ / this.field_82669_g;
      int l = p_75047_2_ / this.field_82669_g;
      Random random = this.field_75039_c.func_72843_D(k, l, 14357617);
      k = k * this.field_82669_g;
      l = l * this.field_82669_g;
      k = k + random.nextInt(this.field_82669_g - 8);
      l = l + random.nextInt(this.field_82669_g - 8);
      if (i == k && j == l) {
         Biome biome = this.field_75039_c.func_72959_q().func_180631_a(new BlockPos(i * 16 + 8, 0, j * 16 + 8));
         if (biome == null) {
            return false;
         }

         for(Biome biome1 : field_75061_e) {
            if (biome == biome1) {
               return true;
            }
         }
      }

      return false;
   }

   public BlockPos func_180706_b(World p_180706_1_, BlockPos p_180706_2_, boolean p_180706_3_) {
      this.field_75039_c = p_180706_1_;
      return func_191069_a(p_180706_1_, this, p_180706_2_, this.field_82669_g, 8, 14357617, false, 100, p_180706_3_);
   }

   protected StructureStart func_75049_b(int p_75049_1_, int p_75049_2_) {
      return new MapGenScatteredFeature.Start(this.field_75039_c, this.field_75038_b, p_75049_1_, p_75049_2_);
   }

   public boolean func_175798_a(BlockPos p_175798_1_) {
      StructureStart structurestart = this.func_175797_c(p_175798_1_);
      if (structurestart != null && structurestart instanceof MapGenScatteredFeature.Start && !structurestart.field_75075_a.isEmpty()) {
         StructureComponent structurecomponent = structurestart.field_75075_a.get(0);
         return structurecomponent instanceof ComponentScatteredFeaturePieces.SwampHut;
      } else {
         return false;
      }
   }

   public List<Biome.SpawnListEntry> func_82667_a() {
      return this.field_82668_f;
   }

   public static class Start extends StructureStart {
      public Start() {
      }

      public Start(World p_i2060_1_, Random p_i2060_2_, int p_i2060_3_, int p_i2060_4_) {
         this(p_i2060_1_, p_i2060_2_, p_i2060_3_, p_i2060_4_, p_i2060_1_.func_180494_b(new BlockPos(p_i2060_3_ * 16 + 8, 0, p_i2060_4_ * 16 + 8)));
      }

      public Start(World p_i47072_1_, Random p_i47072_2_, int p_i47072_3_, int p_i47072_4_, Biome p_i47072_5_) {
         super(p_i47072_3_, p_i47072_4_);
         if (p_i47072_5_ != Biomes.field_76782_w && p_i47072_5_ != Biomes.field_76792_x) {
            if (p_i47072_5_ == Biomes.field_76780_h) {
               ComponentScatteredFeaturePieces.SwampHut componentscatteredfeaturepieces$swamphut = new ComponentScatteredFeaturePieces.SwampHut(p_i47072_2_, p_i47072_3_ * 16, p_i47072_4_ * 16);
               this.field_75075_a.add(componentscatteredfeaturepieces$swamphut);
            } else if (p_i47072_5_ != Biomes.field_76769_d && p_i47072_5_ != Biomes.field_76786_s) {
               if (p_i47072_5_ == Biomes.field_76774_n || p_i47072_5_ == Biomes.field_150584_S) {
                  ComponentScatteredFeaturePieces.Igloo componentscatteredfeaturepieces$igloo = new ComponentScatteredFeaturePieces.Igloo(p_i47072_2_, p_i47072_3_ * 16, p_i47072_4_ * 16);
                  this.field_75075_a.add(componentscatteredfeaturepieces$igloo);
               }
            } else {
               ComponentScatteredFeaturePieces.DesertPyramid componentscatteredfeaturepieces$desertpyramid = new ComponentScatteredFeaturePieces.DesertPyramid(p_i47072_2_, p_i47072_3_ * 16, p_i47072_4_ * 16);
               this.field_75075_a.add(componentscatteredfeaturepieces$desertpyramid);
            }
         } else {
            ComponentScatteredFeaturePieces.JunglePyramid componentscatteredfeaturepieces$junglepyramid = new ComponentScatteredFeaturePieces.JunglePyramid(p_i47072_2_, p_i47072_3_ * 16, p_i47072_4_ * 16);
            this.field_75075_a.add(componentscatteredfeaturepieces$junglepyramid);
         }

         this.func_75072_c();
      }
   }
}
