package net.minecraft.entity;

public interface IProjectile
{
    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy);
}
