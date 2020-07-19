package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;

public abstract class BlockContainer extends Block implements ITileEntityProvider {
   protected BlockContainer(Material p_i45386_1_) {
      this(p_i45386_1_, p_i45386_1_.func_151565_r());
   }

   protected BlockContainer(Material p_i46402_1_, MapColor p_i46402_2_) {
      super(p_i46402_1_, p_i46402_2_);
      this.field_149758_A = true;
   }

   protected boolean func_181086_a(World p_181086_1_, BlockPos p_181086_2_, EnumFacing p_181086_3_) {
      return p_181086_1_.func_180495_p(p_181086_2_.func_177972_a(p_181086_3_)).func_185904_a() == Material.field_151570_A;
   }

   protected boolean func_181087_e(World p_181087_1_, BlockPos p_181087_2_) {
      return this.func_181086_a(p_181087_1_, p_181087_2_, EnumFacing.NORTH) || this.func_181086_a(p_181087_1_, p_181087_2_, EnumFacing.SOUTH) || this.func_181086_a(p_181087_1_, p_181087_2_, EnumFacing.WEST) || this.func_181086_a(p_181087_1_, p_181087_2_, EnumFacing.EAST);
   }

   public EnumBlockRenderType func_149645_b(IBlockState p_149645_1_) {
      return EnumBlockRenderType.INVISIBLE;
   }

   public void func_180663_b(World p_180663_1_, BlockPos p_180663_2_, IBlockState p_180663_3_) {
      super.func_180663_b(p_180663_1_, p_180663_2_, p_180663_3_);
      p_180663_1_.func_175713_t(p_180663_2_);
   }

   public void func_180657_a(World p_180657_1_, EntityPlayer p_180657_2_, BlockPos p_180657_3_, IBlockState p_180657_4_, @Nullable TileEntity p_180657_5_, ItemStack p_180657_6_) {
      if (p_180657_5_ instanceof IWorldNameable && ((IWorldNameable)p_180657_5_).func_145818_k_()) {
         p_180657_2_.func_71029_a(StatList.func_188055_a(this));
         p_180657_2_.func_71020_j(0.005F);
         if (p_180657_1_.field_72995_K) {
            return;
         }

         int i = EnchantmentHelper.func_77506_a(Enchantments.field_185308_t, p_180657_6_);
         Item item = this.func_180660_a(p_180657_4_, p_180657_1_.field_73012_v, i);
         if (item == Items.field_190931_a) {
            return;
         }

         ItemStack itemstack = new ItemStack(item, this.func_149745_a(p_180657_1_.field_73012_v));
         itemstack.func_151001_c(((IWorldNameable)p_180657_5_).func_70005_c_());
         func_180635_a(p_180657_1_, p_180657_3_, itemstack);
      } else {
         super.func_180657_a(p_180657_1_, p_180657_2_, p_180657_3_, p_180657_4_, (TileEntity)null, p_180657_6_);
      }

   }

   public boolean func_189539_a(IBlockState p_189539_1_, World p_189539_2_, BlockPos p_189539_3_, int p_189539_4_, int p_189539_5_) {
      super.func_189539_a(p_189539_1_, p_189539_2_, p_189539_3_, p_189539_4_, p_189539_5_);
      TileEntity tileentity = p_189539_2_.func_175625_s(p_189539_3_);
      return tileentity == null ? false : tileentity.func_145842_c(p_189539_4_, p_189539_5_);
   }
}
