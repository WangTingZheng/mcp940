package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockDropper;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStem;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlabNew;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockWall;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.BlockStateMapper;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

public class BlockModelShapes {
   private final Map<IBlockState, IBakedModel> field_178129_a = Maps.<IBlockState, IBakedModel>newIdentityHashMap();
   private final BlockStateMapper field_178127_b = new BlockStateMapper();
   private final ModelManager field_178128_c;

   public BlockModelShapes(ModelManager p_i46245_1_) {
      this.field_178128_c = p_i46245_1_;
      this.func_178119_d();
   }

   public BlockStateMapper func_178120_a() {
      return this.field_178127_b;
   }

   public TextureAtlasSprite func_178122_a(IBlockState p_178122_1_) {
      Block block = p_178122_1_.func_177230_c();
      IBakedModel ibakedmodel = this.func_178125_b(p_178122_1_);
      if (ibakedmodel == null || ibakedmodel == this.field_178128_c.func_174951_a()) {
         if (block == Blocks.field_150444_as || block == Blocks.field_150472_an || block == Blocks.field_150486_ae || block == Blocks.field_150447_bR || block == Blocks.field_180393_cK || block == Blocks.field_180394_cL || block == Blocks.field_150324_C) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:blocks/planks_oak");
         }

         if (block == Blocks.field_150477_bB) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:blocks/obsidian");
         }

