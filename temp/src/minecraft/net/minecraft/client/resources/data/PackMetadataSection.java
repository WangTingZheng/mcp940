package net.minecraft.client.resources.data;

import net.minecraft.util.text.ITextComponent;

public class PackMetadataSection implements IMetadataSection {
   private final ITextComponent field_110464_a;
   private final int field_110463_b;

   public PackMetadataSection(ITextComponent p_i1034_1_, int p_i1034_2_) {
      this.field_110464_a = p_i1034_1_;
      this.field_110463_b = p_i1034_2_;
   }

   public ITextComponent func_152805_a() {
      return this.field_110464_a;
   }

   public int func_110462_b() {
      return this.field_110463_b;
   }
}
