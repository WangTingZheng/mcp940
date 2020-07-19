package net.minecraft.client.renderer;

public class Matrix4f extends org.lwjgl.util.vector.Matrix4f
{
    public Matrix4f(float[] matrix)
    {
        this.m00 = matrix[0];
        this.m01 = matrix[1];
        this.m02 = matrix[2];
        this.m03 = matrix[3];
        this.m10 = matrix[4];
        this.m11 = matrix[5];
        this.m12 = matrix[6];
        this.m13 = matrix[7];
        this.m20 = matrix[8];
        this.m21 = matrix[9];
        this.m22 = matrix[10];
        this.m23 = matrix[11];
        this.m30 = matrix[12];
        this.m31 = matrix[13];
        this.m32 = matrix[14];
        this.m33 = matrix[15];
    }

    public Matrix4f()
    {
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
