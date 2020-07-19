package net.minecraft.block.state.pattern;

import com.google.common.base.MoreObjects;
import com.google.common.base.Predicate;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class BlockPattern
{
    private final Predicate<BlockWorldState>[][][] blockMatches;
    private final int fingerLength;
    private final int thumbLength;
    private final int palmLength;

    public BlockPattern(Predicate<BlockWorldState>[][][] predicatesIn)
    {
        this.blockMatches = predicatesIn;
        this.fingerLength = predicatesIn.length;

        if (this.fingerLength > 0)
        {
            this.thumbLength = predicatesIn[0].length;

            if (this.thumbLength > 0)
            {
                this.palmLength = predicatesIn[0][0].length;
            }
            else
            {
                this.palmLength = 0;
            }
        }
        else
        {
            this.thumbLength = 0;
            this.palmLength = 0;
        }
    }

    public int getFingerLength()
    {
        return this.fingerLength;
    }

    public int getThumbLength()
    {
        return this.thumbLength;
    }

    public int getPalmLength()
    {
        return this.palmLength;
    }

    @Nullable

    /**
     * checks that the given pattern & rotation is at the block co-ordinates.
     */
    private BlockPattern.PatternHelper checkPatternAt(BlockPos pos, EnumFacing finger, EnumFacing thumb, LoadingCache<BlockPos, BlockWorldState> lcache)
    {
        for (int i = 0; i < this.palmLength; ++i)
        {
            for (int j = 0; j < this.thumbLength; ++j)
            {
                for (int k = 0; k < this.fingerLength; ++k)
                {
                    if (!this.blockMatches[k][j][i].apply(lcache.getUnchecked(translateOffset(pos, finger, thumb, i, j, k))))
                    {
                        return null;
                    }
                }
            }
        }

        return new BlockPattern.PatternHelper(pos, finger, thumb, lcache, this.palmLength, this.thumbLength, this.fingerLength);
    }

    @Nullable

    /**
     * Calculates whether the given world position matches the pattern. Warning, fairly heavy function. @return a
     * BlockPattern.PatternHelper if found, null otherwise.
     */
    public BlockPattern.PatternHelper match(World worldIn, BlockPos pos)
    {
        LoadingCache<BlockPos, BlockWorldState> loadingcache = createLoadingCache(worldIn, false);
        int i = Math.max(Math.max(this.palmLength, this.thumbLength), this.fingerLength);

        for (BlockPos blockpos : BlockPos.getAllInBox(pos, pos.add(i - 1, i - 1, i - 1)))
        {
            for (EnumFacing enumfacing : EnumFacing.values())
            {
                for (EnumFacing enumfacing1 : EnumFacing.values())
                {
                    if (enumfacing1 != enumfacing && enumfacing1 != enumfacing.getOpposite())
                    {
                        BlockPattern.PatternHelper blockpattern$patternhelper = this.checkPatternAt(blockpos, enumfacing, enumfacing1, loadingcache);

                        if (blockpattern$patternhelper != null)
                        {
                            return blockpattern$patternhelper;
                        }
                    }
                }
            }
        }

        return null;
    }

    public static LoadingCache<BlockPos, BlockWorldState> createLoadingCache(World worldIn, boolean forceLoadIn)
    {
        return CacheBuilder.newBuilder().<BlockPos, BlockWorldState>build(new BlockPattern.CacheLoader(worldIn, forceLoadIn));
    }

    /**
     * Offsets the position of pos in the direction of finger and thumb facing by offset amounts, follows the right-hand
     * rule for cross products (finger, thumb, palm) @return A new BlockPos offset in the facing directions
     */
    protected static BlockPos translateOffset(BlockPos pos, EnumFacing finger, EnumFacing thumb, int palmOffset, int thumbOffset, int fingerOffset)
    {
        if (finger != thumb && finger != thumb.getOpposite())
        {
            Vec3i vec3i = new Vec3i(finger.getFrontOffsetX(), finger.getFrontOffsetY(), finger.getFrontOffsetZ());
            Vec3i vec3i1 = new Vec3i(thumb.getFrontOffsetX(), thumb.getFrontOffsetY(), thumb.getFrontOffsetZ());
            Vec3i vec3i2 = vec3i.crossProduct(vec3i1);
            return pos.add(vec3i1.getX() * -thumbOffset + vec3i2.getX() * palmOffset + vec3i.getX() * fingerOffset, vec3i1.getY() * -thumbOffset + vec3i2.getY() * palmOffset + vec3i.getY() * fingerOffset, vec3i1.getZ() * -thumbOffset + vec3i2.getZ() * palmOffset + vec3i.getZ() * fingerOffset);
        }
        else
        {
            throw new IllegalArgumentException("Invalid forwards & up combination");
        }
    }

    static class CacheLoader extends com.google.common.cache.CacheLoader<BlockPos, BlockWorldState>
    {
        private final World world;
        private final boolean forceLoad;

        public CacheLoader(World worldIn, boolean forceLoadIn)
        {
            this.world = worldIn;
            this.forceLoad = forceLoadIn;
        }

        public BlockWorldState load(BlockPos p_load_1_) throws Exception
        {
            return new BlockWorldState(this.world, p_load_1_, this.forceLoad);
        }
    }

    public static class PatternHelper
    {
        private final BlockPos frontTopLeft;
        private final EnumFacing forwards;
        private final EnumFacing up;
        private final LoadingCache<BlockPos, BlockWorldState> lcache;
        private final int width;
        private final int height;
        private final int depth;

        public PatternHelper(BlockPos posIn, EnumFacing fingerIn, EnumFacing thumbIn, LoadingCache<BlockPos, BlockWorldState> lcacheIn, int widthIn, int heightIn, int depthIn)
        {
            this.frontTopLeft = posIn;
            this.forwards = fingerIn;
            this.up = thumbIn;
            this.lcache = lcacheIn;
            this.width = widthIn;
            this.height = heightIn;
            this.depth = depthIn;
        }

        public BlockPos getFrontTopLeft()
        {
            return this.frontTopLeft;
        }

        public EnumFacing getForwards()
        {
            return this.forwards;
        }

        public EnumFacing getUp()
        {
            return this.up;
        }

        public int getWidth()
        {
            return this.width;
        }

        public int getHeight()
        {
            return this.height;
        }

        public BlockWorldState translateOffset(int palmOffset, int thumbOffset, int fingerOffset)
        {
            return this.lcache.getUnchecked(BlockPattern.translateOffset(this.frontTopLeft, this.getForwards(), this.getUp(), palmOffset, thumbOffset, fingerOffset));
        }

        public String toString()
        {
            return MoreObjects.toStringHelper(this).add("up", this.up).add("forwards", this.forwards).add("frontTopLeft", this.frontTopLeft).toString();
        }
    }
}
