package net.minecraft.world.gen.structure;

import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class ComponentScatteredFeaturePieces {
   public static void func_143045_a() {
      MapGenStructureIO.func_143031_a(ComponentScatteredFeaturePieces.DesertPyramid.class, "TeDP");
      MapGenStructureIO.func_143031_a(ComponentScatteredFeaturePieces.JunglePyramid.class, "TeJP");
      MapGenStructureIO.func_143031_a(ComponentScatteredFeaturePieces.SwampHut.class, "TeSH");
      MapGenStructureIO.func_143031_a(ComponentScatteredFeaturePieces.Igloo.class, "Iglu");
   }

   public static class DesertPyramid extends ComponentScatteredFeaturePieces.Feature {
      private final boolean[] field_74940_h = new boolean[4];

      public DesertPyramid() {
      }

      public DesertPyramid(Random p_i2062_1_, int p_i2062_2_, int p_i2062_3_) {
         super(p_i2062_1_, p_i2062_2_, 64, p_i2062_3_, 21, 15, 21);
      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         super.func_143012_a(p_143012_1_);
         p_143012_1_.func_74757_a("hasPlacedChest0", this.field_74940_h[0]);
         p_143012_1_.func_74757_a("hasPlacedChest1", this.field_74940_h[1]);
         p_143012_1_.func_74757_a("hasPlacedChest2", this.field_74940_h[2]);
         p_143012_1_.func_74757_a("hasPlacedChest3", this.field_74940_h[3]);
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         super.func_143011_b(p_143011_1_, p_143011_2_);
         this.field_74940_h[0] = p_143011_1_.func_74767_n("hasPlacedChest0");
         this.field_74940_h[1] = p_143011_1_.func_74767_n("hasPlacedChest1");
         this.field_74940_h[2] = p_143011_1_.func_74767_n("hasPlacedChest2");
         this.field_74940_h[3] = p_143011_1_.func_74767_n("hasPlacedChest3");
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, -4, 0, this.field_74939_a - 1, 0, this.field_74938_c - 1, Blocks.field_150322_A.func_176223_P(), Blocks.field_150322_A.func_176223_P(), false);

         for(int i = 1; i <= 9; ++i) {
            this.func_175804_a(p_74875_1_, p_74875_3_, i, i, i, this.field_74939_a - 1 - i, i, this.field_74938_c - 1 - i, Blocks.field_150322_A.func_176223_P(), Blocks.field_150322_A.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, i + 1, i, i + 1, this.field_74939_a - 2 - i, i, this.field_74938_c - 2 - i, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         }

         for(int i2 = 0; i2 < this.field_74939_a; ++i2) {
            for(int j = 0; j < this.field_74938_c; ++j) {
               int k = -5;
               this.func_175808_b(p_74875_1_, Blocks.field_150322_A.func_176223_P(), i2, -5, j, p_74875_3_);
            }
         }

         IBlockState iblockstate1 = Blocks.field_150372_bz.func_176223_P().func_177226_a(BlockStairs.field_176309_a, EnumFacing.NORTH);
         IBlockState iblockstate2 = Blocks.field_150372_bz.func_176223_P().func_177226_a(BlockStairs.field_176309_a, EnumFacing.SOUTH);
         IBlockState iblockstate3 = Blocks.field_150372_bz.func_176223_P().func_177226_a(BlockStairs.field_176309_a, EnumFacing.EAST);
         IBlockState iblockstate = Blocks.field_150372_bz.func_176223_P().func_177226_a(BlockStairs.field_176309_a, EnumFacing.WEST);
         int l = ~EnumDyeColor.ORANGE.func_176767_b() & 15;
         int i1 = ~EnumDyeColor.BLUE.func_176767_b() & 15;
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 9, 4, Blocks.field_150322_A.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 10, 1, 3, 10, 3, Blocks.field_150322_A.func_176223_P(), Blocks.field_150322_A.func_176223_P(), false);
         this.func_175811_a(p_74875_1_, iblockstate1, 2, 10, 0, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate2, 2, 10, 4, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate3, 0, 10, 2, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate, 4, 10, 2, p_74875_3_);
         this.func_175804_a(p_74875_1_, p_74875_3_, this.field_74939_a - 5, 0, 0, this.field_74939_a - 1, 9, 4, Blocks.field_150322_A.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, this.field_74939_a - 4, 10, 1, this.field_74939_a - 2, 10, 3, Blocks.field_150322_A.func_176223_P(), Blocks.field_150322_A.func_176223_P(), false);
         this.func_175811_a(p_74875_1_, iblockstate1, this.field_74939_a - 3, 10, 0, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate2, this.field_74939_a - 3, 10, 4, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate3, this.field_74939_a - 5, 10, 2, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate, this.field_74939_a - 1, 10, 2, p_74875_3_);
         this.func_175804_a(p_74875_1_, p_74875_3_, 8, 0, 0, 12, 4, 4, Blocks.field_150322_A.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 9, 1, 0, 11, 3, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 9, 1, 1, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 9, 2, 1, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 9, 3, 1, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 10, 3, 1, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 11, 3, 1, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 11, 2, 1, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 11, 1, 1, p_74875_3_);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 1, 1, 8, 3, 3, Blocks.field_150322_A.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 1, 2, 8, 2, 2, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 12, 1, 1, 16, 3, 3, Blocks.field_150322_A.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 12, 1, 2, 16, 2, 2, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 5, 4, 5, this.field_74939_a - 6, 4, this.field_74938_c - 6, Blocks.field_150322_A.func_176223_P(), Blocks.field_150322_A.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 9, 4, 9, 11, 4, 11, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 8, 1, 8, 8, 3, 8, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 12, 1, 8, 12, 3, 8, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 8, 1, 12, 8, 3, 12, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 12, 1, 12, 12, 3, 12, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 1, 5, 4, 4, 11, Blocks.field_150322_A.func_176223_P(), Blocks.field_150322_A.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, this.field_74939_a - 5, 1, 5, this.field_74939_a - 2, 4, 11, Blocks.field_150322_A.func_176223_P(), Blocks.field_150322_A.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 6, 7, 9, 6, 7, 11, Blocks.field_150322_A.func_176223_P(), Blocks.field_150322_A.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, this.field_74939_a - 7, 7, 9, this.field_74939_a - 7, 7, 11, Blocks.field_150322_A.func_176223_P(), Blocks.field_150322_A.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 5, 5, 9, 5, 7, 11, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, this.field_74939_a - 6, 5, 9, this.field_74939_a - 6, 7, 11, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
         this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 5, 5, 10, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 5, 6, 10, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 6, 6, 10, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), this.field_74939_a - 6, 5, 10, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), this.field_74939_a - 6, 6, 10, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), this.field_74939_a - 7, 6, 10, p_74875_3_);
         this.func_175804_a(p_74875_1_, p_74875_3_, 2, 4, 4, 2, 6, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, this.field_74939_a - 3, 4, 4, this.field_74939_a - 3, 6, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175811_a(p_74875_1_, iblockstate1, 2, 4, 5, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate1, 2, 3, 4, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate1, this.field_74939_a - 3, 4, 5, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate1, this.field_74939_a - 3, 3, 4, p_74875_3_);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 1, 3, 2, 2, 3, Blocks.field_150322_A.func_176223_P(), Blocks.field_150322_A.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, this.field_74939_a - 3, 1, 3, this.field_74939_a - 2, 2, 3, Blocks.field_150322_A.func_176223_P(), Blocks.field_150322_A.func_176223_P(), false);
         this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176223_P(), 1, 1, 2, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176223_P(), this.field_74939_a - 2, 1, 2, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150333_U.func_176203_a(BlockStoneSlab.EnumType.SAND.func_176624_a()), 1, 2, 2, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150333_U.func_176203_a(BlockStoneSlab.EnumType.SAND.func_176624_a()), this.field_74939_a - 2, 2, 2, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate, 2, 1, 2, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate3, this.field_74939_a - 3, 1, 2, p_74875_3_);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 3, 5, 4, 3, 18, Blocks.field_150322_A.func_176223_P(), Blocks.field_150322_A.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, this.field_74939_a - 5, 3, 5, this.field_74939_a - 5, 3, 17, Blocks.field_150322_A.func_176223_P(), Blocks.field_150322_A.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 3, 1, 5, 4, 2, 16, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, this.field_74939_a - 6, 1, 5, this.field_74939_a - 5, 2, 16, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);

         for(int j1 = 5; j1 <= 17; j1 += 2) {
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 4, 1, j1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.CHISELED.func_176675_a()), 4, 2, j1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), this.field_74939_a - 5, 1, j1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.CHISELED.func_176675_a()), this.field_74939_a - 5, 2, j1, p_74875_3_);
         }

         this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), 10, 0, 7, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), 10, 0, 8, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), 9, 0, 9, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), 11, 0, 9, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), 8, 0, 10, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), 12, 0, 10, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), 7, 0, 10, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), 13, 0, 10, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), 9, 0, 11, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), 11, 0, 11, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), 10, 0, 12, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), 10, 0, 13, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(i1), 10, 0, 10, p_74875_3_);

         for(int j2 = 0; j2 <= this.field_74939_a - 1; j2 += this.field_74939_a - 1) {
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), j2, 2, 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), j2, 2, 2, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), j2, 2, 3, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), j2, 3, 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), j2, 3, 2, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), j2, 3, 3, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), j2, 4, 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.CHISELED.func_176675_a()), j2, 4, 2, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), j2, 4, 3, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), j2, 5, 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), j2, 5, 2, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), j2, 5, 3, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), j2, 6, 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.CHISELED.func_176675_a()), j2, 6, 2, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), j2, 6, 3, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), j2, 7, 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), j2, 7, 2, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), j2, 7, 3, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), j2, 8, 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), j2, 8, 2, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), j2, 8, 3, p_74875_3_);
         }

         for(int k2 = 2; k2 <= this.field_74939_a - 3; k2 += this.field_74939_a - 3 - 2) {
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), k2 - 1, 2, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), k2, 2, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), k2 + 1, 2, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), k2 - 1, 3, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), k2, 3, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), k2 + 1, 3, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), k2 - 1, 4, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.CHISELED.func_176675_a()), k2, 4, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), k2 + 1, 4, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), k2 - 1, 5, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), k2, 5, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), k2 + 1, 5, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), k2 - 1, 6, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.CHISELED.func_176675_a()), k2, 6, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), k2 + 1, 6, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), k2 - 1, 7, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), k2, 7, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), k2 + 1, 7, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), k2 - 1, 8, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), k2, 8, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), k2 + 1, 8, 0, p_74875_3_);
         }

         this.func_175804_a(p_74875_1_, p_74875_3_, 8, 4, 0, 12, 6, 0, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
         this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 8, 6, 0, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 12, 6, 0, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), 9, 5, 0, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.CHISELED.func_176675_a()), 10, 5, 0, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150406_ce.func_176203_a(l), 11, 5, 0, p_74875_3_);
         this.func_175804_a(p_74875_1_, p_74875_3_, 8, -14, 8, 12, -11, 12, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 8, -10, 8, 12, -10, 12, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.CHISELED.func_176675_a()), Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.CHISELED.func_176675_a()), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 8, -9, 8, 12, -9, 12, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 8, -8, 8, 12, -1, 12, Blocks.field_150322_A.func_176223_P(), Blocks.field_150322_A.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 9, -11, 9, 11, -1, 11, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175811_a(p_74875_1_, Blocks.field_150456_au.func_176223_P(), 10, -11, 10, p_74875_3_);
         this.func_175804_a(p_74875_1_, p_74875_3_, 9, -13, 9, 11, -13, 11, Blocks.field_150335_W.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 8, -11, 10, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 8, -10, 10, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.CHISELED.func_176675_a()), 7, -10, 10, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 7, -11, 10, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 12, -11, 10, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 12, -10, 10, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.CHISELED.func_176675_a()), 13, -10, 10, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 13, -11, 10, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 10, -11, 8, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 10, -10, 8, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.CHISELED.func_176675_a()), 10, -10, 7, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 10, -11, 7, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 10, -11, 12, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 10, -10, 12, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.CHISELED.func_176675_a()), 10, -10, 13, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150322_A.func_176203_a(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 10, -11, 13, p_74875_3_);

         for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
            if (!this.field_74940_h[enumfacing.func_176736_b()]) {
               int k1 = enumfacing.func_82601_c() * 2;
               int l1 = enumfacing.func_82599_e() * 2;
               this.field_74940_h[enumfacing.func_176736_b()] = this.func_186167_a(p_74875_1_, p_74875_3_, p_74875_2_, 10 + k1, -11, 10 + l1, LootTableList.field_186429_k);
            }
         }

         return true;
      }
   }

   abstract static class Feature extends StructureComponent {
      protected int field_74939_a;
      protected int field_74937_b;
      protected int field_74938_c;
      protected int field_74936_d = -1;

      public Feature() {
      }

      protected Feature(Random p_i2065_1_, int p_i2065_2_, int p_i2065_3_, int p_i2065_4_, int p_i2065_5_, int p_i2065_6_, int p_i2065_7_) {
         super(0);
         this.field_74939_a = p_i2065_5_;
         this.field_74937_b = p_i2065_6_;
         this.field_74938_c = p_i2065_7_;
         this.func_186164_a(EnumFacing.Plane.HORIZONTAL.func_179518_a(p_i2065_1_));
         if (this.func_186165_e().func_176740_k() == EnumFacing.Axis.Z) {
            this.field_74887_e = new StructureBoundingBox(p_i2065_2_, p_i2065_3_, p_i2065_4_, p_i2065_2_ + p_i2065_5_ - 1, p_i2065_3_ + p_i2065_6_ - 1, p_i2065_4_ + p_i2065_7_ - 1);
         } else {
            this.field_74887_e = new StructureBoundingBox(p_i2065_2_, p_i2065_3_, p_i2065_4_, p_i2065_2_ + p_i2065_7_ - 1, p_i2065_3_ + p_i2065_6_ - 1, p_i2065_4_ + p_i2065_5_ - 1);
         }

      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         p_143012_1_.func_74768_a("Width", this.field_74939_a);
         p_143012_1_.func_74768_a("Height", this.field_74937_b);
         p_143012_1_.func_74768_a("Depth", this.field_74938_c);
         p_143012_1_.func_74768_a("HPos", this.field_74936_d);
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         this.field_74939_a = p_143011_1_.func_74762_e("Width");
         this.field_74937_b = p_143011_1_.func_74762_e("Height");
         this.field_74938_c = p_143011_1_.func_74762_e("Depth");
         this.field_74936_d = p_143011_1_.func_74762_e("HPos");
      }

      protected boolean func_74935_a(World p_74935_1_, StructureBoundingBox p_74935_2_, int p_74935_3_) {
         if (this.field_74936_d >= 0) {
            return true;
         } else {
            int i = 0;
            int j = 0;
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for(int k = this.field_74887_e.field_78896_c; k <= this.field_74887_e.field_78892_f; ++k) {
               for(int l = this.field_74887_e.field_78897_a; l <= this.field_74887_e.field_78893_d; ++l) {
                  blockpos$mutableblockpos.func_181079_c(l, 64, k);
                  if (p_74935_2_.func_175898_b(blockpos$mutableblockpos)) {
                     i += Math.max(p_74935_1_.func_175672_r(blockpos$mutableblockpos).func_177956_o(), p_74935_1_.field_73011_w.func_76557_i());
                     ++j;
                  }
               }
            }

            if (j == 0) {
               return false;
            } else {
               this.field_74936_d = i / j;
               this.field_74887_e.func_78886_a(0, this.field_74936_d - this.field_74887_e.field_78895_b + p_74935_3_, 0);
               return true;
            }
         }
      }
   }

   public static class Igloo extends ComponentScatteredFeaturePieces.Feature {
      private static final ResourceLocation field_186170_e = new ResourceLocation("igloo/igloo_top");
      private static final ResourceLocation field_186171_f = new ResourceLocation("igloo/igloo_middle");
      private static final ResourceLocation field_186172_g = new ResourceLocation("igloo/igloo_bottom");

      public Igloo() {
      }

      public Igloo(Random p_i47036_1_, int p_i47036_2_, int p_i47036_3_) {
         super(p_i47036_1_, p_i47036_2_, 64, p_i47036_3_, 7, 5, 8);
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         if (!this.func_74935_a(p_74875_1_, p_74875_3_, -1)) {
            return false;
         } else {
            StructureBoundingBox structureboundingbox = this.func_74874_b();
            BlockPos blockpos = new BlockPos(structureboundingbox.field_78897_a, structureboundingbox.field_78895_b, structureboundingbox.field_78896_c);
            Rotation[] arotation = Rotation.values();
            MinecraftServer minecraftserver = p_74875_1_.func_73046_m();
            TemplateManager templatemanager = p_74875_1_.func_72860_G().func_186340_h();
            PlacementSettings placementsettings = (new PlacementSettings()).func_186220_a(arotation[p_74875_2_.nextInt(arotation.length)]).func_186225_a(Blocks.field_189881_dj).func_186223_a(structureboundingbox);
            Template template = templatemanager.func_186237_a(minecraftserver, field_186170_e);
            template.func_186260_a(p_74875_1_, blockpos, placementsettings);
            if (p_74875_2_.nextDouble() < 0.5D) {
               Template template1 = templatemanager.func_186237_a(minecraftserver, field_186171_f);
               Template template2 = templatemanager.func_186237_a(minecraftserver, field_186172_g);
               int i = p_74875_2_.nextInt(8) + 4;

               for(int j = 0; j < i; ++j) {
                  BlockPos blockpos1 = template.func_186262_a(placementsettings, new BlockPos(3, -1 - j * 3, 5), placementsettings, new BlockPos(1, 2, 1));
                  template1.func_186260_a(p_74875_1_, blockpos.func_177971_a(blockpos1), placementsettings);
               }

               BlockPos blockpos4 = blockpos.func_177971_a(template.func_186262_a(placementsettings, new BlockPos(3, -1 - i * 3, 5), placementsettings, new BlockPos(3, 5, 7)));
               template2.func_186260_a(p_74875_1_, blockpos4, placementsettings);
               Map<BlockPos, String> map = template2.func_186258_a(blockpos4, placementsettings);

               for(Entry<BlockPos, String> entry : map.entrySet()) {
                  if ("chest".equals(entry.getValue())) {
                     BlockPos blockpos2 = entry.getKey();
                     p_74875_1_.func_180501_a(blockpos2, Blocks.field_150350_a.func_176223_P(), 3);
                     TileEntity tileentity = p_74875_1_.func_175625_s(blockpos2.func_177977_b());
                     if (tileentity instanceof TileEntityChest) {
                        ((TileEntityChest)tileentity).func_189404_a(LootTableList.field_186431_m, p_74875_2_.nextLong());
                     }
                  }
               }
            } else {
               BlockPos blockpos3 = Template.func_186266_a(placementsettings, new BlockPos(3, 0, 5));
               p_74875_1_.func_180501_a(blockpos.func_177971_a(blockpos3), Blocks.field_150433_aE.func_176223_P(), 3);
            }

            return true;
         }
      }
   }

   public static class JunglePyramid extends ComponentScatteredFeaturePieces.Feature {
      private boolean field_74947_h;
      private boolean field_74948_i;
      private boolean field_74945_j;
      private boolean field_74946_k;
      private static final ComponentScatteredFeaturePieces.JunglePyramid.Stones field_74942_n = new ComponentScatteredFeaturePieces.JunglePyramid.Stones();

      public JunglePyramid() {
      }

      public JunglePyramid(Random p_i2064_1_, int p_i2064_2_, int p_i2064_3_) {
         super(p_i2064_1_, p_i2064_2_, 64, p_i2064_3_, 12, 10, 15);
      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         super.func_143012_a(p_143012_1_);
         p_143012_1_.func_74757_a("placedMainChest", this.field_74947_h);
         p_143012_1_.func_74757_a("placedHiddenChest", this.field_74948_i);
         p_143012_1_.func_74757_a("placedTrap1", this.field_74945_j);
         p_143012_1_.func_74757_a("placedTrap2", this.field_74946_k);
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         super.func_143011_b(p_143011_1_, p_143011_2_);
         this.field_74947_h = p_143011_1_.func_74767_n("placedMainChest");
         this.field_74948_i = p_143011_1_.func_74767_n("placedHiddenChest");
         this.field_74945_j = p_143011_1_.func_74767_n("placedTrap1");
         this.field_74946_k = p_143011_1_.func_74767_n("placedTrap2");
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         if (!this.func_74935_a(p_74875_1_, p_74875_3_, 0)) {
            return false;
         } else {
            this.func_74882_a(p_74875_1_, p_74875_3_, 0, -4, 0, this.field_74939_a - 1, 0, this.field_74938_c - 1, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 2, 1, 2, 9, 2, 2, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 2, 1, 12, 9, 2, 12, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 2, 1, 3, 2, 2, 11, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 9, 1, 3, 9, 2, 11, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 1, 3, 1, 10, 6, 1, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 1, 3, 13, 10, 6, 13, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 1, 3, 2, 1, 6, 12, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 10, 3, 2, 10, 6, 12, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 2, 3, 2, 9, 3, 12, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 2, 6, 2, 9, 6, 12, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 3, 7, 3, 8, 7, 11, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 4, 8, 4, 7, 8, 10, false, p_74875_2_, field_74942_n);
            this.func_74878_a(p_74875_1_, p_74875_3_, 3, 1, 3, 8, 2, 11);
            this.func_74878_a(p_74875_1_, p_74875_3_, 4, 3, 6, 7, 3, 9);
            this.func_74878_a(p_74875_1_, p_74875_3_, 2, 4, 2, 9, 5, 12);
            this.func_74878_a(p_74875_1_, p_74875_3_, 4, 6, 5, 7, 6, 9);
            this.func_74878_a(p_74875_1_, p_74875_3_, 5, 7, 6, 6, 7, 8);
            this.func_74878_a(p_74875_1_, p_74875_3_, 5, 1, 2, 6, 2, 2);
            this.func_74878_a(p_74875_1_, p_74875_3_, 5, 2, 12, 6, 2, 12);
            this.func_74878_a(p_74875_1_, p_74875_3_, 5, 5, 1, 6, 5, 1);
            this.func_74878_a(p_74875_1_, p_74875_3_, 5, 5, 13, 6, 5, 13);
            this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 1, 5, 5, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 10, 5, 5, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 1, 5, 9, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 10, 5, 9, p_74875_3_);

            for(int i = 0; i <= 14; i += 14) {
               this.func_74882_a(p_74875_1_, p_74875_3_, 2, 4, i, 2, 5, i, false, p_74875_2_, field_74942_n);
               this.func_74882_a(p_74875_1_, p_74875_3_, 4, 4, i, 4, 5, i, false, p_74875_2_, field_74942_n);
               this.func_74882_a(p_74875_1_, p_74875_3_, 7, 4, i, 7, 5, i, false, p_74875_2_, field_74942_n);
               this.func_74882_a(p_74875_1_, p_74875_3_, 9, 4, i, 9, 5, i, false, p_74875_2_, field_74942_n);
            }

            this.func_74882_a(p_74875_1_, p_74875_3_, 5, 6, 0, 6, 6, 0, false, p_74875_2_, field_74942_n);

            for(int l = 0; l <= 11; l += 11) {
               for(int j = 2; j <= 12; j += 2) {
                  this.func_74882_a(p_74875_1_, p_74875_3_, l, 4, j, l, 5, j, false, p_74875_2_, field_74942_n);
               }

               this.func_74882_a(p_74875_1_, p_74875_3_, l, 6, 5, l, 6, 5, false, p_74875_2_, field_74942_n);
               this.func_74882_a(p_74875_1_, p_74875_3_, l, 6, 9, l, 6, 9, false, p_74875_2_, field_74942_n);
            }

            this.func_74882_a(p_74875_1_, p_74875_3_, 2, 7, 2, 2, 9, 2, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 9, 7, 2, 9, 9, 2, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 2, 7, 12, 2, 9, 12, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 9, 7, 12, 9, 9, 12, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 4, 9, 4, 4, 9, 4, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 7, 9, 4, 7, 9, 4, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 4, 9, 10, 4, 9, 10, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 7, 9, 10, 7, 9, 10, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 5, 9, 7, 6, 9, 7, false, p_74875_2_, field_74942_n);
            IBlockState iblockstate2 = Blocks.field_150446_ar.func_176223_P().func_177226_a(BlockStairs.field_176309_a, EnumFacing.EAST);
            IBlockState iblockstate3 = Blocks.field_150446_ar.func_176223_P().func_177226_a(BlockStairs.field_176309_a, EnumFacing.WEST);
            IBlockState iblockstate = Blocks.field_150446_ar.func_176223_P().func_177226_a(BlockStairs.field_176309_a, EnumFacing.SOUTH);
            IBlockState iblockstate1 = Blocks.field_150446_ar.func_176223_P().func_177226_a(BlockStairs.field_176309_a, EnumFacing.NORTH);
            this.func_175811_a(p_74875_1_, iblockstate1, 5, 9, 6, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate1, 6, 9, 6, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate, 5, 9, 8, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate, 6, 9, 8, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate1, 4, 0, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate1, 5, 0, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate1, 6, 0, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate1, 7, 0, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate1, 4, 1, 8, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate1, 4, 2, 9, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate1, 4, 3, 10, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate1, 7, 1, 8, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate1, 7, 2, 9, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate1, 7, 3, 10, p_74875_3_);
            this.func_74882_a(p_74875_1_, p_74875_3_, 4, 1, 9, 4, 1, 9, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 7, 1, 9, 7, 1, 9, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 4, 1, 10, 7, 2, 10, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 5, 4, 5, 6, 4, 5, false, p_74875_2_, field_74942_n);
            this.func_175811_a(p_74875_1_, iblockstate2, 4, 4, 5, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate3, 7, 4, 5, p_74875_3_);

            for(int k = 0; k < 4; ++k) {
               this.func_175811_a(p_74875_1_, iblockstate, 5, 0 - k, 6 + k, p_74875_3_);
               this.func_175811_a(p_74875_1_, iblockstate, 6, 0 - k, 6 + k, p_74875_3_);
               this.func_74878_a(p_74875_1_, p_74875_3_, 5, 0 - k, 7 + k, 6, 0 - k, 9 + k);
            }

            this.func_74878_a(p_74875_1_, p_74875_3_, 1, -3, 12, 10, -1, 13);
            this.func_74878_a(p_74875_1_, p_74875_3_, 1, -3, 1, 3, -1, 13);
            this.func_74878_a(p_74875_1_, p_74875_3_, 1, -3, 1, 9, -1, 5);

            for(int i1 = 1; i1 <= 13; i1 += 2) {
               this.func_74882_a(p_74875_1_, p_74875_3_, 1, -3, i1, 1, -2, i1, false, p_74875_2_, field_74942_n);
            }

            for(int j1 = 2; j1 <= 12; j1 += 2) {
               this.func_74882_a(p_74875_1_, p_74875_3_, 1, -1, j1, 3, -1, j1, false, p_74875_2_, field_74942_n);
            }

            this.func_74882_a(p_74875_1_, p_74875_3_, 2, -2, 1, 5, -2, 1, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 7, -2, 1, 9, -2, 1, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 6, -3, 1, 6, -3, 1, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 6, -1, 1, 6, -1, 1, false, p_74875_2_, field_74942_n);
            this.func_175811_a(p_74875_1_, Blocks.field_150479_bC.func_176223_P().func_177226_a(BlockTripWireHook.field_176264_a, EnumFacing.EAST).func_177226_a(BlockTripWireHook.field_176265_M, Boolean.valueOf(true)), 1, -3, 8, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150479_bC.func_176223_P().func_177226_a(BlockTripWireHook.field_176264_a, EnumFacing.WEST).func_177226_a(BlockTripWireHook.field_176265_M, Boolean.valueOf(true)), 4, -3, 8, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150473_bD.func_176223_P().func_177226_a(BlockTripWire.field_176294_M, Boolean.valueOf(true)), 2, -3, 8, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150473_bD.func_176223_P().func_177226_a(BlockTripWire.field_176294_M, Boolean.valueOf(true)), 3, -3, 8, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150488_af.func_176223_P(), 5, -3, 7, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150488_af.func_176223_P(), 5, -3, 6, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150488_af.func_176223_P(), 5, -3, 5, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150488_af.func_176223_P(), 5, -3, 4, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150488_af.func_176223_P(), 5, -3, 3, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150488_af.func_176223_P(), 5, -3, 2, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150488_af.func_176223_P(), 5, -3, 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150488_af.func_176223_P(), 4, -3, 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150341_Y.func_176223_P(), 3, -3, 1, p_74875_3_);
            if (!this.field_74945_j) {
               this.field_74945_j = this.func_189419_a(p_74875_1_, p_74875_3_, p_74875_2_, 3, -2, 1, EnumFacing.NORTH, LootTableList.field_189420_m);
            }

            this.func_175811_a(p_74875_1_, Blocks.field_150395_bd.func_176223_P().func_177226_a(BlockVine.field_176279_N, Boolean.valueOf(true)), 3, -2, 2, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150479_bC.func_176223_P().func_177226_a(BlockTripWireHook.field_176264_a, EnumFacing.NORTH).func_177226_a(BlockTripWireHook.field_176265_M, Boolean.valueOf(true)), 7, -3, 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150479_bC.func_176223_P().func_177226_a(BlockTripWireHook.field_176264_a, EnumFacing.SOUTH).func_177226_a(BlockTripWireHook.field_176265_M, Boolean.valueOf(true)), 7, -3, 5, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150473_bD.func_176223_P().func_177226_a(BlockTripWire.field_176294_M, Boolean.valueOf(true)), 7, -3, 2, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150473_bD.func_176223_P().func_177226_a(BlockTripWire.field_176294_M, Boolean.valueOf(true)), 7, -3, 3, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150473_bD.func_176223_P().func_177226_a(BlockTripWire.field_176294_M, Boolean.valueOf(true)), 7, -3, 4, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150488_af.func_176223_P(), 8, -3, 6, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150488_af.func_176223_P(), 9, -3, 6, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150488_af.func_176223_P(), 9, -3, 5, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150341_Y.func_176223_P(), 9, -3, 4, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150488_af.func_176223_P(), 9, -2, 4, p_74875_3_);
            if (!this.field_74946_k) {
               this.field_74946_k = this.func_189419_a(p_74875_1_, p_74875_3_, p_74875_2_, 9, -2, 3, EnumFacing.WEST, LootTableList.field_189420_m);
            }

            this.func_175811_a(p_74875_1_, Blocks.field_150395_bd.func_176223_P().func_177226_a(BlockVine.field_176278_M, Boolean.valueOf(true)), 8, -1, 3, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150395_bd.func_176223_P().func_177226_a(BlockVine.field_176278_M, Boolean.valueOf(true)), 8, -2, 3, p_74875_3_);
            if (!this.field_74947_h) {
               this.field_74947_h = this.func_186167_a(p_74875_1_, p_74875_3_, p_74875_2_, 8, -3, 3, LootTableList.field_186430_l);
            }

            this.func_175811_a(p_74875_1_, Blocks.field_150341_Y.func_176223_P(), 9, -3, 2, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150341_Y.func_176223_P(), 8, -3, 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150341_Y.func_176223_P(), 4, -3, 5, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150341_Y.func_176223_P(), 5, -2, 5, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150341_Y.func_176223_P(), 5, -1, 5, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150341_Y.func_176223_P(), 6, -3, 5, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150341_Y.func_176223_P(), 7, -2, 5, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150341_Y.func_176223_P(), 7, -1, 5, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150341_Y.func_176223_P(), 8, -3, 5, p_74875_3_);
            this.func_74882_a(p_74875_1_, p_74875_3_, 9, -1, 1, 9, -1, 5, false, p_74875_2_, field_74942_n);
            this.func_74878_a(p_74875_1_, p_74875_3_, 8, -3, 8, 10, -1, 10);
            this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176203_a(BlockStoneBrick.field_176252_O), 8, -2, 11, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176203_a(BlockStoneBrick.field_176252_O), 9, -2, 11, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176203_a(BlockStoneBrick.field_176252_O), 10, -2, 11, p_74875_3_);
            IBlockState iblockstate4 = Blocks.field_150442_at.func_176223_P().func_177226_a(BlockLever.field_176360_a, BlockLever.EnumOrientation.NORTH);
            this.func_175811_a(p_74875_1_, iblockstate4, 8, -2, 12, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate4, 9, -2, 12, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate4, 10, -2, 12, p_74875_3_);
            this.func_74882_a(p_74875_1_, p_74875_3_, 8, -3, 8, 8, -3, 10, false, p_74875_2_, field_74942_n);
            this.func_74882_a(p_74875_1_, p_74875_3_, 10, -3, 8, 10, -3, 10, false, p_74875_2_, field_74942_n);
            this.func_175811_a(p_74875_1_, Blocks.field_150341_Y.func_176223_P(), 10, -2, 9, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150488_af.func_176223_P(), 8, -2, 9, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150488_af.func_176223_P(), 8, -2, 10, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150488_af.func_176223_P(), 10, -1, 9, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150320_F.func_176223_P().func_177226_a(BlockPistonBase.field_176387_N, EnumFacing.UP), 9, -2, 8, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150320_F.func_176223_P().func_177226_a(BlockPistonBase.field_176387_N, EnumFacing.WEST), 10, -2, 8, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150320_F.func_176223_P().func_177226_a(BlockPistonBase.field_176387_N, EnumFacing.WEST), 10, -1, 8, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150413_aR.func_176223_P().func_177226_a(BlockRedstoneRepeater.field_185512_D, EnumFacing.NORTH), 10, -2, 10, p_74875_3_);
            if (!this.field_74948_i) {
               this.field_74948_i = this.func_186167_a(p_74875_1_, p_74875_3_, p_74875_2_, 9, -3, 10, LootTableList.field_186430_l);
            }

            return true;
         }
      }

      static class Stones extends StructureComponent.BlockSelector {
         private Stones() {
         }

         public void func_75062_a(Random p_75062_1_, int p_75062_2_, int p_75062_3_, int p_75062_4_, boolean p_75062_5_) {
            if (p_75062_1_.nextFloat() < 0.4F) {
               this.field_151562_a = Blocks.field_150347_e.func_176223_P();
            } else {
               this.field_151562_a = Blocks.field_150341_Y.func_176223_P();
            }

         }
      }
   }

   public static class SwampHut extends ComponentScatteredFeaturePieces.Feature {
      private boolean field_82682_h;

      public SwampHut() {
      }

      public SwampHut(Random p_i2066_1_, int p_i2066_2_, int p_i2066_3_) {
         super(p_i2066_1_, p_i2066_2_, 64, p_i2066_3_, 7, 7, 9);
      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         super.func_143012_a(p_143012_1_);
         p_143012_1_.func_74757_a("Witch", this.field_82682_h);
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         super.func_143011_b(p_143011_1_, p_143011_2_);
         this.field_82682_h = p_143011_1_.func_74767_n("Witch");
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         if (!this.func_74935_a(p_74875_1_, p_74875_3_, 0)) {
            return false;
         } else {
            this.func_175804_a(p_74875_1_, p_74875_3_, 1, 1, 1, 5, 1, 7, Blocks.field_150344_f.func_176203_a(BlockPlanks.EnumType.SPRUCE.func_176839_a()), Blocks.field_150344_f.func_176203_a(BlockPlanks.EnumType.SPRUCE.func_176839_a()), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 1, 4, 2, 5, 4, 7, Blocks.field_150344_f.func_176203_a(BlockPlanks.EnumType.SPRUCE.func_176839_a()), Blocks.field_150344_f.func_176203_a(BlockPlanks.EnumType.SPRUCE.func_176839_a()), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 2, 1, 0, 4, 1, 0, Blocks.field_150344_f.func_176203_a(BlockPlanks.EnumType.SPRUCE.func_176839_a()), Blocks.field_150344_f.func_176203_a(BlockPlanks.EnumType.SPRUCE.func_176839_a()), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 2, 2, 2, 3, 3, 2, Blocks.field_150344_f.func_176203_a(BlockPlanks.EnumType.SPRUCE.func_176839_a()), Blocks.field_150344_f.func_176203_a(BlockPlanks.EnumType.SPRUCE.func_176839_a()), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 1, 2, 3, 1, 3, 6, Blocks.field_150344_f.func_176203_a(BlockPlanks.EnumType.SPRUCE.func_176839_a()), Blocks.field_150344_f.func_176203_a(BlockPlanks.EnumType.SPRUCE.func_176839_a()), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 5, 2, 3, 5, 3, 6, Blocks.field_150344_f.func_176203_a(BlockPlanks.EnumType.SPRUCE.func_176839_a()), Blocks.field_150344_f.func_176203_a(BlockPlanks.EnumType.SPRUCE.func_176839_a()), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 2, 2, 7, 4, 3, 7, Blocks.field_150344_f.func_176203_a(BlockPlanks.EnumType.SPRUCE.func_176839_a()), Blocks.field_150344_f.func_176203_a(BlockPlanks.EnumType.SPRUCE.func_176839_a()), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 1, 0, 2, 1, 3, 2, Blocks.field_150364_r.func_176223_P(), Blocks.field_150364_r.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 5, 0, 2, 5, 3, 2, Blocks.field_150364_r.func_176223_P(), Blocks.field_150364_r.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 1, 0, 7, 1, 3, 7, Blocks.field_150364_r.func_176223_P(), Blocks.field_150364_r.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 5, 0, 7, 5, 3, 7, Blocks.field_150364_r.func_176223_P(), Blocks.field_150364_r.func_176223_P(), false);
            this.func_175811_a(p_74875_1_, Blocks.field_180407_aO.func_176223_P(), 2, 3, 2, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_180407_aO.func_176223_P(), 3, 3, 7, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 1, 3, 4, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 5, 3, 4, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 5, 3, 5, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150457_bL.func_176223_P().func_177226_a(BlockFlowerPot.field_176443_b, BlockFlowerPot.EnumFlowerType.MUSHROOM_RED), 1, 3, 5, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150462_ai.func_176223_P(), 3, 2, 6, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150383_bp.func_176223_P(), 4, 2, 6, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_180407_aO.func_176223_P(), 1, 2, 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_180407_aO.func_176223_P(), 5, 2, 1, p_74875_3_);
            IBlockState iblockstate = Blocks.field_150485_bF.func_176223_P().func_177226_a(BlockStairs.field_176309_a, EnumFacing.NORTH);
            IBlockState iblockstate1 = Blocks.field_150485_bF.func_176223_P().func_177226_a(BlockStairs.field_176309_a, EnumFacing.EAST);
            IBlockState iblockstate2 = Blocks.field_150485_bF.func_176223_P().func_177226_a(BlockStairs.field_176309_a, EnumFacing.WEST);
            IBlockState iblockstate3 = Blocks.field_150485_bF.func_176223_P().func_177226_a(BlockStairs.field_176309_a, EnumFacing.SOUTH);
            this.func_175804_a(p_74875_1_, p_74875_3_, 0, 4, 1, 6, 4, 1, iblockstate, iblockstate, false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 0, 4, 2, 0, 4, 7, iblockstate1, iblockstate1, false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 6, 4, 2, 6, 4, 7, iblockstate2, iblockstate2, false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 0, 4, 8, 6, 4, 8, iblockstate3, iblockstate3, false);

            for(int i = 2; i <= 7; i += 5) {
               for(int j = 1; j <= 5; j += 4) {
                  this.func_175808_b(p_74875_1_, Blocks.field_150364_r.func_176223_P(), j, -1, i, p_74875_3_);
               }
            }

            if (!this.field_82682_h) {
               int l = this.func_74865_a(2, 5);
               int i1 = this.func_74862_a(2);
               int k = this.func_74873_b(2, 5);
               if (p_74875_3_.func_175898_b(new BlockPos(l, i1, k))) {
                  this.field_82682_h = true;
                  EntityWitch entitywitch = new EntityWitch(p_74875_1_);
                  entitywitch.func_110163_bv();
                  entitywitch.func_70012_b((double)l + 0.5D, (double)i1, (double)k + 0.5D, 0.0F, 0.0F);
                  entitywitch.func_180482_a(p_74875_1_.func_175649_E(new BlockPos(l, i1, k)), (IEntityLivingData)null);
                  p_74875_1_.func_72838_d(entitywitch);
               }
            }

            return true;
         }
      }
   }
}
