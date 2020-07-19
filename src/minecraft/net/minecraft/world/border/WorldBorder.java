package net.minecraft.world.border;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class WorldBorder
{
    private final List<IBorderListener> listeners = Lists.<IBorderListener>newArrayList();
    private double centerX;
    private double centerZ;
    private double startDiameter = 6.0E7D;
    private double endDiameter;
    private long endTime;
    private long startTime;
    private int worldSize;
    private double damageAmount;
    private double damageBuffer;
    private int warningTime;
    private int warningDistance;

    public WorldBorder()
    {
        this.endDiameter = this.startDiameter;
        this.worldSize = 29999984;
        this.damageAmount = 0.2D;
        this.damageBuffer = 5.0D;
        this.warningTime = 15;
        this.warningDistance = 5;
    }

    public boolean contains(BlockPos pos)
    {
        return (double)(pos.getX() + 1) > this.minX() && (double)pos.getX() < this.maxX() && (double)(pos.getZ() + 1) > this.minZ() && (double)pos.getZ() < this.maxZ();
    }

    public boolean contains(ChunkPos range)
    {
        return (double)range.getXEnd() > this.minX() && (double)range.getXStart() < this.maxX() && (double)range.getZEnd() > this.minZ() && (double)range.getZStart() < this.maxZ();
    }

    public boolean contains(AxisAlignedBB bb)
    {
        return bb.maxX > this.minX() && bb.minX < this.maxX() && bb.maxZ > this.minZ() && bb.minZ < this.maxZ();
    }

    public double getClosestDistance(Entity entityIn)
    {
        return this.getClosestDistance(entityIn.posX, entityIn.posZ);
    }

    public double getClosestDistance(double x, double z)
    {
        double d0 = z - this.minZ();
        double d1 = this.maxZ() - z;
        double d2 = x - this.minX();
        double d3 = this.maxX() - x;
        double d4 = Math.min(d2, d3);
        d4 = Math.min(d4, d0);
        return Math.min(d4, d1);
    }

    public EnumBorderStatus getStatus()
    {
        if (this.endDiameter < this.startDiameter)
        {
            return EnumBorderStatus.SHRINKING;
        }
        else
        {
            return this.endDiameter > this.startDiameter ? EnumBorderStatus.GROWING : EnumBorderStatus.STATIONARY;
        }
    }

    public double minX()
    {
        double d0 = this.getCenterX() - this.getDiameter() / 2.0D;

        if (d0 < (double)(-this.worldSize))
        {
            d0 = (double)(-this.worldSize);
        }

        return d0;
    }

    public double minZ()
    {
        double d0 = this.getCenterZ() - this.getDiameter() / 2.0D;

        if (d0 < (double)(-this.worldSize))
        {
            d0 = (double)(-this.worldSize);
        }

        return d0;
    }

    public double maxX()
    {
        double d0 = this.getCenterX() + this.getDiameter() / 2.0D;

        if (d0 > (double)this.worldSize)
        {
            d0 = (double)this.worldSize;
        }

        return d0;
    }

    public double maxZ()
    {
        double d0 = this.getCenterZ() + this.getDiameter() / 2.0D;

        if (d0 > (double)this.worldSize)
        {
            d0 = (double)this.worldSize;
        }

        return d0;
    }

    public double getCenterX()
    {
        return this.centerX;
    }

    public double getCenterZ()
    {
        return this.centerZ;
    }

    public void setCenter(double x, double z)
    {
        this.centerX = x;
        this.centerZ = z;

        for (IBorderListener iborderlistener : this.getListeners())
        {
            iborderlistener.onCenterChanged(this, x, z);
        }
    }

    public double getDiameter()
    {
        if (this.getStatus() != EnumBorderStatus.STATIONARY)
        {
            double d0 = (double)((float)(System.currentTimeMillis() - this.startTime) / (float)(this.endTime - this.startTime));

            if (d0 < 1.0D)
            {
                return this.startDiameter + (this.endDiameter - this.startDiameter) * d0;
            }

            this.setTransition(this.endDiameter);
        }

        return this.startDiameter;
    }

    public long getTimeUntilTarget()
    {
        return this.getStatus() == EnumBorderStatus.STATIONARY ? 0L : this.endTime - System.currentTimeMillis();
    }

    public double getTargetSize()
    {
        return this.endDiameter;
    }

    public void setTransition(double newSize)
    {
        this.startDiameter = newSize;
        this.endDiameter = newSize;
        this.endTime = System.currentTimeMillis();
        this.startTime = this.endTime;

        for (IBorderListener iborderlistener : this.getListeners())
        {
            iborderlistener.onSizeChanged(this, newSize);
        }
    }

    public void setTransition(double oldSize, double newSize, long time)
    {
        this.startDiameter = oldSize;
        this.endDiameter = newSize;
        this.startTime = System.currentTimeMillis();
        this.endTime = this.startTime + time;

        for (IBorderListener iborderlistener : this.getListeners())
        {
            iborderlistener.onTransitionStarted(this, oldSize, newSize, time);
        }
    }

    protected List<IBorderListener> getListeners()
    {
        return Lists.newArrayList(this.listeners);
    }

    public void addListener(IBorderListener listener)
    {
        this.listeners.add(listener);
    }

    public void setSize(int size)
    {
        this.worldSize = size;
    }

    public int getSize()
    {
        return this.worldSize;
    }

    public double getDamageBuffer()
    {
        return this.damageBuffer;
    }

    public void setDamageBuffer(double bufferSize)
    {
        this.damageBuffer = bufferSize;

        for (IBorderListener iborderlistener : this.getListeners())
        {
            iborderlistener.onDamageBufferChanged(this, bufferSize);
        }
    }

    public double getDamageAmount()
    {
        return this.damageAmount;
    }

    public void setDamageAmount(double newAmount)
    {
        this.damageAmount = newAmount;

        for (IBorderListener iborderlistener : this.getListeners())
        {
            iborderlistener.onDamageAmountChanged(this, newAmount);
        }
    }

    public double getResizeSpeed()
    {
        return this.endTime == this.startTime ? 0.0D : Math.abs(this.startDiameter - this.endDiameter) / (double)(this.endTime - this.startTime);
    }

    public int getWarningTime()
    {
        return this.warningTime;
    }

    public void setWarningTime(int warningTime)
    {
        this.warningTime = warningTime;

        for (IBorderListener iborderlistener : this.getListeners())
        {
            iborderlistener.onWarningTimeChanged(this, warningTime);
        }
    }

    public int getWarningDistance()
    {
        return this.warningDistance;
    }

    public void setWarningDistance(int warningDistance)
    {
        this.warningDistance = warningDistance;

        for (IBorderListener iborderlistener : this.getListeners())
        {
            iborderlistener.onWarningDistanceChanged(this, warningDistance);
        }
    }
}
