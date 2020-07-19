package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.profiler.Profiler;
import org.lwjgl.opengl.GL11;

public class Lagometer
{
    private static Minecraft mc;
    private static GameSettings gameSettings;
    private static Profiler profiler;
    public static boolean active = false;
    public static Lagometer.TimerNano timerTick = new Lagometer.TimerNano();
    public static Lagometer.TimerNano timerScheduledExecutables = new Lagometer.TimerNano();
    public static Lagometer.TimerNano timerChunkUpload = new Lagometer.TimerNano();
    public static Lagometer.TimerNano timerChunkUpdate = new Lagometer.TimerNano();
    public static Lagometer.TimerNano timerVisibility = new Lagometer.TimerNano();
    public static Lagometer.TimerNano timerTerrain = new Lagometer.TimerNano();
    public static Lagometer.TimerNano timerServer = new Lagometer.TimerNano();
    private static long[] timesFrame = new long[512];
    private static long[] timesTick = new long[512];
    private static long[] timesScheduledExecutables = new long[512];
    private static long[] timesChunkUpload = new long[512];
    private static long[] timesChunkUpdate = new long[512];
    private static long[] timesVisibility = new long[512];
    private static long[] timesTerrain = new long[512];
    private static long[] timesServer = new long[512];
    private static boolean[] gcs = new boolean[512];
    private static int numRecordedFrameTimes = 0;
    private static long prevFrameTimeNano = -1L;
    private static long renderTimeNano = 0L;
    private static long memTimeStartMs = System.currentTimeMillis();
    private static long memStart = getMemoryUsed();
    private static long memTimeLast = memTimeStartMs;
    private static long memLast = memStart;
    private static long memTimeDiffMs = 1L;
    private static long memDiff = 0L;
    private static int memMbSec = 0;

    public static boolean updateMemoryAllocation()
    {
        long i = System.currentTimeMillis();
        long j = getMemoryUsed();
        boolean flag = false;

        if (j < memLast)
        {
            double d0 = (double)memDiff / 1000000.0D;
            double d1 = (double)memTimeDiffMs / 1000.0D;
            int k = (int)(d0 / d1);

            if (k > 0)
            {
                memMbSec = k;
            }

            memTimeStartMs = i;
            memStart = j;
            memTimeDiffMs = 0L;
            memDiff = 0L;
            flag = true;
        }
        else
        {
            memTimeDiffMs = i - memTimeStartMs;
            memDiff = j - memStart;
        }

        memTimeLast = i;
        memLast = j;
        return flag;
    }

    private static long getMemoryUsed()
    {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static void updateLagometer()
    {
        if (mc == null)
        {
            mc = Minecraft.getMinecraft();
            gameSettings = mc.gameSettings;
            profiler = mc.mcProfiler;
        }

        if (gameSettings.showDebugInfo && (gameSettings.ofLagometer || gameSettings.showLagometer))
        {
            active = true;
            long timeNowNano = System.nanoTime();

            if (prevFrameTimeNano == -1L)
            {
                prevFrameTimeNano = timeNowNano;
            }
            else
            {
                int j = numRecordedFrameTimes & timesFrame.length - 1;
                ++numRecordedFrameTimes;
                boolean flag = updateMemoryAllocation();
                timesFrame[j] = timeNowNano - prevFrameTimeNano - renderTimeNano;
                timesTick[j] = timerTick.timeNano;
                timesScheduledExecutables[j] = timerScheduledExecutables.timeNano;
                timesChunkUpload[j] = timerChunkUpload.timeNano;
                timesChunkUpdate[j] = timerChunkUpdate.timeNano;
                timesVisibility[j] = timerVisibility.timeNano;
                timesTerrain[j] = timerTerrain.timeNano;
                timesServer[j] = timerServer.timeNano;
                gcs[j] = flag;
                timerTick.reset();
                timerScheduledExecutables.reset();
                timerVisibility.reset();
                timerChunkUpdate.reset();
                timerChunkUpload.reset();
                timerTerrain.reset();
                timerServer.reset();
                prevFrameTimeNano = System.nanoTime();
            }
        }
        else
        {
            active = false;
            prevFrameTimeNano = -1L;
        }
    }

    public static void showLagometer(ScaledResolution p_showLagometer_0_)
    {
        if (gameSettings != null)
        {
            if (gameSettings.ofLagometer || gameSettings.showLagometer)
            {
                long i = System.nanoTime();
                GlStateManager.clear(256);
                GlStateManager.matrixMode(5889);
                GlStateManager.pushMatrix();
                GlStateManager.enableColorMaterial();
                GlStateManager.loadIdentity();
                GlStateManager.ortho(0.0D, (double)mc.displayWidth, (double)mc.displayHeight, 0.0D, 1000.0D, 3000.0D);
                GlStateManager.matrixMode(5888);
                GlStateManager.pushMatrix();
                GlStateManager.loadIdentity();
                GlStateManager.translate(0.0F, 0.0F, -2000.0F);
                GL11.glLineWidth(1.0F);
                GlStateManager.disableTexture2D();
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferbuilder = tessellator.getBuffer();
                bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);

                for (int j = 0; j < timesFrame.length; ++j)
                {
                    int k = (j - numRecordedFrameTimes & timesFrame.length - 1) * 100 / timesFrame.length;
                    k = k + 155;
                    float f = (float)mc.displayHeight;
                    long l = 0L;

                    if (gcs[j])
                    {
                        renderTime(j, timesFrame[j], k, k / 2, 0, f, bufferbuilder);
                    }
                    else
                    {
                        renderTime(j, timesFrame[j], k, k, k, f, bufferbuilder);
                        f = f - (float)renderTime(j, timesServer[j], k / 2, k / 2, k / 2, f, bufferbuilder);
                        f = f - (float)renderTime(j, timesTerrain[j], 0, k, 0, f, bufferbuilder);
                        f = f - (float)renderTime(j, timesVisibility[j], k, k, 0, f, bufferbuilder);
                        f = f - (float)renderTime(j, timesChunkUpdate[j], k, 0, 0, f, bufferbuilder);
                        f = f - (float)renderTime(j, timesChunkUpload[j], k, 0, k, f, bufferbuilder);
                        f = f - (float)renderTime(j, timesScheduledExecutables[j], 0, 0, k, f, bufferbuilder);
                        float f2 = f - (float)renderTime(j, timesTick[j], 0, k, k, f, bufferbuilder);
                    }
                }

                renderTimeDivider(0, timesFrame.length, 33333333L, 196, 196, 196, (float)mc.displayHeight, bufferbuilder);
                renderTimeDivider(0, timesFrame.length, 16666666L, 196, 196, 196, (float)mc.displayHeight, bufferbuilder);
                tessellator.draw();
                GlStateManager.enableTexture2D();
                int j2 = mc.displayHeight - 80;
                int k2 = mc.displayHeight - 160;
                mc.fontRenderer.drawString("30", 2, k2 + 1, -8947849);
                mc.fontRenderer.drawString("30", 1, k2, -3881788);
                mc.fontRenderer.drawString("60", 2, j2 + 1, -8947849);
                mc.fontRenderer.drawString("60", 1, j2, -3881788);
                GlStateManager.matrixMode(5889);
                GlStateManager.popMatrix();
                GlStateManager.matrixMode(5888);
                GlStateManager.popMatrix();
                GlStateManager.enableTexture2D();
                float f1 = 1.0F - (float)((double)(System.currentTimeMillis() - memTimeStartMs) / 1000.0D);
                f1 = Config.limit(f1, 0.0F, 1.0F);
                int l2 = (int)(170.0F + f1 * 85.0F);
                int i1 = (int)(100.0F + f1 * 55.0F);
                int j1 = (int)(10.0F + f1 * 10.0F);
                int k1 = l2 << 16 | i1 << 8 | j1;
                int l1 = 512 / p_showLagometer_0_.getScaleFactor() + 2;
                int i2 = mc.displayHeight / p_showLagometer_0_.getScaleFactor() - 8;
                GuiIngame guiingame = mc.ingameGUI;
                GuiIngame.drawRect(l1 - 1, i2 - 1, l1 + 50, i2 + 10, -1605349296);
                mc.fontRenderer.drawString(" " + memMbSec + " MB/s", l1, i2, k1);
                renderTimeNano = System.nanoTime() - i;
            }
        }
    }

