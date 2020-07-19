package net.minecraft.util;

public class TupleIntJsonSerializable
{
    private int integerValue;
    private IJsonSerializable jsonSerializableValue;

    /**
     * Gets the integer value stored in this tuple.
     */
    public int getIntegerValue()
    {
        return this.integerValue;
    }

    /**
     * Sets this tuple's integer value to the given value.
     */
    public void setIntegerValue(int integerValueIn)
    {
        this.integerValue = integerValueIn;
    }

    public <T extends IJsonSerializable> T getJsonSerializableValue()
    {
        return (T)this.jsonSerializableValue;
    }

    /**
     * Sets this tuple's JsonSerializable value to the given value.
     */
    public void setJsonSerializableValue(IJsonSerializable jsonSerializableValueIn)
    {
        this.jsonSerializableValue = jsonSerializableValueIn;
    }
}
