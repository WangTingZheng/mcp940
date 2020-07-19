package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.regex.Pattern;

public class JsonToNBT
{
    private static final Pattern DOUBLE_PATTERN_NOSUFFIX = Pattern.compile("[-+]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?", 2);
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", 2);
    private static final Pattern FLOAT_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?f", 2);
    private static final Pattern BYTE_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)b", 2);
    private static final Pattern LONG_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)l", 2);
    private static final Pattern SHORT_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)s", 2);
    private static final Pattern INT_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)");
    private final String string;
    private int cursor;

    public static NBTTagCompound getTagFromJson(String jsonString) throws NBTException
    {
        return (new JsonToNBT(jsonString)).readSingleStruct();
    }

    @VisibleForTesting
    NBTTagCompound readSingleStruct() throws NBTException
    {
        NBTTagCompound nbttagcompound = this.readStruct();
        this.skipWhitespace();

        if (this.canRead())
        {
            ++this.cursor;
            throw this.exception("Trailing data found");
        }
        else
        {
            return nbttagcompound;
        }
    }

    @VisibleForTesting
    JsonToNBT(String stringIn)
    {
        this.string = stringIn;
    }

    protected String readKey() throws NBTException
    {
        this.skipWhitespace();

        if (!this.canRead())
        {
            throw this.exception("Expected key");
        }
        else
        {
            return this.peek() == '"' ? this.readQuotedString() : this.readString();
        }
    }

    private NBTException exception(String message)
    {
        return new NBTException(message, this.string, this.cursor);
    }

    protected NBTBase readTypedValue() throws NBTException
    {
        this.skipWhitespace();

        if (this.peek() == '"')
        {
            return new NBTTagString(this.readQuotedString());
        }
        else
        {
            String s = this.readString();

            if (s.isEmpty())
            {
                throw this.exception("Expected value");
            }
            else
            {
                return this.type(s);
            }
        }
    }

    private NBTBase type(String stringIn)
    {
        try
        {
            if (FLOAT_PATTERN.matcher(stringIn).matches())
            {
                return new NBTTagFloat(Float.parseFloat(stringIn.substring(0, stringIn.length() - 1)));
            }

            if (BYTE_PATTERN.matcher(stringIn).matches())
            {
                return new NBTTagByte(Byte.parseByte(stringIn.substring(0, stringIn.length() - 1)));
            }

            if (LONG_PATTERN.matcher(stringIn).matches())
            {
                return new NBTTagLong(Long.parseLong(stringIn.substring(0, stringIn.length() - 1)));
            }

            if (SHORT_PATTERN.matcher(stringIn).matches())
            {
                return new NBTTagShort(Short.parseShort(stringIn.substring(0, stringIn.length() - 1)));
            }

            if (INT_PATTERN.matcher(stringIn).matches())
            {
                return new NBTTagInt(Integer.parseInt(stringIn));
            }

            if (DOUBLE_PATTERN.matcher(stringIn).matches())
            {
                return new NBTTagDouble(Double.parseDouble(stringIn.substring(0, stringIn.length() - 1)));
            }

            if (DOUBLE_PATTERN_NOSUFFIX.matcher(stringIn).matches())
            {
                return new NBTTagDouble(Double.parseDouble(stringIn));
            }

            if ("true".equalsIgnoreCase(stringIn))
            {
                return new NBTTagByte((byte)1);
            }

            if ("false".equalsIgnoreCase(stringIn))
            {
                return new NBTTagByte((byte)0);
            }
        }
        catch (NumberFormatException var3)
        {
            ;
        }

        return new NBTTagString(stringIn);
    }

    private String readQuotedString() throws NBTException
    {
        int i = ++this.cursor;
        StringBuilder stringbuilder = null;
        boolean flag = false;

        while (this.canRead())
        {
            char c0 = this.pop();

            if (flag)
            {
                if (c0 != '\\' && c0 != '"')
                {
                    throw this.exception("Invalid escape of '" + c0 + "'");
                }

                flag = false;
            }
            else
            {
                if (c0 == '\\')
                {
                    flag = true;

                    if (stringbuilder == null)
                    {
                        stringbuilder = new StringBuilder(this.string.substring(i, this.cursor - 1));
                    }

                    continue;
                }

                if (c0 == '"')
                {
                    return stringbuilder == null ? this.string.substring(i, this.cursor - 1) : stringbuilder.toString();
                }
            }

            if (stringbuilder != null)
            {
                stringbuilder.append(c0);
            }
        }

        throw this.exception("Missing termination quote");
    }

    private String readString()
    {
        int i;

        for (i = this.cursor; this.canRead() && this.isAllowedInKey(this.peek()); ++this.cursor)
        {
            ;
        }

        return this.string.substring(i, this.cursor);
    }

    protected NBTBase readValue() throws NBTException
    {
        this.skipWhitespace();

        if (!this.canRead())
        {
            throw this.exception("Expected value");
        }
        else
        {
            char c0 = this.peek();

            if (c0 == '{')
            {
                return this.readStruct();
            }
            else
            {
                return c0 == '[' ? this.readList() : this.readTypedValue();
            }
        }
    }

    protected NBTBase readList() throws NBTException
    {
        return this.canRead(2) && this.peek(1) != '"' && this.peek(2) == ';' ? this.readArrayTag() : this.readListTag();
    }

    protected NBTTagCompound readStruct() throws NBTException
    {
        this.expect('{');
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.skipWhitespace();

        while (this.canRead() && this.peek() != '}')
        {
            String s = this.readKey();

            if (s.isEmpty())
            {
                throw this.exception("Expected non-empty key");
            }

            this.expect(':');
            nbttagcompound.setTag(s, this.readValue());

            if (!this.hasElementSeparator())
            {
                break;
            }

            if (!this.canRead())
            {
                throw this.exception("Expected key");
            }
        }

        this.expect('}');
        return nbttagcompound;
    }

    private NBTBase readListTag() throws NBTException
    {
        this.expect('[');
        this.skipWhitespace();

        if (!this.canRead())
        {
            throw this.exception("Expected value");
        }
        else
        {
            NBTTagList nbttaglist = new NBTTagList();
            int i = -1;

            while (this.peek() != ']')
            {
                NBTBase nbtbase = this.readValue();
                int j = nbtbase.getId();

                if (i < 0)
                {
                    i = j;
                }
                else if (j != i)
                {
                    throw this.exception("Unable to insert " + NBTBase.getTagTypeName(j) + " into ListTag of type " + NBTBase.getTagTypeName(i));
                }

                nbttaglist.appendTag(nbtbase);

                if (!this.hasElementSeparator())
                {
                    break;
                }

                if (!this.canRead())
                {
                    throw this.exception("Expected value");
                }
            }

            this.expect(']');
            return nbttaglist;
        }
    }

    private NBTBase readArrayTag() throws NBTException
    {
        this.expect('[');
        char c0 = this.pop();
        this.pop();
        this.skipWhitespace();

        if (!this.canRead())
        {
            throw this.exception("Expected value");
        }
        else if (c0 == 'B')
        {
            return new NBTTagByteArray(this.readArray((byte)7, (byte)1));
        }
        else if (c0 == 'L')
        {
            return new NBTTagLongArray(this.readArray((byte)12, (byte)4));
        }
        else if (c0 == 'I')
        {
            return new NBTTagIntArray(this.readArray((byte)11, (byte)3));
        }
        else
        {
            throw this.exception("Invalid array type '" + c0 + "' found");
        }
    }

    private <T extends Number> List<T> readArray(byte p_193603_1_, byte p_193603_2_) throws NBTException
    {
        List<T> list = Lists.<T>newArrayList();

        while (true)
        {
            if (this.peek() != ']')
            {
                NBTBase nbtbase = this.readValue();
                int i = nbtbase.getId();

                if (i != p_193603_2_)
                {
                    throw this.exception("Unable to insert " + NBTBase.getTagTypeName(i) + " into " + NBTBase.getTagTypeName(p_193603_1_));
                }

                if (p_193603_2_ == 1)
                {
                    list.add((T)Byte.valueOf(((NBTPrimitive)nbtbase).getByte()));
                }
                else if (p_193603_2_ == 4)
                {
                    list.add((T)Long.valueOf(((NBTPrimitive)nbtbase).getLong()));
                }
                else
                {
                    list.add((T)Integer.valueOf(((NBTPrimitive)nbtbase).getInt()));
                }

                if (this.hasElementSeparator())
                {
                    if (!this.canRead())
                    {
                        throw this.exception("Expected value");
                    }

                    continue;
                }
            }

            this.expect(']');
            return list;
        }
    }

    private void skipWhitespace()
    {
        while (this.canRead() && Character.isWhitespace(this.peek()))
        {
            ++this.cursor;
        }
    }

    private boolean hasElementSeparator()
    {
        this.skipWhitespace();

        if (this.canRead() && this.peek() == ',')
        {
            ++this.cursor;
            this.skipWhitespace();
            return true;
        }
        else
        {
            return false;
        }
    }

    private void expect(char expected) throws NBTException
    {
        this.skipWhitespace();
        boolean flag = this.canRead();

        if (flag && this.peek() == expected)
        {
            ++this.cursor;
        }
        else
        {
            throw new NBTException("Expected '" + expected + "' but got '" + (flag ? this.peek() : "<EOF>") + "'", this.string, this.cursor + 1);
        }
    }

    protected boolean isAllowedInKey(char charIn)
    {
        return charIn >= '0' && charIn <= '9' || charIn >= 'A' && charIn <= 'Z' || charIn >= 'a' && charIn <= 'z' || charIn == '_' || charIn == '-' || charIn == '.' || charIn == '+';
    }

    private boolean canRead(int p_193608_1_)
    {
        return this.cursor + p_193608_1_ < this.string.length();
    }

    boolean canRead()
    {
        return this.canRead(0);
    }

    private char peek(int p_193597_1_)
    {
        return this.string.charAt(this.cursor + p_193597_1_);
    }

    private char peek()
    {
        return this.peek(0);
    }

    private char pop()
    {
        return this.string.charAt(this.cursor++);
    }
}
