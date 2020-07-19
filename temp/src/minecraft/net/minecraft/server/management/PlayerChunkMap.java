package net.minecraft.server.management;

import com.google.common.base.Predicate;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

public class PlayerChunkMap {
   private static final Predicate<EntityPlayerMP> field_187308_a = new Predicate<EntityPlayerMP>() {
      public boolean apply(@Nullable EntityPlayerMP p_apply_1_) {
         return p_apply_1_ != null && !p_apply_1_.func_175149_v();
      }
   };
   private static final Predicate<EntityPlayerMP> field_187309_b = new Predicate<EntityPlayerMP>() {
      public boolean apply(@Nullable EntityPlayerMP p_apply_1_) {
         return p_apply_1_ != null && (!p_apply_1_.func_175149_v() || p_apply_1_.func_71121_q().func_82736_K().func_82766_b("spectatorsGenerateChunks"));
      }
   };
   private final WorldServer field_72701_a;
   private final List<EntityPlayerMP> field_72699_b = Lists.<EntityPlayerMP>newArrayList();
   private final Long2ObjectMap<PlayerChunkMapEntry> field_72700_c = new Long2ObjectOpenHashMap<PlayerChunkMapEntry>(4096);
   private final Set<PlayerChunkMapEntry> field_72697_d = Sets.<PlayerChunkMapEntry>newHashSet();
   private final List<PlayerChunkMapEntry> field_187310_g = Lists.<PlayerChunkMapEntry>newLinkedList();
   private final List<PlayerChunkMapEntry> field_187311_h = Lists.<PlayerChunkMapEntry>newLinkedList();
   private final List<PlayerChunkMapEntry> field_111193_e = Lists.<PlayerChunkMapEntry>newArrayList();
   private int field_72698_e;
   private long field_111192_g;
   private boolean field_187312_l = true;
   private boolean field_187313_m = true;

   public PlayerChunkMap(WorldServer p_i1176_1_) {
      this.field_72701_a = p_i1176_1_;
      this.func_152622_a(p_i1176_1_.func_73046_m().func_184103_al().func_72395_o());
   }

   public WorldServer func_72688_a() {
      return this.field_72701_a;
   }

   public Iterator<Chunk> func_187300_b() {
      final Iterator<PlayerChunkMapEntry> iterator = this.field_111193_e.iterator();
      return new AbstractIterator<Chunk>() {
         protected Chunk computeNext() {
            while(true) {
               if (iterator.hasNext()) {
                  PlayerChunkMapEntry playerchunkmapentry = iterator.next();
                  Chunk chunk = playerchunkmapentry.func_187266_f();
                  if (chunk == null) {
                     continue;
                  }

                  if (!chunk.func_177423_u() && chunk.func_177419_t()) {
                     return chunk;
                  }

                  if (!chunk.func_186035_j()) {
                     return chunk;
                  }

                  if (!playerchunkmapentry.func_187271_a(128.0D, PlayerChunkMap.field_187308_a)) {
                     continue;
                  }

                  return chunk;
               }

               return (Chunk)this.endOfData();
            }
         }
      };
   }

   public void func_72693_b() {
      long i = this.field_72701_a.func_82737_E();
      if (i - this.field_111192_g > 8000L) {
         this.field_111192_g = i;

         for(int j = 0; j < this.field_111193_e.size(); ++j) {
            PlayerChunkMapEntry playerchunkmapentry = this.field_111193_e.get(j);
            playerchunkmapentry.func_187280_d();
            playerchunkmapentry.func_187279_c();
         }
      }

      if (!this.field_72697_d.isEmpty()) {
         for(PlayerChunkMapEntry playerchunkmapentry2 : this.field_72697_d) {
            playerchunkmapentry2.func_187280_d();
         }

         this.field_72697_d.clear();
      }

      if (this.field_187312_l && i % 4L == 0L) {
         this.field_187312_l = false;
         Collections.sort(this.field_187311_h, new Comparator<PlayerChunkMapEntry>() {
            public int compare(PlayerChunkMapEntry p_compare_1_, PlayerChunkMapEntry p_compare_2_) {
               return ComparisonChain.start().compare(p_compare_1_.func_187270_g(), p_compare_2_.func_187270_g()).result();
            }
         });
      }

      if (this.field_187313_m && i % 4L == 2L) {
         this.field_187313_m = false;
         Collections.sort(this.field_187310_g, new Comparator<PlayerChunkMapEntry>() {
            public int compare(PlayerChunkMapEntry p_compare_1_, PlayerChunkMapEntry p_compare_2_) {
               return ComparisonChain.start().compare(p_compare_1_.func_187270_g(), p_compare_2_.func_187270_g()).result();
            }
         });
      }

      if (!this.field_187311_h.isEmpty()) {
         long l = System.nanoTime() + 50000000L;
         int k = 49;
         Iterator<PlayerChunkMapEntry> iterator = this.field_187311_h.iterator();

         while(iterator.hasNext()) {
            PlayerChunkMapEntry playerchunkmapentry1 = iterator.next();
            if (playerchunkmapentry1.func_187266_f() == null) {
               boolean flag = playerchunkmapentry1.func_187269_a(field_187309_b);
               if (playerchunkmapentry1.func_187268_a(flag)) {
                  iterator.remove();
                  if (playerchunkmapentry1.func_187272_b()) {
                     this.field_187310_g.remove(playerchunkmapentry1);
                  }

                  --k;
                  if (k < 0 || System.nanoTime() > l) {
                     break;
                  }
               }
            }
         }
      }

      if (!this.field_187310_g.isEmpty()) {
         int i1 = 81;
         Iterator<PlayerChunkMapEntry> iterator1 = this.field_187310_g.iterator();

         while(iterator1.hasNext()) {
            PlayerChunkMapEntry playerchunkmapentry3 = iterator1.next();
            if (playerchunkmapentry3.func_187272_b()) {
               iterator1.remove();
               --i1;
               if (i1 < 0) {
                  break;
               }
            }
         }
      }

      if (this.field_72699_b.isEmpty()) {
         WorldProvider worldprovider = this.field_72701_a.field_73011_w;
         if (!worldprovider.func_76567_e()) {
            this.field_72701_a.func_72863_F().func_73240_a();
         }
      }

   }

