package net.minecraft.client.renderer.chunk;

import java.util.Set;
import net.minecraft.util.EnumFacing;

public class SetVisibility
{
    private static final int COUNT_FACES = EnumFacing.values().length;
    private long bits;

    public void setManyVisible(Set<EnumFacing> facing)
    {
        for (EnumFacing enumfacing : facing)
        {
            for (EnumFacing enumfacing1 : facing)
            {
                this.setVisible(enumfacing, enumfacing1, true);
            }
        }
    }

    public void setVisible(EnumFacing facing, EnumFacing facing2, boolean p_178619_3_)
    {
        this.setBit(facing.ordinal() + facing2.ordinal() * COUNT_FACES, p_178619_3_);
        this.setBit(facing2.ordinal() + facing.ordinal() * COUNT_FACES, p_178619_3_);
    }

    public void setAllVisible(boolean visible)
    {
        if (visible)
        {
            this.bits = -1L;
        }
        else
        {
            this.bits = 0L;
        }
    }

    public boolean isVisible(EnumFacing facing, EnumFacing facing2)
    {
        return this.getBit(facing.ordinal() + facing2.ordinal() * COUNT_FACES);
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(' ');

        for (EnumFacing enumfacing : EnumFacing.values())
        {
            stringbuilder.append(' ').append(enumfacing.toString().toUpperCase().charAt(0));
        }

        stringbuilder.append('\n');

        for (EnumFacing enumfacing2 : EnumFacing.values())
        {
            stringbuilder.append(enumfacing2.toString().toUpperCase().charAt(0));

            for (EnumFacing enumfacing1 : EnumFacing.values())
            {
                if (enumfacing2 == enumfacing1)
                {
                    stringbuilder.append("  ");
                }
                else
                {
                    boolean flag = this.isVisible(enumfacing2, enumfacing1);
                    stringbuilder.append(' ').append((char)(flag ? 'Y' : 'n'));
                }
            }

            stringbuilder.append('\n');
        }

        return stringbuilder.toString();
    }

    private boolean getBit(int p_getBit_1_)
    {
        return (this.bits & (long)(1 << p_getBit_1_)) != 0L;
    }

    private void setBit(int p_setBit_1_, boolean p_setBit_2_)
    {
        if (p_setBit_2_)
        {
            this.setBit(p_setBit_1_);
        }
        else
        {
            this.clearBit(p_setBit_1_);
        }
    }

    private void setBit(int p_setBit_1_)
    {
        this.bits |= (long)(1 << p_setBit_1_);
    }

    private void clearBit(int p_clearBit_1_)
    {
        this.bits &= (long)(~(1 << p_clearBit_1_));
    }
}