         if (block == Blocks.field_150356_k || block == Blocks.field_150353_l) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:blocks/lava_still");
         }

         if (block == Blocks.field_150358_i || block == Blocks.field_150355_j) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:blocks/water_still");
         }

         if (block == Blocks.field_150465_bP) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:blocks/soul_sand");
         }

         if (block == Blocks.field_180401_cv) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:items/barrier");
         }

         if (block == Blocks.field_189881_dj) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:items/structure_void");
         }

         if (block == Blocks.field_190977_dl) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:blocks/shulker_top_white");
         }

         if (block == Blocks.field_190978_dm) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:blocks/shulker_top_orange");
         }

         if (block == Blocks.field_190979_dn) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:blocks/shulker_top_magenta");
         }

         if (block == Blocks.field_190980_do) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:blocks/shulker_top_light_blue");
         }

         if (block == Blocks.field_190981_dp) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:blocks/shulker_top_yellow");
         }

         if (block == Blocks.field_190982_dq) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:blocks/shulker_top_lime");
         }

         if (block == Blocks.field_190983_dr) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:blocks/shulker_top_pink");
         }

         if (block == Blocks.field_190984_ds) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:blocks/shulker_top_gray");
         }

         if (block == Blocks.field_190985_dt) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:blocks/shulker_top_silver");
         }

         if (block == Blocks.field_190986_du) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:blocks/shulker_top_cyan");
         }

         if (block == Blocks.field_190987_dv) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:blocks/shulker_top_purple");
         }

         if (block == Blocks.field_190988_dw) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:blocks/shulker_top_blue");
         }

         if (block == Blocks.field_190989_dx) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:blocks/shulker_top_brown");
         }

         if (block == Blocks.field_190990_dy) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:blocks/shulker_top_green");
         }

         if (block == Blocks.field_190991_dz) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:blocks/shulker_top_red");
         }

         if (block == Blocks.field_190975_dA) {
            return this.field_178128_c.func_174952_b().func_110572_b("minecraft:blocks/shulker_top_black");
         }
      }

      if (ibakedmodel == null) {
         ibakedmodel = this.field_178128_c.func_174951_a();
      }

      return ibakedmodel.func_177554_e();
   }

   public IBakedModel func_178125_b(IBlockState p_178125_1_) {
      IBakedModel ibakedmodel = this.field_178129_a.get(p_178125_1_);
      if (ibakedmodel == null) {
         ibakedmodel = this.field_178128_c.func_174951_a();
      }

      return ibakedmodel;
   }

   public ModelManager func_178126_b() {
      return this.field_178128_c;
   }

   public void func_178124_c() {
      this.field_178129_a.clear();

      for(Entry<IBlockState, ModelResourceLocation> entry : this.field_178127_b.func_178446_a().entrySet()) {
         this.field_178129_a.put(entry.getKey(), this.field_178128_c.func_174953_a(entry.getValue()));
      }

   }

   public void func_178121_a(Block p_178121_1_, IStateMapper p_178121_2_) {
      this.field_178127_b.func_178447_a(p_178121_1_, p_178121_2_);
   }

   public void func_178123_a(Block... p_178123_1_) {
      this.field_178127_b.func_178448_a(p_178123_1_);
   }

   private void func_178119_d() {
      this.func_178123_a(Blocks.field_150350_a, Blocks.field_150358_i, Blocks.field_150355_j, Blocks.field_150356_k, Blocks.field_150353_l, Blocks.field_180384_M, Blocks.field_150486_ae, Blocks.field_150477_bB, Blocks.field_150447_bR, Blocks.field_150472_an, Blocks.field_150465_bP, Blocks.field_150384_bq, Blocks.field_180401_cv, Blocks.field_150444_as, Blocks.field_180394_cL, Blocks.field_180393_cK, Blocks.field_185775_db, Blocks.field_189881_dj, Blocks.field_190977_dl, Blocks.field_190978_dm, Blocks.field_190979_dn, Blocks.field_190980_do, Blocks.field_190981_dp, Blocks.field_190982_dq, Blocks.field_190983_dr, Blocks.field_190984_ds, Blocks.field_190985_dt, Blocks.field_190986_du, Blocks.field_190987_dv, Blocks.field_190988_dw, Blocks.field_190989_dx, Blocks.field_190990_dy, Blocks.field_190991_dz, Blocks.field_190975_dA, Blocks.field_150324_C);
      this.func_178121_a(Blocks.field_150348_b, (new StateMap.Builder()).func_178440_a(BlockStone.field_176247_a).func_178441_a());
      this.func_178121_a(Blocks.field_180397_cI, (new StateMap.Builder()).func_178440_a(BlockPrismarine.field_176332_a).func_178441_a());
      this.func_178121_a(Blocks.field_150362_t, (new StateMap.Builder()).func_178440_a(BlockOldLeaf.field_176239_P).func_178439_a("_leaves").func_178442_a(BlockLeaves.field_176236_b, BlockLeaves.field_176237_a).func_178441_a());
      this.func_178121_a(Blocks.field_150361_u, (new StateMap.Builder()).func_178440_a(BlockNewLeaf.field_176240_P).func_178439_a("_leaves").func_178442_a(BlockLeaves.field_176236_b, BlockLeaves.field_176237_a).func_178441_a());
      this.func_178121_a(Blocks.field_150434_aF, (new StateMap.Builder()).func_178442_a(BlockCactus.field_176587_a).func_178441_a());
      this.func_178121_a(Blocks.field_150436_aH, (new StateMap.Builder()).func_178442_a(BlockReed.field_176355_a).func_178441_a());
      this.func_178121_a(Blocks.field_150421_aI, (new StateMap.Builder()).func_178442_a(BlockJukebox.field_176432_a).func_178441_a());
      this.func_178121_a(Blocks.field_150463_bK, (new StateMap.Builder()).func_178440_a(BlockWall.field_176255_P).func_178439_a("_wall").func_178441_a());
      this.func_178121_a(Blocks.field_150398_cm, (new StateMap.Builder()).func_178440_a(BlockDoublePlant.field_176493_a).func_178442_a(BlockDoublePlant.field_181084_N).func_178441_a());
      this.func_178121_a(Blocks.field_180390_bo, (new StateMap.Builder()).func_178442_a(BlockFenceGate.field_176465_b).func_178441_a());
      this.func_178121_a(Blocks.field_180391_bp, (new StateMap.Builder()).func_178442_a(BlockFenceGate.field_176465_b).func_178441_a());
      this.func_178121_a(Blocks.field_180392_bq, (new StateMap.Builder()).func_178442_a(BlockFenceGate.field_176465_b).func_178441_a());
      this.func_178121_a(Blocks.field_180386_br, (new StateMap.Builder()).func_178442_a(BlockFenceGate.field_176465_b).func_178441_a());
      this.func_178121_a(Blocks.field_180385_bs, (new StateMap.Builder()).func_178442_a(BlockFenceGate.field_176465_b).func_178441_a());
      this.func_178121_a(Blocks.field_180387_bt, (new StateMap.Builder()).func_178442_a(BlockFenceGate.field_176465_b).func_178441_a());
      this.func_178121_a(Blocks.field_150473_bD, (new StateMap.Builder()).func_178442_a(BlockTripWire.field_176295_N, BlockTripWire.field_176293_a).func_178441_a());
      this.func_178121_a(Blocks.field_150373_bw, (new StateMap.Builder()).func_178440_a(BlockPlanks.field_176383_a).func_178439_a("_double_slab").func_178441_a());
      this.func_178121_a(Blocks.field_150376_bx, (new StateMap.Builder()).func_178440_a(BlockPlanks.field_176383_a).func_178439_a("_slab").func_178441_a());
      this.func_178121_a(Blocks.field_150335_W, (new StateMap.Builder()).func_178442_a(BlockTNT.field_176246_a).func_178441_a());
      this.func_178121_a(Blocks.field_150480_ab, (new StateMap.Builder()).func_178442_a(BlockFire.field_176543_a).func_178441_a());
      this.func_178121_a(Blocks.field_150488_af, (new StateMap.Builder()).func_178442_a(BlockRedstoneWire.field_176351_O).func_178441_a());
      this.func_178121_a(Blocks.field_180413_ao, (new StateMap.Builder()).func_178442_a(BlockDoor.field_176522_N).func_178441_a());
      this.func_178121_a(Blocks.field_180414_ap, (new StateMap.Builder()).func_178442_a(BlockDoor.field_176522_N).func_178441_a());
      this.func_178121_a(Blocks.field_180412_aq, (new StateMap.Builder()).func_178442_a(BlockDoor.field_176522_N).func_178441_a());
      this.func_178121_a(Blocks.field_180411_ar, (new StateMap.Builder()).func_178442_a(BlockDoor.field_176522_N).func_178441_a());
      this.func_178121_a(Blocks.field_180410_as, (new StateMap.Builder()).func_178442_a(BlockDoor.field_176522_N).func_178441_a());
      this.func_178121_a(Blocks.field_180409_at, (new StateMap.Builder()).func_178442_a(BlockDoor.field_176522_N).func_178441_a());
      this.func_178121_a(Blocks.field_150454_av, (new StateMap.Builder()).func_178442_a(BlockDoor.field_176522_N).func_178441_a());
      this.func_178121_a(Blocks.field_150325_L, (new StateMap.Builder()).func_178440_a(BlockColored.field_176581_a).func_178439_a("_wool").func_178441_a());
      this.func_178121_a(Blocks.field_150404_cg, (new StateMap.Builder()).func_178440_a(BlockColored.field_176581_a).func_178439_a("_carpet").func_178441_a());
      this.func_178121_a(Blocks.field_150406_ce, (new StateMap.Builder()).func_178440_a(BlockColored.field_176581_a).func_178439_a("_stained_hardened_clay").func_178441_a());
      this.func_178121_a(Blocks.field_150397_co, (new StateMap.Builder()).func_178440_a(BlockColored.field_176581_a).func_178439_a("_stained_glass_pane").func_178441_a());
      this.func_178121_a(Blocks.field_150399_cn, (new StateMap.Builder()).func_178440_a(BlockColored.field_176581_a).func_178439_a("_stained_glass").func_178441_a());
      this.func_178121_a(Blocks.field_150322_A, (new StateMap.Builder()).func_178440_a(BlockSandStone.field_176297_a).func_178441_a());
      this.func_178121_a(Blocks.field_180395_cM, (new StateMap.Builder()).func_178440_a(BlockRedSandstone.field_176336_a).func_178441_a());
      this.func_178121_a(Blocks.field_150329_H, (new StateMap.Builder()).func_178440_a(BlockTallGrass.field_176497_a).func_178441_a());
      this.func_178121_a(Blocks.field_150327_N, (new StateMap.Builder()).func_178440_a(Blocks.field_150327_N.func_176494_l()).func_178441_a());
      this.func_178121_a(Blocks.field_150328_O, (new StateMap.Builder()).func_178440_a(Blocks.field_150328_O.func_176494_l()).func_178441_a());
      this.func_178121_a(Blocks.field_150333_U, (new StateMap.Builder()).func_178440_a(BlockStoneSlab.field_176556_M).func_178439_a("_slab").func_178441_a());
      this.func_178121_a(Blocks.field_180389_cP, (new StateMap.Builder()).func_178440_a(BlockStoneSlabNew.field_176559_M).func_178439_a("_slab").func_178441_a());
      this.func_178121_a(Blocks.field_150418_aU, (new StateMap.Builder()).func_178440_a(BlockSilverfish.field_176378_a).func_178439_a("_monster_egg").func_178441_a());
      this.func_178121_a(Blocks.field_150417_aV, (new StateMap.Builder()).func_178440_a(BlockStoneBrick.field_176249_a).func_178441_a());
      this.func_178121_a(Blocks.field_150367_z, (new StateMap.Builder()).func_178442_a(BlockDispenser.field_176440_b).func_178441_a());
      this.func_178121_a(Blocks.field_150409_cd, (new StateMap.Builder()).func_178442_a(BlockDropper.field_176440_b).func_178441_a());
      this.func_178121_a(Blocks.field_150364_r, (new StateMap.Builder()).func_178440_a(BlockOldLog.field_176301_b).func_178439_a("_log").func_178441_a());
      this.func_178121_a(Blocks.field_150363_s, (new StateMap.Builder()).func_178440_a(BlockNewLog.field_176300_b).func_178439_a("_log").func_178441_a());
      this.func_178121_a(Blocks.field_150344_f, (new StateMap.Builder()).func_178440_a(BlockPlanks.field_176383_a).func_178439_a("_planks").func_178441_a());
      this.func_178121_a(Blocks.field_150345_g, (new StateMap.Builder()).func_178440_a(BlockSapling.field_176480_a).func_178439_a("_sapling").func_178441_a());
      this.func_178121_a(Blocks.field_150354_m, (new StateMap.Builder()).func_178440_a(BlockSand.field_176504_a).func_178441_a());
      this.func_178121_a(Blocks.field_150438_bZ, (new StateMap.Builder()).func_178442_a(BlockHopper.field_176429_b).func_178441_a());
      this.func_178121_a(Blocks.field_150457_bL, (new StateMap.Builder()).func_178442_a(BlockFlowerPot.field_176444_a).func_178441_a());
      this.func_178121_a(Blocks.field_192443_dR, (new StateMap.Builder()).func_178440_a(BlockColored.field_176581_a).func_178439_a("_concrete").func_178441_a());
      this.func_178121_a(Blocks.field_192444_dS, (new StateMap.Builder()).func_178440_a(BlockColored.field_176581_a).func_178439_a("_concrete_powder").func_178441_a());
      this.func_178121_a(Blocks.field_150371_ca, new StateMapperBase() {
         protected ModelResourceLocation func_178132_a(IBlockState p_178132_1_) {
            BlockQuartz.EnumType blockquartz$enumtype = (BlockQuartz.EnumType)p_178132_1_.func_177229_b(BlockQuartz.field_176335_a);
            switch(blockquartz$enumtype) {
            case DEFAULT:
            default:
               return new ModelResourceLocation("quartz_block", "normal");
            case CHISELED:
               return new ModelResourceLocation("chiseled_quartz_block", "normal");
            case LINES_Y:
               return new ModelResourceLocation("quartz_column", "axis=y");
            case LINES_X:
               return new ModelResourceLocation("quartz_column", "axis=x");
            case LINES_Z:
               return new ModelResourceLocation("quartz_column", "axis=z");
            }
         }
      });
      this.func_178121_a(Blocks.field_150330_I, new StateMapperBase() {
         protected ModelResourceLocation func_178132_a(IBlockState p_178132_1_) {
            return new ModelResourceLocation("dead_bush", "normal");
         }
      });
      this.func_178121_a(Blocks.field_150393_bb, new StateMapperBase() {
         protected ModelResourceLocation func_178132_a(IBlockState p_178132_1_) {
            Map<IProperty<?>, Comparable<?>> map = Maps.<IProperty<?>, Comparable<?>>newLinkedHashMap(p_178132_1_.func_177228_b());
            if (p_178132_1_.func_177229_b(BlockStem.field_176483_b) != EnumFacing.UP) {
               map.remove(BlockStem.field_176484_a);
            }

            return new ModelResourceLocation(Block.field_149771_c.func_177774_c(p_178132_1_.func_177230_c()), this.func_178131_a(map));
         }
      });
      this.func_178121_a(Blocks.field_150394_bc, new StateMapperBase() {
         protected ModelResourceLocation func_178132_a(IBlockState p_178132_1_) {
            Map<IProperty<?>, Comparable<?>> map = Maps.<IProperty<?>, Comparable<?>>newLinkedHashMap(p_178132_1_.func_177228_b());
            if (p_178132_1_.func_177229_b(BlockStem.field_176483_b) != EnumFacing.UP) {
               map.remove(BlockStem.field_176484_a);
            }

            return new ModelResourceLocation(Block.field_149771_c.func_177774_c(p_178132_1_.func_177230_c()), this.func_178131_a(map));
         }
      });
      this.func_178121_a(Blocks.field_150346_d, new StateMapperBase() {
         protected ModelResourceLocation func_178132_a(IBlockState p_178132_1_) {
            Map<IProperty<?>, Comparable<?>> map = Maps.<IProperty<?>, Comparable<?>>newLinkedHashMap(p_178132_1_.func_177228_b());
            String s = BlockDirt.field_176386_a.func_177702_a((BlockDirt.DirtType)map.remove(BlockDirt.field_176386_a));
            if (BlockDirt.DirtType.PODZOL != p_178132_1_.func_177229_b(BlockDirt.field_176386_a)) {
               map.remove(BlockDirt.field_176385_b);
            }

            return new ModelResourceLocation(s, this.func_178131_a(map));
         }
      });
      this.func_178121_a(Blocks.field_150334_T, new StateMapperBase() {
         protected ModelResourceLocation func_178132_a(IBlockState p_178132_1_) {
            Map<IProperty<?>, Comparable<?>> map = Maps.<IProperty<?>, Comparable<?>>newLinkedHashMap(p_178132_1_.func_177228_b());
            String s = BlockStoneSlab.field_176556_M.func_177702_a((BlockStoneSlab.EnumType)map.remove(BlockStoneSlab.field_176556_M));
            map.remove(BlockStoneSlab.field_176555_b);
            String s1 = ((Boolean)p_178132_1_.func_177229_b(BlockStoneSlab.field_176555_b)).booleanValue() ? "all" : "normal";
            return new ModelResourceLocation(s + "_double_slab", s1);
         }
      });
      this.func_178121_a(Blocks.field_180388_cO, new StateMapperBase() {
         protected ModelResourceLocation func_178132_a(IBlockState p_178132_1_) {
            Map<IProperty<?>, Comparable<?>> map = Maps.<IProperty<?>, Comparable<?>>newLinkedHashMap(p_178132_1_.func_177228_b());
            String s = BlockStoneSlabNew.field_176559_M.func_177702_a((BlockStoneSlabNew.EnumType)map.remove(BlockStoneSlabNew.field_176559_M));
            map.remove(BlockStoneSlab.field_176555_b);
            String s1 = ((Boolean)p_178132_1_.func_177229_b(BlockStoneSlabNew.field_176558_b)).booleanValue() ? "all" : "normal";
            return new ModelResourceLocation(s + "_double_slab", s1);
         }
      });
   }
}
