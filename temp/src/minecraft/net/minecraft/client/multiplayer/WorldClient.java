package net.minecraft.client.multiplayer;

import com.google.common.collect.Sets;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSoundMinecart;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.ParticleFirework;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.DimensionType;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.SaveDataMemoryStorage;
import net.minecraft.world.storage.SaveHandlerMP;
import net.minecraft.world.storage.WorldInfo;

public class WorldClient extends World {
   private final NetHandlerPlayClient field_73035_a;
   private ChunkProviderClient field_73033_b;
   private final Set<Entity> field_73032_d = Sets.<Entity>newHashSet();
   private final Set<Entity> field_73036_L = Sets.<Entity>newHashSet();
   private final Minecraft field_73037_M = Minecraft.func_71410_x();
   private final Set<ChunkPos> field_73038_N = Sets.<ChunkPos>newHashSet();
   private int field_184158_M;
   protected Set<ChunkPos> field_184157_a;

   public WorldClient(NetHandlerPlayClient p_i45063_1_, WorldSettings p_i45063_2_, int p_i45063_3_, EnumDifficulty p_i45063_4_, Profiler p_i45063_5_) {
      super(new SaveHandlerMP(), new WorldInfo(p_i45063_2_, "MpServer"), DimensionType.func_186069_a(p_i45063_3_).func_186070_d(), p_i45063_5_, true);
      this.field_184158_M = this.field_73012_v.nextInt(12000);
      this.field_184157_a = Sets.<ChunkPos>newHashSet();
      this.field_73035_a = p_i45063_1_;
      this.func_72912_H().func_176144_a(p_i45063_4_);
      this.func_175652_B(new BlockPos(8, 64, 8));
      this.field_73011_w.func_76558_a(this);
      this.field_73020_y = this.func_72970_h();
      this.field_72988_C = new SaveDataMemoryStorage();
      this.func_72966_v();
      this.func_72947_a();
   }

   public void func_72835_b() {
      super.func_72835_b();
      this.func_82738_a(this.func_82737_E() + 1L);
      if (this.func_82736_K().func_82766_b("doDaylightCycle")) {
         this.func_72877_b(this.func_72820_D() + 1L);
      }

      this.field_72984_F.func_76320_a("reEntryProcessing");

      for(int i = 0; i < 10 && !this.field_73036_L.isEmpty(); ++i) {
         Entity entity = this.field_73036_L.iterator().next();
         this.field_73036_L.remove(entity);
         if (!this.field_72996_f.contains(entity)) {
            this.func_72838_d(entity);
         }
      }

      this.field_72984_F.func_76318_c("chunkCache");
      this.field_73033_b.func_73156_b();
      this.field_72984_F.func_76318_c("blocks");
      this.func_147456_g();
      this.field_72984_F.func_76319_b();
   }

   public void func_73031_a(int p_73031_1_, int p_73031_2_, int p_73031_3_, int p_73031_4_, int p_73031_5_, int p_73031_6_) {
   }

   protected IChunkProvider func_72970_h() {
      this.field_73033_b = new ChunkProviderClient(this);
      return this.field_73033_b;
   }

   protected boolean func_175680_a(int p_175680_1_, int p_175680_2_, boolean p_175680_3_) {
      return p_175680_3_ || !this.func_72863_F().func_186025_d(p_175680_1_, p_175680_2_).func_76621_g();
   }

   protected void func_184154_a() {
      this.field_184157_a.clear();
      int i = this.field_73037_M.field_71474_y.field_151451_c;
      this.field_72984_F.func_76320_a("buildList");
      int j = MathHelper.func_76128_c(this.field_73037_M.field_71439_g.field_70165_t / 16.0D);
      int k = MathHelper.func_76128_c(this.field_73037_M.field_71439_g.field_70161_v / 16.0D);

      for(int l = -i; l <= i; ++l) {
         for(int i1 = -i; i1 <= i; ++i1) {
            this.field_184157_a.add(new ChunkPos(l + j, i1 + k));
         }
      }

      this.field_72984_F.func_76319_b();
   }

