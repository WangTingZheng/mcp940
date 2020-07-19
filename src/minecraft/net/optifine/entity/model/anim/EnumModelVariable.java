package net.optifine.entity.model.anim;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.src.Config;

public enum EnumModelVariable
{
    POS_X("tx"),
    POS_Y("ty"),
    POS_Z("tz"),
    ANGLE_X("rx"),
    ANGLE_Y("ry"),
    ANGLE_Z("rz"),
    OFFSET_X("ox"),
    OFFSET_Y("oy"),
    OFFSET_Z("oz"),
    SCALE_X("sx"),
    SCALE_Y("sy"),
    SCALE_Z("sz");

    private String name;
    public static EnumModelVariable[] VALUES = values();

    private EnumModelVariable(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public float getFloat(ModelRenderer mr)
    {
        switch (this)
        {
            case POS_X:
                return mr.rotationPointX;

            case POS_Y:
                return mr.rotationPointY;

            case POS_Z:
                return mr.rotationPointZ;

            case ANGLE_X:
                return mr.rotateAngleX;

            case ANGLE_Y:
                return mr.rotateAngleY;

            case ANGLE_Z:
                return mr.rotateAngleZ;

            case OFFSET_X:
                return mr.offsetX;

            case OFFSET_Y:
                return mr.offsetY;

            case OFFSET_Z:
                return mr.offsetZ;

            case SCALE_X:
                return mr.scaleX;

            case SCALE_Y:
                return mr.scaleY;

            case SCALE_Z:
                return mr.scaleZ;

            default:
                Config.warn("GetFloat not supported for: " + this);
                return 0.0F;
        }
    }

    public void setFloat(ModelRenderer mr, float val)
    {
        switch (this)
        {
            case POS_X:
                mr.rotationPointX = val;
                return;

            case POS_Y:
                mr.rotationPointY = val;
                return;

            case POS_Z:
                mr.rotationPointZ = val;
                return;

            case ANGLE_X:
                mr.rotateAngleX = val;
                return;

            case ANGLE_Y:
                mr.rotateAngleY = val;
                return;

            case ANGLE_Z:
                mr.rotateAngleZ = val;
                return;

            case OFFSET_X:
                mr.offsetX = val;
                return;

            case OFFSET_Y:
                mr.offsetY = val;
                return;

            case OFFSET_Z:
                mr.offsetZ = val;
                return;

            case SCALE_X:
                mr.scaleX = val;
                return;

            case SCALE_Y:
                mr.scaleY = val;
                return;

            case SCALE_Z:
                mr.scaleZ = val;
                return;

            default:
                Config.warn("SetFloat not supported for: " + this);
        }
    }

    public static EnumModelVariable parse(String str)
    {
        for (int i = 0; i < VALUES.length; ++i)
        {
            EnumModelVariable enummodelvariable = VALUES[i];

            if (enummodelvariable.getName().equals(str))
            {
                return enummodelvariable;
            }
        }

        return null;
    }
}
