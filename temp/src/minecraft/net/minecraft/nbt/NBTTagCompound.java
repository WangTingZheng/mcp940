package net.minecraft.nbt;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.util.ReportedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NBTTagCompound extends NBTBase {
   private static final Logger field_191551_b = LogManager.getLogger();
   private static final Pattern field_193583_c = Pattern.compile("[A-Za-z0-9._+-]+");
   private final Map<String, NBTBase> field_74784_a = Maps.<String, NBTBase>newHashMap();

   void func_74734_a(DataOutput p_74734_1_) throws IOException {
      for(String s : this.field_74784_a.keySet()) {
         NBTBase nbtbase = this.field_74784_a.get(s);
         func_150298_a(s, nbtbase, p_74734_1_);
      }

      p_74734_1_.writeByte(0);
   }

   void func_152446_a(DataInput p_152446_1_, int p_152446_2_, NBTSizeTracker p_152446_3_) throws IOException {
      p_152446_3_.func_152450_a(384L);
      if (p_152446_2_ > 512) {
         throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
      } else {
         this.field_74784_a.clear();

         byte b0;
         while((b0 = func_152447_a(p_152446_1_, p_152446_3_)) != 0) {
            String s = func_152448_b(p_152446_1_, p_152446_3_);
            p_152446_3_.func_152450_a((long)(224 + 16 * s.length()));
            NBTBase nbtbase = func_152449_a(b0, s, p_152446_1_, p_152446_2_ + 1, p_152446_3_);
            if (this.field_74784_a.put(s, nbtbase) != null) {
               p_152446_3_.func_152450_a(288L);
            }
         }

      }
   }

   public Set<String> func_150296_c() {
      return this.field_74784_a.keySet();
   }

   public byte func_74732_a() {
      return 10;
   }

   public int func_186856_d() {
      return this.field_74784_a.size();
   }

   public void func_74782_a(String p_74782_1_, NBTBase p_74782_2_) {
      this.field_74784_a.put(p_74782_1_, p_74782_2_);
   }

   public void func_74774_a(String p_74774_1_, byte p_74774_2_) {
      this.field_74784_a.put(p_74774_1_, new NBTTagByte(p_74774_2_));
   }

   public void func_74777_a(String p_74777_1_, short p_74777_2_) {
      this.field_74784_a.put(p_74777_1_, new NBTTagShort(p_74777_2_));
   }

   public void func_74768_a(String p_74768_1_, int p_74768_2_) {
      this.field_74784_a.put(p_74768_1_, new NBTTagInt(p_74768_2_));
   }

   public void func_74772_a(String p_74772_1_, long p_74772_2_) {
      this.field_74784_a.put(p_74772_1_, new NBTTagLong(p_74772_2_));
   }

   public void func_186854_a(String p_186854_1_, UUID p_186854_2_) {
      this.func_74772_a(p_186854_1_ + "Most", p_186854_2_.getMostSignificantBits());
      this.func_74772_a(p_186854_1_ + "Least", p_186854_2_.getLeastSignificantBits());
   }

   @Nullable
   public UUID func_186857_a(String p_186857_1_) {
      return new UUID(this.func_74763_f(p_186857_1_ + "Most"), this.func_74763_f(p_186857_1_ + "Least"));
   }

   public boolean func_186855_b(String p_186855_1_) {
      return this.func_150297_b(p_186855_1_ + "Most", 99) && this.func_150297_b(p_186855_1_ + "Least", 99);
   }

   public void func_74776_a(String p_74776_1_, float p_74776_2_) {
      this.field_74784_a.put(p_74776_1_, new NBTTagFloat(p_74776_2_));
   }

   public void func_74780_a(String p_74780_1_, double p_74780_2_) {
      this.field_74784_a.put(p_74780_1_, new NBTTagDouble(p_74780_2_));
   }

   public void func_74778_a(String p_74778_1_, String p_74778_2_) {
      this.field_74784_a.put(p_74778_1_, new NBTTagString(p_74778_2_));
   }

   public void func_74773_a(String p_74773_1_, byte[] p_74773_2_) {
      this.field_74784_a.put(p_74773_1_, new NBTTagByteArray(p_74773_2_));
   }

   public void func_74783_a(String p_74783_1_, int[] p_74783_2_) {
      this.field_74784_a.put(p_74783_1_, new NBTTagIntArray(p_74783_2_));
   }

   public void func_74757_a(String p_74757_1_, boolean p_74757_2_) {
      this.func_74774_a(p_74757_1_, (byte)(p_74757_2_ ? 1 : 0));
   }

   public NBTBase func_74781_a(String p_74781_1_) {
      return this.field_74784_a.get(p_74781_1_);
   }

   public byte func_150299_b(String p_150299_1_) {
      NBTBase nbtbase = this.field_74784_a.get(p_150299_1_);
      return nbtbase == null ? 0 : nbtbase.func_74732_a();
   }

   public boolean func_74764_b(String p_74764_1_) {
      return this.field_74784_a.containsKey(p_74764_1_);
   }

   public boolean func_150297_b(String p_150297_1_, int p_150297_2_) {
      int i = this.func_150299_b(p_150297_1_);
      if (i == p_150297_2_) {
         return true;
      } else if (p_150297_2_ != 99) {
         return false;
      } else {
         return i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6;
      }
   }

   public byte func_74771_c(String p_74771_1_) {
      try {
         if (this.func_150297_b(p_74771_1_, 99)) {
            return ((NBTPrimitive)this.field_74784_a.get(p_74771_1_)).func_150290_f();
         }
      } catch (ClassCastException var3) {
         ;
      }

      return 0;
   }

   public short func_74765_d(String p_74765_1_) {
      try {
         if (this.func_150297_b(p_74765_1_, 99)) {
            return ((NBTPrimitive)this.field_74784_a.get(p_74765_1_)).func_150289_e();
         }
      } catch (ClassCastException var3) {
         ;
      }

      return 0;
   }

   public int func_74762_e(String p_74762_1_) {
      try {
         if (this.func_150297_b(p_74762_1_, 99)) {
            return ((NBTPrimitive)this.field_74784_a.get(p_74762_1_)).func_150287_d();
         }
      } catch (ClassCastException var3) {
         ;
      }

      return 0;
   }

   public long func_74763_f(String p_74763_1_) {
      try {
         if (this.func_150297_b(p_74763_1_, 99)) {
            return ((NBTPrimitive)this.field_74784_a.get(p_74763_1_)).func_150291_c();
         }
      } catch (ClassCastException var3) {
         ;
      }

      return 0L;
   }

   public float func_74760_g(String p_74760_1_) {
      try {
         if (this.func_150297_b(p_74760_1_, 99)) {
            return ((NBTPrimitive)this.field_74784_a.get(p_74760_1_)).func_150288_h();
         }
      } catch (ClassCastException var3) {
         ;
      }

      return 0.0F;
   }

   public double func_74769_h(String p_74769_1_) {
      try {
         if (this.func_150297_b(p_74769_1_, 99)) {
            return ((NBTPrimitive)this.field_74784_a.get(p_74769_1_)).func_150286_g();
         }
      } catch (ClassCastException var3) {
         ;
      }

      return 0.0D;
   }

   public String func_74779_i(String p_74779_1_) {
      try {
         if (this.func_150297_b(p_74779_1_, 8)) {
            return ((NBTBase)this.field_74784_a.get(p_74779_1_)).func_150285_a_();
         }
      } catch (ClassCastException var3) {
         ;
      }

      return "";
   }

   public byte[] func_74770_j(String p_74770_1_) {
      try {
         if (this.func_150297_b(p_74770_1_, 7)) {
            return ((NBTTagByteArray)this.field_74784_a.get(p_74770_1_)).func_150292_c();
         }
      } catch (ClassCastException classcastexception) {
         throw new ReportedException(this.func_82581_a(p_74770_1_, 7, classcastexception));
      }

      return new byte[0];
   }

   public int[] func_74759_k(String p_74759_1_) {
      try {
         if (this.func_150297_b(p_74759_1_, 11)) {
            return ((NBTTagIntArray)this.field_74784_a.get(p_74759_1_)).func_150302_c();
         }
      } catch (ClassCastException classcastexception) {
         throw new ReportedException(this.func_82581_a(p_74759_1_, 11, classcastexception));
      }

      return new int[0];
   }

   public NBTTagCompound func_74775_l(String p_74775_1_) {
      try {
         if (this.func_150297_b(p_74775_1_, 10)) {
            return (NBTTagCompound)this.field_74784_a.get(p_74775_1_);
         }
      } catch (ClassCastException classcastexception) {
         throw new ReportedException(this.func_82581_a(p_74775_1_, 10, classcastexception));
      }

      return new NBTTagCompound();
   }

   public NBTTagList func_150295_c(String p_150295_1_, int p_150295_2_) {
      try {
         if (this.func_150299_b(p_150295_1_) == 9) {
            NBTTagList nbttaglist = (NBTTagList)this.field_74784_a.get(p_150295_1_);
            if (!nbttaglist.func_82582_d() && nbttaglist.func_150303_d() != p_150295_2_) {
               return new NBTTagList();
            }

            return nbttaglist;
         }
      } catch (ClassCastException classcastexception) {
         throw new ReportedException(this.func_82581_a(p_150295_1_, 9, classcastexception));
      }

      return new NBTTagList();
   }

   public boolean func_74767_n(String p_74767_1_) {
      return this.func_74771_c(p_74767_1_) != 0;
   }

   public void func_82580_o(String p_82580_1_) {
      this.field_74784_a.remove(p_82580_1_);
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder("{");
      Collection<String> collection = this.field_74784_a.keySet();
      if (field_191551_b.isDebugEnabled()) {
         List<String> list = Lists.newArrayList(this.field_74784_a.keySet());
         Collections.sort(list);
         collection = list;
      }

      for(String s : collection) {
         if (stringbuilder.length() != 1) {
            stringbuilder.append(',');
         }

         stringbuilder.append(func_193582_s(s)).append(':').append(this.field_74784_a.get(s));
      }

      return stringbuilder.append('}').toString();
   }

   public boolean func_82582_d() {
      return this.field_74784_a.isEmpty();
   }

   private CrashReport func_82581_a(final String p_82581_1_, final int p_82581_2_, ClassCastException p_82581_3_) {
      CrashReport crashreport = CrashReport.func_85055_a(p_82581_3_, "Reading NBT data");
      CrashReportCategory crashreportcategory = crashreport.func_85057_a("Corrupt NBT tag", 1);
      crashreportcategory.func_189529_a("Tag type found", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            return NBTBase.field_82578_b[((NBTBase)NBTTagCompound.this.field_74784_a.get(p_82581_1_)).func_74732_a()];
         }
      });
      crashreportcategory.func_189529_a("Tag type expected", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            return NBTBase.field_82578_b[p_82581_2_];
         }
      });
      crashreportcategory.func_71507_a("Tag name", p_82581_1_);
      return crashreport;
   }

   public NBTTagCompound func_74737_b() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();

      for(String s : this.field_74784_a.keySet()) {
         nbttagcompound.func_74782_a(s, ((NBTBase)this.field_74784_a.get(s)).func_74737_b());
      }

      return nbttagcompound;
   }

   public boolean equals(Object p_equals_1_) {
      return super.equals(p_equals_1_) && Objects.equals(this.field_74784_a.entrySet(), ((NBTTagCompound)p_equals_1_).field_74784_a.entrySet());
   }

   public int hashCode() {
      return super.hashCode() ^ this.field_74784_a.hashCode();
   }

   private static void func_150298_a(String p_150298_0_, NBTBase p_150298_1_, DataOutput p_150298_2_) throws IOException {
      p_150298_2_.writeByte(p_150298_1_.func_74732_a());
      if (p_150298_1_.func_74732_a() != 0) {
         p_150298_2_.writeUTF(p_150298_0_);
         p_150298_1_.func_74734_a(p_150298_2_);
      }
   }

   private static byte func_152447_a(DataInput p_152447_0_, NBTSizeTracker p_152447_1_) throws IOException {
      return p_152447_0_.readByte();
   }

   private static String func_152448_b(DataInput p_152448_0_, NBTSizeTracker p_152448_1_) throws IOException {
      return p_152448_0_.readUTF();
   }

   static NBTBase func_152449_a(byte p_152449_0_, String p_152449_1_, DataInput p_152449_2_, int p_152449_3_, NBTSizeTracker p_152449_4_) throws IOException {
      NBTBase nbtbase = NBTBase.func_150284_a(p_152449_0_);

      try {
         nbtbase.func_152446_a(p_152449_2_, p_152449_3_, p_152449_4_);
         return nbtbase;
      } catch (IOException ioexception) {
         CrashReport crashreport = CrashReport.func_85055_a(ioexception, "Loading NBT data");
         CrashReportCategory crashreportcategory = crashreport.func_85058_a("NBT Tag");
         crashreportcategory.func_71507_a("Tag name", p_152449_1_);
         crashreportcategory.func_71507_a("Tag type", Byte.valueOf(p_152449_0_));
         throw new ReportedException(crashreport);
      }
   }

   public void func_179237_a(NBTTagCompound p_179237_1_) {
      for(String s : p_179237_1_.field_74784_a.keySet()) {
         NBTBase nbtbase = p_179237_1_.field_74784_a.get(s);
         if (nbtbase.func_74732_a() == 10) {
            if (this.func_150297_b(s, 10)) {
               NBTTagCompound nbttagcompound = this.func_74775_l(s);
               nbttagcompound.func_179237_a((NBTTagCompound)nbtbase);
            } else {
               this.func_74782_a(s, nbtbase.func_74737_b());
            }
         } else {
            this.func_74782_a(s, nbtbase.func_74737_b());
         }
      }

   }

   protected static String func_193582_s(String p_193582_0_) {
      return field_193583_c.matcher(p_193582_0_).matches() ? p_193582_0_ : NBTTagString.func_193588_a(p_193582_0_);
   }
}
