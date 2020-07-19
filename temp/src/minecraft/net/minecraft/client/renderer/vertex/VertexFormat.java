package net.minecraft.client.renderer.vertex;

import com.google.common.collect.Lists;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VertexFormat {
   private static final Logger field_177357_a = LogManager.getLogger();
   private final List<VertexFormatElement> field_177355_b;
   private final List<Integer> field_177356_c;
   private int field_177353_d;
   private int field_177354_e;
   private final List<Integer> field_177351_f;
   private int field_177352_g;

   public VertexFormat(VertexFormat p_i46097_1_) {
      this();

      for(int i = 0; i < p_i46097_1_.func_177345_h(); ++i) {
         this.func_181721_a(p_i46097_1_.func_177348_c(i));
      }

      this.field_177353_d = p_i46097_1_.func_177338_f();
   }

   public VertexFormat() {
      this.field_177355_b = Lists.<VertexFormatElement>newArrayList();
      this.field_177356_c = Lists.<Integer>newArrayList();
      this.field_177354_e = -1;
      this.field_177351_f = Lists.<Integer>newArrayList();
      this.field_177352_g = -1;
   }

   public void func_177339_a() {
      this.field_177355_b.clear();
      this.field_177356_c.clear();
      this.field_177354_e = -1;
      this.field_177351_f.clear();
      this.field_177352_g = -1;
      this.field_177353_d = 0;
   }

   public VertexFormat func_181721_a(VertexFormatElement p_181721_1_) {
      if (p_181721_1_.func_177374_g() && this.func_177341_i()) {
         field_177357_a.warn("VertexFormat error: Trying to add a position VertexFormatElement when one already exists, ignoring.");
         return this;
      } else {
         this.field_177355_b.add(p_181721_1_);
         this.field_177356_c.add(Integer.valueOf(this.field_177353_d));
         switch(p_181721_1_.func_177375_c()) {
         case NORMAL:
            this.field_177352_g = this.field_177353_d;
            break;
         case COLOR:
            this.field_177354_e = this.field_177353_d;
            break;
         case UV:
            this.field_177351_f.add(p_181721_1_.func_177369_e(), Integer.valueOf(this.field_177353_d));
         }

         this.field_177353_d += p_181721_1_.func_177368_f();
         return this;
      }
   }

   public boolean func_177350_b() {
      return this.field_177352_g >= 0;
   }

   public int func_177342_c() {
      return this.field_177352_g;
   }

   public boolean func_177346_d() {
      return this.field_177354_e >= 0;
   }

   public int func_177340_e() {
      return this.field_177354_e;
   }

   public boolean func_177347_a(int p_177347_1_) {
      return this.field_177351_f.size() - 1 >= p_177347_1_;
   }

   public int func_177344_b(int p_177344_1_) {
      return ((Integer)this.field_177351_f.get(p_177344_1_)).intValue();
   }

   public String toString() {
      String s = "format: " + this.field_177355_b.size() + " elements: ";

      for(int i = 0; i < this.field_177355_b.size(); ++i) {
         s = s + ((VertexFormatElement)this.field_177355_b.get(i)).toString();
         if (i != this.field_177355_b.size() - 1) {
            s = s + " ";
         }
      }

      return s;
   }

   private boolean func_177341_i() {
      int i = 0;

      for(int j = this.field_177355_b.size(); i < j; ++i) {
         VertexFormatElement vertexformatelement = this.field_177355_b.get(i);
         if (vertexformatelement.func_177374_g()) {
            return true;
         }
      }

      return false;
   }

   public int func_181719_f() {
      return this.func_177338_f() / 4;
   }

   public int func_177338_f() {
      return this.field_177353_d;
   }

   public List<VertexFormatElement> func_177343_g() {
      return this.field_177355_b;
   }

   public int func_177345_h() {
      return this.field_177355_b.size();
   }

   public VertexFormatElement func_177348_c(int p_177348_1_) {
      return this.field_177355_b.get(p_177348_1_);
   }

   public int func_181720_d(int p_181720_1_) {
      return ((Integer)this.field_177356_c.get(p_181720_1_)).intValue();
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         VertexFormat vertexformat = (VertexFormat)p_equals_1_;
         if (this.field_177353_d != vertexformat.field_177353_d) {
            return false;
         } else if (!this.field_177355_b.equals(vertexformat.field_177355_b)) {
            return false;
         } else {
            return this.field_177356_c.equals(vertexformat.field_177356_c);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int i = this.field_177355_b.hashCode();
      i = 31 * i + this.field_177356_c.hashCode();
      i = 31 * i + this.field_177353_d;
      return i;
   }
}
