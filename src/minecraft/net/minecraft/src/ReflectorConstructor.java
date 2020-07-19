package net.minecraft.src;

import java.lang.reflect.Constructor;

public class ReflectorConstructor
{
    private ReflectorClass reflectorClass = null;
    private Class[] parameterTypes = null;
    private boolean checked = false;
    private Constructor targetConstructor = null;

    public ReflectorConstructor(ReflectorClass p_i81_1_, Class[] p_i81_2_)
    {
        this.reflectorClass = p_i81_1_;
        this.parameterTypes = p_i81_2_;
        Constructor constructor = this.getTargetConstructor();
    }

    public Constructor getTargetConstructor()
    {
        if (this.checked)
        {
            return this.targetConstructor;
        }
        else
        {
            this.checked = true;
            Class oclass = this.reflectorClass.getTargetClass();

            if (oclass == null)
            {
                return null;
            }
            else
            {
                try
                {
                    this.targetConstructor = findConstructor(oclass, this.parameterTypes);

                    if (this.targetConstructor == null)
                    {
                        Config.dbg("(Reflector) Constructor not present: " + oclass.getName() + ", params: " + Config.arrayToString((Object[])this.parameterTypes));
                    }

                    if (this.targetConstructor != null)
                    {
                        this.targetConstructor.setAccessible(true);
                    }
                }
                catch (Throwable throwable)
                {
                    throwable.printStackTrace();
                }

                return this.targetConstructor;
            }
        }
    }

    private static Constructor findConstructor(Class p_findConstructor_0_, Class[] p_findConstructor_1_)
    {
        Constructor[] aconstructor = p_findConstructor_0_.getDeclaredConstructors();

        for (int i = 0; i < aconstructor.length; ++i)
        {
            Constructor constructor = aconstructor[i];
            Class[] aclass = constructor.getParameterTypes();

            if (Reflector.matchesTypes(p_findConstructor_1_, aclass))
            {
                return constructor;
            }
        }

        return null;
    }

    public boolean exists()
    {
        if (this.checked)
        {
            return this.targetConstructor != null;
        }
        else
        {
            return this.getTargetConstructor() != null;
        }
    }

    public void deactivate()
    {
        this.checked = true;
        this.targetConstructor = null;
    }
}
