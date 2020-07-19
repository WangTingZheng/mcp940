package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class StructureNetherBridgePieces {
   private static final StructureNetherBridgePieces.PieceWeight[] field_78742_a = new StructureNetherBridgePieces.PieceWeight[]{new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Straight.class, 30, 0, true), new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Crossing3.class, 10, 4), new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Crossing.class, 10, 4), new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Stairs.class, 10, 3), new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Throne.class, 5, 2), new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Entrance.class, 5, 1)};
   private static final StructureNetherBridgePieces.PieceWeight[] field_78741_b = new StructureNetherBridgePieces.PieceWeight[]{new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor5.class, 25, 0, true), new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Crossing2.class, 15, 5), new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor2.class, 5, 10), new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor.class, 5, 10), new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor3.class, 10, 3, true), new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor4.class, 7, 2), new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.NetherStalkRoom.class, 5, 2)};

   public static void func_143049_a() {
      MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Crossing3.class, "NeBCr");
      MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.End.class, "NeBEF");
      MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Straight.class, "NeBS");
      MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Corridor3.class, "NeCCS");
      MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Corridor4.class, "NeCTB");
      MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Entrance.class, "NeCE");
      MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Crossing2.class, "NeSCSC");
      MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Corridor.class, "NeSCLT");
      MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Corridor5.class, "NeSC");
      MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Corridor2.class, "NeSCRT");
      MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.NetherStalkRoom.class, "NeCSR");
      MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Throne.class, "NeMT");
      MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Crossing.class, "NeRC");
      MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Stairs.class, "NeSR");
      MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Start.class, "NeStart");
   }

   private static StructureNetherBridgePieces.Piece func_175887_b(StructureNetherBridgePieces.PieceWeight p_175887_0_, List<StructureComponent> p_175887_1_, Random p_175887_2_, int p_175887_3_, int p_175887_4_, int p_175887_5_, EnumFacing p_175887_6_, int p_175887_7_) {
      Class<? extends StructureNetherBridgePieces.Piece> oclass = p_175887_0_.field_78828_a;
      StructureNetherBridgePieces.Piece structurenetherbridgepieces$piece = null;
      if (oclass == StructureNetherBridgePieces.Straight.class) {
         structurenetherbridgepieces$piece = StructureNetherBridgePieces.Straight.func_175882_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
      } else if (oclass == StructureNetherBridgePieces.Crossing3.class) {
         structurenetherbridgepieces$piece = StructureNetherBridgePieces.Crossing3.func_175885_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
      } else if (oclass == StructureNetherBridgePieces.Crossing.class) {
         structurenetherbridgepieces$piece = StructureNetherBridgePieces.Crossing.func_175873_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
      } else if (oclass == StructureNetherBridgePieces.Stairs.class) {
         structurenetherbridgepieces$piece = StructureNetherBridgePieces.Stairs.func_175872_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_7_, p_175887_6_);
      } else if (oclass == StructureNetherBridgePieces.Throne.class) {
         structurenetherbridgepieces$piece = StructureNetherBridgePieces.Throne.func_175874_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_7_, p_175887_6_);
      } else if (oclass == StructureNetherBridgePieces.Entrance.class) {
         structurenetherbridgepieces$piece = StructureNetherBridgePieces.Entrance.func_175881_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
      } else if (oclass == StructureNetherBridgePieces.Corridor5.class) {
         structurenetherbridgepieces$piece = StructureNetherBridgePieces.Corridor5.func_175877_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
      } else if (oclass == StructureNetherBridgePieces.Corridor2.class) {
         structurenetherbridgepieces$piece = StructureNetherBridgePieces.Corridor2.func_175876_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
      } else if (oclass == StructureNetherBridgePieces.Corridor.class) {
         structurenetherbridgepieces$piece = StructureNetherBridgePieces.Corridor.func_175879_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
      } else if (oclass == StructureNetherBridgePieces.Corridor3.class) {
         structurenetherbridgepieces$piece = StructureNetherBridgePieces.Corridor3.func_175883_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
      } else if (oclass == StructureNetherBridgePieces.Corridor4.class) {
         structurenetherbridgepieces$piece = StructureNetherBridgePieces.Corridor4.func_175880_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
      } else if (oclass == StructureNetherBridgePieces.Crossing2.class) {
         structurenetherbridgepieces$piece = StructureNetherBridgePieces.Crossing2.func_175878_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
      } else if (oclass == StructureNetherBridgePieces.NetherStalkRoom.class) {
         structurenetherbridgepieces$piece = StructureNetherBridgePieces.NetherStalkRoom.func_175875_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
      }

      return structurenetherbridgepieces$piece;
   }

   public static class Corridor extends StructureNetherBridgePieces.Piece {
      private boolean field_111021_b;

      public Corridor() {
      }

      public Corridor(int p_i45615_1_, Random p_i45615_2_, StructureBoundingBox p_i45615_3_, EnumFacing p_i45615_4_) {
         super(p_i45615_1_);
         this.func_186164_a(p_i45615_4_);
         this.field_74887_e = p_i45615_3_;
         this.field_111021_b = p_i45615_2_.nextInt(3) == 0;
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         super.func_143011_b(p_143011_1_, p_143011_2_);
         this.field_111021_b = p_143011_1_.func_74767_n("Chest");
      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         super.func_143012_a(p_143012_1_);
         p_143012_1_.func_74757_a("Chest", this.field_111021_b);
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         this.func_74961_b((StructureNetherBridgePieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, 1, true);
      }

      public static StructureNetherBridgePieces.Corridor func_175879_a(List<StructureComponent> p_175879_0_, Random p_175879_1_, int p_175879_2_, int p_175879_3_, int p_175879_4_, EnumFacing p_175879_5_, int p_175879_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175879_2_, p_175879_3_, p_175879_4_, -1, 0, 0, 5, 7, 5, p_175879_5_);
         return func_74964_a(structureboundingbox) && StructureComponent.func_74883_a(p_175879_0_, structureboundingbox) == null ? new StructureNetherBridgePieces.Corridor(p_175879_6_, p_175879_1_, structureboundingbox, p_175879_5_) : null;
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 1, 4, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 0, 4, 5, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 2, 0, 4, 5, 4, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 3, 1, 4, 4, 1, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 3, 3, 4, 4, 3, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 0, 0, 5, 0, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 4, 3, 5, 4, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 3, 4, 1, 4, 4, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 3, 3, 4, 3, 4, 4, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         if (this.field_111021_b && p_74875_3_.func_175898_b(new BlockPos(this.func_74865_a(3, 3), this.func_74862_a(2), this.func_74873_b(3, 3)))) {
            this.field_111021_b = false;
            this.func_186167_a(p_74875_1_, p_74875_3_, p_74875_2_, 3, 2, 3, LootTableList.field_186425_g);
         }

         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 6, 0, 4, 6, 4, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);

         for(int i = 0; i <= 4; ++i) {
            for(int j = 0; j <= 4; ++j) {
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), i, -1, j, p_74875_3_);
            }
         }

         return true;
      }
   }

   public static class Corridor2 extends StructureNetherBridgePieces.Piece {
      private boolean field_111020_b;

      public Corridor2() {
      }

      public Corridor2(int p_i45613_1_, Random p_i45613_2_, StructureBoundingBox p_i45613_3_, EnumFacing p_i45613_4_) {
         super(p_i45613_1_);
         this.func_186164_a(p_i45613_4_);
         this.field_74887_e = p_i45613_3_;
         this.field_111020_b = p_i45613_2_.nextInt(3) == 0;
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         super.func_143011_b(p_143011_1_, p_143011_2_);
         this.field_111020_b = p_143011_1_.func_74767_n("Chest");
      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         super.func_143012_a(p_143012_1_);
         p_143012_1_.func_74757_a("Chest", this.field_111020_b);
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         this.func_74965_c((StructureNetherBridgePieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, 1, true);
      }

      public static StructureNetherBridgePieces.Corridor2 func_175876_a(List<StructureComponent> p_175876_0_, Random p_175876_1_, int p_175876_2_, int p_175876_3_, int p_175876_4_, EnumFacing p_175876_5_, int p_175876_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175876_2_, p_175876_3_, p_175876_4_, -1, 0, 0, 5, 7, 5, p_175876_5_);
         return func_74964_a(structureboundingbox) && StructureComponent.func_74883_a(p_175876_0_, structureboundingbox) == null ? new StructureNetherBridgePieces.Corridor2(p_175876_6_, p_175876_1_, structureboundingbox, p_175876_5_) : null;
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 1, 4, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 0, 4, 5, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 0, 0, 5, 4, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 3, 1, 0, 4, 1, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 3, 3, 0, 4, 3, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 2, 0, 4, 5, 0, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 2, 4, 4, 5, 4, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 3, 4, 1, 4, 4, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 3, 3, 4, 3, 4, 4, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         if (this.field_111020_b && p_74875_3_.func_175898_b(new BlockPos(this.func_74865_a(1, 3), this.func_74862_a(2), this.func_74873_b(1, 3)))) {
            this.field_111020_b = false;
            this.func_186167_a(p_74875_1_, p_74875_3_, p_74875_2_, 1, 2, 3, LootTableList.field_186425_g);
         }

         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 6, 0, 4, 6, 4, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);

         for(int i = 0; i <= 4; ++i) {
            for(int j = 0; j <= 4; ++j) {
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), i, -1, j, p_74875_3_);
            }
         }

         return true;
      }
   }

   public static class Corridor3 extends StructureNetherBridgePieces.Piece {
      public Corridor3() {
      }

      public Corridor3(int p_i45619_1_, Random p_i45619_2_, StructureBoundingBox p_i45619_3_, EnumFacing p_i45619_4_) {
         super(p_i45619_1_);
         this.func_186164_a(p_i45619_4_);
         this.field_74887_e = p_i45619_3_;
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         this.func_74963_a((StructureNetherBridgePieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, 1, 0, true);
      }

      public static StructureNetherBridgePieces.Corridor3 func_175883_a(List<StructureComponent> p_175883_0_, Random p_175883_1_, int p_175883_2_, int p_175883_3_, int p_175883_4_, EnumFacing p_175883_5_, int p_175883_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175883_2_, p_175883_3_, p_175883_4_, -1, -7, 0, 5, 14, 10, p_175883_5_);
         return func_74964_a(structureboundingbox) && StructureComponent.func_74883_a(p_175883_0_, structureboundingbox) == null ? new StructureNetherBridgePieces.Corridor3(p_175883_6_, p_175883_1_, structureboundingbox, p_175883_5_) : null;
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         IBlockState iblockstate = Blocks.field_150387_bl.func_176223_P().func_177226_a(BlockStairs.field_176309_a, EnumFacing.SOUTH);

         for(int i = 0; i <= 9; ++i) {
            int j = Math.max(1, 7 - i);
            int k = Math.min(Math.max(j + 5, 14 - i), 13);
            int l = i;
            this.func_175804_a(p_74875_1_, p_74875_3_, 0, 0, i, 4, j, i, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 1, j + 1, i, 3, k - 1, i, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            if (i <= 6) {
               this.func_175811_a(p_74875_1_, iblockstate, 1, j + 1, i, p_74875_3_);
               this.func_175811_a(p_74875_1_, iblockstate, 2, j + 1, i, p_74875_3_);
               this.func_175811_a(p_74875_1_, iblockstate, 3, j + 1, i, p_74875_3_);
            }

            this.func_175804_a(p_74875_1_, p_74875_3_, 0, k, i, 4, k, i, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 0, j + 1, i, 0, k - 1, i, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 4, j + 1, i, 4, k - 1, i, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
            if ((i & 1) == 0) {
               this.func_175804_a(p_74875_1_, p_74875_3_, 0, j + 2, i, 0, j + 3, i, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
               this.func_175804_a(p_74875_1_, p_74875_3_, 4, j + 2, i, 4, j + 3, i, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
            }

            for(int i1 = 0; i1 <= 4; ++i1) {
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), i1, -1, l, p_74875_3_);
            }
         }

         return true;
      }
   }

   public static class Corridor4 extends StructureNetherBridgePieces.Piece {
      public Corridor4() {
      }

      public Corridor4(int p_i45618_1_, Random p_i45618_2_, StructureBoundingBox p_i45618_3_, EnumFacing p_i45618_4_) {
         super(p_i45618_1_);
         this.func_186164_a(p_i45618_4_);
         this.field_74887_e = p_i45618_3_;
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         int i = 1;
         EnumFacing enumfacing = this.func_186165_e();
         if (enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.NORTH) {
            i = 5;
         }

         this.func_74961_b((StructureNetherBridgePieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, i, p_74861_3_.nextInt(8) > 0);
         this.func_74965_c((StructureNetherBridgePieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, i, p_74861_3_.nextInt(8) > 0);
      }

      public static StructureNetherBridgePieces.Corridor4 func_175880_a(List<StructureComponent> p_175880_0_, Random p_175880_1_, int p_175880_2_, int p_175880_3_, int p_175880_4_, EnumFacing p_175880_5_, int p_175880_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175880_2_, p_175880_3_, p_175880_4_, -3, 0, 0, 9, 7, 9, p_175880_5_);
         return func_74964_a(structureboundingbox) && StructureComponent.func_74883_a(p_175880_0_, structureboundingbox) == null ? new StructureNetherBridgePieces.Corridor4(p_175880_6_, p_175880_1_, structureboundingbox, p_175880_5_) : null;
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 0, 0, 8, 1, 8, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 0, 8, 5, 8, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 6, 0, 8, 6, 5, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 0, 2, 5, 0, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 6, 2, 0, 8, 5, 0, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 3, 0, 1, 4, 0, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 7, 3, 0, 7, 4, 0, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 4, 8, 2, 8, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 1, 4, 2, 2, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 6, 1, 4, 7, 2, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 3, 8, 8, 3, 8, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 3, 6, 0, 3, 7, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 8, 3, 6, 8, 3, 7, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 3, 4, 0, 5, 5, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 8, 3, 4, 8, 5, 5, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 3, 5, 2, 5, 5, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 6, 3, 5, 7, 5, 5, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 4, 5, 1, 5, 5, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 7, 4, 5, 7, 5, 5, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);

         for(int i = 0; i <= 5; ++i) {
            for(int j = 0; j <= 8; ++j) {
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), j, -1, i, p_74875_3_);
            }
         }

         return true;
      }
   }

   public static class Corridor5 extends StructureNetherBridgePieces.Piece {
      public Corridor5() {
      }

      public Corridor5(int p_i45614_1_, Random p_i45614_2_, StructureBoundingBox p_i45614_3_, EnumFacing p_i45614_4_) {
         super(p_i45614_1_);
         this.func_186164_a(p_i45614_4_);
         this.field_74887_e = p_i45614_3_;
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         this.func_74963_a((StructureNetherBridgePieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, 1, 0, true);
      }

      public static StructureNetherBridgePieces.Corridor5 func_175877_a(List<StructureComponent> p_175877_0_, Random p_175877_1_, int p_175877_2_, int p_175877_3_, int p_175877_4_, EnumFacing p_175877_5_, int p_175877_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175877_2_, p_175877_3_, p_175877_4_, -1, 0, 0, 5, 7, 5, p_175877_5_);
         return func_74964_a(structureboundingbox) && StructureComponent.func_74883_a(p_175877_0_, structureboundingbox) == null ? new StructureNetherBridgePieces.Corridor5(p_175877_6_, p_175877_1_, structureboundingbox, p_175877_5_) : null;
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 1, 4, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 0, 4, 5, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 0, 0, 5, 4, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 2, 0, 4, 5, 4, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 3, 1, 0, 4, 1, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 3, 3, 0, 4, 3, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 3, 1, 4, 4, 1, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 3, 3, 4, 4, 3, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 6, 0, 4, 6, 4, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);

         for(int i = 0; i <= 4; ++i) {
            for(int j = 0; j <= 4; ++j) {
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), i, -1, j, p_74875_3_);
            }
         }

         return true;
      }
   }

   public static class Crossing extends StructureNetherBridgePieces.Piece {
      public Crossing() {
      }

      public Crossing(int p_i45610_1_, Random p_i45610_2_, StructureBoundingBox p_i45610_3_, EnumFacing p_i45610_4_) {
         super(p_i45610_1_);
         this.func_186164_a(p_i45610_4_);
         this.field_74887_e = p_i45610_3_;
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         this.func_74963_a((StructureNetherBridgePieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, 2, 0, false);
         this.func_74961_b((StructureNetherBridgePieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, 2, false);
         this.func_74965_c((StructureNetherBridgePieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, 2, false);
      }

      public static StructureNetherBridgePieces.Crossing func_175873_a(List<StructureComponent> p_175873_0_, Random p_175873_1_, int p_175873_2_, int p_175873_3_, int p_175873_4_, EnumFacing p_175873_5_, int p_175873_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175873_2_, p_175873_3_, p_175873_4_, -2, 0, 0, 7, 9, 7, p_175873_5_);
         return func_74964_a(structureboundingbox) && StructureComponent.func_74883_a(p_175873_0_, structureboundingbox) == null ? new StructureNetherBridgePieces.Crossing(p_175873_6_, p_175873_1_, structureboundingbox, p_175873_5_) : null;
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 0, 0, 6, 1, 6, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 0, 6, 7, 6, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 0, 1, 6, 0, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 6, 1, 6, 6, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 5, 2, 0, 6, 6, 0, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 5, 2, 6, 6, 6, 6, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 0, 0, 6, 1, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 5, 0, 6, 6, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 6, 2, 0, 6, 6, 1, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 6, 2, 5, 6, 6, 6, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 2, 6, 0, 4, 6, 0, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 2, 5, 0, 4, 5, 0, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 2, 6, 6, 4, 6, 6, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 2, 5, 6, 4, 5, 6, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 6, 2, 0, 6, 4, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 5, 2, 0, 5, 4, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 6, 6, 2, 6, 6, 4, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 6, 5, 2, 6, 5, 4, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);

         for(int i = 0; i <= 6; ++i) {
            for(int j = 0; j <= 6; ++j) {
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), i, -1, j, p_74875_3_);
            }
         }

         return true;
      }
   }

   public static class Crossing2 extends StructureNetherBridgePieces.Piece {
      public Crossing2() {
      }

      public Crossing2(int p_i45616_1_, Random p_i45616_2_, StructureBoundingBox p_i45616_3_, EnumFacing p_i45616_4_) {
         super(p_i45616_1_);
         this.func_186164_a(p_i45616_4_);
         this.field_74887_e = p_i45616_3_;
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         this.func_74963_a((StructureNetherBridgePieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, 1, 0, true);
         this.func_74961_b((StructureNetherBridgePieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, 1, true);
         this.func_74965_c((StructureNetherBridgePieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, 1, true);
      }

      public static StructureNetherBridgePieces.Crossing2 func_175878_a(List<StructureComponent> p_175878_0_, Random p_175878_1_, int p_175878_2_, int p_175878_3_, int p_175878_4_, EnumFacing p_175878_5_, int p_175878_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175878_2_, p_175878_3_, p_175878_4_, -1, 0, 0, 5, 7, 5, p_175878_5_);
         return func_74964_a(structureboundingbox) && StructureComponent.func_74883_a(p_175878_0_, structureboundingbox) == null ? new StructureNetherBridgePieces.Crossing2(p_175878_6_, p_175878_1_, structureboundingbox, p_175878_5_) : null;
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 1, 4, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 0, 4, 5, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 0, 0, 5, 0, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 2, 0, 4, 5, 0, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 4, 0, 5, 4, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 2, 4, 4, 5, 4, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 6, 0, 4, 6, 4, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);

         for(int i = 0; i <= 4; ++i) {
            for(int j = 0; j <= 4; ++j) {
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), i, -1, j, p_74875_3_);
            }
         }

         return true;
      }
   }

   public static class Crossing3 extends StructureNetherBridgePieces.Piece {
      public Crossing3() {
      }

      public Crossing3(int p_i45622_1_, Random p_i45622_2_, StructureBoundingBox p_i45622_3_, EnumFacing p_i45622_4_) {
         super(p_i45622_1_);
         this.func_186164_a(p_i45622_4_);
         this.field_74887_e = p_i45622_3_;
      }

      protected Crossing3(Random p_i2042_1_, int p_i2042_2_, int p_i2042_3_) {
         super(0);
         this.func_186164_a(EnumFacing.Plane.HORIZONTAL.func_179518_a(p_i2042_1_));
         if (this.func_186165_e().func_176740_k() == EnumFacing.Axis.Z) {
            this.field_74887_e = new StructureBoundingBox(p_i2042_2_, 64, p_i2042_3_, p_i2042_2_ + 19 - 1, 73, p_i2042_3_ + 19 - 1);
         } else {
            this.field_74887_e = new StructureBoundingBox(p_i2042_2_, 64, p_i2042_3_, p_i2042_2_ + 19 - 1, 73, p_i2042_3_ + 19 - 1);
         }

      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         this.func_74963_a((StructureNetherBridgePieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, 8, 3, false);
         this.func_74961_b((StructureNetherBridgePieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, 3, 8, false);
         this.func_74965_c((StructureNetherBridgePieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, 3, 8, false);
      }

      public static StructureNetherBridgePieces.Crossing3 func_175885_a(List<StructureComponent> p_175885_0_, Random p_175885_1_, int p_175885_2_, int p_175885_3_, int p_175885_4_, EnumFacing p_175885_5_, int p_175885_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175885_2_, p_175885_3_, p_175885_4_, -8, -3, 0, 19, 10, 19, p_175885_5_);
         return func_74964_a(structureboundingbox) && StructureComponent.func_74883_a(p_175885_0_, structureboundingbox) == null ? new StructureNetherBridgePieces.Crossing3(p_175885_6_, p_175885_1_, structureboundingbox, p_175885_5_) : null;
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         this.func_175804_a(p_74875_1_, p_74875_3_, 7, 3, 0, 11, 4, 18, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 3, 7, 18, 4, 11, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 8, 5, 0, 10, 7, 18, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 5, 8, 18, 7, 10, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 7, 5, 0, 7, 5, 7, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 7, 5, 11, 7, 5, 18, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 11, 5, 0, 11, 5, 7, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 11, 5, 11, 11, 5, 18, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 5, 7, 7, 5, 7, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 11, 5, 7, 18, 5, 7, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 5, 11, 7, 5, 11, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 11, 5, 11, 18, 5, 11, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 7, 2, 0, 11, 2, 5, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 7, 2, 13, 11, 2, 18, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 7, 0, 0, 11, 1, 3, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 7, 0, 15, 11, 1, 18, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);

         for(int i = 7; i <= 11; ++i) {
            for(int j = 0; j <= 2; ++j) {
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), i, -1, j, p_74875_3_);
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), i, -1, 18 - j, p_74875_3_);
            }
         }

         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 7, 5, 2, 11, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 13, 2, 7, 18, 2, 11, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 0, 7, 3, 1, 11, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 15, 0, 7, 18, 1, 11, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);

         for(int k = 0; k <= 2; ++k) {
            for(int l = 7; l <= 11; ++l) {
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), k, -1, l, p_74875_3_);
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), 18 - k, -1, l, p_74875_3_);
            }
         }

         return true;
      }
   }

   public static class End extends StructureNetherBridgePieces.Piece {
      private int field_74972_a;

      public End() {
      }

      public End(int p_i45621_1_, Random p_i45621_2_, StructureBoundingBox p_i45621_3_, EnumFacing p_i45621_4_) {
         super(p_i45621_1_);
         this.func_186164_a(p_i45621_4_);
         this.field_74887_e = p_i45621_3_;
         this.field_74972_a = p_i45621_2_.nextInt();
      }

      public static StructureNetherBridgePieces.End func_175884_a(List<StructureComponent> p_175884_0_, Random p_175884_1_, int p_175884_2_, int p_175884_3_, int p_175884_4_, EnumFacing p_175884_5_, int p_175884_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175884_2_, p_175884_3_, p_175884_4_, -1, -3, 0, 5, 10, 8, p_175884_5_);
         return func_74964_a(structureboundingbox) && StructureComponent.func_74883_a(p_175884_0_, structureboundingbox) == null ? new StructureNetherBridgePieces.End(p_175884_6_, p_175884_1_, structureboundingbox, p_175884_5_) : null;
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         super.func_143011_b(p_143011_1_, p_143011_2_);
         this.field_74972_a = p_143011_1_.func_74762_e("Seed");
      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         super.func_143012_a(p_143012_1_);
         p_143012_1_.func_74768_a("Seed", this.field_74972_a);
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         Random random = new Random((long)this.field_74972_a);

         for(int i = 0; i <= 4; ++i) {
            for(int j = 3; j <= 4; ++j) {
               int k = random.nextInt(8);
               this.func_175804_a(p_74875_1_, p_74875_3_, i, j, 0, i, j, k, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
            }
         }

         int l = random.nextInt(8);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 5, 0, 0, 5, l, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         l = random.nextInt(8);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 5, 0, 4, 5, l, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);

         for(int i1 = 0; i1 <= 4; ++i1) {
            int k1 = random.nextInt(5);
            this.func_175804_a(p_74875_1_, p_74875_3_, i1, 2, 0, i1, 2, k1, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         }

         for(int j1 = 0; j1 <= 4; ++j1) {
            for(int l1 = 0; l1 <= 1; ++l1) {
               int i2 = random.nextInt(3);
               this.func_175804_a(p_74875_1_, p_74875_3_, j1, l1, 0, j1, l1, i2, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
            }
         }

         return true;
      }
   }

   public static class Entrance extends StructureNetherBridgePieces.Piece {
      public Entrance() {
      }

      public Entrance(int p_i45617_1_, Random p_i45617_2_, StructureBoundingBox p_i45617_3_, EnumFacing p_i45617_4_) {
         super(p_i45617_1_);
         this.func_186164_a(p_i45617_4_);
         this.field_74887_e = p_i45617_3_;
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         this.func_74963_a((StructureNetherBridgePieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, 5, 3, true);
      }

      public static StructureNetherBridgePieces.Entrance func_175881_a(List<StructureComponent> p_175881_0_, Random p_175881_1_, int p_175881_2_, int p_175881_3_, int p_175881_4_, EnumFacing p_175881_5_, int p_175881_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175881_2_, p_175881_3_, p_175881_4_, -5, -3, 0, 13, 14, 13, p_175881_5_);
         return func_74964_a(structureboundingbox) && StructureComponent.func_74883_a(p_175881_0_, structureboundingbox) == null ? new StructureNetherBridgePieces.Entrance(p_175881_6_, p_175881_1_, structureboundingbox, p_175881_5_) : null;
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 3, 0, 12, 4, 12, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 5, 0, 12, 13, 12, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 5, 0, 1, 12, 12, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 11, 5, 0, 12, 12, 12, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 2, 5, 11, 4, 12, 12, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 8, 5, 11, 10, 12, 12, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 5, 9, 11, 7, 12, 12, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 2, 5, 0, 4, 12, 1, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 8, 5, 0, 10, 12, 1, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 5, 9, 0, 7, 12, 1, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 2, 11, 2, 10, 12, 10, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 5, 8, 0, 7, 8, 0, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);

         for(int i = 1; i <= 11; i += 2) {
            this.func_175804_a(p_74875_1_, p_74875_3_, i, 10, 0, i, 11, 0, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, i, 10, 12, i, 11, 12, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 0, 10, i, 0, 11, i, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 12, 10, i, 12, 11, i, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
            this.func_175811_a(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), i, 13, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), i, 13, 12, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), 0, 13, i, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), 12, 13, i, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150386_bk.func_176223_P(), i + 1, 13, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150386_bk.func_176223_P(), i + 1, 13, 12, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150386_bk.func_176223_P(), 0, 13, i + 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150386_bk.func_176223_P(), 12, 13, i + 1, p_74875_3_);
         }

         this.func_175811_a(p_74875_1_, Blocks.field_150386_bk.func_176223_P(), 0, 13, 0, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150386_bk.func_176223_P(), 0, 13, 12, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150386_bk.func_176223_P(), 0, 13, 0, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150386_bk.func_176223_P(), 12, 13, 0, p_74875_3_);

         for(int k = 3; k <= 9; k += 2) {
            this.func_175804_a(p_74875_1_, p_74875_3_, 1, 7, k, 1, 8, k, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 11, 7, k, 11, 8, k, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         }

         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 2, 0, 8, 2, 12, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 4, 12, 2, 8, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 0, 0, 8, 1, 3, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 0, 9, 8, 1, 12, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 0, 4, 3, 1, 8, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 9, 0, 4, 12, 1, 8, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);

         for(int l = 4; l <= 8; ++l) {
            for(int j = 0; j <= 2; ++j) {
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), l, -1, j, p_74875_3_);
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), l, -1, 12 - j, p_74875_3_);
            }
         }

         for(int i1 = 0; i1 <= 2; ++i1) {
            for(int j1 = 4; j1 <= 8; ++j1) {
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), i1, -1, j1, p_74875_3_);
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), 12 - i1, -1, j1, p_74875_3_);
            }
         }

         this.func_175804_a(p_74875_1_, p_74875_3_, 5, 5, 5, 7, 5, 7, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 6, 1, 6, 6, 4, 6, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175811_a(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), 6, 0, 6, p_74875_3_);
         IBlockState iblockstate = Blocks.field_150356_k.func_176223_P();
         this.func_175811_a(p_74875_1_, iblockstate, 6, 5, 6, p_74875_3_);
         BlockPos blockpos = new BlockPos(this.func_74865_a(6, 6), this.func_74862_a(5), this.func_74873_b(6, 6));
         if (p_74875_3_.func_175898_b(blockpos)) {
            p_74875_1_.func_189507_a(blockpos, iblockstate, p_74875_2_);
         }

         return true;
      }
   }

   public static class NetherStalkRoom extends StructureNetherBridgePieces.Piece {
      public NetherStalkRoom() {
      }

      public NetherStalkRoom(int p_i45612_1_, Random p_i45612_2_, StructureBoundingBox p_i45612_3_, EnumFacing p_i45612_4_) {
         super(p_i45612_1_);
         this.func_186164_a(p_i45612_4_);
         this.field_74887_e = p_i45612_3_;
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         this.func_74963_a((StructureNetherBridgePieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, 5, 3, true);
         this.func_74963_a((StructureNetherBridgePieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, 5, 11, true);
      }

      public static StructureNetherBridgePieces.NetherStalkRoom func_175875_a(List<StructureComponent> p_175875_0_, Random p_175875_1_, int p_175875_2_, int p_175875_3_, int p_175875_4_, EnumFacing p_175875_5_, int p_175875_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175875_2_, p_175875_3_, p_175875_4_, -5, -3, 0, 13, 14, 13, p_175875_5_);
         return func_74964_a(structureboundingbox) && StructureComponent.func_74883_a(p_175875_0_, structureboundingbox) == null ? new StructureNetherBridgePieces.NetherStalkRoom(p_175875_6_, p_175875_1_, structureboundingbox, p_175875_5_) : null;
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 3, 0, 12, 4, 12, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 5, 0, 12, 13, 12, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 5, 0, 1, 12, 12, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 11, 5, 0, 12, 12, 12, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 2, 5, 11, 4, 12, 12, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 8, 5, 11, 10, 12, 12, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 5, 9, 11, 7, 12, 12, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 2, 5, 0, 4, 12, 1, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 8, 5, 0, 10, 12, 1, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 5, 9, 0, 7, 12, 1, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 2, 11, 2, 10, 12, 10, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);

         for(int i = 1; i <= 11; i += 2) {
            this.func_175804_a(p_74875_1_, p_74875_3_, i, 10, 0, i, 11, 0, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, i, 10, 12, i, 11, 12, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 0, 10, i, 0, 11, i, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 12, 10, i, 12, 11, i, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
            this.func_175811_a(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), i, 13, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), i, 13, 12, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), 0, 13, i, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), 12, 13, i, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150386_bk.func_176223_P(), i + 1, 13, 0, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150386_bk.func_176223_P(), i + 1, 13, 12, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150386_bk.func_176223_P(), 0, 13, i + 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150386_bk.func_176223_P(), 12, 13, i + 1, p_74875_3_);
         }

         this.func_175811_a(p_74875_1_, Blocks.field_150386_bk.func_176223_P(), 0, 13, 0, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150386_bk.func_176223_P(), 0, 13, 12, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150386_bk.func_176223_P(), 0, 13, 0, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150386_bk.func_176223_P(), 12, 13, 0, p_74875_3_);

         for(int j1 = 3; j1 <= 9; j1 += 2) {
            this.func_175804_a(p_74875_1_, p_74875_3_, 1, 7, j1, 1, 8, j1, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 11, 7, j1, 11, 8, j1, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         }

         IBlockState iblockstate = Blocks.field_150387_bl.func_176223_P().func_177226_a(BlockStairs.field_176309_a, EnumFacing.NORTH);

         for(int j = 0; j <= 6; ++j) {
            int k = j + 4;

            for(int l = 5; l <= 7; ++l) {
               this.func_175811_a(p_74875_1_, iblockstate, l, 5 + j, k, p_74875_3_);
            }

            if (k >= 5 && k <= 8) {
               this.func_175804_a(p_74875_1_, p_74875_3_, 5, 5, k, 7, j + 4, k, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
            } else if (k >= 9 && k <= 10) {
               this.func_175804_a(p_74875_1_, p_74875_3_, 5, 8, k, 7, j + 4, k, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
            }

            if (j >= 1) {
               this.func_175804_a(p_74875_1_, p_74875_3_, 5, 6 + j, k, 7, 9 + j, k, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            }
         }

         for(int k1 = 5; k1 <= 7; ++k1) {
            this.func_175811_a(p_74875_1_, iblockstate, k1, 12, 11, p_74875_3_);
         }

         this.func_175804_a(p_74875_1_, p_74875_3_, 5, 6, 7, 5, 7, 7, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 7, 6, 7, 7, 7, 7, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 5, 13, 12, 7, 13, 12, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 2, 5, 2, 3, 5, 3, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 2, 5, 9, 3, 5, 10, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 2, 5, 4, 2, 5, 8, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 9, 5, 2, 10, 5, 3, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 9, 5, 9, 10, 5, 10, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 10, 5, 4, 10, 5, 8, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         IBlockState iblockstate1 = iblockstate.func_177226_a(BlockStairs.field_176309_a, EnumFacing.EAST);
         IBlockState iblockstate2 = iblockstate.func_177226_a(BlockStairs.field_176309_a, EnumFacing.WEST);
         this.func_175811_a(p_74875_1_, iblockstate2, 4, 5, 2, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate2, 4, 5, 3, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate2, 4, 5, 9, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate2, 4, 5, 10, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate1, 8, 5, 2, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate1, 8, 5, 3, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate1, 8, 5, 9, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate1, 8, 5, 10, p_74875_3_);
         this.func_175804_a(p_74875_1_, p_74875_3_, 3, 4, 4, 4, 4, 8, Blocks.field_150425_aM.func_176223_P(), Blocks.field_150425_aM.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 8, 4, 4, 9, 4, 8, Blocks.field_150425_aM.func_176223_P(), Blocks.field_150425_aM.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 3, 5, 4, 4, 5, 8, Blocks.field_150388_bm.func_176223_P(), Blocks.field_150388_bm.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 8, 5, 4, 9, 5, 8, Blocks.field_150388_bm.func_176223_P(), Blocks.field_150388_bm.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 2, 0, 8, 2, 12, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 4, 12, 2, 8, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 0, 0, 8, 1, 3, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 0, 9, 8, 1, 12, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 0, 4, 3, 1, 8, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 9, 0, 4, 12, 1, 8, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);

         for(int l1 = 4; l1 <= 8; ++l1) {
            for(int i1 = 0; i1 <= 2; ++i1) {
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), l1, -1, i1, p_74875_3_);
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), l1, -1, 12 - i1, p_74875_3_);
            }
         }

         for(int i2 = 0; i2 <= 2; ++i2) {
            for(int j2 = 4; j2 <= 8; ++j2) {
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), i2, -1, j2, p_74875_3_);
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), 12 - i2, -1, j2, p_74875_3_);
            }
         }

         return true;
      }
   }

   abstract static class Piece extends StructureComponent {
      public Piece() {
      }

      protected Piece(int p_i2054_1_) {
         super(p_i2054_1_);
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
      }

      private int func_74960_a(List<StructureNetherBridgePieces.PieceWeight> p_74960_1_) {
         boolean flag = false;
         int i = 0;

         for(StructureNetherBridgePieces.PieceWeight structurenetherbridgepieces$pieceweight : p_74960_1_) {
            if (structurenetherbridgepieces$pieceweight.field_78824_d > 0 && structurenetherbridgepieces$pieceweight.field_78827_c < structurenetherbridgepieces$pieceweight.field_78824_d) {
               flag = true;
            }

            i += structurenetherbridgepieces$pieceweight.field_78826_b;
         }

         return flag ? i : -1;
      }

      private StructureNetherBridgePieces.Piece func_175871_a(StructureNetherBridgePieces.Start p_175871_1_, List<StructureNetherBridgePieces.PieceWeight> p_175871_2_, List<StructureComponent> p_175871_3_, Random p_175871_4_, int p_175871_5_, int p_175871_6_, int p_175871_7_, EnumFacing p_175871_8_, int p_175871_9_) {
         int i = this.func_74960_a(p_175871_2_);
         boolean flag = i > 0 && p_175871_9_ <= 30;
         int j = 0;

         while(j < 5 && flag) {
            ++j;
            int k = p_175871_4_.nextInt(i);

            for(StructureNetherBridgePieces.PieceWeight structurenetherbridgepieces$pieceweight : p_175871_2_) {
               k -= structurenetherbridgepieces$pieceweight.field_78826_b;
               if (k < 0) {
                  if (!structurenetherbridgepieces$pieceweight.func_78822_a(p_175871_9_) || structurenetherbridgepieces$pieceweight == p_175871_1_.field_74970_a && !structurenetherbridgepieces$pieceweight.field_78825_e) {
                     break;
                  }

                  StructureNetherBridgePieces.Piece structurenetherbridgepieces$piece = StructureNetherBridgePieces.func_175887_b(structurenetherbridgepieces$pieceweight, p_175871_3_, p_175871_4_, p_175871_5_, p_175871_6_, p_175871_7_, p_175871_8_, p_175871_9_);
                  if (structurenetherbridgepieces$piece != null) {
                     ++structurenetherbridgepieces$pieceweight.field_78827_c;
                     p_175871_1_.field_74970_a = structurenetherbridgepieces$pieceweight;
                     if (!structurenetherbridgepieces$pieceweight.func_78823_a()) {
                        p_175871_2_.remove(structurenetherbridgepieces$pieceweight);
                     }

                     return structurenetherbridgepieces$piece;
                  }
               }
            }
         }

         return StructureNetherBridgePieces.End.func_175884_a(p_175871_3_, p_175871_4_, p_175871_5_, p_175871_6_, p_175871_7_, p_175871_8_, p_175871_9_);
      }

      private StructureComponent func_175870_a(StructureNetherBridgePieces.Start p_175870_1_, List<StructureComponent> p_175870_2_, Random p_175870_3_, int p_175870_4_, int p_175870_5_, int p_175870_6_, @Nullable EnumFacing p_175870_7_, int p_175870_8_, boolean p_175870_9_) {
         if (Math.abs(p_175870_4_ - p_175870_1_.func_74874_b().field_78897_a) <= 112 && Math.abs(p_175870_6_ - p_175870_1_.func_74874_b().field_78896_c) <= 112) {
            List<StructureNetherBridgePieces.PieceWeight> list = p_175870_1_.field_74968_b;
            if (p_175870_9_) {
               list = p_175870_1_.field_74969_c;
            }

            StructureComponent structurecomponent = this.func_175871_a(p_175870_1_, list, p_175870_2_, p_175870_3_, p_175870_4_, p_175870_5_, p_175870_6_, p_175870_7_, p_175870_8_ + 1);
            if (structurecomponent != null) {
               p_175870_2_.add(structurecomponent);
               p_175870_1_.field_74967_d.add(structurecomponent);
            }

            return structurecomponent;
         } else {
            return StructureNetherBridgePieces.End.func_175884_a(p_175870_2_, p_175870_3_, p_175870_4_, p_175870_5_, p_175870_6_, p_175870_7_, p_175870_8_);
         }
      }

      @Nullable
      protected StructureComponent func_74963_a(StructureNetherBridgePieces.Start p_74963_1_, List<StructureComponent> p_74963_2_, Random p_74963_3_, int p_74963_4_, int p_74963_5_, boolean p_74963_6_) {
         EnumFacing enumfacing = this.func_186165_e();
         if (enumfacing != null) {
            switch(enumfacing) {
            case NORTH:
               return this.func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, this.field_74887_e.field_78897_a + p_74963_4_, this.field_74887_e.field_78895_b + p_74963_5_, this.field_74887_e.field_78896_c - 1, enumfacing, this.func_74877_c(), p_74963_6_);
            case SOUTH:
               return this.func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, this.field_74887_e.field_78897_a + p_74963_4_, this.field_74887_e.field_78895_b + p_74963_5_, this.field_74887_e.field_78892_f + 1, enumfacing, this.func_74877_c(), p_74963_6_);
            case WEST:
               return this.func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b + p_74963_5_, this.field_74887_e.field_78896_c + p_74963_4_, enumfacing, this.func_74877_c(), p_74963_6_);
            case EAST:
               return this.func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b + p_74963_5_, this.field_74887_e.field_78896_c + p_74963_4_, enumfacing, this.func_74877_c(), p_74963_6_);
            }
         }

         return null;
      }

      @Nullable
      protected StructureComponent func_74961_b(StructureNetherBridgePieces.Start p_74961_1_, List<StructureComponent> p_74961_2_, Random p_74961_3_, int p_74961_4_, int p_74961_5_, boolean p_74961_6_) {
         EnumFacing enumfacing = this.func_186165_e();
         if (enumfacing != null) {
            switch(enumfacing) {
            case NORTH:
               return this.func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b + p_74961_4_, this.field_74887_e.field_78896_c + p_74961_5_, EnumFacing.WEST, this.func_74877_c(), p_74961_6_);
            case SOUTH:
               return this.func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b + p_74961_4_, this.field_74887_e.field_78896_c + p_74961_5_, EnumFacing.WEST, this.func_74877_c(), p_74961_6_);
            case WEST:
               return this.func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, this.field_74887_e.field_78897_a + p_74961_5_, this.field_74887_e.field_78895_b + p_74961_4_, this.field_74887_e.field_78896_c - 1, EnumFacing.NORTH, this.func_74877_c(), p_74961_6_);
            case EAST:
               return this.func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, this.field_74887_e.field_78897_a + p_74961_5_, this.field_74887_e.field_78895_b + p_74961_4_, this.field_74887_e.field_78896_c - 1, EnumFacing.NORTH, this.func_74877_c(), p_74961_6_);
            }
         }

         return null;
      }

      @Nullable
      protected StructureComponent func_74965_c(StructureNetherBridgePieces.Start p_74965_1_, List<StructureComponent> p_74965_2_, Random p_74965_3_, int p_74965_4_, int p_74965_5_, boolean p_74965_6_) {
         EnumFacing enumfacing = this.func_186165_e();
         if (enumfacing != null) {
            switch(enumfacing) {
            case NORTH:
               return this.func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b + p_74965_4_, this.field_74887_e.field_78896_c + p_74965_5_, EnumFacing.EAST, this.func_74877_c(), p_74965_6_);
            case SOUTH:
               return this.func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b + p_74965_4_, this.field_74887_e.field_78896_c + p_74965_5_, EnumFacing.EAST, this.func_74877_c(), p_74965_6_);
            case WEST:
               return this.func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, this.field_74887_e.field_78897_a + p_74965_5_, this.field_74887_e.field_78895_b + p_74965_4_, this.field_74887_e.field_78892_f + 1, EnumFacing.SOUTH, this.func_74877_c(), p_74965_6_);
            case EAST:
               return this.func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, this.field_74887_e.field_78897_a + p_74965_5_, this.field_74887_e.field_78895_b + p_74965_4_, this.field_74887_e.field_78892_f + 1, EnumFacing.SOUTH, this.func_74877_c(), p_74965_6_);
            }
         }

         return null;
      }

      protected static boolean func_74964_a(StructureBoundingBox p_74964_0_) {
         return p_74964_0_ != null && p_74964_0_.field_78895_b > 10;
      }
   }

   static class PieceWeight {
      public Class<? extends StructureNetherBridgePieces.Piece> field_78828_a;
      public final int field_78826_b;
      public int field_78827_c;
      public int field_78824_d;
      public boolean field_78825_e;

      public PieceWeight(Class<? extends StructureNetherBridgePieces.Piece> p_i2055_1_, int p_i2055_2_, int p_i2055_3_, boolean p_i2055_4_) {
         this.field_78828_a = p_i2055_1_;
         this.field_78826_b = p_i2055_2_;
         this.field_78824_d = p_i2055_3_;
         this.field_78825_e = p_i2055_4_;
      }

      public PieceWeight(Class<? extends StructureNetherBridgePieces.Piece> p_i2056_1_, int p_i2056_2_, int p_i2056_3_) {
         this(p_i2056_1_, p_i2056_2_, p_i2056_3_, false);
      }

      public boolean func_78822_a(int p_78822_1_) {
         return this.field_78824_d == 0 || this.field_78827_c < this.field_78824_d;
      }

      public boolean func_78823_a() {
         return this.field_78824_d == 0 || this.field_78827_c < this.field_78824_d;
      }
   }

   public static class Stairs extends StructureNetherBridgePieces.Piece {
      public Stairs() {
      }

      public Stairs(int p_i45609_1_, Random p_i45609_2_, StructureBoundingBox p_i45609_3_, EnumFacing p_i45609_4_) {
         super(p_i45609_1_);
         this.func_186164_a(p_i45609_4_);
         this.field_74887_e = p_i45609_3_;
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         this.func_74965_c((StructureNetherBridgePieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, 6, 2, false);
      }

      public static StructureNetherBridgePieces.Stairs func_175872_a(List<StructureComponent> p_175872_0_, Random p_175872_1_, int p_175872_2_, int p_175872_3_, int p_175872_4_, int p_175872_5_, EnumFacing p_175872_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175872_2_, p_175872_3_, p_175872_4_, -2, 0, 0, 7, 11, 7, p_175872_6_);
         return func_74964_a(structureboundingbox) && StructureComponent.func_74883_a(p_175872_0_, structureboundingbox) == null ? new StructureNetherBridgePieces.Stairs(p_175872_5_, p_175872_1_, structureboundingbox, p_175872_6_) : null;
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 0, 0, 6, 1, 6, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 0, 6, 10, 6, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 0, 1, 8, 0, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 5, 2, 0, 6, 8, 0, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 1, 0, 8, 6, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 6, 2, 1, 6, 8, 6, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 2, 6, 5, 8, 6, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 3, 2, 0, 5, 4, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 6, 3, 2, 6, 5, 2, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 6, 3, 4, 6, 5, 4, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175811_a(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), 5, 2, 5, p_74875_3_);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 2, 5, 4, 3, 5, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 3, 2, 5, 3, 4, 5, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 2, 2, 5, 2, 5, 5, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 2, 5, 1, 6, 5, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 7, 1, 5, 7, 4, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 6, 8, 2, 6, 8, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 2, 6, 0, 4, 8, 0, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 2, 5, 0, 4, 5, 0, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);

         for(int i = 0; i <= 6; ++i) {
            for(int j = 0; j <= 6; ++j) {
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), i, -1, j, p_74875_3_);
            }
         }

         return true;
      }
   }

   public static class Start extends StructureNetherBridgePieces.Crossing3 {
      public StructureNetherBridgePieces.PieceWeight field_74970_a;
      public List<StructureNetherBridgePieces.PieceWeight> field_74968_b;
      public List<StructureNetherBridgePieces.PieceWeight> field_74969_c;
      public List<StructureComponent> field_74967_d = Lists.<StructureComponent>newArrayList();

      public Start() {
      }

      public Start(Random p_i2059_1_, int p_i2059_2_, int p_i2059_3_) {
         super(p_i2059_1_, p_i2059_2_, p_i2059_3_);
         this.field_74968_b = Lists.<StructureNetherBridgePieces.PieceWeight>newArrayList();

         for(StructureNetherBridgePieces.PieceWeight structurenetherbridgepieces$pieceweight : StructureNetherBridgePieces.field_78742_a) {
            structurenetherbridgepieces$pieceweight.field_78827_c = 0;
            this.field_74968_b.add(structurenetherbridgepieces$pieceweight);
         }

         this.field_74969_c = Lists.<StructureNetherBridgePieces.PieceWeight>newArrayList();

         for(StructureNetherBridgePieces.PieceWeight structurenetherbridgepieces$pieceweight1 : StructureNetherBridgePieces.field_78741_b) {
            structurenetherbridgepieces$pieceweight1.field_78827_c = 0;
            this.field_74969_c.add(structurenetherbridgepieces$pieceweight1);
         }

      }
   }

   public static class Straight extends StructureNetherBridgePieces.Piece {
      public Straight() {
      }

      public Straight(int p_i45620_1_, Random p_i45620_2_, StructureBoundingBox p_i45620_3_, EnumFacing p_i45620_4_) {
         super(p_i45620_1_);
         this.func_186164_a(p_i45620_4_);
         this.field_74887_e = p_i45620_3_;
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         this.func_74963_a((StructureNetherBridgePieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, 1, 3, false);
      }

      public static StructureNetherBridgePieces.Straight func_175882_a(List<StructureComponent> p_175882_0_, Random p_175882_1_, int p_175882_2_, int p_175882_3_, int p_175882_4_, EnumFacing p_175882_5_, int p_175882_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175882_2_, p_175882_3_, p_175882_4_, -1, -3, 0, 5, 10, 19, p_175882_5_);
         return func_74964_a(structureboundingbox) && StructureComponent.func_74883_a(p_175882_0_, structureboundingbox) == null ? new StructureNetherBridgePieces.Straight(p_175882_6_, p_175882_1_, structureboundingbox, p_175882_5_) : null;
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 3, 0, 4, 4, 18, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 5, 0, 3, 7, 18, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 5, 0, 0, 5, 18, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 5, 0, 4, 5, 18, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 0, 4, 2, 5, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 13, 4, 2, 18, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 1, 3, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 0, 15, 4, 1, 18, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);

         for(int i = 0; i <= 4; ++i) {
            for(int j = 0; j <= 2; ++j) {
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), i, -1, j, p_74875_3_);
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), i, -1, 18 - j, p_74875_3_);
            }
         }

         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 1, 1, 0, 4, 1, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 3, 4, 0, 4, 4, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 3, 14, 0, 4, 14, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 1, 17, 0, 4, 17, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 1, 1, 4, 4, 1, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 3, 4, 4, 4, 4, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 3, 14, 4, 4, 14, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 1, 17, 4, 4, 17, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         return true;
      }
   }

   public static class Throne extends StructureNetherBridgePieces.Piece {
      private boolean field_74976_a;

      public Throne() {
      }

      public Throne(int p_i45611_1_, Random p_i45611_2_, StructureBoundingBox p_i45611_3_, EnumFacing p_i45611_4_) {
         super(p_i45611_1_);
         this.func_186164_a(p_i45611_4_);
         this.field_74887_e = p_i45611_3_;
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         super.func_143011_b(p_143011_1_, p_143011_2_);
         this.field_74976_a = p_143011_1_.func_74767_n("Mob");
      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         super.func_143012_a(p_143012_1_);
         p_143012_1_.func_74757_a("Mob", this.field_74976_a);
      }

      public static StructureNetherBridgePieces.Throne func_175874_a(List<StructureComponent> p_175874_0_, Random p_175874_1_, int p_175874_2_, int p_175874_3_, int p_175874_4_, int p_175874_5_, EnumFacing p_175874_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175874_2_, p_175874_3_, p_175874_4_, -2, 0, 0, 7, 8, 9, p_175874_6_);
         return func_74964_a(structureboundingbox) && StructureComponent.func_74883_a(p_175874_0_, structureboundingbox) == null ? new StructureNetherBridgePieces.Throne(p_175874_5_, p_175874_1_, structureboundingbox, p_175874_6_) : null;
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 2, 0, 6, 7, 7, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 0, 0, 5, 1, 7, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 2, 1, 5, 2, 7, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 3, 2, 5, 3, 7, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 4, 3, 5, 4, 7, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 2, 0, 1, 4, 2, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 5, 2, 0, 5, 4, 2, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 5, 2, 1, 5, 3, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 5, 5, 2, 5, 5, 3, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 5, 3, 0, 5, 8, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 6, 5, 3, 6, 5, 8, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 5, 8, 5, 5, 8, Blocks.field_150385_bj.func_176223_P(), Blocks.field_150385_bj.func_176223_P(), false);
         this.func_175811_a(p_74875_1_, Blocks.field_150386_bk.func_176223_P(), 1, 6, 3, p_74875_3_);
         this.func_175811_a(p_74875_1_, Blocks.field_150386_bk.func_176223_P(), 5, 6, 3, p_74875_3_);
         this.func_175804_a(p_74875_1_, p_74875_3_, 0, 6, 3, 0, 6, 8, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 6, 6, 3, 6, 6, 8, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 6, 8, 5, 7, 8, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 2, 8, 8, 4, 8, 8, Blocks.field_150386_bk.func_176223_P(), Blocks.field_150386_bk.func_176223_P(), false);
         if (!this.field_74976_a) {
            BlockPos blockpos = new BlockPos(this.func_74865_a(3, 5), this.func_74862_a(5), this.func_74873_b(3, 5));
            if (p_74875_3_.func_175898_b(blockpos)) {
               this.field_74976_a = true;
               p_74875_1_.func_180501_a(blockpos, Blocks.field_150474_ac.func_176223_P(), 2);
               TileEntity tileentity = p_74875_1_.func_175625_s(blockpos);
               if (tileentity instanceof TileEntityMobSpawner) {
                  ((TileEntityMobSpawner)tileentity).func_145881_a().func_190894_a(EntityList.func_191306_a(EntityBlaze.class));
               }
            }
         }

         for(int i = 0; i <= 6; ++i) {
            for(int j = 0; j <= 6; ++j) {
               this.func_175808_b(p_74875_1_, Blocks.field_150385_bj.func_176223_P(), i, -1, j, p_74875_3_);
            }
         }

         return true;
      }
   }
}
