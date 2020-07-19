package net.minecraft.entity;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BlockFence;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityLeashKnot extends EntityHanging
{
    public EntityLeashKnot(World worldIn)
    {
        super(worldIn);
    }

    public EntityLeashKnot(World worldIn, BlockPos hangingPositionIn)
    {
        super(worldIn, hangingPositionIn);
        this.setPosition((double)hangingPositionIn.getX() + 0.5D, (double)hangingPositionIn.getY() + 0.5D, (double)hangingPositionIn.getZ() + 0.5D);
        float f = 0.125F;
        float f1 = 0.1875F;
        float f2 = 0.25F;
        this.setEntityBoundingBox(new AxisAlignedBB(this.posX - 0.1875D, this.posY - 0.25D + 0.125D, this.posZ - 0.1875D, this.posX + 0.1875D, this.posY + 0.25D + 0.125D, this.posZ + 0.1875D));
        this.forceSpawn = true;
    }

    /**
     * Sets the x,y,z of the entity from the given parameters. Also seems to set up a bounding box.
     */
    public void setPosition(double x, double y, double z)
    {
        super.setPosition((double)MathHelper.floor(x) + 0.5D, (double)MathHelper.floor(y) + 0.5D, (double)MathHelper.floor(z) + 0.5D);
    }

    /**
     * Updates the entity bounding box based on current facing
     */
    protected void updateBoundingBox()
    {
        this.posX = (double)this.hangingPosition.getX() + 0.5D;
        this.posY = (double)this.hangingPosition.getY() + 0.5D;
        this.posZ = (double)this.hangingPosition.getZ() + 0.5D;
    }

    /**
     * Updates facing and bounding box based on it
     */
    public void updateFacingWithBoundingBox(EnumFacing facingDirectionIn)
    {
    }

    public int getWidthPixels()
    {
        return 9;
    }

    public int getHeightPixels()
    {
        return 9;
    }

    public float getEyeHeight()
    {
        return -0.0625F;
    }

    /**
     * Checks if the entity is in range to render.
     */
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 1024.0D;
    }

    /**
     * Called when this entity is broken. Entity parameter may be null.
     */
    public void onBroken(@Nullable Entity brokenEntity)
    {
        this.playSound(SoundEvents.ENTITY_LEASHKNOT_BREAK, 1.0F, 1.0F);
    }

    /**
     * Either write this entity to the NBT tag given and return true, or return false without doing anything. If this
     * returns false the entity is not saved on disk. Riding entities return false here as they are saved with their
     * mount.
     */
    public boolean writeToNBTOptional(NBTTagCompound compound)
    {
        return false;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
    }

    public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        if (this.world.isRemote)
        {
            return true;
        }
        else
        {
            boolean flag = false;
            double d0 = 7.0D;
            List<EntityLiving> list = this.world.<EntityLiving>getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(this.posX - 7.0D, this.posY - 7.0D, this.posZ - 7.0D, this.posX + 7.0D, this.posY + 7.0D, this.posZ + 7.0D));

            for (EntityLiving entityliving : list)
            {
                if (entityliving.getLeashed() && entityliving.getLeashedToEntity() == player)
                {
                    entityliving.setLeashedToEntity(this, true);
                    flag = true;
                }
            }

            if (!flag)
            {
                this.setDead();

                if (player.capabilities.isCreativeMode)
                {
                    for (EntityLiving entityliving1 : list)
                    {
                        if (entityliving1.getLeashed() && entityliving1.getLeashedToEntity() == this)
                        {
                            entityliving1.clearLeashed(true, false);
                        }
                    }
                }
            }

            return true;
        }
    }

    /**
     * checks to make sure painting can be placed there
     */
    public boolean onValidSurface()
    {
        return this.world.getBlockState(this.hangingPosition).getBlock() instanceof BlockFence;
    }

    public static EntityLeashKnot createKnot(World worldIn, BlockPos fence)
    {
        EntityLeashKnot entityleashknot = new EntityLeashKnot(worldIn, fence);
        worldIn.spawnEntity(entityleashknot);
        entityleashknot.playPlaceSound();
        return entityleashknot;
    }

    @Nullable
    public static EntityLeashKnot getKnotForPosition(World worldIn, BlockPos pos)
    {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();

        for (EntityLeashKnot entityleashknot : worldIn.getEntitiesWithinAABB(EntityLeashKnot.class, new AxisAlignedBB((double)i - 1.0D, (double)j - 1.0D, (double)k - 1.0D, (double)i + 1.0D, (double)j + 1.0D, (double)k + 1.0D)))
        {
            if (entityleashknot.getHangingPosition().equals(pos))
            {
                return entityleashknot;
            }
        }

        return null;
    }

    public void playPlaceSound()
    {
        this.playSound(SoundEvents.ENTITY_LEASHKNOT_PLACE, 1.0F, 1.0F);
    }
}
