package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockNote extends BlockContainer {
   private static final List<SoundEvent> field_176434_a = Lists.newArrayList(SoundEvents.field_187682_dG, SoundEvents.field_187676_dE, SoundEvents.field_187688_dI, SoundEvents.field_187685_dH, SoundEvents.field_187679_dF, SoundEvents.field_193809_ey, SoundEvents.field_193807_ew, SoundEvents.field_193810_ez, SoundEvents.field_193808_ex, SoundEvents.field_193785_eE);

   public BlockNote() {
      super(Material.field_151575_d);
      this.func_149647_a(CreativeTabs.field_78028_d);
   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      boolean flag = p_189540_2_.func_175640_z(p_189540_3_);
      TileEntity tileentity = p_189540_2_.func_175625_s(p_189540_3_);
      if (tileentity instanceof TileEntityNote) {
         TileEntityNote tileentitynote = (TileEntityNote)tileentity;
         if (tileentitynote.field_145880_i != flag) {
            if (flag) {
               tileentitynote.func_175108_a(p_189540_2_, p_189540_3_);
            }

            tileentitynote.field_145880_i = flag;
         }
      }

   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      if (p_180639_1_.field_72995_K) {
         return true;
      } else {
         TileEntity tileentity = p_180639_1_.func_175625_s(p_180639_2_);
         if (tileentity instanceof TileEntityNote) {
            TileEntityNote tileentitynote = (TileEntityNote)tileentity;
            tileentitynote.func_145877_a();
            tileentitynote.func_175108_a(p_180639_1_, p_180639_2_);
            p_180639_4_.func_71029_a(StatList.field_188087_U);
         }

         return true;
      }
   }

   public void func_180649_a(World p_180649_1_, BlockPos p_180649_2_, EntityPlayer p_180649_3_) {
      if (!p_180649_1_.field_72995_K) {
         TileEntity tileentity = p_180649_1_.func_175625_s(p_180649_2_);
         if (tileentity instanceof TileEntityNote) {
            ((TileEntityNote)tileentity).func_175108_a(p_180649_1_, p_180649_2_);
            p_180649_3_.func_71029_a(StatList.field_188086_T);
         }

      }
   }

   public TileEntity func_149915_a(World p_149915_1_, int p_149915_2_) {
      return new TileEntityNote();
   }

   private SoundEvent func_185576_e(int p_185576_1_) {
      if (p_185576_1_ < 0 || p_185576_1_ >= field_176434_a.size()) {
         p_185576_1_ = 0;
      }

      return field_176434_a.get(p_185576_1_);
   }

   public boolean func_189539_a(IBlockState p_189539_1_, World p_189539_2_, BlockPos p_189539_3_, int p_189539_4_, int p_189539_5_) {
      float f = (float)Math.pow(2.0D, (double)(p_189539_5_ - 12) / 12.0D);
      p_189539_2_.func_184133_a((EntityPlayer)null, p_189539_3_, this.func_185576_e(p_189539_4_), SoundCategory.RECORDS, 3.0F, f);
      p_189539_2_.func_175688_a(EnumParticleTypes.NOTE, (double)p_189539_3_.func_177958_n() + 0.5D, (double)p_189539_3_.func_177956_o() + 1.2D, (double)p_189539_3_.func_177952_p() + 0.5D, (double)p_189539_5_ / 24.0D, 0.0D, 0.0D);
      return true;
   }

   public EnumBlockRenderType func_149645_b(IBlockState p_149645_1_) {
      return EnumBlockRenderType.MODEL;
   }
}
