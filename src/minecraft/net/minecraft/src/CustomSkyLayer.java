package net.minecraft.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.world.World;

public class CustomSkyLayer
{
    public String source = null;
    private int startFadeIn = -1;
    private int endFadeIn = -1;
    private int startFadeOut = -1;
    private int endFadeOut = -1;
    private int blend = 1;
    private boolean rotate = false;
    private float speed = 1.0F;
    private float[] axis;
    private RangeListInt days;
    private int daysLoop;
    private boolean weatherClear;
    private boolean weatherRain;
    private boolean weatherThunder;
    public int textureId;
    public static final float[] DEFAULT_AXIS = new float[] {1.0F, 0.0F, 0.0F};
    private static final String WEATHER_CLEAR = "clear";
    private static final String WEATHER_RAIN = "rain";
    private static final String WEATHER_THUNDER = "thunder";

    public CustomSkyLayer(Properties p_i32_1_, String p_i32_2_)
    {
        this.axis = DEFAULT_AXIS;
        this.days = null;
        this.daysLoop = 8;
        this.weatherClear = true;
        this.weatherRain = false;
        this.weatherThunder = false;
        this.textureId = -1;
        ConnectedParser connectedparser = new ConnectedParser("CustomSky");
        this.source = p_i32_1_.getProperty("source", p_i32_2_);
        this.startFadeIn = this.parseTime(p_i32_1_.getProperty("startFadeIn"));
        this.endFadeIn = this.parseTime(p_i32_1_.getProperty("endFadeIn"));
        this.startFadeOut = this.parseTime(p_i32_1_.getProperty("startFadeOut"));
        this.endFadeOut = this.parseTime(p_i32_1_.getProperty("endFadeOut"));
        this.blend = Blender.parseBlend(p_i32_1_.getProperty("blend"));
        this.rotate = this.parseBoolean(p_i32_1_.getProperty("rotate"), true);
        this.speed = this.parseFloat(p_i32_1_.getProperty("speed"), 1.0F);
        this.axis = this.parseAxis(p_i32_1_.getProperty("axis"), DEFAULT_AXIS);
        this.days = connectedparser.parseRangeListInt(p_i32_1_.getProperty("days"));
        this.daysLoop = connectedparser.parseInt(p_i32_1_.getProperty("daysLoop"), 8);
        List<String> list = this.parseWeatherList(p_i32_1_.getProperty("weather", "clear"));
        this.weatherClear = list.contains("clear");
        this.weatherRain = list.contains("rain");
        this.weatherThunder = list.contains("thunder");
    }

    private List<String> parseWeatherList(String p_parseWeatherList_1_)
    {
        List<String> list = Arrays.<String>asList("clear", "rain", "thunder");
        List<String> list1 = new ArrayList<String>();
        String[] astring = Config.tokenize(p_parseWeatherList_1_, " ");

        for (int i = 0; i < astring.length; ++i)
        {
            String s = astring[i];

            if (!list.contains(s))
            {
                Config.warn("Unknown weather: " + s);
            }
            else
            {
                list1.add(s);
            }
        }

        return list1;
    }

    private int parseTime(String p_parseTime_1_)
    {
        if (p_parseTime_1_ == null)
        {
            return -1;
        }
        else
        {
            String[] astring = Config.tokenize(p_parseTime_1_, ":");

            if (astring.length != 2)
            {
                Config.warn("Invalid time: " + p_parseTime_1_);
                return -1;
            }
            else
            {
                String s = astring[0];
                String s1 = astring[1];
                int i = Config.parseInt(s, -1);
                int j = Config.parseInt(s1, -1);

                if (i >= 0 && i <= 23 && j >= 0 && j <= 59)
                {
                    i = i - 6;

                    if (i < 0)
                    {
                        i += 24;
                    }

                    int k = i * 1000 + (int)((double)j / 60.0D * 1000.0D);
                    return k;
                }
                else
                {
                    Config.warn("Invalid time: " + p_parseTime_1_);
                    return -1;
                }
            }
        }
    }

    private boolean parseBoolean(String p_parseBoolean_1_, boolean p_parseBoolean_2_)
    {
        if (p_parseBoolean_1_ == null)
        {
            return p_parseBoolean_2_;
        }
        else if (p_parseBoolean_1_.toLowerCase().equals("true"))
        {
            return true;
        }
        else if (p_parseBoolean_1_.toLowerCase().equals("false"))
        {
            return false;
        }
        else
        {
            Config.warn("Unknown boolean: " + p_parseBoolean_1_);
            return p_parseBoolean_2_;
        }
    }

