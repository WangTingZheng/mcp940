package net.minecraft.client.resources.data;

public class TextureMetadataSection implements IMetadataSection {
   private final boolean field_110482_a;
   private final boolean field_110481_b;

   public TextureMetadataSection(boolean p_i46538_1_, boolean p_i46538_2_) {
      this.field_110482_a = p_i46538_1_;
      this.field_110481_b = p_i46538_2_;
   }

   public boolean func_110479_a() {
      return this.field_110482_a;
   }

   public boolean func_110480_b() {
      return this.field_110481_b;
   }
}
