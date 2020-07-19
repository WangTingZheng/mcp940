package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.src.Config;
import net.minecraft.src.CustomColors;
import net.minecraft.src.RenderEnv;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import shadersmod.client.SVertexBuilder;

public class BlockFluidRenderer
{
    private final BlockColors blockColors;
    private final TextureAtlasSprite[] atlasSpritesLava = new TextureAtlasSprite[2];
    private final TextureAtlasSprite[] atlasSpritesWater = new TextureAtlasSprite[2];
    private TextureAtlasSprite atlasSpriteWaterOverlay;

    public BlockFluidRenderer(BlockColors blockColorsIn)
    {
        this.blockColors = blockColorsIn;
        this.initAtlasSprites();
    }

    protected void initAtlasSprites()
    {
        TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
        this.atlasSpritesLava[0] = texturemap.getAtlasSprite("minecraft:blocks/lava_still");
        this.atlasSpritesLava[1] = texturemap.getAtlasSprite("minecraft:blocks/lava_flow");
        this.atlasSpritesWater[0] = texturemap.getAtlasSprite("minecraft:blocks/water_still");
        this.atlasSpritesWater[1] = texturemap.getAtlasSprite("minecraft:blocks/water_flow");
        this.atlasSpriteWaterOverlay = texturemap.getAtlasSprite("minecraft:blocks/water_overlay");
    }

