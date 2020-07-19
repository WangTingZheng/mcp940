package net.minecraft.client.renderer.chunk;

import java.util.BitSet;
import java.util.Set;
import net.minecraft.util.EnumFacing;

public class SetVisibility {
   private static final int field_178623_a = EnumFacing.values().length;
   private final BitSet field_178622_b;

   public SetVisibility() {
      this.field_178622_b = new BitSet(field_178623_a * field_178623_a);
   }

   public void func_178620_a(Set<EnumFacing> p_178620_1_) {
      for(EnumFacing enumfacing : p_178620_1_) {
         for(EnumFacing enumfacing1 : p_178620_1_) {
            this.func_178619_a(enumfacing, enumfacing1, true);
         }
      }

   }

   public void func_178619_a(EnumFacing p_178619_1_, EnumFacing p_178619_2_, boolean p_178619_3_) {
      this.field_178622_b.set(p_178619_1_.ordinal() + p_178619_2_.ordinal() * field_178623_a, p_178619_3_);
      this.field_178622_b.set(p_178619_2_.ordinal() + p_178619_1_.ordinal() * field_178623_a, p_178619_3_);
   }

   public void func_178618_a(boolean p_178618_1_) {
      this.field_178622_b.set(0, this.field_178622_b.size(), p_178618_1_);
   }

   public boolean func_178621_a(EnumFacing p_178621_1_, EnumFacing p_178621_2_) {
      return this.field_178622_b.get(p_178621_1_.ordinal() + p_178621_2_.ordinal() * field_178623_a);
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append(' ');

      for(EnumFacing enumfacing : EnumFacing.values()) {
         stringbuilder.append(' ').append(enumfacing.toString().toUpperCase().charAt(0));
      }

      stringbuilder.append('\n');

      for(EnumFacing enumfacing2 : EnumFacing.values()) {
         stringbuilder.append(enumfacing2.toString().toUpperCase().charAt(0));

         for(EnumFacing enumfacing1 : EnumFacing.values()) {
            if (enumfacing2 == enumfacing1) {
               stringbuilder.append("  ");
            } else {
               boolean flag = this.func_178621_a(enumfacing2, enumfacing1);
               stringbuilder.append(' ').append((char)(flag ? 'Y' : 'n'));
            }
         }

         stringbuilder.append('\n');
      }

      return stringbuilder.toString();
   }
}
