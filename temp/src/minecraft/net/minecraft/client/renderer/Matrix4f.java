package net.minecraft.client.renderer;

public class Matrix4f extends org.lwjgl.util.vector.Matrix4f {
   public Matrix4f(float[] p_i46413_1_) {
      this.m00 = p_i46413_1_[0];
      this.m01 = p_i46413_1_[1];
      this.m02 = p_i46413_1_[2];
      this.m03 = p_i46413_1_[3];
      this.m10 = p_i46413_1_[4];
      this.m11 = p_i46413_1_[5];
      this.m12 = p_i46413_1_[6];
      this.m13 = p_i46413_1_[7];
      this.m20 = p_i46413_1_[8];
      this.m21 = p_i46413_1_[9];
      this.m22 = p_i46413_1_[10];
      this.m23 = p_i46413_1_[11];
      this.m30 = p_i46413_1_[12];
      this.m31 = p_i46413_1_[13];
      this.m32 = p_i46413_1_[14];
      this.m33 = p_i46413_1_[15];
   }

   public Matrix4f() {
      this.m00 = 0.0F;
      this.m01 = 0.0F;
      this.m02 = 0.0F;
      this.m03 = 0.0F;
      this.m10 = 0.0F;
      this.m11 = 0.0F;
      this.m12 = 0.0F;
      this.m13 = 0.0F;
      this.m20 = 0.0F;
      this.m21 = 0.0F;
      this.m22 = 0.0F;
      this.m23 = 0.0F;
      this.m30 = 0.0F;
      this.m31 = 0.0F;
      this.m32 = 0.0F;
      this.m33 = 0.0F;
   }
}
