package net.minecraft.world.chunk;

import com.google.common.base.Predicate;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class EmptyChunk extends Chunk {
   public EmptyChunk(World p_i1994_1_, int p_i1994_2_, int p_i1994_3_) {
      super(p_i1994_1_, p_i1994_2_, p_i1994_3_);
   }

   public boolean func_76600_a(int p_76600_1_, int p_76600_2_) {
      return p_76600_1_ == this.field_76635_g && p_76600_2_ == this.field_76647_h;
   }

   public int func_76611_b(int p_76611_1_, int p_76611_2_) {
      return 0;
   }

   public void func_76590_a() {
   }

   public void func_76603_b() {
   }

   public IBlockState func_177435_g(BlockPos p_177435_1_) {
      return Blocks.field_150350_a.func_176223_P();
   }

   public int func_177437_b(BlockPos p_177437_1_) {
      return 255;
   }

   public int func_177413_a(EnumSkyBlock p_177413_1_, BlockPos p_177413_2_) {
      return p_177413_1_.field_77198_c;
   }

   public void func_177431_a(EnumSkyBlock p_177431_1_, BlockPos p_177431_2_, int p_177431_3_) {
   }

   public int func_177443_a(BlockPos p_177443_1_, int p_177443_2_) {
      return 0;
   }

   public void func_76612_a(Entity p_76612_1_) {
   }

   public void func_76622_b(Entity p_76622_1_) {
   }

   public void func_76608_a(Entity p_76608_1_, int p_76608_2_) {
   }

   public boolean func_177444_d(BlockPos p_177444_1_) {
      return false;
   }

   @Nullable
   public TileEntity func_177424_a(BlockPos p_177424_1_, Chunk.EnumCreateEntityType p_177424_2_) {
      return null;
   }

   public void func_150813_a(TileEntity p_150813_1_) {
   }

   public void func_177426_a(BlockPos p_177426_1_, TileEntity p_177426_2_) {
   }

   public void func_177425_e(BlockPos p_177425_1_) {
   }

   public void func_76631_c() {
   }

   public void func_76623_d() {
   }

   public void func_76630_e() {
   }

   public void func_177414_a(@Nullable Entity p_177414_1_, AxisAlignedBB p_177414_2_, List<Entity> p_177414_3_, Predicate<? super Entity> p_177414_4_) {
   }

   public <T extends Entity> void func_177430_a(Class<? extends T> p_177430_1_, AxisAlignedBB p_177430_2_, List<T> p_177430_3_, Predicate<? super T> p_177430_4_) {
   }

   public boolean func_76601_a(boolean p_76601_1_) {
      return false;
   }

   public Random func_76617_a(long p_76617_1_) {
      return new Random(this.func_177412_p().func_72905_C() + (long)(this.field_76635_g * this.field_76635_g * 4987142) + (long)(this.field_76635_g * 5947611) + (long)(this.field_76647_h * this.field_76647_h) * 4392871L + (long)(this.field_76647_h * 389711) ^ p_76617_1_);
   }

   public boolean func_76621_g() {
      return true;
   }

   public boolean func_76606_c(int p_76606_1_, int p_76606_2_) {
      return true;
   }
}
