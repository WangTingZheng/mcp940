package net.minecraft.src;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class Json
{
    public static float getFloat(JsonObject p_getFloat_0_, String p_getFloat_1_, float p_getFloat_2_)
    {
        JsonElement jsonelement = p_getFloat_0_.get(p_getFloat_1_);
        return jsonelement == null ? p_getFloat_2_ : jsonelement.getAsFloat();
    }

    public static boolean getBoolean(JsonObject p_getBoolean_0_, String p_getBoolean_1_, boolean p_getBoolean_2_)
    {
        JsonElement jsonelement = p_getBoolean_0_.get(p_getBoolean_1_);
        return jsonelement == null ? p_getBoolean_2_ : jsonelement.getAsBoolean();
    }

    public static String getString(JsonObject p_getString_0_, String p_getString_1_)
    {
        return getString(p_getString_0_, p_getString_1_, (String)null);
    }

    public static String getString(JsonObject p_getString_0_, String p_getString_1_, String p_getString_2_)
    {
        JsonElement jsonelement = p_getString_0_.get(p_getString_1_);
        return jsonelement == null ? p_getString_2_ : jsonelement.getAsString();
    }

    public static float[] parseFloatArray(JsonElement p_parseFloatArray_0_, int p_parseFloatArray_1_)
    {
        return parseFloatArray(p_parseFloatArray_0_, p_parseFloatArray_1_, (float[])null);
    }

    public static float[] parseFloatArray(JsonElement p_parseFloatArray_0_, int p_parseFloatArray_1_, float[] p_parseFloatArray_2_)
    {
        if (p_parseFloatArray_0_ == null)
        {
            return p_parseFloatArray_2_;
        }
        else
        {
            JsonArray jsonarray = p_parseFloatArray_0_.getAsJsonArray();

            if (jsonarray.size() != p_parseFloatArray_1_)
            {
                throw new JsonParseException("Wrong array length: " + jsonarray.size() + ", should be: " + p_parseFloatArray_1_ + ", array: " + jsonarray);
            }
            else
            {
                float[] afloat = new float[jsonarray.size()];

                for (int i = 0; i < afloat.length; ++i)
                {
                    afloat[i] = jsonarray.get(i).getAsFloat();
                }

                return afloat;
            }
        }
    }

    public static int[] parseIntArray(JsonElement p_parseIntArray_0_, int p_parseIntArray_1_)
    {
        return parseIntArray(p_parseIntArray_0_, p_parseIntArray_1_, (int[])null);
    }

    public static int[] parseIntArray(JsonElement p_parseIntArray_0_, int p_parseIntArray_1_, int[] p_parseIntArray_2_)
    {
        if (p_parseIntArray_0_ == null)
        {
            return p_parseIntArray_2_;
        }
        else
        {
            JsonArray jsonarray = p_parseIntArray_0_.getAsJsonArray();

            if (jsonarray.size() != p_parseIntArray_1_)
            {
                throw new JsonParseException("Wrong array length: " + jsonarray.size() + ", should be: " + p_parseIntArray_1_ + ", array: " + jsonarray);
            }
            else
            {
                int[] aint = new int[jsonarray.size()];

                for (int i = 0; i < aint.length; ++i)
                {
                    aint[i] = jsonarray.get(i).getAsInt();
                }

                return aint;
            }
        }
    }
}
