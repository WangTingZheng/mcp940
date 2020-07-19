package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.util.vector.Vector3f;

public class ItemTransformVec3f
{
    public static final ItemTransformVec3f DEFAULT = new ItemTransformVec3f(new Vector3f(), new Vector3f(), new Vector3f(1.0F, 1.0F, 1.0F));
    public final Vector3f rotation;
    public final Vector3f translation;
    public final Vector3f scale;

    public ItemTransformVec3f(Vector3f rotation, Vector3f translation, Vector3f scale)
    {
        this.rotation = new Vector3f(rotation);
        this.translation = new Vector3f(translation);
        this.scale = new Vector3f(scale);
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (this.getClass() != p_equals_1_.getClass())
        {
            return false;
        }
        else
        {
            ItemTransformVec3f itemtransformvec3f = (ItemTransformVec3f)p_equals_1_;
            return this.rotation.equals(itemtransformvec3f.rotation) && this.scale.equals(itemtransformvec3f.scale) && this.translation.equals(itemtransformvec3f.translation);
        }
    }

    public int hashCode()
    {
        int i = this.rotation.hashCode();
        i = 31 * i + this.translation.hashCode();
        i = 31 * i + this.scale.hashCode();
        return i;
    }

    static class Deserializer implements JsonDeserializer<ItemTransformVec3f>
    {
        private static final Vector3f ROTATION_DEFAULT = new Vector3f(0.0F, 0.0F, 0.0F);
        private static final Vector3f TRANSLATION_DEFAULT = new Vector3f(0.0F, 0.0F, 0.0F);
        private static final Vector3f SCALE_DEFAULT = new Vector3f(1.0F, 1.0F, 1.0F);

        public ItemTransformVec3f deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException
        {
            JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
            Vector3f vector3f = this.parseVector3f(jsonobject, "rotation", ROTATION_DEFAULT);
            Vector3f vector3f1 = this.parseVector3f(jsonobject, "translation", TRANSLATION_DEFAULT);
            vector3f1.scale(0.0625F);
            vector3f1.x = MathHelper.clamp(vector3f1.x, -5.0F, 5.0F);
            vector3f1.y = MathHelper.clamp(vector3f1.y, -5.0F, 5.0F);
            vector3f1.z = MathHelper.clamp(vector3f1.z, -5.0F, 5.0F);
            Vector3f vector3f2 = this.parseVector3f(jsonobject, "scale", SCALE_DEFAULT);
            vector3f2.x = MathHelper.clamp(vector3f2.x, -4.0F, 4.0F);
            vector3f2.y = MathHelper.clamp(vector3f2.y, -4.0F, 4.0F);
            vector3f2.z = MathHelper.clamp(vector3f2.z, -4.0F, 4.0F);
            return new ItemTransformVec3f(vector3f, vector3f1, vector3f2);
        }

        private Vector3f parseVector3f(JsonObject jsonObject, String key, Vector3f defaultValue)
        {
            if (!jsonObject.has(key))
            {
                return defaultValue;
            }
            else
            {
                JsonArray jsonarray = JsonUtils.getJsonArray(jsonObject, key);

                if (jsonarray.size() != 3)
                {
                    throw new JsonParseException("Expected 3 " + key + " values, found: " + jsonarray.size());
                }
                else
                {
                    float[] afloat = new float[3];

                    for (int i = 0; i < afloat.length; ++i)
                    {
                        afloat[i] = JsonUtils.getFloat(jsonarray.get(i), key + "[" + i + "]");
                    }

                    return new Vector3f(afloat[0], afloat[1], afloat[2]);
                }
            }
        }
    }
}
