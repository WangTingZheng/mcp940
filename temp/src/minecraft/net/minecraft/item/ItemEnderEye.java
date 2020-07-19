package net.minecraft.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ItemEnderEye extends Item {
   public ItemEnderEye() {
      this.func_77637_a(CreativeTabs.field_78026_f);
   }

   public EnumActionResult func_180614_a(EntityPlayer p_180614_1_, World p_180614_2_, BlockPos p_180614_3_, EnumHand p_180614_4_, EnumFacing p_180614_5_, float p_180614_6_, float p_180614_7_, float p_180614_8_) {
      IBlockState iblockstate = p_180614_2_.func_180495_p(p_180614_3_);
      ItemStack itemstack = p_180614_1_.func_184586_b(p_180614_4_);
      if (p_180614_1_.func_175151_a(p_180614_3_.func_177972_a(p_180614_5_), p_180614_5_, itemstack) && iblockstate.func_177230_c() == Blocks.field_150378_br && !((Boolean)iblockstate.func_177229_b(BlockEndPortalFrame.field_176507_b)).booleanValue()) {
         if (p_180614_2_.field_72995_K) {
            return EnumActionResult.SUCCESS;
         } else {
            p_180614_2_.func_180501_a(p_180614_3_, iblockstate.func_177226_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(true)), 2);
            p_180614_2_.func_175666_e(p_180614_3_, Blocks.field_150378_br);
            itemstack.func_190918_g(1);

            for(int i = 0; i < 16; ++i) {
               double d0 = (double)((float)p_180614_3_.func_177958_n() + (5.0F + field_77697_d.nextFloat() * 6.0F) / 16.0F);
               double d1 = (double)((float)p_180614_3_.func_177956_o() + 0.8125F);
               double d2 = (double)((float)p_180614_3_.func_177952_p() + (5.0F + field_77697_d.nextFloat() * 6.0F) / 16.0F);
               double d3 = 0.0D;
               double d4 = 0.0D;
               double d5 = 0.0D;
               p_180614_2_.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }

            p_180614_2_.func_184133_a((EntityPlayer)null, p_180614_3_, SoundEvents.field_193781_bp, SoundCategory.BLOCKS, 1.0F, 1.0F);
            BlockPattern.PatternHelper blockpattern$patternhelper = BlockEndPortalFrame.func_185661_e().func_177681_a(p_180614_2_, p_180614_3_);
            if (blockpattern$patternhelper != null) {
               BlockPos blockpos = blockpattern$patternhelper.func_181117_a().func_177982_a(-3, 0, -3);

               for(int j = 0; j < 3; ++j) {
                  for(int k = 0; k < 3; ++k) {
                     p_180614_2_.func_180501_a(blockpos.func_177982_a(j, 0, k), Blocks.field_150384_bq.func_176223_P(), 2);
                  }
               }

               p_180614_2_.func_175669_a(1038, blockpos.func_177982_a(1, 0, 1), 0);
            }

            return EnumActionResult.SUCCESS;
         }
      } else {
         return EnumActionResult.FAIL;
      }
   }

   public ActionResult<ItemStack> func_77659_a(World p_77659_1_, EntityPlayer p_77659_2_, EnumHand p_77659_3_) {
      ItemStack itemstack = p_77659_2_.func_184586_b(p_77659_3_);
      RayTraceResult raytraceresult = this.func_77621_a(p_77659_1_, p_77659_2_, false);
      if (raytraceresult != null && raytraceresult.field_72313_a == RayTraceResult.Type.BLOCK && p_77659_1_.func_180495_p(raytraceresult.func_178782_a()).func_177230_c() == Blocks.field_150378_br) {
         return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
      } else {
         p_77659_2_.func_184598_c(p_77659_3_);
         if (!p_77659_1_.field_72995_K) {
            BlockPos blockpos = ((WorldServer)p_77659_1_).func_72863_F().func_180513_a(p_77659_1_, "Stronghold", new BlockPos(p_77659_2_), false);
            if (blockpos != null) {
               EntityEnderEye entityendereye = new EntityEnderEye(p_77659_1_, p_77659_2_.field_70165_t, p_77659_2_.field_70163_u + (double)(p_77659_2_.field_70131_O / 2.0F), p_77659_2_.field_70161_v);
               entityendereye.func_180465_a(blockpos);
               p_77659_1_.func_72838_d(entityendereye);
               if (p_77659_2_ instanceof EntityPlayerMP) {
                  CriteriaTriggers.field_192132_l.func_192239_a((EntityPlayerMP)p_77659_2_, blockpos);
               }

               p_77659_1_.func_184148_a((EntityPlayer)null, p_77659_2_.field_70165_t, p_77659_2_.field_70163_u, p_77659_2_.field_70161_v, SoundEvents.field_187528_aR, SoundCategory.NEUTRAL, 0.5F, 0.4F / (field_77697_d.nextFloat() * 0.4F + 0.8F));
               p_77659_1_.func_180498_a((EntityPlayer)null, 1003, new BlockPos(p_77659_2_), 0);
               if (!p_77659_2_.field_71075_bZ.field_75098_d) {
                  itemstack.func_190918_g(1);
               }

               p_77659_2_.func_71029_a(StatList.func_188057_b(this));
               return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
            }
         }

         return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
      }
   }
}
