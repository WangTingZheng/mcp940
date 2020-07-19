package net.minecraft.src;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.optifine.entity.model.CustomEntityModelParser;

public class PlayerItemParser
{
    private static JsonParser jsonParser = new JsonParser();
    public static final String ITEM_TYPE = "type";
    public static final String ITEM_TEXTURE_SIZE = "textureSize";
    public static final String ITEM_USE_PLAYER_TEXTURE = "usePlayerTexture";
    public static final String ITEM_MODELS = "models";
    public static final String MODEL_ID = "id";
    public static final String MODEL_BASE_ID = "baseId";
    public static final String MODEL_TYPE = "type";
    public static final String MODEL_TEXTURE = "texture";
    public static final String MODEL_TEXTURE_SIZE = "textureSize";
    public static final String MODEL_ATTACH_TO = "attachTo";
    public static final String MODEL_INVERT_AXIS = "invertAxis";
    public static final String MODEL_MIRROR_TEXTURE = "mirrorTexture";
    public static final String MODEL_TRANSLATE = "translate";
    public static final String MODEL_ROTATE = "rotate";
    public static final String MODEL_SCALE = "scale";
    public static final String MODEL_BOXES = "boxes";
    public static final String MODEL_SPRITES = "sprites";
    public static final String MODEL_SUBMODEL = "submodel";
    public static final String MODEL_SUBMODELS = "submodels";
    public static final String BOX_TEXTURE_OFFSET = "textureOffset";
    public static final String BOX_COORDINATES = "coordinates";
    public static final String BOX_SIZE_ADD = "sizeAdd";
    public static final String BOX_UV_DOWN = "uvDown";
    public static final String BOX_UV_UP = "uvUp";
    public static final String BOX_UV_NORTH = "uvNorth";
    public static final String BOX_UV_SOUTH = "uvSouth";
    public static final String BOX_UV_WEST = "uvWest";
    public static final String BOX_UV_EAST = "uvEast";
    public static final String BOX_UV_FRONT = "uvFront";
    public static final String BOX_UV_BACK = "uvBack";
    public static final String BOX_UV_LEFT = "uvLeft";
    public static final String BOX_UV_RIGHT = "uvRight";
    public static final String ITEM_TYPE_MODEL = "PlayerItem";
    public static final String MODEL_TYPE_BOX = "ModelBox";

    public static PlayerItemModel parseItemModel(JsonObject p_parseItemModel_0_)
    {
        String s = Json.getString(p_parseItemModel_0_, "type");

        if (!Config.equals(s, "PlayerItem"))
        {
            throw new JsonParseException("Unknown model type: " + s);
        }
        else
        {
            int[] aint = Json.parseIntArray(p_parseItemModel_0_.get("textureSize"), 2);
            checkNull(aint, "Missing texture size");
            Dimension dimension = new Dimension(aint[0], aint[1]);
            boolean flag = Json.getBoolean(p_parseItemModel_0_, "usePlayerTexture", false);
            JsonArray jsonarray = (JsonArray)p_parseItemModel_0_.get("models");
            checkNull(jsonarray, "Missing elements");
            Map map = new HashMap();
            List list = new ArrayList();
            new ArrayList();

            for (int i = 0; i < jsonarray.size(); ++i)
            {
                JsonObject jsonobject = (JsonObject)jsonarray.get(i);
                String s1 = Json.getString(jsonobject, "baseId");

                if (s1 != null)
                {
                    JsonObject jsonobject1 = (JsonObject)map.get(s1);

                    if (jsonobject1 == null)
                    {
                        Config.warn("BaseID not found: " + s1);
                        continue;
                    }

                    for (Entry<String, JsonElement> entry : jsonobject1.entrySet())
                    {
                        if (!jsonobject.has(entry.getKey()))
                        {
                            jsonobject.add(entry.getKey(), entry.getValue());
                        }
                    }
                }

                String s2 = Json.getString(jsonobject, "id");

                if (s2 != null)
                {
                    if (!map.containsKey(s2))
                    {
                        map.put(s2, jsonobject);
                    }
                    else
                    {
                        Config.warn("Duplicate model ID: " + s2);
                    }
                }

                PlayerItemRenderer playeritemrenderer = parseItemRenderer(jsonobject, dimension);

                if (playeritemrenderer != null)
                {
                    list.add(playeritemrenderer);
                }
            }

            PlayerItemRenderer[] aplayeritemrenderer = (PlayerItemRenderer[])list.toArray(new PlayerItemRenderer[list.size()]);
            return new PlayerItemModel(dimension, flag, aplayeritemrenderer);
        }
    }

