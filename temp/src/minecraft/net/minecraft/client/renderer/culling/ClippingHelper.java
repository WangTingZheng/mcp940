package net.minecraft.client.renderer.culling;

public class ClippingHelper {
   public float[][] field_78557_a = new float[6][4];
   public float[] field_178625_b = new float[16];
   public float[] field_178626_c = new float[16];
   public float[] field_78554_d = new float[16];

   private double func_178624_a(float[] p_178624_1_, double p_178624_2_, double p_178624_4_, double p_178624_6_) {
      return (double)p_178624_1_[0] * p_178624_2_ + (double)p_178624_1_[1] * p_178624_4_ + (double)p_178624_1_[2] * p_178624_6_ + (double)p_178624_1_[3];
   }

   public boolean func_78553_b(double p_78553_1_, double p_78553_3_, double p_78553_5_, double p_78553_7_, double p_78553_9_, double p_78553_11_) {
      for(int i = 0; i < 6; ++i) {
         float[] afloat = this.field_78557_a[i];
         if (this.func_178624_a(afloat, p_78553_1_, p_78553_3_, p_78553_5_) <= 0.0D && this.func_178624_a(afloat, p_78553_7_, p_78553_3_, p_78553_5_) <= 0.0D && this.func_178624_a(afloat, p_78553_1_, p_78553_9_, p_78553_5_) <= 0.0D && this.func_178624_a(afloat, p_78553_7_, p_78553_9_, p_78553_5_) <= 0.0D && this.func_178624_a(afloat, p_78553_1_, p_78553_3_, p_78553_11_) <= 0.0D && this.func_178624_a(afloat, p_78553_7_, p_78553_3_, p_78553_11_) <= 0.0D && this.func_178624_a(afloat, p_78553_1_, p_78553_9_, p_78553_11_) <= 0.0D && this.func_178624_a(afloat, p_78553_7_, p_78553_9_, p_78553_11_) <= 0.0D) {
            return false;
         }
      }

      return true;
   }
}
