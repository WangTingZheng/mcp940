package net.minecraft.entity.ai.attributes;

import io.netty.util.internal.ThreadLocalRandom;
import java.util.UUID;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.Validate;

public class AttributeModifier
{
    private final double amount;
    private final int operation;
    private final String name;
    private final UUID id;

    /**
     * If false, this modifier is not saved in NBT. Used for "natural" modifiers like speed boost from sprinting
     */
    private boolean isSaved;

    public AttributeModifier(String nameIn, double amountIn, int operationIn)
    {
        this(MathHelper.getRandomUUID(ThreadLocalRandom.current()), nameIn, amountIn, operationIn);
    }

    public AttributeModifier(UUID idIn, String nameIn, double amountIn, int operationIn)
    {
        this.isSaved = true;
        this.id = idIn;
        this.name = nameIn;
        this.amount = amountIn;
        this.operation = operationIn;
        Validate.notEmpty(nameIn, "Modifier name cannot be empty");
        Validate.inclusiveBetween(0L, 2L, (long)operationIn, "Invalid operation");
    }

    public UUID getID()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public int getOperation()
    {
        return this.operation;
    }

    public double getAmount()
    {
        return this.amount;
    }

    /**
     * @see #isSaved
     */
    public boolean isSaved()
    {
        return this.isSaved;
    }

    /**
     * @see #isSaved
     */
    public AttributeModifier setSaved(boolean saved)
    {
        this.isSaved = saved;
        return this;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass())
        {
            AttributeModifier attributemodifier = (AttributeModifier)p_equals_1_;

            if (this.id != null)
            {
                if (!this.id.equals(attributemodifier.id))
                {
                    return false;
                }
            }
            else if (attributemodifier.id != null)
            {
                return false;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return this.id != null ? this.id.hashCode() : 0;
    }

    public String toString()
    {
        return "AttributeModifier{amount=" + this.amount + ", operation=" + this.operation + ", name='" + this.name + '\'' + ", id=" + this.id + ", serialize=" + this.isSaved + '}';
    }
}
