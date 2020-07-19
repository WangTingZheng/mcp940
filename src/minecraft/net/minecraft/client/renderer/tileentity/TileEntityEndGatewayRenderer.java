package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class TileEntityEndGatewayRenderer extends TileEntityEndPortalRenderer
{
    private static final ResourceLocation END_GATEWAY_BEAM_TEXTURE = new ResourceLocation("textures/entity/end_gateway_beam.png");

    public void render(TileEntityEndPortal te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        GlStateManager.disableFog();
        TileEntityEndGateway tileentityendgateway = (TileEntityEndGateway)te;

        if (tileentityendgateway.isSpawning() || tileentityendgateway.isCoolingDown())
        {
            GlStateManager.alphaFunc(516, 0.1F);
            this.bindTexture(END_GATEWAY_BEAM_TEXTURE);
            float f = tileentityendgateway.isSpawning() ? tileentityendgateway.getSpawnPercent(partialTicks) : tileentityendgateway.getCooldownPercent(partialTicks);
            double d0 = tileentityendgateway.isSpawning() ? 256.0D - y : 50.0D;
            f = MathHelper.sin(f * (float)Math.PI);
            int i = MathHelper.floor((double)f * d0);
            float[] afloat = tileentityendgateway.isSpawning() ? EnumDyeColor.MAGENTA.getColorComponentValues() : EnumDyeColor.PURPLE.getColorComponentValues();
            TileEntityBeaconRenderer.renderBeamSegment(x, y, z, (double)partialTicks, (double)f, (double)tileentityendgateway.getWorld().getTotalWorldTime(), 0, i, afloat, 0.15D, 0.175D);
            TileEntityBeaconRenderer.renderBeamSegment(x, y, z, (double)partialTicks, (double)f, (double)tileentityendgateway.getWorld().getTotalWorldTime(), 0, -i, afloat, 0.15D, 0.175D);
        }

        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        GlStateManager.enableFog();
    }

    protected int getPasses(double p_191286_1_)
    {
        return super.getPasses(p_191286_1_) + 1;
    }

    protected float getOffset()
    {
        return 1.0F;
    }
}