    private static void checkNull(Object p_checkNull_0_, String p_checkNull_1_)
    {
        if (p_checkNull_0_ == null)
        {
            throw new JsonParseException(p_checkNull_1_);
        }
    }

    private static ResourceLocation makeResourceLocation(String p_makeResourceLocation_0_)
    {
        int i = p_makeResourceLocation_0_.indexOf(58);

        if (i < 0)
        {
            return new ResourceLocation(p_makeResourceLocation_0_);
        }
        else
        {
            String s = p_makeResourceLocation_0_.substring(0, i);
            String s1 = p_makeResourceLocation_0_.substring(i + 1);
            return new ResourceLocation(s, s1);
        }
    }

    private static int parseAttachModel(String p_parseAttachModel_0_)
    {
        if (p_parseAttachModel_0_ == null)
        {
            return 0;
        }
        else if (p_parseAttachModel_0_.equals("body"))
        {
            return 0;
        }
        else if (p_parseAttachModel_0_.equals("head"))
        {
            return 1;
        }
        else if (p_parseAttachModel_0_.equals("leftArm"))
        {
            return 2;
        }
        else if (p_parseAttachModel_0_.equals("rightArm"))
        {
            return 3;
        }
        else if (p_parseAttachModel_0_.equals("leftLeg"))
        {
            return 4;
        }
        else if (p_parseAttachModel_0_.equals("rightLeg"))
        {
            return 5;
        }
        else if (p_parseAttachModel_0_.equals("cape"))
        {
            return 6;
        }
        else
        {
            Config.warn("Unknown attachModel: " + p_parseAttachModel_0_);
            return 0;
        }
    }

    public static PlayerItemRenderer parseItemRenderer(JsonObject p_parseItemRenderer_0_, Dimension p_parseItemRenderer_1_)
    {
        String s = Json.getString(p_parseItemRenderer_0_, "type");

        if (!Config.equals(s, "ModelBox"))
        {
            Config.warn("Unknown model type: " + s);
            return null;
        }
        else
        {
            String s1 = Json.getString(p_parseItemRenderer_0_, "attachTo");
            int i = parseAttachModel(s1);
            ModelBase modelbase = new ModelPlayerItem();
            modelbase.textureWidth = p_parseItemRenderer_1_.width;
            modelbase.textureHeight = p_parseItemRenderer_1_.height;
            ModelRenderer modelrenderer = parseModelRenderer(p_parseItemRenderer_0_, modelbase, (int[])null, (String)null);
            PlayerItemRenderer playeritemrenderer = new PlayerItemRenderer(i, modelrenderer);
            return playeritemrenderer;
        }
    }

