package net.minecraft.nbt;

import com.google.common.collect.Lists;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NBTTagList extends NBTBase {
   private static final Logger field_179239_b = LogManager.getLogger();
   private List<NBTBase> field_74747_a = Lists.<NBTBase>newArrayList();
   private byte field_74746_b = 0;

   void func_74734_a(DataOutput p_74734_1_) throws IOException {
      if (this.field_74747_a.isEmpty()) {
         this.field_74746_b = 0;
      } else {
         this.field_74746_b = ((NBTBase)this.field_74747_a.get(0)).func_74732_a();
      }

      p_74734_1_.writeByte(this.field_74746_b);
      p_74734_1_.writeInt(this.field_74747_a.size());

      for(int i = 0; i < this.field_74747_a.size(); ++i) {
         ((NBTBase)this.field_74747_a.get(i)).func_74734_a(p_74734_1_);
      }

   }

   void func_152446_a(DataInput p_152446_1_, int p_152446_2_, NBTSizeTracker p_152446_3_) throws IOException {
      p_152446_3_.func_152450_a(296L);
      if (p_152446_2_ > 512) {
         throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
      } else {
         this.field_74746_b = p_152446_1_.readByte();
         int i = p_152446_1_.readInt();
         if (this.field_74746_b == 0 && i > 0) {
            throw new RuntimeException("Missing type on ListTag");
         } else {
            p_152446_3_.func_152450_a(32L * (long)i);
            this.field_74747_a = Lists.<NBTBase>newArrayListWithCapacity(i);

            for(int j = 0; j < i; ++j) {
               NBTBase nbtbase = NBTBase.func_150284_a(this.field_74746_b);
               nbtbase.func_152446_a(p_152446_1_, p_152446_2_ + 1, p_152446_3_);
               this.field_74747_a.add(nbtbase);
            }

         }
      }
   }

   public byte func_74732_a() {
      return 9;
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder("[");

      for(int i = 0; i < this.field_74747_a.size(); ++i) {
         if (i != 0) {
            stringbuilder.append(',');
         }

         stringbuilder.append(this.field_74747_a.get(i));
      }

      return stringbuilder.append(']').toString();
   }

   public void func_74742_a(NBTBase p_74742_1_) {
      if (p_74742_1_.func_74732_a() == 0) {
         field_179239_b.warn("Invalid TagEnd added to ListTag");
      } else {
         if (this.field_74746_b == 0) {
            this.field_74746_b = p_74742_1_.func_74732_a();
         } else if (this.field_74746_b != p_74742_1_.func_74732_a()) {
            field_179239_b.warn("Adding mismatching tag types to tag list");
            return;
         }

         this.field_74747_a.add(p_74742_1_);
      }
   }

   public void func_150304_a(int p_150304_1_, NBTBase p_150304_2_) {
      if (p_150304_2_.func_74732_a() == 0) {
         field_179239_b.warn("Invalid TagEnd added to ListTag");
      } else if (p_150304_1_ >= 0 && p_150304_1_ < this.field_74747_a.size()) {
         if (this.field_74746_b == 0) {
            this.field_74746_b = p_150304_2_.func_74732_a();
         } else if (this.field_74746_b != p_150304_2_.func_74732_a()) {
            field_179239_b.warn("Adding mismatching tag types to tag list");
            return;
         }

         this.field_74747_a.set(p_150304_1_, p_150304_2_);
      } else {
         field_179239_b.warn("index out of bounds to set tag in tag list");
      }
   }

   public NBTBase func_74744_a(int p_74744_1_) {
      return this.field_74747_a.remove(p_74744_1_);
   }

   public boolean func_82582_d() {
      return this.field_74747_a.isEmpty();
   }

   public NBTTagCompound func_150305_b(int p_150305_1_) {
      if (p_150305_1_ >= 0 && p_150305_1_ < this.field_74747_a.size()) {
         NBTBase nbtbase = this.field_74747_a.get(p_150305_1_);
         if (nbtbase.func_74732_a() == 10) {
            return (NBTTagCompound)nbtbase;
         }
      }

      return new NBTTagCompound();
   }

   public int func_186858_c(int p_186858_1_) {
      if (p_186858_1_ >= 0 && p_186858_1_ < this.field_74747_a.size()) {
         NBTBase nbtbase = this.field_74747_a.get(p_186858_1_);
         if (nbtbase.func_74732_a() == 3) {
            return ((NBTTagInt)nbtbase).func_150287_d();
         }
      }

      return 0;
   }

   public int[] func_150306_c(int p_150306_1_) {
      if (p_150306_1_ >= 0 && p_150306_1_ < this.field_74747_a.size()) {
         NBTBase nbtbase = this.field_74747_a.get(p_150306_1_);
         if (nbtbase.func_74732_a() == 11) {
            return ((NBTTagIntArray)nbtbase).func_150302_c();
         }
      }

      return new int[0];
   }

   public double func_150309_d(int p_150309_1_) {
      if (p_150309_1_ >= 0 && p_150309_1_ < this.field_74747_a.size()) {
         NBTBase nbtbase = this.field_74747_a.get(p_150309_1_);
         if (nbtbase.func_74732_a() == 6) {
            return ((NBTTagDouble)nbtbase).func_150286_g();
         }
      }

      return 0.0D;
   }

   public float func_150308_e(int p_150308_1_) {
      if (p_150308_1_ >= 0 && p_150308_1_ < this.field_74747_a.size()) {
         NBTBase nbtbase = this.field_74747_a.get(p_150308_1_);
         if (nbtbase.func_74732_a() == 5) {
            return ((NBTTagFloat)nbtbase).func_150288_h();
         }
      }

      return 0.0F;
   }

   public String func_150307_f(int p_150307_1_) {
      if (p_150307_1_ >= 0 && p_150307_1_ < this.field_74747_a.size()) {
         NBTBase nbtbase = this.field_74747_a.get(p_150307_1_);
         return nbtbase.func_74732_a() == 8 ? nbtbase.func_150285_a_() : nbtbase.toString();
      } else {
         return "";
      }
   }

   public NBTBase func_179238_g(int p_179238_1_) {
      return (NBTBase)(p_179238_1_ >= 0 && p_179238_1_ < this.field_74747_a.size() ? (NBTBase)this.field_74747_a.get(p_179238_1_) : new NBTTagEnd());
   }

   public int func_74745_c() {
      return this.field_74747_a.size();
   }

   public NBTTagList func_74737_b() {
      NBTTagList nbttaglist = new NBTTagList();
      nbttaglist.field_74746_b = this.field_74746_b;

      for(NBTBase nbtbase : this.field_74747_a) {
         NBTBase nbtbase1 = nbtbase.func_74737_b();
         nbttaglist.field_74747_a.add(nbtbase1);
      }

      return nbttaglist;
   }

   public boolean equals(Object p_equals_1_) {
      if (!super.equals(p_equals_1_)) {
         return false;
      } else {
         NBTTagList nbttaglist = (NBTTagList)p_equals_1_;
         return this.field_74746_b == nbttaglist.field_74746_b && Objects.equals(this.field_74747_a, nbttaglist.field_74747_a);
      }
   }

   public int hashCode() {
      return super.hashCode() ^ this.field_74747_a.hashCode();
   }

   public int func_150303_d() {
      return this.field_74746_b;
   }
}