   protected void func_147456_g() {
      this.func_184154_a();
      if (this.field_184158_M > 0) {
         --this.field_184158_M;
      }

      this.field_73038_N.retainAll(this.field_184157_a);
      if (this.field_73038_N.size() == this.field_184157_a.size()) {
         this.field_73038_N.clear();
      }

      int i = 0;

      for(ChunkPos chunkpos : this.field_184157_a) {
         if (!this.field_73038_N.contains(chunkpos)) {
            int j = chunkpos.field_77276_a * 16;
            int k = chunkpos.field_77275_b * 16;
            this.field_72984_F.func_76320_a("getChunk");
            Chunk chunk = this.func_72964_e(chunkpos.field_77276_a, chunkpos.field_77275_b);
            this.func_147467_a(j, k, chunk);
            this.field_72984_F.func_76319_b();
            this.field_73038_N.add(chunkpos);
            ++i;
            if (i >= 10) {
               return;
            }
         }
      }

   }

   public void func_73025_a(int p_73025_1_, int p_73025_2_, boolean p_73025_3_) {
      if (p_73025_3_) {
         this.field_73033_b.func_73158_c(p_73025_1_, p_73025_2_);
      } else {
         this.field_73033_b.func_73234_b(p_73025_1_, p_73025_2_);
         this.func_147458_c(p_73025_1_ * 16, 0, p_73025_2_ * 16, p_73025_1_ * 16 + 15, 256, p_73025_2_ * 16 + 15);
      }

   }

   public boolean func_72838_d(Entity p_72838_1_) {
      boolean flag = super.func_72838_d(p_72838_1_);
      this.field_73032_d.add(p_72838_1_);
      if (flag) {
         if (p_72838_1_ instanceof EntityMinecart) {
            this.field_73037_M.func_147118_V().func_147682_a(new MovingSoundMinecart((EntityMinecart)p_72838_1_));
         }
      } else {
         this.field_73036_L.add(p_72838_1_);
      }

      return flag;
   }

   public void func_72900_e(Entity p_72900_1_) {
      super.func_72900_e(p_72900_1_);
      this.field_73032_d.remove(p_72900_1_);
   }

   protected void func_72923_a(Entity p_72923_1_) {
      super.func_72923_a(p_72923_1_);
      if (this.field_73036_L.contains(p_72923_1_)) {
         this.field_73036_L.remove(p_72923_1_);
      }

   }

   protected void func_72847_b(Entity p_72847_1_) {
      super.func_72847_b(p_72847_1_);
      if (this.field_73032_d.contains(p_72847_1_)) {
         if (p_72847_1_.func_70089_S()) {
            this.field_73036_L.add(p_72847_1_);
         } else {
            this.field_73032_d.remove(p_72847_1_);
         }
      }

   }

   public void func_73027_a(int p_73027_1_, Entity p_73027_2_) {
      Entity entity = this.func_73045_a(p_73027_1_);
      if (entity != null) {
         this.func_72900_e(entity);
      }

      this.field_73032_d.add(p_73027_2_);
      p_73027_2_.func_145769_d(p_73027_1_);
      if (!this.func_72838_d(p_73027_2_)) {
         this.field_73036_L.add(p_73027_2_);
      }

      this.field_175729_l.func_76038_a(p_73027_1_, p_73027_2_);
   }

   @Nullable
   public Entity func_73045_a(int p_73045_1_) {
      return (Entity)(p_73045_1_ == this.field_73037_M.field_71439_g.func_145782_y() ? this.field_73037_M.field_71439_g : super.func_73045_a(p_73045_1_));
   }

   public Entity func_73028_b(int p_73028_1_) {
      Entity entity = this.field_175729_l.func_76049_d(p_73028_1_);
      if (entity != null) {
         this.field_73032_d.remove(entity);
         this.func_72900_e(entity);
      }

      return entity;
   }

   @Deprecated
   public boolean func_180503_b(BlockPos p_180503_1_, IBlockState p_180503_2_) {
      int i = p_180503_1_.func_177958_n();
      int j = p_180503_1_.func_177956_o();
      int k = p_180503_1_.func_177952_p();
      this.func_73031_a(i, j, k, i, j, k);
      return super.func_180501_a(p_180503_1_, p_180503_2_, 3);
   }

   public void func_72882_A() {
      this.field_73035_a.func_147298_b().func_150718_a(new TextComponentString("Quitting"));
   }

   protected void func_72979_l() {
   }

