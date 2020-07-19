package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public abstract class StructureStart {
   protected List<StructureComponent> field_75075_a = Lists.<StructureComponent>newLinkedList();
   protected StructureBoundingBox field_75074_b;
   private int field_143024_c;
   private int field_143023_d;

   public StructureStart() {
   }

   public StructureStart(int p_i43002_1_, int p_i43002_2_) {
      this.field_143024_c = p_i43002_1_;
      this.field_143023_d = p_i43002_2_;
   }

   public StructureBoundingBox func_75071_a() {
      return this.field_75074_b;
   }

   public List<StructureComponent> func_186161_c() {
      return this.field_75075_a;
   }

   public void func_75068_a(World p_75068_1_, Random p_75068_2_, StructureBoundingBox p_75068_3_) {
      Iterator<StructureComponent> iterator = this.field_75075_a.iterator();

      while(iterator.hasNext()) {
         StructureComponent structurecomponent = iterator.next();
         if (structurecomponent.func_74874_b().func_78884_a(p_75068_3_) && !structurecomponent.func_74875_a(p_75068_1_, p_75068_2_, p_75068_3_)) {
            iterator.remove();
         }
      }

   }

   protected void func_75072_c() {
      this.field_75074_b = StructureBoundingBox.func_78887_a();

      for(StructureComponent structurecomponent : this.field_75075_a) {
         this.field_75074_b.func_78888_b(structurecomponent.func_74874_b());
      }

   }

   public NBTTagCompound func_143021_a(int p_143021_1_, int p_143021_2_) {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.func_74778_a("id", MapGenStructureIO.func_143033_a(this));
      nbttagcompound.func_74768_a("ChunkX", p_143021_1_);
      nbttagcompound.func_74768_a("ChunkZ", p_143021_2_);
      nbttagcompound.func_74782_a("BB", this.field_75074_b.func_151535_h());
      NBTTagList nbttaglist = new NBTTagList();

      for(StructureComponent structurecomponent : this.field_75075_a) {
         nbttaglist.func_74742_a(structurecomponent.func_143010_b());
      }

      nbttagcompound.func_74782_a("Children", nbttaglist);
      this.func_143022_a(nbttagcompound);
      return nbttagcompound;
   }

   public void func_143022_a(NBTTagCompound p_143022_1_) {
   }

   public void func_143020_a(World p_143020_1_, NBTTagCompound p_143020_2_) {
      this.field_143024_c = p_143020_2_.func_74762_e("ChunkX");
      this.field_143023_d = p_143020_2_.func_74762_e("ChunkZ");
      if (p_143020_2_.func_74764_b("BB")) {
         this.field_75074_b = new StructureBoundingBox(p_143020_2_.func_74759_k("BB"));
      }

      NBTTagList nbttaglist = p_143020_2_.func_150295_c("Children", 10);

      for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
         this.field_75075_a.add(MapGenStructureIO.func_143032_b(nbttaglist.func_150305_b(i), p_143020_1_));
      }

      this.func_143017_b(p_143020_2_);
   }

   public void func_143017_b(NBTTagCompound p_143017_1_) {
   }

   protected void func_75067_a(World p_75067_1_, Random p_75067_2_, int p_75067_3_) {
      int i = p_75067_1_.func_181545_F() - p_75067_3_;
      int j = this.field_75074_b.func_78882_c() + 1;
      if (j < i) {
         j += p_75067_2_.nextInt(i - j);
      }

      int k = j - this.field_75074_b.field_78894_e;
      this.field_75074_b.func_78886_a(0, k, 0);

      for(StructureComponent structurecomponent : this.field_75075_a) {
         structurecomponent.func_181138_a(0, k, 0);
      }

   }

   protected void func_75070_a(World p_75070_1_, Random p_75070_2_, int p_75070_3_, int p_75070_4_) {
      int i = p_75070_4_ - p_75070_3_ + 1 - this.field_75074_b.func_78882_c();
      int j;
      if (i > 1) {
         j = p_75070_3_ + p_75070_2_.nextInt(i);
      } else {
         j = p_75070_3_;
      }

      int k = j - this.field_75074_b.field_78895_b;
      this.field_75074_b.func_78886_a(0, k, 0);

      for(StructureComponent structurecomponent : this.field_75075_a) {
         structurecomponent.func_181138_a(0, k, 0);
      }

   }

   public boolean func_75069_d() {
      return true;
   }

   public boolean func_175788_a(ChunkPos p_175788_1_) {
      return true;
   }

   public void func_175787_b(ChunkPos p_175787_1_) {
   }

   public int func_143019_e() {
      return this.field_143024_c;
   }

   public int func_143018_f() {
      return this.field_143023_d;
   }
}
