package net.minecraft.util.math;

public class Vec2f
{
    /** An immutable vector with {@code 0.0F} as the x and y components. */
    public static final Vec2f ZERO = new Vec2f(0.0F, 0.0F);

    /** An immutable vector with {@code 1.0F} as the x and y components. */
    public static final Vec2f ONE = new Vec2f(1.0F, 1.0F);

    /** An immutable vector with {@code 1.0F} as the x component. */
    public static final Vec2f UNIT_X = new Vec2f(1.0F, 0.0F);

    /** An immutable vector with {@code -1.0F} as the x component. */
    public static final Vec2f NEGATIVE_UNIT_X = new Vec2f(-1.0F, 0.0F);

    /** An immutable vector with {@code 1.0F} as the y component. */
    public static final Vec2f UNIT_Y = new Vec2f(0.0F, 1.0F);

    /** An immutable vector with {@code -1.0F} as the y component. */
    public static final Vec2f NEGATIVE_UNIT_Y = new Vec2f(0.0F, -1.0F);

    /**
     * An immutable vector with {@link Float#MAX_VALUE} as the x and y components.
     */
    public static final Vec2f MAX = new Vec2f(Float.MAX_VALUE, Float.MAX_VALUE);

    /**
     * An immutable vector with {@link Float#MIN_VALUE} as the x and y components.
     */
    public static final Vec2f MIN = new Vec2f(Float.MIN_VALUE, Float.MIN_VALUE);

    /** The x component of this vector. */
    public final float x;

    /** The y component of this vector. */
    public final float y;

    public Vec2f(float xIn, float yIn)
    {
        this.x = xIn;
        this.y = yIn;
    }
}
