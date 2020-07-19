package net.minecraft.util.math;

import com.google.common.annotations.VisibleForTesting;
import javax.annotation.Nullable;
import net.minecraft.util.EnumFacing;

public class AxisAlignedBB
{
    /**
     * The minimum X coordinate of this bounding box. Guaranteed to always be less than or equal to {@link #maxX}.
     */
    public final double minX;

    /**
     * The minimum Y coordinate of this bounding box. Guaranteed to always be less than or equal to {@link #maxY}.
     */
    public final double minY;

    /**
     * The minimum Y coordinate of this bounding box. Guaranteed to always be less than or equal to {@link #maxZ}.
     */
    public final double minZ;

    /**
     * The maximum X coordinate of this bounding box. Guaranteed to always be greater than or equal to {@link #minX}.
     */
    public final double maxX;

    /**
     * The maximum Y coordinate of this bounding box. Guaranteed to always be greater than or equal to {@link #minY}.
     */
    public final double maxY;

    /**
     * The maximum Z coordinate of this bounding box. Guaranteed to always be greater than or equal to {@link #minZ}.
     */
    public final double maxZ;

    public AxisAlignedBB(double x1, double y1, double z1, double x2, double y2, double z2)
    {
        this.minX = Math.min(x1, x2);
        this.minY = Math.min(y1, y2);
        this.minZ = Math.min(z1, z2);
        this.maxX = Math.max(x1, x2);
        this.maxY = Math.max(y1, y2);
        this.maxZ = Math.max(z1, z2);
    }

