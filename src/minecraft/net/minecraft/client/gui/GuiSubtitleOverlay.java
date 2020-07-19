package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ISoundEventListener;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class GuiSubtitleOverlay extends Gui implements ISoundEventListener
{
    private final Minecraft client;
    private final List<GuiSubtitleOverlay.Subtitle> subtitles = Lists.<GuiSubtitleOverlay.Subtitle>newArrayList();
    private boolean enabled;

    public GuiSubtitleOverlay(Minecraft clientIn)
    {
        this.client = clientIn;
    }

    public void renderSubtitles(ScaledResolution resolution)
    {
        if (!this.enabled && this.client.gameSettings.showSubtitles)
        {
            this.client.getSoundHandler().addListener(this);
            this.enabled = true;
        }
        else if (this.enabled && !this.client.gameSettings.showSubtitles)
        {
            this.client.getSoundHandler().removeListener(this);
            this.enabled = false;
        }

        if (this.enabled && !this.subtitles.isEmpty())
        {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            Vec3d vec3d = new Vec3d(this.client.player.posX, this.client.player.posY + (double)this.client.player.getEyeHeight(), this.client.player.posZ);
            Vec3d vec3d1 = (new Vec3d(0.0D, 0.0D, -1.0D)).rotatePitch(-this.client.player.rotationPitch * 0.017453292F).rotateYaw(-this.client.player.rotationYaw * 0.017453292F);
            Vec3d vec3d2 = (new Vec3d(0.0D, 1.0D, 0.0D)).rotatePitch(-this.client.player.rotationPitch * 0.017453292F).rotateYaw(-this.client.player.rotationYaw * 0.017453292F);
            Vec3d vec3d3 = vec3d1.crossProduct(vec3d2);
            int i = 0;
            int j = 0;
            Iterator<GuiSubtitleOverlay.Subtitle> iterator = this.subtitles.iterator();

            while (iterator.hasNext())
            {
                GuiSubtitleOverlay.Subtitle guisubtitleoverlay$subtitle = iterator.next();

                if (guisubtitleoverlay$subtitle.getStartTime() + 3000L <= Minecraft.getSystemTime())
                {
                    iterator.remove();
                }
                else
                {
                    j = Math.max(j, this.client.fontRenderer.getStringWidth(guisubtitleoverlay$subtitle.getString()));
                }
            }

            j = j + this.client.fontRenderer.getStringWidth("<") + this.client.fontRenderer.getStringWidth(" ") + this.client.fontRenderer.getStringWidth(">") + this.client.fontRenderer.getStringWidth(" ");

            for (GuiSubtitleOverlay.Subtitle guisubtitleoverlay$subtitle1 : this.subtitles)
            {
                int k = 255;
                String s = guisubtitleoverlay$subtitle1.getString();
                Vec3d vec3d4 = guisubtitleoverlay$subtitle1.getLocation().subtract(vec3d).normalize();
                double d0 = -vec3d3.dotProduct(vec3d4);
                double d1 = -vec3d1.dotProduct(vec3d4);
                boolean flag = d1 > 0.5D;
                int l = j / 2;
                int i1 = this.client.fontRenderer.FONT_HEIGHT;
                int j1 = i1 / 2;
                float f = 1.0F;
                int k1 = this.client.fontRenderer.getStringWidth(s);
                int l1 = MathHelper.floor(MathHelper.clampedLerp(255.0D, 75.0D, (double)((float)(Minecraft.getSystemTime() - guisubtitleoverlay$subtitle1.getStartTime()) / 3000.0F)));
                int i2 = l1 << 16 | l1 << 8 | l1;
                GlStateManager.pushMatrix();
                GlStateManager.translate((float)resolution.getScaledWidth() - (float)l * 1.0F - 2.0F, (float)(resolution.getScaledHeight() - 30) - (float)(i * (i1 + 1)) * 1.0F, 0.0F);
                GlStateManager.scale(1.0F, 1.0F, 1.0F);
                drawRect(-l - 1, -j1 - 1, l + 1, j1 + 1, -872415232);
                GlStateManager.enableBlend();

                if (!flag)
                {
                    if (d0 > 0.0D)
                    {
                        this.client.fontRenderer.drawString(">", l - this.client.fontRenderer.getStringWidth(">"), -j1, i2 + -16777216);
                    }
                    else if (d0 < 0.0D)
                    {
                        this.client.fontRenderer.drawString("<", -l, -j1, i2 + -16777216);
                    }
                }

                this.client.fontRenderer.drawString(s, -k1 / 2, -j1, i2 + -16777216);
                GlStateManager.popMatrix();
                ++i;
            }

            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    public void soundPlay(ISound soundIn, SoundEventAccessor accessor)
    {
        if (accessor.getSubtitle() != null)
        {
            String s = accessor.getSubtitle().getFormattedText();

            if (!this.subtitles.isEmpty())
            {
                for (GuiSubtitleOverlay.Subtitle guisubtitleoverlay$subtitle : this.subtitles)
                {
                    if (guisubtitleoverlay$subtitle.getString().equals(s))
                    {
                        guisubtitleoverlay$subtitle.refresh(new Vec3d((double)soundIn.getXPosF(), (double)soundIn.getYPosF(), (double)soundIn.getZPosF()));
                        return;
                    }
                }
            }

            this.subtitles.add(new GuiSubtitleOverlay.Subtitle(s, new Vec3d((double)soundIn.getXPosF(), (double)soundIn.getYPosF(), (double)soundIn.getZPosF())));
        }
    }

    public class Subtitle
    {
        private final String subtitle;
        private long startTime;
        private Vec3d location;

        public Subtitle(String subtitleIn, Vec3d locationIn)
        {
            this.subtitle = subtitleIn;
            this.location = locationIn;
            this.startTime = Minecraft.getSystemTime();
        }

        public String getString()
        {
            return this.subtitle;
        }

        public long getStartTime()
        {
            return this.startTime;
        }

        public Vec3d getLocation()
        {
            return this.location;
        }

        public void refresh(Vec3d locationIn)
        {
            this.location = locationIn;
            this.startTime = Minecraft.getSystemTime();
        }
    }
}
