package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class NBTTagString extends NBTBase {
   private String field_74751_a;

   public NBTTagString() {
      this("");
   }

   public NBTTagString(String p_i1389_1_) {
      Objects.requireNonNull(p_i1389_1_, "Null string not allowed");
      this.field_74751_a = p_i1389_1_;
   }

   void func_74734_a(DataOutput p_74734_1_) throws IOException {
      p_74734_1_.writeUTF(this.field_74751_a);
   }

   void func_152446_a(DataInput p_152446_1_, int p_152446_2_, NBTSizeTracker p_152446_3_) throws IOException {
      p_152446_3_.func_152450_a(288L);
      this.field_74751_a = p_152446_1_.readUTF();
      p_152446_3_.func_152450_a((long)(16 * this.field_74751_a.length()));
   }

   public byte func_74732_a() {
      return 8;
   }

   public String toString() {
      return func_193588_a(this.field_74751_a);
   }

   public NBTTagString func_74737_b() {
      return new NBTTagString(this.field_74751_a);
   }

   public boolean func_82582_d() {
      return this.field_74751_a.isEmpty();
   }

   public boolean equals(Object p_equals_1_) {
      if (!super.equals(p_equals_1_)) {
         return false;
      } else {
         NBTTagString nbttagstring = (NBTTagString)p_equals_1_;
         return this.field_74751_a == null && nbttagstring.field_74751_a == null || Objects.equals(this.field_74751_a, nbttagstring.field_74751_a);
      }
   }

   public int hashCode() {
      return super.hashCode() ^ this.field_74751_a.hashCode();
   }

   public String func_150285_a_() {
      return this.field_74751_a;
   }

   public static String func_193588_a(String p_193588_0_) {
      StringBuilder stringbuilder = new StringBuilder("\"");

      for(int i = 0; i < p_193588_0_.length(); ++i) {
         char c0 = p_193588_0_.charAt(i);
         if (c0 == '\\' || c0 == '"') {
            stringbuilder.append('\\');
         }

         stringbuilder.append(c0);
      }

      return stringbuilder.append('"').toString();
   }
}
