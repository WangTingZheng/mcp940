package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class CloudRenderer
{
    private Minecraft mc;
    private boolean updated = false;
    private boolean renderFancy = false;
    int cloudTickCounter;
    private Vec3d cloudColor;
    float partialTicks;
    private boolean updateRenderFancy = false;
    private int updateCloudTickCounter = 0;
    private Vec3d updateCloudColor = new Vec3d(-1.0D, -1.0D, -1.0D);
    private double updatePlayerX = 0.0D;
    private double updatePlayerY = 0.0D;
    private double updatePlayerZ = 0.0D;
    private int glListClouds = -1;

    public CloudRenderer(Minecraft p_i23_1_)
    {
        this.mc = p_i23_1_;
        this.glListClouds = GLAllocation.generateDisplayLists(1);
    }

    public void prepareToRender(boolean p_prepareToRender_1_, int p_prepareToRender_2_, float p_prepareToRender_3_, Vec3d p_prepareToRender_4_)
    {
        this.renderFancy = p_prepareToRender_1_;
        this.cloudTickCounter = p_prepareToRender_2_;
        this.partialTicks = p_prepareToRender_3_;
        this.cloudColor = p_prepareToRender_4_;
    }

    public boolean shouldUpdateGlList()
    {
        if (!this.updated)
        {
            return true;
        }
        else if (this.renderFancy != this.updateRenderFancy)
        {
            return true;
        }
        else if (this.cloudTickCounter >= this.updateCloudTickCounter + 20)
        {
            return true;
        }
        else if (Math.abs(this.cloudColor.x - this.updateCloudColor.x) > 0.003D)
        {
            return true;
        }
        else if (Math.abs(this.cloudColor.y - this.updateCloudColor.y) > 0.003D)
        {
            return true;
        }
        else if (Math.abs(this.cloudColor.z - this.updateCloudColor.z) > 0.003D)
        {
            return true;
        }
        else
        {
            Entity entity = this.mc.getRenderViewEntity();
            boolean flag = this.updatePlayerY + (double)entity.getEyeHeight() < 128.0D + (double)(this.mc.gameSettings.ofCloudsHeight * 128.0F);
            boolean flag1 = entity.prevPosY + (double)entity.getEyeHeight() < 128.0D + (double)(this.mc.gameSettings.ofCloudsHeight * 128.0F);
            return flag1 != flag;
        }
    }

    public void startUpdateGlList()
    {
        GL11.glNewList(this.glListClouds, GL11.GL_COMPILE);
    }

    public void endUpdateGlList()
    {
        GL11.glEndList();
        this.updateRenderFancy = this.renderFancy;
        this.updateCloudTickCounter = this.cloudTickCounter;
        this.updateCloudColor = this.cloudColor;
        this.updatePlayerX = this.mc.getRenderViewEntity().prevPosX;
        this.updatePlayerY = this.mc.getRenderViewEntity().prevPosY;
        this.updatePlayerZ = this.mc.getRenderViewEntity().prevPosZ;
        this.updated = true;
        GlStateManager.resetColor();
    }

    public void renderGlList()
    {
        Entity entity = this.mc.getRenderViewEntity();
        double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)this.partialTicks;
        double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double)this.partialTicks;
        double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)this.partialTicks;
        double d3 = (double)((float)(this.cloudTickCounter - this.updateCloudTickCounter) + this.partialTicks);
        float f = (float)(d0 - this.updatePlayerX + d3 * 0.03D);
        float f1 = (float)(d1 - this.updatePlayerY);
        float f2 = (float)(d2 - this.updatePlayerZ);
        GlStateManager.pushMatrix();

        if (this.renderFancy)
        {
            GlStateManager.translate(-f / 12.0F, -f1, -f2 / 12.0F);
        }
        else
        {
            GlStateManager.translate(-f, -f1, -f2);
        }

        GlStateManager.callList(this.glListClouds);
        GlStateManager.popMatrix();
        GlStateManager.resetColor();
    }

    public void reset()
    {
        this.updated = false;
    }
}