    private static long renderTime(int p_renderTime_0_, long p_renderTime_1_, int p_renderTime_3_, int p_renderTime_4_, int p_renderTime_5_, float p_renderTime_6_, BufferBuilder p_renderTime_7_)
    {
        long i = p_renderTime_1_ / 200000L;

        if (i < 3L)
        {
            return 0L;
        }
        else
        {
            p_renderTime_7_.pos((double)((float)p_renderTime_0_ + 0.5F), (double)(p_renderTime_6_ - (float)i + 0.5F), 0.0D).color(p_renderTime_3_, p_renderTime_4_, p_renderTime_5_, 255).endVertex();
            p_renderTime_7_.pos((double)((float)p_renderTime_0_ + 0.5F), (double)(p_renderTime_6_ + 0.5F), 0.0D).color(p_renderTime_3_, p_renderTime_4_, p_renderTime_5_, 255).endVertex();
            return i;
        }
    }

    private static long renderTimeDivider(int p_renderTimeDivider_0_, int p_renderTimeDivider_1_, long p_renderTimeDivider_2_, int p_renderTimeDivider_4_, int p_renderTimeDivider_5_, int p_renderTimeDivider_6_, float p_renderTimeDivider_7_, BufferBuilder p_renderTimeDivider_8_)
    {
        long i = p_renderTimeDivider_2_ / 200000L;

        if (i < 3L)
        {
            return 0L;
        }
        else
        {
            p_renderTimeDivider_8_.pos((double)((float)p_renderTimeDivider_0_ + 0.5F), (double)(p_renderTimeDivider_7_ - (float)i + 0.5F), 0.0D).color(p_renderTimeDivider_4_, p_renderTimeDivider_5_, p_renderTimeDivider_6_, 255).endVertex();
            p_renderTimeDivider_8_.pos((double)((float)p_renderTimeDivider_1_ + 0.5F), (double)(p_renderTimeDivider_7_ - (float)i + 0.5F), 0.0D).color(p_renderTimeDivider_4_, p_renderTimeDivider_5_, p_renderTimeDivider_6_, 255).endVertex();
            return i;
        }
    }

    public static boolean isActive()
    {
        return active;
    }

    public static class TimerNano
    {
        public long timeStartNano = 0L;
        public long timeNano = 0L;

        public void start()
        {
            if (Lagometer.active)
            {
                if (this.timeStartNano == 0L)
                {
                    this.timeStartNano = System.nanoTime();
                }
            }
        }

        public void end()
        {
            if (Lagometer.active)
            {
                if (this.timeStartNano != 0L)
                {
                    this.timeNano += System.nanoTime() - this.timeStartNano;
                    this.timeStartNano = 0L;
                }
            }
        }

        private void reset()
        {
            this.timeNano = 0L;
            this.timeStartNano = 0L;
        }
    }
}
