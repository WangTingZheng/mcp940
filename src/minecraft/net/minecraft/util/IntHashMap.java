package net.minecraft.util;

import javax.annotation.Nullable;

public class IntHashMap<V>
{
    private transient IntHashMap.Entry<V>[] slots = new IntHashMap.Entry[16];

    /** The number of items stored in this map */
    private transient int count;

    /** The grow threshold */
    private int threshold = 12;

    /** The scale factor used to determine when to grow the table */
    private final float growFactor = 0.75F;

    /**
     * Makes the passed in integer suitable for hashing by a number of shifts
     */
    private static int computeHash(int integer)
    {
        integer = integer ^ integer >>> 20 ^ integer >>> 12;
        return integer ^ integer >>> 7 ^ integer >>> 4;
    }

    /**
     * Computes the index of the slot for the hash and slot count passed in.
     */
    private static int getSlotIndex(int hash, int slotCount)
    {
        return hash & slotCount - 1;
    }

    @Nullable

    /**
     * Returns the object associated to a key
     */
    public V lookup(int hashEntry)
    {
        int i = computeHash(hashEntry);

        for (IntHashMap.Entry<V> entry = this.slots[getSlotIndex(i, this.slots.length)]; entry != null; entry = entry.nextEntry)
        {
            if (entry.hashEntry == hashEntry)
            {
                return entry.valueEntry;
            }
        }

        return (V)null;
    }

    /**
     * Returns true if this hash table contains the specified item.
     */
    public boolean containsItem(int hashEntry)
    {
        return this.lookupEntry(hashEntry) != null;
    }

    @Nullable
    final IntHashMap.Entry<V> lookupEntry(int hashEntry)
    {
        int i = computeHash(hashEntry);

        for (IntHashMap.Entry<V> entry = this.slots[getSlotIndex(i, this.slots.length)]; entry != null; entry = entry.nextEntry)
        {
            if (entry.hashEntry == hashEntry)
            {
                return entry;
            }
        }

        return null;
    }

    /**
     * Adds a key and associated value to this map
     */
    public void addKey(int hashEntry, V valueEntry)
    {
        int i = computeHash(hashEntry);
        int j = getSlotIndex(i, this.slots.length);

        for (IntHashMap.Entry<V> entry = this.slots[j]; entry != null; entry = entry.nextEntry)
        {
            if (entry.hashEntry == hashEntry)
            {
                entry.valueEntry = valueEntry;
                return;
            }
        }

        this.insert(i, hashEntry, valueEntry, j);
    }

    /**
     * Increases the number of hash slots
     */
    private void grow(int p_76047_1_)
    {
        IntHashMap.Entry<V>[] entry = this.slots;
        int i = entry.length;

        if (i == 1073741824)
        {
            this.threshold = Integer.MAX_VALUE;
        }
        else
        {
            IntHashMap.Entry<V>[] entry1 = new IntHashMap.Entry[p_76047_1_];
            this.copyTo(entry1);
            this.slots = entry1;
            this.threshold = (int)((float)p_76047_1_ * this.growFactor);
        }
    }

    /**
     * Copies the hash slots to a new array
     */
    private void copyTo(IntHashMap.Entry<V>[] p_76048_1_)
    {
        IntHashMap.Entry<V>[] entry = this.slots;
        int i = p_76048_1_.length;

        for (int j = 0; j < entry.length; ++j)
        {
            IntHashMap.Entry<V> entry1 = entry[j];

            if (entry1 != null)
            {
                entry[j] = null;

                while (true)
                {
                    IntHashMap.Entry<V> entry2 = entry1.nextEntry;
                    int k = getSlotIndex(entry1.slotHash, i);
                    entry1.nextEntry = p_76048_1_[k];
                    p_76048_1_[k] = entry1;
                    entry1 = entry2;

                    if (entry2 == null)
                    {
                        break;
                    }
                }
            }
        }
    }

    @Nullable

    /**
     * Removes the specified object from the map and returns it
     */
    public V removeObject(int o)
    {
        IntHashMap.Entry<V> entry = this.removeEntry(o);
        return (V)(entry == null ? null : entry.valueEntry);
    }

    @Nullable
    final IntHashMap.Entry<V> removeEntry(int p_76036_1_)
    {
        int i = computeHash(p_76036_1_);
        int j = getSlotIndex(i, this.slots.length);
        IntHashMap.Entry<V> entry = this.slots[j];
        IntHashMap.Entry<V> entry1;
        IntHashMap.Entry<V> entry2;

        for (entry1 = entry; entry1 != null; entry1 = entry2)
        {
            entry2 = entry1.nextEntry;

            if (entry1.hashEntry == p_76036_1_)
            {
                --this.count;

                if (entry == entry1)
                {
                    this.slots[j] = entry2;
                }
                else
                {
                    entry.nextEntry = entry2;
                }

                return entry1;
            }

            entry = entry1;
        }

        return entry1;
    }

    /**
     * Removes all entries from the map
     */
    public void clearMap()
    {
        IntHashMap.Entry<V>[] entry = this.slots;

        for (int i = 0; i < entry.length; ++i)
        {
            entry[i] = null;
        }

        this.count = 0;
    }

    /**
     * Adds an object to a slot
     */
    private void insert(int p_76040_1_, int p_76040_2_, V p_76040_3_, int p_76040_4_)
    {
        IntHashMap.Entry<V> entry = this.slots[p_76040_4_];
        this.slots[p_76040_4_] = new IntHashMap.Entry(p_76040_1_, p_76040_2_, p_76040_3_, entry);

        if (this.count++ >= this.threshold)
        {
            this.grow(2 * this.slots.length);
        }
    }

    static class Entry<V>
    {
        final int hashEntry;
        V valueEntry;
        IntHashMap.Entry<V> nextEntry;
        final int slotHash;

        Entry(int p_i1552_1_, int p_i1552_2_, V p_i1552_3_, IntHashMap.Entry<V> p_i1552_4_)
        {
            this.valueEntry = p_i1552_3_;
            this.nextEntry = p_i1552_4_;
            this.hashEntry = p_i1552_2_;
            this.slotHash = p_i1552_1_;
        }

        public final int getHash()
        {
            return this.hashEntry;
        }

        public final V getValue()
        {
            return this.valueEntry;
        }

        public final boolean equals(Object p_equals_1_)
        {
            if (!(p_equals_1_ instanceof IntHashMap.Entry))
            {
                return false;
            }
            else
            {
                IntHashMap.Entry<V> entry = (IntHashMap.Entry)p_equals_1_;

                if (this.hashEntry == entry.hashEntry)
                {
                    Object object = this.getValue();
                    Object object1 = entry.getValue();

                    if (object == object1 || object != null && object.equals(object1))
                    {
                        return true;
                    }
                }

                return false;
            }
        }

        public final int hashCode()
        {
            return IntHashMap.computeHash(this.hashEntry);
        }

        public final String toString()
        {
            return this.getHash() + "=" + this.getValue();
        }
    }
}
