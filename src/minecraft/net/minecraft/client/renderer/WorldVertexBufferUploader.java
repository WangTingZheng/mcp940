package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.util.List;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;
import shadersmod.client.SVertexBuilder;

public class WorldVertexBufferUploader
{
    public void draw(BufferBuilder vertexBufferIn)
    {
        if (vertexBufferIn.getVertexCount() > 0)
        {
            VertexFormat vertexformat = vertexBufferIn.getVertexFormat();
            int i = vertexformat.getNextOffset();
            ByteBuffer bytebuffer = vertexBufferIn.getByteBuffer();
            List<VertexFormatElement> list = vertexformat.getElements();
            boolean flag = Reflector.ForgeVertexFormatElementEnumUseage_preDraw.exists();
            boolean flag1 = Reflector.ForgeVertexFormatElementEnumUseage_postDraw.exists();

            for (int j = 0; j < list.size(); ++j)
            {
                VertexFormatElement vertexformatelement = list.get(j);
                VertexFormatElement.EnumUsage vertexformatelement$enumusage = vertexformatelement.getUsage();

                if (flag)
                {
                    Reflector.callVoid(vertexformatelement$enumusage, Reflector.ForgeVertexFormatElementEnumUseage_preDraw, vertexformat, j, i, bytebuffer);
                }
                else
                {
                    int k = vertexformatelement.getType().getGlConstant();
                    int l = vertexformatelement.getIndex();
                    bytebuffer.position(vertexformat.getOffset(j));

                    switch (vertexformatelement$enumusage)
                    {
                        case POSITION:
                            GlStateManager.glVertexPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
                            GlStateManager.glEnableClientState(32884);
                            break;

                        case UV:
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + l);
                            GlStateManager.glTexCoordPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
                            GlStateManager.glEnableClientState(32888);
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                            break;

                        case COLOR:
                            GlStateManager.glColorPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
                            GlStateManager.glEnableClientState(32886);
                            break;

                        case NORMAL:
                            GlStateManager.glNormalPointer(k, i, bytebuffer);
                            GlStateManager.glEnableClientState(32885);
                    }
                }
            }

            if (vertexBufferIn.isMultiTexture())
            {
                vertexBufferIn.drawMultiTexture();
            }
            else if (Config.isShaders())
            {
                SVertexBuilder.drawArrays(vertexBufferIn.getDrawMode(), 0, vertexBufferIn.getVertexCount(), vertexBufferIn);
            }
            else
            {
                GlStateManager.glDrawArrays(vertexBufferIn.getDrawMode(), 0, vertexBufferIn.getVertexCount());
            }

            int j1 = 0;

            for (int k1 = list.size(); j1 < k1; ++j1)
            {
                VertexFormatElement vertexformatelement1 = list.get(j1);
                VertexFormatElement.EnumUsage vertexformatelement$enumusage1 = vertexformatelement1.getUsage();

                if (flag1)
                {
                    Reflector.callVoid(vertexformatelement$enumusage1, Reflector.ForgeVertexFormatElementEnumUseage_postDraw, vertexformat, j1, i, bytebuffer);
                }
                else
                {
                    int i1 = vertexformatelement1.getIndex();

                    switch (vertexformatelement$enumusage1)
                    {
                        case POSITION:
                            GlStateManager.glDisableClientState(32884);
                            break;

                        case UV:
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + i1);
                            GlStateManager.glDisableClientState(32888);
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                            break;

                        case COLOR:
                            GlStateManager.glDisableClientState(32886);
                            GlStateManager.resetColor();
                            break;

                        case NORMAL:
                            GlStateManager.glDisableClientState(32885);
                    }
                }
            }
        }

        vertexBufferIn.reset();
    }
}
