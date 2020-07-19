package net.minecraft.client.util;

import com.google.gson.JsonObject;
import java.util.Locale;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.JsonUtils;

public class JsonBlendingMode
{
    private static JsonBlendingMode lastApplied;
    private final int srcColorFactor;
    private final int srcAlphaFactor;
    private final int destColorFactor;
    private final int destAlphaFactor;
    private final int blendFunction;
    private final boolean separateBlend;
    private final boolean opaque;

    private JsonBlendingMode(boolean separateBlendIn, boolean opaqueIn, int srcColorFactorIn, int destColorFactorIn, int srcAlphaFactorIn, int destAlphaFactorIn, int blendFunctionIn)
    {
        this.separateBlend = separateBlendIn;
        this.srcColorFactor = srcColorFactorIn;
        this.destColorFactor = destColorFactorIn;
        this.srcAlphaFactor = srcAlphaFactorIn;
        this.destAlphaFactor = destAlphaFactorIn;
        this.opaque = opaqueIn;
        this.blendFunction = blendFunctionIn;
    }

    public JsonBlendingMode()
    {
        this(false, true, 1, 0, 1, 0, 32774);
    }

    public JsonBlendingMode(int srcFactor, int dstFactor, int blendFunctionIn)
    {
        this(false, false, srcFactor, dstFactor, srcFactor, dstFactor, blendFunctionIn);
    }

    public JsonBlendingMode(int srcColorFactorIn, int destColorFactorIn, int srcAlphaFactorIn, int destAlphaFactorIn, int blendFunctionIn)
    {
        this(true, false, srcColorFactorIn, destColorFactorIn, srcAlphaFactorIn, destAlphaFactorIn, blendFunctionIn);
    }

    public void apply()
    {
        if (!this.equals(lastApplied))
        {
            if (lastApplied == null || this.opaque != lastApplied.isOpaque())
            {
                lastApplied = this;

                if (this.opaque)
                {
                    GlStateManager.disableBlend();
                    return;
                }

                GlStateManager.enableBlend();
            }

            GlStateManager.glBlendEquation(this.blendFunction);

            if (this.separateBlend)
            {
                GlStateManager.tryBlendFuncSeparate(this.srcColorFactor, this.destColorFactor, this.srcAlphaFactor, this.destAlphaFactor);
            }
            else
            {
                GlStateManager.blendFunc(this.srcColorFactor, this.destColorFactor);
            }
        }
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (!(p_equals_1_ instanceof JsonBlendingMode))
        {
            return false;
        }
        else
        {
            JsonBlendingMode jsonblendingmode = (JsonBlendingMode)p_equals_1_;

            if (this.blendFunction != jsonblendingmode.blendFunction)
            {
                return false;
            }
            else if (this.destAlphaFactor != jsonblendingmode.destAlphaFactor)
            {
                return false;
            }
            else if (this.destColorFactor != jsonblendingmode.destColorFactor)
            {
                return false;
            }
            else if (this.opaque != jsonblendingmode.opaque)
            {
                return false;
            }
            else if (this.separateBlend != jsonblendingmode.separateBlend)
            {
                return false;
            }
            else if (this.srcAlphaFactor != jsonblendingmode.srcAlphaFactor)
            {
                return false;
            }
            else
            {
                return this.srcColorFactor == jsonblendingmode.srcColorFactor;
            }
        }
    }

    public int hashCode()
    {
        int i = this.srcColorFactor;
        i = 31 * i + this.srcAlphaFactor;
        i = 31 * i + this.destColorFactor;
        i = 31 * i + this.destAlphaFactor;
        i = 31 * i + this.blendFunction;
        i = 31 * i + (this.separateBlend ? 1 : 0);
        i = 31 * i + (this.opaque ? 1 : 0);
        return i;
    }

    public boolean isOpaque()
    {
        return this.opaque;
    }

    public static JsonBlendingMode parseBlendNode(JsonObject json)
    {
        if (json == null)
        {
            return new JsonBlendingMode();
        }
        else
        {
            int i = 32774;
            int j = 1;
            int k = 0;
            int l = 1;
            int i1 = 0;
            boolean flag = true;
            boolean flag1 = false;

            if (JsonUtils.isString(json, "func"))
            {
                i = stringToBlendFunction(json.get("func").getAsString());

                if (i != 32774)
                {
                    flag = false;
                }
            }

            if (JsonUtils.isString(json, "srcrgb"))
            {
                j = stringToBlendFactor(json.get("srcrgb").getAsString());

                if (j != 1)
                {
                    flag = false;
                }
            }

            if (JsonUtils.isString(json, "dstrgb"))
            {
                k = stringToBlendFactor(json.get("dstrgb").getAsString());

                if (k != 0)
                {
                    flag = false;
                }
            }

            if (JsonUtils.isString(json, "srcalpha"))
            {
                l = stringToBlendFactor(json.get("srcalpha").getAsString());

                if (l != 1)
                {
                    flag = false;
                }

                flag1 = true;
            }

            if (JsonUtils.isString(json, "dstalpha"))
            {
                i1 = stringToBlendFactor(json.get("dstalpha").getAsString());

                if (i1 != 0)
                {
                    flag = false;
                }

                flag1 = true;
            }

            if (flag)
            {
                return new JsonBlendingMode();
            }
            else
            {
                return flag1 ? new JsonBlendingMode(j, k, l, i1, i) : new JsonBlendingMode(j, k, i);
            }
        }
    }

    /**
     * Converts a blend function name to an id, returning add (32774) if not recognized.
     */
    private static int stringToBlendFunction(String funcName)
    {
        String s = funcName.trim().toLowerCase(Locale.ROOT);

        if ("add".equals(s))
        {
            return 32774;
        }
        else if ("subtract".equals(s))
        {
            return 32778;
        }
        else if ("reversesubtract".equals(s))
        {
            return 32779;
        }
        else if ("reverse_subtract".equals(s))
        {
            return 32779;
        }
        else if ("min".equals(s))
        {
            return 32775;
        }
        else
        {
            return "max".equals(s) ? 32776 : 32774;
        }
    }

    private static int stringToBlendFactor(String factorName)
    {
        String s = factorName.trim().toLowerCase(Locale.ROOT);
        s = s.replaceAll("_", "");
        s = s.replaceAll("one", "1");
        s = s.replaceAll("zero", "0");
        s = s.replaceAll("minus", "-");

        if ("0".equals(s))
        {
            return 0;
        }
        else if ("1".equals(s))
        {
            return 1;
        }
        else if ("srccolor".equals(s))
        {
            return 768;
        }
        else if ("1-srccolor".equals(s))
        {
            return 769;
        }
        else if ("dstcolor".equals(s))
        {
            return 774;
        }
        else if ("1-dstcolor".equals(s))
        {
            return 775;
        }
        else if ("srcalpha".equals(s))
        {
            return 770;
        }
        else if ("1-srcalpha".equals(s))
        {
            return 771;
        }
        else if ("dstalpha".equals(s))
        {
            return 772;
        }
        else
        {
            return "1-dstalpha".equals(s) ? 773 : -1;
        }
    }
}
