package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenFlowers extends WorldGenerator
{
    private BlockFlower flower;
    private IBlockState state;

    public WorldGenFlowers(BlockFlower flowerIn, BlockFlower.EnumFlowerType type)
    {
        this.setGeneratedBlock(flowerIn, type);
    }

    public void setGeneratedBlock(BlockFlower flowerIn, BlockFlower.EnumFlowerType typeIn)
    {
        this.flower = flowerIn;
        this.state = flowerIn.getDefaultState().withProperty(flowerIn.getTypeProperty(), typeIn);
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        for (int i = 0; i < 64; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.isNether() || blockpos.getY() < 255) && this.flower.canBlockStay(worldIn, blockpos, this.state))
            {
                worldIn.setBlockState(blockpos, this.state, 2);
            }
        }

        return true;
    }
}
