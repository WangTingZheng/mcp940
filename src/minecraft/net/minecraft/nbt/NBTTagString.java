package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class NBTTagString extends NBTBase
{
    /** The string value for the tag (cannot be empty). */
    private String data;

    public NBTTagString()
    {
        this("");
    }

    public NBTTagString(String data)
    {
        Objects.requireNonNull(data, "Null string not allowed");
        this.data = data;
    }

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    void write(DataOutput output) throws IOException
    {
        output.writeUTF(this.data);
    }

    void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException
    {
        sizeTracker.read(288L);
        this.data = input.readUTF();
        sizeTracker.read((long)(16 * this.data.length()));
    }

    /**
     * Gets the type byte for the tag.
     */
    public byte getId()
    {
        return 8;
    }

    public String toString()
    {
        return quoteAndEscape(this.data);
    }

    /**
     * Creates a clone of the tag.
     */
    public NBTTagString copy()
    {
        return new NBTTagString(this.data);
    }

    /**
     * Return whether this compound has no tags.
     */
    public boolean hasNoTags()
    {
        return this.data.isEmpty();
    }

    public boolean equals(Object p_equals_1_)
    {
        if (!super.equals(p_equals_1_))
        {
            return false;
        }
        else
        {
            NBTTagString nbttagstring = (NBTTagString)p_equals_1_;
            return this.data == null && nbttagstring.data == null || Objects.equals(this.data, nbttagstring.data);
        }
    }

    public int hashCode()
    {
        return super.hashCode() ^ this.data.hashCode();
    }

    public String getString()
    {
        return this.data;
    }

    public static String quoteAndEscape(String p_193588_0_)
    {
        StringBuilder stringbuilder = new StringBuilder("\"");

        for (int i = 0; i < p_193588_0_.length(); ++i)
        {
            char c0 = p_193588_0_.charAt(i);

            if (c0 == '\\' || c0 == '"')
            {
                stringbuilder.append('\\');
            }

            stringbuilder.append(c0);
        }

        return stringbuilder.append('"').toString();
    }
}