    private float parseFloat(String p_parseFloat_1_, float p_parseFloat_2_)
    {
        if (p_parseFloat_1_ == null)
        {
            return p_parseFloat_2_;
        }
        else
        {
            float f = Config.parseFloat(p_parseFloat_1_, Float.MIN_VALUE);

            if (f == Float.MIN_VALUE)
            {
                Config.warn("Invalid value: " + p_parseFloat_1_);
                return p_parseFloat_2_;
            }
            else
            {
                return f;
            }
        }
    }

    private float[] parseAxis(String p_parseAxis_1_, float[] p_parseAxis_2_)
    {
        if (p_parseAxis_1_ == null)
        {
            return p_parseAxis_2_;
        }
        else
        {
            String[] astring = Config.tokenize(p_parseAxis_1_, " ");

            if (astring.length != 3)
            {
                Config.warn("Invalid axis: " + p_parseAxis_1_);
                return p_parseAxis_2_;
            }
            else
            {
                float[] afloat = new float[3];

                for (int i = 0; i < astring.length; ++i)
                {
                    afloat[i] = Config.parseFloat(astring[i], Float.MIN_VALUE);

                    if (afloat[i] == Float.MIN_VALUE)
                    {
                        Config.warn("Invalid axis: " + p_parseAxis_1_);
                        return p_parseAxis_2_;
                    }

                    if (afloat[i] < -1.0F || afloat[i] > 1.0F)
                    {
                        Config.warn("Invalid axis values: " + p_parseAxis_1_);
                        return p_parseAxis_2_;
                    }
                }

                float f2 = afloat[0];
                float f = afloat[1];
                float f1 = afloat[2];

                if (f2 * f2 + f * f + f1 * f1 < 1.0E-5F)
                {
                    Config.warn("Invalid axis values: " + p_parseAxis_1_);
                    return p_parseAxis_2_;
                }
                else
                {
                    float[] afloat1 = new float[] {f1, f, -f2};
                    return afloat1;
                }
            }
        }
    }

    public boolean isValid(String p_isValid_1_)
    {
        if (this.source == null)
        {
            Config.warn("No source texture: " + p_isValid_1_);
            return false;
        }
        else
        {
            this.source = TextureUtils.fixResourcePath(this.source, TextureUtils.getBasePath(p_isValid_1_));

            if (this.startFadeIn >= 0 && this.endFadeIn >= 0 && this.endFadeOut >= 0)
            {
                int i = this.normalizeTime(this.endFadeIn - this.startFadeIn);

                if (this.startFadeOut < 0)
                {
                    this.startFadeOut = this.normalizeTime(this.endFadeOut - i);

                    if (this.timeBetween(this.startFadeOut, this.startFadeIn, this.endFadeIn))
                    {
                        this.startFadeOut = this.endFadeIn;
                    }
                }

                int j = this.normalizeTime(this.startFadeOut - this.endFadeIn);
                int k = this.normalizeTime(this.endFadeOut - this.startFadeOut);
                int l = this.normalizeTime(this.startFadeIn - this.endFadeOut);
                int i1 = i + j + k + l;

                if (i1 != 24000)
                {
                    Config.warn("Invalid fadeIn/fadeOut times, sum is not 24h: " + i1);
                    return false;
                }
                else if (this.speed < 0.0F)
                {
                    Config.warn("Invalid speed: " + this.speed);
                    return false;
                }
                else if (this.daysLoop <= 0)
                {
                    Config.warn("Invalid daysLoop: " + this.daysLoop);
                    return false;
                }
                else
                {
                    return true;
                }
            }
            else
            {
                Config.warn("Invalid times, required are: startFadeIn, endFadeIn and endFadeOut.");
                return false;
            }
        }
    }

    private int normalizeTime(int p_normalizeTime_1_)
    {
        while (p_normalizeTime_1_ >= 24000)
        {
            p_normalizeTime_1_ -= 24000;
        }

        while (p_normalizeTime_1_ < 0)
        {
            p_normalizeTime_1_ += 24000;
        }

        return p_normalizeTime_1_;
    }

