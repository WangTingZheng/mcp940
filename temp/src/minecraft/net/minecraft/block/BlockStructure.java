package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStructure extends BlockContainer {
   public static final PropertyEnum<TileEntityStructure.Mode> field_185587_a = PropertyEnum.<TileEntityStructure.Mode>func_177709_a("mode", TileEntityStructure.Mode.class);

   public BlockStructure() {
      super(Material.field_151573_f, MapColor.field_151680_x);
      this.func_180632_j(this.field_176227_L.func_177621_b());
   }

   public TileEntity func_149915_a(World p_149915_1_, int p_149915_2_) {
      return new TileEntityStructure();
   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      TileEntity tileentity = p_180639_1_.func_175625_s(p_180639_2_);
      return tileentity instanceof TileEntityStructure ? ((TileEntityStructure)tileentity).func_189701_a(p_180639_4_) : false;
   }

   public void func_180633_a(World p_180633_1_, BlockPos p_180633_2_, IBlockState p_180633_3_, EntityLivingBase p_180633_4_, ItemStack p_180633_5_) {
      if (!p_180633_1_.field_72995_K) {
         TileEntity tileentity = p_180633_1_.func_175625_s(p_180633_2_);
         if (tileentity instanceof TileEntityStructure) {
            TileEntityStructure tileentitystructure = (TileEntityStructure)tileentity;
            tileentitystructure.func_189720_a(p_180633_4_);
         }
      }
   }

   public int func_149745_a(Random p_149745_1_) {
      return 0;
   }

   public EnumBlockRenderType func_149645_b(IBlockState p_149645_1_) {
      return EnumBlockRenderType.MODEL;
   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      return this.func_176223_P().func_177226_a(field_185587_a, TileEntityStructure.Mode.DATA);
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_185587_a, TileEntityStructure.Mode.func_185108_a(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((TileEntityStructure.Mode)p_176201_1_.func_177229_b(field_185587_a)).func_185110_a();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_185587_a});
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      if (!p_189540_2_.field_72995_K) {
         TileEntity tileentity = p_189540_2_.func_175625_s(p_189540_3_);
         if (tileentity instanceof TileEntityStructure) {
            TileEntityStructure tileentitystructure = (TileEntityStructure)tileentity;
            boolean flag = p_189540_2_.func_175640_z(p_189540_3_);
            boolean flag1 = tileentitystructure.func_189722_G();
            if (flag && !flag1) {
               tileentitystructure.func_189723_d(true);
               this.func_189874_a(tileentitystructure);
            } else if (!flag && flag1) {
               tileentitystructure.func_189723_d(false);
            }

         }
      }
   }

   private void func_189874_a(TileEntityStructure p_189874_1_) {
      switch(p_189874_1_.func_189700_k()) {
      case SAVE:
         p_189874_1_.func_189712_b(false);
         break;
      case LOAD:
         p_189874_1_.func_189714_c(false);
         break;
      case CORNER:
         p_189874_1_.func_189706_E();
      case DATA:
      }

   }
}
