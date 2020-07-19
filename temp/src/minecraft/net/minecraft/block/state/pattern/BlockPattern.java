package net.minecraft.block.state.pattern;

import com.google.common.base.MoreObjects;
import com.google.common.base.Predicate;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class BlockPattern {
   private final Predicate<BlockWorldState>[][][] field_177689_a;
   private final int field_177687_b;
   private final int field_177688_c;
   private final int field_177686_d;

   public BlockPattern(Predicate<BlockWorldState>[][][] p_i45657_1_) {
      this.field_177689_a = p_i45657_1_;
      this.field_177687_b = p_i45657_1_.length;
      if (this.field_177687_b > 0) {
         this.field_177688_c = p_i45657_1_[0].length;
         if (this.field_177688_c > 0) {
            this.field_177686_d = p_i45657_1_[0][0].length;
         } else {
            this.field_177686_d = 0;
         }
      } else {
         this.field_177688_c = 0;
         this.field_177686_d = 0;
      }

   }

   public int func_185922_a() {
      return this.field_177687_b;
   }

   public int func_177685_b() {
      return this.field_177688_c;
   }

   public int func_177684_c() {
      return this.field_177686_d;
   }

   @Nullable
   private BlockPattern.PatternHelper func_177682_a(BlockPos p_177682_1_, EnumFacing p_177682_2_, EnumFacing p_177682_3_, LoadingCache<BlockPos, BlockWorldState> p_177682_4_) {
      for(int i = 0; i < this.field_177686_d; ++i) {
         for(int j = 0; j < this.field_177688_c; ++j) {
            for(int k = 0; k < this.field_177687_b; ++k) {
               if (!this.field_177689_a[k][j][i].apply(p_177682_4_.getUnchecked(func_177683_a(p_177682_1_, p_177682_2_, p_177682_3_, i, j, k)))) {
                  return null;
               }
            }
         }
      }

      return new BlockPattern.PatternHelper(p_177682_1_, p_177682_2_, p_177682_3_, p_177682_4_, this.field_177686_d, this.field_177688_c, this.field_177687_b);
   }

   @Nullable
   public BlockPattern.PatternHelper func_177681_a(World p_177681_1_, BlockPos p_177681_2_) {
      LoadingCache<BlockPos, BlockWorldState> loadingcache = func_181627_a(p_177681_1_, false);
      int i = Math.max(Math.max(this.field_177686_d, this.field_177688_c), this.field_177687_b);

      for(BlockPos blockpos : BlockPos.func_177980_a(p_177681_2_, p_177681_2_.func_177982_a(i - 1, i - 1, i - 1))) {
         for(EnumFacing enumfacing : EnumFacing.values()) {
            for(EnumFacing enumfacing1 : EnumFacing.values()) {
               if (enumfacing1 != enumfacing && enumfacing1 != enumfacing.func_176734_d()) {
                  BlockPattern.PatternHelper blockpattern$patternhelper = this.func_177682_a(blockpos, enumfacing, enumfacing1, loadingcache);
                  if (blockpattern$patternhelper != null) {
                     return blockpattern$patternhelper;
                  }
               }
            }
         }
      }

      return null;
   }

   public static LoadingCache<BlockPos, BlockWorldState> func_181627_a(World p_181627_0_, boolean p_181627_1_) {
      return CacheBuilder.newBuilder().<BlockPos, BlockWorldState>build(new BlockPattern.CacheLoader(p_181627_0_, p_181627_1_));
   }

   protected static BlockPos func_177683_a(BlockPos p_177683_0_, EnumFacing p_177683_1_, EnumFacing p_177683_2_, int p_177683_3_, int p_177683_4_, int p_177683_5_) {
      if (p_177683_1_ != p_177683_2_ && p_177683_1_ != p_177683_2_.func_176734_d()) {
         Vec3i vec3i = new Vec3i(p_177683_1_.func_82601_c(), p_177683_1_.func_96559_d(), p_177683_1_.func_82599_e());
         Vec3i vec3i1 = new Vec3i(p_177683_2_.func_82601_c(), p_177683_2_.func_96559_d(), p_177683_2_.func_82599_e());
         Vec3i vec3i2 = vec3i.func_177955_d(vec3i1);
         return p_177683_0_.func_177982_a(vec3i1.func_177958_n() * -p_177683_4_ + vec3i2.func_177958_n() * p_177683_3_ + vec3i.func_177958_n() * p_177683_5_, vec3i1.func_177956_o() * -p_177683_4_ + vec3i2.func_177956_o() * p_177683_3_ + vec3i.func_177956_o() * p_177683_5_, vec3i1.func_177952_p() * -p_177683_4_ + vec3i2.func_177952_p() * p_177683_3_ + vec3i.func_177952_p() * p_177683_5_);
      } else {
         throw new IllegalArgumentException("Invalid forwards & up combination");
      }
   }

   static class CacheLoader extends com.google.common.cache.CacheLoader<BlockPos, BlockWorldState> {
      private final World field_177680_a;
      private final boolean field_181626_b;

      public CacheLoader(World p_i46460_1_, boolean p_i46460_2_) {
         this.field_177680_a = p_i46460_1_;
         this.field_181626_b = p_i46460_2_;
      }

      public BlockWorldState load(BlockPos p_load_1_) throws Exception {
         return new BlockWorldState(this.field_177680_a, p_load_1_, this.field_181626_b);
      }
   }

   public static class PatternHelper {
      private final BlockPos field_177674_a;
      private final EnumFacing field_177672_b;
      private final EnumFacing field_177673_c;
      private final LoadingCache<BlockPos, BlockWorldState> field_177671_d;
      private final int field_181120_e;
      private final int field_181121_f;
      private final int field_181122_g;

      public PatternHelper(BlockPos p_i46378_1_, EnumFacing p_i46378_2_, EnumFacing p_i46378_3_, LoadingCache<BlockPos, BlockWorldState> p_i46378_4_, int p_i46378_5_, int p_i46378_6_, int p_i46378_7_) {
         this.field_177674_a = p_i46378_1_;
         this.field_177672_b = p_i46378_2_;
         this.field_177673_c = p_i46378_3_;
         this.field_177671_d = p_i46378_4_;
         this.field_181120_e = p_i46378_5_;
         this.field_181121_f = p_i46378_6_;
         this.field_181122_g = p_i46378_7_;
      }

      public BlockPos func_181117_a() {
         return this.field_177674_a;
      }

      public EnumFacing func_177669_b() {
         return this.field_177672_b;
      }

      public EnumFacing func_177668_c() {
         return this.field_177673_c;
      }

      public int func_181118_d() {
         return this.field_181120_e;
      }

      public int func_181119_e() {
         return this.field_181121_f;
      }

      public BlockWorldState func_177670_a(int p_177670_1_, int p_177670_2_, int p_177670_3_) {
         return this.field_177671_d.getUnchecked(BlockPattern.func_177683_a(this.field_177674_a, this.func_177669_b(), this.func_177668_c(), p_177670_1_, p_177670_2_, p_177670_3_));
      }

      public String toString() {
         return MoreObjects.toStringHelper(this).add("up", this.field_177673_c).add("forwards", this.field_177672_b).add("frontTopLeft", this.field_177674_a).toString();
      }
   }
}