    public void render(int p_render_1_, float p_render_2_, float p_render_3_, float p_render_4_)
    {
        float f = 1.0F - p_render_3_;
        float f1 = p_render_3_ - p_render_4_;
        float f2 = 0.0F;

        if (this.weatherClear)
        {
            f2 += f;
        }

        if (this.weatherRain)
        {
            f2 += f1;
        }

        if (this.weatherThunder)
        {
            f2 += p_render_4_;
        }

        f2 = Config.limit(f2, 0.0F, 1.0F);
        float f3 = f2 * this.getFadeBrightness(p_render_1_);
        f3 = Config.limit(f3, 0.0F, 1.0F);

        if (f3 >= 1.0E-4F)
        {
            GlStateManager.bindTexture(this.textureId);
            Blender.setupBlend(this.blend, f3);
            GlStateManager.pushMatrix();

            if (this.rotate)
            {
                GlStateManager.rotate(p_render_2_ * 360.0F * this.speed, this.axis[0], this.axis[1], this.axis[2]);
            }

            Tessellator tessellator = Tessellator.getInstance();
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
            this.renderSide(tessellator, 4);
            GlStateManager.pushMatrix();
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            this.renderSide(tessellator, 1);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            this.renderSide(tessellator, 0);
            GlStateManager.popMatrix();
            GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            this.renderSide(tessellator, 5);
            GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            this.renderSide(tessellator, 2);
            GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            this.renderSide(tessellator, 3);
            GlStateManager.popMatrix();
        }
    }

    private float getFadeBrightness(int p_getFadeBrightness_1_)
    {
        if (this.timeBetween(p_getFadeBrightness_1_, this.startFadeIn, this.endFadeIn))
        {
            int k = this.normalizeTime(this.endFadeIn - this.startFadeIn);
            int l = this.normalizeTime(p_getFadeBrightness_1_ - this.startFadeIn);
            return (float)l / (float)k;
        }
        else if (this.timeBetween(p_getFadeBrightness_1_, this.endFadeIn, this.startFadeOut))
        {
            return 1.0F;
        }
        else if (this.timeBetween(p_getFadeBrightness_1_, this.startFadeOut, this.endFadeOut))
        {
            int i = this.normalizeTime(this.endFadeOut - this.startFadeOut);
            int j = this.normalizeTime(p_getFadeBrightness_1_ - this.startFadeOut);
            return 1.0F - (float)j / (float)i;
        }
        else
        {
            return 0.0F;
        }
    }

    private void renderSide(Tessellator p_renderSide_1_, int p_renderSide_2_)
    {
        BufferBuilder bufferbuilder = p_renderSide_1_.getBuffer();
        double d0 = (double)(p_renderSide_2_ % 3) / 3.0D;
        double d1 = (double)(p_renderSide_2_ / 3) / 2.0D;
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(-100.0D, -100.0D, -100.0D).tex(d0, d1).endVertex();
        bufferbuilder.pos(-100.0D, -100.0D, 100.0D).tex(d0, d1 + 0.5D).endVertex();
        bufferbuilder.pos(100.0D, -100.0D, 100.0D).tex(d0 + 0.3333333333333333D, d1 + 0.5D).endVertex();
        bufferbuilder.pos(100.0D, -100.0D, -100.0D).tex(d0 + 0.3333333333333333D, d1).endVertex();
        p_renderSide_1_.draw();
    }

    public boolean isActive(World p_isActive_1_, int p_isActive_2_)
    {
        if (this.timeBetween(p_isActive_2_, this.endFadeOut, this.startFadeIn))
        {
            return false;
        }
        else
        {
            if (this.days != null)
            {
                long i = p_isActive_1_.getWorldTime();
                long j;

                for (j = i - (long)this.startFadeIn; j < 0L; j += (long)(24000 * this.daysLoop))
                {
                    ;
                }

                int k = (int)(j / 24000L);
                int l = k % this.daysLoop;

                if (!this.days.isInRange(l))
                {
                    return false;
                }
            }

            return true;
        }
    }

    private boolean timeBetween(int p_timeBetween_1_, int p_timeBetween_2_, int p_timeBetween_3_)
    {
        if (p_timeBetween_2_ <= p_timeBetween_3_)
        {
            return p_timeBetween_1_ >= p_timeBetween_2_ && p_timeBetween_1_ <= p_timeBetween_3_;
        }
        else
        {
            return p_timeBetween_1_ >= p_timeBetween_2_ || p_timeBetween_1_ <= p_timeBetween_3_;
        }
    }

    public String toString()
    {
        return "" + this.source + ", " + this.startFadeIn + "-" + this.endFadeIn + " " + this.startFadeOut + "-" + this.endFadeOut;
    }
}
