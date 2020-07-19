package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BlockMagma extends Block {
   public BlockMagma() {
      super(Material.field_151576_e);
      this.func_149647_a(CreativeTabs.field_78030_b);
      this.func_149715_a(0.2F);
      this.func_149675_a(true);
   }

   public MapColor func_180659_g(IBlockState p_180659_1_, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
      return MapColor.field_151655_K;
   }

   public void func_176199_a(World p_176199_1_, BlockPos p_176199_2_, Entity p_176199_3_) {
      if (!p_176199_3_.func_70045_F() && p_176199_3_ instanceof EntityLivingBase && !EnchantmentHelper.func_189869_j((EntityLivingBase)p_176199_3_)) {
         p_176199_3_.func_70097_a(DamageSource.field_190095_e, 1.0F);
      }

      super.func_176199_a(p_176199_1_, p_176199_2_, p_176199_3_);
   }

   public int func_185484_c(IBlockState p_185484_1_, IBlockAccess p_185484_2_, BlockPos p_185484_3_) {
      return 15728880;
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      BlockPos blockpos = p_180650_2_.func_177984_a();
      IBlockState iblockstate = p_180650_1_.func_180495_p(blockpos);
      if (iblockstate.func_177230_c() == Blocks.field_150355_j || iblockstate.func_177230_c() == Blocks.field_150358_i) {
         p_180650_1_.func_175698_g(blockpos);
         p_180650_1_.func_184133_a((EntityPlayer)null, p_180650_2_, SoundEvents.field_187646_bt, SoundCategory.BLOCKS, 0.5F, 2.6F + (p_180650_1_.field_73012_v.nextFloat() - p_180650_1_.field_73012_v.nextFloat()) * 0.8F);
         if (p_180650_1_ instanceof WorldServer) {
            ((WorldServer)p_180650_1_).func_175739_a(EnumParticleTypes.SMOKE_LARGE, (double)blockpos.func_177958_n() + 0.5D, (double)blockpos.func_177956_o() + 0.25D, (double)blockpos.func_177952_p() + 0.5D, 8, 0.5D, 0.25D, 0.5D, 0.0D);
         }
      }

   }

   public boolean func_189872_a(IBlockState p_189872_1_, Entity p_189872_2_) {
      return p_189872_2_.func_70045_F();
   }
}
