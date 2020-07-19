package shadersmod.client;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.ViewFrustum;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class ShadowUtils
{
    public static Iterator<RenderChunk> makeShadowChunkIterator(WorldClient world, double partialTicks, Entity viewEntity, int renderDistanceChunks, ViewFrustum viewFrustum)
    {
        float f = Shaders.getShadowRenderDistance();

        if (f > 0.0F && f < (float)((renderDistanceChunks - 1) * 16))
        {
            int i = MathHelper.ceil(f / 16.0F) + 1;
            float f6 = world.getCelestialAngleRadians((float)partialTicks);
            float f1 = Shaders.sunPathRotation * 0.017453292F;
            float f2 = f6 > ((float)Math.PI / 2F) && f6 < ((float)Math.PI * 3F / 2F) ? f6 + (float)Math.PI : f6;
            float f3 = -MathHelper.sin(f2);
            float f4 = MathHelper.cos(f2) * MathHelper.cos(f1);
            float f5 = -MathHelper.cos(f2) * MathHelper.sin(f1);
            BlockPos blockpos = new BlockPos(MathHelper.floor(viewEntity.posX) >> 4, MathHelper.floor(viewEntity.posY) >> 4, MathHelper.floor(viewEntity.posZ) >> 4);
            BlockPos blockpos1 = blockpos.add((double)(-f3 * (float)i), (double)(-f4 * (float)i), (double)(-f5 * (float)i));
            BlockPos blockpos2 = blockpos.add((double)(f3 * (float)renderDistanceChunks), (double)(f4 * (float)renderDistanceChunks), (double)(f5 * (float)renderDistanceChunks));
            IteratorRenderChunks iteratorrenderchunks = new IteratorRenderChunks(viewFrustum, blockpos1, blockpos2, i, i);
            return iteratorrenderchunks;
        }
        else
        {
            List<RenderChunk> list = Arrays.<RenderChunk>asList(viewFrustum.renderChunks);
            Iterator<RenderChunk> iterator = list.iterator();
            return iterator;
        }
    }
}
