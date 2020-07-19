package net.minecraft.world.gen.structure.template;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.Mirror;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.Rotation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class Template {
   private final List<Template.BlockInfo> field_186270_a = Lists.<Template.BlockInfo>newArrayList();
   private final List<Template.EntityInfo> field_186271_b = Lists.<Template.EntityInfo>newArrayList();
   private BlockPos field_186272_c = BlockPos.field_177992_a;
   private String field_186273_d = "?";

   public BlockPos func_186259_a() {
      return this.field_186272_c;
   }

   public void func_186252_a(String p_186252_1_) {
      this.field_186273_d = p_186252_1_;
   }

   public String func_186261_b() {
      return this.field_186273_d;
   }

   public void func_186254_a(World p_186254_1_, BlockPos p_186254_2_, BlockPos p_186254_3_, boolean p_186254_4_, @Nullable Block p_186254_5_) {
      if (p_186254_3_.func_177958_n() >= 1 && p_186254_3_.func_177956_o() >= 1 && p_186254_3_.func_177952_p() >= 1) {
         BlockPos blockpos = p_186254_2_.func_177971_a(p_186254_3_).func_177982_a(-1, -1, -1);
         List<Template.BlockInfo> list = Lists.<Template.BlockInfo>newArrayList();
         List<Template.BlockInfo> list1 = Lists.<Template.BlockInfo>newArrayList();
         List<Template.BlockInfo> list2 = Lists.<Template.BlockInfo>newArrayList();
         BlockPos blockpos1 = new BlockPos(Math.min(p_186254_2_.func_177958_n(), blockpos.func_177958_n()), Math.min(p_186254_2_.func_177956_o(), blockpos.func_177956_o()), Math.min(p_186254_2_.func_177952_p(), blockpos.func_177952_p()));
         BlockPos blockpos2 = new BlockPos(Math.max(p_186254_2_.func_177958_n(), blockpos.func_177958_n()), Math.max(p_186254_2_.func_177956_o(), blockpos.func_177956_o()), Math.max(p_186254_2_.func_177952_p(), blockpos.func_177952_p()));
         this.field_186272_c = p_186254_3_;

         for(BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.func_177975_b(blockpos1, blockpos2)) {
            BlockPos blockpos3 = blockpos$mutableblockpos.func_177973_b(blockpos1);
            IBlockState iblockstate = p_186254_1_.func_180495_p(blockpos$mutableblockpos);
            if (p_186254_5_ == null || p_186254_5_ != iblockstate.func_177230_c()) {
               TileEntity tileentity = p_186254_1_.func_175625_s(blockpos$mutableblockpos);
               if (tileentity != null) {
                  NBTTagCompound nbttagcompound = tileentity.func_189515_b(new NBTTagCompound());
                  nbttagcompound.func_82580_o("x");
                  nbttagcompound.func_82580_o("y");
                  nbttagcompound.func_82580_o("z");
                  list1.add(new Template.BlockInfo(blockpos3, iblockstate, nbttagcompound));
               } else if (!iblockstate.func_185913_b() && !iblockstate.func_185917_h()) {
                  list2.add(new Template.BlockInfo(blockpos3, iblockstate, (NBTTagCompound)null));
               } else {
                  list.add(new Template.BlockInfo(blockpos3, iblockstate, (NBTTagCompound)null));
               }
            }
         }

         this.field_186270_a.clear();
         this.field_186270_a.addAll(list);
         this.field_186270_a.addAll(list1);
         this.field_186270_a.addAll(list2);
         if (p_186254_4_) {
            this.func_186255_a(p_186254_1_, blockpos1, blockpos2.func_177982_a(1, 1, 1));
         } else {
            this.field_186271_b.clear();
         }

      }
   }

   private void func_186255_a(World p_186255_1_, BlockPos p_186255_2_, BlockPos p_186255_3_) {
      List<Entity> list = p_186255_1_.<Entity>func_175647_a(Entity.class, new AxisAlignedBB(p_186255_2_, p_186255_3_), new Predicate<Entity>() {
         public boolean apply(@Nullable Entity p_apply_1_) {
            return !(p_apply_1_ instanceof EntityPlayer);
         }
      });
      this.field_186271_b.clear();

      for(Entity entity : list) {
         Vec3d vec3d = new Vec3d(entity.field_70165_t - (double)p_186255_2_.func_177958_n(), entity.field_70163_u - (double)p_186255_2_.func_177956_o(), entity.field_70161_v - (double)p_186255_2_.func_177952_p());
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         entity.func_70039_c(nbttagcompound);
         BlockPos blockpos;
         if (entity instanceof EntityPainting) {
            blockpos = ((EntityPainting)entity).func_174857_n().func_177973_b(p_186255_2_);
         } else {
            blockpos = new BlockPos(vec3d);
         }

         this.field_186271_b.add(new Template.EntityInfo(vec3d, blockpos, nbttagcompound));
      }

   }

   public Map<BlockPos, String> func_186258_a(BlockPos p_186258_1_, PlacementSettings p_186258_2_) {
      Map<BlockPos, String> map = Maps.<BlockPos, String>newHashMap();
      StructureBoundingBox structureboundingbox = p_186258_2_.func_186213_g();

      for(Template.BlockInfo template$blockinfo : this.field_186270_a) {
         BlockPos blockpos = func_186266_a(p_186258_2_, template$blockinfo.field_186242_a).func_177971_a(p_186258_1_);
         if (structureboundingbox == null || structureboundingbox.func_175898_b(blockpos)) {
            IBlockState iblockstate = template$blockinfo.field_186243_b;
            if (iblockstate.func_177230_c() == Blocks.field_185779_df && template$blockinfo.field_186244_c != null) {
               TileEntityStructure.Mode tileentitystructure$mode = TileEntityStructure.Mode.valueOf(template$blockinfo.field_186244_c.func_74779_i("mode"));
               if (tileentitystructure$mode == TileEntityStructure.Mode.DATA) {
                  map.put(blockpos, template$blockinfo.field_186244_c.func_74779_i("metadata"));
               }
            }
         }
      }

      return map;
   }

   public BlockPos func_186262_a(PlacementSettings p_186262_1_, BlockPos p_186262_2_, PlacementSettings p_186262_3_, BlockPos p_186262_4_) {
      BlockPos blockpos = func_186266_a(p_186262_1_, p_186262_2_);
      BlockPos blockpos1 = func_186266_a(p_186262_3_, p_186262_4_);
      return blockpos.func_177973_b(blockpos1);
   }

   public static BlockPos func_186266_a(PlacementSettings p_186266_0_, BlockPos p_186266_1_) {
      return func_186268_a(p_186266_1_, p_186266_0_.func_186212_b(), p_186266_0_.func_186215_c());
   }

   public void func_186260_a(World p_186260_1_, BlockPos p_186260_2_, PlacementSettings p_186260_3_) {
      p_186260_3_.func_186224_i();
      this.func_186253_b(p_186260_1_, p_186260_2_, p_186260_3_);
   }

   public void func_186253_b(World p_186253_1_, BlockPos p_186253_2_, PlacementSettings p_186253_3_) {
      this.func_189960_a(p_186253_1_, p_186253_2_, new BlockRotationProcessor(p_186253_2_, p_186253_3_), p_186253_3_, 2);
   }

   public void func_189962_a(World p_189962_1_, BlockPos p_189962_2_, PlacementSettings p_189962_3_, int p_189962_4_) {
      this.func_189960_a(p_189962_1_, p_189962_2_, new BlockRotationProcessor(p_189962_2_, p_189962_3_), p_189962_3_, p_189962_4_);
   }

   public void func_189960_a(World p_189960_1_, BlockPos p_189960_2_, @Nullable ITemplateProcessor p_189960_3_, PlacementSettings p_189960_4_, int p_189960_5_) {
      if ((!this.field_186270_a.isEmpty() || !p_189960_4_.func_186221_e() && !this.field_186271_b.isEmpty()) && this.field_186272_c.func_177958_n() >= 1 && this.field_186272_c.func_177956_o() >= 1 && this.field_186272_c.func_177952_p() >= 1) {
         Block block = p_189960_4_.func_186219_f();
         StructureBoundingBox structureboundingbox = p_189960_4_.func_186213_g();

         for(Template.BlockInfo template$blockinfo : this.field_186270_a) {
            BlockPos blockpos = func_186266_a(p_189960_4_, template$blockinfo.field_186242_a).func_177971_a(p_189960_2_);
            Template.BlockInfo template$blockinfo1 = p_189960_3_ != null ? p_189960_3_.func_189943_a(p_189960_1_, blockpos, template$blockinfo) : template$blockinfo;
            if (template$blockinfo1 != null) {
               Block block1 = template$blockinfo1.field_186243_b.func_177230_c();
               if ((block == null || block != block1) && (!p_189960_4_.func_186227_h() || block1 != Blocks.field_185779_df) && (structureboundingbox == null || structureboundingbox.func_175898_b(blockpos))) {
                  IBlockState iblockstate = template$blockinfo1.field_186243_b.func_185902_a(p_189960_4_.func_186212_b());
                  IBlockState iblockstate1 = iblockstate.func_185907_a(p_189960_4_.func_186215_c());
                  if (template$blockinfo1.field_186244_c != null) {
                     TileEntity tileentity = p_189960_1_.func_175625_s(blockpos);
                     if (tileentity != null) {
                        if (tileentity instanceof IInventory) {
                           ((IInventory)tileentity).func_174888_l();
                        }

                        p_189960_1_.func_180501_a(blockpos, Blocks.field_180401_cv.func_176223_P(), 4);
                     }
                  }

                  if (p_189960_1_.func_180501_a(blockpos, iblockstate1, p_189960_5_) && template$blockinfo1.field_186244_c != null) {
                     TileEntity tileentity2 = p_189960_1_.func_175625_s(blockpos);
                     if (tileentity2 != null) {
                        template$blockinfo1.field_186244_c.func_74768_a("x", blockpos.func_177958_n());
                        template$blockinfo1.field_186244_c.func_74768_a("y", blockpos.func_177956_o());
                        template$blockinfo1.field_186244_c.func_74768_a("z", blockpos.func_177952_p());
                        tileentity2.func_145839_a(template$blockinfo1.field_186244_c);
                        tileentity2.func_189668_a(p_189960_4_.func_186212_b());
                        tileentity2.func_189667_a(p_189960_4_.func_186215_c());
                     }
                  }
               }
            }
         }

         for(Template.BlockInfo template$blockinfo2 : this.field_186270_a) {
            if (block == null || block != template$blockinfo2.field_186243_b.func_177230_c()) {
               BlockPos blockpos1 = func_186266_a(p_189960_4_, template$blockinfo2.field_186242_a).func_177971_a(p_189960_2_);
               if (structureboundingbox == null || structureboundingbox.func_175898_b(blockpos1)) {
                  p_189960_1_.func_175722_b(blockpos1, template$blockinfo2.field_186243_b.func_177230_c(), false);
                  if (template$blockinfo2.field_186244_c != null) {
                     TileEntity tileentity1 = p_189960_1_.func_175625_s(blockpos1);
                     if (tileentity1 != null) {
                        tileentity1.func_70296_d();
                     }
                  }
               }
            }
         }

         if (!p_189960_4_.func_186221_e()) {
            this.func_186263_a(p_189960_1_, p_189960_2_, p_189960_4_.func_186212_b(), p_189960_4_.func_186215_c(), structureboundingbox);
         }

      }
   }

   private void func_186263_a(World p_186263_1_, BlockPos p_186263_2_, Mirror p_186263_3_, Rotation p_186263_4_, @Nullable StructureBoundingBox p_186263_5_) {
      for(Template.EntityInfo template$entityinfo : this.field_186271_b) {
         BlockPos blockpos = func_186268_a(template$entityinfo.field_186248_b, p_186263_3_, p_186263_4_).func_177971_a(p_186263_2_);
         if (p_186263_5_ == null || p_186263_5_.func_175898_b(blockpos)) {
            NBTTagCompound nbttagcompound = template$entityinfo.field_186249_c;
            Vec3d vec3d = func_186269_a(template$entityinfo.field_186247_a, p_186263_3_, p_186263_4_);
            Vec3d vec3d1 = vec3d.func_72441_c((double)p_186263_2_.func_177958_n(), (double)p_186263_2_.func_177956_o(), (double)p_186263_2_.func_177952_p());
            NBTTagList nbttaglist = new NBTTagList();
            nbttaglist.func_74742_a(new NBTTagDouble(vec3d1.field_72450_a));
            nbttaglist.func_74742_a(new NBTTagDouble(vec3d1.field_72448_b));
            nbttaglist.func_74742_a(new NBTTagDouble(vec3d1.field_72449_c));
            nbttagcompound.func_74782_a("Pos", nbttaglist);
            nbttagcompound.func_186854_a("UUID", UUID.randomUUID());

            Entity entity;
            try {
               entity = EntityList.func_75615_a(nbttagcompound, p_186263_1_);
            } catch (Exception var15) {
               entity = null;
            }

            if (entity != null) {
               float f = entity.func_184217_a(p_186263_3_);
               f = f + (entity.field_70177_z - entity.func_184229_a(p_186263_4_));
               entity.func_70012_b(vec3d1.field_72450_a, vec3d1.field_72448_b, vec3d1.field_72449_c, f, entity.field_70125_A);
               p_186263_1_.func_72838_d(entity);
            }
         }
      }

   }

   public BlockPos func_186257_a(Rotation p_186257_1_) {
      switch(p_186257_1_) {
      case COUNTERCLOCKWISE_90:
      case CLOCKWISE_90:
         return new BlockPos(this.field_186272_c.func_177952_p(), this.field_186272_c.func_177956_o(), this.field_186272_c.func_177958_n());
      default:
         return this.field_186272_c;
      }
   }

   private static BlockPos func_186268_a(BlockPos p_186268_0_, Mirror p_186268_1_, Rotation p_186268_2_) {
      int i = p_186268_0_.func_177958_n();
      int j = p_186268_0_.func_177956_o();
      int k = p_186268_0_.func_177952_p();
      boolean flag = true;
      switch(p_186268_1_) {
      case LEFT_RIGHT:
         k = -k;
         break;
      case FRONT_BACK:
         i = -i;
         break;
      default:
         flag = false;
      }

      switch(p_186268_2_) {
      case COUNTERCLOCKWISE_90:
         return new BlockPos(k, j, -i);
      case CLOCKWISE_90:
         return new BlockPos(-k, j, i);
      case CLOCKWISE_180:
         return new BlockPos(-i, j, -k);
      default:
         return flag ? new BlockPos(i, j, k) : p_186268_0_;
      }
   }

   private static Vec3d func_186269_a(Vec3d p_186269_0_, Mirror p_186269_1_, Rotation p_186269_2_) {
      double d0 = p_186269_0_.field_72450_a;
      double d1 = p_186269_0_.field_72448_b;
      double d2 = p_186269_0_.field_72449_c;
      boolean flag = true;
      switch(p_186269_1_) {
      case LEFT_RIGHT:
         d2 = 1.0D - d2;
         break;
      case FRONT_BACK:
         d0 = 1.0D - d0;
         break;
      default:
         flag = false;
      }

      switch(p_186269_2_) {
      case COUNTERCLOCKWISE_90:
         return new Vec3d(d2, d1, 1.0D - d0);
      case CLOCKWISE_90:
         return new Vec3d(1.0D - d2, d1, d0);
      case CLOCKWISE_180:
         return new Vec3d(1.0D - d0, d1, 1.0D - d2);
      default:
         return flag ? new Vec3d(d0, d1, d2) : p_186269_0_;
      }
   }

   public BlockPos func_189961_a(BlockPos p_189961_1_, Mirror p_189961_2_, Rotation p_189961_3_) {
      return func_191157_a(p_189961_1_, p_189961_2_, p_189961_3_, this.func_186259_a().func_177958_n(), this.func_186259_a().func_177952_p());
   }

   public static BlockPos func_191157_a(BlockPos p_191157_0_, Mirror p_191157_1_, Rotation p_191157_2_, int p_191157_3_, int p_191157_4_) {
      --p_191157_3_;
      --p_191157_4_;
      int i = p_191157_1_ == Mirror.FRONT_BACK ? p_191157_3_ : 0;
      int j = p_191157_1_ == Mirror.LEFT_RIGHT ? p_191157_4_ : 0;
      BlockPos blockpos = p_191157_0_;
      switch(p_191157_2_) {
      case COUNTERCLOCKWISE_90:
         blockpos = p_191157_0_.func_177982_a(j, 0, p_191157_3_ - i);
         break;
      case CLOCKWISE_90:
         blockpos = p_191157_0_.func_177982_a(p_191157_4_ - j, 0, i);
         break;
      case CLOCKWISE_180:
         blockpos = p_191157_0_.func_177982_a(p_191157_3_ - i, 0, p_191157_4_ - j);
         break;
      case NONE:
         blockpos = p_191157_0_.func_177982_a(i, 0, j);
      }

      return blockpos;
   }

   public static void func_191158_a(DataFixer p_191158_0_) {
      p_191158_0_.func_188258_a(FixTypes.STRUCTURE, new IDataWalker() {
         public NBTTagCompound func_188266_a(IDataFixer p_188266_1_, NBTTagCompound p_188266_2_, int p_188266_3_) {
            if (p_188266_2_.func_150297_b("entities", 9)) {
               NBTTagList nbttaglist = p_188266_2_.func_150295_c("entities", 10);

               for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
                  NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.func_179238_g(i);
                  if (nbttagcompound.func_150297_b("nbt", 10)) {
                     nbttagcompound.func_74782_a("nbt", p_188266_1_.func_188251_a(FixTypes.ENTITY, nbttagcompound.func_74775_l("nbt"), p_188266_3_));
                  }
               }
            }

            if (p_188266_2_.func_150297_b("blocks", 9)) {
               NBTTagList nbttaglist1 = p_188266_2_.func_150295_c("blocks", 10);

               for(int j = 0; j < nbttaglist1.func_74745_c(); ++j) {
                  NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist1.func_179238_g(j);
                  if (nbttagcompound1.func_150297_b("nbt", 10)) {
                     nbttagcompound1.func_74782_a("nbt", p_188266_1_.func_188251_a(FixTypes.BLOCK_ENTITY, nbttagcompound1.func_74775_l("nbt"), p_188266_3_));
                  }
               }
            }

            return p_188266_2_;
         }
      });
   }

   public NBTTagCompound func_189552_a(NBTTagCompound p_189552_1_) {
      Template.BasicPalette template$basicpalette = new Template.BasicPalette();
      NBTTagList nbttaglist = new NBTTagList();

      for(Template.BlockInfo template$blockinfo : this.field_186270_a) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.func_74782_a("pos", this.func_186267_a(template$blockinfo.field_186242_a.func_177958_n(), template$blockinfo.field_186242_a.func_177956_o(), template$blockinfo.field_186242_a.func_177952_p()));
         nbttagcompound.func_74768_a("state", template$basicpalette.func_189954_a(template$blockinfo.field_186243_b));
         if (template$blockinfo.field_186244_c != null) {
            nbttagcompound.func_74782_a("nbt", template$blockinfo.field_186244_c);
         }

         nbttaglist.func_74742_a(nbttagcompound);
      }

      NBTTagList nbttaglist1 = new NBTTagList();

      for(Template.EntityInfo template$entityinfo : this.field_186271_b) {
         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
         nbttagcompound1.func_74782_a("pos", this.func_186264_a(template$entityinfo.field_186247_a.field_72450_a, template$entityinfo.field_186247_a.field_72448_b, template$entityinfo.field_186247_a.field_72449_c));
         nbttagcompound1.func_74782_a("blockPos", this.func_186267_a(template$entityinfo.field_186248_b.func_177958_n(), template$entityinfo.field_186248_b.func_177956_o(), template$entityinfo.field_186248_b.func_177952_p()));
         if (template$entityinfo.field_186249_c != null) {
            nbttagcompound1.func_74782_a("nbt", template$entityinfo.field_186249_c);
         }

         nbttaglist1.func_74742_a(nbttagcompound1);
      }

      NBTTagList nbttaglist2 = new NBTTagList();

      for(IBlockState iblockstate : template$basicpalette) {
         nbttaglist2.func_74742_a(NBTUtil.func_190009_a(new NBTTagCompound(), iblockstate));
      }

      p_189552_1_.func_74782_a("palette", nbttaglist2);
      p_189552_1_.func_74782_a("blocks", nbttaglist);
      p_189552_1_.func_74782_a("entities", nbttaglist1);
      p_189552_1_.func_74782_a("size", this.func_186267_a(this.field_186272_c.func_177958_n(), this.field_186272_c.func_177956_o(), this.field_186272_c.func_177952_p()));
      p_189552_1_.func_74778_a("author", this.field_186273_d);
      p_189552_1_.func_74768_a("DataVersion", 1139);
      return p_189552_1_;
   }

   public void func_186256_b(NBTTagCompound p_186256_1_) {
      this.field_186270_a.clear();
      this.field_186271_b.clear();
      NBTTagList nbttaglist = p_186256_1_.func_150295_c("size", 3);
      this.field_186272_c = new BlockPos(nbttaglist.func_186858_c(0), nbttaglist.func_186858_c(1), nbttaglist.func_186858_c(2));
      this.field_186273_d = p_186256_1_.func_74779_i("author");
      Template.BasicPalette template$basicpalette = new Template.BasicPalette();
      NBTTagList nbttaglist1 = p_186256_1_.func_150295_c("palette", 10);

      for(int i = 0; i < nbttaglist1.func_74745_c(); ++i) {
         template$basicpalette.func_189956_a(NBTUtil.func_190008_d(nbttaglist1.func_150305_b(i)), i);
      }

      NBTTagList nbttaglist3 = p_186256_1_.func_150295_c("blocks", 10);

      for(int j = 0; j < nbttaglist3.func_74745_c(); ++j) {
         NBTTagCompound nbttagcompound = nbttaglist3.func_150305_b(j);
         NBTTagList nbttaglist2 = nbttagcompound.func_150295_c("pos", 3);
         BlockPos blockpos = new BlockPos(nbttaglist2.func_186858_c(0), nbttaglist2.func_186858_c(1), nbttaglist2.func_186858_c(2));
         IBlockState iblockstate = template$basicpalette.func_189955_a(nbttagcompound.func_74762_e("state"));
         NBTTagCompound nbttagcompound1;
         if (nbttagcompound.func_74764_b("nbt")) {
            nbttagcompound1 = nbttagcompound.func_74775_l("nbt");
         } else {
            nbttagcompound1 = null;
         }

         this.field_186270_a.add(new Template.BlockInfo(blockpos, iblockstate, nbttagcompound1));
      }

      NBTTagList nbttaglist4 = p_186256_1_.func_150295_c("entities", 10);

      for(int k = 0; k < nbttaglist4.func_74745_c(); ++k) {
         NBTTagCompound nbttagcompound3 = nbttaglist4.func_150305_b(k);
         NBTTagList nbttaglist5 = nbttagcompound3.func_150295_c("pos", 6);
         Vec3d vec3d = new Vec3d(nbttaglist5.func_150309_d(0), nbttaglist5.func_150309_d(1), nbttaglist5.func_150309_d(2));
         NBTTagList nbttaglist6 = nbttagcompound3.func_150295_c("blockPos", 3);
         BlockPos blockpos1 = new BlockPos(nbttaglist6.func_186858_c(0), nbttaglist6.func_186858_c(1), nbttaglist6.func_186858_c(2));
         if (nbttagcompound3.func_74764_b("nbt")) {
            NBTTagCompound nbttagcompound2 = nbttagcompound3.func_74775_l("nbt");
            this.field_186271_b.add(new Template.EntityInfo(vec3d, blockpos1, nbttagcompound2));
         }
      }

   }

   private NBTTagList func_186267_a(int... p_186267_1_) {
      NBTTagList nbttaglist = new NBTTagList();

      for(int i : p_186267_1_) {
         nbttaglist.func_74742_a(new NBTTagInt(i));
      }

      return nbttaglist;
   }

   private NBTTagList func_186264_a(double... p_186264_1_) {
      NBTTagList nbttaglist = new NBTTagList();

      for(double d0 : p_186264_1_) {
         nbttaglist.func_74742_a(new NBTTagDouble(d0));
      }

      return nbttaglist;
   }

   static class BasicPalette implements Iterable<IBlockState> {
      public static final IBlockState field_189957_a = Blocks.field_150350_a.func_176223_P();
      final ObjectIntIdentityMap<IBlockState> field_189958_b;
      private int field_189959_c;

      private BasicPalette() {
         this.field_189958_b = new ObjectIntIdentityMap<IBlockState>(16);
      }

      public int func_189954_a(IBlockState p_189954_1_) {
         int i = this.field_189958_b.func_148747_b(p_189954_1_);
         if (i == -1) {
            i = this.field_189959_c++;
            this.field_189958_b.func_148746_a(p_189954_1_, i);
         }

         return i;
      }

      @Nullable
      public IBlockState func_189955_a(int p_189955_1_) {
         IBlockState iblockstate = this.field_189958_b.func_148745_a(p_189955_1_);
         return iblockstate == null ? field_189957_a : iblockstate;
      }

      public Iterator<IBlockState> iterator() {
         return this.field_189958_b.iterator();
      }

      public void func_189956_a(IBlockState p_189956_1_, int p_189956_2_) {
         this.field_189958_b.func_148746_a(p_189956_1_, p_189956_2_);
      }
   }

   public static class BlockInfo {
      public final BlockPos field_186242_a;
      public final IBlockState field_186243_b;
      public final NBTTagCompound field_186244_c;

      public BlockInfo(BlockPos p_i47042_1_, IBlockState p_i47042_2_, @Nullable NBTTagCompound p_i47042_3_) {
         this.field_186242_a = p_i47042_1_;
         this.field_186243_b = p_i47042_2_;
         this.field_186244_c = p_i47042_3_;
      }
   }

   public static class EntityInfo {
      public final Vec3d field_186247_a;
      public final BlockPos field_186248_b;
      public final NBTTagCompound field_186249_c;

      public EntityInfo(Vec3d p_i47101_1_, BlockPos p_i47101_2_, NBTTagCompound p_i47101_3_) {
         this.field_186247_a = p_i47101_1_;
         this.field_186248_b = p_i47101_2_;
         this.field_186249_c = p_i47101_3_;
      }
   }
}
