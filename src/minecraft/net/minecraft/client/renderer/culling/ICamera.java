package net.minecraft.client.renderer.culling;

import net.minecraft.util.math.AxisAlignedBB;

public interface ICamera
{
    /**
     * Returns true if the bounding box is inside all 6 clipping planes, otherwise returns false.
     */
    boolean isBoundingBoxInFrustum(AxisAlignedBB p_78546_1_);

    void setPosition(double xIn, double yIn, double zIn);
}