   public boolean func_152621_a(int p_152621_1_, int p_152621_2_) {
      long i = func_187307_d(p_152621_1_, p_152621_2_);
      return this.field_72700_c.get(i) != null;
   }

   @Nullable
   public PlayerChunkMapEntry func_187301_b(int p_187301_1_, int p_187301_2_) {
      return (PlayerChunkMapEntry)this.field_72700_c.get(func_187307_d(p_187301_1_, p_187301_2_));
   }

   private PlayerChunkMapEntry func_187302_c(int p_187302_1_, int p_187302_2_) {
      long i = func_187307_d(p_187302_1_, p_187302_2_);
      PlayerChunkMapEntry playerchunkmapentry = (PlayerChunkMapEntry)this.field_72700_c.get(i);
      if (playerchunkmapentry == null) {
         playerchunkmapentry = new PlayerChunkMapEntry(this, p_187302_1_, p_187302_2_);
         this.field_72700_c.put(i, playerchunkmapentry);
         this.field_111193_e.add(playerchunkmapentry);
         if (playerchunkmapentry.func_187266_f() == null) {
            this.field_187311_h.add(playerchunkmapentry);
         }

         if (!playerchunkmapentry.func_187272_b()) {
            this.field_187310_g.add(playerchunkmapentry);
         }
      }

      return playerchunkmapentry;
   }

   public void func_180244_a(BlockPos p_180244_1_) {
      int i = p_180244_1_.func_177958_n() >> 4;
      int j = p_180244_1_.func_177952_p() >> 4;
      PlayerChunkMapEntry playerchunkmapentry = this.func_187301_b(i, j);
      if (playerchunkmapentry != null) {
         playerchunkmapentry.func_187265_a(p_180244_1_.func_177958_n() & 15, p_180244_1_.func_177956_o(), p_180244_1_.func_177952_p() & 15);
      }

   }

   public void func_72683_a(EntityPlayerMP p_72683_1_) {
      int i = (int)p_72683_1_.field_70165_t >> 4;
      int j = (int)p_72683_1_.field_70161_v >> 4;
      p_72683_1_.field_71131_d = p_72683_1_.field_70165_t;
      p_72683_1_.field_71132_e = p_72683_1_.field_70161_v;

      for(int k = i - this.field_72698_e; k <= i + this.field_72698_e; ++k) {
         for(int l = j - this.field_72698_e; l <= j + this.field_72698_e; ++l) {
            this.func_187302_c(k, l).func_187276_a(p_72683_1_);
         }
      }

      this.field_72699_b.add(p_72683_1_);
      this.func_187306_e();
   }

   public void func_72695_c(EntityPlayerMP p_72695_1_) {
      int i = (int)p_72695_1_.field_71131_d >> 4;
      int j = (int)p_72695_1_.field_71132_e >> 4;

      for(int k = i - this.field_72698_e; k <= i + this.field_72698_e; ++k) {
         for(int l = j - this.field_72698_e; l <= j + this.field_72698_e; ++l) {
            PlayerChunkMapEntry playerchunkmapentry = this.func_187301_b(k, l);
            if (playerchunkmapentry != null) {
               playerchunkmapentry.func_187277_b(p_72695_1_);
            }
         }
      }

      this.field_72699_b.remove(p_72695_1_);
      this.func_187306_e();
   }

   private boolean func_72684_a(int p_72684_1_, int p_72684_2_, int p_72684_3_, int p_72684_4_, int p_72684_5_) {
      int i = p_72684_1_ - p_72684_3_;
      int j = p_72684_2_ - p_72684_4_;
      if (i >= -p_72684_5_ && i <= p_72684_5_) {
         return j >= -p_72684_5_ && j <= p_72684_5_;
      } else {
         return false;
      }
   }

