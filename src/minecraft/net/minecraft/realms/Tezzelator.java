package net.minecraft.realms;

import net.minecraft.client.renderer.Tessellator;

public class Tezzelator
{
    public static Tessellator t = Tessellator.getInstance();
    public static final Tezzelator instance = new Tezzelator();

    public void end()
    {
        t.draw();
    }

    public Tezzelator vertex(double p_vertex_1_, double p_vertex_3_, double p_vertex_5_)
    {
        t.getBuffer().pos(p_vertex_1_, p_vertex_3_, p_vertex_5_);
        return this;
    }

    public void color(float p_color_1_, float p_color_2_, float p_color_3_, float p_color_4_)
    {
        t.getBuffer().color(p_color_1_, p_color_2_, p_color_3_, p_color_4_);
    }

    public void tex2(short p_tex2_1_, short p_tex2_2_)
    {
        t.getBuffer().lightmap(p_tex2_1_, p_tex2_2_);
    }

    public void normal(float p_normal_1_, float p_normal_2_, float p_normal_3_)
    {
        t.getBuffer().normal(p_normal_1_, p_normal_2_, p_normal_3_);
    }

    public void begin(int p_begin_1_, RealmsVertexFormat p_begin_2_)
    {
        t.getBuffer().begin(p_begin_1_, p_begin_2_.getVertexFormat());
    }

    public void endVertex()
    {
        t.getBuffer().endVertex();
    }

    public void offset(double p_offset_1_, double p_offset_3_, double p_offset_5_)
    {
        t.getBuffer().setTranslation(p_offset_1_, p_offset_3_, p_offset_5_);
    }

    public RealmsBufferBuilder color(int p_color_1_, int p_color_2_, int p_color_3_, int p_color_4_)
    {
        return new RealmsBufferBuilder(t.getBuffer().color(p_color_1_, p_color_2_, p_color_3_, p_color_4_));
    }

    public Tezzelator tex(double p_tex_1_, double p_tex_3_)
    {
        t.getBuffer().tex(p_tex_1_, p_tex_3_);
        return this;
    }
}
