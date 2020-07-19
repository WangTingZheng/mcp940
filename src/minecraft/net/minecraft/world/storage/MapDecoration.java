package net.minecraft.world.storage;

import net.minecraft.util.math.MathHelper;

public class MapDecoration
{
    private final MapDecoration.Type type;
    private byte x;
    private byte y;
    private byte rotation;

    public MapDecoration(MapDecoration.Type typeIn, byte xIn, byte yIn, byte rotationIn)
    {
        this.type = typeIn;
        this.x = xIn;
        this.y = yIn;
        this.rotation = rotationIn;
    }

    public byte getImage()
    {
        return this.type.getIcon();
    }

    public MapDecoration.Type getType()
    {
        return this.type;
    }

    public byte getX()
    {
        return this.x;
    }

    public byte getY()
    {
        return this.y;
    }

    public byte getRotation()
    {
        return this.rotation;
    }

    public boolean renderOnFrame()
    {
        return this.type.isRenderedOnFrame();
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (!(p_equals_1_ instanceof MapDecoration))
        {
            return false;
        }
        else
        {
            MapDecoration mapdecoration = (MapDecoration)p_equals_1_;

            if (this.type != mapdecoration.type)
            {
                return false;
            }
            else if (this.rotation != mapdecoration.rotation)
            {
                return false;
            }
            else if (this.x != mapdecoration.x)
            {
                return false;
            }
            else
            {
                return this.y == mapdecoration.y;
            }
        }
    }

    public int hashCode()
    {
        int i = this.type.getIcon();
        i = 31 * i + this.x;
        i = 31 * i + this.y;
        i = 31 * i + this.rotation;
        return i;
    }

    public static enum Type
    {
        PLAYER(false),
        FRAME(true),
        RED_MARKER(false),
        BLUE_MARKER(false),
        TARGET_X(true),
        TARGET_POINT(true),
        PLAYER_OFF_MAP(false),
        PLAYER_OFF_LIMITS(false),
        MANSION(true, 5393476),
        MONUMENT(true, 3830373);

        private final byte icon;
        private final boolean renderedOnFrame;
        private final int mapColor;

        private Type(boolean p_i47343_3_)
        {
            this(p_i47343_3_, -1);
        }

        private Type(boolean p_i47344_3_, int p_i47344_4_)
        {
            this.icon = (byte)this.ordinal();
            this.renderedOnFrame = p_i47344_3_;
            this.mapColor = p_i47344_4_;
        }

        public byte getIcon()
        {
            return this.icon;
        }

        public boolean isRenderedOnFrame()
        {
            return this.renderedOnFrame;
        }

        public boolean hasMapColor()
        {
            return this.mapColor >= 0;
        }

        public int getMapColor()
        {
            return this.mapColor;
        }

        public static MapDecoration.Type byIcon(byte p_191159_0_)
        {
            return values()[MathHelper.clamp(p_191159_0_, 0, values().length - 1)];
        }
    }
}
