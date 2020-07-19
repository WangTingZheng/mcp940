package net.minecraft.util;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import java.util.Arrays;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.util.math.MathHelper;

public class IntIdentityHashBiMap<K> implements IObjectIntIterable<K>
{
    private static final Object EMPTY = null;
    private K[] values;
    private int[] intKeys;
    private K[] byId;
    private int nextFreeIndex;
    private int mapSize;

    public IntIdentityHashBiMap(int initialCapacity)
    {
        initialCapacity = (int)((float)initialCapacity / 0.8F);
        this.values = (K[])(new Object[initialCapacity]);
        this.intKeys = new int[initialCapacity];
        this.byId = (K[])(new Object[initialCapacity]);
    }

    public int getId(@Nullable K p_186815_1_)
    {
        return this.getValue(this.getIndex(p_186815_1_, this.hashObject(p_186815_1_)));
    }

    @Nullable
    public K get(int idIn)
    {
        return (K)(idIn >= 0 && idIn < this.byId.length ? this.byId[idIn] : null);
    }

    private int getValue(int p_186805_1_)
    {
        return p_186805_1_ == -1 ? -1 : this.intKeys[p_186805_1_];
    }

    /**
     * Adds the given object while expanding this map
     */
    public int add(K objectIn)
    {
        int i = this.nextId();
        this.put(objectIn, i);
        return i;
    }

    private int nextId()
    {
        while (this.nextFreeIndex < this.byId.length && this.byId[this.nextFreeIndex] != null)
        {
            ++this.nextFreeIndex;
        }

        return this.nextFreeIndex;
    }

    /**
     * Rehashes the map to the new capacity
     */
    private void grow(int capacity)
    {
        K[] ak = this.values;
        int[] aint = this.intKeys;
        this.values = (K[])(new Object[capacity]);
        this.intKeys = new int[capacity];
        this.byId = (K[])(new Object[capacity]);
        this.nextFreeIndex = 0;
        this.mapSize = 0;

        for (int i = 0; i < ak.length; ++i)
        {
            if (ak[i] != null)
            {
                this.put(ak[i], aint[i]);
            }
        }
    }

    /**
     * Puts the provided object value with the integer key.
     */
    public void put(K objectIn, int intKey)
    {
        int i = Math.max(intKey, this.mapSize + 1);

        if ((float)i >= (float)this.values.length * 0.8F)
        {
            int j;

            for (j = this.values.length << 1; j < intKey; j <<= 1)
            {
                ;
            }

            this.grow(j);
        }

        int k = this.findEmpty(this.hashObject(objectIn));
        this.values[k] = objectIn;
        this.intKeys[k] = intKey;
        this.byId[intKey] = objectIn;
        ++this.mapSize;

        if (intKey == this.nextFreeIndex)
        {
            ++this.nextFreeIndex;
        }
    }

    private int hashObject(@Nullable K obectIn)
    {
        return (MathHelper.hash(System.identityHashCode(obectIn)) & Integer.MAX_VALUE) % this.values.length;
    }

    private int getIndex(@Nullable K objectIn, int p_186816_2_)
    {
        for (int i = p_186816_2_; i < this.values.length; ++i)
        {
            if (this.values[i] == objectIn)
            {
                return i;
            }

            if (this.values[i] == EMPTY)
            {
                return -1;
            }
        }

        for (int j = 0; j < p_186816_2_; ++j)
        {
            if (this.values[j] == objectIn)
            {
                return j;
            }

            if (this.values[j] == EMPTY)
            {
                return -1;
            }
        }

        return -1;
    }

    private int findEmpty(int p_186806_1_)
    {
        for (int i = p_186806_1_; i < this.values.length; ++i)
        {
            if (this.values[i] == EMPTY)
            {
                return i;
            }
        }

        for (int j = 0; j < p_186806_1_; ++j)
        {
            if (this.values[j] == EMPTY)
            {
                return j;
            }
        }

        throw new RuntimeException("Overflowed :(");
    }

    public Iterator<K> iterator()
    {
        return Iterators.filter(Iterators.forArray(this.byId), Predicates.notNull());
    }

    public void clear()
    {
        Arrays.fill(this.values, (Object)null);
        Arrays.fill(this.byId, (Object)null);
        this.nextFreeIndex = 0;
        this.mapSize = 0;
    }

    public int size()
    {
        return this.mapSize;
    }
}
