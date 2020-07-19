package net.minecraft.src;

import net.minecraft.util.EnumFacing;

public class QuadBounds
{
    private float minX = Float.MAX_VALUE;
    private float minY = Float.MAX_VALUE;
    private float minZ = Float.MAX_VALUE;
    private float maxX = -3.4028235E38F;
    private float maxY = -3.4028235E38F;
    private float maxZ = -3.4028235E38F;

    public QuadBounds(int[] p_i73_1_)
    {
        int i = p_i73_1_.length / 4;

        for (int j = 0; j < 4; ++j)
        {
            int k = j * i;
            float f = Float.intBitsToFloat(p_i73_1_[k + 0]);
            float f1 = Float.intBitsToFloat(p_i73_1_[k + 1]);
            float f2 = Float.intBitsToFloat(p_i73_1_[k + 2]);

            if (this.minX > f)
            {
                this.minX = f;
            }

            if (this.minY > f1)
            {
                this.minY = f1;
            }

            if (this.minZ > f2)
            {
                this.minZ = f2;
            }

            if (this.maxX < f)
            {
                this.maxX = f;
            }

            if (this.maxY < f1)
            {
                this.maxY = f1;
            }

            if (this.maxZ < f2)
            {
                this.maxZ = f2;
            }
        }
    }

    public float getMinX()
    {
        return this.minX;
    }

    public float getMinY()
    {
        return this.minY;
    }

    public float getMinZ()
    {
        return this.minZ;
    }

    public float getMaxX()
    {
        return this.maxX;
    }

    public float getMaxY()
    {
        return this.maxY;
    }

    public float getMaxZ()
    {
        return this.maxZ;
    }

    public boolean isFaceQuad(EnumFacing p_isFaceQuad_1_)
    {
        float f;
        float f1;
        float f2;

        switch (p_isFaceQuad_1_)
        {
            case DOWN:
                f = this.getMinY();
                f1 = this.getMaxY();
                f2 = 0.0F;
                break;

            case UP:
                f = this.getMinY();
                f1 = this.getMaxY();
                f2 = 1.0F;
                break;

            case NORTH:
                f = this.getMinZ();
                f1 = this.getMaxZ();
                f2 = 0.0F;
                break;

            case SOUTH:
                f = this.getMinZ();
                f1 = this.getMaxZ();
                f2 = 1.0F;
                break;

            case WEST:
                f = this.getMinX();
                f1 = this.getMaxX();
                f2 = 0.0F;
                break;

            case EAST:
                f = this.getMinX();
                f1 = this.getMaxX();
                f2 = 1.0F;
                break;

            default:
                return false;
        }

        return f == f2 && f1 == f2;
    }

    public boolean isFullQuad(EnumFacing p_isFullQuad_1_)
    {
        float f;
        float f1;
        float f2;
        float f3;

        switch (p_isFullQuad_1_)
        {
            case DOWN:
            case UP:
                f = this.getMinX();
                f1 = this.getMaxX();
                f2 = this.getMinZ();
                f3 = this.getMaxZ();
                break;

            case NORTH:
            case SOUTH:
                f = this.getMinX();
                f1 = this.getMaxX();
                f2 = this.getMinY();
                f3 = this.getMaxY();
                break;

            case WEST:
            case EAST:
                f = this.getMinY();
                f1 = this.getMaxY();
                f2 = this.getMinZ();
                f3 = this.getMaxZ();
                break;

            default:
                return false;
        }

        return f == 0.0F && f1 == 1.0F && f2 == 0.0F && f3 == 1.0F;
    }
}
