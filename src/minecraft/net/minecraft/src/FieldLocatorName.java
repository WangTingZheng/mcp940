package net.minecraft.src;

import java.lang.reflect.Field;

public class FieldLocatorName implements IFieldLocator
{
    private ReflectorClass reflectorClass = null;
    private String targetFieldName = null;

    public FieldLocatorName(ReflectorClass p_i35_1_, String p_i35_2_)
    {
        this.reflectorClass = p_i35_1_;
        this.targetFieldName = p_i35_2_;
    }

    public Field getField()
    {
        Class oclass = this.reflectorClass.getTargetClass();

        if (oclass == null)
        {
            return null;
        }
        else
        {
            try
            {
                Field field = this.getDeclaredField(oclass, this.targetFieldName);
                field.setAccessible(true);
                return field;
            }
            catch (NoSuchFieldException var3)
            {
                Config.log("(Reflector) Field not present: " + oclass.getName() + "." + this.targetFieldName);
                return null;
            }
            catch (SecurityException securityexception)
            {
                securityexception.printStackTrace();
                return null;
            }
            catch (Throwable throwable)
            {
                throwable.printStackTrace();
                return null;
            }
        }
    }

    private Field getDeclaredField(Class p_getDeclaredField_1_, String p_getDeclaredField_2_) throws NoSuchFieldException
    {
        Field[] afield = p_getDeclaredField_1_.getDeclaredFields();

        for (int i = 0; i < afield.length; ++i)
        {
            Field field = afield[i];

            if (field.getName().equals(p_getDeclaredField_2_))
            {
                return field;
            }
        }

        if (p_getDeclaredField_1_ == Object.class)
        {
            throw new NoSuchFieldException(p_getDeclaredField_2_);
        }
        else
        {
            return this.getDeclaredField(p_getDeclaredField_1_.getSuperclass(), p_getDeclaredField_2_);
        }
    }
}
