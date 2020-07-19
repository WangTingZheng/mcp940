package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class MapGenCavesHell extends MapGenBase
{
    protected static final IBlockState AIR = Blocks.AIR.getDefaultState();

    protected void addRoom(long p_180705_1_, int p_180705_3_, int p_180705_4_, ChunkPrimer p_180705_5_, double p_180705_6_, double p_180705_8_, double p_180705_10_)
    {
        this.addTunnel(p_180705_1_, p_180705_3_, p_180705_4_, p_180705_5_, p_180705_6_, p_180705_8_, p_180705_10_, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
    }

    protected void addTunnel(long p_180704_1_, int p_180704_3_, int p_180704_4_, ChunkPrimer p_180704_5_, double p_180704_6_, double p_180704_8_, double p_180704_10_, float p_180704_12_, float p_180704_13_, float p_180704_14_, int p_180704_15_, int p_180704_16_, double p_180704_17_)
    {
        double d0 = (double)(p_180704_3_ * 16 + 8);
        double d1 = (double)(p_180704_4_ * 16 + 8);
        float f = 0.0F;
        float f1 = 0.0F;
        Random random = new Random(p_180704_1_);

        if (p_180704_16_ <= 0)
        {
            int i = this.range * 16 - 16;
            p_180704_16_ = i - random.nextInt(i / 4);
        }

        boolean flag1 = false;

        if (p_180704_15_ == -1)
        {
            p_180704_15_ = p_180704_16_ / 2;
            flag1 = true;
        }

        int j = random.nextInt(p_180704_16_ / 2) + p_180704_16_ / 4;

        for (boolean flag = random.nextInt(6) == 0; p_180704_15_ < p_180704_16_; ++p_180704_15_)
        {
            double d2 = 1.5D + (double)(MathHelper.sin((float)p_180704_15_ * (float)Math.PI / (float)p_180704_16_) * p_180704_12_);
            double d3 = d2 * p_180704_17_;
            float f2 = MathHelper.cos(p_180704_14_);
            float f3 = MathHelper.sin(p_180704_14_);
            p_180704_6_ += (double)(MathHelper.cos(p_180704_13_) * f2);
            p_180704_8_ += (double)f3;
            p_180704_10_ += (double)(MathHelper.sin(p_180704_13_) * f2);

            if (flag)
            {
                p_180704_14_ = p_180704_14_ * 0.92F;
            }
            else
            {
                p_180704_14_ = p_180704_14_ * 0.7F;
            }

            p_180704_14_ = p_180704_14_ + f1 * 0.1F;
            p_180704_13_ += f * 0.1F;
            f1 = f1 * 0.9F;
            f = f * 0.75F;
            f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            f = f + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

            if (!flag1 && p_180704_15_ == j && p_180704_12_ > 1.0F)
            {
                this.addTunnel(random.nextLong(), p_180704_3_, p_180704_4_, p_180704_5_, p_180704_6_, p_180704_8_, p_180704_10_, random.nextFloat() * 0.5F + 0.5F, p_180704_13_ - ((float)Math.PI / 2F), p_180704_14_ / 3.0F, p_180704_15_, p_180704_16_, 1.0D);
                this.addTunnel(random.nextLong(), p_180704_3_, p_180704_4_, p_180704_5_, p_180704_6_, p_180704_8_, p_180704_10_, random.nextFloat() * 0.5F + 0.5F, p_180704_13_ + ((float)Math.PI / 2F), p_180704_14_ / 3.0F, p_180704_15_, p_180704_16_, 1.0D);
                return;
            }

            if (flag1 || random.nextInt(4) != 0)
            {
                double d4 = p_180704_6_ - d0;
                double d5 = p_180704_10_ - d1;
                double d6 = (double)(p_180704_16_ - p_180704_15_);
                double d7 = (double)(p_180704_12_ + 2.0F + 16.0F);

                if (d4 * d4 + d5 * d5 - d6 * d6 > d7 * d7)
                {
                    return;
                }

                if (p_180704_6_ >= d0 - 16.0D - d2 * 2.0D && p_180704_10_ >= d1 - 16.0D - d2 * 2.0D && p_180704_6_ <= d0 + 16.0D + d2 * 2.0D && p_180704_10_ <= d1 + 16.0D + d2 * 2.0D)
                {
                    int j2 = MathHelper.floor(p_180704_6_ - d2) - p_180704_3_ * 16 - 1;
                    int k = MathHelper.floor(p_180704_6_ + d2) - p_180704_3_ * 16 + 1;
                    int k2 = MathHelper.floor(p_180704_8_ - d3) - 1;
                    int l = MathHelper.floor(p_180704_8_ + d3) + 1;
                    int l2 = MathHelper.floor(p_180704_10_ - d2) - p_180704_4_ * 16 - 1;
                    int i1 = MathHelper.floor(p_180704_10_ + d2) - p_180704_4_ * 16 + 1;

                    if (j2 < 0)
                    {
                        j2 = 0;
                    }

                    if (k > 16)
                    {
                        k = 16;
                    }

                    if (k2 < 1)
                    {
                        k2 = 1;
                    }

                    if (l > 120)
                    {
                        l = 120;
                    }

                    if (l2 < 0)
                    {
                        l2 = 0;
                    }

                    if (i1 > 16)
                    {
                        i1 = 16;
                    }

                    boolean flag2 = false;

                    for (int j1 = j2; !flag2 && j1 < k; ++j1)
                    {
                        for (int k1 = l2; !flag2 && k1 < i1; ++k1)
                        {
                            for (int l1 = l + 1; !flag2 && l1 >= k2 - 1; --l1)
                            {
                                if (l1 >= 0 && l1 < 128)
                                {
                                    IBlockState iblockstate = p_180704_5_.getBlockState(j1, l1, k1);

                                    if (iblockstate.getBlock() == Blocks.FLOWING_LAVA || iblockstate.getBlock() == Blocks.LAVA)
                                    {
                                        flag2 = true;
                                    }

                                    if (l1 != k2 - 1 && j1 != j2 && j1 != k - 1 && k1 != l2 && k1 != i1 - 1)
                                    {
                                        l1 = k2;
                                    }
                                }
                            }
                        }
                    }

                    if (!flag2)
                    {
                        for (int i3 = j2; i3 < k; ++i3)
                        {
                            double d10 = ((double)(i3 + p_180704_3_ * 16) + 0.5D - p_180704_6_) / d2;

                            for (int j3 = l2; j3 < i1; ++j3)
                            {
                                double d8 = ((double)(j3 + p_180704_4_ * 16) + 0.5D - p_180704_10_) / d2;

                                for (int i2 = l; i2 > k2; --i2)
                                {
                                    double d9 = ((double)(i2 - 1) + 0.5D - p_180704_8_) / d3;

                                    if (d9 > -0.7D && d10 * d10 + d9 * d9 + d8 * d8 < 1.0D)
                                    {
                                        IBlockState iblockstate1 = p_180704_5_.getBlockState(i3, i2, j3);

                                        if (iblockstate1.getBlock() == Blocks.NETHERRACK || iblockstate1.getBlock() == Blocks.DIRT || iblockstate1.getBlock() == Blocks.GRASS)
                                        {
                                            p_180704_5_.setBlockState(i3, i2, j3, AIR);
                                        }
                                    }
                                }
                            }
                        }

                        if (flag1)
                        {
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Recursively called by generate()
     */
    protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int originalX, int originalZ, ChunkPrimer chunkPrimerIn)
    {
        int i = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(10) + 1) + 1);

        if (this.rand.nextInt(5) != 0)
        {
            i = 0;
        }

        for (int j = 0; j < i; ++j)
        {
            double d0 = (double)(chunkX * 16 + this.rand.nextInt(16));
            double d1 = (double)this.rand.nextInt(128);
            double d2 = (double)(chunkZ * 16 + this.rand.nextInt(16));
            int k = 1;

            if (this.rand.nextInt(4) == 0)
            {
                this.addRoom(this.rand.nextLong(), originalX, originalZ, chunkPrimerIn, d0, d1, d2);
                k += this.rand.nextInt(4);
            }

            for (int l = 0; l < k; ++l)
            {
                float f = this.rand.nextFloat() * ((float)Math.PI * 2F);
                float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
                float f2 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();
                this.addTunnel(this.rand.nextLong(), originalX, originalZ, chunkPrimerIn, d0, d1, d2, f2 * 2.0F, f, f1, 0, 0, 0.5D);
            }
        }
    }
}