   protected void func_147467_a(int p_147467_1_, int p_147467_2_, Chunk p_147467_3_) {
      super.func_147467_a(p_147467_1_, p_147467_2_, p_147467_3_);
      if (this.field_184158_M == 0) {
         this.field_73005_l = this.field_73005_l * 3 + 1013904223;
         int i = this.field_73005_l >> 2;
         int j = i & 15;
         int k = i >> 8 & 15;
         int l = i >> 16 & 255;
         BlockPos blockpos = new BlockPos(j + p_147467_1_, l, k + p_147467_2_);
         IBlockState iblockstate = p_147467_3_.func_177435_g(blockpos);
         j = j + p_147467_1_;
         k = k + p_147467_2_;
         if (iblockstate.func_185904_a() == Material.field_151579_a && this.func_175699_k(blockpos) <= this.field_73012_v.nextInt(8) && this.func_175642_b(EnumSkyBlock.SKY, blockpos) <= 0 && this.field_73037_M.field_71439_g != null && this.field_73037_M.field_71439_g.func_70092_e((double)j + 0.5D, (double)l + 0.5D, (double)k + 0.5D) > 4.0D) {
            this.func_184134_a((double)j + 0.5D, (double)l + 0.5D, (double)k + 0.5D, SoundEvents.field_187674_a, SoundCategory.AMBIENT, 0.7F, 0.8F + this.field_73012_v.nextFloat() * 0.2F, false);
            this.field_184158_M = this.field_73012_v.nextInt(12000) + 6000;
         }
      }

   }

