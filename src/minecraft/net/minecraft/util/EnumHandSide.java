package net.minecraft.util;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public enum EnumHandSide
{
    LEFT(new TextComponentTranslation("options.mainHand.left", new Object[0])),
    RIGHT(new TextComponentTranslation("options.mainHand.right", new Object[0]));

    private final ITextComponent handName;

    private EnumHandSide(ITextComponent nameIn)
    {
        this.handName = nameIn;
    }

    public EnumHandSide opposite()
    {
        return this == LEFT ? RIGHT : LEFT;
    }

    public String toString()
    {
        return this.handName.getUnformattedText();
    }
}
