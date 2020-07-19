package net.minecraft.village;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.storage.WorldSavedData;

public class VillageCollection extends WorldSavedData {
   private World field_75556_a;
   private final List<BlockPos> field_75554_b = Lists.<BlockPos>newArrayList();
   private final List<VillageDoorInfo> field_75555_c = Lists.<VillageDoorInfo>newArrayList();
   private final List<Village> field_75552_d = Lists.<Village>newArrayList();
   private int field_75553_e;

   public VillageCollection(String p_i1677_1_) {
      super(p_i1677_1_);
   }

   public VillageCollection(World p_i1678_1_) {
      super(func_176062_a(p_i1678_1_.field_73011_w));
      this.field_75556_a = p_i1678_1_;
      this.func_76185_a();
   }

   public void func_82566_a(World p_82566_1_) {
      this.field_75556_a = p_82566_1_;

      for(Village village : this.field_75552_d) {
         village.func_82691_a(p_82566_1_);
      }

   }

   public void func_176060_a(BlockPos p_176060_1_) {
      if (this.field_75554_b.size() <= 64) {
         if (!this.func_176057_e(p_176060_1_)) {
            this.field_75554_b.add(p_176060_1_);
         }

      }
   }

   public void func_75544_a() {
      ++this.field_75553_e;

      for(Village village : this.field_75552_d) {
         village.func_75560_a(this.field_75553_e);
      }

      this.func_75549_c();
      this.func_75543_d();
      this.func_75545_e();
      if (this.field_75553_e % 400 == 0) {
         this.func_76185_a();
      }

   }

   private void func_75549_c() {
      Iterator<Village> iterator = this.field_75552_d.iterator();

      while(iterator.hasNext()) {
         Village village = iterator.next();
         if (village.func_75566_g()) {
            iterator.remove();
            this.func_76185_a();
         }
      }

   }

   public List<Village> func_75540_b() {
      return this.field_75552_d;
   }

   public Village func_176056_a(BlockPos p_176056_1_, int p_176056_2_) {
      Village village = null;
      double d0 = 3.4028234663852886E38D;

      for(Village village1 : this.field_75552_d) {
         double d1 = village1.func_180608_a().func_177951_i(p_176056_1_);
         if (d1 < d0) {
            float f = (float)(p_176056_2_ + village1.func_75568_b());
            if (d1 <= (double)(f * f)) {
               village = village1;
               d0 = d1;
            }
         }
      }

      return village;
   }

   private void func_75543_d() {
      if (!this.field_75554_b.isEmpty()) {
         this.func_180609_b(this.field_75554_b.remove(0));
      }
   }

   private void func_75545_e() {
      for(int i = 0; i < this.field_75555_c.size(); ++i) {
         VillageDoorInfo villagedoorinfo = this.field_75555_c.get(i);
         Village village = this.func_176056_a(villagedoorinfo.func_179852_d(), 32);
         if (village == null) {
            village = new Village(this.field_75556_a);
            this.field_75552_d.add(village);
            this.func_76185_a();
         }

         village.func_75576_a(villagedoorinfo);
      }

      this.field_75555_c.clear();
   }

   private void func_180609_b(BlockPos p_180609_1_) {
      int i = 16;
      int j = 4;
      int k = 16;

      for(int l = -16; l < 16; ++l) {
         for(int i1 = -4; i1 < 4; ++i1) {
            for(int j1 = -16; j1 < 16; ++j1) {
               BlockPos blockpos = p_180609_1_.func_177982_a(l, i1, j1);
               if (this.func_176058_f(blockpos)) {
                  VillageDoorInfo villagedoorinfo = this.func_176055_c(blockpos);
                  if (villagedoorinfo == null) {
                     this.func_176059_d(blockpos);
                  } else {
                     villagedoorinfo.func_179849_a(this.field_75553_e);
                  }
               }
            }
         }
      }

   }

   @Nullable
   private VillageDoorInfo func_176055_c(BlockPos p_176055_1_) {
      for(VillageDoorInfo villagedoorinfo : this.field_75555_c) {
         if (villagedoorinfo.func_179852_d().func_177958_n() == p_176055_1_.func_177958_n() && villagedoorinfo.func_179852_d().func_177952_p() == p_176055_1_.func_177952_p() && Math.abs(villagedoorinfo.func_179852_d().func_177956_o() - p_176055_1_.func_177956_o()) <= 1) {
            return villagedoorinfo;
         }
      }

      for(Village village : this.field_75552_d) {
         VillageDoorInfo villagedoorinfo1 = village.func_179864_e(p_176055_1_);
         if (villagedoorinfo1 != null) {
            return villagedoorinfo1;
         }
      }

      return null;
   }

   private void func_176059_d(BlockPos p_176059_1_) {
      EnumFacing enumfacing = BlockDoor.func_176517_h(this.field_75556_a, p_176059_1_);
      EnumFacing enumfacing1 = enumfacing.func_176734_d();
      int i = this.func_176061_a(p_176059_1_, enumfacing, 5);
      int j = this.func_176061_a(p_176059_1_, enumfacing1, i + 1);
      if (i != j) {
         this.field_75555_c.add(new VillageDoorInfo(p_176059_1_, i < j ? enumfacing : enumfacing1, this.field_75553_e));
      }

   }

   private int func_176061_a(BlockPos p_176061_1_, EnumFacing p_176061_2_, int p_176061_3_) {
      int i = 0;

      for(int j = 1; j <= 5; ++j) {
         if (this.field_75556_a.func_175678_i(p_176061_1_.func_177967_a(p_176061_2_, j))) {
            ++i;
            if (i >= p_176061_3_) {
               return i;
            }
         }
      }

      return i;
   }

   private boolean func_176057_e(BlockPos p_176057_1_) {
      for(BlockPos blockpos : this.field_75554_b) {
         if (blockpos.equals(p_176057_1_)) {
            return true;
         }
      }

      return false;
   }

   private boolean func_176058_f(BlockPos p_176058_1_) {
      IBlockState iblockstate = this.field_75556_a.func_180495_p(p_176058_1_);
      Block block = iblockstate.func_177230_c();
      if (block instanceof BlockDoor) {
         return iblockstate.func_185904_a() == Material.field_151575_d;
      } else {
         return false;
      }
   }

   public void func_76184_a(NBTTagCompound p_76184_1_) {
      this.field_75553_e = p_76184_1_.func_74762_e("Tick");
      NBTTagList nbttaglist = p_76184_1_.func_150295_c("Villages", 10);

      for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = nbttaglist.func_150305_b(i);
         Village village = new Village();
         village.func_82690_a(nbttagcompound);
         this.field_75552_d.add(village);
      }

   }

   public NBTTagCompound func_189551_b(NBTTagCompound p_189551_1_) {
      p_189551_1_.func_74768_a("Tick", this.field_75553_e);
      NBTTagList nbttaglist = new NBTTagList();

      for(Village village : this.field_75552_d) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         village.func_82689_b(nbttagcompound);
         nbttaglist.func_74742_a(nbttagcompound);
      }

      p_189551_1_.func_74782_a("Villages", nbttaglist);
      return p_189551_1_;
   }

   public static String func_176062_a(WorldProvider p_176062_0_) {
      return "villages" + p_176062_0_.func_186058_p().func_186067_c();
   }
}