   public void func_73029_E(int p_73029_1_, int p_73029_2_, int p_73029_3_) {
      int i = 32;
      Random random = new Random();
      ItemStack itemstack = this.field_73037_M.field_71439_g.func_184614_ca();
      boolean flag = this.field_73037_M.field_71442_b.func_178889_l() == GameType.CREATIVE && !itemstack.func_190926_b() && itemstack.func_77973_b() == Item.func_150898_a(Blocks.field_180401_cv);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int j = 0; j < 667; ++j) {
         this.func_184153_a(p_73029_1_, p_73029_2_, p_73029_3_, 16, random, flag, blockpos$mutableblockpos);
         this.func_184153_a(p_73029_1_, p_73029_2_, p_73029_3_, 32, random, flag, blockpos$mutableblockpos);
      }

   }

   public void func_184153_a(int p_184153_1_, int p_184153_2_, int p_184153_3_, int p_184153_4_, Random p_184153_5_, boolean p_184153_6_, BlockPos.MutableBlockPos p_184153_7_) {
      int i = p_184153_1_ + this.field_73012_v.nextInt(p_184153_4_) - this.field_73012_v.nextInt(p_184153_4_);
      int j = p_184153_2_ + this.field_73012_v.nextInt(p_184153_4_) - this.field_73012_v.nextInt(p_184153_4_);
      int k = p_184153_3_ + this.field_73012_v.nextInt(p_184153_4_) - this.field_73012_v.nextInt(p_184153_4_);
      p_184153_7_.func_181079_c(i, j, k);
      IBlockState iblockstate = this.func_180495_p(p_184153_7_);
      iblockstate.func_177230_c().func_180655_c(iblockstate, this, p_184153_7_, p_184153_5_);
      if (p_184153_6_ && iblockstate.func_177230_c() == Blocks.field_180401_cv) {
         this.func_175688_a(EnumParticleTypes.BARRIER, (double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), 0.0D, 0.0D, 0.0D, new int[0]);
      }

   }

   public void func_73022_a() {
      this.field_72996_f.removeAll(this.field_72997_g);

      for(int i = 0; i < this.field_72997_g.size(); ++i) {
         Entity entity = this.field_72997_g.get(i);
         int j = entity.field_70176_ah;
         int k = entity.field_70164_aj;
         if (entity.field_70175_ag && this.func_175680_a(j, k, true)) {
            this.func_72964_e(j, k).func_76622_b(entity);
         }
      }

      for(int i1 = 0; i1 < this.field_72997_g.size(); ++i1) {
         this.func_72847_b(this.field_72997_g.get(i1));
      }

      this.field_72997_g.clear();

      for(int j1 = 0; j1 < this.field_72996_f.size(); ++j1) {
         Entity entity1 = this.field_72996_f.get(j1);
         Entity entity2 = entity1.func_184187_bx();
         if (entity2 != null) {
            if (!entity2.field_70128_L && entity2.func_184196_w(entity1)) {
               continue;
            }

            entity1.func_184210_p();
         }

         if (entity1.field_70128_L) {
            int k1 = entity1.field_70176_ah;
            int l = entity1.field_70164_aj;
            if (entity1.field_70175_ag && this.func_175680_a(k1, l, true)) {
               this.func_72964_e(k1, l).func_76622_b(entity1);
            }

            this.field_72996_f.remove(j1--);
            this.func_72847_b(entity1);
         }
      }

   }

   public CrashReportCategory func_72914_a(CrashReport p_72914_1_) {
      CrashReportCategory crashreportcategory = super.func_72914_a(p_72914_1_);
      crashreportcategory.func_189529_a("Forced entities", new ICrashReportDetail<String>() {
         public String call() {
            return WorldClient.this.field_73032_d.size() + " total; " + WorldClient.this.field_73032_d;
         }
      });
      crashreportcategory.func_189529_a("Retry entities", new ICrashReportDetail<String>() {
         public String call() {
            return WorldClient.this.field_73036_L.size() + " total; " + WorldClient.this.field_73036_L;
         }
      });
      crashreportcategory.func_189529_a("Server brand", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            return WorldClient.this.field_73037_M.field_71439_g.func_142021_k();
         }
      });
      crashreportcategory.func_189529_a("Server type", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            return WorldClient.this.field_73037_M.func_71401_C() == null ? "Non-integrated multiplayer server" : "Integrated singleplayer server";
         }
      });
      return crashreportcategory;
   }

   public void func_184148_a(@Nullable EntityPlayer p_184148_1_, double p_184148_2_, double p_184148_4_, double p_184148_6_, SoundEvent p_184148_8_, SoundCategory p_184148_9_, float p_184148_10_, float p_184148_11_) {
      if (p_184148_1_ == this.field_73037_M.field_71439_g) {
         this.func_184134_a(p_184148_2_, p_184148_4_, p_184148_6_, p_184148_8_, p_184148_9_, p_184148_10_, p_184148_11_, false);
      }

   }

   public void func_184156_a(BlockPos p_184156_1_, SoundEvent p_184156_2_, SoundCategory p_184156_3_, float p_184156_4_, float p_184156_5_, boolean p_184156_6_) {
      this.func_184134_a((double)p_184156_1_.func_177958_n() + 0.5D, (double)p_184156_1_.func_177956_o() + 0.5D, (double)p_184156_1_.func_177952_p() + 0.5D, p_184156_2_, p_184156_3_, p_184156_4_, p_184156_5_, p_184156_6_);
   }

   public void func_184134_a(double p_184134_1_, double p_184134_3_, double p_184134_5_, SoundEvent p_184134_7_, SoundCategory p_184134_8_, float p_184134_9_, float p_184134_10_, boolean p_184134_11_) {
      double d0 = this.field_73037_M.func_175606_aa().func_70092_e(p_184134_1_, p_184134_3_, p_184134_5_);
      PositionedSoundRecord positionedsoundrecord = new PositionedSoundRecord(p_184134_7_, p_184134_8_, p_184134_9_, p_184134_10_, (float)p_184134_1_, (float)p_184134_3_, (float)p_184134_5_);
      if (p_184134_11_ && d0 > 100.0D) {
         double d1 = Math.sqrt(d0) / 40.0D;
         this.field_73037_M.func_147118_V().func_147681_a(positionedsoundrecord, (int)(d1 * 20.0D));
      } else {
         this.field_73037_M.func_147118_V().func_147682_a(positionedsoundrecord);
      }

   }

   public void func_92088_a(double p_92088_1_, double p_92088_3_, double p_92088_5_, double p_92088_7_, double p_92088_9_, double p_92088_11_, @Nullable NBTTagCompound p_92088_13_) {
      this.field_73037_M.field_71452_i.func_78873_a(new ParticleFirework.Starter(this, p_92088_1_, p_92088_3_, p_92088_5_, p_92088_7_, p_92088_9_, p_92088_11_, this.field_73037_M.field_71452_i, p_92088_13_));
   }

   public void func_184135_a(Packet<?> p_184135_1_) {
      this.field_73035_a.func_147297_a(p_184135_1_);
   }

   public void func_96443_a(Scoreboard p_96443_1_) {
      this.field_96442_D = p_96443_1_;
   }

   public void func_72877_b(long p_72877_1_) {
      if (p_72877_1_ < 0L) {
         p_72877_1_ = -p_72877_1_;
         this.func_82736_K().func_82764_b("doDaylightCycle", "false");
      } else {
         this.func_82736_K().func_82764_b("doDaylightCycle", "true");
      }

      super.func_72877_b(p_72877_1_);
   }

   public ChunkProviderClient func_72863_F() {
      return (ChunkProviderClient)super.func_72863_F();
   }
}
