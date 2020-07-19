package net.minecraft.world.gen.structure;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.TemplateManager;

public abstract class StructureComponent
{
    protected StructureBoundingBox boundingBox;
    @Nullable

    /** switches the Coordinate System base off the Bounding Box */
    private EnumFacing coordBaseMode;
    private Mirror mirror;
    private Rotation rotation;

    /** The type ID of this component. */
    protected int componentType;

    public StructureComponent()
    {
    }

    protected StructureComponent(int type)
    {
        this.componentType = type;
    }

    /**
     * Writes structure base data (id, boundingbox, {@link
     * net.minecraft.world.gen.structure.StructureComponent#coordBaseMode coordBase} and {@link
     * net.minecraft.world.gen.structure.StructureComponent#componentType componentType}) to new NBTTagCompound and
     * returns it.
     */
    public final NBTTagCompound createStructureBaseNBT()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setString("id", MapGenStructureIO.getStructureComponentName(this));
        nbttagcompound.setTag("BB", this.boundingBox.toNBTTagIntArray());
        EnumFacing enumfacing = this.getCoordBaseMode();
        nbttagcompound.setInteger("O", enumfacing == null ? -1 : enumfacing.getHorizontalIndex());
        nbttagcompound.setInteger("GD", this.componentType);
        this.writeStructureToNBT(nbttagcompound);
        return nbttagcompound;
    }

    /**
     * (abstract) Helper method to write subclass data to NBT
     */
    protected abstract void writeStructureToNBT(NBTTagCompound tagCompound);

    /**
     * Reads and sets structure base data (boundingbox, {@link
     * net.minecraft.world.gen.structure.StructureComponent#coordBaseMode coordBase} and {@link
     * net.minecraft.world.gen.structure.StructureComponent#componentType componentType})
     */
    public void readStructureBaseNBT(World worldIn, NBTTagCompound tagCompound)
    {
        if (tagCompound.hasKey("BB"))
        {
            this.boundingBox = new StructureBoundingBox(tagCompound.getIntArray("BB"));
        }

        int i = tagCompound.getInteger("O");
        this.setCoordBaseMode(i == -1 ? null : EnumFacing.getHorizontal(i));
        this.componentType = tagCompound.getInteger("GD");
        this.readStructureFromNBT(tagCompound, worldIn.getSaveHandler().getStructureTemplateManager());
    }

    /**
     * (abstract) Helper method to read subclass data from NBT
     */
    protected abstract void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_);

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand)
    {
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public abstract boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn);

    public StructureBoundingBox getBoundingBox()
    {
        return this.boundingBox;
    }

    /**
     * Returns the component type ID of this component.
     */
    public int getComponentType()
    {
        return this.componentType;
    }

    /**
     * Discover if bounding box can fit within the current bounding box object.
     */
    public static StructureComponent findIntersecting(List<StructureComponent> listIn, StructureBoundingBox boundingboxIn)
    {
        for (StructureComponent structurecomponent : listIn)
        {
            if (structurecomponent.getBoundingBox() != null && structurecomponent.getBoundingBox().intersectsWith(boundingboxIn))
            {
                return structurecomponent;
            }
        }

        return null;
    }

    /**
     * checks the entire StructureBoundingBox for Liquids
     */
    protected boolean isLiquidInStructureBoundingBox(World worldIn, StructureBoundingBox boundingboxIn)
    {
        int i = Math.max(this.boundingBox.minX - 1, boundingboxIn.minX);
        int j = Math.max(this.boundingBox.minY - 1, boundingboxIn.minY);
        int k = Math.max(this.boundingBox.minZ - 1, boundingboxIn.minZ);
        int l = Math.min(this.boundingBox.maxX + 1, boundingboxIn.maxX);
        int i1 = Math.min(this.boundingBox.maxY + 1, boundingboxIn.maxY);
        int j1 = Math.min(this.boundingBox.maxZ + 1, boundingboxIn.maxZ);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int k1 = i; k1 <= l; ++k1)
        {
            for (int l1 = k; l1 <= j1; ++l1)
            {
                if (worldIn.getBlockState(blockpos$mutableblockpos.setPos(k1, j, l1)).getMaterial().isLiquid())
                {
                    return true;
                }

                if (worldIn.getBlockState(blockpos$mutableblockpos.setPos(k1, i1, l1)).getMaterial().isLiquid())
                {
                    return true;
                }
            }
        }

        for (int i2 = i; i2 <= l; ++i2)
        {
            for (int k2 = j; k2 <= i1; ++k2)
            {
                if (worldIn.getBlockState(blockpos$mutableblockpos.setPos(i2, k2, k)).getMaterial().isLiquid())
                {
                    return true;
                }

                if (worldIn.getBlockState(blockpos$mutableblockpos.setPos(i2, k2, j1)).getMaterial().isLiquid())
                {
                    return true;
                }
            }
        }

        for (int j2 = k; j2 <= j1; ++j2)
        {
            for (int l2 = j; l2 <= i1; ++l2)
            {
                if (worldIn.getBlockState(blockpos$mutableblockpos.setPos(i, l2, j2)).getMaterial().isLiquid())
                {
                    return true;
                }

                if (worldIn.getBlockState(blockpos$mutableblockpos.setPos(l, l2, j2)).getMaterial().isLiquid())
                {
                    return true;
                }
            }
        }

        return false;
    }

    protected int getXWithOffset(int x, int z)
    {
        EnumFacing enumfacing = this.getCoordBaseMode();

        if (enumfacing == null)
        {
            return x;
        }
        else
        {
            switch (enumfacing)
            {
                case NORTH:
                case SOUTH:
                    return this.boundingBox.minX + x;

                case WEST:
                    return this.boundingBox.maxX - z;

                case EAST:
                    return this.boundingBox.minX + z;

                default:
                    return x;
            }
        }
    }

    protected int getYWithOffset(int y)
    {
        return this.getCoordBaseMode() == null ? y : y + this.boundingBox.minY;
    }

    protected int getZWithOffset(int x, int z)
    {
        EnumFacing enumfacing = this.getCoordBaseMode();

        if (enumfacing == null)
        {
            return z;
        }
        else
        {
            switch (enumfacing)
            {
                case NORTH:
                    return this.boundingBox.maxZ - z;

                case SOUTH:
                    return this.boundingBox.minZ + z;

                case WEST:
                case EAST:
                    return this.boundingBox.minZ + x;

                default:
                    return z;
            }
        }
    }

    protected void setBlockState(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn)
    {
        BlockPos blockpos = new BlockPos(this.getXWithOffset(x, z), this.getYWithOffset(y), this.getZWithOffset(x, z));

        if (boundingboxIn.isVecInside(blockpos))
        {
            if (this.mirror != Mirror.NONE)
            {
                blockstateIn = blockstateIn.withMirror(this.mirror);
            }

            if (this.rotation != Rotation.NONE)
            {
                blockstateIn = blockstateIn.withRotation(this.rotation);
            }

            worldIn.setBlockState(blockpos, blockstateIn, 2);
        }
    }

    protected IBlockState getBlockStateFromPos(World worldIn, int x, int y, int z, StructureBoundingBox boundingboxIn)
    {
        int i = this.getXWithOffset(x, z);
        int j = this.getYWithOffset(y);
        int k = this.getZWithOffset(x, z);
        BlockPos blockpos = new BlockPos(i, j, k);
        return !boundingboxIn.isVecInside(blockpos) ? Blocks.AIR.getDefaultState() : worldIn.getBlockState(blockpos);
    }

    protected int getSkyBrightness(World worldIn, int x, int y, int z, StructureBoundingBox boundingboxIn)
    {
        int i = this.getXWithOffset(x, z);
        int j = this.getYWithOffset(y + 1);
        int k = this.getZWithOffset(x, z);
        BlockPos blockpos = new BlockPos(i, j, k);
        return !boundingboxIn.isVecInside(blockpos) ? EnumSkyBlock.SKY.defaultLightValue : worldIn.getLightFor(EnumSkyBlock.SKY, blockpos);
    }

    /**
     * arguments: (World worldObj, StructureBoundingBox structBB, int minX, int minY, int minZ, int maxX, int maxY, int
     * maxZ)
     */
    protected void fillWithAir(World worldIn, StructureBoundingBox structurebb, int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
    {
        for (int i = minY; i <= maxY; ++i)
        {
            for (int j = minX; j <= maxX; ++j)
            {
                for (int k = minZ; k <= maxZ; ++k)
                {
                    this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), j, i, k, structurebb);
                }
            }
        }
    }

    /**
     * Fill the given area with the selected blocks
     */
    protected void fillWithBlocks(World worldIn, StructureBoundingBox boundingboxIn, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, IBlockState boundaryBlockState, IBlockState insideBlockState, boolean existingOnly)
    {
        for (int i = yMin; i <= yMax; ++i)
        {
            for (int j = xMin; j <= xMax; ++j)
            {
                for (int k = zMin; k <= zMax; ++k)
                {
                    if (!existingOnly || this.getBlockStateFromPos(worldIn, j, i, k, boundingboxIn).getMaterial() != Material.AIR)
                    {
                        if (i != yMin && i != yMax && j != xMin && j != xMax && k != zMin && k != zMax)
                        {
                            this.setBlockState(worldIn, insideBlockState, j, i, k, boundingboxIn);
                        }
                        else
                        {
                            this.setBlockState(worldIn, boundaryBlockState, j, i, k, boundingboxIn);
                        }
                    }
                }
            }
        }
    }

    /**
     * arguments: World worldObj, StructureBoundingBox structBB, int minX, int minY, int minZ, int maxX, int maxY, int
     * maxZ, boolean alwaysreplace, Random rand, StructurePieceBlockSelector blockselector
     */
    protected void fillWithRandomizedBlocks(World worldIn, StructureBoundingBox boundingboxIn, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, boolean alwaysReplace, Random rand, StructureComponent.BlockSelector blockselector)
    {
        for (int i = minY; i <= maxY; ++i)
        {
            for (int j = minX; j <= maxX; ++j)
            {
                for (int k = minZ; k <= maxZ; ++k)
                {
                    if (!alwaysReplace || this.getBlockStateFromPos(worldIn, j, i, k, boundingboxIn).getMaterial() != Material.AIR)
                    {
                        blockselector.selectBlocks(rand, j, i, k, i == minY || i == maxY || j == minX || j == maxX || k == minZ || k == maxZ);
                        this.setBlockState(worldIn, blockselector.getBlockState(), j, i, k, boundingboxIn);
                    }
                }
            }
        }
    }

    protected void generateMaybeBox(World worldIn, StructureBoundingBox sbb, Random rand, float chance, int x1, int y1, int z1, int x2, int y2, int z2, IBlockState edgeState, IBlockState state, boolean requireNonAir, int requiredSkylight)
    {
        for (int i = y1; i <= y2; ++i)
        {
            for (int j = x1; j <= x2; ++j)
            {
                for (int k = z1; k <= z2; ++k)
                {
                    if (rand.nextFloat() <= chance && (!requireNonAir || this.getBlockStateFromPos(worldIn, j, i, k, sbb).getMaterial() != Material.AIR) && (requiredSkylight <= 0 || this.getSkyBrightness(worldIn, j, i, k, sbb) < requiredSkylight))
                    {
                        if (i != y1 && i != y2 && j != x1 && j != x2 && k != z1 && k != z2)
                        {
                            this.setBlockState(worldIn, state, j, i, k, sbb);
                        }
                        else
                        {
                            this.setBlockState(worldIn, edgeState, j, i, k, sbb);
                        }
                    }
                }
            }
        }
    }

    protected void randomlyPlaceBlock(World worldIn, StructureBoundingBox boundingboxIn, Random rand, float chance, int x, int y, int z, IBlockState blockstateIn)
    {
        if (rand.nextFloat() < chance)
        {
            this.setBlockState(worldIn, blockstateIn, x, y, z, boundingboxIn);
        }
    }

    protected void randomlyRareFillWithBlocks(World worldIn, StructureBoundingBox boundingboxIn, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, IBlockState blockstateIn, boolean excludeAir)
    {
        float f = (float)(maxX - minX + 1);
        float f1 = (float)(maxY - minY + 1);
        float f2 = (float)(maxZ - minZ + 1);
        float f3 = (float)minX + f / 2.0F;
        float f4 = (float)minZ + f2 / 2.0F;

        for (int i = minY; i <= maxY; ++i)
        {
            float f5 = (float)(i - minY) / f1;

            for (int j = minX; j <= maxX; ++j)
            {
                float f6 = ((float)j - f3) / (f * 0.5F);

                for (int k = minZ; k <= maxZ; ++k)
                {
                    float f7 = ((float)k - f4) / (f2 * 0.5F);

                    if (!excludeAir || this.getBlockStateFromPos(worldIn, j, i, k, boundingboxIn).getMaterial() != Material.AIR)
                    {
                        float f8 = f6 * f6 + f5 * f5 + f7 * f7;

                        if (f8 <= 1.05F)
                        {
                            this.setBlockState(worldIn, blockstateIn, j, i, k, boundingboxIn);
                        }
                    }
                }
            }
        }
    }

    /**
     * Deletes all continuous blocks from selected position upwards. Stops at hitting air.
     */
    protected void clearCurrentPositionBlocksUpwards(World worldIn, int x, int y, int z, StructureBoundingBox structurebb)
    {
        BlockPos blockpos = new BlockPos(this.getXWithOffset(x, z), this.getYWithOffset(y), this.getZWithOffset(x, z));

        if (structurebb.isVecInside(blockpos))
        {
            while (!worldIn.isAirBlock(blockpos) && blockpos.getY() < 255)
            {
                worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
                blockpos = blockpos.up();
            }
        }
    }

    /**
     * Replaces air and liquid from given position downwards. Stops when hitting anything else than air or liquid
     */
    protected void replaceAirAndLiquidDownwards(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn)
    {
        int i = this.getXWithOffset(x, z);
        int j = this.getYWithOffset(y);
        int k = this.getZWithOffset(x, z);

        if (boundingboxIn.isVecInside(new BlockPos(i, j, k)))
        {
            while ((worldIn.isAirBlock(new BlockPos(i, j, k)) || worldIn.getBlockState(new BlockPos(i, j, k)).getMaterial().isLiquid()) && j > 1)
            {
                worldIn.setBlockState(new BlockPos(i, j, k), blockstateIn, 2);
                --j;
            }
        }
    }

    /**
     * Adds chest to the structure and sets its contents
     */
    protected boolean generateChest(World worldIn, StructureBoundingBox structurebb, Random randomIn, int x, int y, int z, ResourceLocation loot)
    {
        BlockPos blockpos = new BlockPos(this.getXWithOffset(x, z), this.getYWithOffset(y), this.getZWithOffset(x, z));
        return this.generateChest(worldIn, structurebb, randomIn, blockpos, loot, (IBlockState)null);
    }

    protected boolean generateChest(World p_191080_1_, StructureBoundingBox p_191080_2_, Random p_191080_3_, BlockPos p_191080_4_, ResourceLocation p_191080_5_, @Nullable IBlockState p_191080_6_)
    {
        if (p_191080_2_.isVecInside(p_191080_4_) && p_191080_1_.getBlockState(p_191080_4_).getBlock() != Blocks.CHEST)
        {
            if (p_191080_6_ == null)
            {
                p_191080_6_ = Blocks.CHEST.correctFacing(p_191080_1_, p_191080_4_, Blocks.CHEST.getDefaultState());
            }

            p_191080_1_.setBlockState(p_191080_4_, p_191080_6_, 2);
            TileEntity tileentity = p_191080_1_.getTileEntity(p_191080_4_);

            if (tileentity instanceof TileEntityChest)
            {
                ((TileEntityChest)tileentity).setLootTable(p_191080_5_, p_191080_3_.nextLong());
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    protected boolean createDispenser(World worldIn, StructureBoundingBox sbb, Random rand, int x, int y, int z, EnumFacing facing, ResourceLocation lootTableIn)
    {
        BlockPos blockpos = new BlockPos(this.getXWithOffset(x, z), this.getYWithOffset(y), this.getZWithOffset(x, z));

        if (sbb.isVecInside(blockpos) && worldIn.getBlockState(blockpos).getBlock() != Blocks.DISPENSER)
        {
            this.setBlockState(worldIn, Blocks.DISPENSER.getDefaultState().withProperty(BlockDispenser.FACING, facing), x, y, z, sbb);
            TileEntity tileentity = worldIn.getTileEntity(blockpos);

            if (tileentity instanceof TileEntityDispenser)
            {
                ((TileEntityDispenser)tileentity).setLootTable(lootTableIn, rand.nextLong());
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    protected void generateDoor(World worldIn, StructureBoundingBox sbb, Random rand, int x, int y, int z, EnumFacing facing, BlockDoor door)
    {
        this.setBlockState(worldIn, door.getDefaultState().withProperty(BlockDoor.FACING, facing), x, y, z, sbb);
        this.setBlockState(worldIn, door.getDefaultState().withProperty(BlockDoor.FACING, facing).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), x, y + 1, z, sbb);
    }

    public void offset(int x, int y, int z)
    {
        this.boundingBox.offset(x, y, z);
    }

    @Nullable
    public EnumFacing getCoordBaseMode()
    {
        return this.coordBaseMode;
    }

    public void setCoordBaseMode(@Nullable EnumFacing facing)
    {
        this.coordBaseMode = facing;

        if (facing == null)
        {
            this.rotation = Rotation.NONE;
            this.mirror = Mirror.NONE;
        }
        else
        {
            switch (facing)
            {
                case SOUTH:
                    this.mirror = Mirror.LEFT_RIGHT;
                    this.rotation = Rotation.NONE;
                    break;

                case WEST:
                    this.mirror = Mirror.LEFT_RIGHT;
                    this.rotation = Rotation.CLOCKWISE_90;
                    break;

                case EAST:
                    this.mirror = Mirror.NONE;
                    this.rotation = Rotation.CLOCKWISE_90;
                    break;

                default:
                    this.mirror = Mirror.NONE;
                    this.rotation = Rotation.NONE;
            }
        }
    }

    public abstract static class BlockSelector
    {
        protected IBlockState blockstate = Blocks.AIR.getDefaultState();

        public abstract void selectBlocks(Random rand, int x, int y, int z, boolean wall);

        public IBlockState getBlockState()
        {
            return this.blockstate;
        }
    }
}
