package net.minecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

public abstract class TileEntityLockable extends TileEntity implements ILockableContainer
{
    private LockCode code = LockCode.EMPTY_CODE;

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.code = LockCode.fromNBT(compound);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        if (this.code != null)
        {
            this.code.toNBT(compound);
        }

        return compound;
    }

    public boolean isLocked()
    {
        return this.code != null && !this.code.isEmpty();
    }

    public LockCode getLockCode()
    {
        return this.code;
    }

    public void setLockCode(LockCode code)
    {
        this.code = code;
    }

    /**
     * Get the formatted ChatComponent that will be used for the sender's username in chat
     */
    public ITextComponent getDisplayName()
    {
        return (ITextComponent)(this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]));
    }
}
