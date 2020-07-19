package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemBucket extends Item {
   private final Block field_77876_a;

   public ItemBucket(Block p_i45331_1_) {
      this.field_77777_bU = 1;
      this.field_77876_a = p_i45331_1_;
      this.func_77637_a(CreativeTabs.field_78026_f);
   }

   public ActionResult<ItemStack> func_77659_a(World p_77659_1_, EntityPlayer p_77659_2_, EnumHand p_77659_3_) {
      boolean flag = this.field_77876_a == Blocks.field_150350_a;
      ItemStack itemstack = p_77659_2_.func_184586_b(p_77659_3_);
      RayTraceResult raytraceresult = this.func_77621_a(p_77659_1_, p_77659_2_, flag);
      if (raytraceresult == null) {
         return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
      } else if (raytraceresult.field_72313_a != RayTraceResult.Type.BLOCK) {
         return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
      } else {
         BlockPos blockpos = raytraceresult.func_178782_a();
         if (!p_77659_1_.func_175660_a(p_77659_2_, blockpos)) {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
         } else if (flag) {
            if (!p_77659_2_.func_175151_a(blockpos.func_177972_a(raytraceresult.field_178784_b), raytraceresult.field_178784_b, itemstack)) {
               return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
            } else {
               IBlockState iblockstate = p_77659_1_.func_180495_p(blockpos);
               Material material = iblockstate.func_185904_a();
               if (material == Material.field_151586_h && ((Integer)iblockstate.func_177229_b(BlockLiquid.field_176367_b)).intValue() == 0) {
                  p_77659_1_.func_180501_a(blockpos, Blocks.field_150350_a.func_176223_P(), 11);
                  p_77659_2_.func_71029_a(StatList.func_188057_b(this));
                  p_77659_2_.func_184185_a(SoundEvents.field_187630_M, 1.0F, 1.0F);
                  return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, this.func_150910_a(itemstack, p_77659_2_, Items.field_151131_as));
               } else if (material == Material.field_151587_i && ((Integer)iblockstate.func_177229_b(BlockLiquid.field_176367_b)).intValue() == 0) {
                  p_77659_2_.func_184185_a(SoundEvents.field_187633_N, 1.0F, 1.0F);
                  p_77659_1_.func_180501_a(blockpos, Blocks.field_150350_a.func_176223_P(), 11);
                  p_77659_2_.func_71029_a(StatList.func_188057_b(this));
                  return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, this.func_150910_a(itemstack, p_77659_2_, Items.field_151129_at));
               } else {
                  return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
               }
            }
         } else {
            boolean flag1 = p_77659_1_.func_180495_p(blockpos).func_177230_c().func_176200_f(p_77659_1_, blockpos);
            BlockPos blockpos1 = flag1 && raytraceresult.field_178784_b == EnumFacing.UP ? blockpos : blockpos.func_177972_a(raytraceresult.field_178784_b);
            if (!p_77659_2_.func_175151_a(blockpos1, raytraceresult.field_178784_b, itemstack)) {
               return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
            } else if (this.func_180616_a(p_77659_2_, p_77659_1_, blockpos1)) {
               if (p_77659_2_ instanceof EntityPlayerMP) {
                  CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)p_77659_2_, blockpos1, itemstack);
               }

               p_77659_2_.func_71029_a(StatList.func_188057_b(this));
               return !p_77659_2_.field_71075_bZ.field_75098_d ? new ActionResult(EnumActionResult.SUCCESS, new ItemStack(Items.field_151133_ar)) : new ActionResult(EnumActionResult.SUCCESS, itemstack);
            } else {
               return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
            }
         }
      }
   }

   private ItemStack func_150910_a(ItemStack p_150910_1_, EntityPlayer p_150910_2_, Item p_150910_3_) {
      if (p_150910_2_.field_71075_bZ.field_75098_d) {
         return p_150910_1_;
      } else {
         p_150910_1_.func_190918_g(1);
         if (p_150910_1_.func_190926_b()) {
            return new ItemStack(p_150910_3_);
         } else {
            if (!p_150910_2_.field_71071_by.func_70441_a(new ItemStack(p_150910_3_))) {
               p_150910_2_.func_71019_a(new ItemStack(p_150910_3_), false);
            }

            return p_150910_1_;
         }
      }
   }

   public boolean func_180616_a(@Nullable EntityPlayer p_180616_1_, World p_180616_2_, BlockPos p_180616_3_) {
      if (this.field_77876_a == Blocks.field_150350_a) {
         return false;
      } else {
         IBlockState iblockstate = p_180616_2_.func_180495_p(p_180616_3_);
         Material material = iblockstate.func_185904_a();
         boolean flag = !material.func_76220_a();
         boolean flag1 = iblockstate.func_177230_c().func_176200_f(p_180616_2_, p_180616_3_);
         if (!p_180616_2_.func_175623_d(p_180616_3_) && !flag && !flag1) {
            return false;
         } else {
            if (p_180616_2_.field_73011_w.func_177500_n() && this.field_77876_a == Blocks.field_150358_i) {
               int l = p_180616_3_.func_177958_n();
               int i = p_180616_3_.func_177956_o();
               int j = p_180616_3_.func_177952_p();
               p_180616_2_.func_184133_a(p_180616_1_, p_180616_3_, SoundEvents.field_187646_bt, SoundCategory.BLOCKS, 0.5F, 2.6F + (p_180616_2_.field_73012_v.nextFloat() - p_180616_2_.field_73012_v.nextFloat()) * 0.8F);

               for(int k = 0; k < 8; ++k) {
                  p_180616_2_.func_175688_a(EnumParticleTypes.SMOKE_LARGE, (double)l + Math.random(), (double)i + Math.random(), (double)j + Math.random(), 0.0D, 0.0D, 0.0D);
               }
            } else {
               if (!p_180616_2_.field_72995_K && (flag || flag1) && !material.func_76224_d()) {
                  p_180616_2_.func_175655_b(p_180616_3_, true);
               }

               SoundEvent soundevent = this.field_77876_a == Blocks.field_150356_k ? SoundEvents.field_187627_L : SoundEvents.field_187624_K;
               p_180616_2_.func_184133_a(p_180616_1_, p_180616_3_, soundevent, SoundCategory.BLOCKS, 1.0F, 1.0F);
               p_180616_2_.func_180501_a(p_180616_3_, this.field_77876_a.func_176223_P(), 11);
            }

            return true;
         }
      }
   }
}
