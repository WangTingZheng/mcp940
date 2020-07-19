package net.minecraft.world;

import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class ChunkCache implements IBlockAccess {
   protected int field_72818_a;
   protected int field_72816_b;
   protected Chunk[][] field_72817_c;
   protected boolean field_72814_d;
   protected World field_72815_e;

   public ChunkCache(World p_i45746_1_, BlockPos p_i45746_2_, BlockPos p_i45746_3_, int p_i45746_4_) {
      this.field_72815_e = p_i45746_1_;
      this.field_72818_a = p_i45746_2_.func_177958_n() - p_i45746_4_ >> 4;
      this.field_72816_b = p_i45746_2_.func_177952_p() - p_i45746_4_ >> 4;
      int i = p_i45746_3_.func_177958_n() + p_i45746_4_ >> 4;
      int j = p_i45746_3_.func_177952_p() + p_i45746_4_ >> 4;
      this.field_72817_c = new Chunk[i - this.field_72818_a + 1][j - this.field_72816_b + 1];
      this.field_72814_d = true;

      for(int k = this.field_72818_a; k <= i; ++k) {
         for(int l = this.field_72816_b; l <= j; ++l) {
            this.field_72817_c[k - this.field_72818_a][l - this.field_72816_b] = p_i45746_1_.func_72964_e(k, l);
         }
      }

      for(int i1 = p_i45746_2_.func_177958_n() >> 4; i1 <= p_i45746_3_.func_177958_n() >> 4; ++i1) {
         for(int j1 = p_i45746_2_.func_177952_p() >> 4; j1 <= p_i45746_3_.func_177952_p() >> 4; ++j1) {
            Chunk chunk = this.field_72817_c[i1 - this.field_72818_a][j1 - this.field_72816_b];
            if (chunk != null && !chunk.func_76606_c(p_i45746_2_.func_177956_o(), p_i45746_3_.func_177956_o())) {
               this.field_72814_d = false;
            }
         }
      }

   }

   public boolean func_72806_N() {
      return this.field_72814_d;
   }

   @Nullable
   public TileEntity func_175625_s(BlockPos p_175625_1_) {
      return this.func_190300_a(p_175625_1_, Chunk.EnumCreateEntityType.IMMEDIATE);
   }

   @Nullable
   public TileEntity func_190300_a(BlockPos p_190300_1_, Chunk.EnumCreateEntityType p_190300_2_) {
      int i = (p_190300_1_.func_177958_n() >> 4) - this.field_72818_a;
      int j = (p_190300_1_.func_177952_p() >> 4) - this.field_72816_b;
      return this.field_72817_c[i][j].func_177424_a(p_190300_1_, p_190300_2_);
   }

   public int func_175626_b(BlockPos p_175626_1_, int p_175626_2_) {
      int i = this.func_175629_a(EnumSkyBlock.SKY, p_175626_1_);
      int j = this.func_175629_a(EnumSkyBlock.BLOCK, p_175626_1_);
      if (j < p_175626_2_) {
         j = p_175626_2_;
      }

      return i << 20 | j << 4;
   }

   public IBlockState func_180495_p(BlockPos p_180495_1_) {
      if (p_180495_1_.func_177956_o() >= 0 && p_180495_1_.func_177956_o() < 256) {
         int i = (p_180495_1_.func_177958_n() >> 4) - this.field_72818_a;
         int j = (p_180495_1_.func_177952_p() >> 4) - this.field_72816_b;
         if (i >= 0 && i < this.field_72817_c.length && j >= 0 && j < this.field_72817_c[i].length) {
            Chunk chunk = this.field_72817_c[i][j];
            if (chunk != null) {
               return chunk.func_177435_g(p_180495_1_);
            }
         }
      }

      return Blocks.field_150350_a.func_176223_P();
   }

   public Biome func_180494_b(BlockPos p_180494_1_) {
      int i = (p_180494_1_.func_177958_n() >> 4) - this.field_72818_a;
      int j = (p_180494_1_.func_177952_p() >> 4) - this.field_72816_b;
      return this.field_72817_c[i][j].func_177411_a(p_180494_1_, this.field_72815_e.func_72959_q());
   }

   private int func_175629_a(EnumSkyBlock p_175629_1_, BlockPos p_175629_2_) {
      if (p_175629_1_ == EnumSkyBlock.SKY && !this.field_72815_e.field_73011_w.func_191066_m()) {
         return 0;
      } else if (p_175629_2_.func_177956_o() >= 0 && p_175629_2_.func_177956_o() < 256) {
         if (this.func_180495_p(p_175629_2_).func_185916_f()) {
            int l = 0;

            for(EnumFacing enumfacing : EnumFacing.values()) {
               int k = this.func_175628_b(p_175629_1_, p_175629_2_.func_177972_a(enumfacing));
               if (k > l) {
                  l = k;
               }

               if (l >= 15) {
                  return l;
               }
            }

            return l;
         } else {
            int i = (p_175629_2_.func_177958_n() >> 4) - this.field_72818_a;
            int j = (p_175629_2_.func_177952_p() >> 4) - this.field_72816_b;
            return this.field_72817_c[i][j].func_177413_a(p_175629_1_, p_175629_2_);
         }
      } else {
         return p_175629_1_.field_77198_c;
      }
   }

   public boolean func_175623_d(BlockPos p_175623_1_) {
      return this.func_180495_p(p_175623_1_).func_185904_a() == Material.field_151579_a;
   }

   public int func_175628_b(EnumSkyBlock p_175628_1_, BlockPos p_175628_2_) {
      if (p_175628_2_.func_177956_o() >= 0 && p_175628_2_.func_177956_o() < 256) {
         int i = (p_175628_2_.func_177958_n() >> 4) - this.field_72818_a;
         int j = (p_175628_2_.func_177952_p() >> 4) - this.field_72816_b;
         return this.field_72817_c[i][j].func_177413_a(p_175628_1_, p_175628_2_);
      } else {
         return p_175628_1_.field_77198_c;
      }
   }

   public int func_175627_a(BlockPos p_175627_1_, EnumFacing p_175627_2_) {
      return this.func_180495_p(p_175627_1_).func_185893_b(this, p_175627_1_, p_175627_2_);
   }

   public WorldType func_175624_G() {
      return this.field_72815_e.func_175624_G();
   }
}
