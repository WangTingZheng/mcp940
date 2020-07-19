package net.minecraft.block;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMaterialMatcher;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPumpkin extends BlockHorizontal {
   private BlockPattern field_176394_a;
   private BlockPattern field_176393_b;
   private BlockPattern field_176395_M;
   private BlockPattern field_176396_O;
   private static final Predicate<IBlockState> field_181085_Q = new Predicate<IBlockState>() {
      public boolean apply(@Nullable IBlockState p_apply_1_) {
         return p_apply_1_ != null && (p_apply_1_.func_177230_c() == Blocks.field_150423_aK || p_apply_1_.func_177230_c() == Blocks.field_150428_aP);
      }
   };

   protected BlockPumpkin() {
      super(Material.field_151572_C, MapColor.field_151676_q);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_185512_D, EnumFacing.NORTH));
      this.func_149675_a(true);
      this.func_149647_a(CreativeTabs.field_78030_b);
   }

   public void func_176213_c(World p_176213_1_, BlockPos p_176213_2_, IBlockState p_176213_3_) {
      super.func_176213_c(p_176213_1_, p_176213_2_, p_176213_3_);
      this.func_180673_e(p_176213_1_, p_176213_2_);
   }

   public boolean func_176390_d(World p_176390_1_, BlockPos p_176390_2_) {
      return this.func_176392_j().func_177681_a(p_176390_1_, p_176390_2_) != null || this.func_176389_S().func_177681_a(p_176390_1_, p_176390_2_) != null;
   }

   private void func_180673_e(World p_180673_1_, BlockPos p_180673_2_) {
      BlockPattern.PatternHelper blockpattern$patternhelper = this.func_176391_l().func_177681_a(p_180673_1_, p_180673_2_);
      if (blockpattern$patternhelper != null) {
         for(int i = 0; i < this.func_176391_l().func_177685_b(); ++i) {
            BlockWorldState blockworldstate = blockpattern$patternhelper.func_177670_a(0, i, 0);
            p_180673_1_.func_180501_a(blockworldstate.func_177508_d(), Blocks.field_150350_a.func_176223_P(), 2);
         }

         EntitySnowman entitysnowman = new EntitySnowman(p_180673_1_);
         BlockPos blockpos1 = blockpattern$patternhelper.func_177670_a(0, 2, 0).func_177508_d();
         entitysnowman.func_70012_b((double)blockpos1.func_177958_n() + 0.5D, (double)blockpos1.func_177956_o() + 0.05D, (double)blockpos1.func_177952_p() + 0.5D, 0.0F, 0.0F);
         p_180673_1_.func_72838_d(entitysnowman);

         for(EntityPlayerMP entityplayermp : p_180673_1_.func_72872_a(EntityPlayerMP.class, entitysnowman.func_174813_aQ().func_186662_g(5.0D))) {
            CriteriaTriggers.field_192133_m.func_192229_a(entityplayermp, entitysnowman);
         }

         for(int l = 0; l < 120; ++l) {
            p_180673_1_.func_175688_a(EnumParticleTypes.SNOW_SHOVEL, (double)blockpos1.func_177958_n() + p_180673_1_.field_73012_v.nextDouble(), (double)blockpos1.func_177956_o() + p_180673_1_.field_73012_v.nextDouble() * 2.5D, (double)blockpos1.func_177952_p() + p_180673_1_.field_73012_v.nextDouble(), 0.0D, 0.0D, 0.0D);
         }

         for(int i1 = 0; i1 < this.func_176391_l().func_177685_b(); ++i1) {
            BlockWorldState blockworldstate2 = blockpattern$patternhelper.func_177670_a(0, i1, 0);
            p_180673_1_.func_175722_b(blockworldstate2.func_177508_d(), Blocks.field_150350_a, false);
         }
      } else {
         blockpattern$patternhelper = this.func_176388_T().func_177681_a(p_180673_1_, p_180673_2_);
         if (blockpattern$patternhelper != null) {
            for(int j = 0; j < this.func_176388_T().func_177684_c(); ++j) {
               for(int k = 0; k < this.func_176388_T().func_177685_b(); ++k) {
                  p_180673_1_.func_180501_a(blockpattern$patternhelper.func_177670_a(j, k, 0).func_177508_d(), Blocks.field_150350_a.func_176223_P(), 2);
               }
            }

            BlockPos blockpos = blockpattern$patternhelper.func_177670_a(1, 2, 0).func_177508_d();
            EntityIronGolem entityirongolem = new EntityIronGolem(p_180673_1_);
            entityirongolem.func_70849_f(true);
            entityirongolem.func_70012_b((double)blockpos.func_177958_n() + 0.5D, (double)blockpos.func_177956_o() + 0.05D, (double)blockpos.func_177952_p() + 0.5D, 0.0F, 0.0F);
            p_180673_1_.func_72838_d(entityirongolem);

            for(EntityPlayerMP entityplayermp1 : p_180673_1_.func_72872_a(EntityPlayerMP.class, entityirongolem.func_174813_aQ().func_186662_g(5.0D))) {
               CriteriaTriggers.field_192133_m.func_192229_a(entityplayermp1, entityirongolem);
            }

            for(int j1 = 0; j1 < 120; ++j1) {
               p_180673_1_.func_175688_a(EnumParticleTypes.SNOWBALL, (double)blockpos.func_177958_n() + p_180673_1_.field_73012_v.nextDouble(), (double)blockpos.func_177956_o() + p_180673_1_.field_73012_v.nextDouble() * 3.9D, (double)blockpos.func_177952_p() + p_180673_1_.field_73012_v.nextDouble(), 0.0D, 0.0D, 0.0D);
            }

            for(int k1 = 0; k1 < this.func_176388_T().func_177684_c(); ++k1) {
               for(int l1 = 0; l1 < this.func_176388_T().func_177685_b(); ++l1) {
                  BlockWorldState blockworldstate1 = blockpattern$patternhelper.func_177670_a(k1, l1, 0);
                  p_180673_1_.func_175722_b(blockworldstate1.func_177508_d(), Blocks.field_150350_a, false);
               }
            }
         }
      }

   }

   public boolean func_176196_c(World p_176196_1_, BlockPos p_176196_2_) {
      return p_176196_1_.func_180495_p(p_176196_2_).func_177230_c().field_149764_J.func_76222_j() && p_176196_1_.func_180495_p(p_176196_2_.func_177977_b()).func_185896_q();
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      return p_185499_1_.func_177226_a(field_185512_D, p_185499_2_.func_185831_a((EnumFacing)p_185499_1_.func_177229_b(field_185512_D)));
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      return p_185471_1_.func_185907_a(p_185471_2_.func_185800_a((EnumFacing)p_185471_1_.func_177229_b(field_185512_D)));
   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      return this.func_176223_P().func_177226_a(field_185512_D, p_180642_8_.func_174811_aO().func_176734_d());
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_185512_D, EnumFacing.func_176731_b(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((EnumFacing)p_176201_1_.func_177229_b(field_185512_D)).func_176736_b();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_185512_D});
   }

   protected BlockPattern func_176392_j() {
      if (this.field_176394_a == null) {
         this.field_176394_a = FactoryBlockPattern.func_177660_a().func_177659_a(" ", "#", "#").func_177662_a('#', BlockWorldState.func_177510_a(BlockStateMatcher.func_177638_a(Blocks.field_150433_aE))).func_177661_b();
      }

      return this.field_176394_a;
   }

   protected BlockPattern func_176391_l() {
      if (this.field_176393_b == null) {
         this.field_176393_b = FactoryBlockPattern.func_177660_a().func_177659_a("^", "#", "#").func_177662_a('^', BlockWorldState.func_177510_a(field_181085_Q)).func_177662_a('#', BlockWorldState.func_177510_a(BlockStateMatcher.func_177638_a(Blocks.field_150433_aE))).func_177661_b();
      }

      return this.field_176393_b;
   }

   protected BlockPattern func_176389_S() {
      if (this.field_176395_M == null) {
         this.field_176395_M = FactoryBlockPattern.func_177660_a().func_177659_a("~ ~", "###", "~#~").func_177662_a('#', BlockWorldState.func_177510_a(BlockStateMatcher.func_177638_a(Blocks.field_150339_S))).func_177662_a('~', BlockWorldState.func_177510_a(BlockMaterialMatcher.func_189886_a(Material.field_151579_a))).func_177661_b();
      }

      return this.field_176395_M;
   }

   protected BlockPattern func_176388_T() {
      if (this.field_176396_O == null) {
         this.field_176396_O = FactoryBlockPattern.func_177660_a().func_177659_a("~^~", "###", "~#~").func_177662_a('^', BlockWorldState.func_177510_a(field_181085_Q)).func_177662_a('#', BlockWorldState.func_177510_a(BlockStateMatcher.func_177638_a(Blocks.field_150339_S))).func_177662_a('~', BlockWorldState.func_177510_a(BlockMaterialMatcher.func_189886_a(Material.field_151579_a))).func_177661_b();
      }

      return this.field_176396_O;
   }
}
