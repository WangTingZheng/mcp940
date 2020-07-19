package net.minecraft.world;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.advancements.FunctionManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEventData;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.INpc;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketBlockAction;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketParticles;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.ScoreboardSaveData;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerChunkMap;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ReportedException;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.VillageCollection;
import net.minecraft.village.VillageSiege;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.feature.WorldGeneratorBonusChest;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;
import net.minecraft.world.storage.WorldSavedDataCallableSave;
import net.minecraft.world.storage.loot.LootTableManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldServer extends World implements IThreadListener {
   private static final Logger field_147491_a = LogManager.getLogger();
   private final MinecraftServer field_73061_a;
   private final EntityTracker field_73062_L;
   private final PlayerChunkMap field_73063_M;
   private final Set<NextTickListEntry> field_73064_N = Sets.<NextTickListEntry>newHashSet();
   private final TreeSet<NextTickListEntry> field_73065_O = new TreeSet<NextTickListEntry>();
   private final Map<UUID, Entity> field_175741_N = Maps.<UUID, Entity>newHashMap();
   public boolean field_73058_d;
   private boolean field_73068_P;
   private int field_80004_Q;
   private final Teleporter field_85177_Q;
   private final WorldEntitySpawner field_175742_R = new WorldEntitySpawner();
   protected final VillageSiege field_175740_d = new VillageSiege(this);
   private final WorldServer.ServerBlockEventList[] field_147490_S = new WorldServer.ServerBlockEventList[]{new WorldServer.ServerBlockEventList(), new WorldServer.ServerBlockEventList()};
   private int field_147489_T;
   private final List<NextTickListEntry> field_94579_S = Lists.<NextTickListEntry>newArrayList();

   public WorldServer(MinecraftServer p_i45921_1_, ISaveHandler p_i45921_2_, WorldInfo p_i45921_3_, int p_i45921_4_, Profiler p_i45921_5_) {
      super(p_i45921_2_, p_i45921_3_, DimensionType.func_186069_a(p_i45921_4_).func_186070_d(), p_i45921_5_, false);
      this.field_73061_a = p_i45921_1_;
      this.field_73062_L = new EntityTracker(this);
      this.field_73063_M = new PlayerChunkMap(this);
      this.field_73011_w.func_76558_a(this);
      this.field_73020_y = this.func_72970_h();
      this.field_85177_Q = new Teleporter(this);
      this.func_72966_v();
      this.func_72947_a();
      this.func_175723_af().func_177725_a(p_i45921_1_.func_175580_aG());
   }

   public World func_175643_b() {
      this.field_72988_C = new MapStorage(this.field_73019_z);
      String s = VillageCollection.func_176062_a(this.field_73011_w);
      VillageCollection villagecollection = (VillageCollection)this.field_72988_C.func_75742_a(VillageCollection.class, s);
      if (villagecollection == null) {
         this.field_72982_D = new VillageCollection(this);
         this.field_72988_C.func_75745_a(s, this.field_72982_D);
      } else {
         this.field_72982_D = villagecollection;
         this.field_72982_D.func_82566_a(this);
      }

      this.field_96442_D = new ServerScoreboard(this.field_73061_a);
      ScoreboardSaveData scoreboardsavedata = (ScoreboardSaveData)this.field_72988_C.func_75742_a(ScoreboardSaveData.class, "scoreboard");
      if (scoreboardsavedata == null) {
         scoreboardsavedata = new ScoreboardSaveData();
         this.field_72988_C.func_75745_a("scoreboard", scoreboardsavedata);
      }

      scoreboardsavedata.func_96499_a(this.field_96442_D);
      ((ServerScoreboard)this.field_96442_D).func_186684_a(new WorldSavedDataCallableSave(scoreboardsavedata));
      this.field_184151_B = new LootTableManager(new File(new File(this.field_73019_z.func_75765_b(), "data"), "loot_tables"));
      this.field_191951_C = new AdvancementManager(new File(new File(this.field_73019_z.func_75765_b(), "data"), "advancements"));
      this.field_193036_D = new FunctionManager(new File(new File(this.field_73019_z.func_75765_b(), "data"), "functions"), this.field_73061_a);
      this.func_175723_af().func_177739_c(this.field_72986_A.func_176120_C(), this.field_72986_A.func_176126_D());
      this.func_175723_af().func_177744_c(this.field_72986_A.func_176140_I());
      this.func_175723_af().func_177724_b(this.field_72986_A.func_176138_H());
      this.func_175723_af().func_177747_c(this.field_72986_A.func_176131_J());
      this.func_175723_af().func_177723_b(this.field_72986_A.func_176139_K());
      if (this.field_72986_A.func_176134_F() > 0L) {
         this.func_175723_af().func_177738_a(this.field_72986_A.func_176137_E(), this.field_72986_A.func_176132_G(), this.field_72986_A.func_176134_F());
      } else {
         this.func_175723_af().func_177750_a(this.field_72986_A.func_176137_E());
      }

      return this;
   }

   public void func_72835_b() {
      super.func_72835_b();
      if (this.func_72912_H().func_76093_s() && this.func_175659_aa() != EnumDifficulty.HARD) {
         this.func_72912_H().func_176144_a(EnumDifficulty.HARD);
      }

      this.field_73011_w.func_177499_m().func_76938_b();
      if (this.func_73056_e()) {
         if (this.func_82736_K().func_82766_b("doDaylightCycle")) {
            long i = this.field_72986_A.func_76073_f() + 24000L;
            this.field_72986_A.func_76068_b(i - i % 24000L);
         }

         this.func_73053_d();
      }

      this.field_72984_F.func_76320_a("mobSpawner");
      if (this.func_82736_K().func_82766_b("doMobSpawning") && this.field_72986_A.func_76067_t() != WorldType.field_180272_g) {
         this.field_175742_R.func_77192_a(this, this.field_72985_G, this.field_72992_H, this.field_72986_A.func_82573_f() % 400L == 0L);
      }

      this.field_72984_F.func_76318_c("chunkSource");
      this.field_73020_y.func_73156_b();
      int j = this.func_72967_a(1.0F);
      if (j != this.func_175657_ab()) {
         this.func_175692_b(j);
      }

      this.field_72986_A.func_82572_b(this.field_72986_A.func_82573_f() + 1L);
      if (this.func_82736_K().func_82766_b("doDaylightCycle")) {
         this.field_72986_A.func_76068_b(this.field_72986_A.func_76073_f() + 1L);
      }

      this.field_72984_F.func_76318_c("tickPending");
      this.func_72955_a(false);
      this.field_72984_F.func_76318_c("tickBlocks");
      this.func_147456_g();
      this.field_72984_F.func_76318_c("chunkMap");
      this.field_73063_M.func_72693_b();
      this.field_72984_F.func_76318_c("village");
      this.field_72982_D.func_75544_a();
      this.field_175740_d.func_75528_a();
      this.field_72984_F.func_76318_c("portalForcer");
      this.field_85177_Q.func_85189_a(this.func_82737_E());
      this.field_72984_F.func_76319_b();
      this.func_147488_Z();
   }

   @Nullable
   public Biome.SpawnListEntry func_175734_a(EnumCreatureType p_175734_1_, BlockPos p_175734_2_) {
      List<Biome.SpawnListEntry> list = this.func_72863_F().func_177458_a(p_175734_1_, p_175734_2_);
      return list != null && !list.isEmpty() ? (Biome.SpawnListEntry)WeightedRandom.func_76271_a(this.field_73012_v, list) : null;
   }

   public boolean func_175732_a(EnumCreatureType p_175732_1_, Biome.SpawnListEntry p_175732_2_, BlockPos p_175732_3_) {
      List<Biome.SpawnListEntry> list = this.func_72863_F().func_177458_a(p_175732_1_, p_175732_3_);
      return list != null && !list.isEmpty() ? list.contains(p_175732_2_) : false;
   }

   public void func_72854_c() {
      this.field_73068_P = false;
      if (!this.field_73010_i.isEmpty()) {
         int i = 0;
         int j = 0;

         for(EntityPlayer entityplayer : this.field_73010_i) {
            if (entityplayer.func_175149_v()) {
               ++i;
            } else if (entityplayer.func_70608_bn()) {
               ++j;
            }
         }

         this.field_73068_P = j > 0 && j >= this.field_73010_i.size() - i;
      }

   }

   protected void func_73053_d() {
      this.field_73068_P = false;

      for(EntityPlayer entityplayer : this.field_73010_i) {
         if (entityplayer.func_70608_bn()) {
            entityplayer.func_70999_a(false, false, true);
         }
      }

      if (this.func_82736_K().func_82766_b("doWeatherCycle")) {
         this.func_73051_P();
      }

   }

   private void func_73051_P() {
      this.field_72986_A.func_76080_g(0);
      this.field_72986_A.func_76084_b(false);
      this.field_72986_A.func_76090_f(0);
      this.field_72986_A.func_76069_a(false);
   }

   public boolean func_73056_e() {
      if (this.field_73068_P && !this.field_72995_K) {
         for(EntityPlayer entityplayer : this.field_73010_i) {
            if (!entityplayer.func_175149_v() && !entityplayer.func_71026_bH()) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public void func_72974_f() {
      if (this.field_72986_A.func_76075_d() <= 0) {
         this.field_72986_A.func_76056_b(this.func_181545_F() + 1);
      }

      int i = this.field_72986_A.func_76079_c();
      int j = this.field_72986_A.func_76074_e();
      int k = 0;

      while(this.func_184141_c(new BlockPos(i, 0, j)).func_185904_a() == Material.field_151579_a) {
         i += this.field_73012_v.nextInt(8) - this.field_73012_v.nextInt(8);
         j += this.field_73012_v.nextInt(8) - this.field_73012_v.nextInt(8);
         ++k;
         if (k == 10000) {
            break;
         }
      }

      this.field_72986_A.func_76058_a(i);
      this.field_72986_A.func_76087_c(j);
   }

   protected boolean func_175680_a(int p_175680_1_, int p_175680_2_, boolean p_175680_3_) {
      return this.func_72863_F().func_73149_a(p_175680_1_, p_175680_2_);
   }

   protected void func_184162_i() {
      this.field_72984_F.func_76320_a("playerCheckLight");
      if (!this.field_73010_i.isEmpty()) {
         int i = this.field_73012_v.nextInt(this.field_73010_i.size());
         EntityPlayer entityplayer = this.field_73010_i.get(i);
         int j = MathHelper.func_76128_c(entityplayer.field_70165_t) + this.field_73012_v.nextInt(11) - 5;
         int k = MathHelper.func_76128_c(entityplayer.field_70163_u) + this.field_73012_v.nextInt(11) - 5;
         int l = MathHelper.func_76128_c(entityplayer.field_70161_v) + this.field_73012_v.nextInt(11) - 5;
         this.func_175664_x(new BlockPos(j, k, l));
      }

      this.field_72984_F.func_76319_b();
   }

   protected void func_147456_g() {
      this.func_184162_i();
      if (this.field_72986_A.func_76067_t() == WorldType.field_180272_g) {
         Iterator<Chunk> iterator1 = this.field_73063_M.func_187300_b();

         while(iterator1.hasNext()) {
            ((Chunk)iterator1.next()).func_150804_b(false);
         }

      } else {
         int i = this.func_82736_K().func_180263_c("randomTickSpeed");
         boolean flag = this.func_72896_J();
         boolean flag1 = this.func_72911_I();
         this.field_72984_F.func_76320_a("pollingChunks");

         for(Iterator<Chunk> iterator = this.field_73063_M.func_187300_b(); iterator.hasNext(); this.field_72984_F.func_76319_b()) {
            this.field_72984_F.func_76320_a("getChunk");
            Chunk chunk = iterator.next();
            int j = chunk.field_76635_g * 16;
            int k = chunk.field_76647_h * 16;
            this.field_72984_F.func_76318_c("checkNextLight");
            chunk.func_76594_o();
            this.field_72984_F.func_76318_c("tickChunk");
            chunk.func_150804_b(false);
            this.field_72984_F.func_76318_c("thunder");
            if (flag && flag1 && this.field_73012_v.nextInt(100000) == 0) {
               this.field_73005_l = this.field_73005_l * 3 + 1013904223;
               int l = this.field_73005_l >> 2;
               BlockPos blockpos = this.func_175736_a(new BlockPos(j + (l & 15), 0, k + (l >> 8 & 15)));
               if (this.func_175727_C(blockpos)) {
                  DifficultyInstance difficultyinstance = this.func_175649_E(blockpos);
                  if (this.func_82736_K().func_82766_b("doMobSpawning") && this.field_73012_v.nextDouble() < (double)difficultyinstance.func_180168_b() * 0.01D) {
                     EntitySkeletonHorse entityskeletonhorse = new EntitySkeletonHorse(this);
                     entityskeletonhorse.func_190691_p(true);
                     entityskeletonhorse.func_70873_a(0);
                     entityskeletonhorse.func_70107_b((double)blockpos.func_177958_n(), (double)blockpos.func_177956_o(), (double)blockpos.func_177952_p());
                     this.func_72838_d(entityskeletonhorse);
                     this.func_72942_c(new EntityLightningBolt(this, (double)blockpos.func_177958_n(), (double)blockpos.func_177956_o(), (double)blockpos.func_177952_p(), true));
                  } else {
                     this.func_72942_c(new EntityLightningBolt(this, (double)blockpos.func_177958_n(), (double)blockpos.func_177956_o(), (double)blockpos.func_177952_p(), false));
                  }
               }
            }

            this.field_72984_F.func_76318_c("iceandsnow");
            if (this.field_73012_v.nextInt(16) == 0) {
               this.field_73005_l = this.field_73005_l * 3 + 1013904223;
               int j2 = this.field_73005_l >> 2;
               BlockPos blockpos1 = this.func_175725_q(new BlockPos(j + (j2 & 15), 0, k + (j2 >> 8 & 15)));
               BlockPos blockpos2 = blockpos1.func_177977_b();
               if (this.func_175662_w(blockpos2)) {
                  this.func_175656_a(blockpos2, Blocks.field_150432_aD.func_176223_P());
               }

               if (flag && this.func_175708_f(blockpos1, true)) {
                  this.func_175656_a(blockpos1, Blocks.field_150431_aC.func_176223_P());
               }

               if (flag && this.func_180494_b(blockpos2).func_76738_d()) {
                  this.func_180495_p(blockpos2).func_177230_c().func_176224_k(this, blockpos2);
               }
            }

            this.field_72984_F.func_76318_c("tickBlocks");
            if (i > 0) {
               for(ExtendedBlockStorage extendedblockstorage : chunk.func_76587_i()) {
                  if (extendedblockstorage != Chunk.field_186036_a && extendedblockstorage.func_76675_b()) {
                     for(int i1 = 0; i1 < i; ++i1) {
                        this.field_73005_l = this.field_73005_l * 3 + 1013904223;
                        int j1 = this.field_73005_l >> 2;
                        int k1 = j1 & 15;
                        int l1 = j1 >> 8 & 15;
                        int i2 = j1 >> 16 & 15;
                        IBlockState iblockstate = extendedblockstorage.func_177485_a(k1, i2, l1);
                        Block block = iblockstate.func_177230_c();
                        this.field_72984_F.func_76320_a("randomTick");
                        if (block.func_149653_t()) {
                           block.func_180645_a(this, new BlockPos(k1 + j, i2 + extendedblockstorage.func_76662_d(), l1 + k), iblockstate, this.field_73012_v);
                        }

                        this.field_72984_F.func_76319_b();
                     }
                  }
               }
            }
         }

         this.field_72984_F.func_76319_b();
      }
   }

   protected BlockPos func_175736_a(BlockPos p_175736_1_) {
      BlockPos blockpos = this.func_175725_q(p_175736_1_);
      AxisAlignedBB axisalignedbb = (new AxisAlignedBB(blockpos, new BlockPos(blockpos.func_177958_n(), this.func_72800_K(), blockpos.func_177952_p()))).func_186662_g(3.0D);
      List<EntityLivingBase> list = this.func_175647_a(EntityLivingBase.class, axisalignedbb, new Predicate<EntityLivingBase>() {
         public boolean apply(@Nullable EntityLivingBase p_apply_1_) {
            return p_apply_1_ != null && p_apply_1_.func_70089_S() && WorldServer.this.func_175678_i(p_apply_1_.func_180425_c());
         }
      });
      if (!list.isEmpty()) {
         return ((EntityLivingBase)list.get(this.field_73012_v.nextInt(list.size()))).func_180425_c();
      } else {
         if (blockpos.func_177956_o() == -1) {
            blockpos = blockpos.func_177981_b(2);
         }

         return blockpos;
      }
   }

   public boolean func_175691_a(BlockPos p_175691_1_, Block p_175691_2_) {
      NextTickListEntry nextticklistentry = new NextTickListEntry(p_175691_1_, p_175691_2_);
      return this.field_94579_S.contains(nextticklistentry);
   }

   public boolean func_184145_b(BlockPos p_184145_1_, Block p_184145_2_) {
      NextTickListEntry nextticklistentry = new NextTickListEntry(p_184145_1_, p_184145_2_);
      return this.field_73064_N.contains(nextticklistentry);
   }

   public void func_175684_a(BlockPos p_175684_1_, Block p_175684_2_, int p_175684_3_) {
      this.func_175654_a(p_175684_1_, p_175684_2_, p_175684_3_, 0);
   }

   public void func_175654_a(BlockPos p_175654_1_, Block p_175654_2_, int p_175654_3_, int p_175654_4_) {
      Material material = p_175654_2_.func_176223_P().func_185904_a();
      if (this.field_72999_e && material != Material.field_151579_a) {
         if (p_175654_2_.func_149698_L()) {
            if (this.func_175707_a(p_175654_1_.func_177982_a(-8, -8, -8), p_175654_1_.func_177982_a(8, 8, 8))) {
               IBlockState iblockstate = this.func_180495_p(p_175654_1_);
               if (iblockstate.func_185904_a() != Material.field_151579_a && iblockstate.func_177230_c() == p_175654_2_) {
                  iblockstate.func_177230_c().func_180650_b(this, p_175654_1_, iblockstate, this.field_73012_v);
               }
            }

            return;
         }

         p_175654_3_ = 1;
      }

      NextTickListEntry nextticklistentry = new NextTickListEntry(p_175654_1_, p_175654_2_);
      if (this.func_175667_e(p_175654_1_)) {
         if (material != Material.field_151579_a) {
            nextticklistentry.func_77176_a((long)p_175654_3_ + this.field_72986_A.func_82573_f());
            nextticklistentry.func_82753_a(p_175654_4_);
         }

         if (!this.field_73064_N.contains(nextticklistentry)) {
            this.field_73064_N.add(nextticklistentry);
            this.field_73065_O.add(nextticklistentry);
         }
      }

   }

   public void func_180497_b(BlockPos p_180497_1_, Block p_180497_2_, int p_180497_3_, int p_180497_4_) {
      NextTickListEntry nextticklistentry = new NextTickListEntry(p_180497_1_, p_180497_2_);
      nextticklistentry.func_82753_a(p_180497_4_);
      Material material = p_180497_2_.func_176223_P().func_185904_a();
      if (material != Material.field_151579_a) {
         nextticklistentry.func_77176_a((long)p_180497_3_ + this.field_72986_A.func_82573_f());
      }

      if (!this.field_73064_N.contains(nextticklistentry)) {
         this.field_73064_N.add(nextticklistentry);
         this.field_73065_O.add(nextticklistentry);
      }

   }

   public void func_72939_s() {
      if (this.field_73010_i.isEmpty()) {
         if (this.field_80004_Q++ >= 300) {
            return;
         }
      } else {
         this.func_82742_i();
      }

      this.field_73011_w.func_186059_r();
      super.func_72939_s();
   }

   protected void func_184147_l() {
      super.func_184147_l();
      this.field_72984_F.func_76318_c("players");

      for(int i = 0; i < this.field_73010_i.size(); ++i) {
         Entity entity = this.field_73010_i.get(i);
         Entity entity1 = entity.func_184187_bx();
         if (entity1 != null) {
            if (!entity1.field_70128_L && entity1.func_184196_w(entity)) {
               continue;
            }

            entity.func_184210_p();
         }

         this.field_72984_F.func_76320_a("tick");
         if (!entity.field_70128_L) {
            try {
               this.func_72870_g(entity);
            } catch (Throwable throwable) {
               CrashReport crashreport = CrashReport.func_85055_a(throwable, "Ticking player");
               CrashReportCategory crashreportcategory = crashreport.func_85058_a("Player being ticked");
               entity.func_85029_a(crashreportcategory);
               throw new ReportedException(crashreport);
            }
         }

         this.field_72984_F.func_76319_b();
         this.field_72984_F.func_76320_a("remove");
         if (entity.field_70128_L) {
            int j = entity.field_70176_ah;
            int k = entity.field_70164_aj;
            if (entity.field_70175_ag && this.func_175680_a(j, k, true)) {
               this.func_72964_e(j, k).func_76622_b(entity);
            }

            this.field_72996_f.remove(entity);
            this.func_72847_b(entity);
         }

         this.field_72984_F.func_76319_b();
      }

   }

   public void func_82742_i() {
      this.field_80004_Q = 0;
   }

   public boolean func_72955_a(boolean p_72955_1_) {
      if (this.field_72986_A.func_76067_t() == WorldType.field_180272_g) {
         return false;
      } else {
         int i = this.field_73065_O.size();
         if (i != this.field_73064_N.size()) {
            throw new IllegalStateException("TickNextTick list out of synch");
         } else {
            if (i > 65536) {
               i = 65536;
            }

            this.field_72984_F.func_76320_a("cleaning");

            for(int j = 0; j < i; ++j) {
               NextTickListEntry nextticklistentry = this.field_73065_O.first();
               if (!p_72955_1_ && nextticklistentry.field_77180_e > this.field_72986_A.func_82573_f()) {
                  break;
               }

               this.field_73065_O.remove(nextticklistentry);
               this.field_73064_N.remove(nextticklistentry);
               this.field_94579_S.add(nextticklistentry);
            }

            this.field_72984_F.func_76319_b();
            this.field_72984_F.func_76320_a("ticking");
            Iterator<NextTickListEntry> iterator = this.field_94579_S.iterator();

            while(iterator.hasNext()) {
               NextTickListEntry nextticklistentry1 = iterator.next();
               iterator.remove();
               int k = 0;
               if (this.func_175707_a(nextticklistentry1.field_180282_a.func_177982_a(0, 0, 0), nextticklistentry1.field_180282_a.func_177982_a(0, 0, 0))) {
                  IBlockState iblockstate = this.func_180495_p(nextticklistentry1.field_180282_a);
                  if (iblockstate.func_185904_a() != Material.field_151579_a && Block.func_149680_a(iblockstate.func_177230_c(), nextticklistentry1.func_151351_a())) {
                     try {
                        iblockstate.func_177230_c().func_180650_b(this, nextticklistentry1.field_180282_a, iblockstate, this.field_73012_v);
                     } catch (Throwable throwable) {
                        CrashReport crashreport = CrashReport.func_85055_a(throwable, "Exception while ticking a block");
                        CrashReportCategory crashreportcategory = crashreport.func_85058_a("Block being ticked");
                        CrashReportCategory.func_175750_a(crashreportcategory, nextticklistentry1.field_180282_a, iblockstate);
                        throw new ReportedException(crashreport);
                     }
                  }
               } else {
                  this.func_175684_a(nextticklistentry1.field_180282_a, nextticklistentry1.func_151351_a(), 0);
               }
            }

            this.field_72984_F.func_76319_b();
            this.field_94579_S.clear();
            return !this.field_73065_O.isEmpty();
         }
      }
   }

   @Nullable
   public List<NextTickListEntry> func_72920_a(Chunk p_72920_1_, boolean p_72920_2_) {
      ChunkPos chunkpos = p_72920_1_.func_76632_l();
      int i = (chunkpos.field_77276_a << 4) - 2;
      int j = i + 16 + 2;
      int k = (chunkpos.field_77275_b << 4) - 2;
      int l = k + 16 + 2;
      return this.func_175712_a(new StructureBoundingBox(i, 0, k, j, 256, l), p_72920_2_);
   }

   @Nullable
   public List<NextTickListEntry> func_175712_a(StructureBoundingBox p_175712_1_, boolean p_175712_2_) {
      List<NextTickListEntry> list = null;

      for(int i = 0; i < 2; ++i) {
         Iterator<NextTickListEntry> iterator;
         if (i == 0) {
            iterator = this.field_73065_O.iterator();
         } else {
            iterator = this.field_94579_S.iterator();
         }

         while(iterator.hasNext()) {
            NextTickListEntry nextticklistentry = iterator.next();
            BlockPos blockpos = nextticklistentry.field_180282_a;
            if (blockpos.func_177958_n() >= p_175712_1_.field_78897_a && blockpos.func_177958_n() < p_175712_1_.field_78893_d && blockpos.func_177952_p() >= p_175712_1_.field_78896_c && blockpos.func_177952_p() < p_175712_1_.field_78892_f) {
               if (p_175712_2_) {
                  if (i == 0) {
                     this.field_73064_N.remove(nextticklistentry);
                  }

                  iterator.remove();
               }

               if (list == null) {
                  list = Lists.<NextTickListEntry>newArrayList();
               }

               list.add(nextticklistentry);
            }
         }
      }

      return list;
   }

   public void func_72866_a(Entity p_72866_1_, boolean p_72866_2_) {
      if (!this.func_175735_ai() && (p_72866_1_ instanceof EntityAnimal || p_72866_1_ instanceof EntityWaterMob)) {
         p_72866_1_.func_70106_y();
      }

      if (!this.func_175738_ah() && p_72866_1_ instanceof INpc) {
         p_72866_1_.func_70106_y();
      }

      super.func_72866_a(p_72866_1_, p_72866_2_);
   }

   private boolean func_175738_ah() {
      return this.field_73061_a.func_71220_V();
   }

   private boolean func_175735_ai() {
      return this.field_73061_a.func_71268_U();
   }

   protected IChunkProvider func_72970_h() {
      IChunkLoader ichunkloader = this.field_73019_z.func_75763_a(this.field_73011_w);
      return new ChunkProviderServer(this, ichunkloader, this.field_73011_w.func_186060_c());
   }

   public boolean func_175660_a(EntityPlayer p_175660_1_, BlockPos p_175660_2_) {
      return !this.field_73061_a.func_175579_a(this, p_175660_2_, p_175660_1_) && this.func_175723_af().func_177746_a(p_175660_2_);
   }

   public void func_72963_a(WorldSettings p_72963_1_) {
      if (!this.field_72986_A.func_76070_v()) {
         try {
            this.func_73052_b(p_72963_1_);
            if (this.field_72986_A.func_76067_t() == WorldType.field_180272_g) {
               this.func_175737_aj();
            }

            super.func_72963_a(p_72963_1_);
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.func_85055_a(throwable, "Exception initializing level");

            try {
               this.func_72914_a(crashreport);
            } catch (Throwable var5) {
               ;
            }

            throw new ReportedException(crashreport);
         }

         this.field_72986_A.func_76091_d(true);
      }

   }

   private void func_175737_aj() {
      this.field_72986_A.func_176128_f(false);
      this.field_72986_A.func_176121_c(true);
      this.field_72986_A.func_76084_b(false);
      this.field_72986_A.func_76069_a(false);
      this.field_72986_A.func_176142_i(1000000000);
      this.field_72986_A.func_76068_b(6000L);
      this.field_72986_A.func_76060_a(GameType.SPECTATOR);
      this.field_72986_A.func_176119_g(false);
      this.field_72986_A.func_176144_a(EnumDifficulty.PEACEFUL);
      this.field_72986_A.func_180783_e(true);
      this.func_82736_K().func_82764_b("doDaylightCycle", "false");
   }

   private void func_73052_b(WorldSettings p_73052_1_) {
      if (!this.field_73011_w.func_76567_e()) {
         this.field_72986_A.func_176143_a(BlockPos.field_177992_a.func_177981_b(this.field_73011_w.func_76557_i()));
      } else if (this.field_72986_A.func_76067_t() == WorldType.field_180272_g) {
         this.field_72986_A.func_176143_a(BlockPos.field_177992_a.func_177984_a());
      } else {
         this.field_72987_B = true;
         BiomeProvider biomeprovider = this.field_73011_w.func_177499_m();
         List<Biome> list = biomeprovider.func_76932_a();
         Random random = new Random(this.func_72905_C());
         BlockPos blockpos = biomeprovider.func_180630_a(0, 0, 256, list, random);
         int i = 8;
         int j = this.field_73011_w.func_76557_i();
         int k = 8;
         if (blockpos != null) {
            i = blockpos.func_177958_n();
            k = blockpos.func_177952_p();
         } else {
            field_147491_a.warn("Unable to find spawn biome");
         }

         int l = 0;

         while(!this.field_73011_w.func_76566_a(i, k)) {
            i += random.nextInt(64) - random.nextInt(64);
            k += random.nextInt(64) - random.nextInt(64);
            ++l;
            if (l == 1000) {
               break;
            }
         }

         this.field_72986_A.func_176143_a(new BlockPos(i, j, k));
         this.field_72987_B = false;
         if (p_73052_1_.func_77167_c()) {
            this.func_73047_i();
         }

      }
   }

   protected void func_73047_i() {
      WorldGeneratorBonusChest worldgeneratorbonuschest = new WorldGeneratorBonusChest();

      for(int i = 0; i < 10; ++i) {
         int j = this.field_72986_A.func_76079_c() + this.field_73012_v.nextInt(6) - this.field_73012_v.nextInt(6);
         int k = this.field_72986_A.func_76074_e() + this.field_73012_v.nextInt(6) - this.field_73012_v.nextInt(6);
         BlockPos blockpos = this.func_175672_r(new BlockPos(j, 0, k)).func_177984_a();
         if (worldgeneratorbonuschest.func_180709_b(this, this.field_73012_v, blockpos)) {
            break;
         }
      }

   }

   @Nullable
   public BlockPos func_180504_m() {
      return this.field_73011_w.func_177496_h();
   }

   public void func_73044_a(boolean p_73044_1_, @Nullable IProgressUpdate p_73044_2_) throws MinecraftException {
      ChunkProviderServer chunkproviderserver = this.func_72863_F();
      if (chunkproviderserver.func_73157_c()) {
         if (p_73044_2_ != null) {
            p_73044_2_.func_73720_a("Saving level");
         }

         this.func_73042_a();
         if (p_73044_2_ != null) {
            p_73044_2_.func_73719_c("Saving chunks");
         }

         chunkproviderserver.func_186027_a(p_73044_1_);

         for(Chunk chunk : Lists.newArrayList(chunkproviderserver.func_189548_a())) {
            if (chunk != null && !this.field_73063_M.func_152621_a(chunk.field_76635_g, chunk.field_76647_h)) {
               chunkproviderserver.func_189549_a(chunk);
            }
         }

      }
   }

   public void func_104140_m() {
      ChunkProviderServer chunkproviderserver = this.func_72863_F();
      if (chunkproviderserver.func_73157_c()) {
         chunkproviderserver.func_104112_b();
      }
   }

   protected void func_73042_a() throws MinecraftException {
      this.func_72906_B();

      for(WorldServer worldserver : this.field_73061_a.field_71305_c) {
         if (worldserver instanceof WorldServerMulti) {
            ((WorldServerMulti)worldserver).func_184166_c();
         }
      }

      this.field_72986_A.func_176145_a(this.func_175723_af().func_177741_h());
      this.field_72986_A.func_176124_d(this.func_175723_af().func_177731_f());
      this.field_72986_A.func_176141_c(this.func_175723_af().func_177721_g());
      this.field_72986_A.func_176129_e(this.func_175723_af().func_177742_m());
      this.field_72986_A.func_176125_f(this.func_175723_af().func_177727_n());
      this.field_72986_A.func_176122_j(this.func_175723_af().func_177748_q());
      this.field_72986_A.func_176136_k(this.func_175723_af().func_177740_p());
      this.field_72986_A.func_176118_b(this.func_175723_af().func_177751_j());
      this.field_72986_A.func_176135_e(this.func_175723_af().func_177732_i());
      this.field_73019_z.func_75755_a(this.field_72986_A, this.field_73061_a.func_184103_al().func_72378_q());
      this.field_72988_C.func_75744_a();
   }

   public boolean func_72838_d(Entity p_72838_1_) {
      return this.func_184165_i(p_72838_1_) ? super.func_72838_d(p_72838_1_) : false;
   }

   public void func_175650_b(Collection<Entity> p_175650_1_) {
      for(Entity entity : Lists.newArrayList(p_175650_1_)) {
         if (this.func_184165_i(entity)) {
            this.field_72996_f.add(entity);
            this.func_72923_a(entity);
         }
      }

   }

   private boolean func_184165_i(Entity p_184165_1_) {
      if (p_184165_1_.field_70128_L) {
         field_147491_a.warn("Tried to add entity {} but it was marked as removed already", (Object)EntityList.func_191301_a(p_184165_1_));
         return false;
      } else {
         UUID uuid = p_184165_1_.func_110124_au();
         if (this.field_175741_N.containsKey(uuid)) {
            Entity entity = this.field_175741_N.get(uuid);
            if (this.field_72997_g.contains(entity)) {
               this.field_72997_g.remove(entity);
            } else {
               if (!(p_184165_1_ instanceof EntityPlayer)) {
                  field_147491_a.warn("Keeping entity {} that already exists with UUID {}", EntityList.func_191301_a(entity), uuid.toString());
                  return false;
               }

               field_147491_a.warn("Force-added player with duplicate UUID {}", (Object)uuid.toString());
            }

            this.func_72973_f(entity);
         }

         return true;
      }
   }

   protected void func_72923_a(Entity p_72923_1_) {
      super.func_72923_a(p_72923_1_);
      this.field_175729_l.func_76038_a(p_72923_1_.func_145782_y(), p_72923_1_);
      this.field_175741_N.put(p_72923_1_.func_110124_au(), p_72923_1_);
      Entity[] aentity = p_72923_1_.func_70021_al();
      if (aentity != null) {
         for(Entity entity : aentity) {
            this.field_175729_l.func_76038_a(entity.func_145782_y(), entity);
         }
      }

   }

   protected void func_72847_b(Entity p_72847_1_) {
      super.func_72847_b(p_72847_1_);
      this.field_175729_l.func_76049_d(p_72847_1_.func_145782_y());
      this.field_175741_N.remove(p_72847_1_.func_110124_au());
      Entity[] aentity = p_72847_1_.func_70021_al();
      if (aentity != null) {
         for(Entity entity : aentity) {
            this.field_175729_l.func_76049_d(entity.func_145782_y());
         }
      }

   }

   public boolean func_72942_c(Entity p_72942_1_) {
      if (super.func_72942_c(p_72942_1_)) {
         this.field_73061_a.func_184103_al().func_148543_a((EntityPlayer)null, p_72942_1_.field_70165_t, p_72942_1_.field_70163_u, p_72942_1_.field_70161_v, 512.0D, this.field_73011_w.func_186058_p().func_186068_a(), new SPacketSpawnGlobalEntity(p_72942_1_));
         return true;
      } else {
         return false;
      }
   }

   public void func_72960_a(Entity p_72960_1_, byte p_72960_2_) {
      this.func_73039_n().func_151248_b(p_72960_1_, new SPacketEntityStatus(p_72960_1_, p_72960_2_));
   }

   public ChunkProviderServer func_72863_F() {
      return (ChunkProviderServer)super.func_72863_F();
   }

   public Explosion func_72885_a(@Nullable Entity p_72885_1_, double p_72885_2_, double p_72885_4_, double p_72885_6_, float p_72885_8_, boolean p_72885_9_, boolean p_72885_10_) {
      Explosion explosion = new Explosion(this, p_72885_1_, p_72885_2_, p_72885_4_, p_72885_6_, p_72885_8_, p_72885_9_, p_72885_10_);
      explosion.func_77278_a();
      explosion.func_77279_a(false);
      if (!p_72885_10_) {
         explosion.func_180342_d();
      }

      for(EntityPlayer entityplayer : this.field_73010_i) {
         if (entityplayer.func_70092_e(p_72885_2_, p_72885_4_, p_72885_6_) < 4096.0D) {
            ((EntityPlayerMP)entityplayer).field_71135_a.func_147359_a(new SPacketExplosion(p_72885_2_, p_72885_4_, p_72885_6_, p_72885_8_, explosion.func_180343_e(), (Vec3d)explosion.func_77277_b().get(entityplayer)));
         }
      }

      return explosion;
   }

   public void func_175641_c(BlockPos p_175641_1_, Block p_175641_2_, int p_175641_3_, int p_175641_4_) {
      BlockEventData blockeventdata = new BlockEventData(p_175641_1_, p_175641_2_, p_175641_3_, p_175641_4_);

      for(BlockEventData blockeventdata1 : this.field_147490_S[this.field_147489_T]) {
         if (blockeventdata1.equals(blockeventdata)) {
            return;
         }
      }

      this.field_147490_S[this.field_147489_T].add(blockeventdata);
   }

   private void func_147488_Z() {
      while(!this.field_147490_S[this.field_147489_T].isEmpty()) {
         int i = this.field_147489_T;
         this.field_147489_T ^= 1;

         for(BlockEventData blockeventdata : this.field_147490_S[i]) {
            if (this.func_147485_a(blockeventdata)) {
               this.field_73061_a.func_184103_al().func_148543_a((EntityPlayer)null, (double)blockeventdata.func_180328_a().func_177958_n(), (double)blockeventdata.func_180328_a().func_177956_o(), (double)blockeventdata.func_180328_a().func_177952_p(), 64.0D, this.field_73011_w.func_186058_p().func_186068_a(), new SPacketBlockAction(blockeventdata.func_180328_a(), blockeventdata.func_151337_f(), blockeventdata.func_151339_d(), blockeventdata.func_151338_e()));
            }
         }

         this.field_147490_S[i].clear();
      }

   }

   private boolean func_147485_a(BlockEventData p_147485_1_) {
      IBlockState iblockstate = this.func_180495_p(p_147485_1_.func_180328_a());
      return iblockstate.func_177230_c() == p_147485_1_.func_151337_f() ? iblockstate.func_189547_a(this, p_147485_1_.func_180328_a(), p_147485_1_.func_151339_d(), p_147485_1_.func_151338_e()) : false;
   }

   public void func_73041_k() {
      this.field_73019_z.func_75759_a();
   }

   protected void func_72979_l() {
      boolean flag = this.func_72896_J();
      super.func_72979_l();
      if (this.field_73003_n != this.field_73004_o) {
         this.field_73061_a.func_184103_al().func_148537_a(new SPacketChangeGameState(7, this.field_73004_o), this.field_73011_w.func_186058_p().func_186068_a());
      }

      if (this.field_73018_p != this.field_73017_q) {
         this.field_73061_a.func_184103_al().func_148537_a(new SPacketChangeGameState(8, this.field_73017_q), this.field_73011_w.func_186058_p().func_186068_a());
      }

      if (flag != this.func_72896_J()) {
         if (flag) {
            this.field_73061_a.func_184103_al().func_148540_a(new SPacketChangeGameState(2, 0.0F));
         } else {
            this.field_73061_a.func_184103_al().func_148540_a(new SPacketChangeGameState(1, 0.0F));
         }

         this.field_73061_a.func_184103_al().func_148540_a(new SPacketChangeGameState(7, this.field_73004_o));
         this.field_73061_a.func_184103_al().func_148540_a(new SPacketChangeGameState(8, this.field_73017_q));
      }

   }

   @Nullable
   public MinecraftServer func_73046_m() {
      return this.field_73061_a;
   }

   public EntityTracker func_73039_n() {
      return this.field_73062_L;
   }

   public PlayerChunkMap func_184164_w() {
      return this.field_73063_M;
   }

   public Teleporter func_85176_s() {
      return this.field_85177_Q;
   }

   public TemplateManager func_184163_y() {
      return this.field_73019_z.func_186340_h();
   }

   public void func_175739_a(EnumParticleTypes p_175739_1_, double p_175739_2_, double p_175739_4_, double p_175739_6_, int p_175739_8_, double p_175739_9_, double p_175739_11_, double p_175739_13_, double p_175739_15_, int... p_175739_17_) {
      this.func_180505_a(p_175739_1_, false, p_175739_2_, p_175739_4_, p_175739_6_, p_175739_8_, p_175739_9_, p_175739_11_, p_175739_13_, p_175739_15_, p_175739_17_);
   }

   public void func_180505_a(EnumParticleTypes p_180505_1_, boolean p_180505_2_, double p_180505_3_, double p_180505_5_, double p_180505_7_, int p_180505_9_, double p_180505_10_, double p_180505_12_, double p_180505_14_, double p_180505_16_, int... p_180505_18_) {
      SPacketParticles spacketparticles = new SPacketParticles(p_180505_1_, p_180505_2_, (float)p_180505_3_, (float)p_180505_5_, (float)p_180505_7_, (float)p_180505_10_, (float)p_180505_12_, (float)p_180505_14_, (float)p_180505_16_, p_180505_9_, p_180505_18_);

      for(int i = 0; i < this.field_73010_i.size(); ++i) {
         EntityPlayerMP entityplayermp = (EntityPlayerMP)this.field_73010_i.get(i);
         this.func_184159_a(entityplayermp, p_180505_2_, p_180505_3_, p_180505_5_, p_180505_7_, spacketparticles);
      }

   }

   public void func_184161_a(EntityPlayerMP p_184161_1_, EnumParticleTypes p_184161_2_, boolean p_184161_3_, double p_184161_4_, double p_184161_6_, double p_184161_8_, int p_184161_10_, double p_184161_11_, double p_184161_13_, double p_184161_15_, double p_184161_17_, int... p_184161_19_) {
      Packet<?> packet = new SPacketParticles(p_184161_2_, p_184161_3_, (float)p_184161_4_, (float)p_184161_6_, (float)p_184161_8_, (float)p_184161_11_, (float)p_184161_13_, (float)p_184161_15_, (float)p_184161_17_, p_184161_10_, p_184161_19_);
      this.func_184159_a(p_184161_1_, p_184161_3_, p_184161_4_, p_184161_6_, p_184161_8_, packet);
   }

   private void func_184159_a(EntityPlayerMP p_184159_1_, boolean p_184159_2_, double p_184159_3_, double p_184159_5_, double p_184159_7_, Packet<?> p_184159_9_) {
      BlockPos blockpos = p_184159_1_.func_180425_c();
      double d0 = blockpos.func_177954_c(p_184159_3_, p_184159_5_, p_184159_7_);
      if (d0 <= 1024.0D || p_184159_2_ && d0 <= 262144.0D) {
         p_184159_1_.field_71135_a.func_147359_a(p_184159_9_);
      }

   }

   @Nullable
   public Entity func_175733_a(UUID p_175733_1_) {
      return this.field_175741_N.get(p_175733_1_);
   }

   public ListenableFuture<Object> func_152344_a(Runnable p_152344_1_) {
      return this.field_73061_a.func_152344_a(p_152344_1_);
   }

   public boolean func_152345_ab() {
      return this.field_73061_a.func_152345_ab();
   }

   @Nullable
   public BlockPos func_190528_a(String p_190528_1_, BlockPos p_190528_2_, boolean p_190528_3_) {
      return this.func_72863_F().func_180513_a(this, p_190528_1_, p_190528_2_, p_190528_3_);
   }

   public AdvancementManager func_191952_z() {
      return this.field_191951_C;
   }

   public FunctionManager func_193037_A() {
      return this.field_193036_D;
   }

   static class ServerBlockEventList extends ArrayList<BlockEventData> {
      private ServerBlockEventList() {
      }
   }
}