   public void func_72685_d(EntityPlayerMP p_72685_1_) {
      int i = (int)p_72685_1_.field_70165_t >> 4;
      int j = (int)p_72685_1_.field_70161_v >> 4;
      double d0 = p_72685_1_.field_71131_d - p_72685_1_.field_70165_t;
      double d1 = p_72685_1_.field_71132_e - p_72685_1_.field_70161_v;
      double d2 = d0 * d0 + d1 * d1;
      if (d2 >= 64.0D) {
         int k = (int)p_72685_1_.field_71131_d >> 4;
         int l = (int)p_72685_1_.field_71132_e >> 4;
         int i1 = this.field_72698_e;
         int j1 = i - k;
         int k1 = j - l;
         if (j1 != 0 || k1 != 0) {
            for(int l1 = i - i1; l1 <= i + i1; ++l1) {
               for(int i2 = j - i1; i2 <= j + i1; ++i2) {
                  if (!this.func_72684_a(l1, i2, k, l, i1)) {
                     this.func_187302_c(l1, i2).func_187276_a(p_72685_1_);
                  }

                  if (!this.func_72684_a(l1 - j1, i2 - k1, i, j, i1)) {
                     PlayerChunkMapEntry playerchunkmapentry = this.func_187301_b(l1 - j1, i2 - k1);
                     if (playerchunkmapentry != null) {
                        playerchunkmapentry.func_187277_b(p_72685_1_);
                     }
                  }
               }
            }

            p_72685_1_.field_71131_d = p_72685_1_.field_70165_t;
            p_72685_1_.field_71132_e = p_72685_1_.field_70161_v;
            this.func_187306_e();
         }
      }
   }

   public boolean func_72694_a(EntityPlayerMP p_72694_1_, int p_72694_2_, int p_72694_3_) {
      PlayerChunkMapEntry playerchunkmapentry = this.func_187301_b(p_72694_2_, p_72694_3_);
      return playerchunkmapentry != null && playerchunkmapentry.func_187275_d(p_72694_1_) && playerchunkmapentry.func_187274_e();
   }

   public void func_152622_a(int p_152622_1_) {
      p_152622_1_ = MathHelper.func_76125_a(p_152622_1_, 3, 32);
      if (p_152622_1_ != this.field_72698_e) {
         int i = p_152622_1_ - this.field_72698_e;

         for(EntityPlayerMP entityplayermp : Lists.newArrayList(this.field_72699_b)) {
            int j = (int)entityplayermp.field_70165_t >> 4;
            int k = (int)entityplayermp.field_70161_v >> 4;
            if (i > 0) {
               for(int j1 = j - p_152622_1_; j1 <= j + p_152622_1_; ++j1) {
                  for(int k1 = k - p_152622_1_; k1 <= k + p_152622_1_; ++k1) {
                     PlayerChunkMapEntry playerchunkmapentry = this.func_187302_c(j1, k1);
                     if (!playerchunkmapentry.func_187275_d(entityplayermp)) {
                        playerchunkmapentry.func_187276_a(entityplayermp);
                     }
                  }
               }
            } else {
               for(int l = j - this.field_72698_e; l <= j + this.field_72698_e; ++l) {
                  for(int i1 = k - this.field_72698_e; i1 <= k + this.field_72698_e; ++i1) {
                     if (!this.func_72684_a(l, i1, j, k, p_152622_1_)) {
                        this.func_187302_c(l, i1).func_187277_b(entityplayermp);
                     }
                  }
               }
            }
         }

         this.field_72698_e = p_152622_1_;
         this.func_187306_e();
      }
   }

   private void func_187306_e() {
      this.field_187312_l = true;
      this.field_187313_m = true;
   }

   public static int func_72686_a(int p_72686_0_) {
      return p_72686_0_ * 16 - 16;
   }

   private static long func_187307_d(int p_187307_0_, int p_187307_1_) {
      return (long)p_187307_0_ + 2147483647L | (long)p_187307_1_ + 2147483647L << 32;
   }

   public void func_187304_a(PlayerChunkMapEntry p_187304_1_) {
      this.field_72697_d.add(p_187304_1_);
   }

   public void func_187305_b(PlayerChunkMapEntry p_187305_1_) {
      ChunkPos chunkpos = p_187305_1_.func_187264_a();
      long i = func_187307_d(chunkpos.field_77276_a, chunkpos.field_77275_b);
      p_187305_1_.func_187279_c();
      this.field_72700_c.remove(i);
      this.field_111193_e.remove(p_187305_1_);
      this.field_72697_d.remove(p_187305_1_);
      this.field_187310_g.remove(p_187305_1_);
      this.field_187311_h.remove(p_187305_1_);
      Chunk chunk = p_187305_1_.func_187266_f();
      if (chunk != null) {
         this.func_72688_a().func_72863_F().func_189549_a(chunk);
      }

   }
}
