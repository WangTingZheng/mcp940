package net.minecraft.src;

import java.lang.reflect.Array;
import java.util.ArrayDeque;

public class ArrayCache
{
    private Class elementClass = null;
    private int maxCacheSize = 0;
    private ArrayDeque cache = new ArrayDeque();

    public ArrayCache(Class p_i10_1_, int p_i10_2_)
    {
        this.elementClass = p_i10_1_;
        this.maxCacheSize = p_i10_2_;
    }

    public synchronized Object allocate(int p_allocate_1_)
    {
        Object object = this.cache.pollLast();

        if (object == null || Array.getLength(object) < p_allocate_1_)
        {
            object = Array.newInstance(this.elementClass, p_allocate_1_);
        }

        return object;
    }

    public synchronized void free(Object p_free_1_)
    {
        if (p_free_1_ != null)
        {
            Class oclass = p_free_1_.getClass();

            if (oclass.getComponentType() != this.elementClass)
            {
                throw new IllegalArgumentException("Wrong component type");
            }
            else if (this.cache.size() < this.maxCacheSize)
            {
                this.cache.add(p_free_1_);
            }
        }
    }
}
