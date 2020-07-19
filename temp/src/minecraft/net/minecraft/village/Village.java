package net.minecraft.village;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Village {
   private World field_75586_a;
   private final List<VillageDoorInfo> field_75584_b = Lists.<VillageDoorInfo>newArrayList();
   private BlockPos field_75585_c = BlockPos.field_177992_a;
   private BlockPos field_75582_d = BlockPos.field_177992_a;
   private int field_75583_e;
   private int field_75580_f;
   private int field_75581_g;
   private int field_75588_h;
   private int field_82694_i;
   private final Map<String, Integer> field_82693_j = Maps.<String, Integer>newHashMap();
   private final List<Village.VillageAggressor> field_75589_i = Lists.<Village.VillageAggressor>newArrayList();
   private int field_75587_j;

   public Village() {
   }

   public Village(World p_i1675_1_) {
      this.field_75586_a = p_i1675_1_;
   }

   public void func_82691_a(World p_82691_1_) {
      this.field_75586_a = p_82691_1_;
   }

   public void func_75560_a(int p_75560_1_) {
      this.field_75581_g = p_75560_1_;
      this.func_75557_k();
      this.func_75565_j();
      if (p_75560_1_ % 20 == 0) {
         this.func_75572_i();
      }

      if (p_75560_1_ % 30 == 0) {
         this.func_75579_h();
      }

      int i = this.field_75588_h / 10;
      if (this.field_75587_j < i && this.field_75584_b.size() > 20 && this.field_75586_a.field_73012_v.nextInt(7000) == 0) {
         Vec3d vec3d = this.func_179862_a(this.field_75582_d, 2, 4, 2);
         if (vec3d != null) {
            EntityIronGolem entityirongolem = new EntityIronGolem(this.field_75586_a);
            entityirongolem.func_70107_b(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c);
            this.field_75586_a.func_72838_d(entityirongolem);
            ++this.field_75587_j;
         }
      }

   }

   private Vec3d func_179862_a(BlockPos p_179862_1_, int p_179862_2_, int p_179862_3_, int p_179862_4_) {
      for(int i = 0; i < 10; ++i) {
         BlockPos blockpos = p_179862_1_.func_177982_a(this.field_75586_a.field_73012_v.nextInt(16) - 8, this.field_75586_a.field_73012_v.nextInt(6) - 3, this.field_75586_a.field_73012_v.nextInt(16) - 8);
         if (this.func_179866_a(blockpos) && this.func_179861_a(new BlockPos(p_179862_2_, p_179862_3_, p_179862_4_), blockpos)) {
            return new Vec3d((double)blockpos.func_177958_n(), (double)blockpos.func_177956_o(), (double)blockpos.func_177952_p());
         }
      }

      return null;
   }

   private boolean func_179861_a(BlockPos p_179861_1_, BlockPos p_179861_2_) {
      if (!this.field_75586_a.func_180495_p(p_179861_2_.func_177977_b()).func_185896_q()) {
         return false;
      } else {
         int i = p_179861_2_.func_177958_n() - p_179861_1_.func_177958_n() / 2;
         int j = p_179861_2_.func_177952_p() - p_179861_1_.func_177952_p() / 2;

         for(int k = i; k < i + p_179861_1_.func_177958_n(); ++k) {
            for(int l = p_179861_2_.func_177956_o(); l < p_179861_2_.func_177956_o() + p_179861_1_.func_177956_o(); ++l) {
               for(int i1 = j; i1 < j + p_179861_1_.func_177952_p(); ++i1) {
                  if (this.field_75586_a.func_180495_p(new BlockPos(k, l, i1)).func_185915_l()) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   private void func_75579_h() {
      List<EntityIronGolem> list = this.field_75586_a.<EntityIronGolem>func_72872_a(EntityIronGolem.class, new AxisAlignedBB((double)(this.field_75582_d.func_177958_n() - this.field_75583_e), (double)(this.field_75582_d.func_177956_o() - 4), (double)(this.field_75582_d.func_177952_p() - this.field_75583_e), (double)(this.field_75582_d.func_177958_n() + this.field_75583_e), (double)(this.field_75582_d.func_177956_o() + 4), (double)(this.field_75582_d.func_177952_p() + this.field_75583_e)));
      this.field_75587_j = list.size();
   }

   private void func_75572_i() {
      List<EntityVillager> list = this.field_75586_a.<EntityVillager>func_72872_a(EntityVillager.class, new AxisAlignedBB((double)(this.field_75582_d.func_177958_n() - this.field_75583_e), (double)(this.field_75582_d.func_177956_o() - 4), (double)(this.field_75582_d.func_177952_p() - this.field_75583_e), (double)(this.field_75582_d.func_177958_n() + this.field_75583_e), (double)(this.field_75582_d.func_177956_o() + 4), (double)(this.field_75582_d.func_177952_p() + this.field_75583_e)));
      this.field_75588_h = list.size();
      if (this.field_75588_h == 0) {
         this.field_82693_j.clear();
      }

   }

   public BlockPos func_180608_a() {
      return this.field_75582_d;
   }

   public int func_75568_b() {
      return this.field_75583_e;
   }

   public int func_75567_c() {
      return this.field_75584_b.size();
   }

   public int func_75561_d() {
      return this.field_75581_g - this.field_75580_f;
   }

   public int func_75562_e() {
      return this.field_75588_h;
   }

   public boolean func_179866_a(BlockPos p_179866_1_) {
      return this.field_75582_d.func_177951_i(p_179866_1_) < (double)(this.field_75583_e * this.field_75583_e);
   }

   public List<VillageDoorInfo> func_75558_f() {
      return this.field_75584_b;
   }

   public VillageDoorInfo func_179865_b(BlockPos p_179865_1_) {
      VillageDoorInfo villagedoorinfo = null;
      int i = Integer.MAX_VALUE;

      for(VillageDoorInfo villagedoorinfo1 : this.field_75584_b) {
         int j = villagedoorinfo1.func_179848_a(p_179865_1_);
         if (j < i) {
            villagedoorinfo = villagedoorinfo1;
            i = j;
         }
      }

      return villagedoorinfo;
   }

   public VillageDoorInfo func_179863_c(BlockPos p_179863_1_) {
      VillageDoorInfo villagedoorinfo = null;
      int i = Integer.MAX_VALUE;

      for(VillageDoorInfo villagedoorinfo1 : this.field_75584_b) {
         int j = villagedoorinfo1.func_179848_a(p_179863_1_);
         if (j > 256) {
            j = j * 1000;
         } else {
            j = villagedoorinfo1.func_75468_f();
         }

         if (j < i) {
            BlockPos blockpos = villagedoorinfo1.func_179852_d();
            EnumFacing enumfacing = villagedoorinfo1.func_188567_j();
            if (this.field_75586_a.func_180495_p(blockpos.func_177967_a(enumfacing, 1)).func_177230_c().func_176205_b(this.field_75586_a, blockpos.func_177967_a(enumfacing, 1)) && this.field_75586_a.func_180495_p(blockpos.func_177967_a(enumfacing, -1)).func_177230_c().func_176205_b(this.field_75586_a, blockpos.func_177967_a(enumfacing, -1)) && this.field_75586_a.func_180495_p(blockpos.func_177984_a().func_177967_a(enumfacing, 1)).func_177230_c().func_176205_b(this.field_75586_a, blockpos.func_177984_a().func_177967_a(enumfacing, 1)) && this.field_75586_a.func_180495_p(blockpos.func_177984_a().func_177967_a(enumfacing, -1)).func_177230_c().func_176205_b(this.field_75586_a, blockpos.func_177984_a().func_177967_a(enumfacing, -1))) {
               villagedoorinfo = villagedoorinfo1;
               i = j;
            }
         }
      }

      return villagedoorinfo;
   }

   @Nullable
   public VillageDoorInfo func_179864_e(BlockPos p_179864_1_) {
      if (this.field_75582_d.func_177951_i(p_179864_1_) > (double)(this.field_75583_e * this.field_75583_e)) {
         return null;
      } else {
         for(VillageDoorInfo villagedoorinfo : this.field_75584_b) {
            if (villagedoorinfo.func_179852_d().func_177958_n() == p_179864_1_.func_177958_n() && villagedoorinfo.func_179852_d().func_177952_p() == p_179864_1_.func_177952_p() && Math.abs(villagedoorinfo.func_179852_d().func_177956_o() - p_179864_1_.func_177956_o()) <= 1) {
               return villagedoorinfo;
            }
         }

         return null;
      }
   }

   public void func_75576_a(VillageDoorInfo p_75576_1_) {
      this.field_75584_b.add(p_75576_1_);
      this.field_75585_c = this.field_75585_c.func_177971_a(p_75576_1_.func_179852_d());
      this.func_75573_l();
      this.field_75580_f = p_75576_1_.func_75473_b();
   }

   public boolean func_75566_g() {
      return this.field_75584_b.isEmpty();
   }

   public void func_75575_a(EntityLivingBase p_75575_1_) {
      for(Village.VillageAggressor village$villageaggressor : this.field_75589_i) {
         if (village$villageaggressor.field_75592_a == p_75575_1_) {
            village$villageaggressor.field_75590_b = this.field_75581_g;
            return;
         }
      }

      this.field_75589_i.add(new Village.VillageAggressor(p_75575_1_, this.field_75581_g));
   }

   @Nullable
   public EntityLivingBase func_75571_b(EntityLivingBase p_75571_1_) {
      double d0 = Double.MAX_VALUE;
      Village.VillageAggressor village$villageaggressor = null;

      for(int i = 0; i < this.field_75589_i.size(); ++i) {
         Village.VillageAggressor village$villageaggressor1 = this.field_75589_i.get(i);
         double d1 = village$villageaggressor1.field_75592_a.func_70068_e(p_75571_1_);
         if (d1 <= d0) {
            village$villageaggressor = village$villageaggressor1;
            d0 = d1;
         }
      }

      return village$villageaggressor == null ? null : village$villageaggressor.field_75592_a;
   }

   public EntityPlayer func_82685_c(EntityLivingBase p_82685_1_) {
      double d0 = Double.MAX_VALUE;
      EntityPlayer entityplayer = null;

      for(String s : this.field_82693_j.keySet()) {
         if (this.func_82687_d(s)) {
            EntityPlayer entityplayer1 = this.field_75586_a.func_72924_a(s);
            if (entityplayer1 != null) {
               double d1 = entityplayer1.func_70068_e(p_82685_1_);
               if (d1 <= d0) {
                  entityplayer = entityplayer1;
                  d0 = d1;
               }
            }
         }
      }

      return entityplayer;
   }

   private void func_75565_j() {
      Iterator<Village.VillageAggressor> iterator = this.field_75589_i.iterator();

      while(iterator.hasNext()) {
         Village.VillageAggressor village$villageaggressor = iterator.next();
         if (!village$villageaggressor.field_75592_a.func_70089_S() || Math.abs(this.field_75581_g - village$villageaggressor.field_75590_b) > 300) {
            iterator.remove();
         }
      }

   }

   private void func_75557_k() {
      boolean flag = false;
      boolean flag1 = this.field_75586_a.field_73012_v.nextInt(50) == 0;
      Iterator<VillageDoorInfo> iterator = this.field_75584_b.iterator();

      while(iterator.hasNext()) {
         VillageDoorInfo villagedoorinfo = iterator.next();
         if (flag1) {
            villagedoorinfo.func_75466_d();
         }

         if (!this.func_179860_f(villagedoorinfo.func_179852_d()) || Math.abs(this.field_75581_g - villagedoorinfo.func_75473_b()) > 1200) {
            this.field_75585_c = this.field_75585_c.func_177973_b(villagedoorinfo.func_179852_d());
            flag = true;
            villagedoorinfo.func_179853_a(true);
            iterator.remove();
         }
      }

      if (flag) {
         this.func_75573_l();
      }

   }

   private boolean func_179860_f(BlockPos p_179860_1_) {
      IBlockState iblockstate = this.field_75586_a.func_180495_p(p_179860_1_);
      Block block = iblockstate.func_177230_c();
      if (block instanceof BlockDoor) {
         return iblockstate.func_185904_a() == Material.field_151575_d;
      } else {
         return false;
      }
   }

   private void func_75573_l() {
      int i = this.field_75584_b.size();
      if (i == 0) {
         this.field_75582_d = BlockPos.field_177992_a;
         this.field_75583_e = 0;
      } else {
         this.field_75582_d = new BlockPos(this.field_75585_c.func_177958_n() / i, this.field_75585_c.func_177956_o() / i, this.field_75585_c.func_177952_p() / i);
         int j = 0;

         for(VillageDoorInfo villagedoorinfo : this.field_75584_b) {
            j = Math.max(villagedoorinfo.func_179848_a(this.field_75582_d), j);
         }

         this.field_75583_e = Math.max(32, (int)Math.sqrt((double)j) + 1);
      }
   }

   public int func_82684_a(String p_82684_1_) {
      Integer integer = this.field_82693_j.get(p_82684_1_);
      return integer == null ? 0 : integer.intValue();
   }

   public int func_82688_a(String p_82688_1_, int p_82688_2_) {
      int i = this.func_82684_a(p_82688_1_);
      int j = MathHelper.func_76125_a(i + p_82688_2_, -30, 10);
      this.field_82693_j.put(p_82688_1_, Integer.valueOf(j));
      return j;
   }

   public boolean func_82687_d(String p_82687_1_) {
      return this.func_82684_a(p_82687_1_) <= -15;
   }

   public void func_82690_a(NBTTagCompound p_82690_1_) {
      this.field_75588_h = p_82690_1_.func_74762_e("PopSize");
      this.field_75583_e = p_82690_1_.func_74762_e("Radius");
      this.field_75587_j = p_82690_1_.func_74762_e("Golems");
      this.field_75580_f = p_82690_1_.func_74762_e("Stable");
      this.field_75581_g = p_82690_1_.func_74762_e("Tick");
      this.field_82694_i = p_82690_1_.func_74762_e("MTick");
      this.field_75582_d = new BlockPos(p_82690_1_.func_74762_e("CX"), p_82690_1_.func_74762_e("CY"), p_82690_1_.func_74762_e("CZ"));
      this.field_75585_c = new BlockPos(p_82690_1_.func_74762_e("ACX"), p_82690_1_.func_74762_e("ACY"), p_82690_1_.func_74762_e("ACZ"));
      NBTTagList nbttaglist = p_82690_1_.func_150295_c("Doors", 10);

      for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = nbttaglist.func_150305_b(i);
         VillageDoorInfo villagedoorinfo = new VillageDoorInfo(new BlockPos(nbttagcompound.func_74762_e("X"), nbttagcompound.func_74762_e("Y"), nbttagcompound.func_74762_e("Z")), nbttagcompound.func_74762_e("IDX"), nbttagcompound.func_74762_e("IDZ"), nbttagcompound.func_74762_e("TS"));
         this.field_75584_b.add(villagedoorinfo);
      }

      NBTTagList nbttaglist1 = p_82690_1_.func_150295_c("Players", 10);

      for(int j = 0; j < nbttaglist1.func_74745_c(); ++j) {
         NBTTagCompound nbttagcompound1 = nbttaglist1.func_150305_b(j);
         if (nbttagcompound1.func_74764_b("UUID") && this.field_75586_a != null && this.field_75586_a.func_73046_m() != null) {
            PlayerProfileCache playerprofilecache = this.field_75586_a.func_73046_m().func_152358_ax();
            GameProfile gameprofile = playerprofilecache.func_152652_a(UUID.fromString(nbttagcompound1.func_74779_i("UUID")));
            if (gameprofile != null) {
               this.field_82693_j.put(gameprofile.getName(), Integer.valueOf(nbttagcompound1.func_74762_e("S")));
            }
         } else {
            this.field_82693_j.put(nbttagcompound1.func_74779_i("Name"), Integer.valueOf(nbttagcompound1.func_74762_e("S")));
         }
      }

   }

   public void func_82689_b(NBTTagCompound p_82689_1_) {
      p_82689_1_.func_74768_a("PopSize", this.field_75588_h);
      p_82689_1_.func_74768_a("Radius", this.field_75583_e);
      p_82689_1_.func_74768_a("Golems", this.field_75587_j);
      p_82689_1_.func_74768_a("Stable", this.field_75580_f);
      p_82689_1_.func_74768_a("Tick", this.field_75581_g);
      p_82689_1_.func_74768_a("MTick", this.field_82694_i);
      p_82689_1_.func_74768_a("CX", this.field_75582_d.func_177958_n());
      p_82689_1_.func_74768_a("CY", this.field_75582_d.func_177956_o());
      p_82689_1_.func_74768_a("CZ", this.field_75582_d.func_177952_p());
      p_82689_1_.func_74768_a("ACX", this.field_75585_c.func_177958_n());
      p_82689_1_.func_74768_a("ACY", this.field_75585_c.func_177956_o());
      p_82689_1_.func_74768_a("ACZ", this.field_75585_c.func_177952_p());
      NBTTagList nbttaglist = new NBTTagList();

      for(VillageDoorInfo villagedoorinfo : this.field_75584_b) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.func_74768_a("X", villagedoorinfo.func_179852_d().func_177958_n());
         nbttagcompound.func_74768_a("Y", villagedoorinfo.func_179852_d().func_177956_o());
         nbttagcompound.func_74768_a("Z", villagedoorinfo.func_179852_d().func_177952_p());
         nbttagcompound.func_74768_a("IDX", villagedoorinfo.func_179847_f());
         nbttagcompound.func_74768_a("IDZ", villagedoorinfo.func_179855_g());
         nbttagcompound.func_74768_a("TS", villagedoorinfo.func_75473_b());
         nbttaglist.func_74742_a(nbttagcompound);
      }

      p_82689_1_.func_74782_a("Doors", nbttaglist);
      NBTTagList nbttaglist1 = new NBTTagList();

      for(String s : this.field_82693_j.keySet()) {
         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
         PlayerProfileCache playerprofilecache = this.field_75586_a.func_73046_m().func_152358_ax();

         try {
            GameProfile gameprofile = playerprofilecache.func_152655_a(s);
            if (gameprofile != null) {
               nbttagcompound1.func_74778_a("UUID", gameprofile.getId().toString());
               nbttagcompound1.func_74768_a("S", ((Integer)this.field_82693_j.get(s)).intValue());
               nbttaglist1.func_74742_a(nbttagcompound1);
            }
         } catch (RuntimeException var9) {
            ;
         }
      }

      p_82689_1_.func_74782_a("Players", nbttaglist1);
   }

   public void func_82692_h() {
      this.field_82694_i = this.field_75581_g;
   }

   public boolean func_82686_i() {
      return this.field_82694_i == 0 || this.field_75581_g - this.field_82694_i >= 3600;
   }

   public void func_82683_b(int p_82683_1_) {
      for(String s : this.field_82693_j.keySet()) {
         this.func_82688_a(s, p_82683_1_);
      }

   }

   class VillageAggressor {
      public EntityLivingBase field_75592_a;
      public int field_75590_b;

      VillageAggressor(EntityLivingBase p_i1674_2_, int p_i1674_3_) {
         this.field_75592_a = p_i1674_2_;
         this.field_75590_b = p_i1674_3_;
      }
   }
}
