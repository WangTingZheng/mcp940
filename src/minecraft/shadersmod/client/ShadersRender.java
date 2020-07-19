package shadersmod.client;

import java.nio.IntBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class ShadersRender
{
    private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");

    public static void setFrustrumPosition(ICamera frustum, double x, double y, double z)
    {
        frustum.setPosition(x, y, z);
    }

    public static void setupTerrain(RenderGlobal renderGlobal, Entity viewEntity, double partialTicks, ICamera camera, int frameCount, boolean playerSpectator)
    {
        renderGlobal.setupTerrain(viewEntity, partialTicks, camera, frameCount, playerSpectator);
    }

    public static void beginTerrainSolid()
    {
        if (Shaders.isRenderingWorld)
        {
            Shaders.fogEnabled = true;
            Shaders.useProgram(7);
        }
    }

    public static void beginTerrainCutoutMipped()
    {
        if (Shaders.isRenderingWorld)
        {
            Shaders.useProgram(7);
        }
    }

    public static void beginTerrainCutout()
    {
        if (Shaders.isRenderingWorld)
        {
            Shaders.useProgram(7);
        }
    }

    public static void endTerrain()
    {
        if (Shaders.isRenderingWorld)
        {
            Shaders.useProgram(3);
        }
    }

    public static void beginTranslucent()
    {
        if (Shaders.isRenderingWorld)
        {
            if (Shaders.usedDepthBuffers >= 2)
            {
                GlStateManager.setActiveTexture(33995);
                Shaders.checkGLError("pre copy depth");
                GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, Shaders.renderWidth, Shaders.renderHeight);
                Shaders.checkGLError("copy depth");
                GlStateManager.setActiveTexture(33984);
            }

            Shaders.useProgram(12);
        }
    }

    public static void endTranslucent()
    {
        if (Shaders.isRenderingWorld)
        {
            Shaders.useProgram(3);
        }
    }

    public static void renderHand0(EntityRenderer er, float par1, int par2)
    {
        if (!Shaders.isShadowPass)
        {
            boolean flag = Shaders.isItemToRenderMainTranslucent();
            boolean flag1 = Shaders.isItemToRenderOffTranslucent();

            if (!flag || !flag1)
            {
                Shaders.readCenterDepth();
                Shaders.beginHand();
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                Shaders.setSkipRenderHands(flag, flag1);
                er.renderHand(par1, par2, true, false, false);
                Shaders.endHand();
                Shaders.setHandsRendered(!flag, !flag1);
                Shaders.setSkipRenderHands(false, false);
            }
        }
    }

    public static void renderHand1(EntityRenderer er, float par1, int par2)
    {
        if (!Shaders.isShadowPass && !Shaders.isBothHandsRendered())
        {
            Shaders.readCenterDepth();
            GlStateManager.enableBlend();
            Shaders.beginHand();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            Shaders.setSkipRenderHands(Shaders.isHandRenderedMain(), Shaders.isHandRenderedOff());
            er.renderHand(par1, par2, true, false, true);
            Shaders.endHand();
            Shaders.setHandsRendered(true, true);
            Shaders.setSkipRenderHands(false, false);
        }
    }

    public static void renderItemFP(ItemRenderer itemRenderer, float par1, boolean renderTranslucent)
    {
        Shaders.setRenderingFirstPersonHand(true);
        GlStateManager.depthMask(true);

        if (renderTranslucent)
        {
            GlStateManager.depthFunc(519);
            GL11.glPushMatrix();
            IntBuffer intbuffer = Shaders.activeDrawBuffers;
            Shaders.setDrawBuffers(Shaders.drawBuffersNone);
            Shaders.renderItemKeepDepthMask = true;
            itemRenderer.renderItemInFirstPerson(par1);
            Shaders.renderItemKeepDepthMask = false;
            Shaders.setDrawBuffers(intbuffer);
            GL11.glPopMatrix();
        }

        GlStateManager.depthFunc(515);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        itemRenderer.renderItemInFirstPerson(par1);
        Shaders.setRenderingFirstPersonHand(false);
    }

    public static void renderFPOverlay(EntityRenderer er, float par1, int par2)
    {
        if (!Shaders.isShadowPass)
        {
            Shaders.beginFPOverlay();
            er.renderHand(par1, par2, false, true, false);
            Shaders.endFPOverlay();
        }
    }

    public static void beginBlockDamage()
    {
        if (Shaders.isRenderingWorld)
        {
            Shaders.useProgram(11);

            if (Shaders.programsID[11] == Shaders.programsID[7])
            {
                Shaders.setDrawBuffers(Shaders.drawBuffersColorAtt0);
                GlStateManager.depthMask(false);
            }
        }
    }

    public static void endBlockDamage()
    {
        if (Shaders.isRenderingWorld)
        {
            GlStateManager.depthMask(true);
            Shaders.useProgram(3);
        }
    }

    public static void renderShadowMap(EntityRenderer entityRenderer, int pass, float partialTicks, long finishTimeNano)
    {
        if (Shaders.usedShadowDepthBuffers > 0 && --Shaders.shadowPassCounter <= 0)
        {
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.mcProfiler.endStartSection("shadow pass");
            RenderGlobal renderglobal = minecraft.renderGlobal;
            Shaders.isShadowPass = true;
            Shaders.shadowPassCounter = Shaders.shadowPassInterval;
            Shaders.preShadowPassThirdPersonView = minecraft.gameSettings.thirdPersonView;
            minecraft.gameSettings.thirdPersonView = 1;
            Shaders.checkGLError("pre shadow");
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glPushMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glPushMatrix();
            minecraft.mcProfiler.endStartSection("shadow clear");
            EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.sfb);
            Shaders.checkGLError("shadow bind sfb");
            Shaders.useProgram(30);
            minecraft.mcProfiler.endStartSection("shadow camera");
            entityRenderer.setupCameraTransform(partialTicks, 2);
            Shaders.setCameraShadow(partialTicks);
            ActiveRenderInfo.updateRenderInfo(minecraft.player, minecraft.gameSettings.thirdPersonView == 2);
            Shaders.checkGLError("shadow camera");
            GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
            Shaders.checkGLError("shadow drawbuffers");
            GL11.glReadBuffer(0);
            Shaders.checkGLError("shadow readbuffer");
            EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, Shaders.sfbDepthTextures.get(0), 0);

            if (Shaders.usedShadowColorBuffers != 0)
            {
                EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, Shaders.sfbColorTextures.get(0), 0);
            }

            Shaders.checkFramebufferStatus("shadow fb");
            GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glClear(Shaders.usedShadowColorBuffers != 0 ? GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT : GL11.GL_DEPTH_BUFFER_BIT);
            Shaders.checkGLError("shadow clear");
            minecraft.mcProfiler.endStartSection("shadow frustum");
            ClippingHelper clippinghelper = ClippingHelperShadow.getInstance();
            minecraft.mcProfiler.endStartSection("shadow culling");
            Frustum frustum = new Frustum(clippinghelper);
            Entity entity = minecraft.getRenderViewEntity();
            double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks;
            double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks;
            double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks;
            frustum.setPosition(d0, d1, d2);
            GlStateManager.shadeModel(7425);
            GlStateManager.enableDepth();
            GlStateManager.depthFunc(515);
            GlStateManager.depthMask(true);
            GlStateManager.colorMask(true, true, true, true);
            GlStateManager.disableCull();
            minecraft.mcProfiler.endStartSection("shadow prepareterrain");
            minecraft.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            minecraft.mcProfiler.endStartSection("shadow setupterrain");
            int i = 0;
            i = entityRenderer.frameCount;
            entityRenderer.frameCount = i + 1;
            renderglobal.setupTerrain(entity, (double)partialTicks, frustum, i, minecraft.player.isSpectator());
            minecraft.mcProfiler.endStartSection("shadow updatechunks");
            minecraft.mcProfiler.endStartSection("shadow terrain");
            GlStateManager.matrixMode(5888);
            GlStateManager.pushMatrix();
            GlStateManager.disableAlpha();
            renderglobal.renderBlockLayer(BlockRenderLayer.SOLID, (double)partialTicks, 2, entity);
            Shaders.checkGLError("shadow terrain solid");
            GlStateManager.enableAlpha();
            renderglobal.renderBlockLayer(BlockRenderLayer.CUTOUT_MIPPED, (double)partialTicks, 2, entity);
            Shaders.checkGLError("shadow terrain cutoutmipped");
            minecraft.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
            renderglobal.renderBlockLayer(BlockRenderLayer.CUTOUT, (double)partialTicks, 2, entity);
            Shaders.checkGLError("shadow terrain cutout");
            minecraft.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
            GlStateManager.shadeModel(7424);
            GlStateManager.alphaFunc(516, 0.1F);
            GlStateManager.matrixMode(5888);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            minecraft.mcProfiler.endStartSection("shadow entities");

            if (Reflector.ForgeHooksClient_setRenderPass.exists())
            {
                Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, Integer.valueOf(0));
            }

            renderglobal.renderEntities(entity, frustum, partialTicks);
            Shaders.checkGLError("shadow entities");
            GlStateManager.matrixMode(5888);
            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
            GlStateManager.disableBlend();
            GlStateManager.enableCull();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.alphaFunc(516, 0.1F);

            if (Shaders.usedShadowDepthBuffers >= 2)
            {
                GlStateManager.setActiveTexture(33989);
                Shaders.checkGLError("pre copy shadow depth");
                GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, Shaders.shadowMapWidth, Shaders.shadowMapHeight);
                Shaders.checkGLError("copy shadow depth");
                GlStateManager.setActiveTexture(33984);
            }

            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            minecraft.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            GlStateManager.shadeModel(7425);
            Shaders.checkGLError("shadow pre-translucent");
            GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
            Shaders.checkGLError("shadow drawbuffers pre-translucent");
            Shaders.checkFramebufferStatus("shadow pre-translucent");

            if (Shaders.isRenderShadowTranslucent())
            {
                minecraft.mcProfiler.endStartSection("shadow translucent");
                renderglobal.renderBlockLayer(BlockRenderLayer.TRANSLUCENT, (double)partialTicks, 2, entity);
                Shaders.checkGLError("shadow translucent");
            }

            if (Reflector.ForgeHooksClient_setRenderPass.exists())
            {
                RenderHelper.enableStandardItemLighting();
                Reflector.call(Reflector.ForgeHooksClient_setRenderPass, Integer.valueOf(1));
                renderglobal.renderEntities(entity, frustum, partialTicks);
                Reflector.call(Reflector.ForgeHooksClient_setRenderPass, Integer.valueOf(-1));
                RenderHelper.disableStandardItemLighting();
                Shaders.checkGLError("shadow entities 1");
            }

            GlStateManager.shadeModel(7424);
            GlStateManager.depthMask(true);
            GlStateManager.enableCull();
            GlStateManager.disableBlend();
            GL11.glFlush();
            Shaders.checkGLError("shadow flush");
            Shaders.isShadowPass = false;
            minecraft.gameSettings.thirdPersonView = Shaders.preShadowPassThirdPersonView;
            minecraft.mcProfiler.endStartSection("shadow postprocess");

            if (Shaders.hasGlGenMipmap)
            {
                if (Shaders.usedShadowDepthBuffers >= 1)
                {
                    if (Shaders.shadowMipmapEnabled[0])
                    {
                        GlStateManager.setActiveTexture(33988);
                        GlStateManager.bindTexture(Shaders.sfbDepthTextures.get(0));
                        GL30.glGenerateMipmap(3553);
                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, Shaders.shadowFilterNearest[0] ? GL11.GL_NEAREST_MIPMAP_NEAREST : GL11.GL_LINEAR_MIPMAP_LINEAR);
                    }

                    if (Shaders.usedShadowDepthBuffers >= 2 && Shaders.shadowMipmapEnabled[1])
                    {
                        GlStateManager.setActiveTexture(33989);
                        GlStateManager.bindTexture(Shaders.sfbDepthTextures.get(1));
                        GL30.glGenerateMipmap(3553);
                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, Shaders.shadowFilterNearest[1] ? GL11.GL_NEAREST_MIPMAP_NEAREST : GL11.GL_LINEAR_MIPMAP_LINEAR);
                    }

                    GlStateManager.setActiveTexture(33984);
                }

                if (Shaders.usedShadowColorBuffers >= 1)
                {
                    if (Shaders.shadowColorMipmapEnabled[0])
                    {
                        GlStateManager.setActiveTexture(33997);
                        GlStateManager.bindTexture(Shaders.sfbColorTextures.get(0));
                        GL30.glGenerateMipmap(3553);
                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, Shaders.shadowColorFilterNearest[0] ? GL11.GL_NEAREST_MIPMAP_NEAREST : GL11.GL_LINEAR_MIPMAP_LINEAR);
                    }

                    if (Shaders.usedShadowColorBuffers >= 2 && Shaders.shadowColorMipmapEnabled[1])
                    {
                        GlStateManager.setActiveTexture(33998);
                        GlStateManager.bindTexture(Shaders.sfbColorTextures.get(1));
                        GL30.glGenerateMipmap(3553);
                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, Shaders.shadowColorFilterNearest[1] ? GL11.GL_NEAREST_MIPMAP_NEAREST : GL11.GL_LINEAR_MIPMAP_LINEAR);
                    }

                    GlStateManager.setActiveTexture(33984);
                }
            }

            Shaders.checkGLError("shadow postprocess");
            EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.dfb);
            GL11.glViewport(0, 0, Shaders.renderWidth, Shaders.renderHeight);
            Shaders.activeDrawBuffers = null;
            minecraft.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Shaders.useProgram(7);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            Shaders.checkGLError("shadow end");
        }
    }

    public static void preRenderChunkLayer(BlockRenderLayer blockLayerIn)
    {
        if (Shaders.isRenderBackFace(blockLayerIn))
        {
            GlStateManager.disableCull();
        }

        if (OpenGlHelper.useVbo())
        {
            GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
            GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
            GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
            GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
        }
    }

    public static void postRenderChunkLayer(BlockRenderLayer blockLayerIn)
    {
        if (OpenGlHelper.useVbo())
        {
            GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
            GL20.glDisableVertexAttribArray(Shaders.midTexCoordAttrib);
            GL20.glDisableVertexAttribArray(Shaders.tangentAttrib);
            GL20.glDisableVertexAttribArray(Shaders.entityAttrib);
        }

        if (Shaders.isRenderBackFace(blockLayerIn))
        {
            GlStateManager.enableCull();
        }
    }

    public static void setupArrayPointersVbo()
    {
        int i = 14;
        GL11.glVertexPointer(3, GL11.GL_FLOAT, 56, 0L);
        GL11.glColorPointer(4, GL11.GL_UNSIGNED_BYTE, 56, 12L);
        GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 56, 16L);
        OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glTexCoordPointer(2, GL11.GL_SHORT, 56, 24L);
        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glNormalPointer(GL11.GL_BYTE, 56, 28L);
        GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, GL11.GL_FLOAT, false, 56, 32L);
        GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, GL11.GL_SHORT, false, 56, 40L);
        GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, GL11.GL_SHORT, false, 56, 48L);
    }

    public static void beaconBeamBegin()
    {
        Shaders.useProgram(14);
    }

    public static void beaconBeamStartQuad1()
    {
    }

    public static void beaconBeamStartQuad2()
    {
    }

    public static void beaconBeamDraw1()
    {
    }

    public static void beaconBeamDraw2()
    {
        GlStateManager.disableBlend();
    }

    public static void renderEnchantedGlintBegin()
    {
        Shaders.useProgram(17);
    }

    public static void renderEnchantedGlintEnd()
    {
        if (Shaders.isRenderingWorld)
        {
            if (Shaders.isRenderingFirstPersonHand() && Shaders.isRenderBothHands())
            {
                Shaders.useProgram(19);
            }
            else
            {
                Shaders.useProgram(16);
            }
        }
        else
        {
            Shaders.useProgram(0);
        }
    }

    public static boolean renderEndPortal(TileEntityEndPortal te, double x, double y, double z, float partialTicks, int destroyStage, float offset)
    {
        if (!Shaders.isShadowPass && Shaders.programsID[Shaders.activeProgram] == 0)
        {
            return false;
        }
        else
        {
            GlStateManager.disableLighting();
            Config.getTextureManager().bindTexture(END_PORTAL_TEXTURE);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.BLOCK);
            float f = 0.5F;
            float f1 = f * 0.15F;
            float f2 = f * 0.3F;
            float f3 = f * 0.4F;
            float f4 = 0.0F;
            float f5 = 0.2F;
            float f6 = (float)(System.currentTimeMillis() % 100000L) / 100000.0F;
            int i = 240;

            if (te.shouldRenderFace(EnumFacing.SOUTH))
            {
                bufferbuilder.pos(x, y, z + 1.0D).color(f1, f2, f3, 1.0F).tex((double)(f4 + f6), (double)(f4 + f6)).lightmap(i, i).endVertex();
                bufferbuilder.pos(x + 1.0D, y, z + 1.0D).color(f1, f2, f3, 1.0F).tex((double)(f4 + f6), (double)(f5 + f6)).lightmap(i, i).endVertex();
                bufferbuilder.pos(x + 1.0D, y + 1.0D, z + 1.0D).color(f1, f2, f3, 1.0F).tex((double)(f5 + f6), (double)(f5 + f6)).lightmap(i, i).endVertex();
                bufferbuilder.pos(x, y + 1.0D, z + 1.0D).color(f1, f2, f3, 1.0F).tex((double)(f5 + f6), (double)(f4 + f6)).lightmap(i, i).endVertex();
            }

            if (te.shouldRenderFace(EnumFacing.NORTH))
            {
                bufferbuilder.pos(x, y + 1.0D, z).color(f1, f2, f3, 1.0F).tex((double)(f5 + f6), (double)(f5 + f6)).lightmap(i, i).endVertex();
                bufferbuilder.pos(x + 1.0D, y + 1.0D, z).color(f1, f2, f3, 1.0F).tex((double)(f5 + f6), (double)(f4 + f6)).lightmap(i, i).endVertex();
                bufferbuilder.pos(x + 1.0D, y, z).color(f1, f2, f3, 1.0F).tex((double)(f4 + f6), (double)(f4 + f6)).lightmap(i, i).endVertex();
                bufferbuilder.pos(x, y, z).color(f1, f2, f3, 1.0F).tex((double)(f4 + f6), (double)(f5 + f6)).lightmap(i, i).endVertex();
            }

            if (te.shouldRenderFace(EnumFacing.EAST))
            {
                bufferbuilder.pos(x + 1.0D, y + 1.0D, z).color(f1, f2, f3, 1.0F).tex((double)(f5 + f6), (double)(f5 + f6)).lightmap(i, i).endVertex();
                bufferbuilder.pos(x + 1.0D, y + 1.0D, z + 1.0D).color(f1, f2, f3, 1.0F).tex((double)(f5 + f6), (double)(f4 + f6)).lightmap(i, i).endVertex();
                bufferbuilder.pos(x + 1.0D, y, z + 1.0D).color(f1, f2, f3, 1.0F).tex((double)(f4 + f6), (double)(f4 + f6)).lightmap(i, i).endVertex();
                bufferbuilder.pos(x + 1.0D, y, z).color(f1, f2, f3, 1.0F).tex((double)(f4 + f6), (double)(f5 + f6)).lightmap(i, i).endVertex();
            }

            if (te.shouldRenderFace(EnumFacing.WEST))
            {
                bufferbuilder.pos(x, y, z).color(f1, f2, f3, 1.0F).tex((double)(f4 + f6), (double)(f4 + f6)).lightmap(i, i).endVertex();
                bufferbuilder.pos(x, y, z + 1.0D).color(f1, f2, f3, 1.0F).tex((double)(f4 + f6), (double)(f5 + f6)).lightmap(i, i).endVertex();
                bufferbuilder.pos(x, y + 1.0D, z + 1.0D).color(f1, f2, f3, 1.0F).tex((double)(f5 + f6), (double)(f5 + f6)).lightmap(i, i).endVertex();
                bufferbuilder.pos(x, y + 1.0D, z).color(f1, f2, f3, 1.0F).tex((double)(f5 + f6), (double)(f4 + f6)).lightmap(i, i).endVertex();
            }

            if (te.shouldRenderFace(EnumFacing.DOWN))
            {
                bufferbuilder.pos(x, y, z).color(f1, f2, f3, 1.0F).tex((double)(f4 + f6), (double)(f4 + f6)).lightmap(i, i).endVertex();
                bufferbuilder.pos(x + 1.0D, y, z).color(f1, f2, f3, 1.0F).tex((double)(f4 + f6), (double)(f5 + f6)).lightmap(i, i).endVertex();
                bufferbuilder.pos(x + 1.0D, y, z + 1.0D).color(f1, f2, f3, 1.0F).tex((double)(f5 + f6), (double)(f5 + f6)).lightmap(i, i).endVertex();
                bufferbuilder.pos(x, y, z + 1.0D).color(f1, f2, f3, 1.0F).tex((double)(f5 + f6), (double)(f4 + f6)).lightmap(i, i).endVertex();
            }

            if (te.shouldRenderFace(EnumFacing.UP))
            {
                bufferbuilder.pos(x, y + (double)offset, z + 1.0D).color(f1, f2, f3, 1.0F).tex((double)(f4 + f6), (double)(f4 + f6)).lightmap(i, i).endVertex();
                bufferbuilder.pos(x + 1.0D, y + (double)offset, z + 1.0D).color(f1, f2, f3, 1.0F).tex((double)(f4 + f6), (double)(f5 + f6)).lightmap(i, i).endVertex();
                bufferbuilder.pos(x + 1.0D, y + (double)offset, z).color(f1, f2, f3, 1.0F).tex((double)(f5 + f6), (double)(f5 + f6)).lightmap(i, i).endVertex();
                bufferbuilder.pos(x, y + (double)offset, z).color(f1, f2, f3, 1.0F).tex((double)(f5 + f6), (double)(f4 + f6)).lightmap(i, i).endVertex();
            }

            tessellator.draw();
            GlStateManager.enableLighting();
            return true;
        }
    }
}
