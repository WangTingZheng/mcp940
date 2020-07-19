package net.minecraft.client.renderer.culling;

import java.nio.FloatBuffer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;

public class ClippingHelperImpl extends ClippingHelper
{
    private static final ClippingHelperImpl instance = new ClippingHelperImpl();
    private final FloatBuffer projectionMatrixBuffer = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer modelviewMatrixBuffer = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer floatBuffer16 = GLAllocation.createDirectFloatBuffer(16);

    /**
     * Initialises the ClippingHelper object then returns an instance of it.
     */
    public static ClippingHelper getInstance()
    {
        instance.init();
        return instance;
    }

    private void normalize(float[] p_180547_1_)
    {
        float f = MathHelper.sqrt(p_180547_1_[0] * p_180547_1_[0] + p_180547_1_[1] * p_180547_1_[1] + p_180547_1_[2] * p_180547_1_[2]);
        p_180547_1_[0] /= f;
        p_180547_1_[1] /= f;
        p_180547_1_[2] /= f;
        p_180547_1_[3] /= f;
    }

    public void init()
    {
        this.projectionMatrixBuffer.clear();
        this.modelviewMatrixBuffer.clear();
        this.floatBuffer16.clear();
        GlStateManager.getFloat(2983, this.projectionMatrixBuffer);
        GlStateManager.getFloat(2982, this.modelviewMatrixBuffer);
        float[] afloat = this.projectionMatrix;
        float[] afloat1 = this.modelviewMatrix;
        this.projectionMatrixBuffer.flip().limit(16);
        this.projectionMatrixBuffer.get(afloat);
        this.modelviewMatrixBuffer.flip().limit(16);
        this.modelviewMatrixBuffer.get(afloat1);
        this.clippingMatrix[0] = afloat1[0] * afloat[0] + afloat1[1] * afloat[4] + afloat1[2] * afloat[8] + afloat1[3] * afloat[12];
        this.clippingMatrix[1] = afloat1[0] * afloat[1] + afloat1[1] * afloat[5] + afloat1[2] * afloat[9] + afloat1[3] * afloat[13];
        this.clippingMatrix[2] = afloat1[0] * afloat[2] + afloat1[1] * afloat[6] + afloat1[2] * afloat[10] + afloat1[3] * afloat[14];
        this.clippingMatrix[3] = afloat1[0] * afloat[3] + afloat1[1] * afloat[7] + afloat1[2] * afloat[11] + afloat1[3] * afloat[15];
        this.clippingMatrix[4] = afloat1[4] * afloat[0] + afloat1[5] * afloat[4] + afloat1[6] * afloat[8] + afloat1[7] * afloat[12];
        this.clippingMatrix[5] = afloat1[4] * afloat[1] + afloat1[5] * afloat[5] + afloat1[6] * afloat[9] + afloat1[7] * afloat[13];
        this.clippingMatrix[6] = afloat1[4] * afloat[2] + afloat1[5] * afloat[6] + afloat1[6] * afloat[10] + afloat1[7] * afloat[14];
        this.clippingMatrix[7] = afloat1[4] * afloat[3] + afloat1[5] * afloat[7] + afloat1[6] * afloat[11] + afloat1[7] * afloat[15];
        this.clippingMatrix[8] = afloat1[8] * afloat[0] + afloat1[9] * afloat[4] + afloat1[10] * afloat[8] + afloat1[11] * afloat[12];
        this.clippingMatrix[9] = afloat1[8] * afloat[1] + afloat1[9] * afloat[5] + afloat1[10] * afloat[9] + afloat1[11] * afloat[13];
        this.clippingMatrix[10] = afloat1[8] * afloat[2] + afloat1[9] * afloat[6] + afloat1[10] * afloat[10] + afloat1[11] * afloat[14];
        this.clippingMatrix[11] = afloat1[8] * afloat[3] + afloat1[9] * afloat[7] + afloat1[10] * afloat[11] + afloat1[11] * afloat[15];
        this.clippingMatrix[12] = afloat1[12] * afloat[0] + afloat1[13] * afloat[4] + afloat1[14] * afloat[8] + afloat1[15] * afloat[12];
        this.clippingMatrix[13] = afloat1[12] * afloat[1] + afloat1[13] * afloat[5] + afloat1[14] * afloat[9] + afloat1[15] * afloat[13];
        this.clippingMatrix[14] = afloat1[12] * afloat[2] + afloat1[13] * afloat[6] + afloat1[14] * afloat[10] + afloat1[15] * afloat[14];
        this.clippingMatrix[15] = afloat1[12] * afloat[3] + afloat1[13] * afloat[7] + afloat1[14] * afloat[11] + afloat1[15] * afloat[15];
        float[] afloat2 = this.frustum[0];
        afloat2[0] = this.clippingMatrix[3] - this.clippingMatrix[0];
        afloat2[1] = this.clippingMatrix[7] - this.clippingMatrix[4];
        afloat2[2] = this.clippingMatrix[11] - this.clippingMatrix[8];
        afloat2[3] = this.clippingMatrix[15] - this.clippingMatrix[12];
        this.normalize(afloat2);
        float[] afloat3 = this.frustum[1];
        afloat3[0] = this.clippingMatrix[3] + this.clippingMatrix[0];
        afloat3[1] = this.clippingMatrix[7] + this.clippingMatrix[4];
        afloat3[2] = this.clippingMatrix[11] + this.clippingMatrix[8];
        afloat3[3] = this.clippingMatrix[15] + this.clippingMatrix[12];
        this.normalize(afloat3);
        float[] afloat4 = this.frustum[2];
        afloat4[0] = this.clippingMatrix[3] + this.clippingMatrix[1];
        afloat4[1] = this.clippingMatrix[7] + this.clippingMatrix[5];
        afloat4[2] = this.clippingMatrix[11] + this.clippingMatrix[9];
        afloat4[3] = this.clippingMatrix[15] + this.clippingMatrix[13];
        this.normalize(afloat4);
        float[] afloat5 = this.frustum[3];
        afloat5[0] = this.clippingMatrix[3] - this.clippingMatrix[1];
        afloat5[1] = this.clippingMatrix[7] - this.clippingMatrix[5];
        afloat5[2] = this.clippingMatrix[11] - this.clippingMatrix[9];
        afloat5[3] = this.clippingMatrix[15] - this.clippingMatrix[13];
        this.normalize(afloat5);
        float[] afloat6 = this.frustum[4];
        afloat6[0] = this.clippingMatrix[3] - this.clippingMatrix[2];
        afloat6[1] = this.clippingMatrix[7] - this.clippingMatrix[6];
        afloat6[2] = this.clippingMatrix[11] - this.clippingMatrix[10];
        afloat6[3] = this.clippingMatrix[15] - this.clippingMatrix[14];
        this.normalize(afloat6);
        float[] afloat7 = this.frustum[5];
        afloat7[0] = this.clippingMatrix[3] + this.clippingMatrix[2];
        afloat7[1] = this.clippingMatrix[7] + this.clippingMatrix[6];
        afloat7[2] = this.clippingMatrix[11] + this.clippingMatrix[10];
        afloat7[3] = this.clippingMatrix[15] + this.clippingMatrix[14];
        this.normalize(afloat7);
    }
}
