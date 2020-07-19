package net.minecraft.client.renderer.vertex;

import net.minecraft.src.Config;
import net.minecraft.src.Reflector;
import shadersmod.client.SVertexFormat;

public class DefaultVertexFormats
{
    public static VertexFormat BLOCK = new VertexFormat();
    public static VertexFormat ITEM = new VertexFormat();
    private static final VertexFormat BLOCK_VANILLA = BLOCK;
    private static final VertexFormat ITEM_VANILLA = ITEM;
    public static final VertexFormat OLDMODEL_POSITION_TEX_NORMAL = new VertexFormat();
    public static final VertexFormat PARTICLE_POSITION_TEX_COLOR_LMAP = new VertexFormat();
    public static final VertexFormat POSITION = new VertexFormat();
    public static final VertexFormat POSITION_COLOR = new VertexFormat();
    public static final VertexFormat POSITION_TEX = new VertexFormat();
    public static final VertexFormat POSITION_NORMAL = new VertexFormat();
    public static final VertexFormat POSITION_TEX_COLOR = new VertexFormat();
    public static final VertexFormat POSITION_TEX_NORMAL = new VertexFormat();
    public static final VertexFormat POSITION_TEX_LMAP_COLOR = new VertexFormat();
    public static final VertexFormat POSITION_TEX_COLOR_NORMAL = new VertexFormat();
    public static final VertexFormatElement POSITION_3F = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, 3);
    public static final VertexFormatElement COLOR_4UB = new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUsage.COLOR, 4);
    public static final VertexFormatElement TEX_2F = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.UV, 2);
    public static final VertexFormatElement TEX_2S = new VertexFormatElement(1, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.UV, 2);
    public static final VertexFormatElement NORMAL_3B = new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.NORMAL, 3);
    public static final VertexFormatElement PADDING_1B = new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.PADDING, 1);

    public static void updateVertexFormats()
    {
        if (Config.isShaders())
        {
            BLOCK = SVertexFormat.makeDefVertexFormatBlock();
            ITEM = SVertexFormat.makeDefVertexFormatItem();
        }
        else
        {
            BLOCK = BLOCK_VANILLA;
            ITEM = ITEM_VANILLA;
        }

        if (Reflector.Attributes_DEFAULT_BAKED_FORMAT.exists())
        {
            VertexFormat vertexformat = ITEM;
            VertexFormat vertexformat1 = (VertexFormat)Reflector.getFieldValue(Reflector.Attributes_DEFAULT_BAKED_FORMAT);
            vertexformat1.clear();

            for (int i = 0; i < vertexformat.getElementCount(); ++i)
            {
                vertexformat1.addElement(vertexformat.getElement(i));
            }
        }
    }

    static
    {
        BLOCK.addElement(POSITION_3F);
        BLOCK.addElement(COLOR_4UB);
        BLOCK.addElement(TEX_2F);
        BLOCK.addElement(TEX_2S);
        ITEM.addElement(POSITION_3F);
        ITEM.addElement(COLOR_4UB);
        ITEM.addElement(TEX_2F);
        ITEM.addElement(NORMAL_3B);
        ITEM.addElement(PADDING_1B);
        OLDMODEL_POSITION_TEX_NORMAL.addElement(POSITION_3F);
        OLDMODEL_POSITION_TEX_NORMAL.addElement(TEX_2F);
        OLDMODEL_POSITION_TEX_NORMAL.addElement(NORMAL_3B);
        OLDMODEL_POSITION_TEX_NORMAL.addElement(PADDING_1B);
        PARTICLE_POSITION_TEX_COLOR_LMAP.addElement(POSITION_3F);
        PARTICLE_POSITION_TEX_COLOR_LMAP.addElement(TEX_2F);
        PARTICLE_POSITION_TEX_COLOR_LMAP.addElement(COLOR_4UB);
        PARTICLE_POSITION_TEX_COLOR_LMAP.addElement(TEX_2S);
        POSITION.addElement(POSITION_3F);
        POSITION_COLOR.addElement(POSITION_3F);
        POSITION_COLOR.addElement(COLOR_4UB);
        POSITION_TEX.addElement(POSITION_3F);
        POSITION_TEX.addElement(TEX_2F);
        POSITION_NORMAL.addElement(POSITION_3F);
        POSITION_NORMAL.addElement(NORMAL_3B);
        POSITION_NORMAL.addElement(PADDING_1B);
        POSITION_TEX_COLOR.addElement(POSITION_3F);
        POSITION_TEX_COLOR.addElement(TEX_2F);
        POSITION_TEX_COLOR.addElement(COLOR_4UB);
        POSITION_TEX_NORMAL.addElement(POSITION_3F);
        POSITION_TEX_NORMAL.addElement(TEX_2F);
        POSITION_TEX_NORMAL.addElement(NORMAL_3B);
        POSITION_TEX_NORMAL.addElement(PADDING_1B);
        POSITION_TEX_LMAP_COLOR.addElement(POSITION_3F);
        POSITION_TEX_LMAP_COLOR.addElement(TEX_2F);
        POSITION_TEX_LMAP_COLOR.addElement(TEX_2S);
        POSITION_TEX_LMAP_COLOR.addElement(COLOR_4UB);
        POSITION_TEX_COLOR_NORMAL.addElement(POSITION_3F);
        POSITION_TEX_COLOR_NORMAL.addElement(TEX_2F);
        POSITION_TEX_COLOR_NORMAL.addElement(COLOR_4UB);
        POSITION_TEX_COLOR_NORMAL.addElement(NORMAL_3B);
        POSITION_TEX_COLOR_NORMAL.addElement(PADDING_1B);
    }
}
