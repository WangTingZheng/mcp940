package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class StructureEndCityPieces {
   private static final PlacementSettings field_186202_b = (new PlacementSettings()).func_186222_a(true);
   private static final PlacementSettings field_186203_c = (new PlacementSettings()).func_186222_a(true).func_186225_a(Blocks.field_150350_a);
   private static final StructureEndCityPieces.IGenerator field_186204_d = new StructureEndCityPieces.IGenerator() {
      public void func_186184_a() {
      }

      public boolean func_191086_a(TemplateManager p_191086_1_, int p_191086_2_, StructureEndCityPieces.CityTemplate p_191086_3_, BlockPos p_191086_4_, List<StructureComponent> p_191086_5_, Random p_191086_6_) {
         if (p_191086_2_ > 8) {
            return false;
         } else {
            Rotation rotation = p_191086_3_.field_186177_b.func_186215_c();
            StructureEndCityPieces.CityTemplate structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, p_191086_3_, p_191086_4_, "base_floor", rotation, true));
            int i = p_191086_6_.nextInt(3);
            if (i == 0) {
               StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-1, 4, -1), "base_roof", rotation, true));
            } else if (i == 1) {
               structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-1, 0, -1), "second_floor_2", rotation, false));
               structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-1, 8, -1), "second_roof", rotation, false));
               StructureEndCityPieces.func_191088_b(p_191086_1_, StructureEndCityPieces.field_186206_f, p_191086_2_ + 1, structureendcitypieces$citytemplate, (BlockPos)null, p_191086_5_, p_191086_6_);
            } else if (i == 2) {
               structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-1, 0, -1), "second_floor_2", rotation, false));
               structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-1, 4, -1), "third_floor_c", rotation, false));
               structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-1, 8, -1), "third_roof", rotation, true));
               StructureEndCityPieces.func_191088_b(p_191086_1_, StructureEndCityPieces.field_186206_f, p_191086_2_ + 1, structureendcitypieces$citytemplate, (BlockPos)null, p_191086_5_, p_191086_6_);
            }

            return true;
         }
      }
   };
   private static final List<Tuple<Rotation, BlockPos>> field_186205_e = Lists.newArrayList(new Tuple(Rotation.NONE, new BlockPos(1, -1, 0)), new Tuple(Rotation.CLOCKWISE_90, new BlockPos(6, -1, 1)), new Tuple(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 5)), new Tuple(Rotation.CLOCKWISE_180, new BlockPos(5, -1, 6)));
   private static final StructureEndCityPieces.IGenerator field_186206_f = new StructureEndCityPieces.IGenerator() {
      public void func_186184_a() {
      }

      public boolean func_191086_a(TemplateManager p_191086_1_, int p_191086_2_, StructureEndCityPieces.CityTemplate p_191086_3_, BlockPos p_191086_4_, List<StructureComponent> p_191086_5_, Random p_191086_6_) {
         Rotation rotation = p_191086_3_.field_186177_b.func_186215_c();
         StructureEndCityPieces.CityTemplate lvt_8_1_ = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, p_191086_3_, new BlockPos(3 + p_191086_6_.nextInt(2), -3, 3 + p_191086_6_.nextInt(2)), "tower_base", rotation, true));
         lvt_8_1_ = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, lvt_8_1_, new BlockPos(0, 7, 0), "tower_piece", rotation, true));
         StructureEndCityPieces.CityTemplate structureendcitypieces$citytemplate1 = p_191086_6_.nextInt(3) == 0 ? lvt_8_1_ : null;
         int i = 1 + p_191086_6_.nextInt(3);

         for(int j = 0; j < i; ++j) {
            lvt_8_1_ = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, lvt_8_1_, new BlockPos(0, 4, 0), "tower_piece", rotation, true));
            if (j < i - 1 && p_191086_6_.nextBoolean()) {
               structureendcitypieces$citytemplate1 = lvt_8_1_;
            }
         }

         if (structureendcitypieces$citytemplate1 != null) {
            for(Tuple<Rotation, BlockPos> tuple : StructureEndCityPieces.field_186205_e) {
               if (p_191086_6_.nextBoolean()) {
                  StructureEndCityPieces.CityTemplate structureendcitypieces$citytemplate2 = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate1, tuple.func_76340_b(), "bridge_end", rotation.func_185830_a(tuple.func_76341_a()), true));
                  StructureEndCityPieces.func_191088_b(p_191086_1_, StructureEndCityPieces.field_186207_g, p_191086_2_ + 1, structureendcitypieces$citytemplate2, (BlockPos)null, p_191086_5_, p_191086_6_);
               }
            }

            StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, lvt_8_1_, new BlockPos(-1, 4, -1), "tower_top", rotation, true));
         } else {
            if (p_191086_2_ != 7) {
               return StructureEndCityPieces.func_191088_b(p_191086_1_, StructureEndCityPieces.field_186209_i, p_191086_2_ + 1, lvt_8_1_, (BlockPos)null, p_191086_5_, p_191086_6_);
            }

            StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, lvt_8_1_, new BlockPos(-1, 4, -1), "tower_top", rotation, true));
         }

         return true;
      }
   };
   private static final StructureEndCityPieces.IGenerator field_186207_g = new StructureEndCityPieces.IGenerator() {
      public boolean field_186186_a;

      public void func_186184_a() {
         this.field_186186_a = false;
      }

      public boolean func_191086_a(TemplateManager p_191086_1_, int p_191086_2_, StructureEndCityPieces.CityTemplate p_191086_3_, BlockPos p_191086_4_, List<StructureComponent> p_191086_5_, Random p_191086_6_) {
         Rotation rotation = p_191086_3_.field_186177_b.func_186215_c();
         int i = p_191086_6_.nextInt(4) + 1;
         StructureEndCityPieces.CityTemplate structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, p_191086_3_, new BlockPos(0, 0, -4), "bridge_piece", rotation, true));
         structureendcitypieces$citytemplate.field_74886_g = -1;
         int j = 0;

         for(int k = 0; k < i; ++k) {
            if (p_191086_6_.nextBoolean()) {
               structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(0, j, -4), "bridge_piece", rotation, true));
               j = 0;
            } else {
               if (p_191086_6_.nextBoolean()) {
                  structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(0, j, -4), "bridge_steep_stairs", rotation, true));
               } else {
                  structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(0, j, -8), "bridge_gentle_stairs", rotation, true));
               }

               j = 4;
            }
         }

         if (!this.field_186186_a && p_191086_6_.nextInt(10 - p_191086_2_) == 0) {
            StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-8 + p_191086_6_.nextInt(8), j, -70 + p_191086_6_.nextInt(10)), "ship", rotation, true));
            this.field_186186_a = true;
         } else if (!StructureEndCityPieces.func_191088_b(p_191086_1_, StructureEndCityPieces.field_186204_d, p_191086_2_ + 1, structureendcitypieces$citytemplate, new BlockPos(-3, j + 1, -11), p_191086_5_, p_191086_6_)) {
            return false;
         }

         structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(4, j, 0), "bridge_end", rotation.func_185830_a(Rotation.CLOCKWISE_180), true));
         structureendcitypieces$citytemplate.field_74886_g = -1;
         return true;
      }
   };
   private static final List<Tuple<Rotation, BlockPos>> field_186208_h = Lists.newArrayList(new Tuple(Rotation.NONE, new BlockPos(4, -1, 0)), new Tuple(Rotation.CLOCKWISE_90, new BlockPos(12, -1, 4)), new Tuple(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 8)), new Tuple(Rotation.CLOCKWISE_180, new BlockPos(8, -1, 12)));
   private static final StructureEndCityPieces.IGenerator field_186209_i = new StructureEndCityPieces.IGenerator() {
      public void func_186184_a() {
      }

      public boolean func_191086_a(TemplateManager p_191086_1_, int p_191086_2_, StructureEndCityPieces.CityTemplate p_191086_3_, BlockPos p_191086_4_, List<StructureComponent> p_191086_5_, Random p_191086_6_) {
         Rotation rotation = p_191086_3_.field_186177_b.func_186215_c();
         StructureEndCityPieces.CityTemplate structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, p_191086_3_, new BlockPos(-3, 4, -3), "fat_tower_base", rotation, true));
         structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(0, 4, 0), "fat_tower_middle", rotation, true));

         for(int i = 0; i < 2 && p_191086_6_.nextInt(3) != 0; ++i) {
            structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(0, 8, 0), "fat_tower_middle", rotation, true));

            for(Tuple<Rotation, BlockPos> tuple : StructureEndCityPieces.field_186208_h) {
               if (p_191086_6_.nextBoolean()) {
                  StructureEndCityPieces.CityTemplate structureendcitypieces$citytemplate1 = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, tuple.func_76340_b(), "bridge_end", rotation.func_185830_a(tuple.func_76341_a()), true));
                  StructureEndCityPieces.func_191088_b(p_191086_1_, StructureEndCityPieces.field_186207_g, p_191086_2_ + 1, structureendcitypieces$citytemplate1, (BlockPos)null, p_191086_5_, p_191086_6_);
               }
            }
         }

         StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-2, 8, -2), "fat_tower_top", rotation, true));
         return true;
      }
   };

   public static void func_186200_a() {
      MapGenStructureIO.func_143031_a(StructureEndCityPieces.CityTemplate.class, "ECP");
   }

   private static StructureEndCityPieces.CityTemplate func_191090_b(TemplateManager p_191090_0_, StructureEndCityPieces.CityTemplate p_191090_1_, BlockPos p_191090_2_, String p_191090_3_, Rotation p_191090_4_, boolean p_191090_5_) {
      StructureEndCityPieces.CityTemplate structureendcitypieces$citytemplate = new StructureEndCityPieces.CityTemplate(p_191090_0_, p_191090_3_, p_191090_1_.field_186178_c, p_191090_4_, p_191090_5_);
      BlockPos blockpos = p_191090_1_.field_186176_a.func_186262_a(p_191090_1_.field_186177_b, p_191090_2_, structureendcitypieces$citytemplate.field_186177_b, BlockPos.field_177992_a);
      structureendcitypieces$citytemplate.func_181138_a(blockpos.func_177958_n(), blockpos.func_177956_o(), blockpos.func_177952_p());
      return structureendcitypieces$citytemplate;
   }

   public static void func_191087_a(TemplateManager p_191087_0_, BlockPos p_191087_1_, Rotation p_191087_2_, List<StructureComponent> p_191087_3_, Random p_191087_4_) {
      field_186209_i.func_186184_a();
      field_186204_d.func_186184_a();
      field_186207_g.func_186184_a();
      field_186206_f.func_186184_a();
      StructureEndCityPieces.CityTemplate structureendcitypieces$citytemplate = func_189935_b(p_191087_3_, new StructureEndCityPieces.CityTemplate(p_191087_0_, "base_floor", p_191087_1_, p_191087_2_, true));
      structureendcitypieces$citytemplate = func_189935_b(p_191087_3_, func_191090_b(p_191087_0_, structureendcitypieces$citytemplate, new BlockPos(-1, 0, -1), "second_floor", p_191087_2_, false));
      structureendcitypieces$citytemplate = func_189935_b(p_191087_3_, func_191090_b(p_191087_0_, structureendcitypieces$citytemplate, new BlockPos(-1, 4, -1), "third_floor", p_191087_2_, false));
      structureendcitypieces$citytemplate = func_189935_b(p_191087_3_, func_191090_b(p_191087_0_, structureendcitypieces$citytemplate, new BlockPos(-1, 8, -1), "third_roof", p_191087_2_, true));
      func_191088_b(p_191087_0_, field_186206_f, 1, structureendcitypieces$citytemplate, (BlockPos)null, p_191087_3_, p_191087_4_);
   }

   private static StructureEndCityPieces.CityTemplate func_189935_b(List<StructureComponent> p_189935_0_, StructureEndCityPieces.CityTemplate p_189935_1_) {
      p_189935_0_.add(p_189935_1_);
      return p_189935_1_;
   }

   private static boolean func_191088_b(TemplateManager p_191088_0_, StructureEndCityPieces.IGenerator p_191088_1_, int p_191088_2_, StructureEndCityPieces.CityTemplate p_191088_3_, BlockPos p_191088_4_, List<StructureComponent> p_191088_5_, Random p_191088_6_) {
      if (p_191088_2_ > 8) {
         return false;
      } else {
         List<StructureComponent> list = Lists.<StructureComponent>newArrayList();
         if (p_191088_1_.func_191086_a(p_191088_0_, p_191088_2_, p_191088_3_, p_191088_4_, list, p_191088_6_)) {
            boolean flag = false;
            int i = p_191088_6_.nextInt();

            for(StructureComponent structurecomponent : list) {
               structurecomponent.field_74886_g = i;
               StructureComponent structurecomponent1 = StructureComponent.func_74883_a(p_191088_5_, structurecomponent.func_74874_b());
               if (structurecomponent1 != null && structurecomponent1.field_74886_g != p_191088_3_.field_74886_g) {
                  flag = true;
                  break;
               }
            }

            if (!flag) {
               p_191088_5_.addAll(list);
               return true;
            }
         }

         return false;
      }
   }

   public static class CityTemplate extends StructureComponentTemplate {
      private String field_186181_d;
      private Rotation field_186182_e;
      private boolean field_186183_f;

      public CityTemplate() {
      }

      public CityTemplate(TemplateManager p_i47214_1_, String p_i47214_2_, BlockPos p_i47214_3_, Rotation p_i47214_4_, boolean p_i47214_5_) {
         super(0);
         this.field_186181_d = p_i47214_2_;
         this.field_186178_c = p_i47214_3_;
         this.field_186182_e = p_i47214_4_;
         this.field_186183_f = p_i47214_5_;
         this.func_191085_a(p_i47214_1_);
      }

      private void func_191085_a(TemplateManager p_191085_1_) {
         Template template = p_191085_1_.func_186237_a((MinecraftServer)null, new ResourceLocation("endcity/" + this.field_186181_d));
         PlacementSettings placementsettings = (this.field_186183_f ? StructureEndCityPieces.field_186202_b : StructureEndCityPieces.field_186203_c).func_186217_a().func_186220_a(this.field_186182_e);
         this.func_186173_a(template, this.field_186178_c, placementsettings);
      }

      protected void func_143012_a(NBTTagCompound p_143012_1_) {
         super.func_143012_a(p_143012_1_);
         p_143012_1_.func_74778_a("Template", this.field_186181_d);
         p_143012_1_.func_74778_a("Rot", this.field_186182_e.name());
         p_143012_1_.func_74757_a("OW", this.field_186183_f);
      }

      protected void func_143011_b(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
         super.func_143011_b(p_143011_1_, p_143011_2_);
         this.field_186181_d = p_143011_1_.func_74779_i("Template");
         this.field_186182_e = Rotation.valueOf(p_143011_1_.func_74779_i("Rot"));
         this.field_186183_f = p_143011_1_.func_74767_n("OW");
         this.func_191085_a(p_143011_2_);
      }

      protected void func_186175_a(String p_186175_1_, BlockPos p_186175_2_, World p_186175_3_, Random p_186175_4_, StructureBoundingBox p_186175_5_) {
         if (p_186175_1_.startsWith("Chest")) {
            BlockPos blockpos = p_186175_2_.func_177977_b();
            if (p_186175_5_.func_175898_b(blockpos)) {
               TileEntity tileentity = p_186175_3_.func_175625_s(blockpos);
               if (tileentity instanceof TileEntityChest) {
                  ((TileEntityChest)tileentity).func_189404_a(LootTableList.field_186421_c, p_186175_4_.nextLong());
               }
            }
         } else if (p_186175_1_.startsWith("Sentry")) {
            EntityShulker entityshulker = new EntityShulker(p_186175_3_);
            entityshulker.func_70107_b((double)p_186175_2_.func_177958_n() + 0.5D, (double)p_186175_2_.func_177956_o() + 0.5D, (double)p_186175_2_.func_177952_p() + 0.5D);
            entityshulker.func_184694_g(p_186175_2_);
            p_186175_3_.func_72838_d(entityshulker);
         } else if (p_186175_1_.startsWith("Elytra")) {
            EntityItemFrame entityitemframe = new EntityItemFrame(p_186175_3_, p_186175_2_, this.field_186182_e.func_185831_a(EnumFacing.SOUTH));
            entityitemframe.func_82334_a(new ItemStack(Items.field_185160_cR));
            p_186175_3_.func_72838_d(entityitemframe);
         }

      }
   }

   interface IGenerator {
      void func_186184_a();

      boolean func_191086_a(TemplateManager var1, int var2, StructureEndCityPieces.CityTemplate var3, BlockPos var4, List<StructureComponent> var5, Random var6);
   }
}
