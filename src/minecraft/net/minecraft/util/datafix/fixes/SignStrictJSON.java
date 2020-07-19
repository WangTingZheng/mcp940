package net.minecraft.util.datafix.fixes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.StringUtils;
import net.minecraft.util.datafix.IFixableData;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class SignStrictJSON implements IFixableData
{
    public static final Gson GSON_INSTANCE = (new GsonBuilder()).registerTypeAdapter(ITextComponent.class, new JsonDeserializer<ITextComponent>()
    {
        public ITextComponent deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException
        {
            if (p_deserialize_1_.isJsonPrimitive())
            {
                return new TextComponentString(p_deserialize_1_.getAsString());
            }
            else if (p_deserialize_1_.isJsonArray())
            {
                JsonArray jsonarray = p_deserialize_1_.getAsJsonArray();
                ITextComponent itextcomponent = null;

                for (JsonElement jsonelement : jsonarray)
                {
                    ITextComponent itextcomponent1 = this.deserialize(jsonelement, jsonelement.getClass(), p_deserialize_3_);

                    if (itextcomponent == null)
                    {
                        itextcomponent = itextcomponent1;
                    }
                    else
                    {
                        itextcomponent.appendSibling(itextcomponent1);
                    }
                }

                return itextcomponent;
            }
            else
            {
                throw new JsonParseException("Don't know how to turn " + p_deserialize_1_ + " into a Component");
            }
        }
    }).create();

    public int getFixVersion()
    {
        return 101;
    }

    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        if ("Sign".equals(compound.getString("id")))
        {
            this.updateLine(compound, "Text1");
            this.updateLine(compound, "Text2");
            this.updateLine(compound, "Text3");
            this.updateLine(compound, "Text4");
        }

        return compound;
    }

    private void updateLine(NBTTagCompound compound, String key)
    {
        String s = compound.getString(key);
        ITextComponent itextcomponent = null;

        if (!"null".equals(s) && !StringUtils.isNullOrEmpty(s))
        {
            if (s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"' || s.charAt(0) == '{' && s.charAt(s.length() - 1) == '}')
            {
                try
                {
                    itextcomponent = (ITextComponent)JsonUtils.gsonDeserialize(GSON_INSTANCE, s, ITextComponent.class, true);

                    if (itextcomponent == null)
                    {
                        itextcomponent = new TextComponentString("");
                    }
                }
                catch (JsonParseException var8)
                {
                    ;
                }

                if (itextcomponent == null)
                {
                    try
                    {
                        itextcomponent = ITextComponent.Serializer.jsonToComponent(s);
                    }
                    catch (JsonParseException var7)
                    {
                        ;
                    }
                }

                if (itextcomponent == null)
                {
                    try
                    {
                        itextcomponent = ITextComponent.Serializer.fromJsonLenient(s);
                    }
                    catch (JsonParseException var6)
                    {
                        ;
                    }
                }

                if (itextcomponent == null)
                {
                    itextcomponent = new TextComponentString(s);
                }
            }
            else
            {
                itextcomponent = new TextComponentString(s);
            }
        }
        else
        {
            itextcomponent = new TextComponentString("");
        }

        compound.setString(key, ITextComponent.Serializer.componentToJson(itextcomponent));
    }
}
