package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class StructureMineshaftPieces {
   public static void func_143048_a() {
      MapGenStructureIO.func_143031_a(StructureMineshaftPieces.Corridor.class, "MSCorridor");
      MapGenStructureIO.func_143031_a(StructureMineshaftPieces.Cross.class, "MSCrossing");
      MapGenStructureIO.func_143031_a(StructureMineshaftPieces.Room.class, "MSRoom");
      MapGenStructureIO.func_143031_a(StructureMineshaftPieces.Stairs.class, "MSStairs");
   }

   private static StructureMineshaftPieces.Peice func_189940_a(List<StructureComponent> p_189940_0_, Random p_189940_1_, int p_189940_2_, int p_189940_3_, int p_189940_4_, @Nullable EnumFacing p_189940_5_, int p_189940_6_, MapGenMineshaft.Type p_189940_7_) {
      int i = p_189940_1_.nextInt(100);
      if (i >= 80) {
         StructureBoundingBox structureboundingbox = StructureMineshaftPieces.Cross.func_175813_a(p_189940_0_, p_189940_1_, p_189940_2_, p_189940_3_, p_189940_4_, p_189940_5_);
         if (structureboundingbox != null) {
            return new StructureMineshaftPieces.Cross(p_189940_6_, p_189940_1_, structureboundingbox, p_189940_5_, p_189940_7_);
         }
      } else if (i >= 70) {
         StructureBoundingBox structureboundingbox1 = StructureMineshaftPieces.Stairs.func_175812_a(p_189940_0_, p_189940_1_, p_189940_2_, p_189940_3_, p_189940_4_, p_189940_5_);
         if (structureboundingbox1 != null) {
            return new StructureMineshaftPieces.Stairs(p_189940_6_, p_189940_1_, structureboundingbox1, p_189940_5_, p_189940_7_);
         }
      } else {
         StructureBoundingBox structureboundingbox2 = StructureMineshaftPieces.Corridor.func_175814_a(p_189940_0_, p_189940_1_, p_189940_2_, p_189940_3_, p_189940_4_, p_189940_5_);
         if (structureboundingbox2 != null) {
            return new StructureMineshaftPieces.Corridor(p_189940_6_, p_189940_1_, structureboundingbox2, p_189940_5_, p_189940_7_);
         }
      }

      return null;
   }

   private static StructureMineshaftPieces.Peice func_189938_b(StructureComponent p_189938_0_, List<StructureComponent> p_189938_1_, Random p_189938_2_, int p_189938_3_, int p_189938_4_, int p_189938_5_, EnumFacing p_189938_6_, int p_189938_7_) {
      if (p_189938_7_ > 8) {
         return null;
      } else if (Math.abs(p_189938_3_ - p_189938_0_.func_74874_b().field_78897_a) <= 80 && Math.abs(p_189938_5_ - p_189938_0_.func_74874_b().field_78896_c) <= 80) {
         MapGenMineshaft.Type mapgenmineshaft$type = ((StructureMineshaftPieces.Peice)p_189938_0_).field_189920_a;
         StructureMineshaftPieces.Peice structuremineshaftpieces$peice = func_189940_a(p_189938_1_, p_189938_2_, p_189938_3_, p_189938_4_, p_189938_5_, p_189938_6_, p_189938_7_ + 1, mapgenmineshaft$type);
         if (structuremineshaftpieces$peice != null) {
            p_189938_1_.add(structuremineshaftpieces$peice);
            structuremineshaftpieces$peice.func_74861_a(p_189938_0_, p_189938_1_, p_189938_2_);
         }

         return structuremineshaftpieces$peice;
      } else {
         return null;
      }
   }

   public static class Corridor extends StructureMineshaftPieces.Peice {
      private boolean field_74958_a;
      private boolean field_74956_b;
      private boolean field_74957_c;
      private int field_74955_d;

      public Corridor() {
      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         super.func_143012_a(p_143012_1_);
         p_143012_1_.func_74757_a("hr", this.field_74958_a);
         p_143012_1_.func_74757_a("sc", this.field_74956_b);
         p_143012_1_.func_74757_a("hps", this.field_74957_c);
         p_143012_1_.func_74768_a("Num", this.field_74955_d);
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         super.func_143011_b(p_143011_1_, p_143011_2_);
         this.field_74958_a = p_143011_1_.func_74767_n("hr");
         this.field_74956_b = p_143011_1_.func_74767_n("sc");
         this.field_74957_c = p_143011_1_.func_74767_n("hps");
         this.field_74955_d = p_143011_1_.func_74762_e("Num");
      }

      public Corridor(int p_i47140_1_, Random p_i47140_2_, StructureBoundingBox p_i47140_3_, EnumFacing p_i47140_4_, MapGenMineshaft.Type p_i47140_5_) {
         super(p_i47140_1_, p_i47140_5_);
         this.func_186164_a(p_i47140_4_);
         this.field_74887_e = p_i47140_3_;
         this.field_74958_a = p_i47140_2_.nextInt(3) == 0;
         this.field_74956_b = !this.field_74958_a && p_i47140_2_.nextInt(23) == 0;
         if (this.func_186165_e().func_176740_k() == EnumFacing.Axis.Z) {
            this.field_74955_d = p_i47140_3_.func_78880_d() / 5;
         } else {
            this.field_74955_d = p_i47140_3_.func_78883_b() / 5;
         }

      }

      public static StructureBoundingBox func_175814_a(List<StructureComponent> p_175814_0_, Random p_175814_1_, int p_175814_2_, int p_175814_3_, int p_175814_4_, EnumFacing p_175814_5_) {
         StructureBoundingBox structureboundingbox = new StructureBoundingBox(p_175814_2_, p_175814_3_, p_175814_4_, p_175814_2_, p_175814_3_ + 2, p_175814_4_);

         int i;
         for(i = p_175814_1_.nextInt(3) + 2; i > 0; --i) {
            int j = i * 5;
            switch(p_175814_5_) {
            case NORTH:
            default:
               structureboundingbox.field_78893_d = p_175814_2_ + 2;
               structureboundingbox.field_78896_c = p_175814_4_ - (j - 1);
               break;
            case SOUTH:
               structureboundingbox.field_78893_d = p_175814_2_ + 2;
               structureboundingbox.field_78892_f = p_175814_4_ + (j - 1);
               break;
            case WEST:
               structureboundingbox.field_78897_a = p_175814_2_ - (j - 1);
               structureboundingbox.field_78892_f = p_175814_4_ + 2;
               break;
            case EAST:
               structureboundingbox.field_78893_d = p_175814_2_ + (j - 1);
               structureboundingbox.field_78892_f = p_175814_4_ + 2;
            }

            if (StructureComponent.func_74883_a(p_175814_0_, structureboundingbox) == null) {
               break;
            }
         }

         return i > 0 ? structureboundingbox : null;
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         int i = this.func_74877_c();
         int j = p_74861_3_.nextInt(4);
         EnumFacing enumfacing = this.func_186165_e();
         if (enumfacing != null) {
            switch(enumfacing) {
            case NORTH:
            default:
               if (j <= 1) {
                  StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a, this.field_74887_e.field_78895_b - 1 + p_74861_3_.nextInt(3), this.field_74887_e.field_78896_c - 1, enumfacing, i);
               } else if (j == 2) {
                  StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b - 1 + p_74861_3_.nextInt(3), this.field_74887_e.field_78896_c, EnumFacing.WEST, i);
               } else {
                  StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b - 1 + p_74861_3_.nextInt(3), this.field_74887_e.field_78896_c, EnumFacing.EAST, i);
               }
               break;
            case SOUTH:
               if (j <= 1) {
                  StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a, this.field_74887_e.field_78895_b - 1 + p_74861_3_.nextInt(3), this.field_74887_e.field_78892_f + 1, enumfacing, i);
               } else if (j == 2) {
                  StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b - 1 + p_74861_3_.nextInt(3), this.field_74887_e.field_78892_f - 3, EnumFacing.WEST, i);
               } else {
                  StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b - 1 + p_74861_3_.nextInt(3), this.field_74887_e.field_78892_f - 3, EnumFacing.EAST, i);
               }
               break;
            case WEST:
               if (j <= 1) {
                  StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b - 1 + p_74861_3_.nextInt(3), this.field_74887_e.field_78896_c, enumfacing, i);
               } else if (j == 2) {
                  StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a, this.field_74887_e.field_78895_b - 1 + p_74861_3_.nextInt(3), this.field_74887_e.field_78896_c - 1, EnumFacing.NORTH, i);
               } else {
                  StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a, this.field_74887_e.field_78895_b - 1 + p_74861_3_.nextInt(3), this.field_74887_e.field_78892_f + 1, EnumFacing.SOUTH, i);
               }
               break;
            case EAST:
               if (j <= 1) {
                  StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b - 1 + p_74861_3_.nextInt(3), this.field_74887_e.field_78896_c, enumfacing, i);
               } else if (j == 2) {
                  StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78893_d - 3, this.field_74887_e.field_78895_b - 1 + p_74861_3_.nextInt(3), this.field_74887_e.field_78896_c - 1, EnumFacing.NORTH, i);
               } else {
                  StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78893_d - 3, this.field_74887_e.field_78895_b - 1 + p_74861_3_.nextInt(3), this.field_74887_e.field_78892_f + 1, EnumFacing.SOUTH, i);
               }
            }
         }

         if (i < 8) {
            if (enumfacing != EnumFacing.NORTH && enumfacing != EnumFacing.SOUTH) {
               for(int i1 = this.field_74887_e.field_78897_a + 3; i1 + 3 <= this.field_74887_e.field_78893_d; i1 += 5) {
                  int j1 = p_74861_3_.nextInt(5);
                  if (j1 == 0) {
                     StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, i1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c - 1, EnumFacing.NORTH, i + 1);
                  } else if (j1 == 1) {
                     StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, i1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78892_f + 1, EnumFacing.SOUTH, i + 1);
                  }
               }
            } else {
               for(int k = this.field_74887_e.field_78896_c + 3; k + 3 <= this.field_74887_e.field_78892_f; k += 5) {
                  int l = p_74861_3_.nextInt(5);
                  if (l == 0) {
                     StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b, k, EnumFacing.WEST, i + 1);
                  } else if (l == 1) {
                     StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b, k, EnumFacing.EAST, i + 1);
                  }
               }
            }
         }

      }

      protected boolean func_186167_a(World p_186167_1_, StructureBoundingBox p_186167_2_, Random p_186167_3_, int p_186167_4_, int p_186167_5_, int p_186167_6_, ResourceLocation p_186167_7_) {
         BlockPos blockpos = new BlockPos(this.func_74865_a(p_186167_4_, p_186167_6_), this.func_74862_a(p_186167_5_), this.func_74873_b(p_186167_4_, p_186167_6_));
         if (p_186167_2_.func_175898_b(blockpos) && p_186167_1_.func_180495_p(blockpos).func_185904_a() == Material.field_151579_a && p_186167_1_.func_180495_p(blockpos.func_177977_b()).func_185904_a() != Material.field_151579_a) {
            IBlockState iblockstate = Blocks.field_150448_aq.func_176223_P().func_177226_a(BlockRail.field_176565_b, p_186167_3_.nextBoolean() ? BlockRailBase.EnumRailDirection.NORTH_SOUTH : BlockRailBase.EnumRailDirection.EAST_WEST);
            this.func_175811_a(p_186167_1_, iblockstate, p_186167_4_, p_186167_5_, p_186167_6_, p_186167_2_);
            EntityMinecartChest entityminecartchest = new EntityMinecartChest(p_186167_1_, (double)((float)blockpos.func_177958_n() + 0.5F), (double)((float)blockpos.func_177956_o() + 0.5F), (double)((float)blockpos.func_177952_p() + 0.5F));
            entityminecartchest.func_184289_a(p_186167_7_, p_186167_3_.nextLong());
            p_186167_1_.func_72838_d(entityminecartchest);
            return true;
         } else {
            return false;
         }
      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         if (this.func_74860_a(p_74875_1_, p_74875_3_)) {
            return false;
         } else {
            int i = 0;
            int j = 2;
            int k = 0;
            int l = 2;
            int i1 = this.field_74955_d * 5 - 1;
            IBlockState iblockstate = this.func_189917_F_();
            this.func_175804_a(p_74875_1_, p_74875_3_, 0, 0, 0, 2, 1, i1, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            this.func_189914_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.8F, 0, 2, 0, 2, 2, i1, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false, 0);
            if (this.field_74956_b) {
               this.func_189914_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.6F, 0, 0, 0, 2, 1, i1, Blocks.field_150321_G.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false, 8);
            }

            for(int j1 = 0; j1 < this.field_74955_d; ++j1) {
               int k1 = 2 + j1 * 5;
               this.func_189921_a(p_74875_1_, p_74875_3_, 0, 0, k1, 2, 2, p_74875_2_);
               this.func_189922_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.1F, 0, 2, k1 - 1);
               this.func_189922_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.1F, 2, 2, k1 - 1);
               this.func_189922_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.1F, 0, 2, k1 + 1);
               this.func_189922_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.1F, 2, 2, k1 + 1);
               this.func_189922_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.05F, 0, 2, k1 - 2);
               this.func_189922_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.05F, 2, 2, k1 - 2);
               this.func_189922_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.05F, 0, 2, k1 + 2);
               this.func_189922_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.05F, 2, 2, k1 + 2);
               if (p_74875_2_.nextInt(100) == 0) {
                  this.func_186167_a(p_74875_1_, p_74875_3_, p_74875_2_, 2, 0, k1 - 1, LootTableList.field_186424_f);
               }

               if (p_74875_2_.nextInt(100) == 0) {
                  this.func_186167_a(p_74875_1_, p_74875_3_, p_74875_2_, 0, 0, k1 + 1, LootTableList.field_186424_f);
               }

               if (this.field_74956_b && !this.field_74957_c) {
                  int l1 = this.func_74862_a(0);
                  int i2 = k1 - 1 + p_74875_2_.nextInt(3);
                  int j2 = this.func_74865_a(1, i2);
                  int k2 = this.func_74873_b(1, i2);
                  BlockPos blockpos = new BlockPos(j2, l1, k2);
                  if (p_74875_3_.func_175898_b(blockpos) && this.func_189916_b(p_74875_1_, 1, 0, i2, p_74875_3_) < 8) {
                     this.field_74957_c = true;
                     p_74875_1_.func_180501_a(blockpos, Blocks.field_150474_ac.func_176223_P(), 2);
                     TileEntity tileentity = p_74875_1_.func_175625_s(blockpos);
                     if (tileentity instanceof TileEntityMobSpawner) {
                        ((TileEntityMobSpawner)tileentity).func_145881_a().func_190894_a(EntityList.func_191306_a(EntityCaveSpider.class));
                     }
                  }
               }
            }

            for(int l2 = 0; l2 <= 2; ++l2) {
               for(int i3 = 0; i3 <= i1; ++i3) {
                  int k3 = -1;
                  IBlockState iblockstate3 = this.func_175807_a(p_74875_1_, l2, -1, i3, p_74875_3_);
                  if (iblockstate3.func_185904_a() == Material.field_151579_a && this.func_189916_b(p_74875_1_, l2, -1, i3, p_74875_3_) < 8) {
                     int l3 = -1;
                     this.func_175811_a(p_74875_1_, iblockstate, l2, -1, i3, p_74875_3_);
                  }
               }
            }

            if (this.field_74958_a) {
               IBlockState iblockstate1 = Blocks.field_150448_aq.func_176223_P().func_177226_a(BlockRail.field_176565_b, BlockRailBase.EnumRailDirection.NORTH_SOUTH);

               for(int j3 = 0; j3 <= i1; ++j3) {
                  IBlockState iblockstate2 = this.func_175807_a(p_74875_1_, 1, -1, j3, p_74875_3_);
                  if (iblockstate2.func_185904_a() != Material.field_151579_a && iblockstate2.func_185913_b()) {
                     float f = this.func_189916_b(p_74875_1_, 1, 0, j3, p_74875_3_) > 8 ? 0.9F : 0.7F;
                     this.func_175809_a(p_74875_1_, p_74875_3_, p_74875_2_, f, 1, 0, j3, iblockstate1);
                  }
               }
            }

            return true;
         }
      }

      private void func_189921_a(World p_189921_1_, StructureBoundingBox p_189921_2_, int p_189921_3_, int p_189921_4_, int p_189921_5_, int p_189921_6_, int p_189921_7_, Random p_189921_8_) {
         if (this.func_189918_a(p_189921_1_, p_189921_2_, p_189921_3_, p_189921_7_, p_189921_6_, p_189921_5_)) {
            IBlockState iblockstate = this.func_189917_F_();
            IBlockState iblockstate1 = this.func_189919_b();
            IBlockState iblockstate2 = Blocks.field_150350_a.func_176223_P();
            this.func_175804_a(p_189921_1_, p_189921_2_, p_189921_3_, p_189921_4_, p_189921_5_, p_189921_3_, p_189921_6_ - 1, p_189921_5_, iblockstate1, iblockstate2, false);
            this.func_175804_a(p_189921_1_, p_189921_2_, p_189921_7_, p_189921_4_, p_189921_5_, p_189921_7_, p_189921_6_ - 1, p_189921_5_, iblockstate1, iblockstate2, false);
            if (p_189921_8_.nextInt(4) == 0) {
               this.func_175804_a(p_189921_1_, p_189921_2_, p_189921_3_, p_189921_6_, p_189921_5_, p_189921_3_, p_189921_6_, p_189921_5_, iblockstate, iblockstate2, false);
               this.func_175804_a(p_189921_1_, p_189921_2_, p_189921_7_, p_189921_6_, p_189921_5_, p_189921_7_, p_189921_6_, p_189921_5_, iblockstate, iblockstate2, false);
            } else {
               this.func_175804_a(p_189921_1_, p_189921_2_, p_189921_3_, p_189921_6_, p_189921_5_, p_189921_7_, p_189921_6_, p_189921_5_, iblockstate, iblockstate2, false);
               this.func_175809_a(p_189921_1_, p_189921_2_, p_189921_8_, 0.05F, p_189921_3_ + 1, p_189921_6_, p_189921_5_ - 1, Blocks.field_150478_aa.func_176223_P().func_177226_a(BlockTorch.field_176596_a, EnumFacing.NORTH));
               this.func_175809_a(p_189921_1_, p_189921_2_, p_189921_8_, 0.05F, p_189921_3_ + 1, p_189921_6_, p_189921_5_ + 1, Blocks.field_150478_aa.func_176223_P().func_177226_a(BlockTorch.field_176596_a, EnumFacing.SOUTH));
            }

         }
      }

      private void func_189922_a(World p_189922_1_, StructureBoundingBox p_189922_2_, Random p_189922_3_, float p_189922_4_, int p_189922_5_, int p_189922_6_, int p_189922_7_) {
         if (this.func_189916_b(p_189922_1_, p_189922_5_, p_189922_6_, p_189922_7_, p_189922_2_) < 8) {
            this.func_175809_a(p_189922_1_, p_189922_2_, p_189922_3_, p_189922_4_, p_189922_5_, p_189922_6_, p_189922_7_, Blocks.field_150321_G.func_176223_P());
         }

      }
   }

   public static class Cross extends StructureMineshaftPieces.Peice {
      private EnumFacing field_74953_a;
      private boolean field_74952_b;

      public Cross() {
      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         super.func_143012_a(p_143012_1_);
         p_143012_1_.func_74757_a("tf", this.field_74952_b);
         p_143012_1_.func_74768_a("D", this.field_74953_a.func_176736_b());
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         super.func_143011_b(p_143011_1_, p_143011_2_);
         this.field_74952_b = p_143011_1_.func_74767_n("tf");
         this.field_74953_a = EnumFacing.func_176731_b(p_143011_1_.func_74762_e("D"));
      }

      public Cross(int p_i47139_1_, Random p_i47139_2_, StructureBoundingBox p_i47139_3_, @Nullable EnumFacing p_i47139_4_, MapGenMineshaft.Type p_i47139_5_) {
         super(p_i47139_1_, p_i47139_5_);
         this.field_74953_a = p_i47139_4_;
         this.field_74887_e = p_i47139_3_;
         this.field_74952_b = p_i47139_3_.func_78882_c() > 3;
      }

      public static StructureBoundingBox func_175813_a(List<StructureComponent> p_175813_0_, Random p_175813_1_, int p_175813_2_, int p_175813_3_, int p_175813_4_, EnumFacing p_175813_5_) {
         StructureBoundingBox structureboundingbox = new StructureBoundingBox(p_175813_2_, p_175813_3_, p_175813_4_, p_175813_2_, p_175813_3_ + 2, p_175813_4_);
         if (p_175813_1_.nextInt(4) == 0) {
            structureboundingbox.field_78894_e += 4;
         }

         switch(p_175813_5_) {
         case NORTH:
         default:
            structureboundingbox.field_78897_a = p_175813_2_ - 1;
            structureboundingbox.field_78893_d = p_175813_2_ + 3;
            structureboundingbox.field_78896_c = p_175813_4_ - 4;
            break;
         case SOUTH:
            structureboundingbox.field_78897_a = p_175813_2_ - 1;
            structureboundingbox.field_78893_d = p_175813_2_ + 3;
            structureboundingbox.field_78892_f = p_175813_4_ + 3 + 1;
            break;
         case WEST:
            structureboundingbox.field_78897_a = p_175813_2_ - 4;
            structureboundingbox.field_78896_c = p_175813_4_ - 1;
            structureboundingbox.field_78892_f = p_175813_4_ + 3;
            break;
         case EAST:
            structureboundingbox.field_78893_d = p_175813_2_ + 3 + 1;
            structureboundingbox.field_78896_c = p_175813_4_ - 1;
            structureboundingbox.field_78892_f = p_175813_4_ + 3;
         }

         return StructureComponent.func_74883_a(p_175813_0_, structureboundingbox) != null ? null : structureboundingbox;
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         int i = this.func_74877_c();
         switch(this.field_74953_a) {
         case NORTH:
         default:
            StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c - 1, EnumFacing.NORTH, i);
            StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, EnumFacing.WEST, i);
            StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, EnumFacing.EAST, i);
            break;
         case SOUTH:
            StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78892_f + 1, EnumFacing.SOUTH, i);
            StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, EnumFacing.WEST, i);
            StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, EnumFacing.EAST, i);
            break;
         case WEST:
            StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c - 1, EnumFacing.NORTH, i);
            StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78892_f + 1, EnumFacing.SOUTH, i);
            StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, EnumFacing.WEST, i);
            break;
         case EAST:
            StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c - 1, EnumFacing.NORTH, i);
            StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78892_f + 1, EnumFacing.SOUTH, i);
            StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, EnumFacing.EAST, i);
         }

         if (this.field_74952_b) {
            if (p_74861_3_.nextBoolean()) {
               StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b + 3 + 1, this.field_74887_e.field_78896_c - 1, EnumFacing.NORTH, i);
            }

            if (p_74861_3_.nextBoolean()) {
               StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b + 3 + 1, this.field_74887_e.field_78896_c + 1, EnumFacing.WEST, i);
            }

            if (p_74861_3_.nextBoolean()) {
               StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b + 3 + 1, this.field_74887_e.field_78896_c + 1, EnumFacing.EAST, i);
            }

            if (p_74861_3_.nextBoolean()) {
               StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b + 3 + 1, this.field_74887_e.field_78892_f + 1, EnumFacing.SOUTH, i);
            }
         }

      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         if (this.func_74860_a(p_74875_1_, p_74875_3_)) {
            return false;
         } else {
            IBlockState iblockstate = this.func_189917_F_();
            if (this.field_74952_b) {
               this.func_175804_a(p_74875_1_, p_74875_3_, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c, this.field_74887_e.field_78893_d - 1, this.field_74887_e.field_78895_b + 3 - 1, this.field_74887_e.field_78892_f, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
               this.func_175804_a(p_74875_1_, p_74875_3_, this.field_74887_e.field_78897_a, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, this.field_74887_e.field_78893_d, this.field_74887_e.field_78895_b + 3 - 1, this.field_74887_e.field_78892_f - 1, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
               this.func_175804_a(p_74875_1_, p_74875_3_, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78894_e - 2, this.field_74887_e.field_78896_c, this.field_74887_e.field_78893_d - 1, this.field_74887_e.field_78894_e, this.field_74887_e.field_78892_f, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
               this.func_175804_a(p_74875_1_, p_74875_3_, this.field_74887_e.field_78897_a, this.field_74887_e.field_78894_e - 2, this.field_74887_e.field_78896_c + 1, this.field_74887_e.field_78893_d, this.field_74887_e.field_78894_e, this.field_74887_e.field_78892_f - 1, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
               this.func_175804_a(p_74875_1_, p_74875_3_, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b + 3, this.field_74887_e.field_78896_c + 1, this.field_74887_e.field_78893_d - 1, this.field_74887_e.field_78895_b + 3, this.field_74887_e.field_78892_f - 1, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            } else {
               this.func_175804_a(p_74875_1_, p_74875_3_, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c, this.field_74887_e.field_78893_d - 1, this.field_74887_e.field_78894_e, this.field_74887_e.field_78892_f, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
               this.func_175804_a(p_74875_1_, p_74875_3_, this.field_74887_e.field_78897_a, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, this.field_74887_e.field_78893_d, this.field_74887_e.field_78894_e, this.field_74887_e.field_78892_f - 1, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            }

            this.func_189923_b(p_74875_1_, p_74875_3_, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, this.field_74887_e.field_78894_e);
            this.func_189923_b(p_74875_1_, p_74875_3_, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78892_f - 1, this.field_74887_e.field_78894_e);
            this.func_189923_b(p_74875_1_, p_74875_3_, this.field_74887_e.field_78893_d - 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, this.field_74887_e.field_78894_e);
            this.func_189923_b(p_74875_1_, p_74875_3_, this.field_74887_e.field_78893_d - 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78892_f - 1, this.field_74887_e.field_78894_e);

            for(int i = this.field_74887_e.field_78897_a; i <= this.field_74887_e.field_78893_d; ++i) {
               for(int j = this.field_74887_e.field_78896_c; j <= this.field_74887_e.field_78892_f; ++j) {
                  if (this.func_175807_a(p_74875_1_, i, this.field_74887_e.field_78895_b - 1, j, p_74875_3_).func_185904_a() == Material.field_151579_a && this.func_189916_b(p_74875_1_, i, this.field_74887_e.field_78895_b - 1, j, p_74875_3_) < 8) {
                     this.func_175811_a(p_74875_1_, iblockstate, i, this.field_74887_e.field_78895_b - 1, j, p_74875_3_);
                  }
               }
            }

            return true;
         }
      }

      private void func_189923_b(World p_189923_1_, StructureBoundingBox p_189923_2_, int p_189923_3_, int p_189923_4_, int p_189923_5_, int p_189923_6_) {
         if (this.func_175807_a(p_189923_1_, p_189923_3_, p_189923_6_ + 1, p_189923_5_, p_189923_2_).func_185904_a() != Material.field_151579_a) {
            this.func_175804_a(p_189923_1_, p_189923_2_, p_189923_3_, p_189923_4_, p_189923_5_, p_189923_3_, p_189923_6_, p_189923_5_, this.func_189917_F_(), Blocks.field_150350_a.func_176223_P(), false);
         }

      }
   }

   abstract static class Peice extends StructureComponent {
      protected MapGenMineshaft.Type field_189920_a;

      public Peice() {
      }

      public Peice(int p_i47138_1_, MapGenMineshaft.Type p_i47138_2_) {
         super(p_i47138_1_);
         this.field_189920_a = p_i47138_2_;
      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         p_143012_1_.func_74768_a("MST", this.field_189920_a.ordinal());
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         this.field_189920_a = MapGenMineshaft.Type.func_189910_a(p_143011_1_.func_74762_e("MST"));
      }

      protected IBlockState func_189917_F_() {
         switch(this.field_189920_a) {
         case NORMAL:
         default:
            return Blocks.field_150344_f.func_176223_P();
         case MESA:
            return Blocks.field_150344_f.func_176223_P().func_177226_a(BlockPlanks.field_176383_a, BlockPlanks.EnumType.DARK_OAK);
         }
      }

      protected IBlockState func_189919_b() {
         switch(this.field_189920_a) {
         case NORMAL:
         default:
            return Blocks.field_180407_aO.func_176223_P();
         case MESA:
            return Blocks.field_180406_aS.func_176223_P();
         }
      }

      protected boolean func_189918_a(World p_189918_1_, StructureBoundingBox p_189918_2_, int p_189918_3_, int p_189918_4_, int p_189918_5_, int p_189918_6_) {
         for(int i = p_189918_3_; i <= p_189918_4_; ++i) {
            if (this.func_175807_a(p_189918_1_, i, p_189918_5_ + 1, p_189918_6_, p_189918_2_).func_185904_a() == Material.field_151579_a) {
               return false;
            }
         }

         return true;
      }
   }

   public static class Room extends StructureMineshaftPieces.Peice {
      private final List<StructureBoundingBox> field_74949_a = Lists.<StructureBoundingBox>newLinkedList();

      public Room() {
      }

      public Room(int p_i47137_1_, Random p_i47137_2_, int p_i47137_3_, int p_i47137_4_, MapGenMineshaft.Type p_i47137_5_) {
         super(p_i47137_1_, p_i47137_5_);
         this.field_189920_a = p_i47137_5_;
         this.field_74887_e = new StructureBoundingBox(p_i47137_3_, 50, p_i47137_4_, p_i47137_3_ + 7 + p_i47137_2_.nextInt(6), 54 + p_i47137_2_.nextInt(6), p_i47137_4_ + 7 + p_i47137_2_.nextInt(6));
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         int i = this.func_74877_c();
         int j = this.field_74887_e.func_78882_c() - 3 - 1;
         if (j <= 0) {
            j = 1;
         }

         int k;
         for(lvt_5_1_ = 0; k < this.field_74887_e.func_78883_b(); k = k + 4) {
            k = k + p_74861_3_.nextInt(this.field_74887_e.func_78883_b());
            if (k + 3 > this.field_74887_e.func_78883_b()) {
               break;
            }

            StructureMineshaftPieces.Peice structuremineshaftpieces$peice = StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a + k, this.field_74887_e.field_78895_b + p_74861_3_.nextInt(j) + 1, this.field_74887_e.field_78896_c - 1, EnumFacing.NORTH, i);
            if (structuremineshaftpieces$peice != null) {
               StructureBoundingBox structureboundingbox = structuremineshaftpieces$peice.func_74874_b();
               this.field_74949_a.add(new StructureBoundingBox(structureboundingbox.field_78897_a, structureboundingbox.field_78895_b, this.field_74887_e.field_78896_c, structureboundingbox.field_78893_d, structureboundingbox.field_78894_e, this.field_74887_e.field_78896_c + 1));
            }
         }

         for(k = 0; k < this.field_74887_e.func_78883_b(); k = k + 4) {
            k = k + p_74861_3_.nextInt(this.field_74887_e.func_78883_b());
            if (k + 3 > this.field_74887_e.func_78883_b()) {
               break;
            }

            StructureMineshaftPieces.Peice structuremineshaftpieces$peice1 = StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a + k, this.field_74887_e.field_78895_b + p_74861_3_.nextInt(j) + 1, this.field_74887_e.field_78892_f + 1, EnumFacing.SOUTH, i);
            if (structuremineshaftpieces$peice1 != null) {
               StructureBoundingBox structureboundingbox1 = structuremineshaftpieces$peice1.func_74874_b();
               this.field_74949_a.add(new StructureBoundingBox(structureboundingbox1.field_78897_a, structureboundingbox1.field_78895_b, this.field_74887_e.field_78892_f - 1, structureboundingbox1.field_78893_d, structureboundingbox1.field_78894_e, this.field_74887_e.field_78892_f));
            }
         }

         for(k = 0; k < this.field_74887_e.func_78880_d(); k = k + 4) {
            k = k + p_74861_3_.nextInt(this.field_74887_e.func_78880_d());
            if (k + 3 > this.field_74887_e.func_78880_d()) {
               break;
            }

            StructureMineshaftPieces.Peice structuremineshaftpieces$peice2 = StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b + p_74861_3_.nextInt(j) + 1, this.field_74887_e.field_78896_c + k, EnumFacing.WEST, i);
            if (structuremineshaftpieces$peice2 != null) {
               StructureBoundingBox structureboundingbox2 = structuremineshaftpieces$peice2.func_74874_b();
               this.field_74949_a.add(new StructureBoundingBox(this.field_74887_e.field_78897_a, structureboundingbox2.field_78895_b, structureboundingbox2.field_78896_c, this.field_74887_e.field_78897_a + 1, structureboundingbox2.field_78894_e, structureboundingbox2.field_78892_f));
            }
         }

         for(k = 0; k < this.field_74887_e.func_78880_d(); k = k + 4) {
            k = k + p_74861_3_.nextInt(this.field_74887_e.func_78880_d());
            if (k + 3 > this.field_74887_e.func_78880_d()) {
               break;
            }

            StructureComponent structurecomponent = StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b + p_74861_3_.nextInt(j) + 1, this.field_74887_e.field_78896_c + k, EnumFacing.EAST, i);
            if (structurecomponent != null) {
               StructureBoundingBox structureboundingbox3 = structurecomponent.func_74874_b();
               this.field_74949_a.add(new StructureBoundingBox(this.field_74887_e.field_78893_d - 1, structureboundingbox3.field_78895_b, structureboundingbox3.field_78896_c, this.field_74887_e.field_78893_d, structureboundingbox3.field_78894_e, structureboundingbox3.field_78892_f));
            }
         }

      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         if (this.func_74860_a(p_74875_1_, p_74875_3_)) {
            return false;
         } else {
            this.func_175804_a(p_74875_1_, p_74875_3_, this.field_74887_e.field_78897_a, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c, this.field_74887_e.field_78893_d, this.field_74887_e.field_78895_b, this.field_74887_e.field_78892_f, Blocks.field_150346_d.func_176223_P(), Blocks.field_150350_a.func_176223_P(), true);
            this.func_175804_a(p_74875_1_, p_74875_3_, this.field_74887_e.field_78897_a, this.field_74887_e.field_78895_b + 1, this.field_74887_e.field_78896_c, this.field_74887_e.field_78893_d, Math.min(this.field_74887_e.field_78895_b + 3, this.field_74887_e.field_78894_e), this.field_74887_e.field_78892_f, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);

            for(StructureBoundingBox structureboundingbox : this.field_74949_a) {
               this.func_175804_a(p_74875_1_, p_74875_3_, structureboundingbox.field_78897_a, structureboundingbox.field_78894_e - 2, structureboundingbox.field_78896_c, structureboundingbox.field_78893_d, structureboundingbox.field_78894_e, structureboundingbox.field_78892_f, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            }

            this.func_180777_a(p_74875_1_, p_74875_3_, this.field_74887_e.field_78897_a, this.field_74887_e.field_78895_b + 4, this.field_74887_e.field_78896_c, this.field_74887_e.field_78893_d, this.field_74887_e.field_78894_e, this.field_74887_e.field_78892_f, Blocks.field_150350_a.func_176223_P(), false);
            return true;
         }
      }

      public void func_181138_a(int p_181138_1_, int p_181138_2_, int p_181138_3_) {
         super.func_181138_a(p_181138_1_, p_181138_2_, p_181138_3_);

         for(StructureBoundingBox structureboundingbox : this.field_74949_a) {
            structureboundingbox.func_78886_a(p_181138_1_, p_181138_2_, p_181138_3_);
         }

      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         super.func_143012_a(p_143012_1_);
         NBTTagList nbttaglist = new NBTTagList();

         for(StructureBoundingBox structureboundingbox : this.field_74949_a) {
            nbttaglist.func_74742_a(structureboundingbox.func_151535_h());
         }

         p_143012_1_.func_74782_a("Entrances", nbttaglist);
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         super.func_143011_b(p_143011_1_, p_143011_2_);
         NBTTagList nbttaglist = p_143011_1_.func_150295_c("Entrances", 11);

         for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
            this.field_74949_a.add(new StructureBoundingBox(nbttaglist.func_150306_c(i)));
         }

      }
   }

   public static class Stairs extends StructureMineshaftPieces.Peice {
      public Stairs() {
      }

      public Stairs(int p_i47136_1_, Random p_i47136_2_, StructureBoundingBox p_i47136_3_, EnumFacing p_i47136_4_, MapGenMineshaft.Type p_i47136_5_) {
         super(p_i47136_1_, p_i47136_5_);
         this.func_186164_a(p_i47136_4_);
         this.field_74887_e = p_i47136_3_;
      }

      public static StructureBoundingBox func_175812_a(List<StructureComponent> p_175812_0_, Random p_175812_1_, int p_175812_2_, int p_175812_3_, int p_175812_4_, EnumFacing p_175812_5_) {
         StructureBoundingBox structureboundingbox = new StructureBoundingBox(p_175812_2_, p_175812_3_ - 5, p_175812_4_, p_175812_2_, p_175812_3_ + 2, p_175812_4_);
         switch(p_175812_5_) {
         case NORTH:
         default:
            structureboundingbox.field_78893_d = p_175812_2_ + 2;
            structureboundingbox.field_78896_c = p_175812_4_ - 8;
            break;
         case SOUTH:
            structureboundingbox.field_78893_d = p_175812_2_ + 2;
            structureboundingbox.field_78892_f = p_175812_4_ + 8;
            break;
         case WEST:
            structureboundingbox.field_78897_a = p_175812_2_ - 8;
            structureboundingbox.field_78892_f = p_175812_4_ + 2;
            break;
         case EAST:
            structureboundingbox.field_78893_d = p_175812_2_ + 8;
            structureboundingbox.field_78892_f = p_175812_4_ + 2;
         }

         return StructureComponent.func_74883_a(p_175812_0_, structureboundingbox) != null ? null : structureboundingbox;
      }

      public void func_74861_a(StructureComponent p_74861_1_, List<StructureComponent> p_74861_2_, Random p_74861_3_) {
         int i = this.func_74877_c();
         EnumFacing enumfacing = this.func_186165_e();
         if (enumfacing != null) {
            switch(enumfacing) {
            case NORTH:
            default:
               StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c - 1, EnumFacing.NORTH, i);
               break;
            case SOUTH:
               StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a, this.field_74887_e.field_78895_b, this.field_74887_e.field_78892_f + 1, EnumFacing.SOUTH, i);
               break;
            case WEST:
               StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c, EnumFacing.WEST, i);
               break;
            case EAST:
               StructureMineshaftPieces.func_189938_b(p_74861_1_, p_74861_2_, p_74861_3_, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c, EnumFacing.EAST, i);
            }
         }

      }

      public boolean func_74875_a(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
         if (this.func_74860_a(p_74875_1_, p_74875_3_)) {
            return false;
         } else {
            this.func_175804_a(p_74875_1_, p_74875_3_, 0, 5, 0, 2, 7, 1, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            this.func_175804_a(p_74875_1_, p_74875_3_, 0, 0, 7, 2, 2, 8, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);

            for(int i = 0; i < 5; ++i) {
               this.func_175804_a(p_74875_1_, p_74875_3_, 0, 5 - i - (i < 4 ? 1 : 0), 2 + i, 2, 7 - i, 2 + i, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
            }

            return true;
         }
      }
   }
}