    public boolean renderFluid(IBlockAccess blockAccess, IBlockState blockStateIn, BlockPos blockPosIn, BufferBuilder worldRendererIn)
    {
        boolean flag3;

        try
        {
            if (Config.isShaders())
            {
                SVertexBuilder.pushEntity(blockStateIn, blockPosIn, blockAccess, worldRendererIn);
            }

            BlockLiquid blockliquid = (BlockLiquid)blockStateIn.getBlock();
            boolean flag = blockStateIn.getMaterial() == Material.LAVA;
            TextureAtlasSprite[] atextureatlassprite = flag ? this.atlasSpritesLava : this.atlasSpritesWater;
            RenderEnv renderenv = worldRendererIn.getRenderEnv(blockAccess, blockStateIn, blockPosIn);
            int i = CustomColors.getFluidColor(blockAccess, blockStateIn, blockPosIn, renderenv);
            float f = (float)(i >> 16 & 255) / 255.0F;
            float f1 = (float)(i >> 8 & 255) / 255.0F;
            float f2 = (float)(i & 255) / 255.0F;
            boolean flag1 = blockStateIn.shouldSideBeRendered(blockAccess, blockPosIn, EnumFacing.UP);
            boolean flag2 = blockStateIn.shouldSideBeRendered(blockAccess, blockPosIn, EnumFacing.DOWN);
            boolean[] aboolean = renderenv.getBorderFlags();
            aboolean[0] = blockStateIn.shouldSideBeRendered(blockAccess, blockPosIn, EnumFacing.NORTH);
            aboolean[1] = blockStateIn.shouldSideBeRendered(blockAccess, blockPosIn, EnumFacing.SOUTH);
            aboolean[2] = blockStateIn.shouldSideBeRendered(blockAccess, blockPosIn, EnumFacing.WEST);
            aboolean[3] = blockStateIn.shouldSideBeRendered(blockAccess, blockPosIn, EnumFacing.EAST);

            if (flag1 || flag2 || aboolean[0] || aboolean[1] || aboolean[2] || aboolean[3])
            {
                flag3 = false;
                float f3 = 0.5F;
                float f4 = 1.0F;
                float f5 = 0.8F;
                float f6 = 0.6F;
                Material material = blockStateIn.getMaterial();
                float f7 = this.getFluidHeight(blockAccess, blockPosIn, material);
                float f8 = this.getFluidHeight(blockAccess, blockPosIn.south(), material);
                float f9 = this.getFluidHeight(blockAccess, blockPosIn.east().south(), material);
                float f10 = this.getFluidHeight(blockAccess, blockPosIn.east(), material);
                double d0 = (double)blockPosIn.getX();
                double d1 = (double)blockPosIn.getY();
                double d2 = (double)blockPosIn.getZ();
                float f11 = 0.001F;

                if (flag1)
                {
                    flag3 = true;
                    float f12 = BlockLiquid.getSlopeAngle(blockAccess, blockPosIn, material, blockStateIn);
                    TextureAtlasSprite textureatlassprite = f12 > -999.0F ? atextureatlassprite[1] : atextureatlassprite[0];
                    worldRendererIn.setSprite(textureatlassprite);
                    f7 -= 0.001F;
                    f8 -= 0.001F;
                    f9 -= 0.001F;
                    f10 -= 0.001F;
                    float f13;
                    float f14;
                    float f15;
                    float f16;
                    float f17;
                    float f18;
                    float f19;
                    float f20;

                    if (f12 < -999.0F)
                    {
                        f13 = textureatlassprite.getInterpolatedU(0.0D);
                        f17 = textureatlassprite.getInterpolatedV(0.0D);
                        f14 = f13;
                        f18 = textureatlassprite.getInterpolatedV(16.0D);
                        f15 = textureatlassprite.getInterpolatedU(16.0D);
                        f19 = f18;
                        f16 = f15;
                        f20 = f17;
                    }
                    else
                    {
                        float f21 = MathHelper.sin(f12) * 0.25F;
                        float f22 = MathHelper.cos(f12) * 0.25F;
                        float f23 = 8.0F;
                        f13 = textureatlassprite.getInterpolatedU((double)(8.0F + (-f22 - f21) * 16.0F));
                        f17 = textureatlassprite.getInterpolatedV((double)(8.0F + (-f22 + f21) * 16.0F));
                        f14 = textureatlassprite.getInterpolatedU((double)(8.0F + (-f22 + f21) * 16.0F));
                        f18 = textureatlassprite.getInterpolatedV((double)(8.0F + (f22 + f21) * 16.0F));
                        f15 = textureatlassprite.getInterpolatedU((double)(8.0F + (f22 + f21) * 16.0F));
                        f19 = textureatlassprite.getInterpolatedV((double)(8.0F + (f22 - f21) * 16.0F));
                        f16 = textureatlassprite.getInterpolatedU((double)(8.0F + (f22 - f21) * 16.0F));
                        f20 = textureatlassprite.getInterpolatedV((double)(8.0F + (-f22 - f21) * 16.0F));
                    }

                    int k2 = blockStateIn.getPackedLightmapCoords(blockAccess, blockPosIn);
                    int l2 = k2 >> 16 & 65535;
                    int i3 = k2 & 65535;
                    float f24 = 1.0F * f;
                    float f25 = 1.0F * f1;
                    float f26 = 1.0F * f2;
                    worldRendererIn.pos(d0 + 0.0D, d1 + (double)f7, d2 + 0.0D).color(f24, f25, f26, 1.0F).tex((double)f13, (double)f17).lightmap(l2, i3).endVertex();
                    worldRendererIn.pos(d0 + 0.0D, d1 + (double)f8, d2 + 1.0D).color(f24, f25, f26, 1.0F).tex((double)f14, (double)f18).lightmap(l2, i3).endVertex();
                    worldRendererIn.pos(d0 + 1.0D, d1 + (double)f9, d2 + 1.0D).color(f24, f25, f26, 1.0F).tex((double)f15, (double)f19).lightmap(l2, i3).endVertex();
                    worldRendererIn.pos(d0 + 1.0D, d1 + (double)f10, d2 + 0.0D).color(f24, f25, f26, 1.0F).tex((double)f16, (double)f20).lightmap(l2, i3).endVertex();

                    if (blockliquid.shouldRenderSides(blockAccess, blockPosIn.up()))
                    {
                        worldRendererIn.pos(d0 + 0.0D, d1 + (double)f7, d2 + 0.0D).color(f24, f25, f26, 1.0F).tex((double)f13, (double)f17).lightmap(l2, i3).endVertex();
                        worldRendererIn.pos(d0 + 1.0D, d1 + (double)f10, d2 + 0.0D).color(f24, f25, f26, 1.0F).tex((double)f16, (double)f20).lightmap(l2, i3).endVertex();
                        worldRendererIn.pos(d0 + 1.0D, d1 + (double)f9, d2 + 1.0D).color(f24, f25, f26, 1.0F).tex((double)f15, (double)f19).lightmap(l2, i3).endVertex();
                        worldRendererIn.pos(d0 + 0.0D, d1 + (double)f8, d2 + 1.0D).color(f24, f25, f26, 1.0F).tex((double)f14, (double)f18).lightmap(l2, i3).endVertex();
                    }
                }

                if (flag2)
                {
                    float f38 = atextureatlassprite[0].getMinU();
                    float f39 = atextureatlassprite[0].getMaxU();
                    float f40 = atextureatlassprite[0].getMinV();
                    float f41 = atextureatlassprite[0].getMaxV();
                    int l1 = blockStateIn.getPackedLightmapCoords(blockAccess, blockPosIn.down());
                    int i2 = l1 >> 16 & 65535;
                    int j2 = l1 & 65535;
                    worldRendererIn.pos(d0, d1, d2 + 1.0D).color(f * 0.5F, f1 * 0.5F, f2 * 0.5F, 1.0F).tex((double)f38, (double)f41).lightmap(i2, j2).endVertex();
                    worldRendererIn.pos(d0, d1, d2).color(f * 0.5F, f1 * 0.5F, f2 * 0.5F, 1.0F).tex((double)f38, (double)f40).lightmap(i2, j2).endVertex();
                    worldRendererIn.pos(d0 + 1.0D, d1, d2).color(f * 0.5F, f1 * 0.5F, f2 * 0.5F, 1.0F).tex((double)f39, (double)f40).lightmap(i2, j2).endVertex();
                    worldRendererIn.pos(d0 + 1.0D, d1, d2 + 1.0D).color(f * 0.5F, f1 * 0.5F, f2 * 0.5F, 1.0F).tex((double)f39, (double)f41).lightmap(i2, j2).endVertex();
                    flag3 = true;
                }

                for (int i1 = 0; i1 < 4; ++i1)
                {
                    int j1 = 0;
                    int k1 = 0;

                    if (i1 == 0)
                    {
                        --k1;
                    }

                    if (i1 == 1)
                    {
                        ++k1;
                    }

                    if (i1 == 2)
                    {
                        --j1;
                    }

                    if (i1 == 3)
                    {
                        ++j1;
                    }

                    BlockPos blockpos = blockPosIn.add(j1, 0, k1);
                    TextureAtlasSprite textureatlassprite1 = atextureatlassprite[1];
                    worldRendererIn.setSprite(textureatlassprite1);
                    float f42 = 0.0F;
                    float f43 = 0.0F;

                    if (!flag)
                    {
                        IBlockState iblockstate = blockAccess.getBlockState(blockpos);
                        Block block = iblockstate.getBlock();

                        if (block == Blocks.GLASS || block == Blocks.STAINED_GLASS || block == Blocks.BEACON || block == Blocks.SLIME_BLOCK)
                        {
                            textureatlassprite1 = this.atlasSpriteWaterOverlay;
                            worldRendererIn.setSprite(textureatlassprite1);
                        }

                        if (block == Blocks.FARMLAND || block == Blocks.GRASS_PATH)
                        {
                            f42 = 0.9375F;
                            f43 = 0.9375F;
                        }

                        if (block instanceof BlockSlab)
                        {
                            BlockSlab blockslab = (BlockSlab)block;

                            if (!blockslab.isDouble() && iblockstate.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.BOTTOM)
                            {
                                f42 = 0.5F;
                                f43 = 0.5F;
                            }
                        }
                    }

                    if (aboolean[i1])
                    {
                        float f44;
                        float f45;
                        double d3;
                        double d4;
                        double d5;
                        double d6;

                        if (i1 == 0)
                        {
                            f44 = f7;
                            f45 = f10;
                            d3 = d0;
                            d5 = d0 + 1.0D;
                            d4 = d2 + 0.0010000000474974513D;
                            d6 = d2 + 0.0010000000474974513D;
                        }
                        else if (i1 == 1)
                        {
                            f44 = f9;
                            f45 = f8;
                            d3 = d0 + 1.0D;
                            d5 = d0;
                            d4 = d2 + 1.0D - 0.0010000000474974513D;
                            d6 = d2 + 1.0D - 0.0010000000474974513D;
                        }
                        else if (i1 == 2)
                        {
                            f44 = f8;
                            f45 = f7;
                            d3 = d0 + 0.0010000000474974513D;
                            d5 = d0 + 0.0010000000474974513D;
                            d4 = d2 + 1.0D;
                            d6 = d2;
                        }
                        else
                        {
                            f44 = f10;
                            f45 = f9;
                            d3 = d0 + 1.0D - 0.0010000000474974513D;
                            d5 = d0 + 1.0D - 0.0010000000474974513D;
                            d4 = d2;
                            d6 = d2 + 1.0D;
                        }

                        if (f44 > f42 || f45 > f43)
                        {
                            f42 = Math.min(f42, f44);
                            f43 = Math.min(f43, f45);

                            if (f42 > f11)
                            {
                                f42 -= f11;
                            }

                            if (f43 > f11)
                            {
                                f43 -= f11;
                            }

                            flag3 = true;
                            float f27 = textureatlassprite1.getInterpolatedU(0.0D);
                            float f28 = textureatlassprite1.getInterpolatedU(8.0D);
                            float f29 = textureatlassprite1.getInterpolatedV((double)((1.0F - f44) * 16.0F * 0.5F));
                            float f30 = textureatlassprite1.getInterpolatedV((double)((1.0F - f45) * 16.0F * 0.5F));
                            float f31 = textureatlassprite1.getInterpolatedV(8.0D);
                            float f32 = textureatlassprite1.getInterpolatedV((double)((1.0F - f42) * 16.0F * 0.5F));
                            float f33 = textureatlassprite1.getInterpolatedV((double)((1.0F - f43) * 16.0F * 0.5F));
                            int j = blockStateIn.getPackedLightmapCoords(blockAccess, blockpos);
                            int k = j >> 16 & 65535;
                            int l = j & 65535;
                            float f34 = i1 < 2 ? 0.8F : 0.6F;
                            float f35 = 1.0F * f34 * f;
                            float f36 = 1.0F * f34 * f1;
                            float f37 = 1.0F * f34 * f2;
                            worldRendererIn.pos(d3, d1 + (double)f44, d4).color(f35, f36, f37, 1.0F).tex((double)f27, (double)f29).lightmap(k, l).endVertex();
                            worldRendererIn.pos(d5, d1 + (double)f45, d6).color(f35, f36, f37, 1.0F).tex((double)f28, (double)f30).lightmap(k, l).endVertex();
                            worldRendererIn.pos(d5, d1 + (double)f43, d6).color(f35, f36, f37, 1.0F).tex((double)f28, (double)f33).lightmap(k, l).endVertex();
                            worldRendererIn.pos(d3, d1 + (double)f42, d4).color(f35, f36, f37, 1.0F).tex((double)f27, (double)f32).lightmap(k, l).endVertex();

                            if (textureatlassprite1 != this.atlasSpriteWaterOverlay)
                            {
                                worldRendererIn.pos(d3, d1 + (double)f42, d4).color(f35, f36, f37, 1.0F).tex((double)f27, (double)f32).lightmap(k, l).endVertex();
                                worldRendererIn.pos(d5, d1 + (double)f43, d6).color(f35, f36, f37, 1.0F).tex((double)f28, (double)f33).lightmap(k, l).endVertex();
                                worldRendererIn.pos(d5, d1 + (double)f45, d6).color(f35, f36, f37, 1.0F).tex((double)f28, (double)f30).lightmap(k, l).endVertex();
                                worldRendererIn.pos(d3, d1 + (double)f44, d4).color(f35, f36, f37, 1.0F).tex((double)f27, (double)f29).lightmap(k, l).endVertex();
                            }
                        }
                    }
                }

                worldRendererIn.setSprite((TextureAtlasSprite)null);
                boolean flag4 = flag3;
                return flag4;
            }

            flag3 = false;
        }
        finally
        {
            if (Config.isShaders())
            {
                SVertexBuilder.popEntity(worldRendererIn);
            }
        }

        return flag3;
    }

    private float getFluidHeight(IBlockAccess blockAccess, BlockPos blockPosIn, Material blockMaterial)
    {
        int i = 0;
        float f = 0.0F;

        for (int j = 0; j < 4; ++j)
        {
            BlockPos blockpos = blockPosIn.add(-(j & 1), 0, -(j >> 1 & 1));

            if (blockAccess.getBlockState(blockpos.up()).getMaterial() == blockMaterial)
            {
                return 1.0F;
            }

            IBlockState iblockstate = blockAccess.getBlockState(blockpos);
            Material material = iblockstate.getMaterial();

            if (material != blockMaterial)
            {
                if (!material.isSolid())
                {
                    ++f;
                    ++i;
                }
            }
            else
            {
                int k = ((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue();

                if (k >= 8 || k == 0)
                {
                    f += BlockLiquid.getLiquidHeightPercent(k) * 10.0F;
                    i += 10;
                }

                f += BlockLiquid.getLiquidHeightPercent(k);
                ++i;
            }
        }

        return 1.0F - f / (float)i;
    }
}