    public AxisAlignedBB(BlockPos pos)
    {
        this((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(pos.getX() + 1), (double)(pos.getY() + 1), (double)(pos.getZ() + 1));
    }

    public AxisAlignedBB(BlockPos pos1, BlockPos pos2)
    {
        this((double)pos1.getX(), (double)pos1.getY(), (double)pos1.getZ(), (double)pos2.getX(), (double)pos2.getY(), (double)pos2.getZ());
    }

    public AxisAlignedBB(Vec3d min, Vec3d max)
    {
        this(min.x, min.y, min.z, max.x, max.y, max.z);
    }

    /**
     * Helper method that returns a new {@link AxisAlignedBB} with the given value for {@link #maxY} and all other
     * values taken from this bounding box.
     */
    public AxisAlignedBB setMaxY(double y2)
    {
        return new AxisAlignedBB(this.minX, this.minY, this.minZ, this.maxX, y2, this.maxZ);
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (!(p_equals_1_ instanceof AxisAlignedBB))
        {
            return false;
        }
        else
        {
            AxisAlignedBB axisalignedbb = (AxisAlignedBB)p_equals_1_;

            if (Double.compare(axisalignedbb.minX, this.minX) != 0)
            {
                return false;
            }
            else if (Double.compare(axisalignedbb.minY, this.minY) != 0)
            {
                return false;
            }
            else if (Double.compare(axisalignedbb.minZ, this.minZ) != 0)
            {
                return false;
            }
            else if (Double.compare(axisalignedbb.maxX, this.maxX) != 0)
            {
                return false;
            }
            else if (Double.compare(axisalignedbb.maxY, this.maxY) != 0)
            {
                return false;
            }
            else
            {
                return Double.compare(axisalignedbb.maxZ, this.maxZ) == 0;
            }
        }
    }

    public int hashCode()
    {
        long i = Double.doubleToLongBits(this.minX);
        int j = (int)(i ^ i >>> 32);
        i = Double.doubleToLongBits(this.minY);
        j = 31 * j + (int)(i ^ i >>> 32);
        i = Double.doubleToLongBits(this.minZ);
        j = 31 * j + (int)(i ^ i >>> 32);
        i = Double.doubleToLongBits(this.maxX);
        j = 31 * j + (int)(i ^ i >>> 32);
        i = Double.doubleToLongBits(this.maxY);
        j = 31 * j + (int)(i ^ i >>> 32);
        i = Double.doubleToLongBits(this.maxZ);
        j = 31 * j + (int)(i ^ i >>> 32);
        return j;
    }

    /**
     * Creates a new {@link AxisAlignedBB} that has been contracted by the given amount, with positive changes
     * decreasing max values and negative changes increasing min values.
     * <br/>
     * If the amount to contract by is larger than the length of a side, then the side will wrap (still creating a valid
     * AABB - see last sample).
     *  
     * <h3>Samples:</h3>
     * <table>
     * <tr><th>Input</th><th>Result</th></tr>
     * <tr><td><pre><code>new AxisAlignedBB(0, 0, 0, 4, 4, 4).contract(2, 2, 2)</code></pre></td><td><pre><samp>box[0.0,
     * 0.0, 0.0 -> 2.0, 2.0, 2.0]</samp></pre></td></tr>
     * <tr><td><pre><code>new AxisAlignedBB(0, 0, 0, 4, 4, 4).contract(-2, -2,
     * -2)</code></pre></td><td><pre><samp>box[2.0, 2.0, 2.0 -> 4.0, 4.0, 4.0]</samp></pre></td></tr>
     * <tr><td><pre><code>new AxisAlignedBB(5, 5, 5, 7, 7, 7).contract(0, 1,
     * -1)</code></pre></td><td><pre><samp>box[5.0, 5.0, 6.0 -> 7.0, 6.0, 7.0]</samp></pre></td></tr>
     * <tr><td><pre><code>new AxisAlignedBB(-2, -2, -2, 2, 2, 2).contract(4, -4,
     * 0)</code></pre></td><td><pre><samp>box[-8.0, 2.0, -2.0 -> -2.0, 8.0, 2.0]</samp></pre></td></tr>
     * </table>
     *  
     * <h3>See Also:</h3>
     * <ul>
     * <li>{@link #expand(double, double, double)} - like this, except for expanding.</li>
     * <li>{@link #grow(double, double, double)} and {@link #grow(double)} - expands in all directions.</li>
     * <li>{@link #shrink(double)} - contracts in all directions (like {@link #grow(double)})</li>
     * </ul>
     *  
     * @return A new modified bounding box.
     */
    public AxisAlignedBB contract(double x, double y, double z)
    {
        double d0 = this.minX;
        double d1 = this.minY;
        double d2 = this.minZ;
        double d3 = this.maxX;
        double d4 = this.maxY;
        double d5 = this.maxZ;

        if (x < 0.0D)
        {
            d0 -= x;
        }
        else if (x > 0.0D)
        {
            d3 -= x;
        }

        if (y < 0.0D)
        {
            d1 -= y;
        }
        else if (y > 0.0D)
        {
            d4 -= y;
        }

        if (z < 0.0D)
        {
            d2 -= z;
        }
        else if (z > 0.0D)
        {
            d5 -= z;
        }

        return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
    }

    /**
     * Creates a new {@link AxisAlignedBB} that has been expanded by the given amount, with positive changes increasing
     * max values and negative changes decreasing min values.

     * <h3>Samples:</h3>
     * <table>
     * <tr><th>Input</th><th>Result</th></tr>
     * <tr><td><pre><code>new AxisAlignedBB(0, 0, 0, 1, 1, 1).expand(2, 2, 2)</code></pre></td><td><pre><samp>box[0, 0,
     * 0 -> 3, 3, 3]</samp></pre></td><td>
     * <tr><td><pre><code>new AxisAlignedBB(0, 0, 0, 1, 1, 1).expand(-2, -2, -2)</code></pre></td><td><pre><samp>box[-2,
     * -2, -2 -> 1, 1, 1]</samp></pre></td><td>
     * <tr><td><pre><code>new AxisAlignedBB(5, 5, 5, 7, 7, 7).expand(0, 1, -1)</code></pre></td><td><pre><samp>box[5, 5,
     * 4, 7, 8, 7]</samp></pre></td><td>
     * </table>

     * <h3>See Also:</h3>
     * <ul>
     * <li>{@link #contract(double, double, double)} - like this, except for shrinking.</li>
     * <li>{@link #grow(double, double, double)} and {@link #grow(double)} - expands in all directions.</li>
     * <li>{@link #shrink(double)} - contracts in all directions (like {@link #grow(double)})</li>
     * </ul>

     * @return A modified bounding box that will always be equal or greater in volume to this bounding box.
     */
    public AxisAlignedBB expand(double x, double y, double z)
    {
        double d0 = this.minX;
        double d1 = this.minY;
        double d2 = this.minZ;
        double d3 = this.maxX;
        double d4 = this.maxY;
        double d5 = this.maxZ;

        if (x < 0.0D)
        {
            d0 += x;
        }
        else if (x > 0.0D)
        {
            d3 += x;
        }

        if (y < 0.0D)
        {
            d1 += y;
        }
        else if (y > 0.0D)
        {
            d4 += y;
        }

        if (z < 0.0D)
        {
            d2 += z;
        }
        else if (z > 0.0D)
        {
            d5 += z;
        }

        return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
    }

    /**
     * Creates a new {@link AxisAlignedBB} that has been contracted by the given amount in both directions. Negative
     * values will shrink the AABB instead of expanding it.
     * <br/>
     * Side lengths will be increased by 2 times the value of the parameters, since both min and max are changed.
     * <br/>
     * If contracting and the amount to contract by is larger than the length of a side, then the side will wrap (still
     * creating a valid AABB - see last ample).
     *  
     * <h3>Samples:</h3>
     * <table>
     * <tr><th>Input</th><th>Result</th></tr>
     * <tr><td><pre><code>new AxisAlignedBB(0, 0, 0, 1, 1, 1).grow(2, 2, 2)</code></pre></td><td><pre><samp>box[-2.0,
     * -2.0, -2.0 -> 3.0, 3.0, 3.0]</samp></pre></td></tr>
     * <tr><td><pre><code>new AxisAlignedBB(0, 0, 0, 6, 6, 6).grow(-2, -2, -2)</code></pre></td><td><pre><samp>box[2.0,
     * 2.0, 2.0 -> 4.0, 4.0, 4.0]</samp></pre></td></tr>
     * <tr><td><pre><code>new AxisAlignedBB(5, 5, 5, 7, 7, 7).grow(0, 1, -1)</code></pre></td><td><pre><samp>box[5.0,
     * 4.0, 6.0 -> 7.0, 8.0, 6.0]</samp></pre></td></tr>
     * <tr><td><pre><code>new AxisAlignedBB(1, 1, 1, 3, 3, 3).grow(-4, -2, -3)</code></pre></td><td><pre><samp>box[-1.0,
     * 1.0, 0.0 -> 5.0, 3.0, 4.0]</samp></pre></td></tr>
     * </table>
     *  
     * <h3>See Also:</h3>
     * <ul>
     * <li>{@link #expand(double, double, double)} - expands in only one direction.</li>
     * <li>{@link #contract(double, double, double)} - contracts in only one direction.</li>
     * <lu>{@link #grow(double)} - version of this that expands in all directions from one parameter.</li>
     * <li>{@link #shrink(double)} - contracts in all directions</li>
     * </ul>
     *  
     * @return A modified bounding box.
     */
    public AxisAlignedBB grow(double x, double y, double z)
    {
        double d0 = this.minX - x;
        double d1 = this.minY - y;
        double d2 = this.minZ - z;
        double d3 = this.maxX + x;
        double d4 = this.maxY + y;
        double d5 = this.maxZ + z;
        return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
    }

    /**
     * Creates a new {@link AxisAlignedBB} that is expanded by the given value in all directions. Equivalent to {@link
     * #grow(double, double, double)} with the given value for all 3 params. Negative values will shrink the AABB.
     * <br/>
     * Side lengths will be increased by 2 times the value of the parameter, since both min and max are changed.
     * <br/>
     * If contracting and the amount to contract by is larger than the length of a side, then the side will wrap (still
     * creating a valid AABB - see samples on {@link #grow(double, double, double)}).
     *  
     * @return A modified AABB.
     */
    public AxisAlignedBB grow(double value)
    {
        return this.grow(value, value, value);
    }

    public AxisAlignedBB intersect(AxisAlignedBB p_191500_1_)
    {
        double d0 = Math.max(this.minX, p_191500_1_.minX);
        double d1 = Math.max(this.minY, p_191500_1_.minY);
        double d2 = Math.max(this.minZ, p_191500_1_.minZ);
        double d3 = Math.min(this.maxX, p_191500_1_.maxX);
        double d4 = Math.min(this.maxY, p_191500_1_.maxY);
        double d5 = Math.min(this.maxZ, p_191500_1_.maxZ);
        return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
    }

    public AxisAlignedBB union(AxisAlignedBB other)
    {
        double d0 = Math.min(this.minX, other.minX);
        double d1 = Math.min(this.minY, other.minY);
        double d2 = Math.min(this.minZ, other.minZ);
        double d3 = Math.max(this.maxX, other.maxX);
        double d4 = Math.max(this.maxY, other.maxY);
        double d5 = Math.max(this.maxZ, other.maxZ);
        return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
    }

    /**
     * Offsets the current bounding box by the specified amount.
     */
    public AxisAlignedBB offset(double x, double y, double z)
    {
        return new AxisAlignedBB(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
    }

    public AxisAlignedBB offset(BlockPos pos)
    {
        return new AxisAlignedBB(this.minX + (double)pos.getX(), this.minY + (double)pos.getY(), this.minZ + (double)pos.getZ(), this.maxX + (double)pos.getX(), this.maxY + (double)pos.getY(), this.maxZ + (double)pos.getZ());
    }

    public AxisAlignedBB offset(Vec3d p_191194_1_)
    {
        return this.offset(p_191194_1_.x, p_191194_1_.y, p_191194_1_.z);
    }

    /**
     * if instance and the argument bounding boxes overlap in the Y and Z dimensions, calculate the offset between them
     * in the X dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public double calculateXOffset(AxisAlignedBB other, double offsetX)
    {
        if (other.maxY > this.minY && other.minY < this.maxY && other.maxZ > this.minZ && other.minZ < this.maxZ)
        {
            if (offsetX > 0.0D && other.maxX <= this.minX)
            {
                double d1 = this.minX - other.maxX;

                if (d1 < offsetX)
                {
                    offsetX = d1;
                }
            }
            else if (offsetX < 0.0D && other.minX >= this.maxX)
            {
                double d0 = this.maxX - other.minX;

                if (d0 > offsetX)
                {
                    offsetX = d0;
                }
            }

            return offsetX;
        }
        else
        {
            return offsetX;
        }
    }

    /**
     * if instance and the argument bounding boxes overlap in the X and Z dimensions, calculate the offset between them
     * in the Y dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public double calculateYOffset(AxisAlignedBB other, double offsetY)
    {
        if (other.maxX > this.minX && other.minX < this.maxX && other.maxZ > this.minZ && other.minZ < this.maxZ)
        {
            if (offsetY > 0.0D && other.maxY <= this.minY)
            {
                double d1 = this.minY - other.maxY;

                if (d1 < offsetY)
                {
                    offsetY = d1;
                }
            }
            else if (offsetY < 0.0D && other.minY >= this.maxY)
            {
                double d0 = this.maxY - other.minY;

                if (d0 > offsetY)
                {
                    offsetY = d0;
                }
            }

            return offsetY;
        }
        else
        {
            return offsetY;
        }
    }

    /**
     * if instance and the argument bounding boxes overlap in the Y and X dimensions, calculate the offset between them
     * in the Z dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public double calculateZOffset(AxisAlignedBB other, double offsetZ)
    {
        if (other.maxX > this.minX && other.minX < this.maxX && other.maxY > this.minY && other.minY < this.maxY)
        {
            if (offsetZ > 0.0D && other.maxZ <= this.minZ)
            {
                double d1 = this.minZ - other.maxZ;

                if (d1 < offsetZ)
                {
                    offsetZ = d1;
                }
            }
            else if (offsetZ < 0.0D && other.minZ >= this.maxZ)
            {
                double d0 = this.maxZ - other.minZ;

                if (d0 > offsetZ)
                {
                    offsetZ = d0;
                }
            }

            return offsetZ;
        }
        else
        {
            return offsetZ;
        }
    }

    /**
     * Checks if the bounding box intersects with another.
     */
    public boolean intersects(AxisAlignedBB other)
    {
        return this.intersects(other.minX, other.minY, other.minZ, other.maxX, other.maxY, other.maxZ);
    }

    public boolean intersects(double x1, double y1, double z1, double x2, double y2, double z2)
    {
        return this.minX < x2 && this.maxX > x1 && this.minY < y2 && this.maxY > y1 && this.minZ < z2 && this.maxZ > z1;
    }

    public boolean intersects(Vec3d min, Vec3d max)
    {
        return this.intersects(Math.min(min.x, max.x), Math.min(min.y, max.y), Math.min(min.z, max.z), Math.max(min.x, max.x), Math.max(min.y, max.y), Math.max(min.z, max.z));
    }

    /**
     * Returns if the supplied Vec3D is completely inside the bounding box
     */
    public boolean contains(Vec3d vec)
    {
        if (vec.x > this.minX && vec.x < this.maxX)
        {
            if (vec.y > this.minY && vec.y < this.maxY)
            {
                return vec.z > this.minZ && vec.z < this.maxZ;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns the average length of the edges of the bounding box.
     */
    public double getAverageEdgeLength()
    {
        double d0 = this.maxX - this.minX;
        double d1 = this.maxY - this.minY;
        double d2 = this.maxZ - this.minZ;
        return (d0 + d1 + d2) / 3.0D;
    }

    /**
     * Creates a new {@link AxisAlignedBB} that is expanded by the given value in all directions. Equivalent to {@link
     * #grow(double)} with value set to the negative of the value provided here. Passing a negative value to this method
     * values will grow the AABB.
     * <br/>
     * Side lengths will be decreased by 2 times the value of the parameter, since both min and max are changed.
     * <br/>
     * If contracting and the amount to contract by is larger than the length of a side, then the side will wrap (still
     * creating a valid AABB - see samples on {@link #grow(double, double, double)}).
     *  
     * @return A modified AABB.
     */
    public AxisAlignedBB shrink(double value)
    {
        return this.grow(-value);
    }

    @Nullable
    public RayTraceResult calculateIntercept(Vec3d vecA, Vec3d vecB)
    {
        Vec3d vec3d = this.collideWithXPlane(this.minX, vecA, vecB);
        EnumFacing enumfacing = EnumFacing.WEST;
        Vec3d vec3d1 = this.collideWithXPlane(this.maxX, vecA, vecB);

        if (vec3d1 != null && this.isClosest(vecA, vec3d, vec3d1))
        {
            vec3d = vec3d1;
            enumfacing = EnumFacing.EAST;
        }

        vec3d1 = this.collideWithYPlane(this.minY, vecA, vecB);

        if (vec3d1 != null && this.isClosest(vecA, vec3d, vec3d1))
        {
            vec3d = vec3d1;
            enumfacing = EnumFacing.DOWN;
        }

        vec3d1 = this.collideWithYPlane(this.maxY, vecA, vecB);

        if (vec3d1 != null && this.isClosest(vecA, vec3d, vec3d1))
        {
            vec3d = vec3d1;
            enumfacing = EnumFacing.UP;
        }

        vec3d1 = this.collideWithZPlane(this.minZ, vecA, vecB);

        if (vec3d1 != null && this.isClosest(vecA, vec3d, vec3d1))
        {
            vec3d = vec3d1;
            enumfacing = EnumFacing.NORTH;
        }

        vec3d1 = this.collideWithZPlane(this.maxZ, vecA, vecB);

        if (vec3d1 != null && this.isClosest(vecA, vec3d, vec3d1))
        {
            vec3d = vec3d1;
            enumfacing = EnumFacing.SOUTH;
        }

        return vec3d == null ? null : new RayTraceResult(vec3d, enumfacing);
    }

    @VisibleForTesting
    boolean isClosest(Vec3d p_186661_1_, @Nullable Vec3d p_186661_2_, Vec3d p_186661_3_)
    {
        return p_186661_2_ == null || p_186661_1_.squareDistanceTo(p_186661_3_) < p_186661_1_.squareDistanceTo(p_186661_2_);
    }

    @Nullable
    @VisibleForTesting
    Vec3d collideWithXPlane(double p_186671_1_, Vec3d p_186671_3_, Vec3d p_186671_4_)
    {
        Vec3d vec3d = p_186671_3_.getIntermediateWithXValue(p_186671_4_, p_186671_1_);
        return vec3d != null && this.intersectsWithYZ(vec3d) ? vec3d : null;
    }

    @Nullable
    @VisibleForTesting
    Vec3d collideWithYPlane(double p_186663_1_, Vec3d p_186663_3_, Vec3d p_186663_4_)
    {
        Vec3d vec3d = p_186663_3_.getIntermediateWithYValue(p_186663_4_, p_186663_1_);
        return vec3d != null && this.intersectsWithXZ(vec3d) ? vec3d : null;
    }

    @Nullable
    @VisibleForTesting
    Vec3d collideWithZPlane(double p_186665_1_, Vec3d p_186665_3_, Vec3d p_186665_4_)
    {
        Vec3d vec3d = p_186665_3_.getIntermediateWithZValue(p_186665_4_, p_186665_1_);
        return vec3d != null && this.intersectsWithXY(vec3d) ? vec3d : null;
    }

    @VisibleForTesting
    public boolean intersectsWithYZ(Vec3d vec)
    {
        return vec.y >= this.minY && vec.y <= this.maxY && vec.z >= this.minZ && vec.z <= this.maxZ;
    }

    @VisibleForTesting
    public boolean intersectsWithXZ(Vec3d vec)
    {
        return vec.x >= this.minX && vec.x <= this.maxX && vec.z >= this.minZ && vec.z <= this.maxZ;
    }

    @VisibleForTesting
    public boolean intersectsWithXY(Vec3d vec)
    {
        return vec.x >= this.minX && vec.x <= this.maxX && vec.y >= this.minY && vec.y <= this.maxY;
    }

    public String toString()
    {
        return "box[" + this.minX + ", " + this.minY + ", " + this.minZ + " -> " + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
    }

    public boolean hasNaN()
    {
        return Double.isNaN(this.minX) || Double.isNaN(this.minY) || Double.isNaN(this.minZ) || Double.isNaN(this.maxX) || Double.isNaN(this.maxY) || Double.isNaN(this.maxZ);
    }

    public Vec3d getCenter()
    {
        return new Vec3d(this.minX + (this.maxX - this.minX) * 0.5D, this.minY + (this.maxY - this.minY) * 0.5D, this.minZ + (this.maxZ - this.minZ) * 0.5D);
    }
}
