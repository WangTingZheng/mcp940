package net.minecraft.advancements;

import net.minecraft.util.text.TextFormatting;

public enum FrameType
{
    TASK("task", 0, TextFormatting.GREEN),
    CHALLENGE("challenge", 26, TextFormatting.DARK_PURPLE),
    GOAL("goal", 52, TextFormatting.GREEN);

    private final String name;
    private final int icon;
    private final TextFormatting format;

    private FrameType(String nameIn, int iconIn, TextFormatting formatIn)
    {
        this.name = nameIn;
        this.icon = iconIn;
        this.format = formatIn;
    }

    public String getName()
    {
        return this.name;
    }

    public int getIcon()
    {
        return this.icon;
    }

    public static FrameType byName(String nameIn)
    {
        for (FrameType frametype : values())
        {
            if (frametype.name.equals(nameIn))
            {
                return frametype;
            }
        }

        throw new IllegalArgumentException("Unknown frame type '" + nameIn + "'");
    }

    public TextFormatting getFormat()
    {
        return this.format;
    }
}
