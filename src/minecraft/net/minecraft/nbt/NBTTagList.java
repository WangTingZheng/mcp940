package net.minecraft.nbt;

import com.google.common.collect.Lists;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NBTTagList extends NBTBase
{
    private static final Logger LOGGER = LogManager.getLogger();
    private List<NBTBase> tagList = Lists.<NBTBase>newArrayList();

    /**
     * The type byte for the tags in the list - they must all be of the same type.
     */
    private byte tagType = 0;

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    void write(DataOutput output) throws IOException
    {
        if (this.tagList.isEmpty())
        {
            this.tagType = 0;
        }
        else
        {
            this.tagType = ((NBTBase)this.tagList.get(0)).getId();
        }

        output.writeByte(this.tagType);
        output.writeInt(this.tagList.size());

        for (int i = 0; i < this.tagList.size(); ++i)
        {
            ((NBTBase)this.tagList.get(i)).write(output);
        }
    }

    void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException
    {
        sizeTracker.read(296L);

        if (depth > 512)
        {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        }
        else
        {
            this.tagType = input.readByte();
            int i = input.readInt();

            if (this.tagType == 0 && i > 0)
            {
                throw new RuntimeException("Missing type on ListTag");
            }
            else
            {
                sizeTracker.read(32L * (long)i);
                this.tagList = Lists.<NBTBase>newArrayListWithCapacity(i);

                for (int j = 0; j < i; ++j)
                {
                    NBTBase nbtbase = NBTBase.createNewByType(this.tagType);
                    nbtbase.read(input, depth + 1, sizeTracker);
                    this.tagList.add(nbtbase);
                }
            }
        }
    }

    /**
     * Gets the type byte for the tag.
     */
    public byte getId()
    {
        return 9;
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder("[");

        for (int i = 0; i < this.tagList.size(); ++i)
        {
            if (i != 0)
            {
                stringbuilder.append(',');
            }

            stringbuilder.append(this.tagList.get(i));
        }

        return stringbuilder.append(']').toString();
    }

    /**
     * Adds the provided tag to the end of the list. There is no check to verify this tag is of the same type as any
     * previous tag.
     */
    public void appendTag(NBTBase nbt)
    {
        if (nbt.getId() == 0)
        {
            LOGGER.warn("Invalid TagEnd added to ListTag");
        }
        else
        {
            if (this.tagType == 0)
            {
                this.tagType = nbt.getId();
            }
            else if (this.tagType != nbt.getId())
            {
                LOGGER.warn("Adding mismatching tag types to tag list");
                return;
            }

            this.tagList.add(nbt);
        }
    }

    /**
     * Set the given index to the given tag
     */
    public void set(int idx, NBTBase nbt)
    {
        if (nbt.getId() == 0)
        {
            LOGGER.warn("Invalid TagEnd added to ListTag");
        }
        else if (idx >= 0 && idx < this.tagList.size())
        {
            if (this.tagType == 0)
            {
                this.tagType = nbt.getId();
            }
            else if (this.tagType != nbt.getId())
            {
                LOGGER.warn("Adding mismatching tag types to tag list");
                return;
            }

            this.tagList.set(idx, nbt);
        }
        else
        {
            LOGGER.warn("index out of bounds to set tag in tag list");
        }
    }

    /**
     * Removes a tag at the given index.
     */
    public NBTBase removeTag(int i)
    {
        return this.tagList.remove(i);
    }

    /**
     * Return whether this compound has no tags.
     */
    public boolean hasNoTags()
    {
        return this.tagList.isEmpty();
    }

    /**
     * Retrieves the NBTTagCompound at the specified index in the list
     */
    public NBTTagCompound getCompoundTagAt(int i)
    {
        if (i >= 0 && i < this.tagList.size())
        {
            NBTBase nbtbase = this.tagList.get(i);

            if (nbtbase.getId() == 10)
            {
                return (NBTTagCompound)nbtbase;
            }
        }

        return new NBTTagCompound();
    }

    public int getIntAt(int p_186858_1_)
    {
        if (p_186858_1_ >= 0 && p_186858_1_ < this.tagList.size())
        {
            NBTBase nbtbase = this.tagList.get(p_186858_1_);

            if (nbtbase.getId() == 3)
            {
                return ((NBTTagInt)nbtbase).getInt();
            }
        }

        return 0;
    }

    public int[] getIntArrayAt(int i)
    {
        if (i >= 0 && i < this.tagList.size())
        {
            NBTBase nbtbase = this.tagList.get(i);

            if (nbtbase.getId() == 11)
            {
                return ((NBTTagIntArray)nbtbase).getIntArray();
            }
        }

        return new int[0];
    }

    public double getDoubleAt(int i)
    {
        if (i >= 0 && i < this.tagList.size())
        {
            NBTBase nbtbase = this.tagList.get(i);

            if (nbtbase.getId() == 6)
            {
                return ((NBTTagDouble)nbtbase).getDouble();
            }
        }

        return 0.0D;
    }

    public float getFloatAt(int i)
    {
        if (i >= 0 && i < this.tagList.size())
        {
            NBTBase nbtbase = this.tagList.get(i);

            if (nbtbase.getId() == 5)
            {
                return ((NBTTagFloat)nbtbase).getFloat();
            }
        }

        return 0.0F;
    }

    /**
     * Retrieves the tag String value at the specified index in the list
     */
    public String getStringTagAt(int i)
    {
        if (i >= 0 && i < this.tagList.size())
        {
            NBTBase nbtbase = this.tagList.get(i);
            return nbtbase.getId() == 8 ? nbtbase.getString() : nbtbase.toString();
        }
        else
        {
            return "";
        }
    }

    /**
     * Get the tag at the given position
     */
    public NBTBase get(int idx)
    {
        return (NBTBase)(idx >= 0 && idx < this.tagList.size() ? (NBTBase)this.tagList.get(idx) : new NBTTagEnd());
    }

    /**
     * Returns the number of tags in the list.
     */
    public int tagCount()
    {
        return this.tagList.size();
    }

    /**
     * Creates a clone of the tag.
     */
    public NBTTagList copy()
    {
        NBTTagList nbttaglist = new NBTTagList();
        nbttaglist.tagType = this.tagType;

        for (NBTBase nbtbase : this.tagList)
        {
            NBTBase nbtbase1 = nbtbase.copy();
            nbttaglist.tagList.add(nbtbase1);
        }

        return nbttaglist;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (!super.equals(p_equals_1_))
        {
            return false;
        }
        else
        {
            NBTTagList nbttaglist = (NBTTagList)p_equals_1_;
            return this.tagType == nbttaglist.tagType && Objects.equals(this.tagList, nbttaglist.tagList);
        }
    }

    public int hashCode()
    {
        return super.hashCode() ^ this.tagList.hashCode();
    }

    public int getTagType()
    {
        return this.tagType;
    }
}
