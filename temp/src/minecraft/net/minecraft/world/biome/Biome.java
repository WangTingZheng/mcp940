package net.minecraft.world.biome;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenSwamp;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Biome {
   private static final Logger field_150586_aC = LogManager.getLogger();
   protected static final IBlockState field_185365_a = Blocks.field_150348_b.func_176223_P();
   protected static final IBlockState field_185366_b = Blocks.field_150350_a.func_176223_P();
   protected static final IBlockState field_185367_c = Blocks.field_150357_h.func_176223_P();
   protected static final IBlockState field_185368_d = Blocks.field_150351_n.func_176223_P();
   protected static final IBlockState field_185369_e = Blocks.field_180395_cM.func_176223_P();
   protected static final IBlockState field_185370_f = Blocks.field_150322_A.func_176223_P();
   protected static final IBlockState field_185371_g = Blocks.field_150432_aD.func_176223_P();
   protected static final IBlockState field_185372_h = Blocks.field_150355_j.func_176223_P();
   public static final ObjectIntIdentityMap<Biome> field_185373_j = new ObjectIntIdentityMap<Biome>();
   protected static final NoiseGeneratorPerlin field_150605_ac = new NoiseGeneratorPerlin(new Random(1234L), 1);
   protected static final NoiseGeneratorPerlin field_180281_af = new NoiseGeneratorPerlin(new Random(2345L), 1);
   protected static final WorldGenDoublePlant field_180280_ag = new WorldGenDoublePlant();
   protected static final WorldGenTrees field_76757_N = new WorldGenTrees(false);
   protected static final WorldGenBigTree field_76758_O = new WorldGenBigTree(false);
   protected static final WorldGenSwamp field_76763_Q = new WorldGenSwamp();
   public static final RegistryNamespaced<ResourceLocation, Biome> field_185377_q = new RegistryNamespaced<ResourceLocation, Biome>();
   private final String field_76791_y;
   private final float field_76748_D;
   private final float field_76749_E;
   private final float field_76750_F;
   private final float field_76751_G;
   private final int field_76759_H;
   private final boolean field_76766_R;
   private final boolean field_76765_S;
   @Nullable
   private final String field_185364_H;
   public IBlockState field_76752_A = Blocks.field_150349_c.func_176223_P();
   public IBlockState field_76753_B = Blocks.field_150346_d.func_176223_P();
   public BiomeDecorator field_76760_I;
   protected List<Biome.SpawnListEntry> field_76761_J = Lists.<Biome.SpawnListEntry>newArrayList();
   protected List<Biome.SpawnListEntry> field_76762_K = Lists.<Biome.SpawnListEntry>newArrayList();
   protected List<Biome.SpawnListEntry> field_76755_L = Lists.<Biome.SpawnListEntry>newArrayList();
   protected List<Biome.SpawnListEntry> field_82914_M = Lists.<Biome.SpawnListEntry>newArrayList();

   public static int func_185362_a(Biome p_185362_0_) {
      return field_185377_q.func_148757_b(p_185362_0_);
   }

   @Nullable
   public static Biome func_185357_a(int p_185357_0_) {
      return field_185377_q.func_148754_a(p_185357_0_);
   }

   @Nullable
   public static Biome func_185356_b(Biome p_185356_0_) {
      return field_185373_j.func_148745_a(func_185362_a(p_185356_0_));
   }

   protected Biome(Biome.BiomeProperties p_i46713_1_) {
      this.field_76791_y = p_i46713_1_.field_185412_a;
      this.field_76748_D = p_i46713_1_.field_185413_b;
      this.field_76749_E = p_i46713_1_.field_185414_c;
      this.field_76750_F = p_i46713_1_.field_185415_d;
      this.field_76751_G = p_i46713_1_.field_185416_e;
      this.field_76759_H = p_i46713_1_.field_185417_f;
      this.field_76766_R = p_i46713_1_.field_185418_g;
      this.field_76765_S = p_i46713_1_.field_185419_h;
      this.field_185364_H = p_i46713_1_.field_185420_i;
      this.field_76760_I = this.func_76729_a();
      this.field_76762_K.add(new Biome.SpawnListEntry(EntitySheep.class, 12, 4, 4));
      this.field_76762_K.add(new Biome.SpawnListEntry(EntityPig.class, 10, 4, 4));
      this.field_76762_K.add(new Biome.SpawnListEntry(EntityChicken.class, 10, 4, 4));
      this.field_76762_K.add(new Biome.SpawnListEntry(EntityCow.class, 8, 4, 4));
      this.field_76761_J.add(new Biome.SpawnListEntry(EntitySpider.class, 100, 4, 4));
      this.field_76761_J.add(new Biome.SpawnListEntry(EntityZombie.class, 95, 4, 4));
      this.field_76761_J.add(new Biome.SpawnListEntry(EntityZombieVillager.class, 5, 1, 1));
      this.field_76761_J.add(new Biome.SpawnListEntry(EntitySkeleton.class, 100, 4, 4));
      this.field_76761_J.add(new Biome.SpawnListEntry(EntityCreeper.class, 100, 4, 4));
      this.field_76761_J.add(new Biome.SpawnListEntry(EntitySlime.class, 100, 4, 4));
      this.field_76761_J.add(new Biome.SpawnListEntry(EntityEnderman.class, 10, 1, 4));
      this.field_76761_J.add(new Biome.SpawnListEntry(EntityWitch.class, 5, 1, 1));
      this.field_76755_L.add(new Biome.SpawnListEntry(EntitySquid.class, 10, 4, 4));
      this.field_82914_M.add(new Biome.SpawnListEntry(EntityBat.class, 10, 8, 8));
   }

   protected BiomeDecorator func_76729_a() {
      return new BiomeDecorator();
   }

   public boolean func_185363_b() {
      return this.field_185364_H != null;
   }

   public WorldGenAbstractTree func_150567_a(Random p_150567_1_) {
      return (WorldGenAbstractTree)(p_150567_1_.nextInt(10) == 0 ? field_76758_O : field_76757_N);
   }

   public WorldGenerator func_76730_b(Random p_76730_1_) {
      return new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
   }

   public BlockFlower.EnumFlowerType func_180623_a(Random p_180623_1_, BlockPos p_180623_2_) {
      return p_180623_1_.nextInt(3) > 0 ? BlockFlower.EnumFlowerType.DANDELION : BlockFlower.EnumFlowerType.POPPY;
   }

   public int func_76731_a(float p_76731_1_) {
      p_76731_1_ = p_76731_1_ / 3.0F;
      p_76731_1_ = MathHelper.func_76131_a(p_76731_1_, -1.0F, 1.0F);
      return MathHelper.func_181758_c(0.62222224F - p_76731_1_ * 0.05F, 0.5F + p_76731_1_ * 0.1F, 1.0F);
   }

   public List<Biome.SpawnListEntry> func_76747_a(EnumCreatureType p_76747_1_) {
      switch(p_76747_1_) {
      case MONSTER:
         return this.field_76761_J;
      case CREATURE:
         return this.field_76762_K;
      case WATER_CREATURE:
         return this.field_76755_L;
      case AMBIENT:
         return this.field_82914_M;
      default:
         return Collections.<Biome.SpawnListEntry>emptyList();
      }
   }

   public boolean func_76746_c() {
      return this.func_150559_j();
   }

   public boolean func_76738_d() {
      return this.func_150559_j() ? false : this.field_76765_S;
   }

   public boolean func_76736_e() {
      return this.func_76727_i() > 0.85F;
   }

   public float func_76741_f() {
      return 0.1F;
   }

   public final float func_180626_a(BlockPos p_180626_1_) {
      if (p_180626_1_.func_177956_o() > 64) {
         float f = (float)(field_150605_ac.func_151601_a((double)((float)p_180626_1_.func_177958_n() / 8.0F), (double)((float)p_180626_1_.func_177952_p() / 8.0F)) * 4.0D);
         return this.func_185353_n() - (f + (float)p_180626_1_.func_177956_o() - 64.0F) * 0.05F / 30.0F;
      } else {
         return this.func_185353_n();
      }
   }

   public void func_180624_a(World p_180624_1_, Random p_180624_2_, BlockPos p_180624_3_) {
      this.field_76760_I.func_180292_a(p_180624_1_, p_180624_2_, this, p_180624_3_);
   }

   public int func_180627_b(BlockPos p_180627_1_) {
      double d0 = (double)MathHelper.func_76131_a(this.func_180626_a(p_180627_1_), 0.0F, 1.0F);
      double d1 = (double)MathHelper.func_76131_a(this.func_76727_i(), 0.0F, 1.0F);
      return ColorizerGrass.func_77480_a(d0, d1);
   }

   public int func_180625_c(BlockPos p_180625_1_) {
      double d0 = (double)MathHelper.func_76131_a(this.func_180626_a(p_180625_1_), 0.0F, 1.0F);
      double d1 = (double)MathHelper.func_76131_a(this.func_76727_i(), 0.0F, 1.0F);
      return ColorizerFoliage.func_77470_a(d0, d1);
   }

   public void func_180622_a(World p_180622_1_, Random p_180622_2_, ChunkPrimer p_180622_3_, int p_180622_4_, int p_180622_5_, double p_180622_6_) {
      this.func_180628_b(p_180622_1_, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
   }

   public final void func_180628_b(World p_180628_1_, Random p_180628_2_, ChunkPrimer p_180628_3_, int p_180628_4_, int p_180628_5_, double p_180628_6_) {
      int i = p_180628_1_.func_181545_F();
      IBlockState iblockstate = this.field_76752_A;
      IBlockState iblockstate1 = this.field_76753_B;
      int j = -1;
      int k = (int)(p_180628_6_ / 3.0D + 3.0D + p_180628_2_.nextDouble() * 0.25D);
      int l = p_180628_4_ & 15;
      int i1 = p_180628_5_ & 15;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int j1 = 255; j1 >= 0; --j1) {
         if (j1 <= p_180628_2_.nextInt(5)) {
            p_180628_3_.func_177855_a(i1, j1, l, field_185367_c);
         } else {
            IBlockState iblockstate2 = p_180628_3_.func_177856_a(i1, j1, l);
            if (iblockstate2.func_185904_a() == Material.field_151579_a) {
               j = -1;
            } else if (iblockstate2.func_177230_c() == Blocks.field_150348_b) {
               if (j == -1) {
                  if (k <= 0) {
                     iblockstate = field_185366_b;
                     iblockstate1 = field_185365_a;
                  } else if (j1 >= i - 4 && j1 <= i + 1) {
                     iblockstate = this.field_76752_A;
                     iblockstate1 = this.field_76753_B;
                  }

                  if (j1 < i && (iblockstate == null || iblockstate.func_185904_a() == Material.field_151579_a)) {
                     if (this.func_180626_a(blockpos$mutableblockpos.func_181079_c(p_180628_4_, j1, p_180628_5_)) < 0.15F) {
                        iblockstate = field_185371_g;
                     } else {
                        iblockstate = field_185372_h;
                     }
                  }

                  j = k;
                  if (j1 >= i - 1) {
                     p_180628_3_.func_177855_a(i1, j1, l, iblockstate);
                  } else if (j1 < i - 7 - k) {
                     iblockstate = field_185366_b;
                     iblockstate1 = field_185365_a;
                     p_180628_3_.func_177855_a(i1, j1, l, field_185368_d);
                  } else {
                     p_180628_3_.func_177855_a(i1, j1, l, iblockstate1);
                  }
               } else if (j > 0) {
                  --j;
                  p_180628_3_.func_177855_a(i1, j1, l, iblockstate1);
                  if (j == 0 && iblockstate1.func_177230_c() == Blocks.field_150354_m && k > 1) {
                     j = p_180628_2_.nextInt(4) + Math.max(0, j1 - 63);
                     iblockstate1 = iblockstate1.func_177229_b(BlockSand.field_176504_a) == BlockSand.EnumType.RED_SAND ? field_185369_e : field_185370_f;
                  }
               }
            }
         }
      }

   }

   public Class<? extends Biome> func_150562_l() {
      return this.getClass();
   }

   public Biome.TempCategory func_150561_m() {
      if ((double)this.func_185353_n() < 0.2D) {
         return Biome.TempCategory.COLD;
      } else {
         return (double)this.func_185353_n() < 1.0D ? Biome.TempCategory.MEDIUM : Biome.TempCategory.WARM;
      }
   }

   @Nullable
   public static Biome func_150568_d(int p_150568_0_) {
      return func_180276_a(p_150568_0_, (Biome)null);
   }

   public static Biome func_180276_a(int p_180276_0_, Biome p_180276_1_) {
      Biome biome = func_185357_a(p_180276_0_);
      return biome == null ? p_180276_1_ : biome;
   }

   public boolean func_185352_i() {
      return false;
   }

   public final float func_185355_j() {
      return this.field_76748_D;
   }

   public final float func_76727_i() {
      return this.field_76751_G;
   }

   public final String func_185359_l() {
      return this.field_76791_y;
   }

   public final float func_185360_m() {
      return this.field_76749_E;
   }

   public final float func_185353_n() {
      return this.field_76750_F;
   }

   public final int func_185361_o() {
      return this.field_76759_H;
   }

   public final boolean func_150559_j() {
      return this.field_76766_R;
   }

   public static void func_185358_q() {
      func_185354_a(0, "ocean", new BiomeOcean((new Biome.BiomeProperties("Ocean")).func_185398_c(-1.0F).func_185400_d(0.1F)));
      func_185354_a(1, "plains", new BiomePlains(false, (new Biome.BiomeProperties("Plains")).func_185398_c(0.125F).func_185400_d(0.05F).func_185410_a(0.8F).func_185395_b(0.4F)));
      func_185354_a(2, "desert", new BiomeDesert((new Biome.BiomeProperties("Desert")).func_185398_c(0.125F).func_185400_d(0.05F).func_185410_a(2.0F).func_185395_b(0.0F).func_185396_a()));
      func_185354_a(3, "extreme_hills", new BiomeHills(BiomeHills.Type.NORMAL, (new Biome.BiomeProperties("Extreme Hills")).func_185398_c(1.0F).func_185400_d(0.5F).func_185410_a(0.2F).func_185395_b(0.3F)));
      func_185354_a(4, "forest", new BiomeForest(BiomeForest.Type.NORMAL, (new Biome.BiomeProperties("Forest")).func_185410_a(0.7F).func_185395_b(0.8F)));
      func_185354_a(5, "taiga", new BiomeTaiga(BiomeTaiga.Type.NORMAL, (new Biome.BiomeProperties("Taiga")).func_185398_c(0.2F).func_185400_d(0.2F).func_185410_a(0.25F).func_185395_b(0.8F)));
      func_185354_a(6, "swampland", new BiomeSwamp((new Biome.BiomeProperties("Swampland")).func_185398_c(-0.2F).func_185400_d(0.1F).func_185410_a(0.8F).func_185395_b(0.9F).func_185402_a(14745518)));
      func_185354_a(7, "river", new BiomeRiver((new Biome.BiomeProperties("River")).func_185398_c(-0.5F).func_185400_d(0.0F)));
      func_185354_a(8, "hell", new BiomeHell((new Biome.BiomeProperties("Hell")).func_185410_a(2.0F).func_185395_b(0.0F).func_185396_a()));
      func_185354_a(9, "sky", new BiomeEnd((new Biome.BiomeProperties("The End")).func_185396_a()));
      func_185354_a(10, "frozen_ocean", new BiomeOcean((new Biome.BiomeProperties("FrozenOcean")).func_185398_c(-1.0F).func_185400_d(0.1F).func_185410_a(0.0F).func_185395_b(0.5F).func_185411_b()));
      func_185354_a(11, "frozen_river", new BiomeRiver((new Biome.BiomeProperties("FrozenRiver")).func_185398_c(-0.5F).func_185400_d(0.0F).func_185410_a(0.0F).func_185395_b(0.5F).func_185411_b()));
      func_185354_a(12, "ice_flats", new BiomeSnow(false, (new Biome.BiomeProperties("Ice Plains")).func_185398_c(0.125F).func_185400_d(0.05F).func_185410_a(0.0F).func_185395_b(0.5F).func_185411_b()));
      func_185354_a(13, "ice_mountains", new BiomeSnow(false, (new Biome.BiomeProperties("Ice Mountains")).func_185398_c(0.45F).func_185400_d(0.3F).func_185410_a(0.0F).func_185395_b(0.5F).func_185411_b()));
      func_185354_a(14, "mushroom_island", new BiomeMushroomIsland((new Biome.BiomeProperties("MushroomIsland")).func_185398_c(0.2F).func_185400_d(0.3F).func_185410_a(0.9F).func_185395_b(1.0F)));
      func_185354_a(15, "mushroom_island_shore", new BiomeMushroomIsland((new Biome.BiomeProperties("MushroomIslandShore")).func_185398_c(0.0F).func_185400_d(0.025F).func_185410_a(0.9F).func_185395_b(1.0F)));
      func_185354_a(16, "beaches", new BiomeBeach((new Biome.BiomeProperties("Beach")).func_185398_c(0.0F).func_185400_d(0.025F).func_185410_a(0.8F).func_185395_b(0.4F)));
      func_185354_a(17, "desert_hills", new BiomeDesert((new Biome.BiomeProperties("DesertHills")).func_185398_c(0.45F).func_185400_d(0.3F).func_185410_a(2.0F).func_185395_b(0.0F).func_185396_a()));
      func_185354_a(18, "forest_hills", new BiomeForest(BiomeForest.Type.NORMAL, (new Biome.BiomeProperties("ForestHills")).func_185398_c(0.45F).func_185400_d(0.3F).func_185410_a(0.7F).func_185395_b(0.8F)));
      func_185354_a(19, "taiga_hills", new BiomeTaiga(BiomeTaiga.Type.NORMAL, (new Biome.BiomeProperties("TaigaHills")).func_185410_a(0.25F).func_185395_b(0.8F).func_185398_c(0.45F).func_185400_d(0.3F)));
      func_185354_a(20, "smaller_extreme_hills", new BiomeHills(BiomeHills.Type.EXTRA_TREES, (new Biome.BiomeProperties("Extreme Hills Edge")).func_185398_c(0.8F).func_185400_d(0.3F).func_185410_a(0.2F).func_185395_b(0.3F)));
      func_185354_a(21, "jungle", new BiomeJungle(false, (new Biome.BiomeProperties("Jungle")).func_185410_a(0.95F).func_185395_b(0.9F)));
      func_185354_a(22, "jungle_hills", new BiomeJungle(false, (new Biome.BiomeProperties("JungleHills")).func_185398_c(0.45F).func_185400_d(0.3F).func_185410_a(0.95F).func_185395_b(0.9F)));
      func_185354_a(23, "jungle_edge", new BiomeJungle(true, (new Biome.BiomeProperties("JungleEdge")).func_185410_a(0.95F).func_185395_b(0.8F)));
      func_185354_a(24, "deep_ocean", new BiomeOcean((new Biome.BiomeProperties("Deep Ocean")).func_185398_c(-1.8F).func_185400_d(0.1F)));
      func_185354_a(25, "stone_beach", new BiomeStoneBeach((new Biome.BiomeProperties("Stone Beach")).func_185398_c(0.1F).func_185400_d(0.8F).func_185410_a(0.2F).func_185395_b(0.3F)));
      func_185354_a(26, "cold_beach", new BiomeBeach((new Biome.BiomeProperties("Cold Beach")).func_185398_c(0.0F).func_185400_d(0.025F).func_185410_a(0.05F).func_185395_b(0.3F).func_185411_b()));
      func_185354_a(27, "birch_forest", new BiomeForest(BiomeForest.Type.BIRCH, (new Biome.BiomeProperties("Birch Forest")).func_185410_a(0.6F).func_185395_b(0.6F)));
      func_185354_a(28, "birch_forest_hills", new BiomeForest(BiomeForest.Type.BIRCH, (new Biome.BiomeProperties("Birch Forest Hills")).func_185398_c(0.45F).func_185400_d(0.3F).func_185410_a(0.6F).func_185395_b(0.6F)));
      func_185354_a(29, "roofed_forest", new BiomeForest(BiomeForest.Type.ROOFED, (new Biome.BiomeProperties("Roofed Forest")).func_185410_a(0.7F).func_185395_b(0.8F)));
      func_185354_a(30, "taiga_cold", new BiomeTaiga(BiomeTaiga.Type.NORMAL, (new Biome.BiomeProperties("Cold Taiga")).func_185398_c(0.2F).func_185400_d(0.2F).func_185410_a(-0.5F).func_185395_b(0.4F).func_185411_b()));
      func_185354_a(31, "taiga_cold_hills", new BiomeTaiga(BiomeTaiga.Type.NORMAL, (new Biome.BiomeProperties("Cold Taiga Hills")).func_185398_c(0.45F).func_185400_d(0.3F).func_185410_a(-0.5F).func_185395_b(0.4F).func_185411_b()));
      func_185354_a(32, "redwood_taiga", new BiomeTaiga(BiomeTaiga.Type.MEGA, (new Biome.BiomeProperties("Mega Taiga")).func_185410_a(0.3F).func_185395_b(0.8F).func_185398_c(0.2F).func_185400_d(0.2F)));
      func_185354_a(33, "redwood_taiga_hills", new BiomeTaiga(BiomeTaiga.Type.MEGA, (new Biome.BiomeProperties("Mega Taiga Hills")).func_185398_c(0.45F).func_185400_d(0.3F).func_185410_a(0.3F).func_185395_b(0.8F)));
      func_185354_a(34, "extreme_hills_with_trees", new BiomeHills(BiomeHills.Type.EXTRA_TREES, (new Biome.BiomeProperties("Extreme Hills+")).func_185398_c(1.0F).func_185400_d(0.5F).func_185410_a(0.2F).func_185395_b(0.3F)));
      func_185354_a(35, "savanna", new BiomeSavanna((new Biome.BiomeProperties("Savanna")).func_185398_c(0.125F).func_185400_d(0.05F).func_185410_a(1.2F).func_185395_b(0.0F).func_185396_a()));
      func_185354_a(36, "savanna_rock", new BiomeSavanna((new Biome.BiomeProperties("Savanna Plateau")).func_185398_c(1.5F).func_185400_d(0.025F).func_185410_a(1.0F).func_185395_b(0.0F).func_185396_a()));
      func_185354_a(37, "mesa", new BiomeMesa(false, false, (new Biome.BiomeProperties("Mesa")).func_185410_a(2.0F).func_185395_b(0.0F).func_185396_a()));
      func_185354_a(38, "mesa_rock", new BiomeMesa(false, true, (new Biome.BiomeProperties("Mesa Plateau F")).func_185398_c(1.5F).func_185400_d(0.025F).func_185410_a(2.0F).func_185395_b(0.0F).func_185396_a()));
      func_185354_a(39, "mesa_clear_rock", new BiomeMesa(false, false, (new Biome.BiomeProperties("Mesa Plateau")).func_185398_c(1.5F).func_185400_d(0.025F).func_185410_a(2.0F).func_185395_b(0.0F).func_185396_a()));
      func_185354_a(127, "void", new BiomeVoid((new Biome.BiomeProperties("The Void")).func_185396_a()));
      func_185354_a(129, "mutated_plains", new BiomePlains(true, (new Biome.BiomeProperties("Sunflower Plains")).func_185399_a("plains").func_185398_c(0.125F).func_185400_d(0.05F).func_185410_a(0.8F).func_185395_b(0.4F)));
      func_185354_a(130, "mutated_desert", new BiomeDesert((new Biome.BiomeProperties("Desert M")).func_185399_a("desert").func_185398_c(0.225F).func_185400_d(0.25F).func_185410_a(2.0F).func_185395_b(0.0F).func_185396_a()));
      func_185354_a(131, "mutated_extreme_hills", new BiomeHills(BiomeHills.Type.MUTATED, (new Biome.BiomeProperties("Extreme Hills M")).func_185399_a("extreme_hills").func_185398_c(1.0F).func_185400_d(0.5F).func_185410_a(0.2F).func_185395_b(0.3F)));
      func_185354_a(132, "mutated_forest", new BiomeForest(BiomeForest.Type.FLOWER, (new Biome.BiomeProperties("Flower Forest")).func_185399_a("forest").func_185400_d(0.4F).func_185410_a(0.7F).func_185395_b(0.8F)));
      func_185354_a(133, "mutated_taiga", new BiomeTaiga(BiomeTaiga.Type.NORMAL, (new Biome.BiomeProperties("Taiga M")).func_185399_a("taiga").func_185398_c(0.3F).func_185400_d(0.4F).func_185410_a(0.25F).func_185395_b(0.8F)));
      func_185354_a(134, "mutated_swampland", new BiomeSwamp((new Biome.BiomeProperties("Swampland M")).func_185399_a("swampland").func_185398_c(-0.1F).func_185400_d(0.3F).func_185410_a(0.8F).func_185395_b(0.9F).func_185402_a(14745518)));
      func_185354_a(140, "mutated_ice_flats", new BiomeSnow(true, (new Biome.BiomeProperties("Ice Plains Spikes")).func_185399_a("ice_flats").func_185398_c(0.425F).func_185400_d(0.45000002F).func_185410_a(0.0F).func_185395_b(0.5F).func_185411_b()));
      func_185354_a(149, "mutated_jungle", new BiomeJungle(false, (new Biome.BiomeProperties("Jungle M")).func_185399_a("jungle").func_185398_c(0.2F).func_185400_d(0.4F).func_185410_a(0.95F).func_185395_b(0.9F)));
      func_185354_a(151, "mutated_jungle_edge", new BiomeJungle(true, (new Biome.BiomeProperties("JungleEdge M")).func_185399_a("jungle_edge").func_185398_c(0.2F).func_185400_d(0.4F).func_185410_a(0.95F).func_185395_b(0.8F)));
      func_185354_a(155, "mutated_birch_forest", new BiomeForestMutated((new Biome.BiomeProperties("Birch Forest M")).func_185399_a("birch_forest").func_185398_c(0.2F).func_185400_d(0.4F).func_185410_a(0.6F).func_185395_b(0.6F)));
      func_185354_a(156, "mutated_birch_forest_hills", new BiomeForestMutated((new Biome.BiomeProperties("Birch Forest Hills M")).func_185399_a("birch_forest_hills").func_185398_c(0.55F).func_185400_d(0.5F).func_185410_a(0.6F).func_185395_b(0.6F)));
      func_185354_a(157, "mutated_roofed_forest", new BiomeForest(BiomeForest.Type.ROOFED, (new Biome.BiomeProperties("Roofed Forest M")).func_185399_a("roofed_forest").func_185398_c(0.2F).func_185400_d(0.4F).func_185410_a(0.7F).func_185395_b(0.8F)));
      func_185354_a(158, "mutated_taiga_cold", new BiomeTaiga(BiomeTaiga.Type.NORMAL, (new Biome.BiomeProperties("Cold Taiga M")).func_185399_a("taiga_cold").func_185398_c(0.3F).func_185400_d(0.4F).func_185410_a(-0.5F).func_185395_b(0.4F).func_185411_b()));
      func_185354_a(160, "mutated_redwood_taiga", new BiomeTaiga(BiomeTaiga.Type.MEGA_SPRUCE, (new Biome.BiomeProperties("Mega Spruce Taiga")).func_185399_a("redwood_taiga").func_185398_c(0.2F).func_185400_d(0.2F).func_185410_a(0.25F).func_185395_b(0.8F)));
      func_185354_a(161, "mutated_redwood_taiga_hills", new BiomeTaiga(BiomeTaiga.Type.MEGA_SPRUCE, (new Biome.BiomeProperties("Redwood Taiga Hills M")).func_185399_a("redwood_taiga_hills").func_185398_c(0.2F).func_185400_d(0.2F).func_185410_a(0.25F).func_185395_b(0.8F)));
      func_185354_a(162, "mutated_extreme_hills_with_trees", new BiomeHills(BiomeHills.Type.MUTATED, (new Biome.BiomeProperties("Extreme Hills+ M")).func_185399_a("extreme_hills_with_trees").func_185398_c(1.0F).func_185400_d(0.5F).func_185410_a(0.2F).func_185395_b(0.3F)));
      func_185354_a(163, "mutated_savanna", new BiomeSavannaMutated((new Biome.BiomeProperties("Savanna M")).func_185399_a("savanna").func_185398_c(0.3625F).func_185400_d(1.225F).func_185410_a(1.1F).func_185395_b(0.0F).func_185396_a()));
      func_185354_a(164, "mutated_savanna_rock", new BiomeSavannaMutated((new Biome.BiomeProperties("Savanna Plateau M")).func_185399_a("savanna_rock").func_185398_c(1.05F).func_185400_d(1.2125001F).func_185410_a(1.0F).func_185395_b(0.0F).func_185396_a()));
      func_185354_a(165, "mutated_mesa", new BiomeMesa(true, false, (new Biome.BiomeProperties("Mesa (Bryce)")).func_185399_a("mesa").func_185410_a(2.0F).func_185395_b(0.0F).func_185396_a()));
      func_185354_a(166, "mutated_mesa_rock", new BiomeMesa(false, true, (new Biome.BiomeProperties("Mesa Plateau F M")).func_185399_a("mesa_rock").func_185398_c(0.45F).func_185400_d(0.3F).func_185410_a(2.0F).func_185395_b(0.0F).func_185396_a()));
      func_185354_a(167, "mutated_mesa_clear_rock", new BiomeMesa(false, false, (new Biome.BiomeProperties("Mesa Plateau M")).func_185399_a("mesa_clear_rock").func_185398_c(0.45F).func_185400_d(0.3F).func_185410_a(2.0F).func_185395_b(0.0F).func_185396_a()));
   }

   private static void func_185354_a(int p_185354_0_, String p_185354_1_, Biome p_185354_2_) {
      field_185377_q.func_177775_a(p_185354_0_, new ResourceLocation(p_185354_1_), p_185354_2_);
      if (p_185354_2_.func_185363_b()) {
         field_185373_j.func_148746_a(p_185354_2_, func_185362_a(field_185377_q.func_82594_a(new ResourceLocation(p_185354_2_.field_185364_H))));
      }

   }

   public static class BiomeProperties {
      private final String field_185412_a;
      private float field_185413_b = 0.1F;
      private float field_185414_c = 0.2F;
      private float field_185415_d = 0.5F;
      private float field_185416_e = 0.5F;
      private int field_185417_f = 16777215;
      private boolean field_185418_g;
      private boolean field_185419_h = true;
      @Nullable
      private String field_185420_i;

      public BiomeProperties(String p_i47073_1_) {
         this.field_185412_a = p_i47073_1_;
      }

      protected Biome.BiomeProperties func_185410_a(float p_185410_1_) {
         if (p_185410_1_ > 0.1F && p_185410_1_ < 0.2F) {
            throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
         } else {
            this.field_185415_d = p_185410_1_;
            return this;
         }
      }

      protected Biome.BiomeProperties func_185395_b(float p_185395_1_) {
         this.field_185416_e = p_185395_1_;
         return this;
      }

      protected Biome.BiomeProperties func_185398_c(float p_185398_1_) {
         this.field_185413_b = p_185398_1_;
         return this;
      }

      protected Biome.BiomeProperties func_185400_d(float p_185400_1_) {
         this.field_185414_c = p_185400_1_;
         return this;
      }

      protected Biome.BiomeProperties func_185396_a() {
         this.field_185419_h = false;
         return this;
      }

      protected Biome.BiomeProperties func_185411_b() {
         this.field_185418_g = true;
         return this;
      }

      protected Biome.BiomeProperties func_185402_a(int p_185402_1_) {
         this.field_185417_f = p_185402_1_;
         return this;
      }

      protected Biome.BiomeProperties func_185399_a(String p_185399_1_) {
         this.field_185420_i = p_185399_1_;
         return this;
      }
   }

   public static class SpawnListEntry extends WeightedRandom.Item {
      public Class<? extends EntityLiving> field_76300_b;
      public int field_76301_c;
      public int field_76299_d;

      public SpawnListEntry(Class<? extends EntityLiving> p_i1970_1_, int p_i1970_2_, int p_i1970_3_, int p_i1970_4_) {
         super(p_i1970_2_);
         this.field_76300_b = p_i1970_1_;
         this.field_76301_c = p_i1970_3_;
         this.field_76299_d = p_i1970_4_;
      }

      public String toString() {
         return this.field_76300_b.getSimpleName() + "*(" + this.field_76301_c + "-" + this.field_76299_d + "):" + this.field_76292_a;
      }
   }

   public static enum TempCategory {
      OCEAN,
      COLD,
      MEDIUM,
      WARM;
   }
}
