package net.minecraft.client.renderer.debug;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;

public class DebugRenderer
{
    public final DebugRenderer.IDebugRenderer pathfinding;
    public final DebugRenderer.IDebugRenderer water;
    public final DebugRenderer.IDebugRenderer chunkBorder;
    public final DebugRenderer.IDebugRenderer heightMap;
    public final DebugRenderer.IDebugRenderer collisionBox;
    public final DebugRenderer.IDebugRenderer neighborsUpdate;
    public final DebugRenderer.IDebugRenderer solidFace;
    private boolean chunkBorderEnabled;
    private boolean pathfindingEnabled;
    private boolean waterEnabled;
    private boolean heightMapEnabled;
    private boolean collisionBoxEnabled;
    private boolean neighborsUpdateEnabled;
    private boolean solidFaceEnabled;

    public DebugRenderer(Minecraft clientIn)
    {
        this.pathfinding = new DebugRendererPathfinding(clientIn);
        this.water = new DebugRendererWater(clientIn);
        this.chunkBorder = new DebugRendererChunkBorder(clientIn);
        this.heightMap = new DebugRendererHeightMap(clientIn);
        this.collisionBox = new DebugRendererCollisionBox(clientIn);
        this.neighborsUpdate = new DebugRendererNeighborsUpdate(clientIn);
        this.solidFace = new DebugRendererSolidFace(clientIn);
    }

    public boolean shouldRender()
    {
        return this.chunkBorderEnabled || this.pathfindingEnabled || this.waterEnabled || this.heightMapEnabled || this.collisionBoxEnabled || this.neighborsUpdateEnabled || this.solidFaceEnabled;
    }

    /**
     * Toggles the debug screen's visibility.
     */
    public boolean toggleChunkBorders()
    {
        this.chunkBorderEnabled = !this.chunkBorderEnabled;
        return this.chunkBorderEnabled;
    }

    public void renderDebug(float partialTicks, long finishTimeNano)
    {
        if (this.pathfindingEnabled)
        {
            this.pathfinding.render(partialTicks, finishTimeNano);
        }

        if (this.chunkBorderEnabled && !Minecraft.getMinecraft().isReducedDebug())
        {
            this.chunkBorder.render(partialTicks, finishTimeNano);
        }

        if (this.waterEnabled)
        {
            this.water.render(partialTicks, finishTimeNano);
        }

        if (this.heightMapEnabled)
        {
            this.heightMap.render(partialTicks, finishTimeNano);
        }

        if (this.collisionBoxEnabled)
        {
            this.collisionBox.render(partialTicks, finishTimeNano);
        }

        if (this.neighborsUpdateEnabled)
        {
            this.neighborsUpdate.render(partialTicks, finishTimeNano);
        }

        if (this.solidFaceEnabled)
        {
            this.solidFace.render(partialTicks, finishTimeNano);
        }
    }

    public static void renderDebugText(String p_191556_0_, int p_191556_1_, int p_191556_2_, int p_191556_3_, float p_191556_4_, int p_191556_5_)
    {
        renderDebugText(p_191556_0_, (double)p_191556_1_ + 0.5D, (double)p_191556_2_ + 0.5D, (double)p_191556_3_ + 0.5D, p_191556_4_, p_191556_5_);
    }

    public static void renderDebugText(String str, double x, double y, double z, float partialTicks, int color)
    {
        Minecraft minecraft = Minecraft.getMinecraft();

        if (minecraft.player != null && minecraft.getRenderManager() != null && minecraft.getRenderManager().options != null)
        {
            FontRenderer fontrenderer = minecraft.fontRenderer;
            EntityPlayer entityplayer = minecraft.player;
            double d0 = entityplayer.lastTickPosX + (entityplayer.posX - entityplayer.lastTickPosX) * (double)partialTicks;
            double d1 = entityplayer.lastTickPosY + (entityplayer.posY - entityplayer.lastTickPosY) * (double)partialTicks;
            double d2 = entityplayer.lastTickPosZ + (entityplayer.posZ - entityplayer.lastTickPosZ) * (double)partialTicks;
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)(x - d0), (float)(y - d1) + 0.07F, (float)(z - d2));
            GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.scale(0.02F, -0.02F, 0.02F);
            RenderManager rendermanager = minecraft.getRenderManager();
            GlStateManager.rotate(-rendermanager.playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate((float)(rendermanager.options.thirdPersonView == 2 ? 1 : -1) * rendermanager.playerViewX, 1.0F, 0.0F, 0.0F);
            GlStateManager.disableLighting();
            GlStateManager.enableTexture2D();
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.scale(-1.0F, 1.0F, 1.0F);
            fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, 0, color);
            GlStateManager.enableLighting();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
        }
    }

    public interface IDebugRenderer
    {
        void render(float partialTicks, long finishTimeNano);
    }
}