    public static ModelRenderer parseModelRenderer(JsonObject p_parseModelRenderer_0_, ModelBase p_parseModelRenderer_1_, int[] p_parseModelRenderer_2_, String p_parseModelRenderer_3_)
    {
        ModelRenderer modelrenderer = new ModelRenderer(p_parseModelRenderer_1_);
        String s = Json.getString(p_parseModelRenderer_0_, "id");
        modelrenderer.setId(s);
        float f = Json.getFloat(p_parseModelRenderer_0_, "scale", 1.0F);
        modelrenderer.scaleX = f;
        modelrenderer.scaleY = f;
        modelrenderer.scaleZ = f;
        String s1 = Json.getString(p_parseModelRenderer_0_, "texture");

        if (s1 != null)
        {
            modelrenderer.setTextureLocation(CustomEntityModelParser.getResourceLocation(p_parseModelRenderer_3_, s1, ".png"));
        }

        int[] aint = Json.parseIntArray(p_parseModelRenderer_0_.get("textureSize"), 2);

        if (aint == null)
        {
            aint = p_parseModelRenderer_2_;
        }

        if (aint != null)
        {
            modelrenderer.setTextureSize(aint[0], aint[1]);
        }

        String s2 = Json.getString(p_parseModelRenderer_0_, "invertAxis", "").toLowerCase();
        boolean flag = s2.contains("x");
        boolean flag1 = s2.contains("y");
        boolean flag2 = s2.contains("z");
        float[] afloat = Json.parseFloatArray(p_parseModelRenderer_0_.get("translate"), 3, new float[3]);

        if (flag)
        {
            afloat[0] = -afloat[0];
        }

        if (flag1)
        {
            afloat[1] = -afloat[1];
        }

        if (flag2)
        {
            afloat[2] = -afloat[2];
        }

        float[] afloat1 = Json.parseFloatArray(p_parseModelRenderer_0_.get("rotate"), 3, new float[3]);

        for (int i = 0; i < afloat1.length; ++i)
        {
            afloat1[i] = afloat1[i] / 180.0F * (float)Math.PI;
        }

        if (flag)
        {
            afloat1[0] = -afloat1[0];
        }

        if (flag1)
        {
            afloat1[1] = -afloat1[1];
        }

        if (flag2)
        {
            afloat1[2] = -afloat1[2];
        }

        modelrenderer.setRotationPoint(afloat[0], afloat[1], afloat[2]);
        modelrenderer.rotateAngleX = afloat1[0];
        modelrenderer.rotateAngleY = afloat1[1];
        modelrenderer.rotateAngleZ = afloat1[2];
        String s3 = Json.getString(p_parseModelRenderer_0_, "mirrorTexture", "").toLowerCase();
        boolean flag3 = s3.contains("u");
        boolean flag4 = s3.contains("v");

        if (flag3)
        {
            modelrenderer.mirror = true;
        }

        if (flag4)
        {
            modelrenderer.mirrorV = true;
        }

        JsonArray jsonarray = p_parseModelRenderer_0_.getAsJsonArray("boxes");

        if (jsonarray != null)
        {
            for (int j = 0; j < jsonarray.size(); ++j)
            {
                JsonObject jsonobject = jsonarray.get(j).getAsJsonObject();
                int[] aint1 = Json.parseIntArray(jsonobject.get("textureOffset"), 2);
                int[][] aint2 = parseFaceUvs(jsonobject);

                if (aint1 == null && aint2 == null)
                {
                    throw new JsonParseException("Texture offset not specified");
                }

                float[] afloat2 = Json.parseFloatArray(jsonobject.get("coordinates"), 6);

                if (afloat2 == null)
                {
                    throw new JsonParseException("Coordinates not specified");
                }

                if (flag)
                {
                    afloat2[0] = -afloat2[0] - afloat2[3];
                }

                if (flag1)
                {
                    afloat2[1] = -afloat2[1] - afloat2[4];
                }

                if (flag2)
                {
                    afloat2[2] = -afloat2[2] - afloat2[5];
                }

                float f1 = Json.getFloat(jsonobject, "sizeAdd", 0.0F);

                if (aint2 != null)
                {
                    modelrenderer.addBox(aint2, afloat2[0], afloat2[1], afloat2[2], afloat2[3], afloat2[4], afloat2[5], f1);
                }
                else
                {
                    modelrenderer.setTextureOffset(aint1[0], aint1[1]);
                    modelrenderer.addBox(afloat2[0], afloat2[1], afloat2[2], (int)afloat2[3], (int)afloat2[4], (int)afloat2[5], f1);
                }
            }
        }

        JsonArray jsonarray1 = p_parseModelRenderer_0_.getAsJsonArray("sprites");

        if (jsonarray1 != null)
        {
            for (int k = 0; k < jsonarray1.size(); ++k)
            {
                JsonObject jsonobject2 = jsonarray1.get(k).getAsJsonObject();
                int[] aint3 = Json.parseIntArray(jsonobject2.get("textureOffset"), 2);

                if (aint3 == null)
                {
                    throw new JsonParseException("Texture offset not specified");
                }

                float[] afloat3 = Json.parseFloatArray(jsonobject2.get("coordinates"), 6);

                if (afloat3 == null)
                {
                    throw new JsonParseException("Coordinates not specified");
                }

                if (flag)
                {
                    afloat3[0] = -afloat3[0] - afloat3[3];
                }

                if (flag1)
                {
                    afloat3[1] = -afloat3[1] - afloat3[4];
                }

                if (flag2)
                {
                    afloat3[2] = -afloat3[2] - afloat3[5];
                }

                float f2 = Json.getFloat(jsonobject2, "sizeAdd", 0.0F);
                modelrenderer.setTextureOffset(aint3[0], aint3[1]);
                modelrenderer.addSprite(afloat3[0], afloat3[1], afloat3[2], (int)afloat3[3], (int)afloat3[4], (int)afloat3[5], f2);
            }
        }

        JsonObject jsonobject1 = (JsonObject)p_parseModelRenderer_0_.get("submodel");

        if (jsonobject1 != null)
        {
            ModelRenderer modelrenderer2 = parseModelRenderer(jsonobject1, p_parseModelRenderer_1_, aint, p_parseModelRenderer_3_);
            modelrenderer.addChild(modelrenderer2);
        }

        JsonArray jsonarray2 = (JsonArray)p_parseModelRenderer_0_.get("submodels");

        if (jsonarray2 != null)
        {
            for (int l = 0; l < jsonarray2.size(); ++l)
            {
                JsonObject jsonobject3 = (JsonObject)jsonarray2.get(l);
                ModelRenderer modelrenderer3 = parseModelRenderer(jsonobject3, p_parseModelRenderer_1_, aint, p_parseModelRenderer_3_);

                if (modelrenderer3.getId() != null)
                {
                    ModelRenderer modelrenderer1 = modelrenderer.getChild(modelrenderer3.getId());

                    if (modelrenderer1 != null)
                    {
                        Config.warn("Duplicate model ID: " + modelrenderer3.getId());
                    }
                }

                modelrenderer.addChild(modelrenderer3);
            }
        }

        return modelrenderer;
    }

