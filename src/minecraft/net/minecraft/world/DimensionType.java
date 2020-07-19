package net.minecraft.world;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public enum DimensionType
{
    OVERWORLD(0, "overworld", "", WorldProviderSurface.class),
    NETHER(-1, "the_nether", "_nether", WorldProviderHell.class),
    THE_END(1, "the_end", "_end", WorldProviderEnd.class);

    private final int id;
    private final String name;
    private final String suffix;
    private final Class <? extends WorldProvider > clazz;

    private DimensionType(int idIn, String nameIn, String suffixIn, Class <? extends WorldProvider > clazzIn)
    {
        this.id = idIn;
        this.name = nameIn;
        this.suffix = suffixIn;
        this.clazz = clazzIn;
    }

    public int getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getSuffix()
    {
        return this.suffix;
    }

    public WorldProvider createDimension()
    {
        try
        {
            Constructor <? extends WorldProvider > constructor = this.clazz.getConstructor();
            return constructor.newInstance();
        }
        catch (NoSuchMethodException nosuchmethodexception)
        {
            throw new Error("Could not create new dimension", nosuchmethodexception);
        }
        catch (InvocationTargetException invocationtargetexception)
        {
            throw new Error("Could not create new dimension", invocationtargetexception);
        }
        catch (InstantiationException instantiationexception)
        {
            throw new Error("Could not create new dimension", instantiationexception);
        }
        catch (IllegalAccessException illegalaccessexception)
        {
            throw new Error("Could not create new dimension", illegalaccessexception);
        }
    }

    public static DimensionType getById(int id)
    {
        for (DimensionType dimensiontype : values())
        {
            if (dimensiontype.getId() == id)
            {
                return dimensiontype;
            }
        }

        throw new IllegalArgumentException("Invalid dimension id " + id);
    }

    public static DimensionType byName(String p_193417_0_)
    {
        for (DimensionType dimensiontype : values())
        {
            if (dimensiontype.getName().equals(p_193417_0_))
            {
                return dimensiontype;
            }
        }

        throw new IllegalArgumentException("Invalid dimension " + p_193417_0_);
    }
}
