package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class StructureStrongholdPieces {
   private static final StructureStrongholdPieces.PieceWeight[] field_75205_b = new StructureStrongholdPieces.PieceWeight[]{new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Straight.class, 40, 0), new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Prison.class, 5, 5), new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.LeftTurn.class, 20, 0), new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.RightTurn.class, 20, 0), new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.RoomCrossing.class, 10, 6), new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.StairsStraight.class, 5, 5), new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Stairs.class, 5, 5), new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Crossing.class, 5, 4), new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.ChestCorridor.class, 5, 4), new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Library.class, 10, 2) {
      public boolean func_75189_a(int p_75189_1_) {
         return super.func_75189_a(p_75189_1_) && p_75189_1_ > 4;
      }
   }, new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.PortalRoom.class, 20, 1) {
      public boolean func_75189_a(int p_75189_1_) {
         return super.func_75189_a(p_75189_1_) && p_75189_1_ > 5;
      }
   }};
   private static List<StructureStrongholdPieces.PieceWeight> field_75206_c;
   private static Class<? extends StructureStrongholdPieces.Stronghold> field_75203_d;
   static int field_75207_a;
   private static final StructureStrongholdPieces.Stones field_75204_e = new StructureStrongholdPieces.Stones();

   public static void func_143046_a() {
      MapGenStructureIO.func_143031_a(StructureStrongholdPieces.ChestCorridor.class, "SHCC");
      MapGenStructureIO.func_143031_a(StructureStrongholdPieces.Corridor.class, "SHFC");
      MapGenStructureIO.func_143031_a(StructureStrongholdPieces.Crossing.class, "SH5C");
      MapGenStructureIO.func_143031_a(StructureStrongholdPieces.LeftTurn.class, "SHLT");
      MapGenStructureIO.func_143031_a(StructureStrongholdPieces.Library.class, "SHLi");
      MapGenStructureIO.func_143031_a(StructureStrongholdPieces.PortalRoom.class, "SHPR");
      MapGenStructureIO.func_143031_a(StructureStrongholdPieces.Prison.class, "SHPH");
      MapGenStructureIO.func_143031_a(StructureStrongholdPieces.RightTurn.class, "SHRT");
      MapGenStructureIO.func_143031_a(StructureStrongholdPieces.RoomCrossing.class, "SHRC");
      MapGenStructureIO.func_143031_a(StructureStrongholdPieces.Stairs.class, "SHSD");
      MapGenStructureIO.func_143031_a(StructureStrongholdPieces.Stairs2.class, "SHStart");
      MapGenStructureIO.func_143031_a(StructureStrongholdPieces.Straight.class, "SHS");
      MapGenStructureIO.func_143031_a(StructureStrongholdPieces.StairsStraight.class, "SHSSD");
   }

   public static void func_75198_a() {
      field_75206_c = Lists.<StructureStrongholdPieces.PieceWeight>newArrayList();

      for(StructureStrongholdPieces.PieceWeight structurestrongholdpieces$pieceweight : field_75205_b) {
         structurestrongholdpieces$pieceweight.field_75193_c = 0;
         field_75206_c.add(structurestrongholdpieces$pieceweight);
      }

      field_75203_d = null;
   }

   private static boolean func_75202_c() {
      boolean flag = false;
      field_75207_a = 0;

      for(StructureStrongholdPieces.PieceWeight structurestrongholdpieces$pieceweight : field_75206_c) {
         if (structurestrongholdpieces$pieceweight.field_75191_d > 0 && structurestrongholdpieces$pieceweight.field_75193_c < structurestrongholdpieces$pieceweight.field_75191_d) {
            flag = true;
         }

         field_75207_a += structurestrongholdpieces$pieceweight.field_75192_b;
      }

      return flag;
   }

   private static StructureStrongholdPieces.Stronghold func_175954_a(Class<? extends StructureStrongholdPieces.Stronghold> p_175954_0_, List<StructureComponent> p_175954_1_, Random p_175954_2_, int p_175954_3_, int p_175954_4_, int p_175954_5_, @Nullable EnumFacing p_175954_6_, int p_175954_7_) {
      StructureStrongholdPieces.Stronghold structurestrongholdpieces$stronghold = null;
      if (p_175954_0_ == StructureStrongholdPieces.Straight.class) {
         structurestrongholdpieces$stronghold = StructureStrongholdPieces.Straight.func_175862_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
      } else if (p_175954_0_ == StructureStrongholdPieces.Prison.class) {
         structurestrongholdpieces$stronghold = StructureStrongholdPieces.Prison.func_175860_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
      } else if (p_175954_0_ == StructureStrongholdPieces.LeftTurn.class) {
         structurestrongholdpieces$stronghold = StructureStrongholdPieces.LeftTurn.func_175867_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
      } else if (p_175954_0_ == StructureStrongholdPieces.RightTurn.class) {
         structurestrongholdpieces$stronghold = StructureStrongholdPieces.RightTurn.func_175867_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
      } else if (p_175954_0_ == StructureStrongholdPieces.RoomCrossing.class) {
         structurestrongholdpieces$stronghold = StructureStrongholdPieces.RoomCrossing.func_175859_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
      } else if (p_175954_0_ == StructureStrongholdPieces.StairsStraight.class) {
         structurestrongholdpieces$stronghold = StructureStrongholdPieces.StairsStraight.func_175861_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
      } else if (p_175954_0_ == StructureStrongholdPieces.Stairs.class) {
         structurestrongholdpieces$stronghold = StructureStrongholdPieces.Stairs.func_175863_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
      } else if (p_175954_0_ == StructureStrongholdPieces.Crossing.class) {
         structurestrongholdpieces$stronghold = StructureStrongholdPieces.Crossing.func_175866_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
      } else if (p_175954_0_ == StructureStrongholdPieces.ChestCorridor.class) {
         structurestrongholdpieces$stronghold = StructureStrongholdPieces.ChestCorridor.func_175868_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
      } else if (p_175954_0_ == StructureStrongholdPieces.Library.class) {
         structurestrongholdpieces$stronghold = StructureStrongholdPieces.Library.func_175864_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
      } else if (p_175954_0_ == StructureStrongholdPieces.PortalRoom.class) {
         structurestrongholdpieces$stronghold = StructureStrongholdPieces.PortalRoom.func_175865_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
      }

      return structurestrongholdpieces$stronghold;
   }

   private static StructureStrongholdPieces.Stronghold func_175955_b(StructureStrongholdPieces.Stairs2 p_175955_0_, List<StructureComponent> p_175955_1_, Random p_175955_2_, int p_175955_3_, int p_175955_4_, int p_175955_5_, EnumFacing p_175955_6_, int p_175955_7_) {
      if (!func_75202_c()) {
         return null;
      } else {
         if (field_75203_d != null) {
            StructureStrongholdPieces.Stronghold structurestrongholdpieces$stronghold = func_175954_a(field_75203_d, p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_, p_175955_7_);
            field_75203_d = null;
            if (structurestrongholdpieces$stronghold != null) {
               return structurestrongholdpieces$stronghold;
            }
         }

         int j = 0;

         while(j < 5) {
            ++j;
            int i = p_175955_2_.nextInt(field_75207_a);

            for(StructureStrongholdPieces.PieceWeight structurestrongholdpieces$pieceweight : field_75206_c) {
               i -= structurestrongholdpieces$pieceweight.field_75192_b;
               if (i < 0) {
                  if (!structurestrongholdpieces$pieceweight.func_75189_a(p_175955_7_) || structurestrongholdpieces$pieceweight == p_175955_0_.field_75027_a) {
                     break;
                  }

                  StructureStrongholdPieces.Stronghold structurestrongholdpieces$stronghold1 = func_175954_a(structurestrongholdpieces$pieceweight.field_75194_a, p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_, p_175955_7_);
                  if (structurestrongholdpieces$stronghold1 != null) {
                     ++structurestrongholdpieces$pieceweight.field_75193_c;
                     p_175955_0_.field_75027_a = structurestrongholdpieces$pieceweight;
                     if (!structurestrongholdpieces$pieceweight.func_75190_a()) {
                        field_75206_c.remove(structurestrongholdpieces$pieceweight);
                     }

                     return structurestrongholdpieces$stronghold1;
                  }
               }
            }
         }

         StructureBoundingBox structureboundingbox = StructureStrongholdPieces.Corridor.func_175869_a(p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_);
         if (structureboundingbox != null && structureboundingbox.field_78895_b > 1) {
            return new StructureStrongholdPieces.Corridor(p_175955_7_, p_175955_2_, structureboundingbox, p_175955_6_);
         } else {
            return null;
         }
      }
   }

   private static StructureComponent func_175953_c(StructureStrongholdPieces.Stairs2 p_175953_0_, List<StructureComponent> p_175953_1_, Random p_175953_2_, int p_175953_3_, int p_175953_4_, int p_175953_5_, @Nullable EnumFacing p_175953_6_, int p_175953_7_) {
      if (p_175953_7_ > 50) {
         return null;
      } else if (Math.abs(p_175953_3_ - p_175953_0_.func_74874_b().field_78897_a) <= 112 && Math.abs(p_175953_5_ - p_175953_0_.func_74874_b().field_78896_c) <= 112) {
         StructureComponent structurecomponent = func_175955_b(p_175953_0_, p_175953_1_, p_175953_2_, p_175953_3_, p_175953_4_, p_175953_5_, p_175953_6_, p_175953_7_ + 1);
         if (structurecomponent != null) {
            p_175953_1_.add(structurecomponent);
            p_175953_0_.field_75026_c.add(structurecomponent);
         }

         return structurecomponent;
      } else {
         return null;
      }
   }

   public static class ChestCorridor extends StructureStrongholdPieces.Stronghold {
      private boolean field_75002_c;

      public ChestCorridor() {
      }

      public ChestCorridor(int p_i45582_1_, Random p_i45582_2_, StructureBoundingBox p_i45582_3_, EnumFacing p_i45582_4_) {
         super(p_i45582_1_);
         this.func_186164_a(p_i45582_4_);
         this.field_143013_d = this.func_74988_a(p_i45582_2_);
         this.field_74887_e = p_i45582_3_;
      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         super.func_143012_a(p_143012_1_);
         p_143012_1_.func_74757_a("Chest", this.field_75002_c);
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         super.func_143011_b(p_143011_1_, p_143011_2_);
         this.field_75002_c = p_143011_1_.func_74767_n("Chest");
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         this.func_74986_a((StructureStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
      }

      public static StructureStrongholdPieces.ChestCorridor func_175868_a(List<StructureComponent> p_175868_0_, Random p_175868_1_, int p_175868_2_, int p_175868_3_, int p_175868_4_, EnumFacing p_175868_5_, int p_175868_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175868_2_, p_175868_3_, p_175868_4_, -1, -1, 0, 5, 5, 7, p_175868_5_);
         return func_74991_a(structureboundingbox) && StructureComponent.func_74883_a(p_175868_0_, structureboundingbox) == null ? new StructureStrongholdPieces.ChestCorridor(p_175868_6_, p_175868_1_, structureboundingbox, p_175868_5_) : null;
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         if (this.func_74860_a(p_74875_1_, p_74875_3_)) {
            return false;
         } else {
            this.func_74882_a(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 4, 6, true, p_74875_2_, StructureStrongholdPieces.field_75204_e);
            this.func_74990_a(p_74875_1_, p_74875_2_, p_74875_3_, this.field_143013_d, 1, 1, 0);
            this.func_74990_a(p_74875_1_, p_74875_2_, p_74875_3_, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
            this.func_175804_a(p_74875_1_, p_74875_3_, 3, 1, 2, 3, 1, 4, Blocks.field_150417_aV.func_176223_P(), Blocks.field_150417_aV.func_176223_P(), false);
            this.func_175811_a(p_74875_1_, Blocks.field_150333_U.func_176203_a(BlockStoneSlab.EnumType.SMOOTHBRICK.func_176624_a()), 3, 1, 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150333_U.func_176203_a(BlockStoneSlab.EnumType.SMOOTHBRICK.func_176624_a()), 3, 1, 5, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150333_U.func_176203_a(BlockStoneSlab.EnumType.SMOOTHBRICK.func_176624_a()), 3, 2, 2, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150333_U.func_176203_a(BlockStoneSlab.EnumType.SMOOTHBRICK.func_176624_a()), 3, 2, 4, p_74875_3_);

            for(int i = 2; i <= 4; ++i) {
               this.func_175811_a(p_74875_1_, Blocks.field_150333_U.func_176203_a(BlockStoneSlab.EnumType.SMOOTHBRICK.func_176624_a()), 2, 1, i, p_74875_3_);
            }

            if (!this.field_75002_c && p_74875_3_.func_175898_b(new BlockPos(this.func_74865_a(3, 3), this.func_74862_a(2), this.func_74873_b(3, 3)))) {
               this.field_75002_c = true;
               this.func_186167_a(p_74875_1_, p_74875_3_, p_74875_2_, 3, 2, 3, LootTableList.field_186428_j);
            }

            return true;
         }
      }
   }

   public static class Corridor extends StructureStrongholdPieces.Stronghold {
      private int field_74993_a;

      public Corridor() {
      }

      public Corridor(int p_i45581_1_, Random p_i45581_2_, StructureBoundingBox p_i45581_3_, EnumFacing p_i45581_4_) {
         super(p_i45581_1_);
         this.func_186164_a(p_i45581_4_);
         this.field_74887_e = p_i45581_3_;
         this.field_74993_a = p_i45581_4_ != EnumFacing.NORTH && p_i45581_4_ != EnumFacing.SOUTH ? p_i45581_3_.func_78883_b() : p_i45581_3_.func_78880_d();
      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         super.func_143012_a(p_143012_1_);
         p_143012_1_.func_74768_a("Steps", this.field_74993_a);
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         super.func_143011_b(p_143011_1_, p_143011_2_);
         this.field_74993_a = p_143011_1_.func_74762_e("Steps");
      }

      public static StructureBoundingBox func_175869_a(List<StructureComponent> p_175869_0_, Random p_175869_1_, int p_175869_2_, int p_175869_3_, int p_175869_4_, EnumFacing p_175869_5_) {
         int i = 3;
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, 4, p_175869_5_);
         StructureComponent structurecomponent = StructureComponent.func_74883_a(p_175869_0_, structureboundingbox);
         if (structurecomponent == null) {
            return null;
         } else {
            if (structurecomponent.func_74874_b().field_78895_b == structureboundingbox.field_78895_b) {
               for(int j = 3; j >= 1; --j) {
                  structureboundingbox = StructureBoundingBox.func_175897_a(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, j - 1, p_175869_5_);
                  if (!structurecomponent.func_74874_b().func_78884_a(structureboundingbox)) {
                     return StructureBoundingBox.func_175897_a(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, j, p_175869_5_);
                  }
               }
            }

            return null;
         }
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         if (this.func_74860_a(p_74875_1_, p_74875_3_)) {
            return false;
         } else {
            for(int i = 0; i < this.field_74993_a; ++i) {
               this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 0, 0, i, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 1, 0, i, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 2, 0, i, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 3, 0, i, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 4, 0, i, p_74875_3_);

               for(int j = 1; j <= 3; ++j) {
                  this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 0, j, i, p_74875_3_);
                  this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 1, j, i, p_74875_3_);
                  this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 2, j, i, p_74875_3_);
                  this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 3, j, i, p_74875_3_);
                  this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 4, j, i, p_74875_3_);
               }

               this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 0, 4, i, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 1, 4, i, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 2, 4, i, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 3, 4, i, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 4, 4, i, p_74875_3_);
            }

            return true;
         }
      }
   }

   public static class Crossing extends StructureStrongholdPieces.Stronghold {
      private boolean field_74996_b;
      private boolean field_74997_c;
      private boolean field_74995_d;
      private boolean field_74999_h;

      public Crossing() {
      }

      public Crossing(int p_i45580_1_, Random p_i45580_2_, StructureBoundingBox p_i45580_3_, EnumFacing p_i45580_4_) {
         super(p_i45580_1_);
         this.func_186164_a(p_i45580_4_);
         this.field_143013_d = this.func_74988_a(p_i45580_2_);
         this.field_74887_e = p_i45580_3_;
         this.field_74996_b = p_i45580_2_.nextBoolean();
         this.field_74997_c = p_i45580_2_.nextBoolean();
         this.field_74995_d = p_i45580_2_.nextBoolean();
         this.field_74999_h = p_i45580_2_.nextInt(3) > 0;
      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         super.func_143012_a(p_143012_1_);
         p_143012_1_.func_74757_a("leftLow", this.field_74996_b);
         p_143012_1_.func_74757_a("leftHigh", this.field_74997_c);
         p_143012_1_.func_74757_a("rightLow", this.field_74995_d);
         p_143012_1_.func_74757_a("rightHigh", this.field_74999_h);
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         super.func_143011_b(p_143011_1_, p_143011_2_);
         this.field_74996_b = p_143011_1_.func_74767_n("leftLow");
         this.field_74997_c = p_143011_1_.func_74767_n("leftHigh");
         this.field_74995_d = p_143011_1_.func_74767_n("rightLow");
         this.field_74999_h = p_143011_1_.func_74767_n("rightHigh");
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         int i = 3;
         int j = 5;
         EnumFacing enumfacing = this.func_186165_e();
         if (enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.NORTH) {
            i = 8 - i;
            j = 8 - j;
         }

         this.func_74986_a((StructureStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 5, 1);
         if (this.field_74996_b) {
            this.func_74989_b((StructureStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, i, 1);
         }

         if (this.field_74997_c) {
            this.func_74989_b((StructureStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, j, 7);
         }

         if (this.field_74995_d) {
            this.func_74987_c((StructureStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, i, 1);
         }

         if (this.field_74999_h) {
            this.func_74987_c((StructureStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, j, 7);
         }

      }

      public static StructureStrongholdPieces.Crossing func_175866_a(List<StructureComponent> p_175866_0_, Random p_175866_1_, int p_175866_2_, int p_175866_3_, int p_175866_4_, EnumFacing p_175866_5_, int p_175866_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175866_2_, p_175866_3_, p_175866_4_, -4, -3, 0, 10, 9, 11, p_175866_5_);
         return func_74991_a(structureboundingbox) && StructureComponent.func_74883_a(p_175866_0_, structureboundingbox) == null ? new StructureStrongholdPieces.Crossing(p_175866_6_, p_175866_1_, structureboundingbox, p_175866_5_) : null;
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         if (this.func_74860_a(p_74875_1_, p_74875_3_)) {
            return false;
         } else {
            this.func_74882_a(p_74875_1_, p_74875_3_, 0, 0, 0, 9, 8, 10, true, p_74875_2_, StructureStrongholdPieces.field_75204_e);
            this.func_74990_a(p_74875_1_, p_74875_2_, p_74875_3_, this.field_143013_d, 4, 3, 0);
            if (this.field_74996_b) {
               this.func_175804_a(p_74875_1_, p_74875_3_, 0, 3, 1, 0, 5, 3, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            }

            if (this.field_74995_d) {
               this.func_175804_a(p_74875_1_, p_74875_3_, 9, 3, 1, 9, 5, 3, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            }

            if (this.field_74997_c) {
               this.func_175804_a(p_74875_1_, p_74875_3_, 0, 5, 7, 0, 7, 9, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            }

            if (this.field_74999_h) {
               this.func_175804_a(p_74875_1_, p_74875_3_, 9, 5, 7, 9, 7, 9, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            }

            this.func_175804_a(p_74875_1_, p_74875_3_, 5, 1, 10, 7, 3, 10, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            this.func_74882_a(p_74875_1_, p_74875_3_, 1, 2, 1, 8, 2, 6, false, p_74875_2_, StructureStrongholdPieces.field_75204_e);
            this.func_74882_a(p_74875_1_, p_74875_3_, 4, 1, 5, 4, 4, 9, false, p_74875_2_, StructureStrongholdPieces.field_75204_e);
            this.func_74882_a(p_74875_1_, p_74875_3_, 8, 1, 5, 8, 4, 9, false, p_74875_2_, StructureStrongholdPieces.field_75204_e);
            this.func_74882_a(p_74875_1_, p_74875_3_, 1, 4, 7, 3, 4, 9, false, p_74875_2_, StructureStrongholdPieces.field_75204_e);
            this.func_74882_a(p_74875_1_, p_74875_3_, 1, 3, 5, 3, 3, 6, false, p_74875_2_, StructureStrongholdPieces.field_75204_e);
            this.func_175804_a(p_74875_1_, p_74875_3_, 1, 3, 4, 3, 3, 4, Blocks.field_150333_U.func_176223_P(), Blocks.field_150333_U.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 1, 4, 6, 3, 4, 6, Blocks.field_150333_U.func_176223_P(), Blocks.field_150333_U.func_176223_P(), false);
            this.func_74882_a(p_74875_1_, p_74875_3_, 5, 1, 7, 7, 1, 8, false, p_74875_2_, StructureStrongholdPieces.field_75204_e);
            this.func_175804_a(p_74875_1_, p_74875_3_, 5, 1, 9, 7, 1, 9, Blocks.field_150333_U.func_176223_P(), Blocks.field_150333_U.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 5, 2, 7, 7, 2, 7, Blocks.field_150333_U.func_176223_P(), Blocks.field_150333_U.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 4, 5, 7, 4, 5, 9, Blocks.field_150333_U.func_176223_P(), Blocks.field_150333_U.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 8, 5, 7, 8, 5, 9, Blocks.field_150333_U.func_176223_P(), Blocks.field_150333_U.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 5, 5, 7, 7, 5, 9, Blocks.field_150334_T.func_176223_P(), Blocks.field_150334_T.func_176223_P(), false);
            this.func_175811_a(p_74875_1_, Blocks.field_150478_aa.func_176223_P().func_177226_a(BlockTorch.field_176596_a, EnumFacing.SOUTH), 6, 5, 6, p_74875_3_);
            return true;
         }
      }
   }

   public static class LeftTurn extends StructureStrongholdPieces.Stronghold {
      public LeftTurn() {
      }

      public LeftTurn(int p_i45579_1_, Random p_i45579_2_, StructureBoundingBox p_i45579_3_, EnumFacing p_i45579_4_) {
         super(p_i45579_1_);
         this.func_186164_a(p_i45579_4_);
         this.field_143013_d = this.func_74988_a(p_i45579_2_);
         this.field_74887_e = p_i45579_3_;
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         EnumFacing enumfacing = this.func_186165_e();
         if (enumfacing != EnumFacing.NORTH && enumfacing != EnumFacing.EAST) {
            this.func_74987_c((StructureStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
         } else {
            this.func_74989_b((StructureStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
         }

      }

      public static StructureStrongholdPieces.LeftTurn func_175867_a(List<StructureComponent> p_175867_0_, Random p_175867_1_, int p_175867_2_, int p_175867_3_, int p_175867_4_, EnumFacing p_175867_5_, int p_175867_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175867_2_, p_175867_3_, p_175867_4_, -1, -1, 0, 5, 5, 5, p_175867_5_);
         return func_74991_a(structureboundingbox) && StructureComponent.func_74883_a(p_175867_0_, structureboundingbox) == null ? new StructureStrongholdPieces.LeftTurn(p_175867_6_, p_175867_1_, structureboundingbox, p_175867_5_) : null;
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         if (this.func_74860_a(p_74875_1_, p_74875_3_)) {
            return false;
         } else {
            this.func_74882_a(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 4, 4, true, p_74875_2_, StructureStrongholdPieces.field_75204_e);
            this.func_74990_a(p_74875_1_, p_74875_2_, p_74875_3_, this.field_143013_d, 1, 1, 0);
            EnumFacing enumfacing = this.func_186165_e();
            if (enumfacing != EnumFacing.NORTH && enumfacing != EnumFacing.EAST) {
               this.func_175804_a(p_74875_1_, p_74875_3_, 4, 1, 1, 4, 3, 3, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            } else {
               this.func_175804_a(p_74875_1_, p_74875_3_, 0, 1, 1, 0, 3, 3, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            }

            return true;
         }
      }
   }

   public static class Library extends StructureStrongholdPieces.Stronghold {
      private boolean field_75008_c;

      public Library() {
      }

      public Library(int p_i45578_1_, Random p_i45578_2_, StructureBoundingBox p_i45578_3_, EnumFacing p_i45578_4_) {
         super(p_i45578_1_);
         this.func_186164_a(p_i45578_4_);
         this.field_143013_d = this.func_74988_a(p_i45578_2_);
         this.field_74887_e = p_i45578_3_;
         this.field_75008_c = p_i45578_3_.func_78882_c() > 6;
      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         super.func_143012_a(p_143012_1_);
         p_143012_1_.func_74757_a("Tall", this.field_75008_c);
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         super.func_143011_b(p_143011_1_, p_143011_2_);
         this.field_75008_c = p_143011_1_.func_74767_n("Tall");
      }

      public static StructureStrongholdPieces.Library func_175864_a(List<StructureComponent> p_175864_0_, Random p_175864_1_, int p_175864_2_, int p_175864_3_, int p_175864_4_, EnumFacing p_175864_5_, int p_175864_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175864_2_, p_175864_3_, p_175864_4_, -4, -1, 0, 14, 11, 15, p_175864_5_);
         if (!func_74991_a(structureboundingbox) || StructureComponent.func_74883_a(p_175864_0_, structureboundingbox) != null) {
            structureboundingbox = StructureBoundingBox.func_175897_a(p_175864_2_, p_175864_3_, p_175864_4_, -4, -1, 0, 14, 6, 15, p_175864_5_);
            if (!func_74991_a(structureboundingbox) || StructureComponent.func_74883_a(p_175864_0_, structureboundingbox) != null) {
               return null;
            }
         }

         return new StructureStrongholdPieces.Library(p_175864_6_, p_175864_1_, structureboundingbox, p_175864_5_);
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         if (this.func_74860_a(p_74875_1_, p_74875_3_)) {
            return false;
         } else {
            int i = 11;
            if (!this.field_75008_c) {
               i = 6;
            }

            this.func_74882_a(p_74875_1_, p_74875_3_, 0, 0, 0, 13, i - 1, 14, true, p_74875_2_, StructureStrongholdPieces.field_75204_e);
            this.func_74990_a(p_74875_1_, p_74875_2_, p_74875_3_, this.field_143013_d, 4, 1, 0);
            this.func_189914_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.07F, 2, 1, 1, 11, 4, 13, Blocks.field_150321_G.func_176223_P(), Blocks.field_150321_G.func_176223_P(), false, 0);
            int j = 1;
            int k = 12;

            for(int l = 1; l <= 13; ++l) {
               if ((l - 1) % 4 == 0) {
                  this.func_175804_a(p_74875_1_, p_74875_3_, 1, 1, l, 1, 4, l, Blocks.field_150344_f.func_176223_P(), Blocks.field_150344_f.func_176223_P(), false);
                  this.func_175804_a(p_74875_1_, p_74875_3_, 12, 1, l, 12, 4, l, Blocks.field_150344_f.func_176223_P(), Blocks.field_150344_f.func_176223_P(), false);
                  this.func_175811_a(p_74875_1_, Blocks.field_150478_aa.func_176223_P().func_177226_a(BlockTorch.field_176596_a, EnumFacing.EAST), 2, 3, l, p_74875_3_);
                  this.func_175811_a(p_74875_1_, Blocks.field_150478_aa.func_176223_P().func_177226_a(BlockTorch.field_176596_a, EnumFacing.WEST), 11, 3, l, p_74875_3_);
                  if (this.field_75008_c) {
                     this.func_175804_a(p_74875_1_, p_74875_3_, 1, 6, l, 1, 9, l, Blocks.field_150344_f.func_176223_P(), Blocks.field_150344_f.func_176223_P(), false);
                     this.func_175804_a(p_74875_1_, p_74875_3_, 12, 6, l, 12, 9, l, Blocks.field_150344_f.func_176223_P(), Blocks.field_150344_f.func_176223_P(), false);
                  }
               } else {
                  this.func_175804_a(p_74875_1_, p_74875_3_, 1, 1, l, 1, 4, l, Blocks.field_150342_X.func_176223_P(), Blocks.field_150342_X.func_176223_P(), false);
                  this.func_175804_a(p_74875_1_, p_74875_3_, 12, 1, l, 12, 4, l, Blocks.field_150342_X.func_176223_P(), Blocks.field_150342_X.func_176223_P(), false);
                  if (this.field_75008_c) {
                     this.func_175804_a(p_74875_1_, p_74875_3_, 1, 6, l, 1, 9, l, Blocks.field_150342_X.func_176223_P(), Blocks.field_150342_X.func_176223_P(), false);
                     this.func_175804_a(p_74875_1_, p_74875_3_, 12, 6, l, 12, 9, l, Blocks.field_150342_X.func_176223_P(), Blocks.field_150342_X.func_176223_P(), false);
                  }
               }
            }

            for(int k1 = 3; k1 < 12; k1 += 2) {
               this.func_175804_a(p_74875_1_, p_74875_3_, 3, 1, k1, 4, 3, k1, Blocks.field_150342_X.func_176223_P(), Blocks.field_150342_X.func_176223_P(), false);
               this.func_175804_a(p_74875_1_, p_74875_3_, 6, 1, k1, 7, 3, k1, Blocks.field_150342_X.func_176223_P(), Blocks.field_150342_X.func_176223_P(), false);
               this.func_175804_a(p_74875_1_, p_74875_3_, 9, 1, k1, 10, 3, k1, Blocks.field_150342_X.func_176223_P(), Blocks.field_150342_X.func_176223_P(), false);
            }

            if (this.field_75008_c) {
               this.func_175804_a(p_74875_1_, p_74875_3_, 1, 5, 1, 3, 5, 13, Blocks.field_150344_f.func_176223_P(), Blocks.field_150344_f.func_176223_P(), false);
               this.func_175804_a(p_74875_1_, p_74875_3_, 10, 5, 1, 12, 5, 13, Blocks.field_150344_f.func_176223_P(), Blocks.field_150344_f.func_176223_P(), false);
               this.func_175804_a(p_74875_1_, p_74875_3_, 4, 5, 1, 9, 5, 2, Blocks.field_150344_f.func_176223_P(), Blocks.field_150344_f.func_176223_P(), false);
               this.func_175804_a(p_74875_1_, p_74875_3_, 4, 5, 12, 9, 5, 13, Blocks.field_150344_f.func_176223_P(), Blocks.field_150344_f.func_176223_P(), false);
               this.func_175811_a(p_74875_1_, Blocks.field_150344_f.func_176223_P(), 9, 5, 11, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150344_f.func_176223_P(), 8, 5, 11, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150344_f.func_176223_P(), 9, 5, 10, p_74875_3_);
               this.func_175804_a(p_74875_1_, p_74875_3_, 3, 6, 2, 3, 6, 12, Blocks.field_180407_aO.func_176223_P(), Blocks.field_180407_aO.func_176223_P(), false);
               this.func_175804_a(p_74875_1_, p_74875_3_, 10, 6, 2, 10, 6, 10, Blocks.field_180407_aO.func_176223_P(), Blocks.field_180407_aO.func_176223_P(), false);
               this.func_175804_a(p_74875_1_, p_74875_3_, 4, 6, 2, 9, 6, 2, Blocks.field_180407_aO.func_176223_P(), Blocks.field_180407_aO.func_176223_P(), false);
               this.func_175804_a(p_74875_1_, p_74875_3_, 4, 6, 12, 8, 6, 12, Blocks.field_180407_aO.func_176223_P(), Blocks.field_180407_aO.func_176223_P(), false);
               this.func_175811_a(p_74875_1_, Blocks.field_180407_aO.func_176223_P(), 9, 6, 11, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_180407_aO.func_176223_P(), 8, 6, 11, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_180407_aO.func_176223_P(), 9, 6, 10, p_74875_3_);
               IBlockState iblockstate1 = Blocks.field_150468_ap.func_176223_P().func_177226_a(BlockLadder.field_176382_a, EnumFacing.SOUTH);
               this.func_175811_a(p_74875_1_, iblockstate1, 10, 1, 13, p_74875_3_);
               this.func_175811_a(p_74875_1_, iblockstate1, 10, 2, 13, p_74875_3_);
               this.func_175811_a(p_74875_1_, iblockstate1, 10, 3, 13, p_74875_3_);
               this.func_175811_a(p_74875_1_, iblockstate1, 10, 4, 13, p_74875_3_);
               this.func_175811_a(p_74875_1_, iblockstate1, 10, 5, 13, p_74875_3_);
               this.func_175811_a(p_74875_1_, iblockstate1, 10, 6, 13, p_74875_3_);
               this.func_175811_a(p_74875_1_, iblockstate1, 10, 7, 13, p_74875_3_);
               int i1 = 7;
               int j1 = 7;
               this.func_175811_a(p_74875_1_, Blocks.field_180407_aO.func_176223_P(), 6, 9, 7, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_180407_aO.func_176223_P(), 7, 9, 7, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_180407_aO.func_176223_P(), 6, 8, 7, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_180407_aO.func_176223_P(), 7, 8, 7, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_180407_aO.func_176223_P(), 6, 7, 7, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_180407_aO.func_176223_P(), 7, 7, 7, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_180407_aO.func_176223_P(), 5, 7, 7, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_180407_aO.func_176223_P(), 8, 7, 7, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_180407_aO.func_176223_P(), 6, 7, 6, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_180407_aO.func_176223_P(), 6, 7, 8, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_180407_aO.func_176223_P(), 7, 7, 6, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_180407_aO.func_176223_P(), 7, 7, 8, p_74875_3_);
               IBlockState iblockstate = Blocks.field_150478_aa.func_176223_P().func_177226_a(BlockTorch.field_176596_a, EnumFacing.UP);
               this.func_175811_a(p_74875_1_, iblockstate, 5, 8, 7, p_74875_3_);
               this.func_175811_a(p_74875_1_, iblockstate, 8, 8, 7, p_74875_3_);
               this.func_175811_a(p_74875_1_, iblockstate, 6, 8, 6, p_74875_3_);
               this.func_175811_a(p_74875_1_, iblockstate, 6, 8, 8, p_74875_3_);
               this.func_175811_a(p_74875_1_, iblockstate, 7, 8, 6, p_74875_3_);
               this.func_175811_a(p_74875_1_, iblockstate, 7, 8, 8, p_74875_3_);
            }

            this.func_186167_a(p_74875_1_, p_74875_3_, p_74875_2_, 3, 3, 5, LootTableList.field_186426_h);
            if (this.field_75008_c) {
               this.func_175811_a(p_74875_1_, Blocks.field_150350_a.func_176223_P(), 12, 9, 1, p_74875_3_);
               this.func_186167_a(p_74875_1_, p_74875_3_, p_74875_2_, 12, 8, 1, LootTableList.field_186426_h);
            }

            return true;
         }
      }
   }

   static class PieceWeight {
      public Class<? extends StructureStrongholdPieces.Stronghold> field_75194_a;
      public final int field_75192_b;
      public int field_75193_c;
      public int field_75191_d;

      public PieceWeight(Class<? extends StructureStrongholdPieces.Stronghold> p_i2076_1_, int p_i2076_2_, int p_i2076_3_) {
         this.field_75194_a = p_i2076_1_;
         this.field_75192_b = p_i2076_2_;
         this.field_75191_d = p_i2076_3_;
      }

      public boolean func_75189_a(int p_75189_1_) {
         return this.field_75191_d == 0 || this.field_75193_c < this.field_75191_d;
      }

      public boolean func_75190_a() {
         return this.field_75191_d == 0 || this.field_75193_c < this.field_75191_d;
      }
   }

   public static class PortalRoom extends StructureStrongholdPieces.Stronghold {
      private boolean field_75005_a;

      public PortalRoom() {
      }

      public PortalRoom(int p_i45577_1_, Random p_i45577_2_, StructureBoundingBox p_i45577_3_, EnumFacing p_i45577_4_) {
         super(p_i45577_1_);
         this.func_186164_a(p_i45577_4_);
         this.field_74887_e = p_i45577_3_;
      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         super.func_143012_a(p_143012_1_);
         p_143012_1_.func_74757_a("Mob", this.field_75005_a);
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         super.func_143011_b(p_143011_1_, p_143011_2_);
         this.field_75005_a = p_143011_1_.func_74767_n("Mob");
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         if (p_74861_1_ != null) {
            ((StructureStrongholdPieces.Stairs2)p_74861_1_).field_75025_b = this;
         }

      }

      public static StructureStrongholdPieces.PortalRoom func_175865_a(List<StructureComponent> p_175865_0_, Random p_175865_1_, int p_175865_2_, int p_175865_3_, int p_175865_4_, EnumFacing p_175865_5_, int p_175865_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175865_2_, p_175865_3_, p_175865_4_, -4, -1, 0, 11, 8, 16, p_175865_5_);
         return func_74991_a(structureboundingbox) && StructureComponent.func_74883_a(p_175865_0_, structureboundingbox) == null ? new StructureStrongholdPieces.PortalRoom(p_175865_6_, p_175865_1_, structureboundingbox, p_175865_5_) : null;
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         this.func_74882_a(p_74875_1_, p_74875_3_, 0, 0, 0, 10, 7, 15, false, p_74875_2_, StructureStrongholdPieces.field_75204_e);
         this.func_74990_a(p_74875_1_, p_74875_2_, p_74875_3_, StructureStrongholdPieces.Stronghold.Door.GRATES, 4, 1, 0);
         int i = 6;
         this.func_74882_a(p_74875_1_, p_74875_3_, 1, i, 1, 1, i, 14, false, p_74875_2_, StructureStrongholdPieces.field_75204_e);
         this.func_74882_a(p_74875_1_, p_74875_3_, 9, i, 1, 9, i, 14, false, p_74875_2_, StructureStrongholdPieces.field_75204_e);
         this.func_74882_a(p_74875_1_, p_74875_3_, 2, i, 1, 8, i, 2, false, p_74875_2_, StructureStrongholdPieces.field_75204_e);
         this.func_74882_a(p_74875_1_, p_74875_3_, 2, i, 14, 8, i, 14, false, p_74875_2_, StructureStrongholdPieces.field_75204_e);
         this.func_74882_a(p_74875_1_, p_74875_3_, 1, 1, 1, 2, 1, 4, false, p_74875_2_, StructureStrongholdPieces.field_75204_e);
         this.func_74882_a(p_74875_1_, p_74875_3_, 8, 1, 1, 9, 1, 4, false, p_74875_2_, StructureStrongholdPieces.field_75204_e);
         this.func_175804_a(p_74875_1_, p_74875_3_, 1, 1, 1, 1, 1, 3, Blocks.field_150356_k.func_176223_P(), Blocks.field_150356_k.func_176223_P(), false);
         this.func_175804_a(p_74875_1_, p_74875_3_, 9, 1, 1, 9, 1, 3, Blocks.field_150356_k.func_176223_P(), Blocks.field_150356_k.func_176223_P(), false);
         this.func_74882_a(p_74875_1_, p_74875_3_, 3, 1, 8, 7, 1, 12, false, p_74875_2_, StructureStrongholdPieces.field_75204_e);
         this.func_175804_a(p_74875_1_, p_74875_3_, 4, 1, 9, 6, 1, 11, Blocks.field_150356_k.func_176223_P(), Blocks.field_150356_k.func_176223_P(), false);

         for(int j = 3; j < 14; j += 2) {
            this.func_175804_a(p_74875_1_, p_74875_3_, 0, 3, j, 0, 4, j, Blocks.field_150411_aY.func_176223_P(), Blocks.field_150411_aY.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 10, 3, j, 10, 4, j, Blocks.field_150411_aY.func_176223_P(), Blocks.field_150411_aY.func_176223_P(), false);
         }

         for(int i1 = 2; i1 < 9; i1 += 2) {
            this.func_175804_a(p_74875_1_, p_74875_3_, i1, 3, 15, i1, 4, 15, Blocks.field_150411_aY.func_176223_P(), Blocks.field_150411_aY.func_176223_P(), false);
         }

         IBlockState iblockstate3 = Blocks.field_150390_bg.func_176223_P().func_177226_a(BlockStairs.field_176309_a, EnumFacing.NORTH);
         this.func_74882_a(p_74875_1_, p_74875_3_, 4, 1, 5, 6, 1, 7, false, p_74875_2_, StructureStrongholdPieces.field_75204_e);
         this.func_74882_a(p_74875_1_, p_74875_3_, 4, 2, 6, 6, 2, 7, false, p_74875_2_, StructureStrongholdPieces.field_75204_e);
         this.func_74882_a(p_74875_1_, p_74875_3_, 4, 3, 7, 6, 3, 7, false, p_74875_2_, StructureStrongholdPieces.field_75204_e);

         for(int k = 4; k <= 6; ++k) {
            this.func_175811_a(p_74875_1_, iblockstate3, k, 1, 4, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate3, k, 2, 5, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate3, k, 3, 6, p_74875_3_);
         }

         IBlockState iblockstate4 = Blocks.field_150378_br.func_176223_P().func_177226_a(BlockEndPortalFrame.field_176508_a, EnumFacing.NORTH);
         IBlockState iblockstate = Blocks.field_150378_br.func_176223_P().func_177226_a(BlockEndPortalFrame.field_176508_a, EnumFacing.SOUTH);
         IBlockState iblockstate1 = Blocks.field_150378_br.func_176223_P().func_177226_a(BlockEndPortalFrame.field_176508_a, EnumFacing.EAST);
         IBlockState iblockstate2 = Blocks.field_150378_br.func_176223_P().func_177226_a(BlockEndPortalFrame.field_176508_a, EnumFacing.WEST);
         boolean flag = true;
         boolean[] aboolean = new boolean[12];

         for(int l = 0; l < aboolean.length; ++l) {
            aboolean[l] = p_74875_2_.nextFloat() > 0.9F;
            flag &= aboolean[l];
         }

         this.func_175811_a(p_74875_1_, iblockstate4.func_177226_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(aboolean[0])), 4, 3, 8, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate4.func_177226_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(aboolean[1])), 5, 3, 8, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate4.func_177226_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(aboolean[2])), 6, 3, 8, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate.func_177226_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(aboolean[3])), 4, 3, 12, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate.func_177226_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(aboolean[4])), 5, 3, 12, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate.func_177226_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(aboolean[5])), 6, 3, 12, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate1.func_177226_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(aboolean[6])), 3, 3, 9, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate1.func_177226_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(aboolean[7])), 3, 3, 10, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate1.func_177226_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(aboolean[8])), 3, 3, 11, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate2.func_177226_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(aboolean[9])), 7, 3, 9, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate2.func_177226_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(aboolean[10])), 7, 3, 10, p_74875_3_);
         this.func_175811_a(p_74875_1_, iblockstate2.func_177226_a(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(aboolean[11])), 7, 3, 11, p_74875_3_);
         if (flag) {
            IBlockState iblockstate5 = Blocks.field_150384_bq.func_176223_P();
            this.func_175811_a(p_74875_1_, iblockstate5, 4, 3, 9, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate5, 5, 3, 9, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate5, 6, 3, 9, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate5, 4, 3, 10, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate5, 5, 3, 10, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate5, 6, 3, 10, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate5, 4, 3, 11, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate5, 5, 3, 11, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate5, 6, 3, 11, p_74875_3_);
         }

         if (!this.field_75005_a) {
            i = this.func_74862_a(3);
            BlockPos blockpos = new BlockPos(this.func_74865_a(5, 6), i, this.func_74873_b(5, 6));
            if (p_74875_3_.func_175898_b(blockpos)) {
               this.field_75005_a = true;
               p_74875_1_.func_180501_a(blockpos, Blocks.field_150474_ac.func_176223_P(), 2);
               TileEntity tileentity = p_74875_1_.func_175625_s(blockpos);
               if (tileentity instanceof TileEntityMobSpawner) {
                  ((TileEntityMobSpawner)tileentity).func_145881_a().func_190894_a(EntityList.func_191306_a(EntitySilverfish.class));
               }
            }
         }

         return true;
      }
   }

   public static class Prison extends StructureStrongholdPieces.Stronghold {
      public Prison() {
      }

      public Prison(int p_i45576_1_, Random p_i45576_2_, StructureBoundingBox p_i45576_3_, EnumFacing p_i45576_4_) {
         super(p_i45576_1_);
         this.func_186164_a(p_i45576_4_);
         this.field_143013_d = this.func_74988_a(p_i45576_2_);
         this.field_74887_e = p_i45576_3_;
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         this.func_74986_a((StructureStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
      }

      public static StructureStrongholdPieces.Prison func_175860_a(List<StructureComponent> p_175860_0_, Random p_175860_1_, int p_175860_2_, int p_175860_3_, int p_175860_4_, EnumFacing p_175860_5_, int p_175860_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175860_2_, p_175860_3_, p_175860_4_, -1, -1, 0, 9, 5, 11, p_175860_5_);
         return func_74991_a(structureboundingbox) && StructureComponent.func_74883_a(p_175860_0_, structureboundingbox) == null ? new StructureStrongholdPieces.Prison(p_175860_6_, p_175860_1_, structureboundingbox, p_175860_5_) : null;
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         if (this.func_74860_a(p_74875_1_, p_74875_3_)) {
            return false;
         } else {
            this.func_74882_a(p_74875_1_, p_74875_3_, 0, 0, 0, 8, 4, 10, true, p_74875_2_, StructureStrongholdPieces.field_75204_e);
            this.func_74990_a(p_74875_1_, p_74875_2_, p_74875_3_, this.field_143013_d, 1, 1, 0);
            this.func_175804_a(p_74875_1_, p_74875_3_, 1, 1, 10, 3, 3, 10, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            this.func_74882_a(p_74875_1_, p_74875_3_, 4, 1, 1, 4, 3, 1, false, p_74875_2_, StructureStrongholdPieces.field_75204_e);
            this.func_74882_a(p_74875_1_, p_74875_3_, 4, 1, 3, 4, 3, 3, false, p_74875_2_, StructureStrongholdPieces.field_75204_e);
            this.func_74882_a(p_74875_1_, p_74875_3_, 4, 1, 7, 4, 3, 7, false, p_74875_2_, StructureStrongholdPieces.field_75204_e);
            this.func_74882_a(p_74875_1_, p_74875_3_, 4, 1, 9, 4, 3, 9, false, p_74875_2_, StructureStrongholdPieces.field_75204_e);
            this.func_175804_a(p_74875_1_, p_74875_3_, 4, 1, 4, 4, 3, 6, Blocks.field_150411_aY.func_176223_P(), Blocks.field_150411_aY.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 5, 1, 5, 7, 3, 5, Blocks.field_150411_aY.func_176223_P(), Blocks.field_150411_aY.func_176223_P(), false);
            this.func_175811_a(p_74875_1_, Blocks.field_150411_aY.func_176223_P(), 4, 3, 2, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150411_aY.func_176223_P(), 4, 3, 8, p_74875_3_);
            IBlockState iblockstate = Blocks.field_150454_av.func_176223_P().func_177226_a(BlockDoor.field_176520_a, EnumFacing.WEST);
            IBlockState iblockstate1 = Blocks.field_150454_av.func_176223_P().func_177226_a(BlockDoor.field_176520_a, EnumFacing.WEST).func_177226_a(BlockDoor.field_176523_O, BlockDoor.EnumDoorHalf.UPPER);
            this.func_175811_a(p_74875_1_, iblockstate, 4, 1, 2, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate1, 4, 2, 2, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate, 4, 1, 8, p_74875_3_);
            this.func_175811_a(p_74875_1_, iblockstate1, 4, 2, 8, p_74875_3_);
            return true;
         }
      }
   }

   public static class RightTurn extends StructureStrongholdPieces.LeftTurn {
      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         EnumFacing enumfacing = this.func_186165_e();
         if (enumfacing != EnumFacing.NORTH && enumfacing != EnumFacing.EAST) {
            this.func_74989_b((StructureStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
         } else {
            this.func_74987_c((StructureStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
         }

      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         if (this.func_74860_a(p_74875_1_, p_74875_3_)) {
            return false;
         } else {
            this.func_74882_a(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 4, 4, true, p_74875_2_, StructureStrongholdPieces.field_75204_e);
            this.func_74990_a(p_74875_1_, p_74875_2_, p_74875_3_, this.field_143013_d, 1, 1, 0);
            EnumFacing enumfacing = this.func_186165_e();
            if (enumfacing != EnumFacing.NORTH && enumfacing != EnumFacing.EAST) {
               this.func_175804_a(p_74875_1_, p_74875_3_, 0, 1, 1, 0, 3, 3, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            } else {
               this.func_175804_a(p_74875_1_, p_74875_3_, 4, 1, 1, 4, 3, 3, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            }

            return true;
         }
      }
   }

   public static class RoomCrossing extends StructureStrongholdPieces.Stronghold {
      protected int field_75013_b;

      public RoomCrossing() {
      }

      public RoomCrossing(int p_i45575_1_, Random p_i45575_2_, StructureBoundingBox p_i45575_3_, EnumFacing p_i45575_4_) {
         super(p_i45575_1_);
         this.func_186164_a(p_i45575_4_);
         this.field_143013_d = this.func_74988_a(p_i45575_2_);
         this.field_74887_e = p_i45575_3_;
         this.field_75013_b = p_i45575_2_.nextInt(5);
      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         super.func_143012_a(p_143012_1_);
         p_143012_1_.func_74768_a("Type", this.field_75013_b);
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         super.func_143011_b(p_143011_1_, p_143011_2_);
         this.field_75013_b = p_143011_1_.func_74762_e("Type");
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         this.func_74986_a((StructureStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 4, 1);
         this.func_74989_b((StructureStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 4);
         this.func_74987_c((StructureStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 4);
      }

      public static StructureStrongholdPieces.RoomCrossing func_175859_a(List<StructureComponent> p_175859_0_, Random p_175859_1_, int p_175859_2_, int p_175859_3_, int p_175859_4_, EnumFacing p_175859_5_, int p_175859_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175859_2_, p_175859_3_, p_175859_4_, -4, -1, 0, 11, 7, 11, p_175859_5_);
         return func_74991_a(structureboundingbox) && StructureComponent.func_74883_a(p_175859_0_, structureboundingbox) == null ? new StructureStrongholdPieces.RoomCrossing(p_175859_6_, p_175859_1_, structureboundingbox, p_175859_5_) : null;
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         if (this.func_74860_a(p_74875_1_, p_74875_3_)) {
            return false;
         } else {
            this.func_74882_a(p_74875_1_, p_74875_3_, 0, 0, 0, 10, 6, 10, true, p_74875_2_, StructureStrongholdPieces.field_75204_e);
            this.func_74990_a(p_74875_1_, p_74875_2_, p_74875_3_, this.field_143013_d, 4, 1, 0);
            this.func_175804_a(p_74875_1_, p_74875_3_, 4, 1, 10, 6, 3, 10, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 0, 1, 4, 0, 3, 6, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 10, 1, 4, 10, 3, 6, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            switch(this.field_75013_b) {
            case 0:
               this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 5, 1, 5, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 5, 2, 5, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 5, 3, 5, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150478_aa.func_176223_P().func_177226_a(BlockTorch.field_176596_a, EnumFacing.WEST), 4, 3, 5, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150478_aa.func_176223_P().func_177226_a(BlockTorch.field_176596_a, EnumFacing.EAST), 6, 3, 5, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150478_aa.func_176223_P().func_177226_a(BlockTorch.field_176596_a, EnumFacing.SOUTH), 5, 3, 4, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150478_aa.func_176223_P().func_177226_a(BlockTorch.field_176596_a, EnumFacing.NORTH), 5, 3, 6, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150333_U.func_176223_P(), 4, 1, 4, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150333_U.func_176223_P(), 4, 1, 5, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150333_U.func_176223_P(), 4, 1, 6, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150333_U.func_176223_P(), 6, 1, 4, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150333_U.func_176223_P(), 6, 1, 5, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150333_U.func_176223_P(), 6, 1, 6, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150333_U.func_176223_P(), 5, 1, 4, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150333_U.func_176223_P(), 5, 1, 6, p_74875_3_);
               break;
            case 1:
               for(int i1 = 0; i1 < 5; ++i1) {
                  this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 3, 1, 3 + i1, p_74875_3_);
                  this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 7, 1, 3 + i1, p_74875_3_);
                  this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 3 + i1, 1, 3, p_74875_3_);
                  this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 3 + i1, 1, 7, p_74875_3_);
               }

               this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 5, 1, 5, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 5, 2, 5, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 5, 3, 5, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150358_i.func_176223_P(), 5, 4, 5, p_74875_3_);
               break;
            case 2:
               for(int i = 1; i <= 9; ++i) {
                  this.func_175811_a(p_74875_1_, Blocks.field_150347_e.func_176223_P(), 1, 3, i, p_74875_3_);
                  this.func_175811_a(p_74875_1_, Blocks.field_150347_e.func_176223_P(), 9, 3, i, p_74875_3_);
               }

               for(int j = 1; j <= 9; ++j) {
                  this.func_175811_a(p_74875_1_, Blocks.field_150347_e.func_176223_P(), j, 3, 1, p_74875_3_);
                  this.func_175811_a(p_74875_1_, Blocks.field_150347_e.func_176223_P(), j, 3, 9, p_74875_3_);
               }

               this.func_175811_a(p_74875_1_, Blocks.field_150347_e.func_176223_P(), 5, 1, 4, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150347_e.func_176223_P(), 5, 1, 6, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150347_e.func_176223_P(), 5, 3, 4, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150347_e.func_176223_P(), 5, 3, 6, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150347_e.func_176223_P(), 4, 1, 5, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150347_e.func_176223_P(), 6, 1, 5, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150347_e.func_176223_P(), 4, 3, 5, p_74875_3_);
               this.func_175811_a(p_74875_1_, Blocks.field_150347_e.func_176223_P(), 6, 3, 5, p_74875_3_);

               for(int k = 1; k <= 3; ++k) {
                  this.func_175811_a(p_74875_1_, Blocks.field_150347_e.func_176223_P(), 4, k, 4, p_74875_3_);
                  this.func_175811_a(p_74875_1_, Blocks.field_150347_e.func_176223_P(), 6, k, 4, p_74875_3_);
                  this.func_175811_a(p_74875_1_, Blocks.field_150347_e.func_176223_P(), 4, k, 6, p_74875_3_);
                  this.func_175811_a(p_74875_1_, Blocks.field_150347_e.func_176223_P(), 6, k, 6, p_74875_3_);
               }

               this.func_175811_a(p_74875_1_, Blocks.field_150478_aa.func_176223_P(), 5, 3, 5, p_74875_3_);

               for(int l = 2; l <= 8; ++l) {
                  this.func_175811_a(p_74875_1_, Blocks.field_150344_f.func_176223_P(), 2, 3, l, p_74875_3_);
                  this.func_175811_a(p_74875_1_, Blocks.field_150344_f.func_176223_P(), 3, 3, l, p_74875_3_);
                  if (l <= 3 || l >= 7) {
                     this.func_175811_a(p_74875_1_, Blocks.field_150344_f.func_176223_P(), 4, 3, l, p_74875_3_);
                     this.func_175811_a(p_74875_1_, Blocks.field_150344_f.func_176223_P(), 5, 3, l, p_74875_3_);
                     this.func_175811_a(p_74875_1_, Blocks.field_150344_f.func_176223_P(), 6, 3, l, p_74875_3_);
                  }

                  this.func_175811_a(p_74875_1_, Blocks.field_150344_f.func_176223_P(), 7, 3, l, p_74875_3_);
                  this.func_175811_a(p_74875_1_, Blocks.field_150344_f.func_176223_P(), 8, 3, l, p_74875_3_);
               }

               IBlockState iblockstate = Blocks.field_150468_ap.func_176223_P().func_177226_a(BlockLadder.field_176382_a, EnumFacing.WEST);
               this.func_175811_a(p_74875_1_, iblockstate, 9, 1, 3, p_74875_3_);
               this.func_175811_a(p_74875_1_, iblockstate, 9, 2, 3, p_74875_3_);
               this.func_175811_a(p_74875_1_, iblockstate, 9, 3, 3, p_74875_3_);
               this.func_186167_a(p_74875_1_, p_74875_3_, p_74875_2_, 3, 4, 8, LootTableList.field_186427_i);
            }

            return true;
         }
      }
   }

   public static class Stairs extends StructureStrongholdPieces.Stronghold {
      private boolean field_75024_a;

      public Stairs() {
      }

      public Stairs(int p_i2081_1_, Random p_i2081_2_, int p_i2081_3_, int p_i2081_4_) {
         super(p_i2081_1_);
         this.field_75024_a = true;
         this.func_186164_a(EnumFacing.Plane.HORIZONTAL.func_179518_a(p_i2081_2_));
         this.field_143013_d = StructureStrongholdPieces.Stronghold.Door.OPENING;
         if (this.func_186165_e().func_176740_k() == EnumFacing.Axis.Z) {
            this.field_74887_e = new StructureBoundingBox(p_i2081_3_, 64, p_i2081_4_, p_i2081_3_ + 5 - 1, 74, p_i2081_4_ + 5 - 1);
         } else {
            this.field_74887_e = new StructureBoundingBox(p_i2081_3_, 64, p_i2081_4_, p_i2081_3_ + 5 - 1, 74, p_i2081_4_ + 5 - 1);
         }

      }

      public Stairs(int p_i45574_1_, Random p_i45574_2_, StructureBoundingBox p_i45574_3_, EnumFacing p_i45574_4_) {
         super(p_i45574_1_);
         this.field_75024_a = false;
         this.func_186164_a(p_i45574_4_);
         this.field_143013_d = this.func_74988_a(p_i45574_2_);
         this.field_74887_e = p_i45574_3_;
      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         super.func_143012_a(p_143012_1_);
         p_143012_1_.func_74757_a("Source", this.field_75024_a);
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         super.func_143011_b(p_143011_1_, p_143011_2_);
         this.field_75024_a = p_143011_1_.func_74767_n("Source");
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         if (this.field_75024_a) {
            StructureStrongholdPieces.field_75203_d = StructureStrongholdPieces.Crossing.class;
         }

         this.func_74986_a((StructureStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
      }

      public static StructureStrongholdPieces.Stairs func_175863_a(List<StructureComponent> p_175863_0_, Random p_175863_1_, int p_175863_2_, int p_175863_3_, int p_175863_4_, EnumFacing p_175863_5_, int p_175863_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175863_2_, p_175863_3_, p_175863_4_, -1, -7, 0, 5, 11, 5, p_175863_5_);
         return func_74991_a(structureboundingbox) && StructureComponent.func_74883_a(p_175863_0_, structureboundingbox) == null ? new StructureStrongholdPieces.Stairs(p_175863_6_, p_175863_1_, structureboundingbox, p_175863_5_) : null;
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         if (this.func_74860_a(p_74875_1_, p_74875_3_)) {
            return false;
         } else {
            this.func_74882_a(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 10, 4, true, p_74875_2_, StructureStrongholdPieces.field_75204_e);
            this.func_74990_a(p_74875_1_, p_74875_2_, p_74875_3_, this.field_143013_d, 1, 7, 0);
            this.func_74990_a(p_74875_1_, p_74875_2_, p_74875_3_, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 4);
            this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 2, 6, 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 1, 5, 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150333_U.func_176203_a(BlockStoneSlab.EnumType.STONE.func_176624_a()), 1, 6, 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 1, 5, 2, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 1, 4, 3, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150333_U.func_176203_a(BlockStoneSlab.EnumType.STONE.func_176624_a()), 1, 5, 3, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 2, 4, 3, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 3, 3, 3, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150333_U.func_176203_a(BlockStoneSlab.EnumType.STONE.func_176624_a()), 3, 4, 3, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 3, 3, 2, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 3, 2, 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150333_U.func_176203_a(BlockStoneSlab.EnumType.STONE.func_176624_a()), 3, 3, 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 2, 2, 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 1, 1, 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150333_U.func_176203_a(BlockStoneSlab.EnumType.STONE.func_176624_a()), 1, 2, 1, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 1, 1, 2, p_74875_3_);
            this.func_175811_a(p_74875_1_, Blocks.field_150333_U.func_176203_a(BlockStoneSlab.EnumType.STONE.func_176624_a()), 1, 1, 3, p_74875_3_);
            return true;
         }
      }
   }

   public static class Stairs2 extends StructureStrongholdPieces.Stairs {
      public StructureStrongholdPieces.PieceWeight field_75027_a;
      public StructureStrongholdPieces.PortalRoom field_75025_b;
      public List<StructureComponent> field_75026_c = Lists.<StructureComponent>newArrayList();

      public Stairs2() {
      }

      public Stairs2(int p_i2083_1_, Random p_i2083_2_, int p_i2083_3_, int p_i2083_4_) {
         super(0, p_i2083_2_, p_i2083_3_, p_i2083_4_);
      }
   }

   public static class StairsStraight extends StructureStrongholdPieces.Stronghold {
      public StairsStraight() {
      }

      public StairsStraight(int p_i45572_1_, Random p_i45572_2_, StructureBoundingBox p_i45572_3_, EnumFacing p_i45572_4_) {
         super(p_i45572_1_);
         this.func_186164_a(p_i45572_4_);
         this.field_143013_d = this.func_74988_a(p_i45572_2_);
         this.field_74887_e = p_i45572_3_;
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         this.func_74986_a((StructureStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
      }

      public static StructureStrongholdPieces.StairsStraight func_175861_a(List<StructureComponent> p_175861_0_, Random p_175861_1_, int p_175861_2_, int p_175861_3_, int p_175861_4_, EnumFacing p_175861_5_, int p_175861_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175861_2_, p_175861_3_, p_175861_4_, -1, -7, 0, 5, 11, 8, p_175861_5_);
         return func_74991_a(structureboundingbox) && StructureComponent.func_74883_a(p_175861_0_, structureboundingbox) == null ? new StructureStrongholdPieces.StairsStraight(p_175861_6_, p_175861_1_, structureboundingbox, p_175861_5_) : null;
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         if (this.func_74860_a(p_74875_1_, p_74875_3_)) {
            return false;
         } else {
            this.func_74882_a(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 10, 7, true, p_74875_2_, StructureStrongholdPieces.field_75204_e);
            this.func_74990_a(p_74875_1_, p_74875_2_, p_74875_3_, this.field_143013_d, 1, 7, 0);
            this.func_74990_a(p_74875_1_, p_74875_2_, p_74875_3_, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 7);
            IBlockState iblockstate = Blocks.field_150446_ar.func_176223_P().func_177226_a(BlockStairs.field_176309_a, EnumFacing.SOUTH);

            for(int i = 0; i < 6; ++i) {
               this.func_175811_a(p_74875_1_, iblockstate, 1, 6 - i, 1 + i, p_74875_3_);
               this.func_175811_a(p_74875_1_, iblockstate, 2, 6 - i, 1 + i, p_74875_3_);
               this.func_175811_a(p_74875_1_, iblockstate, 3, 6 - i, 1 + i, p_74875_3_);
               if (i < 5) {
                  this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 1, 5 - i, 1 + i, p_74875_3_);
                  this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 2, 5 - i, 1 + i, p_74875_3_);
                  this.func_175811_a(p_74875_1_, Blocks.field_150417_aV.func_176223_P(), 3, 5 - i, 1 + i, p_74875_3_);
               }
            }

            return true;
         }
      }
   }

   static class Stones extends StructureComponent.BlockSelector {
      private Stones() {
      }

      public void func_75062_a(Random p_75062_1_, int p_75062_2_, int p_75062_3_, int p_75062_4_, boolean p_75062_5_) {
         if (p_75062_5_) {
            float f = p_75062_1_.nextFloat();
            if (f < 0.2F) {
               this.field_151562_a = Blocks.field_150417_aV.func_176203_a(BlockStoneBrick.field_176251_N);
            } else if (f < 0.5F) {
               this.field_151562_a = Blocks.field_150417_aV.func_176203_a(BlockStoneBrick.field_176250_M);
            } else if (f < 0.55F) {
               this.field_151562_a = Blocks.field_150418_aU.func_176203_a(BlockSilverfish.EnumType.STONEBRICK.func_176881_a());
            } else {
               this.field_151562_a = Blocks.field_150417_aV.func_176223_P();
            }
         } else {
            this.field_151562_a = Blocks.field_150350_a.func_176223_P();
         }

      }
   }

   public static class Straight extends StructureStrongholdPieces.Stronghold {
      private boolean field_75019_b;
      private boolean field_75020_c;

      public Straight() {
      }

      public Straight(int p_i45573_1_, Random p_i45573_2_, StructureBoundingBox p_i45573_3_, EnumFacing p_i45573_4_) {
         super(p_i45573_1_);
         this.func_186164_a(p_i45573_4_);
         this.field_143013_d = this.func_74988_a(p_i45573_2_);
         this.field_74887_e = p_i45573_3_;
         this.field_75019_b = p_i45573_2_.nextInt(2) == 0;
         this.field_75020_c = p_i45573_2_.nextInt(2) == 0;
      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         super.func_143012_a(p_143012_1_);
         p_143012_1_.func_74757_a("Left", this.field_75019_b);
         p_143012_1_.func_74757_a("Right", this.field_75020_c);
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         super.func_143011_b(p_143011_1_, p_143011_2_);
         this.field_75019_b = p_143011_1_.func_74767_n("Left");
         this.field_75020_c = p_143011_1_.func_74767_n("Right");
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         this.func_74986_a((StructureStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
         if (this.field_75019_b) {
            this.func_74989_b((StructureStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 2);
         }

         if (this.field_75020_c) {
            this.func_74987_c((StructureStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 2);
         }

      }

      public static StructureStrongholdPieces.Straight func_175862_a(List<StructureComponent> p_175862_0_, Random p_175862_1_, int p_175862_2_, int p_175862_3_, int p_175862_4_, EnumFacing p_175862_5_, int p_175862_6_) {
         StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(p_175862_2_, p_175862_3_, p_175862_4_, -1, -1, 0, 5, 5, 7, p_175862_5_);
         return func_74991_a(structureboundingbox) && StructureComponent.func_74883_a(p_175862_0_, structureboundingbox) == null ? new StructureStrongholdPieces.Straight(p_175862_6_, p_175862_1_, structureboundingbox, p_175862_5_) : null;
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         if (this.func_74860_a(p_74875_1_, p_74875_3_)) {
            return false;
         } else {
            this.func_74882_a(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 4, 6, true, p_74875_2_, StructureStrongholdPieces.field_75204_e);
            this.func_74990_a(p_74875_1_, p_74875_2_, p_74875_3_, this.field_143013_d, 1, 1, 0);
            this.func_74990_a(p_74875_1_, p_74875_2_, p_74875_3_, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
            IBlockState iblockstate = Blocks.field_150478_aa.func_176223_P().func_177226_a(BlockTorch.field_176596_a, EnumFacing.EAST);
            IBlockState iblockstate1 = Blocks.field_150478_aa.func_176223_P().func_177226_a(BlockTorch.field_176596_a, EnumFacing.WEST);
            this.func_175809_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.1F, 1, 2, 1, iblockstate);
            this.func_175809_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.1F, 3, 2, 1, iblockstate1);
            this.func_175809_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.1F, 1, 2, 5, iblockstate);
            this.func_175809_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.1F, 3, 2, 5, iblockstate1);
            if (this.field_75019_b) {
               this.func_175804_a(p_74875_1_, p_74875_3_, 0, 1, 2, 0, 3, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            }

            if (this.field_75020_c) {
               this.func_175804_a(p_74875_1_, p_74875_3_, 4, 1, 2, 4, 3, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            }

            return true;
         }
      }
   }

   abstract static class Stronghold extends StructureComponent {
      protected StructureStrongholdPieces.Stronghold.Door field_143013_d = StructureStrongholdPieces.Stronghold.Door.OPENING;

      public Stronghold() {
      }

      protected Stronghold(int p_i2087_1_) {
         super(p_i2087_1_);
      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         p_143012_1_.func_74778_a("EntryDoor", this.field_143013_d.name());
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         this.field_143013_d = StructureStrongholdPieces.Stronghold.Door.valueOf(p_143011_1_.func_74779_i("EntryDoor"));
      }

      protected void func_74990_a(World p_74990_1_, Random p_74990_2_, StructureBoundingBox p_74990_3_, StructureStrongholdPieces.Stronghold.Door p_74990_4_, int p_74990_5_, int p_74990_6_, int p_74990_7_) {
         switch(p_74990_4_) {
         case OPENING:
            this.func_175804_a(p_74990_1_, p_74990_3_, p_74990_5_, p_74990_6_, p_74990_7_, p_74990_5_ + 3 - 1, p_74990_6_ + 3 - 1, p_74990_7_, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            break;
         case WOOD_DOOR:
            this.func_175811_a(p_74990_1_, Blocks.field_150417_aV.func_176223_P(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150417_aV.func_176223_P(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150417_aV.func_176223_P(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150417_aV.func_176223_P(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150417_aV.func_176223_P(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150417_aV.func_176223_P(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150417_aV.func_176223_P(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_180413_ao.func_176223_P(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_180413_ao.func_176223_P().func_177226_a(BlockDoor.field_176523_O, BlockDoor.EnumDoorHalf.UPPER), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
            break;
         case GRATES:
            this.func_175811_a(p_74990_1_, Blocks.field_150350_a.func_176223_P(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150350_a.func_176223_P(), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150411_aY.func_176223_P(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150411_aY.func_176223_P(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150411_aY.func_176223_P(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150411_aY.func_176223_P(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150411_aY.func_176223_P(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150411_aY.func_176223_P(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150411_aY.func_176223_P(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
            break;
         case IRON_DOOR:
            this.func_175811_a(p_74990_1_, Blocks.field_150417_aV.func_176223_P(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150417_aV.func_176223_P(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150417_aV.func_176223_P(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150417_aV.func_176223_P(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150417_aV.func_176223_P(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150417_aV.func_176223_P(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150417_aV.func_176223_P(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150454_av.func_176223_P(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150454_av.func_176223_P().func_177226_a(BlockDoor.field_176523_O, BlockDoor.EnumDoorHalf.UPPER), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150430_aB.func_176223_P().func_177226_a(BlockButton.field_176387_N, EnumFacing.NORTH), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ + 1, p_74990_3_);
            this.func_175811_a(p_74990_1_, Blocks.field_150430_aB.func_176223_P().func_177226_a(BlockButton.field_176387_N, EnumFacing.SOUTH), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ - 1, p_74990_3_);
         }

      }

      protected StructureStrongholdPieces.Stronghold.Door func_74988_a(Random p_74988_1_) {
         int i = p_74988_1_.nextInt(5);
         switch(i) {
         case 0:
         case 1:
         default:
            return StructureStrongholdPieces.Stronghold.Door.OPENING;
         case 2:
            return StructureStrongholdPieces.Stronghold.Door.WOOD_DOOR;
         case 3:
            return StructureStrongholdPieces.Stronghold.Door.GRATES;
         case 4:
            return StructureStrongholdPieces.Stronghold.Door.IRON_DOOR;
         }
      }

      @Nullable
      protected StructureComponent func_74986_a(StructureStrongholdPieces.Stairs2 p_74986_1_, List<StructureComponent> p_74986_2_, Random p_74986_3_, int p_74986_4_, int p_74986_5_) {
         EnumFacing enumfacing = this.func_186165_e();
         if (enumfacing != null) {
            switch(enumfacing) {
            case NORTH:
               return StructureStrongholdPieces.func_175953_c(p_74986_1_, p_74986_2_, p_74986_3_, this.field_74887_e.field_78897_a + p_74986_4_, this.field_74887_e.field_78895_b + p_74986_5_, this.field_74887_e.field_78896_c - 1, enumfacing, this.func_74877_c());
            case SOUTH:
               return StructureStrongholdPieces.func_175953_c(p_74986_1_, p_74986_2_, p_74986_3_, this.field_74887_e.field_78897_a + p_74986_4_, this.field_74887_e.field_78895_b + p_74986_5_, this.field_74887_e.field_78892_f + 1, enumfacing, this.func_74877_c());
            case WEST:
               return StructureStrongholdPieces.func_175953_c(p_74986_1_, p_74986_2_, p_74986_3_, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b + p_74986_5_, this.field_74887_e.field_78896_c + p_74986_4_, enumfacing, this.func_74877_c());
            case EAST:
               return StructureStrongholdPieces.func_175953_c(p_74986_1_, p_74986_2_, p_74986_3_, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b + p_74986_5_, this.field_74887_e.field_78896_c + p_74986_4_, enumfacing, this.func_74877_c());
            }
         }

         return null;
      }

      @Nullable
      protected StructureComponent func_74989_b(StructureStrongholdPieces.Stairs2 p_74989_1_, List<StructureComponent> p_74989_2_, Random p_74989_3_, int p_74989_4_, int p_74989_5_) {
         EnumFacing enumfacing = this.func_186165_e();
         if (enumfacing != null) {
            switch(enumfacing) {
            case NORTH:
               return StructureStrongholdPieces.func_175953_c(p_74989_1_, p_74989_2_, p_74989_3_, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b + p_74989_4_, this.field_74887_e.field_78896_c + p_74989_5_, EnumFacing.WEST, this.func_74877_c());
            case SOUTH:
               return StructureStrongholdPieces.func_175953_c(p_74989_1_, p_74989_2_, p_74989_3_, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b + p_74989_4_, this.field_74887_e.field_78896_c + p_74989_5_, EnumFacing.WEST, this.func_74877_c());
            case WEST:
               return StructureStrongholdPieces.func_175953_c(p_74989_1_, p_74989_2_, p_74989_3_, this.field_74887_e.field_78897_a + p_74989_5_, this.field_74887_e.field_78895_b + p_74989_4_, this.field_74887_e.field_78896_c - 1, EnumFacing.NORTH, this.func_74877_c());
            case EAST:
               return StructureStrongholdPieces.func_175953_c(p_74989_1_, p_74989_2_, p_74989_3_, this.field_74887_e.field_78897_a + p_74989_5_, this.field_74887_e.field_78895_b + p_74989_4_, this.field_74887_e.field_78896_c - 1, EnumFacing.NORTH, this.func_74877_c());
            }
         }

         return null;
      }

      @Nullable
      protected StructureComponent func_74987_c(StructureStrongholdPieces.Stairs2 p_74987_1_, List<StructureComponent> p_74987_2_, Random p_74987_3_, int p_74987_4_, int p_74987_5_) {
         EnumFacing enumfacing = this.func_186165_e();
         if (enumfacing != null) {
            switch(enumfacing) {
            case NORTH:
               return StructureStrongholdPieces.func_175953_c(p_74987_1_, p_74987_2_, p_74987_3_, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b + p_74987_4_, this.field_74887_e.field_78896_c + p_74987_5_, EnumFacing.EAST, this.func_74877_c());
            case SOUTH:
               return StructureStrongholdPieces.func_175953_c(p_74987_1_, p_74987_2_, p_74987_3_, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b + p_74987_4_, this.field_74887_e.field_78896_c + p_74987_5_, EnumFacing.EAST, this.func_74877_c());
            case WEST:
               return StructureStrongholdPieces.func_175953_c(p_74987_1_, p_74987_2_, p_74987_3_, this.field_74887_e.field_78897_a + p_74987_5_, this.field_74887_e.field_78895_b + p_74987_4_, this.field_74887_e.field_78892_f + 1, EnumFacing.SOUTH, this.func_74877_c());
            case EAST:
               return StructureStrongholdPieces.func_175953_c(p_74987_1_, p_74987_2_, p_74987_3_, this.field_74887_e.field_78897_a + p_74987_5_, this.field_74887_e.field_78895_b + p_74987_4_, this.field_74887_e.field_78892_f + 1, EnumFacing.SOUTH, this.func_74877_c());
            }
         }

         return null;
      }

      protected static boolean func_74991_a(StructureBoundingBox p_74991_0_) {
         return p_74991_0_ != null && p_74991_0_.field_78895_b > 10;
      }

      public static enum Door {
         OPENING,
         WOOD_DOOR,
         GRATES,
         IRON_DOOR;
      }
   }
}
