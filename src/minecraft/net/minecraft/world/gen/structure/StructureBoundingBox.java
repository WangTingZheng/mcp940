package net.minecraft.world.gen.structure;

import com.google.common.base.MoreObjects;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;

public class StructureBoundingBox
{
    /** The first x coordinate of a bounding box. */
    public int minX;

    /** The first y coordinate of a bounding box. */
    public int minY;

    /** The first z coordinate of a bounding box. */
    public int minZ;

    /** The second x coordinate of a bounding box. */
    public int maxX;

    /** The second y coordinate of a bounding box. */
    public int maxY;

    /** The second z coordinate of a bounding box. */
    public int maxZ;

    public StructureBoundingBox()
    {
    }

    public StructureBoundingBox(int[] coords)
    {
        if (coords.length == 6)
        {
            this.minX = coords[0];
            this.minY = coords[1];
            this.minZ = coords[2];
            this.maxX = coords[3];
            this.maxY = coords[4];
            this.maxZ = coords[5];
        }
    }

    /**
     * returns a new StructureBoundingBox with MAX values
     */
    public static StructureBoundingBox getNewBoundingBox()
    {
        return new StructureBoundingBox(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    /**
     * Create a bounding box with the specified dimensions and rotate it. Used to project a possible new component
     * Bounding Box - to check if it would cut anything already spawned
     */
    public static StructureBoundingBox getComponentToAddBoundingBox(int structureMinX, int structureMinY, int structureMinZ, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, EnumFacing facing)
    {
        switch (facing)
        {
            case NORTH:
                return new StructureBoundingBox(structureMinX + xMin, structureMinY + yMin, structureMinZ - zMax + 1 + zMin, structureMinX + xMax - 1 + xMin, structureMinY + yMax - 1 + yMin, structureMinZ + zMin);

            case SOUTH:
                return new StructureBoundingBox(structureMinX + xMin, structureMinY + yMin, structureMinZ + zMin, structureMinX + xMax - 1 + xMin, structureMinY + yMax - 1 + yMin, structureMinZ + zMax - 1 + zMin);

            case WEST:
                return new StructureBoundingBox(structureMinX - zMax + 1 + zMin, structureMinY + yMin, structureMinZ + xMin, structureMinX + zMin, structureMinY + yMax - 1 + yMin, structureMinZ + xMax - 1 + xMin);

            case EAST:
                return new StructureBoundingBox(structureMinX + zMin, structureMinY + yMin, structureMinZ + xMin, structureMinX + zMax - 1 + zMin, structureMinY + yMax - 1 + yMin, structureMinZ + xMax - 1 + xMin);

            default:
                return new StructureBoundingBox(structureMinX + xMin, structureMinY + yMin, structureMinZ + zMin, structureMinX + xMax - 1 + xMin, structureMinY + yMax - 1 + yMin, structureMinZ + zMax - 1 + zMin);
        }
    }

    public static StructureBoundingBox createProper(int x1, int y1, int z1, int x2, int y2, int z2)
    {
        return new StructureBoundingBox(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2), Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));
    }

    public StructureBoundingBox(StructureBoundingBox structurebb)
    {
        this.minX = structurebb.minX;
        this.minY = structurebb.minY;
        this.minZ = structurebb.minZ;
        this.maxX = structurebb.maxX;
        this.maxY = structurebb.maxY;
        this.maxZ = structurebb.maxZ;
    }

    public StructureBoundingBox(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax)
    {
        this.minX = xMin;
        this.minY = yMin;
        this.minZ = zMin;
        this.maxX = xMax;
        this.maxY = yMax;
        this.maxZ = zMax;
    }

    public StructureBoundingBox(Vec3i vec1, Vec3i vec2)
    {
        this.minX = Math.min(vec1.getX(), vec2.getX());
        this.minY = Math.min(vec1.getY(), vec2.getY());
        this.minZ = Math.min(vec1.getZ(), vec2.getZ());
        this.maxX = Math.max(vec1.getX(), vec2.getX());
        this.maxY = Math.max(vec1.getY(), vec2.getY());
        this.maxZ = Math.max(vec1.getZ(), vec2.getZ());
    }

    public StructureBoundingBox(int xMin, int zMin, int xMax, int zMax)
    {
        this.minX = xMin;
        this.minZ = zMin;
        this.maxX = xMax;
        this.maxZ = zMax;
        this.minY = 1;
        this.maxY = 512;
    }

    /**
     * Discover if bounding box can fit within the current bounding box object.
     */
    public boolean intersectsWith(StructureBoundingBox structurebb)
    {
        return this.maxX >= structurebb.minX && this.minX <= structurebb.maxX && this.maxZ >= structurebb.minZ && this.minZ <= structurebb.maxZ && this.maxY >= structurebb.minY && this.minY <= structurebb.maxY;
    }

    /**
     * Discover if a coordinate is inside the bounding box area.
     */
    public boolean intersectsWith(int minXIn, int minZIn, int maxXIn, int maxZIn)
    {
        return this.maxX >= minXIn && this.minX <= maxXIn && this.maxZ >= minZIn && this.minZ <= maxZIn;
    }

    /**
     * Expands a bounding box's dimensions to include the supplied bounding box.
     */
    public void expandTo(StructureBoundingBox sbb)
    {
        this.minX = Math.min(this.minX, sbb.minX);
        this.minY = Math.min(this.minY, sbb.minY);
        this.minZ = Math.min(this.minZ, sbb.minZ);
        this.maxX = Math.max(this.maxX, sbb.maxX);
        this.maxY = Math.max(this.maxY, sbb.maxY);
        this.maxZ = Math.max(this.maxZ, sbb.maxZ);
    }

    /**
     * Offsets the current bounding box by the specified amount.
     */
    public void offset(int x, int y, int z)
    {
        this.minX += x;
        this.minY += y;
        this.minZ += z;
        this.maxX += x;
        this.maxY += y;
        this.maxZ += z;
    }

    /**
     * Checks if given Vec3i is inside of StructureBoundingBox
     */
    public boolean isVecInside(Vec3i vec)
    {
        return vec.getX() >= this.minX && vec.getX() <= this.maxX && vec.getZ() >= this.minZ && vec.getZ() <= this.maxZ && vec.getY() >= this.minY && vec.getY() <= this.maxY;
    }

    public Vec3i getLength()
    {
        return new Vec3i(this.maxX - this.minX, this.maxY - this.minY, this.maxZ - this.minZ);
    }

    /**
     * Get dimension of the bounding box in the x direction.
     */
    public int getXSize()
    {
        return this.maxX - this.minX + 1;
    }

    /**
     * Get dimension of the bounding box in the y direction.
     */
    public int getYSize()
    {
        return this.maxY - this.minY + 1;
    }

    /**
     * Get dimension of the bounding box in the z direction.
     */
    public int getZSize()
    {
        return this.maxZ - this.minZ + 1;
    }

    public String toString()
    {
        return MoreObjects.toStringHelper(this).add("x0", this.minX).add("y0", this.minY).add("z0", this.minZ).add("x1", this.maxX).add("y1", this.maxY).add("z1", this.maxZ).toString();
    }

    public NBTTagIntArray toNBTTagIntArray()
    {
        return new NBTTagIntArray(new int[] {this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ});
    }
}
