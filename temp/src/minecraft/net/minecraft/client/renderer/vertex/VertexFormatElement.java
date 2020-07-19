package net.minecraft.client.renderer.vertex;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VertexFormatElement {
   private static final Logger field_177381_a = LogManager.getLogger();
   private final VertexFormatElement.EnumType field_177379_b;
   private final VertexFormatElement.EnumUsage field_177380_c;
   private final int field_177377_d;
   private final int field_177378_e;

   public VertexFormatElement(int p_i46096_1_, VertexFormatElement.EnumType p_i46096_2_, VertexFormatElement.EnumUsage p_i46096_3_, int p_i46096_4_) {
      if (this.func_177372_a(p_i46096_1_, p_i46096_3_)) {
         this.field_177380_c = p_i46096_3_;
      } else {
         field_177381_a.warn("Multiple vertex elements of the same type other than UVs are not supported. Forcing type to UV.");
         this.field_177380_c = VertexFormatElement.EnumUsage.UV;
      }

      this.field_177379_b = p_i46096_2_;
      this.field_177377_d = p_i46096_1_;
      this.field_177378_e = p_i46096_4_;
   }

   private final boolean func_177372_a(int p_177372_1_, VertexFormatElement.EnumUsage p_177372_2_) {
      return p_177372_1_ == 0 || p_177372_2_ == VertexFormatElement.EnumUsage.UV;
   }

   public final VertexFormatElement.EnumType func_177367_b() {
      return this.field_177379_b;
   }

   public final VertexFormatElement.EnumUsage func_177375_c() {
      return this.field_177380_c;
   }

   public final int func_177370_d() {
      return this.field_177378_e;
   }

   public final int func_177369_e() {
      return this.field_177377_d;
   }

   public String toString() {
      return this.field_177378_e + "," + this.field_177380_c.func_177384_a() + "," + this.field_177379_b.func_177396_b();
   }

   public final int func_177368_f() {
      return this.field_177379_b.func_177395_a() * this.field_177378_e;
   }

   public final boolean func_177374_g() {
      return this.field_177380_c == VertexFormatElement.EnumUsage.POSITION;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         VertexFormatElement vertexformatelement = (VertexFormatElement)p_equals_1_;
         if (this.field_177378_e != vertexformatelement.field_177378_e) {
            return false;
         } else if (this.field_177377_d != vertexformatelement.field_177377_d) {
            return false;
         } else if (this.field_177379_b != vertexformatelement.field_177379_b) {
            return false;
         } else {
            return this.field_177380_c == vertexformatelement.field_177380_c;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int i = this.field_177379_b.hashCode();
      i = 31 * i + this.field_177380_c.hashCode();
      i = 31 * i + this.field_177377_d;
      i = 31 * i + this.field_177378_e;
      return i;
   }

   public static enum EnumType {
      FLOAT(4, "Float", 5126),
      UBYTE(1, "Unsigned Byte", 5121),
      BYTE(1, "Byte", 5120),
      USHORT(2, "Unsigned Short", 5123),
      SHORT(2, "Short", 5122),
      UINT(4, "Unsigned Int", 5125),
      INT(4, "Int", 5124);

      private final int field_177407_h;
      private final String field_177408_i;
      private final int field_177405_j;

      private EnumType(int p_i46095_3_, String p_i46095_4_, int p_i46095_5_) {
         this.field_177407_h = p_i46095_3_;
         this.field_177408_i = p_i46095_4_;
         this.field_177405_j = p_i46095_5_;
      }

      public int func_177395_a() {
         return this.field_177407_h;
      }

      public String func_177396_b() {
         return this.field_177408_i;
      }

      public int func_177397_c() {
         return this.field_177405_j;
      }
   }

   public static enum EnumUsage {
      POSITION("Position"),
      NORMAL("Normal"),
      COLOR("Vertex Color"),
      UV("UV"),
      MATRIX("Bone Matrix"),
      BLEND_WEIGHT("Blend Weight"),
      PADDING("Padding");

      private final String field_177392_h;

      private EnumUsage(String p_i46094_3_) {
         this.field_177392_h = p_i46094_3_;
      }

      public String func_177384_a() {
         return this.field_177392_h;
      }
   }
}
