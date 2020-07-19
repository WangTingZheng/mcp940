package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTBase {
   public static final String[] field_82578_b = new String[]{"END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]", "LONG[]"};

   abstract void func_74734_a(DataOutput var1) throws IOException;

   abstract void func_152446_a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException;

   public abstract String toString();

   public abstract byte func_74732_a();

   protected static NBTBase func_150284_a(byte p_150284_0_) {
      switch(p_150284_0_) {
      case 0:
         return new NBTTagEnd();
      case 1:
         return new NBTTagByte();
      case 2:
         return new NBTTagShort();
      case 3:
         return new NBTTagInt();
      case 4:
         return new NBTTagLong();
      case 5:
         return new NBTTagFloat();
      case 6:
         return new NBTTagDouble();
      case 7:
         return new NBTTagByteArray();
      case 8:
         return new NBTTagString();
      case 9:
         return new NBTTagList();
      case 10:
         return new NBTTagCompound();
      case 11:
         return new NBTTagIntArray();
      case 12:
         return new NBTTagLongArray();
      default:
         return null;
      }
   }

   public static String func_193581_j(int p_193581_0_) {
      switch(p_193581_0_) {
      case 0:
         return "TAG_End";
      case 1:
         return "TAG_Byte";
      case 2:
         return "TAG_Short";
      case 3:
         return "TAG_Int";
      case 4:
         return "TAG_Long";
      case 5:
         return "TAG_Float";
      case 6:
         return "TAG_Double";
      case 7:
         return "TAG_Byte_Array";
      case 8:
         return "TAG_String";
      case 9:
         return "TAG_List";
      case 10:
         return "TAG_Compound";
      case 11:
         return "TAG_Int_Array";
      case 12:
         return "TAG_Long_Array";
      case 99:
         return "Any Numeric Tag";
      default:
         return "UNKNOWN";
      }
   }

   public abstract NBTBase func_74737_b();

   public boolean func_82582_d() {
      return false;
   }

   public boolean equals(Object p_equals_1_) {
      return p_equals_1_ instanceof NBTBase && this.func_74732_a() == ((NBTBase)p_equals_1_).func_74732_a();
   }

   public int hashCode() {
      return this.func_74732_a();
   }

   protected String func_150285_a_() {
      return this.toString();
   }
}
