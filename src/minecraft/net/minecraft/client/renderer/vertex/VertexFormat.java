package net.minecraft.client.renderer.vertex;

import com.google.common.collect.Lists;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VertexFormat
{
    private static final Logger LOGGER = LogManager.getLogger();
    private final List<VertexFormatElement> elements;
    private final List<Integer> offsets;

    /** The next available offset in this vertex format */
    private int nextOffset;
    private int colorElementOffset;
    private final List<Integer> uvOffsetsById;
    private int normalElementOffset;

    public VertexFormat(VertexFormat vertexFormatIn)
    {
        this();

        for (int i = 0; i < vertexFormatIn.getElementCount(); ++i)
        {
            this.addElement(vertexFormatIn.getElement(i));
        }

        this.nextOffset = vertexFormatIn.getNextOffset();
    }

    public VertexFormat()
    {
        this.elements = Lists.<VertexFormatElement>newArrayList();
        this.offsets = Lists.<Integer>newArrayList();
        this.colorElementOffset = -1;
        this.uvOffsetsById = Lists.<Integer>newArrayList();
        this.normalElementOffset = -1;
    }

    public void clear()
    {
        this.elements.clear();
        this.offsets.clear();
        this.colorElementOffset = -1;
        this.uvOffsetsById.clear();
        this.normalElementOffset = -1;
        this.nextOffset = 0;
    }

    @SuppressWarnings("incomplete-switch")
    public VertexFormat addElement(VertexFormatElement element)
    {
        if (element.isPositionElement() && this.hasPosition())
        {
            LOGGER.warn("VertexFormat error: Trying to add a position VertexFormatElement when one already exists, ignoring.");
            return this;
        }
        else
        {
            this.elements.add(element);
            this.offsets.add(Integer.valueOf(this.nextOffset));

            switch (element.getUsage())
            {
                case NORMAL:
                    this.normalElementOffset = this.nextOffset;
                    break;

                case COLOR:
                    this.colorElementOffset = this.nextOffset;
                    break;

                case UV:
                    this.uvOffsetsById.add(element.getIndex(), Integer.valueOf(this.nextOffset));
            }

            this.nextOffset += element.getSize();
            return this;
        }
    }

    public boolean hasNormal()
    {
        return this.normalElementOffset >= 0;
    }

    public int getNormalOffset()
    {
        return this.normalElementOffset;
    }

    public boolean hasColor()
    {
        return this.colorElementOffset >= 0;
    }

    public int getColorOffset()
    {
        return this.colorElementOffset;
    }

    public boolean hasUvOffset(int id)
    {
        return this.uvOffsetsById.size() - 1 >= id;
    }

    public int getUvOffsetById(int id)
    {
        return ((Integer)this.uvOffsetsById.get(id)).intValue();
    }

    public String toString()
    {
        String s = "format: " + this.elements.size() + " elements: ";

        for (int i = 0; i < this.elements.size(); ++i)
        {
            s = s + ((VertexFormatElement)this.elements.get(i)).toString();

            if (i != this.elements.size() - 1)
            {
                s = s + " ";
            }
        }

        return s;
    }

    private boolean hasPosition()
    {
        int i = 0;

        for (int j = this.elements.size(); i < j; ++i)
        {
            VertexFormatElement vertexformatelement = this.elements.get(i);

            if (vertexformatelement.isPositionElement())
            {
                return true;
            }
        }

        return false;
    }

    public int getIntegerSize()
    {
        return this.getNextOffset() / 4;
    }

    public int getNextOffset()
    {
        return this.nextOffset;
    }

    public List<VertexFormatElement> getElements()
    {
        return this.elements;
    }

    public int getElementCount()
    {
        return this.elements.size();
    }

    public VertexFormatElement getElement(int index)
    {
        return this.elements.get(index);
    }

    public int getOffset(int index)
    {
        return ((Integer)this.offsets.get(index)).intValue();
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass())
        {
            VertexFormat vertexformat = (VertexFormat)p_equals_1_;

            if (this.nextOffset != vertexformat.nextOffset)
            {
                return false;
            }
            else if (!this.elements.equals(vertexformat.elements))
            {
                return false;
            }
            else
            {
                return this.offsets.equals(vertexformat.offsets);
            }
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        int i = this.elements.hashCode();
        i = 31 * i + this.offsets.hashCode();
        i = 31 * i + this.nextOffset;
        return i;
    }
}
