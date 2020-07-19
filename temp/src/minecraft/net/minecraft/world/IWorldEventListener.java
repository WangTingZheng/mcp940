package net.minecraft.world;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public interface IWorldEventListener {
   void func_184376_a(World var1, BlockPos var2, IBlockState var3, IBlockState var4, int var5);

   void func_174959_b(BlockPos var1);

   void func_147585_a(int var1, int var2, int var3, int var4, int var5, int var6);

   void func_184375_a(@Nullable EntityPlayer var1, SoundEvent var2, SoundCategory var3, double var4, double var6, double var8, float var10, float var11);

   void func_184377_a(SoundEvent var1, BlockPos var2);

   void func_180442_a(int var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15);

   void func_190570_a(int var1, boolean var2, boolean var3, double var4, double var6, double var8, double var10, double var12, double var14, int... var16);

   void func_72703_a(Entity var1);

   void func_72709_b(Entity var1);

   void func_180440_a(int var1, BlockPos var2, int var3);

   void func_180439_a(EntityPlayer var1, int var2, BlockPos var3, int var4);

   void func_180441_b(int var1, BlockPos var2, int var3);
}
