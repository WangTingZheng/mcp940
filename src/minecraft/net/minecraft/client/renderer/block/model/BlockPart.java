package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.util.vector.Vector3f;

public class BlockPart
{
    public final Vector3f positionFrom;
    public final Vector3f positionTo;
    public final Map<EnumFacing, BlockPartFace> mapFaces;
    public final BlockPartRotation partRotation;
    public final boolean shade;

    public BlockPart(Vector3f positionFromIn, Vector3f positionToIn, Map<EnumFacing, BlockPartFace> mapFacesIn, @Nullable BlockPartRotation partRotationIn, boolean shadeIn)
    {
        this.positionFrom = positionFromIn;
        this.positionTo = positionToIn;
        this.mapFaces = mapFacesIn;
        this.partRotation = partRotationIn;
        this.shade = shadeIn;
        this.setDefaultUvs();
    }

    private void setDefaultUvs()
    {
        for (Entry<EnumFacing, BlockPartFace> entry : this.mapFaces.entrySet())
        {
            float[] afloat = this.getFaceUvs(entry.getKey());
            (entry.getValue()).blockFaceUV.setUvs(afloat);
        }
    }

    private float[] getFaceUvs(EnumFacing facing)
    {
        switch (facing)
        {
            case DOWN:
                return new float[] {this.positionFrom.x, 16.0F - this.positionTo.z, this.positionTo.x, 16.0F - this.positionFrom.z};
            case UP:
                return new float[] {this.positionFrom.x, this.positionFrom.z, this.positionTo.x, this.positionTo.z};
            case NORTH:
            default:
                return new float[] {16.0F - this.positionTo.x, 16.0F - this.positionTo.y, 16.0F - this.positionFrom.x, 16.0F - this.positionFrom.y};
            case SOUTH:
                return new float[] {this.positionFrom.x, 16.0F - this.positionTo.y, this.positionTo.x, 16.0F - this.positionFrom.y};
            case WEST:
                return new float[] {this.positionFrom.z, 16.0F - this.positionTo.y, this.positionTo.z, 16.0F - this.positionFrom.y};
            case EAST:
                return new float[] {16.0F - this.positionTo.z, 16.0F - this.positionTo.y, 16.0F - this.positionFrom.z, 16.0F - this.positionFrom.y};
        }
    }

    static class Deserializer implements JsonDeserializer<BlockPart>
    {
        public BlockPart deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException
        {
            JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
            Vector3f vector3f = this.parsePositionFrom(jsonobject);
            Vector3f vector3f1 = this.parsePositionTo(jsonobject);
            BlockPartRotation blockpartrotation = this.parseRotation(jsonobject);
            Map<EnumFacing, BlockPartFace> map = this.parseFacesCheck(p_deserialize_3_, jsonobject);

            if (jsonobject.has("shade") && !JsonUtils.isBoolean(jsonobject, "shade"))
            {
                throw new JsonParseException("Expected shade to be a Boolean");
            }
            else
            {
                boolean flag = JsonUtils.getBoolean(jsonobject, "shade", true);
                return new BlockPart(vector3f, vector3f1, map, blockpartrotation, flag);
            }
        }

        @Nullable
        private BlockPartRotation parseRotation(JsonObject object)
        {
            BlockPartRotation blockpartrotation = null;

            if (object.has("rotation"))
            {
                JsonObject jsonobject = JsonUtils.getJsonObject(object, "rotation");
                Vector3f vector3f = this.parsePosition(jsonobject, "origin");
                vector3f.scale(0.0625F);
                EnumFacing.Axis enumfacing$axis = this.parseAxis(jsonobject);
                float f = this.parseAngle(jsonobject);
                boolean flag = JsonUtils.getBoolean(jsonobject, "rescale", false);
                blockpartrotation = new BlockPartRotation(vector3f, enumfacing$axis, f, flag);
            }

            return blockpartrotation;
        }

        private float parseAngle(JsonObject object)
        {
            float f = JsonUtils.getFloat(object, "angle");

            if (f != 0.0F && MathHelper.abs(f) != 22.5F && MathHelper.abs(f) != 45.0F)
            {
                throw new JsonParseException("Invalid rotation " + f + " found, only -45/-22.5/0/22.5/45 allowed");
            }
            else
            {
                return f;
            }
        }

        private EnumFacing.Axis parseAxis(JsonObject object)
        {
            String s = JsonUtils.getString(object, "axis");
            EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.byName(s.toLowerCase(Locale.ROOT));

            if (enumfacing$axis == null)
            {
                throw new JsonParseException("Invalid rotation axis: " + s);
            }
            else
            {
                return enumfacing$axis;
            }
        }

        private Map<EnumFacing, BlockPartFace> parseFacesCheck(JsonDeserializationContext deserializationContext, JsonObject object)
        {
            Map<EnumFacing, BlockPartFace> map = this.parseFaces(deserializationContext, object);

            if (map.isEmpty())
            {
                throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
            }
            else
            {
                return map;
            }
        }

        private Map<EnumFacing, BlockPartFace> parseFaces(JsonDeserializationContext deserializationContext, JsonObject object)
        {
            Map<EnumFacing, BlockPartFace> map = Maps.newEnumMap(EnumFacing.class);
            JsonObject jsonobject = JsonUtils.getJsonObject(object, "faces");

            for (Entry<String, JsonElement> entry : jsonobject.entrySet())
            {
                EnumFacing enumfacing = this.parseEnumFacing(entry.getKey());
                map.put(enumfacing, (BlockPartFace)deserializationContext.deserialize(entry.getValue(), BlockPartFace.class));
            }

            return map;
        }

        private EnumFacing parseEnumFacing(String name)
        {
            EnumFacing enumfacing = EnumFacing.byName(name);

            if (enumfacing == null)
            {
                throw new JsonParseException("Unknown facing: " + name);
            }
            else
            {
                return enumfacing;
            }
        }

        private Vector3f parsePositionTo(JsonObject object)
        {
            Vector3f vector3f = this.parsePosition(object, "to");

            if (vector3f.x >= -16.0F && vector3f.y >= -16.0F && vector3f.z >= -16.0F && vector3f.x <= 32.0F && vector3f.y <= 32.0F && vector3f.z <= 32.0F)
            {
                return vector3f;
            }
            else
            {
                throw new JsonParseException("'to' specifier exceeds the allowed boundaries: " + vector3f);
            }
        }

        private Vector3f parsePositionFrom(JsonObject object)
        {
            Vector3f vector3f = this.parsePosition(object, "from");

            if (vector3f.x >= -16.0F && vector3f.y >= -16.0F && vector3f.z >= -16.0F && vector3f.x <= 32.0F && vector3f.y <= 32.0F && vector3f.z <= 32.0F)
            {
                return vector3f;
            }
            else
            {
                throw new JsonParseException("'from' specifier exceeds the allowed boundaries: " + vector3f);
            }
        }

        private Vector3f parsePosition(JsonObject object, String memberName)
        {
            JsonArray jsonarray = JsonUtils.getJsonArray(object, memberName);

            if (jsonarray.size() != 3)
            {
                throw new JsonParseException("Expected 3 " + memberName + " values, found: " + jsonarray.size());
            }
            else
            {
                float[] afloat = new float[3];

                for (int i = 0; i < afloat.length; ++i)
                {
                    afloat[i] = JsonUtils.getFloat(jsonarray.get(i), memberName + "[" + i + "]");
                }

                return new Vector3f(afloat[0], afloat[1], afloat[2]);
            }
        }
    }
}
