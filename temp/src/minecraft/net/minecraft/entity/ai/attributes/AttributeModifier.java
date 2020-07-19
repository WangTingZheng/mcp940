package net.minecraft.entity.ai.attributes;

import io.netty.util.internal.ThreadLocalRandom;
import java.util.UUID;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.Validate;

public class AttributeModifier {
   private final double field_111174_a;
   private final int field_111172_b;
   private final String field_111173_c;
   private final UUID field_111170_d;
   private boolean field_111171_e;

   public AttributeModifier(String p_i1605_1_, double p_i1605_2_, int p_i1605_4_) {
      this(MathHelper.func_180182_a(ThreadLocalRandom.current()), p_i1605_1_, p_i1605_2_, p_i1605_4_);
   }

   public AttributeModifier(UUID p_i1606_1_, String p_i1606_2_, double p_i1606_3_, int p_i1606_5_) {
      this.field_111171_e = true;
      this.field_111170_d = p_i1606_1_;
      this.field_111173_c = p_i1606_2_;
      this.field_111174_a = p_i1606_3_;
      this.field_111172_b = p_i1606_5_;
      Validate.notEmpty(p_i1606_2_, "Modifier name cannot be empty");
      Validate.inclusiveBetween(0L, 2L, (long)p_i1606_5_, "Invalid operation");
   }

   public UUID func_111167_a() {
      return this.field_111170_d;
   }

   public String func_111166_b() {
      return this.field_111173_c;
   }

   public int func_111169_c() {
      return this.field_111172_b;
   }

   public double func_111164_d() {
      return this.field_111174_a;
   }

   public boolean func_111165_e() {
      return this.field_111171_e;
   }

   public AttributeModifier func_111168_a(boolean p_111168_1_) {
      this.field_111171_e = p_111168_1_;
      return this;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         AttributeModifier attributemodifier = (AttributeModifier)p_equals_1_;
         if (this.field_111170_d != null) {
            if (!this.field_111170_d.equals(attributemodifier.field_111170_d)) {
               return false;
            }
         } else if (attributemodifier.field_111170_d != null) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.field_111170_d != null ? this.field_111170_d.hashCode() : 0;
   }

   public String toString() {
      return "AttributeModifier{amount=" + this.field_111174_a + ", operation=" + this.field_111172_b + ", name='" + this.field_111173_c + '\'' + ", id=" + this.field_111170_d + ", serialize=" + this.field_111171_e + '}';
   }
}
