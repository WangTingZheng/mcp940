package net.minecraft.client.renderer.debug;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DebugRendererWater implements DebugRenderer.IDebugRenderer
{
    private final Minecraft minecraft;
    private EntityPlayer player;
    private double xo;
    private double yo;
    private double zo;

    public DebugRendererWater(Minecraft minecraftIn)
    {
        this.minecraft = minecraftIn;
    }

    public void render(float partialTicks, long finishTimeNano)
    {
        this.player = this.minecraft.player;
        this.xo = this.player.lastTickPosX + (this.player.posX - this.player.lastTickPosX) * (double)partialTicks;
        this.yo = this.player.lastTickPosY + (this.player.posY - this.player.lastTickPosY) * (double)partialTicks;
        this.zo = this.player.lastTickPosZ + (this.player.posZ - this.player.lastTickPosZ) * (double)partialTicks;
        BlockPos blockpos = this.minecraft.player.getPosition();
        World world = this.minecraft.player.world;
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(0.0F, 1.0F, 0.0F, 0.75F);
        GlStateManager.disableTexture2D();
        GlStateManager.glLineWidth(6.0F);

        for (BlockPos blockpos1 : BlockPos.getAllInBox(blockpos.add(-10, -10, -10), blockpos.add(10, 10, 10)))
        {
            IBlockState iblockstate = world.getBlockState(blockpos1);

            if (iblockstate.getBlock() == Blocks.WATER || iblockstate.getBlock() == Blocks.FLOWING_WATER)
            {
                double d0 = (double)BlockLiquid.getLiquidHeight(iblockstate, world, blockpos1);
                RenderGlobal.renderFilledBox((new AxisAlignedBB((double)((float)blockpos1.getX() + 0.01F), (double)((float)blockpos1.getY() + 0.01F), (double)((float)blockpos1.getZ() + 0.01F), (double)((float)blockpos1.getX() + 0.99F), d0, (double)((float)blockpos1.getZ() + 0.99F))).offset(-this.xo, -this.yo, -this.zo), 1.0F, 1.0F, 1.0F, 0.2F);
            }
        }

        for (BlockPos blockpos2 : BlockPos.getAllInBox(blockpos.add(-10, -10, -10), blockpos.add(10, 10, 10)))
        {
            IBlockState iblockstate1 = world.getBlockState(blockpos2);

            if (iblockstate1.getBlock() == Blocks.WATER || iblockstate1.getBlock() == Blocks.FLOWING_WATER)
            {
                Integer integer = (Integer)iblockstate1.getValue(BlockLiquid.LEVEL);
                double d1 = integer.intValue() > 7 ? 0.9D : 1.0D - 0.11D * (double)integer.intValue();
                String s = iblockstate1.getBlock() == Blocks.FLOWING_WATER ? "f" : "s";
                DebugRenderer.renderDebugText(s + " " + integer, (double)blockpos2.getX() + 0.5D, (double)blockpos2.getY() + d1, (double)blockpos2.getZ() + 0.5D, partialTicks, -16777216);
            }
        }

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
