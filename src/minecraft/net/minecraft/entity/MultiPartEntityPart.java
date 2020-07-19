package net.minecraft.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

public class MultiPartEntityPart extends Entity
{
    /** The dragon entity this dragon part belongs to */
    public final IEntityMultiPart parent;
    public final String partName;

    public MultiPartEntityPart(IEntityMultiPart parent, String partName, float width, float height)
    {
        super(parent.getWorld());
        this.setSize(width, height);
        this.parent = parent;
        this.partName = partName;
    }

    protected void entityInit()
    {
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound compound)
    {
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound compound)
    {
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return true;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return this.isEntityInvulnerable(source) ? false : this.parent.attackEntityFromPart(this, source, amount);
    }

    /**
     * Returns true if Entity argument is equal to this Entity
     */
    public boolean isEntityEqual(Entity entityIn)
    {
        return this == entityIn || this.parent == entityIn;
    }
}
