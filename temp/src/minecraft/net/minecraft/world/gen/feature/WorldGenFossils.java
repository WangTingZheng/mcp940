package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class WorldGenFossils extends WorldGenerator {
   private static final ResourceLocation field_189890_a = new ResourceLocation("fossils/fossil_spine_01");
   private static final ResourceLocation field_189891_b = new ResourceLocation("fossils/fossil_spine_02");
   private static final ResourceLocation field_189892_c = new ResourceLocation("fossils/fossil_spine_03");
   private static final ResourceLocation field_189893_d = new ResourceLocation("fossils/fossil_spine_04");
   private static final ResourceLocation field_189894_e = new ResourceLocation("fossils/fossil_spine_01_coal");
   private static final ResourceLocation field_189895_f = new ResourceLocation("fossils/fossil_spine_02_coal");
   private static final ResourceLocation field_189896_g = new ResourceLocation("fossils/fossil_spine_03_coal");
   private static final ResourceLocation field_189897_h = new ResourceLocation("fossils/fossil_spine_04_coal");
   private static final ResourceLocation field_189898_i = new ResourceLocation("fossils/fossil_skull_01");
   private static final ResourceLocation field_189899_j = new ResourceLocation("fossils/fossil_skull_02");
   private static final ResourceLocation field_189900_k = new ResourceLocation("fossils/fossil_skull_03");
   private static final ResourceLocation field_189901_l = new ResourceLocation("fossils/fossil_skull_04");
   private static final ResourceLocation field_189902_m = new ResourceLocation("fossils/fossil_skull_01_coal");
   private static final ResourceLocation field_189903_n = new ResourceLocation("fossils/fossil_skull_02_coal");
   private static final ResourceLocation field_189904_o = new ResourceLocation("fossils/fossil_skull_03_coal");
   private static final ResourceLocation field_189905_p = new ResourceLocation("fossils/fossil_skull_04_coal");
   private static final ResourceLocation[] field_189906_q = new ResourceLocation[]{field_189890_a, field_189891_b, field_189892_c, field_189893_d, field_189898_i, field_189899_j, field_189900_k, field_189901_l};
   private static final ResourceLocation[] field_189907_r = new ResourceLocation[]{field_189894_e, field_189895_f, field_189896_g, field_189897_h, field_189902_m, field_189903_n, field_189904_o, field_189905_p};

   public boolean func_180709_b(World p_180709_1_, Random p_180709_2_, BlockPos p_180709_3_) {
      Random random = p_180709_1_.func_175726_f(p_180709_3_).func_76617_a(987234911L);
      MinecraftServer minecraftserver = p_180709_1_.func_73046_m();
      Rotation[] arotation = Rotation.values();
      Rotation rotation = arotation[random.nextInt(arotation.length)];
      int i = random.nextInt(field_189906_q.length);
      TemplateManager templatemanager = p_180709_1_.func_72860_G().func_186340_h();
      Template template = templatemanager.func_186237_a(minecraftserver, field_189906_q[i]);
      Template template1 = templatemanager.func_186237_a(minecraftserver, field_189907_r[i]);
      ChunkPos chunkpos = new ChunkPos(p_180709_3_);
      StructureBoundingBox structureboundingbox = new StructureBoundingBox(chunkpos.func_180334_c(), 0, chunkpos.func_180333_d(), chunkpos.func_180332_e(), 256, chunkpos.func_180330_f());
      PlacementSettings placementsettings = (new PlacementSettings()).func_186220_a(rotation).func_186223_a(structureboundingbox).func_189950_a(random);
      BlockPos blockpos = template.func_186257_a(rotation);
      int j = random.nextInt(16 - blockpos.func_177958_n());
      int k = random.nextInt(16 - blockpos.func_177952_p());
      int l = 256;

      for(int i1 = 0; i1 < blockpos.func_177958_n(); ++i1) {
         for(int j1 = 0; j1 < blockpos.func_177958_n(); ++j1) {
            l = Math.min(l, p_180709_1_.func_189649_b(p_180709_3_.func_177958_n() + i1 + j, p_180709_3_.func_177952_p() + j1 + k));
         }
      }

      int k1 = Math.max(l - 15 - random.nextInt(10), 10);
      BlockPos blockpos1 = template.func_189961_a(p_180709_3_.func_177982_a(j, k1, k), Mirror.NONE, rotation);
      placementsettings.func_189946_a(0.9F);
      template.func_189962_a(p_180709_1_, blockpos1, placementsettings, 20);
      placementsettings.func_189946_a(0.1F);
      template1.func_189962_a(p_180709_1_, blockpos1, placementsettings, 20);
      return true;
   }
}
