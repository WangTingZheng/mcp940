package net.minecraft.server.management;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.network.play.server.SPacketMultiBlockChange;
import net.minecraft.network.play.server.SPacketUnloadChunk;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerChunkMapEntry {
   private static final Logger field_187281_a = LogManager.getLogger();
   private final PlayerChunkMap field_187282_b;
   private final List<EntityPlayerMP> field_187283_c = Lists.<EntityPlayerMP>newArrayList();
   private final ChunkPos field_187284_d;
   private final short[] field_187285_e = new short[64];
   @Nullable
   private Chunk field_187286_f;
   private int field_187287_g;
   private int field_187288_h;
   private long field_187289_i;
   private boolean field_187290_j;

   public PlayerChunkMapEntry(PlayerChunkMap p_i1518_1_, int p_i1518_2_, int p_i1518_3_) {
      this.field_187282_b = p_i1518_1_;
      this.field_187284_d = new ChunkPos(p_i1518_2_, p_i1518_3_);
      this.field_187286_f = p_i1518_1_.func_72688_a().func_72863_F().func_186028_c(p_i1518_2_, p_i1518_3_);
   }

   public ChunkPos func_187264_a() {
      return this.field_187284_d;
   }

   public void func_187276_a(EntityPlayerMP p_187276_1_) {
      if (this.field_187283_c.contains(p_187276_1_)) {
         field_187281_a.debug("Failed to add player. {} already is in chunk {}, {}", p_187276_1_, Integer.valueOf(this.field_187284_d.field_77276_a), Integer.valueOf(this.field_187284_d.field_77275_b));
      } else {
         if (this.field_187283_c.isEmpty()) {
            this.field_187289_i = this.field_187282_b.func_72688_a().func_82737_E();
         }

         this.field_187283_c.add(p_187276_1_);
         if (this.field_187290_j) {
            this.func_187278_c(p_187276_1_);
         }

      }
   }

   public void func_187277_b(EntityPlayerMP p_187277_1_) {
      if (this.field_187283_c.contains(p_187277_1_)) {
         if (this.field_187290_j) {
            p_187277_1_.field_71135_a.func_147359_a(new SPacketUnloadChunk(this.field_187284_d.field_77276_a, this.field_187284_d.field_77275_b));
         }

         this.field_187283_c.remove(p_187277_1_);
         if (this.field_187283_c.isEmpty()) {
            this.field_187282_b.func_187305_b(this);
         }

      }
   }

   public boolean func_187268_a(boolean p_187268_1_) {
      if (this.field_187286_f != null) {
         return true;
      } else {
         if (p_187268_1_) {
            this.field_187286_f = this.field_187282_b.func_72688_a().func_72863_F().func_186025_d(this.field_187284_d.field_77276_a, this.field_187284_d.field_77275_b);
         } else {
            this.field_187286_f = this.field_187282_b.func_72688_a().func_72863_F().func_186028_c(this.field_187284_d.field_77276_a, this.field_187284_d.field_77275_b);
         }

         return this.field_187286_f != null;
      }
   }

   public boolean func_187272_b() {
      if (this.field_187290_j) {
         return true;
      } else if (this.field_187286_f == null) {
         return false;
      } else if (!this.field_187286_f.func_150802_k()) {
         return false;
      } else {
         this.field_187287_g = 0;
         this.field_187288_h = 0;
         this.field_187290_j = true;
         Packet<?> packet = new SPacketChunkData(this.field_187286_f, 65535);

         for(EntityPlayerMP entityplayermp : this.field_187283_c) {
            entityplayermp.field_71135_a.func_147359_a(packet);
            this.field_187282_b.func_72688_a().func_73039_n().func_85172_a(entityplayermp, this.field_187286_f);
         }

         return true;
      }
   }

   public void func_187278_c(EntityPlayerMP p_187278_1_) {
      if (this.field_187290_j) {
         p_187278_1_.field_71135_a.func_147359_a(new SPacketChunkData(this.field_187286_f, 65535));
         this.field_187282_b.func_72688_a().func_73039_n().func_85172_a(p_187278_1_, this.field_187286_f);
      }
   }

   public void func_187279_c() {
      long i = this.field_187282_b.func_72688_a().func_82737_E();
      if (this.field_187286_f != null) {
         this.field_187286_f.func_177415_c(this.field_187286_f.func_177416_w() + i - this.field_187289_i);
      }

      this.field_187289_i = i;
   }

   public void func_187265_a(int p_187265_1_, int p_187265_2_, int p_187265_3_) {
      if (this.field_187290_j) {
         if (this.field_187287_g == 0) {
            this.field_187282_b.func_187304_a(this);
         }

         this.field_187288_h |= 1 << (p_187265_2_ >> 4);
         if (this.field_187287_g < 64) {
            short short1 = (short)(p_187265_1_ << 12 | p_187265_3_ << 8 | p_187265_2_);

            for(int i = 0; i < this.field_187287_g; ++i) {
               if (this.field_187285_e[i] == short1) {
                  return;
               }
            }

            this.field_187285_e[this.field_187287_g++] = short1;
         }

      }
   }

   public void func_187267_a(Packet<?> p_187267_1_) {
      if (this.field_187290_j) {
         for(int i = 0; i < this.field_187283_c.size(); ++i) {
            (this.field_187283_c.get(i)).field_71135_a.func_147359_a(p_187267_1_);
         }

      }
   }

   public void func_187280_d() {
      if (this.field_187290_j && this.field_187286_f != null) {
         if (this.field_187287_g != 0) {
            if (this.field_187287_g == 1) {
               int i = (this.field_187285_e[0] >> 12 & 15) + this.field_187284_d.field_77276_a * 16;
               int j = this.field_187285_e[0] & 255;
               int k = (this.field_187285_e[0] >> 8 & 15) + this.field_187284_d.field_77275_b * 16;
               BlockPos blockpos = new BlockPos(i, j, k);
               this.func_187267_a(new SPacketBlockChange(this.field_187282_b.func_72688_a(), blockpos));
               if (this.field_187282_b.func_72688_a().func_180495_p(blockpos).func_177230_c().func_149716_u()) {
                  this.func_187273_a(this.field_187282_b.func_72688_a().func_175625_s(blockpos));
               }
            } else if (this.field_187287_g == 64) {
               this.func_187267_a(new SPacketChunkData(this.field_187286_f, this.field_187288_h));
            } else {
               this.func_187267_a(new SPacketMultiBlockChange(this.field_187287_g, this.field_187285_e, this.field_187286_f));

               for(int l = 0; l < this.field_187287_g; ++l) {
                  int i1 = (this.field_187285_e[l] >> 12 & 15) + this.field_187284_d.field_77276_a * 16;
                  int j1 = this.field_187285_e[l] & 255;
                  int k1 = (this.field_187285_e[l] >> 8 & 15) + this.field_187284_d.field_77275_b * 16;
                  BlockPos blockpos1 = new BlockPos(i1, j1, k1);
                  if (this.field_187282_b.func_72688_a().func_180495_p(blockpos1).func_177230_c().func_149716_u()) {
                     this.func_187273_a(this.field_187282_b.func_72688_a().func_175625_s(blockpos1));
                  }
               }
            }

            this.field_187287_g = 0;
            this.field_187288_h = 0;
         }
      }
   }

   private void func_187273_a(@Nullable TileEntity p_187273_1_) {
      if (p_187273_1_ != null) {
         SPacketUpdateTileEntity spacketupdatetileentity = p_187273_1_.func_189518_D_();
         if (spacketupdatetileentity != null) {
            this.func_187267_a(spacketupdatetileentity);
         }
      }

   }

   public boolean func_187275_d(EntityPlayerMP p_187275_1_) {
      return this.field_187283_c.contains(p_187275_1_);
   }

   public boolean func_187269_a(Predicate<EntityPlayerMP> p_187269_1_) {
      return Iterables.tryFind(this.field_187283_c, p_187269_1_).isPresent();
   }

   public boolean func_187271_a(double p_187271_1_, Predicate<EntityPlayerMP> p_187271_3_) {
      int i = 0;

      for(int j = this.field_187283_c.size(); i < j; ++i) {
         EntityPlayerMP entityplayermp = this.field_187283_c.get(i);
         if (p_187271_3_.apply(entityplayermp) && this.field_187284_d.func_185327_a(entityplayermp) < p_187271_1_ * p_187271_1_) {
            return true;
         }
      }

      return false;
   }

   public boolean func_187274_e() {
      return this.field_187290_j;
   }

   @Nullable
   public Chunk func_187266_f() {
      return this.field_187286_f;
   }

   public double func_187270_g() {
      double d0 = Double.MAX_VALUE;

      for(EntityPlayerMP entityplayermp : this.field_187283_c) {
         double d1 = this.field_187284_d.func_185327_a(entityplayermp);
         if (d1 < d0) {
            d0 = d1;
         }
      }

      return d0;
   }
}
