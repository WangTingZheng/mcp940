package net.minecraft.src;

import com.google.common.collect.AbstractIterator;
import java.util.Iterator;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class BlockPosM extends BlockPos
{
    private int mx;
    private int my;
    private int mz;
    private int level;
    private BlockPosM[] facings;
    private boolean needsUpdate;

    public BlockPosM(int p_i16_1_, int p_i16_2_, int p_i16_3_)
    {
        this(p_i16_1_, p_i16_2_, p_i16_3_, 0);
    }

    public BlockPosM(double p_i17_1_, double p_i17_3_, double p_i17_5_)
    {
        this(MathHelper.floor(p_i17_1_), MathHelper.floor(p_i17_3_), MathHelper.floor(p_i17_5_));
    }

    public BlockPosM(int p_i18_1_, int p_i18_2_, int p_i18_3_, int p_i18_4_)
    {
        super(0, 0, 0);
        this.mx = p_i18_1_;
        this.my = p_i18_2_;
        this.mz = p_i18_3_;
        this.level = p_i18_4_;
    }

    /**
     * Gets the X coordinate.
     */
    public int getX()
    {
        return this.mx;
    }

    /**
     * Gets the Y coordinate.
     */
    public int getY()
    {
        return this.my;
    }

    /**
     * Gets the Z coordinate.
     */
    public int getZ()
    {
        return this.mz;
    }

    public void setXyz(int p_setXyz_1_, int p_setXyz_2_, int p_setXyz_3_)
    {
        this.mx = p_setXyz_1_;
        this.my = p_setXyz_2_;
        this.mz = p_setXyz_3_;
        this.needsUpdate = true;
    }

    public void setXyz(double p_setXyz_1_, double p_setXyz_3_, double p_setXyz_5_)
    {
        this.setXyz(MathHelper.floor(p_setXyz_1_), MathHelper.floor(p_setXyz_3_), MathHelper.floor(p_setXyz_5_));
    }

    /**
     * Offset this BlockPos 1 block in the given direction
     */
    public BlockPos offset(EnumFacing facing)
    {
        if (this.level <= 0)
        {
            return super.offset(facing, 1).toImmutable();
        }
        else
        {
            if (this.facings == null)
            {
                this.facings = new BlockPosM[EnumFacing.VALUES.length];
            }

            if (this.needsUpdate)
            {
                this.update();
            }

            int i = facing.getIndex();
            BlockPosM blockposm = this.facings[i];

            if (blockposm == null)
            {
                int j = this.mx + facing.getFrontOffsetX();
                int k = this.my + facing.getFrontOffsetY();
                int l = this.mz + facing.getFrontOffsetZ();
                blockposm = new BlockPosM(j, k, l, this.level - 1);
                this.facings[i] = blockposm;
            }

            return blockposm;
        }
    }

    /**
     * Offsets this BlockPos n blocks in the given direction
     */
    public BlockPos offset(EnumFacing facing, int n)
    {
        return n == 1 ? this.offset(facing) : super.offset(facing, n).toImmutable();
    }

    private void update()
    {
        for (int i = 0; i < 6; ++i)
        {
            BlockPosM blockposm = this.facings[i];

            if (blockposm != null)
            {
                EnumFacing enumfacing = EnumFacing.VALUES[i];
                int j = this.mx + enumfacing.getFrontOffsetX();
                int k = this.my + enumfacing.getFrontOffsetY();
                int l = this.mz + enumfacing.getFrontOffsetZ();
                blockposm.setXyz(j, k, l);
            }
        }

        this.needsUpdate = false;
    }

    /**
     * Returns a version of this BlockPos that is guaranteed to be immutable.
     *  
     * <p>When storing a BlockPos given to you for an extended period of time, make sure you
     * use this in case the value is changed internally.</p>
     */
    public BlockPos toImmutable()
    {
        return new BlockPos(this.mx, this.my, this.mz);
    }

    public static Iterable getAllInBoxMutable(BlockPos p_getAllInBoxMutable_0_, BlockPos p_getAllInBoxMutable_1_)
    {
        final BlockPos blockpos = new BlockPos(Math.min(p_getAllInBoxMutable_0_.getX(), p_getAllInBoxMutable_1_.getX()), Math.min(p_getAllInBoxMutable_0_.getY(), p_getAllInBoxMutable_1_.getY()), Math.min(p_getAllInBoxMutable_0_.getZ(), p_getAllInBoxMutable_1_.getZ()));
        final BlockPos blockpos1 = new BlockPos(Math.max(p_getAllInBoxMutable_0_.getX(), p_getAllInBoxMutable_1_.getX()), Math.max(p_getAllInBoxMutable_0_.getY(), p_getAllInBoxMutable_1_.getY()), Math.max(p_getAllInBoxMutable_0_.getZ(), p_getAllInBoxMutable_1_.getZ()));
        return new Iterable()
        {
            public Iterator iterator()
            {
                return new AbstractIterator()
                {
                    private BlockPosM theBlockPosM = null;
                    protected BlockPosM computeNext0()
                    {
                        if (this.theBlockPosM == null)
                        {
                            this.theBlockPosM = new BlockPosM(blockpos.getX(), blockpos.getY(), blockpos.getZ(), 3);
                            return this.theBlockPosM;
                        }
                        else if (this.theBlockPosM.equals(blockpos1))
                        {
                            return (BlockPosM)this.endOfData();
                        }
                        else
                        {
                            int i = this.theBlockPosM.getX();
                            int j = this.theBlockPosM.getY();
                            int k = this.theBlockPosM.getZ();

                            if (i < blockpos1.getX())
                            {
                                ++i;
                            }
                            else if (j < blockpos1.getY())
                            {
                                i = blockpos.getX();
                                ++j;
                            }
                            else if (k < blockpos1.getZ())
                            {
                                i = blockpos.getX();
                                j = blockpos.getY();
                                ++k;
                            }

                            this.theBlockPosM.setXyz(i, j, k);
                            return this.theBlockPosM;
                        }
                    }
                    protected Object computeNext()
                    {
                        return this.computeNext0();
                    }
                };
            }
        };
    }
}