    private static int[][] parseFaceUvs(JsonObject p_parseFaceUvs_0_)
    {
        int[][] aint = new int[][] {Json.parseIntArray(p_parseFaceUvs_0_.get("uvDown"), 4), Json.parseIntArray(p_parseFaceUvs_0_.get("uvUp"), 4), Json.parseIntArray(p_parseFaceUvs_0_.get("uvNorth"), 4), Json.parseIntArray(p_parseFaceUvs_0_.get("uvSouth"), 4), Json.parseIntArray(p_parseFaceUvs_0_.get("uvWest"), 4), Json.parseIntArray(p_parseFaceUvs_0_.get("uvEast"), 4)};

        if (aint[2] == null)
        {
            aint[2] = Json.parseIntArray(p_parseFaceUvs_0_.get("uvFront"), 4);
        }

        if (aint[3] == null)
        {
            aint[3] = Json.parseIntArray(p_parseFaceUvs_0_.get("uvBack"), 4);
        }

        if (aint[4] == null)
        {
            aint[4] = Json.parseIntArray(p_parseFaceUvs_0_.get("uvLeft"), 4);
        }

        if (aint[5] == null)
        {
            aint[5] = Json.parseIntArray(p_parseFaceUvs_0_.get("uvRight"), 4);
        }

        boolean flag = false;

        for (int i = 0; i < aint.length; ++i)
        {
            if (aint[i] != null)
            {
                flag = true;
            }
        }

        if (!flag)
        {
            return (int[][])null;
        }
        else
        {
            return aint;
        }
    }
}
